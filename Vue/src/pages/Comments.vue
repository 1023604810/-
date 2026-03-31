<template>
  <div class="comments-container">
    <div class="header">
      <h3 class="title">留言列表</h3>
      <div class="searchContainer">
        <el-input v-model="keyword" placeholder="请输入搜索关键字"></el-input>
        <el-button plain style="margin-left:8px" @click="search">搜索</el-button>
      </div>
    </div>
    <el-divider />
    <el-scrollbar class="com_table">
      <el-table :data="searchData.length===0?commentsData:searchData" style="width: 100%">
        <el-table-column prop="time" label="发表时间" width="180" :formatter="dateFormat" />
        <el-table-column prop="name" label="姓名" width="180" />
        <el-table-column prop="content" label="留言内容" />
        <el-table-column width="180">
          <template #default="scope">
            <el-button size="small" type="primary" @click="edit(scope.row)">编辑</el-button>
            <el-button size="small" type="danger" @click="del(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-scrollbar>
    <el-collapse-transition>
      <div>
        <div class="title">编辑留言</div>
        <el-divider />
        <el-form :model="comment" label-width="60px">
          <el-form-item label="姓名">
            <el-input v-model="comment.name" />
          </el-form-item>
          <el-form-item label="邮箱">
            <el-input v-model="comment.email" />
          </el-form-item>
          <el-form-item label="匿名">
            <el-switch v-model="comment.unnamed" />
          </el-form-item>
          <el-form-item label="性别">
            <el-radio-group v-model="comment.gender">
              <el-radio :label="1">男</el-radio>
              <el-radio :label="0">女</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="留言">
            <el-input v-model="comment.content" type="textarea" rows="3" />
          </el-form-item>
          <el-form-item>
            <el-button class="fullBtn" type="primary" @click="confirm">
              确定
            </el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-collapse-transition>
  </div>
</template>

<script>
export default {
  data() {
    return {
      commentsData: [
        {
          id: 110,
          email: "lxm@qq.com",
          name: "李晓萌",
          content: "绝少有地方能像福州的三坊七巷一样,随便一脚踏下去,你的脚印就必然和某一位风云人物的足迹重合。",
          gender: 0,
          support: 21,
          has_sup: true,
          unnamed: false,
          time: 1673344795104,
        },
      ],
      comment: {
        id: 110,
        email: "lxm@qq.com",
        name: "李晓萌",
        content: "绝少有地方能像福州的三坊七巷一样,随便一脚踏下去,你的脚印就必然和某一位风云人物的足迹重合。",
        gender: 0,
        support: 21,
        has_sup: true,
        unnamed: false,
        time: 1673344795104,
      },
      keyword: "",
      searchData: []
    };
  },
  created() {
    this.getComments();
  },
  methods: {
    async getComments() {
      try {
        const response = await this.axios.get("http://localhost:8081/api/messages");
        if (response.status === 200) {
          this.commentsData = response.data;
        }
      } catch (error) {
        console.error("获取留言失败:", error);
      }
    },
    edit(row) {
      this.comment = row;
    },
    del(row) {
      this.axios.delete("http://localhost:8081/api/messages/" + row.id)
        .then(response => {
          if (response.status === 200) {
            this.$message({
              message: response.statusText,
              type: 'success',
            });
            this.getComments();
          }
        })
        .catch(err => {
          this.$message({
            message: err.response.statusText,
            type: 'error',
          });
        });
    },
    confirm() {
      this.axios
        .put(
          "http://localhost:8081/api/messages/" + this.comment.id,
          this.comment
        )
        .then(response => {
          if (response.status === 200) {
            this.$message({
              message: response.statusText,
              type: "success",
            });
            this.getComments();
          }
        })
        .catch(err => {
          this.$message({
            message: err.response.statusText,
            type: "error",
          });
        });
    },
    dateFormat(row) {
      let time = row.time;
      if (!time) {
        return;
      }
      let date = new Date(time);
      let Y = date.getFullYear() + "年";
      let M = (date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1) + "月";
      let D = date.getDate() + "日";
      return Y + M + D;
    },
    search() {
      // 简单的前端搜索实现
      this.searchData = this.commentsData.filter(item => {
        return item.name.includes(this.keyword) || item.content.includes(this.keyword);
      });
    }
  },
};
</script>

<style scoped>
.comments-container {
  padding: 20px;
}

.header, .searchContainer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.title {
  color: #545c64;
}

.com_table {
  margin-bottom: 15px;
  height: 210px;
}

.fullBtn {
  width: 100%;
}
</style>