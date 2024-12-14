<script setup lang="ts">
import {QUESTION_EDITOR_ID} from "@/const"
import Modal from "@/components/modal/Modal.vue"
import type {Question} from "@/question";
import QuestionContentEditor from "@/views/QuestionContentEditor.vue";
import {onMounted, ref, watch} from "vue";
import {toggleModal} from "@/components/modal/modal";
import AnswerEditor from "@/AnswerEditor.vue";
import {saveQuestion} from "@/question_service";
import type {Answer} from "@/answer";

const props = defineProps({
  examId: {
    type: Number,
    required: true,
  },
  id: {
    type: Number,
    required: false,
    default: undefined
  },
  content: {
    type: String,
    required: false,
    default: ''
  },
  answers: {
    type: Array<Answer>,
    required: false,
    default: []
  }
})

const emits = defineEmits<{
  (event: 'saved', question: Question): void
}>()


const qContent = ref<string>(props.content)
const qId = ref<number | undefined>(props.id)
const qAnswers = ref<Array<Answer>>([...props.answers])

const addAnswer = () => {
  qAnswers.value.push({
    isCorrect: false,
    content: ''
  })
}

onMounted(() => {
  console.log('re -render')
})

const deleteAnswer = (index: number) => {
  qAnswers.value.splice(index, 1);
}

const save = async (event: Event) => {
  console.log(JSON.stringify({
    id: qId.value,
    examId: props.examId,
    content: qContent.value,
    answers: qAnswers.value
  }))
  const q = await saveQuestion({
    id: qId.value,
    examId: props.examId,
    content: qContent.value,
    answers: qAnswers.value
  })
  toggleModal(event)
  emits("saved", q)
}

const cancel = (event: Event) => {
  toggleModal(event)
}

const cancelBtn = ref<HTMLButtonElement>()
const saveBtn = ref<HTMLButtonElement>()
const handleKeyDown = (e: KeyboardEvent) => {
  if (e.key === 'Escape') {
    e.preventDefault()
    cancelBtn.value?.click()
  }
}

watch(props, (newProps) => {
  qContent.value = newProps.content
  qId.value = newProps.id
  qAnswers.value = newProps.answers
})
</script>

<template>
  <Modal :id="QUESTION_EDITOR_ID" @keydown="handleKeyDown($event)">
    <template #header>
      <h1>Spierdalaj bootstrap</h1>
    </template>
    <template #body>
      <QuestionContentEditor
        :content="qContent"
        @update:content="qContent=$event"/>
      <AnswerEditor v-for="(answer, answerIndex) in qAnswers"
                    :content="answer.content"
                    @update:content="answer.content=$event"
                    :isCorrect="answer.isCorrect"
                    @update:isCorrect="answer.isCorrect=$event">
        <template v-slot:deleteBtn>
          <button @click="deleteAnswer(answerIndex)">Delete</button>
        </template>
      </AnswerEditor>
      <button @click="addAnswer">Add Answer</button>
    </template>
    <template #footer>
      <button ref="cancelBtn" :data-target="QUESTION_EDITOR_ID" @click="cancel($event)">Anuluj</button>
      <button ref="saveBtn" :data-target="QUESTION_EDITOR_ID" @click="save($event)">Save</button>
    </template>
  </Modal>
</template>

<style scoped>

</style>
