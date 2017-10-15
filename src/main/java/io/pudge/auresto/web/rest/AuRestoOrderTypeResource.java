package io.pudge.auresto.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.pudge.auresto.domain.AuRestoOrderType;

import io.pudge.auresto.repository.AuRestoOrderTypeRepository;
import io.pudge.auresto.repository.search.AuRestoOrderTypeSearchRepository;
import io.pudge.auresto.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing AuRestoOrderType.
 */
@RestController
@RequestMapping("/api")
public class AuRestoOrderTypeResource {

    private final Logger log = LoggerFactory.getLogger(AuRestoOrderTypeResource.class);

    private static final String ENTITY_NAME = "auRestoOrderType";

    private final AuRestoOrderTypeRepository auRestoOrderTypeRepository;

    private final AuRestoOrderTypeSearchRepository auRestoOrderTypeSearchRepository;

    public AuRestoOrderTypeResource(AuRestoOrderTypeRepository auRestoOrderTypeRepository, AuRestoOrderTypeSearchRepository auRestoOrderTypeSearchRepository) {
        this.auRestoOrderTypeRepository = auRestoOrderTypeRepository;
        this.auRestoOrderTypeSearchRepository = auRestoOrderTypeSearchRepository;
    }

    /**
     * POST  /au-resto-order-types : Create a new auRestoOrderType.
     *
     * @param auRestoOrderType the auRestoOrderType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new auRestoOrderType, or with status 400 (Bad Request) if the auRestoOrderType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/au-resto-order-types")
    @Timed
    public ResponseEntity<AuRestoOrderType> createAuRestoOrderType(@RequestBody AuRestoOrderType auRestoOrderType) throws URISyntaxException {
        log.debug("REST request to save AuRestoOrderType : {}", auRestoOrderType);
        if (auRestoOrderType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new auRestoOrderType cannot already have an ID")).body(null);
        }
        AuRestoOrderType result = auRestoOrderTypeRepository.save(auRestoOrderType);
        auRestoOrderTypeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/au-resto-order-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /au-resto-order-types : Updates an existing auRestoOrderType.
     *
     * @param auRestoOrderType the auRestoOrderType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated auRestoOrderType,
     * or with status 400 (Bad Request) if the auRestoOrderType is not valid,
     * or with status 500 (Internal Server Error) if the auRestoOrderType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/au-resto-order-types")
    @Timed
    public ResponseEntity<AuRestoOrderType> updateAuRestoOrderType(@RequestBody AuRestoOrderType auRestoOrderType) throws URISyntaxException {
        log.debug("REST request to update AuRestoOrderType : {}", auRestoOrderType);
        if (auRestoOrderType.getId() == null) {
            return createAuRestoOrderType(auRestoOrderType);
        }
        AuRestoOrderType result = auRestoOrderTypeRepository.save(auRestoOrderType);
        auRestoOrderTypeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, auRestoOrderType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /au-resto-order-types : get all the auRestoOrderTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of auRestoOrderTypes in body
     */
    @GetMapping("/au-resto-order-types")
    @Timed
    public List<AuRestoOrderType> getAllAuRestoOrderTypes() {
        log.debug("REST request to get all AuRestoOrderTypes");
        return auRestoOrderTypeRepository.findAll();
        }

    /**
     * GET  /au-resto-order-types/:id : get the "id" auRestoOrderType.
     *
     * @param id the id of the auRestoOrderType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auRestoOrderType, or with status 404 (Not Found)
     */
    @GetMapping("/au-resto-order-types/{id}")
    @Timed
    public ResponseEntity<AuRestoOrderType> getAuRestoOrderType(@PathVariable Long id) {
        log.debug("REST request to get AuRestoOrderType : {}", id);
        AuRestoOrderType auRestoOrderType = auRestoOrderTypeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(auRestoOrderType));
    }

    /**
     * DELETE  /au-resto-order-types/:id : delete the "id" auRestoOrderType.
     *
     * @param id the id of the auRestoOrderType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/au-resto-order-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuRestoOrderType(@PathVariable Long id) {
        log.debug("REST request to delete AuRestoOrderType : {}", id);
        auRestoOrderTypeRepository.delete(id);
        auRestoOrderTypeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/au-resto-order-types?query=:query : search for the auRestoOrderType corresponding
     * to the query.
     *
     * @param query the query of the auRestoOrderType search
     * @return the result of the search
     */
    @GetMapping("/_search/au-resto-order-types")
    @Timed
    public List<AuRestoOrderType> searchAuRestoOrderTypes(@RequestParam String query) {
        log.debug("REST request to search AuRestoOrderTypes for query {}", query);
        return StreamSupport
            .stream(auRestoOrderTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
