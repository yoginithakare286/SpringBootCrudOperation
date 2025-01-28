package com.yts.revaux.ntquote.web.rest;

import static com.yts.revaux.ntquote.domain.NtQuoteVendorQuoteAsserts.*;
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
import com.yts.revaux.ntquote.domain.NtQuoteVendorQuote;
import com.yts.revaux.ntquote.repository.NtQuoteVendorQuoteRepository;
import com.yts.revaux.ntquote.repository.search.NtQuoteVendorQuoteSearchRepository;
import com.yts.revaux.ntquote.service.dto.NtQuoteVendorQuoteDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuoteVendorQuoteMapper;
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
 * Integration tests for the {@link NtQuoteVendorQuoteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NtQuoteVendorQuoteResourceIT {

    private static final Integer DEFAULT_SR_NO = 1;
    private static final Integer UPDATED_SR_NO = 2;
    private static final Integer SMALLER_SR_NO = 1 - 1;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final String DEFAULT_VENDOR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_QUOTE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_QUOTE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_QUOTE_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_BROWSE = "AAAAAAAAAA";
    private static final String UPDATED_BROWSE = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/nt-quote-vendor-quotes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/nt-quote-vendor-quotes/_search";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NtQuoteVendorQuoteRepository ntQuoteVendorQuoteRepository;

    @Autowired
    private NtQuoteVendorQuoteMapper ntQuoteVendorQuoteMapper;

    @Autowired
    private NtQuoteVendorQuoteSearchRepository ntQuoteVendorQuoteSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNtQuoteVendorQuoteMockMvc;

    private NtQuoteVendorQuote ntQuoteVendorQuote;

    private NtQuoteVendorQuote insertedNtQuoteVendorQuote;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NtQuoteVendorQuote createEntity() {
        return new NtQuoteVendorQuote()
            .srNo(DEFAULT_SR_NO)
            .uid(DEFAULT_UID)
            .vendorName(DEFAULT_VENDOR_NAME)
            .quoteDate(DEFAULT_QUOTE_DATE)
            .fileName(DEFAULT_FILE_NAME)
            .country(DEFAULT_COUNTRY)
            .browse(DEFAULT_BROWSE)
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
    public static NtQuoteVendorQuote createUpdatedEntity() {
        return new NtQuoteVendorQuote()
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .vendorName(UPDATED_VENDOR_NAME)
            .quoteDate(UPDATED_QUOTE_DATE)
            .fileName(UPDATED_FILE_NAME)
            .country(UPDATED_COUNTRY)
            .browse(UPDATED_BROWSE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);
    }

    @BeforeEach
    public void initTest() {
        ntQuoteVendorQuote = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedNtQuoteVendorQuote != null) {
            ntQuoteVendorQuoteRepository.delete(insertedNtQuoteVendorQuote);
            ntQuoteVendorQuoteSearchRepository.delete(insertedNtQuoteVendorQuote);
            insertedNtQuoteVendorQuote = null;
        }
    }

    @Test
    @Transactional
    void createNtQuoteVendorQuote() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteVendorQuoteSearchRepository.findAll());
        // Create the NtQuoteVendorQuote
        NtQuoteVendorQuoteDTO ntQuoteVendorQuoteDTO = ntQuoteVendorQuoteMapper.toDto(ntQuoteVendorQuote);
        var returnedNtQuoteVendorQuoteDTO = om.readValue(
            restNtQuoteVendorQuoteMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteVendorQuoteDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            NtQuoteVendorQuoteDTO.class
        );

        // Validate the NtQuoteVendorQuote in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedNtQuoteVendorQuote = ntQuoteVendorQuoteMapper.toEntity(returnedNtQuoteVendorQuoteDTO);
        assertNtQuoteVendorQuoteUpdatableFieldsEquals(
            returnedNtQuoteVendorQuote,
            getPersistedNtQuoteVendorQuote(returnedNtQuoteVendorQuote)
        );

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteVendorQuoteSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });

        insertedNtQuoteVendorQuote = returnedNtQuoteVendorQuote;
    }

    @Test
    @Transactional
    void createNtQuoteVendorQuoteWithExistingId() throws Exception {
        // Create the NtQuoteVendorQuote with an existing ID
        ntQuoteVendorQuote.setId(1L);
        NtQuoteVendorQuoteDTO ntQuoteVendorQuoteDTO = ntQuoteVendorQuoteMapper.toDto(ntQuoteVendorQuote);

        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteVendorQuoteSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restNtQuoteVendorQuoteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteVendorQuoteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteVendorQuote in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteVendorQuoteSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkUidIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteVendorQuoteSearchRepository.findAll());
        // set the field null
        ntQuoteVendorQuote.setUid(null);

        // Create the NtQuoteVendorQuote, which fails.
        NtQuoteVendorQuoteDTO ntQuoteVendorQuoteDTO = ntQuoteVendorQuoteMapper.toDto(ntQuoteVendorQuote);

        restNtQuoteVendorQuoteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteVendorQuoteDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteVendorQuoteSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotes() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList
        restNtQuoteVendorQuoteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuoteVendorQuote.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].vendorName").value(hasItem(DEFAULT_VENDOR_NAME)))
            .andExpect(jsonPath("$.[*].quoteDate").value(hasItem(DEFAULT_QUOTE_DATE.toString())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].browse").value(hasItem(DEFAULT_BROWSE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getNtQuoteVendorQuote() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get the ntQuoteVendorQuote
        restNtQuoteVendorQuoteMockMvc
            .perform(get(ENTITY_API_URL_ID, ntQuoteVendorQuote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ntQuoteVendorQuote.getId().intValue()))
            .andExpect(jsonPath("$.srNo").value(DEFAULT_SR_NO))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.vendorName").value(DEFAULT_VENDOR_NAME))
            .andExpect(jsonPath("$.quoteDate").value(DEFAULT_QUOTE_DATE.toString()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.browse").value(DEFAULT_BROWSE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNtQuoteVendorQuotesByIdFiltering() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        Long id = ntQuoteVendorQuote.getId();

        defaultNtQuoteVendorQuoteFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultNtQuoteVendorQuoteFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultNtQuoteVendorQuoteFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesBySrNoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where srNo equals to
        defaultNtQuoteVendorQuoteFiltering("srNo.equals=" + DEFAULT_SR_NO, "srNo.equals=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesBySrNoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where srNo in
        defaultNtQuoteVendorQuoteFiltering("srNo.in=" + DEFAULT_SR_NO + "," + UPDATED_SR_NO, "srNo.in=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesBySrNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where srNo is not null
        defaultNtQuoteVendorQuoteFiltering("srNo.specified=true", "srNo.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesBySrNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where srNo is greater than or equal to
        defaultNtQuoteVendorQuoteFiltering("srNo.greaterThanOrEqual=" + DEFAULT_SR_NO, "srNo.greaterThanOrEqual=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesBySrNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where srNo is less than or equal to
        defaultNtQuoteVendorQuoteFiltering("srNo.lessThanOrEqual=" + DEFAULT_SR_NO, "srNo.lessThanOrEqual=" + SMALLER_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesBySrNoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where srNo is less than
        defaultNtQuoteVendorQuoteFiltering("srNo.lessThan=" + UPDATED_SR_NO, "srNo.lessThan=" + DEFAULT_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesBySrNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where srNo is greater than
        defaultNtQuoteVendorQuoteFiltering("srNo.greaterThan=" + SMALLER_SR_NO, "srNo.greaterThan=" + DEFAULT_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where uid equals to
        defaultNtQuoteVendorQuoteFiltering("uid.equals=" + DEFAULT_UID, "uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where uid in
        defaultNtQuoteVendorQuoteFiltering("uid.in=" + DEFAULT_UID + "," + UPDATED_UID, "uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where uid is not null
        defaultNtQuoteVendorQuoteFiltering("uid.specified=true", "uid.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByVendorNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where vendorName equals to
        defaultNtQuoteVendorQuoteFiltering("vendorName.equals=" + DEFAULT_VENDOR_NAME, "vendorName.equals=" + UPDATED_VENDOR_NAME);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByVendorNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where vendorName in
        defaultNtQuoteVendorQuoteFiltering(
            "vendorName.in=" + DEFAULT_VENDOR_NAME + "," + UPDATED_VENDOR_NAME,
            "vendorName.in=" + UPDATED_VENDOR_NAME
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByVendorNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where vendorName is not null
        defaultNtQuoteVendorQuoteFiltering("vendorName.specified=true", "vendorName.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByVendorNameContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where vendorName contains
        defaultNtQuoteVendorQuoteFiltering("vendorName.contains=" + DEFAULT_VENDOR_NAME, "vendorName.contains=" + UPDATED_VENDOR_NAME);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByVendorNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where vendorName does not contain
        defaultNtQuoteVendorQuoteFiltering(
            "vendorName.doesNotContain=" + UPDATED_VENDOR_NAME,
            "vendorName.doesNotContain=" + DEFAULT_VENDOR_NAME
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByQuoteDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where quoteDate equals to
        defaultNtQuoteVendorQuoteFiltering("quoteDate.equals=" + DEFAULT_QUOTE_DATE, "quoteDate.equals=" + UPDATED_QUOTE_DATE);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByQuoteDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where quoteDate in
        defaultNtQuoteVendorQuoteFiltering(
            "quoteDate.in=" + DEFAULT_QUOTE_DATE + "," + UPDATED_QUOTE_DATE,
            "quoteDate.in=" + UPDATED_QUOTE_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByQuoteDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where quoteDate is not null
        defaultNtQuoteVendorQuoteFiltering("quoteDate.specified=true", "quoteDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByQuoteDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where quoteDate is greater than or equal to
        defaultNtQuoteVendorQuoteFiltering(
            "quoteDate.greaterThanOrEqual=" + DEFAULT_QUOTE_DATE,
            "quoteDate.greaterThanOrEqual=" + UPDATED_QUOTE_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByQuoteDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where quoteDate is less than or equal to
        defaultNtQuoteVendorQuoteFiltering(
            "quoteDate.lessThanOrEqual=" + DEFAULT_QUOTE_DATE,
            "quoteDate.lessThanOrEqual=" + SMALLER_QUOTE_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByQuoteDateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where quoteDate is less than
        defaultNtQuoteVendorQuoteFiltering("quoteDate.lessThan=" + UPDATED_QUOTE_DATE, "quoteDate.lessThan=" + DEFAULT_QUOTE_DATE);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByQuoteDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where quoteDate is greater than
        defaultNtQuoteVendorQuoteFiltering("quoteDate.greaterThan=" + SMALLER_QUOTE_DATE, "quoteDate.greaterThan=" + DEFAULT_QUOTE_DATE);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByFileNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where fileName equals to
        defaultNtQuoteVendorQuoteFiltering("fileName.equals=" + DEFAULT_FILE_NAME, "fileName.equals=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByFileNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where fileName in
        defaultNtQuoteVendorQuoteFiltering(
            "fileName.in=" + DEFAULT_FILE_NAME + "," + UPDATED_FILE_NAME,
            "fileName.in=" + UPDATED_FILE_NAME
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByFileNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where fileName is not null
        defaultNtQuoteVendorQuoteFiltering("fileName.specified=true", "fileName.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByFileNameContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where fileName contains
        defaultNtQuoteVendorQuoteFiltering("fileName.contains=" + DEFAULT_FILE_NAME, "fileName.contains=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByFileNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where fileName does not contain
        defaultNtQuoteVendorQuoteFiltering("fileName.doesNotContain=" + UPDATED_FILE_NAME, "fileName.doesNotContain=" + DEFAULT_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where country equals to
        defaultNtQuoteVendorQuoteFiltering("country.equals=" + DEFAULT_COUNTRY, "country.equals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByCountryIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where country in
        defaultNtQuoteVendorQuoteFiltering("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY, "country.in=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where country is not null
        defaultNtQuoteVendorQuoteFiltering("country.specified=true", "country.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByCountryContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where country contains
        defaultNtQuoteVendorQuoteFiltering("country.contains=" + DEFAULT_COUNTRY, "country.contains=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByCountryNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where country does not contain
        defaultNtQuoteVendorQuoteFiltering("country.doesNotContain=" + UPDATED_COUNTRY, "country.doesNotContain=" + DEFAULT_COUNTRY);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByBrowseIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where browse equals to
        defaultNtQuoteVendorQuoteFiltering("browse.equals=" + DEFAULT_BROWSE, "browse.equals=" + UPDATED_BROWSE);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByBrowseIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where browse in
        defaultNtQuoteVendorQuoteFiltering("browse.in=" + DEFAULT_BROWSE + "," + UPDATED_BROWSE, "browse.in=" + UPDATED_BROWSE);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByBrowseIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where browse is not null
        defaultNtQuoteVendorQuoteFiltering("browse.specified=true", "browse.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByBrowseContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where browse contains
        defaultNtQuoteVendorQuoteFiltering("browse.contains=" + DEFAULT_BROWSE, "browse.contains=" + UPDATED_BROWSE);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByBrowseNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where browse does not contain
        defaultNtQuoteVendorQuoteFiltering("browse.doesNotContain=" + UPDATED_BROWSE, "browse.doesNotContain=" + DEFAULT_BROWSE);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where createdBy equals to
        defaultNtQuoteVendorQuoteFiltering("createdBy.equals=" + DEFAULT_CREATED_BY, "createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where createdBy in
        defaultNtQuoteVendorQuoteFiltering(
            "createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY,
            "createdBy.in=" + UPDATED_CREATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where createdBy is not null
        defaultNtQuoteVendorQuoteFiltering("createdBy.specified=true", "createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where createdBy contains
        defaultNtQuoteVendorQuoteFiltering("createdBy.contains=" + DEFAULT_CREATED_BY, "createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where createdBy does not contain
        defaultNtQuoteVendorQuoteFiltering(
            "createdBy.doesNotContain=" + UPDATED_CREATED_BY,
            "createdBy.doesNotContain=" + DEFAULT_CREATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where createdDate equals to
        defaultNtQuoteVendorQuoteFiltering("createdDate.equals=" + DEFAULT_CREATED_DATE, "createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where createdDate in
        defaultNtQuoteVendorQuoteFiltering(
            "createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE,
            "createdDate.in=" + UPDATED_CREATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where createdDate is not null
        defaultNtQuoteVendorQuoteFiltering("createdDate.specified=true", "createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where updatedBy equals to
        defaultNtQuoteVendorQuoteFiltering("updatedBy.equals=" + DEFAULT_UPDATED_BY, "updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where updatedBy in
        defaultNtQuoteVendorQuoteFiltering(
            "updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY,
            "updatedBy.in=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where updatedBy is not null
        defaultNtQuoteVendorQuoteFiltering("updatedBy.specified=true", "updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where updatedBy contains
        defaultNtQuoteVendorQuoteFiltering("updatedBy.contains=" + DEFAULT_UPDATED_BY, "updatedBy.contains=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where updatedBy does not contain
        defaultNtQuoteVendorQuoteFiltering(
            "updatedBy.doesNotContain=" + UPDATED_UPDATED_BY,
            "updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where updatedDate equals to
        defaultNtQuoteVendorQuoteFiltering("updatedDate.equals=" + DEFAULT_UPDATED_DATE, "updatedDate.equals=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where updatedDate in
        defaultNtQuoteVendorQuoteFiltering(
            "updatedDate.in=" + DEFAULT_UPDATED_DATE + "," + UPDATED_UPDATED_DATE,
            "updatedDate.in=" + UPDATED_UPDATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        // Get all the ntQuoteVendorQuoteList where updatedDate is not null
        defaultNtQuoteVendorQuoteFiltering("updatedDate.specified=true", "updatedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorQuotesByNtQuoteIsEqualToSomething() throws Exception {
        NtQuote ntQuote;
        if (TestUtil.findAll(em, NtQuote.class).isEmpty()) {
            ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);
            ntQuote = NtQuoteResourceIT.createEntity();
        } else {
            ntQuote = TestUtil.findAll(em, NtQuote.class).get(0);
        }
        em.persist(ntQuote);
        em.flush();
        ntQuoteVendorQuote.setNtQuote(ntQuote);
        ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);
        Long ntQuoteId = ntQuote.getId();
        // Get all the ntQuoteVendorQuoteList where ntQuote equals to ntQuoteId
        defaultNtQuoteVendorQuoteShouldBeFound("ntQuoteId.equals=" + ntQuoteId);

        // Get all the ntQuoteVendorQuoteList where ntQuote equals to (ntQuoteId + 1)
        defaultNtQuoteVendorQuoteShouldNotBeFound("ntQuoteId.equals=" + (ntQuoteId + 1));
    }

    private void defaultNtQuoteVendorQuoteFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultNtQuoteVendorQuoteShouldBeFound(shouldBeFound);
        defaultNtQuoteVendorQuoteShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNtQuoteVendorQuoteShouldBeFound(String filter) throws Exception {
        restNtQuoteVendorQuoteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuoteVendorQuote.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].vendorName").value(hasItem(DEFAULT_VENDOR_NAME)))
            .andExpect(jsonPath("$.[*].quoteDate").value(hasItem(DEFAULT_QUOTE_DATE.toString())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].browse").value(hasItem(DEFAULT_BROWSE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));

        // Check, that the count call also returns 1
        restNtQuoteVendorQuoteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNtQuoteVendorQuoteShouldNotBeFound(String filter) throws Exception {
        restNtQuoteVendorQuoteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNtQuoteVendorQuoteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNtQuoteVendorQuote() throws Exception {
        // Get the ntQuoteVendorQuote
        restNtQuoteVendorQuoteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNtQuoteVendorQuote() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        long databaseSizeBeforeUpdate = getRepositoryCount();
        ntQuoteVendorQuoteSearchRepository.save(ntQuoteVendorQuote);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteVendorQuoteSearchRepository.findAll());

        // Update the ntQuoteVendorQuote
        NtQuoteVendorQuote updatedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.findById(ntQuoteVendorQuote.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedNtQuoteVendorQuote are not directly saved in db
        em.detach(updatedNtQuoteVendorQuote);
        updatedNtQuoteVendorQuote
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .vendorName(UPDATED_VENDOR_NAME)
            .quoteDate(UPDATED_QUOTE_DATE)
            .fileName(UPDATED_FILE_NAME)
            .country(UPDATED_COUNTRY)
            .browse(UPDATED_BROWSE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);
        NtQuoteVendorQuoteDTO ntQuoteVendorQuoteDTO = ntQuoteVendorQuoteMapper.toDto(updatedNtQuoteVendorQuote);

        restNtQuoteVendorQuoteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ntQuoteVendorQuoteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteVendorQuoteDTO))
            )
            .andExpect(status().isOk());

        // Validate the NtQuoteVendorQuote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedNtQuoteVendorQuoteToMatchAllProperties(updatedNtQuoteVendorQuote);

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteVendorQuoteSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<NtQuoteVendorQuote> ntQuoteVendorQuoteSearchList = Streamable.of(
                    ntQuoteVendorQuoteSearchRepository.findAll()
                ).toList();
                NtQuoteVendorQuote testNtQuoteVendorQuoteSearch = ntQuoteVendorQuoteSearchList.get(searchDatabaseSizeAfter - 1);

                assertNtQuoteVendorQuoteAllPropertiesEquals(testNtQuoteVendorQuoteSearch, updatedNtQuoteVendorQuote);
            });
    }

    @Test
    @Transactional
    void putNonExistingNtQuoteVendorQuote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteVendorQuoteSearchRepository.findAll());
        ntQuoteVendorQuote.setId(longCount.incrementAndGet());

        // Create the NtQuoteVendorQuote
        NtQuoteVendorQuoteDTO ntQuoteVendorQuoteDTO = ntQuoteVendorQuoteMapper.toDto(ntQuoteVendorQuote);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNtQuoteVendorQuoteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ntQuoteVendorQuoteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteVendorQuoteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteVendorQuote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteVendorQuoteSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchNtQuoteVendorQuote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteVendorQuoteSearchRepository.findAll());
        ntQuoteVendorQuote.setId(longCount.incrementAndGet());

        // Create the NtQuoteVendorQuote
        NtQuoteVendorQuoteDTO ntQuoteVendorQuoteDTO = ntQuoteVendorQuoteMapper.toDto(ntQuoteVendorQuote);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteVendorQuoteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteVendorQuoteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteVendorQuote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteVendorQuoteSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNtQuoteVendorQuote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteVendorQuoteSearchRepository.findAll());
        ntQuoteVendorQuote.setId(longCount.incrementAndGet());

        // Create the NtQuoteVendorQuote
        NtQuoteVendorQuoteDTO ntQuoteVendorQuoteDTO = ntQuoteVendorQuoteMapper.toDto(ntQuoteVendorQuote);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteVendorQuoteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteVendorQuoteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NtQuoteVendorQuote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteVendorQuoteSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateNtQuoteVendorQuoteWithPatch() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ntQuoteVendorQuote using partial update
        NtQuoteVendorQuote partialUpdatedNtQuoteVendorQuote = new NtQuoteVendorQuote();
        partialUpdatedNtQuoteVendorQuote.setId(ntQuoteVendorQuote.getId());

        partialUpdatedNtQuoteVendorQuote
            .vendorName(UPDATED_VENDOR_NAME)
            .fileName(UPDATED_FILE_NAME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);

        restNtQuoteVendorQuoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNtQuoteVendorQuote.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNtQuoteVendorQuote))
            )
            .andExpect(status().isOk());

        // Validate the NtQuoteVendorQuote in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNtQuoteVendorQuoteUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedNtQuoteVendorQuote, ntQuoteVendorQuote),
            getPersistedNtQuoteVendorQuote(ntQuoteVendorQuote)
        );
    }

    @Test
    @Transactional
    void fullUpdateNtQuoteVendorQuoteWithPatch() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ntQuoteVendorQuote using partial update
        NtQuoteVendorQuote partialUpdatedNtQuoteVendorQuote = new NtQuoteVendorQuote();
        partialUpdatedNtQuoteVendorQuote.setId(ntQuoteVendorQuote.getId());

        partialUpdatedNtQuoteVendorQuote
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .vendorName(UPDATED_VENDOR_NAME)
            .quoteDate(UPDATED_QUOTE_DATE)
            .fileName(UPDATED_FILE_NAME)
            .country(UPDATED_COUNTRY)
            .browse(UPDATED_BROWSE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);

        restNtQuoteVendorQuoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNtQuoteVendorQuote.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNtQuoteVendorQuote))
            )
            .andExpect(status().isOk());

        // Validate the NtQuoteVendorQuote in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNtQuoteVendorQuoteUpdatableFieldsEquals(
            partialUpdatedNtQuoteVendorQuote,
            getPersistedNtQuoteVendorQuote(partialUpdatedNtQuoteVendorQuote)
        );
    }

    @Test
    @Transactional
    void patchNonExistingNtQuoteVendorQuote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteVendorQuoteSearchRepository.findAll());
        ntQuoteVendorQuote.setId(longCount.incrementAndGet());

        // Create the NtQuoteVendorQuote
        NtQuoteVendorQuoteDTO ntQuoteVendorQuoteDTO = ntQuoteVendorQuoteMapper.toDto(ntQuoteVendorQuote);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNtQuoteVendorQuoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ntQuoteVendorQuoteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ntQuoteVendorQuoteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteVendorQuote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteVendorQuoteSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNtQuoteVendorQuote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteVendorQuoteSearchRepository.findAll());
        ntQuoteVendorQuote.setId(longCount.incrementAndGet());

        // Create the NtQuoteVendorQuote
        NtQuoteVendorQuoteDTO ntQuoteVendorQuoteDTO = ntQuoteVendorQuoteMapper.toDto(ntQuoteVendorQuote);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteVendorQuoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ntQuoteVendorQuoteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteVendorQuote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteVendorQuoteSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNtQuoteVendorQuote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteVendorQuoteSearchRepository.findAll());
        ntQuoteVendorQuote.setId(longCount.incrementAndGet());

        // Create the NtQuoteVendorQuote
        NtQuoteVendorQuoteDTO ntQuoteVendorQuoteDTO = ntQuoteVendorQuoteMapper.toDto(ntQuoteVendorQuote);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteVendorQuoteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ntQuoteVendorQuoteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NtQuoteVendorQuote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteVendorQuoteSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteNtQuoteVendorQuote() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);
        ntQuoteVendorQuoteRepository.save(ntQuoteVendorQuote);
        ntQuoteVendorQuoteSearchRepository.save(ntQuoteVendorQuote);

        long databaseSizeBeforeDelete = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteVendorQuoteSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the ntQuoteVendorQuote
        restNtQuoteVendorQuoteMockMvc
            .perform(delete(ENTITY_API_URL_ID, ntQuoteVendorQuote.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteVendorQuoteSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchNtQuoteVendorQuote() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorQuote = ntQuoteVendorQuoteRepository.saveAndFlush(ntQuoteVendorQuote);
        ntQuoteVendorQuoteSearchRepository.save(ntQuoteVendorQuote);

        // Search the ntQuoteVendorQuote
        restNtQuoteVendorQuoteMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + ntQuoteVendorQuote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuoteVendorQuote.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].vendorName").value(hasItem(DEFAULT_VENDOR_NAME)))
            .andExpect(jsonPath("$.[*].quoteDate").value(hasItem(DEFAULT_QUOTE_DATE.toString())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].browse").value(hasItem(DEFAULT_BROWSE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    protected long getRepositoryCount() {
        return ntQuoteVendorQuoteRepository.count();
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

    protected NtQuoteVendorQuote getPersistedNtQuoteVendorQuote(NtQuoteVendorQuote ntQuoteVendorQuote) {
        return ntQuoteVendorQuoteRepository.findById(ntQuoteVendorQuote.getId()).orElseThrow();
    }

    protected void assertPersistedNtQuoteVendorQuoteToMatchAllProperties(NtQuoteVendorQuote expectedNtQuoteVendorQuote) {
        assertNtQuoteVendorQuoteAllPropertiesEquals(expectedNtQuoteVendorQuote, getPersistedNtQuoteVendorQuote(expectedNtQuoteVendorQuote));
    }

    protected void assertPersistedNtQuoteVendorQuoteToMatchUpdatableProperties(NtQuoteVendorQuote expectedNtQuoteVendorQuote) {
        assertNtQuoteVendorQuoteAllUpdatablePropertiesEquals(
            expectedNtQuoteVendorQuote,
            getPersistedNtQuoteVendorQuote(expectedNtQuoteVendorQuote)
        );
    }
}
