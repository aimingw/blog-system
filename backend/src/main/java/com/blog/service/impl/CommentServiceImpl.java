package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.entity.Comment;
import com.blog.mapper.CommentMapper;
import com.blog.service.CommentService;
import org.springframework.stereotype.Service;

/**
 * 评论服务实现类
 */
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    /**
     * 分页查询评论（支持按文章ID和审核状态筛选）
     */
    @Override
    public IPage<Comment> pageQuery(Integer page, Integer size, Long articleId, Integer status) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        if (articleId != null) {
            wrapper.eq(Comment::getArticleId, articleId);
        }
        if (status != null) {
            wrapper.eq(Comment::getStatus, status);
        }
        wrapper.orderByDesc(Comment::getCreateTime);
        return commentMapper.selectPage(new Page<>(page, size), wrapper);
    }

    @Override
    public Comment getById(Long id) {
        return commentMapper.selectById(id);
    }

    /**
     * 保存评论，默认状态为待审核（0）
     */
    @Override
    public void saveComment(Comment comment) {
        comment.setStatus(0); // 新评论默认待审核
        commentMapper.insert(comment);
    }

    /**
     * 更新评论审核状态
     */
    @Override
    public void updateStatus(Long id, Integer status) {
        Comment comment = new Comment();
        comment.setId(id);
        comment.setStatus(status);
        commentMapper.updateById(comment);
    }

    @Override
    public void deleteById(Long id) {
        commentMapper.deleteById(id);
    }

    /**
     * 统计某篇文章已审核通过的评论数
     */
    @Override
    public long countByArticleId(Long articleId) {
        return commentMapper.selectCount(new LambdaQueryWrapper<Comment>()
                .eq(Comment::getArticleId, articleId)
                .eq(Comment::getStatus, 1));
    }
}
