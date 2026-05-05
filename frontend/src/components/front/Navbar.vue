<template>
  <header class="navbar" :class="{ scrolled: scrolled }">
    <div class="container nav-inner">
      <router-link to="/" class="logo">{{ siteTitle }}</router-link>
      <nav class="nav-links">
        <router-link to="/">首页</router-link>
        <router-link to="/about">关于</router-link>
      </nav>
      <button class="menu-btn" @click="menuOpen = !menuOpen" :aria-label="menuOpen ? '关闭菜单' : '打开菜单'">
        <span></span>
        <span></span>
        <span></span>
      </button>
    </div>
    <div class="mobile-nav" :class="{ open: menuOpen }">
      <router-link to="/" @click="menuOpen = false">首页</router-link>
      <router-link to="/about" @click="menuOpen = false">关于</router-link>
    </div>
  </header>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { useAppStore } from '../../stores/app'

const appStore = useAppStore()
const menuOpen = ref(false)
const scrolled = ref(false)

const siteTitle = computed(() => appStore.siteConfig?.title || 'My Blog')

function onScroll() {
  scrolled.value = window.scrollY > 10
}

onMounted(() => window.addEventListener('scroll', onScroll, { passive: true }))
onUnmounted(() => window.removeEventListener('scroll', onScroll))
</script>

<style scoped>
.navbar {
  position: sticky;
  top: 0;
  z-index: 100;
  background: rgba(255, 255, 255, 0.8);
  backdrop-filter: blur(12px);
  -webkit-backdrop-filter: blur(12px);
  border-bottom: 1px solid transparent;
  transition: border-color 0.25s ease, box-shadow 0.25s ease;
}

.navbar.scrolled {
  border-bottom-color: var(--color-border);
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.04);
}

.nav-inner {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 56px;
}

.logo {
  font-size: 18px;
  font-weight: 700;
  color: var(--color-text) !important;
  letter-spacing: 0.5px;
}

.nav-links {
  display: flex;
  gap: 4px;
}

.nav-links a {
  padding: 6px 16px;
  border-radius: 6px;
  color: var(--color-text-secondary);
  font-size: 14px;
  font-weight: 500;
  transition: all var(--transition-fast);
}

.nav-links a:hover {
  color: var(--color-primary);
  background: var(--color-primary-bg);
}

.nav-links a.router-link-active {
  color: var(--color-primary);
  background: var(--color-primary-bg);
}

/* Mobile menu button */
.menu-btn {
  display: none;
  background: none;
  border: none;
  cursor: pointer;
  padding: 4px;
  width: 32px;
  height: 32px;
  position: relative;
}

.menu-btn span {
  display: block;
  width: 20px;
  height: 2px;
  background: var(--color-text);
  border-radius: 2px;
  position: absolute;
  left: 6px;
  transition: all 0.25s ease;
}

.menu-btn span:nth-child(1) { top: 9px; }
.menu-btn span:nth-child(2) { top: 15px; }
.menu-btn span:nth-child(3) { top: 21px; }

.mobile-nav {
  display: none;
}

@media (max-width: 768px) {
  .nav-links { display: none; }
  .menu-btn { display: block; }

  .mobile-nav {
    display: flex;
    flex-direction: column;
    background: #fff;
    border-top: 1px solid var(--color-border);
    max-height: 0;
    overflow: hidden;
    transition: max-height 0.3s ease;
  }

  .mobile-nav.open {
    max-height: 160px;
  }

  .mobile-nav a {
    padding: 14px 24px;
    color: var(--color-text-secondary);
    font-size: 15px;
    border-bottom: 1px solid var(--color-border-light);
    transition: background var(--transition-fast);
  }

  .mobile-nav a:hover {
    background: var(--color-bg);
  }
}
</style>
