<template>
  <el-container class="admin-layout">
    <!-- 左侧导航栏 -->
    <el-aside width="230px" class="admin-sidebar">
      <!-- 品牌标识 -->
      <div class="sidebar-brand">
        <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round"><rect x="2" y="3" width="20" height="14" rx="2"/><line x1="8" y1="21" x2="16" y2="21"/><line x1="12" y1="17" x2="12" y2="21"/></svg>
        <span>Blog Admin</span>
      </div>

      <!-- 侧边菜单：根据路由自动高亮当前项 -->
      <el-menu
        :default-active="activeMenu"
        router
        background-color="transparent"
        text-color="#94a3b8"
        active-text-color="#e2e8f0"
        class="sidebar-menu"
      >
        <el-menu-item index="/admin/dashboard">
          <el-icon><DataAnalysis /></el-icon>
          <span>仪表盘</span>
        </el-menu-item>
        <el-menu-item index="/admin/articles">
          <el-icon><Document /></el-icon>
          <span>文章管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/categories">
          <el-icon><Collection /></el-icon>
          <span>分类管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/tags">
          <el-icon><PriceTag /></el-icon>
          <span>标签管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/comments">
          <el-icon><ChatDotSquare /></el-icon>
          <span>评论管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/settings">
          <el-icon><Setting /></el-icon>
          <span>站点设置</span>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <!-- 右侧主内容区 -->
    <el-container class="admin-main">
      <!-- 顶部导航栏：用户名 + 操作按钮 -->
      <el-header class="admin-header">
        <div class="header-left">
          <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"><path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/></svg>
          <span>{{ username }}</span>
        </div>
        <div class="header-right">
          <el-button text size="small" @click="$router.push('/')">
            <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"><rect x="2" y="3" width="20" height="14" rx="2"/><line x1="8" y1="21" x2="16" y2="21"/><line x1="12" y1="17" x2="12" y2="21"/></svg>
            查看前台
          </el-button>
          <el-button text size="small" type="danger" @click="handleLogout">退出</el-button>
        </div>
      </el-header>

      <!-- 子路由页面渲染区 -->
      <el-main class="admin-content">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '../../stores/user'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

/** 根据当前路由路径计算激活菜单项 */
const activeMenu = computed(() => route.path)
/** 从Store获取当前登录用户名 */
const username = computed(() => userStore.userInfo?.username || 'Admin')

/** 退出登录：清除状态并跳转到登录页 */
function handleLogout() {
  userStore.logout()
  router.push('/admin/login')
}
</script>

<style scoped>
.admin-layout {
  min-height: 100vh;
}

/* ====== 侧边栏 ====== */
.admin-sidebar {
  background: #1e293b;
  overflow: hidden;
}

.sidebar-brand {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 22px 20px;
  color: #e2e8f0;
  font-size: 16px;
  font-weight: 650;
  letter-spacing: 0.5px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.06);
  margin-bottom: 8px;
}

.sidebar-brand svg {
  flex-shrink: 0;
  color: var(--color-primary-light);
}

.sidebar-menu {
  border-right: none;
}

.sidebar-menu :deep(.el-menu-item) {
  margin: 2px 8px;
  border-radius: 8px;
  height: 42px;
  line-height: 42px;
  font-size: 14px;
  transition: all var(--transition-fast);
}

.sidebar-menu :deep(.el-menu-item:hover) {
  background: rgba(255, 255, 255, 0.06);
  color: #e2e8f0;
}

.sidebar-menu :deep(.el-menu-item.is-active) {
  background: rgba(74, 111, 165, 0.2);
  color: #e2e8f0;
  font-weight: 500;
  border-left: 3px solid var(--color-primary-light);
  padding-left: 17px;
}

.sidebar-menu :deep(.el-menu-item .el-icon) {
  font-size: 17px;
}

/* ====== 顶部栏 ====== */
.admin-main {
  background: var(--color-bg);
}

.admin-header {
  background: var(--color-bg-white);
  border-bottom: 1px solid var(--color-border);
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 24px;
  height: 56px;
  box-shadow: var(--shadow-xs);
}

.header-left {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 14px;
  color: var(--color-text-secondary);
}

.header-right {
  display: flex;
  align-items: center;
  gap: 8px;
}

/* ====== 内容区 ====== */
.admin-content {
  padding: 24px;
}
</style>
