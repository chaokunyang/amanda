package com.timeyang.amanda.base.jpa.domain;

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
    @DocumentId // 也可以不添加，Hibernate Search会自动使用标注了@Id的属性
    private Long id;
}