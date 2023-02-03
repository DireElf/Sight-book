package it.leader.sightbook.service.implementation;

import it.leader.sightbook.dto.SightDto;
import it.leader.sightbook.dto.SightUpdateDto;
import it.leader.sightbook.model.City;
import it.leader.sightbook.model.Sight;
import it.leader.sightbook.repository.CityRepository;
import it.leader.sightbook.repository.SightRepository;
import it.leader.sightbook.service.SightService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class SightServiceImpl implements SightService {

    private final SightRepository sightRepository;
    private final CityRepository cityRepository;
    @PersistenceContext
    private final EntityManager entityManager;


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
        if (params.isEmpty()) {
            return sightRepository.findAll();
        }
        String requiredType = params.get("type");
        boolean shouldSort = params.getOrDefault("sorted", "false").equals("true");

        if (requiredType == null && shouldSort) {
            return getSortedSights();
        }
        if (requiredType != null && !shouldSort) {
            return getSightsByType(requiredType);
        }
        return getFilteredSightsByTypeSortedByName(requiredType);
    }

    private List<Sight> getSortedSights() {
        return sightRepository.findAll(Sort.by("name").ascending());
    }

    private List<Sight> getSightsByType(String requiredType) {
        String statement = "SELECT * FROM sights WHERE type = " + requiredType;
        return entityManager.createQuery(statement, Sight.class).getResultList();
    }

    private List<Sight> getFilteredSightsByTypeSortedByName(String requiredType) {
        String statement = "SELECT * FROM sights WHERE type = " + requiredType + "ORDER BY name";
        return entityManager.createQuery(statement, Sight.class).getResultList();
    }

    @Override
    public void deleteSight(Long id) {
        Optional<Sight> optional = sightRepository.findById(id);
        if (!optional.isPresent()) {
            throw new RuntimeException("Sight with ID " + id + "not found");
        }
        sightRepository.deleteById(id);
    }
}
