package it.leader.sightbook.service.implementation;

import it.leader.sightbook.dto.SightDto;
import it.leader.sightbook.model.City;
import it.leader.sightbook.model.Sight;
import it.leader.sightbook.repository.CityRepository;
import it.leader.sightbook.repository.SightRepository;
import it.leader.sightbook.service.SightService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Long cityId = sightDto.getCityId();
        City city = cityRepository.findById(cityId).orElseThrow(EntityNotFoundException::new);
        sight.setCity(city);
        sightRepository.save(sight);
        return sight;
    }

    @Override
    public Sight updateSight(Long id, SightDto sightDto) {
        Sight sight = sightRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        sight.setDescription(sightDto.getDescription());
        sightRepository.save(sight);
        return sight;
    }

    @Override
    public List<Sight> getSights(Map<String, String> params) {
        List<Sight> sightList = sightRepository.findAll();
        if (params.containsKey("type") && params.get("type").equals("true")) {
            sightList = sightList.stream()
                    .filter(sight -> sight.getType().name().equals(params.get("type")))
                    .toList();
        }
        if (params.containsKey("sorted") && params.get("sorted").equals("true")) {
            sightList = sightList.stream()
                    .sorted(Comparator.comparing(Sight::getName))
                    .toList();
        }
        return sightList;
     }
}
