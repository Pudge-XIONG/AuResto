package io.pudge.auresto.web.rest;

import io.pudge.auresto.AuRestoApp;

import io.pudge.auresto.domain.AuRestoLocation;
import io.pudge.auresto.repository.AuRestoLocationRepository;
import io.pudge.auresto.repository.search.AuRestoLocationSearchRepository;
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
 * Test class for the AuRestoLocationResource REST controller.
 *
 * @see AuRestoLocationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuRestoApp.class)
public class AuRestoLocationResourceIntTest {

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;

    @Autowired
    private AuRestoLocationRepository auRestoLocationRepository;

    @Autowired
    private AuRestoLocationSearchRepository auRestoLocationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAuRestoLocationMockMvc;

    private AuRestoLocation auRestoLocation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AuRestoLocationResource auRestoLocationResource = new AuRestoLocationResource(auRestoLocationRepository, auRestoLocationSearchRepository);
        this.restAuRestoLocationMockMvc = MockMvcBuilders.standaloneSetup(auRestoLocationResource)
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
    public static AuRestoLocation createEntity(EntityManager em) {
        AuRestoLocation auRestoLocation = new AuRestoLocation()
            .address(DEFAULT_ADDRESS)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE);
        return auRestoLocation;
    }

    @Before
    public void initTest() {
        auRestoLocationSearchRepository.deleteAll();
        auRestoLocation = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuRestoLocation() throws Exception {
        int databaseSizeBeforeCreate = auRestoLocationRepository.findAll().size();

        // Create the AuRestoLocation
        restAuRestoLocationMockMvc.perform(post("/api/au-resto-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoLocation)))
            .andExpect(status().isCreated());

        // Validate the AuRestoLocation in the database
        List<AuRestoLocation> auRestoLocationList = auRestoLocationRepository.findAll();
        assertThat(auRestoLocationList).hasSize(databaseSizeBeforeCreate + 1);
        AuRestoLocation testAuRestoLocation = auRestoLocationList.get(auRestoLocationList.size() - 1);
        assertThat(testAuRestoLocation.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testAuRestoLocation.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testAuRestoLocation.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);

        // Validate the AuRestoLocation in Elasticsearch
        AuRestoLocation auRestoLocationEs = auRestoLocationSearchRepository.findOne(testAuRestoLocation.getId());
        assertThat(auRestoLocationEs).isEqualToComparingFieldByField(testAuRestoLocation);
    }

    @Test
    @Transactional
    public void createAuRestoLocationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auRestoLocationRepository.findAll().size();

        // Create the AuRestoLocation with an existing ID
        auRestoLocation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuRestoLocationMockMvc.perform(post("/api/au-resto-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoLocation)))
            .andExpect(status().isBadRequest());

        // Validate the AuRestoLocation in the database
        List<AuRestoLocation> auRestoLocationList = auRestoLocationRepository.findAll();
        assertThat(auRestoLocationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAuRestoLocations() throws Exception {
        // Initialize the database
        auRestoLocationRepository.saveAndFlush(auRestoLocation);

        // Get all the auRestoLocationList
        restAuRestoLocationMockMvc.perform(get("/api/au-resto-locations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())));
    }

    @Test
    @Transactional
    public void getAuRestoLocation() throws Exception {
        // Initialize the database
        auRestoLocationRepository.saveAndFlush(auRestoLocation);

        // Get the auRestoLocation
        restAuRestoLocationMockMvc.perform(get("/api/au-resto-locations/{id}", auRestoLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(auRestoLocation.getId().intValue()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAuRestoLocation() throws Exception {
        // Get the auRestoLocation
        restAuRestoLocationMockMvc.perform(get("/api/au-resto-locations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuRestoLocation() throws Exception {
        // Initialize the database
        auRestoLocationRepository.saveAndFlush(auRestoLocation);
        auRestoLocationSearchRepository.save(auRestoLocation);
        int databaseSizeBeforeUpdate = auRestoLocationRepository.findAll().size();

        // Update the auRestoLocation
        AuRestoLocation updatedAuRestoLocation = auRestoLocationRepository.findOne(auRestoLocation.getId());
        updatedAuRestoLocation
            .address(UPDATED_ADDRESS)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);

        restAuRestoLocationMockMvc.perform(put("/api/au-resto-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAuRestoLocation)))
            .andExpect(status().isOk());

        // Validate the AuRestoLocation in the database
        List<AuRestoLocation> auRestoLocationList = auRestoLocationRepository.findAll();
        assertThat(auRestoLocationList).hasSize(databaseSizeBeforeUpdate);
        AuRestoLocation testAuRestoLocation = auRestoLocationList.get(auRestoLocationList.size() - 1);
        assertThat(testAuRestoLocation.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testAuRestoLocation.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testAuRestoLocation.getLongitude()).isEqualTo(UPDATED_LONGITUDE);

        // Validate the AuRestoLocation in Elasticsearch
        AuRestoLocation auRestoLocationEs = auRestoLocationSearchRepository.findOne(testAuRestoLocation.getId());
        assertThat(auRestoLocationEs).isEqualToComparingFieldByField(testAuRestoLocation);
    }

    @Test
    @Transactional
    public void updateNonExistingAuRestoLocation() throws Exception {
        int databaseSizeBeforeUpdate = auRestoLocationRepository.findAll().size();

        // Create the AuRestoLocation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAuRestoLocationMockMvc.perform(put("/api/au-resto-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoLocation)))
            .andExpect(status().isCreated());

        // Validate the AuRestoLocation in the database
        List<AuRestoLocation> auRestoLocationList = auRestoLocationRepository.findAll();
        assertThat(auRestoLocationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAuRestoLocation() throws Exception {
        // Initialize the database
        auRestoLocationRepository.saveAndFlush(auRestoLocation);
        auRestoLocationSearchRepository.save(auRestoLocation);
        int databaseSizeBeforeDelete = auRestoLocationRepository.findAll().size();

        // Get the auRestoLocation
        restAuRestoLocationMockMvc.perform(delete("/api/au-resto-locations/{id}", auRestoLocation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean auRestoLocationExistsInEs = auRestoLocationSearchRepository.exists(auRestoLocation.getId());
        assertThat(auRestoLocationExistsInEs).isFalse();

        // Validate the database is empty
        List<AuRestoLocation> auRestoLocationList = auRestoLocationRepository.findAll();
        assertThat(auRestoLocationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAuRestoLocation() throws Exception {
        // Initialize the database
        auRestoLocationRepository.saveAndFlush(auRestoLocation);
        auRestoLocationSearchRepository.save(auRestoLocation);

        // Search the auRestoLocation
        restAuRestoLocationMockMvc.perform(get("/api/_search/au-resto-locations?query=id:" + auRestoLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuRestoLocation.class);
        AuRestoLocation auRestoLocation1 = new AuRestoLocation();
        auRestoLocation1.setId(1L);
        AuRestoLocation auRestoLocation2 = new AuRestoLocation();
        auRestoLocation2.setId(auRestoLocation1.getId());
        assertThat(auRestoLocation1).isEqualTo(auRestoLocation2);
        auRestoLocation2.setId(2L);
        assertThat(auRestoLocation1).isNotEqualTo(auRestoLocation2);
        auRestoLocation1.setId(null);
        assertThat(auRestoLocation1).isNotEqualTo(auRestoLocation2);
    }
}
