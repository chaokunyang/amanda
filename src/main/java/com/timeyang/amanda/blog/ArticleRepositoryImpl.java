package com.timeyang.amanda.blog;

import com.timeyang.amanda.base.jpa.criterion.AbstractJpaCriterionRepository;

/**
 * 使用抽象父类的search方法实现，作为Spring Data 仓库方法实现，此处即为Spring Data提供search方法的实现，用于在运行时合并进SpringD创建的代理
 * @author chaokunyang
 * @create 2017-04-17
 */
public class ArticleRepositoryImpl extends AbstractJpaCriterionRepository<Article> {

}
