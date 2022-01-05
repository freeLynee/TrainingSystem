<template xmlns:>
  <el-container class="page">

    <el-aside class="el-aside" style="border-right: solid 1px #e6e6e6;">
      <div class="background">

      </div>
      <el-menu :default-active="activeIndex"
               class="el-menu"
               mode="vertical"
               @select="handleSelect"
               style="border-right: none;"
               background-color="rgba(255,255,255,0)">

        <el-menu-item>
          <i class="el-icon-user"></i>
          <span>名字:  {{this.$store.state.username}}</span>
        </el-menu-item>

        <el-menu-item>
          <i class="el-icon-bell"></i>
          <span>ID:  {{this.$store.state.email}}</span>
        </el-menu-item>

        <el-menu-item>
          <i class="el-icon-bell"></i>
          <span>部门:  {{this.$store.state.email}}</span>
        </el-menu-item>


        <el-menu-item index="1">
          <i class="el-icon-search"></i>
          <span>管理员工以及课程</span>
        </el-menu-item>

        <el-menu-item index="2">
          <i class="el-icon-message"></i>
          <span>员工申请信箱</span>
        </el-menu-item>


      </el-menu>
    </el-aside>

    <el-container>
      <div class="wrapper">


        <div class="addAdmin" v-if="add===0">
          <div class="nav-panel">
            <el-tabs v-model="activeName" @tab-click="handleClick">
              <el-tab-pane label="点击查看普通员工" name="first"></el-tab-pane>
              <el-tab-pane label="点击查看教员员工" name="second"></el-tab-pane>
              <el-tab-pane label="点击查看部门课程" name="third"></el-tab-pane>
            </el-tabs>
          </div>

          <div class="info-panel" v-if="activeName === 'first'" id="history-panel">

            <div class="history-panel" id="appoint-history">
              <div class="table-wrapper">
                <div class="table">

                  <el-table
                    ref="multipleTable"
                    tooltip-effect="dark"
                    height="450"
                    :data="tableData" style="border-radius: 5px;border: 1px solid #DCDFE6;">
                    <el-table-column prop="name" label="名字" width="100">名字</el-table-column>
                    <el-table-column prop="number" label="员工ID" width="100"></el-table-column>
                    <el-table-column prop="operationTime" label="性别" width="100"></el-table-column>
                    <el-table-column prop="operationTime" label="职位" width="100"></el-table-column>
                    <el-table-column prop="operationTime" label="邮箱" width="200"></el-table-column>
                    <el-table-column prop="operationTime" label="入职时间" width="200"></el-table-column>
                    <el-table-column prop="operationTime" label="设为教员" width="100"></el-table-column>
                  </el-table>
                </div>
              </div>
            </div>
          </div>

          <div class="info-panel" v-if="activeName === 'second'">

            <div class="history-panel">
              <div class="table-wrapper">
                <div class="table">

                  <el-table
                    ref="multipleTable"
                    tooltip-effect="dark"
                    height="450"
                    :data="tableData" style="border-radius: 5px;border: 1px solid #DCDFE6;">
                    <el-table-column prop="name" label="名字" width="100">名字</el-table-column>
                    <el-table-column prop="number" label="员工ID" width="100"></el-table-column>
                    <el-table-column prop="operationTime" label="性别" width="100"></el-table-column>
                    <el-table-column prop="operationTime" label="职位" width="100"></el-table-column>
                    <el-table-column prop="operationTime" label="邮箱" width="200"></el-table-column>
                    <el-table-column prop="operationTime" label="入职时间" width="200"></el-table-column>
                    <el-table-column prop="operationTime" label="撤销教员" width="100"></el-table-column>
                  </el-table>
                </div>
              </div>
            </div>
          </div>

          <div class="info-panel" v-if="activeName === 'third'">

            <div class="history-panel">
              <div class="table-wrapper">
                <div class="table">

                  <el-table
                    ref="multipleTable"
                    tooltip-effect="dark"
                    height="450"
                    :data="tableData" style="border-radius: 5px;border: 1px solid #DCDFE6;">
                    <el-table-column prop="name" label="课程名字" width="100">名字</el-table-column>
                    <el-table-column prop="number" label="课程ID" width="100"></el-table-column>
                    <el-table-column prop="operationTime" label="类型" width="100"></el-table-column>
                    <el-table-column prop="operationTime" label="开设教员" width="100"></el-table-column>
                    <el-table-column prop="operationTime" label="是否结课" width="200"></el-table-column>
                  </el-table>
                </div>
              </div>
            </div>
          </div>


        </div>


      </div>
    </el-container>
  </el-container>
