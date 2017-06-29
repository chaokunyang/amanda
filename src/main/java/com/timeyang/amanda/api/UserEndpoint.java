package com.timeyang.amanda.api;

import com.timeyang.amanda.user.domain.User;
import com.timeyang.amanda.user.service.UserService;
import com.timeyang.amanda.user.util.UserUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * 用户服务端点
 * @author chaokunyang
 */
@RestController
@RequestMapping("/api/user")
public class UserEndpoint {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/me")
    public ResponseEntity<User> me() {
        User user = UserUtils.getCurrentUser();
        return Optional.ofNullable(user)
                .map(u -> new ResponseEntity<>(u, HttpStatus.OK))
                .orElseThrow(() -> new UsernameNotFoundException("请登录!"));
    }

    @RequestMapping(value = "/change/password", method = RequestMethod.PUT)
    public void changePassword(@RequestBody ChangePasswordForm form) {
        LOGGER.info("try changing password");
        Assert.notNull(form, "密码不能为空!");
        userService.changePassword(form.getCurrentPassword(), form.getNewPassword());
    }

    @Getter
    @Setter
    private static class ChangePasswordForm {
        private String currentPassword;
        private String newPassword;
    }
}
