package fr.dawan.spring_rest_api.entities;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Manager1 extends BaseEntity{
    private String name;

}
