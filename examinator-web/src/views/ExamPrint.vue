<script setup lang="ts">

import type {Exam} from "@/components/exam/exam";
import {onMounted, type PropType, ref, watch} from "vue";
import ExamPage from "@/components/exam/ExamPage.vue";
import type {Question} from "@/components/question/question";


const props = defineProps({
  exam: {
    type: Object as PropType<Exam>,
    required: true,
  }
})

const pages = ref<Question[][]>([[]])
const numberOfSheets = ref<number>(1)

onMounted(() => {
  numberOfSheets.value = 1
  if (props.exam.questions) {
    pages.value[0] = props.exam.questions.map((question, index) => ({
      ...question,
      sequence: index + 1,
    }))

  }
})

const handlePageOverflow = (pageIndex: number) => {
  console.log('overflown')
  const overflownPage = pages.value[pageIndex]
  const lastTwoQuestions = overflownPage.splice(-2)
  if (pages.value[pageIndex + 1]) {
    const nextPage = pages.value[pageIndex + 1]
    nextPage.unshift(...lastTwoQuestions)
  } else {
    pages.value.push(lastTwoQuestions)
  }
}

watch(numberOfSheets, (newVal) => {
  if (newVal > 100) {
    numberOfSheets.value = 100
  } else if (newVal < 1) {
    numberOfSheets.value = 1
  }
})
</script>

<template>
  <div>
    <div>
      <label for="sheetsNbrInput">Ile arkuszy wygenerować?</label>
      <input id="sheetsNbrInput" placeholder="1" type="number" min="1" max="100" v-model="numberOfSheets"/>
      <button class="btn">Generuj!</button>
    </div>
    <div>
      <ExamPage v-for="(page, pageIndex) in pages" :index="pageIndex+1" :questions="page"
                :exam-title="pageIndex===0 ? exam.name : undefined"
                @overflown="handlePageOverflow(pageIndex)">
      </ExamPage>
    </div>
  </div>
</template>

<style scoped>

</style>
