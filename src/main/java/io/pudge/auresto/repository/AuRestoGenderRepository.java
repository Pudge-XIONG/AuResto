package io.pudge.auresto.repository;

import io.pudge.auresto.domain.AuRestoGender;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AuRestoGender entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuRestoGenderRepository extends JpaRepository<AuRestoGender, Long> {

}
