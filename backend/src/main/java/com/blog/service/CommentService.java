package com.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blog.entity.Comment;

/**
 * 评论服务接口
 */
public interface CommentService {
    /** 分页查询评论（支持按文章和状态筛选） */
    IPage<Comment> pageQuery(Integer page, Integer size, Long articleId, Integer status);
    /** 根据ID查询评论 */
    Comment getById(Long id);
    /** 保存新评论 */
    void saveComment(Comment comment);
    /** 更新评论审核状态 */
    void updateStatus(Long id, Integer status);
    /** 删除评论 */
    void deleteById(Long id);
    /** 统计某篇文章的评论数 */
    long countByArticleId(Long articleId);
}
