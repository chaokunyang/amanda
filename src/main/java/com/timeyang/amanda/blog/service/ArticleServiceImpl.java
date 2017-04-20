package com.timeyang.amanda.blog.service;

import com.timeyang.amanda.base.jpa.criterion.QueryCriteria;
import com.timeyang.amanda.base.search.SearchResult;
import com.timeyang.amanda.blog.Article;
import com.timeyang.amanda.blog.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chaokunyang
 * @create 2017-04-20
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Transactional
    @Override
    public Page search(QueryCriteria criteria, Pageable pageable) {
        return articleRepository.search(criteria, pageable);
    }

    @Transactional
    @Override
    public Page<SearchResult<Article>> search(String query, Pageable pageable) {
        return articleRepository.search(query, pageable);
    }
}
