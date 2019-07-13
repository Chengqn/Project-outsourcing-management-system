<template>
  <el-card class="bod-card">
    <!-- 面包屑 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>首页</el-breadcrumb-item>
      <el-breadcrumb-item>项目管理</el-breadcrumb-item>
      <el-breadcrumb-item>开发任务</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 搜索 -->
    <el-row class="searchRow">
      <el-col>
        <el-input
          @clear="loadMissionList()"
          clearable
          placeholder="请输入内容"
          v-model="query"
          class="inputSearch"
        >
          <el-button @click="searchMission()" slot="append" icon="el-icon-search"></el-button>
        </el-input>
        <el-button type="success" @click="showAddMissionDia()">添加开发任务</el-button>
      </el-col>
    </el-row>
    <!-- 表格 -->
    <el-table :data="missionList" style="width: 100%" height="400">
      <el-table-column fixed type="index" label="#" width="60"></el-table-column>
      <el-table-column prop="project" label="从属项目" width="180"></el-table-column>
      <el-table-column prop="mname" label="任务名称" width="180"></el-table-column>
      <el-table-column prop="mcontent" label="开发内容" width="180"></el-table-column>
      <el-table-column prop="expectpersondays" label="预计人天" width="100"></el-table-column>
      <el-table-column prop="actualpersondays" label="实际人天" width="100"></el-table-column>
      <el-table-column prop="starttime" label="开始时间" width="180"></el-table-column>
      <el-table-column prop="finishtime" label="结束时间" width="180"></el-table-column>
      <el-table-column prop="mstatus" label="任务状态" width="100"></el-table-column>
      <el-table-column prop="contractor" label="承包商" width="100"></el-table-column>
      <el-table-column prop="address" label="操作" width="150" fixed="right">
        <template slot-scope="scope">
          <el-row>
            <el-button
              size="mini"
              plain
              type="primary"
              icon="el-icon-edit"
              @click="showEditMissionDia(scope.row)"
              circle
            ></el-button>
            <el-button
              size="mini"
              plain
              type="danger"
              icon="el-icon-delete"
              @click="showDeleteMissionDia(scope.row.id)"
              circle
            ></el-button>
            <el-button
              size="mini"
              plain
              type="info"
              icon="el-icon-check"
              @click="showSetProjectDia(scope.row)"
              circle
            ></el-button>
          </el-row>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加开发任务的对话框 -->
    <el-dialog title="添加开发任务" :visible.sync="dialogFormVisibleAdd">
      <el-form :model="Mission">
        <el-form-item label="从属项目" label-width="100px">
          <el-input v-model="Mission.project" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="任务名称" label-width="100px">
          <el-input v-model="Mission.mname" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="开发内容" label-width="100px">
          <el-input v-model="Mission.mcontent" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="预计人天" label-width="100px">
          <el-input v-model="Mission.expectpersondays" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="实际人天" label-width="100px">
          <el-input v-model="Mission.actualpersondays" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="开始时间" label-width="100px">
          <template>
            <el-date-picker
              v-model="Mission.starttime"
              type="datetime"
              value-format="yyyy-MM-dd HH:mm:ss"
              format="yyyy-MM-dd HH:mm:ss"
              placeholder="选择日期时间"
            ></el-date-picker>
          </template>
        </el-form-item>
        <el-form-item label="结束时间" label-width="100px">
          <template>
            <el-date-picker
              v-model="Mission.finishtime"
              type="datetime"
              value-format="yyyy-MM-dd HH:mm:ss"
              format="yyyy-MM-dd HH:mm:ss"
              placeholder="选择日期时间"
            ></el-date-picker>
          </template>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleAdd = false">取 消</el-button>
        <el-button type="primary" @click="addMission()">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 查看开发任务的对话框 -->
    <el-dialog title="查看开发任务" :visible.sync="dialogFormVisibleEdit">
      <el-form :model="form">
        <el-form-item label="项目名称" label-width="100px">
          <el-input disabled v-model="form.project" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="任务名称" label-width="100px">
          <el-input disabled v-model="form.mname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="开发内容" label-width="100px">
          <el-input disabled v-model="form.mcontent" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="预计人天" label-width="100px">
          <el-input disabled v-model="form.expectpersondays" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="实际人天" label-width="100px">
          <el-input disabled v-model="form.actualpersondays" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="开始时间" label-width="100px">
          <el-input disabled v-model="form.starttime" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="结束时间" label-width="100px">
          <el-input disabled v-model="form.finishtime" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="承包商" label-width="100px">
          <el-input disabled v-model="form.contractor" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleEdit = false">取 消</el-button>
        <el-button type="primary" @click="dialogFormVisibleEdit = false">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 分配任务的对话框 -->
    <el-dialog title="分配开发任务" :visible.sync="dialogFormVisibleAssign">
      <el-form :model="Assignform">
        <el-form-item label="项目名称" label-width="100px">
          <el-input disabled v-model="Assignform.project" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="任务名称" label-width="100px">
          <el-input disabled v-model="Assignform.mname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="开发内容" label-width="100px">
          <el-input disabled v-model="Assignform.mcontent" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="预计人天" label-width="100px">
          <el-input disabled v-model="Assignform.expectpersondays" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="实际人天" label-width="100px">
          <el-input disabled v-model="Assignform.actualpersondays" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="开始时间" label-width="100px">
          <el-input disabled v-model="Assignform.starttime" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="结束时间" label-width="100px">
          <el-input disabled v-model="Assignform.finishtime" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="承包商" label-width="100px">
          <el-input disabled v-model="Assignform.contractor" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="开发人员" label-width="100px">
          <el-select v-model="Assignform.role" placeholder="请选择开发人员">
            <el-option
              v-for="item in options"
              :key="item.id"
              :label="item.username"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleAssign = false">取 消</el-button>
        <el-button type="primary" @click="RegisUser()">注册开发人员</el-button>
        <el-button type="primary" @click="AssignMission()">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 注册用户的对话框 -->
    <el-dialog title="注册开发人员" :visible.sync="dialogFormVisibleRegis">
      <el-form :model="Regisform">
        <el-form-item label="用户名称" label-width="100px">
          <el-input v-model="Regisform.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="用户密码" label-width="100px" class="pwd">
          <el-input type="password" v-model="Regisform.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="年龄" label-width="100px" class="age">
          <el-input v-model="Regisform.age" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电子邮箱" label-width="100px" class="email">
          <el-input v-model="Regisform.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="用户类型" label-width="100px" class="role">
          <el-select v-model="Regisform.role" placeholder="请选择用户类型">
            <el-option label="开发人员" value="user"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="ClearRegis()">取 消</el-button>
        <el-button type="primary" @click="RegisterUser()">注 册</el-button>
      </div>
    </el-dialog>
  </el-card>
