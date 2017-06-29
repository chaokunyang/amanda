package com.timeyang.amanda.user.util;

import com.timeyang.amanda.user.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 用户工具类
 *
 * @author chaokunyang
 */
public class UserUtils {

    /**
     * 获取当前用户
     * @return
     */
    public static User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null && auth.getPrincipal() instanceof User) {
            return (User) auth.getPrincipal();
        }

        return null;
    }

}
