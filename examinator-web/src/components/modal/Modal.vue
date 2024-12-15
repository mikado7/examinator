<script setup lang="ts">

import {computed, ref, watch} from "vue";

const props = defineProps({
  id: {
    type: String,
    required: true
  }
})

const emits = defineEmits(['opened'])

</script>

<template>
  <dialog class="modal" :id="id" ref="root">
    <article>
      <header class="container" style="margin-top: 0.5rem; margin-bottom: 1rem;">
        <slot name="header"/>
      </header>
      <main class="container" style="margin-bottom: 1rem;">
        <slot name="body"/>
      </main>
      <footer class="container modal-footer">
        <slot name="footer"/>
      </footer>
    </article>
  </dialog>
</template>

<style scoped>
.modal {
  border: 2px solid black;
  border-radius : 5px;
}

.modal-footer {
  display:inline-flex;
  justify-content: flex-end;
  gap: 1rem;
}


</style>
