<template>
  <el-container class="page"
                @mousemove.native="mouseMove($event)">
    <el-header class="header"
               style="height: 100px;
               background-color: rgba(255,255,255,0.7);"
               @mouseover.native="headerOver()"
               @mouseout.native="headerOut()"
               ref="header">
      <div id="backImageWrapper">
        <el-image :src="backImage"
                  ref="backImage"
                  style="width: 150%;min-height: 1010px;margin-left: -25%">
        </el-image>
      </div>
      <el-image class="logo" v-on:click="refresh" :src="logo" style="height: 60px;width: 200px;"></el-image>


      <el-popover
        placement="bottom"
        width="200"
        trigger="hover">
        <el-button class="popover-button" v-on:click="toLogin" type="primary">立即登陆</el-button>
        <div style="text-align: center;padding: 10px 0 10px 0">
          首次使用?
          <router-link to='/Register' style="text-decoration: none">
            <span style="color: #409EFF;">点我注册</span>
          </router-link>

          <!--          <el-link type="primary" href="/Register" style="vertical-align: top;margin-left: 5px">点我注册</el-link>-->
        </div>
        <!--        <el-button v-show="this.$store.state.token === null" slot="reference" class="user-logo" icon="el-icon-s-custom" circle type="primary"></el-button>-->
        <el-avatar icon="el-icon-user-solid" :size="50" class="user-logo" v-show="this.$store.state.token === null"
                   slot="reference"></el-avatar>
      </el-popover>

      <el-popover
        placement="bottom"
        width="200"
        trigger="hover">
        <p class="popover-text">你好， {{this.$store.state.username}}</p>
        <p class="credit_show">信用: {{credit}}</p>
        <el-button class="popover-button" v-on:click="toUsers" type="primary">个人中心</el-button>
        <el-button class="popover-button" v-on:click="Logout" type="primary">退出登陆</el-button>
        <el-button class="popover-button" v-on:click="toRule" type="primary">用户手册</el-button>
        <!--        <el-button v-show="this.$store.state.token !== null" slot="reference" class="user-logo" icon="el-icon-s-custom" circle type="primary"></el-button>-->
        <el-avatar icon="el-icon-user-solid" :size="50" class="user-logo" v-show="this.$store.state.token !== null"
                   slot="reference"></el-avatar>
      </el-popover>


      <div class="search-panel">
        <el-form :model="searchForm">
          <el-form-item :inline="true" class="search-form">
            <el-input id="search-input"
                      clearable
                      placeholder="请输入你要搜索的内容"
                      v-model="searchForm.name"
                      style="height: 40px;
                        width: 400px;"></el-input>
            <el-button id="search-btn"
                       type="primary"
                       v-on:click="searchBooksByName"
                       icon="el-icon-search">搜索
            </el-button>
          </el-form-item>
        </el-form>
      </div>
      <!--      <el-menu class="header-menu" mode="horizontal" background-color="#804848" text-color="#fff"  >
              <router-link to="/">
                <el-menu-item index="1" id="Home-label" v-on:click="refresh">Home</el-menu-item>
              </router-link>
              <router-link to="/Register" v-show="this.$store.state.token === null">
                <el-menu-item index="2" id="Register-label">Register</el-menu-item>
              </router-link>
              <router-link to="/Login" v-show="this.$store.state.token === null">
                <el-menu-item index="3" id="Login-label">Login</el-menu-item>
              </router-link>
              <router-link to="/Login" v-show="this.$store.state.token !== null">
                <el-menu-item index="4" id="Logout-label" v-on:click="Logout">Logout</el-menu-item>
              </router-link>
              <router-link to="/Users" v-show="this.$store.state.token !== null">
                <el-menu-item index="5" id="User-label">Users</el-menu-item>
              </router-link>
            </el-menu>-->

    </el-header>
    <el-main>
      <div class="book-panel b-wrapper">
        <div class="book-block" v-for="data in bookData">
          <div class="book-box" @click="toDetail(data.id)">
            <div class="book-image">

              <el-image :src="data.url"
                        @click="toDetail(data.id)"
                        v-loading="loading"
                        style="width: 150px;
                        height: 200px;
                        border: 1px solid #DCDFE6;
                        border-radius: 5px 0 0 5px;
                        margin-top: 5px">
              </el-image>
            </div>
            <div class="book-details">
              <p class="book-name">{{data.name}}</p>
              <span>评分：<span style="color: goldenrod;">{{data.score}}</span></span>
              <p class="book-description">{{data.description}}</p>
            </div>
          </div>
        </div>
      </div>
      <div class="pagination-panel b-wrapper">
        <el-pagination
          class="pagination"
          background
          :current-page.sync=this.page
          @current-change="handlePageChange"
          layout="prev, pager, next, jumper"
          :total="this.totalPage*10">
        </el-pagination>
      </div>
    </el-main>
    <el-footer class="footer" style="height: 100px;padding: 20px 0 10px 0;text-align: center">
      <p>沐露书风 ©Copyright 2021</p>
    </el-footer>
  </el-container>
