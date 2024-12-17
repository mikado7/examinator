<script setup lang="ts">

import type {Exam} from "@/db/domain/exam";
import {onBeforeMount, ref} from "vue";
import {loadExamWithQuestions} from "@/db/service/exam_service";
import {EXAM_EDITOR_ID, QUESTION_EDITOR_ID} from "@/const";
import QuestionEditor from "@/components/question/QuestionEditor.vue";
import {toggleModal} from "@/components/modal/modal";
import type {Question} from "@/db/domain/question";
import QuestionComponent from "@/components/question/QuestionComponent.vue"
import ExamEditor from "@/components/exam/ExamEditor.vue";
import AnswerComponent from "@/components/answer/AnswerComponent.vue";
import Icon from "@/components/icon/Icon.vue";
import ExamPrint from "@/views/ExamPrint.vue";
import Modal from "@/components/modal/Modal.vue";
import {deleteQuestion, saveQuestion} from "@/db/service/question_service";
import {useRouter} from "vue-router";

const DELETE_QUESTION_MODAL_ID = 'delete-question-dialog'

const router = useRouter()

const props = defineProps({
  examId: {
    type: String,
    required: true,
  }
});

const data = ref<{ exam: Exam, questions: Question[] }>()
const selectedQuestion = ref<Question>({
  content: '',
  examId: data.value?.exam.id,
  sequence: 1,
  answers: []
})

onBeforeMount(async () => {
  const examId = Number(props.examId)
  try {
    data.value = await loadExamWithQuestions(examId)
  } catch (e) {
    await router.push({ name: 'notFound' })
  }
})


const createNewQuestion = (e: Event) => {
  if (data.value?.exam.id) {
    selectedQuestion.value =
      {
        sequence: data.value?.questions.length + 1,
        content: '',
        examId: data.value?.exam.id,
        answers: []
      }
  }
  toggleModal(e)
}

const handleEditorOutput = (editorOutput: Question) => {
  console.log(JSON.stringify(editorOutput))
  if (!data.value?.questions) throw new Error("Exam does not have any questions!")
  const index = data.value.questions.findIndex(q => q.id === editorOutput.id);
  if (index !== -1) {
    data.value?.questions.splice(index, 1, editorOutput);
  } else {
    data.value?.questions.push(editorOutput);
  }
}

const editQuestion = (question: Question, e: Event) => {
  if (!data.value?.exam) return
  selectedQuestion.value = {...question, examId: data.value?.exam.id}
  selectedQuestion.value.answers = question.answers.map(a => ({...a}))
  toggleModal(e)
}

const editExam = (e: Event) => {
  toggleModal(e)
}

const toggleDeleteModal = (e: MouseEvent, question: Question) => {
  const xPos = e.clientX;
  const yPos = e.clientY;
  const deleteQuestionDialog = document.querySelector(`#${DELETE_QUESTION_MODAL_ID}`) as HTMLDialogElement
  if (!deleteQuestionDialog) return
  deleteQuestionDialog.style.left = `calc(${xPos}px - 5rem)`
  deleteQuestionDialog.style.top = `calc(${yPos}px - 5rem)`
  selectedQuestion.value = question
  toggleModal(e)
}

const deleteQ = async (e: Event) => {
  const index = data.value?.questions?.indexOf(selectedQuestion.value)
  if (selectedQuestion.value && index !== -1) {
    if (selectedQuestion.value.id) await deleteQuestion(selectedQuestion.value.id)
    const index = data.value?.questions?.indexOf(selectedQuestion.value)
    data.value?.questions?.splice(index!, 1)
  }
  toggleModal(e)
}

const mode = ref<'printing' | 'editing'>('editing')
const handleModeChange = (s: 'printing' | 'editing') => {
  mode.value = s
}

const dragStart = (event: DragEvent, question: Question) => {
  console.log(question)
  event.dataTransfer?.setData('text/plain', JSON.stringify(question));
};

const dragOver = (event: DragEvent) => {
  event.preventDefault();
};

const drop = async (event: DragEvent, targetIndex: number) => {
  event.preventDefault();
  const eventData = event.dataTransfer?.getData('text/plain');
  if (eventData && data.value?.exam) {
    const draggedQuestion = JSON.parse(eventData) as Question;
    const draggedIndex = data.value.questions.findIndex(q => q.id === draggedQuestion.id);
    if (draggedIndex !== undefined && draggedIndex !== -1) {
      const targetQuestion = data.value.questions[targetIndex];

      const tempSequence = draggedQuestion.sequence;
      draggedQuestion.sequence = targetQuestion.sequence;
      targetQuestion.sequence = tempSequence;

      data.value.questions.splice(draggedIndex, 1);
      data.value.questions.splice(targetIndex, 0, draggedQuestion);

      await saveQuestion(draggedQuestion);
      await saveQuestion(targetQuestion);
    }
  }
}

