<script setup lang="ts">

const props = defineProps({
  index: Number,
  content: String,
})



</script>

<template>
  <div>
    <header class="question-header">
      <p style="margin-right:0.5rem" class="question-index">{{ index }})</p>
      <span class="content ql-editor" v-html="content"></span>
      <div>
      <slot name="editBtn"></slot>
      <slot name="deleteBtn"></slot>
      </div>
    </header>
    <main>
      <slot name="answers"></slot>
    </main>

  </div>

</template>

<style scoped>

.question-header {
  width: 100%;
  display: flex;
  margin-bottom: 0.5rem;
}

.question-index {
  line-height: 1.42;
  margin: 0 1mm 0 0;
}

.ql-editor {
  padding: 0;
}
</style>
