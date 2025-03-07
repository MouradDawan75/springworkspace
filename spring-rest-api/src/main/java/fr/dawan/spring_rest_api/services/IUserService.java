package fr.dawan.spring_rest_api.services;

import fr.dawan.spring_rest_api.dtos.LoginDto;
import fr.dawan.spring_rest_api.dtos.ResponseLoginDto;
import fr.dawan.spring_rest_api.dtos.UserDto;

import java.util.List;

public interface IUserService {

    List<UserDto> getAll() throws Exception;
    UserDto findByEmail(String email) throws Exception;
    UserDto saveOrUpdate(UserDto userDto) throws Exception;;
    void deleteById(long id) throws Exception;
    UserDto getById(long id) throws Exception;
    ResponseLoginDto checkLogin(LoginDto loginDto) throws Exception;
}
