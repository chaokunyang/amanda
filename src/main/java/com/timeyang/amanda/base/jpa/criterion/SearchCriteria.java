package com.timeyang.amanda.base.jpa.criterion;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索条件
 *
 * @author chaokunyang
 * @create 2017-04-17
 */
public interface SearchCriteria extends List<Criterion> {
    class Builder {
        public static SearchCriteria create() {
            return new SearchCriteriaImpl();
        }

        private static class SearchCriteriaImpl extends ArrayList<Criterion>
                implements SearchCriteria {}
    }
}