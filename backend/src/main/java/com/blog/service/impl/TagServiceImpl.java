package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blog.entity.ArticleTag;
import com.blog.entity.Tag;
import com.blog.mapper.ArticleTagMapper;
import com.blog.mapper.TagMapper;
import com.blog.service.TagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 标签服务实现类
 */
@Service
public class TagServiceImpl implements TagService {

    private final TagMapper tagMapper;
    private final ArticleTagMapper articleTagMapper;

    public TagServiceImpl(TagMapper tagMapper, ArticleTagMapper articleTagMapper) {
        this.tagMapper = tagMapper;
        this.articleTagMapper = articleTagMapper;
    }

    @Override
    public List<Tag> listAll() {
        return tagMapper.selectList(null);
    }

    @Override
    public Tag getById(Long id) {
        return tagMapper.selectById(id);
    }

    @Override
    public void saveOrUpdate(Tag tag) {
        if (tag.getId() == null) {
            tagMapper.insert(tag);
        } else {
            tagMapper.updateById(tag);
        }
    }

    /**
     * 删除标签及其所有关联关系（事务保证一致性）
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        tagMapper.deleteById(id);
        // 同时删除该标签与文章的所有关联
        articleTagMapper.delete(new LambdaQueryWrapper<ArticleTag>()
                .eq(ArticleTag::getTagId, id));
    }
}
