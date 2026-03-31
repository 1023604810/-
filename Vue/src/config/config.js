// ECharts配置文件

// 柱状图配置
export const bar_option = {
  tooltip: {
    trigger: 'item'
  },
  legend: {
    bottom: '0'
  },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '15%',
    containLabel: true
  },
  title: {
    text: '用户性别比例',
    left: 'center'
  },
  color: ['#5470c6', '#ee6666'],
  xAxis: {
    type: 'category',
    data: ['男性', '女性']
  },
  yAxis: {
    type: 'value',
    axisLine: {
      lineStyle: {
        color: '#40a9ff'
      }
    }
  },
  series: [
    {
      data: [3, 2],
      type: 'bar',
      barCategoryGap: '40%',
      itemStyle: {
        normal: {
          borderRadius: 6
        }
      }
    }
  ]
};

// 饼图配置
export const circle_option = {
  tooltip: {
    trigger: 'item'
  },
  legend: {
    top: 'bottom'
  },
  series: [
    {
      name: '性别统计',
      type: 'pie',
      radius: ['40%', '70%'],
      avoidLabelOverlap: false,
      itemStyle: {
        borderRadius: 10,
        borderColor: '#fff',
        borderWidth: 2
      },
      label: {
        show: false,
        position: 'center'
      },
      emphasis: {
        label: {
          show: true,
          fontSize: '36',
          fontWeight: 'bold'
        }
      },
      labelLine: {
        show: false
      },
      data: [
        { value: 60, name: '男性' },
        { value: 40, name: '女性' }
      ]
    }
  ]
};

// 柱状图2配置
export const bar_config = {
  title: {
    text: '用户留言数',
    left: 'center'
  },
  color: '#40a9ff',
  xAxis: {
    type: 'category',
    data: ['李晓萌', '王小跳', '张全全', '阮小明', '曾小红']
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      data: [1, 1, 1, 1, 1],
      type: 'bar'
    }
  ]
};

// 折线图配置
export const line_config = {
  title: {
    text: '留言点赞数',
    left: 'center'
  },
  xAxis: {
    type: 'category',
    data: ['李晓萌', '王小跳', '张全全', '阮小明', '曾小红']
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      data: [23, 62, 46, 10, 18],
      type: 'line',
      smooth: true
    }
  ]
};