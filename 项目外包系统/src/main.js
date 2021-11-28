// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import ElementUI from 'element-ui'
import MyHttpServer from '@/plugs/http.js'
import 'element-ui/lib/theme-chalk/index.css'
import '@/assets/css/reset.css'
import router from './router'
import moment from 'moment'


Vue.config.productionTip = false

//全局过滤器
Vue.filter('fmtdate', (V) => {
  return moment().format('YYYY-MM-DD')
})

Vue.use(ElementUI)
Vue.use(MyHttpServer)
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: {App},
  template: '<App/>'
})
