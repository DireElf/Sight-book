package it.leader.sightbook.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import it.leader.sightbook.dto.CityDto;
import it.leader.sightbook.dto.CityUpdateDto;
import it.leader.sightbook.dto.SightDto;
import it.leader.sightbook.model.City;
import it.leader.sightbook.model.Sight;
import it.leader.sightbook.repository.CityRepository;
import it.leader.sightbook.repository.SightRepository;
import it.leader.sightbook.service.CityService;

import it.leader.sightbook.service.SightService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Set;

import static it.leader.sightbook.controller.CityController.SIGHTS;
import static org.junit.jupiter.api.Assertions.*;
import static it.leader.sightbook.utils.TestUtils.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static it.leader.sightbook.controller.CityController.ID;

@SpringBootTest
@AutoConfigureMockMvc
class CityControllerTest {
    public static final String BASE_URL = "/api";
    public static final String CITIES_URL = "/cities";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private SightRepository sightRepository;

    @Autowired
    private CityService cityService;

    @Autowired
    private SightService sightService;

    @AfterEach
    void clear() {
        sightRepository.deleteAll();
        cityRepository.deleteAll();
    }

    @Test
    void testAddCity() throws Exception {
        CityDto cityDto = getSampleCityDto1();
        final MockHttpServletRequestBuilder request = post(BASE_URL + CITIES_URL)
                .content(asJson(cityDto))
                .contentType(APPLICATION_JSON);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isCreated());
        assertEquals(1, cityRepository.findAll().size());
    }

    @Test
    void updateCity() throws Exception {
        CityDto cityDto = getSampleCityDto1();
        cityService.createCity(cityDto);
        Long cityId = cityRepository.findAll().get(0).getId();

        CityUpdateDto cityUpdateDto = new CityUpdateDto(42, true);

        final MockHttpServletRequestBuilder request = put(BASE_URL + CITIES_URL + ID, cityId)
                .content(asJson(cityUpdateDto))
                .contentType(APPLICATION_JSON);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk());
        City updatedCity = cityRepository.findAll().get(0);
        assertEquals(42, updatedCity.getPopulation());
        assertEquals(true, updatedCity.getHasMetro());
    }

    @Test
    void testGetCitySights() throws Exception {
        CityDto cityDto = getSampleCityDto1();
        cityService.createCity(cityDto);

        SightDto sightDto1 = getSampleSightDto1();
        sightDto1.setCityName(cityDto.getName());
        sightService.createSight(sightDto1);
        Sight sight1 = sightRepository.findAll().get(0);

        SightDto sightDto2 = getSampleSightDto2();
        sightDto2.setCityName(cityDto.getName());
        sightService.createSight(sightDto2);
        Sight sight2 = sightRepository.findAll().get(1);

        City city = cityRepository.findAll().get(0);
        city.setSights(Set.of(sight1, sight2));
        cityRepository.save(city);

        Long cityId = cityRepository.findAll().get(0).getId();

        MockHttpServletResponse response = mockMvc.perform(get(BASE_URL + CITIES_URL + ID + SIGHTS, cityId))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        Set<Sight> citySights = fromJson(response.getContentAsString(), new TypeReference<>() {
        });

        assertEquals(2, citySights.size());
    }
}