<template>
  <el-card class="box-card">
    <!-- 面包屑 -->
    <!-- 首页/用户管理/用户列表 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
      <el-breadcrumb-item>用户列表</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 搜索 -->
    <el-row class="searchRow">
      <el-col>
        <el-input
          @clear="loadUserList()"
          clearable
          placeholder="请输入内容"
          v-model="query"
          class="inputSearch"
        >
          <el-button @click="searchUser()" slot="append" icon="el-icon-search"></el-button>
        </el-input>
        <el-button type="success" @click="showAddUserDia()">添加用户</el-button>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <el-table
    height="400px" 
    :data="userList" 
    style="width: 100%">
      <el-table-column fixed type="index" label="#" width="60"></el-table-column>
      <el-table-column prop="name" label="姓名" width="80"></el-table-column>
      <el-table-column prop="age" label="年龄" ></el-table-column>
      <el-table-column prop="email" label="邮箱"></el-table-column>
      <el-table-column label="用户状态">
        <template slot-scope="scope">
          <el-switch 
          v-model="scope.row.mg_state" 
          active-color="#13ce66" 
          inactive-color="#ff4949"
          @change="changMgState(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column prop="address" label="操作">
        <template slot-scope="scope">
          <el-row>
            <el-button 
            size="mini" 
            plain 
            type="primary" 
            icon="el-icon-edit" 
            @click="showEditUserDia(scope.row)"
            circle></el-button>
            <el-button
              size="mini"
              plain
              type="danger"
              icon="el-icon-delete"
              circle
              @click="showDeleUserMsgBox( )"
            ></el-button>
            <el-button 
            size="mini" 
            plain 
            type="info" 
            icon="el-icon-check"
            @click="showSetUserRoleDia(scope.row)" 
            circle></el-button>
          </el-row>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pagenum"
      :page-sizes="[2, 4, 6, 8]"
      :page-size="6"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
    ></el-pagination>
    <!-- 添加用户的对话框 -->
    <el-dialog title="添加用户" :visible.sync="dialogFormVisibleAdd">
      <el-form :model="form">
        <el-form-item label="用户名" label-width="100px">
          <el-input v-model="form.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" label-width="100px">
          <el-input type ="password" v-model="form.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="年龄" label-width="100px">
          <el-input v-model="form.age" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" label-width="100px">
          <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleAdd = false">取 消</el-button>
        <el-button type="primary" @click="addUser()">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 编辑用户的对话框 -->
    <el-dialog title="编辑用户" :visible.sync="dialogFormVisibleEdit">
      <el-form :model="form">
        <el-form-item  label="用户名" label-width="100px">
          <el-input disabled v-model="form.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="年龄" label-width="100px">
          <el-input v-model="form.age" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" label-width="100px">
          <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleEdit = false">取 消</el-button>
        <el-button type="primary" @click="editUser()">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 分配角色的对话框 -->
    <el-dialog title="分配角色" :visible.sync="dialogFormVisibleRole">
  <el-form :model="form">
    <el-form-item label="用户名" label-width="100px">
    {{currUsername}}
    </el-form-item>
    <el-form-item label="角 色" label-width="100px">
      <el-select v-model="currRoleId">
        <el-option label="请选择" :value="-1"></el-option> 
         <el-option label="user" :value="2"></el-option> 
          <el-option label="admin" :value="3"></el-option> 
      </el-select>
    </el-form-item>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="dialogFormVisibleRole = false">取 消</el-button>
    <el-button type="primary" @click="setRole()">确 定</el-button>
  </div>
</el-dialog>

  </el-card>
</template>

