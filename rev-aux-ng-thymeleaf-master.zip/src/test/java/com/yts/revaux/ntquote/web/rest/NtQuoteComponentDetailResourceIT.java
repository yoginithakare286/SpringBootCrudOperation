package com.yts.revaux.ntquote.web.rest;

import static com.yts.revaux.ntquote.domain.NtQuoteComponentDetailAsserts.*;
import static com.yts.revaux.ntquote.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yts.revaux.ntquote.IntegrationTest;
import com.yts.revaux.ntquote.domain.BuyerRfqPricesDetail;
import com.yts.revaux.ntquote.domain.NtQuote;
import com.yts.revaux.ntquote.domain.NtQuoteComponentDetail;
import com.yts.revaux.ntquote.repository.NtQuoteComponentDetailRepository;
import com.yts.revaux.ntquote.repository.search.NtQuoteComponentDetailSearchRepository;
import com.yts.revaux.ntquote.service.dto.NtQuoteComponentDetailDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuoteComponentDetailMapper;
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
 * Integration tests for the {@link NtQuoteComponentDetailResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NtQuoteComponentDetailResourceIT {

    private static final Integer DEFAULT_SR_NO = 1;
    private static final Integer UPDATED_SR_NO = 2;
    private static final Integer SMALLER_SR_NO = 1 - 1;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final String DEFAULT_MATERIAL_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PART_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PART_NUMBER = "BBBBBBBBBB";

    private static final Integer DEFAULT_EAU = 1;
    private static final Integer UPDATED_EAU = 2;
    private static final Integer SMALLER_EAU = 1 - 1;

    private static final String DEFAULT_MANUFACTURING_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_MANUFACTURING_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_FOB_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_FOB_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_PACKING_REQUIREMENTS = "AAAAAAAAAA";
    private static final String UPDATED_PACKING_REQUIREMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_MACHINE_SIZE = "AAAAAAAAAA";
    private static final String UPDATED_MACHINE_SIZE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CYCLE_TIME = 1;
    private static final Integer UPDATED_CYCLE_TIME = 2;
    private static final Integer SMALLER_CYCLE_TIME = 1 - 1;

    private static final Integer DEFAULT_PART_WEIGHT = 1;
    private static final Integer UPDATED_PART_WEIGHT = 2;
    private static final Integer SMALLER_PART_WEIGHT = 1 - 1;

    private static final Integer DEFAULT_RUNNER_WEIGHT = 1;
    private static final Integer UPDATED_RUNNER_WEIGHT = 2;
    private static final Integer SMALLER_RUNNER_WEIGHT = 1 - 1;

    private static final Integer DEFAULT_CAVITIES = 1;
    private static final Integer UPDATED_CAVITIES = 2;
    private static final Integer SMALLER_CAVITIES = 1 - 1;

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_RISK_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_RISK_LEVEL = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/nt-quote-component-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/nt-quote-component-details/_search";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NtQuoteComponentDetailRepository ntQuoteComponentDetailRepository;

    @Autowired
    private NtQuoteComponentDetailMapper ntQuoteComponentDetailMapper;

    @Autowired
    private NtQuoteComponentDetailSearchRepository ntQuoteComponentDetailSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNtQuoteComponentDetailMockMvc;

    private NtQuoteComponentDetail ntQuoteComponentDetail;

    private NtQuoteComponentDetail insertedNtQuoteComponentDetail;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NtQuoteComponentDetail createEntity() {
        return new NtQuoteComponentDetail()
            .srNo(DEFAULT_SR_NO)
            .uid(DEFAULT_UID)
            .materialDescription(DEFAULT_MATERIAL_DESCRIPTION)
            .partNumber(DEFAULT_PART_NUMBER)
            .eau(DEFAULT_EAU)
            .manufacturingLocation(DEFAULT_MANUFACTURING_LOCATION)
            .fobLocation(DEFAULT_FOB_LOCATION)
            .packingRequirements(DEFAULT_PACKING_REQUIREMENTS)
            .machineSize(DEFAULT_MACHINE_SIZE)
            .cycleTime(DEFAULT_CYCLE_TIME)
            .partWeight(DEFAULT_PART_WEIGHT)
            .runnerWeight(DEFAULT_RUNNER_WEIGHT)
            .cavities(DEFAULT_CAVITIES)
            .comments(DEFAULT_COMMENTS)
            .riskLevel(DEFAULT_RISK_LEVEL)
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
    public static NtQuoteComponentDetail createUpdatedEntity() {
        return new NtQuoteComponentDetail()
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .materialDescription(UPDATED_MATERIAL_DESCRIPTION)
            .partNumber(UPDATED_PART_NUMBER)
            .eau(UPDATED_EAU)
            .manufacturingLocation(UPDATED_MANUFACTURING_LOCATION)
            .fobLocation(UPDATED_FOB_LOCATION)
            .packingRequirements(UPDATED_PACKING_REQUIREMENTS)
            .machineSize(UPDATED_MACHINE_SIZE)
            .cycleTime(UPDATED_CYCLE_TIME)
            .partWeight(UPDATED_PART_WEIGHT)
            .runnerWeight(UPDATED_RUNNER_WEIGHT)
            .cavities(UPDATED_CAVITIES)
            .comments(UPDATED_COMMENTS)
            .riskLevel(UPDATED_RISK_LEVEL)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);
    }

    @BeforeEach
    public void initTest() {
        ntQuoteComponentDetail = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedNtQuoteComponentDetail != null) {
            ntQuoteComponentDetailRepository.delete(insertedNtQuoteComponentDetail);
            ntQuoteComponentDetailSearchRepository.delete(insertedNtQuoteComponentDetail);
            insertedNtQuoteComponentDetail = null;
        }
    }

    @Test
    @Transactional
    void createNtQuoteComponentDetail() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteComponentDetailSearchRepository.findAll());
        // Create the NtQuoteComponentDetail
        NtQuoteComponentDetailDTO ntQuoteComponentDetailDTO = ntQuoteComponentDetailMapper.toDto(ntQuoteComponentDetail);
        var returnedNtQuoteComponentDetailDTO = om.readValue(
            restNtQuoteComponentDetailMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteComponentDetailDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            NtQuoteComponentDetailDTO.class
        );

        // Validate the NtQuoteComponentDetail in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedNtQuoteComponentDetail = ntQuoteComponentDetailMapper.toEntity(returnedNtQuoteComponentDetailDTO);
        assertNtQuoteComponentDetailUpdatableFieldsEquals(
            returnedNtQuoteComponentDetail,
            getPersistedNtQuoteComponentDetail(returnedNtQuoteComponentDetail)
        );

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteComponentDetailSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });

        insertedNtQuoteComponentDetail = returnedNtQuoteComponentDetail;
    }

    @Test
    @Transactional
    void createNtQuoteComponentDetailWithExistingId() throws Exception {
        // Create the NtQuoteComponentDetail with an existing ID
        ntQuoteComponentDetail.setId(1L);
        NtQuoteComponentDetailDTO ntQuoteComponentDetailDTO = ntQuoteComponentDetailMapper.toDto(ntQuoteComponentDetail);

        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteComponentDetailSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restNtQuoteComponentDetailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteComponentDetailDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteComponentDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteComponentDetailSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkUidIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteComponentDetailSearchRepository.findAll());
        // set the field null
        ntQuoteComponentDetail.setUid(null);

        // Create the NtQuoteComponentDetail, which fails.
        NtQuoteComponentDetailDTO ntQuoteComponentDetailDTO = ntQuoteComponentDetailMapper.toDto(ntQuoteComponentDetail);

        restNtQuoteComponentDetailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteComponentDetailDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteComponentDetailSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetails() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList
        restNtQuoteComponentDetailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuoteComponentDetail.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].materialDescription").value(hasItem(DEFAULT_MATERIAL_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].partNumber").value(hasItem(DEFAULT_PART_NUMBER)))
            .andExpect(jsonPath("$.[*].eau").value(hasItem(DEFAULT_EAU)))
            .andExpect(jsonPath("$.[*].manufacturingLocation").value(hasItem(DEFAULT_MANUFACTURING_LOCATION)))
            .andExpect(jsonPath("$.[*].fobLocation").value(hasItem(DEFAULT_FOB_LOCATION)))
            .andExpect(jsonPath("$.[*].packingRequirements").value(hasItem(DEFAULT_PACKING_REQUIREMENTS)))
            .andExpect(jsonPath("$.[*].machineSize").value(hasItem(DEFAULT_MACHINE_SIZE)))
            .andExpect(jsonPath("$.[*].cycleTime").value(hasItem(DEFAULT_CYCLE_TIME)))
            .andExpect(jsonPath("$.[*].partWeight").value(hasItem(DEFAULT_PART_WEIGHT)))
            .andExpect(jsonPath("$.[*].runnerWeight").value(hasItem(DEFAULT_RUNNER_WEIGHT)))
            .andExpect(jsonPath("$.[*].cavities").value(hasItem(DEFAULT_CAVITIES)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].riskLevel").value(hasItem(DEFAULT_RISK_LEVEL)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getNtQuoteComponentDetail() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get the ntQuoteComponentDetail
        restNtQuoteComponentDetailMockMvc
            .perform(get(ENTITY_API_URL_ID, ntQuoteComponentDetail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ntQuoteComponentDetail.getId().intValue()))
            .andExpect(jsonPath("$.srNo").value(DEFAULT_SR_NO))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.materialDescription").value(DEFAULT_MATERIAL_DESCRIPTION))
            .andExpect(jsonPath("$.partNumber").value(DEFAULT_PART_NUMBER))
            .andExpect(jsonPath("$.eau").value(DEFAULT_EAU))
            .andExpect(jsonPath("$.manufacturingLocation").value(DEFAULT_MANUFACTURING_LOCATION))
            .andExpect(jsonPath("$.fobLocation").value(DEFAULT_FOB_LOCATION))
            .andExpect(jsonPath("$.packingRequirements").value(DEFAULT_PACKING_REQUIREMENTS))
            .andExpect(jsonPath("$.machineSize").value(DEFAULT_MACHINE_SIZE))
            .andExpect(jsonPath("$.cycleTime").value(DEFAULT_CYCLE_TIME))
            .andExpect(jsonPath("$.partWeight").value(DEFAULT_PART_WEIGHT))
            .andExpect(jsonPath("$.runnerWeight").value(DEFAULT_RUNNER_WEIGHT))
            .andExpect(jsonPath("$.cavities").value(DEFAULT_CAVITIES))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.riskLevel").value(DEFAULT_RISK_LEVEL))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNtQuoteComponentDetailsByIdFiltering() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        Long id = ntQuoteComponentDetail.getId();

        defaultNtQuoteComponentDetailFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultNtQuoteComponentDetailFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultNtQuoteComponentDetailFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsBySrNoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where srNo equals to
        defaultNtQuoteComponentDetailFiltering("srNo.equals=" + DEFAULT_SR_NO, "srNo.equals=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsBySrNoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where srNo in
        defaultNtQuoteComponentDetailFiltering("srNo.in=" + DEFAULT_SR_NO + "," + UPDATED_SR_NO, "srNo.in=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsBySrNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where srNo is not null
        defaultNtQuoteComponentDetailFiltering("srNo.specified=true", "srNo.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsBySrNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where srNo is greater than or equal to
        defaultNtQuoteComponentDetailFiltering("srNo.greaterThanOrEqual=" + DEFAULT_SR_NO, "srNo.greaterThanOrEqual=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsBySrNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where srNo is less than or equal to
        defaultNtQuoteComponentDetailFiltering("srNo.lessThanOrEqual=" + DEFAULT_SR_NO, "srNo.lessThanOrEqual=" + SMALLER_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsBySrNoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where srNo is less than
        defaultNtQuoteComponentDetailFiltering("srNo.lessThan=" + UPDATED_SR_NO, "srNo.lessThan=" + DEFAULT_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsBySrNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where srNo is greater than
        defaultNtQuoteComponentDetailFiltering("srNo.greaterThan=" + SMALLER_SR_NO, "srNo.greaterThan=" + DEFAULT_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where uid equals to
        defaultNtQuoteComponentDetailFiltering("uid.equals=" + DEFAULT_UID, "uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where uid in
        defaultNtQuoteComponentDetailFiltering("uid.in=" + DEFAULT_UID + "," + UPDATED_UID, "uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where uid is not null
        defaultNtQuoteComponentDetailFiltering("uid.specified=true", "uid.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByMaterialDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where materialDescription equals to
        defaultNtQuoteComponentDetailFiltering(
            "materialDescription.equals=" + DEFAULT_MATERIAL_DESCRIPTION,
            "materialDescription.equals=" + UPDATED_MATERIAL_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByMaterialDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where materialDescription in
        defaultNtQuoteComponentDetailFiltering(
            "materialDescription.in=" + DEFAULT_MATERIAL_DESCRIPTION + "," + UPDATED_MATERIAL_DESCRIPTION,
            "materialDescription.in=" + UPDATED_MATERIAL_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByMaterialDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where materialDescription is not null
        defaultNtQuoteComponentDetailFiltering("materialDescription.specified=true", "materialDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByMaterialDescriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where materialDescription contains
        defaultNtQuoteComponentDetailFiltering(
            "materialDescription.contains=" + DEFAULT_MATERIAL_DESCRIPTION,
            "materialDescription.contains=" + UPDATED_MATERIAL_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByMaterialDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where materialDescription does not contain
        defaultNtQuoteComponentDetailFiltering(
            "materialDescription.doesNotContain=" + UPDATED_MATERIAL_DESCRIPTION,
            "materialDescription.doesNotContain=" + DEFAULT_MATERIAL_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByPartNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where partNumber equals to
        defaultNtQuoteComponentDetailFiltering("partNumber.equals=" + DEFAULT_PART_NUMBER, "partNumber.equals=" + UPDATED_PART_NUMBER);
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByPartNumberIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where partNumber in
        defaultNtQuoteComponentDetailFiltering(
            "partNumber.in=" + DEFAULT_PART_NUMBER + "," + UPDATED_PART_NUMBER,
            "partNumber.in=" + UPDATED_PART_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByPartNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where partNumber is not null
        defaultNtQuoteComponentDetailFiltering("partNumber.specified=true", "partNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByPartNumberContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where partNumber contains
        defaultNtQuoteComponentDetailFiltering("partNumber.contains=" + DEFAULT_PART_NUMBER, "partNumber.contains=" + UPDATED_PART_NUMBER);
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByPartNumberNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where partNumber does not contain
        defaultNtQuoteComponentDetailFiltering(
            "partNumber.doesNotContain=" + UPDATED_PART_NUMBER,
            "partNumber.doesNotContain=" + DEFAULT_PART_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByEauIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where eau equals to
        defaultNtQuoteComponentDetailFiltering("eau.equals=" + DEFAULT_EAU, "eau.equals=" + UPDATED_EAU);
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByEauIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where eau in
        defaultNtQuoteComponentDetailFiltering("eau.in=" + DEFAULT_EAU + "," + UPDATED_EAU, "eau.in=" + UPDATED_EAU);
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByEauIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where eau is not null
        defaultNtQuoteComponentDetailFiltering("eau.specified=true", "eau.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByEauIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where eau is greater than or equal to
        defaultNtQuoteComponentDetailFiltering("eau.greaterThanOrEqual=" + DEFAULT_EAU, "eau.greaterThanOrEqual=" + UPDATED_EAU);
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByEauIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where eau is less than or equal to
        defaultNtQuoteComponentDetailFiltering("eau.lessThanOrEqual=" + DEFAULT_EAU, "eau.lessThanOrEqual=" + SMALLER_EAU);
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByEauIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where eau is less than
        defaultNtQuoteComponentDetailFiltering("eau.lessThan=" + UPDATED_EAU, "eau.lessThan=" + DEFAULT_EAU);
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByEauIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where eau is greater than
        defaultNtQuoteComponentDetailFiltering("eau.greaterThan=" + SMALLER_EAU, "eau.greaterThan=" + DEFAULT_EAU);
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByManufacturingLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where manufacturingLocation equals to
        defaultNtQuoteComponentDetailFiltering(
            "manufacturingLocation.equals=" + DEFAULT_MANUFACTURING_LOCATION,
            "manufacturingLocation.equals=" + UPDATED_MANUFACTURING_LOCATION
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByManufacturingLocationIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where manufacturingLocation in
        defaultNtQuoteComponentDetailFiltering(
            "manufacturingLocation.in=" + DEFAULT_MANUFACTURING_LOCATION + "," + UPDATED_MANUFACTURING_LOCATION,
            "manufacturingLocation.in=" + UPDATED_MANUFACTURING_LOCATION
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByManufacturingLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where manufacturingLocation is not null
        defaultNtQuoteComponentDetailFiltering("manufacturingLocation.specified=true", "manufacturingLocation.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByManufacturingLocationContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where manufacturingLocation contains
        defaultNtQuoteComponentDetailFiltering(
            "manufacturingLocation.contains=" + DEFAULT_MANUFACTURING_LOCATION,
            "manufacturingLocation.contains=" + UPDATED_MANUFACTURING_LOCATION
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByManufacturingLocationNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where manufacturingLocation does not contain
        defaultNtQuoteComponentDetailFiltering(
            "manufacturingLocation.doesNotContain=" + UPDATED_MANUFACTURING_LOCATION,
            "manufacturingLocation.doesNotContain=" + DEFAULT_MANUFACTURING_LOCATION
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByFobLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where fobLocation equals to
        defaultNtQuoteComponentDetailFiltering("fobLocation.equals=" + DEFAULT_FOB_LOCATION, "fobLocation.equals=" + UPDATED_FOB_LOCATION);
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByFobLocationIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where fobLocation in
        defaultNtQuoteComponentDetailFiltering(
            "fobLocation.in=" + DEFAULT_FOB_LOCATION + "," + UPDATED_FOB_LOCATION,
            "fobLocation.in=" + UPDATED_FOB_LOCATION
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByFobLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where fobLocation is not null
        defaultNtQuoteComponentDetailFiltering("fobLocation.specified=true", "fobLocation.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByFobLocationContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where fobLocation contains
        defaultNtQuoteComponentDetailFiltering(
            "fobLocation.contains=" + DEFAULT_FOB_LOCATION,
            "fobLocation.contains=" + UPDATED_FOB_LOCATION
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByFobLocationNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where fobLocation does not contain
        defaultNtQuoteComponentDetailFiltering(
            "fobLocation.doesNotContain=" + UPDATED_FOB_LOCATION,
            "fobLocation.doesNotContain=" + DEFAULT_FOB_LOCATION
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByPackingRequirementsIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where packingRequirements equals to
        defaultNtQuoteComponentDetailFiltering(
            "packingRequirements.equals=" + DEFAULT_PACKING_REQUIREMENTS,
            "packingRequirements.equals=" + UPDATED_PACKING_REQUIREMENTS
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByPackingRequirementsIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where packingRequirements in
        defaultNtQuoteComponentDetailFiltering(
            "packingRequirements.in=" + DEFAULT_PACKING_REQUIREMENTS + "," + UPDATED_PACKING_REQUIREMENTS,
            "packingRequirements.in=" + UPDATED_PACKING_REQUIREMENTS
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByPackingRequirementsIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where packingRequirements is not null
        defaultNtQuoteComponentDetailFiltering("packingRequirements.specified=true", "packingRequirements.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByPackingRequirementsContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where packingRequirements contains
        defaultNtQuoteComponentDetailFiltering(
            "packingRequirements.contains=" + DEFAULT_PACKING_REQUIREMENTS,
            "packingRequirements.contains=" + UPDATED_PACKING_REQUIREMENTS
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByPackingRequirementsNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where packingRequirements does not contain
        defaultNtQuoteComponentDetailFiltering(
            "packingRequirements.doesNotContain=" + UPDATED_PACKING_REQUIREMENTS,
            "packingRequirements.doesNotContain=" + DEFAULT_PACKING_REQUIREMENTS
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByMachineSizeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where machineSize equals to
        defaultNtQuoteComponentDetailFiltering("machineSize.equals=" + DEFAULT_MACHINE_SIZE, "machineSize.equals=" + UPDATED_MACHINE_SIZE);
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByMachineSizeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where machineSize in
        defaultNtQuoteComponentDetailFiltering(
            "machineSize.in=" + DEFAULT_MACHINE_SIZE + "," + UPDATED_MACHINE_SIZE,
            "machineSize.in=" + UPDATED_MACHINE_SIZE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByMachineSizeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where machineSize is not null
        defaultNtQuoteComponentDetailFiltering("machineSize.specified=true", "machineSize.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByMachineSizeContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where machineSize contains
        defaultNtQuoteComponentDetailFiltering(
            "machineSize.contains=" + DEFAULT_MACHINE_SIZE,
            "machineSize.contains=" + UPDATED_MACHINE_SIZE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByMachineSizeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where machineSize does not contain
        defaultNtQuoteComponentDetailFiltering(
            "machineSize.doesNotContain=" + UPDATED_MACHINE_SIZE,
            "machineSize.doesNotContain=" + DEFAULT_MACHINE_SIZE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByCycleTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where cycleTime equals to
        defaultNtQuoteComponentDetailFiltering("cycleTime.equals=" + DEFAULT_CYCLE_TIME, "cycleTime.equals=" + UPDATED_CYCLE_TIME);
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByCycleTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where cycleTime in
        defaultNtQuoteComponentDetailFiltering(
            "cycleTime.in=" + DEFAULT_CYCLE_TIME + "," + UPDATED_CYCLE_TIME,
            "cycleTime.in=" + UPDATED_CYCLE_TIME
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByCycleTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where cycleTime is not null
        defaultNtQuoteComponentDetailFiltering("cycleTime.specified=true", "cycleTime.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByCycleTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where cycleTime is greater than or equal to
        defaultNtQuoteComponentDetailFiltering(
            "cycleTime.greaterThanOrEqual=" + DEFAULT_CYCLE_TIME,
            "cycleTime.greaterThanOrEqual=" + UPDATED_CYCLE_TIME
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByCycleTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where cycleTime is less than or equal to
        defaultNtQuoteComponentDetailFiltering(
            "cycleTime.lessThanOrEqual=" + DEFAULT_CYCLE_TIME,
            "cycleTime.lessThanOrEqual=" + SMALLER_CYCLE_TIME
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByCycleTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where cycleTime is less than
        defaultNtQuoteComponentDetailFiltering("cycleTime.lessThan=" + UPDATED_CYCLE_TIME, "cycleTime.lessThan=" + DEFAULT_CYCLE_TIME);
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByCycleTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where cycleTime is greater than
        defaultNtQuoteComponentDetailFiltering(
            "cycleTime.greaterThan=" + SMALLER_CYCLE_TIME,
            "cycleTime.greaterThan=" + DEFAULT_CYCLE_TIME
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByPartWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where partWeight equals to
        defaultNtQuoteComponentDetailFiltering("partWeight.equals=" + DEFAULT_PART_WEIGHT, "partWeight.equals=" + UPDATED_PART_WEIGHT);
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByPartWeightIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where partWeight in
        defaultNtQuoteComponentDetailFiltering(
            "partWeight.in=" + DEFAULT_PART_WEIGHT + "," + UPDATED_PART_WEIGHT,
            "partWeight.in=" + UPDATED_PART_WEIGHT
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByPartWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where partWeight is not null
        defaultNtQuoteComponentDetailFiltering("partWeight.specified=true", "partWeight.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByPartWeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where partWeight is greater than or equal to
        defaultNtQuoteComponentDetailFiltering(
            "partWeight.greaterThanOrEqual=" + DEFAULT_PART_WEIGHT,
            "partWeight.greaterThanOrEqual=" + UPDATED_PART_WEIGHT
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByPartWeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where partWeight is less than or equal to
        defaultNtQuoteComponentDetailFiltering(
            "partWeight.lessThanOrEqual=" + DEFAULT_PART_WEIGHT,
            "partWeight.lessThanOrEqual=" + SMALLER_PART_WEIGHT
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByPartWeightIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where partWeight is less than
        defaultNtQuoteComponentDetailFiltering("partWeight.lessThan=" + UPDATED_PART_WEIGHT, "partWeight.lessThan=" + DEFAULT_PART_WEIGHT);
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByPartWeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where partWeight is greater than
        defaultNtQuoteComponentDetailFiltering(
            "partWeight.greaterThan=" + SMALLER_PART_WEIGHT,
            "partWeight.greaterThan=" + DEFAULT_PART_WEIGHT
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByRunnerWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where runnerWeight equals to
        defaultNtQuoteComponentDetailFiltering(
            "runnerWeight.equals=" + DEFAULT_RUNNER_WEIGHT,
            "runnerWeight.equals=" + UPDATED_RUNNER_WEIGHT
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByRunnerWeightIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where runnerWeight in
        defaultNtQuoteComponentDetailFiltering(
            "runnerWeight.in=" + DEFAULT_RUNNER_WEIGHT + "," + UPDATED_RUNNER_WEIGHT,
            "runnerWeight.in=" + UPDATED_RUNNER_WEIGHT
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByRunnerWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where runnerWeight is not null
        defaultNtQuoteComponentDetailFiltering("runnerWeight.specified=true", "runnerWeight.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByRunnerWeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where runnerWeight is greater than or equal to
        defaultNtQuoteComponentDetailFiltering(
            "runnerWeight.greaterThanOrEqual=" + DEFAULT_RUNNER_WEIGHT,
            "runnerWeight.greaterThanOrEqual=" + UPDATED_RUNNER_WEIGHT
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByRunnerWeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where runnerWeight is less than or equal to
        defaultNtQuoteComponentDetailFiltering(
            "runnerWeight.lessThanOrEqual=" + DEFAULT_RUNNER_WEIGHT,
            "runnerWeight.lessThanOrEqual=" + SMALLER_RUNNER_WEIGHT
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByRunnerWeightIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where runnerWeight is less than
        defaultNtQuoteComponentDetailFiltering(
            "runnerWeight.lessThan=" + UPDATED_RUNNER_WEIGHT,
            "runnerWeight.lessThan=" + DEFAULT_RUNNER_WEIGHT
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByRunnerWeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where runnerWeight is greater than
        defaultNtQuoteComponentDetailFiltering(
            "runnerWeight.greaterThan=" + SMALLER_RUNNER_WEIGHT,
            "runnerWeight.greaterThan=" + DEFAULT_RUNNER_WEIGHT
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByCavitiesIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where cavities equals to
        defaultNtQuoteComponentDetailFiltering("cavities.equals=" + DEFAULT_CAVITIES, "cavities.equals=" + UPDATED_CAVITIES);
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByCavitiesIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where cavities in
        defaultNtQuoteComponentDetailFiltering(
            "cavities.in=" + DEFAULT_CAVITIES + "," + UPDATED_CAVITIES,
            "cavities.in=" + UPDATED_CAVITIES
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByCavitiesIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where cavities is not null
        defaultNtQuoteComponentDetailFiltering("cavities.specified=true", "cavities.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByCavitiesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where cavities is greater than or equal to
        defaultNtQuoteComponentDetailFiltering(
            "cavities.greaterThanOrEqual=" + DEFAULT_CAVITIES,
            "cavities.greaterThanOrEqual=" + UPDATED_CAVITIES
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByCavitiesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where cavities is less than or equal to
        defaultNtQuoteComponentDetailFiltering(
            "cavities.lessThanOrEqual=" + DEFAULT_CAVITIES,
            "cavities.lessThanOrEqual=" + SMALLER_CAVITIES
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByCavitiesIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where cavities is less than
        defaultNtQuoteComponentDetailFiltering("cavities.lessThan=" + UPDATED_CAVITIES, "cavities.lessThan=" + DEFAULT_CAVITIES);
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByCavitiesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where cavities is greater than
        defaultNtQuoteComponentDetailFiltering("cavities.greaterThan=" + SMALLER_CAVITIES, "cavities.greaterThan=" + DEFAULT_CAVITIES);
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where comments equals to
        defaultNtQuoteComponentDetailFiltering("comments.equals=" + DEFAULT_COMMENTS, "comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where comments in
        defaultNtQuoteComponentDetailFiltering(
            "comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS,
            "comments.in=" + UPDATED_COMMENTS
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where comments is not null
        defaultNtQuoteComponentDetailFiltering("comments.specified=true", "comments.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByCommentsContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where comments contains
        defaultNtQuoteComponentDetailFiltering("comments.contains=" + DEFAULT_COMMENTS, "comments.contains=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where comments does not contain
        defaultNtQuoteComponentDetailFiltering(
            "comments.doesNotContain=" + UPDATED_COMMENTS,
            "comments.doesNotContain=" + DEFAULT_COMMENTS
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByRiskLevelIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where riskLevel equals to
        defaultNtQuoteComponentDetailFiltering("riskLevel.equals=" + DEFAULT_RISK_LEVEL, "riskLevel.equals=" + UPDATED_RISK_LEVEL);
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByRiskLevelIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where riskLevel in
        defaultNtQuoteComponentDetailFiltering(
            "riskLevel.in=" + DEFAULT_RISK_LEVEL + "," + UPDATED_RISK_LEVEL,
            "riskLevel.in=" + UPDATED_RISK_LEVEL
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByRiskLevelIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where riskLevel is not null
        defaultNtQuoteComponentDetailFiltering("riskLevel.specified=true", "riskLevel.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByRiskLevelContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where riskLevel contains
        defaultNtQuoteComponentDetailFiltering("riskLevel.contains=" + DEFAULT_RISK_LEVEL, "riskLevel.contains=" + UPDATED_RISK_LEVEL);
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByRiskLevelNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where riskLevel does not contain
        defaultNtQuoteComponentDetailFiltering(
            "riskLevel.doesNotContain=" + UPDATED_RISK_LEVEL,
            "riskLevel.doesNotContain=" + DEFAULT_RISK_LEVEL
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where createdBy equals to
        defaultNtQuoteComponentDetailFiltering("createdBy.equals=" + DEFAULT_CREATED_BY, "createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where createdBy in
        defaultNtQuoteComponentDetailFiltering(
            "createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY,
            "createdBy.in=" + UPDATED_CREATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where createdBy is not null
        defaultNtQuoteComponentDetailFiltering("createdBy.specified=true", "createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where createdBy contains
        defaultNtQuoteComponentDetailFiltering("createdBy.contains=" + DEFAULT_CREATED_BY, "createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where createdBy does not contain
        defaultNtQuoteComponentDetailFiltering(
            "createdBy.doesNotContain=" + UPDATED_CREATED_BY,
            "createdBy.doesNotContain=" + DEFAULT_CREATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where createdDate equals to
        defaultNtQuoteComponentDetailFiltering("createdDate.equals=" + DEFAULT_CREATED_DATE, "createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where createdDate in
        defaultNtQuoteComponentDetailFiltering(
            "createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE,
            "createdDate.in=" + UPDATED_CREATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where createdDate is not null
        defaultNtQuoteComponentDetailFiltering("createdDate.specified=true", "createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where updatedBy equals to
        defaultNtQuoteComponentDetailFiltering("updatedBy.equals=" + DEFAULT_UPDATED_BY, "updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where updatedBy in
        defaultNtQuoteComponentDetailFiltering(
            "updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY,
            "updatedBy.in=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where updatedBy is not null
        defaultNtQuoteComponentDetailFiltering("updatedBy.specified=true", "updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where updatedBy contains
        defaultNtQuoteComponentDetailFiltering("updatedBy.contains=" + DEFAULT_UPDATED_BY, "updatedBy.contains=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where updatedBy does not contain
        defaultNtQuoteComponentDetailFiltering(
            "updatedBy.doesNotContain=" + UPDATED_UPDATED_BY,
            "updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where updatedDate equals to
        defaultNtQuoteComponentDetailFiltering("updatedDate.equals=" + DEFAULT_UPDATED_DATE, "updatedDate.equals=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where updatedDate in
        defaultNtQuoteComponentDetailFiltering(
            "updatedDate.in=" + DEFAULT_UPDATED_DATE + "," + UPDATED_UPDATED_DATE,
            "updatedDate.in=" + UPDATED_UPDATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        // Get all the ntQuoteComponentDetailList where updatedDate is not null
        defaultNtQuoteComponentDetailFiltering("updatedDate.specified=true", "updatedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByNtQuoteIsEqualToSomething() throws Exception {
        NtQuote ntQuote;
        if (TestUtil.findAll(em, NtQuote.class).isEmpty()) {
            ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);
            ntQuote = NtQuoteResourceIT.createEntity();
        } else {
            ntQuote = TestUtil.findAll(em, NtQuote.class).get(0);
        }
        em.persist(ntQuote);
        em.flush();
        ntQuoteComponentDetail.setNtQuote(ntQuote);
        ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);
        Long ntQuoteId = ntQuote.getId();
        // Get all the ntQuoteComponentDetailList where ntQuote equals to ntQuoteId
        defaultNtQuoteComponentDetailShouldBeFound("ntQuoteId.equals=" + ntQuoteId);

        // Get all the ntQuoteComponentDetailList where ntQuote equals to (ntQuoteId + 1)
        defaultNtQuoteComponentDetailShouldNotBeFound("ntQuoteId.equals=" + (ntQuoteId + 1));
    }

    @Test
    @Transactional
    void getAllNtQuoteComponentDetailsByMaterialPriceIsEqualToSomething() throws Exception {
        BuyerRfqPricesDetail materialPrice;
        if (TestUtil.findAll(em, BuyerRfqPricesDetail.class).isEmpty()) {
            ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);
            materialPrice = BuyerRfqPricesDetailResourceIT.createEntity();
        } else {
            materialPrice = TestUtil.findAll(em, BuyerRfqPricesDetail.class).get(0);
        }
        em.persist(materialPrice);
        em.flush();
        ntQuoteComponentDetail.setMaterialPrice(materialPrice);
        ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);
        Long materialPriceId = materialPrice.getId();
        // Get all the ntQuoteComponentDetailList where materialPrice equals to materialPriceId
        defaultNtQuoteComponentDetailShouldBeFound("materialPriceId.equals=" + materialPriceId);

        // Get all the ntQuoteComponentDetailList where materialPrice equals to (materialPriceId + 1)
        defaultNtQuoteComponentDetailShouldNotBeFound("materialPriceId.equals=" + (materialPriceId + 1));
    }

    private void defaultNtQuoteComponentDetailFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultNtQuoteComponentDetailShouldBeFound(shouldBeFound);
        defaultNtQuoteComponentDetailShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNtQuoteComponentDetailShouldBeFound(String filter) throws Exception {
        restNtQuoteComponentDetailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuoteComponentDetail.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].materialDescription").value(hasItem(DEFAULT_MATERIAL_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].partNumber").value(hasItem(DEFAULT_PART_NUMBER)))
            .andExpect(jsonPath("$.[*].eau").value(hasItem(DEFAULT_EAU)))
            .andExpect(jsonPath("$.[*].manufacturingLocation").value(hasItem(DEFAULT_MANUFACTURING_LOCATION)))
            .andExpect(jsonPath("$.[*].fobLocation").value(hasItem(DEFAULT_FOB_LOCATION)))
            .andExpect(jsonPath("$.[*].packingRequirements").value(hasItem(DEFAULT_PACKING_REQUIREMENTS)))
            .andExpect(jsonPath("$.[*].machineSize").value(hasItem(DEFAULT_MACHINE_SIZE)))
            .andExpect(jsonPath("$.[*].cycleTime").value(hasItem(DEFAULT_CYCLE_TIME)))
            .andExpect(jsonPath("$.[*].partWeight").value(hasItem(DEFAULT_PART_WEIGHT)))
            .andExpect(jsonPath("$.[*].runnerWeight").value(hasItem(DEFAULT_RUNNER_WEIGHT)))
            .andExpect(jsonPath("$.[*].cavities").value(hasItem(DEFAULT_CAVITIES)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].riskLevel").value(hasItem(DEFAULT_RISK_LEVEL)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));

        // Check, that the count call also returns 1
        restNtQuoteComponentDetailMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNtQuoteComponentDetailShouldNotBeFound(String filter) throws Exception {
        restNtQuoteComponentDetailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNtQuoteComponentDetailMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNtQuoteComponentDetail() throws Exception {
        // Get the ntQuoteComponentDetail
        restNtQuoteComponentDetailMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNtQuoteComponentDetail() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        long databaseSizeBeforeUpdate = getRepositoryCount();
        ntQuoteComponentDetailSearchRepository.save(ntQuoteComponentDetail);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteComponentDetailSearchRepository.findAll());

        // Update the ntQuoteComponentDetail
        NtQuoteComponentDetail updatedNtQuoteComponentDetail = ntQuoteComponentDetailRepository
            .findById(ntQuoteComponentDetail.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedNtQuoteComponentDetail are not directly saved in db
        em.detach(updatedNtQuoteComponentDetail);
        updatedNtQuoteComponentDetail
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .materialDescription(UPDATED_MATERIAL_DESCRIPTION)
            .partNumber(UPDATED_PART_NUMBER)
            .eau(UPDATED_EAU)
            .manufacturingLocation(UPDATED_MANUFACTURING_LOCATION)
            .fobLocation(UPDATED_FOB_LOCATION)
            .packingRequirements(UPDATED_PACKING_REQUIREMENTS)
            .machineSize(UPDATED_MACHINE_SIZE)
            .cycleTime(UPDATED_CYCLE_TIME)
            .partWeight(UPDATED_PART_WEIGHT)
            .runnerWeight(UPDATED_RUNNER_WEIGHT)
            .cavities(UPDATED_CAVITIES)
            .comments(UPDATED_COMMENTS)
            .riskLevel(UPDATED_RISK_LEVEL)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);
        NtQuoteComponentDetailDTO ntQuoteComponentDetailDTO = ntQuoteComponentDetailMapper.toDto(updatedNtQuoteComponentDetail);

        restNtQuoteComponentDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ntQuoteComponentDetailDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteComponentDetailDTO))
            )
            .andExpect(status().isOk());

        // Validate the NtQuoteComponentDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedNtQuoteComponentDetailToMatchAllProperties(updatedNtQuoteComponentDetail);

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteComponentDetailSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<NtQuoteComponentDetail> ntQuoteComponentDetailSearchList = Streamable.of(
                    ntQuoteComponentDetailSearchRepository.findAll()
                ).toList();
                NtQuoteComponentDetail testNtQuoteComponentDetailSearch = ntQuoteComponentDetailSearchList.get(searchDatabaseSizeAfter - 1);

                assertNtQuoteComponentDetailAllPropertiesEquals(testNtQuoteComponentDetailSearch, updatedNtQuoteComponentDetail);
            });
    }

    @Test
    @Transactional
    void putNonExistingNtQuoteComponentDetail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteComponentDetailSearchRepository.findAll());
        ntQuoteComponentDetail.setId(longCount.incrementAndGet());

        // Create the NtQuoteComponentDetail
        NtQuoteComponentDetailDTO ntQuoteComponentDetailDTO = ntQuoteComponentDetailMapper.toDto(ntQuoteComponentDetail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNtQuoteComponentDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ntQuoteComponentDetailDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteComponentDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteComponentDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteComponentDetailSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchNtQuoteComponentDetail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteComponentDetailSearchRepository.findAll());
        ntQuoteComponentDetail.setId(longCount.incrementAndGet());

        // Create the NtQuoteComponentDetail
        NtQuoteComponentDetailDTO ntQuoteComponentDetailDTO = ntQuoteComponentDetailMapper.toDto(ntQuoteComponentDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteComponentDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteComponentDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteComponentDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteComponentDetailSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNtQuoteComponentDetail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteComponentDetailSearchRepository.findAll());
        ntQuoteComponentDetail.setId(longCount.incrementAndGet());

        // Create the NtQuoteComponentDetail
        NtQuoteComponentDetailDTO ntQuoteComponentDetailDTO = ntQuoteComponentDetailMapper.toDto(ntQuoteComponentDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteComponentDetailMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteComponentDetailDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NtQuoteComponentDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteComponentDetailSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateNtQuoteComponentDetailWithPatch() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ntQuoteComponentDetail using partial update
        NtQuoteComponentDetail partialUpdatedNtQuoteComponentDetail = new NtQuoteComponentDetail();
        partialUpdatedNtQuoteComponentDetail.setId(ntQuoteComponentDetail.getId());

        partialUpdatedNtQuoteComponentDetail
            .srNo(UPDATED_SR_NO)
            .materialDescription(UPDATED_MATERIAL_DESCRIPTION)
            .eau(UPDATED_EAU)
            .packingRequirements(UPDATED_PACKING_REQUIREMENTS)
            .comments(UPDATED_COMMENTS)
            .riskLevel(UPDATED_RISK_LEVEL)
            .createdBy(UPDATED_CREATED_BY);

        restNtQuoteComponentDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNtQuoteComponentDetail.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNtQuoteComponentDetail))
            )
            .andExpect(status().isOk());

        // Validate the NtQuoteComponentDetail in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNtQuoteComponentDetailUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedNtQuoteComponentDetail, ntQuoteComponentDetail),
            getPersistedNtQuoteComponentDetail(ntQuoteComponentDetail)
        );
    }

    @Test
    @Transactional
    void fullUpdateNtQuoteComponentDetailWithPatch() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ntQuoteComponentDetail using partial update
        NtQuoteComponentDetail partialUpdatedNtQuoteComponentDetail = new NtQuoteComponentDetail();
        partialUpdatedNtQuoteComponentDetail.setId(ntQuoteComponentDetail.getId());

        partialUpdatedNtQuoteComponentDetail
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .materialDescription(UPDATED_MATERIAL_DESCRIPTION)
            .partNumber(UPDATED_PART_NUMBER)
            .eau(UPDATED_EAU)
            .manufacturingLocation(UPDATED_MANUFACTURING_LOCATION)
            .fobLocation(UPDATED_FOB_LOCATION)
            .packingRequirements(UPDATED_PACKING_REQUIREMENTS)
            .machineSize(UPDATED_MACHINE_SIZE)
            .cycleTime(UPDATED_CYCLE_TIME)
            .partWeight(UPDATED_PART_WEIGHT)
            .runnerWeight(UPDATED_RUNNER_WEIGHT)
            .cavities(UPDATED_CAVITIES)
            .comments(UPDATED_COMMENTS)
            .riskLevel(UPDATED_RISK_LEVEL)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);

        restNtQuoteComponentDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNtQuoteComponentDetail.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNtQuoteComponentDetail))
            )
            .andExpect(status().isOk());

        // Validate the NtQuoteComponentDetail in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNtQuoteComponentDetailUpdatableFieldsEquals(
            partialUpdatedNtQuoteComponentDetail,
            getPersistedNtQuoteComponentDetail(partialUpdatedNtQuoteComponentDetail)
        );
    }

    @Test
    @Transactional
    void patchNonExistingNtQuoteComponentDetail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteComponentDetailSearchRepository.findAll());
        ntQuoteComponentDetail.setId(longCount.incrementAndGet());

        // Create the NtQuoteComponentDetail
        NtQuoteComponentDetailDTO ntQuoteComponentDetailDTO = ntQuoteComponentDetailMapper.toDto(ntQuoteComponentDetail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNtQuoteComponentDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ntQuoteComponentDetailDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ntQuoteComponentDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteComponentDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteComponentDetailSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNtQuoteComponentDetail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteComponentDetailSearchRepository.findAll());
        ntQuoteComponentDetail.setId(longCount.incrementAndGet());

        // Create the NtQuoteComponentDetail
        NtQuoteComponentDetailDTO ntQuoteComponentDetailDTO = ntQuoteComponentDetailMapper.toDto(ntQuoteComponentDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteComponentDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ntQuoteComponentDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteComponentDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteComponentDetailSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNtQuoteComponentDetail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteComponentDetailSearchRepository.findAll());
        ntQuoteComponentDetail.setId(longCount.incrementAndGet());

        // Create the NtQuoteComponentDetail
        NtQuoteComponentDetailDTO ntQuoteComponentDetailDTO = ntQuoteComponentDetailMapper.toDto(ntQuoteComponentDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteComponentDetailMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ntQuoteComponentDetailDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NtQuoteComponentDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteComponentDetailSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteNtQuoteComponentDetail() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);
        ntQuoteComponentDetailRepository.save(ntQuoteComponentDetail);
        ntQuoteComponentDetailSearchRepository.save(ntQuoteComponentDetail);

        long databaseSizeBeforeDelete = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteComponentDetailSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the ntQuoteComponentDetail
        restNtQuoteComponentDetailMockMvc
            .perform(delete(ENTITY_API_URL_ID, ntQuoteComponentDetail.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteComponentDetailSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchNtQuoteComponentDetail() throws Exception {
        // Initialize the database
        insertedNtQuoteComponentDetail = ntQuoteComponentDetailRepository.saveAndFlush(ntQuoteComponentDetail);
        ntQuoteComponentDetailSearchRepository.save(ntQuoteComponentDetail);

        // Search the ntQuoteComponentDetail
        restNtQuoteComponentDetailMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + ntQuoteComponentDetail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuoteComponentDetail.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].materialDescription").value(hasItem(DEFAULT_MATERIAL_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].partNumber").value(hasItem(DEFAULT_PART_NUMBER)))
            .andExpect(jsonPath("$.[*].eau").value(hasItem(DEFAULT_EAU)))
            .andExpect(jsonPath("$.[*].manufacturingLocation").value(hasItem(DEFAULT_MANUFACTURING_LOCATION)))
            .andExpect(jsonPath("$.[*].fobLocation").value(hasItem(DEFAULT_FOB_LOCATION)))
            .andExpect(jsonPath("$.[*].packingRequirements").value(hasItem(DEFAULT_PACKING_REQUIREMENTS)))
            .andExpect(jsonPath("$.[*].machineSize").value(hasItem(DEFAULT_MACHINE_SIZE)))
            .andExpect(jsonPath("$.[*].cycleTime").value(hasItem(DEFAULT_CYCLE_TIME)))
            .andExpect(jsonPath("$.[*].partWeight").value(hasItem(DEFAULT_PART_WEIGHT)))
            .andExpect(jsonPath("$.[*].runnerWeight").value(hasItem(DEFAULT_RUNNER_WEIGHT)))
            .andExpect(jsonPath("$.[*].cavities").value(hasItem(DEFAULT_CAVITIES)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].riskLevel").value(hasItem(DEFAULT_RISK_LEVEL)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    protected long getRepositoryCount() {
        return ntQuoteComponentDetailRepository.count();
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

    protected NtQuoteComponentDetail getPersistedNtQuoteComponentDetail(NtQuoteComponentDetail ntQuoteComponentDetail) {
        return ntQuoteComponentDetailRepository.findById(ntQuoteComponentDetail.getId()).orElseThrow();
    }

    protected void assertPersistedNtQuoteComponentDetailToMatchAllProperties(NtQuoteComponentDetail expectedNtQuoteComponentDetail) {
        assertNtQuoteComponentDetailAllPropertiesEquals(
            expectedNtQuoteComponentDetail,
            getPersistedNtQuoteComponentDetail(expectedNtQuoteComponentDetail)
        );
    }

    protected void assertPersistedNtQuoteComponentDetailToMatchUpdatableProperties(NtQuoteComponentDetail expectedNtQuoteComponentDetail) {
        assertNtQuoteComponentDetailAllUpdatablePropertiesEquals(
            expectedNtQuoteComponentDetail,
            getPersistedNtQuoteComponentDetail(expectedNtQuoteComponentDetail)
        );
    }
}
