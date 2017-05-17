package com.timeyang.amanda.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * 后台管理
 *
 * @author chaokunyang
 */
@Controller
public class AdminController {

    private static final Logger LOGGER = LogManager.getLogger();

    @RequestMapping(value = "admin", method = RequestMethod.GET)
    public String admin(Map<String, Object> model) {

        return "admin";
    }

}
