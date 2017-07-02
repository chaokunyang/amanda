package com.timeyang.amanda.blog.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.timeyang.amanda.core.jpa.domain.AuditedEntity;
import com.timeyang.amanda.core.jpa.domain.SelfReference;
import com.timeyang.amanda.core.valadation.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.search.annotations.Field;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * <h1>分类</h1>
 * 分类与文章是多对多关系，一篇文章可以属于多个分类
 *
 * @author chaokunyang
 * @create 2017-04-21
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "category_id"))
@JsonAutoDetect(creatorVisibility = JsonAutoDetect.Visibility.NONE,
        fieldVisibility = JsonAutoDetect.Visibility.NONE,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE)
public class Category extends AuditedEntity implements Serializable, SelfReference<Category> {

    @NotBlank(message = "{validate.category.zhName}")
    @Size(min = 2, max = 20)
    @JsonProperty
    @Field
    @Column(unique = true)
    private String zhName;

    @NotBlank(message = "{validate.category.name}")
    @Size(min = 2, max = 20)
    @JsonProperty
    @Field
    @Column(unique = true)
    private String name;

    /**
     * 分类层级，从0开始
     */
    @JsonProperty
    private Integer level;

    /**
     * 分类排序
     */
    @Column(name = "order_number")
    @JsonProperty
    private Integer orderNumber;

    @Column(name = "parent_id", insertable=false, updatable = false)
    @JsonProperty
    private Long parentId;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    @OrderBy(value = "order_number ASC")
    @JsonProperty
    private List<Category> children;

    public Category(String name, String zhName, Integer level, Integer orderNumber, List<Category> children) {
        this.name = name;
        this.zhName = zhName;
        this.level = level;
        this.orderNumber = orderNumber;
        this.children = children;
    }

    public Category(String name, Integer level, Integer orderNumber, List<Category> children) {
        this.name = name;
        this.zhName = name;
        this.level = level;
        this.orderNumber = orderNumber;
        this.children = children;
    }

    /**
     * <h1>加载懒加载的子孙类目数据</h1>
     * note: 仅适用于在事务内调用。事务关闭后，没有session与children的代理(即PersistList)相关联。从而无法从数据库加载子孙类目，而会报错：could not initialize proxy - no Session
     */
    public List<Category> loadChildren() {
        children.size(); // 从数据库加载数据到children。注意：children永远不会是null，即使size为0.因为它实际上是PersistList，所有的调用都委托给PersistList。所以不必担心空指针异常，这不会发生
        children.forEach(child -> child.loadChildren());

        return  children;
    }

    // 不要包含父实体，不然会无限递归，导致内存溢出
    @Override
    public String toString() {
        return "Category{" +
                "zhName='" + zhName + '\'' +
                "name='" + name + '\'' +
                ", level=" + level +
                ", orderNumber=" + orderNumber +
                ", children=" + children +
                '}';
    }
}
