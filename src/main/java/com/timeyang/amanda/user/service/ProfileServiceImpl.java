package com.timeyang.amanda.user.service;

import com.timeyang.amanda.user.domain.Profile;
import com.timeyang.amanda.user.domain.User;
import com.timeyang.amanda.user.repository.ProfileRepository;
import com.timeyang.amanda.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chaokunyang
 */
@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public Profile getProfileByUserId(Long userId) {
        return profileRepository.findUserByUserId(userId);
    }

    @Transactional
    @Override
    public Profile save(Profile profile) {
        User user = userRepository.findOne(profile.getUserId());
        profile.setUser(user);
        return profileRepository.save(profile);
    }

    @Transactional
    @Override
    public void delete(Profile profile) {
        profileRepository.delete(profile);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        profileRepository.delete(id);
    }

    @Transactional
    @Override
    public void deleteAll() {
        profileRepository.deleteAll();
    }
}
