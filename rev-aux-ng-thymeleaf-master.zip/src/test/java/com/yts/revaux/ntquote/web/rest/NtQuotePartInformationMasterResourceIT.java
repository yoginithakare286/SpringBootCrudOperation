package com.yts.revaux.ntquote.web.rest;

import static com.yts.revaux.ntquote.domain.NtQuotePartInformationMasterAsserts.*;
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
import com.yts.revaux.ntquote.domain.NtQuotePartInformationMaster;
import com.yts.revaux.ntquote.repository.NtQuotePartInformationMasterRepository;
import com.yts.revaux.ntquote.repository.search.NtQuotePartInformationMasterSearchRepository;
import com.yts.revaux.ntquote.service.dto.NtQuotePartInformationMasterDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuotePartInformationMasterMapper;
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
 * Integration tests for the {@link NtQuotePartInformationMasterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NtQuotePartInformationMasterResourceIT {

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

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/nt-quote-part-information-masters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/nt-quote-part-information-masters/_search";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NtQuotePartInformationMasterRepository ntQuotePartInformationMasterRepository;

    @Autowired
    private NtQuotePartInformationMasterMapper ntQuotePartInformationMasterMapper;

    @Autowired
    private NtQuotePartInformationMasterSearchRepository ntQuotePartInformationMasterSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNtQuotePartInformationMasterMockMvc;

    private NtQuotePartInformationMaster ntQuotePartInformationMaster;

    private NtQuotePartInformationMaster insertedNtQuotePartInformationMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NtQuotePartInformationMaster createEntity() {
        return new NtQuotePartInformationMaster()
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
    public static NtQuotePartInformationMaster createUpdatedEntity() {
        return new NtQuotePartInformationMaster()
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
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);
    }

    @BeforeEach
    public void initTest() {
        ntQuotePartInformationMaster = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedNtQuotePartInformationMaster != null) {
            ntQuotePartInformationMasterRepository.delete(insertedNtQuotePartInformationMaster);
            ntQuotePartInformationMasterSearchRepository.delete(insertedNtQuotePartInformationMaster);
            insertedNtQuotePartInformationMaster = null;
        }
    }

    @Test
    @Transactional
    void createNtQuotePartInformationMaster() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuotePartInformationMasterSearchRepository.findAll());
        // Create the NtQuotePartInformationMaster
        NtQuotePartInformationMasterDTO ntQuotePartInformationMasterDTO = ntQuotePartInformationMasterMapper.toDto(
            ntQuotePartInformationMaster
        );
        var returnedNtQuotePartInformationMasterDTO = om.readValue(
            restNtQuotePartInformationMasterMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(ntQuotePartInformationMasterDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            NtQuotePartInformationMasterDTO.class
        );

        // Validate the NtQuotePartInformationMaster in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedNtQuotePartInformationMaster = ntQuotePartInformationMasterMapper.toEntity(returnedNtQuotePartInformationMasterDTO);
        assertNtQuotePartInformationMasterUpdatableFieldsEquals(
            returnedNtQuotePartInformationMaster,
            getPersistedNtQuotePartInformationMaster(returnedNtQuotePartInformationMaster)
        );

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuotePartInformationMasterSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });

        insertedNtQuotePartInformationMaster = returnedNtQuotePartInformationMaster;
    }

    @Test
    @Transactional
    void createNtQuotePartInformationMasterWithExistingId() throws Exception {
        // Create the NtQuotePartInformationMaster with an existing ID
        ntQuotePartInformationMaster.setId(1L);
        NtQuotePartInformationMasterDTO ntQuotePartInformationMasterDTO = ntQuotePartInformationMasterMapper.toDto(
            ntQuotePartInformationMaster
        );

        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuotePartInformationMasterSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restNtQuotePartInformationMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuotePartInformationMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuotePartInformationMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuotePartInformationMasterSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkUidIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuotePartInformationMasterSearchRepository.findAll());
        // set the field null
        ntQuotePartInformationMaster.setUid(null);

        // Create the NtQuotePartInformationMaster, which fails.
        NtQuotePartInformationMasterDTO ntQuotePartInformationMasterDTO = ntQuotePartInformationMasterMapper.toDto(
            ntQuotePartInformationMaster
        );

        restNtQuotePartInformationMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuotePartInformationMasterDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuotePartInformationMasterSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMasters() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList
        restNtQuotePartInformationMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuotePartInformationMaster.getId().intValue())))
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
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getNtQuotePartInformationMaster() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get the ntQuotePartInformationMaster
        restNtQuotePartInformationMasterMockMvc
            .perform(get(ENTITY_API_URL_ID, ntQuotePartInformationMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ntQuotePartInformationMaster.getId().intValue()))
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
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNtQuotePartInformationMastersByIdFiltering() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        Long id = ntQuotePartInformationMaster.getId();

        defaultNtQuotePartInformationMasterFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultNtQuotePartInformationMasterFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultNtQuotePartInformationMasterFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersBySrNoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where srNo equals to
        defaultNtQuotePartInformationMasterFiltering("srNo.equals=" + DEFAULT_SR_NO, "srNo.equals=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersBySrNoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where srNo in
        defaultNtQuotePartInformationMasterFiltering("srNo.in=" + DEFAULT_SR_NO + "," + UPDATED_SR_NO, "srNo.in=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersBySrNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where srNo is not null
        defaultNtQuotePartInformationMasterFiltering("srNo.specified=true", "srNo.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersBySrNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where srNo is greater than or equal to
        defaultNtQuotePartInformationMasterFiltering(
            "srNo.greaterThanOrEqual=" + DEFAULT_SR_NO,
            "srNo.greaterThanOrEqual=" + UPDATED_SR_NO
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersBySrNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where srNo is less than or equal to
        defaultNtQuotePartInformationMasterFiltering("srNo.lessThanOrEqual=" + DEFAULT_SR_NO, "srNo.lessThanOrEqual=" + SMALLER_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersBySrNoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where srNo is less than
        defaultNtQuotePartInformationMasterFiltering("srNo.lessThan=" + UPDATED_SR_NO, "srNo.lessThan=" + DEFAULT_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersBySrNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where srNo is greater than
        defaultNtQuotePartInformationMasterFiltering("srNo.greaterThan=" + SMALLER_SR_NO, "srNo.greaterThan=" + DEFAULT_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where uid equals to
        defaultNtQuotePartInformationMasterFiltering("uid.equals=" + DEFAULT_UID, "uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByUidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where uid in
        defaultNtQuotePartInformationMasterFiltering("uid.in=" + DEFAULT_UID + "," + UPDATED_UID, "uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where uid is not null
        defaultNtQuotePartInformationMasterFiltering("uid.specified=true", "uid.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByMaterialDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where materialDescription equals to
        defaultNtQuotePartInformationMasterFiltering(
            "materialDescription.equals=" + DEFAULT_MATERIAL_DESCRIPTION,
            "materialDescription.equals=" + UPDATED_MATERIAL_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByMaterialDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where materialDescription in
        defaultNtQuotePartInformationMasterFiltering(
            "materialDescription.in=" + DEFAULT_MATERIAL_DESCRIPTION + "," + UPDATED_MATERIAL_DESCRIPTION,
            "materialDescription.in=" + UPDATED_MATERIAL_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByMaterialDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where materialDescription is not null
        defaultNtQuotePartInformationMasterFiltering("materialDescription.specified=true", "materialDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByMaterialDescriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where materialDescription contains
        defaultNtQuotePartInformationMasterFiltering(
            "materialDescription.contains=" + DEFAULT_MATERIAL_DESCRIPTION,
            "materialDescription.contains=" + UPDATED_MATERIAL_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByMaterialDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where materialDescription does not contain
        defaultNtQuotePartInformationMasterFiltering(
            "materialDescription.doesNotContain=" + UPDATED_MATERIAL_DESCRIPTION,
            "materialDescription.doesNotContain=" + DEFAULT_MATERIAL_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByPartNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where partNumber equals to
        defaultNtQuotePartInformationMasterFiltering(
            "partNumber.equals=" + DEFAULT_PART_NUMBER,
            "partNumber.equals=" + UPDATED_PART_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByPartNumberIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where partNumber in
        defaultNtQuotePartInformationMasterFiltering(
            "partNumber.in=" + DEFAULT_PART_NUMBER + "," + UPDATED_PART_NUMBER,
            "partNumber.in=" + UPDATED_PART_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByPartNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where partNumber is not null
        defaultNtQuotePartInformationMasterFiltering("partNumber.specified=true", "partNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByPartNumberContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where partNumber contains
        defaultNtQuotePartInformationMasterFiltering(
            "partNumber.contains=" + DEFAULT_PART_NUMBER,
            "partNumber.contains=" + UPDATED_PART_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByPartNumberNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where partNumber does not contain
        defaultNtQuotePartInformationMasterFiltering(
            "partNumber.doesNotContain=" + UPDATED_PART_NUMBER,
            "partNumber.doesNotContain=" + DEFAULT_PART_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByCadFileIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where cadFile equals to
        defaultNtQuotePartInformationMasterFiltering("cadFile.equals=" + DEFAULT_CAD_FILE, "cadFile.equals=" + UPDATED_CAD_FILE);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByCadFileIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where cadFile in
        defaultNtQuotePartInformationMasterFiltering(
            "cadFile.in=" + DEFAULT_CAD_FILE + "," + UPDATED_CAD_FILE,
            "cadFile.in=" + UPDATED_CAD_FILE
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByCadFileIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where cadFile is not null
        defaultNtQuotePartInformationMasterFiltering("cadFile.specified=true", "cadFile.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByCadFileContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where cadFile contains
        defaultNtQuotePartInformationMasterFiltering("cadFile.contains=" + DEFAULT_CAD_FILE, "cadFile.contains=" + UPDATED_CAD_FILE);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByCadFileNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where cadFile does not contain
        defaultNtQuotePartInformationMasterFiltering(
            "cadFile.doesNotContain=" + UPDATED_CAD_FILE,
            "cadFile.doesNotContain=" + DEFAULT_CAD_FILE
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByEauIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where eau equals to
        defaultNtQuotePartInformationMasterFiltering("eau.equals=" + DEFAULT_EAU, "eau.equals=" + UPDATED_EAU);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByEauIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where eau in
        defaultNtQuotePartInformationMasterFiltering("eau.in=" + DEFAULT_EAU + "," + UPDATED_EAU, "eau.in=" + UPDATED_EAU);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByEauIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where eau is not null
        defaultNtQuotePartInformationMasterFiltering("eau.specified=true", "eau.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByEauIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where eau is greater than or equal to
        defaultNtQuotePartInformationMasterFiltering("eau.greaterThanOrEqual=" + DEFAULT_EAU, "eau.greaterThanOrEqual=" + UPDATED_EAU);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByEauIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where eau is less than or equal to
        defaultNtQuotePartInformationMasterFiltering("eau.lessThanOrEqual=" + DEFAULT_EAU, "eau.lessThanOrEqual=" + SMALLER_EAU);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByEauIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where eau is less than
        defaultNtQuotePartInformationMasterFiltering("eau.lessThan=" + UPDATED_EAU, "eau.lessThan=" + DEFAULT_EAU);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByEauIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where eau is greater than
        defaultNtQuotePartInformationMasterFiltering("eau.greaterThan=" + SMALLER_EAU, "eau.greaterThan=" + DEFAULT_EAU);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByPartWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where partWeight equals to
        defaultNtQuotePartInformationMasterFiltering(
            "partWeight.equals=" + DEFAULT_PART_WEIGHT,
            "partWeight.equals=" + UPDATED_PART_WEIGHT
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByPartWeightIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where partWeight in
        defaultNtQuotePartInformationMasterFiltering(
            "partWeight.in=" + DEFAULT_PART_WEIGHT + "," + UPDATED_PART_WEIGHT,
            "partWeight.in=" + UPDATED_PART_WEIGHT
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByPartWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where partWeight is not null
        defaultNtQuotePartInformationMasterFiltering("partWeight.specified=true", "partWeight.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByPartWeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where partWeight is greater than or equal to
        defaultNtQuotePartInformationMasterFiltering(
            "partWeight.greaterThanOrEqual=" + DEFAULT_PART_WEIGHT,
            "partWeight.greaterThanOrEqual=" + UPDATED_PART_WEIGHT
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByPartWeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where partWeight is less than or equal to
        defaultNtQuotePartInformationMasterFiltering(
            "partWeight.lessThanOrEqual=" + DEFAULT_PART_WEIGHT,
            "partWeight.lessThanOrEqual=" + SMALLER_PART_WEIGHT
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByPartWeightIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where partWeight is less than
        defaultNtQuotePartInformationMasterFiltering(
            "partWeight.lessThan=" + UPDATED_PART_WEIGHT,
            "partWeight.lessThan=" + DEFAULT_PART_WEIGHT
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByPartWeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where partWeight is greater than
        defaultNtQuotePartInformationMasterFiltering(
            "partWeight.greaterThan=" + SMALLER_PART_WEIGHT,
            "partWeight.greaterThan=" + DEFAULT_PART_WEIGHT
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByMaterialTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where materialType equals to
        defaultNtQuotePartInformationMasterFiltering(
            "materialType.equals=" + DEFAULT_MATERIAL_TYPE,
            "materialType.equals=" + UPDATED_MATERIAL_TYPE
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByMaterialTypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where materialType in
        defaultNtQuotePartInformationMasterFiltering(
            "materialType.in=" + DEFAULT_MATERIAL_TYPE + "," + UPDATED_MATERIAL_TYPE,
            "materialType.in=" + UPDATED_MATERIAL_TYPE
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByMaterialTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where materialType is not null
        defaultNtQuotePartInformationMasterFiltering("materialType.specified=true", "materialType.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByMaterialTypeContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where materialType contains
        defaultNtQuotePartInformationMasterFiltering(
            "materialType.contains=" + DEFAULT_MATERIAL_TYPE,
            "materialType.contains=" + UPDATED_MATERIAL_TYPE
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByMaterialTypeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where materialType does not contain
        defaultNtQuotePartInformationMasterFiltering(
            "materialType.doesNotContain=" + UPDATED_MATERIAL_TYPE,
            "materialType.doesNotContain=" + DEFAULT_MATERIAL_TYPE
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByMaterialCostIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where materialCost equals to
        defaultNtQuotePartInformationMasterFiltering(
            "materialCost.equals=" + DEFAULT_MATERIAL_COST,
            "materialCost.equals=" + UPDATED_MATERIAL_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByMaterialCostIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where materialCost in
        defaultNtQuotePartInformationMasterFiltering(
            "materialCost.in=" + DEFAULT_MATERIAL_COST + "," + UPDATED_MATERIAL_COST,
            "materialCost.in=" + UPDATED_MATERIAL_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByMaterialCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where materialCost is not null
        defaultNtQuotePartInformationMasterFiltering("materialCost.specified=true", "materialCost.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByMaterialCostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where materialCost is greater than or equal to
        defaultNtQuotePartInformationMasterFiltering(
            "materialCost.greaterThanOrEqual=" + DEFAULT_MATERIAL_COST,
            "materialCost.greaterThanOrEqual=" + UPDATED_MATERIAL_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByMaterialCostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where materialCost is less than or equal to
        defaultNtQuotePartInformationMasterFiltering(
            "materialCost.lessThanOrEqual=" + DEFAULT_MATERIAL_COST,
            "materialCost.lessThanOrEqual=" + SMALLER_MATERIAL_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByMaterialCostIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where materialCost is less than
        defaultNtQuotePartInformationMasterFiltering(
            "materialCost.lessThan=" + UPDATED_MATERIAL_COST,
            "materialCost.lessThan=" + DEFAULT_MATERIAL_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByMaterialCostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where materialCost is greater than
        defaultNtQuotePartInformationMasterFiltering(
            "materialCost.greaterThan=" + SMALLER_MATERIAL_COST,
            "materialCost.greaterThan=" + DEFAULT_MATERIAL_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByExtendedMaterialCostPerIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where extendedMaterialCostPer equals to
        defaultNtQuotePartInformationMasterFiltering(
            "extendedMaterialCostPer.equals=" + DEFAULT_EXTENDED_MATERIAL_COST_PER,
            "extendedMaterialCostPer.equals=" + UPDATED_EXTENDED_MATERIAL_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByExtendedMaterialCostPerIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where extendedMaterialCostPer in
        defaultNtQuotePartInformationMasterFiltering(
            "extendedMaterialCostPer.in=" + DEFAULT_EXTENDED_MATERIAL_COST_PER + "," + UPDATED_EXTENDED_MATERIAL_COST_PER,
            "extendedMaterialCostPer.in=" + UPDATED_EXTENDED_MATERIAL_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByExtendedMaterialCostPerIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where extendedMaterialCostPer is not null
        defaultNtQuotePartInformationMasterFiltering("extendedMaterialCostPer.specified=true", "extendedMaterialCostPer.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByExtendedMaterialCostPerIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where extendedMaterialCostPer is greater than or equal to
        defaultNtQuotePartInformationMasterFiltering(
            "extendedMaterialCostPer.greaterThanOrEqual=" + DEFAULT_EXTENDED_MATERIAL_COST_PER,
            "extendedMaterialCostPer.greaterThanOrEqual=" + UPDATED_EXTENDED_MATERIAL_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByExtendedMaterialCostPerIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where extendedMaterialCostPer is less than or equal to
        defaultNtQuotePartInformationMasterFiltering(
            "extendedMaterialCostPer.lessThanOrEqual=" + DEFAULT_EXTENDED_MATERIAL_COST_PER,
            "extendedMaterialCostPer.lessThanOrEqual=" + SMALLER_EXTENDED_MATERIAL_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByExtendedMaterialCostPerIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where extendedMaterialCostPer is less than
        defaultNtQuotePartInformationMasterFiltering(
            "extendedMaterialCostPer.lessThan=" + UPDATED_EXTENDED_MATERIAL_COST_PER,
            "extendedMaterialCostPer.lessThan=" + DEFAULT_EXTENDED_MATERIAL_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByExtendedMaterialCostPerIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where extendedMaterialCostPer is greater than
        defaultNtQuotePartInformationMasterFiltering(
            "extendedMaterialCostPer.greaterThan=" + SMALLER_EXTENDED_MATERIAL_COST_PER,
            "extendedMaterialCostPer.greaterThan=" + DEFAULT_EXTENDED_MATERIAL_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByExternalMachineCostPerIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where externalMachineCostPer equals to
        defaultNtQuotePartInformationMasterFiltering(
            "externalMachineCostPer.equals=" + DEFAULT_EXTERNAL_MACHINE_COST_PER,
            "externalMachineCostPer.equals=" + UPDATED_EXTERNAL_MACHINE_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByExternalMachineCostPerIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where externalMachineCostPer in
        defaultNtQuotePartInformationMasterFiltering(
            "externalMachineCostPer.in=" + DEFAULT_EXTERNAL_MACHINE_COST_PER + "," + UPDATED_EXTERNAL_MACHINE_COST_PER,
            "externalMachineCostPer.in=" + UPDATED_EXTERNAL_MACHINE_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByExternalMachineCostPerIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where externalMachineCostPer is not null
        defaultNtQuotePartInformationMasterFiltering("externalMachineCostPer.specified=true", "externalMachineCostPer.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByExternalMachineCostPerIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where externalMachineCostPer is greater than or equal to
        defaultNtQuotePartInformationMasterFiltering(
            "externalMachineCostPer.greaterThanOrEqual=" + DEFAULT_EXTERNAL_MACHINE_COST_PER,
            "externalMachineCostPer.greaterThanOrEqual=" + UPDATED_EXTERNAL_MACHINE_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByExternalMachineCostPerIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where externalMachineCostPer is less than or equal to
        defaultNtQuotePartInformationMasterFiltering(
            "externalMachineCostPer.lessThanOrEqual=" + DEFAULT_EXTERNAL_MACHINE_COST_PER,
            "externalMachineCostPer.lessThanOrEqual=" + SMALLER_EXTERNAL_MACHINE_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByExternalMachineCostPerIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where externalMachineCostPer is less than
        defaultNtQuotePartInformationMasterFiltering(
            "externalMachineCostPer.lessThan=" + UPDATED_EXTERNAL_MACHINE_COST_PER,
            "externalMachineCostPer.lessThan=" + DEFAULT_EXTERNAL_MACHINE_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByExternalMachineCostPerIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where externalMachineCostPer is greater than
        defaultNtQuotePartInformationMasterFiltering(
            "externalMachineCostPer.greaterThan=" + SMALLER_EXTERNAL_MACHINE_COST_PER,
            "externalMachineCostPer.greaterThan=" + DEFAULT_EXTERNAL_MACHINE_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByPurchaseComponentCostIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where purchaseComponentCost equals to
        defaultNtQuotePartInformationMasterFiltering(
            "purchaseComponentCost.equals=" + DEFAULT_PURCHASE_COMPONENT_COST,
            "purchaseComponentCost.equals=" + UPDATED_PURCHASE_COMPONENT_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByPurchaseComponentCostIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where purchaseComponentCost in
        defaultNtQuotePartInformationMasterFiltering(
            "purchaseComponentCost.in=" + DEFAULT_PURCHASE_COMPONENT_COST + "," + UPDATED_PURCHASE_COMPONENT_COST,
            "purchaseComponentCost.in=" + UPDATED_PURCHASE_COMPONENT_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByPurchaseComponentCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where purchaseComponentCost is not null
        defaultNtQuotePartInformationMasterFiltering("purchaseComponentCost.specified=true", "purchaseComponentCost.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByPurchaseComponentCostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where purchaseComponentCost is greater than or equal to
        defaultNtQuotePartInformationMasterFiltering(
            "purchaseComponentCost.greaterThanOrEqual=" + DEFAULT_PURCHASE_COMPONENT_COST,
            "purchaseComponentCost.greaterThanOrEqual=" + UPDATED_PURCHASE_COMPONENT_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByPurchaseComponentCostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where purchaseComponentCost is less than or equal to
        defaultNtQuotePartInformationMasterFiltering(
            "purchaseComponentCost.lessThanOrEqual=" + DEFAULT_PURCHASE_COMPONENT_COST,
            "purchaseComponentCost.lessThanOrEqual=" + SMALLER_PURCHASE_COMPONENT_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByPurchaseComponentCostIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where purchaseComponentCost is less than
        defaultNtQuotePartInformationMasterFiltering(
            "purchaseComponentCost.lessThan=" + UPDATED_PURCHASE_COMPONENT_COST,
            "purchaseComponentCost.lessThan=" + DEFAULT_PURCHASE_COMPONENT_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByPurchaseComponentCostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where purchaseComponentCost is greater than
        defaultNtQuotePartInformationMasterFiltering(
            "purchaseComponentCost.greaterThan=" + SMALLER_PURCHASE_COMPONENT_COST,
            "purchaseComponentCost.greaterThan=" + DEFAULT_PURCHASE_COMPONENT_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersBySecondaryExternalOperationCostIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where secondaryExternalOperationCost equals to
        defaultNtQuotePartInformationMasterFiltering(
            "secondaryExternalOperationCost.equals=" + DEFAULT_SECONDARY_EXTERNAL_OPERATION_COST,
            "secondaryExternalOperationCost.equals=" + UPDATED_SECONDARY_EXTERNAL_OPERATION_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersBySecondaryExternalOperationCostIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where secondaryExternalOperationCost in
        defaultNtQuotePartInformationMasterFiltering(
            "secondaryExternalOperationCost.in=" +
            DEFAULT_SECONDARY_EXTERNAL_OPERATION_COST +
            "," +
            UPDATED_SECONDARY_EXTERNAL_OPERATION_COST,
            "secondaryExternalOperationCost.in=" + UPDATED_SECONDARY_EXTERNAL_OPERATION_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersBySecondaryExternalOperationCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where secondaryExternalOperationCost is not null
        defaultNtQuotePartInformationMasterFiltering(
            "secondaryExternalOperationCost.specified=true",
            "secondaryExternalOperationCost.specified=false"
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersBySecondaryExternalOperationCostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where secondaryExternalOperationCost is greater than or equal to
        defaultNtQuotePartInformationMasterFiltering(
            "secondaryExternalOperationCost.greaterThanOrEqual=" + DEFAULT_SECONDARY_EXTERNAL_OPERATION_COST,
            "secondaryExternalOperationCost.greaterThanOrEqual=" + UPDATED_SECONDARY_EXTERNAL_OPERATION_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersBySecondaryExternalOperationCostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where secondaryExternalOperationCost is less than or equal to
        defaultNtQuotePartInformationMasterFiltering(
            "secondaryExternalOperationCost.lessThanOrEqual=" + DEFAULT_SECONDARY_EXTERNAL_OPERATION_COST,
            "secondaryExternalOperationCost.lessThanOrEqual=" + SMALLER_SECONDARY_EXTERNAL_OPERATION_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersBySecondaryExternalOperationCostIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where secondaryExternalOperationCost is less than
        defaultNtQuotePartInformationMasterFiltering(
            "secondaryExternalOperationCost.lessThan=" + UPDATED_SECONDARY_EXTERNAL_OPERATION_COST,
            "secondaryExternalOperationCost.lessThan=" + DEFAULT_SECONDARY_EXTERNAL_OPERATION_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersBySecondaryExternalOperationCostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where secondaryExternalOperationCost is greater than
        defaultNtQuotePartInformationMasterFiltering(
            "secondaryExternalOperationCost.greaterThan=" + SMALLER_SECONDARY_EXTERNAL_OPERATION_COST,
            "secondaryExternalOperationCost.greaterThan=" + DEFAULT_SECONDARY_EXTERNAL_OPERATION_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByOverheadIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where overhead equals to
        defaultNtQuotePartInformationMasterFiltering("overhead.equals=" + DEFAULT_OVERHEAD, "overhead.equals=" + UPDATED_OVERHEAD);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByOverheadIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where overhead in
        defaultNtQuotePartInformationMasterFiltering(
            "overhead.in=" + DEFAULT_OVERHEAD + "," + UPDATED_OVERHEAD,
            "overhead.in=" + UPDATED_OVERHEAD
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByOverheadIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where overhead is not null
        defaultNtQuotePartInformationMasterFiltering("overhead.specified=true", "overhead.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByOverheadIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where overhead is greater than or equal to
        defaultNtQuotePartInformationMasterFiltering(
            "overhead.greaterThanOrEqual=" + DEFAULT_OVERHEAD,
            "overhead.greaterThanOrEqual=" + UPDATED_OVERHEAD
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByOverheadIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where overhead is less than or equal to
        defaultNtQuotePartInformationMasterFiltering(
            "overhead.lessThanOrEqual=" + DEFAULT_OVERHEAD,
            "overhead.lessThanOrEqual=" + SMALLER_OVERHEAD
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByOverheadIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where overhead is less than
        defaultNtQuotePartInformationMasterFiltering("overhead.lessThan=" + UPDATED_OVERHEAD, "overhead.lessThan=" + DEFAULT_OVERHEAD);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByOverheadIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where overhead is greater than
        defaultNtQuotePartInformationMasterFiltering(
            "overhead.greaterThan=" + SMALLER_OVERHEAD,
            "overhead.greaterThan=" + DEFAULT_OVERHEAD
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByPackLogisticCostPerIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where packLogisticCostPer equals to
        defaultNtQuotePartInformationMasterFiltering(
            "packLogisticCostPer.equals=" + DEFAULT_PACK_LOGISTIC_COST_PER,
            "packLogisticCostPer.equals=" + UPDATED_PACK_LOGISTIC_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByPackLogisticCostPerIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where packLogisticCostPer in
        defaultNtQuotePartInformationMasterFiltering(
            "packLogisticCostPer.in=" + DEFAULT_PACK_LOGISTIC_COST_PER + "," + UPDATED_PACK_LOGISTIC_COST_PER,
            "packLogisticCostPer.in=" + UPDATED_PACK_LOGISTIC_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByPackLogisticCostPerIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where packLogisticCostPer is not null
        defaultNtQuotePartInformationMasterFiltering("packLogisticCostPer.specified=true", "packLogisticCostPer.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByPackLogisticCostPerIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where packLogisticCostPer is greater than or equal to
        defaultNtQuotePartInformationMasterFiltering(
            "packLogisticCostPer.greaterThanOrEqual=" + DEFAULT_PACK_LOGISTIC_COST_PER,
            "packLogisticCostPer.greaterThanOrEqual=" + UPDATED_PACK_LOGISTIC_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByPackLogisticCostPerIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where packLogisticCostPer is less than or equal to
        defaultNtQuotePartInformationMasterFiltering(
            "packLogisticCostPer.lessThanOrEqual=" + DEFAULT_PACK_LOGISTIC_COST_PER,
            "packLogisticCostPer.lessThanOrEqual=" + SMALLER_PACK_LOGISTIC_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByPackLogisticCostPerIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where packLogisticCostPer is less than
        defaultNtQuotePartInformationMasterFiltering(
            "packLogisticCostPer.lessThan=" + UPDATED_PACK_LOGISTIC_COST_PER,
            "packLogisticCostPer.lessThan=" + DEFAULT_PACK_LOGISTIC_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByPackLogisticCostPerIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where packLogisticCostPer is greater than
        defaultNtQuotePartInformationMasterFiltering(
            "packLogisticCostPer.greaterThan=" + SMALLER_PACK_LOGISTIC_COST_PER,
            "packLogisticCostPer.greaterThan=" + DEFAULT_PACK_LOGISTIC_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByMachineSizeTonsIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where machineSizeTons equals to
        defaultNtQuotePartInformationMasterFiltering(
            "machineSizeTons.equals=" + DEFAULT_MACHINE_SIZE_TONS,
            "machineSizeTons.equals=" + UPDATED_MACHINE_SIZE_TONS
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByMachineSizeTonsIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where machineSizeTons in
        defaultNtQuotePartInformationMasterFiltering(
            "machineSizeTons.in=" + DEFAULT_MACHINE_SIZE_TONS + "," + UPDATED_MACHINE_SIZE_TONS,
            "machineSizeTons.in=" + UPDATED_MACHINE_SIZE_TONS
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByMachineSizeTonsIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where machineSizeTons is not null
        defaultNtQuotePartInformationMasterFiltering("machineSizeTons.specified=true", "machineSizeTons.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByMachineSizeTonsContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where machineSizeTons contains
        defaultNtQuotePartInformationMasterFiltering(
            "machineSizeTons.contains=" + DEFAULT_MACHINE_SIZE_TONS,
            "machineSizeTons.contains=" + UPDATED_MACHINE_SIZE_TONS
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByMachineSizeTonsNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where machineSizeTons does not contain
        defaultNtQuotePartInformationMasterFiltering(
            "machineSizeTons.doesNotContain=" + UPDATED_MACHINE_SIZE_TONS,
            "machineSizeTons.doesNotContain=" + DEFAULT_MACHINE_SIZE_TONS
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByNumberOfCavitiesIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where numberOfCavities equals to
        defaultNtQuotePartInformationMasterFiltering(
            "numberOfCavities.equals=" + DEFAULT_NUMBER_OF_CAVITIES,
            "numberOfCavities.equals=" + UPDATED_NUMBER_OF_CAVITIES
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByNumberOfCavitiesIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where numberOfCavities in
        defaultNtQuotePartInformationMasterFiltering(
            "numberOfCavities.in=" + DEFAULT_NUMBER_OF_CAVITIES + "," + UPDATED_NUMBER_OF_CAVITIES,
            "numberOfCavities.in=" + UPDATED_NUMBER_OF_CAVITIES
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByNumberOfCavitiesIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where numberOfCavities is not null
        defaultNtQuotePartInformationMasterFiltering("numberOfCavities.specified=true", "numberOfCavities.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByNumberOfCavitiesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where numberOfCavities is greater than or equal to
        defaultNtQuotePartInformationMasterFiltering(
            "numberOfCavities.greaterThanOrEqual=" + DEFAULT_NUMBER_OF_CAVITIES,
            "numberOfCavities.greaterThanOrEqual=" + UPDATED_NUMBER_OF_CAVITIES
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByNumberOfCavitiesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where numberOfCavities is less than or equal to
        defaultNtQuotePartInformationMasterFiltering(
            "numberOfCavities.lessThanOrEqual=" + DEFAULT_NUMBER_OF_CAVITIES,
            "numberOfCavities.lessThanOrEqual=" + SMALLER_NUMBER_OF_CAVITIES
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByNumberOfCavitiesIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where numberOfCavities is less than
        defaultNtQuotePartInformationMasterFiltering(
            "numberOfCavities.lessThan=" + UPDATED_NUMBER_OF_CAVITIES,
            "numberOfCavities.lessThan=" + DEFAULT_NUMBER_OF_CAVITIES
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByNumberOfCavitiesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where numberOfCavities is greater than
        defaultNtQuotePartInformationMasterFiltering(
            "numberOfCavities.greaterThan=" + SMALLER_NUMBER_OF_CAVITIES,
            "numberOfCavities.greaterThan=" + DEFAULT_NUMBER_OF_CAVITIES
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByCycleTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where cycleTime equals to
        defaultNtQuotePartInformationMasterFiltering("cycleTime.equals=" + DEFAULT_CYCLE_TIME, "cycleTime.equals=" + UPDATED_CYCLE_TIME);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByCycleTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where cycleTime in
        defaultNtQuotePartInformationMasterFiltering(
            "cycleTime.in=" + DEFAULT_CYCLE_TIME + "," + UPDATED_CYCLE_TIME,
            "cycleTime.in=" + UPDATED_CYCLE_TIME
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByCycleTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where cycleTime is not null
        defaultNtQuotePartInformationMasterFiltering("cycleTime.specified=true", "cycleTime.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByCycleTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where cycleTime is greater than or equal to
        defaultNtQuotePartInformationMasterFiltering(
            "cycleTime.greaterThanOrEqual=" + DEFAULT_CYCLE_TIME,
            "cycleTime.greaterThanOrEqual=" + UPDATED_CYCLE_TIME
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByCycleTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where cycleTime is less than or equal to
        defaultNtQuotePartInformationMasterFiltering(
            "cycleTime.lessThanOrEqual=" + DEFAULT_CYCLE_TIME,
            "cycleTime.lessThanOrEqual=" + SMALLER_CYCLE_TIME
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByCycleTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where cycleTime is less than
        defaultNtQuotePartInformationMasterFiltering(
            "cycleTime.lessThan=" + UPDATED_CYCLE_TIME,
            "cycleTime.lessThan=" + DEFAULT_CYCLE_TIME
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByCycleTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where cycleTime is greater than
        defaultNtQuotePartInformationMasterFiltering(
            "cycleTime.greaterThan=" + SMALLER_CYCLE_TIME,
            "cycleTime.greaterThan=" + DEFAULT_CYCLE_TIME
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByPerUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where perUnit equals to
        defaultNtQuotePartInformationMasterFiltering("perUnit.equals=" + DEFAULT_PER_UNIT, "perUnit.equals=" + UPDATED_PER_UNIT);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByPerUnitIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where perUnit in
        defaultNtQuotePartInformationMasterFiltering(
            "perUnit.in=" + DEFAULT_PER_UNIT + "," + UPDATED_PER_UNIT,
            "perUnit.in=" + UPDATED_PER_UNIT
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByPerUnitIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where perUnit is not null
        defaultNtQuotePartInformationMasterFiltering("perUnit.specified=true", "perUnit.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByPerUnitIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where perUnit is greater than or equal to
        defaultNtQuotePartInformationMasterFiltering(
            "perUnit.greaterThanOrEqual=" + DEFAULT_PER_UNIT,
            "perUnit.greaterThanOrEqual=" + UPDATED_PER_UNIT
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByPerUnitIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where perUnit is less than or equal to
        defaultNtQuotePartInformationMasterFiltering(
            "perUnit.lessThanOrEqual=" + DEFAULT_PER_UNIT,
            "perUnit.lessThanOrEqual=" + SMALLER_PER_UNIT
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByPerUnitIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where perUnit is less than
        defaultNtQuotePartInformationMasterFiltering("perUnit.lessThan=" + UPDATED_PER_UNIT, "perUnit.lessThan=" + DEFAULT_PER_UNIT);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByPerUnitIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where perUnit is greater than
        defaultNtQuotePartInformationMasterFiltering("perUnit.greaterThan=" + SMALLER_PER_UNIT, "perUnit.greaterThan=" + DEFAULT_PER_UNIT);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByTotalPricePerChinaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where totalPricePerChina equals to
        defaultNtQuotePartInformationMasterFiltering(
            "totalPricePerChina.equals=" + DEFAULT_TOTAL_PRICE_PER_CHINA,
            "totalPricePerChina.equals=" + UPDATED_TOTAL_PRICE_PER_CHINA
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByTotalPricePerChinaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where totalPricePerChina in
        defaultNtQuotePartInformationMasterFiltering(
            "totalPricePerChina.in=" + DEFAULT_TOTAL_PRICE_PER_CHINA + "," + UPDATED_TOTAL_PRICE_PER_CHINA,
            "totalPricePerChina.in=" + UPDATED_TOTAL_PRICE_PER_CHINA
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByTotalPricePerChinaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where totalPricePerChina is not null
        defaultNtQuotePartInformationMasterFiltering("totalPricePerChina.specified=true", "totalPricePerChina.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByTotalPricePerChinaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where totalPricePerChina is greater than or equal to
        defaultNtQuotePartInformationMasterFiltering(
            "totalPricePerChina.greaterThanOrEqual=" + DEFAULT_TOTAL_PRICE_PER_CHINA,
            "totalPricePerChina.greaterThanOrEqual=" + UPDATED_TOTAL_PRICE_PER_CHINA
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByTotalPricePerChinaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where totalPricePerChina is less than or equal to
        defaultNtQuotePartInformationMasterFiltering(
            "totalPricePerChina.lessThanOrEqual=" + DEFAULT_TOTAL_PRICE_PER_CHINA,
            "totalPricePerChina.lessThanOrEqual=" + SMALLER_TOTAL_PRICE_PER_CHINA
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByTotalPricePerChinaIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where totalPricePerChina is less than
        defaultNtQuotePartInformationMasterFiltering(
            "totalPricePerChina.lessThan=" + UPDATED_TOTAL_PRICE_PER_CHINA,
            "totalPricePerChina.lessThan=" + DEFAULT_TOTAL_PRICE_PER_CHINA
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByTotalPricePerChinaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where totalPricePerChina is greater than
        defaultNtQuotePartInformationMasterFiltering(
            "totalPricePerChina.greaterThan=" + SMALLER_TOTAL_PRICE_PER_CHINA,
            "totalPricePerChina.greaterThan=" + DEFAULT_TOTAL_PRICE_PER_CHINA
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByTotalPriceBudgetIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where totalPriceBudget equals to
        defaultNtQuotePartInformationMasterFiltering(
            "totalPriceBudget.equals=" + DEFAULT_TOTAL_PRICE_BUDGET,
            "totalPriceBudget.equals=" + UPDATED_TOTAL_PRICE_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByTotalPriceBudgetIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where totalPriceBudget in
        defaultNtQuotePartInformationMasterFiltering(
            "totalPriceBudget.in=" + DEFAULT_TOTAL_PRICE_BUDGET + "," + UPDATED_TOTAL_PRICE_BUDGET,
            "totalPriceBudget.in=" + UPDATED_TOTAL_PRICE_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByTotalPriceBudgetIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where totalPriceBudget is not null
        defaultNtQuotePartInformationMasterFiltering("totalPriceBudget.specified=true", "totalPriceBudget.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByTotalPriceBudgetIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where totalPriceBudget is greater than or equal to
        defaultNtQuotePartInformationMasterFiltering(
            "totalPriceBudget.greaterThanOrEqual=" + DEFAULT_TOTAL_PRICE_BUDGET,
            "totalPriceBudget.greaterThanOrEqual=" + UPDATED_TOTAL_PRICE_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByTotalPriceBudgetIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where totalPriceBudget is less than or equal to
        defaultNtQuotePartInformationMasterFiltering(
            "totalPriceBudget.lessThanOrEqual=" + DEFAULT_TOTAL_PRICE_BUDGET,
            "totalPriceBudget.lessThanOrEqual=" + SMALLER_TOTAL_PRICE_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByTotalPriceBudgetIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where totalPriceBudget is less than
        defaultNtQuotePartInformationMasterFiltering(
            "totalPriceBudget.lessThan=" + UPDATED_TOTAL_PRICE_BUDGET,
            "totalPriceBudget.lessThan=" + DEFAULT_TOTAL_PRICE_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByTotalPriceBudgetIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where totalPriceBudget is greater than
        defaultNtQuotePartInformationMasterFiltering(
            "totalPriceBudget.greaterThan=" + SMALLER_TOTAL_PRICE_BUDGET,
            "totalPriceBudget.greaterThan=" + DEFAULT_TOTAL_PRICE_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByGrainBudgetIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where grainBudget equals to
        defaultNtQuotePartInformationMasterFiltering(
            "grainBudget.equals=" + DEFAULT_GRAIN_BUDGET,
            "grainBudget.equals=" + UPDATED_GRAIN_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByGrainBudgetIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where grainBudget in
        defaultNtQuotePartInformationMasterFiltering(
            "grainBudget.in=" + DEFAULT_GRAIN_BUDGET + "," + UPDATED_GRAIN_BUDGET,
            "grainBudget.in=" + UPDATED_GRAIN_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByGrainBudgetIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where grainBudget is not null
        defaultNtQuotePartInformationMasterFiltering("grainBudget.specified=true", "grainBudget.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByGrainBudgetIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where grainBudget is greater than or equal to
        defaultNtQuotePartInformationMasterFiltering(
            "grainBudget.greaterThanOrEqual=" + DEFAULT_GRAIN_BUDGET,
            "grainBudget.greaterThanOrEqual=" + UPDATED_GRAIN_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByGrainBudgetIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where grainBudget is less than or equal to
        defaultNtQuotePartInformationMasterFiltering(
            "grainBudget.lessThanOrEqual=" + DEFAULT_GRAIN_BUDGET,
            "grainBudget.lessThanOrEqual=" + SMALLER_GRAIN_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByGrainBudgetIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where grainBudget is less than
        defaultNtQuotePartInformationMasterFiltering(
            "grainBudget.lessThan=" + UPDATED_GRAIN_BUDGET,
            "grainBudget.lessThan=" + DEFAULT_GRAIN_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByGrainBudgetIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where grainBudget is greater than
        defaultNtQuotePartInformationMasterFiltering(
            "grainBudget.greaterThan=" + SMALLER_GRAIN_BUDGET,
            "grainBudget.greaterThan=" + DEFAULT_GRAIN_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByDogatingFixtureBudgetIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where dogatingFixtureBudget equals to
        defaultNtQuotePartInformationMasterFiltering(
            "dogatingFixtureBudget.equals=" + DEFAULT_DOGATING_FIXTURE_BUDGET,
            "dogatingFixtureBudget.equals=" + UPDATED_DOGATING_FIXTURE_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByDogatingFixtureBudgetIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where dogatingFixtureBudget in
        defaultNtQuotePartInformationMasterFiltering(
            "dogatingFixtureBudget.in=" + DEFAULT_DOGATING_FIXTURE_BUDGET + "," + UPDATED_DOGATING_FIXTURE_BUDGET,
            "dogatingFixtureBudget.in=" + UPDATED_DOGATING_FIXTURE_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByDogatingFixtureBudgetIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where dogatingFixtureBudget is not null
        defaultNtQuotePartInformationMasterFiltering("dogatingFixtureBudget.specified=true", "dogatingFixtureBudget.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByDogatingFixtureBudgetIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where dogatingFixtureBudget is greater than or equal to
        defaultNtQuotePartInformationMasterFiltering(
            "dogatingFixtureBudget.greaterThanOrEqual=" + DEFAULT_DOGATING_FIXTURE_BUDGET,
            "dogatingFixtureBudget.greaterThanOrEqual=" + UPDATED_DOGATING_FIXTURE_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByDogatingFixtureBudgetIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where dogatingFixtureBudget is less than or equal to
        defaultNtQuotePartInformationMasterFiltering(
            "dogatingFixtureBudget.lessThanOrEqual=" + DEFAULT_DOGATING_FIXTURE_BUDGET,
            "dogatingFixtureBudget.lessThanOrEqual=" + SMALLER_DOGATING_FIXTURE_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByDogatingFixtureBudgetIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where dogatingFixtureBudget is less than
        defaultNtQuotePartInformationMasterFiltering(
            "dogatingFixtureBudget.lessThan=" + UPDATED_DOGATING_FIXTURE_BUDGET,
            "dogatingFixtureBudget.lessThan=" + DEFAULT_DOGATING_FIXTURE_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByDogatingFixtureBudgetIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where dogatingFixtureBudget is greater than
        defaultNtQuotePartInformationMasterFiltering(
            "dogatingFixtureBudget.greaterThan=" + SMALLER_DOGATING_FIXTURE_BUDGET,
            "dogatingFixtureBudget.greaterThan=" + DEFAULT_DOGATING_FIXTURE_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByGaugeBudgetIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where gaugeBudget equals to
        defaultNtQuotePartInformationMasterFiltering(
            "gaugeBudget.equals=" + DEFAULT_GAUGE_BUDGET,
            "gaugeBudget.equals=" + UPDATED_GAUGE_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByGaugeBudgetIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where gaugeBudget in
        defaultNtQuotePartInformationMasterFiltering(
            "gaugeBudget.in=" + DEFAULT_GAUGE_BUDGET + "," + UPDATED_GAUGE_BUDGET,
            "gaugeBudget.in=" + UPDATED_GAUGE_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByGaugeBudgetIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where gaugeBudget is not null
        defaultNtQuotePartInformationMasterFiltering("gaugeBudget.specified=true", "gaugeBudget.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByGaugeBudgetIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where gaugeBudget is greater than or equal to
        defaultNtQuotePartInformationMasterFiltering(
            "gaugeBudget.greaterThanOrEqual=" + DEFAULT_GAUGE_BUDGET,
            "gaugeBudget.greaterThanOrEqual=" + UPDATED_GAUGE_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByGaugeBudgetIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where gaugeBudget is less than or equal to
        defaultNtQuotePartInformationMasterFiltering(
            "gaugeBudget.lessThanOrEqual=" + DEFAULT_GAUGE_BUDGET,
            "gaugeBudget.lessThanOrEqual=" + SMALLER_GAUGE_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByGaugeBudgetIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where gaugeBudget is less than
        defaultNtQuotePartInformationMasterFiltering(
            "gaugeBudget.lessThan=" + UPDATED_GAUGE_BUDGET,
            "gaugeBudget.lessThan=" + DEFAULT_GAUGE_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByGaugeBudgetIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where gaugeBudget is greater than
        defaultNtQuotePartInformationMasterFiltering(
            "gaugeBudget.greaterThan=" + SMALLER_GAUGE_BUDGET,
            "gaugeBudget.greaterThan=" + DEFAULT_GAUGE_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByEoatIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where eoat equals to
        defaultNtQuotePartInformationMasterFiltering("eoat.equals=" + DEFAULT_EOAT, "eoat.equals=" + UPDATED_EOAT);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByEoatIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where eoat in
        defaultNtQuotePartInformationMasterFiltering("eoat.in=" + DEFAULT_EOAT + "," + UPDATED_EOAT, "eoat.in=" + UPDATED_EOAT);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByEoatIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where eoat is not null
        defaultNtQuotePartInformationMasterFiltering("eoat.specified=true", "eoat.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByEoatIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where eoat is greater than or equal to
        defaultNtQuotePartInformationMasterFiltering("eoat.greaterThanOrEqual=" + DEFAULT_EOAT, "eoat.greaterThanOrEqual=" + UPDATED_EOAT);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByEoatIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where eoat is less than or equal to
        defaultNtQuotePartInformationMasterFiltering("eoat.lessThanOrEqual=" + DEFAULT_EOAT, "eoat.lessThanOrEqual=" + SMALLER_EOAT);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByEoatIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where eoat is less than
        defaultNtQuotePartInformationMasterFiltering("eoat.lessThan=" + UPDATED_EOAT, "eoat.lessThan=" + DEFAULT_EOAT);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByEoatIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where eoat is greater than
        defaultNtQuotePartInformationMasterFiltering("eoat.greaterThan=" + SMALLER_EOAT, "eoat.greaterThan=" + DEFAULT_EOAT);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByChinaTariffBudgetIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where chinaTariffBudget equals to
        defaultNtQuotePartInformationMasterFiltering(
            "chinaTariffBudget.equals=" + DEFAULT_CHINA_TARIFF_BUDGET,
            "chinaTariffBudget.equals=" + UPDATED_CHINA_TARIFF_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByChinaTariffBudgetIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where chinaTariffBudget in
        defaultNtQuotePartInformationMasterFiltering(
            "chinaTariffBudget.in=" + DEFAULT_CHINA_TARIFF_BUDGET + "," + UPDATED_CHINA_TARIFF_BUDGET,
            "chinaTariffBudget.in=" + UPDATED_CHINA_TARIFF_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByChinaTariffBudgetIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where chinaTariffBudget is not null
        defaultNtQuotePartInformationMasterFiltering("chinaTariffBudget.specified=true", "chinaTariffBudget.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByChinaTariffBudgetIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where chinaTariffBudget is greater than or equal to
        defaultNtQuotePartInformationMasterFiltering(
            "chinaTariffBudget.greaterThanOrEqual=" + DEFAULT_CHINA_TARIFF_BUDGET,
            "chinaTariffBudget.greaterThanOrEqual=" + UPDATED_CHINA_TARIFF_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByChinaTariffBudgetIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where chinaTariffBudget is less than or equal to
        defaultNtQuotePartInformationMasterFiltering(
            "chinaTariffBudget.lessThanOrEqual=" + DEFAULT_CHINA_TARIFF_BUDGET,
            "chinaTariffBudget.lessThanOrEqual=" + SMALLER_CHINA_TARIFF_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByChinaTariffBudgetIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where chinaTariffBudget is less than
        defaultNtQuotePartInformationMasterFiltering(
            "chinaTariffBudget.lessThan=" + UPDATED_CHINA_TARIFF_BUDGET,
            "chinaTariffBudget.lessThan=" + DEFAULT_CHINA_TARIFF_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByChinaTariffBudgetIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where chinaTariffBudget is greater than
        defaultNtQuotePartInformationMasterFiltering(
            "chinaTariffBudget.greaterThan=" + SMALLER_CHINA_TARIFF_BUDGET,
            "chinaTariffBudget.greaterThan=" + DEFAULT_CHINA_TARIFF_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByTotalToolingBudgetIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where totalToolingBudget equals to
        defaultNtQuotePartInformationMasterFiltering(
            "totalToolingBudget.equals=" + DEFAULT_TOTAL_TOOLING_BUDGET,
            "totalToolingBudget.equals=" + UPDATED_TOTAL_TOOLING_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByTotalToolingBudgetIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where totalToolingBudget in
        defaultNtQuotePartInformationMasterFiltering(
            "totalToolingBudget.in=" + DEFAULT_TOTAL_TOOLING_BUDGET + "," + UPDATED_TOTAL_TOOLING_BUDGET,
            "totalToolingBudget.in=" + UPDATED_TOTAL_TOOLING_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByTotalToolingBudgetIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where totalToolingBudget is not null
        defaultNtQuotePartInformationMasterFiltering("totalToolingBudget.specified=true", "totalToolingBudget.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByTotalToolingBudgetIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where totalToolingBudget is greater than or equal to
        defaultNtQuotePartInformationMasterFiltering(
            "totalToolingBudget.greaterThanOrEqual=" + DEFAULT_TOTAL_TOOLING_BUDGET,
            "totalToolingBudget.greaterThanOrEqual=" + UPDATED_TOTAL_TOOLING_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByTotalToolingBudgetIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where totalToolingBudget is less than or equal to
        defaultNtQuotePartInformationMasterFiltering(
            "totalToolingBudget.lessThanOrEqual=" + DEFAULT_TOTAL_TOOLING_BUDGET,
            "totalToolingBudget.lessThanOrEqual=" + SMALLER_TOTAL_TOOLING_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByTotalToolingBudgetIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where totalToolingBudget is less than
        defaultNtQuotePartInformationMasterFiltering(
            "totalToolingBudget.lessThan=" + UPDATED_TOTAL_TOOLING_BUDGET,
            "totalToolingBudget.lessThan=" + DEFAULT_TOTAL_TOOLING_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByTotalToolingBudgetIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where totalToolingBudget is greater than
        defaultNtQuotePartInformationMasterFiltering(
            "totalToolingBudget.greaterThan=" + SMALLER_TOTAL_TOOLING_BUDGET,
            "totalToolingBudget.greaterThan=" + DEFAULT_TOTAL_TOOLING_BUDGET
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByLeadTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where leadTime equals to
        defaultNtQuotePartInformationMasterFiltering("leadTime.equals=" + DEFAULT_LEAD_TIME, "leadTime.equals=" + UPDATED_LEAD_TIME);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByLeadTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where leadTime in
        defaultNtQuotePartInformationMasterFiltering(
            "leadTime.in=" + DEFAULT_LEAD_TIME + "," + UPDATED_LEAD_TIME,
            "leadTime.in=" + UPDATED_LEAD_TIME
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByLeadTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where leadTime is not null
        defaultNtQuotePartInformationMasterFiltering("leadTime.specified=true", "leadTime.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByLeadTimeContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where leadTime contains
        defaultNtQuotePartInformationMasterFiltering("leadTime.contains=" + DEFAULT_LEAD_TIME, "leadTime.contains=" + UPDATED_LEAD_TIME);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByLeadTimeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where leadTime does not contain
        defaultNtQuotePartInformationMasterFiltering(
            "leadTime.doesNotContain=" + UPDATED_LEAD_TIME,
            "leadTime.doesNotContain=" + DEFAULT_LEAD_TIME
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByToolingNotesIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where toolingNotes equals to
        defaultNtQuotePartInformationMasterFiltering(
            "toolingNotes.equals=" + DEFAULT_TOOLING_NOTES,
            "toolingNotes.equals=" + UPDATED_TOOLING_NOTES
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByToolingNotesIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where toolingNotes in
        defaultNtQuotePartInformationMasterFiltering(
            "toolingNotes.in=" + DEFAULT_TOOLING_NOTES + "," + UPDATED_TOOLING_NOTES,
            "toolingNotes.in=" + UPDATED_TOOLING_NOTES
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByToolingNotesIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where toolingNotes is not null
        defaultNtQuotePartInformationMasterFiltering("toolingNotes.specified=true", "toolingNotes.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByToolingNotesContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where toolingNotes contains
        defaultNtQuotePartInformationMasterFiltering(
            "toolingNotes.contains=" + DEFAULT_TOOLING_NOTES,
            "toolingNotes.contains=" + UPDATED_TOOLING_NOTES
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByToolingNotesNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where toolingNotes does not contain
        defaultNtQuotePartInformationMasterFiltering(
            "toolingNotes.doesNotContain=" + UPDATED_TOOLING_NOTES,
            "toolingNotes.doesNotContain=" + DEFAULT_TOOLING_NOTES
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByPartDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where partDescription equals to
        defaultNtQuotePartInformationMasterFiltering(
            "partDescription.equals=" + DEFAULT_PART_DESCRIPTION,
            "partDescription.equals=" + UPDATED_PART_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByPartDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where partDescription in
        defaultNtQuotePartInformationMasterFiltering(
            "partDescription.in=" + DEFAULT_PART_DESCRIPTION + "," + UPDATED_PART_DESCRIPTION,
            "partDescription.in=" + UPDATED_PART_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByPartDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where partDescription is not null
        defaultNtQuotePartInformationMasterFiltering("partDescription.specified=true", "partDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByPartDescriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where partDescription contains
        defaultNtQuotePartInformationMasterFiltering(
            "partDescription.contains=" + DEFAULT_PART_DESCRIPTION,
            "partDescription.contains=" + UPDATED_PART_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByPartDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where partDescription does not contain
        defaultNtQuotePartInformationMasterFiltering(
            "partDescription.doesNotContain=" + UPDATED_PART_DESCRIPTION,
            "partDescription.doesNotContain=" + DEFAULT_PART_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByJobIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where jobId equals to
        defaultNtQuotePartInformationMasterFiltering("jobId.equals=" + DEFAULT_JOB_ID, "jobId.equals=" + UPDATED_JOB_ID);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByJobIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where jobId in
        defaultNtQuotePartInformationMasterFiltering("jobId.in=" + DEFAULT_JOB_ID + "," + UPDATED_JOB_ID, "jobId.in=" + UPDATED_JOB_ID);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByJobIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where jobId is not null
        defaultNtQuotePartInformationMasterFiltering("jobId.specified=true", "jobId.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByJobIdContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where jobId contains
        defaultNtQuotePartInformationMasterFiltering("jobId.contains=" + DEFAULT_JOB_ID, "jobId.contains=" + UPDATED_JOB_ID);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByJobIdNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where jobId does not contain
        defaultNtQuotePartInformationMasterFiltering("jobId.doesNotContain=" + UPDATED_JOB_ID, "jobId.doesNotContain=" + DEFAULT_JOB_ID);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByMoldIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where moldId equals to
        defaultNtQuotePartInformationMasterFiltering("moldId.equals=" + DEFAULT_MOLD_ID, "moldId.equals=" + UPDATED_MOLD_ID);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByMoldIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where moldId in
        defaultNtQuotePartInformationMasterFiltering(
            "moldId.in=" + DEFAULT_MOLD_ID + "," + UPDATED_MOLD_ID,
            "moldId.in=" + UPDATED_MOLD_ID
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByMoldIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where moldId is not null
        defaultNtQuotePartInformationMasterFiltering("moldId.specified=true", "moldId.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByMoldIdContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where moldId contains
        defaultNtQuotePartInformationMasterFiltering("moldId.contains=" + DEFAULT_MOLD_ID, "moldId.contains=" + UPDATED_MOLD_ID);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByMoldIdNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where moldId does not contain
        defaultNtQuotePartInformationMasterFiltering(
            "moldId.doesNotContain=" + UPDATED_MOLD_ID,
            "moldId.doesNotContain=" + DEFAULT_MOLD_ID
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where createdBy equals to
        defaultNtQuotePartInformationMasterFiltering("createdBy.equals=" + DEFAULT_CREATED_BY, "createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where createdBy in
        defaultNtQuotePartInformationMasterFiltering(
            "createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY,
            "createdBy.in=" + UPDATED_CREATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where createdBy is not null
        defaultNtQuotePartInformationMasterFiltering("createdBy.specified=true", "createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where createdBy contains
        defaultNtQuotePartInformationMasterFiltering(
            "createdBy.contains=" + DEFAULT_CREATED_BY,
            "createdBy.contains=" + UPDATED_CREATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where createdBy does not contain
        defaultNtQuotePartInformationMasterFiltering(
            "createdBy.doesNotContain=" + UPDATED_CREATED_BY,
            "createdBy.doesNotContain=" + DEFAULT_CREATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where createdDate equals to
        defaultNtQuotePartInformationMasterFiltering(
            "createdDate.equals=" + DEFAULT_CREATED_DATE,
            "createdDate.equals=" + UPDATED_CREATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where createdDate in
        defaultNtQuotePartInformationMasterFiltering(
            "createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE,
            "createdDate.in=" + UPDATED_CREATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where createdDate is not null
        defaultNtQuotePartInformationMasterFiltering("createdDate.specified=true", "createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where updatedBy equals to
        defaultNtQuotePartInformationMasterFiltering("updatedBy.equals=" + DEFAULT_UPDATED_BY, "updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where updatedBy in
        defaultNtQuotePartInformationMasterFiltering(
            "updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY,
            "updatedBy.in=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where updatedBy is not null
        defaultNtQuotePartInformationMasterFiltering("updatedBy.specified=true", "updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where updatedBy contains
        defaultNtQuotePartInformationMasterFiltering(
            "updatedBy.contains=" + DEFAULT_UPDATED_BY,
            "updatedBy.contains=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where updatedBy does not contain
        defaultNtQuotePartInformationMasterFiltering(
            "updatedBy.doesNotContain=" + UPDATED_UPDATED_BY,
            "updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where updatedDate equals to
        defaultNtQuotePartInformationMasterFiltering(
            "updatedDate.equals=" + DEFAULT_UPDATED_DATE,
            "updatedDate.equals=" + UPDATED_UPDATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where updatedDate in
        defaultNtQuotePartInformationMasterFiltering(
            "updatedDate.in=" + DEFAULT_UPDATED_DATE + "," + UPDATED_UPDATED_DATE,
            "updatedDate.in=" + UPDATED_UPDATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        // Get all the ntQuotePartInformationMasterList where updatedDate is not null
        defaultNtQuotePartInformationMasterFiltering("updatedDate.specified=true", "updatedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuotePartInformationMastersByNtQuoteIsEqualToSomething() throws Exception {
        NtQuote ntQuote;
        if (TestUtil.findAll(em, NtQuote.class).isEmpty()) {
            ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);
            ntQuote = NtQuoteResourceIT.createEntity();
        } else {
            ntQuote = TestUtil.findAll(em, NtQuote.class).get(0);
        }
        em.persist(ntQuote);
        em.flush();
        ntQuotePartInformationMaster.setNtQuote(ntQuote);
        ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);
        Long ntQuoteId = ntQuote.getId();
        // Get all the ntQuotePartInformationMasterList where ntQuote equals to ntQuoteId
        defaultNtQuotePartInformationMasterShouldBeFound("ntQuoteId.equals=" + ntQuoteId);

        // Get all the ntQuotePartInformationMasterList where ntQuote equals to (ntQuoteId + 1)
        defaultNtQuotePartInformationMasterShouldNotBeFound("ntQuoteId.equals=" + (ntQuoteId + 1));
    }

    private void defaultNtQuotePartInformationMasterFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultNtQuotePartInformationMasterShouldBeFound(shouldBeFound);
        defaultNtQuotePartInformationMasterShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNtQuotePartInformationMasterShouldBeFound(String filter) throws Exception {
        restNtQuotePartInformationMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuotePartInformationMaster.getId().intValue())))
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
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));

        // Check, that the count call also returns 1
        restNtQuotePartInformationMasterMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNtQuotePartInformationMasterShouldNotBeFound(String filter) throws Exception {
        restNtQuotePartInformationMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNtQuotePartInformationMasterMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNtQuotePartInformationMaster() throws Exception {
        // Get the ntQuotePartInformationMaster
        restNtQuotePartInformationMasterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNtQuotePartInformationMaster() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        long databaseSizeBeforeUpdate = getRepositoryCount();
        ntQuotePartInformationMasterSearchRepository.save(ntQuotePartInformationMaster);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuotePartInformationMasterSearchRepository.findAll());

        // Update the ntQuotePartInformationMaster
        NtQuotePartInformationMaster updatedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository
            .findById(ntQuotePartInformationMaster.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedNtQuotePartInformationMaster are not directly saved in db
        em.detach(updatedNtQuotePartInformationMaster);
        updatedNtQuotePartInformationMaster
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
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);
        NtQuotePartInformationMasterDTO ntQuotePartInformationMasterDTO = ntQuotePartInformationMasterMapper.toDto(
            updatedNtQuotePartInformationMaster
        );

        restNtQuotePartInformationMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ntQuotePartInformationMasterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuotePartInformationMasterDTO))
            )
            .andExpect(status().isOk());

        // Validate the NtQuotePartInformationMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedNtQuotePartInformationMasterToMatchAllProperties(updatedNtQuotePartInformationMaster);

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuotePartInformationMasterSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<NtQuotePartInformationMaster> ntQuotePartInformationMasterSearchList = Streamable.of(
                    ntQuotePartInformationMasterSearchRepository.findAll()
                ).toList();
                NtQuotePartInformationMaster testNtQuotePartInformationMasterSearch = ntQuotePartInformationMasterSearchList.get(
                    searchDatabaseSizeAfter - 1
                );

                assertNtQuotePartInformationMasterAllPropertiesEquals(
                    testNtQuotePartInformationMasterSearch,
                    updatedNtQuotePartInformationMaster
                );
            });
    }

    @Test
    @Transactional
    void putNonExistingNtQuotePartInformationMaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuotePartInformationMasterSearchRepository.findAll());
        ntQuotePartInformationMaster.setId(longCount.incrementAndGet());

        // Create the NtQuotePartInformationMaster
        NtQuotePartInformationMasterDTO ntQuotePartInformationMasterDTO = ntQuotePartInformationMasterMapper.toDto(
            ntQuotePartInformationMaster
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNtQuotePartInformationMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ntQuotePartInformationMasterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuotePartInformationMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuotePartInformationMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuotePartInformationMasterSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchNtQuotePartInformationMaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuotePartInformationMasterSearchRepository.findAll());
        ntQuotePartInformationMaster.setId(longCount.incrementAndGet());

        // Create the NtQuotePartInformationMaster
        NtQuotePartInformationMasterDTO ntQuotePartInformationMasterDTO = ntQuotePartInformationMasterMapper.toDto(
            ntQuotePartInformationMaster
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuotePartInformationMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuotePartInformationMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuotePartInformationMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuotePartInformationMasterSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNtQuotePartInformationMaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuotePartInformationMasterSearchRepository.findAll());
        ntQuotePartInformationMaster.setId(longCount.incrementAndGet());

        // Create the NtQuotePartInformationMaster
        NtQuotePartInformationMasterDTO ntQuotePartInformationMasterDTO = ntQuotePartInformationMasterMapper.toDto(
            ntQuotePartInformationMaster
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuotePartInformationMasterMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuotePartInformationMasterDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NtQuotePartInformationMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuotePartInformationMasterSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateNtQuotePartInformationMasterWithPatch() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ntQuotePartInformationMaster using partial update
        NtQuotePartInformationMaster partialUpdatedNtQuotePartInformationMaster = new NtQuotePartInformationMaster();
        partialUpdatedNtQuotePartInformationMaster.setId(ntQuotePartInformationMaster.getId());

        partialUpdatedNtQuotePartInformationMaster
            .srNo(UPDATED_SR_NO)
            .cadFile(UPDATED_CAD_FILE)
            .eau(UPDATED_EAU)
            .partWeight(UPDATED_PART_WEIGHT)
            .materialCost(UPDATED_MATERIAL_COST)
            .externalMachineCostPer(UPDATED_EXTERNAL_MACHINE_COST_PER)
            .packLogisticCostPer(UPDATED_PACK_LOGISTIC_COST_PER)
            .machineSizeTons(UPDATED_MACHINE_SIZE_TONS)
            .numberOfCavities(UPDATED_NUMBER_OF_CAVITIES)
            .perUnit(UPDATED_PER_UNIT)
            .totalPriceBudget(UPDATED_TOTAL_PRICE_BUDGET)
            .eoat(UPDATED_EOAT)
            .chinaTariffBudget(UPDATED_CHINA_TARIFF_BUDGET)
            .leadTime(UPDATED_LEAD_TIME)
            .toolingNotes(UPDATED_TOOLING_NOTES)
            .jobId(UPDATED_JOB_ID)
            .moldId(UPDATED_MOLD_ID)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restNtQuotePartInformationMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNtQuotePartInformationMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNtQuotePartInformationMaster))
            )
            .andExpect(status().isOk());

        // Validate the NtQuotePartInformationMaster in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNtQuotePartInformationMasterUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedNtQuotePartInformationMaster, ntQuotePartInformationMaster),
            getPersistedNtQuotePartInformationMaster(ntQuotePartInformationMaster)
        );
    }

    @Test
    @Transactional
    void fullUpdateNtQuotePartInformationMasterWithPatch() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ntQuotePartInformationMaster using partial update
        NtQuotePartInformationMaster partialUpdatedNtQuotePartInformationMaster = new NtQuotePartInformationMaster();
        partialUpdatedNtQuotePartInformationMaster.setId(ntQuotePartInformationMaster.getId());

        partialUpdatedNtQuotePartInformationMaster
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
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);

        restNtQuotePartInformationMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNtQuotePartInformationMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNtQuotePartInformationMaster))
            )
            .andExpect(status().isOk());

        // Validate the NtQuotePartInformationMaster in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNtQuotePartInformationMasterUpdatableFieldsEquals(
            partialUpdatedNtQuotePartInformationMaster,
            getPersistedNtQuotePartInformationMaster(partialUpdatedNtQuotePartInformationMaster)
        );
    }

    @Test
    @Transactional
    void patchNonExistingNtQuotePartInformationMaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuotePartInformationMasterSearchRepository.findAll());
        ntQuotePartInformationMaster.setId(longCount.incrementAndGet());

        // Create the NtQuotePartInformationMaster
        NtQuotePartInformationMasterDTO ntQuotePartInformationMasterDTO = ntQuotePartInformationMasterMapper.toDto(
            ntQuotePartInformationMaster
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNtQuotePartInformationMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ntQuotePartInformationMasterDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ntQuotePartInformationMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuotePartInformationMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuotePartInformationMasterSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNtQuotePartInformationMaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuotePartInformationMasterSearchRepository.findAll());
        ntQuotePartInformationMaster.setId(longCount.incrementAndGet());

        // Create the NtQuotePartInformationMaster
        NtQuotePartInformationMasterDTO ntQuotePartInformationMasterDTO = ntQuotePartInformationMasterMapper.toDto(
            ntQuotePartInformationMaster
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuotePartInformationMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ntQuotePartInformationMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuotePartInformationMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuotePartInformationMasterSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNtQuotePartInformationMaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuotePartInformationMasterSearchRepository.findAll());
        ntQuotePartInformationMaster.setId(longCount.incrementAndGet());

        // Create the NtQuotePartInformationMaster
        NtQuotePartInformationMasterDTO ntQuotePartInformationMasterDTO = ntQuotePartInformationMasterMapper.toDto(
            ntQuotePartInformationMaster
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuotePartInformationMasterMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ntQuotePartInformationMasterDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NtQuotePartInformationMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuotePartInformationMasterSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteNtQuotePartInformationMaster() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);
        ntQuotePartInformationMasterRepository.save(ntQuotePartInformationMaster);
        ntQuotePartInformationMasterSearchRepository.save(ntQuotePartInformationMaster);

        long databaseSizeBeforeDelete = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuotePartInformationMasterSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the ntQuotePartInformationMaster
        restNtQuotePartInformationMasterMockMvc
            .perform(delete(ENTITY_API_URL_ID, ntQuotePartInformationMaster.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuotePartInformationMasterSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchNtQuotePartInformationMaster() throws Exception {
        // Initialize the database
        insertedNtQuotePartInformationMaster = ntQuotePartInformationMasterRepository.saveAndFlush(ntQuotePartInformationMaster);
        ntQuotePartInformationMasterSearchRepository.save(ntQuotePartInformationMaster);

        // Search the ntQuotePartInformationMaster
        restNtQuotePartInformationMasterMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + ntQuotePartInformationMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuotePartInformationMaster.getId().intValue())))
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
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    protected long getRepositoryCount() {
        return ntQuotePartInformationMasterRepository.count();
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

    protected NtQuotePartInformationMaster getPersistedNtQuotePartInformationMaster(
        NtQuotePartInformationMaster ntQuotePartInformationMaster
    ) {
        return ntQuotePartInformationMasterRepository.findById(ntQuotePartInformationMaster.getId()).orElseThrow();
    }

    protected void assertPersistedNtQuotePartInformationMasterToMatchAllProperties(
        NtQuotePartInformationMaster expectedNtQuotePartInformationMaster
    ) {
        assertNtQuotePartInformationMasterAllPropertiesEquals(
            expectedNtQuotePartInformationMaster,
            getPersistedNtQuotePartInformationMaster(expectedNtQuotePartInformationMaster)
        );
    }

    protected void assertPersistedNtQuotePartInformationMasterToMatchUpdatableProperties(
        NtQuotePartInformationMaster expectedNtQuotePartInformationMaster
    ) {
        assertNtQuotePartInformationMasterAllUpdatablePropertiesEquals(
            expectedNtQuotePartInformationMaster,
            getPersistedNtQuotePartInformationMaster(expectedNtQuotePartInformationMaster)
        );
    }
}
