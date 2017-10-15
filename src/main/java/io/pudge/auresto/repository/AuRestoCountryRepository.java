package io.pudge.auresto.repository;

import io.pudge.auresto.domain.AuRestoCountry;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AuRestoCountry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuRestoCountryRepository extends JpaRepository<AuRestoCountry, Long> {

}
