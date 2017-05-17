package com.timeyang.amanda.blog.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.timeyang.amanda.core.valadation.NotBlank;
import com.timeyang.amanda.core.jpa.domain.AuditedEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 附件
 *
 * @author chaokunyang
 * @create 2017-04-15
 */
@Getter
@Setter
@Entity
@JsonAutoDetect(creatorVisibility = JsonAutoDetect.Visibility.NONE,
        fieldVisibility = JsonAutoDetect.Visibility.NONE,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE
)
@AttributeOverride(name = "id", column = @Column(name = "attachment_id"))
public class Attachment extends AuditedEntity implements Serializable {

    @NotBlank(message = "{validate.attachment.name}")
    @JsonProperty
    private String name;

    @NotBlank(message = "{validate.attachment.mimeContentType}")
    @JsonProperty
    private String mimeContentType;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Size(min = 1, message = "{validate.attachment.contents}")
    @JsonProperty
    private byte[] contents;

}
