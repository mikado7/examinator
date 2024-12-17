<script setup lang="ts">

import type {Exam} from "@/db/domain/exam";
import {onMounted, type PropType, ref, watch} from "vue";
import ExamPage from "@/components/exam/ExamPage.vue";
import type {Question} from "@/db/domain/question";


const props = defineProps({
  exam: {
    type: Object as PropType<Exam>,
    required: true,
  },
  questions: {
    type: Object as PropType<Array<Question>>,
    required: true
  }
})

const sheets = ref<Question[][][]>([[[]]])
const numberOfSheets = ref<number>(1)
const fontSize = ref<number>(16)
const inputFontSize = ref<number>(fontSize.value)

onMounted(() => {
  numberOfSheets.value = 1
  if (props.questions) {
    sheets.value[0][0] = props.questions.map((question, index) => ({
      ...question,
      sequence: index + 1,
    }))
  }
})

const handlePageOverflow = (sheetIndex: number, pageIndex: number) => {
  const overflownPage = sheets.value[sheetIndex][pageIndex]
  const lastTwoQuestions = overflownPage.splice(-2)
  if (sheets.value[sheetIndex][pageIndex + 1]) {
    const nextPage = sheets.value[sheetIndex][pageIndex + 1]
    nextPage.unshift(...lastTwoQuestions)
  } else {
    sheets.value[sheetIndex].push(lastTwoQuestions)
  }
}

const generateSheets = (reset: boolean) => {
  if (numberOfSheets.value < 0) return
  if (!reset) {
    const originalSheet = sheets.value[0]
    sheets.value = []
    for (let i = 0; i < numberOfSheets.value; i++) {
      const newSheet = originalSheet.map(page =>
        page.map(question => ({
          ...question,
          answers: shuffleArray([...question.answers])
        }))
      );
      sheets.value.push(newSheet);
    }
  } else {
    if (props.questions) {
      sheets.value[0][0] = props.questions.map((question, index) => ({
        ...question,
        sequence: index + 1,
      }))
    }
  }
}

const handleFontSizeChange = (e: Event) => {
  e.preventDefault()
  if (fontSize.value !== inputFontSize.value) {
    fontSize.value = inputFontSize.value
    generateSheets(true)
  }
}

const printSheets = () => {
  window.print()
}

const shuffleArray = (array: any[]) => {
  for (let i = array.length - 1; i > 0; i--) {
    const j = Math.floor(Math.random() * (i + 1)) as number
    [array[i], array[j]] = [array[j], array[i]]
  }
  return array
}

const handleNbrOfSheetsInput = () => {
  if (typeof numberOfSheets.value !== 'number') {
    numberOfSheets.value = 1
  }
}

watch(numberOfSheets, (newVal) => {
  if (newVal > 100) {
    numberOfSheets.value = 100
  } else if (newVal < 1) {
    numberOfSheets.value = 1
  }
})

watch(inputFontSize, (newVal) => {
  if (newVal > 32) inputFontSize.value = 32
})
</script>

<template>
  <div style="margin: auto">
    <div style="padding: 1rem" class="container container-fluid no-print">
      <label for="sheetsNbrInput">Ile arkuszy chcesz wygenerować?</label>
      <input id="sheetsNbrInput" placeholder="1" type="number" min="1" max="200" v-model="numberOfSheets"
             @focusout="handleNbrOfSheetsInput"/>
      <label for="fontSizeInput">Rozmiar czcionki:</label>
      <input id="fontSizeInput" min="8" max="32" value="16" type="number" placeholder="16" v-model="inputFontSize"
             @focusout="handleFontSizeChange($event)"/>
      <button @click="generateSheets(false)" class="btn">Generuj!</button>
      <button @click="printSheets" class="btn">Drukuj!</button>
    </div>
    <div v-for="(sheet, sheetIndex) in sheets" :key="sheetIndex">
      <ExamPage v-for="(page, pageIndex) in sheet" :index="pageIndex+1" :questions="page"
                :style="{fontSize : `${fontSize}px`}"
                :exam-title="pageIndex===0 ? exam.name : undefined"
                @overflown="handlePageOverflow(sheetIndex, pageIndex)">
      </ExamPage>
    </div>
  </div>
</template>

<style scoped>

</style>
