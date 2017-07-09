package com.timeyang.amanda.blog.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.timeyang.amanda.core.jpa.converter.InstantConverter;
import com.timeyang.amanda.core.jpa.domain.BaseEntity;
import com.timeyang.amanda.core.valadation.NotBlank;
import com.timeyang.amanda.user.domain.User;
import lombok.*;
import org.hibernate.search.annotations.Boost;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * @author chaokunyang
 * @create 2017-04-15
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Entity
@AttributeOverride(name = "id", column = @Column(name = "article_id"))
@Indexed
@JsonAutoDetect(creatorVisibility = JsonAutoDetect.Visibility.NONE,
        fieldVisibility = JsonAutoDetect.Visibility.NONE,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE)
public class Article extends BaseEntity implements Serializable {

    @NotNull(message = "{validate.getArticle.user")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "used_id")
    @JsonProperty
    @IndexedEmbedded
    private User user;

    /**
     * 标题
     */
    @NotBlank(message = "{validate.getArticle.title}")
    @JsonProperty
    @Field
    private String title;

    /**
     * 摘要
     */
    @NotBlank(message = "{validate.getArticle.abstract}")
    @JsonProperty
    @Field
    @Lob
    private String abstractContent;

    // @NotNull(message = "{validate.getArticle.categories}")
    // @ManyToMany
    // @JoinTable(name = "article_category",
    //         joinColumns = {@JoinColumn(name = "article_id")},
    //         inverseJoinColumns = {@JoinColumn(name = "category_d")})
    // @JsonProperty
    // @IndexedEmbedded
    // private List<Category> categories;

    @JsonProperty
    private String categoryName;

    /**
     * 引用category表主键
     */
    @Column(name = "category_id", insertable = false, updatable = false)
    @JsonProperty
    private Long categoryId;

    @NotNull(message = "{validate.getArticle.category}")
    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonProperty
    @IndexedEmbedded
    private Category category;

    /**
     * 关键字<br/>
     * 用于在搜索时获得更高评分
     */
    @JsonProperty
    @Field(boost = @Boost(2.0F))
    private String keywords;

    @JsonProperty
    private String pictureUrl;

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

    @Convert(converter = InstantConverter.class)
    @JsonProperty
    @CreatedDate
    private Instant dateCreated;

    // 更新日期不做审计，手动指定更新日期
    @Convert(converter = InstantConverter.class)
    @JsonProperty
    private Instant dateModified;

    @Valid
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "article_attachment",
            joinColumns = {@JoinColumn(name = "article_id")},
            inverseJoinColumns = {@JoinColumn(name = "attachment_id")}
    )
    @OrderColumn(name = "sort_key")
    @JsonProperty
    private List<Attachment> attachments;

    /**
     * 文章访问量
     */
    @JsonProperty
    private Long views;

    public Article(User user, String title, String keywords, String mdBody, String htmlBody, List<Attachment> attachments, Category category, String categoryName) {
        this.user = user;
        this.title = title;
        this.keywords = keywords;
        this.mdBody = mdBody;
        this.htmlBody = htmlBody;
        this.attachments = attachments;
        this.category = category;
        this.categoryName = categoryName;
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
