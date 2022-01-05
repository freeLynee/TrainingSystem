import Vue from 'vue'

import Login from '@/components/Employee/login'
import MyInfo from '@/components/Employee/MyInfo'
import TeacherInfo from '@/components/TeachEmployee/TeacherInfo'
import Setting from '@/components/DeptManager/Setting'
import ManagerInfo from '@/components/DeptManager/ManagerInfo'
import Home from '@/components/Home'

import Router from 'vue-router'

Vue.use(Router)

export const router = new Router({
  mode: 'history',
  base: '/library/',
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/ManagerInfo',
      name: 'ManagerInfo',
      component: ManagerInfo
    },
    {
      path: '/TeacherInfo',
      name: 'TeacherInfo',
      component: TeacherInfo
    },
    {
      path: '/MyInfo',
      name: 'MyInfo',
      component: MyInfo
    },
    {
      path: '/Home',
      name: 'Home',
      component: Home
    }
  ]
})
