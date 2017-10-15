package io.pudge.auresto.repository;

import io.pudge.auresto.domain.AuRestoRecipeType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AuRestoRecipeType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuRestoRecipeTypeRepository extends JpaRepository<AuRestoRecipeType, Long> {

}
