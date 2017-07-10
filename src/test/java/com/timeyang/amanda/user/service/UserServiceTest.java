package com.timeyang.amanda.user.service;

import com.timeyang.amanda.config.AmandaProperties;
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
    private AmandaProperties amandaProperties;

    @Autowired
    private UserService userService;

    @Test
    public void save()  {
        User amanda = userService.getUserByUsername(amandaProperties.getUsername());
        userService.save(amanda, amandaProperties.getPassword());
    }

}