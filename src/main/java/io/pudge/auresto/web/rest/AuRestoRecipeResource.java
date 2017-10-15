package io.pudge.auresto.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.pudge.auresto.domain.AuRestoRecipe;

import io.pudge.auresto.repository.AuRestoRecipeRepository;
import io.pudge.auresto.repository.search.AuRestoRecipeSearchRepository;
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
 * REST controller for managing AuRestoRecipe.
 */
@RestController
@RequestMapping("/api")
public class AuRestoRecipeResource {

    private final Logger log = LoggerFactory.getLogger(AuRestoRecipeResource.class);

    private static final String ENTITY_NAME = "auRestoRecipe";

    private final AuRestoRecipeRepository auRestoRecipeRepository;

    private final AuRestoRecipeSearchRepository auRestoRecipeSearchRepository;

    public AuRestoRecipeResource(AuRestoRecipeRepository auRestoRecipeRepository, AuRestoRecipeSearchRepository auRestoRecipeSearchRepository) {
        this.auRestoRecipeRepository = auRestoRecipeRepository;
        this.auRestoRecipeSearchRepository = auRestoRecipeSearchRepository;
    }

    /**
     * POST  /au-resto-recipes : Create a new auRestoRecipe.
     *
     * @param auRestoRecipe the auRestoRecipe to create
     * @return the ResponseEntity with status 201 (Created) and with body the new auRestoRecipe, or with status 400 (Bad Request) if the auRestoRecipe has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/au-resto-recipes")
    @Timed
    public ResponseEntity<AuRestoRecipe> createAuRestoRecipe(@RequestBody AuRestoRecipe auRestoRecipe) throws URISyntaxException {
        log.debug("REST request to save AuRestoRecipe : {}", auRestoRecipe);
        if (auRestoRecipe.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new auRestoRecipe cannot already have an ID")).body(null);
        }
        AuRestoRecipe result = auRestoRecipeRepository.save(auRestoRecipe);
        auRestoRecipeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/au-resto-recipes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /au-resto-recipes : Updates an existing auRestoRecipe.
     *
     * @param auRestoRecipe the auRestoRecipe to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated auRestoRecipe,
     * or with status 400 (Bad Request) if the auRestoRecipe is not valid,
     * or with status 500 (Internal Server Error) if the auRestoRecipe couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/au-resto-recipes")
    @Timed
    public ResponseEntity<AuRestoRecipe> updateAuRestoRecipe(@RequestBody AuRestoRecipe auRestoRecipe) throws URISyntaxException {
        log.debug("REST request to update AuRestoRecipe : {}", auRestoRecipe);
        if (auRestoRecipe.getId() == null) {
            return createAuRestoRecipe(auRestoRecipe);
        }
        AuRestoRecipe result = auRestoRecipeRepository.save(auRestoRecipe);
        auRestoRecipeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, auRestoRecipe.getId().toString()))
            .body(result);
    }

    /**
     * GET  /au-resto-recipes : get all the auRestoRecipes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of auRestoRecipes in body
     */
    @GetMapping("/au-resto-recipes")
    @Timed
    public List<AuRestoRecipe> getAllAuRestoRecipes() {
        log.debug("REST request to get all AuRestoRecipes");
        return auRestoRecipeRepository.findAll();
        }

    /**
     * GET  /au-resto-recipes/:id : get the "id" auRestoRecipe.
     *
     * @param id the id of the auRestoRecipe to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auRestoRecipe, or with status 404 (Not Found)
     */
    @GetMapping("/au-resto-recipes/{id}")
    @Timed
    public ResponseEntity<AuRestoRecipe> getAuRestoRecipe(@PathVariable Long id) {
        log.debug("REST request to get AuRestoRecipe : {}", id);
        AuRestoRecipe auRestoRecipe = auRestoRecipeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(auRestoRecipe));
    }

    /**
     * DELETE  /au-resto-recipes/:id : delete the "id" auRestoRecipe.
     *
     * @param id the id of the auRestoRecipe to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/au-resto-recipes/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuRestoRecipe(@PathVariable Long id) {
        log.debug("REST request to delete AuRestoRecipe : {}", id);
        auRestoRecipeRepository.delete(id);
        auRestoRecipeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/au-resto-recipes?query=:query : search for the auRestoRecipe corresponding
     * to the query.
     *
     * @param query the query of the auRestoRecipe search
     * @return the result of the search
     */
    @GetMapping("/_search/au-resto-recipes")
    @Timed
    public List<AuRestoRecipe> searchAuRestoRecipes(@RequestParam String query) {
        log.debug("REST request to search AuRestoRecipes for query {}", query);
        return StreamSupport
            .stream(auRestoRecipeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
