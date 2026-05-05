import { createRouter, createWebHistory } from 'vue-router'
import { isLoggedIn } from '../utils/auth'

const routes = [
  {
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
    path: '/admin/login',
    name: 'AdminLogin',
    component: () => import('../views/admin/Login.vue')
  },
  {
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

router.beforeEach((to, from, next) => {
  if (to.matched.some(r => r.meta.requiresAuth)) {
    if (!isLoggedIn()) {
      next({ name: 'AdminLogin' })
    } else {
      next()
    }
  } else {
    next()
  }
})

export default router
