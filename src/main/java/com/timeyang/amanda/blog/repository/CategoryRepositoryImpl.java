package com.timeyang.amanda.blog.repository;

import com.timeyang.amanda.base.jpa.repository.AbstractSelfReferenceRepository;
import com.timeyang.amanda.base.jpa.repository.SelfReferenceRepository;
import com.timeyang.amanda.blog.domain.Category;

/**
 * 自定义分类仓库实现
 *
 * @author chaokunyang
 * @create 2017-04-22
 */
public class CategoryRepositoryImpl extends AbstractSelfReferenceRepository<Category, Long> implements SelfReferenceRepository<Category, Long> {

}
