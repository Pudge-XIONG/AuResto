package io.pudge.auresto.repository.search;

import io.pudge.auresto.domain.AuRestoRestaurantType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AuRestoRestaurantType entity.
 */
public interface AuRestoRestaurantTypeSearchRepository extends ElasticsearchRepository<AuRestoRestaurantType, Long> {
}
