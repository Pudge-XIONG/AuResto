package io.pudge.auresto.repository.search;

import io.pudge.auresto.domain.AuRestoUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AuRestoUser entity.
 */
public interface AuRestoUserSearchRepository extends ElasticsearchRepository<AuRestoUser, Long> {
}
