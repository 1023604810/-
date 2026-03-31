const mysql = require('mysql2/promise');

// 创建数据库连接
async function initDatabase() {
  try {
    // 连接到MySQL服务器
    const connection = await mysql.createConnection({
      host: 'localhost',
      user: 'root',
      password: '123456'
    });
    
    // 创建数据库
    await connection.execute('CREATE DATABASE IF NOT EXISTS vue_login');
    console.log('数据库创建成功');
    
    // 关闭连接
    await connection.end();
    
    // 重新连接到指定数据库
    const dbConnection = await mysql.createConnection({
      host: 'localhost',
      user: 'root',
      password: '123456',
      database: 'vue_login'
    });
    
    // 创建用户表
    await dbConnection.execute(`
      CREATE TABLE IF NOT EXISTS users (
        id INT PRIMARY KEY AUTO_INCREMENT,
        username VARCHAR(255) NOT NULL UNIQUE,
        password VARCHAR(255) NOT NULL
      )
    `);
    console.log('用户表创建成功');
    
    // 插入测试数据
    await dbConnection.execute(
      'INSERT IGNORE INTO users (username, password) VALUES (?, ?)',
      ['admin', '123456']
    );
    await dbConnection.execute(
      'INSERT IGNORE INTO users (username, password) VALUES (?, ?)',
      ['user', '123456']
    );
    console.log('测试数据插入成功');
    
    // 关闭连接
    await dbConnection.end();
  } catch (error) {
    console.error('数据库初始化失败:', error);
  }
}

// 执行初始化
initDatabase();