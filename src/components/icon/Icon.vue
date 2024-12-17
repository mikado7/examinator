<script setup lang="ts">

const props = defineProps({
  color : {
   type : String,
   required : false,
   default : 'black'
  },
  fontSize : {
    type : String,
    required : false,
    default : '1rem'
  }
})


</script>
<template>
  <i class="icon no-print" :style="{fontSize : props.fontSize, color : props.color}">
  </i>
</template>
<style scoped>
.icon {
  height : 1rem;
  width : 1rem;
}
</style>
