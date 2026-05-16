<template>
  <div class="front-layout">
    <!-- 顶部导航栏 -->
    <Navbar />
    <!-- 页面主体内容 -->
    <main class="page-content">
      <div class="container">
        <router-view />
      </div>
    </main>
    <!-- 页脚 -->
    <Footer />
  </div>
</template>

<script setup>
import Navbar from '../../components/front/Navbar.vue'
import Footer from '../../components/front/Footer.vue'
import { useAppStore } from '../../stores/app'
import { onMounted } from 'vue'

const appStore = useAppStore()

/** 页面挂载时加载站点配置（标题、公告等全局信息） */
onMounted(() => {
  appStore.fetchSiteConfig()
})
</script>

<style scoped>
.front-layout {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}
</style>
