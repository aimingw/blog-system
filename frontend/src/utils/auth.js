/**
 * 认证工具模块
 * 使用 localStorage 管理 JWT Token
 */
const TOKEN_KEY = 'token'

/** 获取存储的Token */
export function getToken() {
  return localStorage.getItem(TOKEN_KEY)
}

/** 保存Token到本地存储 */
export function setToken(token) {
  localStorage.setItem(TOKEN_KEY, token)
}

/** 删除本地存储的Token */
export function removeToken() {
  localStorage.removeItem(TOKEN_KEY)
}

/** 判断是否已登录（Token存在即视为已登录） */
export function isLoggedIn() {
  return !!getToken()
}
