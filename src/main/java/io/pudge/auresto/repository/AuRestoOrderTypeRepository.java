package io.pudge.auresto.repository;

import io.pudge.auresto.domain.AuRestoOrderType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AuRestoOrderType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuRestoOrderTypeRepository extends JpaRepository<AuRestoOrderType, Long> {

}
