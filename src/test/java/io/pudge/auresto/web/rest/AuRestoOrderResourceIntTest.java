package io.pudge.auresto.web.rest;

import io.pudge.auresto.AuRestoApp;

import io.pudge.auresto.domain.AuRestoOrder;
import io.pudge.auresto.repository.AuRestoOrderRepository;
import io.pudge.auresto.repository.search.AuRestoOrderSearchRepository;
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
 * Test class for the AuRestoOrderResource REST controller.
 *
 * @see AuRestoOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuRestoApp.class)
public class AuRestoOrderResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private AuRestoOrderRepository auRestoOrderRepository;

    @Autowired
    private AuRestoOrderSearchRepository auRestoOrderSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAuRestoOrderMockMvc;

    private AuRestoOrder auRestoOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AuRestoOrderResource auRestoOrderResource = new AuRestoOrderResource(auRestoOrderRepository, auRestoOrderSearchRepository);
        this.restAuRestoOrderMockMvc = MockMvcBuilders.standaloneSetup(auRestoOrderResource)
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
    public static AuRestoOrder createEntity(EntityManager em) {
        AuRestoOrder auRestoOrder = new AuRestoOrder()
            .code(DEFAULT_CODE)
            .date(DEFAULT_DATE);
        return auRestoOrder;
    }

    @Before
    public void initTest() {
        auRestoOrderSearchRepository.deleteAll();
        auRestoOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuRestoOrder() throws Exception {
        int databaseSizeBeforeCreate = auRestoOrderRepository.findAll().size();

        // Create the AuRestoOrder
        restAuRestoOrderMockMvc.perform(post("/api/au-resto-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoOrder)))
            .andExpect(status().isCreated());

        // Validate the AuRestoOrder in the database
        List<AuRestoOrder> auRestoOrderList = auRestoOrderRepository.findAll();
        assertThat(auRestoOrderList).hasSize(databaseSizeBeforeCreate + 1);
        AuRestoOrder testAuRestoOrder = auRestoOrderList.get(auRestoOrderList.size() - 1);
        assertThat(testAuRestoOrder.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAuRestoOrder.getDate()).isEqualTo(DEFAULT_DATE);

        // Validate the AuRestoOrder in Elasticsearch
        AuRestoOrder auRestoOrderEs = auRestoOrderSearchRepository.findOne(testAuRestoOrder.getId());
        assertThat(auRestoOrderEs).isEqualToComparingFieldByField(testAuRestoOrder);
    }

    @Test
    @Transactional
    public void createAuRestoOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auRestoOrderRepository.findAll().size();

        // Create the AuRestoOrder with an existing ID
        auRestoOrder.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuRestoOrderMockMvc.perform(post("/api/au-resto-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoOrder)))
            .andExpect(status().isBadRequest());

        // Validate the AuRestoOrder in the database
        List<AuRestoOrder> auRestoOrderList = auRestoOrderRepository.findAll();
        assertThat(auRestoOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAuRestoOrders() throws Exception {
        // Initialize the database
        auRestoOrderRepository.saveAndFlush(auRestoOrder);

        // Get all the auRestoOrderList
        restAuRestoOrderMockMvc.perform(get("/api/au-resto-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));
    }

    @Test
    @Transactional
    public void getAuRestoOrder() throws Exception {
        // Initialize the database
        auRestoOrderRepository.saveAndFlush(auRestoOrder);

        // Get the auRestoOrder
        restAuRestoOrderMockMvc.perform(get("/api/au-resto-orders/{id}", auRestoOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(auRestoOrder.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingAuRestoOrder() throws Exception {
        // Get the auRestoOrder
        restAuRestoOrderMockMvc.perform(get("/api/au-resto-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuRestoOrder() throws Exception {
        // Initialize the database
        auRestoOrderRepository.saveAndFlush(auRestoOrder);
        auRestoOrderSearchRepository.save(auRestoOrder);
        int databaseSizeBeforeUpdate = auRestoOrderRepository.findAll().size();

        // Update the auRestoOrder
        AuRestoOrder updatedAuRestoOrder = auRestoOrderRepository.findOne(auRestoOrder.getId());
        updatedAuRestoOrder
            .code(UPDATED_CODE)
            .date(UPDATED_DATE);

        restAuRestoOrderMockMvc.perform(put("/api/au-resto-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAuRestoOrder)))
            .andExpect(status().isOk());

        // Validate the AuRestoOrder in the database
        List<AuRestoOrder> auRestoOrderList = auRestoOrderRepository.findAll();
        assertThat(auRestoOrderList).hasSize(databaseSizeBeforeUpdate);
        AuRestoOrder testAuRestoOrder = auRestoOrderList.get(auRestoOrderList.size() - 1);
        assertThat(testAuRestoOrder.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAuRestoOrder.getDate()).isEqualTo(UPDATED_DATE);

        // Validate the AuRestoOrder in Elasticsearch
        AuRestoOrder auRestoOrderEs = auRestoOrderSearchRepository.findOne(testAuRestoOrder.getId());
        assertThat(auRestoOrderEs).isEqualToComparingFieldByField(testAuRestoOrder);
    }

    @Test
    @Transactional
    public void updateNonExistingAuRestoOrder() throws Exception {
        int databaseSizeBeforeUpdate = auRestoOrderRepository.findAll().size();

        // Create the AuRestoOrder

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAuRestoOrderMockMvc.perform(put("/api/au-resto-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoOrder)))
            .andExpect(status().isCreated());

        // Validate the AuRestoOrder in the database
        List<AuRestoOrder> auRestoOrderList = auRestoOrderRepository.findAll();
        assertThat(auRestoOrderList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAuRestoOrder() throws Exception {
        // Initialize the database
        auRestoOrderRepository.saveAndFlush(auRestoOrder);
        auRestoOrderSearchRepository.save(auRestoOrder);
        int databaseSizeBeforeDelete = auRestoOrderRepository.findAll().size();

        // Get the auRestoOrder
        restAuRestoOrderMockMvc.perform(delete("/api/au-resto-orders/{id}", auRestoOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean auRestoOrderExistsInEs = auRestoOrderSearchRepository.exists(auRestoOrder.getId());
        assertThat(auRestoOrderExistsInEs).isFalse();

        // Validate the database is empty
        List<AuRestoOrder> auRestoOrderList = auRestoOrderRepository.findAll();
        assertThat(auRestoOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAuRestoOrder() throws Exception {
        // Initialize the database
        auRestoOrderRepository.saveAndFlush(auRestoOrder);
        auRestoOrderSearchRepository.save(auRestoOrder);

        // Search the auRestoOrder
        restAuRestoOrderMockMvc.perform(get("/api/_search/au-resto-orders?query=id:" + auRestoOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuRestoOrder.class);
        AuRestoOrder auRestoOrder1 = new AuRestoOrder();
        auRestoOrder1.setId(1L);
        AuRestoOrder auRestoOrder2 = new AuRestoOrder();
        auRestoOrder2.setId(auRestoOrder1.getId());
        assertThat(auRestoOrder1).isEqualTo(auRestoOrder2);
        auRestoOrder2.setId(2L);
        assertThat(auRestoOrder1).isNotEqualTo(auRestoOrder2);
        auRestoOrder1.setId(null);
        assertThat(auRestoOrder1).isNotEqualTo(auRestoOrder2);
    }
}
