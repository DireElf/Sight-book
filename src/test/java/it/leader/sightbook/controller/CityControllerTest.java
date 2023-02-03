package it.leader.sightbook.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import it.leader.sightbook.dto.CityUpdateDto;
import it.leader.sightbook.dto.SightDto;
import it.leader.sightbook.model.City;
import it.leader.sightbook.model.Sight;
import it.leader.sightbook.repository.CityRepository;
import it.leader.sightbook.repository.SightRepository;

import it.leader.sightbook.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;
import java.util.Set;

import static it.leader.sightbook.controller.CityController.CITIES_CONTROLLER_PATH;
import static it.leader.sightbook.controller.CityController.CITY_SIGHTS_ENDPOINT;
import static it.leader.sightbook.controller.SightController.SIGHTS_CONTROLLER_PATH;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static it.leader.sightbook.controller.CityController.ID;

@SpringBootTest
@AutoConfigureMockMvc
class CityControllerTest {
    public static final String BASE_URL = "/api";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private SightRepository sightRepository;

    @Autowired
    private TestUtils testUtils;

    @BeforeEach
    void init() throws Exception {
        testUtils.clearDB();
    }

    @Test
    void addCity() throws Exception {
        assertEquals(0, cityRepository.findAll().size());

        final MockHttpServletRequestBuilder request = post(BASE_URL + CITIES_CONTROLLER_PATH)
                .content(testUtils.asJson(testUtils.SAMPLE_CITY_DTO_1))
                .contentType(APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isCreated());

        assertEquals(1, cityRepository.findAll().size());
    }

    @Test
    void updateCity() throws Exception {
        mockMvc.perform(testUtils.buildRequest(testUtils.SAMPLE_CITY_DTO_1, CITIES_CONTROLLER_PATH));

        City city = cityRepository.findAll().get(0);

        final long cityId = city.getId();
        final int samplePopulation = city.getPopulation() + 1;
        final boolean sampleHasMetro = !city.getHasMetro();

        CityUpdateDto cityUpdateDto = new CityUpdateDto(samplePopulation, sampleHasMetro);

        final MockHttpServletRequestBuilder request = put(BASE_URL + CITIES_CONTROLLER_PATH + ID, cityId)
                .content(testUtils.asJson(cityUpdateDto))
                .contentType(APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());

        City updatedCity = cityRepository.findAll().get(0);

        assertEquals(samplePopulation, updatedCity.getPopulation());
        assertEquals(sampleHasMetro, updatedCity.getHasMetro());
    }

    @Test
    void getCitySights() throws Exception {
        mockMvc.perform(testUtils.buildRequest(testUtils.SAMPLE_CITY_DTO_2, CITIES_CONTROLLER_PATH));

        City city = cityRepository.findAll().get(0);
        Long cityId = cityRepository.findAll().get(0).getId();
        String cityName = city.getName();
        Set<Sight> citySights = city.getSights();

        assertEquals(0, citySights.size());

        SightDto sightDto1 = testUtils.SAMPLE_SIGHT_DTO_1;
        sightDto1.setCityName(cityName);
        mockMvc.perform(testUtils.buildRequest(sightDto1, SIGHTS_CONTROLLER_PATH));

        SightDto sightDto2 = testUtils.SAMPLE_SIGHT_DTO_2;
        sightDto2.setCityName(cityName);
        mockMvc.perform(testUtils.buildRequest(sightDto2, SIGHTS_CONTROLLER_PATH));

        MockHttpServletResponse response = mockMvc.perform(
                get(BASE_URL + CITIES_CONTROLLER_PATH + ID + CITY_SIGHTS_ENDPOINT, cityId))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        List<Sight> updatedCitySights = testUtils.fromJson(response.getContentAsString(),
                new TypeReference<List<Sight>>() {
        });

        assertEquals(2, updatedCitySights.size());
    }
}