package fr.dawan.spring_rest_api.services;

import fr.dawan.spring_rest_api.dtos.LocationDto;

import java.util.List;

public interface ILocationService {
    List<LocationDto> getFromExternalSystem() throws Exception;
}
