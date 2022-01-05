<template>
  <el-container class="base_login">
    <el-main>
      <el-form :model="loginForm"
               :rules="rules"
               ref="loginForm"
               class="login_container"
               label-position="left"
               label-width="0px"
               v-loading="loading">
        <h3 class="login_title">登录</h3>
        <el-form-item prop="username">
          <el-input type="text"
                    v-model="loginForm.username"
                    auto-complete="off"
                    class="text-input"
                    clearable="true"
                    placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input type="password"
                    v-model="loginForm.password"
                    auto-complete="off"
                    class="text-input"
                    clearable="true"
                    placeholder="密码"></el-input>
        </el-form-item>
        <el-form-item prop="position">
          <el-input type="text"
                    v-model="position"
                    auto-complete="off"
                    class="text-input"
                    clearable="true"
                    placeholder="员工0，教员1，主管2，系统管理员3"></el-input>
        </el-form-item>

        <el-form-item class="text-input">
          <el-button type="primary"
                     class="button_login"
                     v-on:click="login('loginForm')">登录
          </el-button>
        </el-form-item>
      </el-form>
    </el-main>
  </el-container>
</template>

<script>
import Home from "../Home";

export default {
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      sname:this.$store.state.name,
      rules: {
        username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
        password: [{required: true, message: '请输入密码', trigger: 'blur'}]
      },
      position:'',
      loading: false,
      url:'',
      remoteUrl: Home.remoteUrl,
    }
  },

  created() {
    this.checkToken()
  },

  methods: {
    // checkToken(){
    //   if (this.$store.state.token !== null) {
    //     let role = this.$store.state.role
    //     if(role === 'teacher' || role === 'postgraduate' || role === 'undergraduate'){
    //       this.$message({
    //         message: '登陆成功!',
    //         type: 'success',
    //         duration: 1500
    //       })
    //       this.$router.push({path: '/'})
    //     } else if(role === 'admin'){
    //       this.$router.push({path: '/AdministratorHome'})
    //     } else if(role === 'sAdmin'){
    //       this.$router.push({path: '/SuperAdministratorUser'})
    //     } else {
    //       this.$store.commit('logout')
    //       this.$router.push({path: '/Login'})
    //       this.$message.error('账号异常!请重新登陆')
    //     }
    //   }
    // },
    //
    login(form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          let postData = new URLSearchParams();
          postData.append('username', this.loginForm.username)
          postData.append('password', this.loginForm.password)

          this.$axios.post('http://localhost:19098/training/login', postData)
            .then(resp => {
              console.log('resp.data:')
              console.log(resp.data)
              console.log("0号是："+resp.data[0])

              if (resp.data[0].code === '2000') {
                //
                console.log('resp.data.data:')
                console.log(resp.data[0])
                // if (resp.data.data.role === 'teacher' || resp.data.data.role === 'postgraduate' || resp.data.data.role === 'undergraduate') {
                  // this.$router.push({path: '/'})
                  this.$message({
                    message: '登陆成功!',
                    type: 'success',
                    duration: 1500
                  })
                this.$store.commit('login',resp.data[0])

                console.log("stroe的名字是："+this.sname)

                this.$message({
                  message: this.sname,
                  type: 'success',
                  duration: 4500
                })
                if(resp.data[0].position ==='staff'){
                  this.$router.push({path: '/MyInfo'})
                }else if(resp.data[0].position ==='teacher'){
                  this.$router.push({path: '/TeacherInfo'})
                }else if(resp.data[0].position ==='manager')
                  this.$router.push({path: '/ManagerInfo'})
                // }
              }
              //     this.$store.commit('login',resp)
              //     console.log(resp.data.data.role)
              //   } else if(resp.data.data.role === 'admin'){
              //     this.$router.push({path: '/whichLibrary'})
              //     this.$message({
              //       message: '管理员登陆成功!',
              //       type: 'success',
              //       duration: 1500
              //     })
              //     this.$store.commit('login',resp)
              //     console.log(resp.data.data.role)
              //   } else if(resp.data.data.role === 'sAdmin'){
              //     this.$router.push({path: '/SuperAdministratorUser'})
              //     this.$message({
              //       message: '超级管理员登陆成功!',
              //       type: 'success',
              //       duration: 1500
              //     })
              //     this.$store.commit('login',resp)
              //     console.log(resp.data.data.role)
              //   } else {
              //     this.$message.error('账号异常!请重新登陆')
              //   }
              // } else {
              //   let message = resp.data.data
              //   this.$message({
              //     message: '登陆失败! ' + message,
              //     type: 'warning',
              //     duration: 750
              //   })
              // }
            })
            .catch(error => {
              console.log('error:')
              console.log(error)
              this.$message.error('login发生错误')
            })

        } else {
          return false
        }
      });

    }
  }
}

</script>

<style scoped>
.base_login {
  background: url("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fp7.itc.cn%2Fimages01%2F20200515%2Fc981b722f62348de924b72dfec6a3864.jpeg&refer=http%3A%2F%2Fp7.itc.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1643272178&t=464549764e4ffbe63852fb41cb2757c5") center;
  background-size: 100% auto;
  width: 100%;
  height: 100%;
  position: fixed;
  min-width: 1466px;
  min-height: 693px;
}

.login_container {
  border-radius: 5px;
  background-clip: padding-box;
  margin: 10% auto auto auto;
  width: 25%;
  min-width: 250px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 2px #cac6c6;
}

.login_title {
  margin-top: 0;
  text-align: center;
  font-weight: normal;
}

.text-input {
  margin: 1.5% auto;
  text-align: center;
}

.button_login {
  margin: 10px auto 5px auto;
  width: 30%;
  border: none;
  color: #fef6f6;
  opacity: 0.9;
}

.toRegister {
  text-align: center;
  margin-top: 20px
}

.toRegister span {
  color: #ffffff;
  display: inline-block;
}

#register-link, #home-link {
  margin: auto 5px;
  display: inline-block;
  font-size: 16px;
  vertical-align: top;
}

.footer {
  text-align: center;
  color: #eae8e8;
}
</style>
