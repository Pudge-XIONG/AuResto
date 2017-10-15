package io.pudge.auresto.repository.search;

import io.pudge.auresto.domain.AuRestoOrderStatus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AuRestoOrderStatus entity.
 */
public interface AuRestoOrderStatusSearchRepository extends ElasticsearchRepository<AuRestoOrderStatus, Long> {
}
