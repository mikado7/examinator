<script setup lang="ts">


import Modal from "@/components/modal/Modal.vue";
import {EXAM_EDITOR_ID} from "@/const";
import {type PropType, ref} from "vue";
import {toggleModal} from "@/components/modal/modal";
import {type Exam} from "@/db/domain/exam";
import {saveExam} from "@/db/service/exam_service";

const props = defineProps({
  exam: {
    type: Object as PropType<Exam>,
    required: false,
    default: {name: ''}
  }
})

const emits = defineEmits<{
  (event: 'saved', exam: Exam): void
}>()

const initialState = ref<string>('');
const saveBtn = ref<HTMLButtonElement>()

const nameInput = ref<HTMLInputElement>()

const cancel = (e: Event) => {
  toggleModal(e)
}

const save = async (e: Event) => {
  emits('saved', await saveExam(props.exam))
  toggleModal(e)
}
</script>


<template>
  <Modal class="exam-editor" :id="EXAM_EDITOR_ID">
    <template #header>
      <h1>Wprowadź nazwę egzaminu!</h1>
    </template>
    <template #body>
      <div class="container exam-editor-body">
        <input
          class="exam-name-input"
          ref="nameInput"
          v-model="exam.name"
          type="text"
          placeholder="Nazwij swój egzamin"
          aria-label="Name"
          minlength="8"
          maxlength="100"
          aria-describedby="exam-validation-helper"
        >
        <!--        <small :style="{visibility: invalid ? 'visible' : 'hidden'}" id="exam-validation-helper">Nazwa egzaminu musi-->
        <!--          zawierać od 8 do 100 znaków</small>-->
      </div>
    </template>
    <template #footer>
      <button class="btn" ref="cancelBtn" :data-target="EXAM_EDITOR_ID" @click="cancel($event)">Close</button>
      <button class="btn" ref="saveBtn" :data-target="EXAM_EDITOR_ID" @click="save($event)">
        Save
      </button>
    </template>
  </Modal>
</template>

<style scoped>
.exam-editor {
  width: 30rem;
}

.exam-editor-body {
  display: inline-flex;
  flex-wrap: wrap;
  gap: 0.5rem;
}

.exam-name-input {
  width: 100%;
  padding: 0.5rem;
}
</style>
