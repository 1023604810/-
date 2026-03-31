<template>
  <div class="billboards-container">
    <h2>留言数据看板</h2>
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card class="stat-card">
          <template #header>
            <div class="card-header">
              <span>总留言数</span>
            </div>
          </template>
          <div class="stat-number">{{ totalComments }}</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <template #header>
            <div class="card-header">
              <span>总点赞数</span>
            </div>
          </template>
          <div class="stat-number">{{ totalSupport }}</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <template #header>
            <div class="card-header">
              <span>男性留言数</span>
            </div>
          </template>
          <div class="stat-number">{{ maleComments }}</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <template #header>
            <div class="card-header">
              <span>女性留言数</span>
            </div>
          </template>
          <div class="stat-number">{{ femaleComments }}</div>
        </el-card>
      </el-col>
    </el-row>
    
    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="24">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>留言热度排行</span>
            </div>
          </template>
          <el-table :data="hotComments" style="width: 100%">
            <el-table-column prop="rank" label="排名" width="80" />
            <el-table-column prop="name" label="姓名" width="120" />
            <el-table-column prop="content" label="留言内容">
              <template #default="scope">
                <span>{{ scope.row.content.length > 50 ? scope.row.content.substring(0, 50) + '...' : scope.row.content }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="support" label="点赞数" width="80" />
          </el-table>
        </el-card>
      </el-col>
    </el-row>
    
    <div class="title">数据看板</div>
    <el-divider />
    <div class="echarts-container">
      <div class="left-con" id="left_con"></div>
      <div class="right-con" id="right_con"></div>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  data() {
    return {
      messageList: [],
      totalComments: 0,
      totalSupport: 0,
      maleComments: 0,
      femaleComments: 0,
      hotComments: []
    };
  },
  created() {
    this.getComments();
  },
  mounted() {
    this.initCharts();
  },
  methods: {
    async getComments() {
      try {
        const response = await this.axios.get("http://localhost:3000/messageList");
        if (response.status === 200) {
          this.messageList = response.data;
          this.calculateStats();
        }
      } catch (error) {
        console.error("获取留言失败:", error);
      }
    },
    calculateStats() {
      // 计算总留言数
      this.totalComments = this.messageList.length;
      
      // 计算总点赞数
      this.totalSupport = this.messageList.reduce((sum, item) => sum + item.support, 0);
      
      // 计算男女留言数
      this.maleComments = this.messageList.filter(item => item.gender === 1).length;
      this.femaleComments = this.messageList.filter(item => item.gender === 0).length;
      
      // 计算热度排行
      this.hotComments = this.messageList
        .sort((a, b) => b.support - a.support)
        .slice(0, 10)
        .map((item, index) => ({
          ...item,
          rank: index + 1
        }));
    },
    traverse() {
      return new Promise(function(resolve, reject) {
        this.axios.get('http://localhost:3000/messageList')
          .then(response => {
            const data = response.data;
            let supportNum = [];
            let supportName = [];
            let maleNum = 0;
            let femaleNum = 0;
            
            data.forEach(item => {
              supportNum.push(item.support);
              supportName.push(item.name);
              if (item.gender === 0) {
                femaleNum++;
              } else {
                maleNum++;
              }
            });
            
            const genderData = [
              { value: maleNum, name: '男性' },
              { value: femaleNum, name: '女性' }
            ];
            
            const bar_config = {
              title: {
                text: '用户留言点赞数',
                left: 'center'
              },
              tooltip: {
                trigger: 'item'
              },
              xAxis: {
                type: 'category',
                data: supportName
              },
              yAxis: {
                type: 'value'
              },
              series: [{
                data: supportNum,
                type: 'bar',
                color: '#40a9ff'
              }]
            };
            
            const circle_option = {
              title: {
                text: '用户性别比例',
                left: 'center'
              },
              tooltip: {
                trigger: 'item'
              },
              series: [{
                name: '性别统计',
                type: 'pie',
                radius: ['40%', '70%'],
                data: genderData,
                itemStyle: {
                  borderRadius: 10,
                  borderColor: '#fff',
                  borderWidth: 2
                }
              }]
            };
            
            resolve({ bar_config: bar_config, circle_option: circle_option });
          })
          .catch(error => {
            reject(error);
          });
      }.bind(this));
    },
    initCharts() {
      this.traverse().then(options => {
        // 初始化左侧图表（柱状图）
        const leftChart = echarts.init(document.getElementById('left_con'));
        leftChart.setOption(options.bar_config);
        
        // 初始化右侧图表（饼图）
        const rightChart = echarts.init(document.getElementById('right_con'));
        rightChart.setOption(options.circle_option);
        
        // 监听窗口大小变化，自适应调整图表大小
        window.addEventListener('resize', () => {
          leftChart.resize();
          rightChart.resize();
        });
      });
    }
  }
};
</script>

<style scoped>
.billboards-container {
  padding: 20px;
}

h2 {
  margin-bottom: 20px;
  color: #303133;
}

.stat-card {
  height: 120px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.stat-number {
  font-size: 32px;
  font-weight: bold;
  color: #409eff;
  margin-top: 20px;
  text-align: center;
}

.title {
  color: #545c64;
  margin-top: 20px;
  margin-bottom: 10px;
}

.echarts-container {
  width: 100%;
  display: flex;
  justify-content: space-between;
  padding: 15px;
}

.left-con {
  margin-right: 40px;
}

.left-con, .right-con {
  width: 50%;
  height: 300px;
  border-radius: 6px;
  background-color: #fafafa;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.08);
  padding: 20px;
  color: #40a9ff;
}
</style>