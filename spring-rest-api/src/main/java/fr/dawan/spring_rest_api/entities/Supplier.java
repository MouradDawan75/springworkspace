package fr.dawan.spring_rest_api.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Supplier extends BaseEntity{

    private String name;

    @ManyToMany(cascade = CascadeType.REMOVE)
    private Set<Product> products = new HashSet<>();
}
