<script setup lang="ts">

const props = defineProps(['index', 'content', 'isCorrect']);


const numToAlpha = (num: number) => {
  return String.fromCharCode(97 + num)
}
</script>

<template>
  <div class="answer">
    <input class="no-print" :checked="isCorrect" type="checkbox" readonly>
    <p class="answer-index">{{numToAlpha(index)}})</p>
    <span class="content ql-editor" v-html="content"></span>
  </div>
</template>

<style scoped>
.answer {
  width: 100%;
  display: inline-flex;
  align-items: center;
  justify-content: flex-start;
}

.answer-index {
  line-height: 1.42;
  margin: 0 1mm 0 1rem;
}

.ql-editor {
  line-height: 2;
  padding: 0;
}

</style>
