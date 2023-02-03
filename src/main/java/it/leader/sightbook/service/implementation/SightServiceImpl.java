package it.leader.sightbook.service.implementation;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import it.leader.sightbook.dto.SightDto;
import it.leader.sightbook.dto.SightUpdateDto;
import it.leader.sightbook.model.City;
import it.leader.sightbook.model.QSight;
import it.leader.sightbook.model.Sight;
import it.leader.sightbook.model.SightType;
import it.leader.sightbook.repository.CityRepository;
import it.leader.sightbook.repository.SightRepository;
import it.leader.sightbook.service.SightService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
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

        final String filterName = "type";

        final String filterValue = params.get(filterName);
        boolean shouldSort = Boolean.parseBoolean(params.getOrDefault("sort", "false"));

        QSight qSight = QSight.sight;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);

        JPAQuery<Sight> query = jpaQueryFactory.selectFrom(qSight);

        if (filterValue != null) {
            booleanBuilder.and(qSight.type.eq(SightType.valueOf(filterValue)));
        }

        if (shouldSort) {
            return query
                    .where(booleanBuilder)
                    .orderBy(qSight.name.asc())
                    .fetch();
        }

        return query.where(booleanBuilder).fetch();
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
