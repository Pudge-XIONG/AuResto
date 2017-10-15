package io.pudge.auresto.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.pudge.auresto.domain.AuRestoFormulaType;

import io.pudge.auresto.repository.AuRestoFormulaTypeRepository;
import io.pudge.auresto.repository.search.AuRestoFormulaTypeSearchRepository;
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
 * REST controller for managing AuRestoFormulaType.
 */
@RestController
@RequestMapping("/api")
public class AuRestoFormulaTypeResource {

    private final Logger log = LoggerFactory.getLogger(AuRestoFormulaTypeResource.class);

    private static final String ENTITY_NAME = "auRestoFormulaType";

    private final AuRestoFormulaTypeRepository auRestoFormulaTypeRepository;

    private final AuRestoFormulaTypeSearchRepository auRestoFormulaTypeSearchRepository;

    public AuRestoFormulaTypeResource(AuRestoFormulaTypeRepository auRestoFormulaTypeRepository, AuRestoFormulaTypeSearchRepository auRestoFormulaTypeSearchRepository) {
        this.auRestoFormulaTypeRepository = auRestoFormulaTypeRepository;
        this.auRestoFormulaTypeSearchRepository = auRestoFormulaTypeSearchRepository;
    }

    /**
     * POST  /au-resto-formula-types : Create a new auRestoFormulaType.
     *
     * @param auRestoFormulaType the auRestoFormulaType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new auRestoFormulaType, or with status 400 (Bad Request) if the auRestoFormulaType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/au-resto-formula-types")
    @Timed
    public ResponseEntity<AuRestoFormulaType> createAuRestoFormulaType(@RequestBody AuRestoFormulaType auRestoFormulaType) throws URISyntaxException {
        log.debug("REST request to save AuRestoFormulaType : {}", auRestoFormulaType);
        if (auRestoFormulaType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new auRestoFormulaType cannot already have an ID")).body(null);
        }
        AuRestoFormulaType result = auRestoFormulaTypeRepository.save(auRestoFormulaType);
        auRestoFormulaTypeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/au-resto-formula-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /au-resto-formula-types : Updates an existing auRestoFormulaType.
     *
     * @param auRestoFormulaType the auRestoFormulaType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated auRestoFormulaType,
     * or with status 400 (Bad Request) if the auRestoFormulaType is not valid,
     * or with status 500 (Internal Server Error) if the auRestoFormulaType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/au-resto-formula-types")
    @Timed
    public ResponseEntity<AuRestoFormulaType> updateAuRestoFormulaType(@RequestBody AuRestoFormulaType auRestoFormulaType) throws URISyntaxException {
        log.debug("REST request to update AuRestoFormulaType : {}", auRestoFormulaType);
        if (auRestoFormulaType.getId() == null) {
            return createAuRestoFormulaType(auRestoFormulaType);
        }
        AuRestoFormulaType result = auRestoFormulaTypeRepository.save(auRestoFormulaType);
        auRestoFormulaTypeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, auRestoFormulaType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /au-resto-formula-types : get all the auRestoFormulaTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of auRestoFormulaTypes in body
     */
    @GetMapping("/au-resto-formula-types")
    @Timed
    public List<AuRestoFormulaType> getAllAuRestoFormulaTypes() {
        log.debug("REST request to get all AuRestoFormulaTypes");
        return auRestoFormulaTypeRepository.findAll();
        }

    /**
     * GET  /au-resto-formula-types/:id : get the "id" auRestoFormulaType.
     *
     * @param id the id of the auRestoFormulaType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auRestoFormulaType, or with status 404 (Not Found)
     */
    @GetMapping("/au-resto-formula-types/{id}")
    @Timed
    public ResponseEntity<AuRestoFormulaType> getAuRestoFormulaType(@PathVariable Long id) {
        log.debug("REST request to get AuRestoFormulaType : {}", id);
        AuRestoFormulaType auRestoFormulaType = auRestoFormulaTypeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(auRestoFormulaType));
    }

    /**
     * DELETE  /au-resto-formula-types/:id : delete the "id" auRestoFormulaType.
     *
     * @param id the id of the auRestoFormulaType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/au-resto-formula-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuRestoFormulaType(@PathVariable Long id) {
        log.debug("REST request to delete AuRestoFormulaType : {}", id);
        auRestoFormulaTypeRepository.delete(id);
        auRestoFormulaTypeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/au-resto-formula-types?query=:query : search for the auRestoFormulaType corresponding
     * to the query.
     *
     * @param query the query of the auRestoFormulaType search
     * @return the result of the search
     */
    @GetMapping("/_search/au-resto-formula-types")
    @Timed
    public List<AuRestoFormulaType> searchAuRestoFormulaTypes(@RequestParam String query) {
        log.debug("REST request to search AuRestoFormulaTypes for query {}", query);
        return StreamSupport
            .stream(auRestoFormulaTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
