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
        [
          {
            size: ['small', false, 'large', 'huge'],
          },
        ],
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
  <div class="editor" :id="editorId" @keydown="handleKeydown($event)"/>
</template>
<style>
.editor {
  width: 210mm;

}
</style>
