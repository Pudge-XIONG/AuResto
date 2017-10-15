package io.pudge.auresto.repository;

import io.pudge.auresto.domain.AuRestoFormula;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AuRestoFormula entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuRestoFormulaRepository extends JpaRepository<AuRestoFormula, Long> {

}