</template>



<script>
export default {
  data() {
    return {
      missionList: [],
      query: "",
      //添加开发任务的对话框是否可见
      dialogFormVisibleAdd: false,
      Mission: {
        mname: "",
        Project: "",
        mcontent: "",
        expectpersondays: "",
        actualpersondays: "",
        starttime: "",
        finishtime: ""
      },
      //查看开发任务的dialog box是否可见
      dialogFormVisibleEdit: false,
      dialogFormVisibleAssign: false,
      dialogFormVisibleRegis: false,
      form: {
        mname: "",
        Project: "",
        mcontent: "",
        expectpersondays: "",
        actualpersondays: "",
        starttime: "",
        finishtime: ""
      },
      options:"",
      Regisform:{
        username:"",
        password:"",
        age:"",
        email:"",
        role:""
      },
      Assignform:{
        mname: "",
        Project: "",
        mcontent: "",
        expectpersondays: "",
        actualpersondays: "",
        starttime: "",
        finishtime: "",
        contractor:"",
        role:""
      },
      
    };
  },

  created() {
    this.getmissionList();
    this.getUserList()
  },

  methods: {
    //分配任务
     async AssignMission() {
      this.$http.post("/mission/assignMission", this.Assignform).then(res => {
        console.log(res);
        const { code, msg, data } = res.data
        //不成功 提示消息
        if (code === 200) {
          this.dialogFormVisibleAssign = false
          //提示成功
          this.$message.success(msg)
        } else {
          //否则提示失败
          this.$message.error(msg);
        }
      });
    },
    //获取下拉框数据
    async getUserList(){
        this.$http.get("/users/getUsers").then(res => {
        console.log(res)
        const { code, msg, data } = res.data
        this.options = data

      });
    },
    //注册用户
    async RegisterUser() {
      this.$http.post("/users/registerUser", this.Regisform).then(res => {
        console.log(res);
        const { code, msg, data } = res.data
        //不成功 提示消息
        if (code === 200) {
          this.dialogFormVisibleRegis = false
          //提示成功
          this.$message.success(msg)
          this.getUserList()
        } else {
          //否则提示失败
          this.$message.error(msg);
        }
      });
    },

    ClearRegis() {
      this.dialogFormVisibleRegis = false;
      this.Regisform = {};
    },

    //注册开发人员
    RegisUser() {
      this.dialogFormVisibleRegis = true;
    },
    //分配开发任务的操作
    showSetProjectDia(Mission) {
      this.dialogFormVisibleAssign = true;
      this.Assignform = Mission;
    },
    //根据id删除开发任务信息
    showDeleteMissionDia(MissionId) {
      this.$confirm("此操作将删除开发任务, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(async () => {
          const res = await this.$http.delete(
            `mission/deleteMission/${MissionId}`
          );
          console.log(res);
          if (res.data.code === 200) {
            //更新视图
            this.getmissionList();
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
    //查看开发任务信息
    showEditMissionDia(Mission) {
      //把值赋给表单
      this.form = Mission;
      this.dialogFormVisibleEdit = true;
    },

    //展示添加开发任务的对话框
    showAddMissionDia() {
      (this.form = {}), (this.dialogFormVisibleAdd = true);
    },
    //添加开发任务的方法请求
    async addMission() {
      this.dialogFormVisibleAdd = false;
      const res = await this.$http.post(`/mission/`, this.Mission);
      console.log(res);
      const { code, msg } = res.data;
      if (code === 200) {
        //提示成功
        this.$message.success(msg);
        //刷新视图获取开发任务信息
        this.getmissionList();
        //清空文本框
        this.Mission = {};
      } else {
        this.$message.warning(msg);
      }
    },

    //实现对从属项目的开发任务模糊查询
    async searchMission() {
      const res = await this.$http.get(
        `/mission/getMissionByProject/${this.query}`
      );
      //console.log(res.data)
      const { msg, code, data } = res.data;
      if (code === 200) {
        //给表格数据赋值
        this.missionList = data;
        //提示
        this.$message.success(msg);
      } else {
        //失败提示
        this.$message.warning(msg);
      }
    },

    //清空搜索框加载表格数据
    loadMissionList() {
      this.getmissionList();
    },
    //获取开发任务信息
    async getmissionList() {
      const res = await this.$http.get(`mission/getMission`);
      console.log(res);
      const { code, data, msg } = res.data;
      if (code === 200) {
        this.missionList = data;
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
