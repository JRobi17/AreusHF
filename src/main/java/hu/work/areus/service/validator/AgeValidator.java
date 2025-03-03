package hu.work.areus.service.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Custom constraint validator for validating the date of birth and age of a customer.
 */
public class AgeValidator implements ConstraintValidator<Age, String> {

    @Override
    public boolean isValid(String birthDateStr, ConstraintValidatorContext context) {
        if (birthDateStr == null) {
            return false;
        }
        try {
            LocalDate birthDate = LocalDate.parse(birthDateStr, DateTimeFormatter.ofPattern("yyyy.MM.dd"));
            return ChronoUnit.YEARS.between(birthDate, LocalDate.now()) >= 18;
        } catch (Exception e) {
            return false;
        }
    }
}
