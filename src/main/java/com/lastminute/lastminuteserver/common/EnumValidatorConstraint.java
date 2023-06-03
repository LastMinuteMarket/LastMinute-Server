package com.lastminute.lastminuteserver.common;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnumValidatorConstraint implements ConstraintValidator<EnumValid, Object> {

    Set<Object> values;

    @Override
    public void initialize(EnumValid constraintAnnotation) {
        values = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
                .map(Convertable::getConvertKey)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return values.contains(value);
    }
}
