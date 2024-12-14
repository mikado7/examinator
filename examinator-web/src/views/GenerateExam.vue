<script setup lang="ts">
import {onBeforeMount, ref} from "vue";
import QuestionComponent from "@/views/QuestionComponent.vue";
import Answer from "@/views/Answer.vue";
import type {Exam} from "@/exam/exam";
import {loadExam} from "@/exam/exam_service";

const props = defineProps<{
  examId: number
}>();

const exam = ref<Exam>()
onBeforeMount(async () => {
  console.log(props.examId)
  const examId = Number(props.examId)
  if (isNaN(examId)) {
    throw new Error('Invalid exam id')
  }
  exam.value = await loadExam(examId)
})

function shuffle<T>(arr: Array<T>): Array<T> {
  const shuffled = [...arr];
  for (let i = shuffled.length - 1; i > 0; i--) {
    const j = Math.floor(Math.random() * (i + 1));
    [shuffled[i], shuffled[j]] = [shuffled[j], shuffled[i]];
  }
  return shuffled;
}

const numToAlpha = (num: number) => {
  return String.fromCharCode(97 + num).toUpperCase()
}

const sheets = ref<Exam[]>([])
const nbrOfSheets = ref<number>();
const generate = () => {
  sheets.value = []
  if (exam.value && nbrOfSheets.value) {
    sheets.value = []
    for (let i = 0; i < nbrOfSheets.value; i++) {
      sheets.value.push(exam.value)
    }
  }
}

const check = () => {
  if (!nbrOfSheets.value) return
  if (nbrOfSheets.value < 0) nbrOfSheets.value=0
  if (nbrOfSheets.value > 100) nbrOfSheets.value=100
}


</script>
<template>
  <div class="no-print">
    <label for="examsNbr">Ile wygenerować</label>
    <input @input="check" v-model="nbrOfSheets" id="examsNbr" type="number" min="1" max="100">
    <button @click="generate" class="btn">Generuj!</button>
  </div>
  <div class="exam questions-on-page" v-for="sheet in sheets">
    <QuestionComponent v-for="(question, questionIndex) in sheet?.questions" :index="questionIndex+1"
                       :content="question.content">
      <template #answers>
        <Answer v-for="(answer, answerIndex) in shuffle(question.answers)" :key="answerIndex"
                :index="numToAlpha(answerIndex)" :content="answer.content">
        </Answer>
      </template>
    </QuestionComponent>
  </div>
</template>

<style scoped>
.exam {
  width: 210mm;

}

.questions-on-page {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  grid-template-rows: repeat(8, 1fr);
  grid-column-gap: 5px;
  grid-row-gap: 5px;
  max-height: calc(297mm - 1cm);
  overflow-y: auto;
}

</style>
