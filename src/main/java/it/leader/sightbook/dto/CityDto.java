package it.leader.sightbook.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
public class CityDto {
    private String name;
    private Integer population;
    private Boolean hasMetro;
    private String country;
    private Set<Long> sightIds;
}
