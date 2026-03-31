import { createApp } from 'vue'
import App from './App.vue'
import axios from 'axios'
import VueAxios from 'vue-axios'
import router from './router/permission.js'
import 'animate.css'
import store from './store/index.js'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
const app = createApp(App)
app.use(VueAxios,axios)
app.use(router)
app.use(store)
app.use(ElementPlus)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}
axios.interceptors.request.use( function(config){
    // 只在config.data是对象时才添加time属性
    if(config.data && typeof config.data === 'object'){
        config.data.time = Date.now()
    }
    return config
},  function(err){
    console.error(err)
}  )
app.mount('#app')