</script>
<template>
  <nav class="no-print">
    <ul class="tabs">
      <li>
        <button class="btn" type="button" @click="handleModeChange('editing')" :disabled="mode === 'editing'">Edytor
        </button>
      </li>
      <li>
        <button class="btn" type="button" @click="handleModeChange('printing')" :disabled="mode === 'printing'">Wydruk
        </button>
      </li>
    </ul>
  </nav>
  <div v-if="data?.exam" v-show="mode === 'editing'" class="exam-view">
    <header class="exam-header">
      <h3>{{ data.exam.name }}
        <Icon :data-target="EXAM_EDITOR_ID" @click="editExam($event)" class="bi bi-pencil-square clickable"
              :color="'black'" :font-size="'1rem'" title="Edytuj nazwę"/>
      </h3>
    </header>
    <main class="exam-body">
      <QuestionComponent v-for="(question, questionIndex) in data.questions?.sort((a, b) => a.sequence - b.sequence)"
                         :key="question.id"
                         :index="questionIndex+1" :content="question.content" draggable="true"
                         @dragstart="dragStart($event, question)" @dragover="dragOver"
                         @drop="drop($event, questionIndex)">
        <template #editBtn>
          <Icon :data-target="QUESTION_EDITOR_ID"
                @click="editQuestion(question, $event)" class="bi bi-pencil-square clickable"
                :color="'black'" title="Edytuj pytanie">
          </Icon>
        </template>
        <template #deleteBtn>
          <Icon :data-target="DELETE_QUESTION_MODAL_ID" @click="toggleDeleteModal($event, question)"
                class="bi bi-trash clickable" :color="'red'" title="Usuń pytanie">Delete
          </Icon>
        </template>
        <template #answers>
          <AnswerComponent v-for="(answer, answerIndex) in question.answers.sort((a,b) => a.id! - b.id!)"
                           :key="answer.id"
                           :index="answerIndex"
                           :is-correct="answer.isCorrect"
                           :content="answer.content">
          </AnswerComponent>
        </template>
      </QuestionComponent>
      <button class="btn" :data-target="QUESTION_EDITOR_ID"
              @click="createNewQuestion($event)"> Dodaj pytanie
      </button>
    </main>
    <ExamEditor :exam-id="data.exam.id" :name="data.exam.name" @saved="data.exam.name=$event.name"/>
    <QuestionEditor
      :propData="selectedQuestion"
      @saved="handleEditorOutput($event)"/>
    <Modal class="modal delete-question-dialog" :id="DELETE_QUESTION_MODAL_ID">
      <template #header><h2>Na pewno?</h2></template>
      <template #footer>
        <button class="btn" :data-target="DELETE_QUESTION_MODAL_ID" @click="toggleModal($event)">Anuluj</button>
        <button class="btn" :data-target="DELETE_QUESTION_MODAL_ID" @click="deleteQ($event)">Usuń</button>
      </template>
    </Modal>
  </div>
  <ExamPrint v-if="mode === 'printing' && data?.exam" :exam="data.exam" :questions="data.questions"/>
</template>

<style scoped>
.tabs {
  display: flex;
  flex-wrap: wrap;
  list-style-type: none;
  margin-top: 0;
  margin-bottom: 1rem;
  box-shadow: 0 0 5px lightgrey;
}

.exam-view {
  padding-left: 1cm;
  padding-right: 1cm;
  padding-top: 0.5cm;
  margin: 1rem auto;
  border: 1px solid;
  width: 210mm;
  min-height: 297mm;
  height: fit-content;
}

@media print {
  .exam-view {
    border: none;
  }
}

.exam-header {
  display: flex;
  flex-direction: column;
  width: 100%;
  margin-bottom: 1em;
}

.exam-body {
  width: 100%;
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  grid-column-gap: 0.5rem;
  grid-row-gap: 1rem;
  max-height: calc(100% - 1cm);
  overflow-y: auto;
}

.delete-question-dialog {
  margin: 0;
  position: absolute;
  z-index: 1000;
}

</style>
