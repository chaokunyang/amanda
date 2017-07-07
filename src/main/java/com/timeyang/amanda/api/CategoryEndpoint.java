package com.timeyang.amanda.api;

import com.timeyang.amanda.blog.domain.Category;
import com.timeyang.amanda.blog.domain.CategoryPathNode;
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
    public Category getRootCategoryTree() {
        LOGGER.info("获取分类树");
        return categoryService.getRootCategoryTree();
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

    @RequestMapping(value = "/path/{id}", method = RequestMethod.GET)
    public List<CategoryPathNode> getCategoryPath(@PathVariable Long id) {
        LOGGER.info("获取指定分类路径，分类id: " + id);
        return categoryService.getCategoryPath(id);
    }

    @RequestMapping(value = "/tree/{id}", method = RequestMethod.GET)
    public Category getCategoryTree(@PathVariable Long id) {
        LOGGER.info("获取指定分类，分类id: " + id);
        return categoryService.getCategoryTree(id);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Category update(@RequestBody Category category) {
        LOGGER.info("更新分类: {}", category);
        return categoryService.save(category);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Category create(@RequestBody Category category) {
        LOGGER.info("创建分类: {}", category);
        return categoryService.create(category);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        LOGGER.info("删除分类，分类id: {}", id);
        categoryService.delete(id);
    }

}
