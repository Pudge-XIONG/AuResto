package io.pudge.auresto.web.rest;

import io.pudge.auresto.AuRestoApp;

import io.pudge.auresto.domain.AuRestoProvince;
import io.pudge.auresto.repository.AuRestoProvinceRepository;
import io.pudge.auresto.repository.search.AuRestoProvinceSearchRepository;
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
 * Test class for the AuRestoProvinceResource REST controller.
 *
 * @see AuRestoProvinceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuRestoApp.class)
public class AuRestoProvinceResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private AuRestoProvinceRepository auRestoProvinceRepository;

    @Autowired
    private AuRestoProvinceSearchRepository auRestoProvinceSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAuRestoProvinceMockMvc;

    private AuRestoProvince auRestoProvince;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AuRestoProvinceResource auRestoProvinceResource = new AuRestoProvinceResource(auRestoProvinceRepository, auRestoProvinceSearchRepository);
        this.restAuRestoProvinceMockMvc = MockMvcBuilders.standaloneSetup(auRestoProvinceResource)
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
    public static AuRestoProvince createEntity(EntityManager em) {
        AuRestoProvince auRestoProvince = new AuRestoProvince()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE);
        return auRestoProvince;
    }

    @Before
    public void initTest() {
        auRestoProvinceSearchRepository.deleteAll();
        auRestoProvince = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuRestoProvince() throws Exception {
        int databaseSizeBeforeCreate = auRestoProvinceRepository.findAll().size();

        // Create the AuRestoProvince
        restAuRestoProvinceMockMvc.perform(post("/api/au-resto-provinces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoProvince)))
            .andExpect(status().isCreated());

        // Validate the AuRestoProvince in the database
        List<AuRestoProvince> auRestoProvinceList = auRestoProvinceRepository.findAll();
        assertThat(auRestoProvinceList).hasSize(databaseSizeBeforeCreate + 1);
        AuRestoProvince testAuRestoProvince = auRestoProvinceList.get(auRestoProvinceList.size() - 1);
        assertThat(testAuRestoProvince.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAuRestoProvince.getCode()).isEqualTo(DEFAULT_CODE);

        // Validate the AuRestoProvince in Elasticsearch
        AuRestoProvince auRestoProvinceEs = auRestoProvinceSearchRepository.findOne(testAuRestoProvince.getId());
        assertThat(auRestoProvinceEs).isEqualToComparingFieldByField(testAuRestoProvince);
    }

    @Test
    @Transactional
    public void createAuRestoProvinceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auRestoProvinceRepository.findAll().size();

        // Create the AuRestoProvince with an existing ID
        auRestoProvince.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuRestoProvinceMockMvc.perform(post("/api/au-resto-provinces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoProvince)))
            .andExpect(status().isBadRequest());

        // Validate the AuRestoProvince in the database
        List<AuRestoProvince> auRestoProvinceList = auRestoProvinceRepository.findAll();
        assertThat(auRestoProvinceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAuRestoProvinces() throws Exception {
        // Initialize the database
        auRestoProvinceRepository.saveAndFlush(auRestoProvince);

        // Get all the auRestoProvinceList
        restAuRestoProvinceMockMvc.perform(get("/api/au-resto-provinces?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoProvince.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    @Test
    @Transactional
    public void getAuRestoProvince() throws Exception {
        // Initialize the database
        auRestoProvinceRepository.saveAndFlush(auRestoProvince);

        // Get the auRestoProvince
        restAuRestoProvinceMockMvc.perform(get("/api/au-resto-provinces/{id}", auRestoProvince.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(auRestoProvince.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAuRestoProvince() throws Exception {
        // Get the auRestoProvince
        restAuRestoProvinceMockMvc.perform(get("/api/au-resto-provinces/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuRestoProvince() throws Exception {
        // Initialize the database
        auRestoProvinceRepository.saveAndFlush(auRestoProvince);
        auRestoProvinceSearchRepository.save(auRestoProvince);
        int databaseSizeBeforeUpdate = auRestoProvinceRepository.findAll().size();

        // Update the auRestoProvince
        AuRestoProvince updatedAuRestoProvince = auRestoProvinceRepository.findOne(auRestoProvince.getId());
        updatedAuRestoProvince
            .name(UPDATED_NAME)
            .code(UPDATED_CODE);

        restAuRestoProvinceMockMvc.perform(put("/api/au-resto-provinces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAuRestoProvince)))
            .andExpect(status().isOk());

        // Validate the AuRestoProvince in the database
        List<AuRestoProvince> auRestoProvinceList = auRestoProvinceRepository.findAll();
        assertThat(auRestoProvinceList).hasSize(databaseSizeBeforeUpdate);
        AuRestoProvince testAuRestoProvince = auRestoProvinceList.get(auRestoProvinceList.size() - 1);
        assertThat(testAuRestoProvince.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAuRestoProvince.getCode()).isEqualTo(UPDATED_CODE);

        // Validate the AuRestoProvince in Elasticsearch
        AuRestoProvince auRestoProvinceEs = auRestoProvinceSearchRepository.findOne(testAuRestoProvince.getId());
        assertThat(auRestoProvinceEs).isEqualToComparingFieldByField(testAuRestoProvince);
    }

    @Test
    @Transactional
    public void updateNonExistingAuRestoProvince() throws Exception {
        int databaseSizeBeforeUpdate = auRestoProvinceRepository.findAll().size();

        // Create the AuRestoProvince

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAuRestoProvinceMockMvc.perform(put("/api/au-resto-provinces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoProvince)))
            .andExpect(status().isCreated());

        // Validate the AuRestoProvince in the database
        List<AuRestoProvince> auRestoProvinceList = auRestoProvinceRepository.findAll();
        assertThat(auRestoProvinceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAuRestoProvince() throws Exception {
        // Initialize the database
        auRestoProvinceRepository.saveAndFlush(auRestoProvince);
        auRestoProvinceSearchRepository.save(auRestoProvince);
        int databaseSizeBeforeDelete = auRestoProvinceRepository.findAll().size();

        // Get the auRestoProvince
        restAuRestoProvinceMockMvc.perform(delete("/api/au-resto-provinces/{id}", auRestoProvince.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean auRestoProvinceExistsInEs = auRestoProvinceSearchRepository.exists(auRestoProvince.getId());
        assertThat(auRestoProvinceExistsInEs).isFalse();

        // Validate the database is empty
        List<AuRestoProvince> auRestoProvinceList = auRestoProvinceRepository.findAll();
        assertThat(auRestoProvinceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAuRestoProvince() throws Exception {
        // Initialize the database
        auRestoProvinceRepository.saveAndFlush(auRestoProvince);
        auRestoProvinceSearchRepository.save(auRestoProvince);

        // Search the auRestoProvince
        restAuRestoProvinceMockMvc.perform(get("/api/_search/au-resto-provinces?query=id:" + auRestoProvince.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoProvince.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuRestoProvince.class);
        AuRestoProvince auRestoProvince1 = new AuRestoProvince();
        auRestoProvince1.setId(1L);
        AuRestoProvince auRestoProvince2 = new AuRestoProvince();
        auRestoProvince2.setId(auRestoProvince1.getId());
        assertThat(auRestoProvince1).isEqualTo(auRestoProvince2);
        auRestoProvince2.setId(2L);
        assertThat(auRestoProvince1).isNotEqualTo(auRestoProvince2);
        auRestoProvince1.setId(null);
        assertThat(auRestoProvince1).isNotEqualTo(auRestoProvince2);
    }
}
