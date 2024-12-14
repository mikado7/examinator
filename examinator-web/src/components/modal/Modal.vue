<script setup lang="ts">

const props = defineProps({
  id: {
    type: String,
    required: true
  }
})
</script>

<template>
  <dialog :id="id">
    <article>
      <header>
        <slot name="header"/>
      </header>
      <main>
        <slot name="body"/>
      </main>
      <footer>
        <slot name="footer"/>
      </footer>
    </article>
  </dialog>
</template>

<style scoped>

</style>
