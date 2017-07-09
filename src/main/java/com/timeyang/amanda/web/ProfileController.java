package com.timeyang.amanda.web;

import com.timeyang.amanda.user.domain.Profile;
import com.timeyang.amanda.user.service.ProfileService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * profile controller
 *
 * @author chaokunyang
 */
@Controller
public class ProfileController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private ProfileService profileService;

    @RequestMapping(value = {"about", "profile"}, method = RequestMethod.GET)
    public String list(Map<String, Object> model) {
        Profile profile = profileService.getProfile();

        model.put("profile", profile);

        return "about";
    }

}
