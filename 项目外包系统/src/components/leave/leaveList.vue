<template>
  <el-card class="bod-card">
    <!-- 面包屑 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>首页</el-breadcrumb-item>
      <el-breadcrumb-item>请假管理</el-breadcrumb-item>
      <el-breadcrumb-item>请假列表</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 搜索 -->
    <el-row class="searchRow">
      <el-col>
        <el-input
          @clear="loadLeaveList()"
          clearable
          placeholder="请输入内容"
          v-model="query"
          class="inputSearch"
        >
          <el-button @click="searchLeave()" slot="append" icon="el-icon-search"></el-button>
        </el-input>
        <el-button type="success" @click="showAddLeaveDia()">添加请假</el-button>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <el-table 
    :data="leaveList" 
    style="width: 100%"
    height="400">
      <el-table-column
      fixed 
      type="index" 
      label="#" 
      width="60"></el-table-column>
      <el-table-column prop="applicant" label="请假人" ></el-table-column>
      <el-table-column prop="starttime" label="开始时间" width="180" ></el-table-column>
      <el-table-column prop="finishtime" label="结束时间" width="180"></el-table-column>
      <el-table-column prop="offreason" label="请假理由"></el-table-column>
      <el-table-column prop="offtype" label="请假类别"></el-table-column>
      <el-table-column prop="offstatus" label="请假状态"></el-table-column>
      <el-table-column prop="totaldays" label="请假天数"></el-table-column>
      <el-table-column prop="approver" label="审批人"></el-table-column>
      <el-table-column prop="comments" label="批语" ></el-table-column>
      <el-table-column prop="address" label="审核"  fixed="right">
        <template slot-scope="scope">
          <el-row>
            <el-button
              size="mini"
              plain
              type="primary"
              icon="el-icon-edit"
              @click="showEditLeaveDia(scope.row)"
              circle
            ></el-button>
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
    <!-- 添加请假单的对话框 -->
    <el-dialog title="添加请假单" :visible.sync="dialogFormVisibleAdd">
      <el-form :model="form">
        <el-form-item label="请假人" label-width="100px">
          <el-input v-model="form.applicant" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="开始时间" label-width="100px">
          <!-- <el-input  v-model="form.starttime" autocomplete="off"></el-input> -->
          <template>
            <el-date-picker 
            v-model="form.starttime" 
            type="datetime" 
            value-format="yyyy-MM-dd HH:mm:ss"
            format="yyyy-MM-dd HH:mm:ss"
            placeholder="选择日期时间"></el-date-picker>
          </template>
        </el-form-item>
        <el-form-item label="结束时间" label-width="100px">
          <!-- <el-input v-model="form.finishtime" autocomplete="off"></el-input> -->
          <template>
            <el-date-picker 
            v-model="form.finishtime" 
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            format="yyyy-MM-dd HH:mm:ss" 
            placeholder="选择日期时间"></el-date-picker>
          </template>
        </el-form-item>
        <el-form-item label="请假理由" label-width="100px">
          <el-input v-model="form.offreason" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="请假类别" label-width="100px">
          <el-input v-model="form.offtype" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="请假天数" label-width="100px">
          <el-input v-model="form.totaldays" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleAdd = false">取 消</el-button>
        <el-button type="primary" @click="addLeave()">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 审核请假单的对话框 -->
    <el-dialog title="审核请假单" :visible.sync="dialogFormVisibleEdit">
      <el-form :model="form">
        <el-form-item  label="请假人" label-width="100px">
          <el-input disabled v-model="form.applicant" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="开始时间" label-width="100px">
          <el-input  disabled v-model="form.starttime" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="结束时间" label-width="100px">
          <el-input  disabled v-model="form.finishtime" autocomplete="off"></el-input>
        </el-form-item>
          <el-form-item label="请假类别" label-width="100px">
          <el-input  disabled v-model="form.offtype" autocomplete="off"></el-input>
        </el-form-item>
          <el-form-item label="请假理由" label-width="100px">
          <el-input  disabled v-model="form.offreason" autocomplete="off"></el-input>
        </el-form-item>
          <el-form-item label="请假天数" label-width="100px">
          <el-input   disabled v-model="form.totaldays" autocomplete="off"></el-input>
        </el-form-item>
          <el-form-item label="请假状态" label-width="100px">
          <el-input v-model="form.offstatus" autocomplete="off"></el-input>
        </el-form-item>
          <el-form-item label="批语" label-width="100px">
          <el-input v-model="form.comments" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleEdit = false">取 消</el-button>
        <el-button type="primary" @click="editLeave()">确 定</el-button>
      </div>
    </el-dialog>
  </el-card>
