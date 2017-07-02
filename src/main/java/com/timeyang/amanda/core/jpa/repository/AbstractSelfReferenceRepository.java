package com.timeyang.amanda.core.jpa.repository;

import com.timeyang.amanda.core.jpa.domain.BaseEntity;
import com.timeyang.amanda.core.jpa.domain.SelfReference;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.LazyInitializationException;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
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
public abstract class AbstractSelfReferenceRepository<T, ID extends Serializable>
        extends AbstractDomainClassAwareRepository<T>
        implements SelfReferenceRepository<T, ID> {

    private static final Logger LOGGER = LogManager.getLogger();

    @Lazy
    @PersistenceContext
    protected EntityManager entityManager;

    public AbstractSelfReferenceRepository(EntityManager entityManager, Class<T> domainClass) {
        super(domainClass);
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    @SuppressWarnings("unchecked")
    public void delete(ID id) {

        Assert.notNull(id, "The given id must not be null!");

        // CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        // CriteriaDelete<T> query = builder.createCriteriaDelete(domainClass);
        //
        // Root<T> root = query.from(domainClass);
        // this.entityManager.createQuery(query.where(
        //         builder.equal(root.get("id"), id)
        // )).executeUpdate();

        T entity = findOne(id);
        if (entity == null)
            throw new EmptyResultDataAccessException(
                    String.format("No %s entity with id %s exists!", domainClass, id), 1);

        delete((SelfReference<T>) entity);

    }

    @Transactional
    @Override
    public void delete(SelfReference<T> entity) {

        Assert.notNull(entity, "The entity must not be null!");

        LinkedList<SelfReference<T>> treeEntities = bfsTraverse(entity);
        treeEntities.forEach(e -> this.entityManager.remove(e));
    }

    @Transactional
    @Override
    @SuppressWarnings("unchecked")
    public void delete(Iterable<? extends T> entities) {

        Assert.notNull(entities, "The given Iterable of entities not be null!");

        LinkedList<SelfReference<T>> allEntitiesToDelete = new LinkedList<>();
        entities.forEach(entity -> bfsTraverse((SelfReference<T>) entity, allEntitiesToDelete));

        allEntitiesToDelete.forEach(category ->
                this.entityManager.remove(category));
    }

    @Transactional
    @Override
    @SuppressWarnings("unchecked")
    public void deleteAll() {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(domainClass);
        Root<T> root = query.from(domainClass);

        LinkedList<SelfReference<T>> allEntitiesToDelete = new LinkedList<>();

        // 获取根类目，如果存在的话
        try {
            T r = this.entityManager.createQuery(
                    query.select(root)
                            .where(builder.equal(root.get("level"), -1))
            ).getSingleResult();
            if(r != null) {
                allEntitiesToDelete.addAll(bfsTraverse((SelfReference<T>) r));
            }
        }catch (NoResultException e) {
        }catch (NonUniqueResultException e) {
            LOGGER.warn("there is more than one root node in parent/child relationship, they will all be deleted, but note that don't do this. this is illegal");
            List<T> r = this.entityManager.createQuery(
                    query.select(root)
                            .where(builder.equal(root.get("level"), -1))
            ).getResultList();
            // 将每个分类的子类目放在列表头部，这样先删除子类目，从而不会违反外键约束
            r.forEach(entity -> allEntitiesToDelete.addAll(bfsTraverse((SelfReference<T>) entity)));
        }

        // 获取一级目录列表
        List<T> firstLevelEntities = this.entityManager.createQuery(
                query.select(root)
                        .where(builder.equal(root.get("level"), 0))
                        .orderBy(builder.asc(root.get("orderNumber")))
        ).getResultList();

        // 将每个分类的子类目放在列表头部，这样先删除子类目，从而不会违反外键约束
        firstLevelEntities.forEach(entity -> allEntitiesToDelete.addAll(bfsTraverse((SelfReference<T>) entity)));

        allEntitiesToDelete.forEach(category ->
                this.entityManager.remove(category));
    }

    /**
     * Retrieves an entity by its id.
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal null} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    @Transactional
    private T findOne(ID id) {

        Assert.notNull(id, "The given id must not be null!");

        return this.entityManager.find(domainClass, id);
    }

    /**
     * <strong>使用广度优先搜索收集</strong>当前实体及其所有子孙实体到集合，叶子实体排在最前面<br/>
     * 这样才能保证先删除子孙节点，后删除父节点，避免违反外键约束
     * @param currentEntity 当前实体
     * @return 当前实体及其所有子孙实体的集合，叶子实体排在集合最前面
     */
    @SuppressWarnings("unchecked")
    private LinkedList<SelfReference<T>> bfsTraverse(SelfReference<T> currentEntity) {
        LinkedList<SelfReference<T>> treeEntities = new LinkedList<>(); // 当前实体及其所有子孙实体的集合

        return bfsTraverse(currentEntity, treeEntities);
    }


    /**
     * <strong>使用广度优先搜索收集</strong>当前实体及其所有子孙实体到集合，叶子实体排在最前面<br/>
     * 这样才能保证先删除子孙节点，后删除父节点，避免违反外键约束
     * @param currentEntity 存放当前实体及其所有子孙实体的集合
     * @param treeEntities 所有实体的集合
     * @return 当前实体及其所有子孙实体的集合，叶子实体排在集合最前面
     */
    @SuppressWarnings("unchecked")
    @Transactional
    private LinkedList<SelfReference<T>> bfsTraverse(SelfReference<T> currentEntity, LinkedList<SelfReference<T>> treeEntities) {

        // 无法获取子孙类目时，在事务中重新获取当前节点
        try {
            currentEntity.getChildren().size();
        } catch (LazyInitializationException e) {
            ID id = (ID) ((BaseEntity)currentEntity).getId();
            currentEntity = (SelfReference<T>) findOne(id);
        }

        // 队列，用于广度优先遍历。每一时刻，队列所包含的节点是那些本身已经被访问，而它的邻居(这里即子节点)还有未被访问的节点
        Queue<SelfReference<T>> queue = new ArrayDeque<>();
        treeEntities.addFirst(currentEntity); // 对当前遍历到的元素执行的操作：加在头部，后续用于删除节点(避免违反外键约束)
        queue.add(currentEntity); // 加在尾部

        while (queue.size() != 0) { // 直到队列为空
            SelfReference<T> parent = queue.remove(); // 移除在队列头部的节点

            if(parent.getChildren().size() != 0) {
                parent.getChildren().forEach(child -> {
                    treeEntities.addFirst((SelfReference<T>) child); // 对当前遍历到的元素执行的操作：加在头部
                    queue.add((SelfReference<T>) child); // 加在尾部
                });
            }
        }

        return treeEntities;
    }

}
