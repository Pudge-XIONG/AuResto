package io.pudge.auresto.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.pudge.auresto.domain.AuRestoOrderStatus;

import io.pudge.auresto.repository.AuRestoOrderStatusRepository;
import io.pudge.auresto.repository.search.AuRestoOrderStatusSearchRepository;
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
 * REST controller for managing AuRestoOrderStatus.
 */
@RestController
@RequestMapping("/api")
public class AuRestoOrderStatusResource {

    private final Logger log = LoggerFactory.getLogger(AuRestoOrderStatusResource.class);

    private static final String ENTITY_NAME = "auRestoOrderStatus";

    private final AuRestoOrderStatusRepository auRestoOrderStatusRepository;

    private final AuRestoOrderStatusSearchRepository auRestoOrderStatusSearchRepository;

    public AuRestoOrderStatusResource(AuRestoOrderStatusRepository auRestoOrderStatusRepository, AuRestoOrderStatusSearchRepository auRestoOrderStatusSearchRepository) {
        this.auRestoOrderStatusRepository = auRestoOrderStatusRepository;
        this.auRestoOrderStatusSearchRepository = auRestoOrderStatusSearchRepository;
    }

    /**
     * POST  /au-resto-order-statuses : Create a new auRestoOrderStatus.
     *
     * @param auRestoOrderStatus the auRestoOrderStatus to create
     * @return the ResponseEntity with status 201 (Created) and with body the new auRestoOrderStatus, or with status 400 (Bad Request) if the auRestoOrderStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/au-resto-order-statuses")
    @Timed
    public ResponseEntity<AuRestoOrderStatus> createAuRestoOrderStatus(@RequestBody AuRestoOrderStatus auRestoOrderStatus) throws URISyntaxException {
        log.debug("REST request to save AuRestoOrderStatus : {}", auRestoOrderStatus);
        if (auRestoOrderStatus.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new auRestoOrderStatus cannot already have an ID")).body(null);
        }
        AuRestoOrderStatus result = auRestoOrderStatusRepository.save(auRestoOrderStatus);
        auRestoOrderStatusSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/au-resto-order-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /au-resto-order-statuses : Updates an existing auRestoOrderStatus.
     *
     * @param auRestoOrderStatus the auRestoOrderStatus to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated auRestoOrderStatus,
     * or with status 400 (Bad Request) if the auRestoOrderStatus is not valid,
     * or with status 500 (Internal Server Error) if the auRestoOrderStatus couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/au-resto-order-statuses")
    @Timed
    public ResponseEntity<AuRestoOrderStatus> updateAuRestoOrderStatus(@RequestBody AuRestoOrderStatus auRestoOrderStatus) throws URISyntaxException {
        log.debug("REST request to update AuRestoOrderStatus : {}", auRestoOrderStatus);
        if (auRestoOrderStatus.getId() == null) {
            return createAuRestoOrderStatus(auRestoOrderStatus);
        }
        AuRestoOrderStatus result = auRestoOrderStatusRepository.save(auRestoOrderStatus);
        auRestoOrderStatusSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, auRestoOrderStatus.getId().toString()))
            .body(result);
    }

    /**
     * GET  /au-resto-order-statuses : get all the auRestoOrderStatuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of auRestoOrderStatuses in body
     */
    @GetMapping("/au-resto-order-statuses")
    @Timed
    public List<AuRestoOrderStatus> getAllAuRestoOrderStatuses() {
        log.debug("REST request to get all AuRestoOrderStatuses");
        return auRestoOrderStatusRepository.findAll();
        }

    /**
     * GET  /au-resto-order-statuses/:id : get the "id" auRestoOrderStatus.
     *
     * @param id the id of the auRestoOrderStatus to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auRestoOrderStatus, or with status 404 (Not Found)
     */
    @GetMapping("/au-resto-order-statuses/{id}")
    @Timed
    public ResponseEntity<AuRestoOrderStatus> getAuRestoOrderStatus(@PathVariable Long id) {
        log.debug("REST request to get AuRestoOrderStatus : {}", id);
        AuRestoOrderStatus auRestoOrderStatus = auRestoOrderStatusRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(auRestoOrderStatus));
    }

    /**
     * DELETE  /au-resto-order-statuses/:id : delete the "id" auRestoOrderStatus.
     *
     * @param id the id of the auRestoOrderStatus to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/au-resto-order-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuRestoOrderStatus(@PathVariable Long id) {
        log.debug("REST request to delete AuRestoOrderStatus : {}", id);
        auRestoOrderStatusRepository.delete(id);
        auRestoOrderStatusSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/au-resto-order-statuses?query=:query : search for the auRestoOrderStatus corresponding
     * to the query.
     *
     * @param query the query of the auRestoOrderStatus search
     * @return the result of the search
     */
    @GetMapping("/_search/au-resto-order-statuses")
    @Timed
    public List<AuRestoOrderStatus> searchAuRestoOrderStatuses(@RequestParam String query) {
        log.debug("REST request to search AuRestoOrderStatuses for query {}", query);
        return StreamSupport
            .stream(auRestoOrderStatusSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
