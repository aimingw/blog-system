import { createApp } from 'vue'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import { marked } from 'marked'
import hljs from 'highlight.js'
import 'highlight.js/styles/github.css'
import App from './App.vue'
import router from './router'
import './assets/main.css'

// 配置 marked (Markdown 渲染器) 集成 highlight.js 代码高亮
marked.setOptions({
  highlight: function(code, lang) {
    if (lang && hljs.getLanguage(lang)) {
      return hljs.highlight(code, { language: lang }).value
    }
    return hljs.highlightAuto(code).value
  },
  breaks: true // 支持换行符转换为 <br>
})

const app = createApp(App)
// 注册 Pinia 状态管理
app.use(createPinia())
// 注册 Vue Router
app.use(router)
// 注册 Element Plus 组件库（中文语言包）
app.use(ElementPlus, { locale: zhCn })
// 全局注册所有 Element Plus 图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}
// 全局注入 marked 渲染函数，供所有组件使用
app.provide('marked', marked)
app.mount('#app')
