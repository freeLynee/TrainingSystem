<template>
  <div id="app">
    <transition :name="transition">
      <router-view></router-view>
    </transition>
  </div>
</template>

<script>
import Home from "./components/Home";

export default {
  name: 'App',

  data() {
    return {
      remoteUrl: "http://localhost:19099",
      transition: '',
    }
  },

  created() {

  },

  methods: {
    // checkCopyExpire() {
    //   this.$axios.post(this.remoteUrl + '/bookCopy/handleBookCopyExpire')
    //     .then(resp => {
    //       console.log(resp.data)
    //     })
    //     .catch(error => {
    //       console.log(error)
    //       this.$message.error('发生错误!')
    //     })
    // },

    checkToken() {

    }
  },
}
  // watch: {
    // '$route'(to, from) {
    //   //实现路由跳转动画
    //   console.log(to.path)
    //   console.log(from.path)
    //   const routeDeep = ['/Register', '/Login', '/whichLibrary', '/', '/AdministratorHome', 'SuperAdministratorUser', '/AdminAppoint', '/AdminLend', '/AdminReturn', '/AddBook', '/userHistory', '/Users', '/Detail', '/Copies', '/ChangePass', '/AdministratorDeleteComments'];
    //   const toDepth = routeDeep.indexOf(to.path)
    //   const fromDepth = routeDeep.indexOf(from.path)
    //   if (toDepth === -1 || fromDepth === -1) {
    //     this.transition = ''
    //   } else {
    //     this.transition = toDepth > fromDepth ? 'slide-left' : 'slide-right'
    //   }
    // },
  // },

</script>

<style>
body {
  margin: 0;
  padding: 0;
}

#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
</style>
<style lang="scss">
.slide-right-enter-active,
.slide-right-leave-active,
.slide-left-enter-active,
.slide-left-leave-active {
  will-change: transform;
  transition: all 500ms;
  position: absolute;
}

.slide-right-enter {
  opacity: 0;
  transform: translateX(-100%);
}

.slide-right-leave-active {
  opacity: 0;
  transform: translateX(100%);
}

.slide-left-enter {
  opacity: 0;
  transform: translateX(100%);
}

.slide-left-leave-active {
  opacity: 0;
  transform: translateX(-100%);
}
</style>
