<template>
  <div class="dashboard">
    <h2 class="page-title">仪表盘</h2>

    <!-- 统计卡片区域 -->
    <el-row :gutter="20">
      <!-- 文章总数 -->
      <el-col :span="8">
        <div class="stat-card">
          <div class="stat-icon articles-icon">
            <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/><polyline points="14 2 14 8 20 8"/></svg>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.articles }}</div>
            <div class="stat-label">文章总数</div>
          </div>
        </div>
      </el-col>

      <!-- 分类数量 -->
      <el-col :span="8">
        <div class="stat-card">
          <div class="stat-icon categories-icon">
            <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"><path d="M22 19a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h5l2 3h9a2 2 0 0 1 2 2z"/></svg>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.categories }}</div>
            <div class="stat-label">分类数量</div>
          </div>
        </div>
      </el-col>

      <!-- 待审核评论 -->
      <el-col :span="8">
        <div class="stat-card">
          <div class="stat-icon comments-icon">
            <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"><path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"/></svg>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.comments }}</div>
            <div class="stat-label">待审核评论</div>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { reactive, onMounted } from 'vue'
import { getAdminArticles, getAdminCategories, getAdminComments } from '../../api'

/** 仪表盘统计数据 */
const stats = reactive({ articles: 0, categories: 0, comments: 0 })

/** 页面挂载时异步加载各项统计数据 */
onMounted(async () => {
  // 获取文章总数
  try { const r = await getAdminArticles({ page: 1, size: 1 }); stats.articles = r.data.total } catch (e) {}
  // 获取分类数量
  try { const r = await getAdminCategories(); stats.categories = (r.data || []).length } catch (e) {}
  // 获取待审核评论数
  try { const r = await getAdminComments({ page: 1, size: 1, status: 0 }); stats.comments = r.data.total } catch (e) {}
})
</script>

<style scoped>
.page-title {
  font-size: 20px;
  font-weight: 700;
  color: var(--color-text);
  margin-bottom: 24px;
}

.stat-card {
  display: flex;
  align-items: center;
  gap: 18px;
  background: var(--color-bg-white);
  border-radius: var(--radius-md);
  padding: 24px;
  box-shadow: var(--shadow-sm);
  border: 1px solid var(--color-border-light);
  transition: all var(--transition-base);
}

.stat-card:hover {
  box-shadow: var(--shadow-md);
  transform: translateY(-1px);
}

.stat-icon {
  flex-shrink: 0;
  width: 48px;
  height: 48px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.articles-icon {
  background: rgba(74, 111, 165, 0.1);
  color: var(--color-primary);
}

.categories-icon {
  background: rgba(82, 183, 136, 0.1);
  color: var(--color-success);
}

.comments-icon {
  background: rgba(230, 162, 60, 0.1);
  color: var(--color-warning);
}

.stat-value {
  font-size: 30px;
  font-weight: 700;
  color: var(--color-text);
  line-height: 1.2;
}

.stat-label {
  font-size: 13px;
  color: var(--color-text-muted);
  margin-top: 2px;
}
</style>
