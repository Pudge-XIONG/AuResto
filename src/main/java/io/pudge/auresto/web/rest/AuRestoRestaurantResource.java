package io.pudge.auresto.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.pudge.auresto.domain.AuRestoRestaurant;

import io.pudge.auresto.repository.AuRestoRestaurantRepository;
import io.pudge.auresto.repository.search.AuRestoRestaurantSearchRepository;
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
 * REST controller for managing AuRestoRestaurant.
 */
@RestController
@RequestMapping("/api")
public class AuRestoRestaurantResource {

    private final Logger log = LoggerFactory.getLogger(AuRestoRestaurantResource.class);

    private static final String ENTITY_NAME = "auRestoRestaurant";

    private final AuRestoRestaurantRepository auRestoRestaurantRepository;

    private final AuRestoRestaurantSearchRepository auRestoRestaurantSearchRepository;

    public AuRestoRestaurantResource(AuRestoRestaurantRepository auRestoRestaurantRepository, AuRestoRestaurantSearchRepository auRestoRestaurantSearchRepository) {
        this.auRestoRestaurantRepository = auRestoRestaurantRepository;
        this.auRestoRestaurantSearchRepository = auRestoRestaurantSearchRepository;
    }

    /**
     * POST  /au-resto-restaurants : Create a new auRestoRestaurant.
     *
     * @param auRestoRestaurant the auRestoRestaurant to create
     * @return the ResponseEntity with status 201 (Created) and with body the new auRestoRestaurant, or with status 400 (Bad Request) if the auRestoRestaurant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/au-resto-restaurants")
    @Timed
    public ResponseEntity<AuRestoRestaurant> createAuRestoRestaurant(@RequestBody AuRestoRestaurant auRestoRestaurant) throws URISyntaxException {
        log.debug("REST request to save AuRestoRestaurant : {}", auRestoRestaurant);
        if (auRestoRestaurant.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new auRestoRestaurant cannot already have an ID")).body(null);
        }
        AuRestoRestaurant result = auRestoRestaurantRepository.save(auRestoRestaurant);
        auRestoRestaurantSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/au-resto-restaurants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /au-resto-restaurants : Updates an existing auRestoRestaurant.
     *
     * @param auRestoRestaurant the auRestoRestaurant to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated auRestoRestaurant,
     * or with status 400 (Bad Request) if the auRestoRestaurant is not valid,
     * or with status 500 (Internal Server Error) if the auRestoRestaurant couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/au-resto-restaurants")
    @Timed
    public ResponseEntity<AuRestoRestaurant> updateAuRestoRestaurant(@RequestBody AuRestoRestaurant auRestoRestaurant) throws URISyntaxException {
        log.debug("REST request to update AuRestoRestaurant : {}", auRestoRestaurant);
        if (auRestoRestaurant.getId() == null) {
            return createAuRestoRestaurant(auRestoRestaurant);
        }
        AuRestoRestaurant result = auRestoRestaurantRepository.save(auRestoRestaurant);
        auRestoRestaurantSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, auRestoRestaurant.getId().toString()))
            .body(result);
    }

    /**
     * GET  /au-resto-restaurants : get all the auRestoRestaurants.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of auRestoRestaurants in body
     */
    @GetMapping("/au-resto-restaurants")
    @Timed
    public List<AuRestoRestaurant> getAllAuRestoRestaurants() {
        log.debug("REST request to get all AuRestoRestaurants");
        return auRestoRestaurantRepository.findAll();
        }

    /**
     * GET  /au-resto-restaurants/:id : get the "id" auRestoRestaurant.
     *
     * @param id the id of the auRestoRestaurant to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auRestoRestaurant, or with status 404 (Not Found)
     */
    @GetMapping("/au-resto-restaurants/{id}")
    @Timed
    public ResponseEntity<AuRestoRestaurant> getAuRestoRestaurant(@PathVariable Long id) {
        log.debug("REST request to get AuRestoRestaurant : {}", id);
        AuRestoRestaurant auRestoRestaurant = auRestoRestaurantRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(auRestoRestaurant));
    }

    /**
     * DELETE  /au-resto-restaurants/:id : delete the "id" auRestoRestaurant.
     *
     * @param id the id of the auRestoRestaurant to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/au-resto-restaurants/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuRestoRestaurant(@PathVariable Long id) {
        log.debug("REST request to delete AuRestoRestaurant : {}", id);
        auRestoRestaurantRepository.delete(id);
        auRestoRestaurantSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/au-resto-restaurants?query=:query : search for the auRestoRestaurant corresponding
     * to the query.
     *
     * @param query the query of the auRestoRestaurant search
     * @return the result of the search
     */
    @GetMapping("/_search/au-resto-restaurants")
    @Timed
    public List<AuRestoRestaurant> searchAuRestoRestaurants(@RequestParam String query) {
        log.debug("REST request to search AuRestoRestaurants for query {}", query);
        return StreamSupport
            .stream(auRestoRestaurantSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
