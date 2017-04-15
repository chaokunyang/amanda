package com.timeyang.amanda.data;

import com.timeyang.amanda.base.converters.InstantConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;
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
public abstract class AuditedEntity extends VersionedEntity {
    @Convert(converter = InstantConverter.class)
    private Instant dateCreated;
    @Convert(converter = InstantConverter.class)
    private Instant dateModified;
}