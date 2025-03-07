package fr.dawan.spring_rest_api.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private long id;
    private int version;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
