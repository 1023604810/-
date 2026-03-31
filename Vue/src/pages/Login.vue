<template>
    <main class="w-50 m-auto">
        <form>
            <div style="text-align:center">
                <img class="mb-3 mt-3" src="../assets/img/lock.svg" alt="" width="80">
                <h1 class="h4 mb-4">用户登录</h1>
            </div>
            <div class="mb-4">
                <input v-model="username" type="text" class="form-control active" id="name" placeholder="请输入用户名"
                    required="">
            </div>
            <div class="mb-4">
                <input v-model="password" type="password" class="form-control" placeholder="请输入密码">
            </div>
            <button v-on:click.prevent="login" class="w-100 btn btn-primary mb-3" type="submit">登录</button>
            <button v-on:click.prevent="guestLogin" class="w-100 btn btn-secondary" type="button">游客登录</button>
            <div class="mt-3 text-center">
                <router-link to="/register" class="text-primary">没有账号？去注册</router-link>
            </div>
        </form>
    </main>
</template>

<script>
export default {
    data() {
        return {
            username: "",
            password: ""
        }
    },
    methods: {
        login() {
            console.log('开始登录，this对象:', this);
            console.log('开始登录，用户名为:', this.username, '密码为:', this.password);
            this.axios.post('/api/login', {
                username: this.username,
                password: this.password
            })
                .then((response) => {
                    console.log('登录响应:', response);
                    if (response.data.data.code == 200) {
                       sessionStorage.setItem('token', response.data.data.token)
                       sessionStorage.setItem('username', this.username)
                       sessionStorage.setItem('isGuest', 'false')
                       // 当用户是admin时跳转到留言管理页面
                       console.log('登录成功，用户名为:', this.username);
                       console.log('登录响应:', response);
                       
                       // 检查后端返回的router信息
                       const routerInfo = response.data.data.data.router;
                       console.log('后端返回的router信息:', routerInfo);
                       
                       if (routerInfo && routerInfo.path) {
                           console.log('执行跳转到:', routerInfo.path);
                           this.$router.push(routerInfo.path)
                       } else {
                           console.log('执行跳转到/messageboard');
                           this.$router.push("/messageboard")
                       }
                    }
                    else {
                        let message=response.data.data.message
                        console.log('登录失败:', message);
                        alert(message)
                    }
                })
                .catch((error) => {
                    console.error('登录请求失败:', error);
                    alert('登录失败，请稍后重试');
                })
        },
        guestLogin() {
            console.log('游客登录');
            sessionStorage.setItem('isGuest', 'true');
            // 跳转到列表页面
            this.$router.push("/articles/list")
        }
    }
}
</script>

<style>

</style>