package com.emir.megamarket.web.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;

public class DateFormatValidator implements ConstraintValidator<ValidDate, String> {
    @Override
    public boolean isValid(String date, ConstraintValidatorContext constraintValidatorContext) {
        if (date == null) {
            return false;
        }
        try {
            OffsetDateTime.parse(date);
            return true;
        } catch (DateTimeParseException ex) {
            return false;
        }
    }
}
