package com.yts.revaux.ntquote.web.rest;

import static com.yts.revaux.ntquote.domain.NtQuoteCustomerInputOutputVersionAsserts.*;
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
import com.yts.revaux.ntquote.domain.NtQuoteCustomerInputOutputVersion;
import com.yts.revaux.ntquote.repository.NtQuoteCustomerInputOutputVersionRepository;
import com.yts.revaux.ntquote.repository.search.NtQuoteCustomerInputOutputVersionSearchRepository;
import com.yts.revaux.ntquote.service.dto.NtQuoteCustomerInputOutputVersionDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuoteCustomerInputOutputVersionMapper;
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
 * Integration tests for the {@link NtQuoteCustomerInputOutputVersionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NtQuoteCustomerInputOutputVersionResourceIT {

    private static final Integer DEFAULT_SR_NO = 1;
    private static final Integer UPDATED_SR_NO = 2;
    private static final Integer SMALLER_SR_NO = 1 - 1;

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
    private static final Integer SMALLER_EST_ANNUAL_VOLUME = 1 - 1;

    private static final Integer DEFAULT_EST_PRODUCTION_RUN_YRS = 1;
    private static final Integer UPDATED_EST_PRODUCTION_RUN_YRS = 2;
    private static final Integer SMALLER_EST_PRODUCTION_RUN_YRS = 1 - 1;

    private static final BigDecimal DEFAULT_MATERIAL_COST_LB = new BigDecimal(1);
    private static final BigDecimal UPDATED_MATERIAL_COST_LB = new BigDecimal(2);
    private static final BigDecimal SMALLER_MATERIAL_COST_LB = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PART_WEIGHT_LB = new BigDecimal(1);
    private static final BigDecimal UPDATED_PART_WEIGHT_LB = new BigDecimal(2);
    private static final BigDecimal SMALLER_PART_WEIGHT_LB = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_RUNNER_WEIGHT_LB = new BigDecimal(1);
    private static final BigDecimal UPDATED_RUNNER_WEIGHT_LB = new BigDecimal(2);
    private static final BigDecimal SMALLER_RUNNER_WEIGHT_LB = new BigDecimal(1 - 1);

    private static final String DEFAULT_MACHINE_SIZE = "AAAAAAAAAA";
    private static final String UPDATED_MACHINE_SIZE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_MACHINE_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_MACHINE_RATE = new BigDecimal(2);
    private static final BigDecimal SMALLER_MACHINE_RATE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_SCRAP_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_SCRAP_RATE = new BigDecimal(2);
    private static final BigDecimal SMALLER_SCRAP_RATE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_MACHINE_EFFICIENCY = new BigDecimal(1);
    private static final BigDecimal UPDATED_MACHINE_EFFICIENCY = new BigDecimal(2);
    private static final BigDecimal SMALLER_MACHINE_EFFICIENCY = new BigDecimal(1 - 1);

    private static final String DEFAULT_FTE = "AAAAAAAAAA";
    private static final String UPDATED_FTE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_LABOR_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LABOR_RATE = new BigDecimal(2);
    private static final BigDecimal SMALLER_LABOR_RATE = new BigDecimal(1 - 1);

    private static final Integer DEFAULT_NUMBER_OF_CAVITIES = 1;
    private static final Integer UPDATED_NUMBER_OF_CAVITIES = 2;
    private static final Integer SMALLER_NUMBER_OF_CAVITIES = 1 - 1;

    private static final Integer DEFAULT_CYCLE_TIME = 1;
    private static final Integer UPDATED_CYCLE_TIME = 2;
    private static final Integer SMALLER_CYCLE_TIME = 1 - 1;

    private static final BigDecimal DEFAULT_PURCHASE_COMPONENT_COST_PART = new BigDecimal(1);
    private static final BigDecimal UPDATED_PURCHASE_COMPONENT_COST_PART = new BigDecimal(2);
    private static final BigDecimal SMALLER_PURCHASE_COMPONENT_COST_PART = new BigDecimal(1 - 1);

    private static final String DEFAULT_SECONDARY_OPERATION_EXTERNAL_PROCESS = "AAAAAAAAAA";
    private static final String UPDATED_SECONDARY_OPERATION_EXTERNAL_PROCESS = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_SECONDARY_OPERATION_LABOR_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_SECONDARY_OPERATION_LABOR_RATE = new BigDecimal(2);
    private static final BigDecimal SMALLER_SECONDARY_OPERATION_LABOR_RATE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_SECONDARY_OPERATION_MACHINE_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_SECONDARY_OPERATION_MACHINE_RATE = new BigDecimal(2);
    private static final BigDecimal SMALLER_SECONDARY_OPERATION_MACHINE_RATE = new BigDecimal(1 - 1);

    private static final Integer DEFAULT_SECONDARY_OPERATION_CYCLE_TIME = 1;
    private static final Integer UPDATED_SECONDARY_OPERATION_CYCLE_TIME = 2;
    private static final Integer SMALLER_SECONDARY_OPERATION_CYCLE_TIME = 1 - 1;

    private static final BigDecimal DEFAULT_EXTERNAL_OPERATION_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_EXTERNAL_OPERATION_RATE = new BigDecimal(2);
    private static final BigDecimal SMALLER_EXTERNAL_OPERATION_RATE = new BigDecimal(1 - 1);

    private static final Integer DEFAULT_PREVENTATIVE_MAINTENANCE_FREQUENCY = 1;
    private static final Integer UPDATED_PREVENTATIVE_MAINTENANCE_FREQUENCY = 2;
    private static final Integer SMALLER_PREVENTATIVE_MAINTENANCE_FREQUENCY = 1 - 1;

    private static final Integer DEFAULT_PREVENTATIVE_MAINTENANCE_COST = 1;
    private static final Integer UPDATED_PREVENTATIVE_MAINTENANCE_COST = 2;
    private static final Integer SMALLER_PREVENTATIVE_MAINTENANCE_COST = 1 - 1;

    private static final BigDecimal DEFAULT_TARGET_PROFIT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TARGET_PROFIT = new BigDecimal(2);
    private static final BigDecimal SMALLER_TARGET_PROFIT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TARGET_MATERIAL_MARKUP = new BigDecimal(1);
    private static final BigDecimal UPDATED_TARGET_MATERIAL_MARKUP = new BigDecimal(2);
    private static final BigDecimal SMALLER_TARGET_MATERIAL_MARKUP = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_ACTUAL_MATERIAL_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_ACTUAL_MATERIAL_COST = new BigDecimal(2);
    private static final BigDecimal SMALLER_ACTUAL_MATERIAL_COST = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PART_PER_HOURS = new BigDecimal(1);
    private static final BigDecimal UPDATED_PART_PER_HOURS = new BigDecimal(2);
    private static final BigDecimal SMALLER_PART_PER_HOURS = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_EST_LOT_SIZE = new BigDecimal(1);
    private static final BigDecimal UPDATED_EST_LOT_SIZE = new BigDecimal(2);
    private static final BigDecimal SMALLER_EST_LOT_SIZE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_SETUP_HOURS = new BigDecimal(1);
    private static final BigDecimal UPDATED_SETUP_HOURS = new BigDecimal(2);
    private static final BigDecimal SMALLER_SETUP_HOURS = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_EXTERNAL_OPERATION_COST_PER = new BigDecimal(1);
    private static final BigDecimal UPDATED_EXTERNAL_OPERATION_COST_PER = new BigDecimal(2);
    private static final BigDecimal SMALLER_EXTERNAL_OPERATION_COST_PER = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_EXTERNAL_MACHINE_COST_PER = new BigDecimal(1);
    private static final BigDecimal UPDATED_EXTERNAL_MACHINE_COST_PER = new BigDecimal(2);
    private static final BigDecimal SMALLER_EXTERNAL_MACHINE_COST_PER = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_EXTENDED_LABOR_COST_PER = new BigDecimal(1);
    private static final BigDecimal UPDATED_EXTENDED_LABOR_COST_PER = new BigDecimal(2);
    private static final BigDecimal SMALLER_EXTENDED_LABOR_COST_PER = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_EXTENDED_MATERIAL_COST_PER = new BigDecimal(1);
    private static final BigDecimal UPDATED_EXTENDED_MATERIAL_COST_PER = new BigDecimal(2);
    private static final BigDecimal SMALLER_EXTENDED_MATERIAL_COST_PER = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PACK_LOGISTIC_COST_PER = new BigDecimal(1);
    private static final BigDecimal UPDATED_PACK_LOGISTIC_COST_PER = new BigDecimal(2);
    private static final BigDecimal SMALLER_PACK_LOGISTIC_COST_PER = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL_PRODUCTION_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_PRODUCTION_COST = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_PRODUCTION_COST = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL_MATERIAL_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_MATERIAL_COST = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_MATERIAL_COST = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL_COST_SGA_PROFIT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_COST_SGA_PROFIT = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_COST_SGA_PROFIT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_SGA_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_SGA_RATE = new BigDecimal(2);
    private static final BigDecimal SMALLER_SGA_RATE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PROFIT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PROFIT = new BigDecimal(2);
    private static final BigDecimal SMALLER_PROFIT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PART_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PART_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_PART_PRICE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_COST = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_COST = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL_SALES = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_SALES = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_SALES = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL_PROFIT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_PROFIT = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_PROFIT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_COST_MATERIAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_COST_MATERIAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_COST_MATERIAL = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL_CONTRIBUTION_MARGIN = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_CONTRIBUTION_MARGIN = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_CONTRIBUTION_MARGIN = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CONTRIBUTION_MARGIN = new BigDecimal(1);
    private static final BigDecimal UPDATED_CONTRIBUTION_MARGIN = new BigDecimal(2);
    private static final BigDecimal SMALLER_CONTRIBUTION_MARGIN = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_MATERIAL_CONTRIBUTION_MARGIN = new BigDecimal(1);
    private static final BigDecimal UPDATED_MATERIAL_CONTRIBUTION_MARGIN = new BigDecimal(2);
    private static final BigDecimal SMALLER_MATERIAL_CONTRIBUTION_MARGIN = new BigDecimal(1 - 1);

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;
    private static final Integer SMALLER_VERSION = 1 - 1;

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

    private static final String ENTITY_API_URL = "/api/nt-quote-customer-input-output-versions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/nt-quote-customer-input-output-versions/_search";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NtQuoteCustomerInputOutputVersionRepository ntQuoteCustomerInputOutputVersionRepository;

    @Autowired
    private NtQuoteCustomerInputOutputVersionMapper ntQuoteCustomerInputOutputVersionMapper;

    @Autowired
    private NtQuoteCustomerInputOutputVersionSearchRepository ntQuoteCustomerInputOutputVersionSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNtQuoteCustomerInputOutputVersionMockMvc;

    private NtQuoteCustomerInputOutputVersion ntQuoteCustomerInputOutputVersion;

    private NtQuoteCustomerInputOutputVersion insertedNtQuoteCustomerInputOutputVersion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NtQuoteCustomerInputOutputVersion createEntity() {
        return new NtQuoteCustomerInputOutputVersion()
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
            .version(DEFAULT_VERSION)
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
    public static NtQuoteCustomerInputOutputVersion createUpdatedEntity() {
        return new NtQuoteCustomerInputOutputVersion()
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
            .version(UPDATED_VERSION)
            .comments(UPDATED_COMMENTS)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);
    }

    @BeforeEach
    public void initTest() {
        ntQuoteCustomerInputOutputVersion = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedNtQuoteCustomerInputOutputVersion != null) {
            ntQuoteCustomerInputOutputVersionRepository.delete(insertedNtQuoteCustomerInputOutputVersion);
            ntQuoteCustomerInputOutputVersionSearchRepository.delete(insertedNtQuoteCustomerInputOutputVersion);
            insertedNtQuoteCustomerInputOutputVersion = null;
        }
    }

    @Test
    @Transactional
    void createNtQuoteCustomerInputOutputVersion() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerInputOutputVersionSearchRepository.findAll());
        // Create the NtQuoteCustomerInputOutputVersion
        NtQuoteCustomerInputOutputVersionDTO ntQuoteCustomerInputOutputVersionDTO = ntQuoteCustomerInputOutputVersionMapper.toDto(
            ntQuoteCustomerInputOutputVersion
        );
        var returnedNtQuoteCustomerInputOutputVersionDTO = om.readValue(
            restNtQuoteCustomerInputOutputVersionMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(ntQuoteCustomerInputOutputVersionDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            NtQuoteCustomerInputOutputVersionDTO.class
        );

        // Validate the NtQuoteCustomerInputOutputVersion in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionMapper.toEntity(
            returnedNtQuoteCustomerInputOutputVersionDTO
        );
        assertNtQuoteCustomerInputOutputVersionUpdatableFieldsEquals(
            returnedNtQuoteCustomerInputOutputVersion,
            getPersistedNtQuoteCustomerInputOutputVersion(returnedNtQuoteCustomerInputOutputVersion)
        );

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerInputOutputVersionSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });

        insertedNtQuoteCustomerInputOutputVersion = returnedNtQuoteCustomerInputOutputVersion;
    }

    @Test
    @Transactional
    void createNtQuoteCustomerInputOutputVersionWithExistingId() throws Exception {
        // Create the NtQuoteCustomerInputOutputVersion with an existing ID
        ntQuoteCustomerInputOutputVersion.setId(1L);
        NtQuoteCustomerInputOutputVersionDTO ntQuoteCustomerInputOutputVersionDTO = ntQuoteCustomerInputOutputVersionMapper.toDto(
            ntQuoteCustomerInputOutputVersion
        );

        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerInputOutputVersionSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restNtQuoteCustomerInputOutputVersionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteCustomerInputOutputVersionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteCustomerInputOutputVersion in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerInputOutputVersionSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkUidIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerInputOutputVersionSearchRepository.findAll());
        // set the field null
        ntQuoteCustomerInputOutputVersion.setUid(null);

        // Create the NtQuoteCustomerInputOutputVersion, which fails.
        NtQuoteCustomerInputOutputVersionDTO ntQuoteCustomerInputOutputVersionDTO = ntQuoteCustomerInputOutputVersionMapper.toDto(
            ntQuoteCustomerInputOutputVersion
        );

        restNtQuoteCustomerInputOutputVersionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteCustomerInputOutputVersionDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerInputOutputVersionSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersions() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList
        restNtQuoteCustomerInputOutputVersionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuoteCustomerInputOutputVersion.getId().intValue())))
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
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getNtQuoteCustomerInputOutputVersion() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get the ntQuoteCustomerInputOutputVersion
        restNtQuoteCustomerInputOutputVersionMockMvc
            .perform(get(ENTITY_API_URL_ID, ntQuoteCustomerInputOutputVersion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ntQuoteCustomerInputOutputVersion.getId().intValue()))
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
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNtQuoteCustomerInputOutputVersionsByIdFiltering() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        Long id = ntQuoteCustomerInputOutputVersion.getId();

        defaultNtQuoteCustomerInputOutputVersionFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultNtQuoteCustomerInputOutputVersionFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultNtQuoteCustomerInputOutputVersionFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySrNoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where srNo equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering("srNo.equals=" + DEFAULT_SR_NO, "srNo.equals=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySrNoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where srNo in
        defaultNtQuoteCustomerInputOutputVersionFiltering("srNo.in=" + DEFAULT_SR_NO + "," + UPDATED_SR_NO, "srNo.in=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySrNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where srNo is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("srNo.specified=true", "srNo.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySrNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where srNo is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "srNo.greaterThanOrEqual=" + DEFAULT_SR_NO,
            "srNo.greaterThanOrEqual=" + UPDATED_SR_NO
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySrNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where srNo is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering("srNo.lessThanOrEqual=" + DEFAULT_SR_NO, "srNo.lessThanOrEqual=" + SMALLER_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySrNoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where srNo is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering("srNo.lessThan=" + UPDATED_SR_NO, "srNo.lessThan=" + DEFAULT_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySrNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where srNo is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering("srNo.greaterThan=" + SMALLER_SR_NO, "srNo.greaterThan=" + DEFAULT_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where uid equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering("uid.equals=" + DEFAULT_UID, "uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where uid in
        defaultNtQuoteCustomerInputOutputVersionFiltering("uid.in=" + DEFAULT_UID + "," + UPDATED_UID, "uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where uid is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("uid.specified=true", "uid.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMaterialDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where materialDescription equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "materialDescription.equals=" + DEFAULT_MATERIAL_DESCRIPTION,
            "materialDescription.equals=" + UPDATED_MATERIAL_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMaterialDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where materialDescription in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "materialDescription.in=" + DEFAULT_MATERIAL_DESCRIPTION + "," + UPDATED_MATERIAL_DESCRIPTION,
            "materialDescription.in=" + UPDATED_MATERIAL_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMaterialDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where materialDescription is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("materialDescription.specified=true", "materialDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMaterialDescriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where materialDescription contains
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "materialDescription.contains=" + DEFAULT_MATERIAL_DESCRIPTION,
            "materialDescription.contains=" + UPDATED_MATERIAL_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMaterialDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where materialDescription does not contain
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "materialDescription.doesNotContain=" + UPDATED_MATERIAL_DESCRIPTION,
            "materialDescription.doesNotContain=" + DEFAULT_MATERIAL_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPartNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where partNumber equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "partNumber.equals=" + DEFAULT_PART_NUMBER,
            "partNumber.equals=" + UPDATED_PART_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPartNumberIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where partNumber in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "partNumber.in=" + DEFAULT_PART_NUMBER + "," + UPDATED_PART_NUMBER,
            "partNumber.in=" + UPDATED_PART_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPartNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where partNumber is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("partNumber.specified=true", "partNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPartNumberContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where partNumber contains
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "partNumber.contains=" + DEFAULT_PART_NUMBER,
            "partNumber.contains=" + UPDATED_PART_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPartNumberNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where partNumber does not contain
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "partNumber.doesNotContain=" + UPDATED_PART_NUMBER,
            "partNumber.doesNotContain=" + DEFAULT_PART_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMaterialIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where materialId equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "materialId.equals=" + DEFAULT_MATERIAL_ID,
            "materialId.equals=" + UPDATED_MATERIAL_ID
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMaterialIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where materialId in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "materialId.in=" + DEFAULT_MATERIAL_ID + "," + UPDATED_MATERIAL_ID,
            "materialId.in=" + UPDATED_MATERIAL_ID
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMaterialIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where materialId is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("materialId.specified=true", "materialId.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMaterialIdContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where materialId contains
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "materialId.contains=" + DEFAULT_MATERIAL_ID,
            "materialId.contains=" + UPDATED_MATERIAL_ID
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMaterialIdNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where materialId does not contain
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "materialId.doesNotContain=" + UPDATED_MATERIAL_ID,
            "materialId.doesNotContain=" + DEFAULT_MATERIAL_ID
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySupplierIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where supplier equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering("supplier.equals=" + DEFAULT_SUPPLIER, "supplier.equals=" + UPDATED_SUPPLIER);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySupplierIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where supplier in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "supplier.in=" + DEFAULT_SUPPLIER + "," + UPDATED_SUPPLIER,
            "supplier.in=" + UPDATED_SUPPLIER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySupplierIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where supplier is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("supplier.specified=true", "supplier.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySupplierContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where supplier contains
        defaultNtQuoteCustomerInputOutputVersionFiltering("supplier.contains=" + DEFAULT_SUPPLIER, "supplier.contains=" + UPDATED_SUPPLIER);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySupplierNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where supplier does not contain
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "supplier.doesNotContain=" + UPDATED_SUPPLIER,
            "supplier.doesNotContain=" + DEFAULT_SUPPLIER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByEstAnnualVolumeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where estAnnualVolume equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "estAnnualVolume.equals=" + DEFAULT_EST_ANNUAL_VOLUME,
            "estAnnualVolume.equals=" + UPDATED_EST_ANNUAL_VOLUME
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByEstAnnualVolumeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where estAnnualVolume in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "estAnnualVolume.in=" + DEFAULT_EST_ANNUAL_VOLUME + "," + UPDATED_EST_ANNUAL_VOLUME,
            "estAnnualVolume.in=" + UPDATED_EST_ANNUAL_VOLUME
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByEstAnnualVolumeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where estAnnualVolume is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("estAnnualVolume.specified=true", "estAnnualVolume.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByEstAnnualVolumeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where estAnnualVolume is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "estAnnualVolume.greaterThanOrEqual=" + DEFAULT_EST_ANNUAL_VOLUME,
            "estAnnualVolume.greaterThanOrEqual=" + UPDATED_EST_ANNUAL_VOLUME
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByEstAnnualVolumeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where estAnnualVolume is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "estAnnualVolume.lessThanOrEqual=" + DEFAULT_EST_ANNUAL_VOLUME,
            "estAnnualVolume.lessThanOrEqual=" + SMALLER_EST_ANNUAL_VOLUME
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByEstAnnualVolumeIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where estAnnualVolume is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "estAnnualVolume.lessThan=" + UPDATED_EST_ANNUAL_VOLUME,
            "estAnnualVolume.lessThan=" + DEFAULT_EST_ANNUAL_VOLUME
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByEstAnnualVolumeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where estAnnualVolume is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "estAnnualVolume.greaterThan=" + SMALLER_EST_ANNUAL_VOLUME,
            "estAnnualVolume.greaterThan=" + DEFAULT_EST_ANNUAL_VOLUME
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByEstProductionRunYrsIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where estProductionRunYrs equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "estProductionRunYrs.equals=" + DEFAULT_EST_PRODUCTION_RUN_YRS,
            "estProductionRunYrs.equals=" + UPDATED_EST_PRODUCTION_RUN_YRS
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByEstProductionRunYrsIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where estProductionRunYrs in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "estProductionRunYrs.in=" + DEFAULT_EST_PRODUCTION_RUN_YRS + "," + UPDATED_EST_PRODUCTION_RUN_YRS,
            "estProductionRunYrs.in=" + UPDATED_EST_PRODUCTION_RUN_YRS
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByEstProductionRunYrsIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where estProductionRunYrs is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("estProductionRunYrs.specified=true", "estProductionRunYrs.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByEstProductionRunYrsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where estProductionRunYrs is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "estProductionRunYrs.greaterThanOrEqual=" + DEFAULT_EST_PRODUCTION_RUN_YRS,
            "estProductionRunYrs.greaterThanOrEqual=" + UPDATED_EST_PRODUCTION_RUN_YRS
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByEstProductionRunYrsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where estProductionRunYrs is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "estProductionRunYrs.lessThanOrEqual=" + DEFAULT_EST_PRODUCTION_RUN_YRS,
            "estProductionRunYrs.lessThanOrEqual=" + SMALLER_EST_PRODUCTION_RUN_YRS
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByEstProductionRunYrsIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where estProductionRunYrs is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "estProductionRunYrs.lessThan=" + UPDATED_EST_PRODUCTION_RUN_YRS,
            "estProductionRunYrs.lessThan=" + DEFAULT_EST_PRODUCTION_RUN_YRS
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByEstProductionRunYrsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where estProductionRunYrs is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "estProductionRunYrs.greaterThan=" + SMALLER_EST_PRODUCTION_RUN_YRS,
            "estProductionRunYrs.greaterThan=" + DEFAULT_EST_PRODUCTION_RUN_YRS
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMaterialCostLbIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where materialCostLb equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "materialCostLb.equals=" + DEFAULT_MATERIAL_COST_LB,
            "materialCostLb.equals=" + UPDATED_MATERIAL_COST_LB
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMaterialCostLbIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where materialCostLb in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "materialCostLb.in=" + DEFAULT_MATERIAL_COST_LB + "," + UPDATED_MATERIAL_COST_LB,
            "materialCostLb.in=" + UPDATED_MATERIAL_COST_LB
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMaterialCostLbIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where materialCostLb is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("materialCostLb.specified=true", "materialCostLb.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMaterialCostLbIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where materialCostLb is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "materialCostLb.greaterThanOrEqual=" + DEFAULT_MATERIAL_COST_LB,
            "materialCostLb.greaterThanOrEqual=" + UPDATED_MATERIAL_COST_LB
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMaterialCostLbIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where materialCostLb is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "materialCostLb.lessThanOrEqual=" + DEFAULT_MATERIAL_COST_LB,
            "materialCostLb.lessThanOrEqual=" + SMALLER_MATERIAL_COST_LB
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMaterialCostLbIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where materialCostLb is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "materialCostLb.lessThan=" + UPDATED_MATERIAL_COST_LB,
            "materialCostLb.lessThan=" + DEFAULT_MATERIAL_COST_LB
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMaterialCostLbIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where materialCostLb is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "materialCostLb.greaterThan=" + SMALLER_MATERIAL_COST_LB,
            "materialCostLb.greaterThan=" + DEFAULT_MATERIAL_COST_LB
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPartWeightLbIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where partWeightLb equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "partWeightLb.equals=" + DEFAULT_PART_WEIGHT_LB,
            "partWeightLb.equals=" + UPDATED_PART_WEIGHT_LB
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPartWeightLbIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where partWeightLb in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "partWeightLb.in=" + DEFAULT_PART_WEIGHT_LB + "," + UPDATED_PART_WEIGHT_LB,
            "partWeightLb.in=" + UPDATED_PART_WEIGHT_LB
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPartWeightLbIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where partWeightLb is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("partWeightLb.specified=true", "partWeightLb.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPartWeightLbIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where partWeightLb is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "partWeightLb.greaterThanOrEqual=" + DEFAULT_PART_WEIGHT_LB,
            "partWeightLb.greaterThanOrEqual=" + UPDATED_PART_WEIGHT_LB
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPartWeightLbIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where partWeightLb is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "partWeightLb.lessThanOrEqual=" + DEFAULT_PART_WEIGHT_LB,
            "partWeightLb.lessThanOrEqual=" + SMALLER_PART_WEIGHT_LB
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPartWeightLbIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where partWeightLb is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "partWeightLb.lessThan=" + UPDATED_PART_WEIGHT_LB,
            "partWeightLb.lessThan=" + DEFAULT_PART_WEIGHT_LB
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPartWeightLbIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where partWeightLb is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "partWeightLb.greaterThan=" + SMALLER_PART_WEIGHT_LB,
            "partWeightLb.greaterThan=" + DEFAULT_PART_WEIGHT_LB
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByRunnerWeightLbIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where runnerWeightLb equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "runnerWeightLb.equals=" + DEFAULT_RUNNER_WEIGHT_LB,
            "runnerWeightLb.equals=" + UPDATED_RUNNER_WEIGHT_LB
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByRunnerWeightLbIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where runnerWeightLb in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "runnerWeightLb.in=" + DEFAULT_RUNNER_WEIGHT_LB + "," + UPDATED_RUNNER_WEIGHT_LB,
            "runnerWeightLb.in=" + UPDATED_RUNNER_WEIGHT_LB
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByRunnerWeightLbIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where runnerWeightLb is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("runnerWeightLb.specified=true", "runnerWeightLb.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByRunnerWeightLbIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where runnerWeightLb is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "runnerWeightLb.greaterThanOrEqual=" + DEFAULT_RUNNER_WEIGHT_LB,
            "runnerWeightLb.greaterThanOrEqual=" + UPDATED_RUNNER_WEIGHT_LB
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByRunnerWeightLbIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where runnerWeightLb is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "runnerWeightLb.lessThanOrEqual=" + DEFAULT_RUNNER_WEIGHT_LB,
            "runnerWeightLb.lessThanOrEqual=" + SMALLER_RUNNER_WEIGHT_LB
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByRunnerWeightLbIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where runnerWeightLb is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "runnerWeightLb.lessThan=" + UPDATED_RUNNER_WEIGHT_LB,
            "runnerWeightLb.lessThan=" + DEFAULT_RUNNER_WEIGHT_LB
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByRunnerWeightLbIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where runnerWeightLb is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "runnerWeightLb.greaterThan=" + SMALLER_RUNNER_WEIGHT_LB,
            "runnerWeightLb.greaterThan=" + DEFAULT_RUNNER_WEIGHT_LB
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMachineSizeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where machineSize equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "machineSize.equals=" + DEFAULT_MACHINE_SIZE,
            "machineSize.equals=" + UPDATED_MACHINE_SIZE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMachineSizeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where machineSize in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "machineSize.in=" + DEFAULT_MACHINE_SIZE + "," + UPDATED_MACHINE_SIZE,
            "machineSize.in=" + UPDATED_MACHINE_SIZE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMachineSizeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where machineSize is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("machineSize.specified=true", "machineSize.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMachineSizeContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where machineSize contains
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "machineSize.contains=" + DEFAULT_MACHINE_SIZE,
            "machineSize.contains=" + UPDATED_MACHINE_SIZE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMachineSizeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where machineSize does not contain
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "machineSize.doesNotContain=" + UPDATED_MACHINE_SIZE,
            "machineSize.doesNotContain=" + DEFAULT_MACHINE_SIZE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMachineRateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where machineRate equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "machineRate.equals=" + DEFAULT_MACHINE_RATE,
            "machineRate.equals=" + UPDATED_MACHINE_RATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMachineRateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where machineRate in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "machineRate.in=" + DEFAULT_MACHINE_RATE + "," + UPDATED_MACHINE_RATE,
            "machineRate.in=" + UPDATED_MACHINE_RATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMachineRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where machineRate is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("machineRate.specified=true", "machineRate.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMachineRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where machineRate is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "machineRate.greaterThanOrEqual=" + DEFAULT_MACHINE_RATE,
            "machineRate.greaterThanOrEqual=" + UPDATED_MACHINE_RATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMachineRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where machineRate is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "machineRate.lessThanOrEqual=" + DEFAULT_MACHINE_RATE,
            "machineRate.lessThanOrEqual=" + SMALLER_MACHINE_RATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMachineRateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where machineRate is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "machineRate.lessThan=" + UPDATED_MACHINE_RATE,
            "machineRate.lessThan=" + DEFAULT_MACHINE_RATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMachineRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where machineRate is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "machineRate.greaterThan=" + SMALLER_MACHINE_RATE,
            "machineRate.greaterThan=" + DEFAULT_MACHINE_RATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByScrapRateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where scrapRate equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "scrapRate.equals=" + DEFAULT_SCRAP_RATE,
            "scrapRate.equals=" + UPDATED_SCRAP_RATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByScrapRateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where scrapRate in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "scrapRate.in=" + DEFAULT_SCRAP_RATE + "," + UPDATED_SCRAP_RATE,
            "scrapRate.in=" + UPDATED_SCRAP_RATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByScrapRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where scrapRate is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("scrapRate.specified=true", "scrapRate.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByScrapRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where scrapRate is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "scrapRate.greaterThanOrEqual=" + DEFAULT_SCRAP_RATE,
            "scrapRate.greaterThanOrEqual=" + UPDATED_SCRAP_RATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByScrapRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where scrapRate is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "scrapRate.lessThanOrEqual=" + DEFAULT_SCRAP_RATE,
            "scrapRate.lessThanOrEqual=" + SMALLER_SCRAP_RATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByScrapRateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where scrapRate is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "scrapRate.lessThan=" + UPDATED_SCRAP_RATE,
            "scrapRate.lessThan=" + DEFAULT_SCRAP_RATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByScrapRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where scrapRate is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "scrapRate.greaterThan=" + SMALLER_SCRAP_RATE,
            "scrapRate.greaterThan=" + DEFAULT_SCRAP_RATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMachineEfficiencyIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where machineEfficiency equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "machineEfficiency.equals=" + DEFAULT_MACHINE_EFFICIENCY,
            "machineEfficiency.equals=" + UPDATED_MACHINE_EFFICIENCY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMachineEfficiencyIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where machineEfficiency in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "machineEfficiency.in=" + DEFAULT_MACHINE_EFFICIENCY + "," + UPDATED_MACHINE_EFFICIENCY,
            "machineEfficiency.in=" + UPDATED_MACHINE_EFFICIENCY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMachineEfficiencyIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where machineEfficiency is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("machineEfficiency.specified=true", "machineEfficiency.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMachineEfficiencyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where machineEfficiency is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "machineEfficiency.greaterThanOrEqual=" + DEFAULT_MACHINE_EFFICIENCY,
            "machineEfficiency.greaterThanOrEqual=" + UPDATED_MACHINE_EFFICIENCY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMachineEfficiencyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where machineEfficiency is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "machineEfficiency.lessThanOrEqual=" + DEFAULT_MACHINE_EFFICIENCY,
            "machineEfficiency.lessThanOrEqual=" + SMALLER_MACHINE_EFFICIENCY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMachineEfficiencyIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where machineEfficiency is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "machineEfficiency.lessThan=" + UPDATED_MACHINE_EFFICIENCY,
            "machineEfficiency.lessThan=" + DEFAULT_MACHINE_EFFICIENCY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMachineEfficiencyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where machineEfficiency is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "machineEfficiency.greaterThan=" + SMALLER_MACHINE_EFFICIENCY,
            "machineEfficiency.greaterThan=" + DEFAULT_MACHINE_EFFICIENCY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByFteIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where fte equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering("fte.equals=" + DEFAULT_FTE, "fte.equals=" + UPDATED_FTE);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByFteIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where fte in
        defaultNtQuoteCustomerInputOutputVersionFiltering("fte.in=" + DEFAULT_FTE + "," + UPDATED_FTE, "fte.in=" + UPDATED_FTE);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByFteIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where fte is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("fte.specified=true", "fte.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByFteContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where fte contains
        defaultNtQuoteCustomerInputOutputVersionFiltering("fte.contains=" + DEFAULT_FTE, "fte.contains=" + UPDATED_FTE);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByFteNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where fte does not contain
        defaultNtQuoteCustomerInputOutputVersionFiltering("fte.doesNotContain=" + UPDATED_FTE, "fte.doesNotContain=" + DEFAULT_FTE);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByLaborRateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where laborRate equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "laborRate.equals=" + DEFAULT_LABOR_RATE,
            "laborRate.equals=" + UPDATED_LABOR_RATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByLaborRateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where laborRate in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "laborRate.in=" + DEFAULT_LABOR_RATE + "," + UPDATED_LABOR_RATE,
            "laborRate.in=" + UPDATED_LABOR_RATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByLaborRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where laborRate is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("laborRate.specified=true", "laborRate.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByLaborRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where laborRate is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "laborRate.greaterThanOrEqual=" + DEFAULT_LABOR_RATE,
            "laborRate.greaterThanOrEqual=" + UPDATED_LABOR_RATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByLaborRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where laborRate is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "laborRate.lessThanOrEqual=" + DEFAULT_LABOR_RATE,
            "laborRate.lessThanOrEqual=" + SMALLER_LABOR_RATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByLaborRateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where laborRate is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "laborRate.lessThan=" + UPDATED_LABOR_RATE,
            "laborRate.lessThan=" + DEFAULT_LABOR_RATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByLaborRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where laborRate is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "laborRate.greaterThan=" + SMALLER_LABOR_RATE,
            "laborRate.greaterThan=" + DEFAULT_LABOR_RATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByNumberOfCavitiesIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where numberOfCavities equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "numberOfCavities.equals=" + DEFAULT_NUMBER_OF_CAVITIES,
            "numberOfCavities.equals=" + UPDATED_NUMBER_OF_CAVITIES
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByNumberOfCavitiesIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where numberOfCavities in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "numberOfCavities.in=" + DEFAULT_NUMBER_OF_CAVITIES + "," + UPDATED_NUMBER_OF_CAVITIES,
            "numberOfCavities.in=" + UPDATED_NUMBER_OF_CAVITIES
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByNumberOfCavitiesIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where numberOfCavities is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("numberOfCavities.specified=true", "numberOfCavities.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByNumberOfCavitiesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where numberOfCavities is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "numberOfCavities.greaterThanOrEqual=" + DEFAULT_NUMBER_OF_CAVITIES,
            "numberOfCavities.greaterThanOrEqual=" + UPDATED_NUMBER_OF_CAVITIES
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByNumberOfCavitiesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where numberOfCavities is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "numberOfCavities.lessThanOrEqual=" + DEFAULT_NUMBER_OF_CAVITIES,
            "numberOfCavities.lessThanOrEqual=" + SMALLER_NUMBER_OF_CAVITIES
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByNumberOfCavitiesIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where numberOfCavities is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "numberOfCavities.lessThan=" + UPDATED_NUMBER_OF_CAVITIES,
            "numberOfCavities.lessThan=" + DEFAULT_NUMBER_OF_CAVITIES
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByNumberOfCavitiesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where numberOfCavities is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "numberOfCavities.greaterThan=" + SMALLER_NUMBER_OF_CAVITIES,
            "numberOfCavities.greaterThan=" + DEFAULT_NUMBER_OF_CAVITIES
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByCycleTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where cycleTime equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "cycleTime.equals=" + DEFAULT_CYCLE_TIME,
            "cycleTime.equals=" + UPDATED_CYCLE_TIME
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByCycleTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where cycleTime in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "cycleTime.in=" + DEFAULT_CYCLE_TIME + "," + UPDATED_CYCLE_TIME,
            "cycleTime.in=" + UPDATED_CYCLE_TIME
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByCycleTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where cycleTime is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("cycleTime.specified=true", "cycleTime.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByCycleTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where cycleTime is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "cycleTime.greaterThanOrEqual=" + DEFAULT_CYCLE_TIME,
            "cycleTime.greaterThanOrEqual=" + UPDATED_CYCLE_TIME
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByCycleTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where cycleTime is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "cycleTime.lessThanOrEqual=" + DEFAULT_CYCLE_TIME,
            "cycleTime.lessThanOrEqual=" + SMALLER_CYCLE_TIME
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByCycleTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where cycleTime is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "cycleTime.lessThan=" + UPDATED_CYCLE_TIME,
            "cycleTime.lessThan=" + DEFAULT_CYCLE_TIME
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByCycleTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where cycleTime is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "cycleTime.greaterThan=" + SMALLER_CYCLE_TIME,
            "cycleTime.greaterThan=" + DEFAULT_CYCLE_TIME
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPurchaseComponentCostPartIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where purchaseComponentCostPart equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "purchaseComponentCostPart.equals=" + DEFAULT_PURCHASE_COMPONENT_COST_PART,
            "purchaseComponentCostPart.equals=" + UPDATED_PURCHASE_COMPONENT_COST_PART
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPurchaseComponentCostPartIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where purchaseComponentCostPart in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "purchaseComponentCostPart.in=" + DEFAULT_PURCHASE_COMPONENT_COST_PART + "," + UPDATED_PURCHASE_COMPONENT_COST_PART,
            "purchaseComponentCostPart.in=" + UPDATED_PURCHASE_COMPONENT_COST_PART
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPurchaseComponentCostPartIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where purchaseComponentCostPart is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "purchaseComponentCostPart.specified=true",
            "purchaseComponentCostPart.specified=false"
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPurchaseComponentCostPartIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where purchaseComponentCostPart is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "purchaseComponentCostPart.greaterThanOrEqual=" + DEFAULT_PURCHASE_COMPONENT_COST_PART,
            "purchaseComponentCostPart.greaterThanOrEqual=" + UPDATED_PURCHASE_COMPONENT_COST_PART
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPurchaseComponentCostPartIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where purchaseComponentCostPart is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "purchaseComponentCostPart.lessThanOrEqual=" + DEFAULT_PURCHASE_COMPONENT_COST_PART,
            "purchaseComponentCostPart.lessThanOrEqual=" + SMALLER_PURCHASE_COMPONENT_COST_PART
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPurchaseComponentCostPartIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where purchaseComponentCostPart is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "purchaseComponentCostPart.lessThan=" + UPDATED_PURCHASE_COMPONENT_COST_PART,
            "purchaseComponentCostPart.lessThan=" + DEFAULT_PURCHASE_COMPONENT_COST_PART
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPurchaseComponentCostPartIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where purchaseComponentCostPart is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "purchaseComponentCostPart.greaterThan=" + SMALLER_PURCHASE_COMPONENT_COST_PART,
            "purchaseComponentCostPart.greaterThan=" + DEFAULT_PURCHASE_COMPONENT_COST_PART
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySecondaryOperationExternalProcessIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where secondaryOperationExternalProcess equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "secondaryOperationExternalProcess.equals=" + DEFAULT_SECONDARY_OPERATION_EXTERNAL_PROCESS,
            "secondaryOperationExternalProcess.equals=" + UPDATED_SECONDARY_OPERATION_EXTERNAL_PROCESS
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySecondaryOperationExternalProcessIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where secondaryOperationExternalProcess in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "secondaryOperationExternalProcess.in=" +
            DEFAULT_SECONDARY_OPERATION_EXTERNAL_PROCESS +
            "," +
            UPDATED_SECONDARY_OPERATION_EXTERNAL_PROCESS,
            "secondaryOperationExternalProcess.in=" + UPDATED_SECONDARY_OPERATION_EXTERNAL_PROCESS
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySecondaryOperationExternalProcessIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where secondaryOperationExternalProcess is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "secondaryOperationExternalProcess.specified=true",
            "secondaryOperationExternalProcess.specified=false"
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySecondaryOperationExternalProcessContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where secondaryOperationExternalProcess contains
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "secondaryOperationExternalProcess.contains=" + DEFAULT_SECONDARY_OPERATION_EXTERNAL_PROCESS,
            "secondaryOperationExternalProcess.contains=" + UPDATED_SECONDARY_OPERATION_EXTERNAL_PROCESS
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySecondaryOperationExternalProcessNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where secondaryOperationExternalProcess does not contain
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "secondaryOperationExternalProcess.doesNotContain=" + UPDATED_SECONDARY_OPERATION_EXTERNAL_PROCESS,
            "secondaryOperationExternalProcess.doesNotContain=" + DEFAULT_SECONDARY_OPERATION_EXTERNAL_PROCESS
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySecondaryOperationLaborRateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where secondaryOperationLaborRate equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "secondaryOperationLaborRate.equals=" + DEFAULT_SECONDARY_OPERATION_LABOR_RATE,
            "secondaryOperationLaborRate.equals=" + UPDATED_SECONDARY_OPERATION_LABOR_RATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySecondaryOperationLaborRateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where secondaryOperationLaborRate in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "secondaryOperationLaborRate.in=" + DEFAULT_SECONDARY_OPERATION_LABOR_RATE + "," + UPDATED_SECONDARY_OPERATION_LABOR_RATE,
            "secondaryOperationLaborRate.in=" + UPDATED_SECONDARY_OPERATION_LABOR_RATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySecondaryOperationLaborRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where secondaryOperationLaborRate is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "secondaryOperationLaborRate.specified=true",
            "secondaryOperationLaborRate.specified=false"
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySecondaryOperationLaborRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where secondaryOperationLaborRate is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "secondaryOperationLaborRate.greaterThanOrEqual=" + DEFAULT_SECONDARY_OPERATION_LABOR_RATE,
            "secondaryOperationLaborRate.greaterThanOrEqual=" + UPDATED_SECONDARY_OPERATION_LABOR_RATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySecondaryOperationLaborRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where secondaryOperationLaborRate is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "secondaryOperationLaborRate.lessThanOrEqual=" + DEFAULT_SECONDARY_OPERATION_LABOR_RATE,
            "secondaryOperationLaborRate.lessThanOrEqual=" + SMALLER_SECONDARY_OPERATION_LABOR_RATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySecondaryOperationLaborRateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where secondaryOperationLaborRate is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "secondaryOperationLaborRate.lessThan=" + UPDATED_SECONDARY_OPERATION_LABOR_RATE,
            "secondaryOperationLaborRate.lessThan=" + DEFAULT_SECONDARY_OPERATION_LABOR_RATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySecondaryOperationLaborRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where secondaryOperationLaborRate is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "secondaryOperationLaborRate.greaterThan=" + SMALLER_SECONDARY_OPERATION_LABOR_RATE,
            "secondaryOperationLaborRate.greaterThan=" + DEFAULT_SECONDARY_OPERATION_LABOR_RATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySecondaryOperationMachineRateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where secondaryOperationMachineRate equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "secondaryOperationMachineRate.equals=" + DEFAULT_SECONDARY_OPERATION_MACHINE_RATE,
            "secondaryOperationMachineRate.equals=" + UPDATED_SECONDARY_OPERATION_MACHINE_RATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySecondaryOperationMachineRateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where secondaryOperationMachineRate in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "secondaryOperationMachineRate.in=" + DEFAULT_SECONDARY_OPERATION_MACHINE_RATE + "," + UPDATED_SECONDARY_OPERATION_MACHINE_RATE,
            "secondaryOperationMachineRate.in=" + UPDATED_SECONDARY_OPERATION_MACHINE_RATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySecondaryOperationMachineRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where secondaryOperationMachineRate is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "secondaryOperationMachineRate.specified=true",
            "secondaryOperationMachineRate.specified=false"
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySecondaryOperationMachineRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where secondaryOperationMachineRate is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "secondaryOperationMachineRate.greaterThanOrEqual=" + DEFAULT_SECONDARY_OPERATION_MACHINE_RATE,
            "secondaryOperationMachineRate.greaterThanOrEqual=" + UPDATED_SECONDARY_OPERATION_MACHINE_RATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySecondaryOperationMachineRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where secondaryOperationMachineRate is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "secondaryOperationMachineRate.lessThanOrEqual=" + DEFAULT_SECONDARY_OPERATION_MACHINE_RATE,
            "secondaryOperationMachineRate.lessThanOrEqual=" + SMALLER_SECONDARY_OPERATION_MACHINE_RATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySecondaryOperationMachineRateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where secondaryOperationMachineRate is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "secondaryOperationMachineRate.lessThan=" + UPDATED_SECONDARY_OPERATION_MACHINE_RATE,
            "secondaryOperationMachineRate.lessThan=" + DEFAULT_SECONDARY_OPERATION_MACHINE_RATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySecondaryOperationMachineRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where secondaryOperationMachineRate is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "secondaryOperationMachineRate.greaterThan=" + SMALLER_SECONDARY_OPERATION_MACHINE_RATE,
            "secondaryOperationMachineRate.greaterThan=" + DEFAULT_SECONDARY_OPERATION_MACHINE_RATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySecondaryOperationCycleTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where secondaryOperationCycleTime equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "secondaryOperationCycleTime.equals=" + DEFAULT_SECONDARY_OPERATION_CYCLE_TIME,
            "secondaryOperationCycleTime.equals=" + UPDATED_SECONDARY_OPERATION_CYCLE_TIME
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySecondaryOperationCycleTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where secondaryOperationCycleTime in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "secondaryOperationCycleTime.in=" + DEFAULT_SECONDARY_OPERATION_CYCLE_TIME + "," + UPDATED_SECONDARY_OPERATION_CYCLE_TIME,
            "secondaryOperationCycleTime.in=" + UPDATED_SECONDARY_OPERATION_CYCLE_TIME
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySecondaryOperationCycleTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where secondaryOperationCycleTime is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "secondaryOperationCycleTime.specified=true",
            "secondaryOperationCycleTime.specified=false"
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySecondaryOperationCycleTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where secondaryOperationCycleTime is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "secondaryOperationCycleTime.greaterThanOrEqual=" + DEFAULT_SECONDARY_OPERATION_CYCLE_TIME,
            "secondaryOperationCycleTime.greaterThanOrEqual=" + UPDATED_SECONDARY_OPERATION_CYCLE_TIME
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySecondaryOperationCycleTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where secondaryOperationCycleTime is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "secondaryOperationCycleTime.lessThanOrEqual=" + DEFAULT_SECONDARY_OPERATION_CYCLE_TIME,
            "secondaryOperationCycleTime.lessThanOrEqual=" + SMALLER_SECONDARY_OPERATION_CYCLE_TIME
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySecondaryOperationCycleTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where secondaryOperationCycleTime is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "secondaryOperationCycleTime.lessThan=" + UPDATED_SECONDARY_OPERATION_CYCLE_TIME,
            "secondaryOperationCycleTime.lessThan=" + DEFAULT_SECONDARY_OPERATION_CYCLE_TIME
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySecondaryOperationCycleTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where secondaryOperationCycleTime is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "secondaryOperationCycleTime.greaterThan=" + SMALLER_SECONDARY_OPERATION_CYCLE_TIME,
            "secondaryOperationCycleTime.greaterThan=" + DEFAULT_SECONDARY_OPERATION_CYCLE_TIME
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByExternalOperationRateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where externalOperationRate equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "externalOperationRate.equals=" + DEFAULT_EXTERNAL_OPERATION_RATE,
            "externalOperationRate.equals=" + UPDATED_EXTERNAL_OPERATION_RATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByExternalOperationRateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where externalOperationRate in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "externalOperationRate.in=" + DEFAULT_EXTERNAL_OPERATION_RATE + "," + UPDATED_EXTERNAL_OPERATION_RATE,
            "externalOperationRate.in=" + UPDATED_EXTERNAL_OPERATION_RATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByExternalOperationRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where externalOperationRate is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("externalOperationRate.specified=true", "externalOperationRate.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByExternalOperationRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where externalOperationRate is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "externalOperationRate.greaterThanOrEqual=" + DEFAULT_EXTERNAL_OPERATION_RATE,
            "externalOperationRate.greaterThanOrEqual=" + UPDATED_EXTERNAL_OPERATION_RATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByExternalOperationRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where externalOperationRate is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "externalOperationRate.lessThanOrEqual=" + DEFAULT_EXTERNAL_OPERATION_RATE,
            "externalOperationRate.lessThanOrEqual=" + SMALLER_EXTERNAL_OPERATION_RATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByExternalOperationRateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where externalOperationRate is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "externalOperationRate.lessThan=" + UPDATED_EXTERNAL_OPERATION_RATE,
            "externalOperationRate.lessThan=" + DEFAULT_EXTERNAL_OPERATION_RATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByExternalOperationRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where externalOperationRate is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "externalOperationRate.greaterThan=" + SMALLER_EXTERNAL_OPERATION_RATE,
            "externalOperationRate.greaterThan=" + DEFAULT_EXTERNAL_OPERATION_RATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPreventativeMaintenanceFrequencyIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where preventativeMaintenanceFrequency equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "preventativeMaintenanceFrequency.equals=" + DEFAULT_PREVENTATIVE_MAINTENANCE_FREQUENCY,
            "preventativeMaintenanceFrequency.equals=" + UPDATED_PREVENTATIVE_MAINTENANCE_FREQUENCY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPreventativeMaintenanceFrequencyIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where preventativeMaintenanceFrequency in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "preventativeMaintenanceFrequency.in=" +
            DEFAULT_PREVENTATIVE_MAINTENANCE_FREQUENCY +
            "," +
            UPDATED_PREVENTATIVE_MAINTENANCE_FREQUENCY,
            "preventativeMaintenanceFrequency.in=" + UPDATED_PREVENTATIVE_MAINTENANCE_FREQUENCY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPreventativeMaintenanceFrequencyIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where preventativeMaintenanceFrequency is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "preventativeMaintenanceFrequency.specified=true",
            "preventativeMaintenanceFrequency.specified=false"
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPreventativeMaintenanceFrequencyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where preventativeMaintenanceFrequency is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "preventativeMaintenanceFrequency.greaterThanOrEqual=" + DEFAULT_PREVENTATIVE_MAINTENANCE_FREQUENCY,
            "preventativeMaintenanceFrequency.greaterThanOrEqual=" + UPDATED_PREVENTATIVE_MAINTENANCE_FREQUENCY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPreventativeMaintenanceFrequencyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where preventativeMaintenanceFrequency is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "preventativeMaintenanceFrequency.lessThanOrEqual=" + DEFAULT_PREVENTATIVE_MAINTENANCE_FREQUENCY,
            "preventativeMaintenanceFrequency.lessThanOrEqual=" + SMALLER_PREVENTATIVE_MAINTENANCE_FREQUENCY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPreventativeMaintenanceFrequencyIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where preventativeMaintenanceFrequency is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "preventativeMaintenanceFrequency.lessThan=" + UPDATED_PREVENTATIVE_MAINTENANCE_FREQUENCY,
            "preventativeMaintenanceFrequency.lessThan=" + DEFAULT_PREVENTATIVE_MAINTENANCE_FREQUENCY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPreventativeMaintenanceFrequencyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where preventativeMaintenanceFrequency is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "preventativeMaintenanceFrequency.greaterThan=" + SMALLER_PREVENTATIVE_MAINTENANCE_FREQUENCY,
            "preventativeMaintenanceFrequency.greaterThan=" + DEFAULT_PREVENTATIVE_MAINTENANCE_FREQUENCY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPreventativeMaintenanceCostIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where preventativeMaintenanceCost equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "preventativeMaintenanceCost.equals=" + DEFAULT_PREVENTATIVE_MAINTENANCE_COST,
            "preventativeMaintenanceCost.equals=" + UPDATED_PREVENTATIVE_MAINTENANCE_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPreventativeMaintenanceCostIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where preventativeMaintenanceCost in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "preventativeMaintenanceCost.in=" + DEFAULT_PREVENTATIVE_MAINTENANCE_COST + "," + UPDATED_PREVENTATIVE_MAINTENANCE_COST,
            "preventativeMaintenanceCost.in=" + UPDATED_PREVENTATIVE_MAINTENANCE_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPreventativeMaintenanceCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where preventativeMaintenanceCost is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "preventativeMaintenanceCost.specified=true",
            "preventativeMaintenanceCost.specified=false"
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPreventativeMaintenanceCostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where preventativeMaintenanceCost is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "preventativeMaintenanceCost.greaterThanOrEqual=" + DEFAULT_PREVENTATIVE_MAINTENANCE_COST,
            "preventativeMaintenanceCost.greaterThanOrEqual=" + UPDATED_PREVENTATIVE_MAINTENANCE_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPreventativeMaintenanceCostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where preventativeMaintenanceCost is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "preventativeMaintenanceCost.lessThanOrEqual=" + DEFAULT_PREVENTATIVE_MAINTENANCE_COST,
            "preventativeMaintenanceCost.lessThanOrEqual=" + SMALLER_PREVENTATIVE_MAINTENANCE_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPreventativeMaintenanceCostIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where preventativeMaintenanceCost is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "preventativeMaintenanceCost.lessThan=" + UPDATED_PREVENTATIVE_MAINTENANCE_COST,
            "preventativeMaintenanceCost.lessThan=" + DEFAULT_PREVENTATIVE_MAINTENANCE_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPreventativeMaintenanceCostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where preventativeMaintenanceCost is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "preventativeMaintenanceCost.greaterThan=" + SMALLER_PREVENTATIVE_MAINTENANCE_COST,
            "preventativeMaintenanceCost.greaterThan=" + DEFAULT_PREVENTATIVE_MAINTENANCE_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTargetProfitIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where targetProfit equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "targetProfit.equals=" + DEFAULT_TARGET_PROFIT,
            "targetProfit.equals=" + UPDATED_TARGET_PROFIT
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTargetProfitIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where targetProfit in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "targetProfit.in=" + DEFAULT_TARGET_PROFIT + "," + UPDATED_TARGET_PROFIT,
            "targetProfit.in=" + UPDATED_TARGET_PROFIT
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTargetProfitIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where targetProfit is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("targetProfit.specified=true", "targetProfit.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTargetProfitIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where targetProfit is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "targetProfit.greaterThanOrEqual=" + DEFAULT_TARGET_PROFIT,
            "targetProfit.greaterThanOrEqual=" + UPDATED_TARGET_PROFIT
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTargetProfitIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where targetProfit is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "targetProfit.lessThanOrEqual=" + DEFAULT_TARGET_PROFIT,
            "targetProfit.lessThanOrEqual=" + SMALLER_TARGET_PROFIT
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTargetProfitIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where targetProfit is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "targetProfit.lessThan=" + UPDATED_TARGET_PROFIT,
            "targetProfit.lessThan=" + DEFAULT_TARGET_PROFIT
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTargetProfitIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where targetProfit is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "targetProfit.greaterThan=" + SMALLER_TARGET_PROFIT,
            "targetProfit.greaterThan=" + DEFAULT_TARGET_PROFIT
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTargetMaterialMarkupIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where targetMaterialMarkup equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "targetMaterialMarkup.equals=" + DEFAULT_TARGET_MATERIAL_MARKUP,
            "targetMaterialMarkup.equals=" + UPDATED_TARGET_MATERIAL_MARKUP
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTargetMaterialMarkupIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where targetMaterialMarkup in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "targetMaterialMarkup.in=" + DEFAULT_TARGET_MATERIAL_MARKUP + "," + UPDATED_TARGET_MATERIAL_MARKUP,
            "targetMaterialMarkup.in=" + UPDATED_TARGET_MATERIAL_MARKUP
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTargetMaterialMarkupIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where targetMaterialMarkup is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("targetMaterialMarkup.specified=true", "targetMaterialMarkup.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTargetMaterialMarkupIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where targetMaterialMarkup is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "targetMaterialMarkup.greaterThanOrEqual=" + DEFAULT_TARGET_MATERIAL_MARKUP,
            "targetMaterialMarkup.greaterThanOrEqual=" + UPDATED_TARGET_MATERIAL_MARKUP
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTargetMaterialMarkupIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where targetMaterialMarkup is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "targetMaterialMarkup.lessThanOrEqual=" + DEFAULT_TARGET_MATERIAL_MARKUP,
            "targetMaterialMarkup.lessThanOrEqual=" + SMALLER_TARGET_MATERIAL_MARKUP
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTargetMaterialMarkupIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where targetMaterialMarkup is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "targetMaterialMarkup.lessThan=" + UPDATED_TARGET_MATERIAL_MARKUP,
            "targetMaterialMarkup.lessThan=" + DEFAULT_TARGET_MATERIAL_MARKUP
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTargetMaterialMarkupIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where targetMaterialMarkup is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "targetMaterialMarkup.greaterThan=" + SMALLER_TARGET_MATERIAL_MARKUP,
            "targetMaterialMarkup.greaterThan=" + DEFAULT_TARGET_MATERIAL_MARKUP
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByActualMaterialCostIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where actualMaterialCost equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "actualMaterialCost.equals=" + DEFAULT_ACTUAL_MATERIAL_COST,
            "actualMaterialCost.equals=" + UPDATED_ACTUAL_MATERIAL_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByActualMaterialCostIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where actualMaterialCost in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "actualMaterialCost.in=" + DEFAULT_ACTUAL_MATERIAL_COST + "," + UPDATED_ACTUAL_MATERIAL_COST,
            "actualMaterialCost.in=" + UPDATED_ACTUAL_MATERIAL_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByActualMaterialCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where actualMaterialCost is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("actualMaterialCost.specified=true", "actualMaterialCost.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByActualMaterialCostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where actualMaterialCost is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "actualMaterialCost.greaterThanOrEqual=" + DEFAULT_ACTUAL_MATERIAL_COST,
            "actualMaterialCost.greaterThanOrEqual=" + UPDATED_ACTUAL_MATERIAL_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByActualMaterialCostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where actualMaterialCost is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "actualMaterialCost.lessThanOrEqual=" + DEFAULT_ACTUAL_MATERIAL_COST,
            "actualMaterialCost.lessThanOrEqual=" + SMALLER_ACTUAL_MATERIAL_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByActualMaterialCostIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where actualMaterialCost is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "actualMaterialCost.lessThan=" + UPDATED_ACTUAL_MATERIAL_COST,
            "actualMaterialCost.lessThan=" + DEFAULT_ACTUAL_MATERIAL_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByActualMaterialCostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where actualMaterialCost is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "actualMaterialCost.greaterThan=" + SMALLER_ACTUAL_MATERIAL_COST,
            "actualMaterialCost.greaterThan=" + DEFAULT_ACTUAL_MATERIAL_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPartPerHoursIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where partPerHours equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "partPerHours.equals=" + DEFAULT_PART_PER_HOURS,
            "partPerHours.equals=" + UPDATED_PART_PER_HOURS
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPartPerHoursIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where partPerHours in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "partPerHours.in=" + DEFAULT_PART_PER_HOURS + "," + UPDATED_PART_PER_HOURS,
            "partPerHours.in=" + UPDATED_PART_PER_HOURS
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPartPerHoursIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where partPerHours is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("partPerHours.specified=true", "partPerHours.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPartPerHoursIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where partPerHours is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "partPerHours.greaterThanOrEqual=" + DEFAULT_PART_PER_HOURS,
            "partPerHours.greaterThanOrEqual=" + UPDATED_PART_PER_HOURS
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPartPerHoursIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where partPerHours is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "partPerHours.lessThanOrEqual=" + DEFAULT_PART_PER_HOURS,
            "partPerHours.lessThanOrEqual=" + SMALLER_PART_PER_HOURS
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPartPerHoursIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where partPerHours is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "partPerHours.lessThan=" + UPDATED_PART_PER_HOURS,
            "partPerHours.lessThan=" + DEFAULT_PART_PER_HOURS
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPartPerHoursIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where partPerHours is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "partPerHours.greaterThan=" + SMALLER_PART_PER_HOURS,
            "partPerHours.greaterThan=" + DEFAULT_PART_PER_HOURS
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByEstLotSizeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where estLotSize equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "estLotSize.equals=" + DEFAULT_EST_LOT_SIZE,
            "estLotSize.equals=" + UPDATED_EST_LOT_SIZE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByEstLotSizeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where estLotSize in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "estLotSize.in=" + DEFAULT_EST_LOT_SIZE + "," + UPDATED_EST_LOT_SIZE,
            "estLotSize.in=" + UPDATED_EST_LOT_SIZE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByEstLotSizeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where estLotSize is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("estLotSize.specified=true", "estLotSize.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByEstLotSizeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where estLotSize is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "estLotSize.greaterThanOrEqual=" + DEFAULT_EST_LOT_SIZE,
            "estLotSize.greaterThanOrEqual=" + UPDATED_EST_LOT_SIZE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByEstLotSizeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where estLotSize is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "estLotSize.lessThanOrEqual=" + DEFAULT_EST_LOT_SIZE,
            "estLotSize.lessThanOrEqual=" + SMALLER_EST_LOT_SIZE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByEstLotSizeIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where estLotSize is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "estLotSize.lessThan=" + UPDATED_EST_LOT_SIZE,
            "estLotSize.lessThan=" + DEFAULT_EST_LOT_SIZE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByEstLotSizeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where estLotSize is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "estLotSize.greaterThan=" + SMALLER_EST_LOT_SIZE,
            "estLotSize.greaterThan=" + DEFAULT_EST_LOT_SIZE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySetupHoursIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where setupHours equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "setupHours.equals=" + DEFAULT_SETUP_HOURS,
            "setupHours.equals=" + UPDATED_SETUP_HOURS
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySetupHoursIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where setupHours in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "setupHours.in=" + DEFAULT_SETUP_HOURS + "," + UPDATED_SETUP_HOURS,
            "setupHours.in=" + UPDATED_SETUP_HOURS
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySetupHoursIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where setupHours is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("setupHours.specified=true", "setupHours.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySetupHoursIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where setupHours is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "setupHours.greaterThanOrEqual=" + DEFAULT_SETUP_HOURS,
            "setupHours.greaterThanOrEqual=" + UPDATED_SETUP_HOURS
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySetupHoursIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where setupHours is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "setupHours.lessThanOrEqual=" + DEFAULT_SETUP_HOURS,
            "setupHours.lessThanOrEqual=" + SMALLER_SETUP_HOURS
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySetupHoursIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where setupHours is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "setupHours.lessThan=" + UPDATED_SETUP_HOURS,
            "setupHours.lessThan=" + DEFAULT_SETUP_HOURS
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySetupHoursIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where setupHours is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "setupHours.greaterThan=" + SMALLER_SETUP_HOURS,
            "setupHours.greaterThan=" + DEFAULT_SETUP_HOURS
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByExternalOperationCostPerIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where externalOperationCostPer equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "externalOperationCostPer.equals=" + DEFAULT_EXTERNAL_OPERATION_COST_PER,
            "externalOperationCostPer.equals=" + UPDATED_EXTERNAL_OPERATION_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByExternalOperationCostPerIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where externalOperationCostPer in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "externalOperationCostPer.in=" + DEFAULT_EXTERNAL_OPERATION_COST_PER + "," + UPDATED_EXTERNAL_OPERATION_COST_PER,
            "externalOperationCostPer.in=" + UPDATED_EXTERNAL_OPERATION_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByExternalOperationCostPerIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where externalOperationCostPer is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "externalOperationCostPer.specified=true",
            "externalOperationCostPer.specified=false"
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByExternalOperationCostPerIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where externalOperationCostPer is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "externalOperationCostPer.greaterThanOrEqual=" + DEFAULT_EXTERNAL_OPERATION_COST_PER,
            "externalOperationCostPer.greaterThanOrEqual=" + UPDATED_EXTERNAL_OPERATION_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByExternalOperationCostPerIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where externalOperationCostPer is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "externalOperationCostPer.lessThanOrEqual=" + DEFAULT_EXTERNAL_OPERATION_COST_PER,
            "externalOperationCostPer.lessThanOrEqual=" + SMALLER_EXTERNAL_OPERATION_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByExternalOperationCostPerIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where externalOperationCostPer is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "externalOperationCostPer.lessThan=" + UPDATED_EXTERNAL_OPERATION_COST_PER,
            "externalOperationCostPer.lessThan=" + DEFAULT_EXTERNAL_OPERATION_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByExternalOperationCostPerIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where externalOperationCostPer is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "externalOperationCostPer.greaterThan=" + SMALLER_EXTERNAL_OPERATION_COST_PER,
            "externalOperationCostPer.greaterThan=" + DEFAULT_EXTERNAL_OPERATION_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByExternalMachineCostPerIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where externalMachineCostPer equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "externalMachineCostPer.equals=" + DEFAULT_EXTERNAL_MACHINE_COST_PER,
            "externalMachineCostPer.equals=" + UPDATED_EXTERNAL_MACHINE_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByExternalMachineCostPerIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where externalMachineCostPer in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "externalMachineCostPer.in=" + DEFAULT_EXTERNAL_MACHINE_COST_PER + "," + UPDATED_EXTERNAL_MACHINE_COST_PER,
            "externalMachineCostPer.in=" + UPDATED_EXTERNAL_MACHINE_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByExternalMachineCostPerIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where externalMachineCostPer is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "externalMachineCostPer.specified=true",
            "externalMachineCostPer.specified=false"
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByExternalMachineCostPerIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where externalMachineCostPer is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "externalMachineCostPer.greaterThanOrEqual=" + DEFAULT_EXTERNAL_MACHINE_COST_PER,
            "externalMachineCostPer.greaterThanOrEqual=" + UPDATED_EXTERNAL_MACHINE_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByExternalMachineCostPerIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where externalMachineCostPer is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "externalMachineCostPer.lessThanOrEqual=" + DEFAULT_EXTERNAL_MACHINE_COST_PER,
            "externalMachineCostPer.lessThanOrEqual=" + SMALLER_EXTERNAL_MACHINE_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByExternalMachineCostPerIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where externalMachineCostPer is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "externalMachineCostPer.lessThan=" + UPDATED_EXTERNAL_MACHINE_COST_PER,
            "externalMachineCostPer.lessThan=" + DEFAULT_EXTERNAL_MACHINE_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByExternalMachineCostPerIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where externalMachineCostPer is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "externalMachineCostPer.greaterThan=" + SMALLER_EXTERNAL_MACHINE_COST_PER,
            "externalMachineCostPer.greaterThan=" + DEFAULT_EXTERNAL_MACHINE_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByExtendedLaborCostPerIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where extendedLaborCostPer equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "extendedLaborCostPer.equals=" + DEFAULT_EXTENDED_LABOR_COST_PER,
            "extendedLaborCostPer.equals=" + UPDATED_EXTENDED_LABOR_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByExtendedLaborCostPerIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where extendedLaborCostPer in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "extendedLaborCostPer.in=" + DEFAULT_EXTENDED_LABOR_COST_PER + "," + UPDATED_EXTENDED_LABOR_COST_PER,
            "extendedLaborCostPer.in=" + UPDATED_EXTENDED_LABOR_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByExtendedLaborCostPerIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where extendedLaborCostPer is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("extendedLaborCostPer.specified=true", "extendedLaborCostPer.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByExtendedLaborCostPerIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where extendedLaborCostPer is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "extendedLaborCostPer.greaterThanOrEqual=" + DEFAULT_EXTENDED_LABOR_COST_PER,
            "extendedLaborCostPer.greaterThanOrEqual=" + UPDATED_EXTENDED_LABOR_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByExtendedLaborCostPerIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where extendedLaborCostPer is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "extendedLaborCostPer.lessThanOrEqual=" + DEFAULT_EXTENDED_LABOR_COST_PER,
            "extendedLaborCostPer.lessThanOrEqual=" + SMALLER_EXTENDED_LABOR_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByExtendedLaborCostPerIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where extendedLaborCostPer is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "extendedLaborCostPer.lessThan=" + UPDATED_EXTENDED_LABOR_COST_PER,
            "extendedLaborCostPer.lessThan=" + DEFAULT_EXTENDED_LABOR_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByExtendedLaborCostPerIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where extendedLaborCostPer is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "extendedLaborCostPer.greaterThan=" + SMALLER_EXTENDED_LABOR_COST_PER,
            "extendedLaborCostPer.greaterThan=" + DEFAULT_EXTENDED_LABOR_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByExtendedMaterialCostPerIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where extendedMaterialCostPer equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "extendedMaterialCostPer.equals=" + DEFAULT_EXTENDED_MATERIAL_COST_PER,
            "extendedMaterialCostPer.equals=" + UPDATED_EXTENDED_MATERIAL_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByExtendedMaterialCostPerIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where extendedMaterialCostPer in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "extendedMaterialCostPer.in=" + DEFAULT_EXTENDED_MATERIAL_COST_PER + "," + UPDATED_EXTENDED_MATERIAL_COST_PER,
            "extendedMaterialCostPer.in=" + UPDATED_EXTENDED_MATERIAL_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByExtendedMaterialCostPerIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where extendedMaterialCostPer is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "extendedMaterialCostPer.specified=true",
            "extendedMaterialCostPer.specified=false"
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByExtendedMaterialCostPerIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where extendedMaterialCostPer is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "extendedMaterialCostPer.greaterThanOrEqual=" + DEFAULT_EXTENDED_MATERIAL_COST_PER,
            "extendedMaterialCostPer.greaterThanOrEqual=" + UPDATED_EXTENDED_MATERIAL_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByExtendedMaterialCostPerIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where extendedMaterialCostPer is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "extendedMaterialCostPer.lessThanOrEqual=" + DEFAULT_EXTENDED_MATERIAL_COST_PER,
            "extendedMaterialCostPer.lessThanOrEqual=" + SMALLER_EXTENDED_MATERIAL_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByExtendedMaterialCostPerIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where extendedMaterialCostPer is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "extendedMaterialCostPer.lessThan=" + UPDATED_EXTENDED_MATERIAL_COST_PER,
            "extendedMaterialCostPer.lessThan=" + DEFAULT_EXTENDED_MATERIAL_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByExtendedMaterialCostPerIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where extendedMaterialCostPer is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "extendedMaterialCostPer.greaterThan=" + SMALLER_EXTENDED_MATERIAL_COST_PER,
            "extendedMaterialCostPer.greaterThan=" + DEFAULT_EXTENDED_MATERIAL_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPackLogisticCostPerIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where packLogisticCostPer equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "packLogisticCostPer.equals=" + DEFAULT_PACK_LOGISTIC_COST_PER,
            "packLogisticCostPer.equals=" + UPDATED_PACK_LOGISTIC_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPackLogisticCostPerIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where packLogisticCostPer in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "packLogisticCostPer.in=" + DEFAULT_PACK_LOGISTIC_COST_PER + "," + UPDATED_PACK_LOGISTIC_COST_PER,
            "packLogisticCostPer.in=" + UPDATED_PACK_LOGISTIC_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPackLogisticCostPerIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where packLogisticCostPer is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("packLogisticCostPer.specified=true", "packLogisticCostPer.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPackLogisticCostPerIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where packLogisticCostPer is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "packLogisticCostPer.greaterThanOrEqual=" + DEFAULT_PACK_LOGISTIC_COST_PER,
            "packLogisticCostPer.greaterThanOrEqual=" + UPDATED_PACK_LOGISTIC_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPackLogisticCostPerIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where packLogisticCostPer is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "packLogisticCostPer.lessThanOrEqual=" + DEFAULT_PACK_LOGISTIC_COST_PER,
            "packLogisticCostPer.lessThanOrEqual=" + SMALLER_PACK_LOGISTIC_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPackLogisticCostPerIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where packLogisticCostPer is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "packLogisticCostPer.lessThan=" + UPDATED_PACK_LOGISTIC_COST_PER,
            "packLogisticCostPer.lessThan=" + DEFAULT_PACK_LOGISTIC_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPackLogisticCostPerIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where packLogisticCostPer is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "packLogisticCostPer.greaterThan=" + SMALLER_PACK_LOGISTIC_COST_PER,
            "packLogisticCostPer.greaterThan=" + DEFAULT_PACK_LOGISTIC_COST_PER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalProductionCostIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalProductionCost equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalProductionCost.equals=" + DEFAULT_TOTAL_PRODUCTION_COST,
            "totalProductionCost.equals=" + UPDATED_TOTAL_PRODUCTION_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalProductionCostIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalProductionCost in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalProductionCost.in=" + DEFAULT_TOTAL_PRODUCTION_COST + "," + UPDATED_TOTAL_PRODUCTION_COST,
            "totalProductionCost.in=" + UPDATED_TOTAL_PRODUCTION_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalProductionCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalProductionCost is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("totalProductionCost.specified=true", "totalProductionCost.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalProductionCostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalProductionCost is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalProductionCost.greaterThanOrEqual=" + DEFAULT_TOTAL_PRODUCTION_COST,
            "totalProductionCost.greaterThanOrEqual=" + UPDATED_TOTAL_PRODUCTION_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalProductionCostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalProductionCost is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalProductionCost.lessThanOrEqual=" + DEFAULT_TOTAL_PRODUCTION_COST,
            "totalProductionCost.lessThanOrEqual=" + SMALLER_TOTAL_PRODUCTION_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalProductionCostIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalProductionCost is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalProductionCost.lessThan=" + UPDATED_TOTAL_PRODUCTION_COST,
            "totalProductionCost.lessThan=" + DEFAULT_TOTAL_PRODUCTION_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalProductionCostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalProductionCost is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalProductionCost.greaterThan=" + SMALLER_TOTAL_PRODUCTION_COST,
            "totalProductionCost.greaterThan=" + DEFAULT_TOTAL_PRODUCTION_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalMaterialCostIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalMaterialCost equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalMaterialCost.equals=" + DEFAULT_TOTAL_MATERIAL_COST,
            "totalMaterialCost.equals=" + UPDATED_TOTAL_MATERIAL_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalMaterialCostIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalMaterialCost in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalMaterialCost.in=" + DEFAULT_TOTAL_MATERIAL_COST + "," + UPDATED_TOTAL_MATERIAL_COST,
            "totalMaterialCost.in=" + UPDATED_TOTAL_MATERIAL_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalMaterialCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalMaterialCost is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("totalMaterialCost.specified=true", "totalMaterialCost.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalMaterialCostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalMaterialCost is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalMaterialCost.greaterThanOrEqual=" + DEFAULT_TOTAL_MATERIAL_COST,
            "totalMaterialCost.greaterThanOrEqual=" + UPDATED_TOTAL_MATERIAL_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalMaterialCostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalMaterialCost is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalMaterialCost.lessThanOrEqual=" + DEFAULT_TOTAL_MATERIAL_COST,
            "totalMaterialCost.lessThanOrEqual=" + SMALLER_TOTAL_MATERIAL_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalMaterialCostIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalMaterialCost is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalMaterialCost.lessThan=" + UPDATED_TOTAL_MATERIAL_COST,
            "totalMaterialCost.lessThan=" + DEFAULT_TOTAL_MATERIAL_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalMaterialCostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalMaterialCost is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalMaterialCost.greaterThan=" + SMALLER_TOTAL_MATERIAL_COST,
            "totalMaterialCost.greaterThan=" + DEFAULT_TOTAL_MATERIAL_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalCostSgaProfitIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalCostSgaProfit equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalCostSgaProfit.equals=" + DEFAULT_TOTAL_COST_SGA_PROFIT,
            "totalCostSgaProfit.equals=" + UPDATED_TOTAL_COST_SGA_PROFIT
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalCostSgaProfitIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalCostSgaProfit in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalCostSgaProfit.in=" + DEFAULT_TOTAL_COST_SGA_PROFIT + "," + UPDATED_TOTAL_COST_SGA_PROFIT,
            "totalCostSgaProfit.in=" + UPDATED_TOTAL_COST_SGA_PROFIT
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalCostSgaProfitIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalCostSgaProfit is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("totalCostSgaProfit.specified=true", "totalCostSgaProfit.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalCostSgaProfitIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalCostSgaProfit is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalCostSgaProfit.greaterThanOrEqual=" + DEFAULT_TOTAL_COST_SGA_PROFIT,
            "totalCostSgaProfit.greaterThanOrEqual=" + UPDATED_TOTAL_COST_SGA_PROFIT
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalCostSgaProfitIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalCostSgaProfit is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalCostSgaProfit.lessThanOrEqual=" + DEFAULT_TOTAL_COST_SGA_PROFIT,
            "totalCostSgaProfit.lessThanOrEqual=" + SMALLER_TOTAL_COST_SGA_PROFIT
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalCostSgaProfitIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalCostSgaProfit is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalCostSgaProfit.lessThan=" + UPDATED_TOTAL_COST_SGA_PROFIT,
            "totalCostSgaProfit.lessThan=" + DEFAULT_TOTAL_COST_SGA_PROFIT
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalCostSgaProfitIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalCostSgaProfit is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalCostSgaProfit.greaterThan=" + SMALLER_TOTAL_COST_SGA_PROFIT,
            "totalCostSgaProfit.greaterThan=" + DEFAULT_TOTAL_COST_SGA_PROFIT
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySgaRateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where sgaRate equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering("sgaRate.equals=" + DEFAULT_SGA_RATE, "sgaRate.equals=" + UPDATED_SGA_RATE);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySgaRateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where sgaRate in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "sgaRate.in=" + DEFAULT_SGA_RATE + "," + UPDATED_SGA_RATE,
            "sgaRate.in=" + UPDATED_SGA_RATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySgaRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where sgaRate is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("sgaRate.specified=true", "sgaRate.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySgaRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where sgaRate is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "sgaRate.greaterThanOrEqual=" + DEFAULT_SGA_RATE,
            "sgaRate.greaterThanOrEqual=" + UPDATED_SGA_RATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySgaRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where sgaRate is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "sgaRate.lessThanOrEqual=" + DEFAULT_SGA_RATE,
            "sgaRate.lessThanOrEqual=" + SMALLER_SGA_RATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySgaRateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where sgaRate is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering("sgaRate.lessThan=" + UPDATED_SGA_RATE, "sgaRate.lessThan=" + DEFAULT_SGA_RATE);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsBySgaRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where sgaRate is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "sgaRate.greaterThan=" + SMALLER_SGA_RATE,
            "sgaRate.greaterThan=" + DEFAULT_SGA_RATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByProfitIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where profit equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering("profit.equals=" + DEFAULT_PROFIT, "profit.equals=" + UPDATED_PROFIT);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByProfitIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where profit in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "profit.in=" + DEFAULT_PROFIT + "," + UPDATED_PROFIT,
            "profit.in=" + UPDATED_PROFIT
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByProfitIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where profit is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("profit.specified=true", "profit.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByProfitIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where profit is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "profit.greaterThanOrEqual=" + DEFAULT_PROFIT,
            "profit.greaterThanOrEqual=" + UPDATED_PROFIT
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByProfitIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where profit is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "profit.lessThanOrEqual=" + DEFAULT_PROFIT,
            "profit.lessThanOrEqual=" + SMALLER_PROFIT
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByProfitIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where profit is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering("profit.lessThan=" + UPDATED_PROFIT, "profit.lessThan=" + DEFAULT_PROFIT);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByProfitIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where profit is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering("profit.greaterThan=" + SMALLER_PROFIT, "profit.greaterThan=" + DEFAULT_PROFIT);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPartPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where partPrice equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "partPrice.equals=" + DEFAULT_PART_PRICE,
            "partPrice.equals=" + UPDATED_PART_PRICE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPartPriceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where partPrice in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "partPrice.in=" + DEFAULT_PART_PRICE + "," + UPDATED_PART_PRICE,
            "partPrice.in=" + UPDATED_PART_PRICE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPartPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where partPrice is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("partPrice.specified=true", "partPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPartPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where partPrice is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "partPrice.greaterThanOrEqual=" + DEFAULT_PART_PRICE,
            "partPrice.greaterThanOrEqual=" + UPDATED_PART_PRICE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPartPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where partPrice is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "partPrice.lessThanOrEqual=" + DEFAULT_PART_PRICE,
            "partPrice.lessThanOrEqual=" + SMALLER_PART_PRICE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPartPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where partPrice is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "partPrice.lessThan=" + UPDATED_PART_PRICE,
            "partPrice.lessThan=" + DEFAULT_PART_PRICE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByPartPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where partPrice is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "partPrice.greaterThan=" + SMALLER_PART_PRICE,
            "partPrice.greaterThan=" + DEFAULT_PART_PRICE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalCostIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalCost equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalCost.equals=" + DEFAULT_TOTAL_COST,
            "totalCost.equals=" + UPDATED_TOTAL_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalCostIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalCost in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalCost.in=" + DEFAULT_TOTAL_COST + "," + UPDATED_TOTAL_COST,
            "totalCost.in=" + UPDATED_TOTAL_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalCost is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("totalCost.specified=true", "totalCost.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalCostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalCost is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalCost.greaterThanOrEqual=" + DEFAULT_TOTAL_COST,
            "totalCost.greaterThanOrEqual=" + UPDATED_TOTAL_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalCostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalCost is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalCost.lessThanOrEqual=" + DEFAULT_TOTAL_COST,
            "totalCost.lessThanOrEqual=" + SMALLER_TOTAL_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalCostIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalCost is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalCost.lessThan=" + UPDATED_TOTAL_COST,
            "totalCost.lessThan=" + DEFAULT_TOTAL_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalCostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalCost is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalCost.greaterThan=" + SMALLER_TOTAL_COST,
            "totalCost.greaterThan=" + DEFAULT_TOTAL_COST
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalSalesIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalSales equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalSales.equals=" + DEFAULT_TOTAL_SALES,
            "totalSales.equals=" + UPDATED_TOTAL_SALES
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalSalesIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalSales in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalSales.in=" + DEFAULT_TOTAL_SALES + "," + UPDATED_TOTAL_SALES,
            "totalSales.in=" + UPDATED_TOTAL_SALES
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalSalesIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalSales is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("totalSales.specified=true", "totalSales.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalSalesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalSales is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalSales.greaterThanOrEqual=" + DEFAULT_TOTAL_SALES,
            "totalSales.greaterThanOrEqual=" + UPDATED_TOTAL_SALES
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalSalesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalSales is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalSales.lessThanOrEqual=" + DEFAULT_TOTAL_SALES,
            "totalSales.lessThanOrEqual=" + SMALLER_TOTAL_SALES
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalSalesIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalSales is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalSales.lessThan=" + UPDATED_TOTAL_SALES,
            "totalSales.lessThan=" + DEFAULT_TOTAL_SALES
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalSalesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalSales is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalSales.greaterThan=" + SMALLER_TOTAL_SALES,
            "totalSales.greaterThan=" + DEFAULT_TOTAL_SALES
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalProfitIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalProfit equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalProfit.equals=" + DEFAULT_TOTAL_PROFIT,
            "totalProfit.equals=" + UPDATED_TOTAL_PROFIT
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalProfitIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalProfit in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalProfit.in=" + DEFAULT_TOTAL_PROFIT + "," + UPDATED_TOTAL_PROFIT,
            "totalProfit.in=" + UPDATED_TOTAL_PROFIT
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalProfitIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalProfit is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("totalProfit.specified=true", "totalProfit.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalProfitIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalProfit is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalProfit.greaterThanOrEqual=" + DEFAULT_TOTAL_PROFIT,
            "totalProfit.greaterThanOrEqual=" + UPDATED_TOTAL_PROFIT
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalProfitIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalProfit is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalProfit.lessThanOrEqual=" + DEFAULT_TOTAL_PROFIT,
            "totalProfit.lessThanOrEqual=" + SMALLER_TOTAL_PROFIT
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalProfitIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalProfit is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalProfit.lessThan=" + UPDATED_TOTAL_PROFIT,
            "totalProfit.lessThan=" + DEFAULT_TOTAL_PROFIT
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalProfitIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalProfit is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalProfit.greaterThan=" + SMALLER_TOTAL_PROFIT,
            "totalProfit.greaterThan=" + DEFAULT_TOTAL_PROFIT
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByCostMaterialIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where costMaterial equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "costMaterial.equals=" + DEFAULT_COST_MATERIAL,
            "costMaterial.equals=" + UPDATED_COST_MATERIAL
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByCostMaterialIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where costMaterial in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "costMaterial.in=" + DEFAULT_COST_MATERIAL + "," + UPDATED_COST_MATERIAL,
            "costMaterial.in=" + UPDATED_COST_MATERIAL
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByCostMaterialIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where costMaterial is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("costMaterial.specified=true", "costMaterial.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByCostMaterialIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where costMaterial is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "costMaterial.greaterThanOrEqual=" + DEFAULT_COST_MATERIAL,
            "costMaterial.greaterThanOrEqual=" + UPDATED_COST_MATERIAL
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByCostMaterialIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where costMaterial is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "costMaterial.lessThanOrEqual=" + DEFAULT_COST_MATERIAL,
            "costMaterial.lessThanOrEqual=" + SMALLER_COST_MATERIAL
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByCostMaterialIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where costMaterial is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "costMaterial.lessThan=" + UPDATED_COST_MATERIAL,
            "costMaterial.lessThan=" + DEFAULT_COST_MATERIAL
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByCostMaterialIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where costMaterial is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "costMaterial.greaterThan=" + SMALLER_COST_MATERIAL,
            "costMaterial.greaterThan=" + DEFAULT_COST_MATERIAL
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalContributionMarginIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalContributionMargin equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalContributionMargin.equals=" + DEFAULT_TOTAL_CONTRIBUTION_MARGIN,
            "totalContributionMargin.equals=" + UPDATED_TOTAL_CONTRIBUTION_MARGIN
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalContributionMarginIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalContributionMargin in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalContributionMargin.in=" + DEFAULT_TOTAL_CONTRIBUTION_MARGIN + "," + UPDATED_TOTAL_CONTRIBUTION_MARGIN,
            "totalContributionMargin.in=" + UPDATED_TOTAL_CONTRIBUTION_MARGIN
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalContributionMarginIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalContributionMargin is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalContributionMargin.specified=true",
            "totalContributionMargin.specified=false"
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalContributionMarginIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalContributionMargin is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalContributionMargin.greaterThanOrEqual=" + DEFAULT_TOTAL_CONTRIBUTION_MARGIN,
            "totalContributionMargin.greaterThanOrEqual=" + UPDATED_TOTAL_CONTRIBUTION_MARGIN
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalContributionMarginIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalContributionMargin is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalContributionMargin.lessThanOrEqual=" + DEFAULT_TOTAL_CONTRIBUTION_MARGIN,
            "totalContributionMargin.lessThanOrEqual=" + SMALLER_TOTAL_CONTRIBUTION_MARGIN
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalContributionMarginIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalContributionMargin is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalContributionMargin.lessThan=" + UPDATED_TOTAL_CONTRIBUTION_MARGIN,
            "totalContributionMargin.lessThan=" + DEFAULT_TOTAL_CONTRIBUTION_MARGIN
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByTotalContributionMarginIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where totalContributionMargin is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "totalContributionMargin.greaterThan=" + SMALLER_TOTAL_CONTRIBUTION_MARGIN,
            "totalContributionMargin.greaterThan=" + DEFAULT_TOTAL_CONTRIBUTION_MARGIN
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByContributionMarginIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where contributionMargin equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "contributionMargin.equals=" + DEFAULT_CONTRIBUTION_MARGIN,
            "contributionMargin.equals=" + UPDATED_CONTRIBUTION_MARGIN
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByContributionMarginIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where contributionMargin in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "contributionMargin.in=" + DEFAULT_CONTRIBUTION_MARGIN + "," + UPDATED_CONTRIBUTION_MARGIN,
            "contributionMargin.in=" + UPDATED_CONTRIBUTION_MARGIN
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByContributionMarginIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where contributionMargin is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("contributionMargin.specified=true", "contributionMargin.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByContributionMarginIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where contributionMargin is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "contributionMargin.greaterThanOrEqual=" + DEFAULT_CONTRIBUTION_MARGIN,
            "contributionMargin.greaterThanOrEqual=" + UPDATED_CONTRIBUTION_MARGIN
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByContributionMarginIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where contributionMargin is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "contributionMargin.lessThanOrEqual=" + DEFAULT_CONTRIBUTION_MARGIN,
            "contributionMargin.lessThanOrEqual=" + SMALLER_CONTRIBUTION_MARGIN
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByContributionMarginIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where contributionMargin is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "contributionMargin.lessThan=" + UPDATED_CONTRIBUTION_MARGIN,
            "contributionMargin.lessThan=" + DEFAULT_CONTRIBUTION_MARGIN
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByContributionMarginIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where contributionMargin is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "contributionMargin.greaterThan=" + SMALLER_CONTRIBUTION_MARGIN,
            "contributionMargin.greaterThan=" + DEFAULT_CONTRIBUTION_MARGIN
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMaterialContributionMarginIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where materialContributionMargin equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "materialContributionMargin.equals=" + DEFAULT_MATERIAL_CONTRIBUTION_MARGIN,
            "materialContributionMargin.equals=" + UPDATED_MATERIAL_CONTRIBUTION_MARGIN
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMaterialContributionMarginIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where materialContributionMargin in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "materialContributionMargin.in=" + DEFAULT_MATERIAL_CONTRIBUTION_MARGIN + "," + UPDATED_MATERIAL_CONTRIBUTION_MARGIN,
            "materialContributionMargin.in=" + UPDATED_MATERIAL_CONTRIBUTION_MARGIN
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMaterialContributionMarginIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where materialContributionMargin is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "materialContributionMargin.specified=true",
            "materialContributionMargin.specified=false"
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMaterialContributionMarginIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where materialContributionMargin is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "materialContributionMargin.greaterThanOrEqual=" + DEFAULT_MATERIAL_CONTRIBUTION_MARGIN,
            "materialContributionMargin.greaterThanOrEqual=" + UPDATED_MATERIAL_CONTRIBUTION_MARGIN
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMaterialContributionMarginIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where materialContributionMargin is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "materialContributionMargin.lessThanOrEqual=" + DEFAULT_MATERIAL_CONTRIBUTION_MARGIN,
            "materialContributionMargin.lessThanOrEqual=" + SMALLER_MATERIAL_CONTRIBUTION_MARGIN
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMaterialContributionMarginIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where materialContributionMargin is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "materialContributionMargin.lessThan=" + UPDATED_MATERIAL_CONTRIBUTION_MARGIN,
            "materialContributionMargin.lessThan=" + DEFAULT_MATERIAL_CONTRIBUTION_MARGIN
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByMaterialContributionMarginIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where materialContributionMargin is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "materialContributionMargin.greaterThan=" + SMALLER_MATERIAL_CONTRIBUTION_MARGIN,
            "materialContributionMargin.greaterThan=" + DEFAULT_MATERIAL_CONTRIBUTION_MARGIN
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where version equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering("version.equals=" + DEFAULT_VERSION, "version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where version in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION,
            "version.in=" + UPDATED_VERSION
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where version is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("version.specified=true", "version.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where version is greater than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "version.greaterThanOrEqual=" + DEFAULT_VERSION,
            "version.greaterThanOrEqual=" + UPDATED_VERSION
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where version is less than or equal to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "version.lessThanOrEqual=" + DEFAULT_VERSION,
            "version.lessThanOrEqual=" + SMALLER_VERSION
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where version is less than
        defaultNtQuoteCustomerInputOutputVersionFiltering("version.lessThan=" + UPDATED_VERSION, "version.lessThan=" + DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where version is greater than
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "version.greaterThan=" + SMALLER_VERSION,
            "version.greaterThan=" + DEFAULT_VERSION
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where comments equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering("comments.equals=" + DEFAULT_COMMENTS, "comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where comments in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS,
            "comments.in=" + UPDATED_COMMENTS
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where comments is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("comments.specified=true", "comments.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByCommentsContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where comments contains
        defaultNtQuoteCustomerInputOutputVersionFiltering("comments.contains=" + DEFAULT_COMMENTS, "comments.contains=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where comments does not contain
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "comments.doesNotContain=" + UPDATED_COMMENTS,
            "comments.doesNotContain=" + DEFAULT_COMMENTS
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where createdBy equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "createdBy.equals=" + DEFAULT_CREATED_BY,
            "createdBy.equals=" + UPDATED_CREATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where createdBy in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY,
            "createdBy.in=" + UPDATED_CREATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where createdBy is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("createdBy.specified=true", "createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where createdBy contains
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "createdBy.contains=" + DEFAULT_CREATED_BY,
            "createdBy.contains=" + UPDATED_CREATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where createdBy does not contain
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "createdBy.doesNotContain=" + UPDATED_CREATED_BY,
            "createdBy.doesNotContain=" + DEFAULT_CREATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where createdDate equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "createdDate.equals=" + DEFAULT_CREATED_DATE,
            "createdDate.equals=" + UPDATED_CREATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where createdDate in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE,
            "createdDate.in=" + UPDATED_CREATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where createdDate is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("createdDate.specified=true", "createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where updatedBy equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "updatedBy.equals=" + DEFAULT_UPDATED_BY,
            "updatedBy.equals=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where updatedBy in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY,
            "updatedBy.in=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where updatedBy is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("updatedBy.specified=true", "updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where updatedBy contains
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "updatedBy.contains=" + DEFAULT_UPDATED_BY,
            "updatedBy.contains=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where updatedBy does not contain
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "updatedBy.doesNotContain=" + UPDATED_UPDATED_BY,
            "updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where updatedDate equals to
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "updatedDate.equals=" + DEFAULT_UPDATED_DATE,
            "updatedDate.equals=" + UPDATED_UPDATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where updatedDate in
        defaultNtQuoteCustomerInputOutputVersionFiltering(
            "updatedDate.in=" + DEFAULT_UPDATED_DATE + "," + UPDATED_UPDATED_DATE,
            "updatedDate.in=" + UPDATED_UPDATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        // Get all the ntQuoteCustomerInputOutputVersionList where updatedDate is not null
        defaultNtQuoteCustomerInputOutputVersionFiltering("updatedDate.specified=true", "updatedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerInputOutputVersionsByNtQuoteIsEqualToSomething() throws Exception {
        NtQuote ntQuote;
        if (TestUtil.findAll(em, NtQuote.class).isEmpty()) {
            ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(ntQuoteCustomerInputOutputVersion);
            ntQuote = NtQuoteResourceIT.createEntity();
        } else {
            ntQuote = TestUtil.findAll(em, NtQuote.class).get(0);
        }
        em.persist(ntQuote);
        em.flush();
        ntQuoteCustomerInputOutputVersion.setNtQuote(ntQuote);
        ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(ntQuoteCustomerInputOutputVersion);
        Long ntQuoteId = ntQuote.getId();
        // Get all the ntQuoteCustomerInputOutputVersionList where ntQuote equals to ntQuoteId
        defaultNtQuoteCustomerInputOutputVersionShouldBeFound("ntQuoteId.equals=" + ntQuoteId);

        // Get all the ntQuoteCustomerInputOutputVersionList where ntQuote equals to (ntQuoteId + 1)
        defaultNtQuoteCustomerInputOutputVersionShouldNotBeFound("ntQuoteId.equals=" + (ntQuoteId + 1));
    }

    private void defaultNtQuoteCustomerInputOutputVersionFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultNtQuoteCustomerInputOutputVersionShouldBeFound(shouldBeFound);
        defaultNtQuoteCustomerInputOutputVersionShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNtQuoteCustomerInputOutputVersionShouldBeFound(String filter) throws Exception {
        restNtQuoteCustomerInputOutputVersionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuoteCustomerInputOutputVersion.getId().intValue())))
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
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));

        // Check, that the count call also returns 1
        restNtQuoteCustomerInputOutputVersionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNtQuoteCustomerInputOutputVersionShouldNotBeFound(String filter) throws Exception {
        restNtQuoteCustomerInputOutputVersionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNtQuoteCustomerInputOutputVersionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNtQuoteCustomerInputOutputVersion() throws Exception {
        // Get the ntQuoteCustomerInputOutputVersion
        restNtQuoteCustomerInputOutputVersionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNtQuoteCustomerInputOutputVersion() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();
        ntQuoteCustomerInputOutputVersionSearchRepository.save(ntQuoteCustomerInputOutputVersion);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerInputOutputVersionSearchRepository.findAll());

        // Update the ntQuoteCustomerInputOutputVersion
        NtQuoteCustomerInputOutputVersion updatedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository
            .findById(ntQuoteCustomerInputOutputVersion.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedNtQuoteCustomerInputOutputVersion are not directly saved in db
        em.detach(updatedNtQuoteCustomerInputOutputVersion);
        updatedNtQuoteCustomerInputOutputVersion
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
            .version(UPDATED_VERSION)
            .comments(UPDATED_COMMENTS)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);
        NtQuoteCustomerInputOutputVersionDTO ntQuoteCustomerInputOutputVersionDTO = ntQuoteCustomerInputOutputVersionMapper.toDto(
            updatedNtQuoteCustomerInputOutputVersion
        );

        restNtQuoteCustomerInputOutputVersionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ntQuoteCustomerInputOutputVersionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteCustomerInputOutputVersionDTO))
            )
            .andExpect(status().isOk());

        // Validate the NtQuoteCustomerInputOutputVersion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedNtQuoteCustomerInputOutputVersionToMatchAllProperties(updatedNtQuoteCustomerInputOutputVersion);

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerInputOutputVersionSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<NtQuoteCustomerInputOutputVersion> ntQuoteCustomerInputOutputVersionSearchList = Streamable.of(
                    ntQuoteCustomerInputOutputVersionSearchRepository.findAll()
                ).toList();
                NtQuoteCustomerInputOutputVersion testNtQuoteCustomerInputOutputVersionSearch =
                    ntQuoteCustomerInputOutputVersionSearchList.get(searchDatabaseSizeAfter - 1);

                assertNtQuoteCustomerInputOutputVersionAllPropertiesEquals(
                    testNtQuoteCustomerInputOutputVersionSearch,
                    updatedNtQuoteCustomerInputOutputVersion
                );
            });
    }

    @Test
    @Transactional
    void putNonExistingNtQuoteCustomerInputOutputVersion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerInputOutputVersionSearchRepository.findAll());
        ntQuoteCustomerInputOutputVersion.setId(longCount.incrementAndGet());

        // Create the NtQuoteCustomerInputOutputVersion
        NtQuoteCustomerInputOutputVersionDTO ntQuoteCustomerInputOutputVersionDTO = ntQuoteCustomerInputOutputVersionMapper.toDto(
            ntQuoteCustomerInputOutputVersion
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNtQuoteCustomerInputOutputVersionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ntQuoteCustomerInputOutputVersionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteCustomerInputOutputVersionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteCustomerInputOutputVersion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerInputOutputVersionSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchNtQuoteCustomerInputOutputVersion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerInputOutputVersionSearchRepository.findAll());
        ntQuoteCustomerInputOutputVersion.setId(longCount.incrementAndGet());

        // Create the NtQuoteCustomerInputOutputVersion
        NtQuoteCustomerInputOutputVersionDTO ntQuoteCustomerInputOutputVersionDTO = ntQuoteCustomerInputOutputVersionMapper.toDto(
            ntQuoteCustomerInputOutputVersion
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteCustomerInputOutputVersionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteCustomerInputOutputVersionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteCustomerInputOutputVersion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerInputOutputVersionSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNtQuoteCustomerInputOutputVersion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerInputOutputVersionSearchRepository.findAll());
        ntQuoteCustomerInputOutputVersion.setId(longCount.incrementAndGet());

        // Create the NtQuoteCustomerInputOutputVersion
        NtQuoteCustomerInputOutputVersionDTO ntQuoteCustomerInputOutputVersionDTO = ntQuoteCustomerInputOutputVersionMapper.toDto(
            ntQuoteCustomerInputOutputVersion
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteCustomerInputOutputVersionMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteCustomerInputOutputVersionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NtQuoteCustomerInputOutputVersion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerInputOutputVersionSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateNtQuoteCustomerInputOutputVersionWithPatch() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ntQuoteCustomerInputOutputVersion using partial update
        NtQuoteCustomerInputOutputVersion partialUpdatedNtQuoteCustomerInputOutputVersion = new NtQuoteCustomerInputOutputVersion();
        partialUpdatedNtQuoteCustomerInputOutputVersion.setId(ntQuoteCustomerInputOutputVersion.getId());

        partialUpdatedNtQuoteCustomerInputOutputVersion
            .srNo(UPDATED_SR_NO)
            .partNumber(UPDATED_PART_NUMBER)
            .materialId(UPDATED_MATERIAL_ID)
            .supplier(UPDATED_SUPPLIER)
            .estAnnualVolume(UPDATED_EST_ANNUAL_VOLUME)
            .estProductionRunYrs(UPDATED_EST_PRODUCTION_RUN_YRS)
            .materialCostLb(UPDATED_MATERIAL_COST_LB)
            .runnerWeightLb(UPDATED_RUNNER_WEIGHT_LB)
            .machineSize(UPDATED_MACHINE_SIZE)
            .scrapRate(UPDATED_SCRAP_RATE)
            .machineEfficiency(UPDATED_MACHINE_EFFICIENCY)
            .fte(UPDATED_FTE)
            .purchaseComponentCostPart(UPDATED_PURCHASE_COMPONENT_COST_PART)
            .secondaryOperationLaborRate(UPDATED_SECONDARY_OPERATION_LABOR_RATE)
            .secondaryOperationMachineRate(UPDATED_SECONDARY_OPERATION_MACHINE_RATE)
            .externalOperationRate(UPDATED_EXTERNAL_OPERATION_RATE)
            .targetMaterialMarkup(UPDATED_TARGET_MATERIAL_MARKUP)
            .actualMaterialCost(UPDATED_ACTUAL_MATERIAL_COST)
            .externalOperationCostPer(UPDATED_EXTERNAL_OPERATION_COST_PER)
            .extendedMaterialCostPer(UPDATED_EXTENDED_MATERIAL_COST_PER)
            .packLogisticCostPer(UPDATED_PACK_LOGISTIC_COST_PER)
            .totalCostSgaProfit(UPDATED_TOTAL_COST_SGA_PROFIT)
            .profit(UPDATED_PROFIT)
            .partPrice(UPDATED_PART_PRICE)
            .totalSales(UPDATED_TOTAL_SALES)
            .costMaterial(UPDATED_COST_MATERIAL)
            .materialContributionMargin(UPDATED_MATERIAL_CONTRIBUTION_MARGIN)
            .version(UPDATED_VERSION)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restNtQuoteCustomerInputOutputVersionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNtQuoteCustomerInputOutputVersion.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNtQuoteCustomerInputOutputVersion))
            )
            .andExpect(status().isOk());

        // Validate the NtQuoteCustomerInputOutputVersion in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNtQuoteCustomerInputOutputVersionUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedNtQuoteCustomerInputOutputVersion, ntQuoteCustomerInputOutputVersion),
            getPersistedNtQuoteCustomerInputOutputVersion(ntQuoteCustomerInputOutputVersion)
        );
    }

    @Test
    @Transactional
    void fullUpdateNtQuoteCustomerInputOutputVersionWithPatch() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ntQuoteCustomerInputOutputVersion using partial update
        NtQuoteCustomerInputOutputVersion partialUpdatedNtQuoteCustomerInputOutputVersion = new NtQuoteCustomerInputOutputVersion();
        partialUpdatedNtQuoteCustomerInputOutputVersion.setId(ntQuoteCustomerInputOutputVersion.getId());

        partialUpdatedNtQuoteCustomerInputOutputVersion
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
            .version(UPDATED_VERSION)
            .comments(UPDATED_COMMENTS)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);

        restNtQuoteCustomerInputOutputVersionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNtQuoteCustomerInputOutputVersion.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNtQuoteCustomerInputOutputVersion))
            )
            .andExpect(status().isOk());

        // Validate the NtQuoteCustomerInputOutputVersion in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNtQuoteCustomerInputOutputVersionUpdatableFieldsEquals(
            partialUpdatedNtQuoteCustomerInputOutputVersion,
            getPersistedNtQuoteCustomerInputOutputVersion(partialUpdatedNtQuoteCustomerInputOutputVersion)
        );
    }

    @Test
    @Transactional
    void patchNonExistingNtQuoteCustomerInputOutputVersion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerInputOutputVersionSearchRepository.findAll());
        ntQuoteCustomerInputOutputVersion.setId(longCount.incrementAndGet());

        // Create the NtQuoteCustomerInputOutputVersion
        NtQuoteCustomerInputOutputVersionDTO ntQuoteCustomerInputOutputVersionDTO = ntQuoteCustomerInputOutputVersionMapper.toDto(
            ntQuoteCustomerInputOutputVersion
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNtQuoteCustomerInputOutputVersionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ntQuoteCustomerInputOutputVersionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ntQuoteCustomerInputOutputVersionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteCustomerInputOutputVersion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerInputOutputVersionSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNtQuoteCustomerInputOutputVersion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerInputOutputVersionSearchRepository.findAll());
        ntQuoteCustomerInputOutputVersion.setId(longCount.incrementAndGet());

        // Create the NtQuoteCustomerInputOutputVersion
        NtQuoteCustomerInputOutputVersionDTO ntQuoteCustomerInputOutputVersionDTO = ntQuoteCustomerInputOutputVersionMapper.toDto(
            ntQuoteCustomerInputOutputVersion
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteCustomerInputOutputVersionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ntQuoteCustomerInputOutputVersionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteCustomerInputOutputVersion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerInputOutputVersionSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNtQuoteCustomerInputOutputVersion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerInputOutputVersionSearchRepository.findAll());
        ntQuoteCustomerInputOutputVersion.setId(longCount.incrementAndGet());

        // Create the NtQuoteCustomerInputOutputVersion
        NtQuoteCustomerInputOutputVersionDTO ntQuoteCustomerInputOutputVersionDTO = ntQuoteCustomerInputOutputVersionMapper.toDto(
            ntQuoteCustomerInputOutputVersion
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteCustomerInputOutputVersionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ntQuoteCustomerInputOutputVersionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NtQuoteCustomerInputOutputVersion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerInputOutputVersionSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteNtQuoteCustomerInputOutputVersion() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );
        ntQuoteCustomerInputOutputVersionRepository.save(ntQuoteCustomerInputOutputVersion);
        ntQuoteCustomerInputOutputVersionSearchRepository.save(ntQuoteCustomerInputOutputVersion);

        long databaseSizeBeforeDelete = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerInputOutputVersionSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the ntQuoteCustomerInputOutputVersion
        restNtQuoteCustomerInputOutputVersionMockMvc
            .perform(delete(ENTITY_API_URL_ID, ntQuoteCustomerInputOutputVersion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerInputOutputVersionSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchNtQuoteCustomerInputOutputVersion() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerInputOutputVersion = ntQuoteCustomerInputOutputVersionRepository.saveAndFlush(
            ntQuoteCustomerInputOutputVersion
        );
        ntQuoteCustomerInputOutputVersionSearchRepository.save(ntQuoteCustomerInputOutputVersion);

        // Search the ntQuoteCustomerInputOutputVersion
        restNtQuoteCustomerInputOutputVersionMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + ntQuoteCustomerInputOutputVersion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuoteCustomerInputOutputVersion.getId().intValue())))
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
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    protected long getRepositoryCount() {
        return ntQuoteCustomerInputOutputVersionRepository.count();
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

    protected NtQuoteCustomerInputOutputVersion getPersistedNtQuoteCustomerInputOutputVersion(
        NtQuoteCustomerInputOutputVersion ntQuoteCustomerInputOutputVersion
    ) {
        return ntQuoteCustomerInputOutputVersionRepository.findById(ntQuoteCustomerInputOutputVersion.getId()).orElseThrow();
    }

    protected void assertPersistedNtQuoteCustomerInputOutputVersionToMatchAllProperties(
        NtQuoteCustomerInputOutputVersion expectedNtQuoteCustomerInputOutputVersion
    ) {
        assertNtQuoteCustomerInputOutputVersionAllPropertiesEquals(
            expectedNtQuoteCustomerInputOutputVersion,
            getPersistedNtQuoteCustomerInputOutputVersion(expectedNtQuoteCustomerInputOutputVersion)
        );
    }

    protected void assertPersistedNtQuoteCustomerInputOutputVersionToMatchUpdatableProperties(
        NtQuoteCustomerInputOutputVersion expectedNtQuoteCustomerInputOutputVersion
    ) {
        assertNtQuoteCustomerInputOutputVersionAllUpdatablePropertiesEquals(
            expectedNtQuoteCustomerInputOutputVersion,
            getPersistedNtQuoteCustomerInputOutputVersion(expectedNtQuoteCustomerInputOutputVersion)
        );
    }
}
