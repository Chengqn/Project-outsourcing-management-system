<template>
  <div class="login-wrap">
    <el-form
      class="login-form"
      label-position="top"
      label-width="80px"
      status-icon
      ref="formData"
      :model="formData"
    >
      <el-col :span="16" :offset="4">
        <h2 class="title">用户登陆</h2>
        <el-form-item label="用户名">
          <el-input v-model="formData.username"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input type="password" v-model="formData.password"></el-input>
        </el-form-item>
        <el-button @click.prevent="handleLogin()" class="login-btn" type="primary">登陆</el-button>
        <el-button id="reg-btn" @click.prevent="handleRegis()">注册</el-button>
      </el-col>
    </el-form>
    <!-- 注册用户的对话框 -->
    <el-dialog title="注册用户" :visible.sync="dialogFormVisibleEdit">
      <el-form :model="form">
        <el-form-item label="用户名称" label-width="100px">
          <el-input v-model="form.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="用户密码" label-width="100px" class="pwd">
          <el-input type="password" v-model="form.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="年龄" label-width="100px" class="age">
          <el-input v-model="form.age" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电子邮箱" label-width="100px" class="email">
          <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="用户类型" label-width="100px" class="role">
        <el-select v-model="form.role" placeholder="请选择用户类型">
          <el-option label="发包方" value="puber"></el-option>
          <el-option label="承包方" value="admin"></el-option>
        </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleEdit = false">取 消</el-button>
        <el-button type="primary" @click="RegisUser()">注 册</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      formData: {
        username: "",
        password: ""
      },
      dialogFormVisibleEdit: false,
      form: {
        username: "",
        password: "",
        age: "",
        email: "",
        role: ""
      }
    };
  },
  methods: {
    //注册用户
   async RegisUser(){
        this.$http.post("/users/registerUser", this.form).then(res => {
        console.log(res);
        const { code, msg, data } = res.data;
        //不成功 提示消息
        if (code === 200) {
           this.dialogFormVisibleEdit = false
          //提示成功
          this.$message.success(msg);
          
        } else {
          //否则提示失败
          this.$message.error(msg);
        }
      });


    },
    //注册逻辑
    handleRegis() {
      this.dialogFormVisibleEdit = true;
    },
    //登陆请求
    handleLogin() {
      this.$http.post("/users/login", this.formData).then(res => {
        console.log(res);
        const { code, msg, data } = res.data;
        //不成功 提示消息
        if (code === 200) {
          //保存token数据
          localStorage.setItem("token", data);
          //console.log(data)
          //跳转到权限鉴别页面
          this.$router.push({ path: "/testRole" });
          //提示成功
          this.$message.success(msg);
        } else {
          //否则提示失败
          this.$message.error(msg);
        }
      });
    }
  }
};
</script>


<style>
.title {
  margin-top: 0px;
}
.login-wrap {
  height: 100%;
  background-color: #324152;
  display: flex;
  justify-content: center;
  align-items: center;
}
.login-wrap .login-form {
  width: 400px;
  background-color: #fff;
  border-radius: 5px;
  padding: 30px;
}
.login-wrap .login-btn {
  width: 100%;
  margin: 40px 0 15px 0;
}
#reg-btn {
  width: 100%;
  margin-left: 0;
  margin-bottom: 10px;
}
.el-form--label-top .el-form-item__label {
  padding: 0;
}
.login-wrap .el-form-item {
  margin-bottom: 0px;
}
.pwd{
  margin-top: 30px
}
.age{
   margin-top: 30px
}
.email{
   margin-top: 30px
}
.role{
   margin-top: 30px
}
</style>
