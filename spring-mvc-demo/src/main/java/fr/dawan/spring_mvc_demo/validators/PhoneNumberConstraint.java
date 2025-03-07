package fr.dawan.spring_mvc_demo.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumberConstraint {
    /*
    Possibilité d'ajouter des attributs de type simple
     */
    String message() default "Invalide phone number";

    /*
    Params obligatoires: utilisés par Spring
     */

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};


}
