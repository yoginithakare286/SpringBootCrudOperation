package com.yts.revaux.ntquote.web.rest;

import static com.yts.revaux.ntquote.domain.NtProjectApprovalAsserts.*;
import static com.yts.revaux.ntquote.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yts.revaux.ntquote.IntegrationTest;
import com.yts.revaux.ntquote.domain.NtProjectApproval;
import com.yts.revaux.ntquote.domain.NtQuote;
import com.yts.revaux.ntquote.repository.NtProjectApprovalRepository;
import com.yts.revaux.ntquote.repository.search.NtProjectApprovalSearchRepository;
import com.yts.revaux.ntquote.service.dto.NtProjectApprovalDTO;
import com.yts.revaux.ntquote.service.mapper.NtProjectApprovalMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link NtProjectApprovalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NtProjectApprovalResourceIT {

    private static final Integer DEFAULT_SR_NO = 1;
    private static final Integer UPDATED_SR_NO = 2;
    private static final Integer SMALLER_SR_NO = 1 - 1;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final String DEFAULT_APPROVED_BY = "AAAAAAAAAA";
    private static final String UPDATED_APPROVED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_APPROVAL_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_APPROVAL_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_APPROVAL_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/nt-project-approvals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/nt-project-approvals/_search";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NtProjectApprovalRepository ntProjectApprovalRepository;

    @Autowired
    private NtProjectApprovalMapper ntProjectApprovalMapper;

    @Autowired
    private NtProjectApprovalSearchRepository ntProjectApprovalSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNtProjectApprovalMockMvc;

    private NtProjectApproval ntProjectApproval;

    private NtProjectApproval insertedNtProjectApproval;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NtProjectApproval createEntity() {
        return new NtProjectApproval()
            .srNo(DEFAULT_SR_NO)
            .uid(DEFAULT_UID)
            .approvedBy(DEFAULT_APPROVED_BY)
            .approvalDate(DEFAULT_APPROVAL_DATE)
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
    public static NtProjectApproval createUpdatedEntity() {
        return new NtProjectApproval()
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .approvedBy(UPDATED_APPROVED_BY)
            .approvalDate(UPDATED_APPROVAL_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);
    }

    @BeforeEach
    public void initTest() {
        ntProjectApproval = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedNtProjectApproval != null) {
            ntProjectApprovalRepository.delete(insertedNtProjectApproval);
            ntProjectApprovalSearchRepository.delete(insertedNtProjectApproval);
            insertedNtProjectApproval = null;
        }
    }

    @Test
    @Transactional
    void createNtProjectApproval() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntProjectApprovalSearchRepository.findAll());
        // Create the NtProjectApproval
        NtProjectApprovalDTO ntProjectApprovalDTO = ntProjectApprovalMapper.toDto(ntProjectApproval);
        var returnedNtProjectApprovalDTO = om.readValue(
            restNtProjectApprovalMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntProjectApprovalDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            NtProjectApprovalDTO.class
        );

        // Validate the NtProjectApproval in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedNtProjectApproval = ntProjectApprovalMapper.toEntity(returnedNtProjectApprovalDTO);
        assertNtProjectApprovalUpdatableFieldsEquals(returnedNtProjectApproval, getPersistedNtProjectApproval(returnedNtProjectApproval));

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntProjectApprovalSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });

        insertedNtProjectApproval = returnedNtProjectApproval;
    }

    @Test
    @Transactional
    void createNtProjectApprovalWithExistingId() throws Exception {
        // Create the NtProjectApproval with an existing ID
        ntProjectApproval.setId(1L);
        NtProjectApprovalDTO ntProjectApprovalDTO = ntProjectApprovalMapper.toDto(ntProjectApproval);

        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntProjectApprovalSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restNtProjectApprovalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntProjectApprovalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NtProjectApproval in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntProjectApprovalSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkUidIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntProjectApprovalSearchRepository.findAll());
        // set the field null
        ntProjectApproval.setUid(null);

        // Create the NtProjectApproval, which fails.
        NtProjectApprovalDTO ntProjectApprovalDTO = ntProjectApprovalMapper.toDto(ntProjectApproval);

        restNtProjectApprovalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntProjectApprovalDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntProjectApprovalSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllNtProjectApprovals() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        // Get all the ntProjectApprovalList
        restNtProjectApprovalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntProjectApproval.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].approvedBy").value(hasItem(DEFAULT_APPROVED_BY)))
            .andExpect(jsonPath("$.[*].approvalDate").value(hasItem(DEFAULT_APPROVAL_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getNtProjectApproval() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        // Get the ntProjectApproval
        restNtProjectApprovalMockMvc
            .perform(get(ENTITY_API_URL_ID, ntProjectApproval.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ntProjectApproval.getId().intValue()))
            .andExpect(jsonPath("$.srNo").value(DEFAULT_SR_NO))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.approvedBy").value(DEFAULT_APPROVED_BY))
            .andExpect(jsonPath("$.approvalDate").value(DEFAULT_APPROVAL_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNtProjectApprovalsByIdFiltering() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        Long id = ntProjectApproval.getId();

        defaultNtProjectApprovalFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultNtProjectApprovalFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultNtProjectApprovalFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNtProjectApprovalsBySrNoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        // Get all the ntProjectApprovalList where srNo equals to
        defaultNtProjectApprovalFiltering("srNo.equals=" + DEFAULT_SR_NO, "srNo.equals=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtProjectApprovalsBySrNoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        // Get all the ntProjectApprovalList where srNo in
        defaultNtProjectApprovalFiltering("srNo.in=" + DEFAULT_SR_NO + "," + UPDATED_SR_NO, "srNo.in=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtProjectApprovalsBySrNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        // Get all the ntProjectApprovalList where srNo is not null
        defaultNtProjectApprovalFiltering("srNo.specified=true", "srNo.specified=false");
    }

    @Test
    @Transactional
    void getAllNtProjectApprovalsBySrNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        // Get all the ntProjectApprovalList where srNo is greater than or equal to
        defaultNtProjectApprovalFiltering("srNo.greaterThanOrEqual=" + DEFAULT_SR_NO, "srNo.greaterThanOrEqual=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtProjectApprovalsBySrNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        // Get all the ntProjectApprovalList where srNo is less than or equal to
        defaultNtProjectApprovalFiltering("srNo.lessThanOrEqual=" + DEFAULT_SR_NO, "srNo.lessThanOrEqual=" + SMALLER_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtProjectApprovalsBySrNoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        // Get all the ntProjectApprovalList where srNo is less than
        defaultNtProjectApprovalFiltering("srNo.lessThan=" + UPDATED_SR_NO, "srNo.lessThan=" + DEFAULT_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtProjectApprovalsBySrNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        // Get all the ntProjectApprovalList where srNo is greater than
        defaultNtProjectApprovalFiltering("srNo.greaterThan=" + SMALLER_SR_NO, "srNo.greaterThan=" + DEFAULT_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtProjectApprovalsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        // Get all the ntProjectApprovalList where uid equals to
        defaultNtProjectApprovalFiltering("uid.equals=" + DEFAULT_UID, "uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    void getAllNtProjectApprovalsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        // Get all the ntProjectApprovalList where uid in
        defaultNtProjectApprovalFiltering("uid.in=" + DEFAULT_UID + "," + UPDATED_UID, "uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    void getAllNtProjectApprovalsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        // Get all the ntProjectApprovalList where uid is not null
        defaultNtProjectApprovalFiltering("uid.specified=true", "uid.specified=false");
    }

    @Test
    @Transactional
    void getAllNtProjectApprovalsByApprovedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        // Get all the ntProjectApprovalList where approvedBy equals to
        defaultNtProjectApprovalFiltering("approvedBy.equals=" + DEFAULT_APPROVED_BY, "approvedBy.equals=" + UPDATED_APPROVED_BY);
    }

    @Test
    @Transactional
    void getAllNtProjectApprovalsByApprovedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        // Get all the ntProjectApprovalList where approvedBy in
        defaultNtProjectApprovalFiltering(
            "approvedBy.in=" + DEFAULT_APPROVED_BY + "," + UPDATED_APPROVED_BY,
            "approvedBy.in=" + UPDATED_APPROVED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtProjectApprovalsByApprovedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        // Get all the ntProjectApprovalList where approvedBy is not null
        defaultNtProjectApprovalFiltering("approvedBy.specified=true", "approvedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllNtProjectApprovalsByApprovedByContainsSomething() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        // Get all the ntProjectApprovalList where approvedBy contains
        defaultNtProjectApprovalFiltering("approvedBy.contains=" + DEFAULT_APPROVED_BY, "approvedBy.contains=" + UPDATED_APPROVED_BY);
    }

    @Test
    @Transactional
    void getAllNtProjectApprovalsByApprovedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        // Get all the ntProjectApprovalList where approvedBy does not contain
        defaultNtProjectApprovalFiltering(
            "approvedBy.doesNotContain=" + UPDATED_APPROVED_BY,
            "approvedBy.doesNotContain=" + DEFAULT_APPROVED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtProjectApprovalsByApprovalDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        // Get all the ntProjectApprovalList where approvalDate equals to
        defaultNtProjectApprovalFiltering("approvalDate.equals=" + DEFAULT_APPROVAL_DATE, "approvalDate.equals=" + UPDATED_APPROVAL_DATE);
    }

    @Test
    @Transactional
    void getAllNtProjectApprovalsByApprovalDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        // Get all the ntProjectApprovalList where approvalDate in
        defaultNtProjectApprovalFiltering(
            "approvalDate.in=" + DEFAULT_APPROVAL_DATE + "," + UPDATED_APPROVAL_DATE,
            "approvalDate.in=" + UPDATED_APPROVAL_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtProjectApprovalsByApprovalDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        // Get all the ntProjectApprovalList where approvalDate is not null
        defaultNtProjectApprovalFiltering("approvalDate.specified=true", "approvalDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNtProjectApprovalsByApprovalDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        // Get all the ntProjectApprovalList where approvalDate is greater than or equal to
        defaultNtProjectApprovalFiltering(
            "approvalDate.greaterThanOrEqual=" + DEFAULT_APPROVAL_DATE,
            "approvalDate.greaterThanOrEqual=" + UPDATED_APPROVAL_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtProjectApprovalsByApprovalDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        // Get all the ntProjectApprovalList where approvalDate is less than or equal to
        defaultNtProjectApprovalFiltering(
            "approvalDate.lessThanOrEqual=" + DEFAULT_APPROVAL_DATE,
            "approvalDate.lessThanOrEqual=" + SMALLER_APPROVAL_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtProjectApprovalsByApprovalDateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        // Get all the ntProjectApprovalList where approvalDate is less than
        defaultNtProjectApprovalFiltering(
            "approvalDate.lessThan=" + UPDATED_APPROVAL_DATE,
            "approvalDate.lessThan=" + DEFAULT_APPROVAL_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtProjectApprovalsByApprovalDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        // Get all the ntProjectApprovalList where approvalDate is greater than
        defaultNtProjectApprovalFiltering(
            "approvalDate.greaterThan=" + SMALLER_APPROVAL_DATE,
            "approvalDate.greaterThan=" + DEFAULT_APPROVAL_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtProjectApprovalsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        // Get all the ntProjectApprovalList where createdBy equals to
        defaultNtProjectApprovalFiltering("createdBy.equals=" + DEFAULT_CREATED_BY, "createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllNtProjectApprovalsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        // Get all the ntProjectApprovalList where createdBy in
        defaultNtProjectApprovalFiltering(
            "createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY,
            "createdBy.in=" + UPDATED_CREATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtProjectApprovalsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        // Get all the ntProjectApprovalList where createdBy is not null
        defaultNtProjectApprovalFiltering("createdBy.specified=true", "createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllNtProjectApprovalsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        // Get all the ntProjectApprovalList where createdBy contains
        defaultNtProjectApprovalFiltering("createdBy.contains=" + DEFAULT_CREATED_BY, "createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllNtProjectApprovalsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        // Get all the ntProjectApprovalList where createdBy does not contain
        defaultNtProjectApprovalFiltering(
            "createdBy.doesNotContain=" + UPDATED_CREATED_BY,
            "createdBy.doesNotContain=" + DEFAULT_CREATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtProjectApprovalsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        // Get all the ntProjectApprovalList where createdDate equals to
        defaultNtProjectApprovalFiltering("createdDate.equals=" + DEFAULT_CREATED_DATE, "createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllNtProjectApprovalsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        // Get all the ntProjectApprovalList where createdDate in
        defaultNtProjectApprovalFiltering(
            "createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE,
            "createdDate.in=" + UPDATED_CREATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtProjectApprovalsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        // Get all the ntProjectApprovalList where createdDate is not null
        defaultNtProjectApprovalFiltering("createdDate.specified=true", "createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNtProjectApprovalsByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        // Get all the ntProjectApprovalList where updatedBy equals to
        defaultNtProjectApprovalFiltering("updatedBy.equals=" + DEFAULT_UPDATED_BY, "updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllNtProjectApprovalsByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        // Get all the ntProjectApprovalList where updatedBy in
        defaultNtProjectApprovalFiltering(
            "updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY,
            "updatedBy.in=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtProjectApprovalsByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        // Get all the ntProjectApprovalList where updatedBy is not null
        defaultNtProjectApprovalFiltering("updatedBy.specified=true", "updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllNtProjectApprovalsByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        // Get all the ntProjectApprovalList where updatedBy contains
        defaultNtProjectApprovalFiltering("updatedBy.contains=" + DEFAULT_UPDATED_BY, "updatedBy.contains=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllNtProjectApprovalsByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        // Get all the ntProjectApprovalList where updatedBy does not contain
        defaultNtProjectApprovalFiltering(
            "updatedBy.doesNotContain=" + UPDATED_UPDATED_BY,
            "updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtProjectApprovalsByUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        // Get all the ntProjectApprovalList where updatedDate equals to
        defaultNtProjectApprovalFiltering("updatedDate.equals=" + DEFAULT_UPDATED_DATE, "updatedDate.equals=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllNtProjectApprovalsByUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        // Get all the ntProjectApprovalList where updatedDate in
        defaultNtProjectApprovalFiltering(
            "updatedDate.in=" + DEFAULT_UPDATED_DATE + "," + UPDATED_UPDATED_DATE,
            "updatedDate.in=" + UPDATED_UPDATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtProjectApprovalsByUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        // Get all the ntProjectApprovalList where updatedDate is not null
        defaultNtProjectApprovalFiltering("updatedDate.specified=true", "updatedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNtProjectApprovalsByNtQuoteIsEqualToSomething() throws Exception {
        NtQuote ntQuote;
        if (TestUtil.findAll(em, NtQuote.class).isEmpty()) {
            ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);
            ntQuote = NtQuoteResourceIT.createEntity();
        } else {
            ntQuote = TestUtil.findAll(em, NtQuote.class).get(0);
        }
        em.persist(ntQuote);
        em.flush();
        ntProjectApproval.setNtQuote(ntQuote);
        ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);
        Long ntQuoteId = ntQuote.getId();
        // Get all the ntProjectApprovalList where ntQuote equals to ntQuoteId
        defaultNtProjectApprovalShouldBeFound("ntQuoteId.equals=" + ntQuoteId);

        // Get all the ntProjectApprovalList where ntQuote equals to (ntQuoteId + 1)
        defaultNtProjectApprovalShouldNotBeFound("ntQuoteId.equals=" + (ntQuoteId + 1));
    }

    private void defaultNtProjectApprovalFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultNtProjectApprovalShouldBeFound(shouldBeFound);
        defaultNtProjectApprovalShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNtProjectApprovalShouldBeFound(String filter) throws Exception {
        restNtProjectApprovalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntProjectApproval.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].approvedBy").value(hasItem(DEFAULT_APPROVED_BY)))
            .andExpect(jsonPath("$.[*].approvalDate").value(hasItem(DEFAULT_APPROVAL_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));

        // Check, that the count call also returns 1
        restNtProjectApprovalMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNtProjectApprovalShouldNotBeFound(String filter) throws Exception {
        restNtProjectApprovalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNtProjectApprovalMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNtProjectApproval() throws Exception {
        // Get the ntProjectApproval
        restNtProjectApprovalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNtProjectApproval() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        long databaseSizeBeforeUpdate = getRepositoryCount();
        ntProjectApprovalSearchRepository.save(ntProjectApproval);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntProjectApprovalSearchRepository.findAll());

        // Update the ntProjectApproval
        NtProjectApproval updatedNtProjectApproval = ntProjectApprovalRepository.findById(ntProjectApproval.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedNtProjectApproval are not directly saved in db
        em.detach(updatedNtProjectApproval);
        updatedNtProjectApproval
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .approvedBy(UPDATED_APPROVED_BY)
            .approvalDate(UPDATED_APPROVAL_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);
        NtProjectApprovalDTO ntProjectApprovalDTO = ntProjectApprovalMapper.toDto(updatedNtProjectApproval);

        restNtProjectApprovalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ntProjectApprovalDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntProjectApprovalDTO))
            )
            .andExpect(status().isOk());

        // Validate the NtProjectApproval in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedNtProjectApprovalToMatchAllProperties(updatedNtProjectApproval);

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntProjectApprovalSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<NtProjectApproval> ntProjectApprovalSearchList = Streamable.of(ntProjectApprovalSearchRepository.findAll()).toList();
                NtProjectApproval testNtProjectApprovalSearch = ntProjectApprovalSearchList.get(searchDatabaseSizeAfter - 1);

                assertNtProjectApprovalAllPropertiesEquals(testNtProjectApprovalSearch, updatedNtProjectApproval);
            });
    }

    @Test
    @Transactional
    void putNonExistingNtProjectApproval() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntProjectApprovalSearchRepository.findAll());
        ntProjectApproval.setId(longCount.incrementAndGet());

        // Create the NtProjectApproval
        NtProjectApprovalDTO ntProjectApprovalDTO = ntProjectApprovalMapper.toDto(ntProjectApproval);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNtProjectApprovalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ntProjectApprovalDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntProjectApprovalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtProjectApproval in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntProjectApprovalSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchNtProjectApproval() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntProjectApprovalSearchRepository.findAll());
        ntProjectApproval.setId(longCount.incrementAndGet());

        // Create the NtProjectApproval
        NtProjectApprovalDTO ntProjectApprovalDTO = ntProjectApprovalMapper.toDto(ntProjectApproval);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtProjectApprovalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntProjectApprovalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtProjectApproval in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntProjectApprovalSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNtProjectApproval() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntProjectApprovalSearchRepository.findAll());
        ntProjectApproval.setId(longCount.incrementAndGet());

        // Create the NtProjectApproval
        NtProjectApprovalDTO ntProjectApprovalDTO = ntProjectApprovalMapper.toDto(ntProjectApproval);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtProjectApprovalMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntProjectApprovalDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NtProjectApproval in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntProjectApprovalSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateNtProjectApprovalWithPatch() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ntProjectApproval using partial update
        NtProjectApproval partialUpdatedNtProjectApproval = new NtProjectApproval();
        partialUpdatedNtProjectApproval.setId(ntProjectApproval.getId());

        partialUpdatedNtProjectApproval
            .approvedBy(UPDATED_APPROVED_BY)
            .approvalDate(UPDATED_APPROVAL_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);

        restNtProjectApprovalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNtProjectApproval.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNtProjectApproval))
            )
            .andExpect(status().isOk());

        // Validate the NtProjectApproval in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNtProjectApprovalUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedNtProjectApproval, ntProjectApproval),
            getPersistedNtProjectApproval(ntProjectApproval)
        );
    }

    @Test
    @Transactional
    void fullUpdateNtProjectApprovalWithPatch() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ntProjectApproval using partial update
        NtProjectApproval partialUpdatedNtProjectApproval = new NtProjectApproval();
        partialUpdatedNtProjectApproval.setId(ntProjectApproval.getId());

        partialUpdatedNtProjectApproval
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .approvedBy(UPDATED_APPROVED_BY)
            .approvalDate(UPDATED_APPROVAL_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);

        restNtProjectApprovalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNtProjectApproval.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNtProjectApproval))
            )
            .andExpect(status().isOk());

        // Validate the NtProjectApproval in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNtProjectApprovalUpdatableFieldsEquals(
            partialUpdatedNtProjectApproval,
            getPersistedNtProjectApproval(partialUpdatedNtProjectApproval)
        );
    }

    @Test
    @Transactional
    void patchNonExistingNtProjectApproval() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntProjectApprovalSearchRepository.findAll());
        ntProjectApproval.setId(longCount.incrementAndGet());

        // Create the NtProjectApproval
        NtProjectApprovalDTO ntProjectApprovalDTO = ntProjectApprovalMapper.toDto(ntProjectApproval);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNtProjectApprovalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ntProjectApprovalDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ntProjectApprovalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtProjectApproval in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntProjectApprovalSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNtProjectApproval() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntProjectApprovalSearchRepository.findAll());
        ntProjectApproval.setId(longCount.incrementAndGet());

        // Create the NtProjectApproval
        NtProjectApprovalDTO ntProjectApprovalDTO = ntProjectApprovalMapper.toDto(ntProjectApproval);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtProjectApprovalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ntProjectApprovalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtProjectApproval in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntProjectApprovalSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNtProjectApproval() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntProjectApprovalSearchRepository.findAll());
        ntProjectApproval.setId(longCount.incrementAndGet());

        // Create the NtProjectApproval
        NtProjectApprovalDTO ntProjectApprovalDTO = ntProjectApprovalMapper.toDto(ntProjectApproval);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtProjectApprovalMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ntProjectApprovalDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NtProjectApproval in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntProjectApprovalSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteNtProjectApproval() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);
        ntProjectApprovalRepository.save(ntProjectApproval);
        ntProjectApprovalSearchRepository.save(ntProjectApproval);

        long databaseSizeBeforeDelete = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntProjectApprovalSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the ntProjectApproval
        restNtProjectApprovalMockMvc
            .perform(delete(ENTITY_API_URL_ID, ntProjectApproval.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntProjectApprovalSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchNtProjectApproval() throws Exception {
        // Initialize the database
        insertedNtProjectApproval = ntProjectApprovalRepository.saveAndFlush(ntProjectApproval);
        ntProjectApprovalSearchRepository.save(ntProjectApproval);

        // Search the ntProjectApproval
        restNtProjectApprovalMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + ntProjectApproval.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntProjectApproval.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].approvedBy").value(hasItem(DEFAULT_APPROVED_BY)))
            .andExpect(jsonPath("$.[*].approvalDate").value(hasItem(DEFAULT_APPROVAL_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    protected long getRepositoryCount() {
        return ntProjectApprovalRepository.count();
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

    protected NtProjectApproval getPersistedNtProjectApproval(NtProjectApproval ntProjectApproval) {
        return ntProjectApprovalRepository.findById(ntProjectApproval.getId()).orElseThrow();
    }

    protected void assertPersistedNtProjectApprovalToMatchAllProperties(NtProjectApproval expectedNtProjectApproval) {
        assertNtProjectApprovalAllPropertiesEquals(expectedNtProjectApproval, getPersistedNtProjectApproval(expectedNtProjectApproval));
    }

    protected void assertPersistedNtProjectApprovalToMatchUpdatableProperties(NtProjectApproval expectedNtProjectApproval) {
        assertNtProjectApprovalAllUpdatablePropertiesEquals(
            expectedNtProjectApproval,
            getPersistedNtProjectApproval(expectedNtProjectApproval)
        );
    }
}
