/**
 * API 接口模块
 * 集中管理所有后端接口调用，按模块分组
 */
import request from '../utils/request'

// ====== 认证相关 ======
/** 管理员登录 */
export function login(data) {
  return request.post('/admin/login', data)
}

// ====== 前台文章 ======
/** 获取已发布文章列表（分页） */
export function getArticles(params) {
  return request.get('/front/articles', { params })
}
/** 获取文章详情 */
export function getArticle(id) {
  return request.get(`/front/articles/${id}`)
}

// ====== 后台文章管理 ======
/** 获取文章列表（含草稿） */
export function getAdminArticles(params) {
  return request.get('/admin/articles', { params })
}
/** 获取文章详情（含标签ID列表） */
export function getAdminArticle(id) {
  return request.get(`/admin/articles/${id}`)
}
/** 新建文章 */
export function createArticle(data) {
  return request.post('/admin/articles', data)
}
/** 更新文章 */
export function updateArticle(data) {
  return request.put('/admin/articles', data)
}
/** 删除文章 */
export function deleteArticle(id) {
  return request.delete(`/admin/articles/${id}`)
}

// ====== 分类 ======
/** 前台获取分类列表 */
export function getCategories() {
  return request.get('/front/categories')
}
/** 后台获取分类列表 */
export function getAdminCategories() {
  return request.get('/admin/categories')
}
/** 新建分类 */
export function createCategory(data) {
  return request.post('/admin/categories', data)
}
/** 更新分类 */
export function updateCategory(data) {
  return request.put('/admin/categories', data)
}
/** 删除分类 */
export function deleteCategory(id) {
  return request.delete(`/admin/categories/${id}`)
}

// ====== 标签 ======
/** 前台获取标签列表 */
export function getTags() {
  return request.get('/front/tags')
}
/** 后台获取标签列表 */
export function getAdminTags() {
  return request.get('/admin/tags')
}
/** 新建标签 */
export function createTag(data) {
  return request.post('/admin/tags', data)
}
/** 更新标签 */
export function updateTag(data) {
  return request.put('/admin/tags', data)
}
/** 删除标签 */
export function deleteTag(id) {
  return request.delete(`/admin/tags/${id}`)
}

// ====== 评论 ======
/** 前台获取已审核评论 */
export function getComments(articleId) {
  return request.get('/front/comments', { params: { articleId } })
}
/** 前台提交评论 */
export function submitComment(data) {
  return request.post('/front/comments', data)
}
/** 后台获取评论列表 */
export function getAdminComments(params) {
  return request.get('/admin/comments', { params })
}
/** 更新评论审核状态 */
export function updateCommentStatus(id, status) {
  return request.put(`/admin/comments/${id}/status`, { status })
}
/** 删除评论 */
export function deleteComment(id) {
  return request.delete(`/admin/comments/${id}`)
}

// ====== 站点配置 ======
/** 前台获取站点配置 */
export function getSiteConfig() {
  return request.get('/front/site-config')
}
/** 后台获取站点配置 */
export function getAdminSiteConfig() {
  return request.get('/admin/site-config')
}
/** 更新站点配置 */
export function updateSiteConfig(data) {
  return request.put('/admin/site-config', data)
}
