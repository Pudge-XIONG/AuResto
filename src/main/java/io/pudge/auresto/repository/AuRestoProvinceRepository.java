package io.pudge.auresto.repository;

import io.pudge.auresto.domain.AuRestoProvince;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AuRestoProvince entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuRestoProvinceRepository extends JpaRepository<AuRestoProvince, Long> {

}
