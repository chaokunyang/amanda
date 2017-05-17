package com.timeyang.amanda.web;

import com.timeyang.amanda.user.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 登录
 *
 * @author chaokunyang
 */
@Controller
public class LoginController {

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {

        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User)
            return "redirect:admin";

        return "login";
    }
}
