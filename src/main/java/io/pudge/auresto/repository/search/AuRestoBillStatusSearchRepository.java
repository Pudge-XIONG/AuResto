package io.pudge.auresto.repository.search;

import io.pudge.auresto.domain.AuRestoBillStatus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AuRestoBillStatus entity.
 */
public interface AuRestoBillStatusSearchRepository extends ElasticsearchRepository<AuRestoBillStatus, Long> {
}
