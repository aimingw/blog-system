<template>
  <div class="about-page">
    <!-- 关于页面头部 -->
    <header class="about-header">
      <div class="about-avatar">
        <svg width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" stroke-linecap="round"><path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/></svg>
      </div>
      <h1 class="about-title">关于</h1>
      <p class="about-subtitle">了解这个博客背后的故事</p>
    </header>

    <!-- 关于内容（Markdown 渲染） -->
    <div class="about-content md-content" v-html="renderedContent"></div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, inject } from 'vue'
import { getSiteConfig } from '../../api'

/** 注入 Markdown 渲染函数 */
const marked = inject('marked')
/** 关于页面的 Markdown 原始内容 */
const content = ref('')

/** 渲染 Markdown 为 HTML */
const renderedContent = computed(() => {
  if (!content.value) return '<p style="color: var(--color-text-muted); text-align: center; padding: 32px 0;">暂无内容</p>'
  return marked(content.value)
})

/** 页面加载时获取站点配置中的"关于"内容 */
onMounted(async () => {
  try {
    const res = await getSiteConfig()
    content.value = res.data?.aboutContent || ''
  } catch (e) {}
})
</script>

<style scoped>
.about-page {
  max-width: var(--content-width);
  margin: 0 auto;
  background: var(--color-bg-white);
  border-radius: var(--radius-lg);
  padding: 40px 44px;
  box-shadow: var(--shadow-sm);
}

.about-header {
  text-align: center;
  margin-bottom: 36px;
}

.about-avatar {
  width: 72px;
  height: 72px;
  border-radius: 50%;
  background: linear-gradient(135deg, var(--color-primary-light) 0%, var(--color-primary) 100%);
  display: inline-flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  margin-bottom: 16px;
  box-shadow: 0 4px 16px rgba(74, 111, 165, 0.2);
}

.about-title {
  font-size: 26px;
  font-weight: 700;
  color: var(--color-text);
  margin-bottom: 6px;
}

.about-subtitle {
  font-size: 14px;
  color: var(--color-text-muted);
}

@media (max-width: 768px) {
  .about-page {
    padding: 28px 20px;
    border-radius: var(--radius-md);
  }
}
</style>
