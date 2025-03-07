package fr.dawan.spring_rest_api.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Player1 extends BaseEntity{

    private String name;

    @OneToOne @MapsId
    private Manager1 manager;
}