</template>
<script>

import Home from "../Home";

export default {

  created() {

    this.checkToken()
    this.name = this.$store.state.username;
    this.email = this.$store.state.email;
    this.address = this.$store.state.address;
    this.findAllBookHistoryAppointmentExpire();
    this.findAllBookCopyLendExpire();
    this.findAllBookFine();
    this.getApply();
    this.getDoneNotPay();
    this.getDonePay();
  },
  data() {
    return {
      creditResult: '',
      paid: 'third',
      activeName: 'first',
      tableData: [],
      activeIndex: '1',
      add: '',
      searchForm: {
        name: '',
        username: '',
      },

      resetForm: {
        username: ''
      },

      name: this.$store.state.username,
      email: this.$store.state.email,
      address: this.$store.state.address,


    }
  },

  methods: {
    handleSelect(key, keyPath) {
      if (key === '1') {
        this.add = 0
      } else if (key === '2') {
        this.add = 1
      } else if (key === '3') {
        this.add = 2
      }
    },
  },

  // methods: {
  //
  //   handleClick(tab, event) {
  //     console.log(tab, event)
  //   },
  //
  //   getApply() {
  //     this.$axios.get(this.remoteUrl + '/pendCredit/findAllUndo')
  //       .then(resp => {
  //         console.log('重置信用申请:')
  //         console.log("resp.data: ", resp.data)
  //
  //         if (resp.data.code === 20000) {
  //           this.applylist = resp.data.data
  //           console.log("未处理的申请：", resp.data.data)
  //         }else{
  //           this.$message.success('没有未处理的申请！')
  //         }
  //       })
  //       .catch(error => {
  //         console.log(error)
  //         this.$message.error('发生错误!')
  //       })
  //   },
  //
  //   getDoneNotPay() {
  //     this.$axios.get(this.remoteUrl + '/pendCredit/findAllNotPay')
  //       .then(resp => {
  //
  //         console.log('已处理但没交钱重置信用申请:')
  //         console.log("已处理但没交钱的申请: ", resp.data)
  //
  //         if (resp.data.code === 20000) {
  //
  //           this.doneNotPaidlist = resp.data.data
  //           console.log("resp.data:（返回的信息) ", resp.data.data)
  //         }
  //
  //       })
  //       .catch(error => {
  //         console.log(error)
  //         this.$message.error('发生错dgfnv误!')
  //       })
  //   },
  //
  //   getDonePay() {
  //     this.$axios.get(this.remoteUrl + '/pendCredit/findAllDone')
  //       .then(resp => {
  //
  //         console.log('已完成的重置信用申请:')
  //         console.log("已完成的申请: ", resp.data)
  //
  //         if (resp.data.code === 20000) {
  //           this.donePaidlist = resp.data.data
  //           console.log("resp.data:（返回的信息) ", resp.data.data)
  //         }
  //
  //       })
  //       .catch(error => {
  //         console.log(error)
  //         this.$message.error('发生错误!')
  //       })
  //   },
  //
  //   Pass(id, pass, creditResult) {
  //
  //     let postData = new URLSearchParams();
  //     postData.append('id', id)
  //     postData.append('pass', pass)
  //     postData.append('credit', creditResult)
  //     if( creditResult > 100 || creditResult <0){
  //       this.$message({
  //         message: '充值的信用范围为0~100以内！',
  //         type: 'warning',
  //         duration: 750
  //       })
  //       return false
  //     }
  //     this.$axios.post(this.remoteUrl + '/admin/examineApplication', postData)
  //       .then(resp => {
  //
  //         console.log("resp.data:（管理员反馈) ", resp.data)
  //         if (resp.data.code === 20000) {
  //           this.$message({
  //             message: '处理申请成功',
  //             type: 'success',
  //             duration: 750
  //           })
  //           location.reload();
  //
  //         } else {
  //           this.$message({
  //             message: '管理员反馈失败',
  //             type: 'error',
  //             duration: 750
  //           })
  //         }
  //       })
  //
  //   },
  //
  //   remindPayCredit(id){
  //     let postData = new URLSearchParams();
  //     postData.append('id', id)
  //
  //     this.$axios.post(this.remoteUrl + '/admin/remindUserPayCredit',postData)
  //       .then(resp => {
  //         if(resp.data.code === 20000){
  //           this.$message({
  //             message: '提醒邮件发送成功！',
  //             type: 'success',
  //             duration: 750
  //           })
  //         }
  //       })
  //
  //   },
  //
  //
  //   showResult(result) {
  //     if (result === true) {
  //       return '已批准申请'
  //     } else {
  //       return '已驳回申请'
  //     }
  //   },
  //
  //   refresh() {
  //     this.$router.push('/SuperAdministratorUser')
  //   },
  //
  //   handleSelect(key, keyPath) {
  //     if (key === '1') {
  //       this.add = 0
  //     } else if (key === '2') {
  //       this.add = 1
  //     } else if (key === '3') {
  //       this.add = 2
  //     } else if (key === '4') {
  //       this.$router.push('/AddBook')
  //     } else if (key === '5') {
  //       this.$router.push('/ChangePass')
  //     } else if (key === '6') {
  //       this.Logout()
  //     } else if (key === '7') {
  //       this.add = 3
  //     } else if (key === '8') {
  //       this.add = 4
  //     }
  //   },
  //   checkToken() {
  //     let token = this.$store.state.token
  //     let role = this.$store.state.role
  //     if (token === null) {
  //       this.$message({
  //         message: '请先登陆！',
  //         type: 'warning',
  //         duration: 750
  //       })
  //       this.$router.replace("/");
  //     } else if (role === 'normal') {
  //       this.$router.push({path: '/Home'})
  //       this.$message.error('你没有访问权限!')
  //     }
  //   },
  //   AddAdministrator(formName) {
  //     this.$refs[formName].validate(valid => {
  //       if (this.AddAdministratorForm.adminPassword !== this.AddAdministratorForm.adminPassword) {
  //         this.$message.warning({message: '两次输入的密码不一致,请重新输入', duration: 1000})
  //       } else if (valid) {
  //         // let postData = new URLSearchParams();
  //         // postData.append('username',this.AddAdministratorForm.administrator)
  //         // postData.append('password',this.AddAdministratorForm.administrator)
  //         // confirm(this.$store.state.username+this.PassForm.password)
  //         this.$axios.post(this.remoteUrl + '/admin/insertAdmin', {
  //             //this.$axios.post('http://106.14.183.192:8099/admin/insertAdmin',{
  //             username: this.AddAdministratorForm.administrator,
  //             password: this.AddAdministratorForm.adminPassword,
  //             email: this.AddAdministratorForm.adminEmail,
  //           }
  //         ).then(resp => {
  //           console.log('resp.data:')
  //           console.log(resp.data)
  //           if (resp.data.code === 20000) {
  //             this.$message.success({message: '添加管理员成功', duration: 1000})
  //           } else {
  //             this.$message.warning({message: '添加管理员失败，' + resp.data.data, duration: 1000})
  //           }
  //         })
  //           .catch(error => {
  //             console.log(error)
  //             this.$message.error('发生错误')
  //           })
  //       } else {
  //         return false
  //       }
  //     });
  //   },
  //   setForms(formName) {
  //     this.$refs[formName].validate(valid => {
  //       if (valid) {
  //         let lendExpire = this.setForm.lendExpireDay * 8640 + this.setForm.lendExpireHour * 360 + this.setForm.lendExpireMinute * 6 + this.setForm.lendExpireSecond;
  //         let appointmentExpire = this.setForm.appointmentExpireDay * 8640 + this.setForm.appointmentExpireHour * 360 + this.setForm.appointmentExpireMinute * 6 + this.setForm.appointmentExpireSecond;
  //         console.log(lendExpire);
  //         let postData = new URLSearchParams();
  //         postData.append('role', this.setForm.role,)
  //         postData.append('maxCopy', this.setForm.maxCopy)
  //         postData.append('lendExpire', lendExpire)
  //         postData.append('appointmentExpire', appointmentExpire)
  //         // confirm(this.$store.state.username+this.PassForm.password)
  //         this.$axios.post(this.remoteUrl + '/role/setRoleProperty', postData
  //           // {
  //           //this.$axios.post('http://106.14.183.192:8099/admin/insertAdmin',{
  //           // role:this.setForm.role,
  //           // maxCopy:this.setForm.maxCopy,
  //           // lendExpire:this.setForm.lendExpire,
  //           // appointmentExpire:this.setForm.appointmentExpire,
  //           // }
  //         ).then(resp => {
  //           console.log('resp.data:')
  //           console.log('111111111111111')
  //           console.log(resp.data)
  //           if (resp.data.code === 20000) {
  //             this.$message.success({message: '修改成功', duration: 1000})
  //           } else {
  //             this.$message.warning({message: '修改失败，' + resp.data.data, duration: 1000})
  //           }
  //         })
  //           .catch(error => {
  //             console.log(error)
  //             this.$message.error('发生错误')
  //           })
  //       } else {
  //         return false
  //       }
  //     });
  //   },
  //
  //   // refresh(){
  //   //     this.$router.push({path: '/AdministratorUser'})
  //   //     this.$router.go(0)
  //   // },
  //
  //   clickReset(userId){
  //
  //     if(this.newCredit > 100 || this.newCredit < 0){
  //       this.$message.warning({message: '设置信用范围只能在0~100以内！', duration: 1000})
  //       return false
  //     }
  //
  //     let postData = new URLSearchParams();
  //     postData.append('userId', userId)
  //     postData.append('credit', this.newCredit)
  //
  //     this.$axios.post(this.remoteUrl + '/admin/resetUserCredit',postData)
  //       .then(resp => {
  //         console.log(resp.data)
  //         if (resp.data.code === 20000) {
  //           this.$message({message: '重置成功 !', type: 'success', duration: 1000})
  //
  //         } else {
  //           console.log("重置信用失败返回信息：", resp.data.data)
  //           this.$message({message: '重置失败 ! ' , type: 'warning', duration: 2000})
  //         }
  //       })
  //       .catch(error => {
  //         console.log('error:')
  //         console.log(error)
  //         this.$message.error('发生错0987误')
  //       })
  //
  //     this.newCredit=''
  //     location.reload();
  //   },
  //
  //
  //   searchForReset(formName) {
  //     let username = this.resetForm.username
  //
  //     this.$axios.get(this.remoteUrl + '/user/findByUsernameLike/'+ username)
  //       .then(resp => {
  //         console.log(resp.data)
  //
  //         if (resp.data.code !== 20000) {
  //           this.$message({message: '用户不存在! ', type: 'warning', duration: 2000})
  //           return false
  //
  //         } else {
  //           console.log("返回的用户啦啦数据：",resp.data.data)
  //           this.tableData = resp.data.data
  //           this.$message({message: '用户找到了! ', type: 'success', duration: 2000})
  //
  //         }
  //       })
  //       .catch(error => {
  //         console.log('error:')
  //         console.log(error)
  //         this.$message.error('发生错误')
  //
  //       })
  //
  //   },
  //
  //
  //
  //
  //
  //
  //   Logout() {
  //     this.$store.commit('logout')
  //     this.$message({
  //       message: '登出成功',
  //       type: 'success',
  //       duration: 750
  //     });
  //     this.$router.push("./");
  //   },
  //
  //
  //
  //   toAdd() {
  //     this.$router.push("/AddBook");
  //   },
  //
  //   toChange() {
  //     this.$router.push("/ChangePass");
  //   },
  //   addAdmin() {
  //     this.add = '0';
  //   },
  //
  //   changeRules() {
  //     this.add = '1';
  //   },
  //   warning() {
  //     this.add = '2';
  //   },
  //   warnings() {
  //     this.$axios.post(this.remoteUrl + '/admin/sendEmail')
  //       // this.$axios.get('http://106.14.183.192:8099/bookCopy/findAllCopyByName/' + this.name,)
  //       .then(resp => {
  //         console.log(resp.data)
  //         console.log("resp.data")
  //         this.$message({
  //           message: '成功发送提醒邮件',
  //           type: 'success',
  //           duration: 750
  //         });
  //       })
  //
  //   },
  //   findAllBookHistoryAppointmentExpire() {
  //     this.$axios.get(this.remoteUrl + '/bookHistory/findAllBookHistoryAppointmentExpire')
  //       // this.$axios.get('http://106.14.183.192:8099/bookCopy/findAllCopyByName/' + this.name,)
  //       .then(resp => {
  //         console.log(resp.data)
  //         this.date1 = resp.data.data;
  //       })
  //
  //   },
  //   findAllBookCopyLendExpire() {
  //     this.$axios.get(this.remoteUrl + '/bookCopy/findAllBookCopyLendExpire')
  //       // this.$axios.get('http://106.14.183.192:8099/bookCopy/findAllCopyByName/' + this.name,)
  //       .then(resp => {
  //         console.log(resp.data)
  //         this.date2 = resp.data.data;
  //       })
  //   },
  //   findAllBookFine() {
  //     this.$axios.get(this.remoteUrl + '/bookFine/findAllBookFine')
  //       // this.$axios.get('http://106.14.183.192:8099/bookCopy/findAllCopyByName/' + this.name,)
  //       .then(resp => {
  //         console.log(resp.data)
  //         this.date3 = resp.data.data;
  //       })
  //   },
  //   // changeAddress()
  //   //     this.$router.push("/whichLibrary");
  //   // }
  //
  // },


}


