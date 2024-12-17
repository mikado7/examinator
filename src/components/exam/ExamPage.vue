  <script setup lang="ts">

  import {onUpdated, type PropType, ref} from "vue";
  import QuestionComponent from "@/components/question/QuestionComponent.vue";
  import Answer from "@/components/answer/AnswerComponent.vue";
  import type {Question} from "@/db/domain/question";

  const props = defineProps({
    questions: {
      type: Array as PropType<Question[]>,
      required: true,
    },
    examTitle: {
      type: String,
      required: false
    },
    index: {
      type: Number,
      required: true
    }
  })

  const foo = ref<HTMLDivElement>()
  onUpdated(() => {
    console.log(foo.value?.scrollHeight)
    console.log(foo.value?.clientHeight)
    if (foo.value) {
      if (foo.value?.scrollHeight > foo.value?.clientHeight) {
        emits('overflown')
      }
    }
  })

  const emits = defineEmits(['overflown'])

  </script>

  <template>
    <div class="a4-page">
      <header class="page-header">
        <div style="padding-left: 1cm; padding-top: 1cm; padding-right: 1cm">
          <h3 v-if="index===1">{{ examTitle }}</h3>
          <p v-if="index===1" style="">Imię i nazwisko: ................................................</p>
          <p v-if="index===1" style="">Numer Albumu: ...................</p>
        </div>
      </header>
      <main class="page-body" ref="foo">
        <div class="questions-container">
          <QuestionComponent v-for="(question, questionIndex) in questions"
                             :index="question.sequence" :content="question.content" :key="questionIndex"
                             >
            <template #answers>
              <Answer v-for="(answer, answerIndex) in question.answers" :index="answerIndex"
                      :content="answer.content"></Answer>
            </template>
          </QuestionComponent>
        </div>
      </main>
      <footer class="page-footer">
        <p style="margin: auto">{{ index }}</p>
      </footer>
    </div>

  </template>

  <style scoped>
  .a4-page {
    display: flex;
    flex-direction: column;
    height: 297mm;
    width: 210mm;
    box-shadow: 0 0 5px lightgrey;
  }

  @media print {
    .a4-page {
      box-shadow: none;
      border: none;
    }

    @-moz-document url-prefix() {
    }

    @media print and (-webkit-min-device-pixel-ratio: 0) {
    }
  }

  @page {
    size: A4 portrait;

  }

  .page-header {
    display: flex;
    flex-direction: column;
    width: 100%;
    margin-bottom: 1em;
    min-height: 1cm;
  }

  .page-body {
    width: 100%;
    flex: 1;
    overflow-y: auto;
  }

  .page-footer {
    display: flex;
    height: 2cm;
    width: 100%;
    text-align: center;
  }

  .questions-container {
    width: 100%;
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    grid-column-gap: 1rem;
    grid-row-gap: 1rem;
  }

  .questions-container > *:nth-child(2n+1) {
    padding-left: 1cm;
  }

  .questions-container > *:nth-child(2n) {
    padding-right: 1cm;
  }


  </style>
