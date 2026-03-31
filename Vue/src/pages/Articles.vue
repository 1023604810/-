<template>
    <div>
        <nav class="py-2 bg-light border-bottom">
            <div class="container d-flex flex-wrap">
                <ul class="nav me-auto">
                    <li class="nav-item">
                       <router-link to="/articles/list" class="nav-link link-secondary px-2">
                            推荐好文
                       </router-link>
                    </li>
                    <li class="nav-item">
                        <router-link to="/articles/grid" class="nav-link link-secondary px-2">
                            热门景点
                       </router-link>
                    </li>
                </ul>
            </div>
        </nav>
        <router-view v-slot="{ Component }" :articleList="articleList">
            <transition 
                enter-active-class="animate__animated animate__fadeInRight "
                leave-active-class="animate__animated animate__fadeOutLeft absolute">
                  <component :is="Component" />
            </transition>
        </router-view>

    </div>
</template>

<script>
export default {
    data() {
        return {
            articleList:[]
        }
    },
    created() {
        this.getArticle()
    },
    methods: {
       getArticle(){
        this.axios.get('/api/articles/recommended')
        .then(response=>{
            if(response.status==200){
                this.articleList=response.data
            }
        })
       }
    }
}
</script>

<style>
.absolute {
    position: absolute;
}
</style>
