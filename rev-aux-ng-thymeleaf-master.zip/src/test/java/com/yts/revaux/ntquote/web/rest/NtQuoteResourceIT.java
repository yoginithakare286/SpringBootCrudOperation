package com.yts.revaux.ntquote.web.rest;

import static com.yts.revaux.ntquote.domain.NtQuoteAsserts.*;
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
import com.yts.revaux.ntquote.domain.NtQuoteProjectApproval;
import com.yts.revaux.ntquote.domain.RfqDetail;
import com.yts.revaux.ntquote.repository.NtQuoteRepository;
import com.yts.revaux.ntquote.repository.search.NtQuoteSearchRepository;
import com.yts.revaux.ntquote.service.dto.NtQuoteDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuoteMapper;
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
 * Integration tests for the {@link NtQuoteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NtQuoteResourceIT {

    private static final Integer DEFAULT_SR_NO = 1;
    private static final Integer UPDATED_SR_NO = 2;
    private static final Integer SMALLER_SR_NO = 1 - 1;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final String DEFAULT_QUOTE_KEY = "AAAAAAAAAA";
    private static final String UPDATED_QUOTE_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_SALES_PERSON = "AAAAAAAAAA";
    private static final String UPDATED_SALES_PERSON = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_QUOTE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_QUOTE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_MOLD_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_MOLD_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_PART_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PART_NUMBER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DUE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DUE_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_MOLD_MANUAL = "AAAAAAAAAA";
    private static final String UPDATED_MOLD_MANUAL = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_PO = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_PO = "BBBBBBBBBB";

    private static final String DEFAULT_VENDOR_QUOTE = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_QUOTE = "BBBBBBBBBB";

    private static final String DEFAULT_VENDOR_PO = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_PO = "BBBBBBBBBB";

    private static final String DEFAULT_CAD_FILE = "AAAAAAAAAA";
    private static final String UPDATED_CAD_FILE = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUOTED_PRICE = 1;
    private static final Integer UPDATED_QUOTED_PRICE = 2;
    private static final Integer SMALLER_QUOTED_PRICE = 1 - 1;

    private static final String DEFAULT_DELIVERY_TIME = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_TIME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_QUOTE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_QUOTE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_QUOTE_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/nt-quotes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/nt-quotes/_search";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NtQuoteRepository ntQuoteRepository;

    @Autowired
    private NtQuoteMapper ntQuoteMapper;

    @Autowired
    private NtQuoteSearchRepository ntQuoteSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNtQuoteMockMvc;

    private NtQuote ntQuote;

    private NtQuote insertedNtQuote;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NtQuote createEntity() {
        return new NtQuote()
            .srNo(DEFAULT_SR_NO)
            .uid(DEFAULT_UID)
            .quoteKey(DEFAULT_QUOTE_KEY)
            .salesPerson(DEFAULT_SALES_PERSON)
            .customerName(DEFAULT_CUSTOMER_NAME)
            .quoteNumber(DEFAULT_QUOTE_NUMBER)
            .status(DEFAULT_STATUS)
            .moldNumber(DEFAULT_MOLD_NUMBER)
            .partNumber(DEFAULT_PART_NUMBER)
            .dueDate(DEFAULT_DUE_DATE)
            .moldManual(DEFAULT_MOLD_MANUAL)
            .customerPo(DEFAULT_CUSTOMER_PO)
            .vendorQuote(DEFAULT_VENDOR_QUOTE)
            .vendorPo(DEFAULT_VENDOR_PO)
            .cadFile(DEFAULT_CAD_FILE)
            .quotedPrice(DEFAULT_QUOTED_PRICE)
            .deliveryTime(DEFAULT_DELIVERY_TIME)
            .quoteDate(DEFAULT_QUOTE_DATE)
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
    public static NtQuote createUpdatedEntity() {
        return new NtQuote()
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .quoteKey(UPDATED_QUOTE_KEY)
            .salesPerson(UPDATED_SALES_PERSON)
            .customerName(UPDATED_CUSTOMER_NAME)
            .quoteNumber(UPDATED_QUOTE_NUMBER)
            .status(UPDATED_STATUS)
            .moldNumber(UPDATED_MOLD_NUMBER)
            .partNumber(UPDATED_PART_NUMBER)
            .dueDate(UPDATED_DUE_DATE)
            .moldManual(UPDATED_MOLD_MANUAL)
            .customerPo(UPDATED_CUSTOMER_PO)
            .vendorQuote(UPDATED_VENDOR_QUOTE)
            .vendorPo(UPDATED_VENDOR_PO)
            .cadFile(UPDATED_CAD_FILE)
            .quotedPrice(UPDATED_QUOTED_PRICE)
            .deliveryTime(UPDATED_DELIVERY_TIME)
            .quoteDate(UPDATED_QUOTE_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);
    }

    @BeforeEach
    public void initTest() {
        ntQuote = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedNtQuote != null) {
            ntQuoteRepository.delete(insertedNtQuote);
            ntQuoteSearchRepository.delete(insertedNtQuote);
            insertedNtQuote = null;
        }
    }

    @Test
    @Transactional
    void createNtQuote() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteSearchRepository.findAll());
        // Create the NtQuote
        NtQuoteDTO ntQuoteDTO = ntQuoteMapper.toDto(ntQuote);
        var returnedNtQuoteDTO = om.readValue(
            restNtQuoteMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            NtQuoteDTO.class
        );

        // Validate the NtQuote in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedNtQuote = ntQuoteMapper.toEntity(returnedNtQuoteDTO);
        assertNtQuoteUpdatableFieldsEquals(returnedNtQuote, getPersistedNtQuote(returnedNtQuote));

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });

        insertedNtQuote = returnedNtQuote;
    }

    @Test
    @Transactional
    void createNtQuoteWithExistingId() throws Exception {
        // Create the NtQuote with an existing ID
        ntQuote.setId(1L);
        NtQuoteDTO ntQuoteDTO = ntQuoteMapper.toDto(ntQuote);

        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restNtQuoteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NtQuote in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkUidIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteSearchRepository.findAll());
        // set the field null
        ntQuote.setUid(null);

        // Create the NtQuote, which fails.
        NtQuoteDTO ntQuoteDTO = ntQuoteMapper.toDto(ntQuote);

        restNtQuoteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkQuoteKeyIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteSearchRepository.findAll());
        // set the field null
        ntQuote.setQuoteKey(null);

        // Create the NtQuote, which fails.
        NtQuoteDTO ntQuoteDTO = ntQuoteMapper.toDto(ntQuote);

        restNtQuoteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllNtQuotes() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList
        restNtQuoteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuote.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].quoteKey").value(hasItem(DEFAULT_QUOTE_KEY)))
            .andExpect(jsonPath("$.[*].salesPerson").value(hasItem(DEFAULT_SALES_PERSON)))
            .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME)))
            .andExpect(jsonPath("$.[*].quoteNumber").value(hasItem(DEFAULT_QUOTE_NUMBER)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].moldNumber").value(hasItem(DEFAULT_MOLD_NUMBER)))
            .andExpect(jsonPath("$.[*].partNumber").value(hasItem(DEFAULT_PART_NUMBER)))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].moldManual").value(hasItem(DEFAULT_MOLD_MANUAL)))
            .andExpect(jsonPath("$.[*].customerPo").value(hasItem(DEFAULT_CUSTOMER_PO)))
            .andExpect(jsonPath("$.[*].vendorQuote").value(hasItem(DEFAULT_VENDOR_QUOTE)))
            .andExpect(jsonPath("$.[*].vendorPo").value(hasItem(DEFAULT_VENDOR_PO)))
            .andExpect(jsonPath("$.[*].cadFile").value(hasItem(DEFAULT_CAD_FILE)))
            .andExpect(jsonPath("$.[*].quotedPrice").value(hasItem(DEFAULT_QUOTED_PRICE)))
            .andExpect(jsonPath("$.[*].deliveryTime").value(hasItem(DEFAULT_DELIVERY_TIME)))
            .andExpect(jsonPath("$.[*].quoteDate").value(hasItem(DEFAULT_QUOTE_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getNtQuote() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get the ntQuote
        restNtQuoteMockMvc
            .perform(get(ENTITY_API_URL_ID, ntQuote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ntQuote.getId().intValue()))
            .andExpect(jsonPath("$.srNo").value(DEFAULT_SR_NO))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.quoteKey").value(DEFAULT_QUOTE_KEY))
            .andExpect(jsonPath("$.salesPerson").value(DEFAULT_SALES_PERSON))
            .andExpect(jsonPath("$.customerName").value(DEFAULT_CUSTOMER_NAME))
            .andExpect(jsonPath("$.quoteNumber").value(DEFAULT_QUOTE_NUMBER))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.moldNumber").value(DEFAULT_MOLD_NUMBER))
            .andExpect(jsonPath("$.partNumber").value(DEFAULT_PART_NUMBER))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.moldManual").value(DEFAULT_MOLD_MANUAL))
            .andExpect(jsonPath("$.customerPo").value(DEFAULT_CUSTOMER_PO))
            .andExpect(jsonPath("$.vendorQuote").value(DEFAULT_VENDOR_QUOTE))
            .andExpect(jsonPath("$.vendorPo").value(DEFAULT_VENDOR_PO))
            .andExpect(jsonPath("$.cadFile").value(DEFAULT_CAD_FILE))
            .andExpect(jsonPath("$.quotedPrice").value(DEFAULT_QUOTED_PRICE))
            .andExpect(jsonPath("$.deliveryTime").value(DEFAULT_DELIVERY_TIME))
            .andExpect(jsonPath("$.quoteDate").value(DEFAULT_QUOTE_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNtQuotesByIdFiltering() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        Long id = ntQuote.getId();

        defaultNtQuoteFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultNtQuoteFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultNtQuoteFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNtQuotesBySrNoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where srNo equals to
        defaultNtQuoteFiltering("srNo.equals=" + DEFAULT_SR_NO, "srNo.equals=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuotesBySrNoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where srNo in
        defaultNtQuoteFiltering("srNo.in=" + DEFAULT_SR_NO + "," + UPDATED_SR_NO, "srNo.in=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuotesBySrNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where srNo is not null
        defaultNtQuoteFiltering("srNo.specified=true", "srNo.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotesBySrNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where srNo is greater than or equal to
        defaultNtQuoteFiltering("srNo.greaterThanOrEqual=" + DEFAULT_SR_NO, "srNo.greaterThanOrEqual=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuotesBySrNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where srNo is less than or equal to
        defaultNtQuoteFiltering("srNo.lessThanOrEqual=" + DEFAULT_SR_NO, "srNo.lessThanOrEqual=" + SMALLER_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuotesBySrNoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where srNo is less than
        defaultNtQuoteFiltering("srNo.lessThan=" + UPDATED_SR_NO, "srNo.lessThan=" + DEFAULT_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuotesBySrNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where srNo is greater than
        defaultNtQuoteFiltering("srNo.greaterThan=" + SMALLER_SR_NO, "srNo.greaterThan=" + DEFAULT_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuotesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where uid equals to
        defaultNtQuoteFiltering("uid.equals=" + DEFAULT_UID, "uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    void getAllNtQuotesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where uid in
        defaultNtQuoteFiltering("uid.in=" + DEFAULT_UID + "," + UPDATED_UID, "uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    void getAllNtQuotesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where uid is not null
        defaultNtQuoteFiltering("uid.specified=true", "uid.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotesByQuoteKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where quoteKey equals to
        defaultNtQuoteFiltering("quoteKey.equals=" + DEFAULT_QUOTE_KEY, "quoteKey.equals=" + UPDATED_QUOTE_KEY);
    }

    @Test
    @Transactional
    void getAllNtQuotesByQuoteKeyIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where quoteKey in
        defaultNtQuoteFiltering("quoteKey.in=" + DEFAULT_QUOTE_KEY + "," + UPDATED_QUOTE_KEY, "quoteKey.in=" + UPDATED_QUOTE_KEY);
    }

    @Test
    @Transactional
    void getAllNtQuotesByQuoteKeyIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where quoteKey is not null
        defaultNtQuoteFiltering("quoteKey.specified=true", "quoteKey.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotesByQuoteKeyContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where quoteKey contains
        defaultNtQuoteFiltering("quoteKey.contains=" + DEFAULT_QUOTE_KEY, "quoteKey.contains=" + UPDATED_QUOTE_KEY);
    }

    @Test
    @Transactional
    void getAllNtQuotesByQuoteKeyNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where quoteKey does not contain
        defaultNtQuoteFiltering("quoteKey.doesNotContain=" + UPDATED_QUOTE_KEY, "quoteKey.doesNotContain=" + DEFAULT_QUOTE_KEY);
    }

    @Test
    @Transactional
    void getAllNtQuotesBySalesPersonIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where salesPerson equals to
        defaultNtQuoteFiltering("salesPerson.equals=" + DEFAULT_SALES_PERSON, "salesPerson.equals=" + UPDATED_SALES_PERSON);
    }

    @Test
    @Transactional
    void getAllNtQuotesBySalesPersonIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where salesPerson in
        defaultNtQuoteFiltering(
            "salesPerson.in=" + DEFAULT_SALES_PERSON + "," + UPDATED_SALES_PERSON,
            "salesPerson.in=" + UPDATED_SALES_PERSON
        );
    }

    @Test
    @Transactional
    void getAllNtQuotesBySalesPersonIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where salesPerson is not null
        defaultNtQuoteFiltering("salesPerson.specified=true", "salesPerson.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotesBySalesPersonContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where salesPerson contains
        defaultNtQuoteFiltering("salesPerson.contains=" + DEFAULT_SALES_PERSON, "salesPerson.contains=" + UPDATED_SALES_PERSON);
    }

    @Test
    @Transactional
    void getAllNtQuotesBySalesPersonNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where salesPerson does not contain
        defaultNtQuoteFiltering("salesPerson.doesNotContain=" + UPDATED_SALES_PERSON, "salesPerson.doesNotContain=" + DEFAULT_SALES_PERSON);
    }

    @Test
    @Transactional
    void getAllNtQuotesByCustomerNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where customerName equals to
        defaultNtQuoteFiltering("customerName.equals=" + DEFAULT_CUSTOMER_NAME, "customerName.equals=" + UPDATED_CUSTOMER_NAME);
    }

    @Test
    @Transactional
    void getAllNtQuotesByCustomerNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where customerName in
        defaultNtQuoteFiltering(
            "customerName.in=" + DEFAULT_CUSTOMER_NAME + "," + UPDATED_CUSTOMER_NAME,
            "customerName.in=" + UPDATED_CUSTOMER_NAME
        );
    }

    @Test
    @Transactional
    void getAllNtQuotesByCustomerNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where customerName is not null
        defaultNtQuoteFiltering("customerName.specified=true", "customerName.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotesByCustomerNameContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where customerName contains
        defaultNtQuoteFiltering("customerName.contains=" + DEFAULT_CUSTOMER_NAME, "customerName.contains=" + UPDATED_CUSTOMER_NAME);
    }

    @Test
    @Transactional
    void getAllNtQuotesByCustomerNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where customerName does not contain
        defaultNtQuoteFiltering(
            "customerName.doesNotContain=" + UPDATED_CUSTOMER_NAME,
            "customerName.doesNotContain=" + DEFAULT_CUSTOMER_NAME
        );
    }

    @Test
    @Transactional
    void getAllNtQuotesByQuoteNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where quoteNumber equals to
        defaultNtQuoteFiltering("quoteNumber.equals=" + DEFAULT_QUOTE_NUMBER, "quoteNumber.equals=" + UPDATED_QUOTE_NUMBER);
    }

    @Test
    @Transactional
    void getAllNtQuotesByQuoteNumberIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where quoteNumber in
        defaultNtQuoteFiltering(
            "quoteNumber.in=" + DEFAULT_QUOTE_NUMBER + "," + UPDATED_QUOTE_NUMBER,
            "quoteNumber.in=" + UPDATED_QUOTE_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotesByQuoteNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where quoteNumber is not null
        defaultNtQuoteFiltering("quoteNumber.specified=true", "quoteNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotesByQuoteNumberContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where quoteNumber contains
        defaultNtQuoteFiltering("quoteNumber.contains=" + DEFAULT_QUOTE_NUMBER, "quoteNumber.contains=" + UPDATED_QUOTE_NUMBER);
    }

    @Test
    @Transactional
    void getAllNtQuotesByQuoteNumberNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where quoteNumber does not contain
        defaultNtQuoteFiltering("quoteNumber.doesNotContain=" + UPDATED_QUOTE_NUMBER, "quoteNumber.doesNotContain=" + DEFAULT_QUOTE_NUMBER);
    }

    @Test
    @Transactional
    void getAllNtQuotesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where status equals to
        defaultNtQuoteFiltering("status.equals=" + DEFAULT_STATUS, "status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllNtQuotesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where status in
        defaultNtQuoteFiltering("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS, "status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllNtQuotesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where status is not null
        defaultNtQuoteFiltering("status.specified=true", "status.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotesByStatusContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where status contains
        defaultNtQuoteFiltering("status.contains=" + DEFAULT_STATUS, "status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllNtQuotesByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where status does not contain
        defaultNtQuoteFiltering("status.doesNotContain=" + UPDATED_STATUS, "status.doesNotContain=" + DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void getAllNtQuotesByMoldNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where moldNumber equals to
        defaultNtQuoteFiltering("moldNumber.equals=" + DEFAULT_MOLD_NUMBER, "moldNumber.equals=" + UPDATED_MOLD_NUMBER);
    }

    @Test
    @Transactional
    void getAllNtQuotesByMoldNumberIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where moldNumber in
        defaultNtQuoteFiltering("moldNumber.in=" + DEFAULT_MOLD_NUMBER + "," + UPDATED_MOLD_NUMBER, "moldNumber.in=" + UPDATED_MOLD_NUMBER);
    }

    @Test
    @Transactional
    void getAllNtQuotesByMoldNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where moldNumber is not null
        defaultNtQuoteFiltering("moldNumber.specified=true", "moldNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotesByMoldNumberContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where moldNumber contains
        defaultNtQuoteFiltering("moldNumber.contains=" + DEFAULT_MOLD_NUMBER, "moldNumber.contains=" + UPDATED_MOLD_NUMBER);
    }

    @Test
    @Transactional
    void getAllNtQuotesByMoldNumberNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where moldNumber does not contain
        defaultNtQuoteFiltering("moldNumber.doesNotContain=" + UPDATED_MOLD_NUMBER, "moldNumber.doesNotContain=" + DEFAULT_MOLD_NUMBER);
    }

    @Test
    @Transactional
    void getAllNtQuotesByPartNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where partNumber equals to
        defaultNtQuoteFiltering("partNumber.equals=" + DEFAULT_PART_NUMBER, "partNumber.equals=" + UPDATED_PART_NUMBER);
    }

    @Test
    @Transactional
    void getAllNtQuotesByPartNumberIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where partNumber in
        defaultNtQuoteFiltering("partNumber.in=" + DEFAULT_PART_NUMBER + "," + UPDATED_PART_NUMBER, "partNumber.in=" + UPDATED_PART_NUMBER);
    }

    @Test
    @Transactional
    void getAllNtQuotesByPartNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where partNumber is not null
        defaultNtQuoteFiltering("partNumber.specified=true", "partNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotesByPartNumberContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where partNumber contains
        defaultNtQuoteFiltering("partNumber.contains=" + DEFAULT_PART_NUMBER, "partNumber.contains=" + UPDATED_PART_NUMBER);
    }

    @Test
    @Transactional
    void getAllNtQuotesByPartNumberNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where partNumber does not contain
        defaultNtQuoteFiltering("partNumber.doesNotContain=" + UPDATED_PART_NUMBER, "partNumber.doesNotContain=" + DEFAULT_PART_NUMBER);
    }

    @Test
    @Transactional
    void getAllNtQuotesByDueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where dueDate equals to
        defaultNtQuoteFiltering("dueDate.equals=" + DEFAULT_DUE_DATE, "dueDate.equals=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllNtQuotesByDueDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where dueDate in
        defaultNtQuoteFiltering("dueDate.in=" + DEFAULT_DUE_DATE + "," + UPDATED_DUE_DATE, "dueDate.in=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllNtQuotesByDueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where dueDate is not null
        defaultNtQuoteFiltering("dueDate.specified=true", "dueDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotesByDueDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where dueDate is greater than or equal to
        defaultNtQuoteFiltering("dueDate.greaterThanOrEqual=" + DEFAULT_DUE_DATE, "dueDate.greaterThanOrEqual=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllNtQuotesByDueDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where dueDate is less than or equal to
        defaultNtQuoteFiltering("dueDate.lessThanOrEqual=" + DEFAULT_DUE_DATE, "dueDate.lessThanOrEqual=" + SMALLER_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllNtQuotesByDueDateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where dueDate is less than
        defaultNtQuoteFiltering("dueDate.lessThan=" + UPDATED_DUE_DATE, "dueDate.lessThan=" + DEFAULT_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllNtQuotesByDueDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where dueDate is greater than
        defaultNtQuoteFiltering("dueDate.greaterThan=" + SMALLER_DUE_DATE, "dueDate.greaterThan=" + DEFAULT_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllNtQuotesByMoldManualIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where moldManual equals to
        defaultNtQuoteFiltering("moldManual.equals=" + DEFAULT_MOLD_MANUAL, "moldManual.equals=" + UPDATED_MOLD_MANUAL);
    }

    @Test
    @Transactional
    void getAllNtQuotesByMoldManualIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where moldManual in
        defaultNtQuoteFiltering("moldManual.in=" + DEFAULT_MOLD_MANUAL + "," + UPDATED_MOLD_MANUAL, "moldManual.in=" + UPDATED_MOLD_MANUAL);
    }

    @Test
    @Transactional
    void getAllNtQuotesByMoldManualIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where moldManual is not null
        defaultNtQuoteFiltering("moldManual.specified=true", "moldManual.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotesByMoldManualContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where moldManual contains
        defaultNtQuoteFiltering("moldManual.contains=" + DEFAULT_MOLD_MANUAL, "moldManual.contains=" + UPDATED_MOLD_MANUAL);
    }

    @Test
    @Transactional
    void getAllNtQuotesByMoldManualNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where moldManual does not contain
        defaultNtQuoteFiltering("moldManual.doesNotContain=" + UPDATED_MOLD_MANUAL, "moldManual.doesNotContain=" + DEFAULT_MOLD_MANUAL);
    }

    @Test
    @Transactional
    void getAllNtQuotesByCustomerPoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where customerPo equals to
        defaultNtQuoteFiltering("customerPo.equals=" + DEFAULT_CUSTOMER_PO, "customerPo.equals=" + UPDATED_CUSTOMER_PO);
    }

    @Test
    @Transactional
    void getAllNtQuotesByCustomerPoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where customerPo in
        defaultNtQuoteFiltering("customerPo.in=" + DEFAULT_CUSTOMER_PO + "," + UPDATED_CUSTOMER_PO, "customerPo.in=" + UPDATED_CUSTOMER_PO);
    }

    @Test
    @Transactional
    void getAllNtQuotesByCustomerPoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where customerPo is not null
        defaultNtQuoteFiltering("customerPo.specified=true", "customerPo.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotesByCustomerPoContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where customerPo contains
        defaultNtQuoteFiltering("customerPo.contains=" + DEFAULT_CUSTOMER_PO, "customerPo.contains=" + UPDATED_CUSTOMER_PO);
    }

    @Test
    @Transactional
    void getAllNtQuotesByCustomerPoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where customerPo does not contain
        defaultNtQuoteFiltering("customerPo.doesNotContain=" + UPDATED_CUSTOMER_PO, "customerPo.doesNotContain=" + DEFAULT_CUSTOMER_PO);
    }

    @Test
    @Transactional
    void getAllNtQuotesByVendorQuoteIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where vendorQuote equals to
        defaultNtQuoteFiltering("vendorQuote.equals=" + DEFAULT_VENDOR_QUOTE, "vendorQuote.equals=" + UPDATED_VENDOR_QUOTE);
    }

    @Test
    @Transactional
    void getAllNtQuotesByVendorQuoteIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where vendorQuote in
        defaultNtQuoteFiltering(
            "vendorQuote.in=" + DEFAULT_VENDOR_QUOTE + "," + UPDATED_VENDOR_QUOTE,
            "vendorQuote.in=" + UPDATED_VENDOR_QUOTE
        );
    }

    @Test
    @Transactional
    void getAllNtQuotesByVendorQuoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where vendorQuote is not null
        defaultNtQuoteFiltering("vendorQuote.specified=true", "vendorQuote.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotesByVendorQuoteContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where vendorQuote contains
        defaultNtQuoteFiltering("vendorQuote.contains=" + DEFAULT_VENDOR_QUOTE, "vendorQuote.contains=" + UPDATED_VENDOR_QUOTE);
    }

    @Test
    @Transactional
    void getAllNtQuotesByVendorQuoteNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where vendorQuote does not contain
        defaultNtQuoteFiltering("vendorQuote.doesNotContain=" + UPDATED_VENDOR_QUOTE, "vendorQuote.doesNotContain=" + DEFAULT_VENDOR_QUOTE);
    }

    @Test
    @Transactional
    void getAllNtQuotesByVendorPoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where vendorPo equals to
        defaultNtQuoteFiltering("vendorPo.equals=" + DEFAULT_VENDOR_PO, "vendorPo.equals=" + UPDATED_VENDOR_PO);
    }

    @Test
    @Transactional
    void getAllNtQuotesByVendorPoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where vendorPo in
        defaultNtQuoteFiltering("vendorPo.in=" + DEFAULT_VENDOR_PO + "," + UPDATED_VENDOR_PO, "vendorPo.in=" + UPDATED_VENDOR_PO);
    }

    @Test
    @Transactional
    void getAllNtQuotesByVendorPoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where vendorPo is not null
        defaultNtQuoteFiltering("vendorPo.specified=true", "vendorPo.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotesByVendorPoContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where vendorPo contains
        defaultNtQuoteFiltering("vendorPo.contains=" + DEFAULT_VENDOR_PO, "vendorPo.contains=" + UPDATED_VENDOR_PO);
    }

    @Test
    @Transactional
    void getAllNtQuotesByVendorPoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where vendorPo does not contain
        defaultNtQuoteFiltering("vendorPo.doesNotContain=" + UPDATED_VENDOR_PO, "vendorPo.doesNotContain=" + DEFAULT_VENDOR_PO);
    }

    @Test
    @Transactional
    void getAllNtQuotesByCadFileIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where cadFile equals to
        defaultNtQuoteFiltering("cadFile.equals=" + DEFAULT_CAD_FILE, "cadFile.equals=" + UPDATED_CAD_FILE);
    }

    @Test
    @Transactional
    void getAllNtQuotesByCadFileIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where cadFile in
        defaultNtQuoteFiltering("cadFile.in=" + DEFAULT_CAD_FILE + "," + UPDATED_CAD_FILE, "cadFile.in=" + UPDATED_CAD_FILE);
    }

    @Test
    @Transactional
    void getAllNtQuotesByCadFileIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where cadFile is not null
        defaultNtQuoteFiltering("cadFile.specified=true", "cadFile.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotesByCadFileContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where cadFile contains
        defaultNtQuoteFiltering("cadFile.contains=" + DEFAULT_CAD_FILE, "cadFile.contains=" + UPDATED_CAD_FILE);
    }

    @Test
    @Transactional
    void getAllNtQuotesByCadFileNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where cadFile does not contain
        defaultNtQuoteFiltering("cadFile.doesNotContain=" + UPDATED_CAD_FILE, "cadFile.doesNotContain=" + DEFAULT_CAD_FILE);
    }

    @Test
    @Transactional
    void getAllNtQuotesByQuotedPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where quotedPrice equals to
        defaultNtQuoteFiltering("quotedPrice.equals=" + DEFAULT_QUOTED_PRICE, "quotedPrice.equals=" + UPDATED_QUOTED_PRICE);
    }

    @Test
    @Transactional
    void getAllNtQuotesByQuotedPriceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where quotedPrice in
        defaultNtQuoteFiltering(
            "quotedPrice.in=" + DEFAULT_QUOTED_PRICE + "," + UPDATED_QUOTED_PRICE,
            "quotedPrice.in=" + UPDATED_QUOTED_PRICE
        );
    }

    @Test
    @Transactional
    void getAllNtQuotesByQuotedPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where quotedPrice is not null
        defaultNtQuoteFiltering("quotedPrice.specified=true", "quotedPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotesByQuotedPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where quotedPrice is greater than or equal to
        defaultNtQuoteFiltering(
            "quotedPrice.greaterThanOrEqual=" + DEFAULT_QUOTED_PRICE,
            "quotedPrice.greaterThanOrEqual=" + UPDATED_QUOTED_PRICE
        );
    }

    @Test
    @Transactional
    void getAllNtQuotesByQuotedPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where quotedPrice is less than or equal to
        defaultNtQuoteFiltering(
            "quotedPrice.lessThanOrEqual=" + DEFAULT_QUOTED_PRICE,
            "quotedPrice.lessThanOrEqual=" + SMALLER_QUOTED_PRICE
        );
    }

    @Test
    @Transactional
    void getAllNtQuotesByQuotedPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where quotedPrice is less than
        defaultNtQuoteFiltering("quotedPrice.lessThan=" + UPDATED_QUOTED_PRICE, "quotedPrice.lessThan=" + DEFAULT_QUOTED_PRICE);
    }

    @Test
    @Transactional
    void getAllNtQuotesByQuotedPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where quotedPrice is greater than
        defaultNtQuoteFiltering("quotedPrice.greaterThan=" + SMALLER_QUOTED_PRICE, "quotedPrice.greaterThan=" + DEFAULT_QUOTED_PRICE);
    }

    @Test
    @Transactional
    void getAllNtQuotesByDeliveryTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where deliveryTime equals to
        defaultNtQuoteFiltering("deliveryTime.equals=" + DEFAULT_DELIVERY_TIME, "deliveryTime.equals=" + UPDATED_DELIVERY_TIME);
    }

    @Test
    @Transactional
    void getAllNtQuotesByDeliveryTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where deliveryTime in
        defaultNtQuoteFiltering(
            "deliveryTime.in=" + DEFAULT_DELIVERY_TIME + "," + UPDATED_DELIVERY_TIME,
            "deliveryTime.in=" + UPDATED_DELIVERY_TIME
        );
    }

    @Test
    @Transactional
    void getAllNtQuotesByDeliveryTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where deliveryTime is not null
        defaultNtQuoteFiltering("deliveryTime.specified=true", "deliveryTime.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotesByDeliveryTimeContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where deliveryTime contains
        defaultNtQuoteFiltering("deliveryTime.contains=" + DEFAULT_DELIVERY_TIME, "deliveryTime.contains=" + UPDATED_DELIVERY_TIME);
    }

    @Test
    @Transactional
    void getAllNtQuotesByDeliveryTimeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where deliveryTime does not contain
        defaultNtQuoteFiltering(
            "deliveryTime.doesNotContain=" + UPDATED_DELIVERY_TIME,
            "deliveryTime.doesNotContain=" + DEFAULT_DELIVERY_TIME
        );
    }

    @Test
    @Transactional
    void getAllNtQuotesByQuoteDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where quoteDate equals to
        defaultNtQuoteFiltering("quoteDate.equals=" + DEFAULT_QUOTE_DATE, "quoteDate.equals=" + UPDATED_QUOTE_DATE);
    }

    @Test
    @Transactional
    void getAllNtQuotesByQuoteDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where quoteDate in
        defaultNtQuoteFiltering("quoteDate.in=" + DEFAULT_QUOTE_DATE + "," + UPDATED_QUOTE_DATE, "quoteDate.in=" + UPDATED_QUOTE_DATE);
    }

    @Test
    @Transactional
    void getAllNtQuotesByQuoteDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where quoteDate is not null
        defaultNtQuoteFiltering("quoteDate.specified=true", "quoteDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotesByQuoteDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where quoteDate is greater than or equal to
        defaultNtQuoteFiltering("quoteDate.greaterThanOrEqual=" + DEFAULT_QUOTE_DATE, "quoteDate.greaterThanOrEqual=" + UPDATED_QUOTE_DATE);
    }

    @Test
    @Transactional
    void getAllNtQuotesByQuoteDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where quoteDate is less than or equal to
        defaultNtQuoteFiltering("quoteDate.lessThanOrEqual=" + DEFAULT_QUOTE_DATE, "quoteDate.lessThanOrEqual=" + SMALLER_QUOTE_DATE);
    }

    @Test
    @Transactional
    void getAllNtQuotesByQuoteDateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where quoteDate is less than
        defaultNtQuoteFiltering("quoteDate.lessThan=" + UPDATED_QUOTE_DATE, "quoteDate.lessThan=" + DEFAULT_QUOTE_DATE);
    }

    @Test
    @Transactional
    void getAllNtQuotesByQuoteDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where quoteDate is greater than
        defaultNtQuoteFiltering("quoteDate.greaterThan=" + SMALLER_QUOTE_DATE, "quoteDate.greaterThan=" + DEFAULT_QUOTE_DATE);
    }

    @Test
    @Transactional
    void getAllNtQuotesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where createdBy equals to
        defaultNtQuoteFiltering("createdBy.equals=" + DEFAULT_CREATED_BY, "createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuotesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where createdBy in
        defaultNtQuoteFiltering("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY, "createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuotesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where createdBy is not null
        defaultNtQuoteFiltering("createdBy.specified=true", "createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where createdBy contains
        defaultNtQuoteFiltering("createdBy.contains=" + DEFAULT_CREATED_BY, "createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuotesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where createdBy does not contain
        defaultNtQuoteFiltering("createdBy.doesNotContain=" + UPDATED_CREATED_BY, "createdBy.doesNotContain=" + DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuotesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where createdDate equals to
        defaultNtQuoteFiltering("createdDate.equals=" + DEFAULT_CREATED_DATE, "createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllNtQuotesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where createdDate in
        defaultNtQuoteFiltering(
            "createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE,
            "createdDate.in=" + UPDATED_CREATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuotesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where createdDate is not null
        defaultNtQuoteFiltering("createdDate.specified=true", "createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotesByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where updatedBy equals to
        defaultNtQuoteFiltering("updatedBy.equals=" + DEFAULT_UPDATED_BY, "updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuotesByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where updatedBy in
        defaultNtQuoteFiltering("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY, "updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuotesByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where updatedBy is not null
        defaultNtQuoteFiltering("updatedBy.specified=true", "updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotesByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where updatedBy contains
        defaultNtQuoteFiltering("updatedBy.contains=" + DEFAULT_UPDATED_BY, "updatedBy.contains=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuotesByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where updatedBy does not contain
        defaultNtQuoteFiltering("updatedBy.doesNotContain=" + UPDATED_UPDATED_BY, "updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuotesByUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where updatedDate equals to
        defaultNtQuoteFiltering("updatedDate.equals=" + DEFAULT_UPDATED_DATE, "updatedDate.equals=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllNtQuotesByUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where updatedDate in
        defaultNtQuoteFiltering(
            "updatedDate.in=" + DEFAULT_UPDATED_DATE + "," + UPDATED_UPDATED_DATE,
            "updatedDate.in=" + UPDATED_UPDATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuotesByUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        // Get all the ntQuoteList where updatedDate is not null
        defaultNtQuoteFiltering("updatedDate.specified=true", "updatedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotesByRfqDetailIsEqualToSomething() throws Exception {
        RfqDetail rfqDetail;
        if (TestUtil.findAll(em, RfqDetail.class).isEmpty()) {
            ntQuoteRepository.saveAndFlush(ntQuote);
            rfqDetail = RfqDetailResourceIT.createEntity();
        } else {
            rfqDetail = TestUtil.findAll(em, RfqDetail.class).get(0);
        }
        em.persist(rfqDetail);
        em.flush();
        ntQuote.setRfqDetail(rfqDetail);
        ntQuoteRepository.saveAndFlush(ntQuote);
        Long rfqDetailId = rfqDetail.getId();
        // Get all the ntQuoteList where rfqDetail equals to rfqDetailId
        defaultNtQuoteShouldBeFound("rfqDetailId.equals=" + rfqDetailId);

        // Get all the ntQuoteList where rfqDetail equals to (rfqDetailId + 1)
        defaultNtQuoteShouldNotBeFound("rfqDetailId.equals=" + (rfqDetailId + 1));
    }

    @Test
    @Transactional
    void getAllNtQuotesByNtQuoteProjectApprovalIsEqualToSomething() throws Exception {
        NtQuoteProjectApproval ntQuoteProjectApproval;
        if (TestUtil.findAll(em, NtQuoteProjectApproval.class).isEmpty()) {
            ntQuoteRepository.saveAndFlush(ntQuote);
            ntQuoteProjectApproval = NtQuoteProjectApprovalResourceIT.createEntity();
        } else {
            ntQuoteProjectApproval = TestUtil.findAll(em, NtQuoteProjectApproval.class).get(0);
        }
        em.persist(ntQuoteProjectApproval);
        em.flush();
        ntQuote.setNtQuoteProjectApproval(ntQuoteProjectApproval);
        ntQuoteRepository.saveAndFlush(ntQuote);
        Long ntQuoteProjectApprovalId = ntQuoteProjectApproval.getId();
        // Get all the ntQuoteList where ntQuoteProjectApproval equals to ntQuoteProjectApprovalId
        defaultNtQuoteShouldBeFound("ntQuoteProjectApprovalId.equals=" + ntQuoteProjectApprovalId);

        // Get all the ntQuoteList where ntQuoteProjectApproval equals to (ntQuoteProjectApprovalId + 1)
        defaultNtQuoteShouldNotBeFound("ntQuoteProjectApprovalId.equals=" + (ntQuoteProjectApprovalId + 1));
    }

    private void defaultNtQuoteFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultNtQuoteShouldBeFound(shouldBeFound);
        defaultNtQuoteShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNtQuoteShouldBeFound(String filter) throws Exception {
        restNtQuoteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuote.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].quoteKey").value(hasItem(DEFAULT_QUOTE_KEY)))
            .andExpect(jsonPath("$.[*].salesPerson").value(hasItem(DEFAULT_SALES_PERSON)))
            .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME)))
            .andExpect(jsonPath("$.[*].quoteNumber").value(hasItem(DEFAULT_QUOTE_NUMBER)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].moldNumber").value(hasItem(DEFAULT_MOLD_NUMBER)))
            .andExpect(jsonPath("$.[*].partNumber").value(hasItem(DEFAULT_PART_NUMBER)))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].moldManual").value(hasItem(DEFAULT_MOLD_MANUAL)))
            .andExpect(jsonPath("$.[*].customerPo").value(hasItem(DEFAULT_CUSTOMER_PO)))
            .andExpect(jsonPath("$.[*].vendorQuote").value(hasItem(DEFAULT_VENDOR_QUOTE)))
            .andExpect(jsonPath("$.[*].vendorPo").value(hasItem(DEFAULT_VENDOR_PO)))
            .andExpect(jsonPath("$.[*].cadFile").value(hasItem(DEFAULT_CAD_FILE)))
            .andExpect(jsonPath("$.[*].quotedPrice").value(hasItem(DEFAULT_QUOTED_PRICE)))
            .andExpect(jsonPath("$.[*].deliveryTime").value(hasItem(DEFAULT_DELIVERY_TIME)))
            .andExpect(jsonPath("$.[*].quoteDate").value(hasItem(DEFAULT_QUOTE_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));

        // Check, that the count call also returns 1
        restNtQuoteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNtQuoteShouldNotBeFound(String filter) throws Exception {
        restNtQuoteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNtQuoteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNtQuote() throws Exception {
        // Get the ntQuote
        restNtQuoteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNtQuote() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        long databaseSizeBeforeUpdate = getRepositoryCount();
        ntQuoteSearchRepository.save(ntQuote);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteSearchRepository.findAll());

        // Update the ntQuote
        NtQuote updatedNtQuote = ntQuoteRepository.findById(ntQuote.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedNtQuote are not directly saved in db
        em.detach(updatedNtQuote);
        updatedNtQuote
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .quoteKey(UPDATED_QUOTE_KEY)
            .salesPerson(UPDATED_SALES_PERSON)
            .customerName(UPDATED_CUSTOMER_NAME)
            .quoteNumber(UPDATED_QUOTE_NUMBER)
            .status(UPDATED_STATUS)
            .moldNumber(UPDATED_MOLD_NUMBER)
            .partNumber(UPDATED_PART_NUMBER)
            .dueDate(UPDATED_DUE_DATE)
            .moldManual(UPDATED_MOLD_MANUAL)
            .customerPo(UPDATED_CUSTOMER_PO)
            .vendorQuote(UPDATED_VENDOR_QUOTE)
            .vendorPo(UPDATED_VENDOR_PO)
            .cadFile(UPDATED_CAD_FILE)
            .quotedPrice(UPDATED_QUOTED_PRICE)
            .deliveryTime(UPDATED_DELIVERY_TIME)
            .quoteDate(UPDATED_QUOTE_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);
        NtQuoteDTO ntQuoteDTO = ntQuoteMapper.toDto(updatedNtQuote);

        restNtQuoteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ntQuoteDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteDTO))
            )
            .andExpect(status().isOk());

        // Validate the NtQuote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedNtQuoteToMatchAllProperties(updatedNtQuote);

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<NtQuote> ntQuoteSearchList = Streamable.of(ntQuoteSearchRepository.findAll()).toList();
                NtQuote testNtQuoteSearch = ntQuoteSearchList.get(searchDatabaseSizeAfter - 1);

                assertNtQuoteAllPropertiesEquals(testNtQuoteSearch, updatedNtQuote);
            });
    }

    @Test
    @Transactional
    void putNonExistingNtQuote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteSearchRepository.findAll());
        ntQuote.setId(longCount.incrementAndGet());

        // Create the NtQuote
        NtQuoteDTO ntQuoteDTO = ntQuoteMapper.toDto(ntQuote);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNtQuoteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ntQuoteDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchNtQuote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteSearchRepository.findAll());
        ntQuote.setId(longCount.incrementAndGet());

        // Create the NtQuote
        NtQuoteDTO ntQuoteDTO = ntQuoteMapper.toDto(ntQuote);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNtQuote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteSearchRepository.findAll());
        ntQuote.setId(longCount.incrementAndGet());

        // Create the NtQuote
        NtQuoteDTO ntQuoteDTO = ntQuoteMapper.toDto(ntQuote);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NtQuote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateNtQuoteWithPatch() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ntQuote using partial update
        NtQuote partialUpdatedNtQuote = new NtQuote();
        partialUpdatedNtQuote.setId(ntQuote.getId());

        partialUpdatedNtQuote
            .quoteKey(UPDATED_QUOTE_KEY)
            .salesPerson(UPDATED_SALES_PERSON)
            .quoteNumber(UPDATED_QUOTE_NUMBER)
            .moldNumber(UPDATED_MOLD_NUMBER)
            .moldManual(UPDATED_MOLD_MANUAL)
            .customerPo(UPDATED_CUSTOMER_PO)
            .cadFile(UPDATED_CAD_FILE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY);

        restNtQuoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNtQuote.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNtQuote))
            )
            .andExpect(status().isOk());

        // Validate the NtQuote in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNtQuoteUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedNtQuote, ntQuote), getPersistedNtQuote(ntQuote));
    }

    @Test
    @Transactional
    void fullUpdateNtQuoteWithPatch() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ntQuote using partial update
        NtQuote partialUpdatedNtQuote = new NtQuote();
        partialUpdatedNtQuote.setId(ntQuote.getId());

        partialUpdatedNtQuote
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .quoteKey(UPDATED_QUOTE_KEY)
            .salesPerson(UPDATED_SALES_PERSON)
            .customerName(UPDATED_CUSTOMER_NAME)
            .quoteNumber(UPDATED_QUOTE_NUMBER)
            .status(UPDATED_STATUS)
            .moldNumber(UPDATED_MOLD_NUMBER)
            .partNumber(UPDATED_PART_NUMBER)
            .dueDate(UPDATED_DUE_DATE)
            .moldManual(UPDATED_MOLD_MANUAL)
            .customerPo(UPDATED_CUSTOMER_PO)
            .vendorQuote(UPDATED_VENDOR_QUOTE)
            .vendorPo(UPDATED_VENDOR_PO)
            .cadFile(UPDATED_CAD_FILE)
            .quotedPrice(UPDATED_QUOTED_PRICE)
            .deliveryTime(UPDATED_DELIVERY_TIME)
            .quoteDate(UPDATED_QUOTE_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);

        restNtQuoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNtQuote.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNtQuote))
            )
            .andExpect(status().isOk());

        // Validate the NtQuote in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNtQuoteUpdatableFieldsEquals(partialUpdatedNtQuote, getPersistedNtQuote(partialUpdatedNtQuote));
    }

    @Test
    @Transactional
    void patchNonExistingNtQuote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteSearchRepository.findAll());
        ntQuote.setId(longCount.incrementAndGet());

        // Create the NtQuote
        NtQuoteDTO ntQuoteDTO = ntQuoteMapper.toDto(ntQuote);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNtQuoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ntQuoteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ntQuoteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNtQuote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteSearchRepository.findAll());
        ntQuote.setId(longCount.incrementAndGet());

        // Create the NtQuote
        NtQuoteDTO ntQuoteDTO = ntQuoteMapper.toDto(ntQuote);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ntQuoteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNtQuote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteSearchRepository.findAll());
        ntQuote.setId(longCount.incrementAndGet());

        // Create the NtQuote
        NtQuoteDTO ntQuoteDTO = ntQuoteMapper.toDto(ntQuote);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ntQuoteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NtQuote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteNtQuote() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);
        ntQuoteRepository.save(ntQuote);
        ntQuoteSearchRepository.save(ntQuote);

        long databaseSizeBeforeDelete = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the ntQuote
        restNtQuoteMockMvc
            .perform(delete(ENTITY_API_URL_ID, ntQuote.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchNtQuote() throws Exception {
        // Initialize the database
        insertedNtQuote = ntQuoteRepository.saveAndFlush(ntQuote);
        ntQuoteSearchRepository.save(ntQuote);

        // Search the ntQuote
        restNtQuoteMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + ntQuote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuote.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].quoteKey").value(hasItem(DEFAULT_QUOTE_KEY)))
            .andExpect(jsonPath("$.[*].salesPerson").value(hasItem(DEFAULT_SALES_PERSON)))
            .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME)))
            .andExpect(jsonPath("$.[*].quoteNumber").value(hasItem(DEFAULT_QUOTE_NUMBER)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].moldNumber").value(hasItem(DEFAULT_MOLD_NUMBER)))
            .andExpect(jsonPath("$.[*].partNumber").value(hasItem(DEFAULT_PART_NUMBER)))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].moldManual").value(hasItem(DEFAULT_MOLD_MANUAL)))
            .andExpect(jsonPath("$.[*].customerPo").value(hasItem(DEFAULT_CUSTOMER_PO)))
            .andExpect(jsonPath("$.[*].vendorQuote").value(hasItem(DEFAULT_VENDOR_QUOTE)))
            .andExpect(jsonPath("$.[*].vendorPo").value(hasItem(DEFAULT_VENDOR_PO)))
            .andExpect(jsonPath("$.[*].cadFile").value(hasItem(DEFAULT_CAD_FILE)))
            .andExpect(jsonPath("$.[*].quotedPrice").value(hasItem(DEFAULT_QUOTED_PRICE)))
            .andExpect(jsonPath("$.[*].deliveryTime").value(hasItem(DEFAULT_DELIVERY_TIME)))
            .andExpect(jsonPath("$.[*].quoteDate").value(hasItem(DEFAULT_QUOTE_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    protected long getRepositoryCount() {
        return ntQuoteRepository.count();
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

    protected NtQuote getPersistedNtQuote(NtQuote ntQuote) {
        return ntQuoteRepository.findById(ntQuote.getId()).orElseThrow();
    }

    protected void assertPersistedNtQuoteToMatchAllProperties(NtQuote expectedNtQuote) {
        assertNtQuoteAllPropertiesEquals(expectedNtQuote, getPersistedNtQuote(expectedNtQuote));
    }

    protected void assertPersistedNtQuoteToMatchUpdatableProperties(NtQuote expectedNtQuote) {
        assertNtQuoteAllUpdatablePropertiesEquals(expectedNtQuote, getPersistedNtQuote(expectedNtQuote));
    }
}
