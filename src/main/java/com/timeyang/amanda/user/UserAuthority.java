package com.timeyang.amanda.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Embeddable;

/**
 * 用户权限
 *
 * @author chaokunyang
 * @create 2017-04-15
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserAuthority implements GrantedAuthority {
    private String authority;
}
