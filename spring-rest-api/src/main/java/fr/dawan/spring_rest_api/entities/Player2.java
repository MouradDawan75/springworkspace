package fr.dawan.spring_rest_api.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Player2 extends BaseEntity{

    private String name;

    @OneToOne
    private Manager2 manager;
}
