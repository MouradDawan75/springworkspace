package fr.dawan.spring_mvc_demo.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/*
ConstraintValidator<Annotation,Type_attribut>
 */
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberConstraint,String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value != null && value.startsWith("0") && value.matches("[0-9]+") && value.length() == 10;
    }
}
