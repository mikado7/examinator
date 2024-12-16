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
      <header class="container" style="padding-top: 1.25rem; padding-bottom: 1.25rem;">
        <div style="padding-left: 1.25rem; padding-right: 1.25rem;">
        <slot name="header"/>
        </div>
      </header>
      <main class="container">
        <div style="padding-left: 1.25rem; padding-right: 1.25rem;">
        <slot name="body"/>
        </div>
      </main>
      <footer class="container modal-footer" style="padding-top: 1.25rem; padding-bottom: 1.25rem;">
        <div style="padding-left: 1.25rem; padding-right: 1.25rem;">
        <slot name="footer"/>
        </div>
      </footer>
    </article>
  </dialog>
</template>

<style>
.modal {
  padding: 0;
  border: 2px solid black;
  border-radius : 5px;
}

.modal-footer {
  display:inline-flex;
  justify-content: flex-end;
  gap: 1.25rem;
}


</style>
