package io.pudge.auresto.web.rest;

import io.pudge.auresto.AuRestoApp;

import io.pudge.auresto.domain.AuRestoCountry;
import io.pudge.auresto.repository.AuRestoCountryRepository;
import io.pudge.auresto.repository.search.AuRestoCountrySearchRepository;
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
 * Test class for the AuRestoCountryResource REST controller.
 *
 * @see AuRestoCountryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuRestoApp.class)
public class AuRestoCountryResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TEL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TEL_CODE = "BBBBBBBBBB";

    @Autowired
    private AuRestoCountryRepository auRestoCountryRepository;

    @Autowired
    private AuRestoCountrySearchRepository auRestoCountrySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAuRestoCountryMockMvc;

    private AuRestoCountry auRestoCountry;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AuRestoCountryResource auRestoCountryResource = new AuRestoCountryResource(auRestoCountryRepository, auRestoCountrySearchRepository);
        this.restAuRestoCountryMockMvc = MockMvcBuilders.standaloneSetup(auRestoCountryResource)
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
    public static AuRestoCountry createEntity(EntityManager em) {
        AuRestoCountry auRestoCountry = new AuRestoCountry()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .telCode(DEFAULT_TEL_CODE);
        return auRestoCountry;
    }

    @Before
    public void initTest() {
        auRestoCountrySearchRepository.deleteAll();
        auRestoCountry = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuRestoCountry() throws Exception {
        int databaseSizeBeforeCreate = auRestoCountryRepository.findAll().size();

        // Create the AuRestoCountry
        restAuRestoCountryMockMvc.perform(post("/api/au-resto-countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoCountry)))
            .andExpect(status().isCreated());

        // Validate the AuRestoCountry in the database
        List<AuRestoCountry> auRestoCountryList = auRestoCountryRepository.findAll();
        assertThat(auRestoCountryList).hasSize(databaseSizeBeforeCreate + 1);
        AuRestoCountry testAuRestoCountry = auRestoCountryList.get(auRestoCountryList.size() - 1);
        assertThat(testAuRestoCountry.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAuRestoCountry.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAuRestoCountry.getTelCode()).isEqualTo(DEFAULT_TEL_CODE);

        // Validate the AuRestoCountry in Elasticsearch
        AuRestoCountry auRestoCountryEs = auRestoCountrySearchRepository.findOne(testAuRestoCountry.getId());
        assertThat(auRestoCountryEs).isEqualToComparingFieldByField(testAuRestoCountry);
    }

    @Test
    @Transactional
    public void createAuRestoCountryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auRestoCountryRepository.findAll().size();

        // Create the AuRestoCountry with an existing ID
        auRestoCountry.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuRestoCountryMockMvc.perform(post("/api/au-resto-countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoCountry)))
            .andExpect(status().isBadRequest());

        // Validate the AuRestoCountry in the database
        List<AuRestoCountry> auRestoCountryList = auRestoCountryRepository.findAll();
        assertThat(auRestoCountryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAuRestoCountries() throws Exception {
        // Initialize the database
        auRestoCountryRepository.saveAndFlush(auRestoCountry);

        // Get all the auRestoCountryList
        restAuRestoCountryMockMvc.perform(get("/api/au-resto-countries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoCountry.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].telCode").value(hasItem(DEFAULT_TEL_CODE.toString())));
    }

    @Test
    @Transactional
    public void getAuRestoCountry() throws Exception {
        // Initialize the database
        auRestoCountryRepository.saveAndFlush(auRestoCountry);

        // Get the auRestoCountry
        restAuRestoCountryMockMvc.perform(get("/api/au-resto-countries/{id}", auRestoCountry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(auRestoCountry.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.telCode").value(DEFAULT_TEL_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAuRestoCountry() throws Exception {
        // Get the auRestoCountry
        restAuRestoCountryMockMvc.perform(get("/api/au-resto-countries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuRestoCountry() throws Exception {
        // Initialize the database
        auRestoCountryRepository.saveAndFlush(auRestoCountry);
        auRestoCountrySearchRepository.save(auRestoCountry);
        int databaseSizeBeforeUpdate = auRestoCountryRepository.findAll().size();

        // Update the auRestoCountry
        AuRestoCountry updatedAuRestoCountry = auRestoCountryRepository.findOne(auRestoCountry.getId());
        updatedAuRestoCountry
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .telCode(UPDATED_TEL_CODE);

        restAuRestoCountryMockMvc.perform(put("/api/au-resto-countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAuRestoCountry)))
            .andExpect(status().isOk());

        // Validate the AuRestoCountry in the database
        List<AuRestoCountry> auRestoCountryList = auRestoCountryRepository.findAll();
        assertThat(auRestoCountryList).hasSize(databaseSizeBeforeUpdate);
        AuRestoCountry testAuRestoCountry = auRestoCountryList.get(auRestoCountryList.size() - 1);
        assertThat(testAuRestoCountry.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAuRestoCountry.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAuRestoCountry.getTelCode()).isEqualTo(UPDATED_TEL_CODE);

        // Validate the AuRestoCountry in Elasticsearch
        AuRestoCountry auRestoCountryEs = auRestoCountrySearchRepository.findOne(testAuRestoCountry.getId());
        assertThat(auRestoCountryEs).isEqualToComparingFieldByField(testAuRestoCountry);
    }

    @Test
    @Transactional
    public void updateNonExistingAuRestoCountry() throws Exception {
        int databaseSizeBeforeUpdate = auRestoCountryRepository.findAll().size();

        // Create the AuRestoCountry

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAuRestoCountryMockMvc.perform(put("/api/au-resto-countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoCountry)))
            .andExpect(status().isCreated());

        // Validate the AuRestoCountry in the database
        List<AuRestoCountry> auRestoCountryList = auRestoCountryRepository.findAll();
        assertThat(auRestoCountryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAuRestoCountry() throws Exception {
        // Initialize the database
        auRestoCountryRepository.saveAndFlush(auRestoCountry);
        auRestoCountrySearchRepository.save(auRestoCountry);
        int databaseSizeBeforeDelete = auRestoCountryRepository.findAll().size();

        // Get the auRestoCountry
        restAuRestoCountryMockMvc.perform(delete("/api/au-resto-countries/{id}", auRestoCountry.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean auRestoCountryExistsInEs = auRestoCountrySearchRepository.exists(auRestoCountry.getId());
        assertThat(auRestoCountryExistsInEs).isFalse();

        // Validate the database is empty
        List<AuRestoCountry> auRestoCountryList = auRestoCountryRepository.findAll();
        assertThat(auRestoCountryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAuRestoCountry() throws Exception {
        // Initialize the database
        auRestoCountryRepository.saveAndFlush(auRestoCountry);
        auRestoCountrySearchRepository.save(auRestoCountry);

        // Search the auRestoCountry
        restAuRestoCountryMockMvc.perform(get("/api/_search/au-resto-countries?query=id:" + auRestoCountry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoCountry.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].telCode").value(hasItem(DEFAULT_TEL_CODE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuRestoCountry.class);
        AuRestoCountry auRestoCountry1 = new AuRestoCountry();
        auRestoCountry1.setId(1L);
        AuRestoCountry auRestoCountry2 = new AuRestoCountry();
        auRestoCountry2.setId(auRestoCountry1.getId());
        assertThat(auRestoCountry1).isEqualTo(auRestoCountry2);
        auRestoCountry2.setId(2L);
        assertThat(auRestoCountry1).isNotEqualTo(auRestoCountry2);
        auRestoCountry1.setId(null);
        assertThat(auRestoCountry1).isNotEqualTo(auRestoCountry2);
    }
}
