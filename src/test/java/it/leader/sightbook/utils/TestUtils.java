package it.leader.sightbook.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.leader.sightbook.dto.CityDto;
import it.leader.sightbook.dto.SightDto;
import it.leader.sightbook.dto.Transferable;
import it.leader.sightbook.model.Sight;
import it.leader.sightbook.repository.CityRepository;
import it.leader.sightbook.repository.SightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Component
public class TestUtils {
    public static final String PATH_TO_FIXTURES = "src/test/resources/fixtures/";
    public static final String BASE_URL = "/api";

    public final CityDto SAMPLE_CITY_DTO_1 = fromJson(
            readFixtureJson("sample_city_dto1.json"),
            new TypeReference<>() {
    });
    public final CityDto SAMPLE_CITY_DTO_2 = fromJson(
            readFixtureJson("sample_city_dto2.json"),
            new TypeReference<>() {
    });
    public final CityDto SAMPLE_CITY_DTO_3 = fromJson(
            readFixtureJson("sample_city_dto3.json"),
            new TypeReference<>() {
    });

    public final SightDto SAMPLE_SIGHT_DTO_1 = fromJson(
            readFixtureJson("sample_sight_dto1.json"),
            new TypeReference<>() {
    });
    public final SightDto SAMPLE_SIGHT_DTO_2 = fromJson(
            readFixtureJson("sample_sight_dto2.json"),
            new TypeReference<>() {
    });
    public final SightDto SAMPLE_SIGHT_DTO_3 = fromJson(
            readFixtureJson("sample_sight_dto3.json"),
            new TypeReference<>() {
    });

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private SightRepository sightRepository;

    private static final ObjectMapper MAPPER = new ObjectMapper().findAndRegisterModules();

    public void clearDB() {
        sightRepository.deleteAll();
        cityRepository.deleteAll();
    }

    public String asJson(final Object object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T fromJson(final String json, final TypeReference<T> to) {
        try {
            return MAPPER.readValue(json, to);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String readFixtureJson(String fileName) {
        try {
            return Files.readString(Path.of(PATH_TO_FIXTURES + fileName).toAbsolutePath().normalize());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public MockHttpServletRequestBuilder buildRequest(Transferable dto, String path) throws Exception {
        return post(BASE_URL + path)
                .content(asJson(dto))
                .contentType(APPLICATION_JSON);
    }

    public boolean isSorted(List<Sight> list) {
        Iterator<Sight> iterator = list.iterator();
        String current, previous = iterator.next().getName();
        while (iterator.hasNext()) {
            current = iterator.next().getName();
            if (previous.compareTo(current) > 0) {
                return false;
            }
            previous = current;
        }
        return true;
    }
}
