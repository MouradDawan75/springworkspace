package fr.dawan.spring_rest_api.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Manager2 extends BaseEntity{

    private String name;

    @OneToOne(mappedBy = "manager")
    private Player2 player;
}
