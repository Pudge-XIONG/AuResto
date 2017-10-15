package io.pudge.auresto.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.pudge.auresto.domain.AuRestoCity;

import io.pudge.auresto.repository.AuRestoCityRepository;
import io.pudge.auresto.repository.search.AuRestoCitySearchRepository;
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
 * REST controller for managing AuRestoCity.
 */
@RestController
@RequestMapping("/api")
public class AuRestoCityResource {

    private final Logger log = LoggerFactory.getLogger(AuRestoCityResource.class);

    private static final String ENTITY_NAME = "auRestoCity";

    private final AuRestoCityRepository auRestoCityRepository;

    private final AuRestoCitySearchRepository auRestoCitySearchRepository;

    public AuRestoCityResource(AuRestoCityRepository auRestoCityRepository, AuRestoCitySearchRepository auRestoCitySearchRepository) {
        this.auRestoCityRepository = auRestoCityRepository;
        this.auRestoCitySearchRepository = auRestoCitySearchRepository;
    }

    /**
     * POST  /au-resto-cities : Create a new auRestoCity.
     *
     * @param auRestoCity the auRestoCity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new auRestoCity, or with status 400 (Bad Request) if the auRestoCity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/au-resto-cities")
    @Timed
    public ResponseEntity<AuRestoCity> createAuRestoCity(@RequestBody AuRestoCity auRestoCity) throws URISyntaxException {
        log.debug("REST request to save AuRestoCity : {}", auRestoCity);
        if (auRestoCity.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new auRestoCity cannot already have an ID")).body(null);
        }
        AuRestoCity result = auRestoCityRepository.save(auRestoCity);
        auRestoCitySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/au-resto-cities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /au-resto-cities : Updates an existing auRestoCity.
     *
     * @param auRestoCity the auRestoCity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated auRestoCity,
     * or with status 400 (Bad Request) if the auRestoCity is not valid,
     * or with status 500 (Internal Server Error) if the auRestoCity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/au-resto-cities")
    @Timed
    public ResponseEntity<AuRestoCity> updateAuRestoCity(@RequestBody AuRestoCity auRestoCity) throws URISyntaxException {
        log.debug("REST request to update AuRestoCity : {}", auRestoCity);
        if (auRestoCity.getId() == null) {
            return createAuRestoCity(auRestoCity);
        }
        AuRestoCity result = auRestoCityRepository.save(auRestoCity);
        auRestoCitySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, auRestoCity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /au-resto-cities : get all the auRestoCities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of auRestoCities in body
     */
    @GetMapping("/au-resto-cities")
    @Timed
    public List<AuRestoCity> getAllAuRestoCities() {
        log.debug("REST request to get all AuRestoCities");
        return auRestoCityRepository.findAll();
        }

    /**
     * GET  /au-resto-cities/:id : get the "id" auRestoCity.
     *
     * @param id the id of the auRestoCity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auRestoCity, or with status 404 (Not Found)
     */
    @GetMapping("/au-resto-cities/{id}")
    @Timed
    public ResponseEntity<AuRestoCity> getAuRestoCity(@PathVariable Long id) {
        log.debug("REST request to get AuRestoCity : {}", id);
        AuRestoCity auRestoCity = auRestoCityRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(auRestoCity));
    }

    /**
     * DELETE  /au-resto-cities/:id : delete the "id" auRestoCity.
     *
     * @param id the id of the auRestoCity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/au-resto-cities/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuRestoCity(@PathVariable Long id) {
        log.debug("REST request to delete AuRestoCity : {}", id);
        auRestoCityRepository.delete(id);
        auRestoCitySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/au-resto-cities?query=:query : search for the auRestoCity corresponding
     * to the query.
     *
     * @param query the query of the auRestoCity search
     * @return the result of the search
     */
    @GetMapping("/_search/au-resto-cities")
    @Timed
    public List<AuRestoCity> searchAuRestoCities(@RequestParam String query) {
        log.debug("REST request to search AuRestoCities for query {}", query);
        return StreamSupport
            .stream(auRestoCitySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
