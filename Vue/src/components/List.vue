<template>
<div>
    <div class="list-group ps-5 pe-5 pt-5 pb-5">
        <!-- 文章列表 -->
        <div class="list-group-item list-group-item-action d-flex gap-3 py-3" 
             style="cursor:pointer"
             v-for="(item, index) in articleList" :key="item.id" v-on:click="goArticle(item)">            
            <img src="../assets/img/message.png" alt="twbs" width="32" height="32" class="rounded-circle flex-shrink-0">
            <div class="d-flex gap-2 w-100 justify-content-between">
                <div>
                    <h6 class="mb-0">{{item.title}}</h6>
                    <p class="mb-0 opacity-75">{{item.introduce}}</p>
                </div>
                <small class="opacity-50 text-nowrap">
                    <p>作者：{{ item.author }}</p>
                    <p>阅读量：
                        {{ item.readNum }}
                    </p>
                </small>
            </div>
        </div>
    </div>
</div>
</template>
<script>
import {mapMutations,mapActions} from 'vuex'
export default {
    props: ['articleList'],
    methods:{  
        ...mapMutations(['store']),
        ...mapActions(['getState']),     
        goArticle(item){ 
            this.getState(item)          
            this.$router.push("/article/"+item.id)
        }
    }   
}
</script>

