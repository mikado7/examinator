package com.examinator.exam.question;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConstraintValidator {

    private static Validator validator;

    static  {
        initializeValidator();
    }

    private static void initializeValidator() {
        HibernateValidatorConfiguration configuration = Validation.byProvider(HibernateValidator.class).configure();
        validator = configuration.buildValidatorFactory().getValidator();
    }

    public static void validate(Object data) {
        validator.validate(data)
                .forEach(violation -> {
                    String propertyPath = violation.getPropertyPath()
                            .toString();
                    throw new IllegalArgumentException(propertyPath + " " + violation.getMessage());
                });
    }



}
