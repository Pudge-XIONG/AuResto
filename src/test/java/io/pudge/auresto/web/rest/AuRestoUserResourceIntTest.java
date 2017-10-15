package io.pudge.auresto.web.rest;

import io.pudge.auresto.AuRestoApp;

import io.pudge.auresto.domain.AuRestoUser;
import io.pudge.auresto.repository.AuRestoUserRepository;
import io.pudge.auresto.repository.search.AuRestoUserSearchRepository;
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
 * Test class for the AuRestoUserResource REST controller.
 *
 * @see AuRestoUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuRestoApp.class)
public class AuRestoUserResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    @Autowired
    private AuRestoUserRepository auRestoUserRepository;

    @Autowired
    private AuRestoUserSearchRepository auRestoUserSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAuRestoUserMockMvc;

    private AuRestoUser auRestoUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AuRestoUserResource auRestoUserResource = new AuRestoUserResource(auRestoUserRepository, auRestoUserSearchRepository);
        this.restAuRestoUserMockMvc = MockMvcBuilders.standaloneSetup(auRestoUserResource)
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
    public static AuRestoUser createEntity(EntityManager em) {
        AuRestoUser auRestoUser = new AuRestoUser()
            .firstName(DEFAULT_FIRST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .login(DEFAULT_LOGIN)
            .password(DEFAULT_PASSWORD);
        return auRestoUser;
    }

    @Before
    public void initTest() {
        auRestoUserSearchRepository.deleteAll();
        auRestoUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuRestoUser() throws Exception {
        int databaseSizeBeforeCreate = auRestoUserRepository.findAll().size();

        // Create the AuRestoUser
        restAuRestoUserMockMvc.perform(post("/api/au-resto-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoUser)))
            .andExpect(status().isCreated());

        // Validate the AuRestoUser in the database
        List<AuRestoUser> auRestoUserList = auRestoUserRepository.findAll();
        assertThat(auRestoUserList).hasSize(databaseSizeBeforeCreate + 1);
        AuRestoUser testAuRestoUser = auRestoUserList.get(auRestoUserList.size() - 1);
        assertThat(testAuRestoUser.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testAuRestoUser.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testAuRestoUser.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testAuRestoUser.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testAuRestoUser.getPassword()).isEqualTo(DEFAULT_PASSWORD);

        // Validate the AuRestoUser in Elasticsearch
        AuRestoUser auRestoUserEs = auRestoUserSearchRepository.findOne(testAuRestoUser.getId());
        assertThat(auRestoUserEs).isEqualToComparingFieldByField(testAuRestoUser);
    }

    @Test
    @Transactional
    public void createAuRestoUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auRestoUserRepository.findAll().size();

        // Create the AuRestoUser with an existing ID
        auRestoUser.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuRestoUserMockMvc.perform(post("/api/au-resto-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoUser)))
            .andExpect(status().isBadRequest());

        // Validate the AuRestoUser in the database
        List<AuRestoUser> auRestoUserList = auRestoUserRepository.findAll();
        assertThat(auRestoUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAuRestoUsers() throws Exception {
        // Initialize the database
        auRestoUserRepository.saveAndFlush(auRestoUser);

        // Get all the auRestoUserList
        restAuRestoUserMockMvc.perform(get("/api/au-resto-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())));
    }

    @Test
    @Transactional
    public void getAuRestoUser() throws Exception {
        // Initialize the database
        auRestoUserRepository.saveAndFlush(auRestoUser);

        // Get the auRestoUser
        restAuRestoUserMockMvc.perform(get("/api/au-resto-users/{id}", auRestoUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(auRestoUser.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAuRestoUser() throws Exception {
        // Get the auRestoUser
        restAuRestoUserMockMvc.perform(get("/api/au-resto-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuRestoUser() throws Exception {
        // Initialize the database
        auRestoUserRepository.saveAndFlush(auRestoUser);
        auRestoUserSearchRepository.save(auRestoUser);
        int databaseSizeBeforeUpdate = auRestoUserRepository.findAll().size();

        // Update the auRestoUser
        AuRestoUser updatedAuRestoUser = auRestoUserRepository.findOne(auRestoUser.getId());
        updatedAuRestoUser
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .login(UPDATED_LOGIN)
            .password(UPDATED_PASSWORD);

        restAuRestoUserMockMvc.perform(put("/api/au-resto-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAuRestoUser)))
            .andExpect(status().isOk());

        // Validate the AuRestoUser in the database
        List<AuRestoUser> auRestoUserList = auRestoUserRepository.findAll();
        assertThat(auRestoUserList).hasSize(databaseSizeBeforeUpdate);
        AuRestoUser testAuRestoUser = auRestoUserList.get(auRestoUserList.size() - 1);
        assertThat(testAuRestoUser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testAuRestoUser.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testAuRestoUser.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testAuRestoUser.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testAuRestoUser.getPassword()).isEqualTo(UPDATED_PASSWORD);

        // Validate the AuRestoUser in Elasticsearch
        AuRestoUser auRestoUserEs = auRestoUserSearchRepository.findOne(testAuRestoUser.getId());
        assertThat(auRestoUserEs).isEqualToComparingFieldByField(testAuRestoUser);
    }

    @Test
    @Transactional
    public void updateNonExistingAuRestoUser() throws Exception {
        int databaseSizeBeforeUpdate = auRestoUserRepository.findAll().size();

        // Create the AuRestoUser

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAuRestoUserMockMvc.perform(put("/api/au-resto-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoUser)))
            .andExpect(status().isCreated());

        // Validate the AuRestoUser in the database
        List<AuRestoUser> auRestoUserList = auRestoUserRepository.findAll();
        assertThat(auRestoUserList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAuRestoUser() throws Exception {
        // Initialize the database
        auRestoUserRepository.saveAndFlush(auRestoUser);
        auRestoUserSearchRepository.save(auRestoUser);
        int databaseSizeBeforeDelete = auRestoUserRepository.findAll().size();

        // Get the auRestoUser
        restAuRestoUserMockMvc.perform(delete("/api/au-resto-users/{id}", auRestoUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean auRestoUserExistsInEs = auRestoUserSearchRepository.exists(auRestoUser.getId());
        assertThat(auRestoUserExistsInEs).isFalse();

        // Validate the database is empty
        List<AuRestoUser> auRestoUserList = auRestoUserRepository.findAll();
        assertThat(auRestoUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAuRestoUser() throws Exception {
        // Initialize the database
        auRestoUserRepository.saveAndFlush(auRestoUser);
        auRestoUserSearchRepository.save(auRestoUser);

        // Search the auRestoUser
        restAuRestoUserMockMvc.perform(get("/api/_search/au-resto-users?query=id:" + auRestoUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuRestoUser.class);
        AuRestoUser auRestoUser1 = new AuRestoUser();
        auRestoUser1.setId(1L);
        AuRestoUser auRestoUser2 = new AuRestoUser();
        auRestoUser2.setId(auRestoUser1.getId());
        assertThat(auRestoUser1).isEqualTo(auRestoUser2);
        auRestoUser2.setId(2L);
        assertThat(auRestoUser1).isNotEqualTo(auRestoUser2);
        auRestoUser1.setId(null);
        assertThat(auRestoUser1).isNotEqualTo(auRestoUser2);
    }
}
