package io.pudge.auresto.web.rest;

import io.pudge.auresto.AuRestoApp;

import io.pudge.auresto.domain.AuRestoProfile;
import io.pudge.auresto.repository.AuRestoProfileRepository;
import io.pudge.auresto.repository.search.AuRestoProfileSearchRepository;
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
 * Test class for the AuRestoProfileResource REST controller.
 *
 * @see AuRestoProfileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuRestoApp.class)
public class AuRestoProfileResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private AuRestoProfileRepository auRestoProfileRepository;

    @Autowired
    private AuRestoProfileSearchRepository auRestoProfileSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAuRestoProfileMockMvc;

    private AuRestoProfile auRestoProfile;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AuRestoProfileResource auRestoProfileResource = new AuRestoProfileResource(auRestoProfileRepository, auRestoProfileSearchRepository);
        this.restAuRestoProfileMockMvc = MockMvcBuilders.standaloneSetup(auRestoProfileResource)
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
    public static AuRestoProfile createEntity(EntityManager em) {
        AuRestoProfile auRestoProfile = new AuRestoProfile()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME);
        return auRestoProfile;
    }

    @Before
    public void initTest() {
        auRestoProfileSearchRepository.deleteAll();
        auRestoProfile = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuRestoProfile() throws Exception {
        int databaseSizeBeforeCreate = auRestoProfileRepository.findAll().size();

        // Create the AuRestoProfile
        restAuRestoProfileMockMvc.perform(post("/api/au-resto-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoProfile)))
            .andExpect(status().isCreated());

        // Validate the AuRestoProfile in the database
        List<AuRestoProfile> auRestoProfileList = auRestoProfileRepository.findAll();
        assertThat(auRestoProfileList).hasSize(databaseSizeBeforeCreate + 1);
        AuRestoProfile testAuRestoProfile = auRestoProfileList.get(auRestoProfileList.size() - 1);
        assertThat(testAuRestoProfile.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAuRestoProfile.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the AuRestoProfile in Elasticsearch
        AuRestoProfile auRestoProfileEs = auRestoProfileSearchRepository.findOne(testAuRestoProfile.getId());
        assertThat(auRestoProfileEs).isEqualToComparingFieldByField(testAuRestoProfile);
    }

    @Test
    @Transactional
    public void createAuRestoProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auRestoProfileRepository.findAll().size();

        // Create the AuRestoProfile with an existing ID
        auRestoProfile.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuRestoProfileMockMvc.perform(post("/api/au-resto-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoProfile)))
            .andExpect(status().isBadRequest());

        // Validate the AuRestoProfile in the database
        List<AuRestoProfile> auRestoProfileList = auRestoProfileRepository.findAll();
        assertThat(auRestoProfileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAuRestoProfiles() throws Exception {
        // Initialize the database
        auRestoProfileRepository.saveAndFlush(auRestoProfile);

        // Get all the auRestoProfileList
        restAuRestoProfileMockMvc.perform(get("/api/au-resto-profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getAuRestoProfile() throws Exception {
        // Initialize the database
        auRestoProfileRepository.saveAndFlush(auRestoProfile);

        // Get the auRestoProfile
        restAuRestoProfileMockMvc.perform(get("/api/au-resto-profiles/{id}", auRestoProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(auRestoProfile.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAuRestoProfile() throws Exception {
        // Get the auRestoProfile
        restAuRestoProfileMockMvc.perform(get("/api/au-resto-profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuRestoProfile() throws Exception {
        // Initialize the database
        auRestoProfileRepository.saveAndFlush(auRestoProfile);
        auRestoProfileSearchRepository.save(auRestoProfile);
        int databaseSizeBeforeUpdate = auRestoProfileRepository.findAll().size();

        // Update the auRestoProfile
        AuRestoProfile updatedAuRestoProfile = auRestoProfileRepository.findOne(auRestoProfile.getId());
        updatedAuRestoProfile
            .code(UPDATED_CODE)
            .name(UPDATED_NAME);

        restAuRestoProfileMockMvc.perform(put("/api/au-resto-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAuRestoProfile)))
            .andExpect(status().isOk());

        // Validate the AuRestoProfile in the database
        List<AuRestoProfile> auRestoProfileList = auRestoProfileRepository.findAll();
        assertThat(auRestoProfileList).hasSize(databaseSizeBeforeUpdate);
        AuRestoProfile testAuRestoProfile = auRestoProfileList.get(auRestoProfileList.size() - 1);
        assertThat(testAuRestoProfile.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAuRestoProfile.getName()).isEqualTo(UPDATED_NAME);

        // Validate the AuRestoProfile in Elasticsearch
        AuRestoProfile auRestoProfileEs = auRestoProfileSearchRepository.findOne(testAuRestoProfile.getId());
        assertThat(auRestoProfileEs).isEqualToComparingFieldByField(testAuRestoProfile);
    }

    @Test
    @Transactional
    public void updateNonExistingAuRestoProfile() throws Exception {
        int databaseSizeBeforeUpdate = auRestoProfileRepository.findAll().size();

        // Create the AuRestoProfile

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAuRestoProfileMockMvc.perform(put("/api/au-resto-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoProfile)))
            .andExpect(status().isCreated());

        // Validate the AuRestoProfile in the database
        List<AuRestoProfile> auRestoProfileList = auRestoProfileRepository.findAll();
        assertThat(auRestoProfileList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAuRestoProfile() throws Exception {
        // Initialize the database
        auRestoProfileRepository.saveAndFlush(auRestoProfile);
        auRestoProfileSearchRepository.save(auRestoProfile);
        int databaseSizeBeforeDelete = auRestoProfileRepository.findAll().size();

        // Get the auRestoProfile
        restAuRestoProfileMockMvc.perform(delete("/api/au-resto-profiles/{id}", auRestoProfile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean auRestoProfileExistsInEs = auRestoProfileSearchRepository.exists(auRestoProfile.getId());
        assertThat(auRestoProfileExistsInEs).isFalse();

        // Validate the database is empty
        List<AuRestoProfile> auRestoProfileList = auRestoProfileRepository.findAll();
        assertThat(auRestoProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAuRestoProfile() throws Exception {
        // Initialize the database
        auRestoProfileRepository.saveAndFlush(auRestoProfile);
        auRestoProfileSearchRepository.save(auRestoProfile);

        // Search the auRestoProfile
        restAuRestoProfileMockMvc.perform(get("/api/_search/au-resto-profiles?query=id:" + auRestoProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuRestoProfile.class);
        AuRestoProfile auRestoProfile1 = new AuRestoProfile();
        auRestoProfile1.setId(1L);
        AuRestoProfile auRestoProfile2 = new AuRestoProfile();
        auRestoProfile2.setId(auRestoProfile1.getId());
        assertThat(auRestoProfile1).isEqualTo(auRestoProfile2);
        auRestoProfile2.setId(2L);
        assertThat(auRestoProfile1).isNotEqualTo(auRestoProfile2);
        auRestoProfile1.setId(null);
        assertThat(auRestoProfile1).isNotEqualTo(auRestoProfile2);
    }
}
