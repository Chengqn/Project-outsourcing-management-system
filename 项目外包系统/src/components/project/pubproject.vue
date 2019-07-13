<template>
  <el-card class="bod-card">
    <!-- 面包屑 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>首页</el-breadcrumb-item>
      <el-breadcrumb-item>项目管理</el-breadcrumb-item>
      <el-breadcrumb-item>发布任务</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 项目发布表单 -->
    <el-form ref="form" :model="form" label-width="80px" class="p_form">
      <el-form-item label="项目名称" class="p_name">
        <el-input v-model="form.pname"></el-input>
      </el-form-item>
      <el-col :span="12">
        <el-form-item label="项目说明" class="p_dis">
          <el-input type="textarea" v-model="form.pexplanation" rows="12"></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="11" :offset="1">
        <!-- 项目需求文档 -->
        <el-upload
          class="upload-demo"
          drag
          action="http://localhost:81/files/upload"
          ref="upload"
          multiple
        >
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">
            将文件拖到此处，或
            <em>点击上传</em>
          </div>
          <div class="el-upload__tip" slot="tip">请上传项目需求文档，且不超过500kb</div>
        </el-upload>
        <el-form-item label="完成时间" label-width="80px" class="p_time">
          <template>
            <el-date-picker
              v-model="form.pfinishtime"
              type="datetime"
              value-format="yyyy-MM-dd HH:mm:ss"
              format="yyyy-MM-dd HH:mm:ss"
              placeholder="选择日期时间"
            ></el-date-picker>
          </template>
        </el-form-item>
      </el-col>

      <el-form-item class="p_button">
        <el-button type="primary" @click="onSubmit()" class="p_submit">立即发布</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
export default {
  data() {
    return {
      form: {
        name: "",
        region: "",
        date1: "",
        date2: "",
        delivery: false,
        type: [],
        resource: "",
        desc: ""
      }
    };
  },

  methods: {
    //发布项目
    async onSubmit() {
      const res = await this.$http.post(`/projects/`, this.form);
      //console.log(res);
      const { code, msg } = res.data
      //console.log(res)
      const status = res.status
      //console.log(status)
      if (code === 200) {
        //提示成功
        this.$message.success(msg);
        //清空文本框
        this.form = {};
        //清空文件上传框
        this.$refs.upload.clearFiles();
      } else if(status === 500) {
        this.$message.warning(msg);
      }
    }
  }
};
</script>

<style>
.p_form {
  width: 800px;
  margin: 0 auto;
}
.p_name {
  margin-top: 40px;
}
.p_time{
  margin-top: 20px;
  margin-left: 28px;
}
.p_button{
  float:right;
  margin-right:290px;
}
.el-upload__tip{
  text-align: center;
}
.p_submit{
  width: 220px;
}
.bod-card {
  height: 100%;
}
</style>
