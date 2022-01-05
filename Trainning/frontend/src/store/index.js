import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    name: localStorage.getItem('name') || null,
    // email: localStorage.getItem('email') || null,
    address: localStorage.getItem('address') || null,
    id: localStorage.getItem('id') || null,
    age: localStorage.getItem('age') || null,
    gender: localStorage.getItem('gender') || null,
    department: localStorage.getItem('department') || null,
    phone: localStorage.getItem('phone') || null,
    joiningTime: localStorage.getItem('joiningTime') || null,
    position: localStorage.getItem('position') || null,
  },

  mutations: {
    login(state, data) {
      //localStorage.setItem('token', data.headers.authorization)
      localStorage.setItem('name', data.name)
      localStorage.setItem('email', data.email)
      localStorage.setItem('address', data.address)
      localStorage.setItem('id', data.id)
      localStorage.setItem('age', data.age)
      localStorage.setItem('gender', data.gender)
      localStorage.setItem('department', data.department)
      localStorage.setItem('phone', data.phone)
      localStorage.setItem('joiningTime', data.joiningTime)
      localStorage.setItem('position', data.position)
      state.email = data.email
      state.name = data.name
      state.id = data.id
      state.address = data.address
      state.department = data.department
      state.phone = data.phone
      state.joiningTime=data.joiningTime
      state.position=data.position

    },
    // setAddress(state, address) {
    //   localStorage.setItem('address', address)
    //   state.address = address
    // },
    logout(state) {
      // 移除token
      //localStorage.removeItem('token')
      localStorage.removeItem('name')
      localStorage.removeItem('email')
      //localStorage.removeItem('role')
      localStorage.removeItem('id')
      localStorage.removeItem('address')
      localStorage.removeItem('age')
      localStorage.removeItem('gender')
      localStorage.removeItem('department')
      localStorage.removeItem('phone')
      localStorage.removeItem('joiningTime')
      localStorage.removeItem('position')
      //state.token = null
      state.name = null
      state.email = null
      //state.role = null
      state.id = null
      state.address = null
      state.age=null
      state.gender=null
      state.department=null
      state.phone=null
      state.joiningTime=null
      state.position=null
    }
  },
  actions: {}
})
