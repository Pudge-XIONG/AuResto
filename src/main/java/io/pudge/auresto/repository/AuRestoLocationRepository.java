package io.pudge.auresto.repository;

import io.pudge.auresto.domain.AuRestoLocation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AuRestoLocation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuRestoLocationRepository extends JpaRepository<AuRestoLocation, Long> {

}