</template>

<script>

// const remoteUrl = 'http://106.14.183.192:8099';
let remoteUrl = "http://localhost:8099";


export default {
  remoteUrl:"http://localhost:19099",

  name: 'Home',


  data() {
    return {
      searchForm: {
        name: '',
        description: '',
      },
      credit: '',
      bookData: [],
      page: 1,
      totalPage: 1,
      mode: 'display',
      backImage: 'https://team13-book.oss-cn-beijing.aliyuncs.com/images/books-1281581.jpg',
      remoteUrl:'http://localhost:80',
      logo: 'https://team13-book.oss-cn-beijing.aliyuncs.com/images/%E6%B2%90%E9%9C%B2%E4%B9%A6%E9%A3%8E.png',
    }
  },

  created() {
    //渲染页码前获取页码信息！
    this.setPage()
    this.checkToken()
    this.get_credit()
  },

  mounted() {
    this.setMode()
    if (this.mode === 'display') {
      this.findAllBooks()
    } else {
      this.searchBooksByName()
    }
  },


  methods: {
    checkToken() {
      let token = this.$store.state.token
      let role = this.$store.state.role

      if (role === 'admin') {
        this.$router.push({path: '/AdministratorHome'})
      } else if (role === 'sAdmin') {
        this.$router.push({path: '/SuperAdministratorUser'})
      }
    },

    refresh() {
      this.$router.push('/')
      this.$router.go(0)
    },

    toLogin() {
      this.$router.push('/Login')
    },

    toUsers() {
      this.$router.push('/Users')
    },

    Logout() {
      this.$store.commit('logout')
      this.$message({
        message: '登出成功',
        type: 'success',
        duration: 750
      });
      this.$router.replace("/Login");
    },

    findAllBooks() {
      this.$axios({
        url: remoteUrl + "/book/findAll",
        //url:"http://106.14.183.192:8099/book/findAll",
        method: "get",

      }).then(resp => {
        console.log('resp.data: ')
        console.log(resp.data)
        this.display(resp.data.data)
      })
    },

    get_credit() {
      this.$axios.get(remoteUrl + '/user/getUserCredit/' + this.$store.state.username)
        .then(resp => {

          console.log("返回的信用在这里：", resp.data.data)

          if (resp.data.code === 20000) {
            this.credit = resp.data.data

          } else {
            this.$message({
              message: '信用取不了',
              type: 'warning',
              duration: 750
            })
          }
        })
      // .catch(error => {
      //     console.log(error)
      //     this.$message.error('发生错误!')
      // })
    },

    setPage() {
      let page = Number(this.$route.query.page)

      if (!isNaN(page)) {
        this.page = page
      } else {
        this.page = 1
      }
      console.log('page=' + this.page)

    },

    setMode() {
      let mode = this.$route.query.mode
      if (mode === 'search') {
        this.mode = 'search'
        this.searchForm.name = this.$route.query.searchName
      } else {
        this.mode = 'display'
      }
      console.log('mode=' + this.mode)
      console.log('this.searchForm.name:' + this.searchForm.name)
    },

    display(data) {
      let page = this.page
      let pageSize = 8;
      this.totalPage = Math.ceil(data.length / pageSize)
      console.log("totalPage=" + this.totalPage)
      this.bookData = data
      if (data.length > pageSize) {
        let i = 0
        for (; i < pageSize && data[(page - 1) * pageSize + i] !== undefined; i++) {
          this.bookData[i] = data[(page - 1) * pageSize + i]
        }
        this.bookData.length = i
      }
      console.log('处理后(图书信息）resp.data:')
      console.log(this.bookData)
    },

    searchBooksByName() {
      let name = this.searchForm.name

      if (name !== '') {
        this.$router.push('/?mode=search&searchName=' + name + '&page=' + this.page)
        //location.hash = 'Home?mode=search&searchName=' + name + '&page=' + this.page
        this.$axios.get(remoteUrl + '/book/findAllByNameLikeOrISBNLike/' + name)
          // this.$axios.get('http://106.14.183.192:8099/book/findAllByNameLike/' + name)
          .then(resp => {
            console.log('resp.data.data:')
            console.log(resp.data)

            if (resp.data.code === 20000) {
              console.log('resp.data.data: ')
              console.log(resp.data.data)
              this.display(resp.data.data)
              this.$message({
                message: '搜索成功',
                type: 'success',
                duration: 750
              })

            } else {
              this.$message({
                message: '搜索失败',
                type: 'warning',
                duration: 750
              })
            }
          })
          .catch(error => {
            console.log(error)
            this.$message.error('发生错误!')
          })
      } else {
        this.$router.push('/')
        location.reload()
      }
    },

    toDetail(id) {
      this.$router.push('/Detail?id=' + id)
    },

    toRule() {
      this.$router.push('/rule')
    },

    handlePageChange(val) {
      if (this.mode === 'display') {
        this.$router.replace({path: '/?mode=' + this.mode + '&page=' + val})
        location.reload()
      } else {
        this.$router.replace({path: '/?mode=' + this.mode + '&searchName=' + this.searchForm + '&page=' + val})
        location.reload()
      }
    },


    mouseMove(e) {
      /*      console.log('mouseMove')
            console.log(e.x)
            console.log(e.y)
            console.log(this.$refs.backImage.$el.style.cssText)*/
      this.$refs.backImage.$el.style.cssText = "margin-left: -25%;width: 150%;min-height: 1010px;transition: 0.1s linear;transform: translate(" + ((e.x - 733) * 0.014) + "px)"
    },

    headerOver() {
      this.$refs.header.$el.style.cssText = "background-color: rgba(255,255,255,0.85);transition: 0.2s linear;height: 100px;box-shadow: 0 0 30px 0 rgba(255,255,255,0.1)"
    },

    headerOut() {
      this.$refs.header.$el.style.cssText = "background-color: rgba(255,255,255,0.7);transition: 0.2s linear;height: 100px;"
    }
  }
}
</script>

