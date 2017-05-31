package com.timeyang.amanda.blog.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.timeyang.amanda.core.jpa.converter.InstantConverter;
import com.timeyang.amanda.core.jpa.domain.AuditedEntity;
import com.timeyang.amanda.core.valadation.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.search.annotations.Boost;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chaokunyang
 * @create 2017-04-15
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@AttributeOverride(name = "id", column = @Column(name = "article_id"))
@Indexed
@JsonAutoDetect(creatorVisibility = JsonAutoDetect.Visibility.NONE,
        fieldVisibility = JsonAutoDetect.Visibility.NONE,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE)
public class Article extends AuditedEntity implements Serializable {

    // @NotNull(message = "{validate.getArticle.user")
    // @ManyToOne(fetch = FetchType.EAGER, optional = false)
    // @JoinColumn(name = "used_id")
    // @JsonProperty
    // @IndexedEmbedded
    // private User user;

    /**
     * 标题
     */
    @NotBlank(message = "{validate.getArticle.title}")
    @JsonProperty
    @Field
    private String title;

    @NotNull(message = "{validate.getArticle.categories}")
    @ManyToMany
    @JoinTable(name = "article_category",
            joinColumns = {@JoinColumn(name = "article_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_d")})
    @JsonProperty
    @IndexedEmbedded
    private List<Category> categories;

    /**
     * 关键字<br/>
     * 用于在搜索时获得更高评分
     */
    @JsonProperty
    @Field(boost = @Boost(2.0F))
    private String keywords;

    @NotBlank(message = "{validate.getArticle.mdBody}")
    @JsonProperty
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Field
    private String mdBody;

    @NotBlank(message = "{validate.getArticle.htmlBody}")
    @JsonProperty
    @Lob
    private String htmlBody;

    /**
     * 是否已发布
     */
    @JsonProperty
    @Field
    private Boolean published;

    /**
     * 发布时间
     */
    @Convert(converter = InstantConverter.class)
    @JsonProperty
    @Field
    private Instant publishedDate;

    @Valid
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "article_attachment",
            joinColumns = {@JoinColumn(name = "article_id")},
            inverseJoinColumns = {@JoinColumn(name = "attachment_id")}
    )
    @OrderColumn(name = "sort_key")
    @JsonProperty
    private List<Attachment> attachments = new ArrayList<>();

    public Article(String title, String keywords, String mdBody, String htmlBody, List<Attachment> attachments, List<Category> categories) {
        // this.user = user;
        this.title = title;
        this.keywords = keywords;
        this.mdBody = mdBody;
        this.htmlBody = htmlBody;
        this.attachments = attachments;
        this.categories = categories;
    }

    /**
     * 加载attachments
     * @return
     */
    @Transient
    public int getNumberOfAttachments() {
        return attachments.size();
    }
}
