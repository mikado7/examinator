import {createRouter, createWebHistory} from 'vue-router'
import ExamView from "@/views/ExamView.vue";
import ExamsView from "@/views/ExamsView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      component: ExamsView,
    },
    {
      path: '/exam/:examId',
      name: 'exam',
      component: ExamView,
      props: true,
    }
  ],
})

export default router
