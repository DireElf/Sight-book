package it.leader.sightbook.controller;

import it.leader.sightbook.dto.CityDto;
import it.leader.sightbook.model.City;
import it.leader.sightbook.model.Sight;
import it.leader.sightbook.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

import static it.leader.sightbook.controller.CityController.CITIES_CONTROLLER_PATH;

@RestController
@RequiredArgsConstructor
@RequestMapping("${base-url}" + CITIES_CONTROLLER_PATH)
public class CityController {

    public static final String CITIES_CONTROLLER_PATH = "/cities";
    public static final String ID = "/{id}";
    public static final String SIGHTS = "/sights";

    private final CityService cityService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public City createCity(@RequestBody CityDto cityDto) {
        return cityService.createCity(cityDto);
    }

    @PutMapping(ID)
    public City updateCity(@PathVariable Long id, @RequestBody CityDto cityDto) {
        return cityService.updateCity(id, cityDto);
    }

    @GetMapping(ID + SIGHTS)
    public Set<Sight> getCitySights(@PathVariable Long id) {
        return cityService.getCitySights(id);
    }
}