</script>


<style scoped>
.page {
  position: absolute;
  width: 100%;
  height: 100%;
  min-width: 1350px;
  min-height: 690px;
  text-align: center;
}

.background {
  position: absolute;
  width: 100%;
  height: 100%;
  min-width: 1350px;
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

.el-aside {
  text-align: left;
  background: rgba(255, 255, 255, 0.7);
}

.logo-wrapper {
  text-align: center;
  padding: 10px;
}

.background {
  background: url("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic181.nipic.com%2Ffile%2F20180909%2F5818563_000050699086_2.jpg&refer=http%3A%2F%2Fpic181.nipic.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1643244971&t=e426bb8bfc71bad17e25fa9774d5d47a") no-repeat center top;
  background-size: cover;
  position: absolute;
  width: 100%;
  height: 100%;
  z-index: -1; /* -1 可以当背景 */
  opacity: 0.5;
}

.ResetTable {
  width: 38%;
}

/*.header-menu {*/
/*  border-bottom: none;*/
/*}*/

/*#Home-label {*/
/*  float: left;*/
/*}*/

/*#User-label {*/
/*  color: #ffffff !important;*/
/*  background-color: rgb(102,58,58) !important;*/
/*}*/

/*#User-label:hover {*/
/*  background-color: rgb(73, 41, 41) !important;*/
/*}*/

/*#User-label,#Logout-label {*/
/*  float: right;*/
/*}*/
.wrapper {
  width: 100%;
  text-align: center;
}

.addAdmin, .setTime, .sendRemind {
  display: inline-block;
  margin: 30px auto;
  border-radius: 5px;
  border: solid 1px #e6e6e6;
  padding: 20px;
  background: rgba(255, 255, 255, 0.7);
}

.addAdmin {
  width: 450px;
}

.setTime {
  width: 900px;
}

.nav-panel {
  background-color: white;
  border: gray 2px solid;
  border-radius: 10px;
}

.info-panel {

  background: rgba(255, 255, 255, 0.7);
}

.book-box {
  border: black 2px solid;
  border-radius: 2px;
}

.apply-label {
  font-size: 20px;
  font-weight: 500;
}


</style>
