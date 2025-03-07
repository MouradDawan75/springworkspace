package fr.dawan.spring_rest_api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "compte_type", discriminatorType = DiscriminatorType.STRING, length = 15)
public class BankAccount {

    @Id
    private String number;
    private double balance;
}
