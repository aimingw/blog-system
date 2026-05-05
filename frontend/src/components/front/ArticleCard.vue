<template>
  <article class="article-card" @click="$router.push(`/article/${article.id}`)">
    <div class="card-body">
      <h2 class="card-title">{{ article.title }}</h2>
      <p class="card-summary">{{ article.summary || '暂无摘要' }}</p>
      <div class="card-meta">
        <span class="meta-date">
          <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"><rect x="3" y="4" width="18" height="18" rx="2"/><line x1="16" y1="2" x2="16" y2="6"/><line x1="8" y1="2" x2="8" y2="6"/><line x1="3" y1="10" x2="21" y2="10"/></svg>
          {{ formatDate(article.createTime) }}
        </span>
        <span v-if="category" class="meta-category">
          <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"><path d="M22 19a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h5l2 3h9a2 2 0 0 1 2 2z"/></svg>
          {{ category.name }}
        </span>
      </div>
    </div>
    <div class="card-arrow">
      <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"><path d="M5 12h14M12 5l7 7-7 7"/></svg>
    </div>
  </article>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  article: { type: Object, required: true },
  categories: { type: Array, default: () => [] }
})

const category = computed(() => {
  if (!props.article.categoryId) return null
  return props.categories.find(c => c.id === props.article.categoryId)
})

function formatDate(dateStr) {
  if (!dateStr) return ''
  const d = new Date(dateStr)
  return d.toLocaleDateString('zh-CN', { year: 'numeric', month: 'long', day: 'numeric' })
}
</script>

<style scoped>
.article-card {
  display: flex;
  align-items: center;
  background: var(--color-bg-white);
  border-radius: var(--radius-md);
  padding: 22px 26px;
  margin-bottom: 12px;
  cursor: pointer;
  box-shadow: var(--shadow-sm);
  border: 1px solid transparent;
  transition: all var(--transition-base);
}

.article-card:hover {
  box-shadow: var(--shadow-hover);
  transform: translateY(-2px);
  border-color: var(--color-border);
}

.card-body {
  flex: 1;
  min-width: 0;
}

.card-title {
  font-size: 18px;
  font-weight: 650;
  color: var(--color-text);
  margin-bottom: 6px;
  line-height: 1.4;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.card-summary {
  font-size: 14px;
  color: var(--color-text-secondary);
  line-height: 1.7;
  margin-bottom: 10px;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.card-meta {
  display: flex;
  gap: 20px;
  font-size: 13px;
  color: var(--color-text-muted);
}

.meta-date,
.meta-category {
  display: inline-flex;
  align-items: center;
  gap: 4px;
}

.meta-category {
  background: var(--color-primary-bg);
  color: var(--color-primary);
  padding: 2px 10px;
  border-radius: 12px;
  font-size: 12px;
  font-weight: 500;
}

.card-arrow {
  flex-shrink: 0;
  margin-left: 16px;
  color: var(--color-text-muted);
  opacity: 0;
  transform: translateX(-8px);
  transition: all var(--transition-base);
}

.article-card:hover .card-arrow {
  opacity: 1;
  transform: translateX(0);
  color: var(--color-primary);
}
</style>
