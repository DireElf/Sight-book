package it.leader.sightbook.repository;

import it.leader.sightbook.model.Sight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SightRepository extends JpaRepository<Sight, Long> {
}
