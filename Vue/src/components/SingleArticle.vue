<template>
   <transition @before-enter="before" @enter="enter">
      <div class="container my-5 whitebg" v-show="show">
              <div class="row p-4 pt-lg-5 pb-lg-5 align-items-center rounded-3 border shadow-lg">
                  <div class="col-lg-12 p-3 p-lg-5 pt-lg-3">
                  <h1 class="display-4 mb-4 fw-bold lh-1">
                      {{ articleInfo.title }}
                  </h1>

                  <div class="bg-light p-2 pt-4 mt-4 mb-4 rounded" v-if="overview.introduce">
                    <p class="d-flex justify-content-between">
                        <small>{{overview.introduce}}</small>
                        <small style="min-width: 150px; text-align: right;">
                            作者：{{overview.author}}
                             / 热度
                          <img v-for="(item,index) in hot" :key="index" src="../assets/img/hot.svg" width="12">
                        </small>
                    </p>
                  </div>

                  <p
                      class="lead"
                      style="line-height: 1.8"
                      v-html="articleInfo.content"
                  ></p>
                  <div class="d-grid gap-2 d-md-flex justify-content-end mb-4 mb-lg-3">
                      <button
                      type="button"
                      class="btn btn-success btn-lg px-4"
                      v-on:click="back"
                      >
                      返回
                      </button>
                  </div>
          </div>
        </div>
      </div>
   </transition>
        
   
   
</template>

<script>
import { mapState, mapGetters } from "vuex";
export default {
  created() {
    this.getArticle();
  },
  data() {
    return {
      articleInfo: {},
      show: false,
    };
  },
  mounted() {
    this.show = true;
  },
  methods: {
    back() {
      this.$router.go(-1);
    },
    getArticle() {
      this.axios
        .get("http://localhost:3000/article/" + this.$route.params.id)
        .then((response) => {
          if (response.status === 200) {
            this.articleInfo = response.data;
          }
        });
    },
    before(el){
      el.style.transform="translateX(100%)"
    },
    enter(el,done){
      el.offsetTop;
      el.style.transform="translateX(0px)"
      el.style.transition="all 0.5s ease"
      done();
    }
  },
  computed:{
    ...mapState(['overview']),
    ...mapGetters(['hot'])
  }
};
</script>
<style scoped>
.lead {
  text-indent: 2em;
}
</style>