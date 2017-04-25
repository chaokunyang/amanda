package com.timeyang.amanda.base.jpa.repository;

import com.timeyang.amanda.base.jpa.domain.SelfReference;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author chaokunyang
 * @create 2017-04-22
 */
public class AbstractSelfReferenceRepository<T, ID extends Serializable>
        extends AbstractDomainClassAwareRepository<T>
        implements SelfReferenceRepository<T, ID> {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public void delete(ID id) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaDelete<T> query = builder.createCriteriaDelete(domainClass);

        Root<T> root = query.from(domainClass);
        entityManager.createQuery(query.where(
                builder.equal(root.get("id"), id)
        )).executeUpdate();
    }

    @Override
    public void delete(SelfReference<T> entity) {
        entityManager.remove(entity);
    }

    @Override
    public void delete(Iterable<? extends T> entities) {
        entities.forEach(entity -> entityManager.remove(entity));
    }

    @Transactional
    @Override
    public void deleteAll() {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(domainClass);
        Root<T> root = query.from(domainClass);

        // 获取一级目录列表
        List<T> firstLevelEntities = entityManager.createQuery(
                query.select(root)
                        .where(builder.equal(root.get("level"), 0))
                        .orderBy(builder.asc(root.get("orderNumber")))
        ).getResultList();


        LinkedList<SelfReference<T>> allEntities = new LinkedList<>();
        // 将子类目放在列表头部，这样先删除子类目，从而不会违反外键约束
        firstLevelEntities.forEach(entity -> bfsTraverse((SelfReference<T>) entity, allEntities));

        allEntities.forEach(category ->
                entityManager.remove(category));
    }

    /**
     * <strong>使用广度优先搜索收集</strong>当前实体及其所有子孙实体到集合，叶子实体排在最前面<br/>
     * 这样才能保证先删除子孙节点，后删除父节点，避免违反外键约束
     * @param currentEntity 当前实体
     * @param allEntities 所有实体的集合
     * @return 当前实体及其所有子孙实体的集合，叶子实体排在集合最前面
     */
    @SuppressWarnings("unchecked")
    private LinkedList<SelfReference<T>> bfsTraverse(SelfReference<T> currentEntity, LinkedList<SelfReference<T>> allEntities) {

        // 队列，用于广度优先遍历。每一时刻，队列所包含的节点是那些本身已经被访问，而它的邻居(这里即子节点)还有未被访问的节点
        Queue<SelfReference<T>> queue = new ArrayDeque<>();
        allEntities.addFirst(currentEntity); // 对当前遍历到的元素执行的操作：加在头部，后续用于删除节点(避免违反外键约束)
        queue.add(currentEntity); // 加在尾部

        while (queue.size() != 0) { // 直到队列为空
            SelfReference<T> parent = queue.remove(); // 移除在队列头部的节点

            if(parent.getChildren().size() != 0) {
                parent.getChildren().forEach(child -> {
                    allEntities.addFirst((SelfReference<T>) child); // 对当前遍历到的元素执行的操作：加在头部
                    queue.add((SelfReference<T>) child); // 加在尾部
                });
            }
        }

        return allEntities;
    }

}
