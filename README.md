# 博客系统

基于 Spring Boot 3 + Vue 3 的全栈个人博客系统。

## 技术栈

**后端**: Spring Boot 3.x, JDK 17, MyBatis-Plus, MySQL 8.0, JWT (jjwt 0.12.x), BCrypt
**前端**: Vue 3 (Composition API), Vite, Vue Router 4, Pinia, Axios, Element Plus, @kangc/v-md-editor

## 项目结构

```
blog-system/
├── db/init.sql          # 数据库初始化脚本
├── backend/             # Spring Boot 后端
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/blog/
│       │   ├── config/          # CORS、MyBatis-Plus、WebMVC 配置
│       │   ├── controller/      # REST 控制器（后台管理 + 前台展示）
│       │   ├── dto/             # 数据传输对象
│       │   ├── entity/          # 数据库实体类
│       │   ├── mapper/          # MyBatis-Plus 数据访问接口
│       │   ├── service/         # 业务逻辑层
│       │   ├── common/          # 统一响应封装、异常处理
│       │   └── utils/           # JWT 工具类
│       └── resources/
│           └── application.yml  # 应用配置文件
└── frontend/            # Vue 3 前端
    ├── package.json
    ├── vite.config.js
    └── src/
        ├── api/          # API 接口模块
        ├── components/   # 公共组件
        ├── router/       # Vue Router 路由配置
        ├── stores/       # Pinia 状态管理
        ├── utils/        # Axios 实例、认证工具
        └── views/        # 页面组件
            ├── front/    # 前台公开页面
            └── admin/    # 后台管理页面
```

## 快速开始

### 环境要求

- JDK 17+
- Maven 3.8+
- MySQL 8.0+
- Node.js 18+
- npm 9+

### 1. 初始化数据库

在 MySQL 中执行初始化脚本：

```bash
mysql -u root -p < db/init.sql
```

### 2. 启动后端

根据你的 MySQL 配置修改 `backend/src/main/resources/application.yml` 中的数据库连接信息，然后：

```bash
cd backend
mvn spring-boot:run
```

后端启动在 http://localhost:8088

### 3. 启动前端

```bash
cd frontend
npm install
npm run dev
```

前端启动在 http://localhost:5173

### 默认管理员账号

- 用户名: `admin`
- 密码: `admin123`

## API 概览

### 后台管理接口（前缀: `/api/admin`，除登录外均需 JWT 认证）

- `POST /api/admin/login` — 管理员登录
- `GET/POST/PUT/DELETE /api/admin/articles` — 文章增删改查
- `GET/POST/PUT/DELETE /api/admin/categories` — 分类增删改查
- `GET/POST/PUT/DELETE /api/admin/tags` — 标签增删改查
- `GET/PUT/DELETE /api/admin/comments` — 评论管理
- `GET/PUT /api/admin/site-config` — 站点配置

### 前台公开接口（前缀: `/api/front`）

- `GET /api/front/articles` — 文章列表（分页）
- `GET /api/front/articles/:id` — 文章详情
- `GET /api/front/categories` — 分类列表
- `GET /api/front/tags` — 标签列表
- `POST /api/front/comments` — 提交评论
- `GET /api/front/comments?articleId=` — 文章评论列表
- `GET /api/front/site-config` — 站点配置
