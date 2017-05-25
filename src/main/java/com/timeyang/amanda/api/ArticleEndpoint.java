package com.timeyang.amanda.api;

import com.timeyang.amanda.blog.domain.Article;
import com.timeyang.amanda.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文章API
 *
 * @author chaokunyang
 */
@RestController
@RequestMapping("api/articles")
public class ArticleEndpoint {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(method = RequestMethod.GET)
    public Page<Article> articles(Pageable pageable) {
        return articleService.articles(pageable);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Article article(@PathVariable Long id) {
        return articleService.article(id);
    }


}
