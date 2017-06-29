package com.timeyang.amanda.user.repository;

import com.timeyang.amanda.user.domain.Profile;
import org.springframework.data.repository.CrudRepository;

/**
 * @author chaokunyang
 */
public interface ProfileRepository extends CrudRepository<Profile, Long> {

    Profile findUserByUserId(Long userId);

}
