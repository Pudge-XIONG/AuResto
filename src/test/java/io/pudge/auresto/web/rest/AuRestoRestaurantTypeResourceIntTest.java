package io.pudge.auresto.web.rest;

import io.pudge.auresto.AuRestoApp;

import io.pudge.auresto.domain.AuRestoRestaurantType;
import io.pudge.auresto.repository.AuRestoRestaurantTypeRepository;
import io.pudge.auresto.repository.search.AuRestoRestaurantTypeSearchRepository;
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
 * Test class for the AuRestoRestaurantTypeResource REST controller.
 *
 * @see AuRestoRestaurantTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuRestoApp.class)
public class AuRestoRestaurantTypeResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private AuRestoRestaurantTypeRepository auRestoRestaurantTypeRepository;

    @Autowired
    private AuRestoRestaurantTypeSearchRepository auRestoRestaurantTypeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAuRestoRestaurantTypeMockMvc;

    private AuRestoRestaurantType auRestoRestaurantType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AuRestoRestaurantTypeResource auRestoRestaurantTypeResource = new AuRestoRestaurantTypeResource(auRestoRestaurantTypeRepository, auRestoRestaurantTypeSearchRepository);
        this.restAuRestoRestaurantTypeMockMvc = MockMvcBuilders.standaloneSetup(auRestoRestaurantTypeResource)
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
    public static AuRestoRestaurantType createEntity(EntityManager em) {
        AuRestoRestaurantType auRestoRestaurantType = new AuRestoRestaurantType()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME);
        return auRestoRestaurantType;
    }

    @Before
    public void initTest() {
        auRestoRestaurantTypeSearchRepository.deleteAll();
        auRestoRestaurantType = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuRestoRestaurantType() throws Exception {
        int databaseSizeBeforeCreate = auRestoRestaurantTypeRepository.findAll().size();

        // Create the AuRestoRestaurantType
        restAuRestoRestaurantTypeMockMvc.perform(post("/api/au-resto-restaurant-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoRestaurantType)))
            .andExpect(status().isCreated());

        // Validate the AuRestoRestaurantType in the database
        List<AuRestoRestaurantType> auRestoRestaurantTypeList = auRestoRestaurantTypeRepository.findAll();
        assertThat(auRestoRestaurantTypeList).hasSize(databaseSizeBeforeCreate + 1);
        AuRestoRestaurantType testAuRestoRestaurantType = auRestoRestaurantTypeList.get(auRestoRestaurantTypeList.size() - 1);
        assertThat(testAuRestoRestaurantType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAuRestoRestaurantType.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the AuRestoRestaurantType in Elasticsearch
        AuRestoRestaurantType auRestoRestaurantTypeEs = auRestoRestaurantTypeSearchRepository.findOne(testAuRestoRestaurantType.getId());
        assertThat(auRestoRestaurantTypeEs).isEqualToComparingFieldByField(testAuRestoRestaurantType);
    }

    @Test
    @Transactional
    public void createAuRestoRestaurantTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auRestoRestaurantTypeRepository.findAll().size();

        // Create the AuRestoRestaurantType with an existing ID
        auRestoRestaurantType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuRestoRestaurantTypeMockMvc.perform(post("/api/au-resto-restaurant-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoRestaurantType)))
            .andExpect(status().isBadRequest());

        // Validate the AuRestoRestaurantType in the database
        List<AuRestoRestaurantType> auRestoRestaurantTypeList = auRestoRestaurantTypeRepository.findAll();
        assertThat(auRestoRestaurantTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAuRestoRestaurantTypes() throws Exception {
        // Initialize the database
        auRestoRestaurantTypeRepository.saveAndFlush(auRestoRestaurantType);

        // Get all the auRestoRestaurantTypeList
        restAuRestoRestaurantTypeMockMvc.perform(get("/api/au-resto-restaurant-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoRestaurantType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getAuRestoRestaurantType() throws Exception {
        // Initialize the database
        auRestoRestaurantTypeRepository.saveAndFlush(auRestoRestaurantType);

        // Get the auRestoRestaurantType
        restAuRestoRestaurantTypeMockMvc.perform(get("/api/au-resto-restaurant-types/{id}", auRestoRestaurantType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(auRestoRestaurantType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAuRestoRestaurantType() throws Exception {
        // Get the auRestoRestaurantType
        restAuRestoRestaurantTypeMockMvc.perform(get("/api/au-resto-restaurant-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuRestoRestaurantType() throws Exception {
        // Initialize the database
        auRestoRestaurantTypeRepository.saveAndFlush(auRestoRestaurantType);
        auRestoRestaurantTypeSearchRepository.save(auRestoRestaurantType);
        int databaseSizeBeforeUpdate = auRestoRestaurantTypeRepository.findAll().size();

        // Update the auRestoRestaurantType
        AuRestoRestaurantType updatedAuRestoRestaurantType = auRestoRestaurantTypeRepository.findOne(auRestoRestaurantType.getId());
        updatedAuRestoRestaurantType
            .code(UPDATED_CODE)
            .name(UPDATED_NAME);

        restAuRestoRestaurantTypeMockMvc.perform(put("/api/au-resto-restaurant-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAuRestoRestaurantType)))
            .andExpect(status().isOk());

        // Validate the AuRestoRestaurantType in the database
        List<AuRestoRestaurantType> auRestoRestaurantTypeList = auRestoRestaurantTypeRepository.findAll();
        assertThat(auRestoRestaurantTypeList).hasSize(databaseSizeBeforeUpdate);
        AuRestoRestaurantType testAuRestoRestaurantType = auRestoRestaurantTypeList.get(auRestoRestaurantTypeList.size() - 1);
        assertThat(testAuRestoRestaurantType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAuRestoRestaurantType.getName()).isEqualTo(UPDATED_NAME);

        // Validate the AuRestoRestaurantType in Elasticsearch
        AuRestoRestaurantType auRestoRestaurantTypeEs = auRestoRestaurantTypeSearchRepository.findOne(testAuRestoRestaurantType.getId());
        assertThat(auRestoRestaurantTypeEs).isEqualToComparingFieldByField(testAuRestoRestaurantType);
    }

    @Test
    @Transactional
    public void updateNonExistingAuRestoRestaurantType() throws Exception {
        int databaseSizeBeforeUpdate = auRestoRestaurantTypeRepository.findAll().size();

        // Create the AuRestoRestaurantType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAuRestoRestaurantTypeMockMvc.perform(put("/api/au-resto-restaurant-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoRestaurantType)))
            .andExpect(status().isCreated());

        // Validate the AuRestoRestaurantType in the database
        List<AuRestoRestaurantType> auRestoRestaurantTypeList = auRestoRestaurantTypeRepository.findAll();
        assertThat(auRestoRestaurantTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAuRestoRestaurantType() throws Exception {
        // Initialize the database
        auRestoRestaurantTypeRepository.saveAndFlush(auRestoRestaurantType);
        auRestoRestaurantTypeSearchRepository.save(auRestoRestaurantType);
        int databaseSizeBeforeDelete = auRestoRestaurantTypeRepository.findAll().size();

        // Get the auRestoRestaurantType
        restAuRestoRestaurantTypeMockMvc.perform(delete("/api/au-resto-restaurant-types/{id}", auRestoRestaurantType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean auRestoRestaurantTypeExistsInEs = auRestoRestaurantTypeSearchRepository.exists(auRestoRestaurantType.getId());
        assertThat(auRestoRestaurantTypeExistsInEs).isFalse();

        // Validate the database is empty
        List<AuRestoRestaurantType> auRestoRestaurantTypeList = auRestoRestaurantTypeRepository.findAll();
        assertThat(auRestoRestaurantTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAuRestoRestaurantType() throws Exception {
        // Initialize the database
        auRestoRestaurantTypeRepository.saveAndFlush(auRestoRestaurantType);
        auRestoRestaurantTypeSearchRepository.save(auRestoRestaurantType);

        // Search the auRestoRestaurantType
        restAuRestoRestaurantTypeMockMvc.perform(get("/api/_search/au-resto-restaurant-types?query=id:" + auRestoRestaurantType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoRestaurantType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuRestoRestaurantType.class);
        AuRestoRestaurantType auRestoRestaurantType1 = new AuRestoRestaurantType();
        auRestoRestaurantType1.setId(1L);
        AuRestoRestaurantType auRestoRestaurantType2 = new AuRestoRestaurantType();
        auRestoRestaurantType2.setId(auRestoRestaurantType1.getId());
        assertThat(auRestoRestaurantType1).isEqualTo(auRestoRestaurantType2);
        auRestoRestaurantType2.setId(2L);
        assertThat(auRestoRestaurantType1).isNotEqualTo(auRestoRestaurantType2);
        auRestoRestaurantType1.setId(null);
        assertThat(auRestoRestaurantType1).isNotEqualTo(auRestoRestaurantType2);
    }
}
