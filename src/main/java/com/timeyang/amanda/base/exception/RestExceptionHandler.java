package com.timeyang.amanda.base.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * Rest端点 Bean Validation 约束违反异常处理器
 *
 * @author chaokunyang
 * @create 2017-04-15
 */
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handle(ConstraintViolationException e) {
        ErrorResponse errors = new ErrorResponse();
        e.getConstraintViolations().forEach(constraintViolation -> {
            ErrorItem errorItem = new ErrorItem();
            errorItem.setCode(constraintViolation.getMessageTemplate());
            errorItem.setMessage(constraintViolation.getMessage());
            errors.addError(errorItem);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings("unused")
    @Getter
    @Setter
    public static class ErrorItem {
        private String code;
        private String message;
    }

    @SuppressWarnings("unused")
    @Getter
    @Setter
    public static class ErrorResponse {

        private List<ErrorItem> errors = new ArrayList<>();

        public void addError(ErrorItem error) {
            this.errors.add(error);
        }
    }
}