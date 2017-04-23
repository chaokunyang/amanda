package com.timeyang.amanda.base.jpa.domain;

import java.util.List;

/**
 * 自引用实体接口，含有父子关系
 *
 * @author chaokunyang
 * @create 2017-04-22
 */
public interface SelfReference<T> {

    /**
     * 获取父实体
     * @return 父实体
     */
    T getParent();

    /**
     * 获取子实体列表
     * @return 子实体列表
     */
    List<T> getChildren();

    /**
     * 获取当前层级，从0开始
     * @return 当前层级
     */
    Integer getLevel();

    /**
     * 获取当前排序数字
     * @return 排序数字
     */
    Integer getOrderNumber();

}
