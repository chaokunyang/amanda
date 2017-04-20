package com.timeyang.amanda.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 用户服务
 *
 * @author chaokunyang
 * @create 2017-04-15
 */
@Validated
public interface UserService extends UserDetailsService {

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    void save(@NotNull(message = "{validate.authenticate.save}") @Valid User user, String newPassword);

    byte[] getHashedPassword(String password);

    User save(@Valid User user);

    void deleteAll();

}
