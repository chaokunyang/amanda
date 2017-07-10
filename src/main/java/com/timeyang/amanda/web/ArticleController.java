package com.timeyang.amanda.web;

import com.timeyang.amanda.blog.domain.Article;
import com.timeyang.amanda.blog.domain.Category;
import com.timeyang.amanda.blog.service.ArticleService;
import com.timeyang.amanda.blog.service.CategoryService;
import com.timeyang.amanda.core.jpa.domain.PageWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author chaokunyang
 * @create 2017-04-18
 */
@Controller
public class ArticleController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "articles/{id}/**", method = RequestMethod.GET)
    public String getArticle(@PathVariable Long id, Map<String, Object> model) {
        Article article = articleService.viewArticle(id);
        model.put("article", article);
        return "article";
    }


    @RequestMapping(value = {"search"}, method = RequestMethod.GET)
    public String search(Pageable pageable, @RequestParam(value = "query", defaultValue = "") String query, Map<String, Object> model) {
        List<Category> categories = categoryService.getFirstLevelCategoriesAndChildTree();
        if(pageable == null) {
            pageable = new PageRequest(0, 5);
        }else if(pageable.getPageSize() > 50) {
            pageable = new PageRequest(pageable.getPageNumber(), 5);
        }

        Page<Article> articles;
        if("".equals(query)) {
            articles = articleService.getPublishedArticles(pageable);
        }else {
            articles = articleService.search(query, pageable);
        }

        PageWrapper<Article> page = new PageWrapper<>(articles, "list");

        model.put("categories", categories);
        model.put("articles", articles);
        model.put("page", page);

        return "search";
    }

    @RequestMapping(value = {"popular", "hottest"}, method = RequestMethod.GET)
    public String getPopular(Pageable pageable, Map<String, Object> model) {
        List<Category> categories = categoryService.getFirstLevelCategoriesAndChildTree();

        Page<Article> articles = articleService.getPopularArticles(pageable);

        PageWrapper<Article> page = new PageWrapper<>(articles, "popular");

        model.put("categories", categories);
        model.put("articles", articles);
        model.put("page", page);

        return "popular";
    }


}
