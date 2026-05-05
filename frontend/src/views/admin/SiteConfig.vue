<template>
  <div>
    <h2 class="page-title">站点设置</h2>

    <el-form :model="form" ref="formRef" label-width="100px" class="settings-form">
      <el-form-item label="网站标题">
        <el-input v-model="form.title" placeholder="网站标题" maxlength="50" />
      </el-form-item>

      <el-form-item label="副标题">
        <el-input v-model="form.subtitle" placeholder="一句话简介" maxlength="100" />
      </el-form-item>

      <el-form-item label="公告">
        <el-input v-model="form.announcement" type="textarea" :rows="2" placeholder="网站公告内容" maxlength="200" show-word-limit />
      </el-form-item>

      <el-form-item label="关于页面">
        <div class="editor-panels">
          <div class="editor-pane">
            <div class="pane-header">
              <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"><polyline points="16 3 21 3 21 8"/><line x1="4" y1="20" x2="21" y2="3"/><path d="M21 16v5H3V3h12"/></svg>
              Markdown
            </div>
            <textarea v-model="form.aboutContent" class="editor-textarea" placeholder="使用 Markdown 格式编写关于页面..."></textarea>
          </div>
          <div class="editor-pane">
            <div class="pane-header">
              <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"><rect x="2" y="3" width="20" height="14" rx="2"/><line x1="8" y1="21" x2="16" y2="21"/><line x1="12" y1="17" x2="12" y2="21"/></svg>
              预览
            </div>
            <div class="preview-content md-content" v-html="renderedContent"></div>
          </div>
        </div>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" :loading="saving" @click="handleSave" class="save-btn">
          <svg v-if="!saving" width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" style="margin-right:4px;vertical-align:-2px"><path d="M19 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11l5 5v11a2 2 0 0 1-2 2z"/><polyline points="17 21 17 13 7 13 7 21"/></svg>
          保存设置
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { reactive, ref, computed, onMounted, inject } from 'vue'
import { getAdminSiteConfig, updateSiteConfig } from '../../api'
import { ElMessage } from 'element-plus'

const marked = inject('marked')
const formRef = ref(null)
const saving = ref(false)
const form = reactive({ title: '', subtitle: '', announcement: '', aboutContent: '' })

const renderedContent = computed(() => {
  if (!form.aboutContent) return '<p style="color:var(--color-text-muted)">输入内容后在此预览</p>'
  return marked(form.aboutContent)
})

async function handleSave() {
  saving.value = true
  try {
    await updateSiteConfig(form)
    ElMessage.success('设置已保存')
  } catch (e) {} finally { saving.value = false }
}

onMounted(async () => {
  try {
    const res = await getAdminSiteConfig()
    const data = res.data
    if (data) {
      form.title = data.title || ''
      form.subtitle = data.subtitle || ''
      form.announcement = data.announcement || ''
      form.aboutContent = data.aboutContent || ''
    }
  } catch (e) {}
})
</script>

<style scoped>
.page-title {
  font-size: 20px;
  font-weight: 700;
  color: var(--color-text);
  margin-bottom: 24px;
}

.settings-form {
  background: var(--color-bg-white);
  padding: 28px 32px;
  border-radius: var(--radius-md);
  box-shadow: var(--shadow-sm);
  border: 1px solid var(--color-border-light);
}

.settings-form :deep(.el-input__wrapper),
.settings-form :deep(.el-textarea__inner) {
  border-radius: 8px;
}

.save-btn {
  border-radius: 8px;
  padding: 10px 24px;
  font-weight: 500;
  background: var(--color-primary);
  border-color: var(--color-primary);
}

.save-btn:hover {
  background: var(--color-primary-dark);
  border-color: var(--color-primary-dark);
}

/* Editor panels */
.editor-panels {
  display: flex;
  gap: 12px;
  width: 100%;
  min-height: 400px;
}

.editor-pane {
  flex: 1;
  border: 1px solid var(--color-border);
  border-radius: var(--radius-md);
  display: flex;
  flex-direction: column;
  overflow: hidden;
  min-width: 0;
}

.pane-header {
  background: #fafbfc;
  padding: 8px 14px;
  font-size: 12px;
  color: var(--color-text-secondary);
  border-bottom: 1px solid var(--color-border);
  font-weight: 600;
  display: flex;
  align-items: center;
  gap: 6px;
  letter-spacing: 1px;
}

.editor-textarea {
  flex: 1;
  border: none;
  resize: none;
  padding: 16px;
  font-family: var(--font-mono);
  font-size: 14px;
  line-height: 1.8;
  outline: none;
  min-height: 400px;
  color: var(--color-text);
  background: var(--color-bg-white);
}

.preview-content {
  flex: 1;
  padding: 16px 20px;
  overflow-y: auto;
}
</style>
