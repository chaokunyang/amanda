package com.timeyang.amanda.blog.service;

import com.timeyang.amanda.blog.domain.Comment;

/**
 * comment service
 *
 * @author chaokunyang
 */
public interface CommentService {

    Comment create(Comment comment);

    Comment update(Comment comment);

    void delete(Long id);

}
