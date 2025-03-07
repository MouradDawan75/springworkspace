package fr.dawan.spring_rest_api.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Player extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @Embedded
    private Manager manager;
}
