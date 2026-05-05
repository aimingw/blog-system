package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.entity.Article;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ArticleMapper extends BaseMapper<Article> {

    @Select("SELECT t_article.* FROM t_article " +
            "LEFT JOIN t_article_tag ON t_article.id = t_article_tag.article_id " +
            "WHERE t_article_tag.tag_id = #{tagId} AND t_article.status = 1 " +
            "ORDER BY t_article.create_time DESC")
    List<Article> selectByTagId(@Param("tagId") Long tagId);
}
