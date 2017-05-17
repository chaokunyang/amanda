package com.timeyang.amanda.core.valadation;


import org.hibernate.validator.constraints.NotBlank;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * NotBank验证器
 *
 * @author chaokunyang
 * @create 2017-04-15
 */
public class NotBlankValidator implements ConstraintValidator<NotBlank, CharSequence> {
    @Override
    public void initialize(NotBlank constraintAnnotation) {

    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if(value instanceof String)
            return ((String) value).trim().length() > 0;
        return value.toString().trim().length() > 0;
    }
}