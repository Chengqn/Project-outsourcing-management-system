<template>
  <el-card>
    <!-- 面包屑 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>首页</el-breadcrumb-item>
      <el-breadcrumb-item>权限管理</el-breadcrumb-item>
      <el-breadcrumb-item>权限列表</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 表格 -->
    <el-table 
    :data="tableData"
    height="400px"
    border
    style="width: 100% ;margin-top:20px">
    <el-table-column
      prop="date"
      label="#"
      width="180">
    </el-table-column>
    <el-table-column
      prop="name"
      label="权限名称"
      width="180">
    </el-table-column>
    <el-table-column
      prop="address"
      label="层级">
    </el-table-column>
     <el-table-column
      prop="do"
      label="操作"
      width="180">
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
        tableData: [{
          date: '1',
          name: '用户管理',
          address: '一级'
        }, {
          date: '2',
          name: '请假审批',
          address: '一级'
        }, {
          date: '3',
          name: '权限管理',
          address: '一级'
        }, {
          date: '4',
          name: '查看请假',
          address: '二级'
        }, {
          date: '5',
          name: '查看用户',
          address: '二级'
        }, {
          date: '6',
          name: '查看权限',
          address: '二级'
        }, {
          date: '7',
          name: '系统登陆',
          address: '二级'
        }]
      }
    }
};
</script>

<style>
</style>
>
