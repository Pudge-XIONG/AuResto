package io.pudge.auresto.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.pudge.auresto.domain.AuRestoUser;

import io.pudge.auresto.repository.AuRestoUserRepository;
import io.pudge.auresto.repository.search.AuRestoUserSearchRepository;
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
 * REST controller for managing AuRestoUser.
 */
@RestController
@RequestMapping("/api")
public class AuRestoUserResource {

    private final Logger log = LoggerFactory.getLogger(AuRestoUserResource.class);

    private static final String ENTITY_NAME = "auRestoUser";

    private final AuRestoUserRepository auRestoUserRepository;

    private final AuRestoUserSearchRepository auRestoUserSearchRepository;

    public AuRestoUserResource(AuRestoUserRepository auRestoUserRepository, AuRestoUserSearchRepository auRestoUserSearchRepository) {
        this.auRestoUserRepository = auRestoUserRepository;
        this.auRestoUserSearchRepository = auRestoUserSearchRepository;
    }

    /**
     * POST  /au-resto-users : Create a new auRestoUser.
     *
     * @param auRestoUser the auRestoUser to create
     * @return the ResponseEntity with status 201 (Created) and with body the new auRestoUser, or with status 400 (Bad Request) if the auRestoUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/au-resto-users")
    @Timed
    public ResponseEntity<AuRestoUser> createAuRestoUser(@RequestBody AuRestoUser auRestoUser) throws URISyntaxException {
        log.debug("REST request to save AuRestoUser : {}", auRestoUser);
        if (auRestoUser.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new auRestoUser cannot already have an ID")).body(null);
        }
        AuRestoUser result = auRestoUserRepository.save(auRestoUser);
        auRestoUserSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/au-resto-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /au-resto-users : Updates an existing auRestoUser.
     *
     * @param auRestoUser the auRestoUser to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated auRestoUser,
     * or with status 400 (Bad Request) if the auRestoUser is not valid,
     * or with status 500 (Internal Server Error) if the auRestoUser couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/au-resto-users")
    @Timed
    public ResponseEntity<AuRestoUser> updateAuRestoUser(@RequestBody AuRestoUser auRestoUser) throws URISyntaxException {
        log.debug("REST request to update AuRestoUser : {}", auRestoUser);
        if (auRestoUser.getId() == null) {
            return createAuRestoUser(auRestoUser);
        }
        AuRestoUser result = auRestoUserRepository.save(auRestoUser);
        auRestoUserSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, auRestoUser.getId().toString()))
            .body(result);
    }

    /**
     * GET  /au-resto-users : get all the auRestoUsers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of auRestoUsers in body
     */
    @GetMapping("/au-resto-users")
    @Timed
    public ResponseEntity<List<AuRestoUser>> getAllAuRestoUsers(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of AuRestoUsers");
        Page<AuRestoUser> page = auRestoUserRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/au-resto-users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /au-resto-users/:id : get the "id" auRestoUser.
     *
     * @param id the id of the auRestoUser to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auRestoUser, or with status 404 (Not Found)
     */
    @GetMapping("/au-resto-users/{id}")
    @Timed
    public ResponseEntity<AuRestoUser> getAuRestoUser(@PathVariable Long id) {
        log.debug("REST request to get AuRestoUser : {}", id);
        AuRestoUser auRestoUser = auRestoUserRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(auRestoUser));
    }

    /**
     * DELETE  /au-resto-users/:id : delete the "id" auRestoUser.
     *
     * @param id the id of the auRestoUser to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/au-resto-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuRestoUser(@PathVariable Long id) {
        log.debug("REST request to delete AuRestoUser : {}", id);
        auRestoUserRepository.delete(id);
        auRestoUserSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/au-resto-users?query=:query : search for the auRestoUser corresponding
     * to the query.
     *
     * @param query the query of the auRestoUser search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/au-resto-users")
    @Timed
    public ResponseEntity<List<AuRestoUser>> searchAuRestoUsers(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of AuRestoUsers for query {}", query);
        Page<AuRestoUser> page = auRestoUserSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/au-resto-users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
