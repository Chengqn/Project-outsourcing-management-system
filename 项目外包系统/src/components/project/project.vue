<template>
  <el-card class="bod-card">
    <!-- 面包屑 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>首页</el-breadcrumb-item>
      <el-breadcrumb-item>项目管理</el-breadcrumb-item>
      <el-breadcrumb-item>项目列表</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 搜索 -->
    <el-row class="searchRow">
      <el-col>
        <el-input
          @clear="loadProjectList()"
          clearable
          placeholder="请输入内容"
          v-model="query"
          class="inputSearch"
        >
          <el-button @click="searchProject()" slot="append" icon="el-icon-search"></el-button>
        </el-input>
        <!-- <el-button type="success" @click="showAddLeaveDia()">添加请假</el-button> -->
      </el-col>
    </el-row>
    <!-- 表格 -->
    <el-table :data="projectList" style="width: 100%" margin-top:10px height="400">
      <el-table-column fixed type="index" label="#" width="60"></el-table-column>
      <el-table-column prop="pname" label="项目名称" width="180"></el-table-column>
      <el-table-column prop="pexplanation" label="项目说明" width="180"></el-table-column>
      <el-table-column prop="pfinishtime" label="项目期望完成时间" width="180"></el-table-column>
      <el-table-column prop="pstatus" label="项目状态"></el-table-column>
      <el-table-column prop="contractor" label="承包商"></el-table-column>
      <el-table-column prop="puber" label="项目发布者" width="100px"></el-table-column>
      <el-table-column prop="address" label="操作" fixed="right" width="150px">
        <template slot-scope="scope">
          <el-row>
            <el-button
              size="mini"
              plain
              type="primary"
              icon="el-icon-edit"
              @click="showEditProjectDia(scope.row)"
              circle
            ></el-button>
            <el-button
              size="mini"
              plain
              type="info"
              icon="el-icon-check"
              @click="showSetProjectDia(scope.row.id)"
              circle
            ></el-button>
            <el-button
              size="mini"
              plain
              type="warning"
              icon="el-icon-download"
              @click="handleDownload(scope.row)"
              circle
            ></el-button>
          </el-row>
        </template>
      </el-table-column>
    </el-table>
    <!-- 查看项目的对话框 -->
    <el-dialog title="查看项目" :visible.sync="dialogFormVisibleEdit">
      <el-form :model="form">
        <el-form-item label="项目名称" label-width="100px">
          <el-input disabled v-model="form.pname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="项目说明" label-width="100px">
          <el-input disabled v-model="form.pexplanation" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="期望完成时间" label-width="100px">
          <el-input disabled v-model="form.pfinishtime" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="项目状态" label-width="100px">
          <el-input disabled v-model="form.pstatus" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="项目完成时间" label-width="100px">
          <el-input disabled v-model="form.totalpersonaldays" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="承包商" label-width="100px">
          <el-input disabled v-model="form.contractor" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="项目发布者" label-width="100px">
          <el-input disabled v-model="form.puber" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleEdit = false">取 消</el-button>
        <el-button type="primary" @click="editProject()">接 单</el-button>
      </div>
    </el-dialog>
  </el-card>
</template>

<script>
export default {
  data() {
    return {
      projectList: [],
      query: "",
      form: {
        pname: "",
        pexplanation: "",
        pfinishtime: "",
        pstatus: "",
        totalpersonaldays: "",
        contractor: "",
        puber: ""
      },
      //对话框是否可见的属性
      dialogFormVisibleEdit: false,
      filename:""
    };
  },
  created() {
    this.getprojectList();
  },
  methods: {
    //下载处理
    async handleDownload(Project) {
      // const res = await this.$http.get(
      //   `/projects/getFileName/${ProjectId}`
      // );
      //console.log(res.data)
     // const filename = res.data
     // console.log(filename)
       const res1 = await this.$http.get(
        `/files/downloadfile/${Project.filename}`
      );
      const { msg, code, data } = res1.data;
      if (code === 200) {
        //提示
        this.$message.success(msg);
      } else {
        //失败提示
        this.$message.warning(msg);
      }
    },

    showSetProjectDia(projectId) {
      this.$confirm("此操作将承包项目, 是否继续?", "提示", {
        confirmButtonText: "确定接单",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(async () => {
          const res = await this.$http.put(`projects/${projectId}`);
          console.log(res);
          const { msg, code, data } = res.data;
          if (code === 200) {
            console.log(code);
            //更新视图
            this.getprojectList();
            //提示成功
            this.$message({
              type: "success",
              message: msg
            });
          } else if (code === 400) {
            //提示项目已有人承包
            this.$message({
              type: "warning",
              message: msg
            });
          }
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消接单"
          });
        });
    },

    //接单功能的实现
    async editProject() {
      const res = await this.$http.put(`projects/${this.form.id}`);
      // console.log(res)
      //关闭对话框
      this.dialogFormVisibleEdit = false;
      //消息提示
      const { msg, code, data } = res.data;
      if (code == 200) {
        this.$message({
          type: "success",
          message: res.data.msg
        });
      } else {
        this.$message.warning(msg);
      }

      //数据更新
      this.getprojectList();
    },

    //查看项目信息
    showEditProjectDia(Project) {
      console.log(Project);
      //把值赋给表单
      this.form = Project;
      this.dialogFormVisibleEdit = true;
    },
    //清空之后加载项目信息
    loadProjectList() {
      this.getprojectList();
    },

    //模糊查询项目
    async searchProject() {
      const res = await this.$http.get(
        `/projects/getProjectByNameLike/${this.query}`
      );
      //console.log(res.data)
      const { msg, code, data } = res.data;
      if (code === 200) {
        //给表格数据赋值
        this.projectList = data;
        //提示
        this.$message.success(msg);
      } else {
        //失败提示
        this.$message.warning(msg);
      }
    },
    //获取项目信息
    async getprojectList() {
      const AUTH_TOKEN = localStorage.getItem("token");
      //console.log(AUTH_TOKEN);

      this.$http.defaults.headers.common["Authorization"] = AUTH_TOKEN;
      const res = await this.$http.get(`projects/`);
      console.log(res);
      const { code, data, msg } = res.data;
      if (code === 200) {
        this.projectList = data;
      } else {
        this.$message.warning(msg);
      }
    }
  }
};
</script>
>


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


