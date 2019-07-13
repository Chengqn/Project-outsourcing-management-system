<template>
  <el-container class="container">
    <el-header class="header">
      <el-row>
        <el-col :span="18" :offset="4" class="middle">
          <h3>项目外包管理后台</h3>
        </el-col>
        <el-col :span="2">
          <div class="grid-content bg-purple">
             <el-button 
             class="loginout"
              @click.prevent="handleSignout" 
              size="small"
              type="info" plain>退出</el-button>
            <!-- <a class="loginout" @click.prevent="handleSignout" href="#">退出</a> -->
          </div>
        </el-col>
      </el-row>
    </el-header>
    <el-container>
      <el-aside class="aside" width="200px">
          <!-- router:true 开启路由模式 -->
        <el-menu 
        :router="true"
        :unique-opened="true">
          <el-submenu index="">
            <template slot="title">
              <i class="el-icon-user"></i>
              <span>用户管理</span>
            </template>

            <el-menu-item index="users">
              <i class="el-icon-user-solid"></i>
              <span>用户列表</span>
            </el-menu-item>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-unlock"></i>
              <span>权限管理</span>
            </template>

            <el-menu-item index="roles">
              <i class="el-icon-info"></i>
              <span>角色列表</span>
            </el-menu-item>
            <el-menu-item index="rights">
              <i class="el-icon-warning"></i>
              <span>权限列表</span>
            </el-menu-item>
          </el-submenu>
          <el-submenu index="3">
            <template slot="title">
              <i class="el-icon-s-promotion"></i>
              <span>请假管理</span>
            </template>

            <el-menu-item index="leaves">
              <i class="el-icon-s-check"></i>
              <span>请假列表</span>
            </el-menu-item>
             <el-menu-item index="applyForLeaves">
              <i class="el-icon-folder-add"></i>
              <span>申请请假</span>
            </el-menu-item>
          </el-submenu>
          <el-submenu index="4">
            <template slot="title">
              <i class="el-icon-s-data"></i>
              <span>项目管理</span>
            </template>
             <!-- <el-menu-item index="pubproject">
              <i class="el-icon-folder-add"></i>
              <span>发布项目</span>
            </el-menu-item>  -->
            <el-menu-item index="project">
              <i class="el-icon-folder-opened"></i>
              <span>项目列表</span>
            </el-menu-item> 
             <el-menu-item index="myproject">
              <i class="el-icon-user-solid"></i>
              <span>我的项目</span>
            </el-menu-item> 
             <el-menu-item index="mission">
              <i class="el-icon-edit-outline"></i>
              <span>开发任务</span>
            </el-menu-item> 
             <el-menu-item index="acceptedProject">
              <i class="el-icon-folder-checked"></i>
              <span>已验收项目</span>
            </el-menu-item> 
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-main class="main">
          <router-view></router-view>
          </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
    beforeCreate(){
        //获取token
        const token = localStorage.getItem("token")
        
        if(!token){
            //不存在返回login
            this.$router.push({name:'login'})

        }
        //存在继续渲染
        
    },
    methods:{
        handleSignout(){
            //清楚token
            localStorage.clear()
            //提示
            this.$message.success('退出成功')
            //返回login
            this.$router.push({name:'login'})
        }
    }
};
</script>

<style>
.container {
  height: 100%;
}
.header {
  background-color: #b3c0d1;
}
.aside {
  background-color: #d3dce6;
}
.main {
  background-color: #e9eef3;
  height: 100%;
}
.middle {
  text-align: center;
}
.loginout {
  line-height: 40px;
  text-decoration: none;
  float: right;
}
</style>


