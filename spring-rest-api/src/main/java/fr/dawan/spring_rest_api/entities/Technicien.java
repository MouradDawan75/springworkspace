package fr.dawan.spring_rest_api.entities;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Technicien extends Employe{

    private String poste;
}
