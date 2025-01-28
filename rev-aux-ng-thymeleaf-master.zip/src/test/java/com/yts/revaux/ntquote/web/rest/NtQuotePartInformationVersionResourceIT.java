package com.yts.revaux.ntquote.web.rest;

import static com.yts.revaux.ntquote.domain.NtQuotePartInformationVersionAsserts.*;
import static com.yts.revaux.ntquote.web.rest.TestUtil.createUpdateProxyForBean;
import static com.yts.revaux.ntquote.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yts.revaux.ntquote.IntegrationTest;
import com.yts.revaux.ntquote.domain.NtQuote;
import com.yts.revaux.ntquote.domain.NtQuotePartInformationVersion;
import com.yts.revaux.ntquote.repository.NtQuotePartInformationVersionRepository;
import com.yts.revaux.ntquote.repository.search.NtQuotePartInformationVersionSearchRepository;
import com.yts.revaux.ntquote.service.dto.NtQuotePartInformationVersionDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuotePartInformationVersionMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
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
 * Integration tests for the {@link NtQuotePartInformationVersionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NtQuotePartInformationVersionResourceIT {

    private static final Integer DEFAULT_SR_NO = 1;
    private static final Integer UPDATED_SR_NO = 2;
    private static final Integer SMALLER_SR_NO = 1 - 1;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final String DEFAULT_MATERIAL_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PART_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PART_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CAD_FILE = "AAAAAAAAAA";
    private static final String UPDATED_CAD_FILE = "BBBBBBBBBB";

    private static final Integer DEFAULT_EAU = 1;
    private static final Integer UPDATED_EAU = 2;
    private static final Integer SMALLER_EAU = 1 - 1;

    private static final Integer DEFAULT_PART_WEIGHT = 1;
    private static final Integer UPDATED_PART_WEIGHT = 2;
    private static final Integer SMALLER_PART_WEIGHT = 1 - 1;

    private static final String DEFAULT_MATERIAL_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_TYPE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_MATERIAL_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_MATERIAL_COST = new BigDecimal(2);
    private static final BigDecimal SMALLER_MATERIAL_COST = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_EXTENDED_MATERIAL_COST_PER = new BigDecimal(1);
    private static final BigDecimal UPDATED_EXTENDED_MATERIAL_COST_PER = new BigDecimal(2);
    private static final BigDecimal SMALLER_EXTENDED_MATERIAL_COST_PER = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_EXTERNAL_MACHINE_COST_PER = new BigDecimal(1);
    private static final BigDecimal UPDATED_EXTERNAL_MACHINE_COST_PER = new BigDecimal(2);
    private static final BigDecimal SMALLER_EXTERNAL_MACHINE_COST_PER = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PURCHASE_COMPONENT_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_PURCHASE_COMPONENT_COST = new BigDecimal(2);
    private static final BigDecimal SMALLER_PURCHASE_COMPONENT_COST = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_SECONDARY_EXTERNAL_OPERATION_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_SECONDARY_EXTERNAL_OPERATION_COST = new BigDecimal(2);
    private static final BigDecimal SMALLER_SECONDARY_EXTERNAL_OPERATION_COST = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_OVERHEAD = new BigDecimal(1);
    private static final BigDecimal UPDATED_OVERHEAD = new BigDecimal(2);
    private static final BigDecimal SMALLER_OVERHEAD = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PACK_LOGISTIC_COST_PER = new BigDecimal(1);
    private static final BigDecimal UPDATED_PACK_LOGISTIC_COST_PER = new BigDecimal(2);
    private static final BigDecimal SMALLER_PACK_LOGISTIC_COST_PER = new BigDecimal(1 - 1);

    private static final String DEFAULT_MACHINE_SIZE_TONS = "AAAAAAAAAA";
    private static final String UPDATED_MACHINE_SIZE_TONS = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMBER_OF_CAVITIES = 1;
    private static final Integer UPDATED_NUMBER_OF_CAVITIES = 2;
    private static final Integer SMALLER_NUMBER_OF_CAVITIES = 1 - 1;

    private static final Integer DEFAULT_CYCLE_TIME = 1;
    private static final Integer UPDATED_CYCLE_TIME = 2;
    private static final Integer SMALLER_CYCLE_TIME = 1 - 1;

    private static final BigDecimal DEFAULT_PER_UNIT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PER_UNIT = new BigDecimal(2);
    private static final BigDecimal SMALLER_PER_UNIT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL_PRICE_PER_CHINA = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_PRICE_PER_CHINA = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_PRICE_PER_CHINA = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL_PRICE_BUDGET = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_PRICE_BUDGET = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_PRICE_BUDGET = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_GRAIN_BUDGET = new BigDecimal(1);
    private static final BigDecimal UPDATED_GRAIN_BUDGET = new BigDecimal(2);
    private static final BigDecimal SMALLER_GRAIN_BUDGET = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_DOGATING_FIXTURE_BUDGET = new BigDecimal(1);
    private static final BigDecimal UPDATED_DOGATING_FIXTURE_BUDGET = new BigDecimal(2);
    private static final BigDecimal SMALLER_DOGATING_FIXTURE_BUDGET = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_GAUGE_BUDGET = new BigDecimal(1);
    private static final BigDecimal UPDATED_GAUGE_BUDGET = new BigDecimal(2);
    private static final BigDecimal SMALLER_GAUGE_BUDGET = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_EOAT = new BigDecimal(1);
    private static final BigDecimal UPDATED_EOAT = new BigDecimal(2);
    private static final BigDecimal SMALLER_EOAT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CHINA_TARIFF_BUDGET = new BigDecimal(1);
    private static final BigDecimal UPDATED_CHINA_TARIFF_BUDGET = new BigDecimal(2);
    private static final BigDecimal SMALLER_CHINA_TARIFF_BUDGET = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL_TOOLING_BUDGET = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_TOOLING_BUDGET = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_TOOLING_BUDGET = new BigDecimal(1 - 1);

    private static final String DEFAULT_LEAD_TIME = "AAAAAAAAAA";
    private static final String UPDATED_LEAD_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_TOOLING_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_TOOLING_NOTES = "BBBBBBBBBB";

    private static final String DEFAULT_PART_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PART_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_JOB_ID = "AAAAAAAAAA";
    private static final String UPDATED_JOB_ID = "BBBBBBBBBB";

    private static final String DEFAULT_MOLD_ID = "AAAAAAAAAA";
    private static final String UPDATED_MOLD_ID = "BBBBBBBBBB";

    private static final String DEFAULT_QUOTE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_QUOTE_TYPE = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/nt-quote-part-information-versions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/nt-quote-part-information-versions/_search";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NtQuotePartInformationVersionRepository ntQuotePartInformationVersionRepository;

    @Autowired
    private NtQuotePartInformationVersionMapper ntQuotePartInformationVersionMapper;

    @Autowired
    private NtQuotePartInformationVersionSearchRepository ntQuotePartInformationVersionSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNtQuotePartInformationVersionMockMvc;

    private NtQuotePartInformationVersion ntQuotePartInformationVersion;

    private NtQuotePartInformationVersion insertedNtQuotePartInformationVersion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NtQuotePartInformationVersion createEntity() {
        return new NtQuotePartInformationVersion()
            .srNo(DEFAULT_SR_NO)
            .uid(DEFAULT_UID)
            .materialDescription(DEFAULT_MATERIAL_DESCRIPTION)
            .partNumber(DEFAULT_PART_NUMBER)
            .cadFile(DEFAULT_CAD_FILE)
            .eau(DEFAULT_EAU)
            .partWeight(DEFAULT_PART_WEIGHT)
            .materialType(DEFAULT_MATERIAL_TYPE)
            .materialCost(DEFAULT_MATERIAL_COST)
            .extendedMaterialCostPer(DEFAULT_EXTENDED_MATERIAL_COST_PER)
            .externalMachineCostPer(DEFAULT_EXTERNAL_MACHINE_COST_PER)
            .purchaseComponentCost(DEFAULT_PURCHASE_COMPONENT_COST)
            .secondaryExternalOperationCost(DEFAULT_SECONDARY_EXTERNAL_OPERATION_COST)
            .overhead(DEFAULT_OVERHEAD)
            .packLogisticCostPer(DEFAULT_PACK_LOGISTIC_COST_PER)
            .machineSizeTons(DEFAULT_MACHINE_SIZE_TONS)
            .numberOfCavities(DEFAULT_NUMBER_OF_CAVITIES)
            .cycleTime(DEFAULT_CYCLE_TIME)
            .perUnit(DEFAULT_PER_UNIT)
            .totalPricePerChina(DEFAULT_TOTAL_PRICE_PER_CHINA)
            .totalPriceBudget(DEFAULT_TOTAL_PRICE_BUDGET)
            .grainBudget(DEFAULT_GRAIN_BUDGET)
            .dogatingFixtureBudget(DEFAULT_DOGATING_FIXTURE_BUDGET)
            .gaugeBudget(DEFAULT_GAUGE_BUDGET)
            .eoat(DEFAULT_EOAT)
            .chinaTariffBudget(DEFAULT_CHINA_TARIFF_BUDGET)
            .totalToolingBudget(DEFAULT_TOTAL_TOOLING_BUDGET)
            .leadTime(DEFAULT_LEAD_TIME)
            .toolingNotes(DEFAULT_TOOLING_NOTES)
            .partDescription(DEFAULT_PART_DESCRIPTION)
            .jobId(DEFAULT_JOB_ID)
            .moldId(DEFAULT_MOLD_ID)
            .quoteType(DEFAULT_QUOTE_TYPE)
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
    public static NtQuotePartInformationVersion createUpdatedEntity() {
        return new NtQuotePartInformationVersion()
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .materialDescription(UPDATED_MATERIAL_DESCRIPTION)
            .partNumber(UPDATED_PART_NUMBER)
            .cadFile(UPDATED_CAD_FILE)
            .eau(UPDATED_EAU)
            .partWeight(UPDATED_PART_WEIGHT)
            .materialType(UPDATED_MATERIAL_TYPE)
            .materialCost(UPDATED_MATERIAL_COST)
            .extendedMaterialCostPer(UPDATED_EXTENDED_MATERIAL_COST_PER)
            .externalMachineCostPer(UPDATED_EXTERNAL_MACHINE_COST_PER)
            .purchaseComponentCost(UPDATED_PURCHASE_COMPONENT_COST)
            .secondaryExternalOperationCost(UPDATED_SECONDARY_EXTERNAL_OPERATION_COST)
            .overhead(UPDATED_OVERHEAD)
            .packLogisticCostPer(UPDATED_PACK_LOGISTIC_COST_PER)
            .machineSizeTons(UPDATED_MACHINE_SIZE_TONS)
            .numberOfCavities(UPDATED_NUMBER_OF_CAVITIES)
            .cycleTime(UPDATED_CYCLE_TIME)
            .perUnit(UPDATED_PER_UNIT)
            .totalPricePerChina(UPDATED_TOTAL_PRICE_PER_CHINA)
            .totalPriceBudget(UPDATED_TOTAL_PRICE_BUDGET)
            .grainBudget(UPDATED_GRAIN_BUDGET)
            .dogatingFixtureBudget(UPDATED_DOGATING_FIXTURE_BUDGET)
            .gaugeBudget(UPDATED_GAUGE_BUDGET)
            .eoat(UPDATED_EOAT)
            .chinaTariffBudget(UPDATED_CHINA_TARIFF_BUDGET)
            .totalToolingBudget(UPDATED_TOTAL_TOOLING_BUDGET)
            .leadTime(UPDATED_LEAD_TIME)
            .toolingNotes(UPDATED_TOOLING_NOTES)
            .partDescription(UPDATED_PART_DESCRIPTION)
            .jobId(UPDATED_JOB_ID)
            .moldId(UPDATED_MOLD_ID)
            .quoteType(UPDATED_QUOTE_TYPE)
            .comments(UPDATED_COMMENTS)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);
    }

    @BeforeEach
    public void initTest() {
        ntQuotePartInformationVersion = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedNtQuotePartInformationVersion != null) {
            ntQuotePartInformationVersionRepository.delete(insertedNtQuotePartInformationVersion);
            ntQuotePartInformationVersionSearchRepository.delete(insertedNtQuotePartInformationVersion);
            insertedNtQuotePartInformationVersion = null;
        }
    }

    @Test
    @Transactional
    void createNtQuotePartInformationVersion() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuotePartInformationVersionSearchRepository.findAll());
        // Create the NtQuotePartInformationVersion
        NtQuotePartInformationVersionDTO ntQuotePartInformationVersionDTO = ntQuotePartInformationVersionMapper.toDto(
            ntQuotePartInformationVersion
        );
        var returnedNtQuotePartInformationVersionDTO = om.readValue(
            restNtQuotePartInformationVersionMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(ntQuotePartInformationVersionDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            NtQuotePartInformationVersionDTO.class
        );

        // Validate the NtQuotePartInformationVersion in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedNtQuotePartInformationVersion = ntQuotePartInformationVersionMapper.toEntity(returnedNtQuotePartInformationVersionDTO);
        assertNtQuotePartInformationVersionUpdatableFieldsEquals(
            returnedNtQuotePartInformationVersion,
            getPersistedNtQuotePartInformationVersion(returnedNtQuotePartInformationVersion)
        );

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuotePartInformationVersionSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });

        insertedNtQuotePartInformationVersion = returnedNtQuotePartInformationVersion;
    }

    @Test
    @Transactional
    void createNtQuotePartInformationVersionWithExistingId() throws Exception {
        // Create the NtQuotePartInformationVersion with an existing ID
        ntQuotePartInformationVersion.setId(1L);
        NtQuotePartInformationVersionDTO ntQuotePartInformationVersionDTO = ntQuotePartInformationVersionMapper.toDto(
            ntQuotePartInformationVersion
        );

        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuotePartInformationVersionSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restNtQuotePartInformationVersionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuotePartInformationVersionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuotePartInformationVersion in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuotePartInformationVersionSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkUidIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuotePartInformationVersionSearchRepository.findAll());
        // set the field null
        ntQuotePartInformationVersion.setUid(null);

        // Create the NtQuotePartInformationVersion, which fails.
        NtQuotePartInformationVersionDTO ntQuotePartInformationVersionDTO = ntQuotePartInformationVersionMapper.toDto(
            ntQuotePartInformationVersion
        );

        restNtQuotePartInformationVersionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuotePartInformationVersionDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuotePartInformationVersionSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersions() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList
        restNtQuotePartInformationVersionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuotePartInformationVersion.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].materialDescription").value(hasItem(DEFAULT_MATERIAL_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].partNumber").value(hasItem(DEFAULT_PART_NUMBER)))
            .andExpect(jsonPath("$.[*].cadFile").value(hasItem(DEFAULT_CAD_FILE)))
            .andExpect(jsonPath("$.[*].eau").value(hasItem(DEFAULT_EAU)))
            .andExpect(jsonPath("$.[*].partWeight").value(hasItem(DEFAULT_PART_WEIGHT)))
            .andExpect(jsonPath("$.[*].materialType").value(hasItem(DEFAULT_MATERIAL_TYPE)))
            .andExpect(jsonPath("$.[*].materialCost").value(hasItem(sameNumber(DEFAULT_MATERIAL_COST))))
            .andExpect(jsonPath("$.[*].extendedMaterialCostPer").value(hasItem(sameNumber(DEFAULT_EXTENDED_MATERIAL_COST_PER))))
            .andExpect(jsonPath("$.[*].externalMachineCostPer").value(hasItem(sameNumber(DEFAULT_EXTERNAL_MACHINE_COST_PER))))
            .andExpect(jsonPath("$.[*].purchaseComponentCost").value(hasItem(sameNumber(DEFAULT_PURCHASE_COMPONENT_COST))))
            .andExpect(
                jsonPath("$.[*].secondaryExternalOperationCost").value(hasItem(sameNumber(DEFAULT_SECONDARY_EXTERNAL_OPERATION_COST)))
            )
            .andExpect(jsonPath("$.[*].overhead").value(hasItem(sameNumber(DEFAULT_OVERHEAD))))
            .andExpect(jsonPath("$.[*].packLogisticCostPer").value(hasItem(sameNumber(DEFAULT_PACK_LOGISTIC_COST_PER))))
            .andExpect(jsonPath("$.[*].machineSizeTons").value(hasItem(DEFAULT_MACHINE_SIZE_TONS)))
            .andExpect(jsonPath("$.[*].numberOfCavities").value(hasItem(DEFAULT_NUMBER_OF_CAVITIES)))
            .andExpect(jsonPath("$.[*].cycleTime").value(hasItem(DEFAULT_CYCLE_TIME)))
            .andExpect(jsonPath("$.[*].perUnit").value(hasItem(sameNumber(DEFAULT_PER_UNIT))))
            .andExpect(jsonPath("$.[*].totalPricePerChina").value(hasItem(sameNumber(DEFAULT_TOTAL_PRICE_PER_CHINA))))
            .andExpect(jsonPath("$.[*].totalPriceBudget").value(hasItem(sameNumber(DEFAULT_TOTAL_PRICE_BUDGET))))
            .andExpect(jsonPath("$.[*].grainBudget").value(hasItem(sameNumber(DEFAULT_GRAIN_BUDGET))))
            .andExpect(jsonPath("$.[*].dogatingFixtureBudget").value(hasItem(sameNumber(DEFAULT_DOGATING_FIXTURE_BUDGET))))
            .andExpect(jsonPath("$.[*].gaugeBudget").value(hasItem(sameNumber(DEFAULT_GAUGE_BUDGET))))
            .andExpect(jsonPath("$.[*].eoat").value(hasItem(sameNumber(DEFAULT_EOAT))))
            .andExpect(jsonPath("$.[*].chinaTariffBudget").value(hasItem(sameNumber(DEFAULT_CHINA_TARIFF_BUDGET))))
            .andExpect(jsonPath("$.[*].totalToolingBudget").value(hasItem(sameNumber(DEFAULT_TOTAL_TOOLING_BUDGET))))
            .andExpect(jsonPath("$.[*].leadTime").value(hasItem(DEFAULT_LEAD_TIME)))
            .andExpect(jsonPath("$.[*].toolingNotes").value(hasItem(DEFAULT_TOOLING_NOTES)))
            .andExpect(jsonPath("$.[*].partDescription").value(hasItem(DEFAULT_PART_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].jobId").value(hasItem(DEFAULT_JOB_ID)))
            .andExpect(jsonPath("$.[*].moldId").value(hasItem(DEFAULT_MOLD_ID)))
            .andExpect(jsonPath("$.[*].quoteType").value(hasItem(DEFAULT_QUOTE_TYPE)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getNtQuotePartInformationVersion() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get the ntQuotePartInformationVersion
        restNtQuotePartInformationVersionMockMvc
            .perform(get(ENTITY_API_URL_ID, ntQuotePartInformationVersion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ntQuotePartInformationVersion.getId().intValue()))
            .andExpect(jsonPath("$.srNo").value(DEFAULT_SR_NO))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.materialDescription").value(DEFAULT_MATERIAL_DESCRIPTION))
            .andExpect(jsonPath("$.partNumber").value(DEFAULT_PART_NUMBER))
            .andExpect(jsonPath("$.cadFile").value(DEFAULT_CAD_FILE))
            .andExpect(jsonPath("$.eau").value(DEFAULT_EAU))
            .andExpect(jsonPath("$.partWeight").value(DEFAULT_PART_WEIGHT))
            .andExpect(jsonPath("$.materialType").value(DEFAULT_MATERIAL_TYPE))
            .andExpect(jsonPath("$.materialCost").value(sameNumber(DEFAULT_MATERIAL_COST)))
            .andExpect(jsonPath("$.extendedMaterialCostPer").value(sameNumber(DEFAULT_EXTENDED_MATERIAL_COST_PER)))
            .andExpect(jsonPath("$.externalMachineCostPer").value(sameNumber(DEFAULT_EXTERNAL_MACHINE_COST_PER)))
            .andExpect(jsonPath("$.purchaseComponentCost").value(sameNumber(DEFAULT_PURCHASE_COMPONENT_COST)))
            .andExpect(jsonPath("$.secondaryExternalOperationCost").value(sameNumber(DEFAULT_SECONDARY_EXTERNAL_OPERATION_COST)))
            .andExpect(jsonPath("$.overhead").value(sameNumber(DEFAULT_OVERHEAD)))
            .andExpect(jsonPath("$.packLogisticCostPer").value(sameNumber(DEFAULT_PACK_LOGISTIC_COST_PER)))
            .andExpect(jsonPath("$.machineSizeTons").value(DEFAULT_MACHINE_SIZE_TONS))
            .andExpect(jsonPath("$.numberOfCavities").value(DEFAULT_NUMBER_OF_CAVITIES))
            .andExpect(jsonPath("$.cycleTime").value(DEFAULT_CYCLE_TIME))
            .andExpect(jsonPath("$.perUnit").value(sameNumber(DEFAULT_PER_UNIT)))
            .andExpect(jsonPath("$.totalPricePerChina").value(sameNumber(DEFAULT_TOTAL_PRICE_PER_CHINA)))
            .andExpect(jsonPath("$.totalPriceBudget").value(sameNumber(DEFAULT_TOTAL_PRICE_BUDGET)))
            .andExpect(jsonPath("$.grainBudget").value(sameNumber(DEFAULT_GRAIN_BUDGET)))
            .andExpect(jsonPath("$.dogatingFixtureBudget").value(sameNumber(DEFAULT_DOGATING_FIXTURE_BUDGET)))
            .andExpect(jsonPath("$.gaugeBudget").value(sameNumber(DEFAULT_GAUGE_BUDGET)))
            .andExpect(jsonPath("$.eoat").value(sameNumber(DEFAULT_EOAT)))
            .andExpect(jsonPath("$.chinaTariffBudget").value(sameNumber(DEFAULT_CHINA_TARIFF_BUDGET)))
            .andExpect(jsonPath("$.totalToolingBudget").value(sameNumber(DEFAULT_TOTAL_TOOLING_BUDGET)))
            .andExpect(jsonPath("$.leadTime").value(DEFAULT_LEAD_TIME))
            .andExpect(jsonPath("$.toolingNotes").value(DEFAULT_TOOLING_NOTES))
            .andExpect(jsonPath("$.partDescription").value(DEFAULT_PART_DESCRIPTION))
            .andExpect(jsonPath("$.jobId").value(DEFAULT_JOB_ID))
            .andExpect(jsonPath("$.moldId").value(DEFAULT_MOLD_ID))
            .andExpect(jsonPath("$.quoteType").value(DEFAULT_QUOTE_TYPE))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNtQuotePartInformationVersionsByIdFiltering() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        Long id = ntQuotePartInformationVersion.getId();

        defaultNtQuotePartInformationVersionFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultNtQuotePartInformationVersionFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultNtQuotePartInformationVersionFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsBySrNoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where srNo equals to
        defaultNtQuotePartInformationVersionFiltering("srNo.equals=" + DEFAULT_SR_NO, "srNo.equals=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsBySrNoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where srNo in
        defaultNtQuotePartInformationVersionFiltering("srNo.in=" + DEFAULT_SR_NO + "," + UPDATED_SR_NO, "srNo.in=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsBySrNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where srNo is not null
        defaultNtQuotePartInformationVersionFiltering("srNo.specified=true", "srNo.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsBySrNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where srNo is greater than or equal to
        defaultNtQuotePartInformationVersionFiltering(
            "srNo.greaterThanOrEqual=" + DEFAULT_SR_NO,
            "srNo.greaterThanOrEqual=" + UPDATED_SR_NO
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsBySrNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where srNo is less than or equal to
        defaultNtQuotePartInformationVersionFiltering("srNo.lessThanOrEqual=" + DEFAULT_SR_NO, "srNo.lessThanOrEqual=" + SMALLER_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsBySrNoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where srNo is less than
        defaultNtQuotePartInformationVersionFiltering("srNo.lessThan=" + UPDATED_SR_NO, "srNo.lessThan=" + DEFAULT_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsBySrNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where srNo is greater than
        defaultNtQuotePartInformationVersionFiltering("srNo.greaterThan=" + SMALLER_SR_NO, "srNo.greaterThan=" + DEFAULT_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where uid equals to
        defaultNtQuotePartInformationVersionFiltering("uid.equals=" + DEFAULT_UID, "uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where uid in
        defaultNtQuotePartInformationVersionFiltering("uid.in=" + DEFAULT_UID + "," + UPDATED_UID, "uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where uid is not null
        defaultNtQuotePartInformationVersionFiltering("uid.specified=true", "uid.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByMaterialDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where materialDescription equals to
        defaultNtQuotePartInformationVersionFiltering(
            "materialDescription.equals=" + DEFAULT_MATERIAL_DESCRIPTION,
            "materialDescription.equals=" + UPDATED_MATERIAL_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByMaterialDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where materialDescription in
        defaultNtQuotePartInformationVersionFiltering(
            "materialDescription.in=" + DEFAULT_MATERIAL_DESCRIPTION + "," + UPDATED_MATERIAL_DESCRIPTION,
            "materialDescription.in=" + UPDATED_MATERIAL_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByMaterialDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where materialDescription is not null
        defaultNtQuotePartInformationVersionFiltering("materialDescription.specified=true", "materialDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByMaterialDescriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where materialDescription contains
        defaultNtQuotePartInformationVersionFiltering(
            "materialDescription.contains=" + DEFAULT_MATERIAL_DESCRIPTION,
            "materialDescription.contains=" + UPDATED_MATERIAL_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByMaterialDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where materialDescription does not contain
        defaultNtQuotePartInformationVersionFiltering(
            "materialDescription.doesNotContain=" + UPDATED_MATERIAL_DESCRIPTION,
            "materialDescription.doesNotContain=" + DEFAULT_MATERIAL_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByPartNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where partNumber equals to
        defaultNtQuotePartInformationVersionFiltering(
            "partNumber.equals=" + DEFAULT_PART_NUMBER,
            "partNumber.equals=" + UPDATED_PART_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByPartNumberIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where partNumber in
        defaultNtQuotePartInformationVersionFiltering(
            "partNumber.in=" + DEFAULT_PART_NUMBER + "," + UPDATED_PART_NUMBER,
            "partNumber.in=" + UPDATED_PART_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByPartNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where partNumber is not null
        defaultNtQuotePartInformationVersionFiltering("partNumber.specified=true", "partNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByPartNumberContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where partNumber contains
        defaultNtQuotePartInformationVersionFiltering(
            "partNumber.contains=" + DEFAULT_PART_NUMBER,
            "partNumber.contains=" + UPDATED_PART_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByPartNumberNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where partNumber does not contain
        defaultNtQuotePartInformationVersionFiltering(
            "partNumber.doesNotContain=" + UPDATED_PART_NUMBER,
            "partNumber.doesNotContain=" + DEFAULT_PART_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByCadFileIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where cadFile equals to
        defaultNtQuotePartInformationVersionFiltering("cadFile.equals=" + DEFAULT_CAD_FILE, "cadFile.equals=" + UPDATED_CAD_FILE);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByCadFileIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where cadFile in
        defaultNtQuotePartInformationVersionFiltering(
            "cadFile.in=" + DEFAULT_CAD_FILE + "," + UPDATED_CAD_FILE,
            "cadFile.in=" + UPDATED_CAD_FILE
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByCadFileIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where cadFile is not null
        defaultNtQuotePartInformationVersionFiltering("cadFile.specified=true", "cadFile.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByCadFileContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where cadFile contains
        defaultNtQuotePartInformationVersionFiltering("cadFile.contains=" + DEFAULT_CAD_FILE, "cadFile.contains=" + UPDATED_CAD_FILE);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByCadFileNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where cadFile does not contain
        defaultNtQuotePartInformationVersionFiltering(
            "cadFile.doesNotContain=" + UPDATED_CAD_FILE,
            "cadFile.doesNotContain=" + DEFAULT_CAD_FILE
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByEauIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where eau equals to
        defaultNtQuotePartInformationVersionFiltering("eau.equals=" + DEFAULT_EAU, "eau.equals=" + UPDATED_EAU);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByEauIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where eau in
        defaultNtQuotePartInformationVersionFiltering("eau.in=" + DEFAULT_EAU + "," + UPDATED_EAU, "eau.in=" + UPDATED_EAU);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByEauIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where eau is not null
        defaultNtQuotePartInformationVersionFiltering("eau.specified=true", "eau.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByEauIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where eau is greater than or equal to
        defaultNtQuotePartInformationVersionFiltering("eau.greaterThanOrEqual=" + DEFAULT_EAU, "eau.greaterThanOrEqual=" + UPDATED_EAU);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByEauIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where eau is less than or equal to
        defaultNtQuotePartInformationVersionFiltering("eau.lessThanOrEqual=" + DEFAULT_EAU, "eau.lessThanOrEqual=" + SMALLER_EAU);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByEauIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where eau is less than
        defaultNtQuotePartInformationVersionFiltering("eau.lessThan=" + UPDATED_EAU, "eau.lessThan=" + DEFAULT_EAU);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByEauIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where eau is greater than
        defaultNtQuotePartInformationVersionFiltering("eau.greaterThan=" + SMALLER_EAU, "eau.greaterThan=" + DEFAULT_EAU);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByPartWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where partWeight equals to
        defaultNtQuotePartInformationVersionFiltering(
            "partWeight.equals=" + DEFAULT_PART_WEIGHT,
            "partWeight.equals=" + UPDATED_PART_WEIGHT
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByPartWeightIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where partWeight in
        defaultNtQuotePartInformationVersionFiltering(
            "partWeight.in=" + DEFAULT_PART_WEIGHT + "," + UPDATED_PART_WEIGHT,
            "partWeight.in=" + UPDATED_PART_WEIGHT
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByPartWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where partWeight is not null
        defaultNtQuotePartInformationVersionFiltering("partWeight.specified=true", "partWeight.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByPartWeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where partWeight is greater than or equal to
        defaultNtQuotePartInformationVersionFiltering(
            "partWeight.greaterThanOrEqual=" + DEFAULT_PART_WEIGHT,
            "partWeight.greaterThanOrEqual=" + UPDATED_PART_WEIGHT
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByPartWeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where partWeight is less than or equal to
        defaultNtQuotePartInformationVersionFiltering(
            "partWeight.lessThanOrEqual=" + DEFAULT_PART_WEIGHT,
            "partWeight.lessThanOrEqual=" + SMALLER_PART_WEIGHT
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByPartWeightIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where partWeight is less than
        defaultNtQuotePartInformationVersionFiltering(
            "partWeight.lessThan=" + UPDATED_PART_WEIGHT,
            "partWeight.lessThan=" + DEFAULT_PART_WEIGHT
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByPartWeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where partWeight is greater than
        defaultNtQuotePartInformationVersionFiltering(
            "partWeight.greaterThan=" + SMALLER_PART_WEIGHT,
            "partWeight.greaterThan=" + DEFAULT_PART_WEIGHT
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByMaterialTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where materialType equals to
        defaultNtQuotePartInformationVersionFiltering(
            "materialType.equals=" + DEFAULT_MATERIAL_TYPE,
            "materialType.equals=" + UPDATED_MATERIAL_TYPE
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByMaterialTypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where materialType in
        defaultNtQuotePartInformationVersionFiltering(
            "materialType.in=" + DEFAULT_MATERIAL_TYPE + "," + UPDATED_MATERIAL_TYPE,
            "materialType.in=" + UPDATED_MATERIAL_TYPE
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByMaterialTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where materialType is not null
        defaultNtQuotePartInformationVersionFiltering("materialType.specified=true", "materialType.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByMaterialTypeContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where materialType contains
        defaultNtQuotePartInformationVersionFiltering(
            "materialType.contains=" + DEFAULT_MATERIAL_TYPE,
            "materialType.contains=" + UPDATED_MATERIAL_TYPE
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByMaterialTypeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where materialType does not contain
        defaultNtQuotePartInformationVersionFiltering(
            "materialType.doesNotContain=" + UPDATED_MATERIAL_TYPE,
            "materialType.doesNotContain=" + DEFAULT_MATERIAL_TYPE
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByMaterialCostIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where materialCost equals to
        defaultNtQuotePartInformationVersionFiltering(
            "materialCost.equals=" + DEFAULT_MATERIAL_COST,
            "materialCost.equals=" + UPDATED_MATERIAL_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByMaterialCostIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where materialCost in
        defaultNtQuotePartInformationVersionFiltering(
            "materialCost.in=" + DEFAULT_MATERIAL_COST + "," + UPDATED_MATERIAL_COST,
            "materialCost.in=" + UPDATED_MATERIAL_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByMaterialCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where materialCost is not null
        defaultNtQuotePartInformationVersionFiltering("materialCost.specified=true", "materialCost.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByMaterialCostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where materialCost is greater than or equal to
        defaultNtQuotePartInformationVersionFiltering(
            "materialCost.greaterThanOrEqual=" + DEFAULT_MATERIAL_COST,
            "materialCost.greaterThanOrEqual=" + UPDATED_MATERIAL_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByMaterialCostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where materialCost is less than or equal to
        defaultNtQuotePartInformationVersionFiltering(
            "materialCost.lessThanOrEqual=" + DEFAULT_MATERIAL_COST,
            "materialCost.lessThanOrEqual=" + SMALLER_MATERIAL_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByMaterialCostIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where materialCost is less than
        defaultNtQuotePartInformationVersionFiltering(
            "materialCost.lessThan=" + UPDATED_MATERIAL_COST,
            "materialCost.lessThan=" + DEFAULT_MATERIAL_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByMaterialCostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where materialCost is greater than
        defaultNtQuotePartInformationVersionFiltering(
            "materialCost.greaterThan=" + SMALLER_MATERIAL_COST,
            "materialCost.greaterThan=" + DEFAULT_MATERIAL_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByExtendedMaterialCostPerIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where extendedMaterialCostPer equals to
        defaultNtQuotePartInformationVersionFiltering(
            "extendedMaterialCostPer.equals=" + DEFAULT_EXTENDED_MATERIAL_COST_PER,
            "extendedMaterialCostPer.equals=" + UPDATED_EXTENDED_MATERIAL_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByExtendedMaterialCostPerIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where extendedMaterialCostPer in
        defaultNtQuotePartInformationVersionFiltering(
            "extendedMaterialCostPer.in=" + DEFAULT_EXTENDED_MATERIAL_COST_PER + "," + UPDATED_EXTENDED_MATERIAL_COST_PER,
            "extendedMaterialCostPer.in=" + UPDATED_EXTENDED_MATERIAL_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByExtendedMaterialCostPerIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where extendedMaterialCostPer is not null
        defaultNtQuotePartInformationVersionFiltering("extendedMaterialCostPer.specified=true", "extendedMaterialCostPer.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByExtendedMaterialCostPerIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where extendedMaterialCostPer is greater than or equal to
        defaultNtQuotePartInformationVersionFiltering(
            "extendedMaterialCostPer.greaterThanOrEqual=" + DEFAULT_EXTENDED_MATERIAL_COST_PER,
            "extendedMaterialCostPer.greaterThanOrEqual=" + UPDATED_EXTENDED_MATERIAL_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByExtendedMaterialCostPerIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where extendedMaterialCostPer is less than or equal to
        defaultNtQuotePartInformationVersionFiltering(
            "extendedMaterialCostPer.lessThanOrEqual=" + DEFAULT_EXTENDED_MATERIAL_COST_PER,
            "extendedMaterialCostPer.lessThanOrEqual=" + SMALLER_EXTENDED_MATERIAL_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByExtendedMaterialCostPerIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where extendedMaterialCostPer is less than
        defaultNtQuotePartInformationVersionFiltering(
            "extendedMaterialCostPer.lessThan=" + UPDATED_EXTENDED_MATERIAL_COST_PER,
            "extendedMaterialCostPer.lessThan=" + DEFAULT_EXTENDED_MATERIAL_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByExtendedMaterialCostPerIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where extendedMaterialCostPer is greater than
        defaultNtQuotePartInformationVersionFiltering(
            "extendedMaterialCostPer.greaterThan=" + SMALLER_EXTENDED_MATERIAL_COST_PER,
            "extendedMaterialCostPer.greaterThan=" + DEFAULT_EXTENDED_MATERIAL_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByExternalMachineCostPerIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where externalMachineCostPer equals to
        defaultNtQuotePartInformationVersionFiltering(
            "externalMachineCostPer.equals=" + DEFAULT_EXTERNAL_MACHINE_COST_PER,
            "externalMachineCostPer.equals=" + UPDATED_EXTERNAL_MACHINE_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByExternalMachineCostPerIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where externalMachineCostPer in
        defaultNtQuotePartInformationVersionFiltering(
            "externalMachineCostPer.in=" + DEFAULT_EXTERNAL_MACHINE_COST_PER + "," + UPDATED_EXTERNAL_MACHINE_COST_PER,
            "externalMachineCostPer.in=" + UPDATED_EXTERNAL_MACHINE_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByExternalMachineCostPerIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where externalMachineCostPer is not null
        defaultNtQuotePartInformationVersionFiltering("externalMachineCostPer.specified=true", "externalMachineCostPer.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByExternalMachineCostPerIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where externalMachineCostPer is greater than or equal to
        defaultNtQuotePartInformationVersionFiltering(
            "externalMachineCostPer.greaterThanOrEqual=" + DEFAULT_EXTERNAL_MACHINE_COST_PER,
            "externalMachineCostPer.greaterThanOrEqual=" + UPDATED_EXTERNAL_MACHINE_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByExternalMachineCostPerIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where externalMachineCostPer is less than or equal to
        defaultNtQuotePartInformationVersionFiltering(
            "externalMachineCostPer.lessThanOrEqual=" + DEFAULT_EXTERNAL_MACHINE_COST_PER,
            "externalMachineCostPer.lessThanOrEqual=" + SMALLER_EXTERNAL_MACHINE_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByExternalMachineCostPerIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where externalMachineCostPer is less than
        defaultNtQuotePartInformationVersionFiltering(
            "externalMachineCostPer.lessThan=" + UPDATED_EXTERNAL_MACHINE_COST_PER,
            "externalMachineCostPer.lessThan=" + DEFAULT_EXTERNAL_MACHINE_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByExternalMachineCostPerIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where externalMachineCostPer is greater than
        defaultNtQuotePartInformationVersionFiltering(
            "externalMachineCostPer.greaterThan=" + SMALLER_EXTERNAL_MACHINE_COST_PER,
            "externalMachineCostPer.greaterThan=" + DEFAULT_EXTERNAL_MACHINE_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByPurchaseComponentCostIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where purchaseComponentCost equals to
        defaultNtQuotePartInformationVersionFiltering(
            "purchaseComponentCost.equals=" + DEFAULT_PURCHASE_COMPONENT_COST,
            "purchaseComponentCost.equals=" + UPDATED_PURCHASE_COMPONENT_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByPurchaseComponentCostIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where purchaseComponentCost in
        defaultNtQuotePartInformationVersionFiltering(
            "purchaseComponentCost.in=" + DEFAULT_PURCHASE_COMPONENT_COST + "," + UPDATED_PURCHASE_COMPONENT_COST,
            "purchaseComponentCost.in=" + UPDATED_PURCHASE_COMPONENT_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByPurchaseComponentCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where purchaseComponentCost is not null
        defaultNtQuotePartInformationVersionFiltering("purchaseComponentCost.specified=true", "purchaseComponentCost.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByPurchaseComponentCostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where purchaseComponentCost is greater than or equal to
        defaultNtQuotePartInformationVersionFiltering(
            "purchaseComponentCost.greaterThanOrEqual=" + DEFAULT_PURCHASE_COMPONENT_COST,
            "purchaseComponentCost.greaterThanOrEqual=" + UPDATED_PURCHASE_COMPONENT_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByPurchaseComponentCostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where purchaseComponentCost is less than or equal to
        defaultNtQuotePartInformationVersionFiltering(
            "purchaseComponentCost.lessThanOrEqual=" + DEFAULT_PURCHASE_COMPONENT_COST,
            "purchaseComponentCost.lessThanOrEqual=" + SMALLER_PURCHASE_COMPONENT_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByPurchaseComponentCostIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where purchaseComponentCost is less than
        defaultNtQuotePartInformationVersionFiltering(
            "purchaseComponentCost.lessThan=" + UPDATED_PURCHASE_COMPONENT_COST,
            "purchaseComponentCost.lessThan=" + DEFAULT_PURCHASE_COMPONENT_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByPurchaseComponentCostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where purchaseComponentCost is greater than
        defaultNtQuotePartInformationVersionFiltering(
            "purchaseComponentCost.greaterThan=" + SMALLER_PURCHASE_COMPONENT_COST,
            "purchaseComponentCost.greaterThan=" + DEFAULT_PURCHASE_COMPONENT_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsBySecondaryExternalOperationCostIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where secondaryExternalOperationCost equals to
        defaultNtQuotePartInformationVersionFiltering(
            "secondaryExternalOperationCost.equals=" + DEFAULT_SECONDARY_EXTERNAL_OPERATION_COST,
            "secondaryExternalOperationCost.equals=" + UPDATED_SECONDARY_EXTERNAL_OPERATION_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsBySecondaryExternalOperationCostIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where secondaryExternalOperationCost in
        defaultNtQuotePartInformationVersionFiltering(
            "secondaryExternalOperationCost.in=" +
            DEFAULT_SECONDARY_EXTERNAL_OPERATION_COST +
            "," +
            UPDATED_SECONDARY_EXTERNAL_OPERATION_COST,
            "secondaryExternalOperationCost.in=" + UPDATED_SECONDARY_EXTERNAL_OPERATION_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsBySecondaryExternalOperationCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where secondaryExternalOperationCost is not null
        defaultNtQuotePartInformationVersionFiltering(
            "secondaryExternalOperationCost.specified=true",
            "secondaryExternalOperationCost.specified=false"
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsBySecondaryExternalOperationCostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where secondaryExternalOperationCost is greater than or equal to
        defaultNtQuotePartInformationVersionFiltering(
            "secondaryExternalOperationCost.greaterThanOrEqual=" + DEFAULT_SECONDARY_EXTERNAL_OPERATION_COST,
            "secondaryExternalOperationCost.greaterThanOrEqual=" + UPDATED_SECONDARY_EXTERNAL_OPERATION_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsBySecondaryExternalOperationCostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where secondaryExternalOperationCost is less than or equal to
        defaultNtQuotePartInformationVersionFiltering(
            "secondaryExternalOperationCost.lessThanOrEqual=" + DEFAULT_SECONDARY_EXTERNAL_OPERATION_COST,
            "secondaryExternalOperationCost.lessThanOrEqual=" + SMALLER_SECONDARY_EXTERNAL_OPERATION_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsBySecondaryExternalOperationCostIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where secondaryExternalOperationCost is less than
        defaultNtQuotePartInformationVersionFiltering(
            "secondaryExternalOperationCost.lessThan=" + UPDATED_SECONDARY_EXTERNAL_OPERATION_COST,
            "secondaryExternalOperationCost.lessThan=" + DEFAULT_SECONDARY_EXTERNAL_OPERATION_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsBySecondaryExternalOperationCostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where secondaryExternalOperationCost is greater than
        defaultNtQuotePartInformationVersionFiltering(
            "secondaryExternalOperationCost.greaterThan=" + SMALLER_SECONDARY_EXTERNAL_OPERATION_COST,
            "secondaryExternalOperationCost.greaterThan=" + DEFAULT_SECONDARY_EXTERNAL_OPERATION_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByOverheadIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where overhead equals to
        defaultNtQuotePartInformationVersionFiltering("overhead.equals=" + DEFAULT_OVERHEAD, "overhead.equals=" + UPDATED_OVERHEAD);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByOverheadIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where overhead in
        defaultNtQuotePartInformationVersionFiltering(
            "overhead.in=" + DEFAULT_OVERHEAD + "," + UPDATED_OVERHEAD,
            "overhead.in=" + UPDATED_OVERHEAD
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByOverheadIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where overhead is not null
        defaultNtQuotePartInformationVersionFiltering("overhead.specified=true", "overhead.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByOverheadIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where overhead is greater than or equal to
        defaultNtQuotePartInformationVersionFiltering(
            "overhead.greaterThanOrEqual=" + DEFAULT_OVERHEAD,
            "overhead.greaterThanOrEqual=" + UPDATED_OVERHEAD
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByOverheadIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where overhead is less than or equal to
        defaultNtQuotePartInformationVersionFiltering(
            "overhead.lessThanOrEqual=" + DEFAULT_OVERHEAD,
            "overhead.lessThanOrEqual=" + SMALLER_OVERHEAD
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByOverheadIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where overhead is less than
        defaultNtQuotePartInformationVersionFiltering("overhead.lessThan=" + UPDATED_OVERHEAD, "overhead.lessThan=" + DEFAULT_OVERHEAD);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByOverheadIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where overhead is greater than
        defaultNtQuotePartInformationVersionFiltering(
            "overhead.greaterThan=" + SMALLER_OVERHEAD,
            "overhead.greaterThan=" + DEFAULT_OVERHEAD
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByPackLogisticCostPerIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where packLogisticCostPer equals to
        defaultNtQuotePartInformationVersionFiltering(
            "packLogisticCostPer.equals=" + DEFAULT_PACK_LOGISTIC_COST_PER,
            "packLogisticCostPer.equals=" + UPDATED_PACK_LOGISTIC_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByPackLogisticCostPerIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where packLogisticCostPer in
        defaultNtQuotePartInformationVersionFiltering(
            "packLogisticCostPer.in=" + DEFAULT_PACK_LOGISTIC_COST_PER + "," + UPDATED_PACK_LOGISTIC_COST_PER,
            "packLogisticCostPer.in=" + UPDATED_PACK_LOGISTIC_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByPackLogisticCostPerIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where packLogisticCostPer is not null
        defaultNtQuotePartInformationVersionFiltering("packLogisticCostPer.specified=true", "packLogisticCostPer.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByPackLogisticCostPerIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where packLogisticCostPer is greater than or equal to
        defaultNtQuotePartInformationVersionFiltering(
            "packLogisticCostPer.greaterThanOrEqual=" + DEFAULT_PACK_LOGISTIC_COST_PER,
            "packLogisticCostPer.greaterThanOrEqual=" + UPDATED_PACK_LOGISTIC_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByPackLogisticCostPerIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where packLogisticCostPer is less than or equal to
        defaultNtQuotePartInformationVersionFiltering(
            "packLogisticCostPer.lessThanOrEqual=" + DEFAULT_PACK_LOGISTIC_COST_PER,
            "packLogisticCostPer.lessThanOrEqual=" + SMALLER_PACK_LOGISTIC_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByPackLogisticCostPerIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where packLogisticCostPer is less than
        defaultNtQuotePartInformationVersionFiltering(
            "packLogisticCostPer.lessThan=" + UPDATED_PACK_LOGISTIC_COST_PER,
            "packLogisticCostPer.lessThan=" + DEFAULT_PACK_LOGISTIC_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByPackLogisticCostPerIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where packLogisticCostPer is greater than
        defaultNtQuotePartInformationVersionFiltering(
            "packLogisticCostPer.greaterThan=" + SMALLER_PACK_LOGISTIC_COST_PER,
            "packLogisticCostPer.greaterThan=" + DEFAULT_PACK_LOGISTIC_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByMachineSizeTonsIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where machineSizeTons equals to
        defaultNtQuotePartInformationVersionFiltering(
            "machineSizeTons.equals=" + DEFAULT_MACHINE_SIZE_TONS,
            "machineSizeTons.equals=" + UPDATED_MACHINE_SIZE_TONS
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByMachineSizeTonsIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where machineSizeTons in
        defaultNtQuotePartInformationVersionFiltering(
            "machineSizeTons.in=" + DEFAULT_MACHINE_SIZE_TONS + "," + UPDATED_MACHINE_SIZE_TONS,
            "machineSizeTons.in=" + UPDATED_MACHINE_SIZE_TONS
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByMachineSizeTonsIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where machineSizeTons is not null
        defaultNtQuotePartInformationVersionFiltering("machineSizeTons.specified=true", "machineSizeTons.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByMachineSizeTonsContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where machineSizeTons contains
        defaultNtQuotePartInformationVersionFiltering(
            "machineSizeTons.contains=" + DEFAULT_MACHINE_SIZE_TONS,
            "machineSizeTons.contains=" + UPDATED_MACHINE_SIZE_TONS
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByMachineSizeTonsNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where machineSizeTons does not contain
        defaultNtQuotePartInformationVersionFiltering(
            "machineSizeTons.doesNotContain=" + UPDATED_MACHINE_SIZE_TONS,
            "machineSizeTons.doesNotContain=" + DEFAULT_MACHINE_SIZE_TONS
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByNumberOfCavitiesIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where numberOfCavities equals to
        defaultNtQuotePartInformationVersionFiltering(
            "numberOfCavities.equals=" + DEFAULT_NUMBER_OF_CAVITIES,
            "numberOfCavities.equals=" + UPDATED_NUMBER_OF_CAVITIES
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByNumberOfCavitiesIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where numberOfCavities in
        defaultNtQuotePartInformationVersionFiltering(
            "numberOfCavities.in=" + DEFAULT_NUMBER_OF_CAVITIES + "," + UPDATED_NUMBER_OF_CAVITIES,
            "numberOfCavities.in=" + UPDATED_NUMBER_OF_CAVITIES
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByNumberOfCavitiesIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where numberOfCavities is not null
        defaultNtQuotePartInformationVersionFiltering("numberOfCavities.specified=true", "numberOfCavities.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByNumberOfCavitiesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where numberOfCavities is greater than or equal to
        defaultNtQuotePartInformationVersionFiltering(
            "numberOfCavities.greaterThanOrEqual=" + DEFAULT_NUMBER_OF_CAVITIES,
            "numberOfCavities.greaterThanOrEqual=" + UPDATED_NUMBER_OF_CAVITIES
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByNumberOfCavitiesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where numberOfCavities is less than or equal to
        defaultNtQuotePartInformationVersionFiltering(
            "numberOfCavities.lessThanOrEqual=" + DEFAULT_NUMBER_OF_CAVITIES,
            "numberOfCavities.lessThanOrEqual=" + SMALLER_NUMBER_OF_CAVITIES
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByNumberOfCavitiesIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where numberOfCavities is less than
        defaultNtQuotePartInformationVersionFiltering(
            "numberOfCavities.lessThan=" + UPDATED_NUMBER_OF_CAVITIES,
            "numberOfCavities.lessThan=" + DEFAULT_NUMBER_OF_CAVITIES
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByNumberOfCavitiesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where numberOfCavities is greater than
        defaultNtQuotePartInformationVersionFiltering(
            "numberOfCavities.greaterThan=" + SMALLER_NUMBER_OF_CAVITIES,
            "numberOfCavities.greaterThan=" + DEFAULT_NUMBER_OF_CAVITIES
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByCycleTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where cycleTime equals to
        defaultNtQuotePartInformationVersionFiltering("cycleTime.equals=" + DEFAULT_CYCLE_TIME, "cycleTime.equals=" + UPDATED_CYCLE_TIME);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByCycleTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where cycleTime in
        defaultNtQuotePartInformationVersionFiltering(
            "cycleTime.in=" + DEFAULT_CYCLE_TIME + "," + UPDATED_CYCLE_TIME,
            "cycleTime.in=" + UPDATED_CYCLE_TIME
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByCycleTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where cycleTime is not null
        defaultNtQuotePartInformationVersionFiltering("cycleTime.specified=true", "cycleTime.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByCycleTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where cycleTime is greater than or equal to
        defaultNtQuotePartInformationVersionFiltering(
            "cycleTime.greaterThanOrEqual=" + DEFAULT_CYCLE_TIME,
            "cycleTime.greaterThanOrEqual=" + UPDATED_CYCLE_TIME
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByCycleTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where cycleTime is less than or equal to
        defaultNtQuotePartInformationVersionFiltering(
            "cycleTime.lessThanOrEqual=" + DEFAULT_CYCLE_TIME,
            "cycleTime.lessThanOrEqual=" + SMALLER_CYCLE_TIME
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByCycleTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where cycleTime is less than
        defaultNtQuotePartInformationVersionFiltering(
            "cycleTime.lessThan=" + UPDATED_CYCLE_TIME,
            "cycleTime.lessThan=" + DEFAULT_CYCLE_TIME
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByCycleTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where cycleTime is greater than
        defaultNtQuotePartInformationVersionFiltering(
            "cycleTime.greaterThan=" + SMALLER_CYCLE_TIME,
            "cycleTime.greaterThan=" + DEFAULT_CYCLE_TIME
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByPerUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where perUnit equals to
        defaultNtQuotePartInformationVersionFiltering("perUnit.equals=" + DEFAULT_PER_UNIT, "perUnit.equals=" + UPDATED_PER_UNIT);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByPerUnitIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where perUnit in
        defaultNtQuotePartInformationVersionFiltering(
            "perUnit.in=" + DEFAULT_PER_UNIT + "," + UPDATED_PER_UNIT,
            "perUnit.in=" + UPDATED_PER_UNIT
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByPerUnitIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where perUnit is not null
        defaultNtQuotePartInformationVersionFiltering("perUnit.specified=true", "perUnit.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByPerUnitIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where perUnit is greater than or equal to
        defaultNtQuotePartInformationVersionFiltering(
            "perUnit.greaterThanOrEqual=" + DEFAULT_PER_UNIT,
            "perUnit.greaterThanOrEqual=" + UPDATED_PER_UNIT
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByPerUnitIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where perUnit is less than or equal to
        defaultNtQuotePartInformationVersionFiltering(
            "perUnit.lessThanOrEqual=" + DEFAULT_PER_UNIT,
            "perUnit.lessThanOrEqual=" + SMALLER_PER_UNIT
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByPerUnitIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where perUnit is less than
        defaultNtQuotePartInformationVersionFiltering("perUnit.lessThan=" + UPDATED_PER_UNIT, "perUnit.lessThan=" + DEFAULT_PER_UNIT);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByPerUnitIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where perUnit is greater than
        defaultNtQuotePartInformationVersionFiltering("perUnit.greaterThan=" + SMALLER_PER_UNIT, "perUnit.greaterThan=" + DEFAULT_PER_UNIT);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByTotalPricePerChinaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where totalPricePerChina equals to
        defaultNtQuotePartInformationVersionFiltering(
            "totalPricePerChina.equals=" + DEFAULT_TOTAL_PRICE_PER_CHINA,
            "totalPricePerChina.equals=" + UPDATED_TOTAL_PRICE_PER_CHINA
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByTotalPricePerChinaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where totalPricePerChina in
        defaultNtQuotePartInformationVersionFiltering(
            "totalPricePerChina.in=" + DEFAULT_TOTAL_PRICE_PER_CHINA + "," + UPDATED_TOTAL_PRICE_PER_CHINA,
            "totalPricePerChina.in=" + UPDATED_TOTAL_PRICE_PER_CHINA
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByTotalPricePerChinaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where totalPricePerChina is not null
        defaultNtQuotePartInformationVersionFiltering("totalPricePerChina.specified=true", "totalPricePerChina.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByTotalPricePerChinaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where totalPricePerChina is greater than or equal to
        defaultNtQuotePartInformationVersionFiltering(
            "totalPricePerChina.greaterThanOrEqual=" + DEFAULT_TOTAL_PRICE_PER_CHINA,
            "totalPricePerChina.greaterThanOrEqual=" + UPDATED_TOTAL_PRICE_PER_CHINA
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByTotalPricePerChinaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where totalPricePerChina is less than or equal to
        defaultNtQuotePartInformationVersionFiltering(
            "totalPricePerChina.lessThanOrEqual=" + DEFAULT_TOTAL_PRICE_PER_CHINA,
            "totalPricePerChina.lessThanOrEqual=" + SMALLER_TOTAL_PRICE_PER_CHINA
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByTotalPricePerChinaIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where totalPricePerChina is less than
        defaultNtQuotePartInformationVersionFiltering(
            "totalPricePerChina.lessThan=" + UPDATED_TOTAL_PRICE_PER_CHINA,
            "totalPricePerChina.lessThan=" + DEFAULT_TOTAL_PRICE_PER_CHINA
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByTotalPricePerChinaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where totalPricePerChina is greater than
        defaultNtQuotePartInformationVersionFiltering(
            "totalPricePerChina.greaterThan=" + SMALLER_TOTAL_PRICE_PER_CHINA,
            "totalPricePerChina.greaterThan=" + DEFAULT_TOTAL_PRICE_PER_CHINA
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByTotalPriceBudgetIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where totalPriceBudget equals to
        defaultNtQuotePartInformationVersionFiltering(
            "totalPriceBudget.equals=" + DEFAULT_TOTAL_PRICE_BUDGET,
            "totalPriceBudget.equals=" + UPDATED_TOTAL_PRICE_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByTotalPriceBudgetIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where totalPriceBudget in
        defaultNtQuotePartInformationVersionFiltering(
            "totalPriceBudget.in=" + DEFAULT_TOTAL_PRICE_BUDGET + "," + UPDATED_TOTAL_PRICE_BUDGET,
            "totalPriceBudget.in=" + UPDATED_TOTAL_PRICE_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByTotalPriceBudgetIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where totalPriceBudget is not null
        defaultNtQuotePartInformationVersionFiltering("totalPriceBudget.specified=true", "totalPriceBudget.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByTotalPriceBudgetIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where totalPriceBudget is greater than or equal to
        defaultNtQuotePartInformationVersionFiltering(
            "totalPriceBudget.greaterThanOrEqual=" + DEFAULT_TOTAL_PRICE_BUDGET,
            "totalPriceBudget.greaterThanOrEqual=" + UPDATED_TOTAL_PRICE_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByTotalPriceBudgetIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where totalPriceBudget is less than or equal to
        defaultNtQuotePartInformationVersionFiltering(
            "totalPriceBudget.lessThanOrEqual=" + DEFAULT_TOTAL_PRICE_BUDGET,
            "totalPriceBudget.lessThanOrEqual=" + SMALLER_TOTAL_PRICE_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByTotalPriceBudgetIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where totalPriceBudget is less than
        defaultNtQuotePartInformationVersionFiltering(
            "totalPriceBudget.lessThan=" + UPDATED_TOTAL_PRICE_BUDGET,
            "totalPriceBudget.lessThan=" + DEFAULT_TOTAL_PRICE_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByTotalPriceBudgetIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where totalPriceBudget is greater than
        defaultNtQuotePartInformationVersionFiltering(
            "totalPriceBudget.greaterThan=" + SMALLER_TOTAL_PRICE_BUDGET,
            "totalPriceBudget.greaterThan=" + DEFAULT_TOTAL_PRICE_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByGrainBudgetIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where grainBudget equals to
        defaultNtQuotePartInformationVersionFiltering(
            "grainBudget.equals=" + DEFAULT_GRAIN_BUDGET,
            "grainBudget.equals=" + UPDATED_GRAIN_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByGrainBudgetIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where grainBudget in
        defaultNtQuotePartInformationVersionFiltering(
            "grainBudget.in=" + DEFAULT_GRAIN_BUDGET + "," + UPDATED_GRAIN_BUDGET,
            "grainBudget.in=" + UPDATED_GRAIN_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByGrainBudgetIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where grainBudget is not null
        defaultNtQuotePartInformationVersionFiltering("grainBudget.specified=true", "grainBudget.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByGrainBudgetIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where grainBudget is greater than or equal to
        defaultNtQuotePartInformationVersionFiltering(
            "grainBudget.greaterThanOrEqual=" + DEFAULT_GRAIN_BUDGET,
            "grainBudget.greaterThanOrEqual=" + UPDATED_GRAIN_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByGrainBudgetIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where grainBudget is less than or equal to
        defaultNtQuotePartInformationVersionFiltering(
            "grainBudget.lessThanOrEqual=" + DEFAULT_GRAIN_BUDGET,
            "grainBudget.lessThanOrEqual=" + SMALLER_GRAIN_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByGrainBudgetIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where grainBudget is less than
        defaultNtQuotePartInformationVersionFiltering(
            "grainBudget.lessThan=" + UPDATED_GRAIN_BUDGET,
            "grainBudget.lessThan=" + DEFAULT_GRAIN_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByGrainBudgetIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where grainBudget is greater than
        defaultNtQuotePartInformationVersionFiltering(
            "grainBudget.greaterThan=" + SMALLER_GRAIN_BUDGET,
            "grainBudget.greaterThan=" + DEFAULT_GRAIN_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByDogatingFixtureBudgetIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where dogatingFixtureBudget equals to
        defaultNtQuotePartInformationVersionFiltering(
            "dogatingFixtureBudget.equals=" + DEFAULT_DOGATING_FIXTURE_BUDGET,
            "dogatingFixtureBudget.equals=" + UPDATED_DOGATING_FIXTURE_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByDogatingFixtureBudgetIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where dogatingFixtureBudget in
        defaultNtQuotePartInformationVersionFiltering(
            "dogatingFixtureBudget.in=" + DEFAULT_DOGATING_FIXTURE_BUDGET + "," + UPDATED_DOGATING_FIXTURE_BUDGET,
            "dogatingFixtureBudget.in=" + UPDATED_DOGATING_FIXTURE_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByDogatingFixtureBudgetIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where dogatingFixtureBudget is not null
        defaultNtQuotePartInformationVersionFiltering("dogatingFixtureBudget.specified=true", "dogatingFixtureBudget.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByDogatingFixtureBudgetIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where dogatingFixtureBudget is greater than or equal to
        defaultNtQuotePartInformationVersionFiltering(
            "dogatingFixtureBudget.greaterThanOrEqual=" + DEFAULT_DOGATING_FIXTURE_BUDGET,
            "dogatingFixtureBudget.greaterThanOrEqual=" + UPDATED_DOGATING_FIXTURE_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByDogatingFixtureBudgetIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where dogatingFixtureBudget is less than or equal to
        defaultNtQuotePartInformationVersionFiltering(
            "dogatingFixtureBudget.lessThanOrEqual=" + DEFAULT_DOGATING_FIXTURE_BUDGET,
            "dogatingFixtureBudget.lessThanOrEqual=" + SMALLER_DOGATING_FIXTURE_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByDogatingFixtureBudgetIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where dogatingFixtureBudget is less than
        defaultNtQuotePartInformationVersionFiltering(
            "dogatingFixtureBudget.lessThan=" + UPDATED_DOGATING_FIXTURE_BUDGET,
            "dogatingFixtureBudget.lessThan=" + DEFAULT_DOGATING_FIXTURE_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByDogatingFixtureBudgetIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where dogatingFixtureBudget is greater than
        defaultNtQuotePartInformationVersionFiltering(
            "dogatingFixtureBudget.greaterThan=" + SMALLER_DOGATING_FIXTURE_BUDGET,
            "dogatingFixtureBudget.greaterThan=" + DEFAULT_DOGATING_FIXTURE_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByGaugeBudgetIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where gaugeBudget equals to
        defaultNtQuotePartInformationVersionFiltering(
            "gaugeBudget.equals=" + DEFAULT_GAUGE_BUDGET,
            "gaugeBudget.equals=" + UPDATED_GAUGE_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByGaugeBudgetIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where gaugeBudget in
        defaultNtQuotePartInformationVersionFiltering(
            "gaugeBudget.in=" + DEFAULT_GAUGE_BUDGET + "," + UPDATED_GAUGE_BUDGET,
            "gaugeBudget.in=" + UPDATED_GAUGE_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByGaugeBudgetIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where gaugeBudget is not null
        defaultNtQuotePartInformationVersionFiltering("gaugeBudget.specified=true", "gaugeBudget.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByGaugeBudgetIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where gaugeBudget is greater than or equal to
        defaultNtQuotePartInformationVersionFiltering(
            "gaugeBudget.greaterThanOrEqual=" + DEFAULT_GAUGE_BUDGET,
            "gaugeBudget.greaterThanOrEqual=" + UPDATED_GAUGE_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByGaugeBudgetIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where gaugeBudget is less than or equal to
        defaultNtQuotePartInformationVersionFiltering(
            "gaugeBudget.lessThanOrEqual=" + DEFAULT_GAUGE_BUDGET,
            "gaugeBudget.lessThanOrEqual=" + SMALLER_GAUGE_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByGaugeBudgetIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where gaugeBudget is less than
        defaultNtQuotePartInformationVersionFiltering(
            "gaugeBudget.lessThan=" + UPDATED_GAUGE_BUDGET,
            "gaugeBudget.lessThan=" + DEFAULT_GAUGE_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByGaugeBudgetIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where gaugeBudget is greater than
        defaultNtQuotePartInformationVersionFiltering(
            "gaugeBudget.greaterThan=" + SMALLER_GAUGE_BUDGET,
            "gaugeBudget.greaterThan=" + DEFAULT_GAUGE_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByEoatIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where eoat equals to
        defaultNtQuotePartInformationVersionFiltering("eoat.equals=" + DEFAULT_EOAT, "eoat.equals=" + UPDATED_EOAT);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByEoatIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where eoat in
        defaultNtQuotePartInformationVersionFiltering("eoat.in=" + DEFAULT_EOAT + "," + UPDATED_EOAT, "eoat.in=" + UPDATED_EOAT);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByEoatIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where eoat is not null
        defaultNtQuotePartInformationVersionFiltering("eoat.specified=true", "eoat.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByEoatIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where eoat is greater than or equal to
        defaultNtQuotePartInformationVersionFiltering("eoat.greaterThanOrEqual=" + DEFAULT_EOAT, "eoat.greaterThanOrEqual=" + UPDATED_EOAT);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByEoatIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where eoat is less than or equal to
        defaultNtQuotePartInformationVersionFiltering("eoat.lessThanOrEqual=" + DEFAULT_EOAT, "eoat.lessThanOrEqual=" + SMALLER_EOAT);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByEoatIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where eoat is less than
        defaultNtQuotePartInformationVersionFiltering("eoat.lessThan=" + UPDATED_EOAT, "eoat.lessThan=" + DEFAULT_EOAT);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByEoatIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where eoat is greater than
        defaultNtQuotePartInformationVersionFiltering("eoat.greaterThan=" + SMALLER_EOAT, "eoat.greaterThan=" + DEFAULT_EOAT);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByChinaTariffBudgetIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where chinaTariffBudget equals to
        defaultNtQuotePartInformationVersionFiltering(
            "chinaTariffBudget.equals=" + DEFAULT_CHINA_TARIFF_BUDGET,
            "chinaTariffBudget.equals=" + UPDATED_CHINA_TARIFF_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByChinaTariffBudgetIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where chinaTariffBudget in
        defaultNtQuotePartInformationVersionFiltering(
            "chinaTariffBudget.in=" + DEFAULT_CHINA_TARIFF_BUDGET + "," + UPDATED_CHINA_TARIFF_BUDGET,
            "chinaTariffBudget.in=" + UPDATED_CHINA_TARIFF_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByChinaTariffBudgetIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where chinaTariffBudget is not null
        defaultNtQuotePartInformationVersionFiltering("chinaTariffBudget.specified=true", "chinaTariffBudget.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByChinaTariffBudgetIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where chinaTariffBudget is greater than or equal to
        defaultNtQuotePartInformationVersionFiltering(
            "chinaTariffBudget.greaterThanOrEqual=" + DEFAULT_CHINA_TARIFF_BUDGET,
            "chinaTariffBudget.greaterThanOrEqual=" + UPDATED_CHINA_TARIFF_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByChinaTariffBudgetIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where chinaTariffBudget is less than or equal to
        defaultNtQuotePartInformationVersionFiltering(
            "chinaTariffBudget.lessThanOrEqual=" + DEFAULT_CHINA_TARIFF_BUDGET,
            "chinaTariffBudget.lessThanOrEqual=" + SMALLER_CHINA_TARIFF_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByChinaTariffBudgetIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where chinaTariffBudget is less than
        defaultNtQuotePartInformationVersionFiltering(
            "chinaTariffBudget.lessThan=" + UPDATED_CHINA_TARIFF_BUDGET,
            "chinaTariffBudget.lessThan=" + DEFAULT_CHINA_TARIFF_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByChinaTariffBudgetIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where chinaTariffBudget is greater than
        defaultNtQuotePartInformationVersionFiltering(
            "chinaTariffBudget.greaterThan=" + SMALLER_CHINA_TARIFF_BUDGET,
            "chinaTariffBudget.greaterThan=" + DEFAULT_CHINA_TARIFF_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByTotalToolingBudgetIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where totalToolingBudget equals to
        defaultNtQuotePartInformationVersionFiltering(
            "totalToolingBudget.equals=" + DEFAULT_TOTAL_TOOLING_BUDGET,
            "totalToolingBudget.equals=" + UPDATED_TOTAL_TOOLING_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByTotalToolingBudgetIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where totalToolingBudget in
        defaultNtQuotePartInformationVersionFiltering(
            "totalToolingBudget.in=" + DEFAULT_TOTAL_TOOLING_BUDGET + "," + UPDATED_TOTAL_TOOLING_BUDGET,
            "totalToolingBudget.in=" + UPDATED_TOTAL_TOOLING_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByTotalToolingBudgetIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where totalToolingBudget is not null
        defaultNtQuotePartInformationVersionFiltering("totalToolingBudget.specified=true", "totalToolingBudget.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByTotalToolingBudgetIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where totalToolingBudget is greater than or equal to
        defaultNtQuotePartInformationVersionFiltering(
            "totalToolingBudget.greaterThanOrEqual=" + DEFAULT_TOTAL_TOOLING_BUDGET,
            "totalToolingBudget.greaterThanOrEqual=" + UPDATED_TOTAL_TOOLING_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByTotalToolingBudgetIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where totalToolingBudget is less than or equal to
        defaultNtQuotePartInformationVersionFiltering(
            "totalToolingBudget.lessThanOrEqual=" + DEFAULT_TOTAL_TOOLING_BUDGET,
            "totalToolingBudget.lessThanOrEqual=" + SMALLER_TOTAL_TOOLING_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByTotalToolingBudgetIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where totalToolingBudget is less than
        defaultNtQuotePartInformationVersionFiltering(
            "totalToolingBudget.lessThan=" + UPDATED_TOTAL_TOOLING_BUDGET,
            "totalToolingBudget.lessThan=" + DEFAULT_TOTAL_TOOLING_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByTotalToolingBudgetIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where totalToolingBudget is greater than
        defaultNtQuotePartInformationVersionFiltering(
            "totalToolingBudget.greaterThan=" + SMALLER_TOTAL_TOOLING_BUDGET,
            "totalToolingBudget.greaterThan=" + DEFAULT_TOTAL_TOOLING_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByLeadTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where leadTime equals to
        defaultNtQuotePartInformationVersionFiltering("leadTime.equals=" + DEFAULT_LEAD_TIME, "leadTime.equals=" + UPDATED_LEAD_TIME);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByLeadTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where leadTime in
        defaultNtQuotePartInformationVersionFiltering(
            "leadTime.in=" + DEFAULT_LEAD_TIME + "," + UPDATED_LEAD_TIME,
            "leadTime.in=" + UPDATED_LEAD_TIME
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByLeadTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where leadTime is not null
        defaultNtQuotePartInformationVersionFiltering("leadTime.specified=true", "leadTime.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByLeadTimeContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where leadTime contains
        defaultNtQuotePartInformationVersionFiltering("leadTime.contains=" + DEFAULT_LEAD_TIME, "leadTime.contains=" + UPDATED_LEAD_TIME);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByLeadTimeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where leadTime does not contain
        defaultNtQuotePartInformationVersionFiltering(
            "leadTime.doesNotContain=" + UPDATED_LEAD_TIME,
            "leadTime.doesNotContain=" + DEFAULT_LEAD_TIME
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByToolingNotesIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where toolingNotes equals to
        defaultNtQuotePartInformationVersionFiltering(
            "toolingNotes.equals=" + DEFAULT_TOOLING_NOTES,
            "toolingNotes.equals=" + UPDATED_TOOLING_NOTES
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByToolingNotesIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where toolingNotes in
        defaultNtQuotePartInformationVersionFiltering(
            "toolingNotes.in=" + DEFAULT_TOOLING_NOTES + "," + UPDATED_TOOLING_NOTES,
            "toolingNotes.in=" + UPDATED_TOOLING_NOTES
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByToolingNotesIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where toolingNotes is not null
        defaultNtQuotePartInformationVersionFiltering("toolingNotes.specified=true", "toolingNotes.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByToolingNotesContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where toolingNotes contains
        defaultNtQuotePartInformationVersionFiltering(
            "toolingNotes.contains=" + DEFAULT_TOOLING_NOTES,
            "toolingNotes.contains=" + UPDATED_TOOLING_NOTES
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByToolingNotesNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where toolingNotes does not contain
        defaultNtQuotePartInformationVersionFiltering(
            "toolingNotes.doesNotContain=" + UPDATED_TOOLING_NOTES,
            "toolingNotes.doesNotContain=" + DEFAULT_TOOLING_NOTES
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByPartDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where partDescription equals to
        defaultNtQuotePartInformationVersionFiltering(
            "partDescription.equals=" + DEFAULT_PART_DESCRIPTION,
            "partDescription.equals=" + UPDATED_PART_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByPartDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where partDescription in
        defaultNtQuotePartInformationVersionFiltering(
            "partDescription.in=" + DEFAULT_PART_DESCRIPTION + "," + UPDATED_PART_DESCRIPTION,
            "partDescription.in=" + UPDATED_PART_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByPartDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where partDescription is not null
        defaultNtQuotePartInformationVersionFiltering("partDescription.specified=true", "partDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByPartDescriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where partDescription contains
        defaultNtQuotePartInformationVersionFiltering(
            "partDescription.contains=" + DEFAULT_PART_DESCRIPTION,
            "partDescription.contains=" + UPDATED_PART_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByPartDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where partDescription does not contain
        defaultNtQuotePartInformationVersionFiltering(
            "partDescription.doesNotContain=" + UPDATED_PART_DESCRIPTION,
            "partDescription.doesNotContain=" + DEFAULT_PART_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByJobIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where jobId equals to
        defaultNtQuotePartInformationVersionFiltering("jobId.equals=" + DEFAULT_JOB_ID, "jobId.equals=" + UPDATED_JOB_ID);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByJobIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where jobId in
        defaultNtQuotePartInformationVersionFiltering("jobId.in=" + DEFAULT_JOB_ID + "," + UPDATED_JOB_ID, "jobId.in=" + UPDATED_JOB_ID);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByJobIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where jobId is not null
        defaultNtQuotePartInformationVersionFiltering("jobId.specified=true", "jobId.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByJobIdContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where jobId contains
        defaultNtQuotePartInformationVersionFiltering("jobId.contains=" + DEFAULT_JOB_ID, "jobId.contains=" + UPDATED_JOB_ID);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByJobIdNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where jobId does not contain
        defaultNtQuotePartInformationVersionFiltering("jobId.doesNotContain=" + UPDATED_JOB_ID, "jobId.doesNotContain=" + DEFAULT_JOB_ID);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByMoldIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where moldId equals to
        defaultNtQuotePartInformationVersionFiltering("moldId.equals=" + DEFAULT_MOLD_ID, "moldId.equals=" + UPDATED_MOLD_ID);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByMoldIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where moldId in
        defaultNtQuotePartInformationVersionFiltering(
            "moldId.in=" + DEFAULT_MOLD_ID + "," + UPDATED_MOLD_ID,
            "moldId.in=" + UPDATED_MOLD_ID
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByMoldIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where moldId is not null
        defaultNtQuotePartInformationVersionFiltering("moldId.specified=true", "moldId.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByMoldIdContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where moldId contains
        defaultNtQuotePartInformationVersionFiltering("moldId.contains=" + DEFAULT_MOLD_ID, "moldId.contains=" + UPDATED_MOLD_ID);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByMoldIdNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where moldId does not contain
        defaultNtQuotePartInformationVersionFiltering(
            "moldId.doesNotContain=" + UPDATED_MOLD_ID,
            "moldId.doesNotContain=" + DEFAULT_MOLD_ID
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByQuoteTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where quoteType equals to
        defaultNtQuotePartInformationVersionFiltering("quoteType.equals=" + DEFAULT_QUOTE_TYPE, "quoteType.equals=" + UPDATED_QUOTE_TYPE);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByQuoteTypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where quoteType in
        defaultNtQuotePartInformationVersionFiltering(
            "quoteType.in=" + DEFAULT_QUOTE_TYPE + "," + UPDATED_QUOTE_TYPE,
            "quoteType.in=" + UPDATED_QUOTE_TYPE
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByQuoteTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where quoteType is not null
        defaultNtQuotePartInformationVersionFiltering("quoteType.specified=true", "quoteType.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByQuoteTypeContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where quoteType contains
        defaultNtQuotePartInformationVersionFiltering(
            "quoteType.contains=" + DEFAULT_QUOTE_TYPE,
            "quoteType.contains=" + UPDATED_QUOTE_TYPE
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByQuoteTypeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where quoteType does not contain
        defaultNtQuotePartInformationVersionFiltering(
            "quoteType.doesNotContain=" + UPDATED_QUOTE_TYPE,
            "quoteType.doesNotContain=" + DEFAULT_QUOTE_TYPE
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where comments equals to
        defaultNtQuotePartInformationVersionFiltering("comments.equals=" + DEFAULT_COMMENTS, "comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where comments in
        defaultNtQuotePartInformationVersionFiltering(
            "comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS,
            "comments.in=" + UPDATED_COMMENTS
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where comments is not null
        defaultNtQuotePartInformationVersionFiltering("comments.specified=true", "comments.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByCommentsContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where comments contains
        defaultNtQuotePartInformationVersionFiltering("comments.contains=" + DEFAULT_COMMENTS, "comments.contains=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where comments does not contain
        defaultNtQuotePartInformationVersionFiltering(
            "comments.doesNotContain=" + UPDATED_COMMENTS,
            "comments.doesNotContain=" + DEFAULT_COMMENTS
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where createdBy equals to
        defaultNtQuotePartInformationVersionFiltering("createdBy.equals=" + DEFAULT_CREATED_BY, "createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where createdBy in
        defaultNtQuotePartInformationVersionFiltering(
            "createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY,
            "createdBy.in=" + UPDATED_CREATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where createdBy is not null
        defaultNtQuotePartInformationVersionFiltering("createdBy.specified=true", "createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where createdBy contains
        defaultNtQuotePartInformationVersionFiltering(
            "createdBy.contains=" + DEFAULT_CREATED_BY,
            "createdBy.contains=" + UPDATED_CREATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where createdBy does not contain
        defaultNtQuotePartInformationVersionFiltering(
            "createdBy.doesNotContain=" + UPDATED_CREATED_BY,
            "createdBy.doesNotContain=" + DEFAULT_CREATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where createdDate equals to
        defaultNtQuotePartInformationVersionFiltering(
            "createdDate.equals=" + DEFAULT_CREATED_DATE,
            "createdDate.equals=" + UPDATED_CREATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where createdDate in
        defaultNtQuotePartInformationVersionFiltering(
            "createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE,
            "createdDate.in=" + UPDATED_CREATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where createdDate is not null
        defaultNtQuotePartInformationVersionFiltering("createdDate.specified=true", "createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where updatedBy equals to
        defaultNtQuotePartInformationVersionFiltering("updatedBy.equals=" + DEFAULT_UPDATED_BY, "updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where updatedBy in
        defaultNtQuotePartInformationVersionFiltering(
            "updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY,
            "updatedBy.in=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where updatedBy is not null
        defaultNtQuotePartInformationVersionFiltering("updatedBy.specified=true", "updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where updatedBy contains
        defaultNtQuotePartInformationVersionFiltering(
            "updatedBy.contains=" + DEFAULT_UPDATED_BY,
            "updatedBy.contains=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where updatedBy does not contain
        defaultNtQuotePartInformationVersionFiltering(
            "updatedBy.doesNotContain=" + UPDATED_UPDATED_BY,
            "updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where updatedDate equals to
        defaultNtQuotePartInformationVersionFiltering(
            "updatedDate.equals=" + DEFAULT_UPDATED_DATE,
            "updatedDate.equals=" + UPDATED_UPDATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where updatedDate in
        defaultNtQuotePartInformationVersionFiltering(
            "updatedDate.in=" + DEFAULT_UPDATED_DATE + "," + UPDATED_UPDATED_DATE,
            "updatedDate.in=" + UPDATED_UPDATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        // Get all the ntQuotePartInformationVersionList where updatedDate is not null
        defaultNtQuotePartInformationVersionFiltering("updatedDate.specified=true", "updatedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationVersionsByNtQuoteIsEqualToSomething() throws Exception {
        NtQuote ntQuote;
        if (TestUtil.findAll(em, NtQuote.class).isEmpty()) {
            ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);
            ntQuote = NtQuoteResourceIT.createEntity();
        } else {
            ntQuote = TestUtil.findAll(em, NtQuote.class).get(0);
        }
        em.persist(ntQuote);
        em.flush();
        ntQuotePartInformationVersion.setNtQuote(ntQuote);
        ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);
        Long ntQuoteId = ntQuote.getId();
        // Get all the ntQuotePartInformationVersionList where ntQuote equals to ntQuoteId
        defaultNtQuotePartInformationVersionShouldBeFound("ntQuoteId.equals=" + ntQuoteId);

        // Get all the ntQuotePartInformationVersionList where ntQuote equals to (ntQuoteId + 1)
        defaultNtQuotePartInformationVersionShouldNotBeFound("ntQuoteId.equals=" + (ntQuoteId + 1));
    }

    private void defaultNtQuotePartInformationVersionFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultNtQuotePartInformationVersionShouldBeFound(shouldBeFound);
        defaultNtQuotePartInformationVersionShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNtQuotePartInformationVersionShouldBeFound(String filter) throws Exception {
        restNtQuotePartInformationVersionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuotePartInformationVersion.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].materialDescription").value(hasItem(DEFAULT_MATERIAL_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].partNumber").value(hasItem(DEFAULT_PART_NUMBER)))
            .andExpect(jsonPath("$.[*].cadFile").value(hasItem(DEFAULT_CAD_FILE)))
            .andExpect(jsonPath("$.[*].eau").value(hasItem(DEFAULT_EAU)))
            .andExpect(jsonPath("$.[*].partWeight").value(hasItem(DEFAULT_PART_WEIGHT)))
            .andExpect(jsonPath("$.[*].materialType").value(hasItem(DEFAULT_MATERIAL_TYPE)))
            .andExpect(jsonPath("$.[*].materialCost").value(hasItem(sameNumber(DEFAULT_MATERIAL_COST))))
            .andExpect(jsonPath("$.[*].extendedMaterialCostPer").value(hasItem(sameNumber(DEFAULT_EXTENDED_MATERIAL_COST_PER))))
            .andExpect(jsonPath("$.[*].externalMachineCostPer").value(hasItem(sameNumber(DEFAULT_EXTERNAL_MACHINE_COST_PER))))
            .andExpect(jsonPath("$.[*].purchaseComponentCost").value(hasItem(sameNumber(DEFAULT_PURCHASE_COMPONENT_COST))))
            .andExpect(
                jsonPath("$.[*].secondaryExternalOperationCost").value(hasItem(sameNumber(DEFAULT_SECONDARY_EXTERNAL_OPERATION_COST)))
            )
            .andExpect(jsonPath("$.[*].overhead").value(hasItem(sameNumber(DEFAULT_OVERHEAD))))
            .andExpect(jsonPath("$.[*].packLogisticCostPer").value(hasItem(sameNumber(DEFAULT_PACK_LOGISTIC_COST_PER))))
            .andExpect(jsonPath("$.[*].machineSizeTons").value(hasItem(DEFAULT_MACHINE_SIZE_TONS)))
            .andExpect(jsonPath("$.[*].numberOfCavities").value(hasItem(DEFAULT_NUMBER_OF_CAVITIES)))
            .andExpect(jsonPath("$.[*].cycleTime").value(hasItem(DEFAULT_CYCLE_TIME)))
            .andExpect(jsonPath("$.[*].perUnit").value(hasItem(sameNumber(DEFAULT_PER_UNIT))))
            .andExpect(jsonPath("$.[*].totalPricePerChina").value(hasItem(sameNumber(DEFAULT_TOTAL_PRICE_PER_CHINA))))
            .andExpect(jsonPath("$.[*].totalPriceBudget").value(hasItem(sameNumber(DEFAULT_TOTAL_PRICE_BUDGET))))
            .andExpect(jsonPath("$.[*].grainBudget").value(hasItem(sameNumber(DEFAULT_GRAIN_BUDGET))))
            .andExpect(jsonPath("$.[*].dogatingFixtureBudget").value(hasItem(sameNumber(DEFAULT_DOGATING_FIXTURE_BUDGET))))
            .andExpect(jsonPath("$.[*].gaugeBudget").value(hasItem(sameNumber(DEFAULT_GAUGE_BUDGET))))
            .andExpect(jsonPath("$.[*].eoat").value(hasItem(sameNumber(DEFAULT_EOAT))))
            .andExpect(jsonPath("$.[*].chinaTariffBudget").value(hasItem(sameNumber(DEFAULT_CHINA_TARIFF_BUDGET))))
            .andExpect(jsonPath("$.[*].totalToolingBudget").value(hasItem(sameNumber(DEFAULT_TOTAL_TOOLING_BUDGET))))
            .andExpect(jsonPath("$.[*].leadTime").value(hasItem(DEFAULT_LEAD_TIME)))
            .andExpect(jsonPath("$.[*].toolingNotes").value(hasItem(DEFAULT_TOOLING_NOTES)))
            .andExpect(jsonPath("$.[*].partDescription").value(hasItem(DEFAULT_PART_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].jobId").value(hasItem(DEFAULT_JOB_ID)))
            .andExpect(jsonPath("$.[*].moldId").value(hasItem(DEFAULT_MOLD_ID)))
            .andExpect(jsonPath("$.[*].quoteType").value(hasItem(DEFAULT_QUOTE_TYPE)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));

        // Check, that the count call also returns 1
        restNtQuotePartInformationVersionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNtQuotePartInformationVersionShouldNotBeFound(String filter) throws Exception {
        restNtQuotePartInformationVersionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNtQuotePartInformationVersionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNtQuotePartInformationVersion() throws Exception {
        // Get the ntQuotePartInformationVersion
        restNtQuotePartInformationVersionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNtQuotePartInformationVersion() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        long databaseSizeBeforeUpdate = getRepositoryCount();
        ntQuotePartInformationVersionSearchRepository.save(ntQuotePartInformationVersion);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuotePartInformationVersionSearchRepository.findAll());

        // Update the ntQuotePartInformationVersion
        NtQuotePartInformationVersion updatedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository
            .findById(ntQuotePartInformationVersion.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedNtQuotePartInformationVersion are not directly saved in db
        em.detach(updatedNtQuotePartInformationVersion);
        updatedNtQuotePartInformationVersion
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .materialDescription(UPDATED_MATERIAL_DESCRIPTION)
            .partNumber(UPDATED_PART_NUMBER)
            .cadFile(UPDATED_CAD_FILE)
            .eau(UPDATED_EAU)
            .partWeight(UPDATED_PART_WEIGHT)
            .materialType(UPDATED_MATERIAL_TYPE)
            .materialCost(UPDATED_MATERIAL_COST)
            .extendedMaterialCostPer(UPDATED_EXTENDED_MATERIAL_COST_PER)
            .externalMachineCostPer(UPDATED_EXTERNAL_MACHINE_COST_PER)
            .purchaseComponentCost(UPDATED_PURCHASE_COMPONENT_COST)
            .secondaryExternalOperationCost(UPDATED_SECONDARY_EXTERNAL_OPERATION_COST)
            .overhead(UPDATED_OVERHEAD)
            .packLogisticCostPer(UPDATED_PACK_LOGISTIC_COST_PER)
            .machineSizeTons(UPDATED_MACHINE_SIZE_TONS)
            .numberOfCavities(UPDATED_NUMBER_OF_CAVITIES)
            .cycleTime(UPDATED_CYCLE_TIME)
            .perUnit(UPDATED_PER_UNIT)
            .totalPricePerChina(UPDATED_TOTAL_PRICE_PER_CHINA)
            .totalPriceBudget(UPDATED_TOTAL_PRICE_BUDGET)
            .grainBudget(UPDATED_GRAIN_BUDGET)
            .dogatingFixtureBudget(UPDATED_DOGATING_FIXTURE_BUDGET)
            .gaugeBudget(UPDATED_GAUGE_BUDGET)
            .eoat(UPDATED_EOAT)
            .chinaTariffBudget(UPDATED_CHINA_TARIFF_BUDGET)
            .totalToolingBudget(UPDATED_TOTAL_TOOLING_BUDGET)
            .leadTime(UPDATED_LEAD_TIME)
            .toolingNotes(UPDATED_TOOLING_NOTES)
            .partDescription(UPDATED_PART_DESCRIPTION)
            .jobId(UPDATED_JOB_ID)
            .moldId(UPDATED_MOLD_ID)
            .quoteType(UPDATED_QUOTE_TYPE)
            .comments(UPDATED_COMMENTS)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);
        NtQuotePartInformationVersionDTO ntQuotePartInformationVersionDTO = ntQuotePartInformationVersionMapper.toDto(
            updatedNtQuotePartInformationVersion
        );

        restNtQuotePartInformationVersionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ntQuotePartInformationVersionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuotePartInformationVersionDTO))
            )
            .andExpect(status().isOk());

        // Validate the NtQuotePartInformationVersion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedNtQuotePartInformationVersionToMatchAllProperties(updatedNtQuotePartInformationVersion);

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuotePartInformationVersionSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<NtQuotePartInformationVersion> ntQuotePartInformationVersionSearchList = Streamable.of(
                    ntQuotePartInformationVersionSearchRepository.findAll()
                ).toList();
                NtQuotePartInformationVersion testNtQuotePartInformationVersionSearch = ntQuotePartInformationVersionSearchList.get(
                    searchDatabaseSizeAfter - 1
                );

                assertNtQuotePartInformationVersionAllPropertiesEquals(
                    testNtQuotePartInformationVersionSearch,
                    updatedNtQuotePartInformationVersion
                );
            });
    }

    @Test
    @Transactional
    void putNonExistingNtQuotePartInformationVersion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuotePartInformationVersionSearchRepository.findAll());
        ntQuotePartInformationVersion.setId(longCount.incrementAndGet());

        // Create the NtQuotePartInformationVersion
        NtQuotePartInformationVersionDTO ntQuotePartInformationVersionDTO = ntQuotePartInformationVersionMapper.toDto(
            ntQuotePartInformationVersion
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNtQuotePartInformationVersionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ntQuotePartInformationVersionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuotePartInformationVersionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuotePartInformationVersion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuotePartInformationVersionSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchNtQuotePartInformationVersion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuotePartInformationVersionSearchRepository.findAll());
        ntQuotePartInformationVersion.setId(longCount.incrementAndGet());

        // Create the NtQuotePartInformationVersion
        NtQuotePartInformationVersionDTO ntQuotePartInformationVersionDTO = ntQuotePartInformationVersionMapper.toDto(
            ntQuotePartInformationVersion
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuotePartInformationVersionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuotePartInformationVersionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuotePartInformationVersion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuotePartInformationVersionSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNtQuotePartInformationVersion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuotePartInformationVersionSearchRepository.findAll());
        ntQuotePartInformationVersion.setId(longCount.incrementAndGet());

        // Create the NtQuotePartInformationVersion
        NtQuotePartInformationVersionDTO ntQuotePartInformationVersionDTO = ntQuotePartInformationVersionMapper.toDto(
            ntQuotePartInformationVersion
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuotePartInformationVersionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuotePartInformationVersionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NtQuotePartInformationVersion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuotePartInformationVersionSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateNtQuotePartInformationVersionWithPatch() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ntQuotePartInformationVersion using partial update
        NtQuotePartInformationVersion partialUpdatedNtQuotePartInformationVersion = new NtQuotePartInformationVersion();
        partialUpdatedNtQuotePartInformationVersion.setId(ntQuotePartInformationVersion.getId());

        partialUpdatedNtQuotePartInformationVersion
            .srNo(UPDATED_SR_NO)
            .materialDescription(UPDATED_MATERIAL_DESCRIPTION)
            .partNumber(UPDATED_PART_NUMBER)
            .materialCost(UPDATED_MATERIAL_COST)
            .externalMachineCostPer(UPDATED_EXTERNAL_MACHINE_COST_PER)
            .purchaseComponentCost(UPDATED_PURCHASE_COMPONENT_COST)
            .secondaryExternalOperationCost(UPDATED_SECONDARY_EXTERNAL_OPERATION_COST)
            .overhead(UPDATED_OVERHEAD)
            .packLogisticCostPer(UPDATED_PACK_LOGISTIC_COST_PER)
            .perUnit(UPDATED_PER_UNIT)
            .totalPricePerChina(UPDATED_TOTAL_PRICE_PER_CHINA)
            .grainBudget(UPDATED_GRAIN_BUDGET)
            .gaugeBudget(UPDATED_GAUGE_BUDGET)
            .eoat(UPDATED_EOAT)
            .chinaTariffBudget(UPDATED_CHINA_TARIFF_BUDGET)
            .totalToolingBudget(UPDATED_TOTAL_TOOLING_BUDGET)
            .leadTime(UPDATED_LEAD_TIME)
            .toolingNotes(UPDATED_TOOLING_NOTES)
            .partDescription(UPDATED_PART_DESCRIPTION)
            .jobId(UPDATED_JOB_ID)
            .quoteType(UPDATED_QUOTE_TYPE);

        restNtQuotePartInformationVersionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNtQuotePartInformationVersion.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNtQuotePartInformationVersion))
            )
            .andExpect(status().isOk());

        // Validate the NtQuotePartInformationVersion in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNtQuotePartInformationVersionUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedNtQuotePartInformationVersion, ntQuotePartInformationVersion),
            getPersistedNtQuotePartInformationVersion(ntQuotePartInformationVersion)
        );
    }

    @Test
    @Transactional
    void fullUpdateNtQuotePartInformationVersionWithPatch() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ntQuotePartInformationVersion using partial update
        NtQuotePartInformationVersion partialUpdatedNtQuotePartInformationVersion = new NtQuotePartInformationVersion();
        partialUpdatedNtQuotePartInformationVersion.setId(ntQuotePartInformationVersion.getId());

        partialUpdatedNtQuotePartInformationVersion
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .materialDescription(UPDATED_MATERIAL_DESCRIPTION)
            .partNumber(UPDATED_PART_NUMBER)
            .cadFile(UPDATED_CAD_FILE)
            .eau(UPDATED_EAU)
            .partWeight(UPDATED_PART_WEIGHT)
            .materialType(UPDATED_MATERIAL_TYPE)
            .materialCost(UPDATED_MATERIAL_COST)
            .extendedMaterialCostPer(UPDATED_EXTENDED_MATERIAL_COST_PER)
            .externalMachineCostPer(UPDATED_EXTERNAL_MACHINE_COST_PER)
            .purchaseComponentCost(UPDATED_PURCHASE_COMPONENT_COST)
            .secondaryExternalOperationCost(UPDATED_SECONDARY_EXTERNAL_OPERATION_COST)
            .overhead(UPDATED_OVERHEAD)
            .packLogisticCostPer(UPDATED_PACK_LOGISTIC_COST_PER)
            .machineSizeTons(UPDATED_MACHINE_SIZE_TONS)
            .numberOfCavities(UPDATED_NUMBER_OF_CAVITIES)
            .cycleTime(UPDATED_CYCLE_TIME)
            .perUnit(UPDATED_PER_UNIT)
            .totalPricePerChina(UPDATED_TOTAL_PRICE_PER_CHINA)
            .totalPriceBudget(UPDATED_TOTAL_PRICE_BUDGET)
            .grainBudget(UPDATED_GRAIN_BUDGET)
            .dogatingFixtureBudget(UPDATED_DOGATING_FIXTURE_BUDGET)
            .gaugeBudget(UPDATED_GAUGE_BUDGET)
            .eoat(UPDATED_EOAT)
            .chinaTariffBudget(UPDATED_CHINA_TARIFF_BUDGET)
            .totalToolingBudget(UPDATED_TOTAL_TOOLING_BUDGET)
            .leadTime(UPDATED_LEAD_TIME)
            .toolingNotes(UPDATED_TOOLING_NOTES)
            .partDescription(UPDATED_PART_DESCRIPTION)
            .jobId(UPDATED_JOB_ID)
            .moldId(UPDATED_MOLD_ID)
            .quoteType(UPDATED_QUOTE_TYPE)
            .comments(UPDATED_COMMENTS)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);

        restNtQuotePartInformationVersionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNtQuotePartInformationVersion.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNtQuotePartInformationVersion))
            )
            .andExpect(status().isOk());

        // Validate the NtQuotePartInformationVersion in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNtQuotePartInformationVersionUpdatableFieldsEquals(
            partialUpdatedNtQuotePartInformationVersion,
            getPersistedNtQuotePartInformationVersion(partialUpdatedNtQuotePartInformationVersion)
        );
    }

    @Test
    @Transactional
    void patchNonExistingNtQuotePartInformationVersion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuotePartInformationVersionSearchRepository.findAll());
        ntQuotePartInformationVersion.setId(longCount.incrementAndGet());

        // Create the NtQuotePartInformationVersion
        NtQuotePartInformationVersionDTO ntQuotePartInformationVersionDTO = ntQuotePartInformationVersionMapper.toDto(
            ntQuotePartInformationVersion
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNtQuotePartInformationVersionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ntQuotePartInformationVersionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ntQuotePartInformationVersionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuotePartInformationVersion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuotePartInformationVersionSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNtQuotePartInformationVersion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuotePartInformationVersionSearchRepository.findAll());
        ntQuotePartInformationVersion.setId(longCount.incrementAndGet());

        // Create the NtQuotePartInformationVersion
        NtQuotePartInformationVersionDTO ntQuotePartInformationVersionDTO = ntQuotePartInformationVersionMapper.toDto(
            ntQuotePartInformationVersion
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuotePartInformationVersionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ntQuotePartInformationVersionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuotePartInformationVersion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuotePartInformationVersionSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNtQuotePartInformationVersion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuotePartInformationVersionSearchRepository.findAll());
        ntQuotePartInformationVersion.setId(longCount.incrementAndGet());

        // Create the NtQuotePartInformationVersion
        NtQuotePartInformationVersionDTO ntQuotePartInformationVersionDTO = ntQuotePartInformationVersionMapper.toDto(
            ntQuotePartInformationVersion
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuotePartInformationVersionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ntQuotePartInformationVersionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NtQuotePartInformationVersion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuotePartInformationVersionSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteNtQuotePartInformationVersion() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);
        ntQuotePartInformationVersionRepository.save(ntQuotePartInformationVersion);
        ntQuotePartInformationVersionSearchRepository.save(ntQuotePartInformationVersion);

        long databaseSizeBeforeDelete = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuotePartInformationVersionSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the ntQuotePartInformationVersion
        restNtQuotePartInformationVersionMockMvc
            .perform(delete(ENTITY_API_URL_ID, ntQuotePartInformationVersion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuotePartInformationVersionSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchNtQuotePartInformationVersion() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationVersion = ntQuotePartInformationVersionRepository.saveAndFlush(ntQuotePartInformationVersion);
        ntQuotePartInformationVersionSearchRepository.save(ntQuotePartInformationVersion);

        // Search the ntQuotePartInformationVersion
        restNtQuotePartInformationVersionMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + ntQuotePartInformationVersion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuotePartInformationVersion.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].materialDescription").value(hasItem(DEFAULT_MATERIAL_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].partNumber").value(hasItem(DEFAULT_PART_NUMBER)))
            .andExpect(jsonPath("$.[*].cadFile").value(hasItem(DEFAULT_CAD_FILE)))
            .andExpect(jsonPath("$.[*].eau").value(hasItem(DEFAULT_EAU)))
            .andExpect(jsonPath("$.[*].partWeight").value(hasItem(DEFAULT_PART_WEIGHT)))
            .andExpect(jsonPath("$.[*].materialType").value(hasItem(DEFAULT_MATERIAL_TYPE)))
            .andExpect(jsonPath("$.[*].materialCost").value(hasItem(sameNumber(DEFAULT_MATERIAL_COST))))
            .andExpect(jsonPath("$.[*].extendedMaterialCostPer").value(hasItem(sameNumber(DEFAULT_EXTENDED_MATERIAL_COST_PER))))
            .andExpect(jsonPath("$.[*].externalMachineCostPer").value(hasItem(sameNumber(DEFAULT_EXTERNAL_MACHINE_COST_PER))))
            .andExpect(jsonPath("$.[*].purchaseComponentCost").value(hasItem(sameNumber(DEFAULT_PURCHASE_COMPONENT_COST))))
            .andExpect(
                jsonPath("$.[*].secondaryExternalOperationCost").value(hasItem(sameNumber(DEFAULT_SECONDARY_EXTERNAL_OPERATION_COST)))
            )
            .andExpect(jsonPath("$.[*].overhead").value(hasItem(sameNumber(DEFAULT_OVERHEAD))))
            .andExpect(jsonPath("$.[*].packLogisticCostPer").value(hasItem(sameNumber(DEFAULT_PACK_LOGISTIC_COST_PER))))
            .andExpect(jsonPath("$.[*].machineSizeTons").value(hasItem(DEFAULT_MACHINE_SIZE_TONS)))
            .andExpect(jsonPath("$.[*].numberOfCavities").value(hasItem(DEFAULT_NUMBER_OF_CAVITIES)))
            .andExpect(jsonPath("$.[*].cycleTime").value(hasItem(DEFAULT_CYCLE_TIME)))
            .andExpect(jsonPath("$.[*].perUnit").value(hasItem(sameNumber(DEFAULT_PER_UNIT))))
            .andExpect(jsonPath("$.[*].totalPricePerChina").value(hasItem(sameNumber(DEFAULT_TOTAL_PRICE_PER_CHINA))))
            .andExpect(jsonPath("$.[*].totalPriceBudget").value(hasItem(sameNumber(DEFAULT_TOTAL_PRICE_BUDGET))))
            .andExpect(jsonPath("$.[*].grainBudget").value(hasItem(sameNumber(DEFAULT_GRAIN_BUDGET))))
            .andExpect(jsonPath("$.[*].dogatingFixtureBudget").value(hasItem(sameNumber(DEFAULT_DOGATING_FIXTURE_BUDGET))))
            .andExpect(jsonPath("$.[*].gaugeBudget").value(hasItem(sameNumber(DEFAULT_GAUGE_BUDGET))))
            .andExpect(jsonPath("$.[*].eoat").value(hasItem(sameNumber(DEFAULT_EOAT))))
            .andExpect(jsonPath("$.[*].chinaTariffBudget").value(hasItem(sameNumber(DEFAULT_CHINA_TARIFF_BUDGET))))
            .andExpect(jsonPath("$.[*].totalToolingBudget").value(hasItem(sameNumber(DEFAULT_TOTAL_TOOLING_BUDGET))))
            .andExpect(jsonPath("$.[*].leadTime").value(hasItem(DEFAULT_LEAD_TIME)))
            .andExpect(jsonPath("$.[*].toolingNotes").value(hasItem(DEFAULT_TOOLING_NOTES)))
            .andExpect(jsonPath("$.[*].partDescription").value(hasItem(DEFAULT_PART_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].jobId").value(hasItem(DEFAULT_JOB_ID)))
            .andExpect(jsonPath("$.[*].moldId").value(hasItem(DEFAULT_MOLD_ID)))
            .andExpect(jsonPath("$.[*].quoteType").value(hasItem(DEFAULT_QUOTE_TYPE)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    protected long getRepositoryCount() {
        return ntQuotePartInformationVersionRepository.count();
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

    protected NtQuotePartInformationVersion getPersistedNtQuotePartInformationVersion(
        NtQuotePartInformationVersion ntQuotePartInformationVersion
    ) {
        return ntQuotePartInformationVersionRepository.findById(ntQuotePartInformationVersion.getId()).orElseThrow();
    }

    protected void assertPersistedNtQuotePartInformationVersionToMatchAllProperties(
        NtQuotePartInformationVersion expectedNtQuotePartInformationVersion
    ) {
        assertNtQuotePartInformationVersionAllPropertiesEquals(
            expectedNtQuotePartInformationVersion,
            getPersistedNtQuotePartInformationVersion(expectedNtQuotePartInformationVersion)
        );
    }

    protected void assertPersistedNtQuotePartInformationVersionToMatchUpdatableProperties(
        NtQuotePartInformationVersion expectedNtQuotePartInformationVersion
    ) {
        assertNtQuotePartInformationVersionAllUpdatablePropertiesEquals(
            expectedNtQuotePartInformationVersion,
            getPersistedNtQuotePartInformationVersion(expectedNtQuotePartInformationVersion)
        );
    }
}
