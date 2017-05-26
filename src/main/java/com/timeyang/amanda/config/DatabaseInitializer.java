package com.timeyang.amanda.config;

import com.timeyang.amanda.authority.UserAuthority;
import com.timeyang.amanda.authority.util.AuthorityUtils;
import com.timeyang.amanda.blog.domain.Article;
import com.timeyang.amanda.blog.domain.Category;
import com.timeyang.amanda.blog.domain.Comment;
import com.timeyang.amanda.blog.repository.ArticleRepository;
import com.timeyang.amanda.blog.repository.CommentRepository;
import com.timeyang.amanda.blog.service.CategoryService;
import com.timeyang.amanda.user.User;
import com.timeyang.amanda.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 初始化数据
 *
 * @author chaokunyang
 * @create 2017-04-18
 */
@Service
@Profile("dev")
public class DatabaseInitializer {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentRepository commentRepository;

    public void populate() {
        deleteAll();

        List<UserAuthority> userAuthorityList = AuthorityUtils.createAuthorityList(
                "VIEW_ARTICLES", "VIEW_ARTICLE", "CREATE_ARTICLE", "EDIT_ARTICLE", "DELETE_ARTICLE", "DELETE_ANY_ARTICLE",
                "VIEW_COMMENTS", "VIEW_COMMENT", "CREATE_COMMENT", "EDIT_COMMENT", "DELETE_COMMENT", "DELETE_ANY_COMMENT",
                "VIEW_ATTACHMENT", "DELETE_ATTACHMENT", "DELETE_ANY_ATTACHMENT",
                "VIEW_USER_SESSIONS");
        Set<UserAuthority> authorities = new HashSet<>(userAuthorityList);
        User user = new User("amanda", userService.getHashedPassword("timeyang"),
                authorities, true, true, true, true);
        userService.save(user);

        addCategories();

        Article article = new Article(user, "我为什么开发Amanda？",
                "Amanda、 Spring Boot、Spring Data JPA、 Hibernate Search",
                "Amanda关注于使用最好和最合适的工具解决问题", "Amanda关注于使用最好和最合适的工具解决问题", null, null);

        articleRepository.save(article);

        Comment comment = new Comment(article.getId(), user, "Amanda设计不错, 我很喜欢", null);
        commentRepository.save(comment);

        Article logPlatform = new Article(user, "基于Elastic Stack + Fluentd搭建实时日志平台",
                "Elastic Stack、Fluentd、ElasticSearch、Logstash、Beats、filebeat、metricbeat、 Kibana",
                "基于Elastic Stack + Fluentd搭建实时日志平台", "基于Elastic Stack + Fluentd搭建实时日志平台", null, null);

        articleRepository.save(logPlatform);
    }

    public void deleteAll() {
        commentRepository.deleteAll();
        articleRepository.deleteAll();
        categoryService.deleteAll();
        userService.deleteAll();
    }

    private void addCategories() {
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
}
