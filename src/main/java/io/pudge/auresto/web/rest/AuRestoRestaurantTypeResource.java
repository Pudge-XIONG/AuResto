package io.pudge.auresto.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.pudge.auresto.domain.AuRestoRestaurantType;

import io.pudge.auresto.repository.AuRestoRestaurantTypeRepository;
import io.pudge.auresto.repository.search.AuRestoRestaurantTypeSearchRepository;
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
 * REST controller for managing AuRestoRestaurantType.
 */
@RestController
@RequestMapping("/api")
public class AuRestoRestaurantTypeResource {

    private final Logger log = LoggerFactory.getLogger(AuRestoRestaurantTypeResource.class);

    private static final String ENTITY_NAME = "auRestoRestaurantType";

    private final AuRestoRestaurantTypeRepository auRestoRestaurantTypeRepository;

    private final AuRestoRestaurantTypeSearchRepository auRestoRestaurantTypeSearchRepository;

    public AuRestoRestaurantTypeResource(AuRestoRestaurantTypeRepository auRestoRestaurantTypeRepository, AuRestoRestaurantTypeSearchRepository auRestoRestaurantTypeSearchRepository) {
        this.auRestoRestaurantTypeRepository = auRestoRestaurantTypeRepository;
        this.auRestoRestaurantTypeSearchRepository = auRestoRestaurantTypeSearchRepository;
    }

    /**
     * POST  /au-resto-restaurant-types : Create a new auRestoRestaurantType.
     *
     * @param auRestoRestaurantType the auRestoRestaurantType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new auRestoRestaurantType, or with status 400 (Bad Request) if the auRestoRestaurantType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/au-resto-restaurant-types")
    @Timed
    public ResponseEntity<AuRestoRestaurantType> createAuRestoRestaurantType(@RequestBody AuRestoRestaurantType auRestoRestaurantType) throws URISyntaxException {
        log.debug("REST request to save AuRestoRestaurantType : {}", auRestoRestaurantType);
        if (auRestoRestaurantType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new auRestoRestaurantType cannot already have an ID")).body(null);
        }
        AuRestoRestaurantType result = auRestoRestaurantTypeRepository.save(auRestoRestaurantType);
        auRestoRestaurantTypeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/au-resto-restaurant-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /au-resto-restaurant-types : Updates an existing auRestoRestaurantType.
     *
     * @param auRestoRestaurantType the auRestoRestaurantType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated auRestoRestaurantType,
     * or with status 400 (Bad Request) if the auRestoRestaurantType is not valid,
     * or with status 500 (Internal Server Error) if the auRestoRestaurantType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/au-resto-restaurant-types")
    @Timed
    public ResponseEntity<AuRestoRestaurantType> updateAuRestoRestaurantType(@RequestBody AuRestoRestaurantType auRestoRestaurantType) throws URISyntaxException {
        log.debug("REST request to update AuRestoRestaurantType : {}", auRestoRestaurantType);
        if (auRestoRestaurantType.getId() == null) {
            return createAuRestoRestaurantType(auRestoRestaurantType);
        }
        AuRestoRestaurantType result = auRestoRestaurantTypeRepository.save(auRestoRestaurantType);
        auRestoRestaurantTypeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, auRestoRestaurantType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /au-resto-restaurant-types : get all the auRestoRestaurantTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of auRestoRestaurantTypes in body
     */
    @GetMapping("/au-resto-restaurant-types")
    @Timed
    public List<AuRestoRestaurantType> getAllAuRestoRestaurantTypes() {
        log.debug("REST request to get all AuRestoRestaurantTypes");
        return auRestoRestaurantTypeRepository.findAll();
        }

    /**
     * GET  /au-resto-restaurant-types/:id : get the "id" auRestoRestaurantType.
     *
     * @param id the id of the auRestoRestaurantType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auRestoRestaurantType, or with status 404 (Not Found)
     */
    @GetMapping("/au-resto-restaurant-types/{id}")
    @Timed
    public ResponseEntity<AuRestoRestaurantType> getAuRestoRestaurantType(@PathVariable Long id) {
        log.debug("REST request to get AuRestoRestaurantType : {}", id);
        AuRestoRestaurantType auRestoRestaurantType = auRestoRestaurantTypeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(auRestoRestaurantType));
    }

    /**
     * DELETE  /au-resto-restaurant-types/:id : delete the "id" auRestoRestaurantType.
     *
     * @param id the id of the auRestoRestaurantType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/au-resto-restaurant-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuRestoRestaurantType(@PathVariable Long id) {
        log.debug("REST request to delete AuRestoRestaurantType : {}", id);
        auRestoRestaurantTypeRepository.delete(id);
        auRestoRestaurantTypeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/au-resto-restaurant-types?query=:query : search for the auRestoRestaurantType corresponding
     * to the query.
     *
     * @param query the query of the auRestoRestaurantType search
     * @return the result of the search
     */
    @GetMapping("/_search/au-resto-restaurant-types")
    @Timed
    public List<AuRestoRestaurantType> searchAuRestoRestaurantTypes(@RequestParam String query) {
        log.debug("REST request to search AuRestoRestaurantTypes for query {}", query);
        return StreamSupport
            .stream(auRestoRestaurantTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
