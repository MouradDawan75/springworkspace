package fr.dawan.spring_rest_api.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    private long id;
    private int version;
    private String description;
    private double price;
    private long categoryId;
    private String imagePath;
}
