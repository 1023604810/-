<template>
  <div class="con">
    <!-- 导航栏 -->
    <header class="d-flex flex-wrap align-items-center justify-content-center py-3 border-bottom">
      <ul class="nav col-12 col-md-auto mb-2 justify-content-center mb-md-0">
        <li>
          <router-link to="/messageboard" class="nav-link px-2 link-secondary">留言板</router-link>
        </li>
        <li>
          <router-link to="/articles" class="nav-link px-2 link-secondary">热点文章</router-link>
        </li>
      </ul>
      <ul class="nav nav-right">
        <router-link to="/mine" class="nav-link px-2 link-secondary">个人中心</router-link>       
      </ul>
    </header>
    <!-- 路由视图 -->
    <router-view></router-view>
    <!-- 底部栏 -->
    <footer class="py-5 text-muted text-center text-small">
      <p>© 2020-2032</p>
    </footer>
  </div>
  <div class="top_banner">
    <img src="./assets/img/topbanner.png" alt="" />
  </div>
</template>

<script>
export default {
  
};
</script>

<style>
@import "./assets/css/bootstrap.css";

.con {
  position: relative;
  z-index: 999;
}

.top_banner {
  width: 100%;
  height: 100%;
  position: fixed;
  top: 0;
  left: 0;
}

.top_banner>img {
  width: 100%;
  height: 100%;
}

.nav-right {
  position: absolute;
  right: 30px;
  top: 15px;
}
</style>
