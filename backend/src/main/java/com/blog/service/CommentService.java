package com.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blog.entity.Comment;

public interface CommentService {
    IPage<Comment> pageQuery(Integer page, Integer size, Long articleId, Integer status);
    Comment getById(Long id);
    void saveComment(Comment comment);
    void updateStatus(Long id, Integer status);
    void deleteById(Long id);
    long countByArticleId(Long articleId);
}
