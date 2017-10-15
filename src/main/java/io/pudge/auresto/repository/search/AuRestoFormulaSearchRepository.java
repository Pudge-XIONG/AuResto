package io.pudge.auresto.repository.search;

import io.pudge.auresto.domain.AuRestoFormula;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AuRestoFormula entity.
 */
public interface AuRestoFormulaSearchRepository extends ElasticsearchRepository<AuRestoFormula, Long> {
}
