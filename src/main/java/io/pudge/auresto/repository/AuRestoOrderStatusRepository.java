package io.pudge.auresto.repository;

import io.pudge.auresto.domain.AuRestoOrderStatus;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AuRestoOrderStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuRestoOrderStatusRepository extends JpaRepository<AuRestoOrderStatus, Long> {

}
