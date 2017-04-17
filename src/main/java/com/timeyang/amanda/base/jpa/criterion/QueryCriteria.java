package com.timeyang.amanda.base.jpa.criterion;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索条件
 *
 * @author chaokunyang
 * @create 2017-04-17
 */
public interface QueryCriteria extends List<Criterion> {
    class Builder {
        public static QueryCriteria create() {
            return new QueryCriteriaImpl();
        }

        private static class QueryCriteriaImpl extends ArrayList<Criterion>
                implements QueryCriteria {}
    }
}