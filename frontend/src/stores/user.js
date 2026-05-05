import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { getToken, setToken, removeToken } from '../utils/auth'

export const useUserStore = defineStore('user', () => {
  const token = ref(getToken())
  const userInfo = ref(null)

  const isLoggedIn = computed(() => !!token.value)

  function login(t) {
    token.value = t
    setToken(t)
  }

  function logout() {
    token.value = ''
    userInfo.value = null
    removeToken()
  }

  function setUserInfo(info) {
    userInfo.value = info
  }

  return { token, userInfo, isLoggedIn, login, logout, setUserInfo }
})
