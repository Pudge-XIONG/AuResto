package io.pudge.auresto.repository;

import io.pudge.auresto.domain.AuRestoUser;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AuRestoUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuRestoUserRepository extends JpaRepository<AuRestoUser, Long> {

}
