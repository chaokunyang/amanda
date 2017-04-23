package com.timeyang.amanda.blog.service;

import com.timeyang.amanda.blog.domain.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author chaokunyang
 * @create 2017-04-21
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void save() {
        // categoryService.deleteAll();

        Category lang = new Category("编程语言", 0, 0, null, null);
        List<Category> langs = Arrays.asList(
                new Category("Java", 1, 0, lang, null),
                new Category("Scala", 1, 1, lang, null),
                new Category("Clojure", 1, 2, lang, null),
                new Category("Groovy", 1, 3, lang, null),
                new Category("Python", 1, 4, lang, null),
                new Category("EcmaScript", 1, 5, lang, null)
        );
        lang.setChildren(langs);

        Category reactive = new Category("响应式编程", 0, 3, null, null);
        List<Category> reactives = Arrays.asList(
                new Category("Akka", 1, 0, reactive, null),
                new Category("Spring Reactor", 1, 1, reactive, null),
                new Category("Vert.x", 1, 2, reactive, null)
        );
        reactive.setChildren(reactives);

        List<Category> categories = Arrays.asList(
                lang,
                new Category("微服务", 0, 1, null, null),
                new Category("Streaming", 0, 2, null, null),
                reactive,
                new Category("容器", 0, 4, null, null),
                new Category("构建交付", 0, 5, null, null)
        );
        categoryService.save(categories);
    }

    @Test
    public void getAllCategory() throws Exception {
        List<Category> categories = categoryService.getAllCategory();
        System.out.println(categories);
    }

}