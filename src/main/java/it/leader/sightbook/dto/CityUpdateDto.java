package it.leader.sightbook.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CityUpdateDto implements Transferable {
    private Integer population;
    private Boolean hasMetro;
}
