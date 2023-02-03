package it.leader.sightbook.repository;

import it.leader.sightbook.model.QSight;
import it.leader.sightbook.model.Sight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface SightRepository extends JpaRepository<Sight, Long>,
        QuerydslPredicateExecutor<Sight>, QuerydslBinderCustomizer<QSight> {

    @Override
    default void customize(QuerydslBindings bindings, QSight sight) {
    }
}
