package com.yts.revaux.ntquote.web.rest;

import static com.yts.revaux.ntquote.domain.NtQuoteCommentsAsserts.*;
import static com.yts.revaux.ntquote.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yts.revaux.ntquote.IntegrationTest;
import com.yts.revaux.ntquote.domain.NtQuoteComments;
import com.yts.revaux.ntquote.repository.NtQuoteCommentsRepository;
import com.yts.revaux.ntquote.repository.search.NtQuoteCommentsSearchRepository;
import com.yts.revaux.ntquote.service.dto.NtQuoteCommentsDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuoteCommentsMapper;
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
 * Integration tests for the {@link NtQuoteCommentsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NtQuoteCommentsResourceIT {

    private static final Integer DEFAULT_SR_NO = 1;
    private static final Integer UPDATED_SR_NO = 2;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/nt-quote-comments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/nt-quote-comments/_search";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NtQuoteCommentsRepository ntQuoteCommentsRepository;

    @Autowired
    private NtQuoteCommentsMapper ntQuoteCommentsMapper;

    @Autowired
    private NtQuoteCommentsSearchRepository ntQuoteCommentsSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNtQuoteCommentsMockMvc;

    private NtQuoteComments ntQuoteComments;

    private NtQuoteComments insertedNtQuoteComments;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NtQuoteComments createEntity() {
        return new NtQuoteComments()
            .srNo(DEFAULT_SR_NO)
            .uid(DEFAULT_UID)
            .comments(DEFAULT_COMMENTS)
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
    public static NtQuoteComments createUpdatedEntity() {
        return new NtQuoteComments()
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .comments(UPDATED_COMMENTS)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);
    }

    @BeforeEach
    public void initTest() {
        ntQuoteComments = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedNtQuoteComments != null) {
            ntQuoteCommentsRepository.delete(insertedNtQuoteComments);
            ntQuoteCommentsSearchRepository.delete(insertedNtQuoteComments);
            insertedNtQuoteComments = null;
        }
    }

    @Test
    @Transactional
    void createNtQuoteComments() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCommentsSearchRepository.findAll());
        // Create the NtQuoteComments
        NtQuoteCommentsDTO ntQuoteCommentsDTO = ntQuoteCommentsMapper.toDto(ntQuoteComments);
        var returnedNtQuoteCommentsDTO = om.readValue(
            restNtQuoteCommentsMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteCommentsDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            NtQuoteCommentsDTO.class
        );

        // Validate the NtQuoteComments in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedNtQuoteComments = ntQuoteCommentsMapper.toEntity(returnedNtQuoteCommentsDTO);
        assertNtQuoteCommentsUpdatableFieldsEquals(returnedNtQuoteComments, getPersistedNtQuoteComments(returnedNtQuoteComments));

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCommentsSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });

        insertedNtQuoteComments = returnedNtQuoteComments;
    }

    @Test
    @Transactional
    void createNtQuoteCommentsWithExistingId() throws Exception {
        // Create the NtQuoteComments with an existing ID
        ntQuoteComments.setId(1L);
        NtQuoteCommentsDTO ntQuoteCommentsDTO = ntQuoteCommentsMapper.toDto(ntQuoteComments);

        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCommentsSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restNtQuoteCommentsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteCommentsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteComments in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCommentsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkUidIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCommentsSearchRepository.findAll());
        // set the field null
        ntQuoteComments.setUid(null);

        // Create the NtQuoteComments, which fails.
        NtQuoteCommentsDTO ntQuoteCommentsDTO = ntQuoteCommentsMapper.toDto(ntQuoteComments);

        restNtQuoteCommentsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteCommentsDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCommentsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllNtQuoteComments() throws Exception {
        // Initialize the database
        insertedNtQuoteComments = ntQuoteCommentsRepository.saveAndFlush(ntQuoteComments);

        // Get all the ntQuoteCommentsList
        restNtQuoteCommentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuoteComments.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getNtQuoteComments() throws Exception {
        // Initialize the database
        insertedNtQuoteComments = ntQuoteCommentsRepository.saveAndFlush(ntQuoteComments);

        // Get the ntQuoteComments
        restNtQuoteCommentsMockMvc
            .perform(get(ENTITY_API_URL_ID, ntQuoteComments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ntQuoteComments.getId().intValue()))
            .andExpect(jsonPath("$.srNo").value(DEFAULT_SR_NO))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingNtQuoteComments() throws Exception {
        // Get the ntQuoteComments
        restNtQuoteCommentsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNtQuoteComments() throws Exception {
        // Initialize the database
        insertedNtQuoteComments = ntQuoteCommentsRepository.saveAndFlush(ntQuoteComments);

        long databaseSizeBeforeUpdate = getRepositoryCount();
        ntQuoteCommentsSearchRepository.save(ntQuoteComments);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCommentsSearchRepository.findAll());

        // Update the ntQuoteComments
        NtQuoteComments updatedNtQuoteComments = ntQuoteCommentsRepository.findById(ntQuoteComments.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedNtQuoteComments are not directly saved in db
        em.detach(updatedNtQuoteComments);
        updatedNtQuoteComments
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .comments(UPDATED_COMMENTS)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);
        NtQuoteCommentsDTO ntQuoteCommentsDTO = ntQuoteCommentsMapper.toDto(updatedNtQuoteComments);

        restNtQuoteCommentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ntQuoteCommentsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteCommentsDTO))
            )
            .andExpect(status().isOk());

        // Validate the NtQuoteComments in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedNtQuoteCommentsToMatchAllProperties(updatedNtQuoteComments);

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCommentsSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<NtQuoteComments> ntQuoteCommentsSearchList = Streamable.of(ntQuoteCommentsSearchRepository.findAll()).toList();
                NtQuoteComments testNtQuoteCommentsSearch = ntQuoteCommentsSearchList.get(searchDatabaseSizeAfter - 1);

                assertNtQuoteCommentsAllPropertiesEquals(testNtQuoteCommentsSearch, updatedNtQuoteComments);
            });
    }

    @Test
    @Transactional
    void putNonExistingNtQuoteComments() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCommentsSearchRepository.findAll());
        ntQuoteComments.setId(longCount.incrementAndGet());

        // Create the NtQuoteComments
        NtQuoteCommentsDTO ntQuoteCommentsDTO = ntQuoteCommentsMapper.toDto(ntQuoteComments);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNtQuoteCommentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ntQuoteCommentsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteCommentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteComments in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCommentsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchNtQuoteComments() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCommentsSearchRepository.findAll());
        ntQuoteComments.setId(longCount.incrementAndGet());

        // Create the NtQuoteComments
        NtQuoteCommentsDTO ntQuoteCommentsDTO = ntQuoteCommentsMapper.toDto(ntQuoteComments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteCommentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteCommentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteComments in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCommentsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNtQuoteComments() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCommentsSearchRepository.findAll());
        ntQuoteComments.setId(longCount.incrementAndGet());

        // Create the NtQuoteComments
        NtQuoteCommentsDTO ntQuoteCommentsDTO = ntQuoteCommentsMapper.toDto(ntQuoteComments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteCommentsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteCommentsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NtQuoteComments in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCommentsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateNtQuoteCommentsWithPatch() throws Exception {
        // Initialize the database
        insertedNtQuoteComments = ntQuoteCommentsRepository.saveAndFlush(ntQuoteComments);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ntQuoteComments using partial update
        NtQuoteComments partialUpdatedNtQuoteComments = new NtQuoteComments();
        partialUpdatedNtQuoteComments.setId(ntQuoteComments.getId());

        partialUpdatedNtQuoteComments.uid(UPDATED_UID).createdDate(UPDATED_CREATED_DATE).updatedBy(UPDATED_UPDATED_BY);

        restNtQuoteCommentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNtQuoteComments.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNtQuoteComments))
            )
            .andExpect(status().isOk());

        // Validate the NtQuoteComments in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNtQuoteCommentsUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedNtQuoteComments, ntQuoteComments),
            getPersistedNtQuoteComments(ntQuoteComments)
        );
    }

    @Test
    @Transactional
    void fullUpdateNtQuoteCommentsWithPatch() throws Exception {
        // Initialize the database
        insertedNtQuoteComments = ntQuoteCommentsRepository.saveAndFlush(ntQuoteComments);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ntQuoteComments using partial update
        NtQuoteComments partialUpdatedNtQuoteComments = new NtQuoteComments();
        partialUpdatedNtQuoteComments.setId(ntQuoteComments.getId());

        partialUpdatedNtQuoteComments
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .comments(UPDATED_COMMENTS)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);

        restNtQuoteCommentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNtQuoteComments.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNtQuoteComments))
            )
            .andExpect(status().isOk());

        // Validate the NtQuoteComments in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNtQuoteCommentsUpdatableFieldsEquals(
            partialUpdatedNtQuoteComments,
            getPersistedNtQuoteComments(partialUpdatedNtQuoteComments)
        );
    }

    @Test
    @Transactional
    void patchNonExistingNtQuoteComments() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCommentsSearchRepository.findAll());
        ntQuoteComments.setId(longCount.incrementAndGet());

        // Create the NtQuoteComments
        NtQuoteCommentsDTO ntQuoteCommentsDTO = ntQuoteCommentsMapper.toDto(ntQuoteComments);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNtQuoteCommentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ntQuoteCommentsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ntQuoteCommentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteComments in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCommentsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNtQuoteComments() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCommentsSearchRepository.findAll());
        ntQuoteComments.setId(longCount.incrementAndGet());

        // Create the NtQuoteComments
        NtQuoteCommentsDTO ntQuoteCommentsDTO = ntQuoteCommentsMapper.toDto(ntQuoteComments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteCommentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ntQuoteCommentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteComments in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCommentsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNtQuoteComments() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCommentsSearchRepository.findAll());
        ntQuoteComments.setId(longCount.incrementAndGet());

        // Create the NtQuoteComments
        NtQuoteCommentsDTO ntQuoteCommentsDTO = ntQuoteCommentsMapper.toDto(ntQuoteComments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteCommentsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ntQuoteCommentsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NtQuoteComments in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCommentsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteNtQuoteComments() throws Exception {
        // Initialize the database
        insertedNtQuoteComments = ntQuoteCommentsRepository.saveAndFlush(ntQuoteComments);
        ntQuoteCommentsRepository.save(ntQuoteComments);
        ntQuoteCommentsSearchRepository.save(ntQuoteComments);

        long databaseSizeBeforeDelete = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCommentsSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the ntQuoteComments
        restNtQuoteCommentsMockMvc
            .perform(delete(ENTITY_API_URL_ID, ntQuoteComments.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCommentsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchNtQuoteComments() throws Exception {
        // Initialize the database
        insertedNtQuoteComments = ntQuoteCommentsRepository.saveAndFlush(ntQuoteComments);
        ntQuoteCommentsSearchRepository.save(ntQuoteComments);

        // Search the ntQuoteComments
        restNtQuoteCommentsMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + ntQuoteComments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuoteComments.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    protected long getRepositoryCount() {
        return ntQuoteCommentsRepository.count();
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

    protected NtQuoteComments getPersistedNtQuoteComments(NtQuoteComments ntQuoteComments) {
        return ntQuoteCommentsRepository.findById(ntQuoteComments.getId()).orElseThrow();
    }

    protected void assertPersistedNtQuoteCommentsToMatchAllProperties(NtQuoteComments expectedNtQuoteComments) {
        assertNtQuoteCommentsAllPropertiesEquals(expectedNtQuoteComments, getPersistedNtQuoteComments(expectedNtQuoteComments));
    }

    protected void assertPersistedNtQuoteCommentsToMatchUpdatableProperties(NtQuoteComments expectedNtQuoteComments) {
        assertNtQuoteCommentsAllUpdatablePropertiesEquals(expectedNtQuoteComments, getPersistedNtQuoteComments(expectedNtQuoteComments));
    }
}
