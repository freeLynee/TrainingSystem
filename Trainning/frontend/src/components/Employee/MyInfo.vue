<template>
  <el-container class="page">
    <div class="background">
    </div>
    <el-header class="header" style="height: 70px">
      <el-page-header @back="handleRouteBack" content='员工个人中心'>
      </el-page-header>
    </el-header>

    <div class="user-panel">
      <div class="user-logo">
        <el-avatar :size="90"></el-avatar>
      </div>
      <div class="user-info">
        <div class="user-info-column">员工ID:{{ID}}</div>
        <div class="user-info-column">名字:{{name}}</div>
        <div class="user-info-column">部门:{{dept}}</div>
        <div class="user-info-column">职位:{{position}}</div>

      </div>
    </div>

    <div class="nav-panel">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="基本信息" name="first">基本信息</el-tab-pane>
        <el-tab-pane label="参加的课程" name="second">参加课程</el-tab-pane>


      </el-tabs>
    </div>

    <div class="info-panel" v-if="activeName === 'first'" id="book-panel">
      <div class="user-info">
        <div class="user-info-column">员工ID:{{ID}}</div>
        <div class="user-info-column">名字:{{name}}</div>
        <div class="user-info-column">性别:{{gender}}</div>
        <div class="user-info-column">年龄:{{age}}</div>
        <div class="user-info-column">入职时间:{{joiningTime}}</div>
        <div class="user-info-column">住址:{{address}}</div>
      </div>
    </div>

    <div class="info-panel" v-if="activeName === 'second'" id="history-panel">

      <div class="radio-group">
        <el-radio-group v-model="historyTabPosition" @change="handleHistoryRadioChange">
          <el-radio-button label="正在参加"></el-radio-button>
          <el-radio-button label="培训历史"></el-radio-button>
        </el-radio-group>
      </div>
      <div class="history-panel" id="appoint-history">
        <div class="table-wrapper">
          <div class="table">

            <el-table
              v-if="historyTabPosition === '正在参加'"
              ref="onClass"
              tooltip-effect="dark"
              height="450"
              :data="onClass"
              style="border-radius: 5px;border: 1px solid #DCDFE6;">
              <el-table-column prop="course_name" label="课程名称" width="200"></el-table-column>
              <el-table-column prop="course_id" label="课程ID" width="200"></el-table-column>
              <el-table-column prop="grade" label="成绩" width="200"></el-table-column>
              <el-table-column prop="is_endClass" label="是否结课" width="200"></el-table-column>
              <el-table-column prop="is_endClass" label="开课教员" width="200"></el-table-column>
            </el-table>

            <el-table
              v-if="historyTabPosition === '培训历史'"
              ref="onClass"
              tooltip-effect="dark"
              height="450"
              :data="endClass"
              style="border-radius: 5px;border: 1px solid #DCDFE6;">
              <el-table-column prop="course_name" label="课程名称" width="200"></el-table-column>
              <el-table-column prop="course_id" label="课程ID" width="200"></el-table-column>
              <el-table-column prop="grade" label="成绩" width="200"></el-table-column>
              <el-table-column prop="teacher" label="开课教员" width="200"></el-table-column>
              <el-table-column prop="graduation" label="是否通过" width="200"></el-table-column>
            </el-table>
          </div>
        </div>
      </div>
    </div>

  </el-container>
</template>
<script>

export default {

  created() {

    this.getOnClass()
    this.getClassHistory()
  },
  mounted() {
    this.setMode()
    if (this.mode === 'display') {
      this.getFinish()
    }
  },

  data() {
    return {
      historyTabPosition: "",

      applyH: '1',
      applySec: '1',
      activeName: 'first',

      onClass:[],//储存正参加课程
      endClass:[],//储存已结课的课程

      name: this.$store.state.name,
      ID:this.$store.state.id,
      address:this.$store.state.address,
      email: this.$store.state.email,
      age: this.$store.state.age,
      gender: this.$store.state.gender,
      dept: this.$store.state.department,
      phone: this.$store.state.phone,
      joiningTime:this.$store.state.joiningTime,
      position:this.$store.state.position,
      //role: this.$store.state.role,
      //userId: this.$store.state.userId,

    }
  },

  methods: {
    ToSetCourse() {
      this.$router.push({path: '/SetCourse'})
    },
    ToSetting() {
      this.$router.push({path: '/Setting'})
    },

    getOnClass(){
      this.$axios({
        url:"http://localhost:19098/training/getOnClass/"+this.ID,
        method: "get",
      }).then(resp => {
        console.log('正参加: ')
        console.log(resp.data)
        this.onClass=resp.data
        // this.display(resp.data.data)
      })
    },

    getClassHistory(){
      this.$axios({
        url:"http://localhost:19098/training/getClassHistory/"+this.ID,
        method: "get",
      }).then(resp => {
        console.log('已结课: ')
        console.log(resp.data)
        this.endClass=resp.data
        // this.display(resp.data.data)
      })
    }
  }


}
</script>

<style scoped>
.page {
  position: absolute;
  width: 100%;
  min-width: 750px;
  min-height: 910px;
  text-align: center;
}

.background {
  background: url("https://t7.baidu.com/it/u=351009663,2085847565&fm=193&f=GIF") no-repeat center top;
  background-size: 1800px;
  position: absolute;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  z-index: -1; /* -1 可以当背景 */
  opacity: 0.4;
}

.header {
  padding: 20px 30px;
  background-color: rgba(255, 255, 255, 1);
}

.user-panel, .book-info {
}

.user-panel {
  padding: 40px 0;
  margin: 0 50px;
}


.user-logo, .user-info {
  display: inline-block;
  vertical-align: top;
}

.user-info {
  margin: 0 20px;
}

.user-info-column {
  text-align: left;
  margin: 10px 0;
  font-size: 16px;
  font-weight: bold;
}

.nav-panel {
  margin: 0 50px;
  padding: 10px 30px 20px 30px;
  background: #ffffff;
  border-radius: 5px 5px 0 0;
}

.info-panel {
  background: #ffffff;
  margin: 0 50px;
}

.radio-group {
  margin-bottom: 20px;
}

.book-panel, .history-panel {
  margin: 20px 30px;
  min-height: 500px;
}

.book-block-wrapper {
  min-height: 445px;
  text-align: left;
}

.book-block {
  display: inline-block;
  margin: 10px 6px;
  height: 200px;
  width: 590px;
  background-color: rgba(242, 244, 248, 0.9);
  border: 1px solid #DCDFE6;
  border-radius: 5px;
}

.table {
  display: inline-block;
}

.book-image, .book-details-wrapper {
  display: inline-block;
  vertical-align: top;
}

.book-details-wrapper {
  padding: 0 10px;
  width: 410px;
}

.book-details {
  height: 30px;
  padding: 10px 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  text-align: left;
}

.pagination {
  margin: 20px 0;
}

.footer p {
  font-weight: bold;
  color: #ffffff;
}
</style>

//查看基本信息，身份，参加的课程，开设的课程，部门主管和教员会有跳转到setting的引导，还有员工申请转部门的申请
