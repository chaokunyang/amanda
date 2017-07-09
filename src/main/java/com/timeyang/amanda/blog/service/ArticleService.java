package com.timeyang.amanda.blog.service;

import com.timeyang.amanda.blog.domain.Article;
import com.timeyang.amanda.core.jpa.criterion.QueryCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

/**
 * 文章服务
 *
 * @author chaokunyang
 * @create 2017-04-20
 */
@Validated
public interface ArticleService {

    Page search(QueryCriteria criteria, Pageable pageable);

    Page<Article> search(String query, Pageable pageable);

    Page<Article> getArticles(Pageable pageable);

    Page<Article> getPublishedArticles(Pageable pageable);

    /**
     * 获取文章API，不改变文章阅读次数
     * @param id
     * @return
     */
    Article getArticle(Long id);

    /**
     * 网站用户查看文章，每调用一次文章阅读次数加一
     * @param id
     * @return
     */
    Article viewArticle(Long id);

    Article save(Article article);

    Article publishArticle(Long id);

    Article cancelPublish(Long id);

    void delete(Long id);
}
