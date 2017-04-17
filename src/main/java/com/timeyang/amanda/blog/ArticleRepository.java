package com.timeyang.amanda.blog;

import com.timeyang.amanda.base.jpa.criterion.CriterionRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * 文章仓库
 *
 * @author chaokunyang
 * @create 2017-04-17
 */
public interface ArticleRepository extends CrudRepository<Article, Long>, CriterionRepository<Article> {
}
