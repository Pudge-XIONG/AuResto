package io.pudge.auresto.repository.search;

import io.pudge.auresto.domain.AuRestoMenu;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AuRestoMenu entity.
 */
public interface AuRestoMenuSearchRepository extends ElasticsearchRepository<AuRestoMenu, Long> {
}
