package io.pudge.auresto.repository;

import io.pudge.auresto.domain.AuRestoRestaurant;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AuRestoRestaurant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuRestoRestaurantRepository extends JpaRepository<AuRestoRestaurant, Long> {

}
