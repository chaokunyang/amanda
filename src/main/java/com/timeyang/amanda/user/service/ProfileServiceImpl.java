package com.timeyang.amanda.user.service;

import com.timeyang.amanda.user.domain.Profile;
import com.timeyang.amanda.user.repository.ProfileRepository;
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
}
