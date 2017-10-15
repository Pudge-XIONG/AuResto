package io.pudge.auresto.repository;

import io.pudge.auresto.domain.AuRestoTable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AuRestoTable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuRestoTableRepository extends JpaRepository<AuRestoTable, Long> {

}
