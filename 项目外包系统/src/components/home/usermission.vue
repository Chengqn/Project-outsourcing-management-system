<template>
  <el-card class="bod-card">
    <!-- 面包屑 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>首页</el-breadcrumb-item>
      <el-breadcrumb-item>项目管理</el-breadcrumb-item>
      <el-breadcrumb-item>我的任务</el-breadcrumb-item>
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
        <!-- <el-button type="success" @click="showAddLeaveDia()">添加请假</el-button> -->
      </el-col>
    </el-row>
    <!-- 表格 -->
    <el-table :data="MyMissionList" style="width: 100%" margin-top:30px height="400">
      <el-table-column fixed type="index" label="#" width="60"></el-table-column>
      <el-table-column prop="project" label="从属项目" width="180"></el-table-column>
      <el-table-column prop="mname" label="任务名称" width="180"></el-table-column>
      <el-table-column prop="mcontent" label="开发任务" width="180"></el-table-column>
      <el-table-column prop="expectpersondays" label="预计人天" width="100"></el-table-column>
      <el-table-column prop="actualpersondays" label="实际人天" width="100"></el-table-column>
      <el-table-column prop="puber" label="项目发布者" width="180"></el-table-column>
      <el-table-column prop="starttime" label="开始时间" width="180"></el-table-column>
      <el-table-column prop="finishtime" label="结束时间" width="180"></el-table-column>
      <el-table-column prop="mstatus" label="任务状态" width="100"></el-table-column>
      <el-table-column prop="contractor" label="承包商" width="100"></el-table-column>
      <el-table-column prop="address" label="完成" fixed="right">
        <template slot-scope="scope">
          <el-row>
            <el-button
              size="mini"
              plain
              type="primary"
              icon="el-icon-check"
              @click="showCompeteMissionDia(scope.row.id)"
              circle
            ></el-button>
          </el-row>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>


<script>
export default {
  data() {
    return {
      MyMissionList: "",
      query: ""
    };
  },
  created() {
    this.getMyMissiontList();
  },
  methods: {
    //实现任务的查询
    searchMission() {},
    //请空之后刷新获取信息
    loadMissionList() {},
    //完成任务
    async showCompeteMissionDia(MissionId) {
      this.$confirm("此操作将完成开发任务, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(async () => {
          const res = await this.$http.post(
            `mission/competeMisision/${MissionId}`
          );
          const { code, data, msg } = res.data;
          console.log(res);
          if (code === 200) {
            //更新视图
           this.getMyMissiontList()
            //提示成功
            this.$message({
              type: "success",
              message: msg
            });
          }else if(code === 400){
             //提示失败
            this.$message({
              type: "error",
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
    //获取已验收项目信息
    async getMyMissiontList() {
      const res = await this.$http.get(`mission/getMyMission`);
      console.log(res);
      const { code, data, msg } = res.data;
      if (code === 200) {
        this.MyMissionList = data;
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
