package com.timeyang.amanda.api;

import com.timeyang.amanda.blog.domain.Article;
import com.timeyang.amanda.blog.service.ArticleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger LOGGER = LogManager.getLogger();

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
        LOGGER.info("保存文章");
        return articleService.save(article);
    }

    @RequestMapping(value = "/publish/{id}", method = RequestMethod.PUT)
    public Article publishArticle(@PathVariable Long id) {
        LOGGER.info("发布文章");
        return articleService.publishArticle(id);
    }

    @RequestMapping(value = "/publish/cancel/{id}", method = RequestMethod.PUT)
    public Article cancelPublish(@PathVariable Long id) {
        LOGGER.info("取消发布");
        return articleService.cancelPublish(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        LOGGER.info("删除文章，文章ID: {}", id);
        articleService.delete(id);
    }

}
