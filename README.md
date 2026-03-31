# 项目简介

## 项目概述

路游论坛系统是一个基于 Spring Boot 和 Vue 3 开发的全栈 Web 应用，主要功能包括消息板、文章管理和用户登录系统。项目采用前后端分离架构，后端提供 RESTful API，前端负责用户界面和交互。

## 技术栈

### 后端技术
- **框架**: Spring Boot 2.6.13
- **语言**: Java 11
- **数据访问**: Spring Data JPA
- **数据库**: MySQL
- **安全**: Spring Security + JWT
- **构建工具**: Maven

### 前端技术
- **框架**: Vue 3
- **状态管理**: Vuex 4
- **路由**: Vue Router 4
- **UI 库**: Element Plus
- **HTTP 客户端**: Axios
- **动画**: Animate.css
- **构建工具**: Vue CLI

## 功能特性

### 后端功能
- **消息管理**: 支持消息的增删改查操作
- **文章管理**: 提供文章的CRUD接口
- **用户认证**: 基于JWT的用户登录和认证系统
- **跨域支持**: 配置了CORS支持，方便前端调用API

### 前端功能
- **消息板**: 查看、发布和管理消息
- **文章列表**: 浏览和查看文章
- **用户登录/注册**: 支持用户认证
- **响应式设计**: 适配不同屏幕尺寸
- **动画效果**: 集成Animate.css提供流畅的动画体验

## 目录结构

```
Project/
├── SpringBoot/          # 后端项目
│   ├── src/             # 源代码
│   │   ├── main/java/   # Java源代码
│   │   └── resources/   # 资源文件
│   ├── target/          # 构建输出
│   └── pom.xml          # Maven配置文件
├── Vue/                 # 前端项目
│   ├── src/             # 源代码
│   │   ├── assets/      # 静态资源
│   │   ├── components/  # 组件
│   │   ├── config/      # 配置
│   │   ├── pages/       # 页面
│   │   ├── router/      # 路由
│   │   └── store/       # 状态管理
│   ├── dist/            # 构建输出
│   ├── public/          # 公共文件
│   └── package.json     # 依赖配置
```

## 运行指南

### 后端运行
1. 确保安装了 Java 11 和 Maven
2. 配置 MySQL 数据库（参考 `application.properties`）
3. 执行 `mvn spring-boot:run` 启动后端服务

### 前端运行
1. 确保安装了 Node.js 和 npm/yarn
2. 执行 `npm install` 或 `yarn` 安装依赖
3. 执行 `npm run dev` 启动开发服务器
4. 执行 `npm run build` 构建生产版本

## 许可证

本项目采用 MIT 许可证，详情请参阅 LICENSE 文件。

## 贡献指南

欢迎提交 Issue 和 Pull Request 来改进本项目。在提交代码前，请确保遵循项目的代码风格和最佳实践。
