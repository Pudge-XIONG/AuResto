package io.pudge.auresto.web.rest;

import io.pudge.auresto.AuRestoApp;

import io.pudge.auresto.domain.AuRestoRecipe;
import io.pudge.auresto.repository.AuRestoRecipeRepository;
import io.pudge.auresto.repository.search.AuRestoRecipeSearchRepository;
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
 * Test class for the AuRestoRecipeResource REST controller.
 *
 * @see AuRestoRecipeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuRestoApp.class)
public class AuRestoRecipeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    @Autowired
    private AuRestoRecipeRepository auRestoRecipeRepository;

    @Autowired
    private AuRestoRecipeSearchRepository auRestoRecipeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAuRestoRecipeMockMvc;

    private AuRestoRecipe auRestoRecipe;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AuRestoRecipeResource auRestoRecipeResource = new AuRestoRecipeResource(auRestoRecipeRepository, auRestoRecipeSearchRepository);
        this.restAuRestoRecipeMockMvc = MockMvcBuilders.standaloneSetup(auRestoRecipeResource)
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
    public static AuRestoRecipe createEntity(EntityManager em) {
        AuRestoRecipe auRestoRecipe = new AuRestoRecipe()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .comment(DEFAULT_COMMENT)
            .price(DEFAULT_PRICE);
        return auRestoRecipe;
    }

    @Before
    public void initTest() {
        auRestoRecipeSearchRepository.deleteAll();
        auRestoRecipe = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuRestoRecipe() throws Exception {
        int databaseSizeBeforeCreate = auRestoRecipeRepository.findAll().size();

        // Create the AuRestoRecipe
        restAuRestoRecipeMockMvc.perform(post("/api/au-resto-recipes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoRecipe)))
            .andExpect(status().isCreated());

        // Validate the AuRestoRecipe in the database
        List<AuRestoRecipe> auRestoRecipeList = auRestoRecipeRepository.findAll();
        assertThat(auRestoRecipeList).hasSize(databaseSizeBeforeCreate + 1);
        AuRestoRecipe testAuRestoRecipe = auRestoRecipeList.get(auRestoRecipeList.size() - 1);
        assertThat(testAuRestoRecipe.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAuRestoRecipe.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAuRestoRecipe.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testAuRestoRecipe.getPrice()).isEqualTo(DEFAULT_PRICE);

        // Validate the AuRestoRecipe in Elasticsearch
        AuRestoRecipe auRestoRecipeEs = auRestoRecipeSearchRepository.findOne(testAuRestoRecipe.getId());
        assertThat(auRestoRecipeEs).isEqualToComparingFieldByField(testAuRestoRecipe);
    }

    @Test
    @Transactional
    public void createAuRestoRecipeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auRestoRecipeRepository.findAll().size();

        // Create the AuRestoRecipe with an existing ID
        auRestoRecipe.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuRestoRecipeMockMvc.perform(post("/api/au-resto-recipes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoRecipe)))
            .andExpect(status().isBadRequest());

        // Validate the AuRestoRecipe in the database
        List<AuRestoRecipe> auRestoRecipeList = auRestoRecipeRepository.findAll();
        assertThat(auRestoRecipeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAuRestoRecipes() throws Exception {
        // Initialize the database
        auRestoRecipeRepository.saveAndFlush(auRestoRecipe);

        // Get all the auRestoRecipeList
        restAuRestoRecipeMockMvc.perform(get("/api/au-resto-recipes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoRecipe.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    public void getAuRestoRecipe() throws Exception {
        // Initialize the database
        auRestoRecipeRepository.saveAndFlush(auRestoRecipe);

        // Get the auRestoRecipe
        restAuRestoRecipeMockMvc.perform(get("/api/au-resto-recipes/{id}", auRestoRecipe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(auRestoRecipe.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAuRestoRecipe() throws Exception {
        // Get the auRestoRecipe
        restAuRestoRecipeMockMvc.perform(get("/api/au-resto-recipes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuRestoRecipe() throws Exception {
        // Initialize the database
        auRestoRecipeRepository.saveAndFlush(auRestoRecipe);
        auRestoRecipeSearchRepository.save(auRestoRecipe);
        int databaseSizeBeforeUpdate = auRestoRecipeRepository.findAll().size();

        // Update the auRestoRecipe
        AuRestoRecipe updatedAuRestoRecipe = auRestoRecipeRepository.findOne(auRestoRecipe.getId());
        updatedAuRestoRecipe
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .comment(UPDATED_COMMENT)
            .price(UPDATED_PRICE);

        restAuRestoRecipeMockMvc.perform(put("/api/au-resto-recipes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAuRestoRecipe)))
            .andExpect(status().isOk());

        // Validate the AuRestoRecipe in the database
        List<AuRestoRecipe> auRestoRecipeList = auRestoRecipeRepository.findAll();
        assertThat(auRestoRecipeList).hasSize(databaseSizeBeforeUpdate);
        AuRestoRecipe testAuRestoRecipe = auRestoRecipeList.get(auRestoRecipeList.size() - 1);
        assertThat(testAuRestoRecipe.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAuRestoRecipe.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAuRestoRecipe.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testAuRestoRecipe.getPrice()).isEqualTo(UPDATED_PRICE);

        // Validate the AuRestoRecipe in Elasticsearch
        AuRestoRecipe auRestoRecipeEs = auRestoRecipeSearchRepository.findOne(testAuRestoRecipe.getId());
        assertThat(auRestoRecipeEs).isEqualToComparingFieldByField(testAuRestoRecipe);
    }

    @Test
    @Transactional
    public void updateNonExistingAuRestoRecipe() throws Exception {
        int databaseSizeBeforeUpdate = auRestoRecipeRepository.findAll().size();

        // Create the AuRestoRecipe

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAuRestoRecipeMockMvc.perform(put("/api/au-resto-recipes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auRestoRecipe)))
            .andExpect(status().isCreated());

        // Validate the AuRestoRecipe in the database
        List<AuRestoRecipe> auRestoRecipeList = auRestoRecipeRepository.findAll();
        assertThat(auRestoRecipeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAuRestoRecipe() throws Exception {
        // Initialize the database
        auRestoRecipeRepository.saveAndFlush(auRestoRecipe);
        auRestoRecipeSearchRepository.save(auRestoRecipe);
        int databaseSizeBeforeDelete = auRestoRecipeRepository.findAll().size();

        // Get the auRestoRecipe
        restAuRestoRecipeMockMvc.perform(delete("/api/au-resto-recipes/{id}", auRestoRecipe.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean auRestoRecipeExistsInEs = auRestoRecipeSearchRepository.exists(auRestoRecipe.getId());
        assertThat(auRestoRecipeExistsInEs).isFalse();

        // Validate the database is empty
        List<AuRestoRecipe> auRestoRecipeList = auRestoRecipeRepository.findAll();
        assertThat(auRestoRecipeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAuRestoRecipe() throws Exception {
        // Initialize the database
        auRestoRecipeRepository.saveAndFlush(auRestoRecipe);
        auRestoRecipeSearchRepository.save(auRestoRecipe);

        // Search the auRestoRecipe
        restAuRestoRecipeMockMvc.perform(get("/api/_search/au-resto-recipes?query=id:" + auRestoRecipe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auRestoRecipe.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuRestoRecipe.class);
        AuRestoRecipe auRestoRecipe1 = new AuRestoRecipe();
        auRestoRecipe1.setId(1L);
        AuRestoRecipe auRestoRecipe2 = new AuRestoRecipe();
        auRestoRecipe2.setId(auRestoRecipe1.getId());
        assertThat(auRestoRecipe1).isEqualTo(auRestoRecipe2);
        auRestoRecipe2.setId(2L);
        assertThat(auRestoRecipe1).isNotEqualTo(auRestoRecipe2);
        auRestoRecipe1.setId(null);
        assertThat(auRestoRecipe1).isNotEqualTo(auRestoRecipe2);
    }
}
