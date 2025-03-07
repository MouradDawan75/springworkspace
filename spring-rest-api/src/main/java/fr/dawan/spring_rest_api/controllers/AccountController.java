package fr.dawan.spring_rest_api.controllers;

import fr.dawan.spring_rest_api.dtos.LoginDto;
import fr.dawan.spring_rest_api.dtos.ResponseLoginDto;
import fr.dawan.spring_rest_api.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    private IUserService userService;


    @PostMapping(value = "/login", produces = "application/json", consumes = "application/json")
    public ResponseLoginDto login(@RequestBody LoginDto loginDto) throws Exception{
        return userService.checkLogin(loginDto);
    }
}
