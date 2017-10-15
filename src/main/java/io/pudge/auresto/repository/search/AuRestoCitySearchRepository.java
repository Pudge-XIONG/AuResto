package io.pudge.auresto.repository.search;

import io.pudge.auresto.domain.AuRestoCity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AuRestoCity entity.
 */
public interface AuRestoCitySearchRepository extends ElasticsearchRepository<AuRestoCity, Long> {
}
