package io.pudge.auresto.web.rest;

import io.pudge.auresto.AuRestoApp;

import io.pudge.auresto.domain.AuRestoBillStatus;
import io.pudge.auresto.repository.AuRestoBillStatusRepository;
import io.pudge.auresto.repository.search.AuRestoBillStatusSearchRepository;
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
 * Test class for the AuRestoBillStatusResource REST controller.
 *
 * @see AuRestoBillStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuRestoApp.class)
public class AuRestoBillStatusResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private AuRestoBillStatusRepository auRestoBillStatusRepository;

    @Autowired
    private AuRestoBillStatusSearchRepository auRestoBillStatusSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAuRestoBillStatusMockMvc;

    private AuRestoBillStatus auRestoBillStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AuRestoBillStatusResource auRestoBillStatusResource = new AuRestoBillStatusResource(auRestoBillStatusRepository, auRestoBillStatusSearchRepository);
        this.restAuRestoBillStatusMockMvc = MockMvcBuilders.standaloneSetup(auRestoBillStatusResource)
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
    public static AuRestoBillStatus createEntity(EntityManager em) {
        AuRestoBillStatus auRestoBillStatus = new AuRestoBillStatus()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME);
        return auRestoBillStatus;
    }

    @Before
    public void initTest() {
        auRestoBillStatusSearchRepository.deleteAll();
        auRestoBillStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuRestoBillStatus() throws Exception {
        int databaseSizeBeforeCreate = auRestoBillStatusRepository.findAll().size();

        // Create the AuRestoBillStatus
        restAuRestoBillStatusMockMvc.perform(post("/api/au-resto-bill-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoBillStatus)))
            .andExpect(status().isCreated());

        // Validate the AuRestoBillStatus in the database
        List<AuRestoBillStatus> auRestoBillStatusList = auRestoBillStatusRepository.findAll();
        assertThat(auRestoBillStatusList).hasSize(databaseSizeBeforeCreate + 1);
        AuRestoBillStatus testAuRestoBillStatus = auRestoBillStatusList.get(auRestoBillStatusList.size() - 1);
        assertThat(testAuRestoBillStatus.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAuRestoBillStatus.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the AuRestoBillStatus in Elasticsearch
        AuRestoBillStatus auRestoBillStatusEs = auRestoBillStatusSearchRepository.findOne(testAuRestoBillStatus.getId());
        assertThat(auRestoBillStatusEs).isEqualToComparingFieldByField(testAuRestoBillStatus);
    }

    @Test
    @Transactional
    public void createAuRestoBillStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auRestoBillStatusRepository.findAll().size();

        // Create the AuRestoBillStatus with an existing ID
        auRestoBillStatus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuRestoBillStatusMockMvc.perform(post("/api/au-resto-bill-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoBillStatus)))
            .andExpect(status().isBadRequest());

        // Validate the AuRestoBillStatus in the database
        List<AuRestoBillStatus> auRestoBillStatusList = auRestoBillStatusRepository.findAll();
        assertThat(auRestoBillStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAuRestoBillStatuses() throws Exception {
        // Initialize the database
        auRestoBillStatusRepository.saveAndFlush(auRestoBillStatus);

        // Get all the auRestoBillStatusList
        restAuRestoBillStatusMockMvc.perform(get("/api/au-resto-bill-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoBillStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getAuRestoBillStatus() throws Exception {
        // Initialize the database
        auRestoBillStatusRepository.saveAndFlush(auRestoBillStatus);

        // Get the auRestoBillStatus
        restAuRestoBillStatusMockMvc.perform(get("/api/au-resto-bill-statuses/{id}", auRestoBillStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(auRestoBillStatus.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAuRestoBillStatus() throws Exception {
        // Get the auRestoBillStatus
        restAuRestoBillStatusMockMvc.perform(get("/api/au-resto-bill-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuRestoBillStatus() throws Exception {
        // Initialize the database
        auRestoBillStatusRepository.saveAndFlush(auRestoBillStatus);
        auRestoBillStatusSearchRepository.save(auRestoBillStatus);
        int databaseSizeBeforeUpdate = auRestoBillStatusRepository.findAll().size();

        // Update the auRestoBillStatus
        AuRestoBillStatus updatedAuRestoBillStatus = auRestoBillStatusRepository.findOne(auRestoBillStatus.getId());
        updatedAuRestoBillStatus
            .code(UPDATED_CODE)
            .name(UPDATED_NAME);

        restAuRestoBillStatusMockMvc.perform(put("/api/au-resto-bill-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAuRestoBillStatus)))
            .andExpect(status().isOk());

        // Validate the AuRestoBillStatus in the database
        List<AuRestoBillStatus> auRestoBillStatusList = auRestoBillStatusRepository.findAll();
        assertThat(auRestoBillStatusList).hasSize(databaseSizeBeforeUpdate);
        AuRestoBillStatus testAuRestoBillStatus = auRestoBillStatusList.get(auRestoBillStatusList.size() - 1);
        assertThat(testAuRestoBillStatus.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAuRestoBillStatus.getName()).isEqualTo(UPDATED_NAME);

        // Validate the AuRestoBillStatus in Elasticsearch
        AuRestoBillStatus auRestoBillStatusEs = auRestoBillStatusSearchRepository.findOne(testAuRestoBillStatus.getId());
        assertThat(auRestoBillStatusEs).isEqualToComparingFieldByField(testAuRestoBillStatus);
    }

    @Test
    @Transactional
    public void updateNonExistingAuRestoBillStatus() throws Exception {
        int databaseSizeBeforeUpdate = auRestoBillStatusRepository.findAll().size();

        // Create the AuRestoBillStatus

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAuRestoBillStatusMockMvc.perform(put("/api/au-resto-bill-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoBillStatus)))
            .andExpect(status().isCreated());

        // Validate the AuRestoBillStatus in the database
        List<AuRestoBillStatus> auRestoBillStatusList = auRestoBillStatusRepository.findAll();
        assertThat(auRestoBillStatusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAuRestoBillStatus() throws Exception {
        // Initialize the database
        auRestoBillStatusRepository.saveAndFlush(auRestoBillStatus);
        auRestoBillStatusSearchRepository.save(auRestoBillStatus);
        int databaseSizeBeforeDelete = auRestoBillStatusRepository.findAll().size();

        // Get the auRestoBillStatus
        restAuRestoBillStatusMockMvc.perform(delete("/api/au-resto-bill-statuses/{id}", auRestoBillStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean auRestoBillStatusExistsInEs = auRestoBillStatusSearchRepository.exists(auRestoBillStatus.getId());
        assertThat(auRestoBillStatusExistsInEs).isFalse();

        // Validate the database is empty
        List<AuRestoBillStatus> auRestoBillStatusList = auRestoBillStatusRepository.findAll();
        assertThat(auRestoBillStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAuRestoBillStatus() throws Exception {
        // Initialize the database
        auRestoBillStatusRepository.saveAndFlush(auRestoBillStatus);
        auRestoBillStatusSearchRepository.save(auRestoBillStatus);

        // Search the auRestoBillStatus
        restAuRestoBillStatusMockMvc.perform(get("/api/_search/au-resto-bill-statuses?query=id:" + auRestoBillStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoBillStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuRestoBillStatus.class);
        AuRestoBillStatus auRestoBillStatus1 = new AuRestoBillStatus();
        auRestoBillStatus1.setId(1L);
        AuRestoBillStatus auRestoBillStatus2 = new AuRestoBillStatus();
        auRestoBillStatus2.setId(auRestoBillStatus1.getId());
        assertThat(auRestoBillStatus1).isEqualTo(auRestoBillStatus2);
        auRestoBillStatus2.setId(2L);
        assertThat(auRestoBillStatus1).isNotEqualTo(auRestoBillStatus2);
        auRestoBillStatus1.setId(null);
        assertThat(auRestoBillStatus1).isNotEqualTo(auRestoBillStatus2);
    }
}
