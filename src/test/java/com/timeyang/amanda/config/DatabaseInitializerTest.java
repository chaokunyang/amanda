package com.timeyang.amanda.config;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by chaokunyang on 2017/4/19.
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class DatabaseInitializerTest {

    @Autowired
    private DatabaseInitializer databaseInitializer;

    @Before
    public void deleteAll() {
        databaseInitializer.deleteAll();
    }

    @Test
    public void populate() {
        databaseInitializer.populate();
    }

}