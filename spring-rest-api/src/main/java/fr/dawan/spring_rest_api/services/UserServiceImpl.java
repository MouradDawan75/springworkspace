package fr.dawan.spring_rest_api.services;

import fr.dawan.spring_rest_api.dtos.LoginDto;
import fr.dawan.spring_rest_api.dtos.ResponseLoginDto;
import fr.dawan.spring_rest_api.dtos.UserDto;
import fr.dawan.spring_rest_api.entities.User;
import fr.dawan.spring_rest_api.exceptions.AuthentificationException;
import fr.dawan.spring_rest_api.repositories.UserRepository;
import fr.dawan.spring_rest_api.tools.DtoTool;
import fr.dawan.spring_rest_api.tools.TokenSaver;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtService jwtService;


    @Override
    public List<UserDto> getAll() throws Exception {
        List<UserDto> result = new ArrayList<>();
        List<User> entities = userRepository.findAll();
        for(User u : entities){
            result.add(DtoTool.convert(u, UserDto.class));
        }

        return result;
    }

    @Async
    public CompletableFuture<List<UserDto>> getAllAsync() throws Exception {
        List<UserDto> result = new ArrayList<>();
        List<User> entities = userRepository.findAll();
        for(User u : entities){
            result.add(DtoTool.convert(u, UserDto.class));
        }

        return CompletableFuture.completedFuture(result);
    }

    @Override
    public UserDto findByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if(user != null){
            return DtoTool.convert(user,UserDto.class);
        }
        return null;
    }

    @Override
    public UserDto saveOrUpdate(UserDto userDto) throws Exception {

        User user = DtoTool.convert(userDto, User.class);

        if(userDto.getId() == 0){
            user.setPassword(encoder.encode(userDto.getPassword()));
        }else{
            //vérifier si password modifié côté front
            User userDB = userRepository.findByEmail(userDto.getEmail());
            if(!userDB.getPassword().equals(userDto.getPassword())){
                user.setPassword(encoder.encode(userDto.getPassword()));
            }

        }

        User savedUser = userRepository.saveAndFlush(user);


        return DtoTool.convert(savedUser, UserDto.class);
    }

    @Override
    public void deleteById(long id) throws Exception {
        userRepository.deleteById(id);

    }

    @Override
    public UserDto getById(long id) throws Exception {
        Optional<User> optional = userRepository.findById(id);
        if(optional.isPresent()){
            return DtoTool.convert(optional.get(), UserDto.class);
        }
        return null;
    }

    @Override
    public ResponseLoginDto checkLogin(LoginDto loginDto) throws Exception {

        User user = userRepository.findByEmail(loginDto.getEmail());
        if(user != null && encoder.matches(loginDto.getPassword(), user.getPassword())){
            //connexion OK
            ResponseLoginDto response = DtoTool.convert(user, ResponseLoginDto.class);

            //Génération du Token
            //choisir les infos (claims) à insérer dans le token
            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", user.getId());
            claims.put("fullName", user.getFirstName()+" "+user.getLastName());

            //Génération du Token
            String token = jwtService.doGenerateToken(claims, user.getEmail());

            response.setToken(token);

            //Sauvegarde du Token côté serveur
            TokenSaver.mapTokenByEmail.put(user.getEmail(), token);

            return response;

        }

        throw new AuthentificationException("Echec connexion........");
    }
}
