package it.leader.sightbook.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.leader.sightbook.dto.CityDto;
import it.leader.sightbook.dto.CityUpdateDto;
import it.leader.sightbook.dto.SightDto;
import it.leader.sightbook.model.SightType;
import it.leader.sightbook.repository.CityRepository;
import it.leader.sightbook.repository.SightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestUtils {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private SightRepository sightRepository;

    private static final ObjectMapper mapper = new ObjectMapper();

    public static CityDto getSampleCityDto1() {
        CityDto cityDto = new CityDto();
        cityDto.setName("Tomsk");
        cityDto.setPopulation(1000);
        cityDto.setHasMetro(false);
        cityDto.setCountry("Russia");
        return cityDto;
    }

    public static CityDto getSampleCityDto2() {
        CityDto cityDto = new CityDto();
        cityDto.setName("Seversk");
        cityDto.setPopulation(100);
        cityDto.setHasMetro(false);
        cityDto.setCountry("Russia");
        return cityDto;
    }

    public static SightDto getSampleSightDto1() {
        SightDto sightDto = new SightDto();
        sightDto.setName("Monument1");
        sightDto.setDescription("Description");
        sightDto.setCityName("Tomsk");
        sightDto.setSightType(SightType.MONUMENT);
        sightDto.setCreationDate(parseDate("2000-01-01"));
        return sightDto;
    }

    public static SightDto getSampleSightDto2() {
        SightDto sightDto = new SightDto();
        sightDto.setName("RESERVE");
        sightDto.setDescription("Description");
        sightDto.setCityName("Seversk");
        sightDto.setSightType(SightType.RESERVE);
        sightDto.setCreationDate(parseDate("2001-02-02"));
        return sightDto;
    }

    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public ResultActions perform(final MockHttpServletRequestBuilder request) throws Exception {
        return mockMvc.perform(request);
    }

    public static String asJson(final Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }

    public static <T> T fromJson(final String json, final TypeReference<T> to) throws JsonProcessingException {
        return mapper.readValue(json, to);
    }
}
