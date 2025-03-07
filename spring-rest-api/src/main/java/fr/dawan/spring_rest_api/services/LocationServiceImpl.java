package fr.dawan.spring_rest_api.services;

import fr.dawan.spring_rest_api.dtos.LocationDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class LocationServiceImpl implements ILocationService{

    /*
    RestTemplate: classe de Spring permettant l'exécution de requêtes HTTP
     */

    @Autowired
    private RestTemplate restTemplate;

    private String URL = "https://dawan.org/public/location";

    @Override
    public List<LocationDto> getFromExternalSystem() throws Exception {

        /*
        getForEntity: renvoie le status code plus le contenu du body
        getForObject: renvoie uniquement le contenu du body
         */

        ResponseEntity<LocationDto[]> response = restTemplate.getForEntity(URL, LocationDto[].class);
        if(response.getStatusCode() == HttpStatus.OK && response.hasBody()){
            LocationDto[] result =  response.getBody();

            return Arrays.asList(result);
        }

        throw new Exception("No location result.....");
    }
}
