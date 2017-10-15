package io.pudge.auresto.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.pudge.auresto.domain.AuRestoMenu;

import io.pudge.auresto.repository.AuRestoMenuRepository;
import io.pudge.auresto.repository.search.AuRestoMenuSearchRepository;
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
 * REST controller for managing AuRestoMenu.
 */
@RestController
@RequestMapping("/api")
public class AuRestoMenuResource {

    private final Logger log = LoggerFactory.getLogger(AuRestoMenuResource.class);

    private static final String ENTITY_NAME = "auRestoMenu";

    private final AuRestoMenuRepository auRestoMenuRepository;

    private final AuRestoMenuSearchRepository auRestoMenuSearchRepository;

    public AuRestoMenuResource(AuRestoMenuRepository auRestoMenuRepository, AuRestoMenuSearchRepository auRestoMenuSearchRepository) {
        this.auRestoMenuRepository = auRestoMenuRepository;
        this.auRestoMenuSearchRepository = auRestoMenuSearchRepository;
    }

    /**
     * POST  /au-resto-menus : Create a new auRestoMenu.
     *
     * @param auRestoMenu the auRestoMenu to create
     * @return the ResponseEntity with status 201 (Created) and with body the new auRestoMenu, or with status 400 (Bad Request) if the auRestoMenu has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/au-resto-menus")
    @Timed
    public ResponseEntity<AuRestoMenu> createAuRestoMenu(@RequestBody AuRestoMenu auRestoMenu) throws URISyntaxException {
        log.debug("REST request to save AuRestoMenu : {}", auRestoMenu);
        if (auRestoMenu.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new auRestoMenu cannot already have an ID")).body(null);
        }
        AuRestoMenu result = auRestoMenuRepository.save(auRestoMenu);
        auRestoMenuSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/au-resto-menus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /au-resto-menus : Updates an existing auRestoMenu.
     *
     * @param auRestoMenu the auRestoMenu to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated auRestoMenu,
     * or with status 400 (Bad Request) if the auRestoMenu is not valid,
     * or with status 500 (Internal Server Error) if the auRestoMenu couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/au-resto-menus")
    @Timed
    public ResponseEntity<AuRestoMenu> updateAuRestoMenu(@RequestBody AuRestoMenu auRestoMenu) throws URISyntaxException {
        log.debug("REST request to update AuRestoMenu : {}", auRestoMenu);
        if (auRestoMenu.getId() == null) {
            return createAuRestoMenu(auRestoMenu);
        }
        AuRestoMenu result = auRestoMenuRepository.save(auRestoMenu);
        auRestoMenuSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, auRestoMenu.getId().toString()))
            .body(result);
    }

    /**
     * GET  /au-resto-menus : get all the auRestoMenus.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of auRestoMenus in body
     */
    @GetMapping("/au-resto-menus")
    @Timed
    public List<AuRestoMenu> getAllAuRestoMenus() {
        log.debug("REST request to get all AuRestoMenus");
        return auRestoMenuRepository.findAll();
        }

    /**
     * GET  /au-resto-menus/:id : get the "id" auRestoMenu.
     *
     * @param id the id of the auRestoMenu to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auRestoMenu, or with status 404 (Not Found)
     */
    @GetMapping("/au-resto-menus/{id}")
    @Timed
    public ResponseEntity<AuRestoMenu> getAuRestoMenu(@PathVariable Long id) {
        log.debug("REST request to get AuRestoMenu : {}", id);
        AuRestoMenu auRestoMenu = auRestoMenuRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(auRestoMenu));
    }

    /**
     * DELETE  /au-resto-menus/:id : delete the "id" auRestoMenu.
     *
     * @param id the id of the auRestoMenu to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/au-resto-menus/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuRestoMenu(@PathVariable Long id) {
        log.debug("REST request to delete AuRestoMenu : {}", id);
        auRestoMenuRepository.delete(id);
        auRestoMenuSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/au-resto-menus?query=:query : search for the auRestoMenu corresponding
     * to the query.
     *
     * @param query the query of the auRestoMenu search
     * @return the result of the search
     */
    @GetMapping("/_search/au-resto-menus")
    @Timed
    public List<AuRestoMenu> searchAuRestoMenus(@RequestParam String query) {
        log.debug("REST request to search AuRestoMenus for query {}", query);
        return StreamSupport
            .stream(auRestoMenuSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
