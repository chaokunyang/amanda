package com.timeyang.amanda.base.jpa.repository;

import com.timeyang.amanda.blog.repository.CategoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * ${DESCRIPTION}
 *
 * @author chaokunyang
 * @create 2017-04-23
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class SelfReferenceRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void delete() throws Exception {
    }

    @Test
    public void delete1() throws Exception {
    }

    @Test
    public void delete2() throws Exception {
    }

    @Test
    public void deleteAll() throws Exception {
        categoryRepository.deleteAll();
    }

}