import request from '../utils/request'

// Auth
export function login(data) {
  return request.post('/admin/login', data)
}

// Front Articles
export function getArticles(params) {
  return request.get('/front/articles', { params })
}
export function getArticle(id) {
  return request.get(`/front/articles/${id}`)
}

// Admin Articles
export function getAdminArticles(params) {
  return request.get('/admin/articles', { params })
}
export function getAdminArticle(id) {
  return request.get(`/admin/articles/${id}`)
}
export function createArticle(data) {
  return request.post('/admin/articles', data)
}
export function updateArticle(data) {
  return request.put('/admin/articles', data)
}
export function deleteArticle(id) {
  return request.delete(`/admin/articles/${id}`)
}

// Categories
export function getCategories() {
  return request.get('/front/categories')
}
export function getAdminCategories() {
  return request.get('/admin/categories')
}
export function createCategory(data) {
  return request.post('/admin/categories', data)
}
export function updateCategory(data) {
  return request.put('/admin/categories', data)
}
export function deleteCategory(id) {
  return request.delete(`/admin/categories/${id}`)
}

// Tags
export function getTags() {
  return request.get('/front/tags')
}
export function getAdminTags() {
  return request.get('/admin/tags')
}
export function createTag(data) {
  return request.post('/admin/tags', data)
}
export function updateTag(data) {
  return request.put('/admin/tags', data)
}
export function deleteTag(id) {
  return request.delete(`/admin/tags/${id}`)
}

// Comments
export function getComments(articleId) {
  return request.get('/front/comments', { params: { articleId } })
}
export function submitComment(data) {
  return request.post('/front/comments', data)
}
export function getAdminComments(params) {
  return request.get('/admin/comments', { params })
}
export function updateCommentStatus(id, status) {
  return request.put(`/admin/comments/${id}/status`, { status })
}
export function deleteComment(id) {
  return request.delete(`/admin/comments/${id}`)
}

// Site Config
export function getSiteConfig() {
  return request.get('/front/site-config')
}
export function getAdminSiteConfig() {
  return request.get('/admin/site-config')
}
export function updateSiteConfig(data) {
  return request.put('/admin/site-config', data)
}
