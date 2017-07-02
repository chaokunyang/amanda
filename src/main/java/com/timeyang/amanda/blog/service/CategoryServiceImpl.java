package com.timeyang.amanda.blog.service;

import com.timeyang.amanda.blog.domain.Category;
import com.timeyang.amanda.blog.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author chaokunyang
 * @create 2017-04-21
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    @Override
    public Iterable<Category> save(List<Category> categories) {
        return categoryRepository.save(categories);
    }

    @Transactional
    @Override
    public Category getCategoryTree() {
        Category root = categoryRepository.findCategoryByName("Category");
        root.loadChildren();
        return root;
    }

    @Override
    public void delete(Long id) {
        categoryRepository.delete(id);
    }

    @Transactional
    @Override
    public List<Category> getFirstLevelCategoriesAndChildTree() {
        List<Category> categories = categoryRepository.findByLevelOrderByOrderNumberAsc(0);

        categories.forEach(Category::loadChildren); // 加载所有子类目，直到末级类目

        return categories;
    }

    @Transactional
    @Override
    public Category getCategory(Long id) {
        return categoryRepository.findOne(id);
    }

    @Transactional
    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findCategoryByName(name);
    }

    @Transactional
    @Override
    public void deleteAll() {
        categoryRepository.deleteAll();
    }

    @Transactional
    @Override
    public List<Category> getFirstLevelCategories() {

        return categoryRepository.findByLevelOrderByOrderNumberAsc(0);
    }

}
