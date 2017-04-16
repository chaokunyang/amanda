package com.timeyang.amanda.base.jpa.listener;

import com.timeyang.amanda.base.jpa.domain.AuditedEntity;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Autowired
    private AuditingHandler auditingHandler;

    /**
     * 当persist events发生时设置创建、修改时间和auditor
     * @param auditedEntity
     */
    @PrePersist
    public void beforeInsert(AuditedEntity auditedEntity) {
        auditingHandler.markCreated(auditedEntity);
    }

    /**
     * 当update events发生时设置修改时间和auditor
     * @param auditedEntity
     */
    @PreUpdate
    public void beforeUpdate(AuditedEntity auditedEntity) {
        auditingHandler.markModified(auditedEntity);
    }
}
