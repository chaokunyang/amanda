package com.timeyang.amanda.core.jpa.criterion;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;

/**
 * 条件
 *
 * @author chaokunyang
 * @create 2017-04-16
 */
@AllArgsConstructor
@Getter
public class Criterion {

    private final String propertyName;
    private final Operator operator;
    private Object compareTo;

    public enum Operator {
        EQ {
            @Override
            public Predicate toPredicate(Criterion c, Root<?> r, CriteriaBuilder b) {
                return b.equal(r.get(c.getPropertyName()), c.getCompareTo());
            }
        },
        NEQ {
            @Override
            public Predicate toPredicate(Criterion c, Root<?> r, CriteriaBuilder b) {
                return b.notEqual(r.get(c.getPropertyName()), c.getCompareTo());
            }
        },
        LT {
            @Override
            public Predicate toPredicate(Criterion c, Root<?> r, CriteriaBuilder b) {
                return b.lessThan(
                        r.<Comparable>get(c.getPropertyName()), getComparable(c));
            }
        },
        LTE {
            @Override
            public Predicate toPredicate(Criterion c, Root<?> r, CriteriaBuilder b) {
                return b.lessThanOrEqualTo(
                        r.get(c.getPropertyName()), getComparable(c));
            }
        },
        GT {
            @Override
            public Predicate toPredicate(Criterion c, Root<?> r, CriteriaBuilder b) {
                return b.greaterThan(
                        r.get(c.getPropertyName()), getComparable(c));
            }
        },
        GTE {
            @Override
            public Predicate toPredicate(Criterion c, Root<?> r, CriteriaBuilder b) {
                return b.greaterThanOrEqualTo(
                        r.get(c.getPropertyName()), getComparable(c));
            }
        },
        LIKE {
            @Override
            public Predicate toPredicate(Criterion c, Root<?> r, CriteriaBuilder b) {
                return b.like(
                        r.get(c.getPropertyName()), getString(c));
            }
        },
        NOT_LIKE {
            @Override
            public Predicate toPredicate(Criterion c, Root<?> r, CriteriaBuilder b) {
                return b.notLike(
                        r.get(c.getPropertyName()), getString(c));
            }
        },
        IN {
            @Override
            public Predicate toPredicate(Criterion c, Root<?> r, CriteriaBuilder b) {
                Object o = c.getCompareTo();
                if(o == null)
                    return r.get(c.getPropertyName()).in();
                if(o instanceof Collection)
                    return r.get(c.getPropertyName()).in((Collection) o);
                throw new IllegalArgumentException(c.getPropertyName());
            }
        },
        NOT_IN {
            @Override
            public Predicate toPredicate(Criterion c, Root<?> r, CriteriaBuilder b) {
                Object o = c.getCompareTo();
                if(o == null)
                    return b.not(r.get(c.getPropertyName()).in());
                if(o instanceof Collection)
                    return b.not(r.get(c.getPropertyName()).in((Collection)o));
                throw new IllegalArgumentException(c.getPropertyName());
            }
        },
        NULL {
            @Override
            public Predicate toPredicate(Criterion c, Root<?> r, CriteriaBuilder b) {
                return r.get(c.getPropertyName()).isNull();
            }
        },
        NOT_NULL {
            @Override
            public Predicate toPredicate(Criterion c, Root<?> r, CriteriaBuilder b) {
                return r.get(c.getPropertyName()).isNotNull();
            }
        };

        public abstract Predicate toPredicate(Criterion c, Root<?> r, CriteriaBuilder b);

        public static Comparable getComparable(Criterion c) {
            Object o = c.getCompareTo();
            if(o != null && !(o instanceof Comparable))
                throw new IllegalArgumentException(c.getPropertyName());

            return (Comparable) o;
        }

        public static String getString(Criterion c) {
            if(!(c.getCompareTo() instanceof String))
                throw new IllegalArgumentException(c.getPropertyName());

            return (String)c.getCompareTo();
        }
    }

}