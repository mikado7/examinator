import {createRouter, createWebHistory} from 'vue-router'
import QuestionEditor from '@/views/QuestionContentEditor.vue'
import Home from '@/Home.vue'
import Exam from "@/exam/Exam.vue";
import Exams from "@/exam/Exams.vue";
import GeneratedExam from "@/views/GenerateExam.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      component: Home,
    },
    {
      path: '/question-editor',
      component: QuestionEditor,
    },
    {
      path: '/exam/:examId',
      name: 'exam',
      component: Exam,
      props: true,
    },
    {
      path: '/exams',
      component: Exams,
    },
    {
      path: '/generate-exam/:examId',
      name: 'generate-exam',
      component: GeneratedExam,
      props: true
    }
  ],
})

export default router
