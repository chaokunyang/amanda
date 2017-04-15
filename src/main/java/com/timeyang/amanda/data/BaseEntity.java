package com.timeyang.amanda.data;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.search.annotations.DocumentId;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author chaokunyang
 * @create 2017-04-15
 */
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @DocumentId
    private long id;
}