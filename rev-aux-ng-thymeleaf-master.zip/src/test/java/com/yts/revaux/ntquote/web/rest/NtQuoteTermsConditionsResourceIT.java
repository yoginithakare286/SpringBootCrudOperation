package com.yts.revaux.ntquote.web.rest;

import static com.yts.revaux.ntquote.domain.NtQuoteTermsConditionsAsserts.*;
import static com.yts.revaux.ntquote.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yts.revaux.ntquote.IntegrationTest;
import com.yts.revaux.ntquote.domain.NtQuoteTermsConditions;
import com.yts.revaux.ntquote.repository.NtQuoteTermsConditionsRepository;
import com.yts.revaux.ntquote.repository.search.NtQuoteTermsConditionsSearchRepository;
import com.yts.revaux.ntquote.service.dto.NtQuoteTermsConditionsDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuoteTermsConditionsMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.assertj.core.util.IterableUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.util.Streamable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link NtQuoteTermsConditionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NtQuoteTermsConditionsResourceIT {

    private static final Integer DEFAULT_SR_NO = 1;
    private static final Integer UPDATED_SR_NO = 2;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final String DEFAULT_TERMS_CONDITIONS = "AAAAAAAAAA";
    private static final String UPDATED_TERMS_CONDITIONS = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/nt-quote-terms-conditions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/nt-quote-terms-conditions/_search";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NtQuoteTermsConditionsRepository ntQuoteTermsConditionsRepository;

    @Autowired
    private NtQuoteTermsConditionsMapper ntQuoteTermsConditionsMapper;

    @Autowired
    private NtQuoteTermsConditionsSearchRepository ntQuoteTermsConditionsSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNtQuoteTermsConditionsMockMvc;

    private NtQuoteTermsConditions ntQuoteTermsConditions;

    private NtQuoteTermsConditions insertedNtQuoteTermsConditions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NtQuoteTermsConditions createEntity() {
        return new NtQuoteTermsConditions()
            .srNo(DEFAULT_SR_NO)
            .uid(DEFAULT_UID)
            .termsConditions(DEFAULT_TERMS_CONDITIONS)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedDate(DEFAULT_UPDATED_DATE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NtQuoteTermsConditions createUpdatedEntity() {
        return new NtQuoteTermsConditions()
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .termsConditions(UPDATED_TERMS_CONDITIONS)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);
    }

    @BeforeEach
    public void initTest() {
        ntQuoteTermsConditions = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedNtQuoteTermsConditions != null) {
            ntQuoteTermsConditionsRepository.delete(insertedNtQuoteTermsConditions);
            ntQuoteTermsConditionsSearchRepository.delete(insertedNtQuoteTermsConditions);
            insertedNtQuoteTermsConditions = null;
        }
    }

    @Test
    @Transactional
    void createNtQuoteTermsConditions() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteTermsConditionsSearchRepository.findAll());
        // Create the NtQuoteTermsConditions
        NtQuoteTermsConditionsDTO ntQuoteTermsConditionsDTO = ntQuoteTermsConditionsMapper.toDto(ntQuoteTermsConditions);
        var returnedNtQuoteTermsConditionsDTO = om.readValue(
            restNtQuoteTermsConditionsMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteTermsConditionsDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            NtQuoteTermsConditionsDTO.class
        );

        // Validate the NtQuoteTermsConditions in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedNtQuoteTermsConditions = ntQuoteTermsConditionsMapper.toEntity(returnedNtQuoteTermsConditionsDTO);
        assertNtQuoteTermsConditionsUpdatableFieldsEquals(
            returnedNtQuoteTermsConditions,
            getPersistedNtQuoteTermsConditions(returnedNtQuoteTermsConditions)
        );

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteTermsConditionsSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });

        insertedNtQuoteTermsConditions = returnedNtQuoteTermsConditions;
    }

    @Test
    @Transactional
    void createNtQuoteTermsConditionsWithExistingId() throws Exception {
        // Create the NtQuoteTermsConditions with an existing ID
        ntQuoteTermsConditions.setId(1L);
        NtQuoteTermsConditionsDTO ntQuoteTermsConditionsDTO = ntQuoteTermsConditionsMapper.toDto(ntQuoteTermsConditions);

        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteTermsConditionsSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restNtQuoteTermsConditionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteTermsConditionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteTermsConditions in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteTermsConditionsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkUidIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteTermsConditionsSearchRepository.findAll());
        // set the field null
        ntQuoteTermsConditions.setUid(null);

        // Create the NtQuoteTermsConditions, which fails.
        NtQuoteTermsConditionsDTO ntQuoteTermsConditionsDTO = ntQuoteTermsConditionsMapper.toDto(ntQuoteTermsConditions);

        restNtQuoteTermsConditionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteTermsConditionsDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteTermsConditionsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllNtQuoteTermsConditions() throws Exception {
        // Initialize the database
        insertedNtQuoteTermsConditions = ntQuoteTermsConditionsRepository.saveAndFlush(ntQuoteTermsConditions);

        // Get all the ntQuoteTermsConditionsList
        restNtQuoteTermsConditionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuoteTermsConditions.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].termsConditions").value(hasItem(DEFAULT_TERMS_CONDITIONS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getNtQuoteTermsConditions() throws Exception {
        // Initialize the database
        insertedNtQuoteTermsConditions = ntQuoteTermsConditionsRepository.saveAndFlush(ntQuoteTermsConditions);

        // Get the ntQuoteTermsConditions
        restNtQuoteTermsConditionsMockMvc
            .perform(get(ENTITY_API_URL_ID, ntQuoteTermsConditions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ntQuoteTermsConditions.getId().intValue()))
            .andExpect(jsonPath("$.srNo").value(DEFAULT_SR_NO))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.termsConditions").value(DEFAULT_TERMS_CONDITIONS))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingNtQuoteTermsConditions() throws Exception {
        // Get the ntQuoteTermsConditions
        restNtQuoteTermsConditionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNtQuoteTermsConditions() throws Exception {
        // Initialize the database
        insertedNtQuoteTermsConditions = ntQuoteTermsConditionsRepository.saveAndFlush(ntQuoteTermsConditions);

        long databaseSizeBeforeUpdate = getRepositoryCount();
        ntQuoteTermsConditionsSearchRepository.save(ntQuoteTermsConditions);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteTermsConditionsSearchRepository.findAll());

        // Update the ntQuoteTermsConditions
        NtQuoteTermsConditions updatedNtQuoteTermsConditions = ntQuoteTermsConditionsRepository
            .findById(ntQuoteTermsConditions.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedNtQuoteTermsConditions are not directly saved in db
        em.detach(updatedNtQuoteTermsConditions);
        updatedNtQuoteTermsConditions
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .termsConditions(UPDATED_TERMS_CONDITIONS)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);
        NtQuoteTermsConditionsDTO ntQuoteTermsConditionsDTO = ntQuoteTermsConditionsMapper.toDto(updatedNtQuoteTermsConditions);

        restNtQuoteTermsConditionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ntQuoteTermsConditionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteTermsConditionsDTO))
            )
            .andExpect(status().isOk());

        // Validate the NtQuoteTermsConditions in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedNtQuoteTermsConditionsToMatchAllProperties(updatedNtQuoteTermsConditions);

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteTermsConditionsSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<NtQuoteTermsConditions> ntQuoteTermsConditionsSearchList = Streamable.of(
                    ntQuoteTermsConditionsSearchRepository.findAll()
                ).toList();
                NtQuoteTermsConditions testNtQuoteTermsConditionsSearch = ntQuoteTermsConditionsSearchList.get(searchDatabaseSizeAfter - 1);

                assertNtQuoteTermsConditionsAllPropertiesEquals(testNtQuoteTermsConditionsSearch, updatedNtQuoteTermsConditions);
            });
    }

    @Test
    @Transactional
    void putNonExistingNtQuoteTermsConditions() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteTermsConditionsSearchRepository.findAll());
        ntQuoteTermsConditions.setId(longCount.incrementAndGet());

        // Create the NtQuoteTermsConditions
        NtQuoteTermsConditionsDTO ntQuoteTermsConditionsDTO = ntQuoteTermsConditionsMapper.toDto(ntQuoteTermsConditions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNtQuoteTermsConditionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ntQuoteTermsConditionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteTermsConditionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteTermsConditions in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteTermsConditionsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchNtQuoteTermsConditions() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteTermsConditionsSearchRepository.findAll());
        ntQuoteTermsConditions.setId(longCount.incrementAndGet());

        // Create the NtQuoteTermsConditions
        NtQuoteTermsConditionsDTO ntQuoteTermsConditionsDTO = ntQuoteTermsConditionsMapper.toDto(ntQuoteTermsConditions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteTermsConditionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteTermsConditionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteTermsConditions in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteTermsConditionsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNtQuoteTermsConditions() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteTermsConditionsSearchRepository.findAll());
        ntQuoteTermsConditions.setId(longCount.incrementAndGet());

        // Create the NtQuoteTermsConditions
        NtQuoteTermsConditionsDTO ntQuoteTermsConditionsDTO = ntQuoteTermsConditionsMapper.toDto(ntQuoteTermsConditions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteTermsConditionsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteTermsConditionsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NtQuoteTermsConditions in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteTermsConditionsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateNtQuoteTermsConditionsWithPatch() throws Exception {
        // Initialize the database
        insertedNtQuoteTermsConditions = ntQuoteTermsConditionsRepository.saveAndFlush(ntQuoteTermsConditions);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ntQuoteTermsConditions using partial update
        NtQuoteTermsConditions partialUpdatedNtQuoteTermsConditions = new NtQuoteTermsConditions();
        partialUpdatedNtQuoteTermsConditions.setId(ntQuoteTermsConditions.getId());

        partialUpdatedNtQuoteTermsConditions.createdBy(UPDATED_CREATED_BY);

        restNtQuoteTermsConditionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNtQuoteTermsConditions.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNtQuoteTermsConditions))
            )
            .andExpect(status().isOk());

        // Validate the NtQuoteTermsConditions in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNtQuoteTermsConditionsUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedNtQuoteTermsConditions, ntQuoteTermsConditions),
            getPersistedNtQuoteTermsConditions(ntQuoteTermsConditions)
        );
    }

    @Test
    @Transactional
    void fullUpdateNtQuoteTermsConditionsWithPatch() throws Exception {
        // Initialize the database
        insertedNtQuoteTermsConditions = ntQuoteTermsConditionsRepository.saveAndFlush(ntQuoteTermsConditions);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ntQuoteTermsConditions using partial update
        NtQuoteTermsConditions partialUpdatedNtQuoteTermsConditions = new NtQuoteTermsConditions();
        partialUpdatedNtQuoteTermsConditions.setId(ntQuoteTermsConditions.getId());

        partialUpdatedNtQuoteTermsConditions
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .termsConditions(UPDATED_TERMS_CONDITIONS)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);

        restNtQuoteTermsConditionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNtQuoteTermsConditions.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNtQuoteTermsConditions))
            )
            .andExpect(status().isOk());

        // Validate the NtQuoteTermsConditions in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNtQuoteTermsConditionsUpdatableFieldsEquals(
            partialUpdatedNtQuoteTermsConditions,
            getPersistedNtQuoteTermsConditions(partialUpdatedNtQuoteTermsConditions)
        );
    }

    @Test
    @Transactional
    void patchNonExistingNtQuoteTermsConditions() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteTermsConditionsSearchRepository.findAll());
        ntQuoteTermsConditions.setId(longCount.incrementAndGet());

        // Create the NtQuoteTermsConditions
        NtQuoteTermsConditionsDTO ntQuoteTermsConditionsDTO = ntQuoteTermsConditionsMapper.toDto(ntQuoteTermsConditions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNtQuoteTermsConditionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ntQuoteTermsConditionsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ntQuoteTermsConditionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteTermsConditions in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteTermsConditionsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNtQuoteTermsConditions() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteTermsConditionsSearchRepository.findAll());
        ntQuoteTermsConditions.setId(longCount.incrementAndGet());

        // Create the NtQuoteTermsConditions
        NtQuoteTermsConditionsDTO ntQuoteTermsConditionsDTO = ntQuoteTermsConditionsMapper.toDto(ntQuoteTermsConditions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteTermsConditionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ntQuoteTermsConditionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteTermsConditions in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteTermsConditionsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNtQuoteTermsConditions() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteTermsConditionsSearchRepository.findAll());
        ntQuoteTermsConditions.setId(longCount.incrementAndGet());

        // Create the NtQuoteTermsConditions
        NtQuoteTermsConditionsDTO ntQuoteTermsConditionsDTO = ntQuoteTermsConditionsMapper.toDto(ntQuoteTermsConditions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteTermsConditionsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ntQuoteTermsConditionsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NtQuoteTermsConditions in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteTermsConditionsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteNtQuoteTermsConditions() throws Exception {
        // Initialize the database
        insertedNtQuoteTermsConditions = ntQuoteTermsConditionsRepository.saveAndFlush(ntQuoteTermsConditions);
        ntQuoteTermsConditionsRepository.save(ntQuoteTermsConditions);
        ntQuoteTermsConditionsSearchRepository.save(ntQuoteTermsConditions);

        long databaseSizeBeforeDelete = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteTermsConditionsSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the ntQuoteTermsConditions
        restNtQuoteTermsConditionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, ntQuoteTermsConditions.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteTermsConditionsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchNtQuoteTermsConditions() throws Exception {
        // Initialize the database
        insertedNtQuoteTermsConditions = ntQuoteTermsConditionsRepository.saveAndFlush(ntQuoteTermsConditions);
        ntQuoteTermsConditionsSearchRepository.save(ntQuoteTermsConditions);

        // Search the ntQuoteTermsConditions
        restNtQuoteTermsConditionsMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + ntQuoteTermsConditions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuoteTermsConditions.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].termsConditions").value(hasItem(DEFAULT_TERMS_CONDITIONS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    protected long getRepositoryCount() {
        return ntQuoteTermsConditionsRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected NtQuoteTermsConditions getPersistedNtQuoteTermsConditions(NtQuoteTermsConditions ntQuoteTermsConditions) {
        return ntQuoteTermsConditionsRepository.findById(ntQuoteTermsConditions.getId()).orElseThrow();
    }

    protected void assertPersistedNtQuoteTermsConditionsToMatchAllProperties(NtQuoteTermsConditions expectedNtQuoteTermsConditions) {
        assertNtQuoteTermsConditionsAllPropertiesEquals(
            expectedNtQuoteTermsConditions,
            getPersistedNtQuoteTermsConditions(expectedNtQuoteTermsConditions)
        );
    }

    protected void assertPersistedNtQuoteTermsConditionsToMatchUpdatableProperties(NtQuoteTermsConditions expectedNtQuoteTermsConditions) {
        assertNtQuoteTermsConditionsAllUpdatablePropertiesEquals(
            expectedNtQuoteTermsConditions,
            getPersistedNtQuoteTermsConditions(expectedNtQuoteTermsConditions)
        );
    }
}
