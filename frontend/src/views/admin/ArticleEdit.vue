<template>
  <div class="article-edit">
    <h2 class="page-title">{{ isEdit ? '编辑文章' : '新建文章' }}</h2>

    <el-form :model="form" :rules="rules" ref="formRef" label-width="80px" class="edit-form">
      <el-form-item label="标题" prop="title">
        <el-input v-model="form.title" placeholder="文章标题" maxlength="200" show-word-limit />
      </el-form-item>

      <el-row :gutter="16">
        <el-col :span="12">
          <el-form-item label="分类">
            <el-select v-model="form.categoryId" placeholder="选择分类" clearable style="width:100%">
              <el-option v-for="c in categories" :key="c.id" :label="c.name" :value="c.id" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="标签">
            <el-select v-model="form.tagIds" placeholder="选择标签" multiple style="width:100%">
              <el-option v-for="t in tags" :key="t.id" :label="t.name" :value="t.id" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-form-item label="状态">
        <el-radio-group v-model="form.status">
          <el-radio-button :value="1">已发布</el-radio-button>
          <el-radio-button :value="0">草稿</el-radio-button>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="内容">
        <div class="editor-panels">
          <div class="editor-pane">
            <div class="pane-header">
              <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"><polyline points="16 3 21 3 21 8"/><line x1="4" y1="20" x2="21" y2="3"/><path d="M21 16v5H3V3h12"/></svg>
              Markdown
            </div>
            <textarea
              v-model="form.content"
              class="editor-textarea"
              placeholder="使用 Markdown 格式编写文章..."
            ></textarea>
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
          保存
        </el-button>
        <el-button @click="$router.push('/admin/articles')">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted, inject } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getAdminArticle, createArticle, updateArticle, getAdminCategories, getAdminTags } from '../../api'

const route = useRoute()
const router = useRouter()
const marked = inject('marked')
const formRef = ref(null)
const saving = ref(false)
const categories = ref([])
const tags = ref([])
const form = reactive({
  id: null,
  title: '',
  content: '',
  categoryId: null,
  tagIds: [],
  status: 1
})
const rules = { title: [{ required: true, message: '请输入标题', trigger: 'blur' }] }
const isEdit = computed(() => !!route.params.id)

const renderedContent = computed(() => {
  if (!form.content) return '<p style="color:var(--color-text-muted)">输入内容后在此预览</p>'
  return marked(form.content)
})

async function handleSave() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  saving.value = true
  try {
    if (isEdit.value) {
      await updateArticle({ id: form.id, title: form.title, content: form.content, categoryId: form.categoryId, tagIds: form.tagIds, status: form.status })
    } else {
      await createArticle({ title: form.title, content: form.content, categoryId: form.categoryId, tagIds: form.tagIds, status: form.status })
    }
    ElMessage.success('保存成功')
    router.push('/admin/articles')
  } catch (e) {
  } finally { saving.value = false }
}

onMounted(async () => {
  try { const r = await getAdminCategories(); categories.value = r.data || [] } catch (e) {}
  try { const r = await getAdminTags(); tags.value = r.data || [] } catch (e) {}
  if (isEdit.value) {
    try {
      const res = await getAdminArticle(route.params.id)
      const article = res.data.article
      if (article) {
        form.id = article.id
        form.title = article.title
        form.content = article.content || ''
        form.categoryId = article.categoryId
        form.status = article.status
        form.tagIds = res.data.tagIds || []
      }
    } catch (e) {}
  }
})
</script>

<style scoped>
.page-title {
  font-size: 20px;
  font-weight: 700;
  color: var(--color-text);
  margin-bottom: 24px;
}

.edit-form {
  background: var(--color-bg-white);
  padding: 28px 32px;
  border-radius: var(--radius-md);
  box-shadow: var(--shadow-sm);
  border: 1px solid var(--color-border-light);
}

.edit-form :deep(.el-input__wrapper),
.edit-form :deep(.el-select .el-input__wrapper) {
  border-radius: 8px;
}

.edit-form :deep(.el-radio-button__inner) {
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
  min-height: 500px;
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
  min-height: 500px;
  color: var(--color-text);
  background: var(--color-bg-white);
}

.preview-content {
  flex: 1;
  padding: 16px 20px;
  overflow-y: auto;
}
</style>
