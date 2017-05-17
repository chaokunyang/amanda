package com.timeyang.amanda.blog.service;

import com.timeyang.amanda.core.jpa.criterion.QueryCriteria;
import com.timeyang.amanda.core.search.SearchResult;
import com.timeyang.amanda.blog.domain.Article;
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

    @Transactional // 必须包含该行，不只是懒加载问题，而是需要 transactional EntityManager available (无法得到FullTextEntityManager)
    @Override
    public Page<SearchResult<Article>> search(String query, Pageable pageable) {
        return articleRepository.search(query, pageable);
    }
}
