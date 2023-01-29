package it.leader.sightbook.service.implementation;

import it.leader.sightbook.dto.SightDto;
import it.leader.sightbook.dto.SightUpdateDto;
import it.leader.sightbook.model.City;
import it.leader.sightbook.model.Sight;
import it.leader.sightbook.repository.CityRepository;
import it.leader.sightbook.repository.SightRepository;
import it.leader.sightbook.service.SightService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class SightServiceImpl implements SightService {

    private final SightRepository sightRepository;
    private final CityRepository cityRepository;


    @Override
    public Sight createSight(SightDto sightDto) {
        Sight sight = new Sight();
        sight.setName(sightDto.getName());
        sight.setCreationDate(sightDto.getCreationDate());
        sight.setDescription(sightDto.getDescription());
        sight.setType(sightDto.getSightType());
        String cityName = sightDto.getCityName();
        City city = cityRepository.findByName(cityName);
        city.addSightToCity(sight);
        sightRepository.save(sight);
        cityRepository.save(city);
        return sight;
    }

    @Override
    public Sight updateSight(Long id, SightUpdateDto sightUpdateDto) {
        Sight sight = sightRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        sight.setDescription(sightUpdateDto.getDescription());
        sightRepository.save(sight);
        return sight;
    }

    @Override
    public List<Sight> getSights(Map<String, String> params) {
        List<Sight> sightList = new ArrayList<>(sightRepository.findAll());
        boolean shouldFilter = params.containsKey("type");
        boolean shouldSort = params.containsKey("sorted") && params.get("sorted").equals("true");
        if (!shouldFilter && !shouldSort) {
            return sightList;
        }
        List<Sight> filteredList = null;
        if (shouldFilter) {
            filteredList = getListFilteredByType(sightList, params.get("type"));
            if (!shouldSort) return filteredList;
        }
        return filteredList == null ? getSortedList(sightList) : getSortedList(filteredList);
    }

    private List<Sight> getSortedList(List<Sight> list) {
        return list.stream()
                .sorted(Comparator.comparing(Sight::getName))
                .toList();
    }

    private List<Sight> getListFilteredByType(List<Sight> list, String criteria) {
        return list.stream()
                .filter(sight -> sight.getType().name().equals(criteria))
                .toList();
    }
}
