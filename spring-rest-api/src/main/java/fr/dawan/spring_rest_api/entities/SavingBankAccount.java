package fr.dawan.spring_rest_api.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("compte_epargne")
public class SavingBankAccount extends BankAccount{

 private double interestRate;

}
