<template>
  <footer class="footer">
    <div class="container footer-inner">
      <div class="footer-brand">
        <span class="footer-logo">{{ siteTitle }}</span>
        <span class="footer-desc">用文字记录思考</span>
      </div>
      <p class="footer-copy">&copy; {{ year }} {{ siteTitle }}. Built with Vue & Spring Boot</p>
    </div>
  </footer>
</template>

<script setup>
import { computed } from 'vue'
import { useAppStore } from '../../stores/app'

const appStore = useAppStore()
const year = new Date().getFullYear()
const siteTitle = computed(() => appStore.siteConfig?.title || 'My Blog')
</script>

<style scoped>
.footer {
  border-top: 1px solid var(--color-border-light);
  padding: 32px 0;
  margin-top: auto;
  background: var(--color-bg-white);
}

.footer-inner {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 12px;
}

.footer-brand {
  display: flex;
  align-items: center;
  gap: 14px;
}

.footer-logo {
  font-size: 15px;
  font-weight: 600;
  color: var(--color-text);
}

.footer-desc {
  font-size: 13px;
  color: var(--color-text-muted);
  padding-left: 14px;
  border-left: 1px solid var(--color-border);
}

.footer-copy {
  font-size: 12px;
  color: var(--color-text-muted);
}
</style>
