package io.pudge.auresto.web.rest;

import io.pudge.auresto.AuRestoApp;

import io.pudge.auresto.domain.AuRestoOrderType;
import io.pudge.auresto.repository.AuRestoOrderTypeRepository;
import io.pudge.auresto.repository.search.AuRestoOrderTypeSearchRepository;
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
 * Test class for the AuRestoOrderTypeResource REST controller.
 *
 * @see AuRestoOrderTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuRestoApp.class)
public class AuRestoOrderTypeResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private AuRestoOrderTypeRepository auRestoOrderTypeRepository;

    @Autowired
    private AuRestoOrderTypeSearchRepository auRestoOrderTypeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAuRestoOrderTypeMockMvc;

    private AuRestoOrderType auRestoOrderType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AuRestoOrderTypeResource auRestoOrderTypeResource = new AuRestoOrderTypeResource(auRestoOrderTypeRepository, auRestoOrderTypeSearchRepository);
        this.restAuRestoOrderTypeMockMvc = MockMvcBuilders.standaloneSetup(auRestoOrderTypeResource)
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
    public static AuRestoOrderType createEntity(EntityManager em) {
        AuRestoOrderType auRestoOrderType = new AuRestoOrderType()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME);
        return auRestoOrderType;
    }

    @Before
    public void initTest() {
        auRestoOrderTypeSearchRepository.deleteAll();
        auRestoOrderType = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuRestoOrderType() throws Exception {
        int databaseSizeBeforeCreate = auRestoOrderTypeRepository.findAll().size();

        // Create the AuRestoOrderType
        restAuRestoOrderTypeMockMvc.perform(post("/api/au-resto-order-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoOrderType)))
            .andExpect(status().isCreated());

        // Validate the AuRestoOrderType in the database
        List<AuRestoOrderType> auRestoOrderTypeList = auRestoOrderTypeRepository.findAll();
        assertThat(auRestoOrderTypeList).hasSize(databaseSizeBeforeCreate + 1);
        AuRestoOrderType testAuRestoOrderType = auRestoOrderTypeList.get(auRestoOrderTypeList.size() - 1);
        assertThat(testAuRestoOrderType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAuRestoOrderType.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the AuRestoOrderType in Elasticsearch
        AuRestoOrderType auRestoOrderTypeEs = auRestoOrderTypeSearchRepository.findOne(testAuRestoOrderType.getId());
        assertThat(auRestoOrderTypeEs).isEqualToComparingFieldByField(testAuRestoOrderType);
    }

    @Test
    @Transactional
    public void createAuRestoOrderTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auRestoOrderTypeRepository.findAll().size();

        // Create the AuRestoOrderType with an existing ID
        auRestoOrderType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuRestoOrderTypeMockMvc.perform(post("/api/au-resto-order-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoOrderType)))
            .andExpect(status().isBadRequest());

        // Validate the AuRestoOrderType in the database
        List<AuRestoOrderType> auRestoOrderTypeList = auRestoOrderTypeRepository.findAll();
        assertThat(auRestoOrderTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAuRestoOrderTypes() throws Exception {
        // Initialize the database
        auRestoOrderTypeRepository.saveAndFlush(auRestoOrderType);

        // Get all the auRestoOrderTypeList
        restAuRestoOrderTypeMockMvc.perform(get("/api/au-resto-order-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoOrderType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getAuRestoOrderType() throws Exception {
        // Initialize the database
        auRestoOrderTypeRepository.saveAndFlush(auRestoOrderType);

        // Get the auRestoOrderType
        restAuRestoOrderTypeMockMvc.perform(get("/api/au-resto-order-types/{id}", auRestoOrderType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(auRestoOrderType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAuRestoOrderType() throws Exception {
        // Get the auRestoOrderType
        restAuRestoOrderTypeMockMvc.perform(get("/api/au-resto-order-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuRestoOrderType() throws Exception {
        // Initialize the database
        auRestoOrderTypeRepository.saveAndFlush(auRestoOrderType);
        auRestoOrderTypeSearchRepository.save(auRestoOrderType);
        int databaseSizeBeforeUpdate = auRestoOrderTypeRepository.findAll().size();

        // Update the auRestoOrderType
        AuRestoOrderType updatedAuRestoOrderType = auRestoOrderTypeRepository.findOne(auRestoOrderType.getId());
        updatedAuRestoOrderType
            .code(UPDATED_CODE)
            .name(UPDATED_NAME);

        restAuRestoOrderTypeMockMvc.perform(put("/api/au-resto-order-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAuRestoOrderType)))
            .andExpect(status().isOk());

        // Validate the AuRestoOrderType in the database
        List<AuRestoOrderType> auRestoOrderTypeList = auRestoOrderTypeRepository.findAll();
        assertThat(auRestoOrderTypeList).hasSize(databaseSizeBeforeUpdate);
        AuRestoOrderType testAuRestoOrderType = auRestoOrderTypeList.get(auRestoOrderTypeList.size() - 1);
        assertThat(testAuRestoOrderType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAuRestoOrderType.getName()).isEqualTo(UPDATED_NAME);

        // Validate the AuRestoOrderType in Elasticsearch
        AuRestoOrderType auRestoOrderTypeEs = auRestoOrderTypeSearchRepository.findOne(testAuRestoOrderType.getId());
        assertThat(auRestoOrderTypeEs).isEqualToComparingFieldByField(testAuRestoOrderType);
    }

    @Test
    @Transactional
    public void updateNonExistingAuRestoOrderType() throws Exception {
        int databaseSizeBeforeUpdate = auRestoOrderTypeRepository.findAll().size();

        // Create the AuRestoOrderType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAuRestoOrderTypeMockMvc.perform(put("/api/au-resto-order-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoOrderType)))
            .andExpect(status().isCreated());

        // Validate the AuRestoOrderType in the database
        List<AuRestoOrderType> auRestoOrderTypeList = auRestoOrderTypeRepository.findAll();
        assertThat(auRestoOrderTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAuRestoOrderType() throws Exception {
        // Initialize the database
        auRestoOrderTypeRepository.saveAndFlush(auRestoOrderType);
        auRestoOrderTypeSearchRepository.save(auRestoOrderType);
        int databaseSizeBeforeDelete = auRestoOrderTypeRepository.findAll().size();

        // Get the auRestoOrderType
        restAuRestoOrderTypeMockMvc.perform(delete("/api/au-resto-order-types/{id}", auRestoOrderType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean auRestoOrderTypeExistsInEs = auRestoOrderTypeSearchRepository.exists(auRestoOrderType.getId());
        assertThat(auRestoOrderTypeExistsInEs).isFalse();

        // Validate the database is empty
        List<AuRestoOrderType> auRestoOrderTypeList = auRestoOrderTypeRepository.findAll();
        assertThat(auRestoOrderTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAuRestoOrderType() throws Exception {
        // Initialize the database
        auRestoOrderTypeRepository.saveAndFlush(auRestoOrderType);
        auRestoOrderTypeSearchRepository.save(auRestoOrderType);

        // Search the auRestoOrderType
        restAuRestoOrderTypeMockMvc.perform(get("/api/_search/au-resto-order-types?query=id:" + auRestoOrderType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoOrderType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuRestoOrderType.class);
        AuRestoOrderType auRestoOrderType1 = new AuRestoOrderType();
        auRestoOrderType1.setId(1L);
        AuRestoOrderType auRestoOrderType2 = new AuRestoOrderType();
        auRestoOrderType2.setId(auRestoOrderType1.getId());
        assertThat(auRestoOrderType1).isEqualTo(auRestoOrderType2);
        auRestoOrderType2.setId(2L);
        assertThat(auRestoOrderType1).isNotEqualTo(auRestoOrderType2);
        auRestoOrderType1.setId(null);
        assertThat(auRestoOrderType1).isNotEqualTo(auRestoOrderType2);
    }
}
