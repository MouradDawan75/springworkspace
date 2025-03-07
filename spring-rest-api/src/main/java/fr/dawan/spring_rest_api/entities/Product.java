package fr.dawan.spring_rest_api.entities;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
/*
@Audited: Hibernate Envers
 */
//@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED) //gestion des relations
//@Entity(name = "t_products") si besoin de modifier le nom de la table
public class Product extends BaseEntity{

    /*
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Version
    private int version;

     */

    @Column(unique = true, nullable = false, length = 255)
    private String description;

    @Column(name = "priceht", precision = 2)
    private double price;

    //@Lob: Large object -> fichier - texte large
    //private byte[] image; // stocker le binaire de l'image en BD
    private String imagePath; // emplacement de l'image



    public enum ProductState{
        NEUF,EXCELLENT,MAUVAIS
    }

    @Enumerated(EnumType.STRING)
    private ProductState state;

    @ElementCollection // 1 table product_ingredients (PK-FK: product_id)
    private Set<String> ingredients = new HashSet<>();

    @ElementCollection // 1 table (PK-FK: product_id)
    @CollectionTable(name = "t_price_promo")
    @MapKeyColumn(name = "promotion")
    @Column(name = "price")
    private Map<String, Double> priceByPromotions;

    //Champs ignoré lors du mapping
    @Transient
    private String testChamps;

    @ManyToOne
    @JoinColumn(name = "category_id") //optionnel
    private Category category;

    @ManyToMany(cascade = CascadeType.REMOVE, mappedBy = "products")
    private Set<Supplier> suppliers = new HashSet<>();

    @PrePersist
    public void prePersist(){
        /*
        remplir des tables.......
         */
    }

    @PreRemove
    public void preRemove(){
        /*
        conserver le product dans une tabe temporaire
         */
    }

}
