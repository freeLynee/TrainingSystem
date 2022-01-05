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
        <div class="user-info-column">员工ID:</div>
        <div class="user-info-column">名字:</div>
        <div class="user-info-column">职位:</div>
      </div>
    </div>

    <div class="nav-panel">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="基本信息" name="first">基本信息</el-tab-pane>
        <el-tab-pane label="课程信息管理" name="second"></el-tab-pane>
        <el-tab-pane label="分配转入" name="third"></el-tab-pane>
        <el-tab-pane label="员工信息" name="fourth"></el-tab-pane>
        <el-tab-pane label="转部门情况" name="five"></el-tab-pane>
      </el-tabs>
    </div>

    <div class="info-panel" v-if="activeName === 'first'" id="book-panel">
      <div class="user-info">
        <div class="user-info-column">员工ID: 19302227879</div>
        <div class="user-info-column">名字: 卡丽娜</div>
        <div class="user-info-column">性别: 女</div>
        <div class="user-info-column">年龄: 32</div>
        <div class="user-info-column">入职时间: 2016-09-30</div>
        <div class="user-info-column">住址: 上海市杨浦区220号</div>
      </div>
    </div>


    <div class="info-panel" v-if="activeName === 'second'" id="history-panel">
      <div class="search-panel">

        <el-form :model="searchForm">
          <el-form-item :inline="true" class="search-form">
            <el-input id="search-input"
                      clearable
                      placeholder="请输入员工ID"
                      v-model="searchForm.id"
                      style="height: 40px;
                        width: 400px;"></el-input>

          </el-form-item>
        </el-form>

        <el-form :model="searchForm">
          <el-form-item :inline="true" class="search-form">
            <el-input
              clearable
              placeholder="请输入查看课程"
              v-model="searchForm.id"
              style="height: 40px;
                        width: 400px;"></el-input>
          </el-form-item>
        </el-form>

        <el-form :model="searchForm">
          <el-form-item :inline="true" class="search-form">
            <el-input
              clearable
              placeholder="输入0查看通过的，输入1查看未通过"
              v-model="searchForm.pass"
              style="height: 40px;
                        width: 400px;"></el-input>
          </el-form-item>
        </el-form>

        <el-form :model="searchForm">
          <el-form-item :inline="true" class="search-form">
            <el-input
              clearable
              placeholder="输入0查看通过的，输入1查看未通过"
              v-model="searchForm.type"
              style="height: 40px;
                        width: 400px;"></el-input>
          </el-form-item>
        </el-form>
        <el-button id="search-btn"
                   type="primary"
                   v-on:click=""
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
              <el-table-column prop="name" label="名字" width="100"></el-table-column>
              <el-table-column prop="id" label="员工ID" width="100"></el-table-column>
              <el-table-column prop="course" label="课程" width="100"></el-table-column>
              <el-table-column prop="grade" label="成绩" width="100"></el-table-column>
              <el-table-column prop="grade" label="是否通过" width="100"></el-table-column>
              <el-table-column prop="grade" label="未通过次数" width="100"></el-table-column>
            </el-table>

          </div>
        </div>

      </el-main>


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
            <el-input
              clearable
              placeholder="请输入员工ID"
              v-model="searchForm.id"
              style="height: 40px;
                        width: 400px;"></el-input>

          </el-form-item>
        </el-form>
        <el-button
          type="primary"
          v-on:click=""
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
              <el-table-column prop="name" label="名字" width="100"></el-table-column>
              <el-table-column prop="id" label="员工ID" width="100"></el-table-column>
              <el-table-column prop="course" label="课程" width="100"></el-table-column>
              <el-table-column prop="grade" label="成绩" width="100"></el-table-column>
              <el-table-column prop="grade" label="是否通过" width="100"></el-table-column>
              <el-table-column prop="grade" label="未通过次数" width="100"></el-table-column>
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
    // this.checkToken()
    // this.findAllLendCopy()
    // this.getAppointHistory()
    // this.get_credit()
    // this.getLevel()
    // this.getNotDone()
    // this.getNotPay()
    // this.getFinish()
    // this.setPage()

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
      tranAble: '',
      setCourse: {
        name: '',
        ID: '',
        CouID: ''
      },
      searchForm: {
        name: '',
        ID: '',
        couName: '',
        pass: '',
        type: ''
      },
      newDept: {
        name: ''
      },
      gradeData: [],

      historyTabPosition: "",

      name: this.$store.state.username,
      email: this.$store.state.email,
      role: this.$store.state.role,
      userId: this.$store.state.userId,

    }
  },

  methods: {
    ToSetCourse() {
      this.$router.push({path: '/SetCourse'})
    },
    ToSetting() {
      this.$router.push({path: '/Setting'})
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
