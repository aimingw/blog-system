<template>
  <div class="login-page">
    <!-- Geometric pattern background -->
    <div class="bg-pattern"></div>

    <div class="login-card">
      <div class="login-header">
        <div class="login-avatar">
          <svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round"><path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/></svg>
        </div>
        <h1>博客管理后台</h1>
        <p>登录以继续管理您的博客</p>
      </div>

      <el-form ref="formRef" :model="form" :rules="rules" class="login-form" @keyup.enter="handleLogin">
        <el-form-item prop="username">
          <el-input
            v-model="form.username"
            placeholder="用户名"
            size="large"
            :prefix-icon="User"
          />
        </el-form-item>

        <el-form-item prop="password">
          <el-input
            v-model="form.password"
            type="password"
            placeholder="密码"
            size="large"
            show-password
            :prefix-icon="Lock"
          />
        </el-form-item>

        <el-form-item>
          <el-button
            type="primary"
            size="large"
            :loading="loading"
            @click="handleLogin"
            class="submit-btn"
          >
            <span v-if="!loading">登 录</span>
          </el-button>
        </el-form-item>
      </el-form>
    </div>

    <p class="login-footer">&copy; {{ new Date().getFullYear() }} Blog System</p>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { User, Lock } from '@element-plus/icons-vue'
import { login } from '../../api'
import { useUserStore } from '../../stores/user'

const router = useRouter()
const userStore = useUserStore()
const formRef = ref(null)
const loading = ref(false)
const form = reactive({ username: '', password: '' })
const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

async function handleLogin() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  loading.value = true
  try {
    const res = await login({ username: form.username, password: form.password })
    userStore.login(res.data.token)
    userStore.setUserInfo(res.data.user)
    ElMessage.success('登录成功')
    router.push('/admin/dashboard')
  } catch (e) {
    // error handled by interceptor
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background: var(--color-bg);
  position: relative;
  overflow: hidden;
}

/* Subtle geometric pattern */
.bg-pattern {
  position: absolute;
  inset: 0;
  background-image:
    radial-gradient(circle at 20% 50%, rgba(74, 111, 165, 0.04) 0%, transparent 50%),
    radial-gradient(circle at 80% 20%, rgba(74, 111, 165, 0.04) 0%, transparent 50%),
    radial-gradient(circle at 40% 80%, rgba(74, 111, 165, 0.03) 0%, transparent 50%);
  pointer-events: none;
}

/* Card */
.login-card {
  position: relative;
  z-index: 1;
  width: 400px;
  max-width: 92vw;
  background: var(--color-bg-white);
  border-radius: var(--radius-lg);
  padding: 44px 40px;
  box-shadow: var(--shadow-md);
  border: 1px solid var(--color-border-light);
}

.login-header {
  text-align: center;
  margin-bottom: 32px;
}

.login-avatar {
  width: 56px;
  height: 56px;
  border-radius: 50%;
  background: var(--color-primary-bg);
  color: var(--color-primary);
  display: inline-flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 14px;
}

.login-header h1 {
  font-size: 22px;
  font-weight: 700;
  color: var(--color-text);
  margin-bottom: 6px;
  letter-spacing: 1px;
}

.login-header p {
  font-size: 14px;
  color: var(--color-text-secondary);
}

/* Form */
.login-form :deep(.el-input--large) {
  --el-input-border-radius: 8px;
}

.login-form :deep(.el-input__wrapper) {
  border-radius: 8px;
  box-shadow: 0 0 0 1px var(--color-border) inset;
  transition: all var(--transition-base);
  background: var(--color-bg);
}

.login-form :deep(.el-input__wrapper:hover) {
  box-shadow: 0 0 0 1px var(--color-border) inset;
  background: var(--color-bg-white);
}

.login-form :deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 0 0 1px var(--color-primary) inset, 0 0 0 3px var(--color-primary-bg);
}

.submit-btn {
  width: 100%;
  height: 44px;
  border-radius: 8px;
  font-size: 15px;
  font-weight: 600;
  letter-spacing: 4px;
  margin-top: 4px;
  background: var(--color-primary);
  border-color: var(--color-primary);
  transition: all var(--transition-base);
}

.submit-btn:hover {
  background: var(--color-primary-dark);
  border-color: var(--color-primary-dark);
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(74, 111, 165, 0.3);
}

.submit-btn:active {
  transform: translateY(0);
}

.login-footer {
  position: relative;
  z-index: 1;
  margin-top: 20px;
  font-size: 12px;
  color: var(--color-text-muted);
}
</style>
