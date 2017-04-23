package com.timeyang.amanda.blog.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.timeyang.amanda.base.valadation.NotBlank;
import com.timeyang.amanda.base.jpa.domain.AuditedEntity;
import com.timeyang.amanda.user.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 评论
 *
 * @author chaokunyang
 * @create 2017-04-15
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@AttributeOverride(name = "id", column = @Column(name = "comment_id"))
public class Comment extends AuditedEntity {

    @JsonProperty
    private Long articleId;

    @NotNull(message = "{validate.article.comment.user}")
    @JsonProperty
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank(message = "{validate.article.comment.body}")
    @JsonProperty
    @Lob
    private String body;

    @Valid
    @JsonProperty
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinTable(name = "comment_attachment",
            joinColumns = {@JoinColumn(name = "comment_id")},
            inverseJoinColumns = {@JoinColumn(name = "attachment_id")})
    @OrderColumn(name = "sort_key")
    private List<Attachment> attachments = new ArrayList<>();

    @Transient
    public int getNumberOfAttachments()
    {
        return this.attachments.size();
    }
}
