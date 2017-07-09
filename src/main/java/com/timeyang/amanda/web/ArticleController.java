package com.timeyang.amanda.web;

import com.timeyang.amanda.blog.domain.Article;
import com.timeyang.amanda.blog.service.ArticleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * @author chaokunyang
 * @create 2017-04-18
 */
@Controller
@RequestMapping("articles")
public class ArticleController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/{id}/**", method = RequestMethod.GET)
    public String getArticle(@PathVariable Long id, Map<String, Object> model) {
        Article article = articleService.viewArticle(id);
        model.put("article", article);
        return "article";
    }

}
