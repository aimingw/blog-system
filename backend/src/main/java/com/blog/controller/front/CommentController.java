package com.blog.controller.front;

import com.blog.common.Result;
import com.blog.dto.CommentDTO;
import com.blog.entity.Comment;
import com.blog.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("frontCommentController")
@RequestMapping("/api/front/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

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

    @GetMapping
    public Result<List<Comment>> list(@RequestParam Long articleId) {
        var page = commentService.pageQuery(1, 1000, articleId, 1);
        return Result.success(page.getRecords());
    }
}
