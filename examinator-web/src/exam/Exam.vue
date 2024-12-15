<script setup lang="ts">

import type {Exam} from "@/exam/exam";
import {onBeforeMount, ref} from "vue";
import {loadExam} from "@/exam/exam_service";
import {EXAM_EDITOR_ID, QUESTION_EDITOR_ID} from "@/const";
import QuestionEditor from "@/QuestionEditor.vue";
import {toggleModal} from "@/components/modal/modal";
import type {Question} from "@/question";
import QuestionComponent from "@/views/QuestionComponent.vue"
import ExamEditor from "@/exam/ExamEditor.vue";
import Answer from "@/views/Answer.vue";
import Icon from "@/components/modal/Icon.vue";
import {deleteQuestion} from "@/question_service";
import ExamPrint from "@/exam/ExamPrint.vue";

const DELETE_QUESTION_MODAL_ID = 'delete-question-dialog'

const props = defineProps({
  examId: {
    type: Number,
    required: true
  }
});

const exam = ref<Exam>()

onBeforeMount(async () => {
  const examId = Number(props.examId)
  if (isNaN(examId)) {
    throw new Error('Invalid exam id')
  }
  exam.value = await loadExam(examId)
})

const selectedQuestion = ref<Question>(<Question>{
  content: '',
  examId: exam.value?.id,
  answers: []
})

const createNewQuestion = (e: Event) => {
  if (exam.value?.id) {
    selectedQuestion.value = {
      content: '',
      examId: exam.value?.id,
      answers: []
    }
  }
  toggleModal(e)
}

const handleEditorOutput = (editorOutput: Question) => {
  if (!exam.value?.questions) throw new Error("Exam does not have any questions!")
  const index = exam.value?.questions.findIndex(q => q.id === editorOutput.id);
  if (index !== -1) {
    exam.value?.questions.splice(index, 1, editorOutput);
  } else {
    exam.value?.questions.push(editorOutput);
  }
}

const editQuestion = (question: Question, e: Event) => {
  selectedQuestion.value = {...question, examId: props.examId}
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
  const index = exam.value?.questions?.indexOf(selectedQuestion.value)
  if (selectedQuestion.value && index !== -1) {
    if (selectedQuestion.value.id) await deleteQuestion(selectedQuestion.value.id)
    const index = exam.value?.questions?.indexOf(selectedQuestion.value)
    exam.value?.questions?.splice(index, 1)
  }
  toggleModal(e)
}

const mode = ref<'printing' | 'editing'>('editing')
const handleModeChange = (s: 'printing' | 'editing') => {
  mode.value = s
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
  <div v-if="exam" v-show="mode === 'editing'" class="exam-view">
    <header class="page-header">
      <h2 class="content"> {{ exam?.name }}</h2>
      <Icon :data-target="EXAM_EDITOR_ID" @click="editExam($event)" class="bi bi-pencil-square clickable"
            :color="'black'" title="Edytuj nazwę"/>
      <p style="display: block; width: 100%">Imię i nazwisko: ___________________________________</p>
    </header>
    <main class="page-body">
      <QuestionComponent v-for="(question, questionIndex) in exam?.questions" :key="question.id"
                         :index="questionIndex+1" :content="question.content">
        <template #editBtn>
          <Icon :data-target="QUESTION_EDITOR_ID"
                @click="editQuestion(question, $event)" style="margin-right: 4px" class="bi bi-pencil-square clickable"
                :color="'black'" title="Edytuj pytanie">
          </Icon>
        </template>
        <template #deleteBtn>
          <Icon :data-target="DELETE_QUESTION_MODAL_ID" @click="toggleDeleteModal($event, question)"
                style="margin-right: 4px" class="bi bi-trash clickable" :color="'red'" title="Usuń pytanie">Delete
          </Icon>
        </template>
        <template #answers>
          <Answer v-for="(answer, answerIndex) in question.answers" :key="answer.id"
                  :index="answerIndex"
                  :is-correct="answer.isCorrect"
                  :content="answer.content">
          </Answer>
        </template>
      </QuestionComponent>
      <button class="btn" :data-target="QUESTION_EDITOR_ID"
              @click="createNewQuestion($event)"> Dodaj pytanie
      </button>
    </main>
    <ExamEditor :exam-id="exam.id" :name="exam.name" @saved="exam.name=$event.name"/>
    <QuestionEditor
      :question="selectedQuestion"
      @saved="handleEditorOutput($event)"/>
    <RouterLink v-if="exam" :to="{ name : 'generate-exam', params : {examId : exam.id}}">
      <button class="btn">Generuj egzamin</button>
    </RouterLink>
    <dialog class="delete-question-dialog" :id="DELETE_QUESTION_MODAL_ID">
      <article>
        <header>Na pewno?</header>
        <footer>
          <button class="btn" :data-target="DELETE_QUESTION_MODAL_ID" @click="toggleModal($event)">Anuluj</button>
          <button class="btn" :data-target="DELETE_QUESTION_MODAL_ID" @click="deleteQ($event)">Tak</button>
        </footer>
      </article>
    </dialog>
  </div>
  <ExamPrint v-if="mode === 'printing' && exam" :exam="exam"/>
</template>

<style scoped>
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

.page-header {
  display: inline-flex;
  flex-wrap: wrap;
  width: 100%;
  //margin-top: 1em;
  margin-bottom: 1em;

}

.content {
  display: inline-block;
}

.page-body {
  width: 100%;
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  grid-column-gap: 5px;
  grid-row-gap: 1rem;
  max-height: calc(100% - 1cm);
  overflow-y: auto;
}

.delete-question-dialog {
  margin: 0;
  position: absolute;
  z-index: 1000;
}


.tabs {
  display: flex;
  flex-wrap: wrap;
  list-style-type: none;
  margin-top: 0;
  margin-bottom: 0;

}

</style>
