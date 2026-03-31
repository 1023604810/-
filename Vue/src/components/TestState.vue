<template>
    <div class="offset-1">
        文章作者：<input type="text" v-model="this.$store.state.overview.author" /> 
        <button @click="view">提交</button>
    </div>   
</template>

<script>
export default {
    data() {
        return {
            articleList: [],
        }
    },
    created() {
        this.getArticle()
    },
    methods: {
        getArticle() {
            this.axios.get('http://localhost:3000/articleList/')
                .then((response) => {
                    if (response.status == 200) {
                        //获取接口数据，存入articleList数组中。
                        this.articleList = response.data
                        this.$store.state.overview.author=this.articleList[0].author
                    }
                })
        },
        view(){
           this.$router.push("/viewState")
        }
    }
}
</script>

<style>
.absolute {
    position: absolute;
}
</style>


