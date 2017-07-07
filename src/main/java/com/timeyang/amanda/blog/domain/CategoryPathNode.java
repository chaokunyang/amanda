package com.timeyang.amanda.blog.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author chaokunyang
 */
@Getter
@Setter
@AllArgsConstructor
public class CategoryPathNode {
    private Integer level;
    // private Integer orderNumber;
    private Long id;
    // private Long parentId;
}
