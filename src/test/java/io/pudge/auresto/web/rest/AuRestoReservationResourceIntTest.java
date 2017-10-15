package io.pudge.auresto.web.rest;

import io.pudge.auresto.AuRestoApp;

import io.pudge.auresto.domain.AuRestoReservation;
import io.pudge.auresto.repository.AuRestoReservationRepository;
import io.pudge.auresto.repository.search.AuRestoReservationSearchRepository;
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
 * Test class for the AuRestoReservationResource REST controller.
 *
 * @see AuRestoReservationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuRestoApp.class)
public class AuRestoReservationResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_RESERVE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_RESERVE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_RESERVE_FOR_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_RESERVE_FOR_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private AuRestoReservationRepository auRestoReservationRepository;

    @Autowired
    private AuRestoReservationSearchRepository auRestoReservationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAuRestoReservationMockMvc;

    private AuRestoReservation auRestoReservation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AuRestoReservationResource auRestoReservationResource = new AuRestoReservationResource(auRestoReservationRepository, auRestoReservationSearchRepository);
        this.restAuRestoReservationMockMvc = MockMvcBuilders.standaloneSetup(auRestoReservationResource)
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
    public static AuRestoReservation createEntity(EntityManager em) {
        AuRestoReservation auRestoReservation = new AuRestoReservation()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .reserveDate(DEFAULT_RESERVE_DATE)
            .reserveForDate(DEFAULT_RESERVE_FOR_DATE);
        return auRestoReservation;
    }

    @Before
    public void initTest() {
        auRestoReservationSearchRepository.deleteAll();
        auRestoReservation = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuRestoReservation() throws Exception {
        int databaseSizeBeforeCreate = auRestoReservationRepository.findAll().size();

        // Create the AuRestoReservation
        restAuRestoReservationMockMvc.perform(post("/api/au-resto-reservations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoReservation)))
            .andExpect(status().isCreated());

        // Validate the AuRestoReservation in the database
        List<AuRestoReservation> auRestoReservationList = auRestoReservationRepository.findAll();
        assertThat(auRestoReservationList).hasSize(databaseSizeBeforeCreate + 1);
        AuRestoReservation testAuRestoReservation = auRestoReservationList.get(auRestoReservationList.size() - 1);
        assertThat(testAuRestoReservation.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAuRestoReservation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAuRestoReservation.getReserveDate()).isEqualTo(DEFAULT_RESERVE_DATE);
        assertThat(testAuRestoReservation.getReserveForDate()).isEqualTo(DEFAULT_RESERVE_FOR_DATE);

        // Validate the AuRestoReservation in Elasticsearch
        AuRestoReservation auRestoReservationEs = auRestoReservationSearchRepository.findOne(testAuRestoReservation.getId());
        assertThat(auRestoReservationEs).isEqualToComparingFieldByField(testAuRestoReservation);
    }

    @Test
    @Transactional
    public void createAuRestoReservationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auRestoReservationRepository.findAll().size();

        // Create the AuRestoReservation with an existing ID
        auRestoReservation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuRestoReservationMockMvc.perform(post("/api/au-resto-reservations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoReservation)))
            .andExpect(status().isBadRequest());

        // Validate the AuRestoReservation in the database
        List<AuRestoReservation> auRestoReservationList = auRestoReservationRepository.findAll();
        assertThat(auRestoReservationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAuRestoReservations() throws Exception {
        // Initialize the database
        auRestoReservationRepository.saveAndFlush(auRestoReservation);

        // Get all the auRestoReservationList
        restAuRestoReservationMockMvc.perform(get("/api/au-resto-reservations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoReservation.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].reserveDate").value(hasItem(sameInstant(DEFAULT_RESERVE_DATE))))
            .andExpect(jsonPath("$.[*].reserveForDate").value(hasItem(sameInstant(DEFAULT_RESERVE_FOR_DATE))));
    }

    @Test
    @Transactional
    public void getAuRestoReservation() throws Exception {
        // Initialize the database
        auRestoReservationRepository.saveAndFlush(auRestoReservation);

        // Get the auRestoReservation
        restAuRestoReservationMockMvc.perform(get("/api/au-resto-reservations/{id}", auRestoReservation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(auRestoReservation.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.reserveDate").value(sameInstant(DEFAULT_RESERVE_DATE)))
            .andExpect(jsonPath("$.reserveForDate").value(sameInstant(DEFAULT_RESERVE_FOR_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingAuRestoReservation() throws Exception {
        // Get the auRestoReservation
        restAuRestoReservationMockMvc.perform(get("/api/au-resto-reservations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuRestoReservation() throws Exception {
        // Initialize the database
        auRestoReservationRepository.saveAndFlush(auRestoReservation);
        auRestoReservationSearchRepository.save(auRestoReservation);
        int databaseSizeBeforeUpdate = auRestoReservationRepository.findAll().size();

        // Update the auRestoReservation
        AuRestoReservation updatedAuRestoReservation = auRestoReservationRepository.findOne(auRestoReservation.getId());
        updatedAuRestoReservation
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .reserveDate(UPDATED_RESERVE_DATE)
            .reserveForDate(UPDATED_RESERVE_FOR_DATE);

        restAuRestoReservationMockMvc.perform(put("/api/au-resto-reservations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAuRestoReservation)))
            .andExpect(status().isOk());

        // Validate the AuRestoReservation in the database
        List<AuRestoReservation> auRestoReservationList = auRestoReservationRepository.findAll();
        assertThat(auRestoReservationList).hasSize(databaseSizeBeforeUpdate);
        AuRestoReservation testAuRestoReservation = auRestoReservationList.get(auRestoReservationList.size() - 1);
        assertThat(testAuRestoReservation.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAuRestoReservation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAuRestoReservation.getReserveDate()).isEqualTo(UPDATED_RESERVE_DATE);
        assertThat(testAuRestoReservation.getReserveForDate()).isEqualTo(UPDATED_RESERVE_FOR_DATE);

        // Validate the AuRestoReservation in Elasticsearch
        AuRestoReservation auRestoReservationEs = auRestoReservationSearchRepository.findOne(testAuRestoReservation.getId());
        assertThat(auRestoReservationEs).isEqualToComparingFieldByField(testAuRestoReservation);
    }

    @Test
    @Transactional
    public void updateNonExistingAuRestoReservation() throws Exception {
        int databaseSizeBeforeUpdate = auRestoReservationRepository.findAll().size();

        // Create the AuRestoReservation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAuRestoReservationMockMvc.perform(put("/api/au-resto-reservations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoReservation)))
            .andExpect(status().isCreated());

        // Validate the AuRestoReservation in the database
        List<AuRestoReservation> auRestoReservationList = auRestoReservationRepository.findAll();
        assertThat(auRestoReservationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAuRestoReservation() throws Exception {
        // Initialize the database
        auRestoReservationRepository.saveAndFlush(auRestoReservation);
        auRestoReservationSearchRepository.save(auRestoReservation);
        int databaseSizeBeforeDelete = auRestoReservationRepository.findAll().size();

        // Get the auRestoReservation
        restAuRestoReservationMockMvc.perform(delete("/api/au-resto-reservations/{id}", auRestoReservation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean auRestoReservationExistsInEs = auRestoReservationSearchRepository.exists(auRestoReservation.getId());
        assertThat(auRestoReservationExistsInEs).isFalse();

        // Validate the database is empty
        List<AuRestoReservation> auRestoReservationList = auRestoReservationRepository.findAll();
        assertThat(auRestoReservationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAuRestoReservation() throws Exception {
        // Initialize the database
        auRestoReservationRepository.saveAndFlush(auRestoReservation);
        auRestoReservationSearchRepository.save(auRestoReservation);

        // Search the auRestoReservation
        restAuRestoReservationMockMvc.perform(get("/api/_search/au-resto-reservations?query=id:" + auRestoReservation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoReservation.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].reserveDate").value(hasItem(sameInstant(DEFAULT_RESERVE_DATE))))
            .andExpect(jsonPath("$.[*].reserveForDate").value(hasItem(sameInstant(DEFAULT_RESERVE_FOR_DATE))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuRestoReservation.class);
        AuRestoReservation auRestoReservation1 = new AuRestoReservation();
        auRestoReservation1.setId(1L);
        AuRestoReservation auRestoReservation2 = new AuRestoReservation();
        auRestoReservation2.setId(auRestoReservation1.getId());
        assertThat(auRestoReservation1).isEqualTo(auRestoReservation2);
        auRestoReservation2.setId(2L);
        assertThat(auRestoReservation1).isNotEqualTo(auRestoReservation2);
        auRestoReservation1.setId(null);
        assertThat(auRestoReservation1).isNotEqualTo(auRestoReservation2);
    }
}
