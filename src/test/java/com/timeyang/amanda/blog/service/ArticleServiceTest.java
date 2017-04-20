package com.timeyang.amanda.blog.service;

import com.timeyang.amanda.base.search.SearchResult;
import com.timeyang.amanda.blog.Article;
import com.timeyang.amanda.config.DatabaseInitializer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * ArticleService测试
 *
 * @author chaokunyang
 * @create 2017-04-20
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private DatabaseInitializer databaseInitializer;

    @Before
    public void populate() {
        databaseInitializer.deleteAll();
        databaseInitializer.populate();
    }

    @Test
    public void search() {
        Pageable pageable = new PageRequest(0, 10);
        Page<SearchResult<Article>> results = articleService.search("amanda", pageable);
        System.out.println(results);
        Assert.assertTrue( results.getTotalPages() >= 1);
    }

}