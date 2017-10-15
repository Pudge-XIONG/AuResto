package io.pudge.auresto.repository;

import io.pudge.auresto.domain.AuRestoRestaurantType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AuRestoRestaurantType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuRestoRestaurantTypeRepository extends JpaRepository<AuRestoRestaurantType, Long> {

}
