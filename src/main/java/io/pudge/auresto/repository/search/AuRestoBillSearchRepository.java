package io.pudge.auresto.repository.search;

import io.pudge.auresto.domain.AuRestoBill;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AuRestoBill entity.
 */
public interface AuRestoBillSearchRepository extends ElasticsearchRepository<AuRestoBill, Long> {
}
