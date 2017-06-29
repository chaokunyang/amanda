package com.timeyang.amanda.user.service;

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
public class UserServiceTest {

    @Autowired
    private UserService userService;

    /**
     * 可用于在开发时重置密码
     * @throws Exception
     */
    @Test
    public void save() throws Exception {
        User amanda = userService.getUserByUsername("amanda");
        userService.save(amanda, "timeyang");
    }

}