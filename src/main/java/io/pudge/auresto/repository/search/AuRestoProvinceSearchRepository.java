package io.pudge.auresto.repository.search;

import io.pudge.auresto.domain.AuRestoProvince;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AuRestoProvince entity.
 */
public interface AuRestoProvinceSearchRepository extends ElasticsearchRepository<AuRestoProvince, Long> {
}
