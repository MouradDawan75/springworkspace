package fr.dawan.spring_rest_api.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Manager {

    @Column(name = "manager_name")
    private String name;
}
