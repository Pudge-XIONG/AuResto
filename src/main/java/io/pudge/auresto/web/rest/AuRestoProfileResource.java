package io.pudge.auresto.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.pudge.auresto.domain.AuRestoProfile;

import io.pudge.auresto.repository.AuRestoProfileRepository;
import io.pudge.auresto.repository.search.AuRestoProfileSearchRepository;
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
 * REST controller for managing AuRestoProfile.
 */
@RestController
@RequestMapping("/api")
public class AuRestoProfileResource {

    private final Logger log = LoggerFactory.getLogger(AuRestoProfileResource.class);

    private static final String ENTITY_NAME = "auRestoProfile";

    private final AuRestoProfileRepository auRestoProfileRepository;

    private final AuRestoProfileSearchRepository auRestoProfileSearchRepository;

    public AuRestoProfileResource(AuRestoProfileRepository auRestoProfileRepository, AuRestoProfileSearchRepository auRestoProfileSearchRepository) {
        this.auRestoProfileRepository = auRestoProfileRepository;
        this.auRestoProfileSearchRepository = auRestoProfileSearchRepository;
    }

    /**
     * POST  /au-resto-profiles : Create a new auRestoProfile.
     *
     * @param auRestoProfile the auRestoProfile to create
     * @return the ResponseEntity with status 201 (Created) and with body the new auRestoProfile, or with status 400 (Bad Request) if the auRestoProfile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/au-resto-profiles")
    @Timed
    public ResponseEntity<AuRestoProfile> createAuRestoProfile(@RequestBody AuRestoProfile auRestoProfile) throws URISyntaxException {
        log.debug("REST request to save AuRestoProfile : {}", auRestoProfile);
        if (auRestoProfile.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new auRestoProfile cannot already have an ID")).body(null);
        }
        AuRestoProfile result = auRestoProfileRepository.save(auRestoProfile);
        auRestoProfileSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/au-resto-profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /au-resto-profiles : Updates an existing auRestoProfile.
     *
     * @param auRestoProfile the auRestoProfile to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated auRestoProfile,
     * or with status 400 (Bad Request) if the auRestoProfile is not valid,
     * or with status 500 (Internal Server Error) if the auRestoProfile couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/au-resto-profiles")
    @Timed
    public ResponseEntity<AuRestoProfile> updateAuRestoProfile(@RequestBody AuRestoProfile auRestoProfile) throws URISyntaxException {
        log.debug("REST request to update AuRestoProfile : {}", auRestoProfile);
        if (auRestoProfile.getId() == null) {
            return createAuRestoProfile(auRestoProfile);
        }
        AuRestoProfile result = auRestoProfileRepository.save(auRestoProfile);
        auRestoProfileSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, auRestoProfile.getId().toString()))
            .body(result);
    }

    /**
     * GET  /au-resto-profiles : get all the auRestoProfiles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of auRestoProfiles in body
     */
    @GetMapping("/au-resto-profiles")
    @Timed
    public List<AuRestoProfile> getAllAuRestoProfiles() {
        log.debug("REST request to get all AuRestoProfiles");
        return auRestoProfileRepository.findAll();
        }

    /**
     * GET  /au-resto-profiles/:id : get the "id" auRestoProfile.
     *
     * @param id the id of the auRestoProfile to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auRestoProfile, or with status 404 (Not Found)
     */
    @GetMapping("/au-resto-profiles/{id}")
    @Timed
    public ResponseEntity<AuRestoProfile> getAuRestoProfile(@PathVariable Long id) {
        log.debug("REST request to get AuRestoProfile : {}", id);
        AuRestoProfile auRestoProfile = auRestoProfileRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(auRestoProfile));
    }

    /**
     * DELETE  /au-resto-profiles/:id : delete the "id" auRestoProfile.
     *
     * @param id the id of the auRestoProfile to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/au-resto-profiles/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuRestoProfile(@PathVariable Long id) {
        log.debug("REST request to delete AuRestoProfile : {}", id);
        auRestoProfileRepository.delete(id);
        auRestoProfileSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/au-resto-profiles?query=:query : search for the auRestoProfile corresponding
     * to the query.
     *
     * @param query the query of the auRestoProfile search
     * @return the result of the search
     */
    @GetMapping("/_search/au-resto-profiles")
    @Timed
    public List<AuRestoProfile> searchAuRestoProfiles(@RequestParam String query) {
        log.debug("REST request to search AuRestoProfiles for query {}", query);
        return StreamSupport
            .stream(auRestoProfileSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
