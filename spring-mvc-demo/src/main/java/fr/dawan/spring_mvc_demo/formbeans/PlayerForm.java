package fr.dawan.spring_mvc_demo.formbeans;

import fr.dawan.spring_mvc_demo.validators.PhoneNumberConstraint;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
Ajouter la dépendance spring-starter-validation
Doc: https://reflectoring.io/bean-validation-with-spring-boot/
 */
@Getter
@Setter
public class PlayerForm {

    @NotEmpty(message = "Champs obligatoire")
    private String name;

    @NotEmpty(message = "Champs obligatoire")
    private String role;

    @Min(18)
    @Max(64)
    private int age;

    @DecimalMin(value = "100", message = "Taille min 100")
    @DecimalMax(value = "200", message = "Taille max 200")
    private double size;

    @PhoneNumberConstraint(message = "Numéro de tél. invalide.")
    private String telephone;

}
