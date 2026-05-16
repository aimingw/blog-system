/**
 * 用户状态管理（Pinia Store）
 * 管理登录Token和用户信息，提供登录/登出操作
 */
import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { getToken, setToken, removeToken } from '../utils/auth'

export const useUserStore = defineStore('user', () => {
  /** JWT Token */
  const token = ref(getToken())
  /** 当前用户信息 */
  const userInfo = ref(null)

  /** 是否已登录（Token存在即视为已登录） */
  const isLoggedIn = computed(() => !!token.value)

  /** 登录：保存Token到Store和localStorage */
  function login(t) {
    token.value = t
    setToken(t)
  }

  /** 登出：清除Token和用户信息 */
  function logout() {
    token.value = ''
    userInfo.value = null
    removeToken()
  }

  /** 设置用户信息 */
  function setUserInfo(info) {
    userInfo.value = info
  }

  return { token, userInfo, isLoggedIn, login, logout, setUserInfo }
})
