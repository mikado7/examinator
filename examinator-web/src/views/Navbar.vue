<script setup lang="ts">

import {useRouter} from "vue-router";

const router = useRouter()

</script>

<template>
  <nav class="no-print">
    <button class="btn">
    <a style="margin-right:20px;margin-left:20px;" @click="router.back()"><-</a>
    </button>
    <button class="btn">
    <a style="margin-right:20px;margin-left:20px;" @click="router.forward()">-></a>
    </button>
    <a>
      <RouterLink to="/">Dom</RouterLink>
    </a>
  </nav>

</template>

<style scoped>


</style>
