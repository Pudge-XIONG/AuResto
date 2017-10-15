package io.pudge.auresto.repository;

import io.pudge.auresto.domain.AuRestoBillStatus;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AuRestoBillStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuRestoBillStatusRepository extends JpaRepository<AuRestoBillStatus, Long> {

}
