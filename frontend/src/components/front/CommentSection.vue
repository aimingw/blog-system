<template>
  <div class="comment-section">
    <h3 class="comment-heading">
      <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"><path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"/></svg>
      评论
      <span class="comment-count">{{ comments.length }}</span>
    </h3>

    <!-- Comment Form -->
    <div class="comment-form">
      <el-form :model="form" :rules="rules" ref="formRef" @keyup.enter="handleSubmit">
        <el-row :gutter="14">
          <el-col :span="12">
            <el-form-item prop="nickName">
              <el-input v-model="form.nickName" placeholder="昵称" maxlength="30" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="email">
              <el-input v-model="form.email" placeholder="邮箱" maxlength="60" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item prop="content">
          <el-input
            v-model="form.content"
            type="textarea"
            :rows="3"
            placeholder="写下你的想法..."
            maxlength="500"
            show-word-limit
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="submitting" @click="handleSubmit" class="submit-btn">
            发表评论
          </el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- Comments List -->
    <div v-if="comments.length === 0" class="no-comments">
      <svg width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" stroke-linecap="round"><path d="M21 11.5a8.38 8.38 0 0 1-.9 3.8 8.5 8.5 0 0 1-7.6 4.7 8.38 8.38 0 0 1-3.8-.9L3 21l1.9-5.7a8.38 8.38 0 0 1-.9-3.8 8.5 8.5 0 0 1 4.7-7.6 8.38 8.38 0 0 1 3.8-.9h.5a8.48 8.48 0 0 1 8 8v.5z"/></svg>
      <p>还没有评论，来说两句</p>
    </div>

    <div v-for="c in comments" :key="c.id" class="comment-item">
      <div class="comment-header">
        <span class="comment-avatar">{{ c.nickName.charAt(0) }}</span>
        <div class="comment-author">
          <strong>{{ c.nickName }}</strong>
          <span class="comment-time">{{ formatDate(c.createTime) }}</span>
        </div>
      </div>
      <p class="comment-content">{{ c.content }}</p>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getComments, submitComment } from '../../api'
import { ElMessage } from 'element-plus'

const props = defineProps({ articleId: { type: Number, required: true } })

const comments = ref([])
const submitting = ref(false)
const formRef = ref(null)
const form = reactive({ nickName: '', email: '', content: '' })
const rules = {
  nickName: [{ required: true, message: '请输入昵称', trigger: 'blur' }],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '邮箱格式不正确', trigger: 'blur' }
  ],
  content: [{ required: true, message: '请输入评论内容', trigger: 'blur' }]
}

async function handleSubmit() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  submitting.value = true
  try {
    await submitComment({ articleId: props.articleId, ...form })
    ElMessage.success('评论已提交，审核后显示')
    form.nickName = ''; form.email = ''; form.content = ''
    await fetchComments()
  } finally {
    submitting.value = false
  }
}

async function fetchComments() {
  try {
    const res = await getComments(props.articleId)
    comments.value = res.data || []
  } catch (e) { /* ignore */ }
}

function formatDate(dateStr) {
  if (!dateStr) return ''
  return new Date(dateStr).toLocaleDateString('zh-CN', { year: 'numeric', month: 'long', day: 'numeric' })
}

onMounted(fetchComments)
</script>

<style scoped>
.comment-heading {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 18px;
  font-weight: 600;
  color: var(--color-text);
  margin-bottom: 20px;
}

.comment-count {
  font-size: 13px;
  font-weight: 500;
  color: var(--color-text-muted);
  background: var(--color-bg);
  padding: 2px 10px;
  border-radius: 10px;
}

.comment-form {
  margin-bottom: 24px;
}

.submit-btn {
  border-radius: 8px;
  padding: 10px 28px;
  font-weight: 500;
  letter-spacing: 1px;
  background: var(--color-primary);
  border-color: var(--color-primary);
}

.submit-btn:hover {
  background: var(--color-primary-dark);
  border-color: var(--color-primary-dark);
}

/* ====== Empty ====== */
.no-comments {
  text-align: center;
  padding: 36px 0;
  color: var(--color-text-muted);
}

.no-comments svg {
  margin-bottom: 10px;
}

.no-comments p {
  font-size: 14px;
}

/* ====== Comment Items ====== */
.comment-item {
  padding: 18px 0;
  border-bottom: 1px solid var(--color-border-light);
}

.comment-item:last-child {
  border-bottom: none;
}

.comment-header {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 8px;
}

.comment-avatar {
  flex-shrink: 0;
  width: 36px;
  height: 36px;
  border-radius: 50%;
  background: var(--color-primary-bg);
  color: var(--color-primary);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 14px;
  font-weight: 600;
}

.comment-author {
  display: flex;
  flex-direction: column;
  gap: 2px;
}

.comment-author strong {
  font-size: 14px;
  color: var(--color-text);
}

.comment-time {
  font-size: 12px;
  color: var(--color-text-muted);
}

.comment-content {
  color: var(--color-text-secondary);
  font-size: 14px;
  line-height: 1.8;
  padding-left: 48px;
}

/* ====== Override Element Plus form ====== */
.comment-form :deep(.el-input__wrapper) {
  border-radius: 8px;
}

.comment-form :deep(.el-textarea__inner) {
  border-radius: 8px;
}
</style>
