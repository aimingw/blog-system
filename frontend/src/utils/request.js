/**
 * Axios 请求模块
 * 封装 Axios 实例，包含请求拦截和响应拦截处理
 */
import axios from 'axios'
import { ElMessage } from 'element-plus'
import { getToken, removeToken } from './auth'

const request = axios.create({
  baseURL: '/api',      // 通过 Vite 代理转发到后端
  timeout: 15000         // 请求超时时间（毫秒）
})

// 请求拦截器：自动在请求头中附加 JWT Token
request.interceptors.request.use(
  (config) => {
    const token = getToken()
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => Promise.reject(error)
)

// 响应拦截器：统一处理响应数据和错误
request.interceptors.response.use(
  (response) => {
    const data = response.data
    // 业务状态码为200时直接返回数据
    if (data.code === 200) {
      return data
    }
    // 其他业务错误码统一提示
    ElMessage.error(data.message || 'Request failed')
    // 401未授权：清除Token并跳转到登录页
    if (data.code === 401) {
      removeToken()
      window.location.href = '/admin/login'
    }
    return Promise.reject(new Error(data.message))
  },
  (error) => {
    // 网络错误统一提示
    ElMessage.error(error.message || 'Network error')
    return Promise.reject(error)
  }
)

export default request
