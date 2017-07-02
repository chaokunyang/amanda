package com.timeyang.amanda.blog.service;

import com.timeyang.amanda.blog.domain.Article;
import com.timeyang.amanda.blog.repository.ArticleRepository;
import com.timeyang.amanda.core.jpa.criterion.QueryCriteria;
import com.timeyang.amanda.core.search.SearchResult;
import com.timeyang.amanda.user.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

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
    public Page<Article> search(QueryCriteria criteria, Pageable pageable) {
        return articleRepository.search(criteria, pageable);
    }

    @Transactional // 必须包含该行，不只是懒加载问题，而是需要 transactional EntityManager available (无法得到FullTextEntityManager)
    @Override
    public Page<SearchResult<Article>> search(String query, Pageable pageable) {
        return articleRepository.search(query, pageable);
    }

    @Transactional
    @Override
    public Page<Article> getArticles(Pageable pageable) {
        Page<Article> articles = articleRepository.findAll(pageable);
        return articles;
    }

    @Transactional
    @Override
    public Article viewArticle(Long id) {
        Article article = articleRepository.findOne(id);
        if(article.getPublished()) {
            article.setViews(article.getViews() == null ? 1 :  article.getViews() + 1);
        }
        return articleRepository.save(article);
    }

    @Transactional
    @Override
    public Article getArticle(Long id) {
        return articleRepository.findOne(id);
    }

    @Override
    public Article save(Article article) {
        if(article.getUser() == null) {
            article.setUser(UserUtils.getCurrentUser());
        }
        return articleRepository.save(article);
    }

    @Override
    public Article publishArticle(Long id) {
        Article article = articleRepository.findOne(id);
        article.setPublished(true);
        article.setPublishedDate(Instant.now());
        return articleRepository.save(article);
    }

    @Override
    public Article cancelPublish(Long id) {
        Article article = articleRepository.findOne(id);
        article.setPublished(false);
        return articleRepository.save(article);
    }

    @Override
    public void delete(Long id) {
        articleRepository.delete(id);
    }
}
