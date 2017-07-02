package com.timeyang.amanda.blog.repository;

import com.timeyang.amanda.core.jpa.repository.SelfReferenceRepository;
import com.timeyang.amanda.blog.domain.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author chaokunyang
 * @create 2017-04-21
 */
@Repository
public interface CategoryRepository extends SelfReferenceRepository<Category, Long>, CrudRepository<Category, Long> {

    List<Category> findByLevelOrderByOrderNumberAsc(Integer level);

    Category findCategoryByName(String name);

    Category findCategoryByZhName(String zhName);
}
