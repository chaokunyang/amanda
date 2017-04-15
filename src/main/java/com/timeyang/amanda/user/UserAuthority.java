package com.timeyang.amanda.user;

import com.timeyang.amanda.data.AuditedEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;

/**
 * 用户权限
 *
 * @author chaokunyang
 * @create 2017-04-15
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "authority_id"))
public class UserAuthority extends AuditedEntity implements GrantedAuthority {

    private String authority;

    public UserAuthority(String authority) {
        this.authority = authority;
    }

}
