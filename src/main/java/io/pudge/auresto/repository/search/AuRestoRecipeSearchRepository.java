package io.pudge.auresto.repository.search;

import io.pudge.auresto.domain.AuRestoRecipe;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AuRestoRecipe entity.
 */
public interface AuRestoRecipeSearchRepository extends ElasticsearchRepository<AuRestoRecipe, Long> {
}
