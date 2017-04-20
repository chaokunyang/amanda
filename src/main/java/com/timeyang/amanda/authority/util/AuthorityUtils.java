package com.timeyang.amanda.authority.util;

import com.timeyang.amanda.authority.UserAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 权限工具类
 *
 * @author chaokunyang
 * @create 2017-04-18
 */
public class AuthorityUtils {


    public static List<UserAuthority> createAuthorityList(String...  authorities) {
        List<UserAuthority> userAuthorities = new ArrayList<>(authorities.length);
        Arrays.stream(authorities).forEach(authority -> userAuthorities.add(new UserAuthority(authority)));

        return userAuthorities;
    }
}
