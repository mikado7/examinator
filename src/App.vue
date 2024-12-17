<script setup lang="ts">
import Navbar from "@/components/navbar/Navbar.vue";
import {useId} from "vue";

const vueId = useId()

</script>
<template>
  <header id="app-header">
    <Navbar/>
  </header>
  <main id="app-main" class="container scrollable-x">
    <RouterView/>
  </main>
</template>
<style scoped>
main {
  width: 100%;
  display: grid;
  grid-template-rows: auto 1fr;
  margin-top: 1rem;
  margin-left: auto;
  margin-right: auto;
  box-shadow: 0 0 5px lightgrey;
}

@media print {
  #app-main {
    all: unset;
  }
  #app-header {
    all: unset;
  }
  footer {
    all: unset;
  }

}
</style>
