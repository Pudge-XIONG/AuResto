package io.pudge.auresto.web.rest;

import io.pudge.auresto.AuRestoApp;

import io.pudge.auresto.domain.AuRestoTable;
import io.pudge.auresto.repository.AuRestoTableRepository;
import io.pudge.auresto.repository.search.AuRestoTableSearchRepository;
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
 * Test class for the AuRestoTableResource REST controller.
 *
 * @see AuRestoTableResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuRestoApp.class)
public class AuRestoTableResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_MAX_PLACE_NUM = 1;
    private static final Integer UPDATED_MAX_PLACE_NUM = 2;

    private static final Integer DEFAULT_TAKEN_PLACE_NUM = 1;
    private static final Integer UPDATED_TAKEN_PLACE_NUM = 2;

    private static final Boolean DEFAULT_WINDOW = false;
    private static final Boolean UPDATED_WINDOW = true;

    private static final Boolean DEFAULT_OUTSIDE = false;
    private static final Boolean UPDATED_OUTSIDE = true;

    private static final Integer DEFAULT_FLOOR = 1;
    private static final Integer UPDATED_FLOOR = 2;

    private static final Boolean DEFAULT_AVAILABLE = false;
    private static final Boolean UPDATED_AVAILABLE = true;

    @Autowired
    private AuRestoTableRepository auRestoTableRepository;

    @Autowired
    private AuRestoTableSearchRepository auRestoTableSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAuRestoTableMockMvc;

    private AuRestoTable auRestoTable;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AuRestoTableResource auRestoTableResource = new AuRestoTableResource(auRestoTableRepository, auRestoTableSearchRepository);
        this.restAuRestoTableMockMvc = MockMvcBuilders.standaloneSetup(auRestoTableResource)
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
    public static AuRestoTable createEntity(EntityManager em) {
        AuRestoTable auRestoTable = new AuRestoTable()
            .code(DEFAULT_CODE)
            .maxPlaceNum(DEFAULT_MAX_PLACE_NUM)
            .takenPlaceNum(DEFAULT_TAKEN_PLACE_NUM)
            .window(DEFAULT_WINDOW)
            .outside(DEFAULT_OUTSIDE)
            .floor(DEFAULT_FLOOR)
            .available(DEFAULT_AVAILABLE);
        return auRestoTable;
    }

    @Before
    public void initTest() {
        auRestoTableSearchRepository.deleteAll();
        auRestoTable = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuRestoTable() throws Exception {
        int databaseSizeBeforeCreate = auRestoTableRepository.findAll().size();

        // Create the AuRestoTable
        restAuRestoTableMockMvc.perform(post("/api/au-resto-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoTable)))
            .andExpect(status().isCreated());

        // Validate the AuRestoTable in the database
        List<AuRestoTable> auRestoTableList = auRestoTableRepository.findAll();
        assertThat(auRestoTableList).hasSize(databaseSizeBeforeCreate + 1);
        AuRestoTable testAuRestoTable = auRestoTableList.get(auRestoTableList.size() - 1);
        assertThat(testAuRestoTable.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAuRestoTable.getMaxPlaceNum()).isEqualTo(DEFAULT_MAX_PLACE_NUM);
        assertThat(testAuRestoTable.getTakenPlaceNum()).isEqualTo(DEFAULT_TAKEN_PLACE_NUM);
        assertThat(testAuRestoTable.isWindow()).isEqualTo(DEFAULT_WINDOW);
        assertThat(testAuRestoTable.isOutside()).isEqualTo(DEFAULT_OUTSIDE);
        assertThat(testAuRestoTable.getFloor()).isEqualTo(DEFAULT_FLOOR);
        assertThat(testAuRestoTable.isAvailable()).isEqualTo(DEFAULT_AVAILABLE);

        // Validate the AuRestoTable in Elasticsearch
        AuRestoTable auRestoTableEs = auRestoTableSearchRepository.findOne(testAuRestoTable.getId());
        assertThat(auRestoTableEs).isEqualToComparingFieldByField(testAuRestoTable);
    }

    @Test
    @Transactional
    public void createAuRestoTableWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auRestoTableRepository.findAll().size();

        // Create the AuRestoTable with an existing ID
        auRestoTable.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuRestoTableMockMvc.perform(post("/api/au-resto-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoTable)))
            .andExpect(status().isBadRequest());

        // Validate the AuRestoTable in the database
        List<AuRestoTable> auRestoTableList = auRestoTableRepository.findAll();
        assertThat(auRestoTableList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAuRestoTables() throws Exception {
        // Initialize the database
        auRestoTableRepository.saveAndFlush(auRestoTable);

        // Get all the auRestoTableList
        restAuRestoTableMockMvc.perform(get("/api/au-resto-tables?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoTable.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].maxPlaceNum").value(hasItem(DEFAULT_MAX_PLACE_NUM)))
            .andExpect(jsonPath("$.[*].takenPlaceNum").value(hasItem(DEFAULT_TAKEN_PLACE_NUM)))
            .andExpect(jsonPath("$.[*].window").value(hasItem(DEFAULT_WINDOW.booleanValue())))
            .andExpect(jsonPath("$.[*].outside").value(hasItem(DEFAULT_OUTSIDE.booleanValue())))
            .andExpect(jsonPath("$.[*].floor").value(hasItem(DEFAULT_FLOOR)))
            .andExpect(jsonPath("$.[*].available").value(hasItem(DEFAULT_AVAILABLE.booleanValue())));
    }

    @Test
    @Transactional
    public void getAuRestoTable() throws Exception {
        // Initialize the database
        auRestoTableRepository.saveAndFlush(auRestoTable);

        // Get the auRestoTable
        restAuRestoTableMockMvc.perform(get("/api/au-resto-tables/{id}", auRestoTable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(auRestoTable.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.maxPlaceNum").value(DEFAULT_MAX_PLACE_NUM))
            .andExpect(jsonPath("$.takenPlaceNum").value(DEFAULT_TAKEN_PLACE_NUM))
            .andExpect(jsonPath("$.window").value(DEFAULT_WINDOW.booleanValue()))
            .andExpect(jsonPath("$.outside").value(DEFAULT_OUTSIDE.booleanValue()))
            .andExpect(jsonPath("$.floor").value(DEFAULT_FLOOR))
            .andExpect(jsonPath("$.available").value(DEFAULT_AVAILABLE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAuRestoTable() throws Exception {
        // Get the auRestoTable
        restAuRestoTableMockMvc.perform(get("/api/au-resto-tables/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuRestoTable() throws Exception {
        // Initialize the database
        auRestoTableRepository.saveAndFlush(auRestoTable);
        auRestoTableSearchRepository.save(auRestoTable);
        int databaseSizeBeforeUpdate = auRestoTableRepository.findAll().size();

        // Update the auRestoTable
        AuRestoTable updatedAuRestoTable = auRestoTableRepository.findOne(auRestoTable.getId());
        updatedAuRestoTable
            .code(UPDATED_CODE)
            .maxPlaceNum(UPDATED_MAX_PLACE_NUM)
            .takenPlaceNum(UPDATED_TAKEN_PLACE_NUM)
            .window(UPDATED_WINDOW)
            .outside(UPDATED_OUTSIDE)
            .floor(UPDATED_FLOOR)
            .available(UPDATED_AVAILABLE);

        restAuRestoTableMockMvc.perform(put("/api/au-resto-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAuRestoTable)))
            .andExpect(status().isOk());

        // Validate the AuRestoTable in the database
        List<AuRestoTable> auRestoTableList = auRestoTableRepository.findAll();
        assertThat(auRestoTableList).hasSize(databaseSizeBeforeUpdate);
        AuRestoTable testAuRestoTable = auRestoTableList.get(auRestoTableList.size() - 1);
        assertThat(testAuRestoTable.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAuRestoTable.getMaxPlaceNum()).isEqualTo(UPDATED_MAX_PLACE_NUM);
        assertThat(testAuRestoTable.getTakenPlaceNum()).isEqualTo(UPDATED_TAKEN_PLACE_NUM);
        assertThat(testAuRestoTable.isWindow()).isEqualTo(UPDATED_WINDOW);
        assertThat(testAuRestoTable.isOutside()).isEqualTo(UPDATED_OUTSIDE);
        assertThat(testAuRestoTable.getFloor()).isEqualTo(UPDATED_FLOOR);
        assertThat(testAuRestoTable.isAvailable()).isEqualTo(UPDATED_AVAILABLE);

        // Validate the AuRestoTable in Elasticsearch
        AuRestoTable auRestoTableEs = auRestoTableSearchRepository.findOne(testAuRestoTable.getId());
        assertThat(auRestoTableEs).isEqualToComparingFieldByField(testAuRestoTable);
    }

    @Test
    @Transactional
    public void updateNonExistingAuRestoTable() throws Exception {
        int databaseSizeBeforeUpdate = auRestoTableRepository.findAll().size();

        // Create the AuRestoTable

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAuRestoTableMockMvc.perform(put("/api/au-resto-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoTable)))
            .andExpect(status().isCreated());

        // Validate the AuRestoTable in the database
        List<AuRestoTable> auRestoTableList = auRestoTableRepository.findAll();
        assertThat(auRestoTableList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAuRestoTable() throws Exception {
        // Initialize the database
        auRestoTableRepository.saveAndFlush(auRestoTable);
        auRestoTableSearchRepository.save(auRestoTable);
        int databaseSizeBeforeDelete = auRestoTableRepository.findAll().size();

        // Get the auRestoTable
        restAuRestoTableMockMvc.perform(delete("/api/au-resto-tables/{id}", auRestoTable.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean auRestoTableExistsInEs = auRestoTableSearchRepository.exists(auRestoTable.getId());
        assertThat(auRestoTableExistsInEs).isFalse();

        // Validate the database is empty
        List<AuRestoTable> auRestoTableList = auRestoTableRepository.findAll();
        assertThat(auRestoTableList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAuRestoTable() throws Exception {
        // Initialize the database
        auRestoTableRepository.saveAndFlush(auRestoTable);
        auRestoTableSearchRepository.save(auRestoTable);

        // Search the auRestoTable
        restAuRestoTableMockMvc.perform(get("/api/_search/au-resto-tables?query=id:" + auRestoTable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoTable.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].maxPlaceNum").value(hasItem(DEFAULT_MAX_PLACE_NUM)))
            .andExpect(jsonPath("$.[*].takenPlaceNum").value(hasItem(DEFAULT_TAKEN_PLACE_NUM)))
            .andExpect(jsonPath("$.[*].window").value(hasItem(DEFAULT_WINDOW.booleanValue())))
            .andExpect(jsonPath("$.[*].outside").value(hasItem(DEFAULT_OUTSIDE.booleanValue())))
            .andExpect(jsonPath("$.[*].floor").value(hasItem(DEFAULT_FLOOR)))
            .andExpect(jsonPath("$.[*].available").value(hasItem(DEFAULT_AVAILABLE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuRestoTable.class);
        AuRestoTable auRestoTable1 = new AuRestoTable();
        auRestoTable1.setId(1L);
        AuRestoTable auRestoTable2 = new AuRestoTable();
        auRestoTable2.setId(auRestoTable1.getId());
        assertThat(auRestoTable1).isEqualTo(auRestoTable2);
        auRestoTable2.setId(2L);
        assertThat(auRestoTable1).isNotEqualTo(auRestoTable2);
        auRestoTable1.setId(null);
        assertThat(auRestoTable1).isNotEqualTo(auRestoTable2);
    }
}