</template>


<script>
export default {
  data() {
    return {
      query: "",
      total: 20,
      pagenum: 1,
      pagesize: 2,
      leaveList: [],
      total: 20,
      dialogFormVisibleAdd: false,
      dialogFormVisibleEdit:false,
      form: {
        applicant: "",
        starttime: "",
        finishtime: "",
        offreason: "",
        offtype: "",
        totaldays: "",
        comments:""
      },
    };
  },
  created() {
    this.getLeaveList();
  },
  methods: {
      //审核请假单 打开视图
      showEditLeaveDia(Leave){
          this.form = Leave
          this.dialogFormVisibleEdit = true
      },
     //审核请假单 发送请求
     async editLeave(){
       const res = await this.$http.put(`leaves/${this.form.id}`,this.form)
      // console.log(res)
      //关闭对话框
      this.dialogFormVisibleEdit = false
      //消息提示
        this.$message({
              type: "success",
              message: res.data.msg
            });
      //数据更新
      this.getLeaveList()

      },
      //自动获取请假时间
  computed: {
    duration() {
      var sdate = new Date(this.form.starttime);
      var now = new Date(this.form.finishtime);
      var days = now.getTime() - sdate.getTime();
      var day = parseInt(days / (1000 * 60 * 60 * 24));
      return day;
    }
  },
  watch: {
    duration(newValue, oldValue) {
      this.form.totaldays = newValue;
    }
  },
    //发送添加请假单请求
    async addLeave() {
      this.dialogFormVisibleAdd = false;
      const res = await this.$http.post(`/leaves/`, this.form);
      console.log(res);
      const { code, msg } = res.data;
      if (code === 200) {
        //提示成功
        this.$message.success(msg);
        //更新视图
        this.getUserList();
        //清空文本框
        this.form = {};
      } else {
        this.$message.warning(msg);
      }
    },
    //展示添加请假的对话框
    showAddLeaveDia() {
        //清空form表单
      this.form = {},
      this.dialogFormVisibleAdd = true
    },
    //清空之后加载请假单信息
    loadLeaveList() {
      this.getLeaveList();
    },
    //搜索请假消息
    async searchLeave() {
      const res = await this.$http.get(`/leaves/findbyname/${this.query}`);
      //console.log(res.data)
      const { msg, code, data } = res.data;
      if (code === 200) {
        //给表格数据赋值
        this.leaveList = data;
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
    //获取请假数据
    async getLeaveList() {
      const AUTH_TOKEN = localStorage.getItem("token");
      //console.log(AUTH_TOKEN);

      this.$http.defaults.headers.common["Authorization"] = AUTH_TOKEN;
      const res = await this.$http.get(
        `leaves/?query=${this.query}&pagesnum=${this.pagenum}$pagesize=${
          this.pagesize
        }`
      );
      console.log(res);
      const { code, data, msg } = res.data;
      if (code === 200) {
        this.leaveList = data;
        this.pagenum = 1;
      } else {
        this.$message.warning(msg);
      }
    }
  }
};
</script>

<style>
.bod-card {
  height: 100%;
}
.searchRow {
  margin-top: 30px;
}
.inputSearch {
  width: 300px;
}
</style>
