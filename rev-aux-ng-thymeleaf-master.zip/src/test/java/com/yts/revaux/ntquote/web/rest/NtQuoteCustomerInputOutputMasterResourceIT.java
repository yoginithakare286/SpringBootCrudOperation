package com.yts.revaux.ntquote.web.rest;

import static com.yts.revaux.ntquote.domain.NtQuoteCustomerInputOutputMasterAsserts.*;
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
import com.yts.revaux.ntquote.domain.NtQuoteCustomerInputOutputMaster;
import com.yts.revaux.ntquote.repository.NtQuoteCustomerInputOutputMasterRepository;
import com.yts.revaux.ntquote.repository.search.NtQuoteCustomerInputOutputMasterSearchRepository;
import com.yts.revaux.ntquote.service.dto.NtQuoteCustomerInputOutputMasterDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuoteCustomerInputOutputMasterMapper;
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
 * Integration tests for the {@link NtQuoteCustomerInputOutputMasterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NtQuoteCustomerInputOutputMasterResourceIT {

    private static final Integer DEFAULT_SR_NO = 1;
    private static final Integer UPDATED_SR_NO = 2;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final String DEFAULT_MATERIAL_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PART_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PART_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_MATERIAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SUPPLIER = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER = "BBBBBBBBBB";

    private static final Integer DEFAULT_EST_ANNUAL_VOLUME = 1;
    private static final Integer UPDATED_EST_ANNUAL_VOLUME = 2;

    private static final Integer DEFAULT_EST_PRODUCTION_RUN_YRS = 1;
    private static final Integer UPDATED_EST_PRODUCTION_RUN_YRS = 2;

    private static final BigDecimal DEFAULT_MATERIAL_COST_LB = new BigDecimal(1);
    private static final BigDecimal UPDATED_MATERIAL_COST_LB = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PART_WEIGHT_LB = new BigDecimal(1);
    private static final BigDecimal UPDATED_PART_WEIGHT_LB = new BigDecimal(2);

    private static final BigDecimal DEFAULT_RUNNER_WEIGHT_LB = new BigDecimal(1);
    private static final BigDecimal UPDATED_RUNNER_WEIGHT_LB = new BigDecimal(2);

    private static final String DEFAULT_MACHINE_SIZE = "AAAAAAAAAA";
    private static final String UPDATED_MACHINE_SIZE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_MACHINE_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_MACHINE_RATE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SCRAP_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_SCRAP_RATE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_MACHINE_EFFICIENCY = new BigDecimal(1);
    private static final BigDecimal UPDATED_MACHINE_EFFICIENCY = new BigDecimal(2);

    private static final String DEFAULT_FTE = "AAAAAAAAAA";
    private static final String UPDATED_FTE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_LABOR_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LABOR_RATE = new BigDecimal(2);

    private static final Integer DEFAULT_NUMBER_OF_CAVITIES = 1;
    private static final Integer UPDATED_NUMBER_OF_CAVITIES = 2;

    private static final Integer DEFAULT_CYCLE_TIME = 1;
    private static final Integer UPDATED_CYCLE_TIME = 2;

    private static final BigDecimal DEFAULT_PURCHASE_COMPONENT_COST_PART = new BigDecimal(1);
    private static final BigDecimal UPDATED_PURCHASE_COMPONENT_COST_PART = new BigDecimal(2);

    private static final String DEFAULT_SECONDARY_OPERATION_EXTERNAL_PROCESS = "AAAAAAAAAA";
    private static final String UPDATED_SECONDARY_OPERATION_EXTERNAL_PROCESS = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_SECONDARY_OPERATION_LABOR_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_SECONDARY_OPERATION_LABOR_RATE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SECONDARY_OPERATION_MACHINE_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_SECONDARY_OPERATION_MACHINE_RATE = new BigDecimal(2);

    private static final Integer DEFAULT_SECONDARY_OPERATION_CYCLE_TIME = 1;
    private static final Integer UPDATED_SECONDARY_OPERATION_CYCLE_TIME = 2;

    private static final BigDecimal DEFAULT_EXTERNAL_OPERATION_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_EXTERNAL_OPERATION_RATE = new BigDecimal(2);

    private static final Integer DEFAULT_PREVENTATIVE_MAINTENANCE_FREQUENCY = 1;
    private static final Integer UPDATED_PREVENTATIVE_MAINTENANCE_FREQUENCY = 2;

    private static final Integer DEFAULT_PREVENTATIVE_MAINTENANCE_COST = 1;
    private static final Integer UPDATED_PREVENTATIVE_MAINTENANCE_COST = 2;

    private static final BigDecimal DEFAULT_TARGET_PROFIT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TARGET_PROFIT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TARGET_MATERIAL_MARKUP = new BigDecimal(1);
    private static final BigDecimal UPDATED_TARGET_MATERIAL_MARKUP = new BigDecimal(2);

    private static final BigDecimal DEFAULT_ACTUAL_MATERIAL_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_ACTUAL_MATERIAL_COST = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PART_PER_HOURS = new BigDecimal(1);
    private static final BigDecimal UPDATED_PART_PER_HOURS = new BigDecimal(2);

    private static final BigDecimal DEFAULT_EST_LOT_SIZE = new BigDecimal(1);
    private static final BigDecimal UPDATED_EST_LOT_SIZE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SETUP_HOURS = new BigDecimal(1);
    private static final BigDecimal UPDATED_SETUP_HOURS = new BigDecimal(2);

    private static final BigDecimal DEFAULT_EXTERNAL_OPERATION_COST_PER = new BigDecimal(1);
    private static final BigDecimal UPDATED_EXTERNAL_OPERATION_COST_PER = new BigDecimal(2);

    private static final BigDecimal DEFAULT_EXTERNAL_MACHINE_COST_PER = new BigDecimal(1);
    private static final BigDecimal UPDATED_EXTERNAL_MACHINE_COST_PER = new BigDecimal(2);

    private static final BigDecimal DEFAULT_EXTENDED_LABOR_COST_PER = new BigDecimal(1);
    private static final BigDecimal UPDATED_EXTENDED_LABOR_COST_PER = new BigDecimal(2);

    private static final BigDecimal DEFAULT_EXTENDED_MATERIAL_COST_PER = new BigDecimal(1);
    private static final BigDecimal UPDATED_EXTENDED_MATERIAL_COST_PER = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PACK_LOGISTIC_COST_PER = new BigDecimal(1);
    private static final BigDecimal UPDATED_PACK_LOGISTIC_COST_PER = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_PRODUCTION_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_PRODUCTION_COST = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_MATERIAL_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_MATERIAL_COST = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_COST_SGA_PROFIT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_COST_SGA_PROFIT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SGA_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_SGA_RATE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PROFIT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PROFIT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PART_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PART_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_COST = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_SALES = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_SALES = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_PROFIT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_PROFIT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_COST_MATERIAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_COST_MATERIAL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_CONTRIBUTION_MARGIN = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_CONTRIBUTION_MARGIN = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CONTRIBUTION_MARGIN = new BigDecimal(1);
    private static final BigDecimal UPDATED_CONTRIBUTION_MARGIN = new BigDecimal(2);

    private static final BigDecimal DEFAULT_MATERIAL_CONTRIBUTION_MARGIN = new BigDecimal(1);
    private static final BigDecimal UPDATED_MATERIAL_CONTRIBUTION_MARGIN = new BigDecimal(2);

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

    private static final String ENTITY_API_URL = "/api/nt-quote-customer-input-output-masters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/nt-quote-customer-input-output-masters/_search";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NtQuoteCustomerInputOutputMasterRepository ntQuoteCustomerInputOutputMasterRepository;

    @Autowired
    private NtQuoteCustomerInputOutputMasterMapper ntQuoteCustomerInputOutputMasterMapper;

    @Autowired
    private NtQuoteCustomerInputOutputMasterSearchRepository ntQuoteCustomerInputOutputMasterSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNtQuoteCustomerInputOutputMasterMockMvc;

    private NtQuoteCustomerInputOutputMaster ntQuoteCustomerInputOutputMaster;

    private NtQuoteCustomerInputOutputMaster insertedNtQuoteCustomerInputOutputMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NtQuoteCustomerInputOutputMaster createEntity() {
        return new NtQuoteCustomerInputOutputMaster()
            .srNo(DEFAULT_SR_NO)
            .uid(DEFAULT_UID)
            .materialDescription(DEFAULT_MATERIAL_DESCRIPTION)
            .partNumber(DEFAULT_PART_NUMBER)
            .materialId(DEFAULT_MATERIAL_ID)
            .supplier(DEFAULT_SUPPLIER)
            .estAnnualVolume(DEFAULT_EST_ANNUAL_VOLUME)
            .estProductionRunYrs(DEFAULT_EST_PRODUCTION_RUN_YRS)
            .materialCostLb(DEFAULT_MATERIAL_COST_LB)
            .partWeightLb(DEFAULT_PART_WEIGHT_LB)
            .runnerWeightLb(DEFAULT_RUNNER_WEIGHT_LB)
            .machineSize(DEFAULT_MACHINE_SIZE)
            .machineRate(DEFAULT_MACHINE_RATE)
            .scrapRate(DEFAULT_SCRAP_RATE)
            .machineEfficiency(DEFAULT_MACHINE_EFFICIENCY)
            .fte(DEFAULT_FTE)
            .laborRate(DEFAULT_LABOR_RATE)
            .numberOfCavities(DEFAULT_NUMBER_OF_CAVITIES)
            .cycleTime(DEFAULT_CYCLE_TIME)
            .purchaseComponentCostPart(DEFAULT_PURCHASE_COMPONENT_COST_PART)
            .secondaryOperationExternalProcess(DEFAULT_SECONDARY_OPERATION_EXTERNAL_PROCESS)
            .secondaryOperationLaborRate(DEFAULT_SECONDARY_OPERATION_LABOR_RATE)
            .secondaryOperationMachineRate(DEFAULT_SECONDARY_OPERATION_MACHINE_RATE)
            .secondaryOperationCycleTime(DEFAULT_SECONDARY_OPERATION_CYCLE_TIME)
            .externalOperationRate(DEFAULT_EXTERNAL_OPERATION_RATE)
            .preventativeMaintenanceFrequency(DEFAULT_PREVENTATIVE_MAINTENANCE_FREQUENCY)
            .preventativeMaintenanceCost(DEFAULT_PREVENTATIVE_MAINTENANCE_COST)
            .targetProfit(DEFAULT_TARGET_PROFIT)
            .targetMaterialMarkup(DEFAULT_TARGET_MATERIAL_MARKUP)
            .actualMaterialCost(DEFAULT_ACTUAL_MATERIAL_COST)
            .partPerHours(DEFAULT_PART_PER_HOURS)
            .estLotSize(DEFAULT_EST_LOT_SIZE)
            .setupHours(DEFAULT_SETUP_HOURS)
            .externalOperationCostPer(DEFAULT_EXTERNAL_OPERATION_COST_PER)
            .externalMachineCostPer(DEFAULT_EXTERNAL_MACHINE_COST_PER)
            .extendedLaborCostPer(DEFAULT_EXTENDED_LABOR_COST_PER)
            .extendedMaterialCostPer(DEFAULT_EXTENDED_MATERIAL_COST_PER)
            .packLogisticCostPer(DEFAULT_PACK_LOGISTIC_COST_PER)
            .totalProductionCost(DEFAULT_TOTAL_PRODUCTION_COST)
            .totalMaterialCost(DEFAULT_TOTAL_MATERIAL_COST)
            .totalCostSgaProfit(DEFAULT_TOTAL_COST_SGA_PROFIT)
            .sgaRate(DEFAULT_SGA_RATE)
            .profit(DEFAULT_PROFIT)
            .partPrice(DEFAULT_PART_PRICE)
            .totalCost(DEFAULT_TOTAL_COST)
            .totalSales(DEFAULT_TOTAL_SALES)
            .totalProfit(DEFAULT_TOTAL_PROFIT)
            .costMaterial(DEFAULT_COST_MATERIAL)
            .totalContributionMargin(DEFAULT_TOTAL_CONTRIBUTION_MARGIN)
            .contributionMargin(DEFAULT_CONTRIBUTION_MARGIN)
            .materialContributionMargin(DEFAULT_MATERIAL_CONTRIBUTION_MARGIN)
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
    public static NtQuoteCustomerInputOutputMaster createUpdatedEntity() {
        return new NtQuoteCustomerInputOutputMaster()
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .materialDescription(UPDATED_MATERIAL_DESCRIPTION)
            .partNumber(UPDATED_PART_NUMBER)
            .materialId(UPDATED_MATERIAL_ID)
            .supplier(UPDATED_SUPPLIER)
            .estAnnualVolume(UPDATED_EST_ANNUAL_VOLUME)
            .estProductionRunYrs(UPDATED_EST_PRODUCTION_RUN_YRS)
            .materialCostLb(UPDATED_MATERIAL_COST_LB)
            .partWeightLb(UPDATED_PART_WEIGHT_LB)
            .runnerWeightLb(UPDATED_RUNNER_WEIGHT_LB)
            .machineSize(UPDATED_MACHINE_SIZE)
            .machineRate(UPDATED_MACHINE_RATE)
            .scrapRate(UPDATED_SCRAP_RATE)
            .machineEfficiency(UPDATED_MACHINE_EFFICIENCY)
            .fte(UPDATED_FTE)
            .laborRate(UPDATED_LABOR_RATE)
            .numberOfCavities(UPDATED_NUMBER_OF_CAVITIES)
            .cycleTime(UPDATED_CYCLE_TIME)
            .purchaseComponentCostPart(UPDATED_PURCHASE_COMPONENT_COST_PART)
            .secondaryOperationExternalProcess(UPDATED_SECONDARY_OPERATION_EXTERNAL_PROCESS)
            .secondaryOperationLaborRate(UPDATED_SECONDARY_OPERATION_LABOR_RATE)
            .secondaryOperationMachineRate(UPDATED_SECONDARY_OPERATION_MACHINE_RATE)
            .secondaryOperationCycleTime(UPDATED_SECONDARY_OPERATION_CYCLE_TIME)
            .externalOperationRate(UPDATED_EXTERNAL_OPERATION_RATE)
            .preventativeMaintenanceFrequency(UPDATED_PREVENTATIVE_MAINTENANCE_FREQUENCY)
            .preventativeMaintenanceCost(UPDATED_PREVENTATIVE_MAINTENANCE_COST)
            .targetProfit(UPDATED_TARGET_PROFIT)
            .targetMaterialMarkup(UPDATED_TARGET_MATERIAL_MARKUP)
            .actualMaterialCost(UPDATED_ACTUAL_MATERIAL_COST)
            .partPerHours(UPDATED_PART_PER_HOURS)
            .estLotSize(UPDATED_EST_LOT_SIZE)
            .setupHours(UPDATED_SETUP_HOURS)
            .externalOperationCostPer(UPDATED_EXTERNAL_OPERATION_COST_PER)
            .externalMachineCostPer(UPDATED_EXTERNAL_MACHINE_COST_PER)
            .extendedLaborCostPer(UPDATED_EXTENDED_LABOR_COST_PER)
            .extendedMaterialCostPer(UPDATED_EXTENDED_MATERIAL_COST_PER)
            .packLogisticCostPer(UPDATED_PACK_LOGISTIC_COST_PER)
            .totalProductionCost(UPDATED_TOTAL_PRODUCTION_COST)
            .totalMaterialCost(UPDATED_TOTAL_MATERIAL_COST)
            .totalCostSgaProfit(UPDATED_TOTAL_COST_SGA_PROFIT)
            .sgaRate(UPDATED_SGA_RATE)
            .profit(UPDATED_PROFIT)
            .partPrice(UPDATED_PART_PRICE)
            .totalCost(UPDATED_TOTAL_COST)
            .totalSales(UPDATED_TOTAL_SALES)
            .totalProfit(UPDATED_TOTAL_PROFIT)
            .costMaterial(UPDATED_COST_MATERIAL)
            .totalContributionMargin(UPDATED_TOTAL_CONTRIBUTION_MARGIN)
            .contributionMargin(UPDATED_CONTRIBUTION_MARGIN)
            .materialContributionMargin(UPDATED_MATERIAL_CONTRIBUTION_MARGIN)
            .comments(UPDATED_COMMENTS)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);
    }

    @BeforeEach
    public void initTest() {
        ntQuoteCustomerInputOutputMaster = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedNtQuoteCustomerInputOutputMaster != null) {
            ntQuoteCustomerInputOutputMasterRepository.delete(insertedNtQuoteCustomerInputOutputMaster);
            ntQuoteCustomerInputOutputMasterSearchRepository.delete(insertedNtQuoteCustomerInputOutputMaster);
            insertedNtQuoteCustomerInputOutputMaster = null;
        }
    }

    @Test
    @Transactional
    void createNtQuoteCustomerInputOutputMaster() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerInputOutputMasterSearchRepository.findAll());
        // Create the NtQuoteCustomerInputOutputMaster
        NtQuoteCustomerInputOutputMasterDTO ntQuoteCustomerInputOutputMasterDTO = ntQuoteCustomerInputOutputMasterMapper.toDto(
            ntQuoteCustomerInputOutputMaster
        );
        var returnedNtQuoteCustomerInputOutputMasterDTO = om.readValue(
            restNtQuoteCustomerInputOutputMasterMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(ntQuoteCustomerInputOutputMasterDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            NtQuoteCustomerInputOutputMasterDTO.class
        );

        // Validate the NtQuoteCustomerInputOutputMaster in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedNtQuoteCustomerInputOutputMaster = ntQuoteCustomerInputOutputMasterMapper.toEntity(
            returnedNtQuoteCustomerInputOutputMasterDTO
        );
        assertNtQuoteCustomerInputOutputMasterUpdatableFieldsEquals(
            returnedNtQuoteCustomerInputOutputMaster,
            getPersistedNtQuoteCustomerInputOutputMaster(returnedNtQuoteCustomerInputOutputMaster)
        );

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerInputOutputMasterSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });

        insertedNtQuoteCustomerInputOutputMaster = returnedNtQuoteCustomerInputOutputMaster;
    }

    @Test
    @Transactional
    void createNtQuoteCustomerInputOutputMasterWithExistingId() throws Exception {
        // Create the NtQuoteCustomerInputOutputMaster with an existing ID
        ntQuoteCustomerInputOutputMaster.setId(1L);
        NtQuoteCustomerInputOutputMasterDTO ntQuoteCustomerInputOutputMasterDTO = ntQuoteCustomerInputOutputMasterMapper.toDto(
            ntQuoteCustomerInputOutputMaster
        );

        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerInputOutputMasterSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restNtQuoteCustomerInputOutputMasterMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteCustomerInputOutputMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteCustomerInputOutputMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerInputOutputMasterSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkUidIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerInputOutputMasterSearchRepository.findAll());
        // set the field null
        ntQuoteCustomerInputOutputMaster.setUid(null);

        // Create the NtQuoteCustomerInputOutputMaster, which fails.
        NtQuoteCustomerInputOutputMasterDTO ntQuoteCustomerInputOutputMasterDTO = ntQuoteCustomerInputOutputMasterMapper.toDto(
            ntQuoteCustomerInputOutputMaster
        );

        restNtQuoteCustomerInputOutputMasterMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteCustomerInputOutputMasterDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerInputOutputMasterSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputMasters() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputMaster = ntQuoteCustomerInputOutputMasterRepository.saveAndFlush(
            ntQuoteCustomerInputOutputMaster
        );

        // Get all the ntQuoteCustomerInputOutputMasterList
        restNtQuoteCustomerInputOutputMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuoteCustomerInputOutputMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].materialDescription").value(hasItem(DEFAULT_MATERIAL_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].partNumber").value(hasItem(DEFAULT_PART_NUMBER)))
            .andExpect(jsonPath("$.[*].materialId").value(hasItem(DEFAULT_MATERIAL_ID)))
            .andExpect(jsonPath("$.[*].supplier").value(hasItem(DEFAULT_SUPPLIER)))
            .andExpect(jsonPath("$.[*].estAnnualVolume").value(hasItem(DEFAULT_EST_ANNUAL_VOLUME)))
            .andExpect(jsonPath("$.[*].estProductionRunYrs").value(hasItem(DEFAULT_EST_PRODUCTION_RUN_YRS)))
            .andExpect(jsonPath("$.[*].materialCostLb").value(hasItem(sameNumber(DEFAULT_MATERIAL_COST_LB))))
            .andExpect(jsonPath("$.[*].partWeightLb").value(hasItem(sameNumber(DEFAULT_PART_WEIGHT_LB))))
            .andExpect(jsonPath("$.[*].runnerWeightLb").value(hasItem(sameNumber(DEFAULT_RUNNER_WEIGHT_LB))))
            .andExpect(jsonPath("$.[*].machineSize").value(hasItem(DEFAULT_MACHINE_SIZE)))
            .andExpect(jsonPath("$.[*].machineRate").value(hasItem(sameNumber(DEFAULT_MACHINE_RATE))))
            .andExpect(jsonPath("$.[*].scrapRate").value(hasItem(sameNumber(DEFAULT_SCRAP_RATE))))
            .andExpect(jsonPath("$.[*].machineEfficiency").value(hasItem(sameNumber(DEFAULT_MACHINE_EFFICIENCY))))
            .andExpect(jsonPath("$.[*].fte").value(hasItem(DEFAULT_FTE)))
            .andExpect(jsonPath("$.[*].laborRate").value(hasItem(sameNumber(DEFAULT_LABOR_RATE))))
            .andExpect(jsonPath("$.[*].numberOfCavities").value(hasItem(DEFAULT_NUMBER_OF_CAVITIES)))
            .andExpect(jsonPath("$.[*].cycleTime").value(hasItem(DEFAULT_CYCLE_TIME)))
            .andExpect(jsonPath("$.[*].purchaseComponentCostPart").value(hasItem(sameNumber(DEFAULT_PURCHASE_COMPONENT_COST_PART))))
            .andExpect(jsonPath("$.[*].secondaryOperationExternalProcess").value(hasItem(DEFAULT_SECONDARY_OPERATION_EXTERNAL_PROCESS)))
            .andExpect(jsonPath("$.[*].secondaryOperationLaborRate").value(hasItem(sameNumber(DEFAULT_SECONDARY_OPERATION_LABOR_RATE))))
            .andExpect(jsonPath("$.[*].secondaryOperationMachineRate").value(hasItem(sameNumber(DEFAULT_SECONDARY_OPERATION_MACHINE_RATE))))
            .andExpect(jsonPath("$.[*].secondaryOperationCycleTime").value(hasItem(DEFAULT_SECONDARY_OPERATION_CYCLE_TIME)))
            .andExpect(jsonPath("$.[*].externalOperationRate").value(hasItem(sameNumber(DEFAULT_EXTERNAL_OPERATION_RATE))))
            .andExpect(jsonPath("$.[*].preventativeMaintenanceFrequency").value(hasItem(DEFAULT_PREVENTATIVE_MAINTENANCE_FREQUENCY)))
            .andExpect(jsonPath("$.[*].preventativeMaintenanceCost").value(hasItem(DEFAULT_PREVENTATIVE_MAINTENANCE_COST)))
            .andExpect(jsonPath("$.[*].targetProfit").value(hasItem(sameNumber(DEFAULT_TARGET_PROFIT))))
            .andExpect(jsonPath("$.[*].targetMaterialMarkup").value(hasItem(sameNumber(DEFAULT_TARGET_MATERIAL_MARKUP))))
            .andExpect(jsonPath("$.[*].actualMaterialCost").value(hasItem(sameNumber(DEFAULT_ACTUAL_MATERIAL_COST))))
            .andExpect(jsonPath("$.[*].partPerHours").value(hasItem(sameNumber(DEFAULT_PART_PER_HOURS))))
            .andExpect(jsonPath("$.[*].estLotSize").value(hasItem(sameNumber(DEFAULT_EST_LOT_SIZE))))
            .andExpect(jsonPath("$.[*].setupHours").value(hasItem(sameNumber(DEFAULT_SETUP_HOURS))))
            .andExpect(jsonPath("$.[*].externalOperationCostPer").value(hasItem(sameNumber(DEFAULT_EXTERNAL_OPERATION_COST_PER))))
            .andExpect(jsonPath("$.[*].externalMachineCostPer").value(hasItem(sameNumber(DEFAULT_EXTERNAL_MACHINE_COST_PER))))
            .andExpect(jsonPath("$.[*].extendedLaborCostPer").value(hasItem(sameNumber(DEFAULT_EXTENDED_LABOR_COST_PER))))
            .andExpect(jsonPath("$.[*].extendedMaterialCostPer").value(hasItem(sameNumber(DEFAULT_EXTENDED_MATERIAL_COST_PER))))
            .andExpect(jsonPath("$.[*].packLogisticCostPer").value(hasItem(sameNumber(DEFAULT_PACK_LOGISTIC_COST_PER))))
            .andExpect(jsonPath("$.[*].totalProductionCost").value(hasItem(sameNumber(DEFAULT_TOTAL_PRODUCTION_COST))))
            .andExpect(jsonPath("$.[*].totalMaterialCost").value(hasItem(sameNumber(DEFAULT_TOTAL_MATERIAL_COST))))
            .andExpect(jsonPath("$.[*].totalCostSgaProfit").value(hasItem(sameNumber(DEFAULT_TOTAL_COST_SGA_PROFIT))))
            .andExpect(jsonPath("$.[*].sgaRate").value(hasItem(sameNumber(DEFAULT_SGA_RATE))))
            .andExpect(jsonPath("$.[*].profit").value(hasItem(sameNumber(DEFAULT_PROFIT))))
            .andExpect(jsonPath("$.[*].partPrice").value(hasItem(sameNumber(DEFAULT_PART_PRICE))))
            .andExpect(jsonPath("$.[*].totalCost").value(hasItem(sameNumber(DEFAULT_TOTAL_COST))))
            .andExpect(jsonPath("$.[*].totalSales").value(hasItem(sameNumber(DEFAULT_TOTAL_SALES))))
            .andExpect(jsonPath("$.[*].totalProfit").value(hasItem(sameNumber(DEFAULT_TOTAL_PROFIT))))
            .andExpect(jsonPath("$.[*].costMaterial").value(hasItem(sameNumber(DEFAULT_COST_MATERIAL))))
            .andExpect(jsonPath("$.[*].totalContributionMargin").value(hasItem(sameNumber(DEFAULT_TOTAL_CONTRIBUTION_MARGIN))))
            .andExpect(jsonPath("$.[*].contributionMargin").value(hasItem(sameNumber(DEFAULT_CONTRIBUTION_MARGIN))))
            .andExpect(jsonPath("$.[*].materialContributionMargin").value(hasItem(sameNumber(DEFAULT_MATERIAL_CONTRIBUTION_MARGIN))))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getNtQuoteCustomerInputOutputMaster() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputMaster = ntQuoteCustomerInputOutputMasterRepository.saveAndFlush(
            ntQuoteCustomerInputOutputMaster
        );

        // Get the ntQuoteCustomerInputOutputMaster
        restNtQuoteCustomerInputOutputMasterMockMvc
            .perform(get(ENTITY_API_URL_ID, ntQuoteCustomerInputOutputMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ntQuoteCustomerInputOutputMaster.getId().intValue()))
            .andExpect(jsonPath("$.srNo").value(DEFAULT_SR_NO))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.materialDescription").value(DEFAULT_MATERIAL_DESCRIPTION))
            .andExpect(jsonPath("$.partNumber").value(DEFAULT_PART_NUMBER))
            .andExpect(jsonPath("$.materialId").value(DEFAULT_MATERIAL_ID))
            .andExpect(jsonPath("$.supplier").value(DEFAULT_SUPPLIER))
            .andExpect(jsonPath("$.estAnnualVolume").value(DEFAULT_EST_ANNUAL_VOLUME))
            .andExpect(jsonPath("$.estProductionRunYrs").value(DEFAULT_EST_PRODUCTION_RUN_YRS))
            .andExpect(jsonPath("$.materialCostLb").value(sameNumber(DEFAULT_MATERIAL_COST_LB)))
            .andExpect(jsonPath("$.partWeightLb").value(sameNumber(DEFAULT_PART_WEIGHT_LB)))
            .andExpect(jsonPath("$.runnerWeightLb").value(sameNumber(DEFAULT_RUNNER_WEIGHT_LB)))
            .andExpect(jsonPath("$.machineSize").value(DEFAULT_MACHINE_SIZE))
            .andExpect(jsonPath("$.machineRate").value(sameNumber(DEFAULT_MACHINE_RATE)))
            .andExpect(jsonPath("$.scrapRate").value(sameNumber(DEFAULT_SCRAP_RATE)))
            .andExpect(jsonPath("$.machineEfficiency").value(sameNumber(DEFAULT_MACHINE_EFFICIENCY)))
            .andExpect(jsonPath("$.fte").value(DEFAULT_FTE))
            .andExpect(jsonPath("$.laborRate").value(sameNumber(DEFAULT_LABOR_RATE)))
            .andExpect(jsonPath("$.numberOfCavities").value(DEFAULT_NUMBER_OF_CAVITIES))
            .andExpect(jsonPath("$.cycleTime").value(DEFAULT_CYCLE_TIME))
            .andExpect(jsonPath("$.purchaseComponentCostPart").value(sameNumber(DEFAULT_PURCHASE_COMPONENT_COST_PART)))
            .andExpect(jsonPath("$.secondaryOperationExternalProcess").value(DEFAULT_SECONDARY_OPERATION_EXTERNAL_PROCESS))
            .andExpect(jsonPath("$.secondaryOperationLaborRate").value(sameNumber(DEFAULT_SECONDARY_OPERATION_LABOR_RATE)))
            .andExpect(jsonPath("$.secondaryOperationMachineRate").value(sameNumber(DEFAULT_SECONDARY_OPERATION_MACHINE_RATE)))
            .andExpect(jsonPath("$.secondaryOperationCycleTime").value(DEFAULT_SECONDARY_OPERATION_CYCLE_TIME))
            .andExpect(jsonPath("$.externalOperationRate").value(sameNumber(DEFAULT_EXTERNAL_OPERATION_RATE)))
            .andExpect(jsonPath("$.preventativeMaintenanceFrequency").value(DEFAULT_PREVENTATIVE_MAINTENANCE_FREQUENCY))
            .andExpect(jsonPath("$.preventativeMaintenanceCost").value(DEFAULT_PREVENTATIVE_MAINTENANCE_COST))
            .andExpect(jsonPath("$.targetProfit").value(sameNumber(DEFAULT_TARGET_PROFIT)))
            .andExpect(jsonPath("$.targetMaterialMarkup").value(sameNumber(DEFAULT_TARGET_MATERIAL_MARKUP)))
            .andExpect(jsonPath("$.actualMaterialCost").value(sameNumber(DEFAULT_ACTUAL_MATERIAL_COST)))
            .andExpect(jsonPath("$.partPerHours").value(sameNumber(DEFAULT_PART_PER_HOURS)))
            .andExpect(jsonPath("$.estLotSize").value(sameNumber(DEFAULT_EST_LOT_SIZE)))
            .andExpect(jsonPath("$.setupHours").value(sameNumber(DEFAULT_SETUP_HOURS)))
            .andExpect(jsonPath("$.externalOperationCostPer").value(sameNumber(DEFAULT_EXTERNAL_OPERATION_COST_PER)))
            .andExpect(jsonPath("$.externalMachineCostPer").value(sameNumber(DEFAULT_EXTERNAL_MACHINE_COST_PER)))
            .andExpect(jsonPath("$.extendedLaborCostPer").value(sameNumber(DEFAULT_EXTENDED_LABOR_COST_PER)))
            .andExpect(jsonPath("$.extendedMaterialCostPer").value(sameNumber(DEFAULT_EXTENDED_MATERIAL_COST_PER)))
            .andExpect(jsonPath("$.packLogisticCostPer").value(sameNumber(DEFAULT_PACK_LOGISTIC_COST_PER)))
            .andExpect(jsonPath("$.totalProductionCost").value(sameNumber(DEFAULT_TOTAL_PRODUCTION_COST)))
            .andExpect(jsonPath("$.totalMaterialCost").value(sameNumber(DEFAULT_TOTAL_MATERIAL_COST)))
            .andExpect(jsonPath("$.totalCostSgaProfit").value(sameNumber(DEFAULT_TOTAL_COST_SGA_PROFIT)))
            .andExpect(jsonPath("$.sgaRate").value(sameNumber(DEFAULT_SGA_RATE)))
            .andExpect(jsonPath("$.profit").value(sameNumber(DEFAULT_PROFIT)))
            .andExpect(jsonPath("$.partPrice").value(sameNumber(DEFAULT_PART_PRICE)))
            .andExpect(jsonPath("$.totalCost").value(sameNumber(DEFAULT_TOTAL_COST)))
            .andExpect(jsonPath("$.totalSales").value(sameNumber(DEFAULT_TOTAL_SALES)))
            .andExpect(jsonPath("$.totalProfit").value(sameNumber(DEFAULT_TOTAL_PROFIT)))
            .andExpect(jsonPath("$.costMaterial").value(sameNumber(DEFAULT_COST_MATERIAL)))
            .andExpect(jsonPath("$.totalContributionMargin").value(sameNumber(DEFAULT_TOTAL_CONTRIBUTION_MARGIN)))
            .andExpect(jsonPath("$.contributionMargin").value(sameNumber(DEFAULT_CONTRIBUTION_MARGIN)))
            .andExpect(jsonPath("$.materialContributionMargin").value(sameNumber(DEFAULT_MATERIAL_CONTRIBUTION_MARGIN)))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingNtQuoteCustomerInputOutputMaster() throws Exception {
        // Get the ntQuoteCustomerInputOutputMaster
        restNtQuoteCustomerInputOutputMasterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNtQuoteCustomerInputOutputMaster() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputMaster = ntQuoteCustomerInputOutputMasterRepository.saveAndFlush(
            ntQuoteCustomerInputOutputMaster
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();
        ntQuoteCustomerInputOutputMasterSearchRepository.save(ntQuoteCustomerInputOutputMaster);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerInputOutputMasterSearchRepository.findAll());

        // Update the ntQuoteCustomerInputOutputMaster
        NtQuoteCustomerInputOutputMaster updatedNtQuoteCustomerInputOutputMaster = ntQuoteCustomerInputOutputMasterRepository
            .findById(ntQuoteCustomerInputOutputMaster.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedNtQuoteCustomerInputOutputMaster are not directly saved in db
        em.detach(updatedNtQuoteCustomerInputOutputMaster);
        updatedNtQuoteCustomerInputOutputMaster
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .materialDescription(UPDATED_MATERIAL_DESCRIPTION)
            .partNumber(UPDATED_PART_NUMBER)
            .materialId(UPDATED_MATERIAL_ID)
            .supplier(UPDATED_SUPPLIER)
            .estAnnualVolume(UPDATED_EST_ANNUAL_VOLUME)
            .estProductionRunYrs(UPDATED_EST_PRODUCTION_RUN_YRS)
            .materialCostLb(UPDATED_MATERIAL_COST_LB)
            .partWeightLb(UPDATED_PART_WEIGHT_LB)
            .runnerWeightLb(UPDATED_RUNNER_WEIGHT_LB)
            .machineSize(UPDATED_MACHINE_SIZE)
            .machineRate(UPDATED_MACHINE_RATE)
            .scrapRate(UPDATED_SCRAP_RATE)
            .machineEfficiency(UPDATED_MACHINE_EFFICIENCY)
            .fte(UPDATED_FTE)
            .laborRate(UPDATED_LABOR_RATE)
            .numberOfCavities(UPDATED_NUMBER_OF_CAVITIES)
            .cycleTime(UPDATED_CYCLE_TIME)
            .purchaseComponentCostPart(UPDATED_PURCHASE_COMPONENT_COST_PART)
            .secondaryOperationExternalProcess(UPDATED_SECONDARY_OPERATION_EXTERNAL_PROCESS)
            .secondaryOperationLaborRate(UPDATED_SECONDARY_OPERATION_LABOR_RATE)
            .secondaryOperationMachineRate(UPDATED_SECONDARY_OPERATION_MACHINE_RATE)
            .secondaryOperationCycleTime(UPDATED_SECONDARY_OPERATION_CYCLE_TIME)
            .externalOperationRate(UPDATED_EXTERNAL_OPERATION_RATE)
            .preventativeMaintenanceFrequency(UPDATED_PREVENTATIVE_MAINTENANCE_FREQUENCY)
            .preventativeMaintenanceCost(UPDATED_PREVENTATIVE_MAINTENANCE_COST)
            .targetProfit(UPDATED_TARGET_PROFIT)
            .targetMaterialMarkup(UPDATED_TARGET_MATERIAL_MARKUP)
            .actualMaterialCost(UPDATED_ACTUAL_MATERIAL_COST)
            .partPerHours(UPDATED_PART_PER_HOURS)
            .estLotSize(UPDATED_EST_LOT_SIZE)
            .setupHours(UPDATED_SETUP_HOURS)
            .externalOperationCostPer(UPDATED_EXTERNAL_OPERATION_COST_PER)
            .externalMachineCostPer(UPDATED_EXTERNAL_MACHINE_COST_PER)
            .extendedLaborCostPer(UPDATED_EXTENDED_LABOR_COST_PER)
            .extendedMaterialCostPer(UPDATED_EXTENDED_MATERIAL_COST_PER)
            .packLogisticCostPer(UPDATED_PACK_LOGISTIC_COST_PER)
            .totalProductionCost(UPDATED_TOTAL_PRODUCTION_COST)
            .totalMaterialCost(UPDATED_TOTAL_MATERIAL_COST)
            .totalCostSgaProfit(UPDATED_TOTAL_COST_SGA_PROFIT)
            .sgaRate(UPDATED_SGA_RATE)
            .profit(UPDATED_PROFIT)
            .partPrice(UPDATED_PART_PRICE)
            .totalCost(UPDATED_TOTAL_COST)
            .totalSales(UPDATED_TOTAL_SALES)
            .totalProfit(UPDATED_TOTAL_PROFIT)
            .costMaterial(UPDATED_COST_MATERIAL)
            .totalContributionMargin(UPDATED_TOTAL_CONTRIBUTION_MARGIN)
            .contributionMargin(UPDATED_CONTRIBUTION_MARGIN)
            .materialContributionMargin(UPDATED_MATERIAL_CONTRIBUTION_MARGIN)
            .comments(UPDATED_COMMENTS)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);
        NtQuoteCustomerInputOutputMasterDTO ntQuoteCustomerInputOutputMasterDTO = ntQuoteCustomerInputOutputMasterMapper.toDto(
            updatedNtQuoteCustomerInputOutputMaster
        );

        restNtQuoteCustomerInputOutputMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ntQuoteCustomerInputOutputMasterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteCustomerInputOutputMasterDTO))
            )
            .andExpect(status().isOk());

        // Validate the NtQuoteCustomerInputOutputMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedNtQuoteCustomerInputOutputMasterToMatchAllProperties(updatedNtQuoteCustomerInputOutputMaster);

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerInputOutputMasterSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<NtQuoteCustomerInputOutputMaster> ntQuoteCustomerInputOutputMasterSearchList = Streamable.of(
                    ntQuoteCustomerInputOutputMasterSearchRepository.findAll()
                ).toList();
                NtQuoteCustomerInputOutputMaster testNtQuoteCustomerInputOutputMasterSearch =
                    ntQuoteCustomerInputOutputMasterSearchList.get(searchDatabaseSizeAfter - 1);

                assertNtQuoteCustomerInputOutputMasterAllPropertiesEquals(
                    testNtQuoteCustomerInputOutputMasterSearch,
                    updatedNtQuoteCustomerInputOutputMaster
                );
            });
    }

    @Test
    @Transactional
    void putNonExistingNtQuoteCustomerInputOutputMaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerInputOutputMasterSearchRepository.findAll());
        ntQuoteCustomerInputOutputMaster.setId(longCount.incrementAndGet());

        // Create the NtQuoteCustomerInputOutputMaster
        NtQuoteCustomerInputOutputMasterDTO ntQuoteCustomerInputOutputMasterDTO = ntQuoteCustomerInputOutputMasterMapper.toDto(
            ntQuoteCustomerInputOutputMaster
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNtQuoteCustomerInputOutputMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ntQuoteCustomerInputOutputMasterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteCustomerInputOutputMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteCustomerInputOutputMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerInputOutputMasterSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchNtQuoteCustomerInputOutputMaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerInputOutputMasterSearchRepository.findAll());
        ntQuoteCustomerInputOutputMaster.setId(longCount.incrementAndGet());

        // Create the NtQuoteCustomerInputOutputMaster
        NtQuoteCustomerInputOutputMasterDTO ntQuoteCustomerInputOutputMasterDTO = ntQuoteCustomerInputOutputMasterMapper.toDto(
            ntQuoteCustomerInputOutputMaster
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteCustomerInputOutputMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteCustomerInputOutputMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteCustomerInputOutputMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerInputOutputMasterSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNtQuoteCustomerInputOutputMaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerInputOutputMasterSearchRepository.findAll());
        ntQuoteCustomerInputOutputMaster.setId(longCount.incrementAndGet());

        // Create the NtQuoteCustomerInputOutputMaster
        NtQuoteCustomerInputOutputMasterDTO ntQuoteCustomerInputOutputMasterDTO = ntQuoteCustomerInputOutputMasterMapper.toDto(
            ntQuoteCustomerInputOutputMaster
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteCustomerInputOutputMasterMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteCustomerInputOutputMasterDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NtQuoteCustomerInputOutputMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerInputOutputMasterSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateNtQuoteCustomerInputOutputMasterWithPatch() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputMaster = ntQuoteCustomerInputOutputMasterRepository.saveAndFlush(
            ntQuoteCustomerInputOutputMaster
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ntQuoteCustomerInputOutputMaster using partial update
        NtQuoteCustomerInputOutputMaster partialUpdatedNtQuoteCustomerInputOutputMaster = new NtQuoteCustomerInputOutputMaster();
        partialUpdatedNtQuoteCustomerInputOutputMaster.setId(ntQuoteCustomerInputOutputMaster.getId());

        partialUpdatedNtQuoteCustomerInputOutputMaster
            .srNo(UPDATED_SR_NO)
            .partNumber(UPDATED_PART_NUMBER)
            .estProductionRunYrs(UPDATED_EST_PRODUCTION_RUN_YRS)
            .materialCostLb(UPDATED_MATERIAL_COST_LB)
            .runnerWeightLb(UPDATED_RUNNER_WEIGHT_LB)
            .machineSize(UPDATED_MACHINE_SIZE)
            .scrapRate(UPDATED_SCRAP_RATE)
            .machineEfficiency(UPDATED_MACHINE_EFFICIENCY)
            .laborRate(UPDATED_LABOR_RATE)
            .numberOfCavities(UPDATED_NUMBER_OF_CAVITIES)
            .cycleTime(UPDATED_CYCLE_TIME)
            .secondaryOperationExternalProcess(UPDATED_SECONDARY_OPERATION_EXTERNAL_PROCESS)
            .secondaryOperationMachineRate(UPDATED_SECONDARY_OPERATION_MACHINE_RATE)
            .secondaryOperationCycleTime(UPDATED_SECONDARY_OPERATION_CYCLE_TIME)
            .targetMaterialMarkup(UPDATED_TARGET_MATERIAL_MARKUP)
            .actualMaterialCost(UPDATED_ACTUAL_MATERIAL_COST)
            .externalMachineCostPer(UPDATED_EXTERNAL_MACHINE_COST_PER)
            .extendedLaborCostPer(UPDATED_EXTENDED_LABOR_COST_PER)
            .totalCostSgaProfit(UPDATED_TOTAL_COST_SGA_PROFIT)
            .profit(UPDATED_PROFIT)
            .totalCost(UPDATED_TOTAL_COST)
            .totalContributionMargin(UPDATED_TOTAL_CONTRIBUTION_MARGIN)
            .contributionMargin(UPDATED_CONTRIBUTION_MARGIN)
            .comments(UPDATED_COMMENTS)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restNtQuoteCustomerInputOutputMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNtQuoteCustomerInputOutputMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNtQuoteCustomerInputOutputMaster))
            )
            .andExpect(status().isOk());

        // Validate the NtQuoteCustomerInputOutputMaster in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNtQuoteCustomerInputOutputMasterUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedNtQuoteCustomerInputOutputMaster, ntQuoteCustomerInputOutputMaster),
            getPersistedNtQuoteCustomerInputOutputMaster(ntQuoteCustomerInputOutputMaster)
        );
    }

    @Test
    @Transactional
    void fullUpdateNtQuoteCustomerInputOutputMasterWithPatch() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputMaster = ntQuoteCustomerInputOutputMasterRepository.saveAndFlush(
            ntQuoteCustomerInputOutputMaster
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ntQuoteCustomerInputOutputMaster using partial update
        NtQuoteCustomerInputOutputMaster partialUpdatedNtQuoteCustomerInputOutputMaster = new NtQuoteCustomerInputOutputMaster();
        partialUpdatedNtQuoteCustomerInputOutputMaster.setId(ntQuoteCustomerInputOutputMaster.getId());

        partialUpdatedNtQuoteCustomerInputOutputMaster
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .materialDescription(UPDATED_MATERIAL_DESCRIPTION)
            .partNumber(UPDATED_PART_NUMBER)
            .materialId(UPDATED_MATERIAL_ID)
            .supplier(UPDATED_SUPPLIER)
            .estAnnualVolume(UPDATED_EST_ANNUAL_VOLUME)
            .estProductionRunYrs(UPDATED_EST_PRODUCTION_RUN_YRS)
            .materialCostLb(UPDATED_MATERIAL_COST_LB)
            .partWeightLb(UPDATED_PART_WEIGHT_LB)
            .runnerWeightLb(UPDATED_RUNNER_WEIGHT_LB)
            .machineSize(UPDATED_MACHINE_SIZE)
            .machineRate(UPDATED_MACHINE_RATE)
            .scrapRate(UPDATED_SCRAP_RATE)
            .machineEfficiency(UPDATED_MACHINE_EFFICIENCY)
            .fte(UPDATED_FTE)
            .laborRate(UPDATED_LABOR_RATE)
            .numberOfCavities(UPDATED_NUMBER_OF_CAVITIES)
            .cycleTime(UPDATED_CYCLE_TIME)
            .purchaseComponentCostPart(UPDATED_PURCHASE_COMPONENT_COST_PART)
            .secondaryOperationExternalProcess(UPDATED_SECONDARY_OPERATION_EXTERNAL_PROCESS)
            .secondaryOperationLaborRate(UPDATED_SECONDARY_OPERATION_LABOR_RATE)
            .secondaryOperationMachineRate(UPDATED_SECONDARY_OPERATION_MACHINE_RATE)
            .secondaryOperationCycleTime(UPDATED_SECONDARY_OPERATION_CYCLE_TIME)
            .externalOperationRate(UPDATED_EXTERNAL_OPERATION_RATE)
            .preventativeMaintenanceFrequency(UPDATED_PREVENTATIVE_MAINTENANCE_FREQUENCY)
            .preventativeMaintenanceCost(UPDATED_PREVENTATIVE_MAINTENANCE_COST)
            .targetProfit(UPDATED_TARGET_PROFIT)
            .targetMaterialMarkup(UPDATED_TARGET_MATERIAL_MARKUP)
            .actualMaterialCost(UPDATED_ACTUAL_MATERIAL_COST)
            .partPerHours(UPDATED_PART_PER_HOURS)
            .estLotSize(UPDATED_EST_LOT_SIZE)
            .setupHours(UPDATED_SETUP_HOURS)
            .externalOperationCostPer(UPDATED_EXTERNAL_OPERATION_COST_PER)
            .externalMachineCostPer(UPDATED_EXTERNAL_MACHINE_COST_PER)
            .extendedLaborCostPer(UPDATED_EXTENDED_LABOR_COST_PER)
            .extendedMaterialCostPer(UPDATED_EXTENDED_MATERIAL_COST_PER)
            .packLogisticCostPer(UPDATED_PACK_LOGISTIC_COST_PER)
            .totalProductionCost(UPDATED_TOTAL_PRODUCTION_COST)
            .totalMaterialCost(UPDATED_TOTAL_MATERIAL_COST)
            .totalCostSgaProfit(UPDATED_TOTAL_COST_SGA_PROFIT)
            .sgaRate(UPDATED_SGA_RATE)
            .profit(UPDATED_PROFIT)
            .partPrice(UPDATED_PART_PRICE)
            .totalCost(UPDATED_TOTAL_COST)
            .totalSales(UPDATED_TOTAL_SALES)
            .totalProfit(UPDATED_TOTAL_PROFIT)
            .costMaterial(UPDATED_COST_MATERIAL)
            .totalContributionMargin(UPDATED_TOTAL_CONTRIBUTION_MARGIN)
            .contributionMargin(UPDATED_CONTRIBUTION_MARGIN)
            .materialContributionMargin(UPDATED_MATERIAL_CONTRIBUTION_MARGIN)
            .comments(UPDATED_COMMENTS)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);

        restNtQuoteCustomerInputOutputMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNtQuoteCustomerInputOutputMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNtQuoteCustomerInputOutputMaster))
            )
            .andExpect(status().isOk());

        // Validate the NtQuoteCustomerInputOutputMaster in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNtQuoteCustomerInputOutputMasterUpdatableFieldsEquals(
            partialUpdatedNtQuoteCustomerInputOutputMaster,
            getPersistedNtQuoteCustomerInputOutputMaster(partialUpdatedNtQuoteCustomerInputOutputMaster)
        );
    }

    @Test
    @Transactional
    void patchNonExistingNtQuoteCustomerInputOutputMaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerInputOutputMasterSearchRepository.findAll());
        ntQuoteCustomerInputOutputMaster.setId(longCount.incrementAndGet());

        // Create the NtQuoteCustomerInputOutputMaster
        NtQuoteCustomerInputOutputMasterDTO ntQuoteCustomerInputOutputMasterDTO = ntQuoteCustomerInputOutputMasterMapper.toDto(
            ntQuoteCustomerInputOutputMaster
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNtQuoteCustomerInputOutputMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ntQuoteCustomerInputOutputMasterDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ntQuoteCustomerInputOutputMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteCustomerInputOutputMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerInputOutputMasterSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNtQuoteCustomerInputOutputMaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerInputOutputMasterSearchRepository.findAll());
        ntQuoteCustomerInputOutputMaster.setId(longCount.incrementAndGet());

        // Create the NtQuoteCustomerInputOutputMaster
        NtQuoteCustomerInputOutputMasterDTO ntQuoteCustomerInputOutputMasterDTO = ntQuoteCustomerInputOutputMasterMapper.toDto(
            ntQuoteCustomerInputOutputMaster
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteCustomerInputOutputMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ntQuoteCustomerInputOutputMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteCustomerInputOutputMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerInputOutputMasterSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNtQuoteCustomerInputOutputMaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerInputOutputMasterSearchRepository.findAll());
        ntQuoteCustomerInputOutputMaster.setId(longCount.incrementAndGet());

        // Create the NtQuoteCustomerInputOutputMaster
        NtQuoteCustomerInputOutputMasterDTO ntQuoteCustomerInputOutputMasterDTO = ntQuoteCustomerInputOutputMasterMapper.toDto(
            ntQuoteCustomerInputOutputMaster
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteCustomerInputOutputMasterMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ntQuoteCustomerInputOutputMasterDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NtQuoteCustomerInputOutputMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerInputOutputMasterSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteNtQuoteCustomerInputOutputMaster() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputMaster = ntQuoteCustomerInputOutputMasterRepository.saveAndFlush(
            ntQuoteCustomerInputOutputMaster
        );
        ntQuoteCustomerInputOutputMasterRepository.save(ntQuoteCustomerInputOutputMaster);
        ntQuoteCustomerInputOutputMasterSearchRepository.save(ntQuoteCustomerInputOutputMaster);

        long databaseSizeBeforeDelete = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerInputOutputMasterSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the ntQuoteCustomerInputOutputMaster
        restNtQuoteCustomerInputOutputMasterMockMvc
            .perform(delete(ENTITY_API_URL_ID, ntQuoteCustomerInputOutputMaster.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerInputOutputMasterSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchNtQuoteCustomerInputOutputMaster() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputMaster = ntQuoteCustomerInputOutputMasterRepository.saveAndFlush(
            ntQuoteCustomerInputOutputMaster
        );
        ntQuoteCustomerInputOutputMasterSearchRepository.save(ntQuoteCustomerInputOutputMaster);

        // Search the ntQuoteCustomerInputOutputMaster
        restNtQuoteCustomerInputOutputMasterMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + ntQuoteCustomerInputOutputMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuoteCustomerInputOutputMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].materialDescription").value(hasItem(DEFAULT_MATERIAL_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].partNumber").value(hasItem(DEFAULT_PART_NUMBER)))
            .andExpect(jsonPath("$.[*].materialId").value(hasItem(DEFAULT_MATERIAL_ID)))
            .andExpect(jsonPath("$.[*].supplier").value(hasItem(DEFAULT_SUPPLIER)))
            .andExpect(jsonPath("$.[*].estAnnualVolume").value(hasItem(DEFAULT_EST_ANNUAL_VOLUME)))
            .andExpect(jsonPath("$.[*].estProductionRunYrs").value(hasItem(DEFAULT_EST_PRODUCTION_RUN_YRS)))
            .andExpect(jsonPath("$.[*].materialCostLb").value(hasItem(sameNumber(DEFAULT_MATERIAL_COST_LB))))
            .andExpect(jsonPath("$.[*].partWeightLb").value(hasItem(sameNumber(DEFAULT_PART_WEIGHT_LB))))
            .andExpect(jsonPath("$.[*].runnerWeightLb").value(hasItem(sameNumber(DEFAULT_RUNNER_WEIGHT_LB))))
            .andExpect(jsonPath("$.[*].machineSize").value(hasItem(DEFAULT_MACHINE_SIZE)))
            .andExpect(jsonPath("$.[*].machineRate").value(hasItem(sameNumber(DEFAULT_MACHINE_RATE))))
            .andExpect(jsonPath("$.[*].scrapRate").value(hasItem(sameNumber(DEFAULT_SCRAP_RATE))))
            .andExpect(jsonPath("$.[*].machineEfficiency").value(hasItem(sameNumber(DEFAULT_MACHINE_EFFICIENCY))))
            .andExpect(jsonPath("$.[*].fte").value(hasItem(DEFAULT_FTE)))
            .andExpect(jsonPath("$.[*].laborRate").value(hasItem(sameNumber(DEFAULT_LABOR_RATE))))
            .andExpect(jsonPath("$.[*].numberOfCavities").value(hasItem(DEFAULT_NUMBER_OF_CAVITIES)))
            .andExpect(jsonPath("$.[*].cycleTime").value(hasItem(DEFAULT_CYCLE_TIME)))
            .andExpect(jsonPath("$.[*].purchaseComponentCostPart").value(hasItem(sameNumber(DEFAULT_PURCHASE_COMPONENT_COST_PART))))
            .andExpect(jsonPath("$.[*].secondaryOperationExternalProcess").value(hasItem(DEFAULT_SECONDARY_OPERATION_EXTERNAL_PROCESS)))
            .andExpect(jsonPath("$.[*].secondaryOperationLaborRate").value(hasItem(sameNumber(DEFAULT_SECONDARY_OPERATION_LABOR_RATE))))
            .andExpect(jsonPath("$.[*].secondaryOperationMachineRate").value(hasItem(sameNumber(DEFAULT_SECONDARY_OPERATION_MACHINE_RATE))))
            .andExpect(jsonPath("$.[*].secondaryOperationCycleTime").value(hasItem(DEFAULT_SECONDARY_OPERATION_CYCLE_TIME)))
            .andExpect(jsonPath("$.[*].externalOperationRate").value(hasItem(sameNumber(DEFAULT_EXTERNAL_OPERATION_RATE))))
            .andExpect(jsonPath("$.[*].preventativeMaintenanceFrequency").value(hasItem(DEFAULT_PREVENTATIVE_MAINTENANCE_FREQUENCY)))
            .andExpect(jsonPath("$.[*].preventativeMaintenanceCost").value(hasItem(DEFAULT_PREVENTATIVE_MAINTENANCE_COST)))
            .andExpect(jsonPath("$.[*].targetProfit").value(hasItem(sameNumber(DEFAULT_TARGET_PROFIT))))
            .andExpect(jsonPath("$.[*].targetMaterialMarkup").value(hasItem(sameNumber(DEFAULT_TARGET_MATERIAL_MARKUP))))
            .andExpect(jsonPath("$.[*].actualMaterialCost").value(hasItem(sameNumber(DEFAULT_ACTUAL_MATERIAL_COST))))
            .andExpect(jsonPath("$.[*].partPerHours").value(hasItem(sameNumber(DEFAULT_PART_PER_HOURS))))
            .andExpect(jsonPath("$.[*].estLotSize").value(hasItem(sameNumber(DEFAULT_EST_LOT_SIZE))))
            .andExpect(jsonPath("$.[*].setupHours").value(hasItem(sameNumber(DEFAULT_SETUP_HOURS))))
            .andExpect(jsonPath("$.[*].externalOperationCostPer").value(hasItem(sameNumber(DEFAULT_EXTERNAL_OPERATION_COST_PER))))
            .andExpect(jsonPath("$.[*].externalMachineCostPer").value(hasItem(sameNumber(DEFAULT_EXTERNAL_MACHINE_COST_PER))))
            .andExpect(jsonPath("$.[*].extendedLaborCostPer").value(hasItem(sameNumber(DEFAULT_EXTENDED_LABOR_COST_PER))))
            .andExpect(jsonPath("$.[*].extendedMaterialCostPer").value(hasItem(sameNumber(DEFAULT_EXTENDED_MATERIAL_COST_PER))))
            .andExpect(jsonPath("$.[*].packLogisticCostPer").value(hasItem(sameNumber(DEFAULT_PACK_LOGISTIC_COST_PER))))
            .andExpect(jsonPath("$.[*].totalProductionCost").value(hasItem(sameNumber(DEFAULT_TOTAL_PRODUCTION_COST))))
            .andExpect(jsonPath("$.[*].totalMaterialCost").value(hasItem(sameNumber(DEFAULT_TOTAL_MATERIAL_COST))))
            .andExpect(jsonPath("$.[*].totalCostSgaProfit").value(hasItem(sameNumber(DEFAULT_TOTAL_COST_SGA_PROFIT))))
            .andExpect(jsonPath("$.[*].sgaRate").value(hasItem(sameNumber(DEFAULT_SGA_RATE))))
            .andExpect(jsonPath("$.[*].profit").value(hasItem(sameNumber(DEFAULT_PROFIT))))
            .andExpect(jsonPath("$.[*].partPrice").value(hasItem(sameNumber(DEFAULT_PART_PRICE))))
            .andExpect(jsonPath("$.[*].totalCost").value(hasItem(sameNumber(DEFAULT_TOTAL_COST))))
            .andExpect(jsonPath("$.[*].totalSales").value(hasItem(sameNumber(DEFAULT_TOTAL_SALES))))
            .andExpect(jsonPath("$.[*].totalProfit").value(hasItem(sameNumber(DEFAULT_TOTAL_PROFIT))))
            .andExpect(jsonPath("$.[*].costMaterial").value(hasItem(sameNumber(DEFAULT_COST_MATERIAL))))
            .andExpect(jsonPath("$.[*].totalContributionMargin").value(hasItem(sameNumber(DEFAULT_TOTAL_CONTRIBUTION_MARGIN))))
            .andExpect(jsonPath("$.[*].contributionMargin").value(hasItem(sameNumber(DEFAULT_CONTRIBUTION_MARGIN))))
            .andExpect(jsonPath("$.[*].materialContributionMargin").value(hasItem(sameNumber(DEFAULT_MATERIAL_CONTRIBUTION_MARGIN))))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    protected long getRepositoryCount() {
        return ntQuoteCustomerInputOutputMasterRepository.count();
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

    protected NtQuoteCustomerInputOutputMaster getPersistedNtQuoteCustomerInputOutputMaster(
        NtQuoteCustomerInputOutputMaster ntQuoteCustomerInputOutputMaster
    ) {
        return ntQuoteCustomerInputOutputMasterRepository.findById(ntQuoteCustomerInputOutputMaster.getId()).orElseThrow();
    }

    protected void assertPersistedNtQuoteCustomerInputOutputMasterToMatchAllProperties(
        NtQuoteCustomerInputOutputMaster expectedNtQuoteCustomerInputOutputMaster
    ) {
        assertNtQuoteCustomerInputOutputMasterAllPropertiesEquals(
            expectedNtQuoteCustomerInputOutputMaster,
            getPersistedNtQuoteCustomerInputOutputMaster(expectedNtQuoteCustomerInputOutputMaster)
        );
    }

    protected void assertPersistedNtQuoteCustomerInputOutputMasterToMatchUpdatableProperties(
        NtQuoteCustomerInputOutputMaster expectedNtQuoteCustomerInputOutputMaster
    ) {
        assertNtQuoteCustomerInputOutputMasterAllUpdatablePropertiesEquals(
            expectedNtQuoteCustomerInputOutputMaster,
            getPersistedNtQuoteCustomerInputOutputMaster(expectedNtQuoteCustomerInputOutputMaster)
        );
    }
}
