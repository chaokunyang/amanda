package com.timeyang.amanda.blog.repository;

import com.timeyang.amanda.blog.domain.Category;
import com.timeyang.amanda.core.jpa.repository.AbstractSelfReferenceRepository;
import com.timeyang.amanda.core.jpa.repository.SelfReferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

/**
 * 自定义分类仓库实现
 *
 * @author chaokunyang
 * @create 2017-04-22
 */
public class CategoryRepositoryImpl extends AbstractSelfReferenceRepository<Category, Long> implements SelfReferenceRepository<Category, Long> {

    // 默认获取的数据entityManager和类信息在被spring 的cglib代理后会被隐藏在代理里面，导致调用自定义方法时会报空指针异常
    @Autowired
    public CategoryRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Category.class);
    }

}
