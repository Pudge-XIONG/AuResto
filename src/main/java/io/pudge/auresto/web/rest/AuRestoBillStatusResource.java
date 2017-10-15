package io.pudge.auresto.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.pudge.auresto.domain.AuRestoBillStatus;

import io.pudge.auresto.repository.AuRestoBillStatusRepository;
import io.pudge.auresto.repository.search.AuRestoBillStatusSearchRepository;
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
 * REST controller for managing AuRestoBillStatus.
 */
@RestController
@RequestMapping("/api")
public class AuRestoBillStatusResource {

    private final Logger log = LoggerFactory.getLogger(AuRestoBillStatusResource.class);

    private static final String ENTITY_NAME = "auRestoBillStatus";

    private final AuRestoBillStatusRepository auRestoBillStatusRepository;

    private final AuRestoBillStatusSearchRepository auRestoBillStatusSearchRepository;

    public AuRestoBillStatusResource(AuRestoBillStatusRepository auRestoBillStatusRepository, AuRestoBillStatusSearchRepository auRestoBillStatusSearchRepository) {
        this.auRestoBillStatusRepository = auRestoBillStatusRepository;
        this.auRestoBillStatusSearchRepository = auRestoBillStatusSearchRepository;
    }

    /**
     * POST  /au-resto-bill-statuses : Create a new auRestoBillStatus.
     *
     * @param auRestoBillStatus the auRestoBillStatus to create
     * @return the ResponseEntity with status 201 (Created) and with body the new auRestoBillStatus, or with status 400 (Bad Request) if the auRestoBillStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/au-resto-bill-statuses")
    @Timed
    public ResponseEntity<AuRestoBillStatus> createAuRestoBillStatus(@RequestBody AuRestoBillStatus auRestoBillStatus) throws URISyntaxException {
        log.debug("REST request to save AuRestoBillStatus : {}", auRestoBillStatus);
        if (auRestoBillStatus.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new auRestoBillStatus cannot already have an ID")).body(null);
        }
        AuRestoBillStatus result = auRestoBillStatusRepository.save(auRestoBillStatus);
        auRestoBillStatusSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/au-resto-bill-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /au-resto-bill-statuses : Updates an existing auRestoBillStatus.
     *
     * @param auRestoBillStatus the auRestoBillStatus to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated auRestoBillStatus,
     * or with status 400 (Bad Request) if the auRestoBillStatus is not valid,
     * or with status 500 (Internal Server Error) if the auRestoBillStatus couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/au-resto-bill-statuses")
    @Timed
    public ResponseEntity<AuRestoBillStatus> updateAuRestoBillStatus(@RequestBody AuRestoBillStatus auRestoBillStatus) throws URISyntaxException {
        log.debug("REST request to update AuRestoBillStatus : {}", auRestoBillStatus);
        if (auRestoBillStatus.getId() == null) {
            return createAuRestoBillStatus(auRestoBillStatus);
        }
        AuRestoBillStatus result = auRestoBillStatusRepository.save(auRestoBillStatus);
        auRestoBillStatusSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, auRestoBillStatus.getId().toString()))
            .body(result);
    }

    /**
     * GET  /au-resto-bill-statuses : get all the auRestoBillStatuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of auRestoBillStatuses in body
     */
    @GetMapping("/au-resto-bill-statuses")
    @Timed
    public List<AuRestoBillStatus> getAllAuRestoBillStatuses() {
        log.debug("REST request to get all AuRestoBillStatuses");
        return auRestoBillStatusRepository.findAll();
        }

    /**
     * GET  /au-resto-bill-statuses/:id : get the "id" auRestoBillStatus.
     *
     * @param id the id of the auRestoBillStatus to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auRestoBillStatus, or with status 404 (Not Found)
     */
    @GetMapping("/au-resto-bill-statuses/{id}")
    @Timed
    public ResponseEntity<AuRestoBillStatus> getAuRestoBillStatus(@PathVariable Long id) {
        log.debug("REST request to get AuRestoBillStatus : {}", id);
        AuRestoBillStatus auRestoBillStatus = auRestoBillStatusRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(auRestoBillStatus));
    }

    /**
     * DELETE  /au-resto-bill-statuses/:id : delete the "id" auRestoBillStatus.
     *
     * @param id the id of the auRestoBillStatus to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/au-resto-bill-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuRestoBillStatus(@PathVariable Long id) {
        log.debug("REST request to delete AuRestoBillStatus : {}", id);
        auRestoBillStatusRepository.delete(id);
        auRestoBillStatusSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/au-resto-bill-statuses?query=:query : search for the auRestoBillStatus corresponding
     * to the query.
     *
     * @param query the query of the auRestoBillStatus search
     * @return the result of the search
     */
    @GetMapping("/_search/au-resto-bill-statuses")
    @Timed
    public List<AuRestoBillStatus> searchAuRestoBillStatuses(@RequestParam String query) {
        log.debug("REST request to search AuRestoBillStatuses for query {}", query);
        return StreamSupport
            .stream(auRestoBillStatusSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
