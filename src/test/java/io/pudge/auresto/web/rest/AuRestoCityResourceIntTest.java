package io.pudge.auresto.web.rest;

import io.pudge.auresto.AuRestoApp;

import io.pudge.auresto.domain.AuRestoCity;
import io.pudge.auresto.repository.AuRestoCityRepository;
import io.pudge.auresto.repository.search.AuRestoCitySearchRepository;
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

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AuRestoCityResource REST controller.
 *
 * @see AuRestoCityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuRestoApp.class)
public class AuRestoCityResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_POST_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POST_CODE = "BBBBBBBBBB";

    @Autowired
    private AuRestoCityRepository auRestoCityRepository;

    @Autowired
    private AuRestoCitySearchRepository auRestoCitySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAuRestoCityMockMvc;

    private AuRestoCity auRestoCity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AuRestoCityResource auRestoCityResource = new AuRestoCityResource(auRestoCityRepository, auRestoCitySearchRepository);
        this.restAuRestoCityMockMvc = MockMvcBuilders.standaloneSetup(auRestoCityResource)
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
    public static AuRestoCity createEntity(EntityManager em) {
        AuRestoCity auRestoCity = new AuRestoCity()
            .name(DEFAULT_NAME)
            .postCode(DEFAULT_POST_CODE);
        return auRestoCity;
    }

    @Before
    public void initTest() {
        auRestoCitySearchRepository.deleteAll();
        auRestoCity = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuRestoCity() throws Exception {
        int databaseSizeBeforeCreate = auRestoCityRepository.findAll().size();

        // Create the AuRestoCity
        restAuRestoCityMockMvc.perform(post("/api/au-resto-cities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoCity)))
            .andExpect(status().isCreated());

        // Validate the AuRestoCity in the database
        List<AuRestoCity> auRestoCityList = auRestoCityRepository.findAll();
        assertThat(auRestoCityList).hasSize(databaseSizeBeforeCreate + 1);
        AuRestoCity testAuRestoCity = auRestoCityList.get(auRestoCityList.size() - 1);
        assertThat(testAuRestoCity.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAuRestoCity.getPostCode()).isEqualTo(DEFAULT_POST_CODE);

        // Validate the AuRestoCity in Elasticsearch
        AuRestoCity auRestoCityEs = auRestoCitySearchRepository.findOne(testAuRestoCity.getId());
        assertThat(auRestoCityEs).isEqualToComparingFieldByField(testAuRestoCity);
    }

    @Test
    @Transactional
    public void createAuRestoCityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auRestoCityRepository.findAll().size();

        // Create the AuRestoCity with an existing ID
        auRestoCity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuRestoCityMockMvc.perform(post("/api/au-resto-cities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoCity)))
            .andExpect(status().isBadRequest());

        // Validate the AuRestoCity in the database
        List<AuRestoCity> auRestoCityList = auRestoCityRepository.findAll();
        assertThat(auRestoCityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAuRestoCities() throws Exception {
        // Initialize the database
        auRestoCityRepository.saveAndFlush(auRestoCity);

        // Get all the auRestoCityList
        restAuRestoCityMockMvc.perform(get("/api/au-resto-cities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoCity.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].postCode").value(hasItem(DEFAULT_POST_CODE.toString())));
    }

    @Test
    @Transactional
    public void getAuRestoCity() throws Exception {
        // Initialize the database
        auRestoCityRepository.saveAndFlush(auRestoCity);

        // Get the auRestoCity
        restAuRestoCityMockMvc.perform(get("/api/au-resto-cities/{id}", auRestoCity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(auRestoCity.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.postCode").value(DEFAULT_POST_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAuRestoCity() throws Exception {
        // Get the auRestoCity
        restAuRestoCityMockMvc.perform(get("/api/au-resto-cities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuRestoCity() throws Exception {
        // Initialize the database
        auRestoCityRepository.saveAndFlush(auRestoCity);
        auRestoCitySearchRepository.save(auRestoCity);
        int databaseSizeBeforeUpdate = auRestoCityRepository.findAll().size();

        // Update the auRestoCity
        AuRestoCity updatedAuRestoCity = auRestoCityRepository.findOne(auRestoCity.getId());
        updatedAuRestoCity
            .name(UPDATED_NAME)
            .postCode(UPDATED_POST_CODE);

        restAuRestoCityMockMvc.perform(put("/api/au-resto-cities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAuRestoCity)))
            .andExpect(status().isOk());

        // Validate the AuRestoCity in the database
        List<AuRestoCity> auRestoCityList = auRestoCityRepository.findAll();
        assertThat(auRestoCityList).hasSize(databaseSizeBeforeUpdate);
        AuRestoCity testAuRestoCity = auRestoCityList.get(auRestoCityList.size() - 1);
        assertThat(testAuRestoCity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAuRestoCity.getPostCode()).isEqualTo(UPDATED_POST_CODE);

        // Validate the AuRestoCity in Elasticsearch
        AuRestoCity auRestoCityEs = auRestoCitySearchRepository.findOne(testAuRestoCity.getId());
        assertThat(auRestoCityEs).isEqualToComparingFieldByField(testAuRestoCity);
    }

    @Test
    @Transactional
    public void updateNonExistingAuRestoCity() throws Exception {
        int databaseSizeBeforeUpdate = auRestoCityRepository.findAll().size();

        // Create the AuRestoCity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAuRestoCityMockMvc.perform(put("/api/au-resto-cities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoCity)))
            .andExpect(status().isCreated());

        // Validate the AuRestoCity in the database
        List<AuRestoCity> auRestoCityList = auRestoCityRepository.findAll();
        assertThat(auRestoCityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAuRestoCity() throws Exception {
        // Initialize the database
        auRestoCityRepository.saveAndFlush(auRestoCity);
        auRestoCitySearchRepository.save(auRestoCity);
        int databaseSizeBeforeDelete = auRestoCityRepository.findAll().size();

        // Get the auRestoCity
        restAuRestoCityMockMvc.perform(delete("/api/au-resto-cities/{id}", auRestoCity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean auRestoCityExistsInEs = auRestoCitySearchRepository.exists(auRestoCity.getId());
        assertThat(auRestoCityExistsInEs).isFalse();

        // Validate the database is empty
        List<AuRestoCity> auRestoCityList = auRestoCityRepository.findAll();
        assertThat(auRestoCityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAuRestoCity() throws Exception {
        // Initialize the database
        auRestoCityRepository.saveAndFlush(auRestoCity);
        auRestoCitySearchRepository.save(auRestoCity);

        // Search the auRestoCity
        restAuRestoCityMockMvc.perform(get("/api/_search/au-resto-cities?query=id:" + auRestoCity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoCity.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].postCode").value(hasItem(DEFAULT_POST_CODE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuRestoCity.class);
        AuRestoCity auRestoCity1 = new AuRestoCity();
        auRestoCity1.setId(1L);
        AuRestoCity auRestoCity2 = new AuRestoCity();
        auRestoCity2.setId(auRestoCity1.getId());
        assertThat(auRestoCity1).isEqualTo(auRestoCity2);
        auRestoCity2.setId(2L);
        assertThat(auRestoCity1).isNotEqualTo(auRestoCity2);
        auRestoCity1.setId(null);
        assertThat(auRestoCity1).isNotEqualTo(auRestoCity2);
    }
}
