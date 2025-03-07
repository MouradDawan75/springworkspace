package fr.dawan.spring_rest_api.controllers;

import fr.dawan.spring_rest_api.dtos.UserDto;
import fr.dawan.spring_rest_api.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping(value = "/all-user", produces = "application/json")
    public List<UserDto> getAll() throws Exception{
        return userService.getAll();
    }

    @PostMapping(value = {"/create-account", "/update-account"}, produces = "application/json", consumes = "application/json")
    public UserDto saveOrUpdate(@RequestBody UserDto userDto) throws Exception{
        UserDto savedUser = userService.saveOrUpdate(userDto);
        return savedUser;
    }

    @DeleteMapping(value = "/delete/{id}", produces = "text/plain")
    public ResponseEntity<String> deleteById(@PathVariable("id") long id) throws Exception{

        UserDto userDto = userService.getById(id);
        if(userDto == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id = "+id+" not found.");
        }else{
            userService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("User with id = "+id+" deleted.");
        }
    }
}
