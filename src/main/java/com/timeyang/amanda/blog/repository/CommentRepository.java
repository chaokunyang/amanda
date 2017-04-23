package com.timeyang.amanda.blog.repository;

import com.timeyang.amanda.blog.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

/**
 * @author chaokunyang
 * @create 2017-04-19
 */
public interface CommentRepository extends CrudRepository<Comment, Long> {

    Page<Comment> getByArticleIdOrderByDateModifiedDesc(Long articleId, Pageable pageable);

}
