package io.pudge.auresto.repository.search;

import io.pudge.auresto.domain.AuRestoCountry;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AuRestoCountry entity.
 */
public interface AuRestoCountrySearchRepository extends ElasticsearchRepository<AuRestoCountry, Long> {
}
