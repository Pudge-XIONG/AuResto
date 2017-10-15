package io.pudge.auresto.repository.search;

import io.pudge.auresto.domain.AuRestoProfile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AuRestoProfile entity.
 */
public interface AuRestoProfileSearchRepository extends ElasticsearchRepository<AuRestoProfile, Long> {
}