<script>
export default {
  data() {
    return {
      query: "",
      mg_state: "true",
      //表格绑定的数据
      userList: [],
      total: 20,
      pagenum: 1,
      pagesize: 2,
      //对话框是否可见的属性
       dialogFormVisibleAdd: false,
       dialogFormVisibleEdit: false,
       dialogFormVisibleRole:false,
      //添加用户的表单数据
      form: {
        username: "",
        age: "",
        email: "",
        password: ""
      },
      currRoleId:-1,
      currUsername:'',
      currUserId:-1
    }
  },
  created() {
    this.getUserList();
  },
  methods: {
    //设置角色 
    async setRole(){
      const res = await this.$http.put(`users/${this.currUserId}/role`,{
        roleid:this.currRoleId
      })
      console.log(res)

    },
     //分配角色 打开对话框
    async  showSetUserRoleDia(user){
      this.currUserId = user.id
       this.currUsername = user.username
       this.dialogFormVisibleRole = true
       //根据id获取当前用户信息
       const res = await this.$http.get(`users/getUserById/${user.id}`)
       //console.log(res)
      const  currRoleId = res.data.data.role
      console.log(currRoleId)

     },
     //改变用户状态
     async changMgState(user){
        const res = await this.$http.put(`users/${user.id}/state/${user.mg_state}`)
        console.log(user.mg_state)
        console.log(res)
    },
      //编辑用户发送请求
     async editUser(){
       const res = await this.$http.put(`users/${this.form.id}`,this.form)
      // console.log(res)
      //关闭对话框
      this.dialogFormVisibleEdit = false
      //消息提示
        this.$message({
              type: "success",
              message: res.data.msg
            });
      //数据更新
      this.getUserList()

      },
      //编辑用户
      showEditUserDia(User){
          console.log(User)
          //把值赋给表单
          this.form = User
          this.dialogFormVisibleEdit = true
      },
    //删除用户 打开消息盒子
    showDeleUserMsgBox(userId) {
      this.$confirm("此操作将删除用户, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(async () => {
          const res = await this.$http.delete(`users/${userId}`);
          console.log(res);
          if (res.data.code === 200) {
              //更新视图
              this.getUserList()
              //提示成功
            this.$message({
              type: "success",
              message: res.data.msg
            });
          }
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    //添加用户的方法请求
    async addUser() {

      this.dialogFormVisibleAdd = false;
      const res = await this.$http.post(`/users/`, this.form);
      console.log(res);
      const { code, msg } = res.data;
      if (code === 200) {
        //提示成功
        this.$message.success(msg)
        //更新视图
        this.getUserList();
        //清空文本框
        this.form = {};
      } else {
        this.$message.warning(msg);
      }
    },
    //展示添加用户的对话框
    showAddUserDia() {
      this.form = {},
      this.dialogFormVisibleAdd = true;
    },
    //清空搜索框 重新返回数据
    loadUserList() {
      this.getUserList();
    },
    //搜索用户
    async searchUser() {
      const res = await this.$http.get(`/users/${this.query}`);
      //console.log(res.data)
      const { msg, code, data } = res.data;
      if (code === 200) {
        //给表格数据赋值
        this.userList = data;
        //提示
        this.$message.success(msg);
        this.pagenum = 1;
      } else {
        //失败提示
        this.$message.warning(msg);
      }
    },

    //分页方法
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      (this.pagesize = val), this.getUserList();
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      (this.pagenum = val), this.getUserList();
    },
    async getUserList() {
      //query 查询参数 可以为空
      //pagenum 当前页码 不能为空
      //pagesize 每页显示的数 不能为空
      //需要授权的api 必须在请求头使用Authorization 提供token值
      //const AUTH_TOKEN = localStorage.getItem("token")
      //console.log(AUTH_TOKEN);

      //为啥我的token写不进去？？？
      //this.$http.defaults.headers.common["Authorization"] = AUTH_TOKEN

      const res = await this.$http.get(
        `/users/?query=${this.query}&pagesnum=${this.pagenum}$pagesize=${
          this.pagesize
        }`
      )
      console.log(res);
      const { msg, code, data } = res.data;
      if (code === 200) {
        //给表格数据赋值
        this.userList = data;
        //提示
       // this.$message.success(msg);
        this.pagenum = 1;
      } else {
        //失败提示
        this.$message.warning(msg);
      }
    }
  }
};
</script>

<style>
.bod-crad {
  height: 100%;
}
.inputSearch {
  width: 300px;
}
.searchRow {
  margin-top: 30px;
}
</style>
