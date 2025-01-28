package com.yts.revaux.ntquote.web.rest;

import static com.yts.revaux.ntquote.domain.NtQuoteContractReviewInformationAsserts.*;
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
import com.yts.revaux.ntquote.domain.NtQuoteContractReviewInformation;
import com.yts.revaux.ntquote.repository.NtQuoteContractReviewInformationRepository;
import com.yts.revaux.ntquote.repository.search.NtQuoteContractReviewInformationSearchRepository;
import com.yts.revaux.ntquote.service.dto.NtQuoteContractReviewInformationDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuoteContractReviewInformationMapper;
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
 * Integration tests for the {@link NtQuoteContractReviewInformationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NtQuoteContractReviewInformationResourceIT {

    private static final Integer DEFAULT_SR_NO = 1;
    private static final Integer UPDATED_SR_NO = 2;
    private static final Integer SMALLER_SR_NO = 1 - 1;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final String DEFAULT_CONTRACT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CONTRACT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_REVISION = "AAAAAAAAAA";
    private static final String UPDATED_REVISION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_REVIEW_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REVIEW_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_REVIEW_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/nt-quote-contract-review-informations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/nt-quote-contract-review-informations/_search";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NtQuoteContractReviewInformationRepository ntQuoteContractReviewInformationRepository;

    @Autowired
    private NtQuoteContractReviewInformationMapper ntQuoteContractReviewInformationMapper;

    @Autowired
    private NtQuoteContractReviewInformationSearchRepository ntQuoteContractReviewInformationSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNtQuoteContractReviewInformationMockMvc;

    private NtQuoteContractReviewInformation ntQuoteContractReviewInformation;

    private NtQuoteContractReviewInformation insertedNtQuoteContractReviewInformation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NtQuoteContractReviewInformation createEntity() {
        return new NtQuoteContractReviewInformation()
            .srNo(DEFAULT_SR_NO)
            .uid(DEFAULT_UID)
            .contractNumber(DEFAULT_CONTRACT_NUMBER)
            .revision(DEFAULT_REVISION)
            .reviewDate(DEFAULT_REVIEW_DATE)
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
    public static NtQuoteContractReviewInformation createUpdatedEntity() {
        return new NtQuoteContractReviewInformation()
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .contractNumber(UPDATED_CONTRACT_NUMBER)
            .revision(UPDATED_REVISION)
            .reviewDate(UPDATED_REVIEW_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);
    }

    @BeforeEach
    public void initTest() {
        ntQuoteContractReviewInformation = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedNtQuoteContractReviewInformation != null) {
            ntQuoteContractReviewInformationRepository.delete(insertedNtQuoteContractReviewInformation);
            ntQuoteContractReviewInformationSearchRepository.delete(insertedNtQuoteContractReviewInformation);
            insertedNtQuoteContractReviewInformation = null;
        }
    }

    @Test
    @Transactional
    void createNtQuoteContractReviewInformation() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteContractReviewInformationSearchRepository.findAll());
        // Create the NtQuoteContractReviewInformation
        NtQuoteContractReviewInformationDTO ntQuoteContractReviewInformationDTO = ntQuoteContractReviewInformationMapper.toDto(
            ntQuoteContractReviewInformation
        );
        var returnedNtQuoteContractReviewInformationDTO = om.readValue(
            restNtQuoteContractReviewInformationMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(ntQuoteContractReviewInformationDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            NtQuoteContractReviewInformationDTO.class
        );

        // Validate the NtQuoteContractReviewInformation in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationMapper.toEntity(
            returnedNtQuoteContractReviewInformationDTO
        );
        assertNtQuoteContractReviewInformationUpdatableFieldsEquals(
            returnedNtQuoteContractReviewInformation,
            getPersistedNtQuoteContractReviewInformation(returnedNtQuoteContractReviewInformation)
        );

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteContractReviewInformationSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });

        insertedNtQuoteContractReviewInformation = returnedNtQuoteContractReviewInformation;
    }

    @Test
    @Transactional
    void createNtQuoteContractReviewInformationWithExistingId() throws Exception {
        // Create the NtQuoteContractReviewInformation with an existing ID
        ntQuoteContractReviewInformation.setId(1L);
        NtQuoteContractReviewInformationDTO ntQuoteContractReviewInformationDTO = ntQuoteContractReviewInformationMapper.toDto(
            ntQuoteContractReviewInformation
        );

        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteContractReviewInformationSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restNtQuoteContractReviewInformationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteContractReviewInformationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteContractReviewInformation in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteContractReviewInformationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkUidIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteContractReviewInformationSearchRepository.findAll());
        // set the field null
        ntQuoteContractReviewInformation.setUid(null);

        // Create the NtQuoteContractReviewInformation, which fails.
        NtQuoteContractReviewInformationDTO ntQuoteContractReviewInformationDTO = ntQuoteContractReviewInformationMapper.toDto(
            ntQuoteContractReviewInformation
        );

        restNtQuoteContractReviewInformationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteContractReviewInformationDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteContractReviewInformationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformations() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList
        restNtQuoteContractReviewInformationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuoteContractReviewInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].contractNumber").value(hasItem(DEFAULT_CONTRACT_NUMBER)))
            .andExpect(jsonPath("$.[*].revision").value(hasItem(DEFAULT_REVISION)))
            .andExpect(jsonPath("$.[*].reviewDate").value(hasItem(DEFAULT_REVIEW_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getNtQuoteContractReviewInformation() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get the ntQuoteContractReviewInformation
        restNtQuoteContractReviewInformationMockMvc
            .perform(get(ENTITY_API_URL_ID, ntQuoteContractReviewInformation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ntQuoteContractReviewInformation.getId().intValue()))
            .andExpect(jsonPath("$.srNo").value(DEFAULT_SR_NO))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.contractNumber").value(DEFAULT_CONTRACT_NUMBER))
            .andExpect(jsonPath("$.revision").value(DEFAULT_REVISION))
            .andExpect(jsonPath("$.reviewDate").value(DEFAULT_REVIEW_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNtQuoteContractReviewInformationsByIdFiltering() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        Long id = ntQuoteContractReviewInformation.getId();

        defaultNtQuoteContractReviewInformationFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultNtQuoteContractReviewInformationFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultNtQuoteContractReviewInformationFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsBySrNoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where srNo equals to
        defaultNtQuoteContractReviewInformationFiltering("srNo.equals=" + DEFAULT_SR_NO, "srNo.equals=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsBySrNoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where srNo in
        defaultNtQuoteContractReviewInformationFiltering("srNo.in=" + DEFAULT_SR_NO + "," + UPDATED_SR_NO, "srNo.in=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsBySrNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where srNo is not null
        defaultNtQuoteContractReviewInformationFiltering("srNo.specified=true", "srNo.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsBySrNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where srNo is greater than or equal to
        defaultNtQuoteContractReviewInformationFiltering(
            "srNo.greaterThanOrEqual=" + DEFAULT_SR_NO,
            "srNo.greaterThanOrEqual=" + UPDATED_SR_NO
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsBySrNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where srNo is less than or equal to
        defaultNtQuoteContractReviewInformationFiltering("srNo.lessThanOrEqual=" + DEFAULT_SR_NO, "srNo.lessThanOrEqual=" + SMALLER_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsBySrNoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where srNo is less than
        defaultNtQuoteContractReviewInformationFiltering("srNo.lessThan=" + UPDATED_SR_NO, "srNo.lessThan=" + DEFAULT_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsBySrNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where srNo is greater than
        defaultNtQuoteContractReviewInformationFiltering("srNo.greaterThan=" + SMALLER_SR_NO, "srNo.greaterThan=" + DEFAULT_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where uid equals to
        defaultNtQuoteContractReviewInformationFiltering("uid.equals=" + DEFAULT_UID, "uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where uid in
        defaultNtQuoteContractReviewInformationFiltering("uid.in=" + DEFAULT_UID + "," + UPDATED_UID, "uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where uid is not null
        defaultNtQuoteContractReviewInformationFiltering("uid.specified=true", "uid.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsByContractNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where contractNumber equals to
        defaultNtQuoteContractReviewInformationFiltering(
            "contractNumber.equals=" + DEFAULT_CONTRACT_NUMBER,
            "contractNumber.equals=" + UPDATED_CONTRACT_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsByContractNumberIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where contractNumber in
        defaultNtQuoteContractReviewInformationFiltering(
            "contractNumber.in=" + DEFAULT_CONTRACT_NUMBER + "," + UPDATED_CONTRACT_NUMBER,
            "contractNumber.in=" + UPDATED_CONTRACT_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsByContractNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where contractNumber is not null
        defaultNtQuoteContractReviewInformationFiltering("contractNumber.specified=true", "contractNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsByContractNumberContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where contractNumber contains
        defaultNtQuoteContractReviewInformationFiltering(
            "contractNumber.contains=" + DEFAULT_CONTRACT_NUMBER,
            "contractNumber.contains=" + UPDATED_CONTRACT_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsByContractNumberNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where contractNumber does not contain
        defaultNtQuoteContractReviewInformationFiltering(
            "contractNumber.doesNotContain=" + UPDATED_CONTRACT_NUMBER,
            "contractNumber.doesNotContain=" + DEFAULT_CONTRACT_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsByRevisionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where revision equals to
        defaultNtQuoteContractReviewInformationFiltering("revision.equals=" + DEFAULT_REVISION, "revision.equals=" + UPDATED_REVISION);
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsByRevisionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where revision in
        defaultNtQuoteContractReviewInformationFiltering(
            "revision.in=" + DEFAULT_REVISION + "," + UPDATED_REVISION,
            "revision.in=" + UPDATED_REVISION
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsByRevisionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where revision is not null
        defaultNtQuoteContractReviewInformationFiltering("revision.specified=true", "revision.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsByRevisionContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where revision contains
        defaultNtQuoteContractReviewInformationFiltering("revision.contains=" + DEFAULT_REVISION, "revision.contains=" + UPDATED_REVISION);
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsByRevisionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where revision does not contain
        defaultNtQuoteContractReviewInformationFiltering(
            "revision.doesNotContain=" + UPDATED_REVISION,
            "revision.doesNotContain=" + DEFAULT_REVISION
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsByReviewDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where reviewDate equals to
        defaultNtQuoteContractReviewInformationFiltering(
            "reviewDate.equals=" + DEFAULT_REVIEW_DATE,
            "reviewDate.equals=" + UPDATED_REVIEW_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsByReviewDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where reviewDate in
        defaultNtQuoteContractReviewInformationFiltering(
            "reviewDate.in=" + DEFAULT_REVIEW_DATE + "," + UPDATED_REVIEW_DATE,
            "reviewDate.in=" + UPDATED_REVIEW_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsByReviewDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where reviewDate is not null
        defaultNtQuoteContractReviewInformationFiltering("reviewDate.specified=true", "reviewDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsByReviewDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where reviewDate is greater than or equal to
        defaultNtQuoteContractReviewInformationFiltering(
            "reviewDate.greaterThanOrEqual=" + DEFAULT_REVIEW_DATE,
            "reviewDate.greaterThanOrEqual=" + UPDATED_REVIEW_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsByReviewDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where reviewDate is less than or equal to
        defaultNtQuoteContractReviewInformationFiltering(
            "reviewDate.lessThanOrEqual=" + DEFAULT_REVIEW_DATE,
            "reviewDate.lessThanOrEqual=" + SMALLER_REVIEW_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsByReviewDateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where reviewDate is less than
        defaultNtQuoteContractReviewInformationFiltering(
            "reviewDate.lessThan=" + UPDATED_REVIEW_DATE,
            "reviewDate.lessThan=" + DEFAULT_REVIEW_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsByReviewDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where reviewDate is greater than
        defaultNtQuoteContractReviewInformationFiltering(
            "reviewDate.greaterThan=" + SMALLER_REVIEW_DATE,
            "reviewDate.greaterThan=" + DEFAULT_REVIEW_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where createdBy equals to
        defaultNtQuoteContractReviewInformationFiltering(
            "createdBy.equals=" + DEFAULT_CREATED_BY,
            "createdBy.equals=" + UPDATED_CREATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where createdBy in
        defaultNtQuoteContractReviewInformationFiltering(
            "createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY,
            "createdBy.in=" + UPDATED_CREATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where createdBy is not null
        defaultNtQuoteContractReviewInformationFiltering("createdBy.specified=true", "createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where createdBy contains
        defaultNtQuoteContractReviewInformationFiltering(
            "createdBy.contains=" + DEFAULT_CREATED_BY,
            "createdBy.contains=" + UPDATED_CREATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where createdBy does not contain
        defaultNtQuoteContractReviewInformationFiltering(
            "createdBy.doesNotContain=" + UPDATED_CREATED_BY,
            "createdBy.doesNotContain=" + DEFAULT_CREATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where createdDate equals to
        defaultNtQuoteContractReviewInformationFiltering(
            "createdDate.equals=" + DEFAULT_CREATED_DATE,
            "createdDate.equals=" + UPDATED_CREATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where createdDate in
        defaultNtQuoteContractReviewInformationFiltering(
            "createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE,
            "createdDate.in=" + UPDATED_CREATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where createdDate is not null
        defaultNtQuoteContractReviewInformationFiltering("createdDate.specified=true", "createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where updatedBy equals to
        defaultNtQuoteContractReviewInformationFiltering(
            "updatedBy.equals=" + DEFAULT_UPDATED_BY,
            "updatedBy.equals=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where updatedBy in
        defaultNtQuoteContractReviewInformationFiltering(
            "updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY,
            "updatedBy.in=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where updatedBy is not null
        defaultNtQuoteContractReviewInformationFiltering("updatedBy.specified=true", "updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where updatedBy contains
        defaultNtQuoteContractReviewInformationFiltering(
            "updatedBy.contains=" + DEFAULT_UPDATED_BY,
            "updatedBy.contains=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where updatedBy does not contain
        defaultNtQuoteContractReviewInformationFiltering(
            "updatedBy.doesNotContain=" + UPDATED_UPDATED_BY,
            "updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsByUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where updatedDate equals to
        defaultNtQuoteContractReviewInformationFiltering(
            "updatedDate.equals=" + DEFAULT_UPDATED_DATE,
            "updatedDate.equals=" + UPDATED_UPDATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsByUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where updatedDate in
        defaultNtQuoteContractReviewInformationFiltering(
            "updatedDate.in=" + DEFAULT_UPDATED_DATE + "," + UPDATED_UPDATED_DATE,
            "updatedDate.in=" + UPDATED_UPDATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsByUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        // Get all the ntQuoteContractReviewInformationList where updatedDate is not null
        defaultNtQuoteContractReviewInformationFiltering("updatedDate.specified=true", "updatedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteContractReviewInformationsByNtQuoteIsEqualToSomething() throws Exception {
        NtQuote ntQuote;
        if (TestUtil.findAll(em, NtQuote.class).isEmpty()) {
            ntQuoteContractReviewInformationRepository.saveAndFlush(ntQuoteContractReviewInformation);
            ntQuote = NtQuoteResourceIT.createEntity();
        } else {
            ntQuote = TestUtil.findAll(em, NtQuote.class).get(0);
        }
        em.persist(ntQuote);
        em.flush();
        ntQuoteContractReviewInformation.setNtQuote(ntQuote);
        ntQuoteContractReviewInformationRepository.saveAndFlush(ntQuoteContractReviewInformation);
        Long ntQuoteId = ntQuote.getId();
        // Get all the ntQuoteContractReviewInformationList where ntQuote equals to ntQuoteId
        defaultNtQuoteContractReviewInformationShouldBeFound("ntQuoteId.equals=" + ntQuoteId);

        // Get all the ntQuoteContractReviewInformationList where ntQuote equals to (ntQuoteId + 1)
        defaultNtQuoteContractReviewInformationShouldNotBeFound("ntQuoteId.equals=" + (ntQuoteId + 1));
    }

    private void defaultNtQuoteContractReviewInformationFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultNtQuoteContractReviewInformationShouldBeFound(shouldBeFound);
        defaultNtQuoteContractReviewInformationShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNtQuoteContractReviewInformationShouldBeFound(String filter) throws Exception {
        restNtQuoteContractReviewInformationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuoteContractReviewInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].contractNumber").value(hasItem(DEFAULT_CONTRACT_NUMBER)))
            .andExpect(jsonPath("$.[*].revision").value(hasItem(DEFAULT_REVISION)))
            .andExpect(jsonPath("$.[*].reviewDate").value(hasItem(DEFAULT_REVIEW_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));

        // Check, that the count call also returns 1
        restNtQuoteContractReviewInformationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNtQuoteContractReviewInformationShouldNotBeFound(String filter) throws Exception {
        restNtQuoteContractReviewInformationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNtQuoteContractReviewInformationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNtQuoteContractReviewInformation() throws Exception {
        // Get the ntQuoteContractReviewInformation
        restNtQuoteContractReviewInformationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNtQuoteContractReviewInformation() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();
        ntQuoteContractReviewInformationSearchRepository.save(ntQuoteContractReviewInformation);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteContractReviewInformationSearchRepository.findAll());

        // Update the ntQuoteContractReviewInformation
        NtQuoteContractReviewInformation updatedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository
            .findById(ntQuoteContractReviewInformation.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedNtQuoteContractReviewInformation are not directly saved in db
        em.detach(updatedNtQuoteContractReviewInformation);
        updatedNtQuoteContractReviewInformation
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .contractNumber(UPDATED_CONTRACT_NUMBER)
            .revision(UPDATED_REVISION)
            .reviewDate(UPDATED_REVIEW_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);
        NtQuoteContractReviewInformationDTO ntQuoteContractReviewInformationDTO = ntQuoteContractReviewInformationMapper.toDto(
            updatedNtQuoteContractReviewInformation
        );

        restNtQuoteContractReviewInformationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ntQuoteContractReviewInformationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteContractReviewInformationDTO))
            )
            .andExpect(status().isOk());

        // Validate the NtQuoteContractReviewInformation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedNtQuoteContractReviewInformationToMatchAllProperties(updatedNtQuoteContractReviewInformation);

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteContractReviewInformationSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<NtQuoteContractReviewInformation> ntQuoteContractReviewInformationSearchList = Streamable.of(
                    ntQuoteContractReviewInformationSearchRepository.findAll()
                ).toList();
                NtQuoteContractReviewInformation testNtQuoteContractReviewInformationSearch =
                    ntQuoteContractReviewInformationSearchList.get(searchDatabaseSizeAfter - 1);

                assertNtQuoteContractReviewInformationAllPropertiesEquals(
                    testNtQuoteContractReviewInformationSearch,
                    updatedNtQuoteContractReviewInformation
                );
            });
    }

    @Test
    @Transactional
    void putNonExistingNtQuoteContractReviewInformation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteContractReviewInformationSearchRepository.findAll());
        ntQuoteContractReviewInformation.setId(longCount.incrementAndGet());

        // Create the NtQuoteContractReviewInformation
        NtQuoteContractReviewInformationDTO ntQuoteContractReviewInformationDTO = ntQuoteContractReviewInformationMapper.toDto(
            ntQuoteContractReviewInformation
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNtQuoteContractReviewInformationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ntQuoteContractReviewInformationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteContractReviewInformationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteContractReviewInformation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteContractReviewInformationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchNtQuoteContractReviewInformation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteContractReviewInformationSearchRepository.findAll());
        ntQuoteContractReviewInformation.setId(longCount.incrementAndGet());

        // Create the NtQuoteContractReviewInformation
        NtQuoteContractReviewInformationDTO ntQuoteContractReviewInformationDTO = ntQuoteContractReviewInformationMapper.toDto(
            ntQuoteContractReviewInformation
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteContractReviewInformationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteContractReviewInformationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteContractReviewInformation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteContractReviewInformationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNtQuoteContractReviewInformation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteContractReviewInformationSearchRepository.findAll());
        ntQuoteContractReviewInformation.setId(longCount.incrementAndGet());

        // Create the NtQuoteContractReviewInformation
        NtQuoteContractReviewInformationDTO ntQuoteContractReviewInformationDTO = ntQuoteContractReviewInformationMapper.toDto(
            ntQuoteContractReviewInformation
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteContractReviewInformationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteContractReviewInformationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NtQuoteContractReviewInformation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteContractReviewInformationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateNtQuoteContractReviewInformationWithPatch() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ntQuoteContractReviewInformation using partial update
        NtQuoteContractReviewInformation partialUpdatedNtQuoteContractReviewInformation = new NtQuoteContractReviewInformation();
        partialUpdatedNtQuoteContractReviewInformation.setId(ntQuoteContractReviewInformation.getId());

        partialUpdatedNtQuoteContractReviewInformation
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .contractNumber(UPDATED_CONTRACT_NUMBER)
            .reviewDate(UPDATED_REVIEW_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE);

        restNtQuoteContractReviewInformationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNtQuoteContractReviewInformation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNtQuoteContractReviewInformation))
            )
            .andExpect(status().isOk());

        // Validate the NtQuoteContractReviewInformation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNtQuoteContractReviewInformationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedNtQuoteContractReviewInformation, ntQuoteContractReviewInformation),
            getPersistedNtQuoteContractReviewInformation(ntQuoteContractReviewInformation)
        );
    }

    @Test
    @Transactional
    void fullUpdateNtQuoteContractReviewInformationWithPatch() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ntQuoteContractReviewInformation using partial update
        NtQuoteContractReviewInformation partialUpdatedNtQuoteContractReviewInformation = new NtQuoteContractReviewInformation();
        partialUpdatedNtQuoteContractReviewInformation.setId(ntQuoteContractReviewInformation.getId());

        partialUpdatedNtQuoteContractReviewInformation
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .contractNumber(UPDATED_CONTRACT_NUMBER)
            .revision(UPDATED_REVISION)
            .reviewDate(UPDATED_REVIEW_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);

        restNtQuoteContractReviewInformationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNtQuoteContractReviewInformation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNtQuoteContractReviewInformation))
            )
            .andExpect(status().isOk());

        // Validate the NtQuoteContractReviewInformation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNtQuoteContractReviewInformationUpdatableFieldsEquals(
            partialUpdatedNtQuoteContractReviewInformation,
            getPersistedNtQuoteContractReviewInformation(partialUpdatedNtQuoteContractReviewInformation)
        );
    }

    @Test
    @Transactional
    void patchNonExistingNtQuoteContractReviewInformation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteContractReviewInformationSearchRepository.findAll());
        ntQuoteContractReviewInformation.setId(longCount.incrementAndGet());

        // Create the NtQuoteContractReviewInformation
        NtQuoteContractReviewInformationDTO ntQuoteContractReviewInformationDTO = ntQuoteContractReviewInformationMapper.toDto(
            ntQuoteContractReviewInformation
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNtQuoteContractReviewInformationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ntQuoteContractReviewInformationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ntQuoteContractReviewInformationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteContractReviewInformation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteContractReviewInformationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNtQuoteContractReviewInformation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteContractReviewInformationSearchRepository.findAll());
        ntQuoteContractReviewInformation.setId(longCount.incrementAndGet());

        // Create the NtQuoteContractReviewInformation
        NtQuoteContractReviewInformationDTO ntQuoteContractReviewInformationDTO = ntQuoteContractReviewInformationMapper.toDto(
            ntQuoteContractReviewInformation
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteContractReviewInformationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ntQuoteContractReviewInformationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteContractReviewInformation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteContractReviewInformationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNtQuoteContractReviewInformation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteContractReviewInformationSearchRepository.findAll());
        ntQuoteContractReviewInformation.setId(longCount.incrementAndGet());

        // Create the NtQuoteContractReviewInformation
        NtQuoteContractReviewInformationDTO ntQuoteContractReviewInformationDTO = ntQuoteContractReviewInformationMapper.toDto(
            ntQuoteContractReviewInformation
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteContractReviewInformationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ntQuoteContractReviewInformationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NtQuoteContractReviewInformation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteContractReviewInformationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteNtQuoteContractReviewInformation() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );
        ntQuoteContractReviewInformationRepository.save(ntQuoteContractReviewInformation);
        ntQuoteContractReviewInformationSearchRepository.save(ntQuoteContractReviewInformation);

        long databaseSizeBeforeDelete = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteContractReviewInformationSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the ntQuoteContractReviewInformation
        restNtQuoteContractReviewInformationMockMvc
            .perform(delete(ENTITY_API_URL_ID, ntQuoteContractReviewInformation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteContractReviewInformationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchNtQuoteContractReviewInformation() throws Exception {
        // Initialize the database
        insertedNtQuoteContractReviewInformation = ntQuoteContractReviewInformationRepository.saveAndFlush(
            ntQuoteContractReviewInformation
        );
        ntQuoteContractReviewInformationSearchRepository.save(ntQuoteContractReviewInformation);

        // Search the ntQuoteContractReviewInformation
        restNtQuoteContractReviewInformationMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + ntQuoteContractReviewInformation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuoteContractReviewInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].contractNumber").value(hasItem(DEFAULT_CONTRACT_NUMBER)))
            .andExpect(jsonPath("$.[*].revision").value(hasItem(DEFAULT_REVISION)))
            .andExpect(jsonPath("$.[*].reviewDate").value(hasItem(DEFAULT_REVIEW_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    protected long getRepositoryCount() {
        return ntQuoteContractReviewInformationRepository.count();
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

    protected NtQuoteContractReviewInformation getPersistedNtQuoteContractReviewInformation(
        NtQuoteContractReviewInformation ntQuoteContractReviewInformation
    ) {
        return ntQuoteContractReviewInformationRepository.findById(ntQuoteContractReviewInformation.getId()).orElseThrow();
    }

    protected void assertPersistedNtQuoteContractReviewInformationToMatchAllProperties(
        NtQuoteContractReviewInformation expectedNtQuoteContractReviewInformation
    ) {
        assertNtQuoteContractReviewInformationAllPropertiesEquals(
            expectedNtQuoteContractReviewInformation,
            getPersistedNtQuoteContractReviewInformation(expectedNtQuoteContractReviewInformation)
        );
    }

    protected void assertPersistedNtQuoteContractReviewInformationToMatchUpdatableProperties(
        NtQuoteContractReviewInformation expectedNtQuoteContractReviewInformation
    ) {
        assertNtQuoteContractReviewInformationAllUpdatablePropertiesEquals(
            expectedNtQuoteContractReviewInformation,
            getPersistedNtQuoteContractReviewInformation(expectedNtQuoteContractReviewInformation)
        );
    }
}
