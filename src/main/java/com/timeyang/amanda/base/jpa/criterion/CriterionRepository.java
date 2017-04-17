package com.timeyang.amanda.base.jpa.criterion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author chaokunyang
 * @create 2017-04-17
 */
public interface CriterionRepository<T> {
    Page<T> search(SearchCriteria searchCriteria, Pageable pageable);
}
