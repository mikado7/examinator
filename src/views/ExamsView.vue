<script setup lang="ts">

import type {Exam} from "@/db/domain/exam";
import {onMounted, ref} from "vue";
import {loadExams} from "@/db/service/exam_service";
import ExamEditor from "@/components/exam/ExamEditor.vue";
import {toggleModal} from "@/components/modal/modal";
import {EXAM_EDITOR_ID} from "@/const";

const exams = ref<Exam[]>([])

onMounted(async () => {
  await load()
})

const handleEditorOutput = (output: Exam) => {
  exams.value.push(output)
}

const load = async () => {
  exams.value = await loadExams()
}

</script>

<template>
  <div style="padding: 2rem;">
    <h2>Moje egzaminy</h2>
    <ul>
      <li v-for="exam in exams" :key="exam.id">
        {{ exam.name }}
        <RouterLink :to="{ name : 'exam', params : {examId : exam.id}}">
          <button class="btn">Idź do egzaminu</button>
        </RouterLink>
      </li>
    </ul>
    <button class="btn" :data-target="EXAM_EDITOR_ID" @click="toggleModal($event)">Dodaj egzamin</button>
    <ExamEditor @saved="handleEditorOutput($event)"/>
  </div>
</template>

<style scoped>

</style>
