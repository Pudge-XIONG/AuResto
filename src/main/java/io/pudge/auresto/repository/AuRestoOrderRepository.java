package io.pudge.auresto.repository;

import io.pudge.auresto.domain.AuRestoOrder;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AuRestoOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuRestoOrderRepository extends JpaRepository<AuRestoOrder, Long> {

}
