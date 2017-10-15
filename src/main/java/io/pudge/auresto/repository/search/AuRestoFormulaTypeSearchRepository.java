package io.pudge.auresto.repository.search;

import io.pudge.auresto.domain.AuRestoFormulaType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AuRestoFormulaType entity.
 */
public interface AuRestoFormulaTypeSearchRepository extends ElasticsearchRepository<AuRestoFormulaType, Long> {
}
