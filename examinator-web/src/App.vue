<script setup lang="ts">
import Navbar from "@/views/Navbar.vue";
import {useId} from "vue";

const vueId = useId()

</script>
<template>
  <Navbar/>
  <RouterView/>
</template>
<style></style>
