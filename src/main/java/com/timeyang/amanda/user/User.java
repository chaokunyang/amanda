package com.timeyang.amanda.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.timeyang.amanda.data.AuditedEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户
 *
 * @author chaokunyang
 * @create 2017-04-15
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
public class User extends AuditedEntity {
    @JsonProperty
    private String username;
    private byte[] hashedPassword;
    private Set<UserAuthority> authorities = new HashSet<>();
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
}
