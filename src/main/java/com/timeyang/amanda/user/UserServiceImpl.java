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
    public void save(User user, String newPassword) {
        if(StringUtils.hasLength(newPassword)) {
            String salt = BCrypt.gensalt(HASHING_ROUNDS, RANDOM);
            user.setHashedPassword(BCrypt.hashpw(newPassword, salt).getBytes());
        }

        userRepository.save(user);
    }

    @Override
    public byte[] getHashedPassword(String password) {
        if(StringUtils.hasLength(password)) {
            String salt = BCrypt.gensalt(HASHING_ROUNDS, RANDOM);
            return BCrypt.hashpw(password, salt).getBytes();
        }

        throw new IllegalArgumentException("password can't be null or blank");
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void deleteAll() {
        // 必须先获取authorities。
        // 因为authorities是懒加载，直接删除用户会失败，因为user_authority表与user表有外键关联。JPA提供者都是先从user_authority表删除authorities集合的每个数据，然后才删除user表的数据。而在懒加载的情况下，JPA提供者并没有获取authorities数据，因为不会先删除user_authority表数据，从而导致删除失败
        userRepository.findAll()
                .forEach(user -> user.getAuthorities().size());
        userRepository.deleteAll();
    }

}
