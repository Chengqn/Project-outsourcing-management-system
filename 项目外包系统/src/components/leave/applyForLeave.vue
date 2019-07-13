<template>
  <el-card>
    <!-- 面包屑 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>首页</el-breadcrumb-item>
      <el-breadcrumb-item>请假管理</el-breadcrumb-item>
      <el-breadcrumb-item>申请请假</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 请假表单 -->
    <el-form ref="form" :model="form" label-width="80px" style="margin-top:20px">
      <el-form-item label="请假人">
        <el-input v-model="form.applicant"></el-input>
      </el-form-item>
      <el-form-item label="请假理由">
        <el-input v-model="form.offreason"></el-input>
      </el-form-item>
      <el-form-item label="开始时间" label-width="100px">
        <template>
          <el-date-picker
            v-model="form.starttime"
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
            v-model="form.finishtime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            format="yyyy-MM-dd HH:mm:ss"
            placeholder="选择日期时间"
          ></el-date-picker>
        </template>
      </el-form-item>

      <el-form-item label="请假类型">
        <el-input v-model="form.offtype"></el-input>
      </el-form-item>
      <el-form-item label="请假天数">
        <el-input v-model="form.totaldays"></el-input>
      </el-form-item>
      <el-button type="primary" @click="onSubmit()">立即请假</el-button>
      <el-button>取消</el-button>
    </el-form>
  </el-card>
</template>


<script>
export default {
  data() {
    return {
      form: {
        applicant: "",
        starttime: "",
        finfishtime: "",
        offtype: "",
        offreason: "",
        totaldays: ""
      }
    };
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
  methods: {
    async onSubmit() {
      const res = await this.$http.post(`/leaves/`, this.form);
      //console.log(res);
      const { code, msg } = res.data;
      if (code === 200) {
        //提示成功
        this.$message.success(msg);
        //清空文本框
        this.form = {};
      } else {
        this.$message.warning(msg);
      }
    }
  }
};
</script>
<style>
</style>
