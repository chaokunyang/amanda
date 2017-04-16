package com.timeyang.amanda.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author chaokunyang
 * @create 2017-04-15
 */
@Service
public class UserServiceImpl implements UserService {

    private static final SecureRandom RANDOM;
    private static final int HASHING_ROUNDS = 10;

    static {
        try {
            RANDOM = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e); // not possible
        }
    }

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User principal = userRepository.getByUsername(username);
        // make sure the authorities and password are loaded;
        principal.getAuthorities().size();
        principal.getHashedPassword(); // hashedPassword也是懒加载的
        return principal;
    }

    @Transactional
    @Override
    public void saveUser(User user, String newPassword) {
        if(StringUtils.hasLength(newPassword)) {
            String salt = BCrypt.gensalt(HASHING_ROUNDS, RANDOM);
            user.setHashedPassword(BCrypt.hashpw(newPassword, salt).getBytes());
        }

        userRepository.save(user);
    }

}
