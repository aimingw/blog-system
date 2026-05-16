package com.blog.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.blog.common.Result;
import com.blog.dto.PageResult;
import com.blog.entity.Comment;
import com.blog.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 后台评论管理控制器
 * 提供评论的查询、审核、删除接口（管理员权限）
 */
@RestController("adminCommentController")
@RequestMapping("/api/admin/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * 分页查询评论列表（支持按文章ID和审核状态筛选）
     */
    @GetMapping
    public Result<PageResult<Comment>> list(@RequestParam(defaultValue = "1") Integer page,
                                            @RequestParam(defaultValue = "10") Integer size,
                                            @RequestParam(required = false) Long articleId,
                                            @RequestParam(required = false) Integer status) {
        IPage<Comment> p = commentService.pageQuery(page, size, articleId, status);
        PageResult<Comment> result = new PageResult<>();
        result.setRecords(p.getRecords());
        result.setTotal(p.getTotal());
        result.setPage(p.getCurrent());
        result.setSize(p.getSize());
        return Result.success(result);
    }

    /**
     * 更新评论审核状态（通过/拒绝）
     */
    @PutMapping("/{id}/status")
    public Result<?> updateStatus(@PathVariable Long id, @RequestBody Map<String, Integer> body) {
        commentService.updateStatus(id, body.get("status"));
        return Result.success();
    }

    /**
     * 删除评论
     */
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        commentService.deleteById(id);
        return Result.success();
    }
}
