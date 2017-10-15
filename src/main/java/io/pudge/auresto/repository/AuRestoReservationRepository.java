package io.pudge.auresto.repository;

import io.pudge.auresto.domain.AuRestoReservation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AuRestoReservation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuRestoReservationRepository extends JpaRepository<AuRestoReservation, Long> {

}
