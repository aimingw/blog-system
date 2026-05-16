/**
 * 应用全局状态管理（Pinia Store）
 * 管理站点配置等全局共享数据
 */
import { defineStore } from 'pinia'
import { ref } from 'vue'
import { getSiteConfig } from '../api'

export const useAppStore = defineStore('app', () => {
  /** 站点配置数据 */
  const siteConfig = ref(null)

  /** 从后端获取站点配置 */
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
