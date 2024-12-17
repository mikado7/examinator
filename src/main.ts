import {createApp} from 'vue'

import App from './App.vue'
import router from './router/router'
import Quill from 'quill'
import FormulaCustom from './quill/formula_custom'
import {addExampleData} from "@/db/db";

Quill.register(FormulaCustom, true)

const app = createApp(App)
app.use(router)

app.mount('#app')

