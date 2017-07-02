package com.timeyang.amanda.core.jpa.listener;

import com.timeyang.amanda.core.jpa.domain.AuditEntity;
import lombok.NoArgsConstructor;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * 审计实体监听器
 *
 * @author chaokunyang
 * @create 2017-04-16
 */
@NoArgsConstructor
public class AuditingEntityListener {

    // @Autowired // AuditingEntityListener不受Spring管理，无法进行自动装配，后续会参考Spring Data Jpa 的 AuditingEntityListener实现
    private AuditingHandler auditingHandler = new AuditingHandler();

    /**
     * 当persist events发生时设置创建、修改时间和auditor
     * @param auditedEntity
     */
    @PrePersist
    public void beforeInsert(AuditEntity auditedEntity) {
        auditingHandler.markCreated(auditedEntity);
    }

    /**
     * 当update events发生时设置修改时间和auditor
     * @param auditedEntity
     */
    @PreUpdate
    public void beforeUpdate(AuditEntity auditedEntity) {
        auditingHandler.markModified(auditedEntity);
    }
}
