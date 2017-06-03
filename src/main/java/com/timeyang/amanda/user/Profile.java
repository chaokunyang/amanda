package com.timeyang.amanda.user;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.timeyang.amanda.core.jpa.domain.VersionedEntity;
import com.timeyang.amanda.core.valadation.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

/**
 * 个人资料
 *
 * @author chaokunyang
 */
@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
@AttributeOverride(name = "id", column = @Column(name = "profile_id"))
@JsonAutoDetect(creatorVisibility = JsonAutoDetect.Visibility.NONE,
        fieldVisibility = JsonAutoDetect.Visibility.NONE,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE)
public class Profile extends VersionedEntity {

    /**
     * 个人姓名
     * 与username不同，username具有唯一性，可用于用户登录。
     * 而name不具备唯一约束
     */
    private String name;

    @Email
    private String email;

    /**
     * 个人简介
     */
    private String biography ;

    /**
     * 个人网站
     */
    private String url;

    /**
     * githubUrl账号
     */
    private String githubUrl;

    /**
     * 所在公司
     */
    private String company;

    /**
     * 所在位置
     */
    private String location;

    /**
     * 个人简介markdown文档
     */
    @Lob
    private String mdBody;

    /**
     * 个人简介的markdown文本渲染后的html
     */
    @Lob
    private String htmlBody;

}
