-- 创建数据库
CREATE DATABASE IF NOT EXISTS vue_login;

-- 使用数据库
USE vue_login;

-- 创建用户表
CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- 创建留言表
CREATE TABLE IF NOT EXISTS message_list (
    id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    gender INT,
    support INT DEFAULT 0,
    has_sup BOOLEAN DEFAULT FALSE,
    unnamed BOOLEAN DEFAULT FALSE,
    time BIGINT
);

-- 插入测试数据
INSERT IGNORE INTO users (username, password) VALUES ('admin', '123456');
INSERT IGNORE INTO users (username, password) VALUES ('user', '123456');

-- 插入测试留言数据
INSERT IGNORE INTO message_list (email, name, content, gender, support, has_sup, unnamed, time) VALUES (
    'lxm@qq.com',
    '李晓萌',
    '绝少有地方能像福州的三坊七巷一样,随便一脚踏下去,你的脚印就必然和某一位风云人物的足迹重合。',
    0,
    21,
    TRUE,
    FALSE,
    1673344795104
);
