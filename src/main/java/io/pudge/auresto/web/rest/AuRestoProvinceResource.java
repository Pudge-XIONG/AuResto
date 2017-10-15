package io.pudge.auresto.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.pudge.auresto.domain.AuRestoProvince;

import io.pudge.auresto.repository.AuRestoProvinceRepository;
import io.pudge.auresto.repository.search.AuRestoProvinceSearchRepository;
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
 * REST controller for managing AuRestoProvince.
 */
@RestController
@RequestMapping("/api")
public class AuRestoProvinceResource {

    private final Logger log = LoggerFactory.getLogger(AuRestoProvinceResource.class);

    private static final String ENTITY_NAME = "auRestoProvince";

    private final AuRestoProvinceRepository auRestoProvinceRepository;

    private final AuRestoProvinceSearchRepository auRestoProvinceSearchRepository;

    public AuRestoProvinceResource(AuRestoProvinceRepository auRestoProvinceRepository, AuRestoProvinceSearchRepository auRestoProvinceSearchRepository) {
        this.auRestoProvinceRepository = auRestoProvinceRepository;
        this.auRestoProvinceSearchRepository = auRestoProvinceSearchRepository;
    }

    /**
     * POST  /au-resto-provinces : Create a new auRestoProvince.
     *
     * @param auRestoProvince the auRestoProvince to create
     * @return the ResponseEntity with status 201 (Created) and with body the new auRestoProvince, or with status 400 (Bad Request) if the auRestoProvince has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/au-resto-provinces")
    @Timed
    public ResponseEntity<AuRestoProvince> createAuRestoProvince(@RequestBody AuRestoProvince auRestoProvince) throws URISyntaxException {
        log.debug("REST request to save AuRestoProvince : {}", auRestoProvince);
        if (auRestoProvince.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new auRestoProvince cannot already have an ID")).body(null);
        }
        AuRestoProvince result = auRestoProvinceRepository.save(auRestoProvince);
        auRestoProvinceSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/au-resto-provinces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /au-resto-provinces : Updates an existing auRestoProvince.
     *
     * @param auRestoProvince the auRestoProvince to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated auRestoProvince,
     * or with status 400 (Bad Request) if the auRestoProvince is not valid,
     * or with status 500 (Internal Server Error) if the auRestoProvince couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/au-resto-provinces")
    @Timed
    public ResponseEntity<AuRestoProvince> updateAuRestoProvince(@RequestBody AuRestoProvince auRestoProvince) throws URISyntaxException {
        log.debug("REST request to update AuRestoProvince : {}", auRestoProvince);
        if (auRestoProvince.getId() == null) {
            return createAuRestoProvince(auRestoProvince);
        }
        AuRestoProvince result = auRestoProvinceRepository.save(auRestoProvince);
        auRestoProvinceSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, auRestoProvince.getId().toString()))
            .body(result);
    }

    /**
     * GET  /au-resto-provinces : get all the auRestoProvinces.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of auRestoProvinces in body
     */
    @GetMapping("/au-resto-provinces")
    @Timed
    public List<AuRestoProvince> getAllAuRestoProvinces() {
        log.debug("REST request to get all AuRestoProvinces");
        return auRestoProvinceRepository.findAll();
        }

    /**
     * GET  /au-resto-provinces/:id : get the "id" auRestoProvince.
     *
     * @param id the id of the auRestoProvince to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auRestoProvince, or with status 404 (Not Found)
     */
    @GetMapping("/au-resto-provinces/{id}")
    @Timed
    public ResponseEntity<AuRestoProvince> getAuRestoProvince(@PathVariable Long id) {
        log.debug("REST request to get AuRestoProvince : {}", id);
        AuRestoProvince auRestoProvince = auRestoProvinceRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(auRestoProvince));
    }

    /**
     * DELETE  /au-resto-provinces/:id : delete the "id" auRestoProvince.
     *
     * @param id the id of the auRestoProvince to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/au-resto-provinces/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuRestoProvince(@PathVariable Long id) {
        log.debug("REST request to delete AuRestoProvince : {}", id);
        auRestoProvinceRepository.delete(id);
        auRestoProvinceSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/au-resto-provinces?query=:query : search for the auRestoProvince corresponding
     * to the query.
     *
     * @param query the query of the auRestoProvince search
     * @return the result of the search
     */
    @GetMapping("/_search/au-resto-provinces")
    @Timed
    public List<AuRestoProvince> searchAuRestoProvinces(@RequestParam String query) {
        log.debug("REST request to search AuRestoProvinces for query {}", query);
        return StreamSupport
            .stream(auRestoProvinceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
