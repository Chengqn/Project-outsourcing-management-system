<template>
  <el-card class="bod-card">
    <!-- 面包屑 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>首页</el-breadcrumb-item>
      <el-breadcrumb-item>项目管理</el-breadcrumb-item>
      <el-breadcrumb-item>已验收项目</el-breadcrumb-item>
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
    <el-table :data="AcceptedprojectList" style="width: 100%" margin-top:30px height="400">
      <el-table-column fixed type="index" label="#" width="60"></el-table-column>
      <el-table-column prop="pname" label="项目名称" width="150"></el-table-column>
      <el-table-column prop="pexplanation" label="项目说明" width="180"></el-table-column>
      <el-table-column prop="pfinishtime" label="项目期望完成时间" width="180"></el-table-column>
      <el-table-column prop="numofdev" label="开发人数" width="100"></el-table-column>
      <el-table-column prop="totalpersondays" label="实际人天数" width="100"></el-table-column>
      <el-table-column prop="pstatus" label="项目状态"></el-table-column>
      <el-table-column prop="contractor" label="承包商"></el-table-column>
      <el-table-column prop="puber" label="项目发布者" width="100"></el-table-column>
      <el-table-column prop="address" label="汇总" fixed="right">
        <template slot-scope="scope">
          <el-row>
            <el-button
              size="mini"
              plain
              type="primary"
              icon="el-icon-edit"
              @click="showSummaryProjectDia(scope.row.id)"
              circle
            ></el-button>
          </el-row>
        </template>
      </el-table-column>
    </el-table>
    <!-- 任务汇总 -->
    <el-dialog title="任务汇总" customClass="customWidth" :visible.sync="dialogTableVisible">
      <el-table :data="form">
        <el-table-column type="index" label="#" width="60"></el-table-column>
        <el-table-column property="mname" label="任务名称" width="100"></el-table-column>
        <el-table-column property="mcontent" label="开发内容" width="150"></el-table-column>
        <el-table-column property="expectpersondays" label="预计人天"  width="100"></el-table-column>
        <el-table-column property="actualpersondays" label="实际人天"  width="100"></el-table-column>
        <el-table-column property="starttime" label="开始时间"></el-table-column>
        <el-table-column property="finishtime" label="结束时间"></el-table-column>
        <el-table-column property="rate" label="预计人天/实际人天"></el-table-column>
      </el-table>
    </el-dialog>
  </el-card>
</template>


<script>
export default {
  data() {
    return {
      AcceptedprojectList: [],
      query: "",
      dialogTableVisible: false,
      form: {
        mname: "",
        mcontent: "",
        expectpersondays: "",
        actualpersondays: "",
        starttime: "",
        finishtime: "",
        rate:""
      }
    };
  },
  created() {
    this.getAcceptedprojectList();
  },
  methods: {
    //查看项目汇总信息
    async showSummaryProjectDia(ProjectId) {
     // console.log(ProjectId)
      this.dialogTableVisible = true;
      const res = await this.$http.get(
        `/projects/getMissionByProjectId/${ProjectId}`
      );
      console.log(res);
      const { code, data, msg } = res.data;
      if (code === 200) {
        //整理数据
        this.form = data.map(function(val,index){
          val.rate = (val.expectpersondays/val.actualpersondays).toFixed(2);
          return val;
        });
      
        
      } else {
        this.$message.warning(msg);
      }
    },
    //查询之后清空
    loadProjectList() {},

    //获取已验收项目信息
    async getAcceptedprojectList() {
      const res = await this.$http.get(`projects/getAcceptedProject`);
      console.log(res);
      const { code, data, msg } = res.data;
      if (code === 200) {
        this.AcceptedprojectList = data;
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
.customWidth {
  width: 80%;
}
</style>
