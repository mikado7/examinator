<script setup lang="ts">

import {computed, onMounted, onUpdated, type PropType, ref, watch} from "vue";
import type {Question} from "@/question";
import QuestionComponent from "@/views/QuestionComponent.vue";
import Answer from "@/views/Answer.vue";
import type {SequencedQuestion} from "@/exam/ExamPrint.vue";

const props = defineProps({
  questions: {
    type: Array as PropType<SequencedQuestion[]>,
    required: true,
  },
  examTitle : {
    type : String,
    required : false
  },
  index: {
    type: Number,
    required: true
  }
})

const questionsContainer = ref<HTMLDivElement>()
onUpdated(() => {
  if(questionsContainer.value) {
    if(questionsContainer.value?.scrollHeight > questionsContainer.value?.clientHeight) {
      emits('overflown')
    }
  }
})

const emits = defineEmits(['overflown'])

</script>

<template>
  <div class="a4-page">
    <header class="page-header">
      <h2 v-if="index===1">{{examTitle}}</h2>
    </header>
    <main class="page-body">
      <div class="questions-container" ref="questionsContainer">
        <QuestionComponent v-for="(question, questionIndex) in questions"
                           :index="question.sequence" :content="question.content" :key="questionIndex">
          <template #answers>
            <Answer v-for="(answer, answerIndex) in question.answers" :index="answerIndex+1"
                    :content="answer.content"></Answer>
          </template>
        </QuestionComponent>
      </div>
    </main>
    <footer class="page-footer">
      <p style="margin: auto">{{index}}</p>
    </footer>
  </div>

</template>

<style scoped>
.a4-page {
  height: 297mm;
  width: 210mm;
  border: 1px solid;
}

.page-header {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  flex-wrap: wrap;
  width: 100%;
  margin-bottom: 1em;
}

.page-body {
  width: 100%;
  height: calc(297mm - 2cm);
  overflow-y: auto;
}

.page-footer {
  display: inline-flex;
  flex-wrap: wrap;
  width: 100%;
}

.questions-container {
  width: 100%;
  height: 100%;
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  grid-column-gap: 5px;
  grid-row-gap: 1rem;
}


</style>
