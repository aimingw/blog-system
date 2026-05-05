# Blog System

A full-stack personal blog system built with Spring Boot 3 + Vue 3.

## Tech Stack

**Backend**: Spring Boot 3.x, JDK 17, MyBatis-Plus, MySQL 8.0, JWT (jjwt 0.12.x), BCrypt
**Frontend**: Vue 3 (Composition API), Vite, Vue Router 4, Pinia, Axios, Element Plus, @kangc/v-md-editor

## Project Structure

```
blog-system/
├── db/init.sql          # Database initialization script
├── backend/             # Spring Boot backend
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/blog/
│       │   ├── config/          # CORS, MyBatis-Plus, WebMVC configs
│       │   ├── controller/      # REST controllers (admin + front)
│       │   ├── dto/             # Data Transfer Objects
│       │   ├── entity/          # Database entities
│       │   ├── mapper/          # MyBatis-Plus mappers
│       │   ├── service/         # Business logic
│       │   ├── common/          # Result wrapper, exception handler
│       │   └── utils/           # JWT utilities
│       └── resources/
│           └── application.yml  # Application config
└── frontend/            # Vue 3 frontend
    ├── package.json
    ├── vite.config.js
    └── src/
        ├── api/          # API request modules
        ├── components/   # Shared components
        ├── router/       # Vue Router config
        ├── stores/       # Pinia stores
        ├── utils/        # Axios instance, auth helpers
        └── views/        # Page components
            ├── front/    # Public pages
            └── admin/    # Admin dashboard pages
```

## Quick Start

### Prerequisites

- JDK 17+
- Maven 3.8+
- MySQL 8.0+
- Node.js 18+
- npm 9+

### 1. Database Setup

Run the init script against your MySQL instance:

```bash
mysql -u root -p < db/init.sql
```

### 2. Backend

Update `backend/src/main/resources/application.yml` with your MySQL credentials, then:

```bash
cd backend
mvn spring-boot:run
```

The backend starts at http://localhost:8080

### 3. Frontend

```bash
cd frontend
npm install
npm run dev
```

The frontend starts at http://localhost:5173

### Default Admin Account

- Username: `admin`
- Password: `admin123`

## API Overview

### Admin APIs (prefix: `/api/admin`, JWT required except login)

- `POST /api/admin/login` — Admin login
- `GET/POST/PUT/DELETE /api/admin/articles` — Article CRUD
- `GET/POST/PUT/DELETE /api/admin/categories` — Category CRUD
- `GET/POST/PUT/DELETE /api/admin/tags` — Tag CRUD
- `GET/PUT/DELETE /api/admin/comments` — Comment management
- `GET/PUT /api/admin/site-config` — Site configuration

### Front APIs (prefix: `/api/front`)

- `GET /api/front/articles` — Article list (paginated)
- `GET /api/front/articles/:id` — Article detail
- `GET /api/front/categories` — Category list
- `GET /api/front/tags` — Tag list
- `POST /api/front/comments` — Submit comment
- `GET /api/front/comments?articleId=` — Article comments
- `GET /api/front/site-config` — Site configuration
