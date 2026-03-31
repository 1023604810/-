<template>
  <div>
    <nav class="py-2 bg-light border-bottom">
      <div class="container d-flex flex-wrap">
        <ul class="nav me-auto">
          <li
            class="nav-item"
            v-for="(item, index) in childRoutes"
            :key="index"
          >
            <router-link class="nav-link link-secondary px-2" :to="item.path">
              <div>
                {{ item.meta.title }}
              </div>
            </router-link>
          </li>
          <li
            v-if="isAdmin"
            class="nav-item"
          >
            <router-link class="nav-link link-secondary px-2" to="/admin">
              <div>
                管理页面
              </div>
            </router-link>
          </li>
          <li
            class="nav-item nav-link link-secondary px-2" @click="logOut"                      
          >
            退出登陆
          </li>      

          
        </ul>
      </div>
    </nav>
    <router-view></router-view>
  </div>
</template>
<script>
export default {
  computed: {
    childRoutes() {
      return this.$router.getRoutes().filter((item) => {
        return item.meta.isChildPage;
      });
    },
    isAdmin() {
      // 从sessionStorage中获取用户名，判断是否是管理员
      const username = sessionStorage.getItem('username');
      return username === 'admin';
    }
  },
  methods: {
    logOut() {   
     let flag=confirm("确定要退出吗？")  
     if(flag){
      if (sessionStorage.getItem("token")!=null) {
        sessionStorage.removeItem("token");
      }
      if (sessionStorage.getItem("router")!=null) {
        sessionStorage.removeItem("router");
      }
      if (sessionStorage.getItem("username")!=null) {
        sessionStorage.removeItem("username");
      }          
      this.$router.removeRoute("info")
      if(this.$router.hasRoute("admin"))  {
        this.$router.removeRoute("admin")
      }      
      this.$router.push("/");
     }      
    },
  },
};
</script>
