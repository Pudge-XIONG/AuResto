package io.pudge.auresto.web.rest;

import io.pudge.auresto.AuRestoApp;

import io.pudge.auresto.domain.AuRestoBill;
import io.pudge.auresto.repository.AuRestoBillRepository;
import io.pudge.auresto.repository.search.AuRestoBillSearchRepository;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static io.pudge.auresto.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AuRestoBillResource REST controller.
 *
 * @see AuRestoBillResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuRestoApp.class)
public class AuRestoBillResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private AuRestoBillRepository auRestoBillRepository;

    @Autowired
    private AuRestoBillSearchRepository auRestoBillSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAuRestoBillMockMvc;

    private AuRestoBill auRestoBill;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AuRestoBillResource auRestoBillResource = new AuRestoBillResource(auRestoBillRepository, auRestoBillSearchRepository);
        this.restAuRestoBillMockMvc = MockMvcBuilders.standaloneSetup(auRestoBillResource)
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
    public static AuRestoBill createEntity(EntityManager em) {
        AuRestoBill auRestoBill = new AuRestoBill()
            .code(DEFAULT_CODE)
            .date(DEFAULT_DATE);
        return auRestoBill;
    }

    @Before
    public void initTest() {
        auRestoBillSearchRepository.deleteAll();
        auRestoBill = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuRestoBill() throws Exception {
        int databaseSizeBeforeCreate = auRestoBillRepository.findAll().size();

        // Create the AuRestoBill
        restAuRestoBillMockMvc.perform(post("/api/au-resto-bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoBill)))
            .andExpect(status().isCreated());

        // Validate the AuRestoBill in the database
        List<AuRestoBill> auRestoBillList = auRestoBillRepository.findAll();
        assertThat(auRestoBillList).hasSize(databaseSizeBeforeCreate + 1);
        AuRestoBill testAuRestoBill = auRestoBillList.get(auRestoBillList.size() - 1);
        assertThat(testAuRestoBill.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAuRestoBill.getDate()).isEqualTo(DEFAULT_DATE);

        // Validate the AuRestoBill in Elasticsearch
        AuRestoBill auRestoBillEs = auRestoBillSearchRepository.findOne(testAuRestoBill.getId());
        assertThat(auRestoBillEs).isEqualToComparingFieldByField(testAuRestoBill);
    }

    @Test
    @Transactional
    public void createAuRestoBillWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auRestoBillRepository.findAll().size();

        // Create the AuRestoBill with an existing ID
        auRestoBill.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuRestoBillMockMvc.perform(post("/api/au-resto-bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoBill)))
            .andExpect(status().isBadRequest());

        // Validate the AuRestoBill in the database
        List<AuRestoBill> auRestoBillList = auRestoBillRepository.findAll();
        assertThat(auRestoBillList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAuRestoBills() throws Exception {
        // Initialize the database
        auRestoBillRepository.saveAndFlush(auRestoBill);

        // Get all the auRestoBillList
        restAuRestoBillMockMvc.perform(get("/api/au-resto-bills?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoBill.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));
    }

    @Test
    @Transactional
    public void getAuRestoBill() throws Exception {
        // Initialize the database
        auRestoBillRepository.saveAndFlush(auRestoBill);

        // Get the auRestoBill
        restAuRestoBillMockMvc.perform(get("/api/au-resto-bills/{id}", auRestoBill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(auRestoBill.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingAuRestoBill() throws Exception {
        // Get the auRestoBill
        restAuRestoBillMockMvc.perform(get("/api/au-resto-bills/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuRestoBill() throws Exception {
        // Initialize the database
        auRestoBillRepository.saveAndFlush(auRestoBill);
        auRestoBillSearchRepository.save(auRestoBill);
        int databaseSizeBeforeUpdate = auRestoBillRepository.findAll().size();

        // Update the auRestoBill
        AuRestoBill updatedAuRestoBill = auRestoBillRepository.findOne(auRestoBill.getId());
        updatedAuRestoBill
            .code(UPDATED_CODE)
            .date(UPDATED_DATE);

        restAuRestoBillMockMvc.perform(put("/api/au-resto-bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAuRestoBill)))
            .andExpect(status().isOk());

        // Validate the AuRestoBill in the database
        List<AuRestoBill> auRestoBillList = auRestoBillRepository.findAll();
        assertThat(auRestoBillList).hasSize(databaseSizeBeforeUpdate);
        AuRestoBill testAuRestoBill = auRestoBillList.get(auRestoBillList.size() - 1);
        assertThat(testAuRestoBill.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAuRestoBill.getDate()).isEqualTo(UPDATED_DATE);

        // Validate the AuRestoBill in Elasticsearch
        AuRestoBill auRestoBillEs = auRestoBillSearchRepository.findOne(testAuRestoBill.getId());
        assertThat(auRestoBillEs).isEqualToComparingFieldByField(testAuRestoBill);
    }

    @Test
    @Transactional
    public void updateNonExistingAuRestoBill() throws Exception {
        int databaseSizeBeforeUpdate = auRestoBillRepository.findAll().size();

        // Create the AuRestoBill

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAuRestoBillMockMvc.perform(put("/api/au-resto-bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoBill)))
            .andExpect(status().isCreated());

        // Validate the AuRestoBill in the database
        List<AuRestoBill> auRestoBillList = auRestoBillRepository.findAll();
        assertThat(auRestoBillList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAuRestoBill() throws Exception {
        // Initialize the database
        auRestoBillRepository.saveAndFlush(auRestoBill);
        auRestoBillSearchRepository.save(auRestoBill);
        int databaseSizeBeforeDelete = auRestoBillRepository.findAll().size();

        // Get the auRestoBill
        restAuRestoBillMockMvc.perform(delete("/api/au-resto-bills/{id}", auRestoBill.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean auRestoBillExistsInEs = auRestoBillSearchRepository.exists(auRestoBill.getId());
        assertThat(auRestoBillExistsInEs).isFalse();

        // Validate the database is empty
        List<AuRestoBill> auRestoBillList = auRestoBillRepository.findAll();
        assertThat(auRestoBillList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAuRestoBill() throws Exception {
        // Initialize the database
        auRestoBillRepository.saveAndFlush(auRestoBill);
        auRestoBillSearchRepository.save(auRestoBill);

        // Search the auRestoBill
        restAuRestoBillMockMvc.perform(get("/api/_search/au-resto-bills?query=id:" + auRestoBill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoBill.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuRestoBill.class);
        AuRestoBill auRestoBill1 = new AuRestoBill();
        auRestoBill1.setId(1L);
        AuRestoBill auRestoBill2 = new AuRestoBill();
        auRestoBill2.setId(auRestoBill1.getId());
        assertThat(auRestoBill1).isEqualTo(auRestoBill2);
        auRestoBill2.setId(2L);
        assertThat(auRestoBill1).isNotEqualTo(auRestoBill2);
        auRestoBill1.setId(null);
        assertThat(auRestoBill1).isNotEqualTo(auRestoBill2);
    }
}
