const { Sequelize } = require('sequelize');

// 创建数据库连接
const sequelize = new Sequelize('vue_login', 'root', '123456', {
  host: 'localhost',
  dialect: 'mysql',
  port: 3306,
  logging: console.log
});

// 测试数据库连接
sequelize.authenticate()
  .then(() => {
    console.log('数据库连接成功');
  })
  .catch(err => {
    console.error('数据库连接失败:', err);
  });

module.exports = sequelize;