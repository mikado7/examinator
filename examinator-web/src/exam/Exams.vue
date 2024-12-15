<script setup lang="ts">

import type {Exam} from "@/exam/exam";
import {onMounted, ref} from "vue";
import {loadExams} from "@/exam/exam_service";
import ExamEditor from "@/exam/ExamEditor.vue";
import {toggleModal} from "@/components/modal/modal";
import {EXAM_EDITOR_ID} from "@/const";

const exams = ref<Exam[]>([])

const load = async () => {
  exams.value = await loadExams()
}

onMounted(() => {
  load()
})

const handleEditorOutput = (output : Exam) => {
  exams.value.push(output)
}

</script>

<template>
  <div style="padding: 2rem;">
    <h2>Moje egzaminy</h2>
    <ul>
      <li v-for="exam in exams" :key="exam.id">
        {{ exam.name }}
        <RouterLink :to="{ name : 'exam', params : {examId : exam.id}}">
          <button>Go to Exam</button>
        </RouterLink>
      </li>
    </ul>
    <button :data-target="EXAM_EDITOR_ID" @click="toggleModal($event)">Dodaj egzamin</button>
    <ExamEditor @saved="handleEditorOutput($event)"/>

  </div>

</template>

<style scoped>

</style>
