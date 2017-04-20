package com.timeyang.amanda.base.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 搜索结果
 *
 * @author chaokunyang
 * @create 2017-04-16
 */
@Getter
@Setter
@AllArgsConstructor
public class SearchResult<T> {
    private final T entity;
    private final Float relevance;
}
