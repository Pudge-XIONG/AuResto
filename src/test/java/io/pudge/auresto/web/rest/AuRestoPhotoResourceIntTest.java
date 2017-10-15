package io.pudge.auresto.web.rest;

import io.pudge.auresto.AuRestoApp;

import io.pudge.auresto.domain.AuRestoPhoto;
import io.pudge.auresto.repository.AuRestoPhotoRepository;
import io.pudge.auresto.repository.search.AuRestoPhotoSearchRepository;
import io.pudge.auresto.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AuRestoPhotoResource REST controller.
 *
 * @see AuRestoPhotoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuRestoApp.class)
public class AuRestoPhotoResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    @Autowired
    private AuRestoPhotoRepository auRestoPhotoRepository;

    @Autowired
    private AuRestoPhotoSearchRepository auRestoPhotoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAuRestoPhotoMockMvc;

    private AuRestoPhoto auRestoPhoto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AuRestoPhotoResource auRestoPhotoResource = new AuRestoPhotoResource(auRestoPhotoRepository, auRestoPhotoSearchRepository);
        this.restAuRestoPhotoMockMvc = MockMvcBuilders.standaloneSetup(auRestoPhotoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AuRestoPhoto createEntity(EntityManager em) {
        AuRestoPhoto auRestoPhoto = new AuRestoPhoto()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return auRestoPhoto;
    }

    @Before
    public void initTest() {
        auRestoPhotoSearchRepository.deleteAll();
        auRestoPhoto = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuRestoPhoto() throws Exception {
        int databaseSizeBeforeCreate = auRestoPhotoRepository.findAll().size();

        // Create the AuRestoPhoto
        restAuRestoPhotoMockMvc.perform(post("/api/au-resto-photos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoPhoto)))
            .andExpect(status().isCreated());

        // Validate the AuRestoPhoto in the database
        List<AuRestoPhoto> auRestoPhotoList = auRestoPhotoRepository.findAll();
        assertThat(auRestoPhotoList).hasSize(databaseSizeBeforeCreate + 1);
        AuRestoPhoto testAuRestoPhoto = auRestoPhotoList.get(auRestoPhotoList.size() - 1);
        assertThat(testAuRestoPhoto.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAuRestoPhoto.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAuRestoPhoto.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testAuRestoPhoto.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);

        // Validate the AuRestoPhoto in Elasticsearch
        AuRestoPhoto auRestoPhotoEs = auRestoPhotoSearchRepository.findOne(testAuRestoPhoto.getId());
        assertThat(auRestoPhotoEs).isEqualToComparingFieldByField(testAuRestoPhoto);
    }

    @Test
    @Transactional
    public void createAuRestoPhotoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auRestoPhotoRepository.findAll().size();

        // Create the AuRestoPhoto with an existing ID
        auRestoPhoto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuRestoPhotoMockMvc.perform(post("/api/au-resto-photos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoPhoto)))
            .andExpect(status().isBadRequest());

        // Validate the AuRestoPhoto in the database
        List<AuRestoPhoto> auRestoPhotoList = auRestoPhotoRepository.findAll();
        assertThat(auRestoPhotoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAuRestoPhotos() throws Exception {
        // Initialize the database
        auRestoPhotoRepository.saveAndFlush(auRestoPhoto);

        // Get all the auRestoPhotoList
        restAuRestoPhotoMockMvc.perform(get("/api/au-resto-photos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoPhoto.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }

    @Test
    @Transactional
    public void getAuRestoPhoto() throws Exception {
        // Initialize the database
        auRestoPhotoRepository.saveAndFlush(auRestoPhoto);

        // Get the auRestoPhoto
        restAuRestoPhotoMockMvc.perform(get("/api/au-resto-photos/{id}", auRestoPhoto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(auRestoPhoto.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    public void getNonExistingAuRestoPhoto() throws Exception {
        // Get the auRestoPhoto
        restAuRestoPhotoMockMvc.perform(get("/api/au-resto-photos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuRestoPhoto() throws Exception {
        // Initialize the database
        auRestoPhotoRepository.saveAndFlush(auRestoPhoto);
        auRestoPhotoSearchRepository.save(auRestoPhoto);
        int databaseSizeBeforeUpdate = auRestoPhotoRepository.findAll().size();

        // Update the auRestoPhoto
        AuRestoPhoto updatedAuRestoPhoto = auRestoPhotoRepository.findOne(auRestoPhoto.getId());
        updatedAuRestoPhoto
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restAuRestoPhotoMockMvc.perform(put("/api/au-resto-photos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAuRestoPhoto)))
            .andExpect(status().isOk());

        // Validate the AuRestoPhoto in the database
        List<AuRestoPhoto> auRestoPhotoList = auRestoPhotoRepository.findAll();
        assertThat(auRestoPhotoList).hasSize(databaseSizeBeforeUpdate);
        AuRestoPhoto testAuRestoPhoto = auRestoPhotoList.get(auRestoPhotoList.size() - 1);
        assertThat(testAuRestoPhoto.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAuRestoPhoto.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAuRestoPhoto.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testAuRestoPhoto.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);

        // Validate the AuRestoPhoto in Elasticsearch
        AuRestoPhoto auRestoPhotoEs = auRestoPhotoSearchRepository.findOne(testAuRestoPhoto.getId());
        assertThat(auRestoPhotoEs).isEqualToComparingFieldByField(testAuRestoPhoto);
    }

    @Test
    @Transactional
    public void updateNonExistingAuRestoPhoto() throws Exception {
        int databaseSizeBeforeUpdate = auRestoPhotoRepository.findAll().size();

        // Create the AuRestoPhoto

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAuRestoPhotoMockMvc.perform(put("/api/au-resto-photos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoPhoto)))
            .andExpect(status().isCreated());

        // Validate the AuRestoPhoto in the database
        List<AuRestoPhoto> auRestoPhotoList = auRestoPhotoRepository.findAll();
        assertThat(auRestoPhotoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAuRestoPhoto() throws Exception {
        // Initialize the database
        auRestoPhotoRepository.saveAndFlush(auRestoPhoto);
        auRestoPhotoSearchRepository.save(auRestoPhoto);
        int databaseSizeBeforeDelete = auRestoPhotoRepository.findAll().size();

        // Get the auRestoPhoto
        restAuRestoPhotoMockMvc.perform(delete("/api/au-resto-photos/{id}", auRestoPhoto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean auRestoPhotoExistsInEs = auRestoPhotoSearchRepository.exists(auRestoPhoto.getId());
        assertThat(auRestoPhotoExistsInEs).isFalse();

        // Validate the database is empty
        List<AuRestoPhoto> auRestoPhotoList = auRestoPhotoRepository.findAll();
        assertThat(auRestoPhotoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAuRestoPhoto() throws Exception {
        // Initialize the database
        auRestoPhotoRepository.saveAndFlush(auRestoPhoto);
        auRestoPhotoSearchRepository.save(auRestoPhoto);

        // Search the auRestoPhoto
        restAuRestoPhotoMockMvc.perform(get("/api/_search/au-resto-photos?query=id:" + auRestoPhoto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoPhoto.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuRestoPhoto.class);
        AuRestoPhoto auRestoPhoto1 = new AuRestoPhoto();
        auRestoPhoto1.setId(1L);
        AuRestoPhoto auRestoPhoto2 = new AuRestoPhoto();
        auRestoPhoto2.setId(auRestoPhoto1.getId());
        assertThat(auRestoPhoto1).isEqualTo(auRestoPhoto2);
        auRestoPhoto2.setId(2L);
        assertThat(auRestoPhoto1).isNotEqualTo(auRestoPhoto2);
        auRestoPhoto1.setId(null);
        assertThat(auRestoPhoto1).isNotEqualTo(auRestoPhoto2);
    }
}
