package it.leader.sightbook.service;

import it.leader.sightbook.dto.CityDto;
import it.leader.sightbook.dto.CityUpdateDto;
import it.leader.sightbook.model.City;
import it.leader.sightbook.model.Sight;

import java.util.Set;

public interface CityService {
    City createCity(CityDto cityDto);
    City updateCity(Long id, CityUpdateDto dto);
    Set<Sight> getCitySights(Long cityId);
}
