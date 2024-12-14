import {createApp} from 'vue'
import {createPinia} from 'pinia'

import App from './App.vue'
import router from './router/router'
import Quill from 'quill'
import FormulaCustom from './quill/formula_custom'

Quill.register(FormulaCustom, true)

const app = createApp(App)
app.use(createPinia())
app.use(router)

app.mount('#app')

