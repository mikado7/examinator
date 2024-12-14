<script setup lang="ts">

const props = defineProps({
  index : Number,
  content: String,
})


</script>

<template>
  <div>
    <span style="margin-right: 5px;">{{index}})</span>
  <span class="ql-editor" v-html="content"></span>
    <slot name="answers"></slot>
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
