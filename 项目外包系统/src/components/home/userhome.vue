<template>
  <el-container class="container">
    <el-header class="header">
      <el-row>
        <el-col :span="18" :offset="4" class="middle">
          <h3>项目外包开发后台</h3>
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
          <el-submenu index="4">
            <template slot="title">
              <i class="el-icon-s-data"></i>
              <span>项目管理</span>
            </template>
             <el-menu-item index="usermission">
              <i class="el-icon-user"></i>
              <span>我的任务</span>
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


