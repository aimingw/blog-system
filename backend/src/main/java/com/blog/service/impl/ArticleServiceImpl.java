package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.dto.ArticleDTO;
import com.blog.dto.ArticleQueryDTO;
import com.blog.entity.Article;
import com.blog.entity.ArticleTag;
import com.blog.mapper.ArticleMapper;
import com.blog.mapper.ArticleTagMapper;
import com.blog.service.ArticleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleMapper articleMapper;
    private final ArticleTagMapper articleTagMapper;

    public ArticleServiceImpl(ArticleMapper articleMapper, ArticleTagMapper articleTagMapper) {
        this.articleMapper = articleMapper;
        this.articleTagMapper = articleTagMapper;
    }

    @Override
    public IPage<Article> pageQuery(ArticleQueryDTO query) {
        if (query.getTagId() != null) {
            List<Article> articles = articleMapper.selectByTagId(query.getTagId());
            long total = articles.size();
            int from = (query.getPage() - 1) * query.getSize();
            int to = Math.min(from + query.getSize(), articles.size());
            if (from >= total) {
                articles = List.of();
            } else {
                articles = articles.subList(from, to);
            }
            Page<Article> page = new Page<>(query.getPage(), query.getSize(), total);
            page.setRecords(articles);
            return page;
        }
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(query.getKeyword())) {
            wrapper.like(Article::getTitle, query.getKeyword());
        }
        if (query.getCategoryId() != null) {
            wrapper.eq(Article::getCategoryId, query.getCategoryId());
        }
        if (query.getStatus() != null) {
            wrapper.eq(Article::getStatus, query.getStatus());
        }
        wrapper.orderByDesc(Article::getCreateTime);
        return articleMapper.selectPage(new Page<>(query.getPage(), query.getSize()), wrapper);
    }

    @Override
    public Article getById(Long id) {
        return articleMapper.selectById(id);
    }

    @Override
    public Article getPrevArticle(Long id) {
        Article current = articleMapper.selectById(id);
        if (current == null) return null;
        return articleMapper.selectOne(new LambdaQueryWrapper<Article>()
                .lt(Article::getId, id)
                .eq(Article::getStatus, 1)
                .orderByDesc(Article::getId)
                .last("LIMIT 1"));
    }

    @Override
    public Article getNextArticle(Long id) {
        Article current = articleMapper.selectById(id);
        if (current == null) return null;
        return articleMapper.selectOne(new LambdaQueryWrapper<Article>()
                .gt(Article::getId, id)
                .eq(Article::getStatus, 1)
                .orderByAsc(Article::getId)
                .last("LIMIT 1"));
    }

    @Override
    @Transactional
    public void saveOrUpdate(ArticleDTO dto) {
        Article article = new Article();
        article.setId(dto.getId());
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        article.setCategoryId(dto.getCategoryId());
        article.setStatus(dto.getStatus() != null ? dto.getStatus() : 1);
        if (StringUtils.hasText(dto.getContent())) {
            String plain = dto.getContent().replaceAll("[#*`>\\-\\[\\]()!_~|]", "")
                    .replaceAll("\\s+", " ").trim();
            article.setSummary(plain.length() > 200 ? plain.substring(0, 200) : plain);
        }
        if (article.getId() == null) {
            articleMapper.insert(article);
        } else {
            articleMapper.updateById(article);
        }
        articleTagMapper.delete(new LambdaQueryWrapper<ArticleTag>()
                .eq(ArticleTag::getArticleId, article.getId()));
        if (dto.getTagIds() != null && !dto.getTagIds().isEmpty()) {
            for (Long tagId : dto.getTagIds()) {
                ArticleTag at = new ArticleTag();
                at.setArticleId(article.getId());
                at.setTagId(tagId);
                articleTagMapper.insert(at);
            }
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        articleMapper.deleteById(id);
        articleTagMapper.delete(new LambdaQueryWrapper<ArticleTag>()
                .eq(ArticleTag::getArticleId, id));
    }
}
