import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

/**
 * Vite 构建配置
 */
export default defineConfig({
  plugins: [vue()],
  server: {
    port: 5173, // 开发服务器端口
    proxy: {
      // 将 /api 请求代理到后端 Spring Boot 服务
      '/api': {
        target: 'http://localhost:8088',
        changeOrigin: true
      }
    }
  }
})
