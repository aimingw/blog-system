# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Full-stack personal blog system: Spring Boot 3 + Vue 3 (Composition API). Single-admin blog with JWT auth, Markdown articles, category/tag taxonomy, comment moderation, and site configuration.

## Commands

### Backend (Spring Boot + Maven)

```bash
cd backend
mvn spring-boot:run                    # Start backend on :8088
mvn test                               # Run all tests
mvn test -Dtest=ClassName              # Run single test class
```

### Frontend (Vue 3 + Vite)

```bash
cd frontend
npm run dev                            # Start dev server on :5173 (proxies /api to :8088)
npm run build                          # Production build
```

### Database

MySQL 8.0+. Run `db/init.sql` to initialize. Default DB config is `localhost:3306/blog` with user `root` / `123456` in `backend/src/main/resources/application.yml`.

Default admin credentials: `admin` / `admin123`.

## Architecture

### Backend

```
com.blog/
  controller/admin/   → /api/admin/*   (JWT-authenticated, except /api/admin/login)
  controller/front/   → /api/front/*   (public, no auth)
  service/            → Business logic (interfaces + impl/ package)
  mapper/             → MyBatis-Plus BaseMapper interfaces
  entity/             → DB entities (t_article, t_category, t_tag, t_article_tag, t_comment, t_site_config, t_user)
  dto/                → ArticleDTO, ArticleQueryDTO, CommentDTO, LoginDTO, PageResult<T>
  common/             → Result<T> response envelope, BusinessException, GlobalExceptionHandler
  config/             → JwtInterceptor, CorsConfig (allow all origins), MyBatisPlusConfig, WebMvcConfig
  utils/              → JwtUtils (HMAC-SHA256, 7-day expiry)
```

Key design points:
- **Admin and front controllers share the same service classes.** The front layer constrains results to `status=1` (published).
- **Result envelope pattern:** Every response wraps in `Result<T>` (`{code, message, data}`). The Axios response interceptor strips the envelope when `code === 200`.
- **Exception handling:** Services throw `BusinessException(code, message)`; `GlobalExceptionHandler` catches and converts to `Result.error()`.
- **JWT flow:** `JwtInterceptor` on `/api/admin/**` (excludes `/api/admin/login`). Token in `Authorization: Bearer <token>` header. User ID stored as request attribute.
- **Article-Tag is many-to-many** via `t_article_tag` join table. Tag associations are replaced wholesale on update (delete-all + insert-all).
- **Logic delete is configured in YAML (`deleted` field) but not used by any entity** — all deletes are physical.
- **`ArticleServiceImpl.pageQuery()` with tagId filter** uses in-memory pagination (fetch all matching IDs, then slice). OK for small datasets.

### Frontend

```
src/
  main.js              → Bootstrap: Vue → Pinia → Router → Element Plus (zhCn) → all icons global → marked provide
  App.vue              → <router-view> with fade transition
  router/index.js      → createWebHistory. Guard checks `requiresAuth` meta → redirects to /admin/login
  stores/app.js        → siteConfig (fetched on front layout mount)
  stores/user.js       → token + userInfo, login/logout (persists token to localStorage)
  utils/request.js     → Axios instance (baseURL: /api, timeout 15s). Attaches Bearer token. 401 → clear token + redirect.
  utils/auth.js        → localStorage wrapper for token
  api/index.js         → All API functions grouped by domain
  views/front/         → Layout (Navbar+Footer), Home, ArticleDetail, CategoryArticles, TagArticles, About
  views/admin/         → Layout (sidebar+header), Login, Dashboard, ArticleList, ArticleEdit, CategoryList, TagList, CommentList, SiteConfig
  components/front/    → ArticleCard, CommentSection, Navbar, Footer
```

Key design points:
- **Markdown is global:** `marked` is configured in `main.js` with Highlight.js and provided/injected. Article detail, article editor preview, site config about editor, and About page all use `inject('marked')`.
- **ArticleEdit.vue** handles both create and edit: checks `route.params.id` to determine mode.
- **CategoryList and TagList** use identical dialog-based CRUD patterns.
- **Vite proxies `/api` to `localhost:8088`** in dev. In production, a reverse proxy handles this.
- **Element Plus icons are all registered globally** — use `<el-icon><IconName /></el-icon>` without importing.
- **Global CSS** defines design tokens in `:root` and extensive `.md-content` styles for rendered Markdown.
