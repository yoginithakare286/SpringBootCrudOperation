package com.yts.revaux.ntquote.web.rest;

import static com.yts.revaux.ntquote.domain.NtQuoteProjectApprovalAsserts.*;
import static com.yts.revaux.ntquote.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yts.revaux.ntquote.IntegrationTest;
import com.yts.revaux.ntquote.domain.NtQuoteProjectApproval;
import com.yts.revaux.ntquote.repository.NtQuoteProjectApprovalRepository;
import com.yts.revaux.ntquote.repository.search.NtQuoteProjectApprovalSearchRepository;
import com.yts.revaux.ntquote.service.dto.NtQuoteProjectApprovalDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuoteProjectApprovalMapper;
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
 * Integration tests for the {@link NtQuoteProjectApprovalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NtQuoteProjectApprovalResourceIT {

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

    private static final String DEFAULT_PROGRAM_MANAGER = "AAAAAAAAAA";
    private static final String UPDATED_PROGRAM_MANAGER = "BBBBBBBBBB";

    private static final String DEFAULT_ENGINEERING = "AAAAAAAAAA";
    private static final String UPDATED_ENGINEERING = "BBBBBBBBBB";

    private static final String DEFAULT_QUALITY = "AAAAAAAAAA";
    private static final String UPDATED_QUALITY = "BBBBBBBBBB";

    private static final String DEFAULT_MATERIALS = "AAAAAAAAAA";
    private static final String UPDATED_MATERIALS = "BBBBBBBBBB";

    private static final String DEFAULT_PLANT_MANAGER = "AAAAAAAAAA";
    private static final String UPDATED_PLANT_MANAGER = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/nt-quote-project-approvals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/nt-quote-project-approvals/_search";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NtQuoteProjectApprovalRepository ntQuoteProjectApprovalRepository;

    @Autowired
    private NtQuoteProjectApprovalMapper ntQuoteProjectApprovalMapper;

    @Autowired
    private NtQuoteProjectApprovalSearchRepository ntQuoteProjectApprovalSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNtQuoteProjectApprovalMockMvc;

    private NtQuoteProjectApproval ntQuoteProjectApproval;

    private NtQuoteProjectApproval insertedNtQuoteProjectApproval;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NtQuoteProjectApproval createEntity() {
        return new NtQuoteProjectApproval()
            .srNo(DEFAULT_SR_NO)
            .uid(DEFAULT_UID)
            .approvedBy(DEFAULT_APPROVED_BY)
            .approvalDate(DEFAULT_APPROVAL_DATE)
            .programManager(DEFAULT_PROGRAM_MANAGER)
            .engineering(DEFAULT_ENGINEERING)
            .quality(DEFAULT_QUALITY)
            .materials(DEFAULT_MATERIALS)
            .plantManager(DEFAULT_PLANT_MANAGER)
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
    public static NtQuoteProjectApproval createUpdatedEntity() {
        return new NtQuoteProjectApproval()
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .approvedBy(UPDATED_APPROVED_BY)
            .approvalDate(UPDATED_APPROVAL_DATE)
            .programManager(UPDATED_PROGRAM_MANAGER)
            .engineering(UPDATED_ENGINEERING)
            .quality(UPDATED_QUALITY)
            .materials(UPDATED_MATERIALS)
            .plantManager(UPDATED_PLANT_MANAGER)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);
    }

    @BeforeEach
    public void initTest() {
        ntQuoteProjectApproval = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedNtQuoteProjectApproval != null) {
            ntQuoteProjectApprovalRepository.delete(insertedNtQuoteProjectApproval);
            ntQuoteProjectApprovalSearchRepository.delete(insertedNtQuoteProjectApproval);
            insertedNtQuoteProjectApproval = null;
        }
    }

    @Test
    @Transactional
    void createNtQuoteProjectApproval() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteProjectApprovalSearchRepository.findAll());
        // Create the NtQuoteProjectApproval
        NtQuoteProjectApprovalDTO ntQuoteProjectApprovalDTO = ntQuoteProjectApprovalMapper.toDto(ntQuoteProjectApproval);
        var returnedNtQuoteProjectApprovalDTO = om.readValue(
            restNtQuoteProjectApprovalMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteProjectApprovalDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            NtQuoteProjectApprovalDTO.class
        );

        // Validate the NtQuoteProjectApproval in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedNtQuoteProjectApproval = ntQuoteProjectApprovalMapper.toEntity(returnedNtQuoteProjectApprovalDTO);
        assertNtQuoteProjectApprovalUpdatableFieldsEquals(
            returnedNtQuoteProjectApproval,
            getPersistedNtQuoteProjectApproval(returnedNtQuoteProjectApproval)
        );

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteProjectApprovalSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });

        insertedNtQuoteProjectApproval = returnedNtQuoteProjectApproval;
    }

    @Test
    @Transactional
    void createNtQuoteProjectApprovalWithExistingId() throws Exception {
        // Create the NtQuoteProjectApproval with an existing ID
        ntQuoteProjectApproval.setId(1L);
        NtQuoteProjectApprovalDTO ntQuoteProjectApprovalDTO = ntQuoteProjectApprovalMapper.toDto(ntQuoteProjectApproval);

        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteProjectApprovalSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restNtQuoteProjectApprovalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteProjectApprovalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteProjectApproval in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteProjectApprovalSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkUidIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteProjectApprovalSearchRepository.findAll());
        // set the field null
        ntQuoteProjectApproval.setUid(null);

        // Create the NtQuoteProjectApproval, which fails.
        NtQuoteProjectApprovalDTO ntQuoteProjectApprovalDTO = ntQuoteProjectApprovalMapper.toDto(ntQuoteProjectApproval);

        restNtQuoteProjectApprovalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteProjectApprovalDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteProjectApprovalSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovals() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList
        restNtQuoteProjectApprovalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuoteProjectApproval.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].approvedBy").value(hasItem(DEFAULT_APPROVED_BY)))
            .andExpect(jsonPath("$.[*].approvalDate").value(hasItem(DEFAULT_APPROVAL_DATE.toString())))
            .andExpect(jsonPath("$.[*].programManager").value(hasItem(DEFAULT_PROGRAM_MANAGER)))
            .andExpect(jsonPath("$.[*].engineering").value(hasItem(DEFAULT_ENGINEERING)))
            .andExpect(jsonPath("$.[*].quality").value(hasItem(DEFAULT_QUALITY)))
            .andExpect(jsonPath("$.[*].materials").value(hasItem(DEFAULT_MATERIALS)))
            .andExpect(jsonPath("$.[*].plantManager").value(hasItem(DEFAULT_PLANT_MANAGER)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getNtQuoteProjectApproval() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get the ntQuoteProjectApproval
        restNtQuoteProjectApprovalMockMvc
            .perform(get(ENTITY_API_URL_ID, ntQuoteProjectApproval.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ntQuoteProjectApproval.getId().intValue()))
            .andExpect(jsonPath("$.srNo").value(DEFAULT_SR_NO))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.approvedBy").value(DEFAULT_APPROVED_BY))
            .andExpect(jsonPath("$.approvalDate").value(DEFAULT_APPROVAL_DATE.toString()))
            .andExpect(jsonPath("$.programManager").value(DEFAULT_PROGRAM_MANAGER))
            .andExpect(jsonPath("$.engineering").value(DEFAULT_ENGINEERING))
            .andExpect(jsonPath("$.quality").value(DEFAULT_QUALITY))
            .andExpect(jsonPath("$.materials").value(DEFAULT_MATERIALS))
            .andExpect(jsonPath("$.plantManager").value(DEFAULT_PLANT_MANAGER))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNtQuoteProjectApprovalsByIdFiltering() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        Long id = ntQuoteProjectApproval.getId();

        defaultNtQuoteProjectApprovalFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultNtQuoteProjectApprovalFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultNtQuoteProjectApprovalFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsBySrNoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where srNo equals to
        defaultNtQuoteProjectApprovalFiltering("srNo.equals=" + DEFAULT_SR_NO, "srNo.equals=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsBySrNoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where srNo in
        defaultNtQuoteProjectApprovalFiltering("srNo.in=" + DEFAULT_SR_NO + "," + UPDATED_SR_NO, "srNo.in=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsBySrNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where srNo is not null
        defaultNtQuoteProjectApprovalFiltering("srNo.specified=true", "srNo.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsBySrNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where srNo is greater than or equal to
        defaultNtQuoteProjectApprovalFiltering("srNo.greaterThanOrEqual=" + DEFAULT_SR_NO, "srNo.greaterThanOrEqual=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsBySrNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where srNo is less than or equal to
        defaultNtQuoteProjectApprovalFiltering("srNo.lessThanOrEqual=" + DEFAULT_SR_NO, "srNo.lessThanOrEqual=" + SMALLER_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsBySrNoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where srNo is less than
        defaultNtQuoteProjectApprovalFiltering("srNo.lessThan=" + UPDATED_SR_NO, "srNo.lessThan=" + DEFAULT_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsBySrNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where srNo is greater than
        defaultNtQuoteProjectApprovalFiltering("srNo.greaterThan=" + SMALLER_SR_NO, "srNo.greaterThan=" + DEFAULT_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where uid equals to
        defaultNtQuoteProjectApprovalFiltering("uid.equals=" + DEFAULT_UID, "uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where uid in
        defaultNtQuoteProjectApprovalFiltering("uid.in=" + DEFAULT_UID + "," + UPDATED_UID, "uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where uid is not null
        defaultNtQuoteProjectApprovalFiltering("uid.specified=true", "uid.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByApprovedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where approvedBy equals to
        defaultNtQuoteProjectApprovalFiltering("approvedBy.equals=" + DEFAULT_APPROVED_BY, "approvedBy.equals=" + UPDATED_APPROVED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByApprovedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where approvedBy in
        defaultNtQuoteProjectApprovalFiltering(
            "approvedBy.in=" + DEFAULT_APPROVED_BY + "," + UPDATED_APPROVED_BY,
            "approvedBy.in=" + UPDATED_APPROVED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByApprovedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where approvedBy is not null
        defaultNtQuoteProjectApprovalFiltering("approvedBy.specified=true", "approvedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByApprovedByContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where approvedBy contains
        defaultNtQuoteProjectApprovalFiltering("approvedBy.contains=" + DEFAULT_APPROVED_BY, "approvedBy.contains=" + UPDATED_APPROVED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByApprovedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where approvedBy does not contain
        defaultNtQuoteProjectApprovalFiltering(
            "approvedBy.doesNotContain=" + UPDATED_APPROVED_BY,
            "approvedBy.doesNotContain=" + DEFAULT_APPROVED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByApprovalDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where approvalDate equals to
        defaultNtQuoteProjectApprovalFiltering(
            "approvalDate.equals=" + DEFAULT_APPROVAL_DATE,
            "approvalDate.equals=" + UPDATED_APPROVAL_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByApprovalDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where approvalDate in
        defaultNtQuoteProjectApprovalFiltering(
            "approvalDate.in=" + DEFAULT_APPROVAL_DATE + "," + UPDATED_APPROVAL_DATE,
            "approvalDate.in=" + UPDATED_APPROVAL_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByApprovalDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where approvalDate is not null
        defaultNtQuoteProjectApprovalFiltering("approvalDate.specified=true", "approvalDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByApprovalDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where approvalDate is greater than or equal to
        defaultNtQuoteProjectApprovalFiltering(
            "approvalDate.greaterThanOrEqual=" + DEFAULT_APPROVAL_DATE,
            "approvalDate.greaterThanOrEqual=" + UPDATED_APPROVAL_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByApprovalDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where approvalDate is less than or equal to
        defaultNtQuoteProjectApprovalFiltering(
            "approvalDate.lessThanOrEqual=" + DEFAULT_APPROVAL_DATE,
            "approvalDate.lessThanOrEqual=" + SMALLER_APPROVAL_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByApprovalDateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where approvalDate is less than
        defaultNtQuoteProjectApprovalFiltering(
            "approvalDate.lessThan=" + UPDATED_APPROVAL_DATE,
            "approvalDate.lessThan=" + DEFAULT_APPROVAL_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByApprovalDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where approvalDate is greater than
        defaultNtQuoteProjectApprovalFiltering(
            "approvalDate.greaterThan=" + SMALLER_APPROVAL_DATE,
            "approvalDate.greaterThan=" + DEFAULT_APPROVAL_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByProgramManagerIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where programManager equals to
        defaultNtQuoteProjectApprovalFiltering(
            "programManager.equals=" + DEFAULT_PROGRAM_MANAGER,
            "programManager.equals=" + UPDATED_PROGRAM_MANAGER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByProgramManagerIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where programManager in
        defaultNtQuoteProjectApprovalFiltering(
            "programManager.in=" + DEFAULT_PROGRAM_MANAGER + "," + UPDATED_PROGRAM_MANAGER,
            "programManager.in=" + UPDATED_PROGRAM_MANAGER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByProgramManagerIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where programManager is not null
        defaultNtQuoteProjectApprovalFiltering("programManager.specified=true", "programManager.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByProgramManagerContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where programManager contains
        defaultNtQuoteProjectApprovalFiltering(
            "programManager.contains=" + DEFAULT_PROGRAM_MANAGER,
            "programManager.contains=" + UPDATED_PROGRAM_MANAGER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByProgramManagerNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where programManager does not contain
        defaultNtQuoteProjectApprovalFiltering(
            "programManager.doesNotContain=" + UPDATED_PROGRAM_MANAGER,
            "programManager.doesNotContain=" + DEFAULT_PROGRAM_MANAGER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByEngineeringIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where engineering equals to
        defaultNtQuoteProjectApprovalFiltering("engineering.equals=" + DEFAULT_ENGINEERING, "engineering.equals=" + UPDATED_ENGINEERING);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByEngineeringIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where engineering in
        defaultNtQuoteProjectApprovalFiltering(
            "engineering.in=" + DEFAULT_ENGINEERING + "," + UPDATED_ENGINEERING,
            "engineering.in=" + UPDATED_ENGINEERING
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByEngineeringIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where engineering is not null
        defaultNtQuoteProjectApprovalFiltering("engineering.specified=true", "engineering.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByEngineeringContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where engineering contains
        defaultNtQuoteProjectApprovalFiltering(
            "engineering.contains=" + DEFAULT_ENGINEERING,
            "engineering.contains=" + UPDATED_ENGINEERING
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByEngineeringNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where engineering does not contain
        defaultNtQuoteProjectApprovalFiltering(
            "engineering.doesNotContain=" + UPDATED_ENGINEERING,
            "engineering.doesNotContain=" + DEFAULT_ENGINEERING
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByQualityIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where quality equals to
        defaultNtQuoteProjectApprovalFiltering("quality.equals=" + DEFAULT_QUALITY, "quality.equals=" + UPDATED_QUALITY);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByQualityIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where quality in
        defaultNtQuoteProjectApprovalFiltering("quality.in=" + DEFAULT_QUALITY + "," + UPDATED_QUALITY, "quality.in=" + UPDATED_QUALITY);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByQualityIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where quality is not null
        defaultNtQuoteProjectApprovalFiltering("quality.specified=true", "quality.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByQualityContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where quality contains
        defaultNtQuoteProjectApprovalFiltering("quality.contains=" + DEFAULT_QUALITY, "quality.contains=" + UPDATED_QUALITY);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByQualityNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where quality does not contain
        defaultNtQuoteProjectApprovalFiltering("quality.doesNotContain=" + UPDATED_QUALITY, "quality.doesNotContain=" + DEFAULT_QUALITY);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByMaterialsIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where materials equals to
        defaultNtQuoteProjectApprovalFiltering("materials.equals=" + DEFAULT_MATERIALS, "materials.equals=" + UPDATED_MATERIALS);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByMaterialsIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where materials in
        defaultNtQuoteProjectApprovalFiltering(
            "materials.in=" + DEFAULT_MATERIALS + "," + UPDATED_MATERIALS,
            "materials.in=" + UPDATED_MATERIALS
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByMaterialsIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where materials is not null
        defaultNtQuoteProjectApprovalFiltering("materials.specified=true", "materials.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByMaterialsContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where materials contains
        defaultNtQuoteProjectApprovalFiltering("materials.contains=" + DEFAULT_MATERIALS, "materials.contains=" + UPDATED_MATERIALS);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByMaterialsNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where materials does not contain
        defaultNtQuoteProjectApprovalFiltering(
            "materials.doesNotContain=" + UPDATED_MATERIALS,
            "materials.doesNotContain=" + DEFAULT_MATERIALS
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByPlantManagerIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where plantManager equals to
        defaultNtQuoteProjectApprovalFiltering(
            "plantManager.equals=" + DEFAULT_PLANT_MANAGER,
            "plantManager.equals=" + UPDATED_PLANT_MANAGER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByPlantManagerIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where plantManager in
        defaultNtQuoteProjectApprovalFiltering(
            "plantManager.in=" + DEFAULT_PLANT_MANAGER + "," + UPDATED_PLANT_MANAGER,
            "plantManager.in=" + UPDATED_PLANT_MANAGER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByPlantManagerIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where plantManager is not null
        defaultNtQuoteProjectApprovalFiltering("plantManager.specified=true", "plantManager.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByPlantManagerContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where plantManager contains
        defaultNtQuoteProjectApprovalFiltering(
            "plantManager.contains=" + DEFAULT_PLANT_MANAGER,
            "plantManager.contains=" + UPDATED_PLANT_MANAGER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByPlantManagerNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where plantManager does not contain
        defaultNtQuoteProjectApprovalFiltering(
            "plantManager.doesNotContain=" + UPDATED_PLANT_MANAGER,
            "plantManager.doesNotContain=" + DEFAULT_PLANT_MANAGER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where createdBy equals to
        defaultNtQuoteProjectApprovalFiltering("createdBy.equals=" + DEFAULT_CREATED_BY, "createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where createdBy in
        defaultNtQuoteProjectApprovalFiltering(
            "createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY,
            "createdBy.in=" + UPDATED_CREATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where createdBy is not null
        defaultNtQuoteProjectApprovalFiltering("createdBy.specified=true", "createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where createdBy contains
        defaultNtQuoteProjectApprovalFiltering("createdBy.contains=" + DEFAULT_CREATED_BY, "createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where createdBy does not contain
        defaultNtQuoteProjectApprovalFiltering(
            "createdBy.doesNotContain=" + UPDATED_CREATED_BY,
            "createdBy.doesNotContain=" + DEFAULT_CREATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where createdDate equals to
        defaultNtQuoteProjectApprovalFiltering("createdDate.equals=" + DEFAULT_CREATED_DATE, "createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where createdDate in
        defaultNtQuoteProjectApprovalFiltering(
            "createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE,
            "createdDate.in=" + UPDATED_CREATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where createdDate is not null
        defaultNtQuoteProjectApprovalFiltering("createdDate.specified=true", "createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where updatedBy equals to
        defaultNtQuoteProjectApprovalFiltering("updatedBy.equals=" + DEFAULT_UPDATED_BY, "updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where updatedBy in
        defaultNtQuoteProjectApprovalFiltering(
            "updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY,
            "updatedBy.in=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where updatedBy is not null
        defaultNtQuoteProjectApprovalFiltering("updatedBy.specified=true", "updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where updatedBy contains
        defaultNtQuoteProjectApprovalFiltering("updatedBy.contains=" + DEFAULT_UPDATED_BY, "updatedBy.contains=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where updatedBy does not contain
        defaultNtQuoteProjectApprovalFiltering(
            "updatedBy.doesNotContain=" + UPDATED_UPDATED_BY,
            "updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where updatedDate equals to
        defaultNtQuoteProjectApprovalFiltering("updatedDate.equals=" + DEFAULT_UPDATED_DATE, "updatedDate.equals=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where updatedDate in
        defaultNtQuoteProjectApprovalFiltering(
            "updatedDate.in=" + DEFAULT_UPDATED_DATE + "," + UPDATED_UPDATED_DATE,
            "updatedDate.in=" + UPDATED_UPDATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectApprovalsByUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        // Get all the ntQuoteProjectApprovalList where updatedDate is not null
        defaultNtQuoteProjectApprovalFiltering("updatedDate.specified=true", "updatedDate.specified=false");
    }

    private void defaultNtQuoteProjectApprovalFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultNtQuoteProjectApprovalShouldBeFound(shouldBeFound);
        defaultNtQuoteProjectApprovalShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNtQuoteProjectApprovalShouldBeFound(String filter) throws Exception {
        restNtQuoteProjectApprovalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuoteProjectApproval.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].approvedBy").value(hasItem(DEFAULT_APPROVED_BY)))
            .andExpect(jsonPath("$.[*].approvalDate").value(hasItem(DEFAULT_APPROVAL_DATE.toString())))
            .andExpect(jsonPath("$.[*].programManager").value(hasItem(DEFAULT_PROGRAM_MANAGER)))
            .andExpect(jsonPath("$.[*].engineering").value(hasItem(DEFAULT_ENGINEERING)))
            .andExpect(jsonPath("$.[*].quality").value(hasItem(DEFAULT_QUALITY)))
            .andExpect(jsonPath("$.[*].materials").value(hasItem(DEFAULT_MATERIALS)))
            .andExpect(jsonPath("$.[*].plantManager").value(hasItem(DEFAULT_PLANT_MANAGER)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));

        // Check, that the count call also returns 1
        restNtQuoteProjectApprovalMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNtQuoteProjectApprovalShouldNotBeFound(String filter) throws Exception {
        restNtQuoteProjectApprovalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNtQuoteProjectApprovalMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNtQuoteProjectApproval() throws Exception {
        // Get the ntQuoteProjectApproval
        restNtQuoteProjectApprovalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNtQuoteProjectApproval() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        long databaseSizeBeforeUpdate = getRepositoryCount();
        ntQuoteProjectApprovalSearchRepository.save(ntQuoteProjectApproval);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteProjectApprovalSearchRepository.findAll());

        // Update the ntQuoteProjectApproval
        NtQuoteProjectApproval updatedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository
            .findById(ntQuoteProjectApproval.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedNtQuoteProjectApproval are not directly saved in db
        em.detach(updatedNtQuoteProjectApproval);
        updatedNtQuoteProjectApproval
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .approvedBy(UPDATED_APPROVED_BY)
            .approvalDate(UPDATED_APPROVAL_DATE)
            .programManager(UPDATED_PROGRAM_MANAGER)
            .engineering(UPDATED_ENGINEERING)
            .quality(UPDATED_QUALITY)
            .materials(UPDATED_MATERIALS)
            .plantManager(UPDATED_PLANT_MANAGER)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);
        NtQuoteProjectApprovalDTO ntQuoteProjectApprovalDTO = ntQuoteProjectApprovalMapper.toDto(updatedNtQuoteProjectApproval);

        restNtQuoteProjectApprovalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ntQuoteProjectApprovalDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteProjectApprovalDTO))
            )
            .andExpect(status().isOk());

        // Validate the NtQuoteProjectApproval in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedNtQuoteProjectApprovalToMatchAllProperties(updatedNtQuoteProjectApproval);

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteProjectApprovalSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<NtQuoteProjectApproval> ntQuoteProjectApprovalSearchList = Streamable.of(
                    ntQuoteProjectApprovalSearchRepository.findAll()
                ).toList();
                NtQuoteProjectApproval testNtQuoteProjectApprovalSearch = ntQuoteProjectApprovalSearchList.get(searchDatabaseSizeAfter - 1);

                assertNtQuoteProjectApprovalAllPropertiesEquals(testNtQuoteProjectApprovalSearch, updatedNtQuoteProjectApproval);
            });
    }

    @Test
    @Transactional
    void putNonExistingNtQuoteProjectApproval() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteProjectApprovalSearchRepository.findAll());
        ntQuoteProjectApproval.setId(longCount.incrementAndGet());

        // Create the NtQuoteProjectApproval
        NtQuoteProjectApprovalDTO ntQuoteProjectApprovalDTO = ntQuoteProjectApprovalMapper.toDto(ntQuoteProjectApproval);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNtQuoteProjectApprovalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ntQuoteProjectApprovalDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteProjectApprovalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteProjectApproval in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteProjectApprovalSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchNtQuoteProjectApproval() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteProjectApprovalSearchRepository.findAll());
        ntQuoteProjectApproval.setId(longCount.incrementAndGet());

        // Create the NtQuoteProjectApproval
        NtQuoteProjectApprovalDTO ntQuoteProjectApprovalDTO = ntQuoteProjectApprovalMapper.toDto(ntQuoteProjectApproval);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteProjectApprovalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteProjectApprovalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteProjectApproval in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteProjectApprovalSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNtQuoteProjectApproval() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteProjectApprovalSearchRepository.findAll());
        ntQuoteProjectApproval.setId(longCount.incrementAndGet());

        // Create the NtQuoteProjectApproval
        NtQuoteProjectApprovalDTO ntQuoteProjectApprovalDTO = ntQuoteProjectApprovalMapper.toDto(ntQuoteProjectApproval);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteProjectApprovalMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteProjectApprovalDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NtQuoteProjectApproval in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteProjectApprovalSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateNtQuoteProjectApprovalWithPatch() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ntQuoteProjectApproval using partial update
        NtQuoteProjectApproval partialUpdatedNtQuoteProjectApproval = new NtQuoteProjectApproval();
        partialUpdatedNtQuoteProjectApproval.setId(ntQuoteProjectApproval.getId());

        partialUpdatedNtQuoteProjectApproval
            .srNo(UPDATED_SR_NO)
            .approvedBy(UPDATED_APPROVED_BY)
            .quality(UPDATED_QUALITY)
            .plantManager(UPDATED_PLANT_MANAGER)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);

        restNtQuoteProjectApprovalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNtQuoteProjectApproval.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNtQuoteProjectApproval))
            )
            .andExpect(status().isOk());

        // Validate the NtQuoteProjectApproval in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNtQuoteProjectApprovalUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedNtQuoteProjectApproval, ntQuoteProjectApproval),
            getPersistedNtQuoteProjectApproval(ntQuoteProjectApproval)
        );
    }

    @Test
    @Transactional
    void fullUpdateNtQuoteProjectApprovalWithPatch() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ntQuoteProjectApproval using partial update
        NtQuoteProjectApproval partialUpdatedNtQuoteProjectApproval = new NtQuoteProjectApproval();
        partialUpdatedNtQuoteProjectApproval.setId(ntQuoteProjectApproval.getId());

        partialUpdatedNtQuoteProjectApproval
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .approvedBy(UPDATED_APPROVED_BY)
            .approvalDate(UPDATED_APPROVAL_DATE)
            .programManager(UPDATED_PROGRAM_MANAGER)
            .engineering(UPDATED_ENGINEERING)
            .quality(UPDATED_QUALITY)
            .materials(UPDATED_MATERIALS)
            .plantManager(UPDATED_PLANT_MANAGER)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);

        restNtQuoteProjectApprovalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNtQuoteProjectApproval.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNtQuoteProjectApproval))
            )
            .andExpect(status().isOk());

        // Validate the NtQuoteProjectApproval in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNtQuoteProjectApprovalUpdatableFieldsEquals(
            partialUpdatedNtQuoteProjectApproval,
            getPersistedNtQuoteProjectApproval(partialUpdatedNtQuoteProjectApproval)
        );
    }

    @Test
    @Transactional
    void patchNonExistingNtQuoteProjectApproval() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteProjectApprovalSearchRepository.findAll());
        ntQuoteProjectApproval.setId(longCount.incrementAndGet());

        // Create the NtQuoteProjectApproval
        NtQuoteProjectApprovalDTO ntQuoteProjectApprovalDTO = ntQuoteProjectApprovalMapper.toDto(ntQuoteProjectApproval);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNtQuoteProjectApprovalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ntQuoteProjectApprovalDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ntQuoteProjectApprovalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteProjectApproval in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteProjectApprovalSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNtQuoteProjectApproval() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteProjectApprovalSearchRepository.findAll());
        ntQuoteProjectApproval.setId(longCount.incrementAndGet());

        // Create the NtQuoteProjectApproval
        NtQuoteProjectApprovalDTO ntQuoteProjectApprovalDTO = ntQuoteProjectApprovalMapper.toDto(ntQuoteProjectApproval);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteProjectApprovalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ntQuoteProjectApprovalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteProjectApproval in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteProjectApprovalSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNtQuoteProjectApproval() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteProjectApprovalSearchRepository.findAll());
        ntQuoteProjectApproval.setId(longCount.incrementAndGet());

        // Create the NtQuoteProjectApproval
        NtQuoteProjectApprovalDTO ntQuoteProjectApprovalDTO = ntQuoteProjectApprovalMapper.toDto(ntQuoteProjectApproval);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteProjectApprovalMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ntQuoteProjectApprovalDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NtQuoteProjectApproval in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteProjectApprovalSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteNtQuoteProjectApproval() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);
        ntQuoteProjectApprovalRepository.save(ntQuoteProjectApproval);
        ntQuoteProjectApprovalSearchRepository.save(ntQuoteProjectApproval);

        long databaseSizeBeforeDelete = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteProjectApprovalSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the ntQuoteProjectApproval
        restNtQuoteProjectApprovalMockMvc
            .perform(delete(ENTITY_API_URL_ID, ntQuoteProjectApproval.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteProjectApprovalSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchNtQuoteProjectApproval() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectApproval = ntQuoteProjectApprovalRepository.saveAndFlush(ntQuoteProjectApproval);
        ntQuoteProjectApprovalSearchRepository.save(ntQuoteProjectApproval);

        // Search the ntQuoteProjectApproval
        restNtQuoteProjectApprovalMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + ntQuoteProjectApproval.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuoteProjectApproval.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].approvedBy").value(hasItem(DEFAULT_APPROVED_BY)))
            .andExpect(jsonPath("$.[*].approvalDate").value(hasItem(DEFAULT_APPROVAL_DATE.toString())))
            .andExpect(jsonPath("$.[*].programManager").value(hasItem(DEFAULT_PROGRAM_MANAGER)))
            .andExpect(jsonPath("$.[*].engineering").value(hasItem(DEFAULT_ENGINEERING)))
            .andExpect(jsonPath("$.[*].quality").value(hasItem(DEFAULT_QUALITY)))
            .andExpect(jsonPath("$.[*].materials").value(hasItem(DEFAULT_MATERIALS)))
            .andExpect(jsonPath("$.[*].plantManager").value(hasItem(DEFAULT_PLANT_MANAGER)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    protected long getRepositoryCount() {
        return ntQuoteProjectApprovalRepository.count();
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

    protected NtQuoteProjectApproval getPersistedNtQuoteProjectApproval(NtQuoteProjectApproval ntQuoteProjectApproval) {
        return ntQuoteProjectApprovalRepository.findById(ntQuoteProjectApproval.getId()).orElseThrow();
    }

    protected void assertPersistedNtQuoteProjectApprovalToMatchAllProperties(NtQuoteProjectApproval expectedNtQuoteProjectApproval) {
        assertNtQuoteProjectApprovalAllPropertiesEquals(
            expectedNtQuoteProjectApproval,
            getPersistedNtQuoteProjectApproval(expectedNtQuoteProjectApproval)
        );
    }

    protected void assertPersistedNtQuoteProjectApprovalToMatchUpdatableProperties(NtQuoteProjectApproval expectedNtQuoteProjectApproval) {
        assertNtQuoteProjectApprovalAllUpdatablePropertiesEquals(
            expectedNtQuoteProjectApproval,
            getPersistedNtQuoteProjectApproval(expectedNtQuoteProjectApproval)
        );
    }
}
