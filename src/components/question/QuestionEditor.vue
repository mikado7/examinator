<script setup lang="ts">
import {QUESTION_EDITOR_ID} from "@/const"
import Modal from "@/components/modal/Modal.vue"
import type {Question, QuestionAnswers} from "@/db/domain/question";
import QuestionContentEditor from "@/components/question/QuestionContentEditor.vue";
import {type PropType, ref, watch} from "vue";
import {toggleModal} from "@/components/modal/modal";
import AnswerEditor from "@/components/answer/AnswerEditor.vue";
import {saveQuestion} from "@/db/service/question_service";
import Icon from "@/components/icon/Icon.vue";
import type {Answer} from "@/db/domain/answer";
import {deleteAnswer, saveAnswer} from "@/db/service/answer_service";

const DELETE_ANSWER_MODAL_ID = 'delete-answer-modal-id';

const props = defineProps({
  propData: {
    type: Object as PropType<Question>,
    required: true
  }
})

const emits = defineEmits<{
  (event: 'saved', data: Question): void
}>()

const addAnswer = () => {
  props.propData.answers.push({
    questionId: props.propData.id,
    isCorrect: false,
    content: ''
  })
}
const selectedAnswer = ref<number>(-1)
const handleAnswerDelete = (e: MouseEvent, index: number) => {
  const xPos = e.clientX;
  const yPos = e.clientY;
  const d = document.querySelector(`#${DELETE_ANSWER_MODAL_ID}`) as HTMLDialogElement
  if (!d) return
  d.style.left = `calc(${xPos}px - 4rem)`
  d.style.top = `calc(${yPos}px - 4rem)`
  selectedAnswer.value = index
  toggleModal(e)
}

const answersToDelete = ref<Answer[]>([])
const deleteA = (e: Event, index: number) => {
  const deletedAnswer = props.propData.answers.splice(selectedAnswer.value, 1)[0];
  if (typeof deletedAnswer.id !== 'undefined') answersToDelete.value.push(deletedAnswer);
  toggleModal(e)
};

const save = async (event: Event) => {
  await Promise.all(answersToDelete.value.map(a => deleteAnswer(<number>a.id)));
  const question = await saveQuestion(props.propData)
  emits("saved", question)
  toggleModal(event)

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
</script>

<template>
  <Modal style="width: 48rem;" :id="QUESTION_EDITOR_ID" @keydown="handleKeyDown($event)">
    <template #header>
      <h2>Edytujesz pytanie</h2>
    </template>
    <template #body>
      <QuestionContentEditor
        :content="propData.content"
        @update:content="propData.content=$event"/>
      <AnswerEditor v-for="(answer, answerIndex) in propData.answers"
                    :key="answerIndex"
                    :index="answerIndex"
                    :content="answer.content"
                    @update:content="answer.content=$event"
                    :isCorrect="answer.isCorrect"
                    @update:isCorrect="answer.isCorrect=$event">
        <template v-slot:deleteBtn>
          <Icon :data-target="DELETE_ANSWER_MODAL_ID" class="bi bi-trash clickable" :color="'red'"
                title="Usuń odpowiedź" @click="handleAnswerDelete($event, answerIndex)"/>
        </template>
      </AnswerEditor>
      <button class="btn" @click="addAnswer">Dodaj Odpowiedź</button>
    </template>
    <template #footer>
      <button class="btn" ref="cancelBtn" :data-target="QUESTION_EDITOR_ID" @click="cancel($event)">Anuluj</button>
      <button class="btn" ref="saveBtn" :data-target="QUESTION_EDITOR_ID" @click="save($event)">Zapisz</button>
      <dialog class="delete-answer-dialog" :id="DELETE_ANSWER_MODAL_ID">
        <article>
          <header>Na pewno?</header>
          <footer>
            <button :data-target="DELETE_ANSWER_MODAL_ID" @click="toggleModal($event)">Anuluj</button>
            <button :data-target="DELETE_ANSWER_MODAL_ID" @click="deleteA($event, selectedAnswer)">Tak</button>
          </footer>
        </article>
      </dialog>
    </template>
  </Modal>
</template>

<style scoped>

.delete-answer-dialog {
  margin: 0;
  position: absolute;
  z-index: 1000;
}

</style>
