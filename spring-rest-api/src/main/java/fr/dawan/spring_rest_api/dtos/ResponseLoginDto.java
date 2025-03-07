package fr.dawan.spring_rest_api.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseLoginDto {

    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private String token;


}
