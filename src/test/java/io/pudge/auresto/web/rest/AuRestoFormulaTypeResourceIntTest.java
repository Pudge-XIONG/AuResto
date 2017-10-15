package io.pudge.auresto.web.rest;

import io.pudge.auresto.AuRestoApp;

import io.pudge.auresto.domain.AuRestoFormulaType;
import io.pudge.auresto.repository.AuRestoFormulaTypeRepository;
import io.pudge.auresto.repository.search.AuRestoFormulaTypeSearchRepository;
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
 * Test class for the AuRestoFormulaTypeResource REST controller.
 *
 * @see AuRestoFormulaTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuRestoApp.class)
public class AuRestoFormulaTypeResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private AuRestoFormulaTypeRepository auRestoFormulaTypeRepository;

    @Autowired
    private AuRestoFormulaTypeSearchRepository auRestoFormulaTypeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAuRestoFormulaTypeMockMvc;

    private AuRestoFormulaType auRestoFormulaType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AuRestoFormulaTypeResource auRestoFormulaTypeResource = new AuRestoFormulaTypeResource(auRestoFormulaTypeRepository, auRestoFormulaTypeSearchRepository);
        this.restAuRestoFormulaTypeMockMvc = MockMvcBuilders.standaloneSetup(auRestoFormulaTypeResource)
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
    public static AuRestoFormulaType createEntity(EntityManager em) {
        AuRestoFormulaType auRestoFormulaType = new AuRestoFormulaType()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME);
        return auRestoFormulaType;
    }

    @Before
    public void initTest() {
        auRestoFormulaTypeSearchRepository.deleteAll();
        auRestoFormulaType = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuRestoFormulaType() throws Exception {
        int databaseSizeBeforeCreate = auRestoFormulaTypeRepository.findAll().size();

        // Create the AuRestoFormulaType
        restAuRestoFormulaTypeMockMvc.perform(post("/api/au-resto-formula-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoFormulaType)))
            .andExpect(status().isCreated());

        // Validate the AuRestoFormulaType in the database
        List<AuRestoFormulaType> auRestoFormulaTypeList = auRestoFormulaTypeRepository.findAll();
        assertThat(auRestoFormulaTypeList).hasSize(databaseSizeBeforeCreate + 1);
        AuRestoFormulaType testAuRestoFormulaType = auRestoFormulaTypeList.get(auRestoFormulaTypeList.size() - 1);
        assertThat(testAuRestoFormulaType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAuRestoFormulaType.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the AuRestoFormulaType in Elasticsearch
        AuRestoFormulaType auRestoFormulaTypeEs = auRestoFormulaTypeSearchRepository.findOne(testAuRestoFormulaType.getId());
        assertThat(auRestoFormulaTypeEs).isEqualToComparingFieldByField(testAuRestoFormulaType);
    }

    @Test
    @Transactional
    public void createAuRestoFormulaTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auRestoFormulaTypeRepository.findAll().size();

        // Create the AuRestoFormulaType with an existing ID
        auRestoFormulaType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuRestoFormulaTypeMockMvc.perform(post("/api/au-resto-formula-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoFormulaType)))
            .andExpect(status().isBadRequest());

        // Validate the AuRestoFormulaType in the database
        List<AuRestoFormulaType> auRestoFormulaTypeList = auRestoFormulaTypeRepository.findAll();
        assertThat(auRestoFormulaTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAuRestoFormulaTypes() throws Exception {
        // Initialize the database
        auRestoFormulaTypeRepository.saveAndFlush(auRestoFormulaType);

        // Get all the auRestoFormulaTypeList
        restAuRestoFormulaTypeMockMvc.perform(get("/api/au-resto-formula-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoFormulaType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getAuRestoFormulaType() throws Exception {
        // Initialize the database
        auRestoFormulaTypeRepository.saveAndFlush(auRestoFormulaType);

        // Get the auRestoFormulaType
        restAuRestoFormulaTypeMockMvc.perform(get("/api/au-resto-formula-types/{id}", auRestoFormulaType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(auRestoFormulaType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAuRestoFormulaType() throws Exception {
        // Get the auRestoFormulaType
        restAuRestoFormulaTypeMockMvc.perform(get("/api/au-resto-formula-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuRestoFormulaType() throws Exception {
        // Initialize the database
        auRestoFormulaTypeRepository.saveAndFlush(auRestoFormulaType);
        auRestoFormulaTypeSearchRepository.save(auRestoFormulaType);
        int databaseSizeBeforeUpdate = auRestoFormulaTypeRepository.findAll().size();

        // Update the auRestoFormulaType
        AuRestoFormulaType updatedAuRestoFormulaType = auRestoFormulaTypeRepository.findOne(auRestoFormulaType.getId());
        updatedAuRestoFormulaType
            .code(UPDATED_CODE)
            .name(UPDATED_NAME);

        restAuRestoFormulaTypeMockMvc.perform(put("/api/au-resto-formula-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAuRestoFormulaType)))
            .andExpect(status().isOk());

        // Validate the AuRestoFormulaType in the database
        List<AuRestoFormulaType> auRestoFormulaTypeList = auRestoFormulaTypeRepository.findAll();
        assertThat(auRestoFormulaTypeList).hasSize(databaseSizeBeforeUpdate);
        AuRestoFormulaType testAuRestoFormulaType = auRestoFormulaTypeList.get(auRestoFormulaTypeList.size() - 1);
        assertThat(testAuRestoFormulaType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAuRestoFormulaType.getName()).isEqualTo(UPDATED_NAME);

        // Validate the AuRestoFormulaType in Elasticsearch
        AuRestoFormulaType auRestoFormulaTypeEs = auRestoFormulaTypeSearchRepository.findOne(testAuRestoFormulaType.getId());
        assertThat(auRestoFormulaTypeEs).isEqualToComparingFieldByField(testAuRestoFormulaType);
    }

    @Test
    @Transactional
    public void updateNonExistingAuRestoFormulaType() throws Exception {
        int databaseSizeBeforeUpdate = auRestoFormulaTypeRepository.findAll().size();

        // Create the AuRestoFormulaType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAuRestoFormulaTypeMockMvc.perform(put("/api/au-resto-formula-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoFormulaType)))
            .andExpect(status().isCreated());

        // Validate the AuRestoFormulaType in the database
        List<AuRestoFormulaType> auRestoFormulaTypeList = auRestoFormulaTypeRepository.findAll();
        assertThat(auRestoFormulaTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAuRestoFormulaType() throws Exception {
        // Initialize the database
        auRestoFormulaTypeRepository.saveAndFlush(auRestoFormulaType);
        auRestoFormulaTypeSearchRepository.save(auRestoFormulaType);
        int databaseSizeBeforeDelete = auRestoFormulaTypeRepository.findAll().size();

        // Get the auRestoFormulaType
        restAuRestoFormulaTypeMockMvc.perform(delete("/api/au-resto-formula-types/{id}", auRestoFormulaType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean auRestoFormulaTypeExistsInEs = auRestoFormulaTypeSearchRepository.exists(auRestoFormulaType.getId());
        assertThat(auRestoFormulaTypeExistsInEs).isFalse();

        // Validate the database is empty
        List<AuRestoFormulaType> auRestoFormulaTypeList = auRestoFormulaTypeRepository.findAll();
        assertThat(auRestoFormulaTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAuRestoFormulaType() throws Exception {
        // Initialize the database
        auRestoFormulaTypeRepository.saveAndFlush(auRestoFormulaType);
        auRestoFormulaTypeSearchRepository.save(auRestoFormulaType);

        // Search the auRestoFormulaType
        restAuRestoFormulaTypeMockMvc.perform(get("/api/_search/au-resto-formula-types?query=id:" + auRestoFormulaType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoFormulaType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuRestoFormulaType.class);
        AuRestoFormulaType auRestoFormulaType1 = new AuRestoFormulaType();
        auRestoFormulaType1.setId(1L);
        AuRestoFormulaType auRestoFormulaType2 = new AuRestoFormulaType();
        auRestoFormulaType2.setId(auRestoFormulaType1.getId());
        assertThat(auRestoFormulaType1).isEqualTo(auRestoFormulaType2);
        auRestoFormulaType2.setId(2L);
        assertThat(auRestoFormulaType1).isNotEqualTo(auRestoFormulaType2);
        auRestoFormulaType1.setId(null);
        assertThat(auRestoFormulaType1).isNotEqualTo(auRestoFormulaType2);
    }
}
