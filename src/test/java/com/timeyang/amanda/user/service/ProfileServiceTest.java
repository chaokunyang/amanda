package com.timeyang.amanda.user.service;

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
    private ProfileService profileService;

    @Autowired
    private UserService userService;

    @Test
    public void getProfileByUsername() throws Exception {
    }

    @Test
    public void save() throws Exception {
        User user = userService.getUserByUsername("amanda");

        Profile profile = new Profile("杨朝坤", user, "chaokunyang@qq.com", "在微服务、Spring Cloud、Elastic Stack、搜索、Kafka、Spark、机器学习、深度学习等技术有一定的实践经验", "http://timeyang.com", "https://github.com/chaokunyang", "https://twitter.com/chaokunyang", "", "广东 深圳", "擅长大数据、机器学习、流处理、微服务", "", "");
        profileService.save(profile);
    }

    @Test
    public void delete() throws Exception {
    }

    @Test
    public void delete1() throws Exception {
    }

}