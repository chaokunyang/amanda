package com.timeyang.amanda.blog.service;

import com.timeyang.amanda.blog.domain.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author chaokunyang
 * @create 2017-04-21
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void getAllCategory() throws Exception {
        List<Category> categories = categoryService.getAllCategory();
        System.out.println(categories);
    }

}