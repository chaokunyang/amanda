package com.timeyang.amanda.api;

import com.timeyang.amanda.blog.domain.Category;
import com.timeyang.amanda.blog.service.CategoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Category rest端点
 * @author chaokunyang
 * @create 2017-04-25
 */
@RestController
@RequestMapping(value = "/api/categories")
public class CategoryEndpoint {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(method = RequestMethod.GET)
    public Category allCategories() {
        LOGGER.info("获取分类树");
        return categoryService.getCategoryTree();
    }

    @RequestMapping(value = "/first_levels", method = RequestMethod.GET)
    public List<Category> getFirstLevelCategories() {
        LOGGER.info("获取第一级所有分类");
        return categoryService.getFirstLevelCategories();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Category getCategory(@PathVariable Long id) {
        LOGGER.info("获取指定分类，分类id: " + id);
        return categoryService.getCategory(id);
    }

    @RequestMapping
    public Category save(@RequestBody Category category) {
        LOGGER.info("保存分类: {}", category);
        return categoryService.save(category);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void save(@PathVariable Long id) {
        LOGGER.info("删除分类，分类id: {}", id);
        categoryService.delete(id);
    }

}
