<script setup lang="ts">

const props = defineProps(['index', 'content', 'isCorrect']);


const numToAlpha = (num: number) => {
  return String.fromCharCode(97 + num)
}
</script>

<template>
  <div class="answer">
    <input v-if="typeof isCorrect != 'undefined'" class="no-print" :checked="isCorrect" type="checkbox" readonly>
    <p class="answer-index">{{numToAlpha(index)}})</p>
    <span class="content ql-editor" v-html="content"></span>
  </div>
</template>

<style scoped>
.answer {
  width: 100%;
  display: flex;
  margin-bottom: 0.25rem;
}

.answer-index {
  line-height: normal;
  margin: 0 0.5rem 0 0;
}

.ql-editor {
  line-height: normal;
  padding: 0;
}

</style>
