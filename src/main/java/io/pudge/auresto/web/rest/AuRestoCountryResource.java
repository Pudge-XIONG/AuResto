package io.pudge.auresto.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.pudge.auresto.domain.AuRestoCountry;

import io.pudge.auresto.repository.AuRestoCountryRepository;
import io.pudge.auresto.repository.search.AuRestoCountrySearchRepository;
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
 * REST controller for managing AuRestoCountry.
 */
@RestController
@RequestMapping("/api")
public class AuRestoCountryResource {

    private final Logger log = LoggerFactory.getLogger(AuRestoCountryResource.class);

    private static final String ENTITY_NAME = "auRestoCountry";

    private final AuRestoCountryRepository auRestoCountryRepository;

    private final AuRestoCountrySearchRepository auRestoCountrySearchRepository;

    public AuRestoCountryResource(AuRestoCountryRepository auRestoCountryRepository, AuRestoCountrySearchRepository auRestoCountrySearchRepository) {
        this.auRestoCountryRepository = auRestoCountryRepository;
        this.auRestoCountrySearchRepository = auRestoCountrySearchRepository;
    }

    /**
     * POST  /au-resto-countries : Create a new auRestoCountry.
     *
     * @param auRestoCountry the auRestoCountry to create
     * @return the ResponseEntity with status 201 (Created) and with body the new auRestoCountry, or with status 400 (Bad Request) if the auRestoCountry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/au-resto-countries")
    @Timed
    public ResponseEntity<AuRestoCountry> createAuRestoCountry(@RequestBody AuRestoCountry auRestoCountry) throws URISyntaxException {
        log.debug("REST request to save AuRestoCountry : {}", auRestoCountry);
        if (auRestoCountry.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new auRestoCountry cannot already have an ID")).body(null);
        }
        AuRestoCountry result = auRestoCountryRepository.save(auRestoCountry);
        auRestoCountrySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/au-resto-countries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /au-resto-countries : Updates an existing auRestoCountry.
     *
     * @param auRestoCountry the auRestoCountry to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated auRestoCountry,
     * or with status 400 (Bad Request) if the auRestoCountry is not valid,
     * or with status 500 (Internal Server Error) if the auRestoCountry couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/au-resto-countries")
    @Timed
    public ResponseEntity<AuRestoCountry> updateAuRestoCountry(@RequestBody AuRestoCountry auRestoCountry) throws URISyntaxException {
        log.debug("REST request to update AuRestoCountry : {}", auRestoCountry);
        if (auRestoCountry.getId() == null) {
            return createAuRestoCountry(auRestoCountry);
        }
        AuRestoCountry result = auRestoCountryRepository.save(auRestoCountry);
        auRestoCountrySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, auRestoCountry.getId().toString()))
            .body(result);
    }

    /**
     * GET  /au-resto-countries : get all the auRestoCountries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of auRestoCountries in body
     */
    @GetMapping("/au-resto-countries")
    @Timed
    public List<AuRestoCountry> getAllAuRestoCountries() {
        log.debug("REST request to get all AuRestoCountries");
        return auRestoCountryRepository.findAll();
        }

    /**
     * GET  /au-resto-countries/:id : get the "id" auRestoCountry.
     *
     * @param id the id of the auRestoCountry to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auRestoCountry, or with status 404 (Not Found)
     */
    @GetMapping("/au-resto-countries/{id}")
    @Timed
    public ResponseEntity<AuRestoCountry> getAuRestoCountry(@PathVariable Long id) {
        log.debug("REST request to get AuRestoCountry : {}", id);
        AuRestoCountry auRestoCountry = auRestoCountryRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(auRestoCountry));
    }

    /**
     * DELETE  /au-resto-countries/:id : delete the "id" auRestoCountry.
     *
     * @param id the id of the auRestoCountry to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/au-resto-countries/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuRestoCountry(@PathVariable Long id) {
        log.debug("REST request to delete AuRestoCountry : {}", id);
        auRestoCountryRepository.delete(id);
        auRestoCountrySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/au-resto-countries?query=:query : search for the auRestoCountry corresponding
     * to the query.
     *
     * @param query the query of the auRestoCountry search
     * @return the result of the search
     */
    @GetMapping("/_search/au-resto-countries")
    @Timed
    public List<AuRestoCountry> searchAuRestoCountries(@RequestParam String query) {
        log.debug("REST request to search AuRestoCountries for query {}", query);
        return StreamSupport
            .stream(auRestoCountrySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
