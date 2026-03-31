<template>
    <!-- 留言列表渲染 -->
    <div class="d-flex text-muted mb-3" v-for="(item, index) in messageList" v-bind:key="item.id" v-bind:id="item.id">
        <div class=" border-bottom col-md-12">
            <img :src="item.gender == 0 ? avatar_female : avatar_male" alt="" width="24" height="24">
            <strong class="text-gray-dark" v-if="!item.unnamed">{{ item.name }}</strong>
            <strong v-else>匿名用户</strong>
            <p>{{ item.content }}</p>
            <div class="message_sup">
                <!-- 渲染时间 -->
                <small> {{ parserTime(item.time) }} </small>
                <div :class="['support', { supportActived: item.has_sup }]" v-on:click="support(index)">
                    <!-- supportActived样式表示激活 -->
                    <img src="../assets/img/support.png" alt="">
                    <span>{{ item.support }}</span>
                </div>
            </div>
        </div>
    </div>
    <!-- 留言列表渲染结束 -->
</template>

<script>
export default {
    emits:['parentSupport'],
    props: ['messageList', 'avatar_male', 'avatar_female'],
    methods: {
        //解析时间戳
        parserTime(time) {
            if (!time) {
                return
            }
            let date = new Date(time);
            let Y = date.getFullYear() + '年';
            let M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '月';
            let D = date.getDate() + '日';
            return Y + M + D
        },

        //点赞方法
        support(index) {
            //组织新数据
            let newData = {
                data: {
                    id: this.messageList[index].id,
                    email: this.messageList[index].email,
                    name: this.messageList[index].name,
                    content: this.messageList[index].content,
                    gender: this.messageList[index].gender,
                    support: this.messageList[index].support + 1,
                    has_sup: true,
                    unnamed: this.messageList[index].unnamed,
                },
                index: index
            }

            this.$emit('parentSupport',newData)
        }
    }
}
</script>

<style>
/* 样式 */
.message_sup {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.support {
    cursor: pointer;
    display: flex;
    align-items: flex-start;
    height: 22px;
    overflow: hidden;
    margin: 10px;
}

.support span {
    margin-left: 5px;
}

.supportActived img {
    position: relative;
    top: -25px;
}
</style>