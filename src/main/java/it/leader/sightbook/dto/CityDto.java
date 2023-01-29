package it.leader.sightbook.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CityDto implements Transferable {
    private String name;
    private Integer population;
    private Boolean hasMetro;
    private String country;
    private Set<Long> sightIds;
}
