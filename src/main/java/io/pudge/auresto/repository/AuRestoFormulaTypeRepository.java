package io.pudge.auresto.repository;

import io.pudge.auresto.domain.AuRestoFormulaType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AuRestoFormulaType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuRestoFormulaTypeRepository extends JpaRepository<AuRestoFormulaType, Long> {

}
