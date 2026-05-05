<template>
  <div class="category-page">
    <header class="page-header">
      <h2 class="page-title">
        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"><path d="M22 19a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h5l2 3h9a2 2 0 0 1 2 2z"/></svg>
        分类：{{ categoryName }}
      </h2>
      <p class="page-count" v-if="total">共 {{ total }} 篇文章</p>
    </header>

    <div class="articles-list">
      <ArticleCard v-for="article in articles" :key="article.id" :article="article" :categories="categories" />
      <div v-if="articles.length === 0" class="empty-state">
        <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" stroke-linecap="round"><path d="M22 19a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h5l2 3h9a2 2 0 0 1 2 2z"/></svg>
        <p>该分类下暂无文章</p>
      </div>
      <div class="pagination" v-if="total > size">
        <el-pagination background layout="prev, pager, next" :total="total" :page-size="size" v-model:current-page="page" @current-change="fetchArticles" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getArticles, getCategories } from '../../api'
import ArticleCard from '../../components/front/ArticleCard.vue'

const route = useRoute()
const articles = ref([])
const categories = ref([])
const categoryName = ref('')
const page = ref(1)
const size = ref(10)
const total = ref(0)

async function fetchArticles() {
  const res = await getArticles({ page: page.value, size: size.value, categoryId: route.params.id })
  articles.value = res.data.records
  total.value = res.data.total
}

onMounted(async () => {
  try { const r = await getCategories(); categories.value = r.data || [] } catch (e) {}
  const cat = categories.value.find(c => c.id == route.params.id)
  categoryName.value = cat?.name || ''
  await fetchArticles()
})
</script>

<style scoped>
.category-page {
  max-width: var(--content-wide);
  margin: 0 auto;
}

.page-header {
  margin-bottom: 24px;
}

.page-title {
  font-size: 22px;
  font-weight: 700;
  color: var(--color-text);
  display: flex;
  align-items: center;
  gap: 8px;
}

.page-count {
  font-size: 13px;
  color: var(--color-text-muted);
  margin-top: 4px;
  margin-left: 28px;
}

.articles-list {
  /* same as home */
}

.empty-state {
  text-align: center;
  padding: 64px 0;
  color: var(--color-text-muted);
}

.empty-state svg {
  margin-bottom: 12px;
}

.empty-state p {
  font-size: 15px;
}

.pagination {
  display: flex;
  justify-content: center;
  margin-top: 28px;
}

.pagination :deep(.el-pagination .el-pager li.is-active) {
  background: var(--color-primary) !important;
}
</style>
