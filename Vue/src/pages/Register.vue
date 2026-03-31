<template>
    <main class="w-50 m-auto">
        <form>
            <div style="text-align:center">
                <img class="mb-3 mt-3" src="../assets/img/lock.svg" alt="" width="80">
                <h1 class="h4 mb-4">用户注册</h1>
            </div>
            <div class="mb-4">
                <input v-model="username" type="text" class="form-control active" id="name" placeholder="请输入用户名" required>
            </div>
            <div class="mb-4">
                <input v-model="password" type="password" class="form-control" placeholder="请输入密码">
            </div>
            <button v-on:click.prevent="register" class="w-100 btn btn-primary" type="submit">注册</button>
            <div class="mt-3 text-center">
                <router-link to="/login" class="text-primary">已有账号？去登录</router-link>
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
        register() {
            if (!this.username) {
                alert('请输入用户名');
                return;
            }
            if (!this.password) {
                alert('请输入密码');
                return;
            }
            
            this.axios.post('/api/register', {
                username: this.username,
                password: this.password
            })
                .then((response) => {
                    if (response.data.data.code == 200) {
                        alert('注册成功，请登录');
                        this.$router.push("/login")
                    }
                    else {
                        let message = response.data.data.message
                        alert(message)
                    }
                })
                .catch((error) => {
                    console.error('注册请求失败:', error);
                    alert('注册失败，请稍后重试');
                })
        }
    }
}
</script>

<style>

</style>