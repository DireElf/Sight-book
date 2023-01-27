package it.leader.sightbook.controller;

import it.leader.sightbook.dto.SightDto;
import it.leader.sightbook.model.Sight;
import it.leader.sightbook.repository.SightRepository;
import it.leader.sightbook.service.SightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static it.leader.sightbook.controller.SightController.SIGHTS_CONTROLLER_PATH;

@RestController
@RequiredArgsConstructor
@RequestMapping("${base-url}" + SIGHTS_CONTROLLER_PATH)
public class SightController {

    public static final String SIGHTS_CONTROLLER_PATH = "/sights";
    public static final String ID = "/{id}";

    private final SightRepository sightRepository;
    private final SightService sightService;

    @GetMapping("")
    public List<Sight> getSights(@RequestParam(required = false) Map<String, String> params) {
        return params.isEmpty() ? sightRepository.findAll() : sightService.getSights(params);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Sight createSight(@RequestBody SightDto sightDto) {
        return sightService.createSight(sightDto);
    }

    @PutMapping(ID)
    public Sight updateSight(@PathVariable Long id, @RequestBody SightDto sightDto) {
        return sightService.updateSight(id, sightDto);
    }

    @DeleteMapping(ID)
    public void deleteSight(@PathVariable Long id) {
        sightRepository.deleteById(id);
    }
}
