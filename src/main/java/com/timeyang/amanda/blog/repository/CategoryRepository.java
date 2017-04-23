package com.timeyang.amanda.blog.repository;

import com.timeyang.amanda.base.jpa.repository.SelfReferenceRepository;
import com.timeyang.amanda.blog.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author chaokunyang
 * @create 2017-04-21
 */
public interface CategoryRepository extends SelfReferenceRepository<Category, Long>, CrudRepository<Category, Long> {

    List<Category> findByLevelOrderByOrderNumberAsc(Integer level);

}
