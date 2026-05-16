/**
 * Vue Router 路由配置
 * 包含前台公开路由和后台管理路由（需登录认证）
 */
import { createRouter, createWebHistory } from 'vue-router'
import { isLoggedIn } from '../utils/auth'

const routes = [
  {
    // 前台路由（使用前台布局组件）
    path: '/',
    component: () => import('../views/front/Layout.vue'),
    children: [
      { path: '', name: 'Home', component: () => import('../views/front/Home.vue') },
      { path: 'article/:id', name: 'ArticleDetail', component: () => import('../views/front/ArticleDetail.vue') },
      { path: 'category/:id', name: 'CategoryArticles', component: () => import('../views/front/CategoryArticles.vue') },
      { path: 'tag/:id', name: 'TagArticles', component: () => import('../views/front/TagArticles.vue') },
      { path: 'about', name: 'About', component: () => import('../views/front/About.vue') }
    ]
  },
  {
    // 登录页（独立布局）
    path: '/admin/login',
    name: 'AdminLogin',
    component: () => import('../views/admin/Login.vue')
  },
  {
    // 后台管理路由（使用后台布局组件，需要登录认证）
    path: '/admin',
    component: () => import('../views/admin/Layout.vue'),
    meta: { requiresAuth: true },
    children: [
      { path: '', redirect: '/admin/dashboard' },
      { path: 'dashboard', name: 'Dashboard', component: () => import('../views/admin/Dashboard.vue') },
      { path: 'articles', name: 'ArticleList', component: () => import('../views/admin/ArticleList.vue') },
      { path: 'articles/new', name: 'ArticleCreate', component: () => import('../views/admin/ArticleEdit.vue') },
      { path: 'articles/:id/edit', name: 'ArticleEdit', component: () => import('../views/admin/ArticleEdit.vue') },
      { path: 'categories', name: 'CategoryList', component: () => import('../views/admin/CategoryList.vue') },
      { path: 'tags', name: 'TagList', component: () => import('../views/admin/TagList.vue') },
      { path: 'comments', name: 'CommentList', component: () => import('../views/admin/CommentList.vue') },
      { path: 'settings', name: 'SiteConfig', component: () => import('../views/admin/SiteConfig.vue') }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 全局前置守卫：检查需要认证的路由
router.beforeEach((to, from, next) => {
  if (to.matched.some(r => r.meta.requiresAuth)) {
    if (!isLoggedIn()) {
      next({ name: 'AdminLogin' }) // 未登录，跳转到登录页
    } else {
      next()
    }
  } else {
    next()
  }
})

export default router
