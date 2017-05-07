package com.timeyang.amanda.base.jpa.repository;

import com.timeyang.amanda.blog.domain.Category;
import com.timeyang.amanda.blog.repository.CategoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author chaokunyang
 * @create 2017-04-23
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class SelfReferenceRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void deleteById() throws Exception {
        Category category = categoryRepository.findByLevelOrderByOrderNumberAsc(0).get(0);
        categoryRepository.delete(category.getId());
    }

    @Test
    public void deleteEntity() throws Exception {
        Category category = categoryRepository.findByLevelOrderByOrderNumberAsc(0).get(0);
        categoryRepository.delete(category);
    }

    @Test
    public void deleteEntities() {
        List<Category> categories = categoryRepository.findByLevelOrderByOrderNumberAsc(0);
        categoryRepository.delete(categories);
    }

    @Test
    public void deleteAll() throws Exception {
        categoryRepository.deleteAll();
    }

}