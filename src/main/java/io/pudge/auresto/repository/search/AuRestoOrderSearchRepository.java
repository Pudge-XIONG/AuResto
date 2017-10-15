package io.pudge.auresto.repository.search;

import io.pudge.auresto.domain.AuRestoOrder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AuRestoOrder entity.
 */
public interface AuRestoOrderSearchRepository extends ElasticsearchRepository<AuRestoOrder, Long> {
}
