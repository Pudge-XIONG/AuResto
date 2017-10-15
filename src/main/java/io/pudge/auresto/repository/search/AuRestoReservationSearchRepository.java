package io.pudge.auresto.repository.search;

import io.pudge.auresto.domain.AuRestoReservation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AuRestoReservation entity.
 */
public interface AuRestoReservationSearchRepository extends ElasticsearchRepository<AuRestoReservation, Long> {
}
