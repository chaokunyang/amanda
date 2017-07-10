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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * @author chaokunyang
 * @create 2017-04-25
 */
@Controller
public class HomeController {
    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = {"/", "/list"}, method = RequestMethod.GET)
    public String index(Pageable pageable, Map<String, Object> model) {
        List<Category> categories = categoryService.getFirstLevelCategoriesAndChildTree();
        if(pageable.getPageSize() >= 20) {
            pageable = new PageRequest(pageable.getPageNumber(), 5);
        }
        Page<Article> articles = articleService.getPublishedArticles(pageable);

        PageWrapper<Article> page = new PageWrapper<>(articles, "list");

        model.put("categories", categories);
        model.put("page", page);
        model.put("articles", articles);

        return "index";
    }

}
