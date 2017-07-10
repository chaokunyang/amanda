package com.timeyang.amanda.user.service;

import com.timeyang.amanda.config.AmandaProperties;
import com.timeyang.amanda.user.domain.Profile;
import com.timeyang.amanda.user.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * ${DESCRIPTION}
 *
 * @author chaokunyang
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ProfileServiceTest {

    @Autowired
    private AmandaProperties amandaProperties;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserService userService;

    @Test
    public void getProfileByUsername() throws Exception {
    }

    @Test
    public void save() throws Exception {
        User user = userService.getUserByUsername(amandaProperties.getUsername());

        Profile profile = Profile.builder()
                .name("杨朝坤")
                .user(user)
                .email("chaokunyang@qq.com")
                .biography("在微服务、Spring Cloud、Elastic Stack、搜索、Kafka、Spark、机器学习、深度学习等技术有一定的实践经验")
                .url("http://timeyang.com")
                .githubUrl("https://github.com/chaokunyang")
                .twitterUrl("https://twitter.com/chaokunyang")
                .location("广东 深圳")
                .mdBody("在微服务、Spring Cloud、Elastic Stack、搜索、Kafka、Spark、机器学习、深度学习等技术有一定的实践经验")
                .htmlBody("在微服务、Spring Cloud、Elastic Stack、搜索、Kafka、Spark、机器学习、深度学习等技术有一定的实践经验")
                .build();

        profileService.save(profile);
    }

    @Test
    public void delete() throws Exception {
    }

    @Test
    public void delete1() throws Exception {
    }

}