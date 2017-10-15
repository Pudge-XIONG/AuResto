package io.pudge.auresto.web.rest;

import io.pudge.auresto.AuRestoApp;

import io.pudge.auresto.domain.AuRestoGender;
import io.pudge.auresto.repository.AuRestoGenderRepository;
import io.pudge.auresto.repository.search.AuRestoGenderSearchRepository;
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
 * Test class for the AuRestoGenderResource REST controller.
 *
 * @see AuRestoGenderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuRestoApp.class)
public class AuRestoGenderResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private AuRestoGenderRepository auRestoGenderRepository;

    @Autowired
    private AuRestoGenderSearchRepository auRestoGenderSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAuRestoGenderMockMvc;

    private AuRestoGender auRestoGender;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AuRestoGenderResource auRestoGenderResource = new AuRestoGenderResource(auRestoGenderRepository, auRestoGenderSearchRepository);
        this.restAuRestoGenderMockMvc = MockMvcBuilders.standaloneSetup(auRestoGenderResource)
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
    public static AuRestoGender createEntity(EntityManager em) {
        AuRestoGender auRestoGender = new AuRestoGender()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME);
        return auRestoGender;
    }

    @Before
    public void initTest() {
        auRestoGenderSearchRepository.deleteAll();
        auRestoGender = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuRestoGender() throws Exception {
        int databaseSizeBeforeCreate = auRestoGenderRepository.findAll().size();

        // Create the AuRestoGender
        restAuRestoGenderMockMvc.perform(post("/api/au-resto-genders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoGender)))
            .andExpect(status().isCreated());

        // Validate the AuRestoGender in the database
        List<AuRestoGender> auRestoGenderList = auRestoGenderRepository.findAll();
        assertThat(auRestoGenderList).hasSize(databaseSizeBeforeCreate + 1);
        AuRestoGender testAuRestoGender = auRestoGenderList.get(auRestoGenderList.size() - 1);
        assertThat(testAuRestoGender.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAuRestoGender.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the AuRestoGender in Elasticsearch
        AuRestoGender auRestoGenderEs = auRestoGenderSearchRepository.findOne(testAuRestoGender.getId());
        assertThat(auRestoGenderEs).isEqualToComparingFieldByField(testAuRestoGender);
    }

    @Test
    @Transactional
    public void createAuRestoGenderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auRestoGenderRepository.findAll().size();

        // Create the AuRestoGender with an existing ID
        auRestoGender.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuRestoGenderMockMvc.perform(post("/api/au-resto-genders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoGender)))
            .andExpect(status().isBadRequest());

        // Validate the AuRestoGender in the database
        List<AuRestoGender> auRestoGenderList = auRestoGenderRepository.findAll();
        assertThat(auRestoGenderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAuRestoGenders() throws Exception {
        // Initialize the database
        auRestoGenderRepository.saveAndFlush(auRestoGender);

        // Get all the auRestoGenderList
        restAuRestoGenderMockMvc.perform(get("/api/au-resto-genders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoGender.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getAuRestoGender() throws Exception {
        // Initialize the database
        auRestoGenderRepository.saveAndFlush(auRestoGender);

        // Get the auRestoGender
        restAuRestoGenderMockMvc.perform(get("/api/au-resto-genders/{id}", auRestoGender.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(auRestoGender.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAuRestoGender() throws Exception {
        // Get the auRestoGender
        restAuRestoGenderMockMvc.perform(get("/api/au-resto-genders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuRestoGender() throws Exception {
        // Initialize the database
        auRestoGenderRepository.saveAndFlush(auRestoGender);
        auRestoGenderSearchRepository.save(auRestoGender);
        int databaseSizeBeforeUpdate = auRestoGenderRepository.findAll().size();

        // Update the auRestoGender
        AuRestoGender updatedAuRestoGender = auRestoGenderRepository.findOne(auRestoGender.getId());
        updatedAuRestoGender
            .code(UPDATED_CODE)
            .name(UPDATED_NAME);

        restAuRestoGenderMockMvc.perform(put("/api/au-resto-genders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAuRestoGender)))
            .andExpect(status().isOk());

        // Validate the AuRestoGender in the database
        List<AuRestoGender> auRestoGenderList = auRestoGenderRepository.findAll();
        assertThat(auRestoGenderList).hasSize(databaseSizeBeforeUpdate);
        AuRestoGender testAuRestoGender = auRestoGenderList.get(auRestoGenderList.size() - 1);
        assertThat(testAuRestoGender.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAuRestoGender.getName()).isEqualTo(UPDATED_NAME);

        // Validate the AuRestoGender in Elasticsearch
        AuRestoGender auRestoGenderEs = auRestoGenderSearchRepository.findOne(testAuRestoGender.getId());
        assertThat(auRestoGenderEs).isEqualToComparingFieldByField(testAuRestoGender);
    }

    @Test
    @Transactional
    public void updateNonExistingAuRestoGender() throws Exception {
        int databaseSizeBeforeUpdate = auRestoGenderRepository.findAll().size();

        // Create the AuRestoGender

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAuRestoGenderMockMvc.perform(put("/api/au-resto-genders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoGender)))
            .andExpect(status().isCreated());

        // Validate the AuRestoGender in the database
        List<AuRestoGender> auRestoGenderList = auRestoGenderRepository.findAll();
        assertThat(auRestoGenderList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAuRestoGender() throws Exception {
        // Initialize the database
        auRestoGenderRepository.saveAndFlush(auRestoGender);
        auRestoGenderSearchRepository.save(auRestoGender);
        int databaseSizeBeforeDelete = auRestoGenderRepository.findAll().size();

        // Get the auRestoGender
        restAuRestoGenderMockMvc.perform(delete("/api/au-resto-genders/{id}", auRestoGender.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean auRestoGenderExistsInEs = auRestoGenderSearchRepository.exists(auRestoGender.getId());
        assertThat(auRestoGenderExistsInEs).isFalse();

        // Validate the database is empty
        List<AuRestoGender> auRestoGenderList = auRestoGenderRepository.findAll();
        assertThat(auRestoGenderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAuRestoGender() throws Exception {
        // Initialize the database
        auRestoGenderRepository.saveAndFlush(auRestoGender);
        auRestoGenderSearchRepository.save(auRestoGender);

        // Search the auRestoGender
        restAuRestoGenderMockMvc.perform(get("/api/_search/au-resto-genders?query=id:" + auRestoGender.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoGender.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuRestoGender.class);
        AuRestoGender auRestoGender1 = new AuRestoGender();
        auRestoGender1.setId(1L);
        AuRestoGender auRestoGender2 = new AuRestoGender();
        auRestoGender2.setId(auRestoGender1.getId());
        assertThat(auRestoGender1).isEqualTo(auRestoGender2);
        auRestoGender2.setId(2L);
        assertThat(auRestoGender1).isNotEqualTo(auRestoGender2);
        auRestoGender1.setId(null);
        assertThat(auRestoGender1).isNotEqualTo(auRestoGender2);
    }
}
