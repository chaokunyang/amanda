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
        categoryService.deleteAll();

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

        Category microservices = new Category("微服务", 0, 1, null, null);
        List<Category> microservicesList = Arrays.asList(
                new Category("Event Sourcing", 1, 0, microservices, null),
                new Category("CQRS", 1, 1, microservices, null),
                new Category("消息驱动的微服务", 1, 2, microservices, null)
        );
        microservices.setChildren(microservicesList);

        Category reactive = new Category("响应式编程", 0, 3, null, null);
        List<Category> reactives = Arrays.asList(
                new Category("Akka", 1, 0, reactive, null),
                new Category("Spring Reactor", 1, 1, reactive, null),
                new Category("Vert.x", 1, 2, reactive, null)
        );
        reactive.setChildren(reactives);

        Category cloudComputing = new Category("云计算", 0, 4, null, null);
        List<Category> cloudComputingList = Arrays.asList(
                new Category("Docker", 1, 0, cloudComputing, null),
                new Category("Kubernetes", 1, 1, cloudComputing, null),
                new Category("Mesos", 1, 2, cloudComputing, null)
        );
        cloudComputing.setChildren(cloudComputingList);

        Category bigData = new Category("大数据", 0, 5, null, null);
        List<Category> bigDataList = Arrays.asList(
                new Category("Hadoop", 1, 0, bigData, null),
                new Category("Spark", 1, 1, bigData, null),
                new Category("Flink", 1, 2, bigData, null),
                new Category("Storm", 1, 3, bigData, null),
                new Category("Beam", 1, 4, bigData, null)
        );
        bigData.setChildren(bigDataList);

        Category devOps = new Category("DevOps", 0, 6, null, null);
        List<Category> devOpsList = Arrays.asList(
                new Category("DevOps实践", 1, 0, devOps, null),
                new Category("SRE实践", 1, 1, devOps, null)
        );
        devOps.setChildren(devOpsList);

        List<Category> categories = Arrays.asList(
                lang,
                microservices,
                new Category("Streaming", 0, 2, null, null),
                reactive,
                cloudComputing,
                bigData,
                devOps,
                new Category("构建交付", 0, 7, null, null)
        );
        categoryService.save(categories);
    }

    @Test
    public void getAllCategory() throws Exception {
        List<Category> categories = categoryService.getAllCategory();
        System.out.println(categories);
    }

}