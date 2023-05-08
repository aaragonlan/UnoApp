package es.proyecto.uno.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import es.proyecto.uno.IntegrationTest;
import es.proyecto.uno.domain.Tab1;
import es.proyecto.uno.repository.Tab1Repository;
import es.proyecto.uno.service.dto.Tab1DTO;
import es.proyecto.uno.service.mapper.Tab1Mapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link Tab1Resource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class Tab1ResourceIT {

    private static final String DEFAULT_COL_1 = "AAAAAAAAAA";
    private static final String UPDATED_COL_1 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_2 = "AAAAAAAAAA";
    private static final String UPDATED_COL_2 = "BBBBBBBBBB";

    private static final String DEFAULT_COL_3 = "AAAAAAAAAA";
    private static final String UPDATED_COL_3 = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tab-1-s";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private Tab1Repository tab1Repository;

    @Autowired
    private Tab1Mapper tab1Mapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTab1MockMvc;

    private Tab1 tab1;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tab1 createEntity(EntityManager em) {
        Tab1 tab1 = new Tab1().col1(DEFAULT_COL_1).col2(DEFAULT_COL_2).col3(DEFAULT_COL_3);
        return tab1;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tab1 createUpdatedEntity(EntityManager em) {
        Tab1 tab1 = new Tab1().col1(UPDATED_COL_1).col2(UPDATED_COL_2).col3(UPDATED_COL_3);
        return tab1;
    }

    @BeforeEach
    public void initTest() {
        tab1 = createEntity(em);
    }

    @Test
    @Transactional
    void createTab1() throws Exception {
        int databaseSizeBeforeCreate = tab1Repository.findAll().size();
        // Create the Tab1
        Tab1DTO tab1DTO = tab1Mapper.toDto(tab1);
        restTab1MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tab1DTO)))
            .andExpect(status().isCreated());

        // Validate the Tab1 in the database
        List<Tab1> tab1List = tab1Repository.findAll();
        assertThat(tab1List).hasSize(databaseSizeBeforeCreate + 1);
        Tab1 testTab1 = tab1List.get(tab1List.size() - 1);
        assertThat(testTab1.getCol1()).isEqualTo(DEFAULT_COL_1);
        assertThat(testTab1.getCol2()).isEqualTo(DEFAULT_COL_2);
        assertThat(testTab1.getCol3()).isEqualTo(DEFAULT_COL_3);
    }

    @Test
    @Transactional
    void createTab1WithExistingId() throws Exception {
        // Create the Tab1 with an existing ID
        tab1.setId(1L);
        Tab1DTO tab1DTO = tab1Mapper.toDto(tab1);

        int databaseSizeBeforeCreate = tab1Repository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTab1MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tab1DTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tab1 in the database
        List<Tab1> tab1List = tab1Repository.findAll();
        assertThat(tab1List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTab1s() throws Exception {
        // Initialize the database
        tab1Repository.saveAndFlush(tab1);

        // Get all the tab1List
        restTab1MockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tab1.getId().intValue())))
            .andExpect(jsonPath("$.[*].col1").value(hasItem(DEFAULT_COL_1)))
            .andExpect(jsonPath("$.[*].col2").value(hasItem(DEFAULT_COL_2)))
            .andExpect(jsonPath("$.[*].col3").value(hasItem(DEFAULT_COL_3)));
    }

    @Test
    @Transactional
    void getTab1() throws Exception {
        // Initialize the database
        tab1Repository.saveAndFlush(tab1);

        // Get the tab1
        restTab1MockMvc
            .perform(get(ENTITY_API_URL_ID, tab1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tab1.getId().intValue()))
            .andExpect(jsonPath("$.col1").value(DEFAULT_COL_1))
            .andExpect(jsonPath("$.col2").value(DEFAULT_COL_2))
            .andExpect(jsonPath("$.col3").value(DEFAULT_COL_3));
    }

    @Test
    @Transactional
    void getNonExistingTab1() throws Exception {
        // Get the tab1
        restTab1MockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTab1() throws Exception {
        // Initialize the database
        tab1Repository.saveAndFlush(tab1);

        int databaseSizeBeforeUpdate = tab1Repository.findAll().size();

        // Update the tab1
        Tab1 updatedTab1 = tab1Repository.findById(tab1.getId()).get();
        // Disconnect from session so that the updates on updatedTab1 are not directly saved in db
        em.detach(updatedTab1);
        updatedTab1.col1(UPDATED_COL_1).col2(UPDATED_COL_2).col3(UPDATED_COL_3);
        Tab1DTO tab1DTO = tab1Mapper.toDto(updatedTab1);

        restTab1MockMvc
            .perform(
                put(ENTITY_API_URL_ID, tab1DTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tab1DTO))
            )
            .andExpect(status().isOk());

        // Validate the Tab1 in the database
        List<Tab1> tab1List = tab1Repository.findAll();
        assertThat(tab1List).hasSize(databaseSizeBeforeUpdate);
        Tab1 testTab1 = tab1List.get(tab1List.size() - 1);
        assertThat(testTab1.getCol1()).isEqualTo(UPDATED_COL_1);
        assertThat(testTab1.getCol2()).isEqualTo(UPDATED_COL_2);
        assertThat(testTab1.getCol3()).isEqualTo(UPDATED_COL_3);
    }

    @Test
    @Transactional
    void putNonExistingTab1() throws Exception {
        int databaseSizeBeforeUpdate = tab1Repository.findAll().size();
        tab1.setId(count.incrementAndGet());

        // Create the Tab1
        Tab1DTO tab1DTO = tab1Mapper.toDto(tab1);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTab1MockMvc
            .perform(
                put(ENTITY_API_URL_ID, tab1DTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tab1DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tab1 in the database
        List<Tab1> tab1List = tab1Repository.findAll();
        assertThat(tab1List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTab1() throws Exception {
        int databaseSizeBeforeUpdate = tab1Repository.findAll().size();
        tab1.setId(count.incrementAndGet());

        // Create the Tab1
        Tab1DTO tab1DTO = tab1Mapper.toDto(tab1);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTab1MockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tab1DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tab1 in the database
        List<Tab1> tab1List = tab1Repository.findAll();
        assertThat(tab1List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTab1() throws Exception {
        int databaseSizeBeforeUpdate = tab1Repository.findAll().size();
        tab1.setId(count.incrementAndGet());

        // Create the Tab1
        Tab1DTO tab1DTO = tab1Mapper.toDto(tab1);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTab1MockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tab1DTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tab1 in the database
        List<Tab1> tab1List = tab1Repository.findAll();
        assertThat(tab1List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTab1WithPatch() throws Exception {
        // Initialize the database
        tab1Repository.saveAndFlush(tab1);

        int databaseSizeBeforeUpdate = tab1Repository.findAll().size();

        // Update the tab1 using partial update
        Tab1 partialUpdatedTab1 = new Tab1();
        partialUpdatedTab1.setId(tab1.getId());

        partialUpdatedTab1.col3(UPDATED_COL_3);

        restTab1MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTab1.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTab1))
            )
            .andExpect(status().isOk());

        // Validate the Tab1 in the database
        List<Tab1> tab1List = tab1Repository.findAll();
        assertThat(tab1List).hasSize(databaseSizeBeforeUpdate);
        Tab1 testTab1 = tab1List.get(tab1List.size() - 1);
        assertThat(testTab1.getCol1()).isEqualTo(DEFAULT_COL_1);
        assertThat(testTab1.getCol2()).isEqualTo(DEFAULT_COL_2);
        assertThat(testTab1.getCol3()).isEqualTo(UPDATED_COL_3);
    }

    @Test
    @Transactional
    void fullUpdateTab1WithPatch() throws Exception {
        // Initialize the database
        tab1Repository.saveAndFlush(tab1);

        int databaseSizeBeforeUpdate = tab1Repository.findAll().size();

        // Update the tab1 using partial update
        Tab1 partialUpdatedTab1 = new Tab1();
        partialUpdatedTab1.setId(tab1.getId());

        partialUpdatedTab1.col1(UPDATED_COL_1).col2(UPDATED_COL_2).col3(UPDATED_COL_3);

        restTab1MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTab1.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTab1))
            )
            .andExpect(status().isOk());

        // Validate the Tab1 in the database
        List<Tab1> tab1List = tab1Repository.findAll();
        assertThat(tab1List).hasSize(databaseSizeBeforeUpdate);
        Tab1 testTab1 = tab1List.get(tab1List.size() - 1);
        assertThat(testTab1.getCol1()).isEqualTo(UPDATED_COL_1);
        assertThat(testTab1.getCol2()).isEqualTo(UPDATED_COL_2);
        assertThat(testTab1.getCol3()).isEqualTo(UPDATED_COL_3);
    }

    @Test
    @Transactional
    void patchNonExistingTab1() throws Exception {
        int databaseSizeBeforeUpdate = tab1Repository.findAll().size();
        tab1.setId(count.incrementAndGet());

        // Create the Tab1
        Tab1DTO tab1DTO = tab1Mapper.toDto(tab1);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTab1MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tab1DTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tab1DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tab1 in the database
        List<Tab1> tab1List = tab1Repository.findAll();
        assertThat(tab1List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTab1() throws Exception {
        int databaseSizeBeforeUpdate = tab1Repository.findAll().size();
        tab1.setId(count.incrementAndGet());

        // Create the Tab1
        Tab1DTO tab1DTO = tab1Mapper.toDto(tab1);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTab1MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tab1DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tab1 in the database
        List<Tab1> tab1List = tab1Repository.findAll();
        assertThat(tab1List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTab1() throws Exception {
        int databaseSizeBeforeUpdate = tab1Repository.findAll().size();
        tab1.setId(count.incrementAndGet());

        // Create the Tab1
        Tab1DTO tab1DTO = tab1Mapper.toDto(tab1);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTab1MockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tab1DTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tab1 in the database
        List<Tab1> tab1List = tab1Repository.findAll();
        assertThat(tab1List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTab1() throws Exception {
        // Initialize the database
        tab1Repository.saveAndFlush(tab1);

        int databaseSizeBeforeDelete = tab1Repository.findAll().size();

        // Delete the tab1
        restTab1MockMvc
            .perform(delete(ENTITY_API_URL_ID, tab1.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tab1> tab1List = tab1Repository.findAll();
        assertThat(tab1List).hasSize(databaseSizeBeforeDelete - 1);
    }
}
