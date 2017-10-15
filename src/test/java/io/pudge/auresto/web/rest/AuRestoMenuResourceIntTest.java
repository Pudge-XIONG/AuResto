package io.pudge.auresto.web.rest;

import io.pudge.auresto.AuRestoApp;

import io.pudge.auresto.domain.AuRestoMenu;
import io.pudge.auresto.repository.AuRestoMenuRepository;
import io.pudge.auresto.repository.search.AuRestoMenuSearchRepository;
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
 * Test class for the AuRestoMenuResource REST controller.
 *
 * @see AuRestoMenuResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuRestoApp.class)
public class AuRestoMenuResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private AuRestoMenuRepository auRestoMenuRepository;

    @Autowired
    private AuRestoMenuSearchRepository auRestoMenuSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAuRestoMenuMockMvc;

    private AuRestoMenu auRestoMenu;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AuRestoMenuResource auRestoMenuResource = new AuRestoMenuResource(auRestoMenuRepository, auRestoMenuSearchRepository);
        this.restAuRestoMenuMockMvc = MockMvcBuilders.standaloneSetup(auRestoMenuResource)
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
    public static AuRestoMenu createEntity(EntityManager em) {
        AuRestoMenu auRestoMenu = new AuRestoMenu()
            .title(DEFAULT_TITLE)
            .date(DEFAULT_DATE);
        return auRestoMenu;
    }

    @Before
    public void initTest() {
        auRestoMenuSearchRepository.deleteAll();
        auRestoMenu = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuRestoMenu() throws Exception {
        int databaseSizeBeforeCreate = auRestoMenuRepository.findAll().size();

        // Create the AuRestoMenu
        restAuRestoMenuMockMvc.perform(post("/api/au-resto-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoMenu)))
            .andExpect(status().isCreated());

        // Validate the AuRestoMenu in the database
        List<AuRestoMenu> auRestoMenuList = auRestoMenuRepository.findAll();
        assertThat(auRestoMenuList).hasSize(databaseSizeBeforeCreate + 1);
        AuRestoMenu testAuRestoMenu = auRestoMenuList.get(auRestoMenuList.size() - 1);
        assertThat(testAuRestoMenu.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testAuRestoMenu.getDate()).isEqualTo(DEFAULT_DATE);

        // Validate the AuRestoMenu in Elasticsearch
        AuRestoMenu auRestoMenuEs = auRestoMenuSearchRepository.findOne(testAuRestoMenu.getId());
        assertThat(auRestoMenuEs).isEqualToComparingFieldByField(testAuRestoMenu);
    }

    @Test
    @Transactional
    public void createAuRestoMenuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auRestoMenuRepository.findAll().size();

        // Create the AuRestoMenu with an existing ID
        auRestoMenu.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuRestoMenuMockMvc.perform(post("/api/au-resto-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoMenu)))
            .andExpect(status().isBadRequest());

        // Validate the AuRestoMenu in the database
        List<AuRestoMenu> auRestoMenuList = auRestoMenuRepository.findAll();
        assertThat(auRestoMenuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAuRestoMenus() throws Exception {
        // Initialize the database
        auRestoMenuRepository.saveAndFlush(auRestoMenu);

        // Get all the auRestoMenuList
        restAuRestoMenuMockMvc.perform(get("/api/au-resto-menus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoMenu.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));
    }

    @Test
    @Transactional
    public void getAuRestoMenu() throws Exception {
        // Initialize the database
        auRestoMenuRepository.saveAndFlush(auRestoMenu);

        // Get the auRestoMenu
        restAuRestoMenuMockMvc.perform(get("/api/au-resto-menus/{id}", auRestoMenu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(auRestoMenu.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingAuRestoMenu() throws Exception {
        // Get the auRestoMenu
        restAuRestoMenuMockMvc.perform(get("/api/au-resto-menus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuRestoMenu() throws Exception {
        // Initialize the database
        auRestoMenuRepository.saveAndFlush(auRestoMenu);
        auRestoMenuSearchRepository.save(auRestoMenu);
        int databaseSizeBeforeUpdate = auRestoMenuRepository.findAll().size();

        // Update the auRestoMenu
        AuRestoMenu updatedAuRestoMenu = auRestoMenuRepository.findOne(auRestoMenu.getId());
        updatedAuRestoMenu
            .title(UPDATED_TITLE)
            .date(UPDATED_DATE);

        restAuRestoMenuMockMvc.perform(put("/api/au-resto-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAuRestoMenu)))
            .andExpect(status().isOk());

        // Validate the AuRestoMenu in the database
        List<AuRestoMenu> auRestoMenuList = auRestoMenuRepository.findAll();
        assertThat(auRestoMenuList).hasSize(databaseSizeBeforeUpdate);
        AuRestoMenu testAuRestoMenu = auRestoMenuList.get(auRestoMenuList.size() - 1);
        assertThat(testAuRestoMenu.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testAuRestoMenu.getDate()).isEqualTo(UPDATED_DATE);

        // Validate the AuRestoMenu in Elasticsearch
        AuRestoMenu auRestoMenuEs = auRestoMenuSearchRepository.findOne(testAuRestoMenu.getId());
        assertThat(auRestoMenuEs).isEqualToComparingFieldByField(testAuRestoMenu);
    }

    @Test
    @Transactional
    public void updateNonExistingAuRestoMenu() throws Exception {
        int databaseSizeBeforeUpdate = auRestoMenuRepository.findAll().size();

        // Create the AuRestoMenu

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAuRestoMenuMockMvc.perform(put("/api/au-resto-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoMenu)))
            .andExpect(status().isCreated());

        // Validate the AuRestoMenu in the database
        List<AuRestoMenu> auRestoMenuList = auRestoMenuRepository.findAll();
        assertThat(auRestoMenuList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAuRestoMenu() throws Exception {
        // Initialize the database
        auRestoMenuRepository.saveAndFlush(auRestoMenu);
        auRestoMenuSearchRepository.save(auRestoMenu);
        int databaseSizeBeforeDelete = auRestoMenuRepository.findAll().size();

        // Get the auRestoMenu
        restAuRestoMenuMockMvc.perform(delete("/api/au-resto-menus/{id}", auRestoMenu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean auRestoMenuExistsInEs = auRestoMenuSearchRepository.exists(auRestoMenu.getId());
        assertThat(auRestoMenuExistsInEs).isFalse();

        // Validate the database is empty
        List<AuRestoMenu> auRestoMenuList = auRestoMenuRepository.findAll();
        assertThat(auRestoMenuList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAuRestoMenu() throws Exception {
        // Initialize the database
        auRestoMenuRepository.saveAndFlush(auRestoMenu);
        auRestoMenuSearchRepository.save(auRestoMenu);

        // Search the auRestoMenu
        restAuRestoMenuMockMvc.perform(get("/api/_search/au-resto-menus?query=id:" + auRestoMenu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoMenu.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuRestoMenu.class);
        AuRestoMenu auRestoMenu1 = new AuRestoMenu();
        auRestoMenu1.setId(1L);
        AuRestoMenu auRestoMenu2 = new AuRestoMenu();
        auRestoMenu2.setId(auRestoMenu1.getId());
        assertThat(auRestoMenu1).isEqualTo(auRestoMenu2);
        auRestoMenu2.setId(2L);
        assertThat(auRestoMenu1).isNotEqualTo(auRestoMenu2);
        auRestoMenu1.setId(null);
        assertThat(auRestoMenu1).isNotEqualTo(auRestoMenu2);
    }
}
