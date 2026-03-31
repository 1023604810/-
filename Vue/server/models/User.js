const { DataTypes } = require('sequelize');
const sequelize = require('../db');

// 定义用户模型
const User = sequelize.define('User', {
  id: {
    type: DataTypes.INTEGER,
    primaryKey: true,
    autoIncrement: true
  },
  username: {
    type: DataTypes.STRING,
    allowNull: false,
    unique: true
  },
  password: {
    type: DataTypes.STRING,
    allowNull: false
  }
}, {
  tableName: 'users',
  timestamps: false
});

// 同步模型到数据库
User.sync()
  .then(() => {
    console.log('用户表创建成功');
  })
  .catch(err => {
    console.error('用户表创建失败:', err);
  });

module.exports = User;