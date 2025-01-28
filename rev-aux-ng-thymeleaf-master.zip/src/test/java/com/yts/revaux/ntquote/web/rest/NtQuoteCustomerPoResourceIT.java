package com.yts.revaux.ntquote.web.rest;

import static com.yts.revaux.ntquote.domain.NtQuoteCustomerPoAsserts.*;
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
import com.yts.revaux.ntquote.domain.NtQuoteCustomerPo;
import com.yts.revaux.ntquote.repository.NtQuoteCustomerPoRepository;
import com.yts.revaux.ntquote.repository.search.NtQuoteCustomerPoSearchRepository;
import com.yts.revaux.ntquote.service.dto.NtQuoteCustomerPoDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuoteCustomerPoMapper;
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
 * Integration tests for the {@link NtQuoteCustomerPoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NtQuoteCustomerPoResourceIT {

    private static final Integer DEFAULT_SR_NO = 1;
    private static final Integer UPDATED_SR_NO = 2;
    private static final Integer SMALLER_SR_NO = 1 - 1;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final String DEFAULT_CUSTOMER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_NAME = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/nt-quote-customer-pos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/nt-quote-customer-pos/_search";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NtQuoteCustomerPoRepository ntQuoteCustomerPoRepository;

    @Autowired
    private NtQuoteCustomerPoMapper ntQuoteCustomerPoMapper;

    @Autowired
    private NtQuoteCustomerPoSearchRepository ntQuoteCustomerPoSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNtQuoteCustomerPoMockMvc;

    private NtQuoteCustomerPo ntQuoteCustomerPo;

    private NtQuoteCustomerPo insertedNtQuoteCustomerPo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NtQuoteCustomerPo createEntity() {
        return new NtQuoteCustomerPo()
            .srNo(DEFAULT_SR_NO)
            .uid(DEFAULT_UID)
            .customerName(DEFAULT_CUSTOMER_NAME)
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
    public static NtQuoteCustomerPo createUpdatedEntity() {
        return new NtQuoteCustomerPo()
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .customerName(UPDATED_CUSTOMER_NAME)
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
        ntQuoteCustomerPo = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedNtQuoteCustomerPo != null) {
            ntQuoteCustomerPoRepository.delete(insertedNtQuoteCustomerPo);
            ntQuoteCustomerPoSearchRepository.delete(insertedNtQuoteCustomerPo);
            insertedNtQuoteCustomerPo = null;
        }
    }

    @Test
    @Transactional
    void createNtQuoteCustomerPo() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerPoSearchRepository.findAll());
        // Create the NtQuoteCustomerPo
        NtQuoteCustomerPoDTO ntQuoteCustomerPoDTO = ntQuoteCustomerPoMapper.toDto(ntQuoteCustomerPo);
        var returnedNtQuoteCustomerPoDTO = om.readValue(
            restNtQuoteCustomerPoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteCustomerPoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            NtQuoteCustomerPoDTO.class
        );

        // Validate the NtQuoteCustomerPo in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedNtQuoteCustomerPo = ntQuoteCustomerPoMapper.toEntity(returnedNtQuoteCustomerPoDTO);
        assertNtQuoteCustomerPoUpdatableFieldsEquals(returnedNtQuoteCustomerPo, getPersistedNtQuoteCustomerPo(returnedNtQuoteCustomerPo));

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerPoSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });

        insertedNtQuoteCustomerPo = returnedNtQuoteCustomerPo;
    }

    @Test
    @Transactional
    void createNtQuoteCustomerPoWithExistingId() throws Exception {
        // Create the NtQuoteCustomerPo with an existing ID
        ntQuoteCustomerPo.setId(1L);
        NtQuoteCustomerPoDTO ntQuoteCustomerPoDTO = ntQuoteCustomerPoMapper.toDto(ntQuoteCustomerPo);

        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerPoSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restNtQuoteCustomerPoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteCustomerPoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteCustomerPo in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerPoSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkUidIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerPoSearchRepository.findAll());
        // set the field null
        ntQuoteCustomerPo.setUid(null);

        // Create the NtQuoteCustomerPo, which fails.
        NtQuoteCustomerPoDTO ntQuoteCustomerPoDTO = ntQuoteCustomerPoMapper.toDto(ntQuoteCustomerPo);

        restNtQuoteCustomerPoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteCustomerPoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerPoSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPos() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList
        restNtQuoteCustomerPoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuoteCustomerPo.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME)))
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
    void getNtQuoteCustomerPo() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get the ntQuoteCustomerPo
        restNtQuoteCustomerPoMockMvc
            .perform(get(ENTITY_API_URL_ID, ntQuoteCustomerPo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ntQuoteCustomerPo.getId().intValue()))
            .andExpect(jsonPath("$.srNo").value(DEFAULT_SR_NO))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.customerName").value(DEFAULT_CUSTOMER_NAME))
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
    void getNtQuoteCustomerPosByIdFiltering() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        Long id = ntQuoteCustomerPo.getId();

        defaultNtQuoteCustomerPoFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultNtQuoteCustomerPoFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultNtQuoteCustomerPoFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosBySrNoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where srNo equals to
        defaultNtQuoteCustomerPoFiltering("srNo.equals=" + DEFAULT_SR_NO, "srNo.equals=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosBySrNoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where srNo in
        defaultNtQuoteCustomerPoFiltering("srNo.in=" + DEFAULT_SR_NO + "," + UPDATED_SR_NO, "srNo.in=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosBySrNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where srNo is not null
        defaultNtQuoteCustomerPoFiltering("srNo.specified=true", "srNo.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosBySrNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where srNo is greater than or equal to
        defaultNtQuoteCustomerPoFiltering("srNo.greaterThanOrEqual=" + DEFAULT_SR_NO, "srNo.greaterThanOrEqual=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosBySrNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where srNo is less than or equal to
        defaultNtQuoteCustomerPoFiltering("srNo.lessThanOrEqual=" + DEFAULT_SR_NO, "srNo.lessThanOrEqual=" + SMALLER_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosBySrNoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where srNo is less than
        defaultNtQuoteCustomerPoFiltering("srNo.lessThan=" + UPDATED_SR_NO, "srNo.lessThan=" + DEFAULT_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosBySrNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where srNo is greater than
        defaultNtQuoteCustomerPoFiltering("srNo.greaterThan=" + SMALLER_SR_NO, "srNo.greaterThan=" + DEFAULT_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where uid equals to
        defaultNtQuoteCustomerPoFiltering("uid.equals=" + DEFAULT_UID, "uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByUidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where uid in
        defaultNtQuoteCustomerPoFiltering("uid.in=" + DEFAULT_UID + "," + UPDATED_UID, "uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where uid is not null
        defaultNtQuoteCustomerPoFiltering("uid.specified=true", "uid.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByCustomerNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where customerName equals to
        defaultNtQuoteCustomerPoFiltering("customerName.equals=" + DEFAULT_CUSTOMER_NAME, "customerName.equals=" + UPDATED_CUSTOMER_NAME);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByCustomerNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where customerName in
        defaultNtQuoteCustomerPoFiltering(
            "customerName.in=" + DEFAULT_CUSTOMER_NAME + "," + UPDATED_CUSTOMER_NAME,
            "customerName.in=" + UPDATED_CUSTOMER_NAME
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByCustomerNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where customerName is not null
        defaultNtQuoteCustomerPoFiltering("customerName.specified=true", "customerName.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByCustomerNameContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where customerName contains
        defaultNtQuoteCustomerPoFiltering(
            "customerName.contains=" + DEFAULT_CUSTOMER_NAME,
            "customerName.contains=" + UPDATED_CUSTOMER_NAME
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByCustomerNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where customerName does not contain
        defaultNtQuoteCustomerPoFiltering(
            "customerName.doesNotContain=" + UPDATED_CUSTOMER_NAME,
            "customerName.doesNotContain=" + DEFAULT_CUSTOMER_NAME
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByQuoteDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where quoteDate equals to
        defaultNtQuoteCustomerPoFiltering("quoteDate.equals=" + DEFAULT_QUOTE_DATE, "quoteDate.equals=" + UPDATED_QUOTE_DATE);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByQuoteDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where quoteDate in
        defaultNtQuoteCustomerPoFiltering(
            "quoteDate.in=" + DEFAULT_QUOTE_DATE + "," + UPDATED_QUOTE_DATE,
            "quoteDate.in=" + UPDATED_QUOTE_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByQuoteDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where quoteDate is not null
        defaultNtQuoteCustomerPoFiltering("quoteDate.specified=true", "quoteDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByQuoteDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where quoteDate is greater than or equal to
        defaultNtQuoteCustomerPoFiltering(
            "quoteDate.greaterThanOrEqual=" + DEFAULT_QUOTE_DATE,
            "quoteDate.greaterThanOrEqual=" + UPDATED_QUOTE_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByQuoteDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where quoteDate is less than or equal to
        defaultNtQuoteCustomerPoFiltering(
            "quoteDate.lessThanOrEqual=" + DEFAULT_QUOTE_DATE,
            "quoteDate.lessThanOrEqual=" + SMALLER_QUOTE_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByQuoteDateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where quoteDate is less than
        defaultNtQuoteCustomerPoFiltering("quoteDate.lessThan=" + UPDATED_QUOTE_DATE, "quoteDate.lessThan=" + DEFAULT_QUOTE_DATE);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByQuoteDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where quoteDate is greater than
        defaultNtQuoteCustomerPoFiltering("quoteDate.greaterThan=" + SMALLER_QUOTE_DATE, "quoteDate.greaterThan=" + DEFAULT_QUOTE_DATE);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByFileNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where fileName equals to
        defaultNtQuoteCustomerPoFiltering("fileName.equals=" + DEFAULT_FILE_NAME, "fileName.equals=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByFileNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where fileName in
        defaultNtQuoteCustomerPoFiltering("fileName.in=" + DEFAULT_FILE_NAME + "," + UPDATED_FILE_NAME, "fileName.in=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByFileNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where fileName is not null
        defaultNtQuoteCustomerPoFiltering("fileName.specified=true", "fileName.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByFileNameContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where fileName contains
        defaultNtQuoteCustomerPoFiltering("fileName.contains=" + DEFAULT_FILE_NAME, "fileName.contains=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByFileNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where fileName does not contain
        defaultNtQuoteCustomerPoFiltering("fileName.doesNotContain=" + UPDATED_FILE_NAME, "fileName.doesNotContain=" + DEFAULT_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where country equals to
        defaultNtQuoteCustomerPoFiltering("country.equals=" + DEFAULT_COUNTRY, "country.equals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByCountryIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where country in
        defaultNtQuoteCustomerPoFiltering("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY, "country.in=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where country is not null
        defaultNtQuoteCustomerPoFiltering("country.specified=true", "country.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByCountryContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where country contains
        defaultNtQuoteCustomerPoFiltering("country.contains=" + DEFAULT_COUNTRY, "country.contains=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByCountryNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where country does not contain
        defaultNtQuoteCustomerPoFiltering("country.doesNotContain=" + UPDATED_COUNTRY, "country.doesNotContain=" + DEFAULT_COUNTRY);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByBrowseIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where browse equals to
        defaultNtQuoteCustomerPoFiltering("browse.equals=" + DEFAULT_BROWSE, "browse.equals=" + UPDATED_BROWSE);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByBrowseIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where browse in
        defaultNtQuoteCustomerPoFiltering("browse.in=" + DEFAULT_BROWSE + "," + UPDATED_BROWSE, "browse.in=" + UPDATED_BROWSE);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByBrowseIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where browse is not null
        defaultNtQuoteCustomerPoFiltering("browse.specified=true", "browse.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByBrowseContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where browse contains
        defaultNtQuoteCustomerPoFiltering("browse.contains=" + DEFAULT_BROWSE, "browse.contains=" + UPDATED_BROWSE);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByBrowseNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where browse does not contain
        defaultNtQuoteCustomerPoFiltering("browse.doesNotContain=" + UPDATED_BROWSE, "browse.doesNotContain=" + DEFAULT_BROWSE);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where createdBy equals to
        defaultNtQuoteCustomerPoFiltering("createdBy.equals=" + DEFAULT_CREATED_BY, "createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where createdBy in
        defaultNtQuoteCustomerPoFiltering(
            "createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY,
            "createdBy.in=" + UPDATED_CREATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where createdBy is not null
        defaultNtQuoteCustomerPoFiltering("createdBy.specified=true", "createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where createdBy contains
        defaultNtQuoteCustomerPoFiltering("createdBy.contains=" + DEFAULT_CREATED_BY, "createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where createdBy does not contain
        defaultNtQuoteCustomerPoFiltering(
            "createdBy.doesNotContain=" + UPDATED_CREATED_BY,
            "createdBy.doesNotContain=" + DEFAULT_CREATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where createdDate equals to
        defaultNtQuoteCustomerPoFiltering("createdDate.equals=" + DEFAULT_CREATED_DATE, "createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where createdDate in
        defaultNtQuoteCustomerPoFiltering(
            "createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE,
            "createdDate.in=" + UPDATED_CREATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where createdDate is not null
        defaultNtQuoteCustomerPoFiltering("createdDate.specified=true", "createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where updatedBy equals to
        defaultNtQuoteCustomerPoFiltering("updatedBy.equals=" + DEFAULT_UPDATED_BY, "updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where updatedBy in
        defaultNtQuoteCustomerPoFiltering(
            "updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY,
            "updatedBy.in=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where updatedBy is not null
        defaultNtQuoteCustomerPoFiltering("updatedBy.specified=true", "updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where updatedBy contains
        defaultNtQuoteCustomerPoFiltering("updatedBy.contains=" + DEFAULT_UPDATED_BY, "updatedBy.contains=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where updatedBy does not contain
        defaultNtQuoteCustomerPoFiltering(
            "updatedBy.doesNotContain=" + UPDATED_UPDATED_BY,
            "updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where updatedDate equals to
        defaultNtQuoteCustomerPoFiltering("updatedDate.equals=" + DEFAULT_UPDATED_DATE, "updatedDate.equals=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where updatedDate in
        defaultNtQuoteCustomerPoFiltering(
            "updatedDate.in=" + DEFAULT_UPDATED_DATE + "," + UPDATED_UPDATED_DATE,
            "updatedDate.in=" + UPDATED_UPDATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        // Get all the ntQuoteCustomerPoList where updatedDate is not null
        defaultNtQuoteCustomerPoFiltering("updatedDate.specified=true", "updatedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerPosByNtQuoteIsEqualToSomething() throws Exception {
        NtQuote ntQuote;
        if (TestUtil.findAll(em, NtQuote.class).isEmpty()) {
            ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);
            ntQuote = NtQuoteResourceIT.createEntity();
        } else {
            ntQuote = TestUtil.findAll(em, NtQuote.class).get(0);
        }
        em.persist(ntQuote);
        em.flush();
        ntQuoteCustomerPo.setNtQuote(ntQuote);
        ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);
        Long ntQuoteId = ntQuote.getId();
        // Get all the ntQuoteCustomerPoList where ntQuote equals to ntQuoteId
        defaultNtQuoteCustomerPoShouldBeFound("ntQuoteId.equals=" + ntQuoteId);

        // Get all the ntQuoteCustomerPoList where ntQuote equals to (ntQuoteId + 1)
        defaultNtQuoteCustomerPoShouldNotBeFound("ntQuoteId.equals=" + (ntQuoteId + 1));
    }

    private void defaultNtQuoteCustomerPoFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultNtQuoteCustomerPoShouldBeFound(shouldBeFound);
        defaultNtQuoteCustomerPoShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNtQuoteCustomerPoShouldBeFound(String filter) throws Exception {
        restNtQuoteCustomerPoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuoteCustomerPo.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME)))
            .andExpect(jsonPath("$.[*].quoteDate").value(hasItem(DEFAULT_QUOTE_DATE.toString())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].browse").value(hasItem(DEFAULT_BROWSE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));

        // Check, that the count call also returns 1
        restNtQuoteCustomerPoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNtQuoteCustomerPoShouldNotBeFound(String filter) throws Exception {
        restNtQuoteCustomerPoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNtQuoteCustomerPoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNtQuoteCustomerPo() throws Exception {
        // Get the ntQuoteCustomerPo
        restNtQuoteCustomerPoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNtQuoteCustomerPo() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        long databaseSizeBeforeUpdate = getRepositoryCount();
        ntQuoteCustomerPoSearchRepository.save(ntQuoteCustomerPo);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerPoSearchRepository.findAll());

        // Update the ntQuoteCustomerPo
        NtQuoteCustomerPo updatedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.findById(ntQuoteCustomerPo.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedNtQuoteCustomerPo are not directly saved in db
        em.detach(updatedNtQuoteCustomerPo);
        updatedNtQuoteCustomerPo
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .customerName(UPDATED_CUSTOMER_NAME)
            .quoteDate(UPDATED_QUOTE_DATE)
            .fileName(UPDATED_FILE_NAME)
            .country(UPDATED_COUNTRY)
            .browse(UPDATED_BROWSE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);
        NtQuoteCustomerPoDTO ntQuoteCustomerPoDTO = ntQuoteCustomerPoMapper.toDto(updatedNtQuoteCustomerPo);

        restNtQuoteCustomerPoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ntQuoteCustomerPoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteCustomerPoDTO))
            )
            .andExpect(status().isOk());

        // Validate the NtQuoteCustomerPo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedNtQuoteCustomerPoToMatchAllProperties(updatedNtQuoteCustomerPo);

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerPoSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<NtQuoteCustomerPo> ntQuoteCustomerPoSearchList = Streamable.of(ntQuoteCustomerPoSearchRepository.findAll()).toList();
                NtQuoteCustomerPo testNtQuoteCustomerPoSearch = ntQuoteCustomerPoSearchList.get(searchDatabaseSizeAfter - 1);

                assertNtQuoteCustomerPoAllPropertiesEquals(testNtQuoteCustomerPoSearch, updatedNtQuoteCustomerPo);
            });
    }

    @Test
    @Transactional
    void putNonExistingNtQuoteCustomerPo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerPoSearchRepository.findAll());
        ntQuoteCustomerPo.setId(longCount.incrementAndGet());

        // Create the NtQuoteCustomerPo
        NtQuoteCustomerPoDTO ntQuoteCustomerPoDTO = ntQuoteCustomerPoMapper.toDto(ntQuoteCustomerPo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNtQuoteCustomerPoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ntQuoteCustomerPoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteCustomerPoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteCustomerPo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerPoSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchNtQuoteCustomerPo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerPoSearchRepository.findAll());
        ntQuoteCustomerPo.setId(longCount.incrementAndGet());

        // Create the NtQuoteCustomerPo
        NtQuoteCustomerPoDTO ntQuoteCustomerPoDTO = ntQuoteCustomerPoMapper.toDto(ntQuoteCustomerPo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteCustomerPoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteCustomerPoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteCustomerPo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerPoSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNtQuoteCustomerPo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerPoSearchRepository.findAll());
        ntQuoteCustomerPo.setId(longCount.incrementAndGet());

        // Create the NtQuoteCustomerPo
        NtQuoteCustomerPoDTO ntQuoteCustomerPoDTO = ntQuoteCustomerPoMapper.toDto(ntQuoteCustomerPo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteCustomerPoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteCustomerPoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NtQuoteCustomerPo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerPoSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateNtQuoteCustomerPoWithPatch() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ntQuoteCustomerPo using partial update
        NtQuoteCustomerPo partialUpdatedNtQuoteCustomerPo = new NtQuoteCustomerPo();
        partialUpdatedNtQuoteCustomerPo.setId(ntQuoteCustomerPo.getId());

        partialUpdatedNtQuoteCustomerPo
            .uid(UPDATED_UID)
            .quoteDate(UPDATED_QUOTE_DATE)
            .fileName(UPDATED_FILE_NAME)
            .browse(UPDATED_BROWSE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restNtQuoteCustomerPoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNtQuoteCustomerPo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNtQuoteCustomerPo))
            )
            .andExpect(status().isOk());

        // Validate the NtQuoteCustomerPo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNtQuoteCustomerPoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedNtQuoteCustomerPo, ntQuoteCustomerPo),
            getPersistedNtQuoteCustomerPo(ntQuoteCustomerPo)
        );
    }

    @Test
    @Transactional
    void fullUpdateNtQuoteCustomerPoWithPatch() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ntQuoteCustomerPo using partial update
        NtQuoteCustomerPo partialUpdatedNtQuoteCustomerPo = new NtQuoteCustomerPo();
        partialUpdatedNtQuoteCustomerPo.setId(ntQuoteCustomerPo.getId());

        partialUpdatedNtQuoteCustomerPo
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .customerName(UPDATED_CUSTOMER_NAME)
            .quoteDate(UPDATED_QUOTE_DATE)
            .fileName(UPDATED_FILE_NAME)
            .country(UPDATED_COUNTRY)
            .browse(UPDATED_BROWSE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);

        restNtQuoteCustomerPoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNtQuoteCustomerPo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNtQuoteCustomerPo))
            )
            .andExpect(status().isOk());

        // Validate the NtQuoteCustomerPo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNtQuoteCustomerPoUpdatableFieldsEquals(
            partialUpdatedNtQuoteCustomerPo,
            getPersistedNtQuoteCustomerPo(partialUpdatedNtQuoteCustomerPo)
        );
    }

    @Test
    @Transactional
    void patchNonExistingNtQuoteCustomerPo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerPoSearchRepository.findAll());
        ntQuoteCustomerPo.setId(longCount.incrementAndGet());

        // Create the NtQuoteCustomerPo
        NtQuoteCustomerPoDTO ntQuoteCustomerPoDTO = ntQuoteCustomerPoMapper.toDto(ntQuoteCustomerPo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNtQuoteCustomerPoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ntQuoteCustomerPoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ntQuoteCustomerPoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteCustomerPo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerPoSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNtQuoteCustomerPo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerPoSearchRepository.findAll());
        ntQuoteCustomerPo.setId(longCount.incrementAndGet());

        // Create the NtQuoteCustomerPo
        NtQuoteCustomerPoDTO ntQuoteCustomerPoDTO = ntQuoteCustomerPoMapper.toDto(ntQuoteCustomerPo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteCustomerPoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ntQuoteCustomerPoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteCustomerPo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerPoSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNtQuoteCustomerPo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerPoSearchRepository.findAll());
        ntQuoteCustomerPo.setId(longCount.incrementAndGet());

        // Create the NtQuoteCustomerPo
        NtQuoteCustomerPoDTO ntQuoteCustomerPoDTO = ntQuoteCustomerPoMapper.toDto(ntQuoteCustomerPo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteCustomerPoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ntQuoteCustomerPoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NtQuoteCustomerPo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerPoSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteNtQuoteCustomerPo() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);
        ntQuoteCustomerPoRepository.save(ntQuoteCustomerPo);
        ntQuoteCustomerPoSearchRepository.save(ntQuoteCustomerPo);

        long databaseSizeBeforeDelete = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerPoSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the ntQuoteCustomerPo
        restNtQuoteCustomerPoMockMvc
            .perform(delete(ENTITY_API_URL_ID, ntQuoteCustomerPo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerPoSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchNtQuoteCustomerPo() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerPo = ntQuoteCustomerPoRepository.saveAndFlush(ntQuoteCustomerPo);
        ntQuoteCustomerPoSearchRepository.save(ntQuoteCustomerPo);

        // Search the ntQuoteCustomerPo
        restNtQuoteCustomerPoMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + ntQuoteCustomerPo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuoteCustomerPo.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME)))
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
        return ntQuoteCustomerPoRepository.count();
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

    protected NtQuoteCustomerPo getPersistedNtQuoteCustomerPo(NtQuoteCustomerPo ntQuoteCustomerPo) {
        return ntQuoteCustomerPoRepository.findById(ntQuoteCustomerPo.getId()).orElseThrow();
    }

    protected void assertPersistedNtQuoteCustomerPoToMatchAllProperties(NtQuoteCustomerPo expectedNtQuoteCustomerPo) {
        assertNtQuoteCustomerPoAllPropertiesEquals(expectedNtQuoteCustomerPo, getPersistedNtQuoteCustomerPo(expectedNtQuoteCustomerPo));
    }

    protected void assertPersistedNtQuoteCustomerPoToMatchUpdatableProperties(NtQuoteCustomerPo expectedNtQuoteCustomerPo) {
        assertNtQuoteCustomerPoAllUpdatablePropertiesEquals(
            expectedNtQuoteCustomerPo,
            getPersistedNtQuoteCustomerPo(expectedNtQuoteCustomerPo)
        );
    }
}
