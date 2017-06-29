package com.timeyang.amanda.user.repository;

import com.timeyang.amanda.user.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * @author chaokunyang
 * @create 2017-04-15
 */
public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByUsername(String username);
}
