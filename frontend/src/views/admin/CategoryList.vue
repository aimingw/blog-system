<template>
  <div>
    <div class="page-header">
      <h2 class="page-title">分类管理</h2>
      <el-button type="primary" @click="handleAdd">
        <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" style="margin-right:4px;vertical-align:-2px"><line x1="12" y1="5" x2="12" y2="19"/><line x1="5" y1="12" x2="19" y2="12"/></svg>
        新增分类
      </el-button>
    </div>

    <el-table :data="categories" class="styled-table">
      <el-table-column prop="id" label="ID" width="80" align="center" />
      <el-table-column prop="name" label="名称" min-width="200" />
      <el-table-column prop="createTime" label="创建时间" width="180" align="center">
        <template #default="{ row }">{{ formatDate(row.createTime) }}</template>
      </el-table-column>
      <el-table-column label="操作" width="200" align="center">
        <template #default="{ row }">
          <el-button size="small" text type="primary" @click="handleEdit(row)">编辑</el-button>
          <el-button size="small" text type="danger" @click="handleDelete(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" :title="editing.id ? '编辑分类' : '新增分类'" width="420px" class="styled-dialog">
      <el-form :model="editing" :rules="rules" ref="dialogFormRef" label-width="0">
        <el-form-item prop="name">
          <el-input v-model="editing.name" placeholder="分类名称" maxlength="30" size="large" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false" class="cancel-btn">取消</el-button>
        <el-button type="primary" :loading="saving" @click="handleSave" class="confirm-btn">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getAdminCategories, createCategory, updateCategory, deleteCategory } from '../../api'
import { ElMessage, ElMessageBox } from 'element-plus'

const categories = ref([])
const dialogVisible = ref(false)
const saving = ref(false)
const dialogFormRef = ref(null)
const editing = reactive({ id: null, name: '' })
const rules = { name: [{ required: true, message: '请输入名称', trigger: 'blur' }] }

function formatDate(s) {
  if (!s) return ''
  return new Date(s).toLocaleDateString('zh-CN', { year: 'numeric', month: '2-digit', day: '2-digit' })
}

async function fetchList() {
  try { const r = await getAdminCategories(); categories.value = r.data || [] } catch (e) {}
}

function handleAdd() {
  editing.id = null; editing.name = ''
  dialogVisible.value = true
}

function handleEdit(row) {
  editing.id = row.id; editing.name = row.name
  dialogVisible.value = true
}

async function handleSave() {
  const valid = await dialogFormRef.value.validate().catch(() => false)
  if (!valid) return
  saving.value = true
  try {
    if (editing.id) await updateCategory({ id: editing.id, name: editing.name })
    else await createCategory({ name: editing.name })
    ElMessage.success('保存成功')
    dialogVisible.value = false
    fetchList()
  } catch (e) {} finally { saving.value = false }
}

async function handleDelete(id) {
  try {
    await ElMessageBox.confirm('确定删除该分类？', '提示', { type: 'warning' })
    await deleteCategory(id)
    ElMessage.success('删除成功')
    fetchList()
  } catch (e) {}
}

onMounted(fetchList)
</script>

<style scoped>
.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.page-title {
  font-size: 20px;
  font-weight: 700;
  color: var(--color-text);
}

/* Table */
.styled-table {
  border-radius: var(--radius-md);
  overflow: hidden;
  box-shadow: var(--shadow-sm);
  border: 1px solid var(--color-border-light);
}

.styled-table :deep(.el-table__header th) {
  background: #fafbfc;
  color: var(--color-text-secondary);
  font-weight: 600;
  font-size: 13px;
  border-bottom: 1px solid var(--color-border);
  padding: 14px 0;
}

.styled-table :deep(.el-table__body tr:hover > td) {
  background: rgba(74, 111, 165, 0.02);
}

.styled-table :deep(.el-table__body td) {
  padding: 12px 0;
  font-size: 14px;
  border-bottom: 1px solid var(--color-border-light);
}

/* Dialog */
.styled-dialog :deep(.el-dialog) {
  border-radius: var(--radius-lg);
}

.styled-dialog :deep(.el-dialog__header) {
  padding: 20px 24px 0;
}

.styled-dialog :deep(.el-dialog__title) {
  font-weight: 650;
  font-size: 17px;
  color: var(--color-text);
}

.styled-dialog :deep(.el-dialog__body) {
  padding: 20px 24px;
}

.styled-dialog :deep(.el-dialog__footer) {
  padding: 0 24px 20px;
}

.styled-dialog :deep(.el-input__wrapper) {
  border-radius: 8px;
}

.confirm-btn {
  border-radius: 8px;
  padding: 10px 22px;
  background: var(--color-primary);
  border-color: var(--color-primary);
}

.confirm-btn:hover {
  background: var(--color-primary-dark);
  border-color: var(--color-primary-dark);
}

.cancel-btn {
  border-radius: 8px;
  padding: 10px 22px;
}
</style>
