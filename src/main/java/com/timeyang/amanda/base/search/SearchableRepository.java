package com.timeyang.amanda.base.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 全文搜索接口
 *
 * @author chaokunyang
 * @create 2017-04-16
 */
public interface SearchableRepository<T> {
    Page<SearchResult<T>> search(String query, Pageable pageable);
}
