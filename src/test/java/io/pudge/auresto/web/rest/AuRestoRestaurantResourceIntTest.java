package io.pudge.auresto.web.rest;

import io.pudge.auresto.AuRestoApp;

import io.pudge.auresto.domain.AuRestoRestaurant;
import io.pudge.auresto.repository.AuRestoRestaurantRepository;
import io.pudge.auresto.repository.search.AuRestoRestaurantSearchRepository;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static io.pudge.auresto.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AuRestoRestaurantResource REST controller.
 *
 * @see AuRestoRestaurantResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuRestoApp.class)
public class AuRestoRestaurantResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_OPEN_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_OPEN_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_CLOSE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CLOSE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private AuRestoRestaurantRepository auRestoRestaurantRepository;

    @Autowired
    private AuRestoRestaurantSearchRepository auRestoRestaurantSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAuRestoRestaurantMockMvc;

    private AuRestoRestaurant auRestoRestaurant;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AuRestoRestaurantResource auRestoRestaurantResource = new AuRestoRestaurantResource(auRestoRestaurantRepository, auRestoRestaurantSearchRepository);
        this.restAuRestoRestaurantMockMvc = MockMvcBuilders.standaloneSetup(auRestoRestaurantResource)
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
    public static AuRestoRestaurant createEntity(EntityManager em) {
        AuRestoRestaurant auRestoRestaurant = new AuRestoRestaurant()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .openTime(DEFAULT_OPEN_TIME)
            .closeTime(DEFAULT_CLOSE_TIME);
        return auRestoRestaurant;
    }

    @Before
    public void initTest() {
        auRestoRestaurantSearchRepository.deleteAll();
        auRestoRestaurant = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuRestoRestaurant() throws Exception {
        int databaseSizeBeforeCreate = auRestoRestaurantRepository.findAll().size();

        // Create the AuRestoRestaurant
        restAuRestoRestaurantMockMvc.perform(post("/api/au-resto-restaurants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoRestaurant)))
            .andExpect(status().isCreated());

        // Validate the AuRestoRestaurant in the database
        List<AuRestoRestaurant> auRestoRestaurantList = auRestoRestaurantRepository.findAll();
        assertThat(auRestoRestaurantList).hasSize(databaseSizeBeforeCreate + 1);
        AuRestoRestaurant testAuRestoRestaurant = auRestoRestaurantList.get(auRestoRestaurantList.size() - 1);
        assertThat(testAuRestoRestaurant.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAuRestoRestaurant.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAuRestoRestaurant.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAuRestoRestaurant.getOpenTime()).isEqualTo(DEFAULT_OPEN_TIME);
        assertThat(testAuRestoRestaurant.getCloseTime()).isEqualTo(DEFAULT_CLOSE_TIME);

        // Validate the AuRestoRestaurant in Elasticsearch
        AuRestoRestaurant auRestoRestaurantEs = auRestoRestaurantSearchRepository.findOne(testAuRestoRestaurant.getId());
        assertThat(auRestoRestaurantEs).isEqualToComparingFieldByField(testAuRestoRestaurant);
    }

    @Test
    @Transactional
    public void createAuRestoRestaurantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auRestoRestaurantRepository.findAll().size();

        // Create the AuRestoRestaurant with an existing ID
        auRestoRestaurant.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuRestoRestaurantMockMvc.perform(post("/api/au-resto-restaurants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoRestaurant)))
            .andExpect(status().isBadRequest());

        // Validate the AuRestoRestaurant in the database
        List<AuRestoRestaurant> auRestoRestaurantList = auRestoRestaurantRepository.findAll();
        assertThat(auRestoRestaurantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAuRestoRestaurants() throws Exception {
        // Initialize the database
        auRestoRestaurantRepository.saveAndFlush(auRestoRestaurant);

        // Get all the auRestoRestaurantList
        restAuRestoRestaurantMockMvc.perform(get("/api/au-resto-restaurants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoRestaurant.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].openTime").value(hasItem(sameInstant(DEFAULT_OPEN_TIME))))
            .andExpect(jsonPath("$.[*].closeTime").value(hasItem(sameInstant(DEFAULT_CLOSE_TIME))));
    }

    @Test
    @Transactional
    public void getAuRestoRestaurant() throws Exception {
        // Initialize the database
        auRestoRestaurantRepository.saveAndFlush(auRestoRestaurant);

        // Get the auRestoRestaurant
        restAuRestoRestaurantMockMvc.perform(get("/api/au-resto-restaurants/{id}", auRestoRestaurant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(auRestoRestaurant.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.openTime").value(sameInstant(DEFAULT_OPEN_TIME)))
            .andExpect(jsonPath("$.closeTime").value(sameInstant(DEFAULT_CLOSE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingAuRestoRestaurant() throws Exception {
        // Get the auRestoRestaurant
        restAuRestoRestaurantMockMvc.perform(get("/api/au-resto-restaurants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuRestoRestaurant() throws Exception {
        // Initialize the database
        auRestoRestaurantRepository.saveAndFlush(auRestoRestaurant);
        auRestoRestaurantSearchRepository.save(auRestoRestaurant);
        int databaseSizeBeforeUpdate = auRestoRestaurantRepository.findAll().size();

        // Update the auRestoRestaurant
        AuRestoRestaurant updatedAuRestoRestaurant = auRestoRestaurantRepository.findOne(auRestoRestaurant.getId());
        updatedAuRestoRestaurant
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .openTime(UPDATED_OPEN_TIME)
            .closeTime(UPDATED_CLOSE_TIME);

        restAuRestoRestaurantMockMvc.perform(put("/api/au-resto-restaurants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAuRestoRestaurant)))
            .andExpect(status().isOk());

        // Validate the AuRestoRestaurant in the database
        List<AuRestoRestaurant> auRestoRestaurantList = auRestoRestaurantRepository.findAll();
        assertThat(auRestoRestaurantList).hasSize(databaseSizeBeforeUpdate);
        AuRestoRestaurant testAuRestoRestaurant = auRestoRestaurantList.get(auRestoRestaurantList.size() - 1);
        assertThat(testAuRestoRestaurant.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAuRestoRestaurant.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAuRestoRestaurant.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAuRestoRestaurant.getOpenTime()).isEqualTo(UPDATED_OPEN_TIME);
        assertThat(testAuRestoRestaurant.getCloseTime()).isEqualTo(UPDATED_CLOSE_TIME);

        // Validate the AuRestoRestaurant in Elasticsearch
        AuRestoRestaurant auRestoRestaurantEs = auRestoRestaurantSearchRepository.findOne(testAuRestoRestaurant.getId());
        assertThat(auRestoRestaurantEs).isEqualToComparingFieldByField(testAuRestoRestaurant);
    }

    @Test
    @Transactional
    public void updateNonExistingAuRestoRestaurant() throws Exception {
        int databaseSizeBeforeUpdate = auRestoRestaurantRepository.findAll().size();

        // Create the AuRestoRestaurant

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAuRestoRestaurantMockMvc.perform(put("/api/au-resto-restaurants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoRestaurant)))
            .andExpect(status().isCreated());

        // Validate the AuRestoRestaurant in the database
        List<AuRestoRestaurant> auRestoRestaurantList = auRestoRestaurantRepository.findAll();
        assertThat(auRestoRestaurantList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAuRestoRestaurant() throws Exception {
        // Initialize the database
        auRestoRestaurantRepository.saveAndFlush(auRestoRestaurant);
        auRestoRestaurantSearchRepository.save(auRestoRestaurant);
        int databaseSizeBeforeDelete = auRestoRestaurantRepository.findAll().size();

        // Get the auRestoRestaurant
        restAuRestoRestaurantMockMvc.perform(delete("/api/au-resto-restaurants/{id}", auRestoRestaurant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean auRestoRestaurantExistsInEs = auRestoRestaurantSearchRepository.exists(auRestoRestaurant.getId());
        assertThat(auRestoRestaurantExistsInEs).isFalse();

        // Validate the database is empty
        List<AuRestoRestaurant> auRestoRestaurantList = auRestoRestaurantRepository.findAll();
        assertThat(auRestoRestaurantList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAuRestoRestaurant() throws Exception {
        // Initialize the database
        auRestoRestaurantRepository.saveAndFlush(auRestoRestaurant);
        auRestoRestaurantSearchRepository.save(auRestoRestaurant);

        // Search the auRestoRestaurant
        restAuRestoRestaurantMockMvc.perform(get("/api/_search/au-resto-restaurants?query=id:" + auRestoRestaurant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoRestaurant.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].openTime").value(hasItem(sameInstant(DEFAULT_OPEN_TIME))))
            .andExpect(jsonPath("$.[*].closeTime").value(hasItem(sameInstant(DEFAULT_CLOSE_TIME))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuRestoRestaurant.class);
        AuRestoRestaurant auRestoRestaurant1 = new AuRestoRestaurant();
        auRestoRestaurant1.setId(1L);
        AuRestoRestaurant auRestoRestaurant2 = new AuRestoRestaurant();
        auRestoRestaurant2.setId(auRestoRestaurant1.getId());
        assertThat(auRestoRestaurant1).isEqualTo(auRestoRestaurant2);
        auRestoRestaurant2.setId(2L);
        assertThat(auRestoRestaurant1).isNotEqualTo(auRestoRestaurant2);
        auRestoRestaurant1.setId(null);
        assertThat(auRestoRestaurant1).isNotEqualTo(auRestoRestaurant2);
    }
}
