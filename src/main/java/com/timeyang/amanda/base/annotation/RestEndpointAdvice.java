package com.timeyang.amanda.base.annotation;

import org.springframework.web.bind.annotation.ControllerAdvice;

import java.lang.annotation.*;

/**
 * Rest端点异常处理器
 *
 * @author chaokunyang
 * @create 2017-04-15
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ControllerAdvice
public @interface RestEndpointAdvice {
    String value() default "";
}