<template>
  <div class="home">
    <!-- Hero Section -->
    <section class="hero">
      <h1 class="hero-title">{{ heroTitle }}</h1>
      <p class="hero-subtitle">{{ heroSubtitle }}</p>
    </section>

    <!-- Announcement -->
    <div v-if="announcement" class="announcement">
      <div class="announcement-inner">
        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"><circle cx="12" cy="12" r="10"/><line x1="12" y1="16" x2="12" y2="12"/><line x1="12" y1="8" x2="12.01" y2="8"/></svg>
        <span>{{ announcement }}</span>
      </div>
    </div>

    <!-- Article List -->
    <div class="articles-section">
      <ArticleCard v-for="article in articles" :key="article.id" :article="article" :categories="categories" />

      <div v-if="articles.length === 0 && !loading" class="empty-state">
        <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" stroke-linecap="round"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/><polyline points="14 2 14 8 20 8"/><line x1="16" y1="13" x2="8" y2="13"/><line x1="16" y1="17" x2="8" y2="17"/></svg>
        <p>还没有文章</p>
      </div>

      <div class="pagination" v-if="total > size">
        <el-pagination
          background
          layout="prev, pager, next"
          :total="total"
          :page-size="size"
          v-model:current-page="page"
          @current-change="fetchArticles"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { getArticles, getCategories } from '../../api'
import { useAppStore } from '../../stores/app'
import ArticleCard from '../../components/front/ArticleCard.vue'

const appStore = useAppStore()
const articles = ref([])
const categories = ref([])
const loading = ref(false)
const page = ref(1)
const size = ref(10)
const total = ref(0)

const announcement = computed(() => appStore.siteConfig?.announcement || '')
const heroTitle = computed(() => appStore.siteConfig?.title || 'My Blog')
const heroSubtitle = computed(() => appStore.siteConfig?.subtitle || '用文字记录思考与发现')

async function fetchArticles() {
  loading.value = true
  try {
    const res = await getArticles({ page: page.value, size: size.value })
    articles.value = res.data.records
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

onMounted(async () => {
  await fetchArticles()
  try { const res = await getCategories(); categories.value = res.data || [] } catch (e) {}
})
</script>

<style scoped>
/* ====== Hero ====== */
.hero {
  text-align: center;
  padding: 48px 24px 36px;
}

.hero-title {
  font-size: 32px;
  font-weight: 700;
  color: var(--color-text);
  margin-bottom: 10px;
  letter-spacing: 1px;
}

.hero-subtitle {
  font-size: 15px;
  color: var(--color-text-secondary);
  font-weight: 400;
}

/* ====== Announcement ====== */
.announcement {
  margin-bottom: 24px;
}

.announcement-inner {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 12px 18px;
  background: var(--color-primary-bg);
  border-radius: var(--radius-sm);
  color: var(--color-primary-dark);
  font-size: 14px;
  line-height: 1.6;
}

/* ====== Articles ====== */
.articles-section {
  max-width: var(--content-wide);
  margin: 0 auto;
}

.empty-state {
  text-align: center;
  padding: 64px 0;
  color: var(--color-text-muted);
}

.empty-state svg {
  margin-bottom: 12px;
  color: var(--color-text-muted);
}

.empty-state p {
  font-size: 15px;
}

.pagination {
  display: flex;
  justify-content: center;
  margin-top: 28px;
  padding-bottom: 12px;
}

/* Override Element Plus pagination */
.pagination :deep(.el-pagination .btn-prev),
.pagination :deep(.el-pagination .btn-next),
.pagination :deep(.el-pagination .el-pager li) {
  border-radius: 6px !important;
  font-weight: 500;
}

.pagination :deep(.el-pagination .el-pager li.is-active) {
  background: var(--color-primary) !important;
}

@media (max-width: 768px) {
  .hero {
    padding: 32px 16px 24px;
  }
  .hero-title {
    font-size: 26px;
  }
}
</style>
