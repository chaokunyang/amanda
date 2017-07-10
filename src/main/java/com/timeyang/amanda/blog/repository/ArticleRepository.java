package com.timeyang.amanda.blog.repository;

import com.timeyang.amanda.blog.domain.Article;
import com.timeyang.amanda.core.jpa.criterion.CriterionRepository;
import com.timeyang.amanda.core.search.SearchableRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 文章仓库
 *
 * @author chaokunyang
 * @create 2017-04-17
 */
public interface ArticleRepository extends PagingAndSortingRepository<Article, Long>, CriterionRepository<Article>, SearchableRepository<Article> {

    Page<Article> findArticleByPublishedIsTrueOrderByPublishedDateDesc(Pageable pageable);

    @Query("select a from Article a where a.published = true order by a.views desc, a.publishedDate desc")
    Page<Article> findPopular(Pageable pageable);

}
