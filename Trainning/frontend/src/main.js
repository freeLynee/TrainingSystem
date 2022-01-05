// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import {router} from './router'
import store from './store'
import ElementUI from 'element-ui'

Vue.use(ElementUI)

// axios 配置
var axios = require('axios')
// Axios挂载到prototype，全局可以使用this.$axios访问
Vue.prototype.$axios = axios
axios.defaults.baseURL = '/api'
// axios.defaults.withCredentials = true
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8'

Vue.config.productionTip = false

axios.interceptors.response.use(
  response => {
    return response
  }
)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: {App},
  template: '<App/>'
})

router.afterEach((tp, from, next) => {
  window.scrollTo(0, 0)
})
