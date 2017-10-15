package io.pudge.auresto.repository.search;

import io.pudge.auresto.domain.AuRestoPhoto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AuRestoPhoto entity.
 */
public interface AuRestoPhotoSearchRepository extends ElasticsearchRepository<AuRestoPhoto, Long> {
}
