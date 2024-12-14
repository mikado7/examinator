<script setup lang="ts">
import {QUESTION_EDITOR_ID} from "@/const"
import Modal from "@/components/modal/Modal.vue"
import type {Question} from "@/question";
import QuestionContentEditor from "@/views/QuestionContentEditor.vue";
import {onMounted, type PropType, ref, watch} from "vue";
import {toggleModal} from "@/components/modal/modal";
import AnswerEditor from "@/AnswerEditor.vue";
import {saveQuestion} from "@/question_service";
import type {Answer} from "@/answer";

const props = defineProps({
  question: {
    type: Object as PropType<Question>,
    required: true
  }
})

const emits = defineEmits<{
  (event: 'saved', question: Question): void
}>()


const questionRef = ref<Question>(props.question)
const addAnswer = () => {
  questionRef.value.answers.push({
    isCorrect: false,
    content: ''
  })
}

onMounted(() => {
  console.log('re -render')
})

const deleteAnswer = (index: number) => {
  questionRef.value.answers.splice(index, 1);
}

const save = async (event: Event) => {
  const q = await saveQuestion(questionRef.value)
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
  questionRef.value = newProps.question
})
</script>

<template>
  <Modal :id="QUESTION_EDITOR_ID" @keydown="handleKeyDown($event)">
    <template #header>
      <h1>Edytujesz pytanie</h1>
    </template>
    <template #body>
      <QuestionContentEditor
        :content="questionRef.content"
        @update:content="questionRef.content=$event"/>
      <AnswerEditor v-for="(answer, answerIndex) in questionRef.answers"
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
