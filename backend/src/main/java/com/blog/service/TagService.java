package com.blog.service;

import com.blog.entity.Tag;
import java.util.List;

/**
 * 标签服务接口
 */
public interface TagService {
    /** 查询所有标签 */
    List<Tag> listAll();
    /** 根据ID查询标签 */
    Tag getById(Long id);
    /** 新增或更新标签 */
    void saveOrUpdate(Tag tag);
    /** 根据ID删除标签 */
    void deleteById(Long id);
}
