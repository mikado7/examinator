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
import {deleteQuestion} from "@/question_service";


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
  if (index !== undefined && index !== -1) {
    exam.value?.questions.splice(index, 1, editorOutput);
  } else {
    exam.value?.questions.push(editorOutput);
  }
}

const editQuestion = (question: Question, e: Event) => {
  selectedQuestion.value = {...question}
  selectedQuestion.value.answers = question.answers.map(a => ({...a}))
  toggleModal(e)
}

const editExam = (e: Event) => {
  toggleModal(e)
}

const deleteQ = async (index: number) => {
  if (exam.value?.questions) {
    const q = exam.value?.questions[index]
    if (q.id) await deleteQuestion(q.id)
    exam.value?.questions?.splice(index, 1)
  }
}

const numToAlpha = (num: number) => {
  return String.fromCharCode(97 + num).toUpperCase()
}

</script>
<template>
  <div v-if="exam" class="a4-page">
    <header class="page-header">
      <label class="content"> {{ exam?.name }}</label>
      <button class="btn" :data-target="EXAM_EDITOR_ID" @click="editExam($event)">Edytuj egzamin</button>
    </header>
    <main class="questions-on-page">
      <QuestionComponent v-for="(question, questionIndex) in exam?.questions" :key="question.id" :index="questionIndex+1" :content="question.content">
        <template #answers>
          <Answer v-for="(answer, answerIndex) in question.answers" :key="answer.id"
                  :index="numToAlpha(answerIndex)"
                  :is-correct="answer.isCorrect"
                  :content="answer.content">
          </Answer>
          <button class="btn" :data-target="QUESTION_EDITOR_ID"
                  @click="editQuestion(question, $event)">Edytuj
          </button>
          <button class="btn" @click="deleteQ(questionIndex)">Delete</button>
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
      <button>Generuj egzamin</button></RouterLink>
  </div>
</template>

<style scoped>
.a4-page {
  padding-left: 1cm;
  padding-right: 1cm;
  padding-top: 0.5cm;
  margin: auto;
  border: 1px solid;
  width: 210mm;
  height: fit-content;
}

.page-header {
  width: 100%;
}

.questions-on-page {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  grid-template-rows: repeat(8, 1fr);
  grid-column-gap: 5px;
  grid-row-gap: 5px;
  max-height: calc(100% - 1cm);
  overflow-y: auto;
}
</style>
