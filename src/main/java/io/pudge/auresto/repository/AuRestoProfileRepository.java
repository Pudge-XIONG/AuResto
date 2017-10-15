package io.pudge.auresto.repository;

import io.pudge.auresto.domain.AuRestoProfile;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AuRestoProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuRestoProfileRepository extends JpaRepository<AuRestoProfile, Long> {

}
