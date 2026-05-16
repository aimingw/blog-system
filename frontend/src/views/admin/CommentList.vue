<template>
  <div>
    <h2 class="page-title">评论管理</h2>

    <!-- 评论列表表格 -->
    <el-table :data="comments" v-loading="loading" class="styled-table">
      <el-table-column prop="id" label="ID" width="70" align="center" />
      <el-table-column prop="nickName" label="评论者" width="120" />
      <el-table-column prop="content" label="内容" min-width="200" show-overflow-tooltip />
      <el-table-column prop="articleId" label="文章ID" width="90" align="center" />
      <!-- 审核状态列 -->
      <el-table-column label="状态" width="90" align="center">
        <template #default="{ row }">
          <span class="status-tag" :class="statusClass(row.status)">
            {{ statusText(row.status) }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" width="160" align="center">
        <template #default="{ row }">{{ formatDate(row.createTime) }}</template>
      </el-table-column>
      <!-- 操作列：待审核评论可进行通过/拒绝操作 -->
      <el-table-column label="操作" width="240" align="center">
        <template #default="{ row }">
          <el-button v-if="row.status === 0" size="small" text type="success" @click="handleApprove(row.id)">通过</el-button>
          <el-button v-if="row.status === 0" size="small" text type="warning" @click="handleReject(row.id)">拒绝</el-button>
          <el-button size="small" text type="danger" @click="handleDelete(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div class="pagination-wrap" v-if="total > 10">
      <el-pagination background layout="prev, pager, next" :total="total" v-model:current-page="page" @current-change="fetchList" />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getAdminComments, updateCommentStatus, deleteComment } from '../../api'
import { ElMessage, ElMessageBox } from 'element-plus'

/** 评论列表 */
const comments = ref([])
/** 加载状态 */
const loading = ref(false)
/** 当前页码 */
const page = ref(1)
/** 总记录数 */
const total = ref(0)

/** 格式化日期 */
function formatDate(s) {
  if (!s) return ''
  return new Date(s).toLocaleDateString('zh-CN', { year: 'numeric', month: '2-digit', day: '2-digit' })
}

/** 审核状态文本映射 */
function statusText(s) { return s === 1 ? '已通过' : s === 2 ? '已拒绝' : '待审核' }
/** 审核状态样式映射 */
function statusClass(s) { return s === 1 ? 'approved' : s === 2 ? 'rejected' : 'pending' }

/** 获取评论列表 */
async function fetchList() {
  loading.value = true
  try {
    const res = await getAdminComments({ page: page.value, size: 10 })
    comments.value = res.data.records
    total.value = res.data.total
  } finally { loading.value = false }
}

/** 审核通过评论 */
async function handleApprove(id) {
  await updateCommentStatus(id, 1)
  ElMessage.success('已通过')
  fetchList()
}

/** 拒绝评论 */
async function handleReject(id) {
  await updateCommentStatus(id, 2)
  ElMessage.success('已拒绝')
  fetchList()
}

/** 删除评论（带确认弹窗） */
async function handleDelete(id) {
  try {
    await ElMessageBox.confirm('确定删除该评论？', '提示', { type: 'warning' })
    await deleteComment(id)
    ElMessage.success('已删除')
    fetchList()
  } catch (e) {}
}

onMounted(fetchList)
</script>

<style scoped>
.page-title {
  font-size: 20px;
  font-weight: 700;
  color: var(--color-text);
  margin-bottom: 20px;
}

/* 表格样式 */
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

/* 状态标签 */
.status-tag {
  display: inline-block;
  padding: 2px 10px;
  border-radius: 10px;
  font-size: 12px;
  font-weight: 500;
}

.status-tag.approved {
  background: rgba(82, 183, 136, 0.1);
  color: var(--color-success);
}

.status-tag.rejected {
  background: rgba(229, 115, 115, 0.1);
  color: var(--color-danger);
}

.status-tag.pending {
  background: rgba(230, 162, 60, 0.1);
  color: var(--color-warning);
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
