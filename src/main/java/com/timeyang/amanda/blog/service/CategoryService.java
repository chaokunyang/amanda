package com.timeyang.amanda.blog.service;

import com.timeyang.amanda.blog.domain.Category;

import java.util.List;

/**
 * @author chaokunyang
 * @create 2017-04-21
 */
public interface CategoryService {

    Category save(Category category);

    Iterable<Category> save(List<Category> categories);

    List<Category> getFirstLevelCategoriesAndChildTree();

    Category getCategory(Long id);

    Category getCategoryByName(String name);

    void deleteAll();

    List<Category> getFirstLevelCategories();

    Category getCategoryTree();

    void delete(Long id);
}
