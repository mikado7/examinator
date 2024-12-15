<script setup lang="ts">

import type {Exam} from "@/exam/exam";
import {onMounted, type PropType, ref} from "vue";
import ExamPage from "@/exam/ExamPage.vue";
import type {Question} from "@/question";


const props = defineProps({
  exam: {
    type: Object as PropType<Exam>,
    required: true,
  }
})

export interface SequencedQuestion extends Question {
  sequence: number;
}

const pages = ref<SequencedQuestion[][]>([[]])

onMounted(() => {
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
</script>

<template>
  <div>
    <ExamPage v-for="(page, pageIndex) in pages" :index="pageIndex+1" :questions="page" :exam-title="pageIndex===0 ? exam.name : undefined"
              @overflown="handlePageOverflow(pageIndex)">
    </ExamPage>
  </div>
</template>

<style scoped>

</style>
