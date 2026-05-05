import { defineStore } from 'pinia'
import { ref } from 'vue'
import { getSiteConfig } from '../api'

export const useAppStore = defineStore('app', () => {
  const siteConfig = ref(null)

  async function fetchSiteConfig() {
    try {
      const res = await getSiteConfig()
      siteConfig.value = res.data
    } catch (e) {
      // ignore
    }
  }

  return { siteConfig, fetchSiteConfig }
})
