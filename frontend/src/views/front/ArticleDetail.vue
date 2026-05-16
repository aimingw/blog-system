<template>
  <div class="article-page" v-if="article">
    <!-- 阅读进度条 -->
    <div class="progress-bar" :style="{ width: progressPercent + '%' }"></div>

    <article class="article-detail">
      <!-- 文章头部：标题 + 元信息 -->
      <header class="article-header">
        <h1 class="article-title">{{ article.title }}</h1>
        <div class="article-meta">
          <span class="meta-item">
            <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"><rect x="3" y="4" width="18" height="18" rx="2"/><line x1="16" y1="2" x2="16" y2="6"/><line x1="8" y1="2" x2="8" y2="6"/><line x1="3" y1="10" x2="21" y2="10"/></svg>
            {{ formatDate(article.createTime) }}
          </span>
          <!-- 所属分类 -->
          <span v-if="category" class="meta-item meta-category">
            <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"><path d="M22 19a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h5l2 3h9a2 2 0 0 1 2 2z"/></svg>
            {{ category.name }}
          </span>
        </div>
      </header>

      <!-- 文章正文（Markdown 渲染） -->
      <div class="article-body md-content" v-html="renderedContent"></div>

      <!-- 赞赏区域 -->
      <div class="appreciation">
        <div class="appreciation-divider">
          <span>— 感谢阅读 —</span>
        </div>
        <p class="appreciation-hint">如果文章对你有帮助，欢迎赞赏支持</p>
        <div class="appreciation-icon">
          <svg width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" stroke-linecap="round"><path d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z"/></svg>
        </div>
      </div>

      <!-- 上一篇 / 下一篇导航 -->
      <nav class="article-nav">
        <div class="nav-item prev" v-if="prev">
          <router-link :to="`/article/${prev.id}`">
            <span class="nav-label">上一篇</span>
            <span class="nav-title">{{ prev.title }}</span>
          </router-link>
        </div>
        <div class="nav-item next" v-if="next">
          <router-link :to="`/article/${next.id}`">
            <span class="nav-label">下一篇</span>
            <span class="nav-title">{{ next.title }}</span>
          </router-link>
        </div>
        <div v-if="!prev && !next" class="nav-empty"></div>
      </nav>
    </article>

    <!-- 评论区 -->
    <div class="comments-wrapper">
      <CommentSection :article-id="article.id" />
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted, onUnmounted, inject } from 'vue'
import { useRoute } from 'vue-router'
import { getArticle, getCategories } from '../../api'
import CommentSection from '../../components/front/CommentSection.vue'

const route = useRoute()
/** 注入 Markdown 渲染函数 */
const marked = inject('marked')

/** 文章数据 */
const article = ref(null)
/** 上一篇文章 */
const prev = ref(null)
/** 下一篇文章 */
const next = ref(null)
/** 分类列表 */
const categories = ref([])
/** 阅读进度百分比 */
const progressPercent = ref(0)

/** 计算当前文章所属分类 */
const category = computed(() => {
  if (!article.value?.categoryId) return null
  return categories.value.find(c => c.id === article.value.categoryId)
})

/** 渲染 Markdown 内容为 HTML */
const renderedContent = computed(() => {
  if (!article.value?.content) return ''
  return marked(article.value.content)
})

/** 格式化日期 */
function formatDate(dateStr) {
  if (!dateStr) return ''
  return new Date(dateStr).toLocaleDateString('zh-CN', { year: 'numeric', month: 'long', day: 'numeric' })
}

/** 监听滚动事件，更新阅读进度条 */
function handleScroll() {
  const scrollTop = window.scrollY
  const docHeight = document.documentElement.scrollHeight - window.innerHeight
  if (docHeight <= 0) {
    progressPercent.value = 0
    return
  }
  progressPercent.value = Math.min(100, Math.round((scrollTop / docHeight) * 100))
}

async function fetchArticle(id) {
  try {
    const res = await getArticle(id)
    article.value = res.data.article
    prev.value = res.data.prev
    next.value = res.data.next
  } catch (e) {}
}

