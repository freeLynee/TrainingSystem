<template>
  <el-container class="page">
    <el-header class="header" style="height: 70px">
      <div class="background">
        <el-image
          src="https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.vipyl.com%2Fuser%2Fwebimg%2F2017619%2F20176191725449.jpg&refer=http%3A%2F%2Fwww.vipyl.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1643234944&t=a7c5b679ff772d76bca9a9b46cdc2a8a"
          alt="背景加载中" class="background-image" fit="cover"></el-image>
      </div>
      <el-page-header @back="handleRouteBack" content='课程详情'>
      </el-page-header>
    </el-header>

    <div class="body" style="margin:0 auto;">
      <div class="book-wrapper">
        <div class="description">
          <div class="description-1">
            <div class="column">
              <h3 class="column-name" style="border-top: none;">课程名称</h3>
              <p class="details">{{name}}</p>
            </div>
            <div class="column">
              <h3 class="column-name">课程ID</h3>
              <p class="details">{{author}}</p>
            </div>
            <div class="column">
              <h3 class="column-name">开课教员ID</h3>
              <p class="details">{{score}}</p>
            </div>
            <div class="column">
              <h3 class="column-name">是否结课</h3>
              <!--              <el-divider class="divider"></el-divider>-->
              <p class="details" id="description-text">{{description}}</p>
            </div>
          </div>
          <div style="width: 1px;height: 388px; background: rgba(0, 0, 0, 0.2);display: inline-block"></div>
          <div class="description-2">
            <div class="column">
              <h3 class="column-name" style="border-top: none;">课程类型</h3>
              <p class="details">{{ISBN}}</p>
            </div>
            <div class="column">
              <h3 class="column-name">课程简介</h3>
              <p class="details">{{date}}</p>
            </div>

          </div>
        </div>
      </div>

    </div>

  </el-container>
</template>

<script>
import Home from "../Home";

export default {
  name: 'Detail',

  data() {
    return {
      remoteUrl: Home.remoteUrl,
      copyForm: {
        nums: '',
      },
      isAdmin: false,
      url: '',
      author: '',
      name: '',
      description: '',
      ISBN: '',
      date: '',
      lib: '',
      nums: '',
      price: '',
      score: '',

    }
  },


  created() {
    this.getImageInfo()
    this.checkToken()
  },

  // methods: {
  //   checkToken() {
  //     let role = this.$store.state.role
  //     if (role === 'admin' || role === 'sAdmin') {
  //       this.isAdmin = true
  //     }
  //   },
  //
  //   getImageInfo() {
  //     let id = this.$route.query.id;
  //     this.$axios.get(this.remoteUrl + '/book/findById/' + id,)
  //       .then(resp => {
  //         console.log( resp.data)
  //         this.score=resp.data.data.score;
  //         this.url = resp.data.data.url;
  //         this.author = resp.data.data.author;
  //         this.name = resp.data.data.name;
  //         this.description = resp.data.data.description;
  //         this.ISBN = resp.data.data.isbn;
  //         this.date = resp.data.data.pubDate;
  //         this.nums = resp.data.data.nums;
  //         this.price = resp.data.data.price;
  //         if(this.score===null){
  //           this.score="暂无评分";
  //         }
  //       })
  //   },
  //
  //   /*    Logout() {
  //         this.$store.commit('logout')
  //         this.$message({
  //           message: '登出成功',
  //           type: 'success',
  //           duration: 750
  //         });
  //         this.$router.replace("/Login");
  //       },
  //
  //       toUsers() {
  //         this.$router.replace("/Users");
  //       },*/
  //
  //   toCopies(name) {
  //     this.$router.push('/Copies?name=' + name)
  //   },
  //   toComments(id) {
  //     this.$router.push('/Comments?id='+id)
  //   },
  //
  //   handleRouteBack() {
  //     this.$router.go(-1)
  //   },
  //
  //   addCopy(form) {
  //     this.$refs[form].validate((valid) => {
  //       if (valid) {
  //         let nums = this.copyForm.nums
  //         let name = this.name
  //         let library = this.$store.state.address
  //         console.log('nums: ',nums)
  //         console.log('name: ',name)
  //         console.log('library: ',library)
  //         let postData = new URLSearchParams();
  //         postData.append('name', name)
  //         postData.append('nums', nums)
  //         postData.append('library', library)
  //         this.$axios.post(this.remoteUrl + '/admin/addBookCopy', postData)
  //           .then(resp => {
  //             console.log('resp.data:')
  //             console.log(resp.data)
  //             if (resp.data.code === 20000) {
  //               this.$message({message: '添加成功!', type: 'success', duration: 1000})
  //               setTimeout(function () {
  //                 location.reload()
  //               }, 1000)
  //             } else {
  //               this.$message({message: '添加失败!', type: 'warning', duration: 1000})
  //             }
  //           })
  //           .catch(error => {
  //             console.log('error:')
  //             console.log(error)
  //             this.$message.error('发生错误')
  //           })
  //       } else {
  //         return false;
  //       }
  //     });
  //   },
  // }
}


</script>
<style scoped>
.page {
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  min-width: 1300px;
  min-height: 690px;
  text-align: center;
}

.background {
  position: absolute;
  width: 100%;
  height: 100%;
  min-width: 1300px;
  min-height: 690px;
  top: 0;
  left: 0;
  z-index: -100;
  opacity: 0.5;
  overflow: hidden;
}

.background-image {
  width: 1500px;
  height: 800px;
}

.header {
  padding: 20px 30px;
  background-color: rgba(255, 255, 255, 1);
}

.book-wrapper {
  text-align: left;
}

.picture {
  text-align: center;
  display: inline-block;
  padding: 40px;
  width: 320px;
  height: 400px;
  vertical-align: top;
}

.description {
  margin-top: 40px;
  background: rgba(255, 255, 255, 0.7);
  text-align: center;
  display: inline-block;
  width: 800px;
  margin-left: 50px;
  border-radius: 5px;
  border: 1px solid rgba(0, 0, 0, 0.3);
  height: 458px;
}

.description-1, .description-2 {
  vertical-align: top;
  display: inline-block;
  height: 388px;
}

.description-1 {
  width: 395px;
  box-sizing: border-box;
}

.description-2 {
  width: 395px;
  box-sizing: border-box;
}

.column {
}

.column-name {
  height: 26px;
  margin: 0;
  padding: 12px;
  border-bottom: 1px solid rgba(0, 0, 0, 0.2);
  border-top: 1px solid rgba(0, 0, 0, 0.2);
}

.details {
  text-align: center;
  height: 21px;
  overflow: auto;
  text-overflow: ellipsis;
  margin: 12px;
  padding: 0;
}

#description-text {
  padding: 0;
  margin: 12px;
  text-align: left;
  height: 63px;
}

.divider {
  margin: 0;
}

.button-panel {
  margin-top: 20px;
}

.footer p {
  font-weight: bold;
  color: rgba(0, 0, 0, 0.8);
}
</style>
