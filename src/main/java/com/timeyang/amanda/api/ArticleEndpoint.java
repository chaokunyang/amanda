package com.timeyang.amanda.api;

import com.timeyang.amanda.blog.domain.Article;
import com.timeyang.amanda.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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
    public Page<Article> getArticles(Pageable pageable) {
        return articleService.getArticles(pageable);
    }

    @RequestMapping(value = "/{id}/**", method = RequestMethod.GET)
    public Article getArticle(@PathVariable Long id) {
        return articleService.getArticle(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Article saveArticle(@RequestBody Article article) {
        Article result = articleService.save(article);
        return result;
    }

}
