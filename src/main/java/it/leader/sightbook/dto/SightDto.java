package it.leader.sightbook.dto;

import it.leader.sightbook.model.SightType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class SightDto {
    private String name;
    private Date creationDate;
    private String description;
    private SightType sightType;
    private Long cityId;
}
