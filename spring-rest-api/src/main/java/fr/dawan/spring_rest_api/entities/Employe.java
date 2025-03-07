package fr.dawan.spring_rest_api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) //pas de table pour la classe mère
public abstract class Employe {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY) //-> TABLE_PER_CLASS: id ne peut pas être en auto incrément
    private int id;
    private String nom;
    private String prenom;
}
