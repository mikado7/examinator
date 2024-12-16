<script lang="ts" setup>
import Quill from 'quill'
import {onMounted, useId, watch} from 'vue'

const vueId = useId()

let quill: Quill
const editorId = `editor_${vueId}`

const content = defineModel('content', {
  type: String,
  default: '',
})

onMounted(() => {
  quill = new Quill(`#${editorId}`, {
    theme: 'snow',
    modules: {
      toolbar: [
        ['formula'],
        [{ 'size': ['small', false, 'large', 'huge'] }],
      ],
    },
    placeholder: 'Wprowadź treść pytania',
  })
  if (content.value) {
    let delta = quill.clipboard.convert({html: content.value})
    delta.push({insert: ' '})
    quill.setContents(delta, "api")
  }
  quill.on('text-change', () => {
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
const handleKeydown = (e: KeyboardEvent) => {
  if ((e.key === 'Escape' || e.key === 'Enter') && (e.target as HTMLElement).dataset.formula) {
    e.stopPropagation()
  }
}
</script>
<template>
  <div class="question-content-editor">
    <div class="question-quill">
      <div :id="editorId" @keydown="handleKeydown($event)"/>
    </div>
  </div>
</template>
<style scoped>

.question-content-editor {
  display: flex;
  margin-bottom: 1rem;
  margin-top: 1rem;
  padding: 0.5rem;
  border: 1px solid lightgrey;
  border-radius: 2px;
  gap: 0.5rem;
}

.question-quill {
  width: calc((190mm / 2) - 2.5em);
  height: fit-content;
}
</style>
