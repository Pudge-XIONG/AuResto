package io.pudge.auresto.web.rest;

import io.pudge.auresto.AuRestoApp;

import io.pudge.auresto.domain.AuRestoFormula;
import io.pudge.auresto.repository.AuRestoFormulaRepository;
import io.pudge.auresto.repository.search.AuRestoFormulaSearchRepository;
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
 * Test class for the AuRestoFormulaResource REST controller.
 *
 * @see AuRestoFormulaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuRestoApp.class)
public class AuRestoFormulaResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    @Autowired
    private AuRestoFormulaRepository auRestoFormulaRepository;

    @Autowired
    private AuRestoFormulaSearchRepository auRestoFormulaSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAuRestoFormulaMockMvc;

    private AuRestoFormula auRestoFormula;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AuRestoFormulaResource auRestoFormulaResource = new AuRestoFormulaResource(auRestoFormulaRepository, auRestoFormulaSearchRepository);
        this.restAuRestoFormulaMockMvc = MockMvcBuilders.standaloneSetup(auRestoFormulaResource)
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
    public static AuRestoFormula createEntity(EntityManager em) {
        AuRestoFormula auRestoFormula = new AuRestoFormula()
            .title(DEFAULT_TITLE)
            .price(DEFAULT_PRICE);
        return auRestoFormula;
    }

    @Before
    public void initTest() {
        auRestoFormulaSearchRepository.deleteAll();
        auRestoFormula = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuRestoFormula() throws Exception {
        int databaseSizeBeforeCreate = auRestoFormulaRepository.findAll().size();

        // Create the AuRestoFormula
        restAuRestoFormulaMockMvc.perform(post("/api/au-resto-formulas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoFormula)))
            .andExpect(status().isCreated());

        // Validate the AuRestoFormula in the database
        List<AuRestoFormula> auRestoFormulaList = auRestoFormulaRepository.findAll();
        assertThat(auRestoFormulaList).hasSize(databaseSizeBeforeCreate + 1);
        AuRestoFormula testAuRestoFormula = auRestoFormulaList.get(auRestoFormulaList.size() - 1);
        assertThat(testAuRestoFormula.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testAuRestoFormula.getPrice()).isEqualTo(DEFAULT_PRICE);

        // Validate the AuRestoFormula in Elasticsearch
        AuRestoFormula auRestoFormulaEs = auRestoFormulaSearchRepository.findOne(testAuRestoFormula.getId());
        assertThat(auRestoFormulaEs).isEqualToComparingFieldByField(testAuRestoFormula);
    }

    @Test
    @Transactional
    public void createAuRestoFormulaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auRestoFormulaRepository.findAll().size();

        // Create the AuRestoFormula with an existing ID
        auRestoFormula.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuRestoFormulaMockMvc.perform(post("/api/au-resto-formulas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoFormula)))
            .andExpect(status().isBadRequest());

        // Validate the AuRestoFormula in the database
        List<AuRestoFormula> auRestoFormulaList = auRestoFormulaRepository.findAll();
        assertThat(auRestoFormulaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAuRestoFormulas() throws Exception {
        // Initialize the database
        auRestoFormulaRepository.saveAndFlush(auRestoFormula);

        // Get all the auRestoFormulaList
        restAuRestoFormulaMockMvc.perform(get("/api/au-resto-formulas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoFormula.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    public void getAuRestoFormula() throws Exception {
        // Initialize the database
        auRestoFormulaRepository.saveAndFlush(auRestoFormula);

        // Get the auRestoFormula
        restAuRestoFormulaMockMvc.perform(get("/api/au-resto-formulas/{id}", auRestoFormula.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(auRestoFormula.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAuRestoFormula() throws Exception {
        // Get the auRestoFormula
        restAuRestoFormulaMockMvc.perform(get("/api/au-resto-formulas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuRestoFormula() throws Exception {
        // Initialize the database
        auRestoFormulaRepository.saveAndFlush(auRestoFormula);
        auRestoFormulaSearchRepository.save(auRestoFormula);
        int databaseSizeBeforeUpdate = auRestoFormulaRepository.findAll().size();

        // Update the auRestoFormula
        AuRestoFormula updatedAuRestoFormula = auRestoFormulaRepository.findOne(auRestoFormula.getId());
        updatedAuRestoFormula
            .title(UPDATED_TITLE)
            .price(UPDATED_PRICE);

        restAuRestoFormulaMockMvc.perform(put("/api/au-resto-formulas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAuRestoFormula)))
            .andExpect(status().isOk());

        // Validate the AuRestoFormula in the database
        List<AuRestoFormula> auRestoFormulaList = auRestoFormulaRepository.findAll();
        assertThat(auRestoFormulaList).hasSize(databaseSizeBeforeUpdate);
        AuRestoFormula testAuRestoFormula = auRestoFormulaList.get(auRestoFormulaList.size() - 1);
        assertThat(testAuRestoFormula.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testAuRestoFormula.getPrice()).isEqualTo(UPDATED_PRICE);

        // Validate the AuRestoFormula in Elasticsearch
        AuRestoFormula auRestoFormulaEs = auRestoFormulaSearchRepository.findOne(testAuRestoFormula.getId());
        assertThat(auRestoFormulaEs).isEqualToComparingFieldByField(testAuRestoFormula);
    }

    @Test
    @Transactional
    public void updateNonExistingAuRestoFormula() throws Exception {
        int databaseSizeBeforeUpdate = auRestoFormulaRepository.findAll().size();

        // Create the AuRestoFormula

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAuRestoFormulaMockMvc.perform(put("/api/au-resto-formulas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoFormula)))
            .andExpect(status().isCreated());

        // Validate the AuRestoFormula in the database
        List<AuRestoFormula> auRestoFormulaList = auRestoFormulaRepository.findAll();
        assertThat(auRestoFormulaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAuRestoFormula() throws Exception {
        // Initialize the database
        auRestoFormulaRepository.saveAndFlush(auRestoFormula);
        auRestoFormulaSearchRepository.save(auRestoFormula);
        int databaseSizeBeforeDelete = auRestoFormulaRepository.findAll().size();

        // Get the auRestoFormula
        restAuRestoFormulaMockMvc.perform(delete("/api/au-resto-formulas/{id}", auRestoFormula.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean auRestoFormulaExistsInEs = auRestoFormulaSearchRepository.exists(auRestoFormula.getId());
        assertThat(auRestoFormulaExistsInEs).isFalse();

        // Validate the database is empty
        List<AuRestoFormula> auRestoFormulaList = auRestoFormulaRepository.findAll();
        assertThat(auRestoFormulaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAuRestoFormula() throws Exception {
        // Initialize the database
        auRestoFormulaRepository.saveAndFlush(auRestoFormula);
        auRestoFormulaSearchRepository.save(auRestoFormula);

        // Search the auRestoFormula
        restAuRestoFormulaMockMvc.perform(get("/api/_search/au-resto-formulas?query=id:" + auRestoFormula.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoFormula.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuRestoFormula.class);
        AuRestoFormula auRestoFormula1 = new AuRestoFormula();
        auRestoFormula1.setId(1L);
        AuRestoFormula auRestoFormula2 = new AuRestoFormula();
        auRestoFormula2.setId(auRestoFormula1.getId());
        assertThat(auRestoFormula1).isEqualTo(auRestoFormula2);
        auRestoFormula2.setId(2L);
        assertThat(auRestoFormula1).isNotEqualTo(auRestoFormula2);
        auRestoFormula1.setId(null);
        assertThat(auRestoFormula1).isNotEqualTo(auRestoFormula2);
    }
}
