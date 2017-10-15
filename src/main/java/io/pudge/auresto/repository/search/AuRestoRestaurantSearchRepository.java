package io.pudge.auresto.repository.search;

import io.pudge.auresto.domain.AuRestoRestaurant;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AuRestoRestaurant entity.
 */
public interface AuRestoRestaurantSearchRepository extends ElasticsearchRepository<AuRestoRestaurant, Long> {
}
