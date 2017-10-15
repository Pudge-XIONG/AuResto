package io.pudge.auresto.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.pudge.auresto.domain.AuRestoPhoto;

import io.pudge.auresto.repository.AuRestoPhotoRepository;
import io.pudge.auresto.repository.search.AuRestoPhotoSearchRepository;
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
 * REST controller for managing AuRestoPhoto.
 */
@RestController
@RequestMapping("/api")
public class AuRestoPhotoResource {

    private final Logger log = LoggerFactory.getLogger(AuRestoPhotoResource.class);

    private static final String ENTITY_NAME = "auRestoPhoto";

    private final AuRestoPhotoRepository auRestoPhotoRepository;

    private final AuRestoPhotoSearchRepository auRestoPhotoSearchRepository;

    public AuRestoPhotoResource(AuRestoPhotoRepository auRestoPhotoRepository, AuRestoPhotoSearchRepository auRestoPhotoSearchRepository) {
        this.auRestoPhotoRepository = auRestoPhotoRepository;
        this.auRestoPhotoSearchRepository = auRestoPhotoSearchRepository;
    }

    /**
     * POST  /au-resto-photos : Create a new auRestoPhoto.
     *
     * @param auRestoPhoto the auRestoPhoto to create
     * @return the ResponseEntity with status 201 (Created) and with body the new auRestoPhoto, or with status 400 (Bad Request) if the auRestoPhoto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/au-resto-photos")
    @Timed
    public ResponseEntity<AuRestoPhoto> createAuRestoPhoto(@RequestBody AuRestoPhoto auRestoPhoto) throws URISyntaxException {
        log.debug("REST request to save AuRestoPhoto : {}", auRestoPhoto);
        if (auRestoPhoto.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new auRestoPhoto cannot already have an ID")).body(null);
        }
        AuRestoPhoto result = auRestoPhotoRepository.save(auRestoPhoto);
        auRestoPhotoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/au-resto-photos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /au-resto-photos : Updates an existing auRestoPhoto.
     *
     * @param auRestoPhoto the auRestoPhoto to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated auRestoPhoto,
     * or with status 400 (Bad Request) if the auRestoPhoto is not valid,
     * or with status 500 (Internal Server Error) if the auRestoPhoto couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/au-resto-photos")
    @Timed
    public ResponseEntity<AuRestoPhoto> updateAuRestoPhoto(@RequestBody AuRestoPhoto auRestoPhoto) throws URISyntaxException {
        log.debug("REST request to update AuRestoPhoto : {}", auRestoPhoto);
        if (auRestoPhoto.getId() == null) {
            return createAuRestoPhoto(auRestoPhoto);
        }
        AuRestoPhoto result = auRestoPhotoRepository.save(auRestoPhoto);
        auRestoPhotoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, auRestoPhoto.getId().toString()))
            .body(result);
    }

    /**
     * GET  /au-resto-photos : get all the auRestoPhotos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of auRestoPhotos in body
     */
    @GetMapping("/au-resto-photos")
    @Timed
    public List<AuRestoPhoto> getAllAuRestoPhotos() {
        log.debug("REST request to get all AuRestoPhotos");
        return auRestoPhotoRepository.findAll();
        }

    /**
     * GET  /au-resto-photos/:id : get the "id" auRestoPhoto.
     *
     * @param id the id of the auRestoPhoto to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auRestoPhoto, or with status 404 (Not Found)
     */
    @GetMapping("/au-resto-photos/{id}")
    @Timed
    public ResponseEntity<AuRestoPhoto> getAuRestoPhoto(@PathVariable Long id) {
        log.debug("REST request to get AuRestoPhoto : {}", id);
        AuRestoPhoto auRestoPhoto = auRestoPhotoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(auRestoPhoto));
    }

    /**
     * DELETE  /au-resto-photos/:id : delete the "id" auRestoPhoto.
     *
     * @param id the id of the auRestoPhoto to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/au-resto-photos/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuRestoPhoto(@PathVariable Long id) {
        log.debug("REST request to delete AuRestoPhoto : {}", id);
        auRestoPhotoRepository.delete(id);
        auRestoPhotoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/au-resto-photos?query=:query : search for the auRestoPhoto corresponding
     * to the query.
     *
     * @param query the query of the auRestoPhoto search
     * @return the result of the search
     */
    @GetMapping("/_search/au-resto-photos")
    @Timed
    public List<AuRestoPhoto> searchAuRestoPhotos(@RequestParam String query) {
        log.debug("REST request to search AuRestoPhotos for query {}", query);
        return StreamSupport
            .stream(auRestoPhotoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
