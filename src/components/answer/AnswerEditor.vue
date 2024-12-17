<script setup lang="ts">
import Quill from "quill";
import {onMounted, useId, watch} from "vue";

const id = useId()
let quill: Quill

const props = defineProps({
  index : {
    type : Number,
    required : true
  }
})

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
      ],
    },
    placeholder: 'Wprowadź treść odpowiedzi',
  })
  if (content.value) {
    const delta = quill.clipboard.convert({html: content.value})
    delta.push({insert: ' '})
    quill.setContents(delta, "api")
  }
  quill.on('text-change', () => {
    content.value = quill.getSemanticHTML()
  })
})

watch(content, (newContent) => {
  if (quill && newContent !== quill.getSemanticHTML()) {
    const delta = quill.clipboard.convert({html: newContent})
    delta.push({insert: ' '})
    quill.setContents(delta, "api")
  }
})

function isQuillElement(el: HTMLElement): boolean {
  for (const cl of el.classList) {
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
  <div class="answer-editor">
    <p style="flex: 0 0 auto; margin-top: auto; margin-bottom: auto">{{index+1}})</p>
    <div style="margin-top: auto; margin-bottom: auto">
      <label :for="`isCorrect_${id}`">Poprawna?</label>
      <input :id="`isCorrect_${id}`" type="checkbox" v-model="isCorrect"/>
    </div>
    <div class="answer-quill">
      <div :id="`editor_${id}`" @keydown="handleKeydown($event)"/>
    </div>
    <div>
      <slot name="deleteBtn"></slot>
    </div>
  </div>
</template>

<style scoped>
.answer-editor {
  display: flex;
  margin-bottom: 1rem;
  margin-top: 1rem;
  padding: 0.5rem;
  border: 1px solid lightgrey;
  border-radius: 2px;
  gap: 0.5rem;
}

.answer-quill {
  width: calc((190mm/2) - 3em);
  height: fit-content;
}
</style>
