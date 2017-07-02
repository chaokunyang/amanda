package com.timeyang.amanda.core.jpa.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.timeyang.amanda.core.jpa.converter.InstantConverter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Convert;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

/**
 * @author chaokunyang
 */
@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditedEntity extends VersionedEntity {

    @CreatedDate
    @Convert(converter = InstantConverter.class)
    @JsonProperty
    private Instant dateCreated;

    @LastModifiedDate
    @Convert(converter = InstantConverter.class)
    @JsonProperty
    private Instant dateModified;
}
