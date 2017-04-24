package com.timeyang.amanda.blog.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.timeyang.amanda.base.jpa.domain.AuditedEntity;
import com.timeyang.amanda.base.jpa.domain.SelfReference;
import com.timeyang.amanda.base.valadation.NotBlank;
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
public class Category extends AuditedEntity implements Serializable, SelfReference<Category> {

    @NotBlank(message = "{validate.category.name}")
    @Size(min = 2, max = 20)
    @JsonProperty
    @Field
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

    @Column(name = "parent_id", insertable=false, updatable = false) // 该
    @JsonProperty
    private Long parentId;

    // @JsonIgnore // 自动的，因为我们默认忽略了所有
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy(value = "order_number ASC")
    private List<Category> children;

    // 这种构造器不要使用Lombok，因为lombok生成的构造器
    public Category(String name, Integer level, Integer orderNumber, Category parent, List<Category> children) {
        this.name = name;
        this.level = level;
        this.orderNumber = orderNumber;
        this.parent = parent;
        this.children = children;
    }

    @Override
    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    @Override
    public List<Category> getChildren() {
        return children;
    }

    public void setChildren(List<Category> children) {
        this.children = children;
    }

    @Override
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * 加载懒加载的子类目数据
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
                "name='" + name + '\'' +
                ", level=" + level +
                ", orderNumber=" + orderNumber +
                ", children=" + children +
                '}';
    }
}
