package com.timeyang.amanda.user.service;

import com.timeyang.amanda.user.domain.Profile;

/**
 * @author chaokunyang
 */
public interface ProfileService {

    Profile getProfileByUserId(Long userId);

    Profile save(Profile profile);

    void delete(Profile profile);

    void delete(Long id);
}
