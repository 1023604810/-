<template>
  <el-row>
    <el-col>
      <el-menu active-text-color="#ffd04b" background-color="#545c64" default-active="1" text-color="#fff" router>
        <h3 class="logo">Vue论坛管理系统</h3>
        <el-menu-item index="1" route="/admin/comments">
          <el-icon><i-ep-ChatDotRound /></el-icon>
          <span>留言内容管理</span>
        </el-menu-item>
        <el-menu-item index="2" route="/admin/billboards">
          <el-icon><i-ep-Document /></el-icon>
          <span>留言数据看板</span>
        </el-menu-item>
      </el-menu>
    </el-col>
  </el-row>
</template>

<style>
.logo {
  text-align: center;
  color: white;
  padding: 20px 0;
}

.el-row .el-menu {
  height: 100%;
}
</style>