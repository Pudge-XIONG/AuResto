package io.pudge.auresto.web.rest;

import io.pudge.auresto.AuRestoApp;

import io.pudge.auresto.domain.AuRestoRecipeType;
import io.pudge.auresto.repository.AuRestoRecipeTypeRepository;
import io.pudge.auresto.repository.search.AuRestoRecipeTypeSearchRepository;
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
 * Test class for the AuRestoRecipeTypeResource REST controller.
 *
 * @see AuRestoRecipeTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuRestoApp.class)
public class AuRestoRecipeTypeResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private AuRestoRecipeTypeRepository auRestoRecipeTypeRepository;

    @Autowired
    private AuRestoRecipeTypeSearchRepository auRestoRecipeTypeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAuRestoRecipeTypeMockMvc;

    private AuRestoRecipeType auRestoRecipeType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AuRestoRecipeTypeResource auRestoRecipeTypeResource = new AuRestoRecipeTypeResource(auRestoRecipeTypeRepository, auRestoRecipeTypeSearchRepository);
        this.restAuRestoRecipeTypeMockMvc = MockMvcBuilders.standaloneSetup(auRestoRecipeTypeResource)
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
    public static AuRestoRecipeType createEntity(EntityManager em) {
        AuRestoRecipeType auRestoRecipeType = new AuRestoRecipeType()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME);
        return auRestoRecipeType;
    }

    @Before
    public void initTest() {
        auRestoRecipeTypeSearchRepository.deleteAll();
        auRestoRecipeType = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuRestoRecipeType() throws Exception {
        int databaseSizeBeforeCreate = auRestoRecipeTypeRepository.findAll().size();

        // Create the AuRestoRecipeType
        restAuRestoRecipeTypeMockMvc.perform(post("/api/au-resto-recipe-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoRecipeType)))
            .andExpect(status().isCreated());

        // Validate the AuRestoRecipeType in the database
        List<AuRestoRecipeType> auRestoRecipeTypeList = auRestoRecipeTypeRepository.findAll();
        assertThat(auRestoRecipeTypeList).hasSize(databaseSizeBeforeCreate + 1);
        AuRestoRecipeType testAuRestoRecipeType = auRestoRecipeTypeList.get(auRestoRecipeTypeList.size() - 1);
        assertThat(testAuRestoRecipeType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAuRestoRecipeType.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the AuRestoRecipeType in Elasticsearch
        AuRestoRecipeType auRestoRecipeTypeEs = auRestoRecipeTypeSearchRepository.findOne(testAuRestoRecipeType.getId());
        assertThat(auRestoRecipeTypeEs).isEqualToComparingFieldByField(testAuRestoRecipeType);
    }

    @Test
    @Transactional
    public void createAuRestoRecipeTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auRestoRecipeTypeRepository.findAll().size();

        // Create the AuRestoRecipeType with an existing ID
        auRestoRecipeType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuRestoRecipeTypeMockMvc.perform(post("/api/au-resto-recipe-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoRecipeType)))
            .andExpect(status().isBadRequest());

        // Validate the AuRestoRecipeType in the database
        List<AuRestoRecipeType> auRestoRecipeTypeList = auRestoRecipeTypeRepository.findAll();
        assertThat(auRestoRecipeTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAuRestoRecipeTypes() throws Exception {
        // Initialize the database
        auRestoRecipeTypeRepository.saveAndFlush(auRestoRecipeType);

        // Get all the auRestoRecipeTypeList
        restAuRestoRecipeTypeMockMvc.perform(get("/api/au-resto-recipe-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoRecipeType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getAuRestoRecipeType() throws Exception {
        // Initialize the database
        auRestoRecipeTypeRepository.saveAndFlush(auRestoRecipeType);

        // Get the auRestoRecipeType
        restAuRestoRecipeTypeMockMvc.perform(get("/api/au-resto-recipe-types/{id}", auRestoRecipeType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(auRestoRecipeType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAuRestoRecipeType() throws Exception {
        // Get the auRestoRecipeType
        restAuRestoRecipeTypeMockMvc.perform(get("/api/au-resto-recipe-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuRestoRecipeType() throws Exception {
        // Initialize the database
        auRestoRecipeTypeRepository.saveAndFlush(auRestoRecipeType);
        auRestoRecipeTypeSearchRepository.save(auRestoRecipeType);
        int databaseSizeBeforeUpdate = auRestoRecipeTypeRepository.findAll().size();

        // Update the auRestoRecipeType
        AuRestoRecipeType updatedAuRestoRecipeType = auRestoRecipeTypeRepository.findOne(auRestoRecipeType.getId());
        updatedAuRestoRecipeType
            .code(UPDATED_CODE)
            .name(UPDATED_NAME);

        restAuRestoRecipeTypeMockMvc.perform(put("/api/au-resto-recipe-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAuRestoRecipeType)))
            .andExpect(status().isOk());

        // Validate the AuRestoRecipeType in the database
        List<AuRestoRecipeType> auRestoRecipeTypeList = auRestoRecipeTypeRepository.findAll();
        assertThat(auRestoRecipeTypeList).hasSize(databaseSizeBeforeUpdate);
        AuRestoRecipeType testAuRestoRecipeType = auRestoRecipeTypeList.get(auRestoRecipeTypeList.size() - 1);
        assertThat(testAuRestoRecipeType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAuRestoRecipeType.getName()).isEqualTo(UPDATED_NAME);

        // Validate the AuRestoRecipeType in Elasticsearch
        AuRestoRecipeType auRestoRecipeTypeEs = auRestoRecipeTypeSearchRepository.findOne(testAuRestoRecipeType.getId());
        assertThat(auRestoRecipeTypeEs).isEqualToComparingFieldByField(testAuRestoRecipeType);
    }

    @Test
    @Transactional
    public void updateNonExistingAuRestoRecipeType() throws Exception {
        int databaseSizeBeforeUpdate = auRestoRecipeTypeRepository.findAll().size();

        // Create the AuRestoRecipeType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAuRestoRecipeTypeMockMvc.perform(put("/api/au-resto-recipe-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoRecipeType)))
            .andExpect(status().isCreated());

        // Validate the AuRestoRecipeType in the database
        List<AuRestoRecipeType> auRestoRecipeTypeList = auRestoRecipeTypeRepository.findAll();
        assertThat(auRestoRecipeTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAuRestoRecipeType() throws Exception {
        // Initialize the database
        auRestoRecipeTypeRepository.saveAndFlush(auRestoRecipeType);
        auRestoRecipeTypeSearchRepository.save(auRestoRecipeType);
        int databaseSizeBeforeDelete = auRestoRecipeTypeRepository.findAll().size();

        // Get the auRestoRecipeType
        restAuRestoRecipeTypeMockMvc.perform(delete("/api/au-resto-recipe-types/{id}", auRestoRecipeType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean auRestoRecipeTypeExistsInEs = auRestoRecipeTypeSearchRepository.exists(auRestoRecipeType.getId());
        assertThat(auRestoRecipeTypeExistsInEs).isFalse();

        // Validate the database is empty
        List<AuRestoRecipeType> auRestoRecipeTypeList = auRestoRecipeTypeRepository.findAll();
        assertThat(auRestoRecipeTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAuRestoRecipeType() throws Exception {
        // Initialize the database
        auRestoRecipeTypeRepository.saveAndFlush(auRestoRecipeType);
        auRestoRecipeTypeSearchRepository.save(auRestoRecipeType);

        // Search the auRestoRecipeType
        restAuRestoRecipeTypeMockMvc.perform(get("/api/_search/au-resto-recipe-types?query=id:" + auRestoRecipeType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoRecipeType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuRestoRecipeType.class);
        AuRestoRecipeType auRestoRecipeType1 = new AuRestoRecipeType();
        auRestoRecipeType1.setId(1L);
        AuRestoRecipeType auRestoRecipeType2 = new AuRestoRecipeType();
        auRestoRecipeType2.setId(auRestoRecipeType1.getId());
        assertThat(auRestoRecipeType1).isEqualTo(auRestoRecipeType2);
        auRestoRecipeType2.setId(2L);
        assertThat(auRestoRecipeType1).isNotEqualTo(auRestoRecipeType2);
        auRestoRecipeType1.setId(null);
        assertThat(auRestoRecipeType1).isNotEqualTo(auRestoRecipeType2);
    }
}
