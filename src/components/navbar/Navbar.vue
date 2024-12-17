<script setup lang="ts">

import {useRouter} from "vue-router";

const router = useRouter()

</script>

<template>
  <nav class="navbar no-print">
    <h1>
      <RouterLink to="/">Examinator</RouterLink>
    </h1>
  </nav>

</template>

<style scoped>
.navbar {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  padding: 1rem;
  justify-content: space-between;
  box-shadow: 0 2px 4px -2px lightgrey;
}

.navbar a {
  text-decoration: none;
}

.navbar a:visited {
  color: #111827;
}
 .navbar a:link {
   color: #111827;
 }

 .navbar strong {
   font-size : 2rem;
 }



</style>
