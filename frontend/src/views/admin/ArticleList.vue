<template>
  <div>
    <div class="page-header">
      <h2 class="page-title">文章管理</h2>
      <el-button type="primary" @click="$router.push('/admin/articles/new')">
        <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" style="margin-right:4px;vertical-align:-2px"><line x1="12" y1="5" x2="12" y2="19"/><line x1="5" y1="12" x2="19" y2="12"/></svg>
        新建文章
      </el-button>
    </div>

    <el-table :data="articles" v-loading="loading" class="styled-table">
      <el-table-column prop="id" label="ID" width="70" align="center" />
      <el-table-column prop="title" label="标题" min-width="200" show-overflow-tooltip />
      <el-table-column label="状态" width="90" align="center">
        <template #default="{ row }">
          <span class="status-tag" :class="row.status === 1 ? 'published' : 'draft'">
            {{ row.status === 1 ? '已发布' : '草稿' }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" width="160" align="center">
        <template #default="{ row }">{{ formatDate(row.createTime) }}</template>
      </el-table-column>
      <el-table-column label="操作" width="200" align="center">
        <template #default="{ row }">
          <el-button size="small" text type="primary" @click="$router.push(`/admin/articles/${row.id}/edit`)">编辑</el-button>
          <el-button size="small" text type="danger" @click="handleDelete(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination-wrap" v-if="total > 10">
      <el-pagination background layout="prev, pager, next" :total="total" v-model:current-page="page" @current-change="fetchList" />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getAdminArticles, deleteArticle } from '../../api'
import { ElMessage, ElMessageBox } from 'element-plus'

const articles = ref([])
const loading = ref(false)
const page = ref(1)
const total = ref(0)

function formatDate(s) {
  if (!s) return ''
  return new Date(s).toLocaleDateString('zh-CN', { year: 'numeric', month: '2-digit', day: '2-digit' })
}

async function fetchList() {
  loading.value = true
  try {
    const res = await getAdminArticles({ page: page.value, size: 10 })
    articles.value = res.data.records
    total.value = res.data.total
  } finally { loading.value = false }
}

async function handleDelete(id) {
  try {
    await ElMessageBox.confirm('确定删除该文章？', '提示', { type: 'warning' })
    await deleteArticle(id)
    ElMessage.success('已删除')
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

/* ====== Table ====== */
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

/* Status tags */
.status-tag {
  display: inline-block;
  padding: 2px 10px;
  border-radius: 10px;
  font-size: 12px;
  font-weight: 500;
}

.status-tag.published {
  background: rgba(82, 183, 136, 0.1);
  color: var(--color-success);
}

.status-tag.draft {
  background: rgba(154, 169, 183, 0.12);
  color: var(--color-text-muted);
}

.pagination-wrap {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
}

.pagination-wrap :deep(.el-pagination .el-pager li.is-active) {
  background: var(--color-primary) !important;
}

.pagination-wrap :deep(.el-pagination .btn-prev),
.pagination-wrap :deep(.el-pagination .btn-next),
.pagination-wrap :deep(.el-pagination .el-pager li) {
  border-radius: 6px !important;
}
</style>
