package com.timeyang.amanda.base.jpa.criterion;

import com.timeyang.amanda.base.jpa.repository.AbstractDomainClassAwareRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

/**
 * 实现基于JPA的条件查询
 * @author chaokunyang
 * @create 2017-04-17
 */
public abstract class AbstractJpaCriterionRepository<T> extends AbstractDomainClassAwareRepository<T> implements CriterionRepository<T> {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public Page search(SearchCriteria criteria, Pageable pageable) {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
        Root<T> countRoot = countCriteria.from(this.domainClass);
        long total = this.entityManager.createQuery(
                countCriteria.select(builder.count(countRoot))
                        .where(toPredicates(criteria, countRoot, builder))
        ).getSingleResult();

        CriteriaQuery<T> pageCriteria = builder.createQuery(this.domainClass);
        Root<T> pageRoot = pageCriteria.from(this.domainClass);
        List<T> list = this.entityManager.createQuery(
                pageCriteria.select(pageRoot)
                        .where(toPredicates(criteria, pageRoot, builder))
                        .orderBy(toOrders(pageable.getSort(), pageRoot, builder))
        ).setFirstResult(pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<>(new ArrayList<>(list), pageable, total);
    }

    private static Predicate[] toPredicates(SearchCriteria criteria, Root<?> root, CriteriaBuilder builder) {
        Predicate[] predicates = new Predicate[criteria.size()];
        predicates = criteria.stream()
                .map(criterion -> criterion.getOperator().toPredicate(criterion, root, builder)).collect(Collectors.toList()).toArray(predicates);

        return predicates;
    }

}
