package com.timeyang.amanda.base.jpa.listener;


import com.timeyang.amanda.base.jpa.domain.AuditedEntity;
import com.timeyang.amanda.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * 审计处理器，用于标记实体创建和修改
 *
 * @author chaokunyang
 * @create 2017-04-16
 */
@Component
public class AuditingHandler {

    private static final Logger LOGGER = LogManager.getLogger();

    private boolean modifyOnCreation = true;

    public void setModifyOnCreation(boolean modifyOnCreation) {
        this.modifyOnCreation = modifyOnCreation;
    }

    /**
     * 标记给定对象为创建
     * @param auditedEntity
     */
    public void markCreated(AuditedEntity auditedEntity) {
        touch(auditedEntity, true);
    }

    /**
     * 标记给定对象为修改
     * @param auditedEntity
     */
    public void markModified(AuditedEntity auditedEntity) {
        touch(auditedEntity, false);
    }

    private void touch(AuditedEntity auditedEntity, boolean isNew) {
        User auditor = touchAuditor(auditedEntity, isNew);
        Instant now = touchDate(auditedEntity, isNew);

        Object defaultAuditor = auditor == null ? "unknown" : auditor;

        LOGGER.debug("Touched {} - Last modification at {} by {}", new Object[]{auditedEntity, now, defaultAuditor});
    }

    /**
     * 设置正在修改和创建的auditor
     * @param auditedEntity
     * @param isNew
     * @return
     */
    private User touchAuditor(AuditedEntity auditedEntity, boolean isNew) {
        if (auditedEntity == null)
            return null;

        User auditor = getCurrentAuditor();

        if(isNew) {
            auditedEntity.setCreatedBy(auditor);
            if(!modifyOnCreation)
                return auditor; // 如果创建不算做修改，则直接返回
        }

        auditedEntity.setLastModifiedBy(auditor);
        return auditor;
    }

    /**
     * 设置创建和修改日期
     * @param auditedEntity
     * @param isNew
     * @return
     */
    private Instant touchDate(AuditedEntity auditedEntity, boolean isNew) {
        Instant now = Instant.now();

        if(isNew) {
            auditedEntity.setDateCreated(now);
            if(!modifyOnCreation)
                return now; // 如果创建不算做修改，则直接返回
        }

        auditedEntity.setDateModified(now);
        return now;
    }

    /**
     * 返回当前用户
     * @return 当前用户
     */
    private User getCurrentAuditor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null && auth.isAuthenticated()) {
            if(auth.getPrincipal() instanceof User) {
                return (User) auth.getPrincipal();
            }

        }

        return null;
    }
}
