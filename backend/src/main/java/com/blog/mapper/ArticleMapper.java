package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.entity.Article;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 文章数据访问接口
 * 继承MyBatis-Plus BaseMapper获得通用CRUD能力
 */
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 根据标签ID查询已发布的文章列表
     * 通过文章-标签中间表进行联表查询
     */
    @Select("SELECT t_article.* FROM t_article " +
            "LEFT JOIN t_article_tag ON t_article.id = t_article_tag.article_id " +
            "WHERE t_article_tag.tag_id = #{tagId} AND t_article.status = 1 " +
            "ORDER BY t_article.create_time DESC")
    List<Article> selectByTagId(@Param("tagId") Long tagId);
}
