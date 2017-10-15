package io.pudge.auresto.repository;

import io.pudge.auresto.domain.AuRestoMenu;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AuRestoMenu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuRestoMenuRepository extends JpaRepository<AuRestoMenu, Long> {

}
