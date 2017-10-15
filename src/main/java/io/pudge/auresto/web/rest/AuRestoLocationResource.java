package io.pudge.auresto.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.pudge.auresto.domain.AuRestoLocation;

import io.pudge.auresto.repository.AuRestoLocationRepository;
import io.pudge.auresto.repository.search.AuRestoLocationSearchRepository;
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
 * REST controller for managing AuRestoLocation.
 */
@RestController
@RequestMapping("/api")
public class AuRestoLocationResource {

    private final Logger log = LoggerFactory.getLogger(AuRestoLocationResource.class);

    private static final String ENTITY_NAME = "auRestoLocation";

    private final AuRestoLocationRepository auRestoLocationRepository;

    private final AuRestoLocationSearchRepository auRestoLocationSearchRepository;

    public AuRestoLocationResource(AuRestoLocationRepository auRestoLocationRepository, AuRestoLocationSearchRepository auRestoLocationSearchRepository) {
        this.auRestoLocationRepository = auRestoLocationRepository;
        this.auRestoLocationSearchRepository = auRestoLocationSearchRepository;
    }

    /**
     * POST  /au-resto-locations : Create a new auRestoLocation.
     *
     * @param auRestoLocation the auRestoLocation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new auRestoLocation, or with status 400 (Bad Request) if the auRestoLocation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/au-resto-locations")
    @Timed
    public ResponseEntity<AuRestoLocation> createAuRestoLocation(@RequestBody AuRestoLocation auRestoLocation) throws URISyntaxException {
        log.debug("REST request to save AuRestoLocation : {}", auRestoLocation);
        if (auRestoLocation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new auRestoLocation cannot already have an ID")).body(null);
        }
        AuRestoLocation result = auRestoLocationRepository.save(auRestoLocation);
        auRestoLocationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/au-resto-locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /au-resto-locations : Updates an existing auRestoLocation.
     *
     * @param auRestoLocation the auRestoLocation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated auRestoLocation,
     * or with status 400 (Bad Request) if the auRestoLocation is not valid,
     * or with status 500 (Internal Server Error) if the auRestoLocation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/au-resto-locations")
    @Timed
    public ResponseEntity<AuRestoLocation> updateAuRestoLocation(@RequestBody AuRestoLocation auRestoLocation) throws URISyntaxException {
        log.debug("REST request to update AuRestoLocation : {}", auRestoLocation);
        if (auRestoLocation.getId() == null) {
            return createAuRestoLocation(auRestoLocation);
        }
        AuRestoLocation result = auRestoLocationRepository.save(auRestoLocation);
        auRestoLocationSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, auRestoLocation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /au-resto-locations : get all the auRestoLocations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of auRestoLocations in body
     */
    @GetMapping("/au-resto-locations")
    @Timed
    public List<AuRestoLocation> getAllAuRestoLocations() {
        log.debug("REST request to get all AuRestoLocations");
        return auRestoLocationRepository.findAll();
        }

    /**
     * GET  /au-resto-locations/:id : get the "id" auRestoLocation.
     *
     * @param id the id of the auRestoLocation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auRestoLocation, or with status 404 (Not Found)
     */
    @GetMapping("/au-resto-locations/{id}")
    @Timed
    public ResponseEntity<AuRestoLocation> getAuRestoLocation(@PathVariable Long id) {
        log.debug("REST request to get AuRestoLocation : {}", id);
        AuRestoLocation auRestoLocation = auRestoLocationRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(auRestoLocation));
    }

    /**
     * DELETE  /au-resto-locations/:id : delete the "id" auRestoLocation.
     *
     * @param id the id of the auRestoLocation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/au-resto-locations/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuRestoLocation(@PathVariable Long id) {
        log.debug("REST request to delete AuRestoLocation : {}", id);
        auRestoLocationRepository.delete(id);
        auRestoLocationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/au-resto-locations?query=:query : search for the auRestoLocation corresponding
     * to the query.
     *
     * @param query the query of the auRestoLocation search
     * @return the result of the search
     */
    @GetMapping("/_search/au-resto-locations")
    @Timed
    public List<AuRestoLocation> searchAuRestoLocations(@RequestParam String query) {
        log.debug("REST request to search AuRestoLocations for query {}", query);
        return StreamSupport
            .stream(auRestoLocationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
