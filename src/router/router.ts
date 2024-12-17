import {createRouter, createWebHashHistory} from 'vue-router'
import ExamView from "@/views/ExamView.vue";
import ExamsView from "@/views/ExamsView.vue";
import NotFound from "@/views/NotFound.vue";

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
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
    },
    {
      path: '/:pathMatch(.*)*',
      name: 'notFound',
      component: NotFound,
    },
  ],
})

export default router
