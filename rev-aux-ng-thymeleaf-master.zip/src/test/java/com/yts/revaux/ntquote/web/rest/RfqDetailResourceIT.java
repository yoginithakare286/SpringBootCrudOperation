package com.yts.revaux.ntquote.web.rest;

import static com.yts.revaux.ntquote.domain.RfqDetailAsserts.*;
import static com.yts.revaux.ntquote.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yts.revaux.ntquote.IntegrationTest;
import com.yts.revaux.ntquote.domain.RfqDetail;
import com.yts.revaux.ntquote.repository.RfqDetailRepository;
import com.yts.revaux.ntquote.repository.search.RfqDetailSearchRepository;
import com.yts.revaux.ntquote.service.dto.RfqDetailDTO;
import com.yts.revaux.ntquote.service.mapper.RfqDetailMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link RfqDetailResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RfqDetailResourceIT {

    private static final Integer DEFAULT_SR_NO = 1;
    private static final Integer UPDATED_SR_NO = 2;
    private static final Integer SMALLER_SR_NO = 1 - 1;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final String DEFAULT_RFQ_ID = "AAAAAAAAAA";
    private static final String UPDATED_RFQ_ID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ORDER_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ORDER_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_ORDER_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_START_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_END_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_ITEM_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_RFQ_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_RFQ_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_RFQ_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_RFQ_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_RFQ_RECEIVED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RFQ_RECEIVED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_RFQ_RECEIVED_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_QUOTE_DUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_QUOTE_DUE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_QUOTE_DUE_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_PART = "AAAAAAAAAA";
    private static final String UPDATED_PART = "BBBBBBBBBB";

    private static final String DEFAULT_BUYER = "AAAAAAAAAA";
    private static final String UPDATED_BUYER = "BBBBBBBBBB";

    private static final String DEFAULT_EXPECTED_LAUNCH = "AAAAAAAAAA";
    private static final String UPDATED_EXPECTED_LAUNCH = "BBBBBBBBBB";

    private static final String DEFAULT_REQUESTOR = "AAAAAAAAAA";
    private static final String UPDATED_REQUESTOR = "BBBBBBBBBB";

    private static final String DEFAULT_RA_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_RA_STATUS = "BBBBBBBBBB";

    private static final Integer DEFAULT_IS_DELETE = 1;
    private static final Integer UPDATED_IS_DELETE = 2;
    private static final Integer SMALLER_IS_DELETE = 1 - 1;

    private static final String DEFAULT_CUSTOMER_FEEDBACK = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_FEEDBACK = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/rfq-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/rfq-details/_search";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RfqDetailRepository rfqDetailRepository;

    @Autowired
    private RfqDetailMapper rfqDetailMapper;

    @Autowired
    private RfqDetailSearchRepository rfqDetailSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRfqDetailMockMvc;

    private RfqDetail rfqDetail;

    private RfqDetail insertedRfqDetail;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RfqDetail createEntity() {
        return new RfqDetail()
            .srNo(DEFAULT_SR_NO)
            .uid(DEFAULT_UID)
            .rfqId(DEFAULT_RFQ_ID)
            .orderDate(DEFAULT_ORDER_DATE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .itemDescription(DEFAULT_ITEM_DESCRIPTION)
            .rfqStatus(DEFAULT_RFQ_STATUS)
            .rfqType(DEFAULT_RFQ_TYPE)
            .customer(DEFAULT_CUSTOMER)
            .rfqReceivedDate(DEFAULT_RFQ_RECEIVED_DATE)
            .quoteDueDate(DEFAULT_QUOTE_DUE_DATE)
            .part(DEFAULT_PART)
            .buyer(DEFAULT_BUYER)
            .expectedLaunch(DEFAULT_EXPECTED_LAUNCH)
            .requestor(DEFAULT_REQUESTOR)
            .raStatus(DEFAULT_RA_STATUS)
            .isDelete(DEFAULT_IS_DELETE)
            .customerFeedback(DEFAULT_CUSTOMER_FEEDBACK);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RfqDetail createUpdatedEntity() {
        return new RfqDetail()
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .rfqId(UPDATED_RFQ_ID)
            .orderDate(UPDATED_ORDER_DATE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .itemDescription(UPDATED_ITEM_DESCRIPTION)
            .rfqStatus(UPDATED_RFQ_STATUS)
            .rfqType(UPDATED_RFQ_TYPE)
            .customer(UPDATED_CUSTOMER)
            .rfqReceivedDate(UPDATED_RFQ_RECEIVED_DATE)
            .quoteDueDate(UPDATED_QUOTE_DUE_DATE)
            .part(UPDATED_PART)
            .buyer(UPDATED_BUYER)
            .expectedLaunch(UPDATED_EXPECTED_LAUNCH)
            .requestor(UPDATED_REQUESTOR)
            .raStatus(UPDATED_RA_STATUS)
            .isDelete(UPDATED_IS_DELETE)
            .customerFeedback(UPDATED_CUSTOMER_FEEDBACK);
    }

    @BeforeEach
    public void initTest() {
        rfqDetail = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedRfqDetail != null) {
            rfqDetailRepository.delete(insertedRfqDetail);
            rfqDetailSearchRepository.delete(insertedRfqDetail);
            insertedRfqDetail = null;
        }
    }

    @Test
    @Transactional
    void createRfqDetail() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(rfqDetailSearchRepository.findAll());
        // Create the RfqDetail
        RfqDetailDTO rfqDetailDTO = rfqDetailMapper.toDto(rfqDetail);
        var returnedRfqDetailDTO = om.readValue(
            restRfqDetailMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rfqDetailDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RfqDetailDTO.class
        );

        // Validate the RfqDetail in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedRfqDetail = rfqDetailMapper.toEntity(returnedRfqDetailDTO);
        assertRfqDetailUpdatableFieldsEquals(returnedRfqDetail, getPersistedRfqDetail(returnedRfqDetail));

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(rfqDetailSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });

        insertedRfqDetail = returnedRfqDetail;
    }

    @Test
    @Transactional
    void createRfqDetailWithExistingId() throws Exception {
        // Create the RfqDetail with an existing ID
        rfqDetail.setId(1L);
        RfqDetailDTO rfqDetailDTO = rfqDetailMapper.toDto(rfqDetail);

        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(rfqDetailSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restRfqDetailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rfqDetailDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RfqDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(rfqDetailSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkUidIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(rfqDetailSearchRepository.findAll());
        // set the field null
        rfqDetail.setUid(null);

        // Create the RfqDetail, which fails.
        RfqDetailDTO rfqDetailDTO = rfqDetailMapper.toDto(rfqDetail);

        restRfqDetailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rfqDetailDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(rfqDetailSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllRfqDetails() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList
        restRfqDetailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rfqDetail.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].rfqId").value(hasItem(DEFAULT_RFQ_ID)))
            .andExpect(jsonPath("$.[*].orderDate").value(hasItem(DEFAULT_ORDER_DATE.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].itemDescription").value(hasItem(DEFAULT_ITEM_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].rfqStatus").value(hasItem(DEFAULT_RFQ_STATUS)))
            .andExpect(jsonPath("$.[*].rfqType").value(hasItem(DEFAULT_RFQ_TYPE)))
            .andExpect(jsonPath("$.[*].customer").value(hasItem(DEFAULT_CUSTOMER)))
            .andExpect(jsonPath("$.[*].rfqReceivedDate").value(hasItem(DEFAULT_RFQ_RECEIVED_DATE.toString())))
            .andExpect(jsonPath("$.[*].quoteDueDate").value(hasItem(DEFAULT_QUOTE_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].part").value(hasItem(DEFAULT_PART)))
            .andExpect(jsonPath("$.[*].buyer").value(hasItem(DEFAULT_BUYER)))
            .andExpect(jsonPath("$.[*].expectedLaunch").value(hasItem(DEFAULT_EXPECTED_LAUNCH)))
            .andExpect(jsonPath("$.[*].requestor").value(hasItem(DEFAULT_REQUESTOR)))
            .andExpect(jsonPath("$.[*].raStatus").value(hasItem(DEFAULT_RA_STATUS)))
            .andExpect(jsonPath("$.[*].isDelete").value(hasItem(DEFAULT_IS_DELETE)))
            .andExpect(jsonPath("$.[*].customerFeedback").value(hasItem(DEFAULT_CUSTOMER_FEEDBACK)));
    }

    @Test
    @Transactional
    void getRfqDetail() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get the rfqDetail
        restRfqDetailMockMvc
            .perform(get(ENTITY_API_URL_ID, rfqDetail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rfqDetail.getId().intValue()))
            .andExpect(jsonPath("$.srNo").value(DEFAULT_SR_NO))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.rfqId").value(DEFAULT_RFQ_ID))
            .andExpect(jsonPath("$.orderDate").value(DEFAULT_ORDER_DATE.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.itemDescription").value(DEFAULT_ITEM_DESCRIPTION))
            .andExpect(jsonPath("$.rfqStatus").value(DEFAULT_RFQ_STATUS))
            .andExpect(jsonPath("$.rfqType").value(DEFAULT_RFQ_TYPE))
            .andExpect(jsonPath("$.customer").value(DEFAULT_CUSTOMER))
            .andExpect(jsonPath("$.rfqReceivedDate").value(DEFAULT_RFQ_RECEIVED_DATE.toString()))
            .andExpect(jsonPath("$.quoteDueDate").value(DEFAULT_QUOTE_DUE_DATE.toString()))
            .andExpect(jsonPath("$.part").value(DEFAULT_PART))
            .andExpect(jsonPath("$.buyer").value(DEFAULT_BUYER))
            .andExpect(jsonPath("$.expectedLaunch").value(DEFAULT_EXPECTED_LAUNCH))
            .andExpect(jsonPath("$.requestor").value(DEFAULT_REQUESTOR))
            .andExpect(jsonPath("$.raStatus").value(DEFAULT_RA_STATUS))
            .andExpect(jsonPath("$.isDelete").value(DEFAULT_IS_DELETE))
            .andExpect(jsonPath("$.customerFeedback").value(DEFAULT_CUSTOMER_FEEDBACK));
    }

    @Test
    @Transactional
    void getRfqDetailsByIdFiltering() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        Long id = rfqDetail.getId();

        defaultRfqDetailFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultRfqDetailFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultRfqDetailFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRfqDetailsBySrNoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where srNo equals to
        defaultRfqDetailFiltering("srNo.equals=" + DEFAULT_SR_NO, "srNo.equals=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllRfqDetailsBySrNoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where srNo in
        defaultRfqDetailFiltering("srNo.in=" + DEFAULT_SR_NO + "," + UPDATED_SR_NO, "srNo.in=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllRfqDetailsBySrNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where srNo is not null
        defaultRfqDetailFiltering("srNo.specified=true", "srNo.specified=false");
    }

    @Test
    @Transactional
    void getAllRfqDetailsBySrNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where srNo is greater than or equal to
        defaultRfqDetailFiltering("srNo.greaterThanOrEqual=" + DEFAULT_SR_NO, "srNo.greaterThanOrEqual=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllRfqDetailsBySrNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where srNo is less than or equal to
        defaultRfqDetailFiltering("srNo.lessThanOrEqual=" + DEFAULT_SR_NO, "srNo.lessThanOrEqual=" + SMALLER_SR_NO);
    }

    @Test
    @Transactional
    void getAllRfqDetailsBySrNoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where srNo is less than
        defaultRfqDetailFiltering("srNo.lessThan=" + UPDATED_SR_NO, "srNo.lessThan=" + DEFAULT_SR_NO);
    }

    @Test
    @Transactional
    void getAllRfqDetailsBySrNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where srNo is greater than
        defaultRfqDetailFiltering("srNo.greaterThan=" + SMALLER_SR_NO, "srNo.greaterThan=" + DEFAULT_SR_NO);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where uid equals to
        defaultRfqDetailFiltering("uid.equals=" + DEFAULT_UID, "uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where uid in
        defaultRfqDetailFiltering("uid.in=" + DEFAULT_UID + "," + UPDATED_UID, "uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where uid is not null
        defaultRfqDetailFiltering("uid.specified=true", "uid.specified=false");
    }

    @Test
    @Transactional
    void getAllRfqDetailsByRfqIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where rfqId equals to
        defaultRfqDetailFiltering("rfqId.equals=" + DEFAULT_RFQ_ID, "rfqId.equals=" + UPDATED_RFQ_ID);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByRfqIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where rfqId in
        defaultRfqDetailFiltering("rfqId.in=" + DEFAULT_RFQ_ID + "," + UPDATED_RFQ_ID, "rfqId.in=" + UPDATED_RFQ_ID);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByRfqIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where rfqId is not null
        defaultRfqDetailFiltering("rfqId.specified=true", "rfqId.specified=false");
    }

    @Test
    @Transactional
    void getAllRfqDetailsByRfqIdContainsSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where rfqId contains
        defaultRfqDetailFiltering("rfqId.contains=" + DEFAULT_RFQ_ID, "rfqId.contains=" + UPDATED_RFQ_ID);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByRfqIdNotContainsSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where rfqId does not contain
        defaultRfqDetailFiltering("rfqId.doesNotContain=" + UPDATED_RFQ_ID, "rfqId.doesNotContain=" + DEFAULT_RFQ_ID);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByOrderDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where orderDate equals to
        defaultRfqDetailFiltering("orderDate.equals=" + DEFAULT_ORDER_DATE, "orderDate.equals=" + UPDATED_ORDER_DATE);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByOrderDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where orderDate in
        defaultRfqDetailFiltering("orderDate.in=" + DEFAULT_ORDER_DATE + "," + UPDATED_ORDER_DATE, "orderDate.in=" + UPDATED_ORDER_DATE);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByOrderDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where orderDate is not null
        defaultRfqDetailFiltering("orderDate.specified=true", "orderDate.specified=false");
    }

    @Test
    @Transactional
    void getAllRfqDetailsByOrderDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where orderDate is greater than or equal to
        defaultRfqDetailFiltering(
            "orderDate.greaterThanOrEqual=" + DEFAULT_ORDER_DATE,
            "orderDate.greaterThanOrEqual=" + UPDATED_ORDER_DATE
        );
    }

    @Test
    @Transactional
    void getAllRfqDetailsByOrderDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where orderDate is less than or equal to
        defaultRfqDetailFiltering("orderDate.lessThanOrEqual=" + DEFAULT_ORDER_DATE, "orderDate.lessThanOrEqual=" + SMALLER_ORDER_DATE);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByOrderDateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where orderDate is less than
        defaultRfqDetailFiltering("orderDate.lessThan=" + UPDATED_ORDER_DATE, "orderDate.lessThan=" + DEFAULT_ORDER_DATE);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByOrderDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where orderDate is greater than
        defaultRfqDetailFiltering("orderDate.greaterThan=" + SMALLER_ORDER_DATE, "orderDate.greaterThan=" + DEFAULT_ORDER_DATE);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where startDate equals to
        defaultRfqDetailFiltering("startDate.equals=" + DEFAULT_START_DATE, "startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where startDate in
        defaultRfqDetailFiltering("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE, "startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where startDate is not null
        defaultRfqDetailFiltering("startDate.specified=true", "startDate.specified=false");
    }

    @Test
    @Transactional
    void getAllRfqDetailsByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where startDate is greater than or equal to
        defaultRfqDetailFiltering(
            "startDate.greaterThanOrEqual=" + DEFAULT_START_DATE,
            "startDate.greaterThanOrEqual=" + UPDATED_START_DATE
        );
    }

    @Test
    @Transactional
    void getAllRfqDetailsByStartDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where startDate is less than or equal to
        defaultRfqDetailFiltering("startDate.lessThanOrEqual=" + DEFAULT_START_DATE, "startDate.lessThanOrEqual=" + SMALLER_START_DATE);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where startDate is less than
        defaultRfqDetailFiltering("startDate.lessThan=" + UPDATED_START_DATE, "startDate.lessThan=" + DEFAULT_START_DATE);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByStartDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where startDate is greater than
        defaultRfqDetailFiltering("startDate.greaterThan=" + SMALLER_START_DATE, "startDate.greaterThan=" + DEFAULT_START_DATE);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where endDate equals to
        defaultRfqDetailFiltering("endDate.equals=" + DEFAULT_END_DATE, "endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where endDate in
        defaultRfqDetailFiltering("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE, "endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where endDate is not null
        defaultRfqDetailFiltering("endDate.specified=true", "endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllRfqDetailsByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where endDate is greater than or equal to
        defaultRfqDetailFiltering("endDate.greaterThanOrEqual=" + DEFAULT_END_DATE, "endDate.greaterThanOrEqual=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByEndDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where endDate is less than or equal to
        defaultRfqDetailFiltering("endDate.lessThanOrEqual=" + DEFAULT_END_DATE, "endDate.lessThanOrEqual=" + SMALLER_END_DATE);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where endDate is less than
        defaultRfqDetailFiltering("endDate.lessThan=" + UPDATED_END_DATE, "endDate.lessThan=" + DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByEndDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where endDate is greater than
        defaultRfqDetailFiltering("endDate.greaterThan=" + SMALLER_END_DATE, "endDate.greaterThan=" + DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByItemDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where itemDescription equals to
        defaultRfqDetailFiltering(
            "itemDescription.equals=" + DEFAULT_ITEM_DESCRIPTION,
            "itemDescription.equals=" + UPDATED_ITEM_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllRfqDetailsByItemDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where itemDescription in
        defaultRfqDetailFiltering(
            "itemDescription.in=" + DEFAULT_ITEM_DESCRIPTION + "," + UPDATED_ITEM_DESCRIPTION,
            "itemDescription.in=" + UPDATED_ITEM_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllRfqDetailsByItemDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where itemDescription is not null
        defaultRfqDetailFiltering("itemDescription.specified=true", "itemDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllRfqDetailsByItemDescriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where itemDescription contains
        defaultRfqDetailFiltering(
            "itemDescription.contains=" + DEFAULT_ITEM_DESCRIPTION,
            "itemDescription.contains=" + UPDATED_ITEM_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllRfqDetailsByItemDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where itemDescription does not contain
        defaultRfqDetailFiltering(
            "itemDescription.doesNotContain=" + UPDATED_ITEM_DESCRIPTION,
            "itemDescription.doesNotContain=" + DEFAULT_ITEM_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllRfqDetailsByRfqStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where rfqStatus equals to
        defaultRfqDetailFiltering("rfqStatus.equals=" + DEFAULT_RFQ_STATUS, "rfqStatus.equals=" + UPDATED_RFQ_STATUS);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByRfqStatusIsInShouldWork() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where rfqStatus in
        defaultRfqDetailFiltering("rfqStatus.in=" + DEFAULT_RFQ_STATUS + "," + UPDATED_RFQ_STATUS, "rfqStatus.in=" + UPDATED_RFQ_STATUS);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByRfqStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where rfqStatus is not null
        defaultRfqDetailFiltering("rfqStatus.specified=true", "rfqStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllRfqDetailsByRfqStatusContainsSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where rfqStatus contains
        defaultRfqDetailFiltering("rfqStatus.contains=" + DEFAULT_RFQ_STATUS, "rfqStatus.contains=" + UPDATED_RFQ_STATUS);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByRfqStatusNotContainsSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where rfqStatus does not contain
        defaultRfqDetailFiltering("rfqStatus.doesNotContain=" + UPDATED_RFQ_STATUS, "rfqStatus.doesNotContain=" + DEFAULT_RFQ_STATUS);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByRfqTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where rfqType equals to
        defaultRfqDetailFiltering("rfqType.equals=" + DEFAULT_RFQ_TYPE, "rfqType.equals=" + UPDATED_RFQ_TYPE);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByRfqTypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where rfqType in
        defaultRfqDetailFiltering("rfqType.in=" + DEFAULT_RFQ_TYPE + "," + UPDATED_RFQ_TYPE, "rfqType.in=" + UPDATED_RFQ_TYPE);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByRfqTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where rfqType is not null
        defaultRfqDetailFiltering("rfqType.specified=true", "rfqType.specified=false");
    }

    @Test
    @Transactional
    void getAllRfqDetailsByRfqTypeContainsSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where rfqType contains
        defaultRfqDetailFiltering("rfqType.contains=" + DEFAULT_RFQ_TYPE, "rfqType.contains=" + UPDATED_RFQ_TYPE);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByRfqTypeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where rfqType does not contain
        defaultRfqDetailFiltering("rfqType.doesNotContain=" + UPDATED_RFQ_TYPE, "rfqType.doesNotContain=" + DEFAULT_RFQ_TYPE);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where customer equals to
        defaultRfqDetailFiltering("customer.equals=" + DEFAULT_CUSTOMER, "customer.equals=" + UPDATED_CUSTOMER);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByCustomerIsInShouldWork() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where customer in
        defaultRfqDetailFiltering("customer.in=" + DEFAULT_CUSTOMER + "," + UPDATED_CUSTOMER, "customer.in=" + UPDATED_CUSTOMER);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByCustomerIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where customer is not null
        defaultRfqDetailFiltering("customer.specified=true", "customer.specified=false");
    }

    @Test
    @Transactional
    void getAllRfqDetailsByCustomerContainsSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where customer contains
        defaultRfqDetailFiltering("customer.contains=" + DEFAULT_CUSTOMER, "customer.contains=" + UPDATED_CUSTOMER);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByCustomerNotContainsSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where customer does not contain
        defaultRfqDetailFiltering("customer.doesNotContain=" + UPDATED_CUSTOMER, "customer.doesNotContain=" + DEFAULT_CUSTOMER);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByRfqReceivedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where rfqReceivedDate equals to
        defaultRfqDetailFiltering(
            "rfqReceivedDate.equals=" + DEFAULT_RFQ_RECEIVED_DATE,
            "rfqReceivedDate.equals=" + UPDATED_RFQ_RECEIVED_DATE
        );
    }

    @Test
    @Transactional
    void getAllRfqDetailsByRfqReceivedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where rfqReceivedDate in
        defaultRfqDetailFiltering(
            "rfqReceivedDate.in=" + DEFAULT_RFQ_RECEIVED_DATE + "," + UPDATED_RFQ_RECEIVED_DATE,
            "rfqReceivedDate.in=" + UPDATED_RFQ_RECEIVED_DATE
        );
    }

    @Test
    @Transactional
    void getAllRfqDetailsByRfqReceivedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where rfqReceivedDate is not null
        defaultRfqDetailFiltering("rfqReceivedDate.specified=true", "rfqReceivedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllRfqDetailsByRfqReceivedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where rfqReceivedDate is greater than or equal to
        defaultRfqDetailFiltering(
            "rfqReceivedDate.greaterThanOrEqual=" + DEFAULT_RFQ_RECEIVED_DATE,
            "rfqReceivedDate.greaterThanOrEqual=" + UPDATED_RFQ_RECEIVED_DATE
        );
    }

    @Test
    @Transactional
    void getAllRfqDetailsByRfqReceivedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where rfqReceivedDate is less than or equal to
        defaultRfqDetailFiltering(
            "rfqReceivedDate.lessThanOrEqual=" + DEFAULT_RFQ_RECEIVED_DATE,
            "rfqReceivedDate.lessThanOrEqual=" + SMALLER_RFQ_RECEIVED_DATE
        );
    }

    @Test
    @Transactional
    void getAllRfqDetailsByRfqReceivedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where rfqReceivedDate is less than
        defaultRfqDetailFiltering(
            "rfqReceivedDate.lessThan=" + UPDATED_RFQ_RECEIVED_DATE,
            "rfqReceivedDate.lessThan=" + DEFAULT_RFQ_RECEIVED_DATE
        );
    }

    @Test
    @Transactional
    void getAllRfqDetailsByRfqReceivedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where rfqReceivedDate is greater than
        defaultRfqDetailFiltering(
            "rfqReceivedDate.greaterThan=" + SMALLER_RFQ_RECEIVED_DATE,
            "rfqReceivedDate.greaterThan=" + DEFAULT_RFQ_RECEIVED_DATE
        );
    }

    @Test
    @Transactional
    void getAllRfqDetailsByQuoteDueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where quoteDueDate equals to
        defaultRfqDetailFiltering("quoteDueDate.equals=" + DEFAULT_QUOTE_DUE_DATE, "quoteDueDate.equals=" + UPDATED_QUOTE_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByQuoteDueDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where quoteDueDate in
        defaultRfqDetailFiltering(
            "quoteDueDate.in=" + DEFAULT_QUOTE_DUE_DATE + "," + UPDATED_QUOTE_DUE_DATE,
            "quoteDueDate.in=" + UPDATED_QUOTE_DUE_DATE
        );
    }

    @Test
    @Transactional
    void getAllRfqDetailsByQuoteDueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where quoteDueDate is not null
        defaultRfqDetailFiltering("quoteDueDate.specified=true", "quoteDueDate.specified=false");
    }

    @Test
    @Transactional
    void getAllRfqDetailsByQuoteDueDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where quoteDueDate is greater than or equal to
        defaultRfqDetailFiltering(
            "quoteDueDate.greaterThanOrEqual=" + DEFAULT_QUOTE_DUE_DATE,
            "quoteDueDate.greaterThanOrEqual=" + UPDATED_QUOTE_DUE_DATE
        );
    }

    @Test
    @Transactional
    void getAllRfqDetailsByQuoteDueDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where quoteDueDate is less than or equal to
        defaultRfqDetailFiltering(
            "quoteDueDate.lessThanOrEqual=" + DEFAULT_QUOTE_DUE_DATE,
            "quoteDueDate.lessThanOrEqual=" + SMALLER_QUOTE_DUE_DATE
        );
    }

    @Test
    @Transactional
    void getAllRfqDetailsByQuoteDueDateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where quoteDueDate is less than
        defaultRfqDetailFiltering("quoteDueDate.lessThan=" + UPDATED_QUOTE_DUE_DATE, "quoteDueDate.lessThan=" + DEFAULT_QUOTE_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByQuoteDueDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where quoteDueDate is greater than
        defaultRfqDetailFiltering(
            "quoteDueDate.greaterThan=" + SMALLER_QUOTE_DUE_DATE,
            "quoteDueDate.greaterThan=" + DEFAULT_QUOTE_DUE_DATE
        );
    }

    @Test
    @Transactional
    void getAllRfqDetailsByPartIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where part equals to
        defaultRfqDetailFiltering("part.equals=" + DEFAULT_PART, "part.equals=" + UPDATED_PART);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByPartIsInShouldWork() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where part in
        defaultRfqDetailFiltering("part.in=" + DEFAULT_PART + "," + UPDATED_PART, "part.in=" + UPDATED_PART);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByPartIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where part is not null
        defaultRfqDetailFiltering("part.specified=true", "part.specified=false");
    }

    @Test
    @Transactional
    void getAllRfqDetailsByPartContainsSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where part contains
        defaultRfqDetailFiltering("part.contains=" + DEFAULT_PART, "part.contains=" + UPDATED_PART);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByPartNotContainsSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where part does not contain
        defaultRfqDetailFiltering("part.doesNotContain=" + UPDATED_PART, "part.doesNotContain=" + DEFAULT_PART);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByBuyerIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where buyer equals to
        defaultRfqDetailFiltering("buyer.equals=" + DEFAULT_BUYER, "buyer.equals=" + UPDATED_BUYER);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByBuyerIsInShouldWork() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where buyer in
        defaultRfqDetailFiltering("buyer.in=" + DEFAULT_BUYER + "," + UPDATED_BUYER, "buyer.in=" + UPDATED_BUYER);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByBuyerIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where buyer is not null
        defaultRfqDetailFiltering("buyer.specified=true", "buyer.specified=false");
    }

    @Test
    @Transactional
    void getAllRfqDetailsByBuyerContainsSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where buyer contains
        defaultRfqDetailFiltering("buyer.contains=" + DEFAULT_BUYER, "buyer.contains=" + UPDATED_BUYER);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByBuyerNotContainsSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where buyer does not contain
        defaultRfqDetailFiltering("buyer.doesNotContain=" + UPDATED_BUYER, "buyer.doesNotContain=" + DEFAULT_BUYER);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByExpectedLaunchIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where expectedLaunch equals to
        defaultRfqDetailFiltering("expectedLaunch.equals=" + DEFAULT_EXPECTED_LAUNCH, "expectedLaunch.equals=" + UPDATED_EXPECTED_LAUNCH);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByExpectedLaunchIsInShouldWork() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where expectedLaunch in
        defaultRfqDetailFiltering(
            "expectedLaunch.in=" + DEFAULT_EXPECTED_LAUNCH + "," + UPDATED_EXPECTED_LAUNCH,
            "expectedLaunch.in=" + UPDATED_EXPECTED_LAUNCH
        );
    }

    @Test
    @Transactional
    void getAllRfqDetailsByExpectedLaunchIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where expectedLaunch is not null
        defaultRfqDetailFiltering("expectedLaunch.specified=true", "expectedLaunch.specified=false");
    }

    @Test
    @Transactional
    void getAllRfqDetailsByExpectedLaunchContainsSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where expectedLaunch contains
        defaultRfqDetailFiltering(
            "expectedLaunch.contains=" + DEFAULT_EXPECTED_LAUNCH,
            "expectedLaunch.contains=" + UPDATED_EXPECTED_LAUNCH
        );
    }

    @Test
    @Transactional
    void getAllRfqDetailsByExpectedLaunchNotContainsSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where expectedLaunch does not contain
        defaultRfqDetailFiltering(
            "expectedLaunch.doesNotContain=" + UPDATED_EXPECTED_LAUNCH,
            "expectedLaunch.doesNotContain=" + DEFAULT_EXPECTED_LAUNCH
        );
    }

    @Test
    @Transactional
    void getAllRfqDetailsByRequestorIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where requestor equals to
        defaultRfqDetailFiltering("requestor.equals=" + DEFAULT_REQUESTOR, "requestor.equals=" + UPDATED_REQUESTOR);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByRequestorIsInShouldWork() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where requestor in
        defaultRfqDetailFiltering("requestor.in=" + DEFAULT_REQUESTOR + "," + UPDATED_REQUESTOR, "requestor.in=" + UPDATED_REQUESTOR);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByRequestorIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where requestor is not null
        defaultRfqDetailFiltering("requestor.specified=true", "requestor.specified=false");
    }

    @Test
    @Transactional
    void getAllRfqDetailsByRequestorContainsSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where requestor contains
        defaultRfqDetailFiltering("requestor.contains=" + DEFAULT_REQUESTOR, "requestor.contains=" + UPDATED_REQUESTOR);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByRequestorNotContainsSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where requestor does not contain
        defaultRfqDetailFiltering("requestor.doesNotContain=" + UPDATED_REQUESTOR, "requestor.doesNotContain=" + DEFAULT_REQUESTOR);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByRaStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where raStatus equals to
        defaultRfqDetailFiltering("raStatus.equals=" + DEFAULT_RA_STATUS, "raStatus.equals=" + UPDATED_RA_STATUS);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByRaStatusIsInShouldWork() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where raStatus in
        defaultRfqDetailFiltering("raStatus.in=" + DEFAULT_RA_STATUS + "," + UPDATED_RA_STATUS, "raStatus.in=" + UPDATED_RA_STATUS);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByRaStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where raStatus is not null
        defaultRfqDetailFiltering("raStatus.specified=true", "raStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllRfqDetailsByRaStatusContainsSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where raStatus contains
        defaultRfqDetailFiltering("raStatus.contains=" + DEFAULT_RA_STATUS, "raStatus.contains=" + UPDATED_RA_STATUS);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByRaStatusNotContainsSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where raStatus does not contain
        defaultRfqDetailFiltering("raStatus.doesNotContain=" + UPDATED_RA_STATUS, "raStatus.doesNotContain=" + DEFAULT_RA_STATUS);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByIsDeleteIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where isDelete equals to
        defaultRfqDetailFiltering("isDelete.equals=" + DEFAULT_IS_DELETE, "isDelete.equals=" + UPDATED_IS_DELETE);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByIsDeleteIsInShouldWork() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where isDelete in
        defaultRfqDetailFiltering("isDelete.in=" + DEFAULT_IS_DELETE + "," + UPDATED_IS_DELETE, "isDelete.in=" + UPDATED_IS_DELETE);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByIsDeleteIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where isDelete is not null
        defaultRfqDetailFiltering("isDelete.specified=true", "isDelete.specified=false");
    }

    @Test
    @Transactional
    void getAllRfqDetailsByIsDeleteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where isDelete is greater than or equal to
        defaultRfqDetailFiltering("isDelete.greaterThanOrEqual=" + DEFAULT_IS_DELETE, "isDelete.greaterThanOrEqual=" + UPDATED_IS_DELETE);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByIsDeleteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where isDelete is less than or equal to
        defaultRfqDetailFiltering("isDelete.lessThanOrEqual=" + DEFAULT_IS_DELETE, "isDelete.lessThanOrEqual=" + SMALLER_IS_DELETE);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByIsDeleteIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where isDelete is less than
        defaultRfqDetailFiltering("isDelete.lessThan=" + UPDATED_IS_DELETE, "isDelete.lessThan=" + DEFAULT_IS_DELETE);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByIsDeleteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where isDelete is greater than
        defaultRfqDetailFiltering("isDelete.greaterThan=" + SMALLER_IS_DELETE, "isDelete.greaterThan=" + DEFAULT_IS_DELETE);
    }

    @Test
    @Transactional
    void getAllRfqDetailsByCustomerFeedbackIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where customerFeedback equals to
        defaultRfqDetailFiltering(
            "customerFeedback.equals=" + DEFAULT_CUSTOMER_FEEDBACK,
            "customerFeedback.equals=" + UPDATED_CUSTOMER_FEEDBACK
        );
    }

    @Test
    @Transactional
    void getAllRfqDetailsByCustomerFeedbackIsInShouldWork() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where customerFeedback in
        defaultRfqDetailFiltering(
            "customerFeedback.in=" + DEFAULT_CUSTOMER_FEEDBACK + "," + UPDATED_CUSTOMER_FEEDBACK,
            "customerFeedback.in=" + UPDATED_CUSTOMER_FEEDBACK
        );
    }

    @Test
    @Transactional
    void getAllRfqDetailsByCustomerFeedbackIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where customerFeedback is not null
        defaultRfqDetailFiltering("customerFeedback.specified=true", "customerFeedback.specified=false");
    }

    @Test
    @Transactional
    void getAllRfqDetailsByCustomerFeedbackContainsSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where customerFeedback contains
        defaultRfqDetailFiltering(
            "customerFeedback.contains=" + DEFAULT_CUSTOMER_FEEDBACK,
            "customerFeedback.contains=" + UPDATED_CUSTOMER_FEEDBACK
        );
    }

    @Test
    @Transactional
    void getAllRfqDetailsByCustomerFeedbackNotContainsSomething() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        // Get all the rfqDetailList where customerFeedback does not contain
        defaultRfqDetailFiltering(
            "customerFeedback.doesNotContain=" + UPDATED_CUSTOMER_FEEDBACK,
            "customerFeedback.doesNotContain=" + DEFAULT_CUSTOMER_FEEDBACK
        );
    }

    private void defaultRfqDetailFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultRfqDetailShouldBeFound(shouldBeFound);
        defaultRfqDetailShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRfqDetailShouldBeFound(String filter) throws Exception {
        restRfqDetailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rfqDetail.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].rfqId").value(hasItem(DEFAULT_RFQ_ID)))
            .andExpect(jsonPath("$.[*].orderDate").value(hasItem(DEFAULT_ORDER_DATE.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].itemDescription").value(hasItem(DEFAULT_ITEM_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].rfqStatus").value(hasItem(DEFAULT_RFQ_STATUS)))
            .andExpect(jsonPath("$.[*].rfqType").value(hasItem(DEFAULT_RFQ_TYPE)))
            .andExpect(jsonPath("$.[*].customer").value(hasItem(DEFAULT_CUSTOMER)))
            .andExpect(jsonPath("$.[*].rfqReceivedDate").value(hasItem(DEFAULT_RFQ_RECEIVED_DATE.toString())))
            .andExpect(jsonPath("$.[*].quoteDueDate").value(hasItem(DEFAULT_QUOTE_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].part").value(hasItem(DEFAULT_PART)))
            .andExpect(jsonPath("$.[*].buyer").value(hasItem(DEFAULT_BUYER)))
            .andExpect(jsonPath("$.[*].expectedLaunch").value(hasItem(DEFAULT_EXPECTED_LAUNCH)))
            .andExpect(jsonPath("$.[*].requestor").value(hasItem(DEFAULT_REQUESTOR)))
            .andExpect(jsonPath("$.[*].raStatus").value(hasItem(DEFAULT_RA_STATUS)))
            .andExpect(jsonPath("$.[*].isDelete").value(hasItem(DEFAULT_IS_DELETE)))
            .andExpect(jsonPath("$.[*].customerFeedback").value(hasItem(DEFAULT_CUSTOMER_FEEDBACK)));

        // Check, that the count call also returns 1
        restRfqDetailMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRfqDetailShouldNotBeFound(String filter) throws Exception {
        restRfqDetailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRfqDetailMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRfqDetail() throws Exception {
        // Get the rfqDetail
        restRfqDetailMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRfqDetail() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        long databaseSizeBeforeUpdate = getRepositoryCount();
        rfqDetailSearchRepository.save(rfqDetail);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(rfqDetailSearchRepository.findAll());

        // Update the rfqDetail
        RfqDetail updatedRfqDetail = rfqDetailRepository.findById(rfqDetail.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRfqDetail are not directly saved in db
        em.detach(updatedRfqDetail);
        updatedRfqDetail
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .rfqId(UPDATED_RFQ_ID)
            .orderDate(UPDATED_ORDER_DATE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .itemDescription(UPDATED_ITEM_DESCRIPTION)
            .rfqStatus(UPDATED_RFQ_STATUS)
            .rfqType(UPDATED_RFQ_TYPE)
            .customer(UPDATED_CUSTOMER)
            .rfqReceivedDate(UPDATED_RFQ_RECEIVED_DATE)
            .quoteDueDate(UPDATED_QUOTE_DUE_DATE)
            .part(UPDATED_PART)
            .buyer(UPDATED_BUYER)
            .expectedLaunch(UPDATED_EXPECTED_LAUNCH)
            .requestor(UPDATED_REQUESTOR)
            .raStatus(UPDATED_RA_STATUS)
            .isDelete(UPDATED_IS_DELETE)
            .customerFeedback(UPDATED_CUSTOMER_FEEDBACK);
        RfqDetailDTO rfqDetailDTO = rfqDetailMapper.toDto(updatedRfqDetail);

        restRfqDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rfqDetailDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rfqDetailDTO))
            )
            .andExpect(status().isOk());

        // Validate the RfqDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRfqDetailToMatchAllProperties(updatedRfqDetail);

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(rfqDetailSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<RfqDetail> rfqDetailSearchList = Streamable.of(rfqDetailSearchRepository.findAll()).toList();
                RfqDetail testRfqDetailSearch = rfqDetailSearchList.get(searchDatabaseSizeAfter - 1);

                assertRfqDetailAllPropertiesEquals(testRfqDetailSearch, updatedRfqDetail);
            });
    }

    @Test
    @Transactional
    void putNonExistingRfqDetail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(rfqDetailSearchRepository.findAll());
        rfqDetail.setId(longCount.incrementAndGet());

        // Create the RfqDetail
        RfqDetailDTO rfqDetailDTO = rfqDetailMapper.toDto(rfqDetail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRfqDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rfqDetailDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rfqDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RfqDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(rfqDetailSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchRfqDetail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(rfqDetailSearchRepository.findAll());
        rfqDetail.setId(longCount.incrementAndGet());

        // Create the RfqDetail
        RfqDetailDTO rfqDetailDTO = rfqDetailMapper.toDto(rfqDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRfqDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rfqDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RfqDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(rfqDetailSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRfqDetail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(rfqDetailSearchRepository.findAll());
        rfqDetail.setId(longCount.incrementAndGet());

        // Create the RfqDetail
        RfqDetailDTO rfqDetailDTO = rfqDetailMapper.toDto(rfqDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRfqDetailMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rfqDetailDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RfqDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(rfqDetailSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateRfqDetailWithPatch() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rfqDetail using partial update
        RfqDetail partialUpdatedRfqDetail = new RfqDetail();
        partialUpdatedRfqDetail.setId(rfqDetail.getId());

        partialUpdatedRfqDetail
            .uid(UPDATED_UID)
            .rfqId(UPDATED_RFQ_ID)
            .itemDescription(UPDATED_ITEM_DESCRIPTION)
            .rfqStatus(UPDATED_RFQ_STATUS)
            .part(UPDATED_PART)
            .buyer(UPDATED_BUYER)
            .isDelete(UPDATED_IS_DELETE);

        restRfqDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRfqDetail.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRfqDetail))
            )
            .andExpect(status().isOk());

        // Validate the RfqDetail in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRfqDetailUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRfqDetail, rfqDetail),
            getPersistedRfqDetail(rfqDetail)
        );
    }

    @Test
    @Transactional
    void fullUpdateRfqDetailWithPatch() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rfqDetail using partial update
        RfqDetail partialUpdatedRfqDetail = new RfqDetail();
        partialUpdatedRfqDetail.setId(rfqDetail.getId());

        partialUpdatedRfqDetail
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .rfqId(UPDATED_RFQ_ID)
            .orderDate(UPDATED_ORDER_DATE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .itemDescription(UPDATED_ITEM_DESCRIPTION)
            .rfqStatus(UPDATED_RFQ_STATUS)
            .rfqType(UPDATED_RFQ_TYPE)
            .customer(UPDATED_CUSTOMER)
            .rfqReceivedDate(UPDATED_RFQ_RECEIVED_DATE)
            .quoteDueDate(UPDATED_QUOTE_DUE_DATE)
            .part(UPDATED_PART)
            .buyer(UPDATED_BUYER)
            .expectedLaunch(UPDATED_EXPECTED_LAUNCH)
            .requestor(UPDATED_REQUESTOR)
            .raStatus(UPDATED_RA_STATUS)
            .isDelete(UPDATED_IS_DELETE)
            .customerFeedback(UPDATED_CUSTOMER_FEEDBACK);

        restRfqDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRfqDetail.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRfqDetail))
            )
            .andExpect(status().isOk());

        // Validate the RfqDetail in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRfqDetailUpdatableFieldsEquals(partialUpdatedRfqDetail, getPersistedRfqDetail(partialUpdatedRfqDetail));
    }

    @Test
    @Transactional
    void patchNonExistingRfqDetail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(rfqDetailSearchRepository.findAll());
        rfqDetail.setId(longCount.incrementAndGet());

        // Create the RfqDetail
        RfqDetailDTO rfqDetailDTO = rfqDetailMapper.toDto(rfqDetail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRfqDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rfqDetailDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rfqDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RfqDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(rfqDetailSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRfqDetail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(rfqDetailSearchRepository.findAll());
        rfqDetail.setId(longCount.incrementAndGet());

        // Create the RfqDetail
        RfqDetailDTO rfqDetailDTO = rfqDetailMapper.toDto(rfqDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRfqDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rfqDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RfqDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(rfqDetailSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRfqDetail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(rfqDetailSearchRepository.findAll());
        rfqDetail.setId(longCount.incrementAndGet());

        // Create the RfqDetail
        RfqDetailDTO rfqDetailDTO = rfqDetailMapper.toDto(rfqDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRfqDetailMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(rfqDetailDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RfqDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(rfqDetailSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteRfqDetail() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);
        rfqDetailRepository.save(rfqDetail);
        rfqDetailSearchRepository.save(rfqDetail);

        long databaseSizeBeforeDelete = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(rfqDetailSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the rfqDetail
        restRfqDetailMockMvc
            .perform(delete(ENTITY_API_URL_ID, rfqDetail.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(rfqDetailSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchRfqDetail() throws Exception {
        // Initialize the database
        insertedRfqDetail = rfqDetailRepository.saveAndFlush(rfqDetail);
        rfqDetailSearchRepository.save(rfqDetail);

        // Search the rfqDetail
        restRfqDetailMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + rfqDetail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rfqDetail.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].rfqId").value(hasItem(DEFAULT_RFQ_ID)))
            .andExpect(jsonPath("$.[*].orderDate").value(hasItem(DEFAULT_ORDER_DATE.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].itemDescription").value(hasItem(DEFAULT_ITEM_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].rfqStatus").value(hasItem(DEFAULT_RFQ_STATUS)))
            .andExpect(jsonPath("$.[*].rfqType").value(hasItem(DEFAULT_RFQ_TYPE)))
            .andExpect(jsonPath("$.[*].customer").value(hasItem(DEFAULT_CUSTOMER)))
            .andExpect(jsonPath("$.[*].rfqReceivedDate").value(hasItem(DEFAULT_RFQ_RECEIVED_DATE.toString())))
            .andExpect(jsonPath("$.[*].quoteDueDate").value(hasItem(DEFAULT_QUOTE_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].part").value(hasItem(DEFAULT_PART)))
            .andExpect(jsonPath("$.[*].buyer").value(hasItem(DEFAULT_BUYER)))
            .andExpect(jsonPath("$.[*].expectedLaunch").value(hasItem(DEFAULT_EXPECTED_LAUNCH)))
            .andExpect(jsonPath("$.[*].requestor").value(hasItem(DEFAULT_REQUESTOR)))
            .andExpect(jsonPath("$.[*].raStatus").value(hasItem(DEFAULT_RA_STATUS)))
            .andExpect(jsonPath("$.[*].isDelete").value(hasItem(DEFAULT_IS_DELETE)))
            .andExpect(jsonPath("$.[*].customerFeedback").value(hasItem(DEFAULT_CUSTOMER_FEEDBACK)));
    }

    protected long getRepositoryCount() {
        return rfqDetailRepository.count();
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

    protected RfqDetail getPersistedRfqDetail(RfqDetail rfqDetail) {
        return rfqDetailRepository.findById(rfqDetail.getId()).orElseThrow();
    }

    protected void assertPersistedRfqDetailToMatchAllProperties(RfqDetail expectedRfqDetail) {
        assertRfqDetailAllPropertiesEquals(expectedRfqDetail, getPersistedRfqDetail(expectedRfqDetail));
    }

    protected void assertPersistedRfqDetailToMatchUpdatableProperties(RfqDetail expectedRfqDetail) {
        assertRfqDetailAllUpdatablePropertiesEquals(expectedRfqDetail, getPersistedRfqDetail(expectedRfqDetail));
    }
}
