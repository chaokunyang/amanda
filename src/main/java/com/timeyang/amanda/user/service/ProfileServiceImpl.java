package com.timeyang.amanda.user.service;

import com.timeyang.amanda.user.domain.Profile;
import com.timeyang.amanda.user.domain.User;
import com.timeyang.amanda.user.repository.ProfileRepository;
import com.timeyang.amanda.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;

/**
 * @author chaokunyang
 */
@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Profile getProfile() {
        Iterator<Profile> iterator = profileRepository.findAll().iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return new Profile();
    }

    @Transactional
    @Override
    public Profile getProfileByUserId(Long userId) {
        return profileRepository.findUserByUserId(userId);
    }

    @Transactional
    @Override
    public Profile save(Profile profile) {
        return profileRepository.save(profile);
    }

    @Override
    public Profile update(Profile profile) {
        if(profile.getUserId() != null) {
            User user = userRepository.findOne(profile.getUserId());
            profile.setUser(user); // 用于在更新Profile时维持关联关系
        }
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