onMounted(async () => {
  window.addEventListener('scroll', handleScroll, { passive: true })
  await fetchArticle(route.params.id)
  try { const r = await getCategories(); categories.value = r.data || [] } catch (e) {}
})

watch(() => route.params.id, async (newId) => {
  await fetchArticle(newId)
  window.scrollTo({ top: 0 })
})

/** 组件卸载时移除滚动监听 */
onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll)
})
</script>

<style scoped>
/* ====== 阅读进度条 ====== */
.progress-bar {
  position: fixed;
  top: 0;
  left: 0;
  height: 3px;
  background: var(--color-primary);
  z-index: 200;
  transition: width 0.1s linear;
  border-radius: 0 2px 2px 0;
}

/* ====== 文章详情 ====== */
.article-page {
  max-width: var(--content-width);
  margin: 0 auto;
}

.article-detail {
  background: var(--color-bg-white);
  border-radius: var(--radius-lg);
  padding: 40px 44px;
  box-shadow: var(--shadow-sm);
}

.article-header {
  margin-bottom: 32px;
}

.article-title {
  font-size: 28px;
  font-weight: 700;
  line-height: 1.4;
  color: var(--color-text);
  margin-bottom: 14px;
}

.article-meta {
  display: flex;
  gap: 20px;
  flex-wrap: wrap;
}

.meta-item {
  display: inline-flex;
  align-items: center;
  gap: 5px;
  font-size: 13px;
  color: var(--color-text-muted);
}

.meta-category {
  background: var(--color-primary-bg);
  color: var(--color-primary);
  padding: 3px 12px;
  border-radius: 12px;
  font-weight: 500;
  font-size: 12px;
}

/* ====== 赞赏区 ====== */
.appreciation {
  text-align: center;
  padding: 36px 0 12px;
  margin-top: 32px;
}

.appreciation-divider {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-bottom: 16px;
}

.appreciation-divider::before,
.appreciation-divider::after {
  content: '';
  flex: 1;
  height: 1px;
  background: var(--color-border);
}

.appreciation-divider span {
  font-size: 14px;
  color: var(--color-text-muted);
  white-space: nowrap;
}

.appreciation-hint {
  font-size: 13px;
  color: var(--color-text-muted);
  margin-bottom: 16px;
}

.appreciation-icon {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 64px;
  height: 64px;
  border-radius: 50%;
  background: var(--color-primary-bg);
  color: var(--color-primary);
  transition: all var(--transition-base);
  cursor: default;
}

.appreciation-icon:hover {
  background: rgba(74, 111, 165, 0.12);
  transform: scale(1.05);
}

/* ====== 上一篇/下一篇导航 ====== */
.article-nav {
  display: flex;
  gap: 16px;
  margin-top: 32px;
  padding-top: 24px;
  border-top: 1px solid var(--color-border);
}

.nav-item {
  flex: 1;
}

.nav-item a {
  display: flex;
  flex-direction: column;
  gap: 4px;
  padding: 16px 20px;
  border-radius: var(--radius-md);
  background: var(--color-bg);
  border: 1px solid var(--color-border-light);
  transition: all var(--transition-base);
  color: var(--color-text);
}

.nav-item a:hover {
  border-color: var(--color-primary);
  background: var(--color-primary-bg);
  color: var(--color-primary);
}

.nav-label {
  font-size: 12px;
  color: var(--color-text-muted);
  letter-spacing: 1px;
}

.nav-title {
  font-size: 14px;
  font-weight: 500;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.next {
  text-align: right;
}

/* ====== 评论区 ====== */
.comments-wrapper {
  max-width: var(--content-width);
  margin: 40px auto;
  background: var(--color-bg-white);
  border-radius: var(--radius-lg);
  padding: 32px 44px;
  box-shadow: var(--shadow-sm);
}

@media (max-width: 768px) {
  .article-detail {
    padding: 24px 20px;
    border-radius: var(--radius-md);
  }
  .article-title {
    font-size: 22px;
  }
  .comments-wrapper {
    padding: 24px 20px;
    border-radius: var(--radius-md);
  }
  .article-nav {
    flex-direction: column;
    gap: 8px;
  }
}
</style>
