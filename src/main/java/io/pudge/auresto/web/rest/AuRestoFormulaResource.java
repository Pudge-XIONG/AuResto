package io.pudge.auresto.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.pudge.auresto.domain.AuRestoFormula;

import io.pudge.auresto.repository.AuRestoFormulaRepository;
import io.pudge.auresto.repository.search.AuRestoFormulaSearchRepository;
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
 * REST controller for managing AuRestoFormula.
 */
@RestController
@RequestMapping("/api")
public class AuRestoFormulaResource {

    private final Logger log = LoggerFactory.getLogger(AuRestoFormulaResource.class);

    private static final String ENTITY_NAME = "auRestoFormula";

    private final AuRestoFormulaRepository auRestoFormulaRepository;

    private final AuRestoFormulaSearchRepository auRestoFormulaSearchRepository;

    public AuRestoFormulaResource(AuRestoFormulaRepository auRestoFormulaRepository, AuRestoFormulaSearchRepository auRestoFormulaSearchRepository) {
        this.auRestoFormulaRepository = auRestoFormulaRepository;
        this.auRestoFormulaSearchRepository = auRestoFormulaSearchRepository;
    }

    /**
     * POST  /au-resto-formulas : Create a new auRestoFormula.
     *
     * @param auRestoFormula the auRestoFormula to create
     * @return the ResponseEntity with status 201 (Created) and with body the new auRestoFormula, or with status 400 (Bad Request) if the auRestoFormula has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/au-resto-formulas")
    @Timed
    public ResponseEntity<AuRestoFormula> createAuRestoFormula(@RequestBody AuRestoFormula auRestoFormula) throws URISyntaxException {
        log.debug("REST request to save AuRestoFormula : {}", auRestoFormula);
        if (auRestoFormula.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new auRestoFormula cannot already have an ID")).body(null);
        }
        AuRestoFormula result = auRestoFormulaRepository.save(auRestoFormula);
        auRestoFormulaSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/au-resto-formulas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /au-resto-formulas : Updates an existing auRestoFormula.
     *
     * @param auRestoFormula the auRestoFormula to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated auRestoFormula,
     * or with status 400 (Bad Request) if the auRestoFormula is not valid,
     * or with status 500 (Internal Server Error) if the auRestoFormula couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/au-resto-formulas")
    @Timed
    public ResponseEntity<AuRestoFormula> updateAuRestoFormula(@RequestBody AuRestoFormula auRestoFormula) throws URISyntaxException {
        log.debug("REST request to update AuRestoFormula : {}", auRestoFormula);
        if (auRestoFormula.getId() == null) {
            return createAuRestoFormula(auRestoFormula);
        }
        AuRestoFormula result = auRestoFormulaRepository.save(auRestoFormula);
        auRestoFormulaSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, auRestoFormula.getId().toString()))
            .body(result);
    }

    /**
     * GET  /au-resto-formulas : get all the auRestoFormulas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of auRestoFormulas in body
     */
    @GetMapping("/au-resto-formulas")
    @Timed
    public List<AuRestoFormula> getAllAuRestoFormulas() {
        log.debug("REST request to get all AuRestoFormulas");
        return auRestoFormulaRepository.findAll();
        }

    /**
     * GET  /au-resto-formulas/:id : get the "id" auRestoFormula.
     *
     * @param id the id of the auRestoFormula to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auRestoFormula, or with status 404 (Not Found)
     */
    @GetMapping("/au-resto-formulas/{id}")
    @Timed
    public ResponseEntity<AuRestoFormula> getAuRestoFormula(@PathVariable Long id) {
        log.debug("REST request to get AuRestoFormula : {}", id);
        AuRestoFormula auRestoFormula = auRestoFormulaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(auRestoFormula));
    }

    /**
     * DELETE  /au-resto-formulas/:id : delete the "id" auRestoFormula.
     *
     * @param id the id of the auRestoFormula to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/au-resto-formulas/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuRestoFormula(@PathVariable Long id) {
        log.debug("REST request to delete AuRestoFormula : {}", id);
        auRestoFormulaRepository.delete(id);
        auRestoFormulaSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/au-resto-formulas?query=:query : search for the auRestoFormula corresponding
     * to the query.
     *
     * @param query the query of the auRestoFormula search
     * @return the result of the search
     */
    @GetMapping("/_search/au-resto-formulas")
    @Timed
    public List<AuRestoFormula> searchAuRestoFormulas(@RequestParam String query) {
        log.debug("REST request to search AuRestoFormulas for query {}", query);
        return StreamSupport
            .stream(auRestoFormulaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
