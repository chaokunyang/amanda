package com.timeyang.amanda.blog.service;

import com.timeyang.amanda.core.jpa.criterion.QueryCriteria;
import com.timeyang.amanda.core.search.SearchResult;
import com.timeyang.amanda.blog.domain.Article;
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

    Page<SearchResult<Article>> search(String query, Pageable pageable);

}
