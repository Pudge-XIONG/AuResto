package io.pudge.auresto.repository.search;

import io.pudge.auresto.domain.AuRestoOrderType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AuRestoOrderType entity.
 */
public interface AuRestoOrderTypeSearchRepository extends ElasticsearchRepository<AuRestoOrderType, Long> {
}
