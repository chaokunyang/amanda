package com.timeyang.amanda.api;

import com.timeyang.amanda.user.domain.Profile;
import com.timeyang.amanda.user.domain.User;
import com.timeyang.amanda.user.service.ProfileService;
import com.timeyang.amanda.user.service.UserService;
import com.timeyang.amanda.user.util.UserUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author chaokunyang
 */
@RestController
@RequestMapping("/api/profile")
public class ProfileEndpoint {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public Profile getProfile() {
        User user = UserUtils.getCurrentUser();
        if(user != null) {
            return profileService.getProfileByUserId(user.getId());
        }

        return null;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Profile getProfileByUsername(@RequestParam("username") String username) {
        User user = userService.getUserByUsername(username);
        return profileService.getProfileByUserId(user.getId());
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Profile update(@RequestBody Profile profile) {
        return profileService.update(profile);
    }
}
