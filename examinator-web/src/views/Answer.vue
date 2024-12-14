<script setup lang="ts">

const props = defineProps(['index', 'content', 'isCorrect']);


</script>

<template>
  <div class="container">
    <span style="margin-right:5px;"><input :checked="isCorrect" type="checkbox" disabled="true">{{index}})</span>
    <span class="content ql-editor" v-html="content"></span>
  </div>
</template>

<style scoped>
.container {
  display : flex;
  text-align : center;
  align-items : center;
  justify-content : flex-start;
}

.ql-editor {
  padding: 0;
}

</style>
