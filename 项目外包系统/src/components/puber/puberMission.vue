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
      </el-col>
    </el-row>
     <!-- 表格 -->
      <el-table
      :data="missionList"
      style="width: 100%"
         height="400">
      <el-table-column fixed type="index" label="#" width="60"></el-table-column>
      <el-table-column
        prop="project"
        label="从属项目"
        width="180">
      </el-table-column>
      <el-table-column
        prop="mname"
        label="任务名称"
        width="180">
      </el-table-column>
      <el-table-column
        prop="mcontent"
        label="开发内容"
        width="180">
      </el-table-column>
      <el-table-column
        prop="expectpersondays"
        label="预计人天"
        width="100">
      </el-table-column>
       <el-table-column
        prop="actualpersondays"
        label="实际人天"
         width="100">
      </el-table-column>
       <el-table-column
        prop="starttime"
        label="开始时间"
         width="180">
      </el-table-column>
        <el-table-column
        prop="finishtime"
        label="结束时间"
          width="180">
      </el-table-column>
       <el-table-column
        prop="mstatus"
        label="任务状态"
         width="100">
      </el-table-column>
       <el-table-column
        prop="contractor"
        label="承包商"
         width="100">
      </el-table-column>
      <el-table-column prop="address" label="操作" width="150" fixed="right"> 
        <template slot-scope="scope">
          <el-row>
            <el-button 
            size="mini" 
            plain 
            type="primary" 
            icon="el-icon-edit" 
            @click="showEditMissionDia(scope.row)"
            circle></el-button>
          </el-row>
        </template>
      </el-table-column>
    </el-table>

     <!-- 查看项目用户的对话框 -->
    <el-dialog title="查看开发任务" :visible.sync="dialogFormVisibleEdit">
      <el-form :model="form">
          <el-form-item  label="项目名称" label-width="100px">
          <el-input disabled v-model="form.project" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item  label="任务名称" label-width="100px">
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

    


    </el-card>
</template>



<script>
export default {
  data(){
    return{
        missionList:[],
        query:'',
        //添加开发任务的对话框是否可见
        dialogFormVisibleAdd:false,
        Mission:{
              mname:"",
              Project:"",
              mcontent:"",
              expectpersondays:"",
              actualpersondays:"",
              starttime:"",
              finishtime:"",
            },
        //查看开发任务的dialog box是否可见
        dialogFormVisibleEdit:false,
        form:{
              mname:"",
              Project:"",
              mcontent:"",
              expectpersondays:"",
              actualpersondays:"",
              starttime:"",
              finishtime:"",

        }

    };
  },

  created() {
    this.getmissionList();
  },    

  methods:{
    //查看开发任务信息
    showEditMissionDia(Mission){
       //把值赋给表单
        this.form = Mission
        this.dialogFormVisibleEdit = true
    },
    //实现对从属项目的开发任务模糊查询
    async searchMission(){
       const res = await this.$http.get(`/mission/getMissionByProject/${this.query}`);
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
    loadMissionList(){
      this.getmissionList();
    },
    //获取开发任务信息
    async getmissionList() {
    const AUTH_TOKEN = localStorage.getItem("token");
    //console.log(AUTH_TOKEN);

    this.$http.defaults.headers.common["Authorization"] = AUTH_TOKEN;
    const res = await this.$http.get( `mission/getMissionByPuber`);
      console.log(res);
      const { code, data, msg } = res.data;
      if (code === 200) {
        this.missionList = data;
      } else {
        this.$message.warning(msg);
      }
    }
  },
    
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
