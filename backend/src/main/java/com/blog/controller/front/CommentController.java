package com.blog.controller.front;

import com.blog.common.Result;
import com.blog.dto.CommentDTO;
import com.blog.entity.Comment;
import com.blog.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 前台评论控制器
 * 提供公开的评论提交和查看接口（无需登录）
 */
@RestController("frontCommentController")
@RequestMapping("/api/front/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * 提交评论
     * 提交后需管理员审核通过才会公开展示
     */
    @PostMapping
    public Result<?> submit(@RequestBody CommentDTO dto) {
        Comment comment = new Comment();
        comment.setArticleId(dto.getArticleId());
        comment.setNickName(dto.getNickName());
        comment.setEmail(dto.getEmail());
        comment.setContent(dto.getContent());
        commentService.saveComment(comment);
        return Result.success();
    }

    /**
     * 获取某篇文章的已审核评论列表
     */
    @GetMapping
    public Result<List<Comment>> list(@RequestParam Long articleId) {
        // status=1 只返回已审核通过的评论
        var page = commentService.pageQuery(1, 1000, articleId, 1);
        return Result.success(page.getRecords());
    }
}
