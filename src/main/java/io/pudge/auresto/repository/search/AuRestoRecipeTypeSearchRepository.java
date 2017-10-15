package io.pudge.auresto.repository.search;

import io.pudge.auresto.domain.AuRestoRecipeType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AuRestoRecipeType entity.
 */
public interface AuRestoRecipeTypeSearchRepository extends ElasticsearchRepository<AuRestoRecipeType, Long> {
}
