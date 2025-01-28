package com.yts.revaux.ntquote.web.rest;

import static com.yts.revaux.ntquote.domain.NtQuoteVendorPoAsserts.*;
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
import com.yts.revaux.ntquote.domain.NtQuoteVendorPo;
import com.yts.revaux.ntquote.repository.NtQuoteVendorPoRepository;
import com.yts.revaux.ntquote.repository.search.NtQuoteVendorPoSearchRepository;
import com.yts.revaux.ntquote.service.dto.NtQuoteVendorPoDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuoteVendorPoMapper;
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
 * Integration tests for the {@link NtQuoteVendorPoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NtQuoteVendorPoResourceIT {

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

    private static final String ENTITY_API_URL = "/api/nt-quote-vendor-pos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/nt-quote-vendor-pos/_search";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NtQuoteVendorPoRepository ntQuoteVendorPoRepository;

    @Autowired
    private NtQuoteVendorPoMapper ntQuoteVendorPoMapper;

    @Autowired
    private NtQuoteVendorPoSearchRepository ntQuoteVendorPoSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNtQuoteVendorPoMockMvc;

    private NtQuoteVendorPo ntQuoteVendorPo;

    private NtQuoteVendorPo insertedNtQuoteVendorPo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NtQuoteVendorPo createEntity() {
        return new NtQuoteVendorPo()
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
    public static NtQuoteVendorPo createUpdatedEntity() {
        return new NtQuoteVendorPo()
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
        ntQuoteVendorPo = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedNtQuoteVendorPo != null) {
            ntQuoteVendorPoRepository.delete(insertedNtQuoteVendorPo);
            ntQuoteVendorPoSearchRepository.delete(insertedNtQuoteVendorPo);
            insertedNtQuoteVendorPo = null;
        }
    }

    @Test
    @Transactional
    void createNtQuoteVendorPo() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteVendorPoSearchRepository.findAll());
        // Create the NtQuoteVendorPo
        NtQuoteVendorPoDTO ntQuoteVendorPoDTO = ntQuoteVendorPoMapper.toDto(ntQuoteVendorPo);
        var returnedNtQuoteVendorPoDTO = om.readValue(
            restNtQuoteVendorPoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteVendorPoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            NtQuoteVendorPoDTO.class
        );

        // Validate the NtQuoteVendorPo in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedNtQuoteVendorPo = ntQuoteVendorPoMapper.toEntity(returnedNtQuoteVendorPoDTO);
        assertNtQuoteVendorPoUpdatableFieldsEquals(returnedNtQuoteVendorPo, getPersistedNtQuoteVendorPo(returnedNtQuoteVendorPo));

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteVendorPoSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });

        insertedNtQuoteVendorPo = returnedNtQuoteVendorPo;
    }

    @Test
    @Transactional
    void createNtQuoteVendorPoWithExistingId() throws Exception {
        // Create the NtQuoteVendorPo with an existing ID
        ntQuoteVendorPo.setId(1L);
        NtQuoteVendorPoDTO ntQuoteVendorPoDTO = ntQuoteVendorPoMapper.toDto(ntQuoteVendorPo);

        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteVendorPoSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restNtQuoteVendorPoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteVendorPoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteVendorPo in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteVendorPoSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkUidIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteVendorPoSearchRepository.findAll());
        // set the field null
        ntQuoteVendorPo.setUid(null);

        // Create the NtQuoteVendorPo, which fails.
        NtQuoteVendorPoDTO ntQuoteVendorPoDTO = ntQuoteVendorPoMapper.toDto(ntQuoteVendorPo);

        restNtQuoteVendorPoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteVendorPoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteVendorPoSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPos() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList
        restNtQuoteVendorPoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuoteVendorPo.getId().intValue())))
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
    void getNtQuoteVendorPo() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get the ntQuoteVendorPo
        restNtQuoteVendorPoMockMvc
            .perform(get(ENTITY_API_URL_ID, ntQuoteVendorPo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ntQuoteVendorPo.getId().intValue()))
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
    void getNtQuoteVendorPosByIdFiltering() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        Long id = ntQuoteVendorPo.getId();

        defaultNtQuoteVendorPoFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultNtQuoteVendorPoFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultNtQuoteVendorPoFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosBySrNoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where srNo equals to
        defaultNtQuoteVendorPoFiltering("srNo.equals=" + DEFAULT_SR_NO, "srNo.equals=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosBySrNoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where srNo in
        defaultNtQuoteVendorPoFiltering("srNo.in=" + DEFAULT_SR_NO + "," + UPDATED_SR_NO, "srNo.in=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosBySrNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where srNo is not null
        defaultNtQuoteVendorPoFiltering("srNo.specified=true", "srNo.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosBySrNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where srNo is greater than or equal to
        defaultNtQuoteVendorPoFiltering("srNo.greaterThanOrEqual=" + DEFAULT_SR_NO, "srNo.greaterThanOrEqual=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosBySrNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where srNo is less than or equal to
        defaultNtQuoteVendorPoFiltering("srNo.lessThanOrEqual=" + DEFAULT_SR_NO, "srNo.lessThanOrEqual=" + SMALLER_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosBySrNoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where srNo is less than
        defaultNtQuoteVendorPoFiltering("srNo.lessThan=" + UPDATED_SR_NO, "srNo.lessThan=" + DEFAULT_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosBySrNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where srNo is greater than
        defaultNtQuoteVendorPoFiltering("srNo.greaterThan=" + SMALLER_SR_NO, "srNo.greaterThan=" + DEFAULT_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where uid equals to
        defaultNtQuoteVendorPoFiltering("uid.equals=" + DEFAULT_UID, "uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByUidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where uid in
        defaultNtQuoteVendorPoFiltering("uid.in=" + DEFAULT_UID + "," + UPDATED_UID, "uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where uid is not null
        defaultNtQuoteVendorPoFiltering("uid.specified=true", "uid.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByVendorNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where vendorName equals to
        defaultNtQuoteVendorPoFiltering("vendorName.equals=" + DEFAULT_VENDOR_NAME, "vendorName.equals=" + UPDATED_VENDOR_NAME);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByVendorNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where vendorName in
        defaultNtQuoteVendorPoFiltering(
            "vendorName.in=" + DEFAULT_VENDOR_NAME + "," + UPDATED_VENDOR_NAME,
            "vendorName.in=" + UPDATED_VENDOR_NAME
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByVendorNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where vendorName is not null
        defaultNtQuoteVendorPoFiltering("vendorName.specified=true", "vendorName.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByVendorNameContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where vendorName contains
        defaultNtQuoteVendorPoFiltering("vendorName.contains=" + DEFAULT_VENDOR_NAME, "vendorName.contains=" + UPDATED_VENDOR_NAME);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByVendorNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where vendorName does not contain
        defaultNtQuoteVendorPoFiltering(
            "vendorName.doesNotContain=" + UPDATED_VENDOR_NAME,
            "vendorName.doesNotContain=" + DEFAULT_VENDOR_NAME
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByQuoteDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where quoteDate equals to
        defaultNtQuoteVendorPoFiltering("quoteDate.equals=" + DEFAULT_QUOTE_DATE, "quoteDate.equals=" + UPDATED_QUOTE_DATE);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByQuoteDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where quoteDate in
        defaultNtQuoteVendorPoFiltering(
            "quoteDate.in=" + DEFAULT_QUOTE_DATE + "," + UPDATED_QUOTE_DATE,
            "quoteDate.in=" + UPDATED_QUOTE_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByQuoteDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where quoteDate is not null
        defaultNtQuoteVendorPoFiltering("quoteDate.specified=true", "quoteDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByQuoteDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where quoteDate is greater than or equal to
        defaultNtQuoteVendorPoFiltering(
            "quoteDate.greaterThanOrEqual=" + DEFAULT_QUOTE_DATE,
            "quoteDate.greaterThanOrEqual=" + UPDATED_QUOTE_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByQuoteDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where quoteDate is less than or equal to
        defaultNtQuoteVendorPoFiltering(
            "quoteDate.lessThanOrEqual=" + DEFAULT_QUOTE_DATE,
            "quoteDate.lessThanOrEqual=" + SMALLER_QUOTE_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByQuoteDateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where quoteDate is less than
        defaultNtQuoteVendorPoFiltering("quoteDate.lessThan=" + UPDATED_QUOTE_DATE, "quoteDate.lessThan=" + DEFAULT_QUOTE_DATE);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByQuoteDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where quoteDate is greater than
        defaultNtQuoteVendorPoFiltering("quoteDate.greaterThan=" + SMALLER_QUOTE_DATE, "quoteDate.greaterThan=" + DEFAULT_QUOTE_DATE);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByFileNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where fileName equals to
        defaultNtQuoteVendorPoFiltering("fileName.equals=" + DEFAULT_FILE_NAME, "fileName.equals=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByFileNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where fileName in
        defaultNtQuoteVendorPoFiltering("fileName.in=" + DEFAULT_FILE_NAME + "," + UPDATED_FILE_NAME, "fileName.in=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByFileNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where fileName is not null
        defaultNtQuoteVendorPoFiltering("fileName.specified=true", "fileName.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByFileNameContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where fileName contains
        defaultNtQuoteVendorPoFiltering("fileName.contains=" + DEFAULT_FILE_NAME, "fileName.contains=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByFileNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where fileName does not contain
        defaultNtQuoteVendorPoFiltering("fileName.doesNotContain=" + UPDATED_FILE_NAME, "fileName.doesNotContain=" + DEFAULT_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where country equals to
        defaultNtQuoteVendorPoFiltering("country.equals=" + DEFAULT_COUNTRY, "country.equals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByCountryIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where country in
        defaultNtQuoteVendorPoFiltering("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY, "country.in=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where country is not null
        defaultNtQuoteVendorPoFiltering("country.specified=true", "country.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByCountryContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where country contains
        defaultNtQuoteVendorPoFiltering("country.contains=" + DEFAULT_COUNTRY, "country.contains=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByCountryNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where country does not contain
        defaultNtQuoteVendorPoFiltering("country.doesNotContain=" + UPDATED_COUNTRY, "country.doesNotContain=" + DEFAULT_COUNTRY);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByBrowseIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where browse equals to
        defaultNtQuoteVendorPoFiltering("browse.equals=" + DEFAULT_BROWSE, "browse.equals=" + UPDATED_BROWSE);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByBrowseIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where browse in
        defaultNtQuoteVendorPoFiltering("browse.in=" + DEFAULT_BROWSE + "," + UPDATED_BROWSE, "browse.in=" + UPDATED_BROWSE);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByBrowseIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where browse is not null
        defaultNtQuoteVendorPoFiltering("browse.specified=true", "browse.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByBrowseContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where browse contains
        defaultNtQuoteVendorPoFiltering("browse.contains=" + DEFAULT_BROWSE, "browse.contains=" + UPDATED_BROWSE);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByBrowseNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where browse does not contain
        defaultNtQuoteVendorPoFiltering("browse.doesNotContain=" + UPDATED_BROWSE, "browse.doesNotContain=" + DEFAULT_BROWSE);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where createdBy equals to
        defaultNtQuoteVendorPoFiltering("createdBy.equals=" + DEFAULT_CREATED_BY, "createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where createdBy in
        defaultNtQuoteVendorPoFiltering(
            "createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY,
            "createdBy.in=" + UPDATED_CREATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where createdBy is not null
        defaultNtQuoteVendorPoFiltering("createdBy.specified=true", "createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where createdBy contains
        defaultNtQuoteVendorPoFiltering("createdBy.contains=" + DEFAULT_CREATED_BY, "createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where createdBy does not contain
        defaultNtQuoteVendorPoFiltering("createdBy.doesNotContain=" + UPDATED_CREATED_BY, "createdBy.doesNotContain=" + DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where createdDate equals to
        defaultNtQuoteVendorPoFiltering("createdDate.equals=" + DEFAULT_CREATED_DATE, "createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where createdDate in
        defaultNtQuoteVendorPoFiltering(
            "createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE,
            "createdDate.in=" + UPDATED_CREATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where createdDate is not null
        defaultNtQuoteVendorPoFiltering("createdDate.specified=true", "createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where updatedBy equals to
        defaultNtQuoteVendorPoFiltering("updatedBy.equals=" + DEFAULT_UPDATED_BY, "updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where updatedBy in
        defaultNtQuoteVendorPoFiltering(
            "updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY,
            "updatedBy.in=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where updatedBy is not null
        defaultNtQuoteVendorPoFiltering("updatedBy.specified=true", "updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where updatedBy contains
        defaultNtQuoteVendorPoFiltering("updatedBy.contains=" + DEFAULT_UPDATED_BY, "updatedBy.contains=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where updatedBy does not contain
        defaultNtQuoteVendorPoFiltering("updatedBy.doesNotContain=" + UPDATED_UPDATED_BY, "updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where updatedDate equals to
        defaultNtQuoteVendorPoFiltering("updatedDate.equals=" + DEFAULT_UPDATED_DATE, "updatedDate.equals=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where updatedDate in
        defaultNtQuoteVendorPoFiltering(
            "updatedDate.in=" + DEFAULT_UPDATED_DATE + "," + UPDATED_UPDATED_DATE,
            "updatedDate.in=" + UPDATED_UPDATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        // Get all the ntQuoteVendorPoList where updatedDate is not null
        defaultNtQuoteVendorPoFiltering("updatedDate.specified=true", "updatedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteVendorPosByNtQuoteIsEqualToSomething() throws Exception {
        NtQuote ntQuote;
        if (TestUtil.findAll(em, NtQuote.class).isEmpty()) {
            ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);
            ntQuote = NtQuoteResourceIT.createEntity();
        } else {
            ntQuote = TestUtil.findAll(em, NtQuote.class).get(0);
        }
        em.persist(ntQuote);
        em.flush();
        ntQuoteVendorPo.setNtQuote(ntQuote);
        ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);
        Long ntQuoteId = ntQuote.getId();
        // Get all the ntQuoteVendorPoList where ntQuote equals to ntQuoteId
        defaultNtQuoteVendorPoShouldBeFound("ntQuoteId.equals=" + ntQuoteId);

        // Get all the ntQuoteVendorPoList where ntQuote equals to (ntQuoteId + 1)
        defaultNtQuoteVendorPoShouldNotBeFound("ntQuoteId.equals=" + (ntQuoteId + 1));
    }

    private void defaultNtQuoteVendorPoFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultNtQuoteVendorPoShouldBeFound(shouldBeFound);
        defaultNtQuoteVendorPoShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNtQuoteVendorPoShouldBeFound(String filter) throws Exception {
        restNtQuoteVendorPoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuoteVendorPo.getId().intValue())))
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
        restNtQuoteVendorPoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNtQuoteVendorPoShouldNotBeFound(String filter) throws Exception {
        restNtQuoteVendorPoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNtQuoteVendorPoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNtQuoteVendorPo() throws Exception {
        // Get the ntQuoteVendorPo
        restNtQuoteVendorPoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNtQuoteVendorPo() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        long databaseSizeBeforeUpdate = getRepositoryCount();
        ntQuoteVendorPoSearchRepository.save(ntQuoteVendorPo);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteVendorPoSearchRepository.findAll());

        // Update the ntQuoteVendorPo
        NtQuoteVendorPo updatedNtQuoteVendorPo = ntQuoteVendorPoRepository.findById(ntQuoteVendorPo.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedNtQuoteVendorPo are not directly saved in db
        em.detach(updatedNtQuoteVendorPo);
        updatedNtQuoteVendorPo
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
        NtQuoteVendorPoDTO ntQuoteVendorPoDTO = ntQuoteVendorPoMapper.toDto(updatedNtQuoteVendorPo);

        restNtQuoteVendorPoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ntQuoteVendorPoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteVendorPoDTO))
            )
            .andExpect(status().isOk());

        // Validate the NtQuoteVendorPo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedNtQuoteVendorPoToMatchAllProperties(updatedNtQuoteVendorPo);

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteVendorPoSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<NtQuoteVendorPo> ntQuoteVendorPoSearchList = Streamable.of(ntQuoteVendorPoSearchRepository.findAll()).toList();
                NtQuoteVendorPo testNtQuoteVendorPoSearch = ntQuoteVendorPoSearchList.get(searchDatabaseSizeAfter - 1);

                assertNtQuoteVendorPoAllPropertiesEquals(testNtQuoteVendorPoSearch, updatedNtQuoteVendorPo);
            });
    }

    @Test
    @Transactional
    void putNonExistingNtQuoteVendorPo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteVendorPoSearchRepository.findAll());
        ntQuoteVendorPo.setId(longCount.incrementAndGet());

        // Create the NtQuoteVendorPo
        NtQuoteVendorPoDTO ntQuoteVendorPoDTO = ntQuoteVendorPoMapper.toDto(ntQuoteVendorPo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNtQuoteVendorPoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ntQuoteVendorPoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteVendorPoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteVendorPo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteVendorPoSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchNtQuoteVendorPo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteVendorPoSearchRepository.findAll());
        ntQuoteVendorPo.setId(longCount.incrementAndGet());

        // Create the NtQuoteVendorPo
        NtQuoteVendorPoDTO ntQuoteVendorPoDTO = ntQuoteVendorPoMapper.toDto(ntQuoteVendorPo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteVendorPoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteVendorPoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteVendorPo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteVendorPoSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNtQuoteVendorPo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteVendorPoSearchRepository.findAll());
        ntQuoteVendorPo.setId(longCount.incrementAndGet());

        // Create the NtQuoteVendorPo
        NtQuoteVendorPoDTO ntQuoteVendorPoDTO = ntQuoteVendorPoMapper.toDto(ntQuoteVendorPo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteVendorPoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteVendorPoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NtQuoteVendorPo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteVendorPoSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateNtQuoteVendorPoWithPatch() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ntQuoteVendorPo using partial update
        NtQuoteVendorPo partialUpdatedNtQuoteVendorPo = new NtQuoteVendorPo();
        partialUpdatedNtQuoteVendorPo.setId(ntQuoteVendorPo.getId());

        partialUpdatedNtQuoteVendorPo.browse(UPDATED_BROWSE).updatedDate(UPDATED_UPDATED_DATE);

        restNtQuoteVendorPoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNtQuoteVendorPo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNtQuoteVendorPo))
            )
            .andExpect(status().isOk());

        // Validate the NtQuoteVendorPo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNtQuoteVendorPoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedNtQuoteVendorPo, ntQuoteVendorPo),
            getPersistedNtQuoteVendorPo(ntQuoteVendorPo)
        );
    }

    @Test
    @Transactional
    void fullUpdateNtQuoteVendorPoWithPatch() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ntQuoteVendorPo using partial update
        NtQuoteVendorPo partialUpdatedNtQuoteVendorPo = new NtQuoteVendorPo();
        partialUpdatedNtQuoteVendorPo.setId(ntQuoteVendorPo.getId());

        partialUpdatedNtQuoteVendorPo
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

        restNtQuoteVendorPoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNtQuoteVendorPo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNtQuoteVendorPo))
            )
            .andExpect(status().isOk());

        // Validate the NtQuoteVendorPo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNtQuoteVendorPoUpdatableFieldsEquals(
            partialUpdatedNtQuoteVendorPo,
            getPersistedNtQuoteVendorPo(partialUpdatedNtQuoteVendorPo)
        );
    }

    @Test
    @Transactional
    void patchNonExistingNtQuoteVendorPo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteVendorPoSearchRepository.findAll());
        ntQuoteVendorPo.setId(longCount.incrementAndGet());

        // Create the NtQuoteVendorPo
        NtQuoteVendorPoDTO ntQuoteVendorPoDTO = ntQuoteVendorPoMapper.toDto(ntQuoteVendorPo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNtQuoteVendorPoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ntQuoteVendorPoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ntQuoteVendorPoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteVendorPo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteVendorPoSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNtQuoteVendorPo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteVendorPoSearchRepository.findAll());
        ntQuoteVendorPo.setId(longCount.incrementAndGet());

        // Create the NtQuoteVendorPo
        NtQuoteVendorPoDTO ntQuoteVendorPoDTO = ntQuoteVendorPoMapper.toDto(ntQuoteVendorPo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteVendorPoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ntQuoteVendorPoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteVendorPo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteVendorPoSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNtQuoteVendorPo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteVendorPoSearchRepository.findAll());
        ntQuoteVendorPo.setId(longCount.incrementAndGet());

        // Create the NtQuoteVendorPo
        NtQuoteVendorPoDTO ntQuoteVendorPoDTO = ntQuoteVendorPoMapper.toDto(ntQuoteVendorPo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteVendorPoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ntQuoteVendorPoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NtQuoteVendorPo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteVendorPoSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteNtQuoteVendorPo() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);
        ntQuoteVendorPoRepository.save(ntQuoteVendorPo);
        ntQuoteVendorPoSearchRepository.save(ntQuoteVendorPo);

        long databaseSizeBeforeDelete = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteVendorPoSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the ntQuoteVendorPo
        restNtQuoteVendorPoMockMvc
            .perform(delete(ENTITY_API_URL_ID, ntQuoteVendorPo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteVendorPoSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchNtQuoteVendorPo() throws Exception {
        // Initialize the database
        insertedNtQuoteVendorPo = ntQuoteVendorPoRepository.saveAndFlush(ntQuoteVendorPo);
        ntQuoteVendorPoSearchRepository.save(ntQuoteVendorPo);

        // Search the ntQuoteVendorPo
        restNtQuoteVendorPoMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + ntQuoteVendorPo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuoteVendorPo.getId().intValue())))
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
        return ntQuoteVendorPoRepository.count();
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

    protected NtQuoteVendorPo getPersistedNtQuoteVendorPo(NtQuoteVendorPo ntQuoteVendorPo) {
        return ntQuoteVendorPoRepository.findById(ntQuoteVendorPo.getId()).orElseThrow();
    }

    protected void assertPersistedNtQuoteVendorPoToMatchAllProperties(NtQuoteVendorPo expectedNtQuoteVendorPo) {
        assertNtQuoteVendorPoAllPropertiesEquals(expectedNtQuoteVendorPo, getPersistedNtQuoteVendorPo(expectedNtQuoteVendorPo));
    }

    protected void assertPersistedNtQuoteVendorPoToMatchUpdatableProperties(NtQuoteVendorPo expectedNtQuoteVendorPo) {
        assertNtQuoteVendorPoAllUpdatablePropertiesEquals(expectedNtQuoteVendorPo, getPersistedNtQuoteVendorPo(expectedNtQuoteVendorPo));
    }
}
