package com.timeyang.amanda.core.jpa.repository;

import com.timeyang.amanda.core.jpa.domain.SelfReference;

import java.io.Serializable;

/**
 * 自引用实体仓库的一些操作需特殊处理，以避免违反外键约束
 *
 * @author chaokunyang
 * @create 2017-04-22
 */
public interface SelfReferenceRepository<T, ID extends Serializable> {

    /**
     * Deletes all entities from leaf entity to the entity with the given id.
     * @param id id must not be {@literal null}。
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    void delete(ID id);

    /**
     * Deletes all entities from leaf entity to the given entity
     * @param entity
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     */
    void delete(SelfReference<T> entity);

    /**
     * Deletes all entities from leaf entity to the given entities
     * <p>
     *     <strong>
     *         这里本应该是Iterable<? extends SelfReference<T>>。<br/>
     *         但由于Java泛型的擦除，会导致同时继承SelfReferenceRepository和CrudRepository的接口在该方法上产生冲突：both methods have same erasure, yet neither overrides the other.<br/>
     *         void delete(Iterable<? extends SelfReference<T>> entities)会擦除为void delete(Iterable entities)<br/>
     *         void delete(Iterable<? extends T> entities)也会擦除为void delete(Iterable entities)<br/>
     *     </strong><br/>
     *     因此改为与CrudRepository的方法保持一致，从而满足编译需求，但是也就因此缺失了类型安全，在使用entities时就需要强制转型为 SelfReference再使用
     * </p>
     * @param entities
     * @throws IllegalArgumentException in case the given {@link Iterable} is {@literal null}
     */
    void delete(Iterable<? extends T> entities);

    /**
     * Deletes all entities managed by the repository
     */
    void deleteAll();

}
