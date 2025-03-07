package fr.dawan.spring_rest_api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationDto {

    private int id;
    private String name;

    //si besoin de modifier le nom d'un attribut json dans le dto.
   // @JsonProperty(value="name")
   // private String nom;
    private String address;
    private String zipCode;
    private String city;

    public String getAdresseComplete(){
        return address+" "+zipCode+" "+city;
    }
}
