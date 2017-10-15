package io.pudge.auresto.repository;

import io.pudge.auresto.domain.AuRestoPhoto;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AuRestoPhoto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuRestoPhotoRepository extends JpaRepository<AuRestoPhoto, Long> {

}
