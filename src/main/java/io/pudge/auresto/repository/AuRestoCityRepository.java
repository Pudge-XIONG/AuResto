package io.pudge.auresto.repository;

import io.pudge.auresto.domain.AuRestoCity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AuRestoCity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuRestoCityRepository extends JpaRepository<AuRestoCity, Long> {

}
