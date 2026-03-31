const express = require('express');
const cors = require('cors');
const User = require('./models/User');

const app = express();
const PORT = 3001;

// 中间件
app.use(cors());
app.use(express.json({
  limit: '1mb',
  strict: false
}));

// 健康检查接口
app.get('/api/health', (req, res) => {
  res.json({
    status: 'ok',
    message: '服务器运行正常'
  });
});

// 登录接口
app.post('/api/login', async (req, res) => {
  console.log('接收到登录请求:', req.body);
  
  try {
    const { username, password } = req.body;
    
    if (!username || !password) {
      console.log('用户名或密码为空');
      return res.json({
        data: {
          code: 400,
          message: '用户名或密码不能为空',
          data: null
        }
      });
    }
    
    // 查找用户
    console.log('查找用户:', username);
    const user = await User.findOne({ where: { username, password } });
    console.log('查找结果:', user);
    
    if (user) {
      // 登录成功，返回token和路由信息
      const token = Math.random().toString(36).substr(2, 15) + Math.random().toString(36).substr(2, 15);
      let router = [
        'info',
        'admin'
      ];
      
      res.json({
        data: {
          code: 200,
          message: '登录成功',
          data: {
            token,
            router
          }
        }
      });
    } else {
      // 登录失败
      res.json({
        data: {
          code: 401,
          message: '用户名或密码错误',
          data: null
        }
      });
    }
  } catch (error) {
    console.error('登录失败:', error);
    res.json({
      data: {
        code: 500,
        message: '服务器错误',
        data: null
      }
    });
  }
});

// 启动服务器
app.listen(PORT, () => {
  console.log(`服务器运行在 http://localhost:${PORT}`);
});