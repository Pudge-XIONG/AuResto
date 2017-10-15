package io.pudge.auresto.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.pudge.auresto.domain.AuRestoRecipeType;

import io.pudge.auresto.repository.AuRestoRecipeTypeRepository;
import io.pudge.auresto.repository.search.AuRestoRecipeTypeSearchRepository;
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
 * REST controller for managing AuRestoRecipeType.
 */
@RestController
@RequestMapping("/api")
public class AuRestoRecipeTypeResource {

    private final Logger log = LoggerFactory.getLogger(AuRestoRecipeTypeResource.class);

    private static final String ENTITY_NAME = "auRestoRecipeType";

    private final AuRestoRecipeTypeRepository auRestoRecipeTypeRepository;

    private final AuRestoRecipeTypeSearchRepository auRestoRecipeTypeSearchRepository;

    public AuRestoRecipeTypeResource(AuRestoRecipeTypeRepository auRestoRecipeTypeRepository, AuRestoRecipeTypeSearchRepository auRestoRecipeTypeSearchRepository) {
        this.auRestoRecipeTypeRepository = auRestoRecipeTypeRepository;
        this.auRestoRecipeTypeSearchRepository = auRestoRecipeTypeSearchRepository;
    }

    /**
     * POST  /au-resto-recipe-types : Create a new auRestoRecipeType.
     *
     * @param auRestoRecipeType the auRestoRecipeType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new auRestoRecipeType, or with status 400 (Bad Request) if the auRestoRecipeType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/au-resto-recipe-types")
    @Timed
    public ResponseEntity<AuRestoRecipeType> createAuRestoRecipeType(@RequestBody AuRestoRecipeType auRestoRecipeType) throws URISyntaxException {
        log.debug("REST request to save AuRestoRecipeType : {}", auRestoRecipeType);
        if (auRestoRecipeType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new auRestoRecipeType cannot already have an ID")).body(null);
        }
        AuRestoRecipeType result = auRestoRecipeTypeRepository.save(auRestoRecipeType);
        auRestoRecipeTypeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/au-resto-recipe-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /au-resto-recipe-types : Updates an existing auRestoRecipeType.
     *
     * @param auRestoRecipeType the auRestoRecipeType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated auRestoRecipeType,
     * or with status 400 (Bad Request) if the auRestoRecipeType is not valid,
     * or with status 500 (Internal Server Error) if the auRestoRecipeType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/au-resto-recipe-types")
    @Timed
    public ResponseEntity<AuRestoRecipeType> updateAuRestoRecipeType(@RequestBody AuRestoRecipeType auRestoRecipeType) throws URISyntaxException {
        log.debug("REST request to update AuRestoRecipeType : {}", auRestoRecipeType);
        if (auRestoRecipeType.getId() == null) {
            return createAuRestoRecipeType(auRestoRecipeType);
        }
        AuRestoRecipeType result = auRestoRecipeTypeRepository.save(auRestoRecipeType);
        auRestoRecipeTypeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, auRestoRecipeType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /au-resto-recipe-types : get all the auRestoRecipeTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of auRestoRecipeTypes in body
     */
    @GetMapping("/au-resto-recipe-types")
    @Timed
    public List<AuRestoRecipeType> getAllAuRestoRecipeTypes() {
        log.debug("REST request to get all AuRestoRecipeTypes");
        return auRestoRecipeTypeRepository.findAll();
        }

    /**
     * GET  /au-resto-recipe-types/:id : get the "id" auRestoRecipeType.
     *
     * @param id the id of the auRestoRecipeType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auRestoRecipeType, or with status 404 (Not Found)
     */
    @GetMapping("/au-resto-recipe-types/{id}")
    @Timed
    public ResponseEntity<AuRestoRecipeType> getAuRestoRecipeType(@PathVariable Long id) {
        log.debug("REST request to get AuRestoRecipeType : {}", id);
        AuRestoRecipeType auRestoRecipeType = auRestoRecipeTypeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(auRestoRecipeType));
    }

    /**
     * DELETE  /au-resto-recipe-types/:id : delete the "id" auRestoRecipeType.
     *
     * @param id the id of the auRestoRecipeType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/au-resto-recipe-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuRestoRecipeType(@PathVariable Long id) {
        log.debug("REST request to delete AuRestoRecipeType : {}", id);
        auRestoRecipeTypeRepository.delete(id);
        auRestoRecipeTypeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/au-resto-recipe-types?query=:query : search for the auRestoRecipeType corresponding
     * to the query.
     *
     * @param query the query of the auRestoRecipeType search
     * @return the result of the search
     */
    @GetMapping("/_search/au-resto-recipe-types")
    @Timed
    public List<AuRestoRecipeType> searchAuRestoRecipeTypes(@RequestParam String query) {
        log.debug("REST request to search AuRestoRecipeTypes for query {}", query);
        return StreamSupport
            .stream(auRestoRecipeTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
