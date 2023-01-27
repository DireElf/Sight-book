package it.leader.sightbook.service.implementation;

import it.leader.sightbook.dto.CityDto;
import it.leader.sightbook.model.City;
import it.leader.sightbook.model.Sight;
import it.leader.sightbook.repository.CityRepository;
import it.leader.sightbook.repository.SightRepository;
import it.leader.sightbook.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final SightRepository sightRepository;
    private final CityRepository cityRepository;

    @Override
    public City createCity(CityDto cityDto) {
        final City city = new City();
        city.setName(city.getName());
        city.setPopulation(cityDto.getPopulation());
        city.setHasMetro(cityDto.getHasMetro());
        city.setCountry(cityDto.getCountry());
        if (cityDto.getSightIds() != null) {
            city.setSights(getSightsByIds(cityDto));
        }
        cityRepository.save(city);
        return city;
    }

    private Set<Sight> getSightsByIds(CityDto cityDto) {
        return cityDto.getSightIds().stream()
                .map(id -> sightRepository.findById(id).orElseThrow(EntityNotFoundException::new))
                .collect(Collectors.toSet());
    }

    @Override
    public City updateCity(Long id, CityDto cityDto) {
        final City city = cityRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        city.setPopulation(cityDto.getPopulation());
        city.setHasMetro(cityDto.getHasMetro());
        cityRepository.save(city);
        return city;
    }

    @Override
    public Set<Sight> getCitySights(Long cityId) {
        final City city = cityRepository.findById(cityId).orElseThrow(EntityNotFoundException::new);
        if (city.getSights() == null) {
            return new HashSet<>();
        }
        return city.getSights();
    }
}