<style scoped>
.page {
  position: relative;
  height: 100%;
  width: 100%;
  min-width: 1000px;
}

#backImageWrapper {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  height: 100%;
  width: 100%;
  min-width: 1000px;
  min-height: 692px;
  overflow: hidden;
  z-index: -1000;
  opacity: 0.65;
}

.logo {
  margin-top: 10px;
  float: left;
}

.user-logo {
  margin: 20px 70px auto auto;
  float: right;
}

.popover-text {
  margin: 0;
  text-align: center;
}

.credit_show {
  margin: 0;
  text-align: center;
  font-family: "Bernard MT Condensed";
  font-size: 20px;
}

.popover-button {
  margin: 10px 0 0 0;
  border-radius: 2px;
  text-align: center;
  width: 100%;
}

.search-panel {
  margin: 25px auto 0 auto;
}

.search-form {
  position: relative;
  width: 500px;
  margin: 0 auto;
}

#search-btn {
  text-align: center;
  vertical-align: top;
  height: 40px;
  min-width: 70px;
  border: none;
  opacity: 0.9;
}

.b-wrapper {
  min-width: 1000px;
  max-width: 1350px;
  margin: 0 auto;
}

.rule {
  background-color: #8a0000;
  width: 10%;
  height: 50%;
}

.book-panel {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: flex-start;
  align-items: flex-start;

}

.book-block {
  display: inline-block;
  margin: 15px;
  height: 30%;
}

.book-box {
  position: relative;
  width: 300px;
  height: 210px;
  border: 1px solid #DCDFE6;
  border-radius: 5px;
  background-color: rgba(242, 244, 248, 0.8);
  box-shadow: 0 2px 10px 0 rgba(0, 0, 0, 0.1);
  text-align: left;
}

.book-box:hover {
  transition: 0.3s;
  background: rgba(234, 238, 245, 0.9);
  box-shadow: 0 10px 20px 0 rgba(0, 0, 0, 0.4);
  transform: translate(-1px, -1px);
}

.book-image, .book-details {
  position: absolute;
  top: 0;
  bottom: 0;
  display: inline-block;
  vertical-align: top;
}

.book-image {
  left: 0;
}

.book-details {
  right: 0;
  width: 140px;
  height: 202px;
  box-sizing: border-box;
}

.book-name {
  height: 30px;
  text-align: center;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  margin: 15px auto 5px auto;
  border-bottom: 1px solid #b2b2b2;
}

.book-description {
  margin: 15px;
  height: 125px;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  text-overflow: ellipsis;
  overflow: hidden;
  -webkit-line-clamp: 6;
}

.pagination-panel {
  text-align: center;
  margin-top: 15px;
}

.footer p {
  font-weight: bold;
  color: #000000;
}
</style>
