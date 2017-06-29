package com.timeyang.amanda.user.service;

import com.timeyang.amanda.user.domain.User;
import com.timeyang.amanda.user.repository.UserRepository;
import com.timeyang.amanda.user.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @author chaokunyang
 * @create 2017-04-15
 */
@Service
public class UserServiceImpl implements UserService {

    @Lazy // 避免与SecurityConfiguration形成依赖循环
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User principal = userRepository.findUserByUsername(username);
        // make sure the authorities and password are loaded;
        principal.getAuthorities().size();
        principal.getHashedPassword(); // hashedPassword也是懒加载的
        return principal;
    }

    @Transactional
    @Override
    public void save(User user, String newPassword) {
        if(StringUtils.hasLength(newPassword)) {
            user.setHashedPassword(bCryptPasswordEncoder.encode(newPassword).getBytes());
        }

        userRepository.save(user);
    }

    @Override
    public byte[] getHashedPassword(String password) {
        Assert.notNull(password, "password can't be null");
        if(StringUtils.hasLength(password)) {
            // String salt = BCrypt.gensalt(HASHING_ROUNDS, RANDOM);
            // return BCrypt.hashpw(password, salt).getBytes();
            return bCryptPasswordEncoder.encode(password).getBytes();
        }

        throw new IllegalArgumentException("password can't be null or blank");
    }

    @Transactional
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

    @Transactional
    @Override
    public void changePassword(String currentPassword, String newPassword) {
        User currentUser = userRepository.findUserByUsername(UserUtils.getCurrentUser().getUsername()); // 密码在登录后会被擦除
        Assert.notNull(currentUser, "你必须先登录才能修改密码！");
        if(!bCryptPasswordEncoder.matches(currentPassword, currentUser.getPassword()))
            throw new AuthorizationServiceException("密码错误!");

        save(currentUser, newPassword);
    }

    @Transactional
    @Override
    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

}
