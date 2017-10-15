package io.pudge.auresto.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.pudge.auresto.domain.AuRestoOrder;

import io.pudge.auresto.repository.AuRestoOrderRepository;
import io.pudge.auresto.repository.search.AuRestoOrderSearchRepository;
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
 * REST controller for managing AuRestoOrder.
 */
@RestController
@RequestMapping("/api")
public class AuRestoOrderResource {

    private final Logger log = LoggerFactory.getLogger(AuRestoOrderResource.class);

    private static final String ENTITY_NAME = "auRestoOrder";

    private final AuRestoOrderRepository auRestoOrderRepository;

    private final AuRestoOrderSearchRepository auRestoOrderSearchRepository;

    public AuRestoOrderResource(AuRestoOrderRepository auRestoOrderRepository, AuRestoOrderSearchRepository auRestoOrderSearchRepository) {
        this.auRestoOrderRepository = auRestoOrderRepository;
        this.auRestoOrderSearchRepository = auRestoOrderSearchRepository;
    }

    /**
     * POST  /au-resto-orders : Create a new auRestoOrder.
     *
     * @param auRestoOrder the auRestoOrder to create
     * @return the ResponseEntity with status 201 (Created) and with body the new auRestoOrder, or with status 400 (Bad Request) if the auRestoOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/au-resto-orders")
    @Timed
    public ResponseEntity<AuRestoOrder> createAuRestoOrder(@RequestBody AuRestoOrder auRestoOrder) throws URISyntaxException {
        log.debug("REST request to save AuRestoOrder : {}", auRestoOrder);
        if (auRestoOrder.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new auRestoOrder cannot already have an ID")).body(null);
        }
        AuRestoOrder result = auRestoOrderRepository.save(auRestoOrder);
        auRestoOrderSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/au-resto-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /au-resto-orders : Updates an existing auRestoOrder.
     *
     * @param auRestoOrder the auRestoOrder to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated auRestoOrder,
     * or with status 400 (Bad Request) if the auRestoOrder is not valid,
     * or with status 500 (Internal Server Error) if the auRestoOrder couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/au-resto-orders")
    @Timed
    public ResponseEntity<AuRestoOrder> updateAuRestoOrder(@RequestBody AuRestoOrder auRestoOrder) throws URISyntaxException {
        log.debug("REST request to update AuRestoOrder : {}", auRestoOrder);
        if (auRestoOrder.getId() == null) {
            return createAuRestoOrder(auRestoOrder);
        }
        AuRestoOrder result = auRestoOrderRepository.save(auRestoOrder);
        auRestoOrderSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, auRestoOrder.getId().toString()))
            .body(result);
    }

    /**
     * GET  /au-resto-orders : get all the auRestoOrders.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of auRestoOrders in body
     */
    @GetMapping("/au-resto-orders")
    @Timed
    public ResponseEntity<List<AuRestoOrder>> getAllAuRestoOrders(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of AuRestoOrders");
        Page<AuRestoOrder> page = auRestoOrderRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/au-resto-orders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /au-resto-orders/:id : get the "id" auRestoOrder.
     *
     * @param id the id of the auRestoOrder to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auRestoOrder, or with status 404 (Not Found)
     */
    @GetMapping("/au-resto-orders/{id}")
    @Timed
    public ResponseEntity<AuRestoOrder> getAuRestoOrder(@PathVariable Long id) {
        log.debug("REST request to get AuRestoOrder : {}", id);
        AuRestoOrder auRestoOrder = auRestoOrderRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(auRestoOrder));
    }

    /**
     * DELETE  /au-resto-orders/:id : delete the "id" auRestoOrder.
     *
     * @param id the id of the auRestoOrder to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/au-resto-orders/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuRestoOrder(@PathVariable Long id) {
        log.debug("REST request to delete AuRestoOrder : {}", id);
        auRestoOrderRepository.delete(id);
        auRestoOrderSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/au-resto-orders?query=:query : search for the auRestoOrder corresponding
     * to the query.
     *
     * @param query the query of the auRestoOrder search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/au-resto-orders")
    @Timed
    public ResponseEntity<List<AuRestoOrder>> searchAuRestoOrders(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of AuRestoOrders for query {}", query);
        Page<AuRestoOrder> page = auRestoOrderSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/au-resto-orders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
