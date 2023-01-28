package it.leader.sightbook.service;

import it.leader.sightbook.dto.SightDto;
import it.leader.sightbook.dto.SightUpdateDto;
import it.leader.sightbook.model.Sight;

import java.util.List;
import java.util.Map;

public interface SightService {
    Sight createSight(SightDto sightDto);
    Sight updateSight(Long id, SightUpdateDto sightDto);
    List<Sight> getSights(Map<String, String> params);
}
