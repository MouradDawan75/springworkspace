package fr.dawan.spring_rest_api.controllers;

import fr.dawan.spring_rest_api.dtos.LocationDto;
import fr.dawan.spring_rest_api.services.ILocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private ILocationService locationService;

    @GetMapping(produces = "application/json")
    public List<LocationDto> getLocations() throws Exception{
        return locationService.getFromExternalSystem();
    }
}
