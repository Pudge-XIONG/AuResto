package io.pudge.auresto.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.pudge.auresto.domain.AuRestoBill;

import io.pudge.auresto.repository.AuRestoBillRepository;
import io.pudge.auresto.repository.search.AuRestoBillSearchRepository;
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
 * REST controller for managing AuRestoBill.
 */
@RestController
@RequestMapping("/api")
public class AuRestoBillResource {

    private final Logger log = LoggerFactory.getLogger(AuRestoBillResource.class);

    private static final String ENTITY_NAME = "auRestoBill";

    private final AuRestoBillRepository auRestoBillRepository;

    private final AuRestoBillSearchRepository auRestoBillSearchRepository;

    public AuRestoBillResource(AuRestoBillRepository auRestoBillRepository, AuRestoBillSearchRepository auRestoBillSearchRepository) {
        this.auRestoBillRepository = auRestoBillRepository;
        this.auRestoBillSearchRepository = auRestoBillSearchRepository;
    }

    /**
     * POST  /au-resto-bills : Create a new auRestoBill.
     *
     * @param auRestoBill the auRestoBill to create
     * @return the ResponseEntity with status 201 (Created) and with body the new auRestoBill, or with status 400 (Bad Request) if the auRestoBill has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/au-resto-bills")
    @Timed
    public ResponseEntity<AuRestoBill> createAuRestoBill(@RequestBody AuRestoBill auRestoBill) throws URISyntaxException {
        log.debug("REST request to save AuRestoBill : {}", auRestoBill);
        if (auRestoBill.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new auRestoBill cannot already have an ID")).body(null);
        }
        AuRestoBill result = auRestoBillRepository.save(auRestoBill);
        auRestoBillSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/au-resto-bills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /au-resto-bills : Updates an existing auRestoBill.
     *
     * @param auRestoBill the auRestoBill to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated auRestoBill,
     * or with status 400 (Bad Request) if the auRestoBill is not valid,
     * or with status 500 (Internal Server Error) if the auRestoBill couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/au-resto-bills")
    @Timed
    public ResponseEntity<AuRestoBill> updateAuRestoBill(@RequestBody AuRestoBill auRestoBill) throws URISyntaxException {
        log.debug("REST request to update AuRestoBill : {}", auRestoBill);
        if (auRestoBill.getId() == null) {
            return createAuRestoBill(auRestoBill);
        }
        AuRestoBill result = auRestoBillRepository.save(auRestoBill);
        auRestoBillSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, auRestoBill.getId().toString()))
            .body(result);
    }

    /**
     * GET  /au-resto-bills : get all the auRestoBills.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of auRestoBills in body
     */
    @GetMapping("/au-resto-bills")
    @Timed
    public ResponseEntity<List<AuRestoBill>> getAllAuRestoBills(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of AuRestoBills");
        Page<AuRestoBill> page = auRestoBillRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/au-resto-bills");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /au-resto-bills/:id : get the "id" auRestoBill.
     *
     * @param id the id of the auRestoBill to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auRestoBill, or with status 404 (Not Found)
     */
    @GetMapping("/au-resto-bills/{id}")
    @Timed
    public ResponseEntity<AuRestoBill> getAuRestoBill(@PathVariable Long id) {
        log.debug("REST request to get AuRestoBill : {}", id);
        AuRestoBill auRestoBill = auRestoBillRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(auRestoBill));
    }

    /**
     * DELETE  /au-resto-bills/:id : delete the "id" auRestoBill.
     *
     * @param id the id of the auRestoBill to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/au-resto-bills/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuRestoBill(@PathVariable Long id) {
        log.debug("REST request to delete AuRestoBill : {}", id);
        auRestoBillRepository.delete(id);
        auRestoBillSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/au-resto-bills?query=:query : search for the auRestoBill corresponding
     * to the query.
     *
     * @param query the query of the auRestoBill search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/au-resto-bills")
    @Timed
    public ResponseEntity<List<AuRestoBill>> searchAuRestoBills(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of AuRestoBills for query {}", query);
        Page<AuRestoBill> page = auRestoBillSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/au-resto-bills");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
