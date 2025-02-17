import { createApp } from 'vue'
import { createPinia } from 'pinia';
import App from './App.vue'
import router from './router'
// 新增代码：引入全部组件及样式
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import "@fortawesome/fontawesome-free/css/all.css";
import "@fortawesome/fontawesome-free/js/all.js";

const app = createApp(App);
const pinia = createPinia();
app.use(pinia);
app.use(router)
app.use(ElementPlus)
app.mount('#app')
