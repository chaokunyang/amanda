// package com.timeyang.amanda.api;
//
// import com.timeyang.amanda.blog.domain.Comment;
// import com.timeyang.amanda.blog.service.CommentService;
// import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.Logger;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
//
// /**
//  * 结合Oauth2.0或者开放用户注册
//  *
//  * @author chaokunyang
//  */
// @RestController
// @RequestMapping("/comment")
// public class CommentEndpoint {
//
//     private static final Logger LOGGER = LogManager.getLogger();
//
//     @Autowired
//     private CommentService commentService;
//
//     public Comment create(@RequestBody Comment comment) {
//         return commentService.create(comment);
//     }
//
// }
