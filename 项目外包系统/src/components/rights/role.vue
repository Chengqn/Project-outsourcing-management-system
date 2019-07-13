<template>
  <el-card>
    <!-- 面包屑 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>首页</el-breadcrumb-item>
      <el-breadcrumb-item>权限管理</el-breadcrumb-item>
      <el-breadcrumb-item>角色列表</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 按钮 -->
    <el-button type="info" style="margin-top:20px" plain>添加角色</el-button>
    <!-- 表格 -->
    <el-table :data="tableData" height="400px" border style="width: 100% ;margin-top:20px">
      <el-table-column prop="date" label="#" width="180"></el-table-column>
      <el-table-column prop="name" label="角色名称" width="180"></el-table-column>
      <el-table-column prop="address" label="角色描述"></el-table-column>
      <el-table-column prop="do" label="操作">
        <template>
          <el-button size="mini" plain icon="el-icon-edit" circle></el-button>
          <el-button size="mini" plain type="primary" icon="el-icon-delete" circle></el-button>
          <el-button size="mini" plain type="success" icon="el-icon-check" circle></el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script>
export default {
  data() {
    return {
      tableData: [
        {
          date: "1",
          name: "admin",
          address: "超级管理员，具有最高权限"
        },
        {
          date: "2",
          name: "user",
          address: "普通用户可查看信息申请请假"
        }
      ]
    };
  }
};
</script>

<style>
</style>
>