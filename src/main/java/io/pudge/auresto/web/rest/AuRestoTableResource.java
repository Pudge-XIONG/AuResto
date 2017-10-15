package io.pudge.auresto.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.pudge.auresto.domain.AuRestoTable;

import io.pudge.auresto.repository.AuRestoTableRepository;
import io.pudge.auresto.repository.search.AuRestoTableSearchRepository;
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
 * REST controller for managing AuRestoTable.
 */
@RestController
@RequestMapping("/api")
public class AuRestoTableResource {

    private final Logger log = LoggerFactory.getLogger(AuRestoTableResource.class);

    private static final String ENTITY_NAME = "auRestoTable";

    private final AuRestoTableRepository auRestoTableRepository;

    private final AuRestoTableSearchRepository auRestoTableSearchRepository;

    public AuRestoTableResource(AuRestoTableRepository auRestoTableRepository, AuRestoTableSearchRepository auRestoTableSearchRepository) {
        this.auRestoTableRepository = auRestoTableRepository;
        this.auRestoTableSearchRepository = auRestoTableSearchRepository;
    }

    /**
     * POST  /au-resto-tables : Create a new auRestoTable.
     *
     * @param auRestoTable the auRestoTable to create
     * @return the ResponseEntity with status 201 (Created) and with body the new auRestoTable, or with status 400 (Bad Request) if the auRestoTable has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/au-resto-tables")
    @Timed
    public ResponseEntity<AuRestoTable> createAuRestoTable(@RequestBody AuRestoTable auRestoTable) throws URISyntaxException {
        log.debug("REST request to save AuRestoTable : {}", auRestoTable);
        if (auRestoTable.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new auRestoTable cannot already have an ID")).body(null);
        }
        AuRestoTable result = auRestoTableRepository.save(auRestoTable);
        auRestoTableSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/au-resto-tables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /au-resto-tables : Updates an existing auRestoTable.
     *
     * @param auRestoTable the auRestoTable to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated auRestoTable,
     * or with status 400 (Bad Request) if the auRestoTable is not valid,
     * or with status 500 (Internal Server Error) if the auRestoTable couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/au-resto-tables")
    @Timed
    public ResponseEntity<AuRestoTable> updateAuRestoTable(@RequestBody AuRestoTable auRestoTable) throws URISyntaxException {
        log.debug("REST request to update AuRestoTable : {}", auRestoTable);
        if (auRestoTable.getId() == null) {
            return createAuRestoTable(auRestoTable);
        }
        AuRestoTable result = auRestoTableRepository.save(auRestoTable);
        auRestoTableSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, auRestoTable.getId().toString()))
            .body(result);
    }

    /**
     * GET  /au-resto-tables : get all the auRestoTables.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of auRestoTables in body
     */
    @GetMapping("/au-resto-tables")
    @Timed
    public List<AuRestoTable> getAllAuRestoTables() {
        log.debug("REST request to get all AuRestoTables");
        return auRestoTableRepository.findAll();
        }

    /**
     * GET  /au-resto-tables/:id : get the "id" auRestoTable.
     *
     * @param id the id of the auRestoTable to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auRestoTable, or with status 404 (Not Found)
     */
    @GetMapping("/au-resto-tables/{id}")
    @Timed
    public ResponseEntity<AuRestoTable> getAuRestoTable(@PathVariable Long id) {
        log.debug("REST request to get AuRestoTable : {}", id);
        AuRestoTable auRestoTable = auRestoTableRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(auRestoTable));
    }

    /**
     * DELETE  /au-resto-tables/:id : delete the "id" auRestoTable.
     *
     * @param id the id of the auRestoTable to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/au-resto-tables/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuRestoTable(@PathVariable Long id) {
        log.debug("REST request to delete AuRestoTable : {}", id);
        auRestoTableRepository.delete(id);
        auRestoTableSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/au-resto-tables?query=:query : search for the auRestoTable corresponding
     * to the query.
     *
     * @param query the query of the auRestoTable search
     * @return the result of the search
     */
    @GetMapping("/_search/au-resto-tables")
    @Timed
    public List<AuRestoTable> searchAuRestoTables(@RequestParam String query) {
        log.debug("REST request to search AuRestoTables for query {}", query);
        return StreamSupport
            .stream(auRestoTableSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
