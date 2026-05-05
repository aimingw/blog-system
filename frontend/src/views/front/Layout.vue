<template>
  <div class="front-layout">
    <Navbar />
    <main class="page-content">
      <div class="container">
        <router-view />
      </div>
    </main>
    <Footer />
  </div>
</template>

<script setup>
import Navbar from '../../components/front/Navbar.vue'
import Footer from '../../components/front/Footer.vue'
import { useAppStore } from '../../stores/app'
import { onMounted } from 'vue'

const appStore = useAppStore()
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
