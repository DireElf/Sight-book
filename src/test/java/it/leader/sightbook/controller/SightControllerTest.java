package it.leader.sightbook.controller;

import com.fasterxml.jackson.core.type.TypeReference;
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
import static it.leader.sightbook.controller.CityController.ID;
import static it.leader.sightbook.controller.SightController.SIGHTS_CONTROLLER_PATH;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SightControllerTest {
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
    void createSight() throws Exception {
        assertEquals(0, sightRepository.findAll().size());

        mockMvc.perform(testUtils.buildRequest(testUtils.SAMPLE_CITY_DTO_2, CITIES_CONTROLLER_PATH));

        City city = cityRepository.findAll().get(0);
        String cityName = city.getName();

        SightDto sightDto1 = testUtils.SAMPLE_SIGHT_DTO_1;
        sightDto1.setCityName(cityName);

        final MockHttpServletRequestBuilder request = post(BASE_URL + SIGHTS_CONTROLLER_PATH)
                .content(testUtils.asJson(sightDto1))
                .contentType(APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isCreated());

        assertEquals(1, cityRepository.findAll().size());
    }

    @Test
    void getAllSights() throws Exception {
        mockMvc.perform(testUtils.buildRequest(testUtils.SAMPLE_CITY_DTO_2, CITIES_CONTROLLER_PATH));

        City city = cityRepository.findAll().get(0);
        String cityName = city.getName();

        SightDto sightDto1 = testUtils.SAMPLE_SIGHT_DTO_1;
        sightDto1.setCityName(cityName);
        mockMvc.perform(testUtils.buildRequest(sightDto1, SIGHTS_CONTROLLER_PATH))
                .andExpect(status().isCreated());

        SightDto sightDto2 = testUtils.SAMPLE_SIGHT_DTO_2;
        sightDto2.setCityName(cityName);
        mockMvc.perform(testUtils.buildRequest(sightDto2, SIGHTS_CONTROLLER_PATH))
                .andExpect(status().isCreated());

        MockHttpServletResponse response = mockMvc.perform(
                        get(BASE_URL + SIGHTS_CONTROLLER_PATH))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        Set<Sight> sights = testUtils.fromJson(response.getContentAsString(), new TypeReference<>() {
        });

        assertEquals(2, sights.size());
    }

    @Test
    void getSortedSights() throws Exception {
        mockMvc.perform(testUtils.buildRequest(testUtils.SAMPLE_CITY_DTO_2, CITIES_CONTROLLER_PATH));

        City city = cityRepository.findAll().get(0);
        String cityName = city.getName();

        SightDto sightDto1 = testUtils.SAMPLE_SIGHT_DTO_1;
        sightDto1.setCityName(cityName);
        mockMvc.perform(testUtils.buildRequest(sightDto1, SIGHTS_CONTROLLER_PATH))
                .andExpect(status().isCreated());

        SightDto sightDto2 = testUtils.SAMPLE_SIGHT_DTO_2;
        sightDto2.setCityName(cityName);
        mockMvc.perform(testUtils.buildRequest(sightDto2, SIGHTS_CONTROLLER_PATH))
                .andExpect(status().isCreated());

        SightDto sightDto3 = testUtils.SAMPLE_SIGHT_DTO_3;
        sightDto3.setCityName(cityName);
        mockMvc.perform(testUtils.buildRequest(sightDto3, SIGHTS_CONTROLLER_PATH))
                .andExpect(status().isCreated());

        MockHttpServletResponse response = mockMvc.perform(
                get(BASE_URL + SIGHTS_CONTROLLER_PATH).param("sorted", "true"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        List<Sight> sortedSights = testUtils.fromJson(response.getContentAsString(), new TypeReference<>() {
        });

        assertTrue(testUtils.isSorted(sortedSights));
    }

    @Test
    void getFilteredSights() throws Exception {
        mockMvc.perform(testUtils.buildRequest(testUtils.SAMPLE_CITY_DTO_3, CITIES_CONTROLLER_PATH));

        City city = cityRepository.findAll().get(0);
        String cityName = city.getName();

        SightDto sightDto1 = testUtils.SAMPLE_SIGHT_DTO_1;
        sightDto1.setCityName(cityName);
        mockMvc.perform(testUtils.buildRequest(sightDto1, SIGHTS_CONTROLLER_PATH));

        SightDto sightDto2 = testUtils.SAMPLE_SIGHT_DTO_2;
        sightDto2.setCityName(cityName);
        mockMvc.perform(testUtils.buildRequest(sightDto2, SIGHTS_CONTROLLER_PATH));

        SightDto sightDto3 = testUtils.SAMPLE_SIGHT_DTO_3;
        sightDto3.setCityName(cityName);
        mockMvc.perform(testUtils.buildRequest(sightDto3, SIGHTS_CONTROLLER_PATH));

        List<Sight> allSights = sightRepository.findAll();

        int totalSightsAmount = allSights.size();

        String criteria = sightRepository.findAll().get(0).getType().name();

        MockHttpServletResponse response = mockMvc.perform(
                        get(BASE_URL + SIGHTS_CONTROLLER_PATH).param("type", criteria))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        List<Sight> filteredSights = testUtils.fromJson(response.getContentAsString(), new TypeReference<>() {
        });

        assertNotEquals(totalSightsAmount, filteredSights.size());
    }

    @Test
    void deleteSight() throws Exception {
        assertEquals(0, sightRepository.count());

        mockMvc.perform(testUtils.buildRequest(testUtils.SAMPLE_CITY_DTO_2, CITIES_CONTROLLER_PATH));
        City city = cityRepository.findAll().get(0);
        String cityName = city.getName();

        SightDto sightDto1 = testUtils.SAMPLE_SIGHT_DTO_1;
        sightDto1.setCityName(cityName);
        mockMvc.perform(testUtils.buildRequest(sightDto1, SIGHTS_CONTROLLER_PATH));

        assertEquals(1, sightRepository.count());

        Long sightId = sightRepository.findAll().get(0).getId();

        mockMvc.perform(delete(BASE_URL + SIGHTS_CONTROLLER_PATH + ID, sightId))
                .andExpect(status().isOk());

        assertEquals(0, sightRepository.count());
        assertEquals(1, cityRepository.count());
        Set<Sight> citySights = cityRepository.findAll().get(0).getSights();
        assertEquals(0, citySights.size());
    }
}