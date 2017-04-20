package com.timeyang.amanda.blog.repository;

import com.timeyang.amanda.base.jpa.criterion.AbstractJpaCriterionRepository;
import com.timeyang.amanda.base.search.SearchResult;
import com.timeyang.amanda.base.search.SearchableRepository;
import com.timeyang.amanda.blog.Article;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.FatalBeanException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.jpa.EntityManagerProxy;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用抽象父类的JPA criterion search方法实现，作为Spring Data 仓库方法实现，即为Spring Data提供search方法的实现，用于在运行时合并进SpringD创建的代理
 * 创建Lucene搜索方法实现
 * @author chaokunyang
 * @create 2017-04-17
 */
public class ArticleRepositoryImpl extends AbstractJpaCriterionRepository<Article> implements SearchableRepository<Article> {

    private EntityManagerProxy entityManagerProxy;

    @Override
    public Page<SearchResult<Article>> search(String query, Pageable pageable) {
        FullTextEntityManager manager = this.getFullTextEntityManager();

        QueryBuilder builder = manager.getSearchFactory().buildQueryBuilder()
                .forEntity(Article.class).get();

        Query luceneQuery = builder.keyword()
                .onFields("title", "keywords", "mdBody", "user.username")
                .matching(query)
                .createQuery();

        FullTextQuery q = manager.createFullTextQuery(luceneQuery, Article.class);
        q.setProjection(FullTextQuery.THIS, FullTextQuery.SCORE);

        long total = q.getResultSize();

        q.setFirstResult(pageable.getOffset())
                .setMaxResults(pageable.getPageSize());

        @SuppressWarnings("unchecked")
        List<Object[]> results = q.getResultList();
        List<SearchResult<Article>> list = new ArrayList<>();
        results.forEach(o -> list.add(
                new SearchResult<>((Article) o[0], (Float)o[1])
        ));

        return new PageImpl<>(list, pageable, total);
    }

    private FullTextEntityManager getFullTextEntityManager() {
        return Search.getFullTextEntityManager(
                this.entityManagerProxy.getTargetEntityManager());
    }

    @PostConstruct
    public void initialize() {
        if(!(this.entityManager instanceof EntityManagerProxy))
            throw new FatalBeanException("Entity manager " + this.entityManager + " was not a proxy");

        this.entityManagerProxy = (EntityManagerProxy) this.entityManager;
    }

}