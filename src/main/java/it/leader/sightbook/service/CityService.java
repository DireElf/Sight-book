package it.leader.sightbook.service;

import it.leader.sightbook.dto.CityDto;
import it.leader.sightbook.model.City;
import it.leader.sightbook.model.Sight;

public interface CityService {
    City createCity(CityDto cityDto);
    City updateCity(Long id, CityDto dto);
    Iterable<Sight> getCitySights(Long cityId);
}
