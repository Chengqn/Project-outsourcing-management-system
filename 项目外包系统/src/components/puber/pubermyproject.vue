<template>
    <el-card class="bod-card">
    <!-- 面包屑 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>首页</el-breadcrumb-item>
      <el-breadcrumb-item>项目管理</el-breadcrumb-item>
      <el-breadcrumb-item>我的项目</el-breadcrumb-item>
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
        <!-- <el-button type="success" @click="showAddMissionDia()">添加开发任务</el-button> -->
      </el-col>
    </el-row>
     <!-- 表格 -->
      <el-table
      :data="projectList"
      style="width: 100%"
       height="400">
      <el-table-column fixed type="index" label="#" width="40"></el-table-column>
      <el-table-column
        prop="pname"
        label="项目名称"
        width="180">
      </el-table-column>
      <el-table-column
        prop="pexplanation"
        label="项目说明"
        width="180">
      </el-table-column>
      <el-table-column
        prop="pfinishtime"
        label="项目期望完成时间"
        width="180">
      </el-table-column>
       <el-table-column
        prop="pstatus"
        label="项目状态">
      </el-table-column>
       <el-table-column
        prop="contractor"
        label="承包商"
        width="100">
      </el-table-column>
      <el-table-column prop="address" label="操作" fixed="right">
        <template slot-scope="scope">
          <el-row>
            <el-button 
            size="mini" 
            plain 
            type="primary" 
            icon="el-icon-edit" 
            @click="showEditProjectDia(scope.row)"
            circle></el-button>
            <el-button 
            size="mini" 
            plain 
            type="danger" 
            icon="el-icon-delete" 
            @click="showDeleteProjectDia(scope.row.id)"
            circle></el-button>
          </el-row>
        </template>
      </el-table-column>
    </el-table>
     <!-- 查看项目的对话框 -->
    <el-dialog title="查看项目" :visible.sync="dialogFormVisibleEdit">
      <el-form :model="form">
        <el-form-item  label="项目名称" label-width="100px">
          <el-input  v-model="form.pname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="项目说明" label-width="100px">
          <el-input  v-model="form.pexplanation" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="期望完成时间" label-width="100px">
          <el-input  v-model="form.pfinishtime" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="项目状态" label-width="100px">
          <el-input disabled v-model="form.pstatus" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="项目总人天" label-width="100px">
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
        <el-button type="primary" @click="editProject()">修 改</el-button>
      </div>
    </el-dialog>
    </el-card>
</template>


<script>
export default {
    data(){
        return{
            projectList:[],
             form:{
                pname:"",
                pexplanation:"",
                pfinishtime:"",
                pstatus:"",
                totalpersonaldays:"",
                contractor:"",
                puber:""
            },
            Mission:{
              mname:"",
              Project:"",
              mcontent:"",
              expectpersondays:"",
              actualpersondays:"",
              starttime:"",
              finishtime:"",

            },
             //对话框是否可见的属性
             dialogFormVisibleEdit: false,
             query:"",
        };
    },

    created() {
    this.getprojectList();
     },  

    methods:{
    //修改更新项目信息
    async editProject(){
      const res = await this.$http.put(`projects/updateProject/${this.form.id}`,this.form)
      console.log(res)
      //关闭对话框
      this.dialogFormVisibleEdit = false
      //消息提示
        this.$message({
              type: "success",
              message: res.data.msg
            });
      //数据更新
      this.getprojectList()

    },
    //模糊查询项目
    async searchProject() {
      const res = await this.$http.get(`/projects/getProjectByPuber/${this.query}`);
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
    //清空信息之后刷新视图
    loadProjectList(){
      this.getprojectList()
    },

    //删除项目信息 打开消息盒子
    showDeleteProjectDia(projectId) {
      this.$confirm("此操作将删除项目, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(async () => {
          const res = await this.$http.delete(`projects/deleteProjects/${projectId}`);
          console.log(res);
          const { msg, code, data } = res.data;
          if (code === 200) {
              //更新视图
              this.getprojectList()
              //提示成功
            this.$message({
              type: "success",
              message: msg
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
    //查看项目信息
    showEditProjectDia(Project){
        console.log(Project)
        //把值赋给表单
        this.form = Project
        this.dialogFormVisibleEdit = true
      },
    //获取项目信息
    async getprojectList() {
    const AUTH_TOKEN = localStorage.getItem("token");
    //console.log(AUTH_TOKEN);

    this.$http.defaults.headers.common["Authorization"] = AUTH_TOKEN;
    const res = await this.$http.get( `projects/getProjecyByPuber`);
      console.log(res);
      const { code, data, msg } = res.data;
      if (code === 200) {
        this.projectList = data;
      } else {
        this.$message.warning(msg);
      }
    }
    }
    
}
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
