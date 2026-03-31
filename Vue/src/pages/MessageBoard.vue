<template>
    <!-- 页面容器 -->
    <div class="container">
        <main>
            <!-- 文章内容 -->
            <div class="py-5 text-center">
                <img class="d-block mx-auto " src="../assets/img/banner.png">
                <p class="lead mx-auto" style="max-width:980px;line-height:2">
                    {{ article }}
                </p>
            </div>

            <!-- 留言列表 -->
            <div class="py-5">
                <Title>留言列表</Title>
                <Comments v-bind:messageList="messageList" v-bind:avatar_male="avatar_male"
                    v-bind:avatar_female="avatar_female" v-on:parentSupport="support">
                </Comments>

            </div>

            <!-- 发表留言 -->
            <div class="col-md-12">
                <Title>发表留言</Title>
                <form class="needs-validation">
                    <div class="row g-3">
                        <div class="col-12">
                            <label for="name" class="form-label">姓名:</label>
                            <input type="text" class="form-control active" id="name" placeholder="您的姓名" required
                                v-model="message.name">
                        </div>

                        <div class="col-12">
                            <label for="email" class="form-label">邮箱:</label>
                            <input type="email" class="form-control" id="email" placeholder="name@email.com" required
                                v-model="message.email">
                        </div>

                        <div class="col-12">
                            <div>
                                <p class="form-label">性别:</p>
                                <div class="form-check">
                                    <input id="male" name="gender" type="radio" class="form-check-input" required
                                        value="1" v-model="message.gender">
                                    <label class="form-check-label" for="male">男</label>
                                </div>
                                <div class="form-check">
                                    <input id="female" name="gender" type="radio" class="form-check-input" value="0"
                                        v-model="message.gender">
                                    <label class="form-check-label" for="female">女</label>
                                </div>
                            </div>
                        </div>

                        <div class="col-12 py-2">
                            <div class="form-check form-switch">
                                <input class="form-check-input" type="checkbox" role="switch" id="unnamed-switch"
                                    v-model="message.unnamed">
                                <label for="unnamed-switch">匿名评论</label>
                            </div>
                        </div>

                        <div class="col-12 py-2">
                            <input class="list-group-item list-group-item-action list-group-item-success form-control"
                                placeholder="姓名,男性,name@email.com" v-model="tip">
                        </div>

                        <div class="col-12">
                            <label class="form-label">留言内容:</label>
                            <textarea class="form-control" name="" id="" cols="30" rows="5" placeholder="请输入您要发表的内容"
                                v-model="message.content">
                </textarea>
                        </div>

                        <div class="col-12 py-2">
                            <p :class="['list-group-item', 'list-group-item-action', promptSty]">
                                {{ promptMsg }}
                            </p>
                        </div>
                    </div>
                    <hr class="my-4">
                    <button class="w-100 btn btn-success btn-lg" type="submit" v-on:click.prevent="submit">提交</button>
                </form>
            </div>

        </main>
    </div>
</template>
<script>
import avatar_male from "../assets/img/male.png"
import avatar_female from "../assets/img/female.png"
import Comments from "../components/Comments.vue"
import Title from "../components/Title.vue"
export default {
    created() {
        this.getComments().then(result => {
            if (result.status == 200) {
                this.messageList = result.data
            }
        })
    },
    data() {
        return {
            promptMsg: "",
            promptSty: "",
            avatar_male,
            avatar_female,
            article: "福建省，简称“闽”，是中华人民共和国省级行政区。省会福州，位于中国东南沿海，东北与浙江省毗邻，西北与江西省接界，西南与广东省相连，东南隔台湾海峡与台湾省相望，陆地总面积12.4万平方千米",
            messageList: [],

            message: {
                id: "",
                email: "",
                name: "",
                content: "",
                gender: 0,
                support: 0,     //点赞数量
                has_sup: false, //点赞状态
                unnamed: false   //匿名状态
            }
        }
    },
    components: {
        Comments,
        Title,
    },
    methods: {
        support(newData) {
            //数据传递给接口
            this.postSupportState(newData).then(postResult => {
                if (postResult.status == 200) {
                    this.getSupportState(newData.data.id).then(getResult => {
                        if (getResult.status == 200) {
                            this.messageList[newData.index] = getResult.data
                        }
                    })
                }
            })
        },

        submit() {
            // 检查是否登录
            const token = sessionStorage.getItem('token');
            const isGuest = sessionStorage.getItem('isGuest');
            
            if (!token || isGuest === 'true') {
                alert('请先登录后再提交留言！');
                this.$router.push('/');
                return;
            }
            
            if (this.message.name.trim() == "" || this.message.email.trim() == "" || this.message.content.trim() == "") {
                alert("信息输入不全，请输入完整信息！")
                return
            }

            let messageInfo = {
                id: Date.now(),
                email: this.message.email,
                name: this.message.name,
                content: this.message.content,
                gender: this.message.gender,
                support: 0,     //点赞数量
                has_sup: false, //点赞状态
                unnamed: this.message.unnamed  //匿名状态
            }
            this.postComments(messageInfo).then(postResult => {
                if (postResult.status == 201) {
                    this.message.id = ""
                    this.message.email = ""
                    this.message.name = ""
                    this.message.content = ""
                    this.message.gender = 0
                    this.message.support = 0
                    this.message.has_sup = false
                    this.message.unnamed = false
                    this.getComments().then(getResult => {
                        if (getResult.status == 200) {
                            this.messageList = getResult.data
                            this.$nextTick(() => {
                                window.location.href = `#${messageInfo.id}`
                            })
                        }
                    })
                }
            })
        },

        //获取评论数据
        getComments() {
            return this.axios.get("/api/messages")
        },

        //上传评论数据
        postComments(data) {
            return this.axios.post("/api/messages", data)
        },

        //上传更新的点赞数据
        postSupportState(newData) {
            return this.axios.put("/api/messages/" + newData.data.id, newData.data)
        },

        //获取更新的点赞数据
        getSupportState(id) {
            return this.axios.get("/api/messages/" + id)
        },


    },
    computed: {
        tip: {
            get() {
                if (this.message.name == "" && this.message.email == "") return
                let gender = this.message.gender == 0 ? "女" : "男"
                let name = this.message.unnamed == true ? "匿名" : this.message.name
                return name + "," + gender + "," + this.message.email
            },
            set(val) {
                let arr = val.split(",")
                this.message.name = arr[0]
                this.message.gender = arr[1] == "女" ? "0" : "1"
                this.message.email = arr[2]
            }
        }
    },
    watch: {
        "message.content": {
            handler: function (newVal, oldVal) {
                if (newVal.length <= 20) {
                    this.promptMsg = "输入内容不少于20个字符"
                    this.promptSty = "list-group-item-warning"
                }
                else if (newVal.length > 140) {
                    this.promptMsg = "输入内容不超过140个字符"
                    this.promptSty = "list-group-item-danger"
                }
                else {
                    this.promptMsg = "您已输入" + newVal.length + "个字符"
                    this.promptSty = "list-group-item-success"
                }
            },
            immediate: true
        }
    }
}
</script>
