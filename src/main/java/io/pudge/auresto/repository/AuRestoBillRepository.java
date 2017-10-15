package io.pudge.auresto.repository;

import io.pudge.auresto.domain.AuRestoBill;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AuRestoBill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuRestoBillRepository extends JpaRepository<AuRestoBill, Long> {

}
