package com.timeyang.amanda.base.jpa.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * @author chaokunyang
 * @create 2017-04-15
 */
@MappedSuperclass
@Getter
@Setter
public abstract class VersionedEntity extends BaseEntity {
    @Version
    @Column(name = "revision")
    private long version;
}
