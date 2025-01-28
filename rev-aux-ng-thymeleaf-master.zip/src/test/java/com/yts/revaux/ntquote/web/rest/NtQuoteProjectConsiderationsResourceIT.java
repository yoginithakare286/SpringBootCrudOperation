package com.yts.revaux.ntquote.web.rest;

import static com.yts.revaux.ntquote.domain.NtQuoteProjectConsiderationsAsserts.*;
import static com.yts.revaux.ntquote.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yts.revaux.ntquote.IntegrationTest;
import com.yts.revaux.ntquote.domain.NtQuote;
import com.yts.revaux.ntquote.domain.NtQuoteProjectConsiderations;
import com.yts.revaux.ntquote.repository.NtQuoteProjectConsiderationsRepository;
import com.yts.revaux.ntquote.repository.search.NtQuoteProjectConsiderationsSearchRepository;
import com.yts.revaux.ntquote.service.dto.NtQuoteProjectConsiderationsDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuoteProjectConsiderationsMapper;
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
 * Integration tests for the {@link NtQuoteProjectConsiderationsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NtQuoteProjectConsiderationsResourceIT {

    private static final Integer DEFAULT_SR_NO = 1;
    private static final Integer UPDATED_SR_NO = 2;
    private static final Integer SMALLER_SR_NO = 1 - 1;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final String DEFAULT_PROJECT_CONSIDERATION = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_CONSIDERATION = "BBBBBBBBBB";

    private static final Integer DEFAULT_CHOICE = 1;
    private static final Integer UPDATED_CHOICE = 2;
    private static final Integer SMALLER_CHOICE = 1 - 1;

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

    private static final String ENTITY_API_URL = "/api/nt-quote-project-considerations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/nt-quote-project-considerations/_search";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NtQuoteProjectConsiderationsRepository ntQuoteProjectConsiderationsRepository;

    @Autowired
    private NtQuoteProjectConsiderationsMapper ntQuoteProjectConsiderationsMapper;

    @Autowired
    private NtQuoteProjectConsiderationsSearchRepository ntQuoteProjectConsiderationsSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNtQuoteProjectConsiderationsMockMvc;

    private NtQuoteProjectConsiderations ntQuoteProjectConsiderations;

    private NtQuoteProjectConsiderations insertedNtQuoteProjectConsiderations;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NtQuoteProjectConsiderations createEntity() {
        return new NtQuoteProjectConsiderations()
            .srNo(DEFAULT_SR_NO)
            .uid(DEFAULT_UID)
            .projectConsideration(DEFAULT_PROJECT_CONSIDERATION)
            .choice(DEFAULT_CHOICE)
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
    public static NtQuoteProjectConsiderations createUpdatedEntity() {
        return new NtQuoteProjectConsiderations()
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .projectConsideration(UPDATED_PROJECT_CONSIDERATION)
            .choice(UPDATED_CHOICE)
            .comments(UPDATED_COMMENTS)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);
    }

    @BeforeEach
    public void initTest() {
        ntQuoteProjectConsiderations = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedNtQuoteProjectConsiderations != null) {
            ntQuoteProjectConsiderationsRepository.delete(insertedNtQuoteProjectConsiderations);
            ntQuoteProjectConsiderationsSearchRepository.delete(insertedNtQuoteProjectConsiderations);
            insertedNtQuoteProjectConsiderations = null;
        }
    }

    @Test
    @Transactional
    void createNtQuoteProjectConsiderations() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteProjectConsiderationsSearchRepository.findAll());
        // Create the NtQuoteProjectConsiderations
        NtQuoteProjectConsiderationsDTO ntQuoteProjectConsiderationsDTO = ntQuoteProjectConsiderationsMapper.toDto(
            ntQuoteProjectConsiderations
        );
        var returnedNtQuoteProjectConsiderationsDTO = om.readValue(
            restNtQuoteProjectConsiderationsMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(ntQuoteProjectConsiderationsDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            NtQuoteProjectConsiderationsDTO.class
        );

        // Validate the NtQuoteProjectConsiderations in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsMapper.toEntity(returnedNtQuoteProjectConsiderationsDTO);
        assertNtQuoteProjectConsiderationsUpdatableFieldsEquals(
            returnedNtQuoteProjectConsiderations,
            getPersistedNtQuoteProjectConsiderations(returnedNtQuoteProjectConsiderations)
        );

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteProjectConsiderationsSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });

        insertedNtQuoteProjectConsiderations = returnedNtQuoteProjectConsiderations;
    }

    @Test
    @Transactional
    void createNtQuoteProjectConsiderationsWithExistingId() throws Exception {
        // Create the NtQuoteProjectConsiderations with an existing ID
        ntQuoteProjectConsiderations.setId(1L);
        NtQuoteProjectConsiderationsDTO ntQuoteProjectConsiderationsDTO = ntQuoteProjectConsiderationsMapper.toDto(
            ntQuoteProjectConsiderations
        );

        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteProjectConsiderationsSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restNtQuoteProjectConsiderationsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteProjectConsiderationsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteProjectConsiderations in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteProjectConsiderationsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkUidIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteProjectConsiderationsSearchRepository.findAll());
        // set the field null
        ntQuoteProjectConsiderations.setUid(null);

        // Create the NtQuoteProjectConsiderations, which fails.
        NtQuoteProjectConsiderationsDTO ntQuoteProjectConsiderationsDTO = ntQuoteProjectConsiderationsMapper.toDto(
            ntQuoteProjectConsiderations
        );

        restNtQuoteProjectConsiderationsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteProjectConsiderationsDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteProjectConsiderationsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderations() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList
        restNtQuoteProjectConsiderationsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuoteProjectConsiderations.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].projectConsideration").value(hasItem(DEFAULT_PROJECT_CONSIDERATION)))
            .andExpect(jsonPath("$.[*].choice").value(hasItem(DEFAULT_CHOICE)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getNtQuoteProjectConsiderations() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get the ntQuoteProjectConsiderations
        restNtQuoteProjectConsiderationsMockMvc
            .perform(get(ENTITY_API_URL_ID, ntQuoteProjectConsiderations.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ntQuoteProjectConsiderations.getId().intValue()))
            .andExpect(jsonPath("$.srNo").value(DEFAULT_SR_NO))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.projectConsideration").value(DEFAULT_PROJECT_CONSIDERATION))
            .andExpect(jsonPath("$.choice").value(DEFAULT_CHOICE))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNtQuoteProjectConsiderationsByIdFiltering() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        Long id = ntQuoteProjectConsiderations.getId();

        defaultNtQuoteProjectConsiderationsFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultNtQuoteProjectConsiderationsFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultNtQuoteProjectConsiderationsFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsBySrNoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where srNo equals to
        defaultNtQuoteProjectConsiderationsFiltering("srNo.equals=" + DEFAULT_SR_NO, "srNo.equals=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsBySrNoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where srNo in
        defaultNtQuoteProjectConsiderationsFiltering("srNo.in=" + DEFAULT_SR_NO + "," + UPDATED_SR_NO, "srNo.in=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsBySrNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where srNo is not null
        defaultNtQuoteProjectConsiderationsFiltering("srNo.specified=true", "srNo.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsBySrNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where srNo is greater than or equal to
        defaultNtQuoteProjectConsiderationsFiltering(
            "srNo.greaterThanOrEqual=" + DEFAULT_SR_NO,
            "srNo.greaterThanOrEqual=" + UPDATED_SR_NO
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsBySrNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where srNo is less than or equal to
        defaultNtQuoteProjectConsiderationsFiltering("srNo.lessThanOrEqual=" + DEFAULT_SR_NO, "srNo.lessThanOrEqual=" + SMALLER_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsBySrNoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where srNo is less than
        defaultNtQuoteProjectConsiderationsFiltering("srNo.lessThan=" + UPDATED_SR_NO, "srNo.lessThan=" + DEFAULT_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsBySrNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where srNo is greater than
        defaultNtQuoteProjectConsiderationsFiltering("srNo.greaterThan=" + SMALLER_SR_NO, "srNo.greaterThan=" + DEFAULT_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where uid equals to
        defaultNtQuoteProjectConsiderationsFiltering("uid.equals=" + DEFAULT_UID, "uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where uid in
        defaultNtQuoteProjectConsiderationsFiltering("uid.in=" + DEFAULT_UID + "," + UPDATED_UID, "uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where uid is not null
        defaultNtQuoteProjectConsiderationsFiltering("uid.specified=true", "uid.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsByProjectConsiderationIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where projectConsideration equals to
        defaultNtQuoteProjectConsiderationsFiltering(
            "projectConsideration.equals=" + DEFAULT_PROJECT_CONSIDERATION,
            "projectConsideration.equals=" + UPDATED_PROJECT_CONSIDERATION
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsByProjectConsiderationIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where projectConsideration in
        defaultNtQuoteProjectConsiderationsFiltering(
            "projectConsideration.in=" + DEFAULT_PROJECT_CONSIDERATION + "," + UPDATED_PROJECT_CONSIDERATION,
            "projectConsideration.in=" + UPDATED_PROJECT_CONSIDERATION
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsByProjectConsiderationIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where projectConsideration is not null
        defaultNtQuoteProjectConsiderationsFiltering("projectConsideration.specified=true", "projectConsideration.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsByProjectConsiderationContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where projectConsideration contains
        defaultNtQuoteProjectConsiderationsFiltering(
            "projectConsideration.contains=" + DEFAULT_PROJECT_CONSIDERATION,
            "projectConsideration.contains=" + UPDATED_PROJECT_CONSIDERATION
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsByProjectConsiderationNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where projectConsideration does not contain
        defaultNtQuoteProjectConsiderationsFiltering(
            "projectConsideration.doesNotContain=" + UPDATED_PROJECT_CONSIDERATION,
            "projectConsideration.doesNotContain=" + DEFAULT_PROJECT_CONSIDERATION
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsByChoiceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where choice equals to
        defaultNtQuoteProjectConsiderationsFiltering("choice.equals=" + DEFAULT_CHOICE, "choice.equals=" + UPDATED_CHOICE);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsByChoiceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where choice in
        defaultNtQuoteProjectConsiderationsFiltering("choice.in=" + DEFAULT_CHOICE + "," + UPDATED_CHOICE, "choice.in=" + UPDATED_CHOICE);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsByChoiceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where choice is not null
        defaultNtQuoteProjectConsiderationsFiltering("choice.specified=true", "choice.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsByChoiceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where choice is greater than or equal to
        defaultNtQuoteProjectConsiderationsFiltering(
            "choice.greaterThanOrEqual=" + DEFAULT_CHOICE,
            "choice.greaterThanOrEqual=" + UPDATED_CHOICE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsByChoiceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where choice is less than or equal to
        defaultNtQuoteProjectConsiderationsFiltering(
            "choice.lessThanOrEqual=" + DEFAULT_CHOICE,
            "choice.lessThanOrEqual=" + SMALLER_CHOICE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsByChoiceIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where choice is less than
        defaultNtQuoteProjectConsiderationsFiltering("choice.lessThan=" + UPDATED_CHOICE, "choice.lessThan=" + DEFAULT_CHOICE);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsByChoiceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where choice is greater than
        defaultNtQuoteProjectConsiderationsFiltering("choice.greaterThan=" + SMALLER_CHOICE, "choice.greaterThan=" + DEFAULT_CHOICE);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where comments equals to
        defaultNtQuoteProjectConsiderationsFiltering("comments.equals=" + DEFAULT_COMMENTS, "comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where comments in
        defaultNtQuoteProjectConsiderationsFiltering(
            "comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS,
            "comments.in=" + UPDATED_COMMENTS
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where comments is not null
        defaultNtQuoteProjectConsiderationsFiltering("comments.specified=true", "comments.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsByCommentsContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where comments contains
        defaultNtQuoteProjectConsiderationsFiltering("comments.contains=" + DEFAULT_COMMENTS, "comments.contains=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsByCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where comments does not contain
        defaultNtQuoteProjectConsiderationsFiltering(
            "comments.doesNotContain=" + UPDATED_COMMENTS,
            "comments.doesNotContain=" + DEFAULT_COMMENTS
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where createdBy equals to
        defaultNtQuoteProjectConsiderationsFiltering("createdBy.equals=" + DEFAULT_CREATED_BY, "createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where createdBy in
        defaultNtQuoteProjectConsiderationsFiltering(
            "createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY,
            "createdBy.in=" + UPDATED_CREATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where createdBy is not null
        defaultNtQuoteProjectConsiderationsFiltering("createdBy.specified=true", "createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where createdBy contains
        defaultNtQuoteProjectConsiderationsFiltering(
            "createdBy.contains=" + DEFAULT_CREATED_BY,
            "createdBy.contains=" + UPDATED_CREATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where createdBy does not contain
        defaultNtQuoteProjectConsiderationsFiltering(
            "createdBy.doesNotContain=" + UPDATED_CREATED_BY,
            "createdBy.doesNotContain=" + DEFAULT_CREATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where createdDate equals to
        defaultNtQuoteProjectConsiderationsFiltering(
            "createdDate.equals=" + DEFAULT_CREATED_DATE,
            "createdDate.equals=" + UPDATED_CREATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where createdDate in
        defaultNtQuoteProjectConsiderationsFiltering(
            "createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE,
            "createdDate.in=" + UPDATED_CREATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where createdDate is not null
        defaultNtQuoteProjectConsiderationsFiltering("createdDate.specified=true", "createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where updatedBy equals to
        defaultNtQuoteProjectConsiderationsFiltering("updatedBy.equals=" + DEFAULT_UPDATED_BY, "updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where updatedBy in
        defaultNtQuoteProjectConsiderationsFiltering(
            "updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY,
            "updatedBy.in=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where updatedBy is not null
        defaultNtQuoteProjectConsiderationsFiltering("updatedBy.specified=true", "updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where updatedBy contains
        defaultNtQuoteProjectConsiderationsFiltering(
            "updatedBy.contains=" + DEFAULT_UPDATED_BY,
            "updatedBy.contains=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where updatedBy does not contain
        defaultNtQuoteProjectConsiderationsFiltering(
            "updatedBy.doesNotContain=" + UPDATED_UPDATED_BY,
            "updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsByUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where updatedDate equals to
        defaultNtQuoteProjectConsiderationsFiltering(
            "updatedDate.equals=" + DEFAULT_UPDATED_DATE,
            "updatedDate.equals=" + UPDATED_UPDATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsByUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where updatedDate in
        defaultNtQuoteProjectConsiderationsFiltering(
            "updatedDate.in=" + DEFAULT_UPDATED_DATE + "," + UPDATED_UPDATED_DATE,
            "updatedDate.in=" + UPDATED_UPDATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsByUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        // Get all the ntQuoteProjectConsiderationsList where updatedDate is not null
        defaultNtQuoteProjectConsiderationsFiltering("updatedDate.specified=true", "updatedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteProjectConsiderationsByNtQuoteIsEqualToSomething() throws Exception {
        NtQuote ntQuote;
        if (TestUtil.findAll(em, NtQuote.class).isEmpty()) {
            ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);
            ntQuote = NtQuoteResourceIT.createEntity();
        } else {
            ntQuote = TestUtil.findAll(em, NtQuote.class).get(0);
        }
        em.persist(ntQuote);
        em.flush();
        ntQuoteProjectConsiderations.setNtQuote(ntQuote);
        ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);
        Long ntQuoteId = ntQuote.getId();
        // Get all the ntQuoteProjectConsiderationsList where ntQuote equals to ntQuoteId
        defaultNtQuoteProjectConsiderationsShouldBeFound("ntQuoteId.equals=" + ntQuoteId);

        // Get all the ntQuoteProjectConsiderationsList where ntQuote equals to (ntQuoteId + 1)
        defaultNtQuoteProjectConsiderationsShouldNotBeFound("ntQuoteId.equals=" + (ntQuoteId + 1));
    }

    private void defaultNtQuoteProjectConsiderationsFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultNtQuoteProjectConsiderationsShouldBeFound(shouldBeFound);
        defaultNtQuoteProjectConsiderationsShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNtQuoteProjectConsiderationsShouldBeFound(String filter) throws Exception {
        restNtQuoteProjectConsiderationsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuoteProjectConsiderations.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].projectConsideration").value(hasItem(DEFAULT_PROJECT_CONSIDERATION)))
            .andExpect(jsonPath("$.[*].choice").value(hasItem(DEFAULT_CHOICE)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));

        // Check, that the count call also returns 1
        restNtQuoteProjectConsiderationsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNtQuoteProjectConsiderationsShouldNotBeFound(String filter) throws Exception {
        restNtQuoteProjectConsiderationsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNtQuoteProjectConsiderationsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNtQuoteProjectConsiderations() throws Exception {
        // Get the ntQuoteProjectConsiderations
        restNtQuoteProjectConsiderationsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNtQuoteProjectConsiderations() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        long databaseSizeBeforeUpdate = getRepositoryCount();
        ntQuoteProjectConsiderationsSearchRepository.save(ntQuoteProjectConsiderations);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteProjectConsiderationsSearchRepository.findAll());

        // Update the ntQuoteProjectConsiderations
        NtQuoteProjectConsiderations updatedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository
            .findById(ntQuoteProjectConsiderations.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedNtQuoteProjectConsiderations are not directly saved in db
        em.detach(updatedNtQuoteProjectConsiderations);
        updatedNtQuoteProjectConsiderations
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .projectConsideration(UPDATED_PROJECT_CONSIDERATION)
            .choice(UPDATED_CHOICE)
            .comments(UPDATED_COMMENTS)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);
        NtQuoteProjectConsiderationsDTO ntQuoteProjectConsiderationsDTO = ntQuoteProjectConsiderationsMapper.toDto(
            updatedNtQuoteProjectConsiderations
        );

        restNtQuoteProjectConsiderationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ntQuoteProjectConsiderationsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteProjectConsiderationsDTO))
            )
            .andExpect(status().isOk());

        // Validate the NtQuoteProjectConsiderations in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedNtQuoteProjectConsiderationsToMatchAllProperties(updatedNtQuoteProjectConsiderations);

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteProjectConsiderationsSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<NtQuoteProjectConsiderations> ntQuoteProjectConsiderationsSearchList = Streamable.of(
                    ntQuoteProjectConsiderationsSearchRepository.findAll()
                ).toList();
                NtQuoteProjectConsiderations testNtQuoteProjectConsiderationsSearch = ntQuoteProjectConsiderationsSearchList.get(
                    searchDatabaseSizeAfter - 1
                );

                assertNtQuoteProjectConsiderationsAllPropertiesEquals(
                    testNtQuoteProjectConsiderationsSearch,
                    updatedNtQuoteProjectConsiderations
                );
            });
    }

    @Test
    @Transactional
    void putNonExistingNtQuoteProjectConsiderations() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteProjectConsiderationsSearchRepository.findAll());
        ntQuoteProjectConsiderations.setId(longCount.incrementAndGet());

        // Create the NtQuoteProjectConsiderations
        NtQuoteProjectConsiderationsDTO ntQuoteProjectConsiderationsDTO = ntQuoteProjectConsiderationsMapper.toDto(
            ntQuoteProjectConsiderations
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNtQuoteProjectConsiderationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ntQuoteProjectConsiderationsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteProjectConsiderationsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteProjectConsiderations in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteProjectConsiderationsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchNtQuoteProjectConsiderations() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteProjectConsiderationsSearchRepository.findAll());
        ntQuoteProjectConsiderations.setId(longCount.incrementAndGet());

        // Create the NtQuoteProjectConsiderations
        NtQuoteProjectConsiderationsDTO ntQuoteProjectConsiderationsDTO = ntQuoteProjectConsiderationsMapper.toDto(
            ntQuoteProjectConsiderations
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteProjectConsiderationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteProjectConsiderationsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteProjectConsiderations in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteProjectConsiderationsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNtQuoteProjectConsiderations() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteProjectConsiderationsSearchRepository.findAll());
        ntQuoteProjectConsiderations.setId(longCount.incrementAndGet());

        // Create the NtQuoteProjectConsiderations
        NtQuoteProjectConsiderationsDTO ntQuoteProjectConsiderationsDTO = ntQuoteProjectConsiderationsMapper.toDto(
            ntQuoteProjectConsiderations
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteProjectConsiderationsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteProjectConsiderationsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NtQuoteProjectConsiderations in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteProjectConsiderationsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateNtQuoteProjectConsiderationsWithPatch() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ntQuoteProjectConsiderations using partial update
        NtQuoteProjectConsiderations partialUpdatedNtQuoteProjectConsiderations = new NtQuoteProjectConsiderations();
        partialUpdatedNtQuoteProjectConsiderations.setId(ntQuoteProjectConsiderations.getId());

        partialUpdatedNtQuoteProjectConsiderations
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .projectConsideration(UPDATED_PROJECT_CONSIDERATION)
            .createdBy(UPDATED_CREATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);

        restNtQuoteProjectConsiderationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNtQuoteProjectConsiderations.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNtQuoteProjectConsiderations))
            )
            .andExpect(status().isOk());

        // Validate the NtQuoteProjectConsiderations in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNtQuoteProjectConsiderationsUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedNtQuoteProjectConsiderations, ntQuoteProjectConsiderations),
            getPersistedNtQuoteProjectConsiderations(ntQuoteProjectConsiderations)
        );
    }

    @Test
    @Transactional
    void fullUpdateNtQuoteProjectConsiderationsWithPatch() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ntQuoteProjectConsiderations using partial update
        NtQuoteProjectConsiderations partialUpdatedNtQuoteProjectConsiderations = new NtQuoteProjectConsiderations();
        partialUpdatedNtQuoteProjectConsiderations.setId(ntQuoteProjectConsiderations.getId());

        partialUpdatedNtQuoteProjectConsiderations
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .projectConsideration(UPDATED_PROJECT_CONSIDERATION)
            .choice(UPDATED_CHOICE)
            .comments(UPDATED_COMMENTS)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);

        restNtQuoteProjectConsiderationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNtQuoteProjectConsiderations.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNtQuoteProjectConsiderations))
            )
            .andExpect(status().isOk());

        // Validate the NtQuoteProjectConsiderations in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNtQuoteProjectConsiderationsUpdatableFieldsEquals(
            partialUpdatedNtQuoteProjectConsiderations,
            getPersistedNtQuoteProjectConsiderations(partialUpdatedNtQuoteProjectConsiderations)
        );
    }

    @Test
    @Transactional
    void patchNonExistingNtQuoteProjectConsiderations() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteProjectConsiderationsSearchRepository.findAll());
        ntQuoteProjectConsiderations.setId(longCount.incrementAndGet());

        // Create the NtQuoteProjectConsiderations
        NtQuoteProjectConsiderationsDTO ntQuoteProjectConsiderationsDTO = ntQuoteProjectConsiderationsMapper.toDto(
            ntQuoteProjectConsiderations
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNtQuoteProjectConsiderationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ntQuoteProjectConsiderationsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ntQuoteProjectConsiderationsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteProjectConsiderations in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteProjectConsiderationsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNtQuoteProjectConsiderations() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteProjectConsiderationsSearchRepository.findAll());
        ntQuoteProjectConsiderations.setId(longCount.incrementAndGet());

        // Create the NtQuoteProjectConsiderations
        NtQuoteProjectConsiderationsDTO ntQuoteProjectConsiderationsDTO = ntQuoteProjectConsiderationsMapper.toDto(
            ntQuoteProjectConsiderations
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteProjectConsiderationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ntQuoteProjectConsiderationsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteProjectConsiderations in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteProjectConsiderationsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNtQuoteProjectConsiderations() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteProjectConsiderationsSearchRepository.findAll());
        ntQuoteProjectConsiderations.setId(longCount.incrementAndGet());

        // Create the NtQuoteProjectConsiderations
        NtQuoteProjectConsiderationsDTO ntQuoteProjectConsiderationsDTO = ntQuoteProjectConsiderationsMapper.toDto(
            ntQuoteProjectConsiderations
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteProjectConsiderationsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ntQuoteProjectConsiderationsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NtQuoteProjectConsiderations in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteProjectConsiderationsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteNtQuoteProjectConsiderations() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);
        ntQuoteProjectConsiderationsRepository.save(ntQuoteProjectConsiderations);
        ntQuoteProjectConsiderationsSearchRepository.save(ntQuoteProjectConsiderations);

        long databaseSizeBeforeDelete = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteProjectConsiderationsSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the ntQuoteProjectConsiderations
        restNtQuoteProjectConsiderationsMockMvc
            .perform(delete(ENTITY_API_URL_ID, ntQuoteProjectConsiderations.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteProjectConsiderationsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchNtQuoteProjectConsiderations() throws Exception {
        // Initialize the database
        insertedNtQuoteProjectConsiderations = ntQuoteProjectConsiderationsRepository.saveAndFlush(ntQuoteProjectConsiderations);
        ntQuoteProjectConsiderationsSearchRepository.save(ntQuoteProjectConsiderations);

        // Search the ntQuoteProjectConsiderations
        restNtQuoteProjectConsiderationsMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + ntQuoteProjectConsiderations.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuoteProjectConsiderations.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].projectConsideration").value(hasItem(DEFAULT_PROJECT_CONSIDERATION)))
            .andExpect(jsonPath("$.[*].choice").value(hasItem(DEFAULT_CHOICE)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    protected long getRepositoryCount() {
        return ntQuoteProjectConsiderationsRepository.count();
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

    protected NtQuoteProjectConsiderations getPersistedNtQuoteProjectConsiderations(
        NtQuoteProjectConsiderations ntQuoteProjectConsiderations
    ) {
        return ntQuoteProjectConsiderationsRepository.findById(ntQuoteProjectConsiderations.getId()).orElseThrow();
    }

    protected void assertPersistedNtQuoteProjectConsiderationsToMatchAllProperties(
        NtQuoteProjectConsiderations expectedNtQuoteProjectConsiderations
    ) {
        assertNtQuoteProjectConsiderationsAllPropertiesEquals(
            expectedNtQuoteProjectConsiderations,
            getPersistedNtQuoteProjectConsiderations(expectedNtQuoteProjectConsiderations)
        );
    }

    protected void assertPersistedNtQuoteProjectConsiderationsToMatchUpdatableProperties(
        NtQuoteProjectConsiderations expectedNtQuoteProjectConsiderations
    ) {
        assertNtQuoteProjectConsiderationsAllUpdatablePropertiesEquals(
            expectedNtQuoteProjectConsiderations,
            getPersistedNtQuoteProjectConsiderations(expectedNtQuoteProjectConsiderations)
        );
    }
}
