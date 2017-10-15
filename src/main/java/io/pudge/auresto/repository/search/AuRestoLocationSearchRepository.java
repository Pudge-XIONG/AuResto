package io.pudge.auresto.repository.search;

import io.pudge.auresto.domain.AuRestoLocation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AuRestoLocation entity.
 */
public interface AuRestoLocationSearchRepository extends ElasticsearchRepository<AuRestoLocation, Long> {
}
