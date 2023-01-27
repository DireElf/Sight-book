package it.leader.sightbook.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "sights")
public class Sight {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "sight_name", unique = true)
    private String name;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "description")
    private String description;

    @Column(name = "sight_type")
    @Enumerated(EnumType.STRING)
    private SightType type;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false, foreignKey = @ForeignKey(name = "FK_SIGHTS_CITY_ID_COL"))
    private City city;
}
