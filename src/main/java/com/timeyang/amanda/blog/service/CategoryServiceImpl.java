package com.timeyang.amanda.blog.service;

import com.timeyang.amanda.blog.domain.Category;
import com.timeyang.amanda.blog.domain.CategoryPathNode;
import com.timeyang.amanda.blog.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
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
        Category c = categoryRepository.save(category);
        return c;
    }

    @Transactional
    @Override
    public Iterable<Category> save(List<Category> categories) {
        return categoryRepository.save(categories);
    }

    @Transactional
    @Override
    public Category getRootCategoryTree() {
        Category root = categoryRepository.findCategoryByName("Category");
        root.loadChildren();
        return root;
    }

    @Transactional
    @Override
    public void delete(Long id) {
        categoryRepository.delete(id);
    }

    @Transactional
    @Override
    public Category getCategoryTree(Long id) {
        Category c = categoryRepository.findOne(id);
        c.loadChildren();
        return c;
    }

    @Transactional
    @Override
    public Category create(Category category) {
        Category c = categoryRepository.save(category);
        return c;
    }

    @Transactional
    @Override
    public List<CategoryPathNode> getCategoryPath(Long id) {
        LinkedList<CategoryPathNode> path = new LinkedList<>();
        fillCategoryPath(path, id);
        return path;
    }

    private void fillCategoryPath(LinkedList<CategoryPathNode> path, Long id) {
        Category c = categoryRepository.findOne(id);
        // CategoryPathNode pathNode = new CategoryPathNode(c.getLevel(), c.getOrderNumber(), c.getId(), c.getParentId());
        CategoryPathNode pathNode = new CategoryPathNode(c.getLevel(), c.getId());
        path.addFirst(pathNode);
        if(c.getLevel() >= 0 && c.getParentId() != null) {
            fillCategoryPath(path, c.getParentId());
        }
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
