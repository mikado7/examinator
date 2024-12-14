<script setup lang="ts">
import Quill from "quill";
import {onMounted, useId, watch} from "vue";

const id = useId()
let quill: Quill

const content = defineModel('content', {
  type: String,
  default: ''
})

const isCorrect = defineModel('isCorrect', {
  type: Boolean,
  default: false
})


onMounted(() => {
  quill = new Quill(`#editor_${id}`, {
    theme: 'snow',
    modules: {
      toolbar: [
        ['formula'],
        [
          {
            size: ['small', false, 'large', 'huge'],
          },
        ],
      ],
    },
    placeholder: 'Wprowadź treść odpowiedzi',
  })
  if (content.value) {
    let delta = quill.clipboard.convert({html: content.value})
    delta.push({insert: ' '})
    quill.setContents(delta, "api")
  }
  quill.on('text-change', (delta, oldDelta, source) => {
    content.value = quill.getSemanticHTML()
  })
})

watch(content, (newContent) => {
  if (quill && newContent !== quill.getSemanticHTML()) {
    let delta = quill.clipboard.convert({html: newContent})
    delta.push({insert: ' '})
    quill.setContents(delta, "api")
  }
})

function isQuillElement(el: HTMLElement): boolean {
  for (let cl of el.classList) {
    if (cl.startsWith('ql')) {
      return true;
    }
  }
  return false;
}

const handleKeydown = (e: KeyboardEvent) => {
  if ((e.key === 'Escape' || e.key === 'Enter') && (e.target as HTMLElement).dataset.formula || isQuillElement(e.target as HTMLElement)) {
    e.stopPropagation()
  }
}
</script>

<template>
  <div :id="`editor_${id}`" @keydown="handleKeydown($event)" class="editor"></div>
  <label :for="`isCorrect_${id}`">Poprawna: </label>
  <input :id="`isCorrect_${id}`" type="checkbox" v-model="isCorrect"/>
  <slot name="deleteBtn"></slot>
</template>

<style scoped>
</style>
