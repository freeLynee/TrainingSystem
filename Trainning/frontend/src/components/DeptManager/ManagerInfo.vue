<template>
  <el-container class="page">
    <div class="background">
    </div>
    <el-header class="header" style="height: 70px">
      <el-page-header @back="handleRouteBack" content='部门主管个人中心'>
      </el-page-header>
    </el-header>

    <div class="user-panel">
      <div class="user-logo">
        <el-avatar :size="90"></el-avatar>
      </div>
      <div class="user-info">
        <div class="user-info-column">员工ID:{{ID}}</div>
        <div class="user-info-column">名字:{{name}}</div>
        <div class="user-info-column">部门：{{dept}}</div>

      </div>
    </div>

    <div class="nav-panel">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="基本信息" name="first">基本信息</el-tab-pane>
        <el-tab-pane label="员工及课程信息" name="second"></el-tab-pane>
        <el-tab-pane label="分配转入" name="third"></el-tab-pane>
        <el-tab-pane label="查找" name="fourth"></el-tab-pane>
        <el-tab-pane label="转部门情况" name="five"></el-tab-pane>
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
          <el-radio-button label="所有员工"></el-radio-button>
          <el-radio-button label="部门课程"></el-radio-button>

        </el-radio-group>
      </div>
      <div class="history-panel" id="appoint-history">
        <div class="table-wrapper">
          <div class="table">

            <el-table
              v-if="historyTabPosition === '所有员工'"
              ref="multipleTable"
              tooltip-effect="dark"
              height="450"
              :data="allStaff"
              style="border-radius: 5px;border: 1px solid #DCDFE6;">
              <el-table-column prop="name" label="名字" width="100"></el-table-column>
              <el-table-column prop="id" label="员工ID" width="100"></el-table-column>
              <el-table-column prop="gender" label="性别" width="100"></el-table-column>
              <el-table-column prop="email" label="邮箱" width="100"></el-table-column>
              <el-table-column prop="address" label="住址" width="200"></el-table-column>
              <el-table-column prop="joiningTime" label="入职时间" width="200"></el-table-column>
              <el-table-column prop="department" label="部门" width="200"></el-table-column>
            </el-table>

            <el-table
              v-if="historyTabPosition === '部门课程'"
              ref="multipleTable"
              tooltip-effect="dark"
              height="450"
              :data="allCourse"
              style="border-radius: 5px;border: 1px solid #DCDFE6;">
              <el-table-column prop="course_name" label="课程名称" width="200"></el-table-column>
              <el-table-column prop="course_id" label="课程ID" width="200"></el-table-column>
              <el-table-column prop="teacher_name" label="开课老师" width="200"></el-table-column>
              <el-table-column prop="type" label="课程类型" width="200"></el-table-column>
              <el-table-column prop="is_endClass" label="是否结课" width="200"></el-table-column>

            </el-table>
          </div>
        </div>
      </div>
    </div>

    <div class="info-panel" v-if="activeName === 'third'" id="apply-panel">

      <el-main>
        <el-form :model="setCourse"
                 ref="loginForm"
                 class="login_container"
                 label-position="left"
                 label-width="0px"
                 v-loading="loading">
          <h3 class="login_title">分配课程</h3>
          <el-form-item prop="username">
            <el-input type="text"
                      v-model="setCourse.ID"
                      auto-complete="off"
                      class="text-input"
                      clearable="true"
                      placeholder="员工ID"></el-input>
          </el-form-item>
          <el-form-item prop="username">
            <el-input type="text"
                      v-model="setCourse.name"
                      auto-complete="off"
                      class="text-input"
                      clearable="true"
                      placeholder="员工名字"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input type="password"
                      v-model="setCourse.CouID"
                      auto-complete="off"
                      class="text-input"
                      clearable="true"
                      placeholder="课程ID"></el-input>
          </el-form-item>
          <el-form-item class="text-input">
            <el-button type="primary"
                       class="button_login"
                       v-on:click="login('loginForm')">提交
            </el-button>
          </el-form-item>
        </el-form>
      </el-main>

      <el-main>
        <el-form :model="tranDept"
                 ref="loginForm"
                 class="login_container"
                 label-position="left"
                 label-width="0px"
                 v-loading="loading">
          <h3 class="login_title">转部门</h3>
          <el-form-item prop="username">
            <el-input type="text"
                      v-model="tranDept.ID"
                      auto-complete="off"
                      class="text-input"
                      clearable="true"
                      placeholder="员工ID"></el-input>
          </el-form-item>
          <el-form-item prop="username">
            <el-input type="text"
                      v-model="tranDept.name"
                      auto-complete="off"
                      class="text-input"
                      clearable="true"
                      placeholder="员工名字"></el-input>
          </el-form-item>

          <el-form-item class="text-input">
            <el-button type="primary"
                       class="button_login"
                       v-on:click="login('loginForm')">查看是否符合
            </el-button>
          </el-form-item>

          <p>是否符合转部门要求：{{tranAble}}</p>

          <el-form-item prop="password">
            <el-input type="password"
                      v-model="tranDept.dept"
                      auto-complete="off"
                      class="text-input"
                      clearable="true"
                      placeholder="转到的部门"></el-input>
          </el-form-item>
          <el-form-item class="text-input">
            <el-button type="primary"
                       class="button_login"
                       v-on:click="login('loginForm')">提交
            </el-button>
          </el-form-item>
        </el-form>
      </el-main>
    </div>

    <div class="info-panel" v-if="activeName === 'fourth'">

      <div class="search-panel">

        <el-form :model="searchForm">
          <el-form-item :inline="true" class="search-form">
            <el-input id="search-input"
                      clearable
                      placeholder="请输入员工ID"
                      v-model="searchForm.ID"
                      style="height: 40px;
                        width: 400px;"></el-input>

          </el-form-item>
        </el-form>

        <el-form :model="searchForm">
          <el-form-item :inline="true" class="search-form">
            <el-input
              clearable
              placeholder="请输入查看课程"
              v-model="searchForm.couID"
              style="height: 40px;
                        width: 400px;"></el-input>
          </el-form-item>
        </el-form>

        <el-form :model="searchForm">
          <el-form-item :inline="true" class="search-form">
            <el-input
              clearable
              placeholder="输入0查看通过的，输入1查看未通过"
              v-model="searchForm.graduation"
              style="height: 40px;
                        width: 400px;"></el-input>
          </el-form-item>
        </el-form>
        <el-button id="search-btn"
                   type="primary"
                   v-on:click="getStaffGrade"
                   icon="el-icon-search">确认
        </el-button>
      </div>
      <el-main>
        <div class="book-panel b-wrapper">
          <div class="book-block">
            <el-table
              ref="gradeData"
              tooltip-effect="dark"
              height="450"
              :data="staffExam"
              style="border-radius: 5px;border: 1px solid #DCDFE6;">
              <el-table-column prop="staff_name" label="名字" width="100"></el-table-column>
              <el-table-column prop="course_name" label="课程" width="100"></el-table-column>
              <el-table-column prop="course_id" label="课程ID" width="100"></el-table-column>
              <el-table-column prop="is_endClass" label="是否结课" width="100"></el-table-column>
              <el-table-column prop="grade" label="成绩" width="100"></el-table-column>
              <el-table-column prop="graduation" label="是否通过" width="100"></el-table-column>
              <el-table-column prop="failTime" label="累计不通过次数" width="100"></el-table-column>
            </el-table>

          </div>
        </div>

      </el-main>


      <div class="search-panel">
        <el-form :model="newDept">
          <el-form-item :inline="true" class="search-form">
            <el-input
              clearable
              placeholder="输入部门名字"
              v-model="newDept.name"
              style="height: 40px;
                        width: 400px;"></el-input>
          </el-form-item>
        </el-form>

        <el-button
          type="primary"
          icon="el-icon-search">确认
        </el-button>
      </div>

      <el-main>
        <div class="book-panel b-wrapper">
          <div class="book-block">
            <el-table
              ref="gradeData"
              tooltip-effect="dark"
              height="450"

              style="border-radius: 5px;border: 1px solid #DCDFE6;">
              <el-table-column prop="name" label="课程" width="100"></el-table-column>
              <el-table-column prop="id" label="课程ID" width="100"></el-table-column>
              <el-table-column prop="course" label="教师" width="100"></el-table-column>
              <el-table-column prop="grade" label="类型" width="100"></el-table-column>
            </el-table>

          </div>
        </div>

      </el-main>
    </div>

    <div class="info-panel" v-if="activeName === 'five'">
      <el-radio-group v-model="historyTabPosition" @change="handleHistoryRadioChange">
        <el-radio-button label="转部门记录"></el-radio-button>
      </el-radio-group>

      <div class="history-panel">
        <div class="table-wrapper">
          <div class="table">

            <el-table
              v-if="historyTabPosition === '转部门记录'"
              ref="multipleTable"
              tooltip-effect="dark"
              height="450"
              style="border-radius: 5px;border: 1px solid #DCDFE6;">
              <el-table-column prop="name" label="名字" width="100">名字</el-table-column>
              <el-table-column prop="number" label="员工ID" width="100"></el-table-column>
              <el-table-column prop="operationTime" label="原部门" width="100"></el-table-column>
              <el-table-column prop="operationTime" label="当前部门" width="100"></el-table-column>
              <el-table-column prop="operationTime" label="转入时间" width="200"></el-table-column>

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
  this.getAllStaff()
    this.getAllCourse()

  },
  mounted() {
    this.setMode()
    if (this.mode === 'display') {
      this.getFinish()
    }
  },

  data() {
    return {
      tranDept: {
        dept: '',
        name: '',
        ID: '',

      },
      allStaff:[],
      allCourse:[],
      staffExam:[],
      tranAble: '',
      setCourse: {
        name: '',
        ID: '',
        CouID: ''
      },
      searchForm: {
        ID: '',
        couID: '',
        pass: '',
        graduation: '',
      },
      newDept: {
        name: ''
      },
      gradeData: [],
      historyTabPosition: "",
      applyH: '1',
      applySec: '1',
      activeName: 'first',

      name: this.$store.state.name,
      ID: this.$store.state.id,
      address: this.$store.state.address,
      email: this.$store.state.email,
      age: this.$store.state.age,
      gender: this.$store.state.gender,
      dept: this.$store.state.department,
      phone: this.$store.state.phone,
      joiningTime: this.$store.state.joiningTime,
      position: this.$store.state.position,
      page: 1,
      pageSize: 6,
      totalPage: 1,
      tableData: [],
      fit: 'none',
      background: '',
      applyform: {

        current_credit: '',
        content: '',
        money: ''
      },
      apply_fee: '请点击查看',
      mode: 'display',
    }
  },

  methods: {
    ToSetCourse() {
      this.$router.push({path: '/SetCourse'})
    },
    ToSetting() {
      this.$router.push({path: '/Setting'})
    },

    getAllStaff(){
      this.$axios({
        url: "http://localhost:19098/training/getAllStaff/" + "测试部门",
        method: "get",
      }).then(resp => {
        console.log('员工成绩: ')
        console.log(resp.data)
        this.allStaff = resp.data

      })
    },

    getAllCourse(){
      this.$axios({
        url: "http://localhost:19098/training/getAllCourse/" + "测试部门",
        method: "get",
      }).then(resp => {
        console.log('部门下所有课程: ')
        console.log(resp.data)
        this.allCourse = resp.data
      })
    },

    getStaffGrade(){
      this.$axios({
        url: "http://localhost:19098/training/getStaffGrade/" + this.searchForm.ID+"/"+this.searchForm.couID,
        method: "get",
      }).then(resp => {
        console.log('员工的所有成绩: ')
        console.log(resp.data)
        this.staffExam = resp.data
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
