package com.timeyang.amanda.config;

import com.timeyang.amanda.authority.UserAuthority;
import com.timeyang.amanda.authority.util.AuthorityUtils;
import com.timeyang.amanda.blog.domain.Article;
import com.timeyang.amanda.blog.domain.Comment;
import com.timeyang.amanda.blog.repository.ArticleRepository;
import com.timeyang.amanda.blog.repository.CommentRepository;
import com.timeyang.amanda.blog.service.CategoryService;
import com.timeyang.amanda.user.User;
import com.timeyang.amanda.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

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

        Article article = new Article(user, "我为什么开发Amanda？",
                "Amanda、 Spring Boot、Spring Data JPA、 Hibernate Search",
                "Amanda关注于使用最好和最合适的工具解决问题", "Amanda关注于使用最好和最合适的工具解决问题", null, null);

        articleRepository.save(article);

        Comment comment = new Comment(article.getId(), user, "Amanda设计不错, 我很喜欢", null);
        commentRepository.save(comment);
    }

    public void deleteAll() {
        commentRepository.deleteAll();
        articleRepository.deleteAll();
        categoryService.deleteAll();
        userService.deleteAll();
    }
}
