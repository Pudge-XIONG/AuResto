package io.pudge.auresto.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.pudge.auresto.domain.AuRestoGender;

import io.pudge.auresto.repository.AuRestoGenderRepository;
import io.pudge.auresto.repository.search.AuRestoGenderSearchRepository;
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
 * REST controller for managing AuRestoGender.
 */
@RestController
@RequestMapping("/api")
public class AuRestoGenderResource {

    private final Logger log = LoggerFactory.getLogger(AuRestoGenderResource.class);

    private static final String ENTITY_NAME = "auRestoGender";

    private final AuRestoGenderRepository auRestoGenderRepository;

    private final AuRestoGenderSearchRepository auRestoGenderSearchRepository;

    public AuRestoGenderResource(AuRestoGenderRepository auRestoGenderRepository, AuRestoGenderSearchRepository auRestoGenderSearchRepository) {
        this.auRestoGenderRepository = auRestoGenderRepository;
        this.auRestoGenderSearchRepository = auRestoGenderSearchRepository;
    }

    /**
     * POST  /au-resto-genders : Create a new auRestoGender.
     *
     * @param auRestoGender the auRestoGender to create
     * @return the ResponseEntity with status 201 (Created) and with body the new auRestoGender, or with status 400 (Bad Request) if the auRestoGender has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/au-resto-genders")
    @Timed
    public ResponseEntity<AuRestoGender> createAuRestoGender(@RequestBody AuRestoGender auRestoGender) throws URISyntaxException {
        log.debug("REST request to save AuRestoGender : {}", auRestoGender);
        if (auRestoGender.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new auRestoGender cannot already have an ID")).body(null);
        }
        AuRestoGender result = auRestoGenderRepository.save(auRestoGender);
        auRestoGenderSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/au-resto-genders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /au-resto-genders : Updates an existing auRestoGender.
     *
     * @param auRestoGender the auRestoGender to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated auRestoGender,
     * or with status 400 (Bad Request) if the auRestoGender is not valid,
     * or with status 500 (Internal Server Error) if the auRestoGender couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/au-resto-genders")
    @Timed
    public ResponseEntity<AuRestoGender> updateAuRestoGender(@RequestBody AuRestoGender auRestoGender) throws URISyntaxException {
        log.debug("REST request to update AuRestoGender : {}", auRestoGender);
        if (auRestoGender.getId() == null) {
            return createAuRestoGender(auRestoGender);
        }
        AuRestoGender result = auRestoGenderRepository.save(auRestoGender);
        auRestoGenderSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, auRestoGender.getId().toString()))
            .body(result);
    }

    /**
     * GET  /au-resto-genders : get all the auRestoGenders.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of auRestoGenders in body
     */
    @GetMapping("/au-resto-genders")
    @Timed
    public List<AuRestoGender> getAllAuRestoGenders() {
        log.debug("REST request to get all AuRestoGenders");
        return auRestoGenderRepository.findAll();
        }

    /**
     * GET  /au-resto-genders/:id : get the "id" auRestoGender.
     *
     * @param id the id of the auRestoGender to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auRestoGender, or with status 404 (Not Found)
     */
    @GetMapping("/au-resto-genders/{id}")
    @Timed
    public ResponseEntity<AuRestoGender> getAuRestoGender(@PathVariable Long id) {
        log.debug("REST request to get AuRestoGender : {}", id);
        AuRestoGender auRestoGender = auRestoGenderRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(auRestoGender));
    }

    /**
     * DELETE  /au-resto-genders/:id : delete the "id" auRestoGender.
     *
     * @param id the id of the auRestoGender to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/au-resto-genders/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuRestoGender(@PathVariable Long id) {
        log.debug("REST request to delete AuRestoGender : {}", id);
        auRestoGenderRepository.delete(id);
        auRestoGenderSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/au-resto-genders?query=:query : search for the auRestoGender corresponding
     * to the query.
     *
     * @param query the query of the auRestoGender search
     * @return the result of the search
     */
    @GetMapping("/_search/au-resto-genders")
    @Timed
    public List<AuRestoGender> searchAuRestoGenders(@RequestParam String query) {
        log.debug("REST request to search AuRestoGenders for query {}", query);
        return StreamSupport
            .stream(auRestoGenderSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
