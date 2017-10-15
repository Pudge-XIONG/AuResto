package io.pudge.auresto.repository;

import io.pudge.auresto.domain.AuRestoRecipe;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AuRestoRecipe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuRestoRecipeRepository extends JpaRepository<AuRestoRecipe, Long> {

}
