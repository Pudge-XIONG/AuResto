package io.pudge.auresto.web.rest;

import io.pudge.auresto.AuRestoApp;

import io.pudge.auresto.domain.AuRestoOrderStatus;
import io.pudge.auresto.repository.AuRestoOrderStatusRepository;
import io.pudge.auresto.repository.search.AuRestoOrderStatusSearchRepository;
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
 * Test class for the AuRestoOrderStatusResource REST controller.
 *
 * @see AuRestoOrderStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuRestoApp.class)
public class AuRestoOrderStatusResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private AuRestoOrderStatusRepository auRestoOrderStatusRepository;

    @Autowired
    private AuRestoOrderStatusSearchRepository auRestoOrderStatusSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAuRestoOrderStatusMockMvc;

    private AuRestoOrderStatus auRestoOrderStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AuRestoOrderStatusResource auRestoOrderStatusResource = new AuRestoOrderStatusResource(auRestoOrderStatusRepository, auRestoOrderStatusSearchRepository);
        this.restAuRestoOrderStatusMockMvc = MockMvcBuilders.standaloneSetup(auRestoOrderStatusResource)
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
    public static AuRestoOrderStatus createEntity(EntityManager em) {
        AuRestoOrderStatus auRestoOrderStatus = new AuRestoOrderStatus()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME);
        return auRestoOrderStatus;
    }

    @Before
    public void initTest() {
        auRestoOrderStatusSearchRepository.deleteAll();
        auRestoOrderStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuRestoOrderStatus() throws Exception {
        int databaseSizeBeforeCreate = auRestoOrderStatusRepository.findAll().size();

        // Create the AuRestoOrderStatus
        restAuRestoOrderStatusMockMvc.perform(post("/api/au-resto-order-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoOrderStatus)))
            .andExpect(status().isCreated());

        // Validate the AuRestoOrderStatus in the database
        List<AuRestoOrderStatus> auRestoOrderStatusList = auRestoOrderStatusRepository.findAll();
        assertThat(auRestoOrderStatusList).hasSize(databaseSizeBeforeCreate + 1);
        AuRestoOrderStatus testAuRestoOrderStatus = auRestoOrderStatusList.get(auRestoOrderStatusList.size() - 1);
        assertThat(testAuRestoOrderStatus.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAuRestoOrderStatus.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the AuRestoOrderStatus in Elasticsearch
        AuRestoOrderStatus auRestoOrderStatusEs = auRestoOrderStatusSearchRepository.findOne(testAuRestoOrderStatus.getId());
        assertThat(auRestoOrderStatusEs).isEqualToComparingFieldByField(testAuRestoOrderStatus);
    }

    @Test
    @Transactional
    public void createAuRestoOrderStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auRestoOrderStatusRepository.findAll().size();

        // Create the AuRestoOrderStatus with an existing ID
        auRestoOrderStatus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuRestoOrderStatusMockMvc.perform(post("/api/au-resto-order-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoOrderStatus)))
            .andExpect(status().isBadRequest());

        // Validate the AuRestoOrderStatus in the database
        List<AuRestoOrderStatus> auRestoOrderStatusList = auRestoOrderStatusRepository.findAll();
        assertThat(auRestoOrderStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAuRestoOrderStatuses() throws Exception {
        // Initialize the database
        auRestoOrderStatusRepository.saveAndFlush(auRestoOrderStatus);

        // Get all the auRestoOrderStatusList
        restAuRestoOrderStatusMockMvc.perform(get("/api/au-resto-order-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoOrderStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getAuRestoOrderStatus() throws Exception {
        // Initialize the database
        auRestoOrderStatusRepository.saveAndFlush(auRestoOrderStatus);

        // Get the auRestoOrderStatus
        restAuRestoOrderStatusMockMvc.perform(get("/api/au-resto-order-statuses/{id}", auRestoOrderStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(auRestoOrderStatus.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAuRestoOrderStatus() throws Exception {
        // Get the auRestoOrderStatus
        restAuRestoOrderStatusMockMvc.perform(get("/api/au-resto-order-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuRestoOrderStatus() throws Exception {
        // Initialize the database
        auRestoOrderStatusRepository.saveAndFlush(auRestoOrderStatus);
        auRestoOrderStatusSearchRepository.save(auRestoOrderStatus);
        int databaseSizeBeforeUpdate = auRestoOrderStatusRepository.findAll().size();

        // Update the auRestoOrderStatus
        AuRestoOrderStatus updatedAuRestoOrderStatus = auRestoOrderStatusRepository.findOne(auRestoOrderStatus.getId());
        updatedAuRestoOrderStatus
            .code(UPDATED_CODE)
            .name(UPDATED_NAME);

        restAuRestoOrderStatusMockMvc.perform(put("/api/au-resto-order-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAuRestoOrderStatus)))
            .andExpect(status().isOk());

        // Validate the AuRestoOrderStatus in the database
        List<AuRestoOrderStatus> auRestoOrderStatusList = auRestoOrderStatusRepository.findAll();
        assertThat(auRestoOrderStatusList).hasSize(databaseSizeBeforeUpdate);
        AuRestoOrderStatus testAuRestoOrderStatus = auRestoOrderStatusList.get(auRestoOrderStatusList.size() - 1);
        assertThat(testAuRestoOrderStatus.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAuRestoOrderStatus.getName()).isEqualTo(UPDATED_NAME);

        // Validate the AuRestoOrderStatus in Elasticsearch
        AuRestoOrderStatus auRestoOrderStatusEs = auRestoOrderStatusSearchRepository.findOne(testAuRestoOrderStatus.getId());
        assertThat(auRestoOrderStatusEs).isEqualToComparingFieldByField(testAuRestoOrderStatus);
    }

    @Test
    @Transactional
    public void updateNonExistingAuRestoOrderStatus() throws Exception {
        int databaseSizeBeforeUpdate = auRestoOrderStatusRepository.findAll().size();

        // Create the AuRestoOrderStatus

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAuRestoOrderStatusMockMvc.perform(put("/api/au-resto-order-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoOrderStatus)))
            .andExpect(status().isCreated());

        // Validate the AuRestoOrderStatus in the database
        List<AuRestoOrderStatus> auRestoOrderStatusList = auRestoOrderStatusRepository.findAll();
        assertThat(auRestoOrderStatusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAuRestoOrderStatus() throws Exception {
        // Initialize the database
        auRestoOrderStatusRepository.saveAndFlush(auRestoOrderStatus);
        auRestoOrderStatusSearchRepository.save(auRestoOrderStatus);
        int databaseSizeBeforeDelete = auRestoOrderStatusRepository.findAll().size();

        // Get the auRestoOrderStatus
        restAuRestoOrderStatusMockMvc.perform(delete("/api/au-resto-order-statuses/{id}", auRestoOrderStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean auRestoOrderStatusExistsInEs = auRestoOrderStatusSearchRepository.exists(auRestoOrderStatus.getId());
        assertThat(auRestoOrderStatusExistsInEs).isFalse();

        // Validate the database is empty
        List<AuRestoOrderStatus> auRestoOrderStatusList = auRestoOrderStatusRepository.findAll();
        assertThat(auRestoOrderStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAuRestoOrderStatus() throws Exception {
        // Initialize the database
        auRestoOrderStatusRepository.saveAndFlush(auRestoOrderStatus);
        auRestoOrderStatusSearchRepository.save(auRestoOrderStatus);

        // Search the auRestoOrderStatus
        restAuRestoOrderStatusMockMvc.perform(get("/api/_search/au-resto-order-statuses?query=id:" + auRestoOrderStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoOrderStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuRestoOrderStatus.class);
        AuRestoOrderStatus auRestoOrderStatus1 = new AuRestoOrderStatus();
        auRestoOrderStatus1.setId(1L);
        AuRestoOrderStatus auRestoOrderStatus2 = new AuRestoOrderStatus();
        auRestoOrderStatus2.setId(auRestoOrderStatus1.getId());
        assertThat(auRestoOrderStatus1).isEqualTo(auRestoOrderStatus2);
        auRestoOrderStatus2.setId(2L);
        assertThat(auRestoOrderStatus1).isNotEqualTo(auRestoOrderStatus2);
        auRestoOrderStatus1.setId(null);
        assertThat(auRestoOrderStatus1).isNotEqualTo(auRestoOrderStatus2);
    }
}
