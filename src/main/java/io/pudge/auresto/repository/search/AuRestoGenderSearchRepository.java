package io.pudge.auresto.repository.search;

import io.pudge.auresto.domain.AuRestoGender;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AuRestoGender entity.
 */
public interface AuRestoGenderSearchRepository extends ElasticsearchRepository<AuRestoGender, Long> {
}
