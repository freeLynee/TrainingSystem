const user = {
  state: {

    user: localStorage.getItem('user'),

    token: localStorage.getItem('token')

  },
  mutations: {
    // 将token保存到sessionStorage里，token表示登陆状态

    SET_TOKEN: (state, data) => {
      state.token = data

      localStorage.setItem('token', data)
    },

    // 获取用户名

    GET_USER: (state, data) => {
      // 把用户名存起来

      state.user = data

      localStorage.setItem('user', data)
    },

    // 登出

    LOGOUT: (state) => {
      // 登出的时候要清除token

      state.token = null

      state.user = null

      localStorage.removeItem('token')

      localStorage.removeItem('user')
    }

  },

  actions: {}

}

export default user
