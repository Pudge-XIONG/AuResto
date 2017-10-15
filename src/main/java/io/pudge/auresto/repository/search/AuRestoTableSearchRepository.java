package io.pudge.auresto.repository.search;

import io.pudge.auresto.domain.AuRestoTable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AuRestoTable entity.
 */
public interface AuRestoTableSearchRepository extends ElasticsearchRepository<AuRestoTable, Long> {
}
