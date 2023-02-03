package it.leader.sightbook.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "cities")
public class City {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city_name", unique = true)
    private String name;

    @Column(name = "population")
    private Integer population;

    @Column(name = "has_metro")
    private Boolean hasMetro;

    @Column(name = "country")
    private String country;

    @JsonManagedReference
    @OneToMany(mappedBy = "city", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Sight> sights;

    public void addSightToCity(Sight sight) {
        if (sights == null) {
            sights = new HashSet<>();
        }
        sight.setCity(this);
        sights.add(sight);
    }
}
