package com.timeyang.amanda.base.jpa.domain;

import com.timeyang.amanda.base.jpa.converter.InstantConverter;
import com.timeyang.amanda.base.jpa.listener.AuditingEntityListener;
import com.timeyang.amanda.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

/**
 * 审计实体
 *
 * @author chaokunyang
 * @create 2017-04-15
 */
@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditedEntity extends VersionedEntity {

    @Convert(converter = InstantConverter.class)
    private Instant dateCreated;

    @Convert(converter = InstantConverter.class)
    private Instant dateModified;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by") // 继承AuditedEntity的子类所在的表的user_id列包含User实体的主键
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "last_modified_by")
    private User lastModifiedBy;

}