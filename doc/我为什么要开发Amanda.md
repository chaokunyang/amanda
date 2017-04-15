# 我为什么开发Amanda？

Amanda是我开发的个人博客网站系统。它与大多数开源的博客系统不同，Amanda基于良好的技术选型和清晰的领域建模，不重复造轮子，简洁、优雅、可扩展。

Amanda关注于使用最好和最合适的工具解决问题。

很多开源博客系统都很优秀，它们给社区和用户创造了巨大的价值，在此衷心的感谢他们无私的奉献。但它们也存在一些问题：

* 重复造轮子，比如基于netty实现基于NIO的服务器，比如实现自己的ORM框架。这些特性体现了作者的能力，但难以扩展，增加了学习和使用的复杂度。
* 领域建模不够合理，不够清晰，没有明确的界限，代码复杂，难以扩展。

基于这些原因，我决定开发Amanda。基于良好的技术选型和清晰的领域建模，实现一个简洁、灵活、扩展的博客平台。

## 设计理念
Amanda采用领域驱动设计(DDD)的设计理念。各个业务实体之间具有清晰的边界，每个限界上下文都是自包含的。代码结构清晰、简洁、优雅。

## 具体设计
### 页面
Amanda只有5个页面：

* 首页
    * header: 
        * logo 
        * 关于我
        * 搜索框
        * 热门
        * 分类
    * 文章列表
    * footer： GitHub、Twitter等社交账号、联系方式、微信公众号等
* 文章
* 热门文章列表
* 关于我
* 后台管理：基于React的一个单页应用

### 领域建模：
* 分类
* 文章
* 评论
* 附件
* 用户
* 权限
* 订阅
* 统计

### 技术选型：
* Spring Boot作为基础框架
* Spring Boot Actuator做监控
* Spring Data Jpa持久化数据
* Bean Validation验证数据
* Hibernate Search和Lucene作为全文索引，JPA Criteria作为条件查询
* Jcache和Ehcache作为缓存
* Spring Security作为认证授权框架
* Spring Security Oauth2.0 获取第三方信息，用于用户登录、评论、收藏等
* 实现一个简单的文件服务器
* React做管理后台单页应用
* markdown
* 其它：lombok、 pub/sub、@Async、@Scheduled

### 下一版本
* 接入更多Oauth2.0 provider
* 博客同步到各大社交平台
* 接入微信公众号
* 订阅、推送、通知
* 皮肤功能

## LICENSE
Amanda project is licensed under Apache License 2.0.