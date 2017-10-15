package io.pudge.auresto.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.pudge.auresto.domain.AuRestoReservation;

import io.pudge.auresto.repository.AuRestoReservationRepository;
import io.pudge.auresto.repository.search.AuRestoReservationSearchRepository;
import io.pudge.auresto.web.rest.util.HeaderUtil;
import io.pudge.auresto.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
 * REST controller for managing AuRestoReservation.
 */
@RestController
@RequestMapping("/api")
public class AuRestoReservationResource {

    private final Logger log = LoggerFactory.getLogger(AuRestoReservationResource.class);

    private static final String ENTITY_NAME = "auRestoReservation";

    private final AuRestoReservationRepository auRestoReservationRepository;

    private final AuRestoReservationSearchRepository auRestoReservationSearchRepository;

    public AuRestoReservationResource(AuRestoReservationRepository auRestoReservationRepository, AuRestoReservationSearchRepository auRestoReservationSearchRepository) {
        this.auRestoReservationRepository = auRestoReservationRepository;
        this.auRestoReservationSearchRepository = auRestoReservationSearchRepository;
    }

    /**
     * POST  /au-resto-reservations : Create a new auRestoReservation.
     *
     * @param auRestoReservation the auRestoReservation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new auRestoReservation, or with status 400 (Bad Request) if the auRestoReservation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/au-resto-reservations")
    @Timed
    public ResponseEntity<AuRestoReservation> createAuRestoReservation(@RequestBody AuRestoReservation auRestoReservation) throws URISyntaxException {
        log.debug("REST request to save AuRestoReservation : {}", auRestoReservation);
        if (auRestoReservation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new auRestoReservation cannot already have an ID")).body(null);
        }
        AuRestoReservation result = auRestoReservationRepository.save(auRestoReservation);
        auRestoReservationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/au-resto-reservations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /au-resto-reservations : Updates an existing auRestoReservation.
     *
     * @param auRestoReservation the auRestoReservation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated auRestoReservation,
     * or with status 400 (Bad Request) if the auRestoReservation is not valid,
     * or with status 500 (Internal Server Error) if the auRestoReservation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/au-resto-reservations")
    @Timed
    public ResponseEntity<AuRestoReservation> updateAuRestoReservation(@RequestBody AuRestoReservation auRestoReservation) throws URISyntaxException {
        log.debug("REST request to update AuRestoReservation : {}", auRestoReservation);
        if (auRestoReservation.getId() == null) {
            return createAuRestoReservation(auRestoReservation);
        }
        AuRestoReservation result = auRestoReservationRepository.save(auRestoReservation);
        auRestoReservationSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, auRestoReservation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /au-resto-reservations : get all the auRestoReservations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of auRestoReservations in body
     */
    @GetMapping("/au-resto-reservations")
    @Timed
    public ResponseEntity<List<AuRestoReservation>> getAllAuRestoReservations(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of AuRestoReservations");
        Page<AuRestoReservation> page = auRestoReservationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/au-resto-reservations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /au-resto-reservations/:id : get the "id" auRestoReservation.
     *
     * @param id the id of the auRestoReservation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auRestoReservation, or with status 404 (Not Found)
     */
    @GetMapping("/au-resto-reservations/{id}")
    @Timed
    public ResponseEntity<AuRestoReservation> getAuRestoReservation(@PathVariable Long id) {
        log.debug("REST request to get AuRestoReservation : {}", id);
        AuRestoReservation auRestoReservation = auRestoReservationRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(auRestoReservation));
    }

    /**
     * DELETE  /au-resto-reservations/:id : delete the "id" auRestoReservation.
     *
     * @param id the id of the auRestoReservation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/au-resto-reservations/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuRestoReservation(@PathVariable Long id) {
        log.debug("REST request to delete AuRestoReservation : {}", id);
        auRestoReservationRepository.delete(id);
        auRestoReservationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/au-resto-reservations?query=:query : search for the auRestoReservation corresponding
     * to the query.
     *
     * @param query the query of the auRestoReservation search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/au-resto-reservations")
    @Timed
    public ResponseEntity<List<AuRestoReservation>> searchAuRestoReservations(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of AuRestoReservations for query {}", query);
        Page<AuRestoReservation> page = auRestoReservationSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/au-resto-reservations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
