<script setup lang="ts">


import Modal from "@/components/modal/Modal.vue";
import {EXAM_EDITOR_ID} from "@/const";
import {computed, onMounted, ref, watch} from "vue";
import {toggleModal} from "@/components/modal/modal";
import type {Exam} from "@/components/exam/exam";
import {saveExam} from "@/components/exam/exam_service";

const props = defineProps({
  examId: {
    type: Number,
    required: false,
  },
  name: {
    type: String,
    required: false,
    default : ''
  }
})

const emits = defineEmits<{
  (event: 'saved', exam: Exam): void
}>()

const initialState = ref<string>();
const saveBtn = ref<HTMLButtonElement>()
onMounted(() => {
  initialState.value = props.name
})

const examId = ref<number | undefined>(props.examId ? props.examId : undefined)
const name = ref<string>(props.name ? props.name : '')
const nameInput = ref<HTMLInputElement>()
const invalid = computed(() => {return !(name.value.length >= 8 && name.value.length <= 100)})
const diff = computed(() => { return name.value === props.name})

const cancel = (e: Event) => {
  toggleModal(e)
}

const save = async (e: Event) => {
  emits('saved', await saveExam({id: examId.value ? examId.value : undefined, name: name.value}))
  toggleModal(e)
}

watch(props, async (newProps) => {
  name.value = newProps.name ? newProps.name : ''
  examId.value = newProps.examId ? newProps.examId : undefined
})
</script>


<template>
  <Modal class="exam-editor"  :id="EXAM_EDITOR_ID">
    <template #header>
      <h1>Wprowadź nazwę egzaminu!</h1>
    </template>
    <template #body>
      <div class="container exam-editor-body">
      <input
        class="exam-name-input"
        ref="nameInput"
        v-model="name"
        type="text"
        placeholder="Nazwij swój egzamin"
        aria-label="Name"
        minlength="8"
        maxlength="100"
        aria-describedby="exam-validation-helper"
      >
      <small :style="{visibility: invalid ? 'visible' : 'hidden'}" id="exam-validation-helper">Nazwa egzaminu musi zawierać od 8 do 100 znaków</small>
        </div>
    </template>
    <template #footer>
      <button class="btn" ref="cancelBtn" :data-target="EXAM_EDITOR_ID" @click="cancel($event)">Close</button>
      <button class="btn" :disabled="invalid || diff" ref="saveBtn" :data-target="EXAM_EDITOR_ID" @click="save($event)">Save
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
