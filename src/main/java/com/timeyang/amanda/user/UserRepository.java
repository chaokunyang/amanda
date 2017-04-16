package com.timeyang.amanda.user;

import org.springframework.data.repository.CrudRepository;

/**
 * @author chaokunyang
 * @create 2017-04-15
 */
public interface UserRepository extends CrudRepository<User, Long> {
    User getByUsername(String username);
}
