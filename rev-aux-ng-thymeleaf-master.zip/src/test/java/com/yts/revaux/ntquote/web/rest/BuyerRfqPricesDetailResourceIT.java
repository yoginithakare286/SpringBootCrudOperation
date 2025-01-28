package com.yts.revaux.ntquote.web.rest;

import static com.yts.revaux.ntquote.domain.BuyerRfqPricesDetailAsserts.*;
import static com.yts.revaux.ntquote.web.rest.TestUtil.createUpdateProxyForBean;
import static com.yts.revaux.ntquote.web.rest.TestUtil.sameInstant;
import static com.yts.revaux.ntquote.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yts.revaux.ntquote.IntegrationTest;
import com.yts.revaux.ntquote.domain.BuyerRfqPricesDetail;
import com.yts.revaux.ntquote.domain.RfqDetail;
import com.yts.revaux.ntquote.domain.VendorProfile;
import com.yts.revaux.ntquote.repository.BuyerRfqPricesDetailRepository;
import com.yts.revaux.ntquote.repository.search.BuyerRfqPricesDetailSearchRepository;
import com.yts.revaux.ntquote.service.dto.BuyerRfqPricesDetailDTO;
import com.yts.revaux.ntquote.service.mapper.BuyerRfqPricesDetailMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
 * Integration tests for the {@link BuyerRfqPricesDetailResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BuyerRfqPricesDetailResourceIT {

    private static final Integer DEFAULT_SR_NO = 1;
    private static final Integer UPDATED_SR_NO = 2;
    private static final Integer SMALLER_SR_NO = 1 - 1;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final String DEFAULT_LINE = "AAAAAAAAAA";
    private static final String UPDATED_LINE = "BBBBBBBBBB";

    private static final String DEFAULT_MATERIAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;
    private static final Integer SMALLER_QUANTITY = 1 - 1;

    private static final BigDecimal DEFAULT_EST_UNIT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_EST_UNIT_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_EST_UNIT_PRICE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_ACT_UNIT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_ACT_UNIT_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_ACT_UNIT_PRICE = new BigDecimal(1 - 1);

    private static final String DEFAULT_AWARD_FLAG = "AAAAAAAAAA";
    private static final String UPDATED_AWARD_FLAG = "BBBBBBBBBB";

    private static final String DEFAULT_QUOTE_ID = "AAAAAAAAAA";
    private static final String UPDATED_QUOTE_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_RECEIVED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_RECEIVED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_RECEIVED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final BigDecimal DEFAULT_LEAD_DAYS = new BigDecimal(1);
    private static final BigDecimal UPDATED_LEAD_DAYS = new BigDecimal(2);
    private static final BigDecimal SMALLER_LEAD_DAYS = new BigDecimal(1 - 1);

    private static final String DEFAULT_RANK = "AAAAAAAAAA";
    private static final String UPDATED_RANK = "BBBBBBBBBB";

    private static final Integer DEFAULT_SPLIT_QUANTITY_FLAG = 1;
    private static final Integer UPDATED_SPLIT_QUANTITY_FLAG = 2;
    private static final Integer SMALLER_SPLIT_QUANTITY_FLAG = 1 - 1;

    private static final String DEFAULT_MATERIAL_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_UPDATED = "AAAAAAAAAA";
    private static final String UPDATED_LAST_UPDATED = "BBBBBBBBBB";

    private static final Integer DEFAULT_INVITE_RA_FLAG = 1;
    private static final Integer UPDATED_INVITE_RA_FLAG = 2;
    private static final Integer SMALLER_INVITE_RA_FLAG = 1 - 1;

    private static final ZonedDateTime DEFAULT_AWARD_ACCEPTANCES_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_AWARD_ACCEPTANCES_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_AWARD_ACCEPTANCES_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_ORDER_ACCEPTANCES_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ORDER_ACCEPTANCES_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_ORDER_ACCEPTANCES_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Integer DEFAULT_ORDER_ACCEPTANCES_FLAG = 1;
    private static final Integer UPDATED_ORDER_ACCEPTANCES_FLAG = 2;
    private static final Integer SMALLER_ORDER_ACCEPTANCES_FLAG = 1 - 1;

    private static final String DEFAULT_MATERIAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MATERIAL_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_IMAGE = "BBBBBBBBBB";

    private static final Integer DEFAULT_TECHNICAL_SCRUTINY_FLAG = 1;
    private static final Integer UPDATED_TECHNICAL_SCRUTINY_FLAG = 2;
    private static final Integer SMALLER_TECHNICAL_SCRUTINY_FLAG = 1 - 1;

    private static final String DEFAULT_VENDOR_ATTRIBUTES = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_ATTRIBUTES = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_MARGIN_FACTOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_MARGIN_FACTOR = new BigDecimal(2);
    private static final BigDecimal SMALLER_MARGIN_FACTOR = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_FOB = new BigDecimal(1);
    private static final BigDecimal UPDATED_FOB = new BigDecimal(2);
    private static final BigDecimal SMALLER_FOB = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_SHIPPING_FACTOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_SHIPPING_FACTOR = new BigDecimal(2);
    private static final BigDecimal SMALLER_SHIPPING_FACTOR = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_FREIGHT = new BigDecimal(1);
    private static final BigDecimal UPDATED_FREIGHT = new BigDecimal(2);
    private static final BigDecimal SMALLER_FREIGHT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_FINAL_SHIPMENT_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_FINAL_SHIPMENT_COST = new BigDecimal(2);
    private static final BigDecimal SMALLER_FINAL_SHIPMENT_COST = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TARIFF = new BigDecimal(1);
    private static final BigDecimal UPDATED_TARIFF = new BigDecimal(2);
    private static final BigDecimal SMALLER_TARIFF = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CALCULATED_TARIFFS_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_CALCULATED_TARIFFS_COST = new BigDecimal(2);
    private static final BigDecimal SMALLER_CALCULATED_TARIFFS_COST = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL_CUMBERLAND_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_CUMBERLAND_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_CUMBERLAND_PRICE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_LANDED_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LANDED_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_LANDED_PRICE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_APPROVAL_TO_GAIN = new BigDecimal(1);
    private static final BigDecimal UPDATED_APPROVAL_TO_GAIN = new BigDecimal(2);
    private static final BigDecimal SMALLER_APPROVAL_TO_GAIN = new BigDecimal(1 - 1);

    private static final String DEFAULT_MOLD_SIZE_MOLD_WEIGHT = "AAAAAAAAAA";
    private static final String UPDATED_MOLD_SIZE_MOLD_WEIGHT = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_MOLD_LIFE_EXPECTANCY = new BigDecimal(1);
    private static final BigDecimal UPDATED_MOLD_LIFE_EXPECTANCY = new BigDecimal(2);
    private static final BigDecimal SMALLER_MOLD_LIFE_EXPECTANCY = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL_COST_COMPARISON = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_COST_COMPARISON = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_COST_COMPARISON = new BigDecimal(1 - 1);

    private static final String DEFAULT_LENGTH = "AAAAAAAAAA";
    private static final String UPDATED_LENGTH = "BBBBBBBBBB";

    private static final String DEFAULT_WIDTH = "AAAAAAAAAA";
    private static final String UPDATED_WIDTH = "BBBBBBBBBB";

    private static final String DEFAULT_GUAGE = "AAAAAAAAAA";
    private static final String UPDATED_GUAGE = "BBBBBBBBBB";

    private static final String DEFAULT_TOLERANCE = "AAAAAAAAAA";
    private static final String UPDATED_TOLERANCE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/buyer-rfq-prices-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/buyer-rfq-prices-details/_search";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BuyerRfqPricesDetailRepository buyerRfqPricesDetailRepository;

    @Autowired
    private BuyerRfqPricesDetailMapper buyerRfqPricesDetailMapper;

    @Autowired
    private BuyerRfqPricesDetailSearchRepository buyerRfqPricesDetailSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBuyerRfqPricesDetailMockMvc;

    private BuyerRfqPricesDetail buyerRfqPricesDetail;

    private BuyerRfqPricesDetail insertedBuyerRfqPricesDetail;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BuyerRfqPricesDetail createEntity() {
        return new BuyerRfqPricesDetail()
            .srNo(DEFAULT_SR_NO)
            .uid(DEFAULT_UID)
            .line(DEFAULT_LINE)
            .materialId(DEFAULT_MATERIAL_ID)
            .quantity(DEFAULT_QUANTITY)
            .estUnitPrice(DEFAULT_EST_UNIT_PRICE)
            .actUnitPrice(DEFAULT_ACT_UNIT_PRICE)
            .awardFlag(DEFAULT_AWARD_FLAG)
            .quoteId(DEFAULT_QUOTE_ID)
            .receivedDate(DEFAULT_RECEIVED_DATE)
            .leadDays(DEFAULT_LEAD_DAYS)
            .rank(DEFAULT_RANK)
            .splitQuantityFlag(DEFAULT_SPLIT_QUANTITY_FLAG)
            .materialDescription(DEFAULT_MATERIAL_DESCRIPTION)
            .lastUpdated(DEFAULT_LAST_UPDATED)
            .inviteRaFlag(DEFAULT_INVITE_RA_FLAG)
            .awardAcceptancesDate(DEFAULT_AWARD_ACCEPTANCES_DATE)
            .orderAcceptancesDate(DEFAULT_ORDER_ACCEPTANCES_DATE)
            .orderAcceptancesFlag(DEFAULT_ORDER_ACCEPTANCES_FLAG)
            .materialName(DEFAULT_MATERIAL_NAME)
            .materialImage(DEFAULT_MATERIAL_IMAGE)
            .technicalScrutinyFlag(DEFAULT_TECHNICAL_SCRUTINY_FLAG)
            .vendorAttributes(DEFAULT_VENDOR_ATTRIBUTES)
            .marginFactor(DEFAULT_MARGIN_FACTOR)
            .fob(DEFAULT_FOB)
            .shippingFactor(DEFAULT_SHIPPING_FACTOR)
            .freight(DEFAULT_FREIGHT)
            .finalShipmentCost(DEFAULT_FINAL_SHIPMENT_COST)
            .tariff(DEFAULT_TARIFF)
            .calculatedTariffsCost(DEFAULT_CALCULATED_TARIFFS_COST)
            .totalCumberlandPrice(DEFAULT_TOTAL_CUMBERLAND_PRICE)
            .landedPrice(DEFAULT_LANDED_PRICE)
            .approvalToGain(DEFAULT_APPROVAL_TO_GAIN)
            .moldSizeMoldWeight(DEFAULT_MOLD_SIZE_MOLD_WEIGHT)
            .moldLifeExpectancy(DEFAULT_MOLD_LIFE_EXPECTANCY)
            .totalCostComparison(DEFAULT_TOTAL_COST_COMPARISON)
            .length(DEFAULT_LENGTH)
            .width(DEFAULT_WIDTH)
            .guage(DEFAULT_GUAGE)
            .tolerance(DEFAULT_TOLERANCE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BuyerRfqPricesDetail createUpdatedEntity() {
        return new BuyerRfqPricesDetail()
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .line(UPDATED_LINE)
            .materialId(UPDATED_MATERIAL_ID)
            .quantity(UPDATED_QUANTITY)
            .estUnitPrice(UPDATED_EST_UNIT_PRICE)
            .actUnitPrice(UPDATED_ACT_UNIT_PRICE)
            .awardFlag(UPDATED_AWARD_FLAG)
            .quoteId(UPDATED_QUOTE_ID)
            .receivedDate(UPDATED_RECEIVED_DATE)
            .leadDays(UPDATED_LEAD_DAYS)
            .rank(UPDATED_RANK)
            .splitQuantityFlag(UPDATED_SPLIT_QUANTITY_FLAG)
            .materialDescription(UPDATED_MATERIAL_DESCRIPTION)
            .lastUpdated(UPDATED_LAST_UPDATED)
            .inviteRaFlag(UPDATED_INVITE_RA_FLAG)
            .awardAcceptancesDate(UPDATED_AWARD_ACCEPTANCES_DATE)
            .orderAcceptancesDate(UPDATED_ORDER_ACCEPTANCES_DATE)
            .orderAcceptancesFlag(UPDATED_ORDER_ACCEPTANCES_FLAG)
            .materialName(UPDATED_MATERIAL_NAME)
            .materialImage(UPDATED_MATERIAL_IMAGE)
            .technicalScrutinyFlag(UPDATED_TECHNICAL_SCRUTINY_FLAG)
            .vendorAttributes(UPDATED_VENDOR_ATTRIBUTES)
            .marginFactor(UPDATED_MARGIN_FACTOR)
            .fob(UPDATED_FOB)
            .shippingFactor(UPDATED_SHIPPING_FACTOR)
            .freight(UPDATED_FREIGHT)
            .finalShipmentCost(UPDATED_FINAL_SHIPMENT_COST)
            .tariff(UPDATED_TARIFF)
            .calculatedTariffsCost(UPDATED_CALCULATED_TARIFFS_COST)
            .totalCumberlandPrice(UPDATED_TOTAL_CUMBERLAND_PRICE)
            .landedPrice(UPDATED_LANDED_PRICE)
            .approvalToGain(UPDATED_APPROVAL_TO_GAIN)
            .moldSizeMoldWeight(UPDATED_MOLD_SIZE_MOLD_WEIGHT)
            .moldLifeExpectancy(UPDATED_MOLD_LIFE_EXPECTANCY)
            .totalCostComparison(UPDATED_TOTAL_COST_COMPARISON)
            .length(UPDATED_LENGTH)
            .width(UPDATED_WIDTH)
            .guage(UPDATED_GUAGE)
            .tolerance(UPDATED_TOLERANCE);
    }

    @BeforeEach
    public void initTest() {
        buyerRfqPricesDetail = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedBuyerRfqPricesDetail != null) {
            buyerRfqPricesDetailRepository.delete(insertedBuyerRfqPricesDetail);
            buyerRfqPricesDetailSearchRepository.delete(insertedBuyerRfqPricesDetail);
            insertedBuyerRfqPricesDetail = null;
        }
    }

    @Test
    @Transactional
    void createBuyerRfqPricesDetail() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(buyerRfqPricesDetailSearchRepository.findAll());
        // Create the BuyerRfqPricesDetail
        BuyerRfqPricesDetailDTO buyerRfqPricesDetailDTO = buyerRfqPricesDetailMapper.toDto(buyerRfqPricesDetail);
        var returnedBuyerRfqPricesDetailDTO = om.readValue(
            restBuyerRfqPricesDetailMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(buyerRfqPricesDetailDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            BuyerRfqPricesDetailDTO.class
        );

        // Validate the BuyerRfqPricesDetail in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedBuyerRfqPricesDetail = buyerRfqPricesDetailMapper.toEntity(returnedBuyerRfqPricesDetailDTO);
        assertBuyerRfqPricesDetailUpdatableFieldsEquals(
            returnedBuyerRfqPricesDetail,
            getPersistedBuyerRfqPricesDetail(returnedBuyerRfqPricesDetail)
        );

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(buyerRfqPricesDetailSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });

        insertedBuyerRfqPricesDetail = returnedBuyerRfqPricesDetail;
    }

    @Test
    @Transactional
    void createBuyerRfqPricesDetailWithExistingId() throws Exception {
        // Create the BuyerRfqPricesDetail with an existing ID
        buyerRfqPricesDetail.setId(1L);
        BuyerRfqPricesDetailDTO buyerRfqPricesDetailDTO = buyerRfqPricesDetailMapper.toDto(buyerRfqPricesDetail);

        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(buyerRfqPricesDetailSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restBuyerRfqPricesDetailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(buyerRfqPricesDetailDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BuyerRfqPricesDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(buyerRfqPricesDetailSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkUidIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(buyerRfqPricesDetailSearchRepository.findAll());
        // set the field null
        buyerRfqPricesDetail.setUid(null);

        // Create the BuyerRfqPricesDetail, which fails.
        BuyerRfqPricesDetailDTO buyerRfqPricesDetailDTO = buyerRfqPricesDetailMapper.toDto(buyerRfqPricesDetail);

        restBuyerRfqPricesDetailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(buyerRfqPricesDetailDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(buyerRfqPricesDetailSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkMaterialIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(buyerRfqPricesDetailSearchRepository.findAll());
        // set the field null
        buyerRfqPricesDetail.setMaterialId(null);

        // Create the BuyerRfqPricesDetail, which fails.
        BuyerRfqPricesDetailDTO buyerRfqPricesDetailDTO = buyerRfqPricesDetailMapper.toDto(buyerRfqPricesDetail);

        restBuyerRfqPricesDetailMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(buyerRfqPricesDetailDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(buyerRfqPricesDetailSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetails() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList
        restBuyerRfqPricesDetailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(buyerRfqPricesDetail.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].line").value(hasItem(DEFAULT_LINE)))
            .andExpect(jsonPath("$.[*].materialId").value(hasItem(DEFAULT_MATERIAL_ID)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].estUnitPrice").value(hasItem(sameNumber(DEFAULT_EST_UNIT_PRICE))))
            .andExpect(jsonPath("$.[*].actUnitPrice").value(hasItem(sameNumber(DEFAULT_ACT_UNIT_PRICE))))
            .andExpect(jsonPath("$.[*].awardFlag").value(hasItem(DEFAULT_AWARD_FLAG)))
            .andExpect(jsonPath("$.[*].quoteId").value(hasItem(DEFAULT_QUOTE_ID)))
            .andExpect(jsonPath("$.[*].receivedDate").value(hasItem(sameInstant(DEFAULT_RECEIVED_DATE))))
            .andExpect(jsonPath("$.[*].leadDays").value(hasItem(sameNumber(DEFAULT_LEAD_DAYS))))
            .andExpect(jsonPath("$.[*].rank").value(hasItem(DEFAULT_RANK)))
            .andExpect(jsonPath("$.[*].splitQuantityFlag").value(hasItem(DEFAULT_SPLIT_QUANTITY_FLAG)))
            .andExpect(jsonPath("$.[*].materialDescription").value(hasItem(DEFAULT_MATERIAL_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED)))
            .andExpect(jsonPath("$.[*].inviteRaFlag").value(hasItem(DEFAULT_INVITE_RA_FLAG)))
            .andExpect(jsonPath("$.[*].awardAcceptancesDate").value(hasItem(sameInstant(DEFAULT_AWARD_ACCEPTANCES_DATE))))
            .andExpect(jsonPath("$.[*].orderAcceptancesDate").value(hasItem(sameInstant(DEFAULT_ORDER_ACCEPTANCES_DATE))))
            .andExpect(jsonPath("$.[*].orderAcceptancesFlag").value(hasItem(DEFAULT_ORDER_ACCEPTANCES_FLAG)))
            .andExpect(jsonPath("$.[*].materialName").value(hasItem(DEFAULT_MATERIAL_NAME)))
            .andExpect(jsonPath("$.[*].materialImage").value(hasItem(DEFAULT_MATERIAL_IMAGE)))
            .andExpect(jsonPath("$.[*].technicalScrutinyFlag").value(hasItem(DEFAULT_TECHNICAL_SCRUTINY_FLAG)))
            .andExpect(jsonPath("$.[*].vendorAttributes").value(hasItem(DEFAULT_VENDOR_ATTRIBUTES)))
            .andExpect(jsonPath("$.[*].marginFactor").value(hasItem(sameNumber(DEFAULT_MARGIN_FACTOR))))
            .andExpect(jsonPath("$.[*].fob").value(hasItem(sameNumber(DEFAULT_FOB))))
            .andExpect(jsonPath("$.[*].shippingFactor").value(hasItem(sameNumber(DEFAULT_SHIPPING_FACTOR))))
            .andExpect(jsonPath("$.[*].freight").value(hasItem(sameNumber(DEFAULT_FREIGHT))))
            .andExpect(jsonPath("$.[*].finalShipmentCost").value(hasItem(sameNumber(DEFAULT_FINAL_SHIPMENT_COST))))
            .andExpect(jsonPath("$.[*].tariff").value(hasItem(sameNumber(DEFAULT_TARIFF))))
            .andExpect(jsonPath("$.[*].calculatedTariffsCost").value(hasItem(sameNumber(DEFAULT_CALCULATED_TARIFFS_COST))))
            .andExpect(jsonPath("$.[*].totalCumberlandPrice").value(hasItem(sameNumber(DEFAULT_TOTAL_CUMBERLAND_PRICE))))
            .andExpect(jsonPath("$.[*].landedPrice").value(hasItem(sameNumber(DEFAULT_LANDED_PRICE))))
            .andExpect(jsonPath("$.[*].approvalToGain").value(hasItem(sameNumber(DEFAULT_APPROVAL_TO_GAIN))))
            .andExpect(jsonPath("$.[*].moldSizeMoldWeight").value(hasItem(DEFAULT_MOLD_SIZE_MOLD_WEIGHT)))
            .andExpect(jsonPath("$.[*].moldLifeExpectancy").value(hasItem(sameNumber(DEFAULT_MOLD_LIFE_EXPECTANCY))))
            .andExpect(jsonPath("$.[*].totalCostComparison").value(hasItem(sameNumber(DEFAULT_TOTAL_COST_COMPARISON))))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH)))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH)))
            .andExpect(jsonPath("$.[*].guage").value(hasItem(DEFAULT_GUAGE)))
            .andExpect(jsonPath("$.[*].tolerance").value(hasItem(DEFAULT_TOLERANCE)));
    }

    @Test
    @Transactional
    void getBuyerRfqPricesDetail() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get the buyerRfqPricesDetail
        restBuyerRfqPricesDetailMockMvc
            .perform(get(ENTITY_API_URL_ID, buyerRfqPricesDetail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(buyerRfqPricesDetail.getId().intValue()))
            .andExpect(jsonPath("$.srNo").value(DEFAULT_SR_NO))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.line").value(DEFAULT_LINE))
            .andExpect(jsonPath("$.materialId").value(DEFAULT_MATERIAL_ID))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.estUnitPrice").value(sameNumber(DEFAULT_EST_UNIT_PRICE)))
            .andExpect(jsonPath("$.actUnitPrice").value(sameNumber(DEFAULT_ACT_UNIT_PRICE)))
            .andExpect(jsonPath("$.awardFlag").value(DEFAULT_AWARD_FLAG))
            .andExpect(jsonPath("$.quoteId").value(DEFAULT_QUOTE_ID))
            .andExpect(jsonPath("$.receivedDate").value(sameInstant(DEFAULT_RECEIVED_DATE)))
            .andExpect(jsonPath("$.leadDays").value(sameNumber(DEFAULT_LEAD_DAYS)))
            .andExpect(jsonPath("$.rank").value(DEFAULT_RANK))
            .andExpect(jsonPath("$.splitQuantityFlag").value(DEFAULT_SPLIT_QUANTITY_FLAG))
            .andExpect(jsonPath("$.materialDescription").value(DEFAULT_MATERIAL_DESCRIPTION))
            .andExpect(jsonPath("$.lastUpdated").value(DEFAULT_LAST_UPDATED))
            .andExpect(jsonPath("$.inviteRaFlag").value(DEFAULT_INVITE_RA_FLAG))
            .andExpect(jsonPath("$.awardAcceptancesDate").value(sameInstant(DEFAULT_AWARD_ACCEPTANCES_DATE)))
            .andExpect(jsonPath("$.orderAcceptancesDate").value(sameInstant(DEFAULT_ORDER_ACCEPTANCES_DATE)))
            .andExpect(jsonPath("$.orderAcceptancesFlag").value(DEFAULT_ORDER_ACCEPTANCES_FLAG))
            .andExpect(jsonPath("$.materialName").value(DEFAULT_MATERIAL_NAME))
            .andExpect(jsonPath("$.materialImage").value(DEFAULT_MATERIAL_IMAGE))
            .andExpect(jsonPath("$.technicalScrutinyFlag").value(DEFAULT_TECHNICAL_SCRUTINY_FLAG))
            .andExpect(jsonPath("$.vendorAttributes").value(DEFAULT_VENDOR_ATTRIBUTES))
            .andExpect(jsonPath("$.marginFactor").value(sameNumber(DEFAULT_MARGIN_FACTOR)))
            .andExpect(jsonPath("$.fob").value(sameNumber(DEFAULT_FOB)))
            .andExpect(jsonPath("$.shippingFactor").value(sameNumber(DEFAULT_SHIPPING_FACTOR)))
            .andExpect(jsonPath("$.freight").value(sameNumber(DEFAULT_FREIGHT)))
            .andExpect(jsonPath("$.finalShipmentCost").value(sameNumber(DEFAULT_FINAL_SHIPMENT_COST)))
            .andExpect(jsonPath("$.tariff").value(sameNumber(DEFAULT_TARIFF)))
            .andExpect(jsonPath("$.calculatedTariffsCost").value(sameNumber(DEFAULT_CALCULATED_TARIFFS_COST)))
            .andExpect(jsonPath("$.totalCumberlandPrice").value(sameNumber(DEFAULT_TOTAL_CUMBERLAND_PRICE)))
            .andExpect(jsonPath("$.landedPrice").value(sameNumber(DEFAULT_LANDED_PRICE)))
            .andExpect(jsonPath("$.approvalToGain").value(sameNumber(DEFAULT_APPROVAL_TO_GAIN)))
            .andExpect(jsonPath("$.moldSizeMoldWeight").value(DEFAULT_MOLD_SIZE_MOLD_WEIGHT))
            .andExpect(jsonPath("$.moldLifeExpectancy").value(sameNumber(DEFAULT_MOLD_LIFE_EXPECTANCY)))
            .andExpect(jsonPath("$.totalCostComparison").value(sameNumber(DEFAULT_TOTAL_COST_COMPARISON)))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH))
            .andExpect(jsonPath("$.guage").value(DEFAULT_GUAGE))
            .andExpect(jsonPath("$.tolerance").value(DEFAULT_TOLERANCE));
    }

    @Test
    @Transactional
    void getBuyerRfqPricesDetailsByIdFiltering() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        Long id = buyerRfqPricesDetail.getId();

        defaultBuyerRfqPricesDetailFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultBuyerRfqPricesDetailFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultBuyerRfqPricesDetailFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsBySrNoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where srNo equals to
        defaultBuyerRfqPricesDetailFiltering("srNo.equals=" + DEFAULT_SR_NO, "srNo.equals=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsBySrNoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where srNo in
        defaultBuyerRfqPricesDetailFiltering("srNo.in=" + DEFAULT_SR_NO + "," + UPDATED_SR_NO, "srNo.in=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsBySrNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where srNo is not null
        defaultBuyerRfqPricesDetailFiltering("srNo.specified=true", "srNo.specified=false");
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsBySrNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where srNo is greater than or equal to
        defaultBuyerRfqPricesDetailFiltering("srNo.greaterThanOrEqual=" + DEFAULT_SR_NO, "srNo.greaterThanOrEqual=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsBySrNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where srNo is less than or equal to
        defaultBuyerRfqPricesDetailFiltering("srNo.lessThanOrEqual=" + DEFAULT_SR_NO, "srNo.lessThanOrEqual=" + SMALLER_SR_NO);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsBySrNoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where srNo is less than
        defaultBuyerRfqPricesDetailFiltering("srNo.lessThan=" + UPDATED_SR_NO, "srNo.lessThan=" + DEFAULT_SR_NO);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsBySrNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where srNo is greater than
        defaultBuyerRfqPricesDetailFiltering("srNo.greaterThan=" + SMALLER_SR_NO, "srNo.greaterThan=" + DEFAULT_SR_NO);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where uid equals to
        defaultBuyerRfqPricesDetailFiltering("uid.equals=" + DEFAULT_UID, "uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where uid in
        defaultBuyerRfqPricesDetailFiltering("uid.in=" + DEFAULT_UID + "," + UPDATED_UID, "uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where uid is not null
        defaultBuyerRfqPricesDetailFiltering("uid.specified=true", "uid.specified=false");
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByLineIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where line equals to
        defaultBuyerRfqPricesDetailFiltering("line.equals=" + DEFAULT_LINE, "line.equals=" + UPDATED_LINE);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByLineIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where line in
        defaultBuyerRfqPricesDetailFiltering("line.in=" + DEFAULT_LINE + "," + UPDATED_LINE, "line.in=" + UPDATED_LINE);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByLineIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where line is not null
        defaultBuyerRfqPricesDetailFiltering("line.specified=true", "line.specified=false");
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByLineContainsSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where line contains
        defaultBuyerRfqPricesDetailFiltering("line.contains=" + DEFAULT_LINE, "line.contains=" + UPDATED_LINE);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByLineNotContainsSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where line does not contain
        defaultBuyerRfqPricesDetailFiltering("line.doesNotContain=" + UPDATED_LINE, "line.doesNotContain=" + DEFAULT_LINE);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByMaterialIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where materialId equals to
        defaultBuyerRfqPricesDetailFiltering("materialId.equals=" + DEFAULT_MATERIAL_ID, "materialId.equals=" + UPDATED_MATERIAL_ID);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByMaterialIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where materialId in
        defaultBuyerRfqPricesDetailFiltering(
            "materialId.in=" + DEFAULT_MATERIAL_ID + "," + UPDATED_MATERIAL_ID,
            "materialId.in=" + UPDATED_MATERIAL_ID
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByMaterialIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where materialId is not null
        defaultBuyerRfqPricesDetailFiltering("materialId.specified=true", "materialId.specified=false");
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByMaterialIdContainsSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where materialId contains
        defaultBuyerRfqPricesDetailFiltering("materialId.contains=" + DEFAULT_MATERIAL_ID, "materialId.contains=" + UPDATED_MATERIAL_ID);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByMaterialIdNotContainsSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where materialId does not contain
        defaultBuyerRfqPricesDetailFiltering(
            "materialId.doesNotContain=" + UPDATED_MATERIAL_ID,
            "materialId.doesNotContain=" + DEFAULT_MATERIAL_ID
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where quantity equals to
        defaultBuyerRfqPricesDetailFiltering("quantity.equals=" + DEFAULT_QUANTITY, "quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where quantity in
        defaultBuyerRfqPricesDetailFiltering("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY, "quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where quantity is not null
        defaultBuyerRfqPricesDetailFiltering("quantity.specified=true", "quantity.specified=false");
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where quantity is greater than or equal to
        defaultBuyerRfqPricesDetailFiltering(
            "quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY,
            "quantity.greaterThanOrEqual=" + UPDATED_QUANTITY
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where quantity is less than or equal to
        defaultBuyerRfqPricesDetailFiltering(
            "quantity.lessThanOrEqual=" + DEFAULT_QUANTITY,
            "quantity.lessThanOrEqual=" + SMALLER_QUANTITY
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where quantity is less than
        defaultBuyerRfqPricesDetailFiltering("quantity.lessThan=" + UPDATED_QUANTITY, "quantity.lessThan=" + DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where quantity is greater than
        defaultBuyerRfqPricesDetailFiltering("quantity.greaterThan=" + SMALLER_QUANTITY, "quantity.greaterThan=" + DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByEstUnitPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where estUnitPrice equals to
        defaultBuyerRfqPricesDetailFiltering(
            "estUnitPrice.equals=" + DEFAULT_EST_UNIT_PRICE,
            "estUnitPrice.equals=" + UPDATED_EST_UNIT_PRICE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByEstUnitPriceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where estUnitPrice in
        defaultBuyerRfqPricesDetailFiltering(
            "estUnitPrice.in=" + DEFAULT_EST_UNIT_PRICE + "," + UPDATED_EST_UNIT_PRICE,
            "estUnitPrice.in=" + UPDATED_EST_UNIT_PRICE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByEstUnitPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where estUnitPrice is not null
        defaultBuyerRfqPricesDetailFiltering("estUnitPrice.specified=true", "estUnitPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByEstUnitPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where estUnitPrice is greater than or equal to
        defaultBuyerRfqPricesDetailFiltering(
            "estUnitPrice.greaterThanOrEqual=" + DEFAULT_EST_UNIT_PRICE,
            "estUnitPrice.greaterThanOrEqual=" + UPDATED_EST_UNIT_PRICE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByEstUnitPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where estUnitPrice is less than or equal to
        defaultBuyerRfqPricesDetailFiltering(
            "estUnitPrice.lessThanOrEqual=" + DEFAULT_EST_UNIT_PRICE,
            "estUnitPrice.lessThanOrEqual=" + SMALLER_EST_UNIT_PRICE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByEstUnitPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where estUnitPrice is less than
        defaultBuyerRfqPricesDetailFiltering(
            "estUnitPrice.lessThan=" + UPDATED_EST_UNIT_PRICE,
            "estUnitPrice.lessThan=" + DEFAULT_EST_UNIT_PRICE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByEstUnitPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where estUnitPrice is greater than
        defaultBuyerRfqPricesDetailFiltering(
            "estUnitPrice.greaterThan=" + SMALLER_EST_UNIT_PRICE,
            "estUnitPrice.greaterThan=" + DEFAULT_EST_UNIT_PRICE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByActUnitPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where actUnitPrice equals to
        defaultBuyerRfqPricesDetailFiltering(
            "actUnitPrice.equals=" + DEFAULT_ACT_UNIT_PRICE,
            "actUnitPrice.equals=" + UPDATED_ACT_UNIT_PRICE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByActUnitPriceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where actUnitPrice in
        defaultBuyerRfqPricesDetailFiltering(
            "actUnitPrice.in=" + DEFAULT_ACT_UNIT_PRICE + "," + UPDATED_ACT_UNIT_PRICE,
            "actUnitPrice.in=" + UPDATED_ACT_UNIT_PRICE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByActUnitPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where actUnitPrice is not null
        defaultBuyerRfqPricesDetailFiltering("actUnitPrice.specified=true", "actUnitPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByActUnitPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where actUnitPrice is greater than or equal to
        defaultBuyerRfqPricesDetailFiltering(
            "actUnitPrice.greaterThanOrEqual=" + DEFAULT_ACT_UNIT_PRICE,
            "actUnitPrice.greaterThanOrEqual=" + UPDATED_ACT_UNIT_PRICE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByActUnitPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where actUnitPrice is less than or equal to
        defaultBuyerRfqPricesDetailFiltering(
            "actUnitPrice.lessThanOrEqual=" + DEFAULT_ACT_UNIT_PRICE,
            "actUnitPrice.lessThanOrEqual=" + SMALLER_ACT_UNIT_PRICE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByActUnitPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where actUnitPrice is less than
        defaultBuyerRfqPricesDetailFiltering(
            "actUnitPrice.lessThan=" + UPDATED_ACT_UNIT_PRICE,
            "actUnitPrice.lessThan=" + DEFAULT_ACT_UNIT_PRICE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByActUnitPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where actUnitPrice is greater than
        defaultBuyerRfqPricesDetailFiltering(
            "actUnitPrice.greaterThan=" + SMALLER_ACT_UNIT_PRICE,
            "actUnitPrice.greaterThan=" + DEFAULT_ACT_UNIT_PRICE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByAwardFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where awardFlag equals to
        defaultBuyerRfqPricesDetailFiltering("awardFlag.equals=" + DEFAULT_AWARD_FLAG, "awardFlag.equals=" + UPDATED_AWARD_FLAG);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByAwardFlagIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where awardFlag in
        defaultBuyerRfqPricesDetailFiltering(
            "awardFlag.in=" + DEFAULT_AWARD_FLAG + "," + UPDATED_AWARD_FLAG,
            "awardFlag.in=" + UPDATED_AWARD_FLAG
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByAwardFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where awardFlag is not null
        defaultBuyerRfqPricesDetailFiltering("awardFlag.specified=true", "awardFlag.specified=false");
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByAwardFlagContainsSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where awardFlag contains
        defaultBuyerRfqPricesDetailFiltering("awardFlag.contains=" + DEFAULT_AWARD_FLAG, "awardFlag.contains=" + UPDATED_AWARD_FLAG);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByAwardFlagNotContainsSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where awardFlag does not contain
        defaultBuyerRfqPricesDetailFiltering(
            "awardFlag.doesNotContain=" + UPDATED_AWARD_FLAG,
            "awardFlag.doesNotContain=" + DEFAULT_AWARD_FLAG
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByQuoteIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where quoteId equals to
        defaultBuyerRfqPricesDetailFiltering("quoteId.equals=" + DEFAULT_QUOTE_ID, "quoteId.equals=" + UPDATED_QUOTE_ID);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByQuoteIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where quoteId in
        defaultBuyerRfqPricesDetailFiltering("quoteId.in=" + DEFAULT_QUOTE_ID + "," + UPDATED_QUOTE_ID, "quoteId.in=" + UPDATED_QUOTE_ID);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByQuoteIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where quoteId is not null
        defaultBuyerRfqPricesDetailFiltering("quoteId.specified=true", "quoteId.specified=false");
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByQuoteIdContainsSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where quoteId contains
        defaultBuyerRfqPricesDetailFiltering("quoteId.contains=" + DEFAULT_QUOTE_ID, "quoteId.contains=" + UPDATED_QUOTE_ID);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByQuoteIdNotContainsSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where quoteId does not contain
        defaultBuyerRfqPricesDetailFiltering("quoteId.doesNotContain=" + UPDATED_QUOTE_ID, "quoteId.doesNotContain=" + DEFAULT_QUOTE_ID);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByReceivedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where receivedDate equals to
        defaultBuyerRfqPricesDetailFiltering(
            "receivedDate.equals=" + DEFAULT_RECEIVED_DATE,
            "receivedDate.equals=" + UPDATED_RECEIVED_DATE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByReceivedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where receivedDate in
        defaultBuyerRfqPricesDetailFiltering(
            "receivedDate.in=" + DEFAULT_RECEIVED_DATE + "," + UPDATED_RECEIVED_DATE,
            "receivedDate.in=" + UPDATED_RECEIVED_DATE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByReceivedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where receivedDate is not null
        defaultBuyerRfqPricesDetailFiltering("receivedDate.specified=true", "receivedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByReceivedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where receivedDate is greater than or equal to
        defaultBuyerRfqPricesDetailFiltering(
            "receivedDate.greaterThanOrEqual=" + DEFAULT_RECEIVED_DATE,
            "receivedDate.greaterThanOrEqual=" + UPDATED_RECEIVED_DATE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByReceivedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where receivedDate is less than or equal to
        defaultBuyerRfqPricesDetailFiltering(
            "receivedDate.lessThanOrEqual=" + DEFAULT_RECEIVED_DATE,
            "receivedDate.lessThanOrEqual=" + SMALLER_RECEIVED_DATE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByReceivedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where receivedDate is less than
        defaultBuyerRfqPricesDetailFiltering(
            "receivedDate.lessThan=" + UPDATED_RECEIVED_DATE,
            "receivedDate.lessThan=" + DEFAULT_RECEIVED_DATE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByReceivedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where receivedDate is greater than
        defaultBuyerRfqPricesDetailFiltering(
            "receivedDate.greaterThan=" + SMALLER_RECEIVED_DATE,
            "receivedDate.greaterThan=" + DEFAULT_RECEIVED_DATE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByLeadDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where leadDays equals to
        defaultBuyerRfqPricesDetailFiltering("leadDays.equals=" + DEFAULT_LEAD_DAYS, "leadDays.equals=" + UPDATED_LEAD_DAYS);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByLeadDaysIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where leadDays in
        defaultBuyerRfqPricesDetailFiltering(
            "leadDays.in=" + DEFAULT_LEAD_DAYS + "," + UPDATED_LEAD_DAYS,
            "leadDays.in=" + UPDATED_LEAD_DAYS
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByLeadDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where leadDays is not null
        defaultBuyerRfqPricesDetailFiltering("leadDays.specified=true", "leadDays.specified=false");
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByLeadDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where leadDays is greater than or equal to
        defaultBuyerRfqPricesDetailFiltering(
            "leadDays.greaterThanOrEqual=" + DEFAULT_LEAD_DAYS,
            "leadDays.greaterThanOrEqual=" + UPDATED_LEAD_DAYS
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByLeadDaysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where leadDays is less than or equal to
        defaultBuyerRfqPricesDetailFiltering(
            "leadDays.lessThanOrEqual=" + DEFAULT_LEAD_DAYS,
            "leadDays.lessThanOrEqual=" + SMALLER_LEAD_DAYS
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByLeadDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where leadDays is less than
        defaultBuyerRfqPricesDetailFiltering("leadDays.lessThan=" + UPDATED_LEAD_DAYS, "leadDays.lessThan=" + DEFAULT_LEAD_DAYS);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByLeadDaysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where leadDays is greater than
        defaultBuyerRfqPricesDetailFiltering("leadDays.greaterThan=" + SMALLER_LEAD_DAYS, "leadDays.greaterThan=" + DEFAULT_LEAD_DAYS);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByRankIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where rank equals to
        defaultBuyerRfqPricesDetailFiltering("rank.equals=" + DEFAULT_RANK, "rank.equals=" + UPDATED_RANK);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByRankIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where rank in
        defaultBuyerRfqPricesDetailFiltering("rank.in=" + DEFAULT_RANK + "," + UPDATED_RANK, "rank.in=" + UPDATED_RANK);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByRankIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where rank is not null
        defaultBuyerRfqPricesDetailFiltering("rank.specified=true", "rank.specified=false");
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByRankContainsSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where rank contains
        defaultBuyerRfqPricesDetailFiltering("rank.contains=" + DEFAULT_RANK, "rank.contains=" + UPDATED_RANK);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByRankNotContainsSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where rank does not contain
        defaultBuyerRfqPricesDetailFiltering("rank.doesNotContain=" + UPDATED_RANK, "rank.doesNotContain=" + DEFAULT_RANK);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsBySplitQuantityFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where splitQuantityFlag equals to
        defaultBuyerRfqPricesDetailFiltering(
            "splitQuantityFlag.equals=" + DEFAULT_SPLIT_QUANTITY_FLAG,
            "splitQuantityFlag.equals=" + UPDATED_SPLIT_QUANTITY_FLAG
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsBySplitQuantityFlagIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where splitQuantityFlag in
        defaultBuyerRfqPricesDetailFiltering(
            "splitQuantityFlag.in=" + DEFAULT_SPLIT_QUANTITY_FLAG + "," + UPDATED_SPLIT_QUANTITY_FLAG,
            "splitQuantityFlag.in=" + UPDATED_SPLIT_QUANTITY_FLAG
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsBySplitQuantityFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where splitQuantityFlag is not null
        defaultBuyerRfqPricesDetailFiltering("splitQuantityFlag.specified=true", "splitQuantityFlag.specified=false");
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsBySplitQuantityFlagIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where splitQuantityFlag is greater than or equal to
        defaultBuyerRfqPricesDetailFiltering(
            "splitQuantityFlag.greaterThanOrEqual=" + DEFAULT_SPLIT_QUANTITY_FLAG,
            "splitQuantityFlag.greaterThanOrEqual=" + UPDATED_SPLIT_QUANTITY_FLAG
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsBySplitQuantityFlagIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where splitQuantityFlag is less than or equal to
        defaultBuyerRfqPricesDetailFiltering(
            "splitQuantityFlag.lessThanOrEqual=" + DEFAULT_SPLIT_QUANTITY_FLAG,
            "splitQuantityFlag.lessThanOrEqual=" + SMALLER_SPLIT_QUANTITY_FLAG
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsBySplitQuantityFlagIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where splitQuantityFlag is less than
        defaultBuyerRfqPricesDetailFiltering(
            "splitQuantityFlag.lessThan=" + UPDATED_SPLIT_QUANTITY_FLAG,
            "splitQuantityFlag.lessThan=" + DEFAULT_SPLIT_QUANTITY_FLAG
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsBySplitQuantityFlagIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where splitQuantityFlag is greater than
        defaultBuyerRfqPricesDetailFiltering(
            "splitQuantityFlag.greaterThan=" + SMALLER_SPLIT_QUANTITY_FLAG,
            "splitQuantityFlag.greaterThan=" + DEFAULT_SPLIT_QUANTITY_FLAG
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByMaterialDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where materialDescription equals to
        defaultBuyerRfqPricesDetailFiltering(
            "materialDescription.equals=" + DEFAULT_MATERIAL_DESCRIPTION,
            "materialDescription.equals=" + UPDATED_MATERIAL_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByMaterialDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where materialDescription in
        defaultBuyerRfqPricesDetailFiltering(
            "materialDescription.in=" + DEFAULT_MATERIAL_DESCRIPTION + "," + UPDATED_MATERIAL_DESCRIPTION,
            "materialDescription.in=" + UPDATED_MATERIAL_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByMaterialDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where materialDescription is not null
        defaultBuyerRfqPricesDetailFiltering("materialDescription.specified=true", "materialDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByMaterialDescriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where materialDescription contains
        defaultBuyerRfqPricesDetailFiltering(
            "materialDescription.contains=" + DEFAULT_MATERIAL_DESCRIPTION,
            "materialDescription.contains=" + UPDATED_MATERIAL_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByMaterialDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where materialDescription does not contain
        defaultBuyerRfqPricesDetailFiltering(
            "materialDescription.doesNotContain=" + UPDATED_MATERIAL_DESCRIPTION,
            "materialDescription.doesNotContain=" + DEFAULT_MATERIAL_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByLastUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where lastUpdated equals to
        defaultBuyerRfqPricesDetailFiltering("lastUpdated.equals=" + DEFAULT_LAST_UPDATED, "lastUpdated.equals=" + UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByLastUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where lastUpdated in
        defaultBuyerRfqPricesDetailFiltering(
            "lastUpdated.in=" + DEFAULT_LAST_UPDATED + "," + UPDATED_LAST_UPDATED,
            "lastUpdated.in=" + UPDATED_LAST_UPDATED
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByLastUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where lastUpdated is not null
        defaultBuyerRfqPricesDetailFiltering("lastUpdated.specified=true", "lastUpdated.specified=false");
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByLastUpdatedContainsSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where lastUpdated contains
        defaultBuyerRfqPricesDetailFiltering(
            "lastUpdated.contains=" + DEFAULT_LAST_UPDATED,
            "lastUpdated.contains=" + UPDATED_LAST_UPDATED
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByLastUpdatedNotContainsSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where lastUpdated does not contain
        defaultBuyerRfqPricesDetailFiltering(
            "lastUpdated.doesNotContain=" + UPDATED_LAST_UPDATED,
            "lastUpdated.doesNotContain=" + DEFAULT_LAST_UPDATED
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByInviteRaFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where inviteRaFlag equals to
        defaultBuyerRfqPricesDetailFiltering(
            "inviteRaFlag.equals=" + DEFAULT_INVITE_RA_FLAG,
            "inviteRaFlag.equals=" + UPDATED_INVITE_RA_FLAG
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByInviteRaFlagIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where inviteRaFlag in
        defaultBuyerRfqPricesDetailFiltering(
            "inviteRaFlag.in=" + DEFAULT_INVITE_RA_FLAG + "," + UPDATED_INVITE_RA_FLAG,
            "inviteRaFlag.in=" + UPDATED_INVITE_RA_FLAG
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByInviteRaFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where inviteRaFlag is not null
        defaultBuyerRfqPricesDetailFiltering("inviteRaFlag.specified=true", "inviteRaFlag.specified=false");
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByInviteRaFlagIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where inviteRaFlag is greater than or equal to
        defaultBuyerRfqPricesDetailFiltering(
            "inviteRaFlag.greaterThanOrEqual=" + DEFAULT_INVITE_RA_FLAG,
            "inviteRaFlag.greaterThanOrEqual=" + UPDATED_INVITE_RA_FLAG
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByInviteRaFlagIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where inviteRaFlag is less than or equal to
        defaultBuyerRfqPricesDetailFiltering(
            "inviteRaFlag.lessThanOrEqual=" + DEFAULT_INVITE_RA_FLAG,
            "inviteRaFlag.lessThanOrEqual=" + SMALLER_INVITE_RA_FLAG
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByInviteRaFlagIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where inviteRaFlag is less than
        defaultBuyerRfqPricesDetailFiltering(
            "inviteRaFlag.lessThan=" + UPDATED_INVITE_RA_FLAG,
            "inviteRaFlag.lessThan=" + DEFAULT_INVITE_RA_FLAG
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByInviteRaFlagIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where inviteRaFlag is greater than
        defaultBuyerRfqPricesDetailFiltering(
            "inviteRaFlag.greaterThan=" + SMALLER_INVITE_RA_FLAG,
            "inviteRaFlag.greaterThan=" + DEFAULT_INVITE_RA_FLAG
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByAwardAcceptancesDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where awardAcceptancesDate equals to
        defaultBuyerRfqPricesDetailFiltering(
            "awardAcceptancesDate.equals=" + DEFAULT_AWARD_ACCEPTANCES_DATE,
            "awardAcceptancesDate.equals=" + UPDATED_AWARD_ACCEPTANCES_DATE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByAwardAcceptancesDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where awardAcceptancesDate in
        defaultBuyerRfqPricesDetailFiltering(
            "awardAcceptancesDate.in=" + DEFAULT_AWARD_ACCEPTANCES_DATE + "," + UPDATED_AWARD_ACCEPTANCES_DATE,
            "awardAcceptancesDate.in=" + UPDATED_AWARD_ACCEPTANCES_DATE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByAwardAcceptancesDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where awardAcceptancesDate is not null
        defaultBuyerRfqPricesDetailFiltering("awardAcceptancesDate.specified=true", "awardAcceptancesDate.specified=false");
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByAwardAcceptancesDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where awardAcceptancesDate is greater than or equal to
        defaultBuyerRfqPricesDetailFiltering(
            "awardAcceptancesDate.greaterThanOrEqual=" + DEFAULT_AWARD_ACCEPTANCES_DATE,
            "awardAcceptancesDate.greaterThanOrEqual=" + UPDATED_AWARD_ACCEPTANCES_DATE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByAwardAcceptancesDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where awardAcceptancesDate is less than or equal to
        defaultBuyerRfqPricesDetailFiltering(
            "awardAcceptancesDate.lessThanOrEqual=" + DEFAULT_AWARD_ACCEPTANCES_DATE,
            "awardAcceptancesDate.lessThanOrEqual=" + SMALLER_AWARD_ACCEPTANCES_DATE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByAwardAcceptancesDateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where awardAcceptancesDate is less than
        defaultBuyerRfqPricesDetailFiltering(
            "awardAcceptancesDate.lessThan=" + UPDATED_AWARD_ACCEPTANCES_DATE,
            "awardAcceptancesDate.lessThan=" + DEFAULT_AWARD_ACCEPTANCES_DATE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByAwardAcceptancesDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where awardAcceptancesDate is greater than
        defaultBuyerRfqPricesDetailFiltering(
            "awardAcceptancesDate.greaterThan=" + SMALLER_AWARD_ACCEPTANCES_DATE,
            "awardAcceptancesDate.greaterThan=" + DEFAULT_AWARD_ACCEPTANCES_DATE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByOrderAcceptancesDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where orderAcceptancesDate equals to
        defaultBuyerRfqPricesDetailFiltering(
            "orderAcceptancesDate.equals=" + DEFAULT_ORDER_ACCEPTANCES_DATE,
            "orderAcceptancesDate.equals=" + UPDATED_ORDER_ACCEPTANCES_DATE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByOrderAcceptancesDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where orderAcceptancesDate in
        defaultBuyerRfqPricesDetailFiltering(
            "orderAcceptancesDate.in=" + DEFAULT_ORDER_ACCEPTANCES_DATE + "," + UPDATED_ORDER_ACCEPTANCES_DATE,
            "orderAcceptancesDate.in=" + UPDATED_ORDER_ACCEPTANCES_DATE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByOrderAcceptancesDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where orderAcceptancesDate is not null
        defaultBuyerRfqPricesDetailFiltering("orderAcceptancesDate.specified=true", "orderAcceptancesDate.specified=false");
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByOrderAcceptancesDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where orderAcceptancesDate is greater than or equal to
        defaultBuyerRfqPricesDetailFiltering(
            "orderAcceptancesDate.greaterThanOrEqual=" + DEFAULT_ORDER_ACCEPTANCES_DATE,
            "orderAcceptancesDate.greaterThanOrEqual=" + UPDATED_ORDER_ACCEPTANCES_DATE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByOrderAcceptancesDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where orderAcceptancesDate is less than or equal to
        defaultBuyerRfqPricesDetailFiltering(
            "orderAcceptancesDate.lessThanOrEqual=" + DEFAULT_ORDER_ACCEPTANCES_DATE,
            "orderAcceptancesDate.lessThanOrEqual=" + SMALLER_ORDER_ACCEPTANCES_DATE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByOrderAcceptancesDateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where orderAcceptancesDate is less than
        defaultBuyerRfqPricesDetailFiltering(
            "orderAcceptancesDate.lessThan=" + UPDATED_ORDER_ACCEPTANCES_DATE,
            "orderAcceptancesDate.lessThan=" + DEFAULT_ORDER_ACCEPTANCES_DATE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByOrderAcceptancesDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where orderAcceptancesDate is greater than
        defaultBuyerRfqPricesDetailFiltering(
            "orderAcceptancesDate.greaterThan=" + SMALLER_ORDER_ACCEPTANCES_DATE,
            "orderAcceptancesDate.greaterThan=" + DEFAULT_ORDER_ACCEPTANCES_DATE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByOrderAcceptancesFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where orderAcceptancesFlag equals to
        defaultBuyerRfqPricesDetailFiltering(
            "orderAcceptancesFlag.equals=" + DEFAULT_ORDER_ACCEPTANCES_FLAG,
            "orderAcceptancesFlag.equals=" + UPDATED_ORDER_ACCEPTANCES_FLAG
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByOrderAcceptancesFlagIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where orderAcceptancesFlag in
        defaultBuyerRfqPricesDetailFiltering(
            "orderAcceptancesFlag.in=" + DEFAULT_ORDER_ACCEPTANCES_FLAG + "," + UPDATED_ORDER_ACCEPTANCES_FLAG,
            "orderAcceptancesFlag.in=" + UPDATED_ORDER_ACCEPTANCES_FLAG
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByOrderAcceptancesFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where orderAcceptancesFlag is not null
        defaultBuyerRfqPricesDetailFiltering("orderAcceptancesFlag.specified=true", "orderAcceptancesFlag.specified=false");
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByOrderAcceptancesFlagIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where orderAcceptancesFlag is greater than or equal to
        defaultBuyerRfqPricesDetailFiltering(
            "orderAcceptancesFlag.greaterThanOrEqual=" + DEFAULT_ORDER_ACCEPTANCES_FLAG,
            "orderAcceptancesFlag.greaterThanOrEqual=" + UPDATED_ORDER_ACCEPTANCES_FLAG
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByOrderAcceptancesFlagIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where orderAcceptancesFlag is less than or equal to
        defaultBuyerRfqPricesDetailFiltering(
            "orderAcceptancesFlag.lessThanOrEqual=" + DEFAULT_ORDER_ACCEPTANCES_FLAG,
            "orderAcceptancesFlag.lessThanOrEqual=" + SMALLER_ORDER_ACCEPTANCES_FLAG
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByOrderAcceptancesFlagIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where orderAcceptancesFlag is less than
        defaultBuyerRfqPricesDetailFiltering(
            "orderAcceptancesFlag.lessThan=" + UPDATED_ORDER_ACCEPTANCES_FLAG,
            "orderAcceptancesFlag.lessThan=" + DEFAULT_ORDER_ACCEPTANCES_FLAG
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByOrderAcceptancesFlagIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where orderAcceptancesFlag is greater than
        defaultBuyerRfqPricesDetailFiltering(
            "orderAcceptancesFlag.greaterThan=" + SMALLER_ORDER_ACCEPTANCES_FLAG,
            "orderAcceptancesFlag.greaterThan=" + DEFAULT_ORDER_ACCEPTANCES_FLAG
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByMaterialNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where materialName equals to
        defaultBuyerRfqPricesDetailFiltering(
            "materialName.equals=" + DEFAULT_MATERIAL_NAME,
            "materialName.equals=" + UPDATED_MATERIAL_NAME
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByMaterialNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where materialName in
        defaultBuyerRfqPricesDetailFiltering(
            "materialName.in=" + DEFAULT_MATERIAL_NAME + "," + UPDATED_MATERIAL_NAME,
            "materialName.in=" + UPDATED_MATERIAL_NAME
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByMaterialNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where materialName is not null
        defaultBuyerRfqPricesDetailFiltering("materialName.specified=true", "materialName.specified=false");
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByMaterialNameContainsSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where materialName contains
        defaultBuyerRfqPricesDetailFiltering(
            "materialName.contains=" + DEFAULT_MATERIAL_NAME,
            "materialName.contains=" + UPDATED_MATERIAL_NAME
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByMaterialNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where materialName does not contain
        defaultBuyerRfqPricesDetailFiltering(
            "materialName.doesNotContain=" + UPDATED_MATERIAL_NAME,
            "materialName.doesNotContain=" + DEFAULT_MATERIAL_NAME
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByMaterialImageIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where materialImage equals to
        defaultBuyerRfqPricesDetailFiltering(
            "materialImage.equals=" + DEFAULT_MATERIAL_IMAGE,
            "materialImage.equals=" + UPDATED_MATERIAL_IMAGE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByMaterialImageIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where materialImage in
        defaultBuyerRfqPricesDetailFiltering(
            "materialImage.in=" + DEFAULT_MATERIAL_IMAGE + "," + UPDATED_MATERIAL_IMAGE,
            "materialImage.in=" + UPDATED_MATERIAL_IMAGE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByMaterialImageIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where materialImage is not null
        defaultBuyerRfqPricesDetailFiltering("materialImage.specified=true", "materialImage.specified=false");
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByMaterialImageContainsSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where materialImage contains
        defaultBuyerRfqPricesDetailFiltering(
            "materialImage.contains=" + DEFAULT_MATERIAL_IMAGE,
            "materialImage.contains=" + UPDATED_MATERIAL_IMAGE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByMaterialImageNotContainsSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where materialImage does not contain
        defaultBuyerRfqPricesDetailFiltering(
            "materialImage.doesNotContain=" + UPDATED_MATERIAL_IMAGE,
            "materialImage.doesNotContain=" + DEFAULT_MATERIAL_IMAGE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByTechnicalScrutinyFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where technicalScrutinyFlag equals to
        defaultBuyerRfqPricesDetailFiltering(
            "technicalScrutinyFlag.equals=" + DEFAULT_TECHNICAL_SCRUTINY_FLAG,
            "technicalScrutinyFlag.equals=" + UPDATED_TECHNICAL_SCRUTINY_FLAG
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByTechnicalScrutinyFlagIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where technicalScrutinyFlag in
        defaultBuyerRfqPricesDetailFiltering(
            "technicalScrutinyFlag.in=" + DEFAULT_TECHNICAL_SCRUTINY_FLAG + "," + UPDATED_TECHNICAL_SCRUTINY_FLAG,
            "technicalScrutinyFlag.in=" + UPDATED_TECHNICAL_SCRUTINY_FLAG
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByTechnicalScrutinyFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where technicalScrutinyFlag is not null
        defaultBuyerRfqPricesDetailFiltering("technicalScrutinyFlag.specified=true", "technicalScrutinyFlag.specified=false");
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByTechnicalScrutinyFlagIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where technicalScrutinyFlag is greater than or equal to
        defaultBuyerRfqPricesDetailFiltering(
            "technicalScrutinyFlag.greaterThanOrEqual=" + DEFAULT_TECHNICAL_SCRUTINY_FLAG,
            "technicalScrutinyFlag.greaterThanOrEqual=" + UPDATED_TECHNICAL_SCRUTINY_FLAG
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByTechnicalScrutinyFlagIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where technicalScrutinyFlag is less than or equal to
        defaultBuyerRfqPricesDetailFiltering(
            "technicalScrutinyFlag.lessThanOrEqual=" + DEFAULT_TECHNICAL_SCRUTINY_FLAG,
            "technicalScrutinyFlag.lessThanOrEqual=" + SMALLER_TECHNICAL_SCRUTINY_FLAG
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByTechnicalScrutinyFlagIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where technicalScrutinyFlag is less than
        defaultBuyerRfqPricesDetailFiltering(
            "technicalScrutinyFlag.lessThan=" + UPDATED_TECHNICAL_SCRUTINY_FLAG,
            "technicalScrutinyFlag.lessThan=" + DEFAULT_TECHNICAL_SCRUTINY_FLAG
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByTechnicalScrutinyFlagIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where technicalScrutinyFlag is greater than
        defaultBuyerRfqPricesDetailFiltering(
            "technicalScrutinyFlag.greaterThan=" + SMALLER_TECHNICAL_SCRUTINY_FLAG,
            "technicalScrutinyFlag.greaterThan=" + DEFAULT_TECHNICAL_SCRUTINY_FLAG
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByVendorAttributesIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where vendorAttributes equals to
        defaultBuyerRfqPricesDetailFiltering(
            "vendorAttributes.equals=" + DEFAULT_VENDOR_ATTRIBUTES,
            "vendorAttributes.equals=" + UPDATED_VENDOR_ATTRIBUTES
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByVendorAttributesIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where vendorAttributes in
        defaultBuyerRfqPricesDetailFiltering(
            "vendorAttributes.in=" + DEFAULT_VENDOR_ATTRIBUTES + "," + UPDATED_VENDOR_ATTRIBUTES,
            "vendorAttributes.in=" + UPDATED_VENDOR_ATTRIBUTES
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByVendorAttributesIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where vendorAttributes is not null
        defaultBuyerRfqPricesDetailFiltering("vendorAttributes.specified=true", "vendorAttributes.specified=false");
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByVendorAttributesContainsSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where vendorAttributes contains
        defaultBuyerRfqPricesDetailFiltering(
            "vendorAttributes.contains=" + DEFAULT_VENDOR_ATTRIBUTES,
            "vendorAttributes.contains=" + UPDATED_VENDOR_ATTRIBUTES
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByVendorAttributesNotContainsSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where vendorAttributes does not contain
        defaultBuyerRfqPricesDetailFiltering(
            "vendorAttributes.doesNotContain=" + UPDATED_VENDOR_ATTRIBUTES,
            "vendorAttributes.doesNotContain=" + DEFAULT_VENDOR_ATTRIBUTES
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByMarginFactorIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where marginFactor equals to
        defaultBuyerRfqPricesDetailFiltering(
            "marginFactor.equals=" + DEFAULT_MARGIN_FACTOR,
            "marginFactor.equals=" + UPDATED_MARGIN_FACTOR
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByMarginFactorIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where marginFactor in
        defaultBuyerRfqPricesDetailFiltering(
            "marginFactor.in=" + DEFAULT_MARGIN_FACTOR + "," + UPDATED_MARGIN_FACTOR,
            "marginFactor.in=" + UPDATED_MARGIN_FACTOR
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByMarginFactorIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where marginFactor is not null
        defaultBuyerRfqPricesDetailFiltering("marginFactor.specified=true", "marginFactor.specified=false");
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByMarginFactorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where marginFactor is greater than or equal to
        defaultBuyerRfqPricesDetailFiltering(
            "marginFactor.greaterThanOrEqual=" + DEFAULT_MARGIN_FACTOR,
            "marginFactor.greaterThanOrEqual=" + UPDATED_MARGIN_FACTOR
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByMarginFactorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where marginFactor is less than or equal to
        defaultBuyerRfqPricesDetailFiltering(
            "marginFactor.lessThanOrEqual=" + DEFAULT_MARGIN_FACTOR,
            "marginFactor.lessThanOrEqual=" + SMALLER_MARGIN_FACTOR
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByMarginFactorIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where marginFactor is less than
        defaultBuyerRfqPricesDetailFiltering(
            "marginFactor.lessThan=" + UPDATED_MARGIN_FACTOR,
            "marginFactor.lessThan=" + DEFAULT_MARGIN_FACTOR
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByMarginFactorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where marginFactor is greater than
        defaultBuyerRfqPricesDetailFiltering(
            "marginFactor.greaterThan=" + SMALLER_MARGIN_FACTOR,
            "marginFactor.greaterThan=" + DEFAULT_MARGIN_FACTOR
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByFobIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where fob equals to
        defaultBuyerRfqPricesDetailFiltering("fob.equals=" + DEFAULT_FOB, "fob.equals=" + UPDATED_FOB);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByFobIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where fob in
        defaultBuyerRfqPricesDetailFiltering("fob.in=" + DEFAULT_FOB + "," + UPDATED_FOB, "fob.in=" + UPDATED_FOB);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByFobIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where fob is not null
        defaultBuyerRfqPricesDetailFiltering("fob.specified=true", "fob.specified=false");
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByFobIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where fob is greater than or equal to
        defaultBuyerRfqPricesDetailFiltering("fob.greaterThanOrEqual=" + DEFAULT_FOB, "fob.greaterThanOrEqual=" + UPDATED_FOB);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByFobIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where fob is less than or equal to
        defaultBuyerRfqPricesDetailFiltering("fob.lessThanOrEqual=" + DEFAULT_FOB, "fob.lessThanOrEqual=" + SMALLER_FOB);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByFobIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where fob is less than
        defaultBuyerRfqPricesDetailFiltering("fob.lessThan=" + UPDATED_FOB, "fob.lessThan=" + DEFAULT_FOB);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByFobIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where fob is greater than
        defaultBuyerRfqPricesDetailFiltering("fob.greaterThan=" + SMALLER_FOB, "fob.greaterThan=" + DEFAULT_FOB);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByShippingFactorIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where shippingFactor equals to
        defaultBuyerRfqPricesDetailFiltering(
            "shippingFactor.equals=" + DEFAULT_SHIPPING_FACTOR,
            "shippingFactor.equals=" + UPDATED_SHIPPING_FACTOR
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByShippingFactorIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where shippingFactor in
        defaultBuyerRfqPricesDetailFiltering(
            "shippingFactor.in=" + DEFAULT_SHIPPING_FACTOR + "," + UPDATED_SHIPPING_FACTOR,
            "shippingFactor.in=" + UPDATED_SHIPPING_FACTOR
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByShippingFactorIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where shippingFactor is not null
        defaultBuyerRfqPricesDetailFiltering("shippingFactor.specified=true", "shippingFactor.specified=false");
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByShippingFactorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where shippingFactor is greater than or equal to
        defaultBuyerRfqPricesDetailFiltering(
            "shippingFactor.greaterThanOrEqual=" + DEFAULT_SHIPPING_FACTOR,
            "shippingFactor.greaterThanOrEqual=" + UPDATED_SHIPPING_FACTOR
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByShippingFactorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where shippingFactor is less than or equal to
        defaultBuyerRfqPricesDetailFiltering(
            "shippingFactor.lessThanOrEqual=" + DEFAULT_SHIPPING_FACTOR,
            "shippingFactor.lessThanOrEqual=" + SMALLER_SHIPPING_FACTOR
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByShippingFactorIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where shippingFactor is less than
        defaultBuyerRfqPricesDetailFiltering(
            "shippingFactor.lessThan=" + UPDATED_SHIPPING_FACTOR,
            "shippingFactor.lessThan=" + DEFAULT_SHIPPING_FACTOR
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByShippingFactorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where shippingFactor is greater than
        defaultBuyerRfqPricesDetailFiltering(
            "shippingFactor.greaterThan=" + SMALLER_SHIPPING_FACTOR,
            "shippingFactor.greaterThan=" + DEFAULT_SHIPPING_FACTOR
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByFreightIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where freight equals to
        defaultBuyerRfqPricesDetailFiltering("freight.equals=" + DEFAULT_FREIGHT, "freight.equals=" + UPDATED_FREIGHT);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByFreightIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where freight in
        defaultBuyerRfqPricesDetailFiltering("freight.in=" + DEFAULT_FREIGHT + "," + UPDATED_FREIGHT, "freight.in=" + UPDATED_FREIGHT);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByFreightIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where freight is not null
        defaultBuyerRfqPricesDetailFiltering("freight.specified=true", "freight.specified=false");
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByFreightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where freight is greater than or equal to
        defaultBuyerRfqPricesDetailFiltering(
            "freight.greaterThanOrEqual=" + DEFAULT_FREIGHT,
            "freight.greaterThanOrEqual=" + UPDATED_FREIGHT
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByFreightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where freight is less than or equal to
        defaultBuyerRfqPricesDetailFiltering("freight.lessThanOrEqual=" + DEFAULT_FREIGHT, "freight.lessThanOrEqual=" + SMALLER_FREIGHT);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByFreightIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where freight is less than
        defaultBuyerRfqPricesDetailFiltering("freight.lessThan=" + UPDATED_FREIGHT, "freight.lessThan=" + DEFAULT_FREIGHT);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByFreightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where freight is greater than
        defaultBuyerRfqPricesDetailFiltering("freight.greaterThan=" + SMALLER_FREIGHT, "freight.greaterThan=" + DEFAULT_FREIGHT);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByFinalShipmentCostIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where finalShipmentCost equals to
        defaultBuyerRfqPricesDetailFiltering(
            "finalShipmentCost.equals=" + DEFAULT_FINAL_SHIPMENT_COST,
            "finalShipmentCost.equals=" + UPDATED_FINAL_SHIPMENT_COST
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByFinalShipmentCostIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where finalShipmentCost in
        defaultBuyerRfqPricesDetailFiltering(
            "finalShipmentCost.in=" + DEFAULT_FINAL_SHIPMENT_COST + "," + UPDATED_FINAL_SHIPMENT_COST,
            "finalShipmentCost.in=" + UPDATED_FINAL_SHIPMENT_COST
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByFinalShipmentCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where finalShipmentCost is not null
        defaultBuyerRfqPricesDetailFiltering("finalShipmentCost.specified=true", "finalShipmentCost.specified=false");
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByFinalShipmentCostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where finalShipmentCost is greater than or equal to
        defaultBuyerRfqPricesDetailFiltering(
            "finalShipmentCost.greaterThanOrEqual=" + DEFAULT_FINAL_SHIPMENT_COST,
            "finalShipmentCost.greaterThanOrEqual=" + UPDATED_FINAL_SHIPMENT_COST
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByFinalShipmentCostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where finalShipmentCost is less than or equal to
        defaultBuyerRfqPricesDetailFiltering(
            "finalShipmentCost.lessThanOrEqual=" + DEFAULT_FINAL_SHIPMENT_COST,
            "finalShipmentCost.lessThanOrEqual=" + SMALLER_FINAL_SHIPMENT_COST
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByFinalShipmentCostIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where finalShipmentCost is less than
        defaultBuyerRfqPricesDetailFiltering(
            "finalShipmentCost.lessThan=" + UPDATED_FINAL_SHIPMENT_COST,
            "finalShipmentCost.lessThan=" + DEFAULT_FINAL_SHIPMENT_COST
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByFinalShipmentCostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where finalShipmentCost is greater than
        defaultBuyerRfqPricesDetailFiltering(
            "finalShipmentCost.greaterThan=" + SMALLER_FINAL_SHIPMENT_COST,
            "finalShipmentCost.greaterThan=" + DEFAULT_FINAL_SHIPMENT_COST
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByTariffIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where tariff equals to
        defaultBuyerRfqPricesDetailFiltering("tariff.equals=" + DEFAULT_TARIFF, "tariff.equals=" + UPDATED_TARIFF);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByTariffIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where tariff in
        defaultBuyerRfqPricesDetailFiltering("tariff.in=" + DEFAULT_TARIFF + "," + UPDATED_TARIFF, "tariff.in=" + UPDATED_TARIFF);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByTariffIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where tariff is not null
        defaultBuyerRfqPricesDetailFiltering("tariff.specified=true", "tariff.specified=false");
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByTariffIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where tariff is greater than or equal to
        defaultBuyerRfqPricesDetailFiltering("tariff.greaterThanOrEqual=" + DEFAULT_TARIFF, "tariff.greaterThanOrEqual=" + UPDATED_TARIFF);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByTariffIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where tariff is less than or equal to
        defaultBuyerRfqPricesDetailFiltering("tariff.lessThanOrEqual=" + DEFAULT_TARIFF, "tariff.lessThanOrEqual=" + SMALLER_TARIFF);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByTariffIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where tariff is less than
        defaultBuyerRfqPricesDetailFiltering("tariff.lessThan=" + UPDATED_TARIFF, "tariff.lessThan=" + DEFAULT_TARIFF);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByTariffIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where tariff is greater than
        defaultBuyerRfqPricesDetailFiltering("tariff.greaterThan=" + SMALLER_TARIFF, "tariff.greaterThan=" + DEFAULT_TARIFF);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByCalculatedTariffsCostIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where calculatedTariffsCost equals to
        defaultBuyerRfqPricesDetailFiltering(
            "calculatedTariffsCost.equals=" + DEFAULT_CALCULATED_TARIFFS_COST,
            "calculatedTariffsCost.equals=" + UPDATED_CALCULATED_TARIFFS_COST
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByCalculatedTariffsCostIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where calculatedTariffsCost in
        defaultBuyerRfqPricesDetailFiltering(
            "calculatedTariffsCost.in=" + DEFAULT_CALCULATED_TARIFFS_COST + "," + UPDATED_CALCULATED_TARIFFS_COST,
            "calculatedTariffsCost.in=" + UPDATED_CALCULATED_TARIFFS_COST
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByCalculatedTariffsCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where calculatedTariffsCost is not null
        defaultBuyerRfqPricesDetailFiltering("calculatedTariffsCost.specified=true", "calculatedTariffsCost.specified=false");
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByCalculatedTariffsCostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where calculatedTariffsCost is greater than or equal to
        defaultBuyerRfqPricesDetailFiltering(
            "calculatedTariffsCost.greaterThanOrEqual=" + DEFAULT_CALCULATED_TARIFFS_COST,
            "calculatedTariffsCost.greaterThanOrEqual=" + UPDATED_CALCULATED_TARIFFS_COST
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByCalculatedTariffsCostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where calculatedTariffsCost is less than or equal to
        defaultBuyerRfqPricesDetailFiltering(
            "calculatedTariffsCost.lessThanOrEqual=" + DEFAULT_CALCULATED_TARIFFS_COST,
            "calculatedTariffsCost.lessThanOrEqual=" + SMALLER_CALCULATED_TARIFFS_COST
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByCalculatedTariffsCostIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where calculatedTariffsCost is less than
        defaultBuyerRfqPricesDetailFiltering(
            "calculatedTariffsCost.lessThan=" + UPDATED_CALCULATED_TARIFFS_COST,
            "calculatedTariffsCost.lessThan=" + DEFAULT_CALCULATED_TARIFFS_COST
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByCalculatedTariffsCostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where calculatedTariffsCost is greater than
        defaultBuyerRfqPricesDetailFiltering(
            "calculatedTariffsCost.greaterThan=" + SMALLER_CALCULATED_TARIFFS_COST,
            "calculatedTariffsCost.greaterThan=" + DEFAULT_CALCULATED_TARIFFS_COST
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByTotalCumberlandPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where totalCumberlandPrice equals to
        defaultBuyerRfqPricesDetailFiltering(
            "totalCumberlandPrice.equals=" + DEFAULT_TOTAL_CUMBERLAND_PRICE,
            "totalCumberlandPrice.equals=" + UPDATED_TOTAL_CUMBERLAND_PRICE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByTotalCumberlandPriceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where totalCumberlandPrice in
        defaultBuyerRfqPricesDetailFiltering(
            "totalCumberlandPrice.in=" + DEFAULT_TOTAL_CUMBERLAND_PRICE + "," + UPDATED_TOTAL_CUMBERLAND_PRICE,
            "totalCumberlandPrice.in=" + UPDATED_TOTAL_CUMBERLAND_PRICE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByTotalCumberlandPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where totalCumberlandPrice is not null
        defaultBuyerRfqPricesDetailFiltering("totalCumberlandPrice.specified=true", "totalCumberlandPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByTotalCumberlandPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where totalCumberlandPrice is greater than or equal to
        defaultBuyerRfqPricesDetailFiltering(
            "totalCumberlandPrice.greaterThanOrEqual=" + DEFAULT_TOTAL_CUMBERLAND_PRICE,
            "totalCumberlandPrice.greaterThanOrEqual=" + UPDATED_TOTAL_CUMBERLAND_PRICE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByTotalCumberlandPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where totalCumberlandPrice is less than or equal to
        defaultBuyerRfqPricesDetailFiltering(
            "totalCumberlandPrice.lessThanOrEqual=" + DEFAULT_TOTAL_CUMBERLAND_PRICE,
            "totalCumberlandPrice.lessThanOrEqual=" + SMALLER_TOTAL_CUMBERLAND_PRICE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByTotalCumberlandPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where totalCumberlandPrice is less than
        defaultBuyerRfqPricesDetailFiltering(
            "totalCumberlandPrice.lessThan=" + UPDATED_TOTAL_CUMBERLAND_PRICE,
            "totalCumberlandPrice.lessThan=" + DEFAULT_TOTAL_CUMBERLAND_PRICE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByTotalCumberlandPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where totalCumberlandPrice is greater than
        defaultBuyerRfqPricesDetailFiltering(
            "totalCumberlandPrice.greaterThan=" + SMALLER_TOTAL_CUMBERLAND_PRICE,
            "totalCumberlandPrice.greaterThan=" + DEFAULT_TOTAL_CUMBERLAND_PRICE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByLandedPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where landedPrice equals to
        defaultBuyerRfqPricesDetailFiltering("landedPrice.equals=" + DEFAULT_LANDED_PRICE, "landedPrice.equals=" + UPDATED_LANDED_PRICE);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByLandedPriceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where landedPrice in
        defaultBuyerRfqPricesDetailFiltering(
            "landedPrice.in=" + DEFAULT_LANDED_PRICE + "," + UPDATED_LANDED_PRICE,
            "landedPrice.in=" + UPDATED_LANDED_PRICE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByLandedPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where landedPrice is not null
        defaultBuyerRfqPricesDetailFiltering("landedPrice.specified=true", "landedPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByLandedPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where landedPrice is greater than or equal to
        defaultBuyerRfqPricesDetailFiltering(
            "landedPrice.greaterThanOrEqual=" + DEFAULT_LANDED_PRICE,
            "landedPrice.greaterThanOrEqual=" + UPDATED_LANDED_PRICE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByLandedPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where landedPrice is less than or equal to
        defaultBuyerRfqPricesDetailFiltering(
            "landedPrice.lessThanOrEqual=" + DEFAULT_LANDED_PRICE,
            "landedPrice.lessThanOrEqual=" + SMALLER_LANDED_PRICE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByLandedPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where landedPrice is less than
        defaultBuyerRfqPricesDetailFiltering(
            "landedPrice.lessThan=" + UPDATED_LANDED_PRICE,
            "landedPrice.lessThan=" + DEFAULT_LANDED_PRICE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByLandedPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where landedPrice is greater than
        defaultBuyerRfqPricesDetailFiltering(
            "landedPrice.greaterThan=" + SMALLER_LANDED_PRICE,
            "landedPrice.greaterThan=" + DEFAULT_LANDED_PRICE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByApprovalToGainIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where approvalToGain equals to
        defaultBuyerRfqPricesDetailFiltering(
            "approvalToGain.equals=" + DEFAULT_APPROVAL_TO_GAIN,
            "approvalToGain.equals=" + UPDATED_APPROVAL_TO_GAIN
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByApprovalToGainIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where approvalToGain in
        defaultBuyerRfqPricesDetailFiltering(
            "approvalToGain.in=" + DEFAULT_APPROVAL_TO_GAIN + "," + UPDATED_APPROVAL_TO_GAIN,
            "approvalToGain.in=" + UPDATED_APPROVAL_TO_GAIN
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByApprovalToGainIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where approvalToGain is not null
        defaultBuyerRfqPricesDetailFiltering("approvalToGain.specified=true", "approvalToGain.specified=false");
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByApprovalToGainIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where approvalToGain is greater than or equal to
        defaultBuyerRfqPricesDetailFiltering(
            "approvalToGain.greaterThanOrEqual=" + DEFAULT_APPROVAL_TO_GAIN,
            "approvalToGain.greaterThanOrEqual=" + UPDATED_APPROVAL_TO_GAIN
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByApprovalToGainIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where approvalToGain is less than or equal to
        defaultBuyerRfqPricesDetailFiltering(
            "approvalToGain.lessThanOrEqual=" + DEFAULT_APPROVAL_TO_GAIN,
            "approvalToGain.lessThanOrEqual=" + SMALLER_APPROVAL_TO_GAIN
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByApprovalToGainIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where approvalToGain is less than
        defaultBuyerRfqPricesDetailFiltering(
            "approvalToGain.lessThan=" + UPDATED_APPROVAL_TO_GAIN,
            "approvalToGain.lessThan=" + DEFAULT_APPROVAL_TO_GAIN
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByApprovalToGainIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where approvalToGain is greater than
        defaultBuyerRfqPricesDetailFiltering(
            "approvalToGain.greaterThan=" + SMALLER_APPROVAL_TO_GAIN,
            "approvalToGain.greaterThan=" + DEFAULT_APPROVAL_TO_GAIN
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByMoldSizeMoldWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where moldSizeMoldWeight equals to
        defaultBuyerRfqPricesDetailFiltering(
            "moldSizeMoldWeight.equals=" + DEFAULT_MOLD_SIZE_MOLD_WEIGHT,
            "moldSizeMoldWeight.equals=" + UPDATED_MOLD_SIZE_MOLD_WEIGHT
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByMoldSizeMoldWeightIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where moldSizeMoldWeight in
        defaultBuyerRfqPricesDetailFiltering(
            "moldSizeMoldWeight.in=" + DEFAULT_MOLD_SIZE_MOLD_WEIGHT + "," + UPDATED_MOLD_SIZE_MOLD_WEIGHT,
            "moldSizeMoldWeight.in=" + UPDATED_MOLD_SIZE_MOLD_WEIGHT
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByMoldSizeMoldWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where moldSizeMoldWeight is not null
        defaultBuyerRfqPricesDetailFiltering("moldSizeMoldWeight.specified=true", "moldSizeMoldWeight.specified=false");
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByMoldSizeMoldWeightContainsSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where moldSizeMoldWeight contains
        defaultBuyerRfqPricesDetailFiltering(
            "moldSizeMoldWeight.contains=" + DEFAULT_MOLD_SIZE_MOLD_WEIGHT,
            "moldSizeMoldWeight.contains=" + UPDATED_MOLD_SIZE_MOLD_WEIGHT
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByMoldSizeMoldWeightNotContainsSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where moldSizeMoldWeight does not contain
        defaultBuyerRfqPricesDetailFiltering(
            "moldSizeMoldWeight.doesNotContain=" + UPDATED_MOLD_SIZE_MOLD_WEIGHT,
            "moldSizeMoldWeight.doesNotContain=" + DEFAULT_MOLD_SIZE_MOLD_WEIGHT
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByMoldLifeExpectancyIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where moldLifeExpectancy equals to
        defaultBuyerRfqPricesDetailFiltering(
            "moldLifeExpectancy.equals=" + DEFAULT_MOLD_LIFE_EXPECTANCY,
            "moldLifeExpectancy.equals=" + UPDATED_MOLD_LIFE_EXPECTANCY
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByMoldLifeExpectancyIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where moldLifeExpectancy in
        defaultBuyerRfqPricesDetailFiltering(
            "moldLifeExpectancy.in=" + DEFAULT_MOLD_LIFE_EXPECTANCY + "," + UPDATED_MOLD_LIFE_EXPECTANCY,
            "moldLifeExpectancy.in=" + UPDATED_MOLD_LIFE_EXPECTANCY
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByMoldLifeExpectancyIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where moldLifeExpectancy is not null
        defaultBuyerRfqPricesDetailFiltering("moldLifeExpectancy.specified=true", "moldLifeExpectancy.specified=false");
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByMoldLifeExpectancyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where moldLifeExpectancy is greater than or equal to
        defaultBuyerRfqPricesDetailFiltering(
            "moldLifeExpectancy.greaterThanOrEqual=" + DEFAULT_MOLD_LIFE_EXPECTANCY,
            "moldLifeExpectancy.greaterThanOrEqual=" + UPDATED_MOLD_LIFE_EXPECTANCY
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByMoldLifeExpectancyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where moldLifeExpectancy is less than or equal to
        defaultBuyerRfqPricesDetailFiltering(
            "moldLifeExpectancy.lessThanOrEqual=" + DEFAULT_MOLD_LIFE_EXPECTANCY,
            "moldLifeExpectancy.lessThanOrEqual=" + SMALLER_MOLD_LIFE_EXPECTANCY
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByMoldLifeExpectancyIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where moldLifeExpectancy is less than
        defaultBuyerRfqPricesDetailFiltering(
            "moldLifeExpectancy.lessThan=" + UPDATED_MOLD_LIFE_EXPECTANCY,
            "moldLifeExpectancy.lessThan=" + DEFAULT_MOLD_LIFE_EXPECTANCY
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByMoldLifeExpectancyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where moldLifeExpectancy is greater than
        defaultBuyerRfqPricesDetailFiltering(
            "moldLifeExpectancy.greaterThan=" + SMALLER_MOLD_LIFE_EXPECTANCY,
            "moldLifeExpectancy.greaterThan=" + DEFAULT_MOLD_LIFE_EXPECTANCY
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByTotalCostComparisonIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where totalCostComparison equals to
        defaultBuyerRfqPricesDetailFiltering(
            "totalCostComparison.equals=" + DEFAULT_TOTAL_COST_COMPARISON,
            "totalCostComparison.equals=" + UPDATED_TOTAL_COST_COMPARISON
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByTotalCostComparisonIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where totalCostComparison in
        defaultBuyerRfqPricesDetailFiltering(
            "totalCostComparison.in=" + DEFAULT_TOTAL_COST_COMPARISON + "," + UPDATED_TOTAL_COST_COMPARISON,
            "totalCostComparison.in=" + UPDATED_TOTAL_COST_COMPARISON
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByTotalCostComparisonIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where totalCostComparison is not null
        defaultBuyerRfqPricesDetailFiltering("totalCostComparison.specified=true", "totalCostComparison.specified=false");
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByTotalCostComparisonIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where totalCostComparison is greater than or equal to
        defaultBuyerRfqPricesDetailFiltering(
            "totalCostComparison.greaterThanOrEqual=" + DEFAULT_TOTAL_COST_COMPARISON,
            "totalCostComparison.greaterThanOrEqual=" + UPDATED_TOTAL_COST_COMPARISON
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByTotalCostComparisonIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where totalCostComparison is less than or equal to
        defaultBuyerRfqPricesDetailFiltering(
            "totalCostComparison.lessThanOrEqual=" + DEFAULT_TOTAL_COST_COMPARISON,
            "totalCostComparison.lessThanOrEqual=" + SMALLER_TOTAL_COST_COMPARISON
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByTotalCostComparisonIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where totalCostComparison is less than
        defaultBuyerRfqPricesDetailFiltering(
            "totalCostComparison.lessThan=" + UPDATED_TOTAL_COST_COMPARISON,
            "totalCostComparison.lessThan=" + DEFAULT_TOTAL_COST_COMPARISON
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByTotalCostComparisonIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where totalCostComparison is greater than
        defaultBuyerRfqPricesDetailFiltering(
            "totalCostComparison.greaterThan=" + SMALLER_TOTAL_COST_COMPARISON,
            "totalCostComparison.greaterThan=" + DEFAULT_TOTAL_COST_COMPARISON
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByLengthIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where length equals to
        defaultBuyerRfqPricesDetailFiltering("length.equals=" + DEFAULT_LENGTH, "length.equals=" + UPDATED_LENGTH);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByLengthIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where length in
        defaultBuyerRfqPricesDetailFiltering("length.in=" + DEFAULT_LENGTH + "," + UPDATED_LENGTH, "length.in=" + UPDATED_LENGTH);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByLengthIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where length is not null
        defaultBuyerRfqPricesDetailFiltering("length.specified=true", "length.specified=false");
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByLengthContainsSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where length contains
        defaultBuyerRfqPricesDetailFiltering("length.contains=" + DEFAULT_LENGTH, "length.contains=" + UPDATED_LENGTH);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByLengthNotContainsSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where length does not contain
        defaultBuyerRfqPricesDetailFiltering("length.doesNotContain=" + UPDATED_LENGTH, "length.doesNotContain=" + DEFAULT_LENGTH);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByWidthIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where width equals to
        defaultBuyerRfqPricesDetailFiltering("width.equals=" + DEFAULT_WIDTH, "width.equals=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByWidthIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where width in
        defaultBuyerRfqPricesDetailFiltering("width.in=" + DEFAULT_WIDTH + "," + UPDATED_WIDTH, "width.in=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByWidthIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where width is not null
        defaultBuyerRfqPricesDetailFiltering("width.specified=true", "width.specified=false");
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByWidthContainsSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where width contains
        defaultBuyerRfqPricesDetailFiltering("width.contains=" + DEFAULT_WIDTH, "width.contains=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByWidthNotContainsSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where width does not contain
        defaultBuyerRfqPricesDetailFiltering("width.doesNotContain=" + UPDATED_WIDTH, "width.doesNotContain=" + DEFAULT_WIDTH);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByGuageIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where guage equals to
        defaultBuyerRfqPricesDetailFiltering("guage.equals=" + DEFAULT_GUAGE, "guage.equals=" + UPDATED_GUAGE);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByGuageIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where guage in
        defaultBuyerRfqPricesDetailFiltering("guage.in=" + DEFAULT_GUAGE + "," + UPDATED_GUAGE, "guage.in=" + UPDATED_GUAGE);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByGuageIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where guage is not null
        defaultBuyerRfqPricesDetailFiltering("guage.specified=true", "guage.specified=false");
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByGuageContainsSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where guage contains
        defaultBuyerRfqPricesDetailFiltering("guage.contains=" + DEFAULT_GUAGE, "guage.contains=" + UPDATED_GUAGE);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByGuageNotContainsSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where guage does not contain
        defaultBuyerRfqPricesDetailFiltering("guage.doesNotContain=" + UPDATED_GUAGE, "guage.doesNotContain=" + DEFAULT_GUAGE);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByToleranceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where tolerance equals to
        defaultBuyerRfqPricesDetailFiltering("tolerance.equals=" + DEFAULT_TOLERANCE, "tolerance.equals=" + UPDATED_TOLERANCE);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByToleranceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where tolerance in
        defaultBuyerRfqPricesDetailFiltering(
            "tolerance.in=" + DEFAULT_TOLERANCE + "," + UPDATED_TOLERANCE,
            "tolerance.in=" + UPDATED_TOLERANCE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByToleranceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where tolerance is not null
        defaultBuyerRfqPricesDetailFiltering("tolerance.specified=true", "tolerance.specified=false");
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByToleranceContainsSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where tolerance contains
        defaultBuyerRfqPricesDetailFiltering("tolerance.contains=" + DEFAULT_TOLERANCE, "tolerance.contains=" + UPDATED_TOLERANCE);
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByToleranceNotContainsSomething() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        // Get all the buyerRfqPricesDetailList where tolerance does not contain
        defaultBuyerRfqPricesDetailFiltering(
            "tolerance.doesNotContain=" + UPDATED_TOLERANCE,
            "tolerance.doesNotContain=" + DEFAULT_TOLERANCE
        );
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByRfqDetailIsEqualToSomething() throws Exception {
        RfqDetail rfqDetail;
        if (TestUtil.findAll(em, RfqDetail.class).isEmpty()) {
            buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);
            rfqDetail = RfqDetailResourceIT.createEntity();
        } else {
            rfqDetail = TestUtil.findAll(em, RfqDetail.class).get(0);
        }
        em.persist(rfqDetail);
        em.flush();
        buyerRfqPricesDetail.setRfqDetail(rfqDetail);
        buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);
        Long rfqDetailId = rfqDetail.getId();
        // Get all the buyerRfqPricesDetailList where rfqDetail equals to rfqDetailId
        defaultBuyerRfqPricesDetailShouldBeFound("rfqDetailId.equals=" + rfqDetailId);

        // Get all the buyerRfqPricesDetailList where rfqDetail equals to (rfqDetailId + 1)
        defaultBuyerRfqPricesDetailShouldNotBeFound("rfqDetailId.equals=" + (rfqDetailId + 1));
    }

    @Test
    @Transactional
    void getAllBuyerRfqPricesDetailsByVendorIsEqualToSomething() throws Exception {
        VendorProfile vendor;
        if (TestUtil.findAll(em, VendorProfile.class).isEmpty()) {
            buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);
            vendor = VendorProfileResourceIT.createEntity();
        } else {
            vendor = TestUtil.findAll(em, VendorProfile.class).get(0);
        }
        em.persist(vendor);
        em.flush();
        buyerRfqPricesDetail.setVendor(vendor);
        buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);
        Long vendorId = vendor.getId();
        // Get all the buyerRfqPricesDetailList where vendor equals to vendorId
        defaultBuyerRfqPricesDetailShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the buyerRfqPricesDetailList where vendor equals to (vendorId + 1)
        defaultBuyerRfqPricesDetailShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }

    private void defaultBuyerRfqPricesDetailFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultBuyerRfqPricesDetailShouldBeFound(shouldBeFound);
        defaultBuyerRfqPricesDetailShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBuyerRfqPricesDetailShouldBeFound(String filter) throws Exception {
        restBuyerRfqPricesDetailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(buyerRfqPricesDetail.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].line").value(hasItem(DEFAULT_LINE)))
            .andExpect(jsonPath("$.[*].materialId").value(hasItem(DEFAULT_MATERIAL_ID)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].estUnitPrice").value(hasItem(sameNumber(DEFAULT_EST_UNIT_PRICE))))
            .andExpect(jsonPath("$.[*].actUnitPrice").value(hasItem(sameNumber(DEFAULT_ACT_UNIT_PRICE))))
            .andExpect(jsonPath("$.[*].awardFlag").value(hasItem(DEFAULT_AWARD_FLAG)))
            .andExpect(jsonPath("$.[*].quoteId").value(hasItem(DEFAULT_QUOTE_ID)))
            .andExpect(jsonPath("$.[*].receivedDate").value(hasItem(sameInstant(DEFAULT_RECEIVED_DATE))))
            .andExpect(jsonPath("$.[*].leadDays").value(hasItem(sameNumber(DEFAULT_LEAD_DAYS))))
            .andExpect(jsonPath("$.[*].rank").value(hasItem(DEFAULT_RANK)))
            .andExpect(jsonPath("$.[*].splitQuantityFlag").value(hasItem(DEFAULT_SPLIT_QUANTITY_FLAG)))
            .andExpect(jsonPath("$.[*].materialDescription").value(hasItem(DEFAULT_MATERIAL_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED)))
            .andExpect(jsonPath("$.[*].inviteRaFlag").value(hasItem(DEFAULT_INVITE_RA_FLAG)))
            .andExpect(jsonPath("$.[*].awardAcceptancesDate").value(hasItem(sameInstant(DEFAULT_AWARD_ACCEPTANCES_DATE))))
            .andExpect(jsonPath("$.[*].orderAcceptancesDate").value(hasItem(sameInstant(DEFAULT_ORDER_ACCEPTANCES_DATE))))
            .andExpect(jsonPath("$.[*].orderAcceptancesFlag").value(hasItem(DEFAULT_ORDER_ACCEPTANCES_FLAG)))
            .andExpect(jsonPath("$.[*].materialName").value(hasItem(DEFAULT_MATERIAL_NAME)))
            .andExpect(jsonPath("$.[*].materialImage").value(hasItem(DEFAULT_MATERIAL_IMAGE)))
            .andExpect(jsonPath("$.[*].technicalScrutinyFlag").value(hasItem(DEFAULT_TECHNICAL_SCRUTINY_FLAG)))
            .andExpect(jsonPath("$.[*].vendorAttributes").value(hasItem(DEFAULT_VENDOR_ATTRIBUTES)))
            .andExpect(jsonPath("$.[*].marginFactor").value(hasItem(sameNumber(DEFAULT_MARGIN_FACTOR))))
            .andExpect(jsonPath("$.[*].fob").value(hasItem(sameNumber(DEFAULT_FOB))))
            .andExpect(jsonPath("$.[*].shippingFactor").value(hasItem(sameNumber(DEFAULT_SHIPPING_FACTOR))))
            .andExpect(jsonPath("$.[*].freight").value(hasItem(sameNumber(DEFAULT_FREIGHT))))
            .andExpect(jsonPath("$.[*].finalShipmentCost").value(hasItem(sameNumber(DEFAULT_FINAL_SHIPMENT_COST))))
            .andExpect(jsonPath("$.[*].tariff").value(hasItem(sameNumber(DEFAULT_TARIFF))))
            .andExpect(jsonPath("$.[*].calculatedTariffsCost").value(hasItem(sameNumber(DEFAULT_CALCULATED_TARIFFS_COST))))
            .andExpect(jsonPath("$.[*].totalCumberlandPrice").value(hasItem(sameNumber(DEFAULT_TOTAL_CUMBERLAND_PRICE))))
            .andExpect(jsonPath("$.[*].landedPrice").value(hasItem(sameNumber(DEFAULT_LANDED_PRICE))))
            .andExpect(jsonPath("$.[*].approvalToGain").value(hasItem(sameNumber(DEFAULT_APPROVAL_TO_GAIN))))
            .andExpect(jsonPath("$.[*].moldSizeMoldWeight").value(hasItem(DEFAULT_MOLD_SIZE_MOLD_WEIGHT)))
            .andExpect(jsonPath("$.[*].moldLifeExpectancy").value(hasItem(sameNumber(DEFAULT_MOLD_LIFE_EXPECTANCY))))
            .andExpect(jsonPath("$.[*].totalCostComparison").value(hasItem(sameNumber(DEFAULT_TOTAL_COST_COMPARISON))))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH)))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH)))
            .andExpect(jsonPath("$.[*].guage").value(hasItem(DEFAULT_GUAGE)))
            .andExpect(jsonPath("$.[*].tolerance").value(hasItem(DEFAULT_TOLERANCE)));

        // Check, that the count call also returns 1
        restBuyerRfqPricesDetailMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBuyerRfqPricesDetailShouldNotBeFound(String filter) throws Exception {
        restBuyerRfqPricesDetailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBuyerRfqPricesDetailMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBuyerRfqPricesDetail() throws Exception {
        // Get the buyerRfqPricesDetail
        restBuyerRfqPricesDetailMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBuyerRfqPricesDetail() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        long databaseSizeBeforeUpdate = getRepositoryCount();
        buyerRfqPricesDetailSearchRepository.save(buyerRfqPricesDetail);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(buyerRfqPricesDetailSearchRepository.findAll());

        // Update the buyerRfqPricesDetail
        BuyerRfqPricesDetail updatedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository
            .findById(buyerRfqPricesDetail.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedBuyerRfqPricesDetail are not directly saved in db
        em.detach(updatedBuyerRfqPricesDetail);
        updatedBuyerRfqPricesDetail
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .line(UPDATED_LINE)
            .materialId(UPDATED_MATERIAL_ID)
            .quantity(UPDATED_QUANTITY)
            .estUnitPrice(UPDATED_EST_UNIT_PRICE)
            .actUnitPrice(UPDATED_ACT_UNIT_PRICE)
            .awardFlag(UPDATED_AWARD_FLAG)
            .quoteId(UPDATED_QUOTE_ID)
            .receivedDate(UPDATED_RECEIVED_DATE)
            .leadDays(UPDATED_LEAD_DAYS)
            .rank(UPDATED_RANK)
            .splitQuantityFlag(UPDATED_SPLIT_QUANTITY_FLAG)
            .materialDescription(UPDATED_MATERIAL_DESCRIPTION)
            .lastUpdated(UPDATED_LAST_UPDATED)
            .inviteRaFlag(UPDATED_INVITE_RA_FLAG)
            .awardAcceptancesDate(UPDATED_AWARD_ACCEPTANCES_DATE)
            .orderAcceptancesDate(UPDATED_ORDER_ACCEPTANCES_DATE)
            .orderAcceptancesFlag(UPDATED_ORDER_ACCEPTANCES_FLAG)
            .materialName(UPDATED_MATERIAL_NAME)
            .materialImage(UPDATED_MATERIAL_IMAGE)
            .technicalScrutinyFlag(UPDATED_TECHNICAL_SCRUTINY_FLAG)
            .vendorAttributes(UPDATED_VENDOR_ATTRIBUTES)
            .marginFactor(UPDATED_MARGIN_FACTOR)
            .fob(UPDATED_FOB)
            .shippingFactor(UPDATED_SHIPPING_FACTOR)
            .freight(UPDATED_FREIGHT)
            .finalShipmentCost(UPDATED_FINAL_SHIPMENT_COST)
            .tariff(UPDATED_TARIFF)
            .calculatedTariffsCost(UPDATED_CALCULATED_TARIFFS_COST)
            .totalCumberlandPrice(UPDATED_TOTAL_CUMBERLAND_PRICE)
            .landedPrice(UPDATED_LANDED_PRICE)
            .approvalToGain(UPDATED_APPROVAL_TO_GAIN)
            .moldSizeMoldWeight(UPDATED_MOLD_SIZE_MOLD_WEIGHT)
            .moldLifeExpectancy(UPDATED_MOLD_LIFE_EXPECTANCY)
            .totalCostComparison(UPDATED_TOTAL_COST_COMPARISON)
            .length(UPDATED_LENGTH)
            .width(UPDATED_WIDTH)
            .guage(UPDATED_GUAGE)
            .tolerance(UPDATED_TOLERANCE);
        BuyerRfqPricesDetailDTO buyerRfqPricesDetailDTO = buyerRfqPricesDetailMapper.toDto(updatedBuyerRfqPricesDetail);

        restBuyerRfqPricesDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, buyerRfqPricesDetailDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(buyerRfqPricesDetailDTO))
            )
            .andExpect(status().isOk());

        // Validate the BuyerRfqPricesDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBuyerRfqPricesDetailToMatchAllProperties(updatedBuyerRfqPricesDetail);

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(buyerRfqPricesDetailSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<BuyerRfqPricesDetail> buyerRfqPricesDetailSearchList = Streamable.of(
                    buyerRfqPricesDetailSearchRepository.findAll()
                ).toList();
                BuyerRfqPricesDetail testBuyerRfqPricesDetailSearch = buyerRfqPricesDetailSearchList.get(searchDatabaseSizeAfter - 1);

                assertBuyerRfqPricesDetailAllPropertiesEquals(testBuyerRfqPricesDetailSearch, updatedBuyerRfqPricesDetail);
            });
    }

    @Test
    @Transactional
    void putNonExistingBuyerRfqPricesDetail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(buyerRfqPricesDetailSearchRepository.findAll());
        buyerRfqPricesDetail.setId(longCount.incrementAndGet());

        // Create the BuyerRfqPricesDetail
        BuyerRfqPricesDetailDTO buyerRfqPricesDetailDTO = buyerRfqPricesDetailMapper.toDto(buyerRfqPricesDetail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBuyerRfqPricesDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, buyerRfqPricesDetailDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(buyerRfqPricesDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuyerRfqPricesDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(buyerRfqPricesDetailSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchBuyerRfqPricesDetail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(buyerRfqPricesDetailSearchRepository.findAll());
        buyerRfqPricesDetail.setId(longCount.incrementAndGet());

        // Create the BuyerRfqPricesDetail
        BuyerRfqPricesDetailDTO buyerRfqPricesDetailDTO = buyerRfqPricesDetailMapper.toDto(buyerRfqPricesDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuyerRfqPricesDetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(buyerRfqPricesDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuyerRfqPricesDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(buyerRfqPricesDetailSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBuyerRfqPricesDetail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(buyerRfqPricesDetailSearchRepository.findAll());
        buyerRfqPricesDetail.setId(longCount.incrementAndGet());

        // Create the BuyerRfqPricesDetail
        BuyerRfqPricesDetailDTO buyerRfqPricesDetailDTO = buyerRfqPricesDetailMapper.toDto(buyerRfqPricesDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuyerRfqPricesDetailMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(buyerRfqPricesDetailDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BuyerRfqPricesDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(buyerRfqPricesDetailSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateBuyerRfqPricesDetailWithPatch() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the buyerRfqPricesDetail using partial update
        BuyerRfqPricesDetail partialUpdatedBuyerRfqPricesDetail = new BuyerRfqPricesDetail();
        partialUpdatedBuyerRfqPricesDetail.setId(buyerRfqPricesDetail.getId());

        partialUpdatedBuyerRfqPricesDetail
            .uid(UPDATED_UID)
            .line(UPDATED_LINE)
            .materialId(UPDATED_MATERIAL_ID)
            .quantity(UPDATED_QUANTITY)
            .estUnitPrice(UPDATED_EST_UNIT_PRICE)
            .receivedDate(UPDATED_RECEIVED_DATE)
            .leadDays(UPDATED_LEAD_DAYS)
            .materialDescription(UPDATED_MATERIAL_DESCRIPTION)
            .inviteRaFlag(UPDATED_INVITE_RA_FLAG)
            .orderAcceptancesDate(UPDATED_ORDER_ACCEPTANCES_DATE)
            .orderAcceptancesFlag(UPDATED_ORDER_ACCEPTANCES_FLAG)
            .materialName(UPDATED_MATERIAL_NAME)
            .materialImage(UPDATED_MATERIAL_IMAGE)
            .technicalScrutinyFlag(UPDATED_TECHNICAL_SCRUTINY_FLAG)
            .vendorAttributes(UPDATED_VENDOR_ATTRIBUTES)
            .shippingFactor(UPDATED_SHIPPING_FACTOR)
            .freight(UPDATED_FREIGHT)
            .finalShipmentCost(UPDATED_FINAL_SHIPMENT_COST)
            .calculatedTariffsCost(UPDATED_CALCULATED_TARIFFS_COST)
            .totalCumberlandPrice(UPDATED_TOTAL_CUMBERLAND_PRICE)
            .approvalToGain(UPDATED_APPROVAL_TO_GAIN)
            .moldSizeMoldWeight(UPDATED_MOLD_SIZE_MOLD_WEIGHT)
            .moldLifeExpectancy(UPDATED_MOLD_LIFE_EXPECTANCY)
            .length(UPDATED_LENGTH);

        restBuyerRfqPricesDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBuyerRfqPricesDetail.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBuyerRfqPricesDetail))
            )
            .andExpect(status().isOk());

        // Validate the BuyerRfqPricesDetail in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBuyerRfqPricesDetailUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBuyerRfqPricesDetail, buyerRfqPricesDetail),
            getPersistedBuyerRfqPricesDetail(buyerRfqPricesDetail)
        );
    }

    @Test
    @Transactional
    void fullUpdateBuyerRfqPricesDetailWithPatch() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the buyerRfqPricesDetail using partial update
        BuyerRfqPricesDetail partialUpdatedBuyerRfqPricesDetail = new BuyerRfqPricesDetail();
        partialUpdatedBuyerRfqPricesDetail.setId(buyerRfqPricesDetail.getId());

        partialUpdatedBuyerRfqPricesDetail
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .line(UPDATED_LINE)
            .materialId(UPDATED_MATERIAL_ID)
            .quantity(UPDATED_QUANTITY)
            .estUnitPrice(UPDATED_EST_UNIT_PRICE)
            .actUnitPrice(UPDATED_ACT_UNIT_PRICE)
            .awardFlag(UPDATED_AWARD_FLAG)
            .quoteId(UPDATED_QUOTE_ID)
            .receivedDate(UPDATED_RECEIVED_DATE)
            .leadDays(UPDATED_LEAD_DAYS)
            .rank(UPDATED_RANK)
            .splitQuantityFlag(UPDATED_SPLIT_QUANTITY_FLAG)
            .materialDescription(UPDATED_MATERIAL_DESCRIPTION)
            .lastUpdated(UPDATED_LAST_UPDATED)
            .inviteRaFlag(UPDATED_INVITE_RA_FLAG)
            .awardAcceptancesDate(UPDATED_AWARD_ACCEPTANCES_DATE)
            .orderAcceptancesDate(UPDATED_ORDER_ACCEPTANCES_DATE)
            .orderAcceptancesFlag(UPDATED_ORDER_ACCEPTANCES_FLAG)
            .materialName(UPDATED_MATERIAL_NAME)
            .materialImage(UPDATED_MATERIAL_IMAGE)
            .technicalScrutinyFlag(UPDATED_TECHNICAL_SCRUTINY_FLAG)
            .vendorAttributes(UPDATED_VENDOR_ATTRIBUTES)
            .marginFactor(UPDATED_MARGIN_FACTOR)
            .fob(UPDATED_FOB)
            .shippingFactor(UPDATED_SHIPPING_FACTOR)
            .freight(UPDATED_FREIGHT)
            .finalShipmentCost(UPDATED_FINAL_SHIPMENT_COST)
            .tariff(UPDATED_TARIFF)
            .calculatedTariffsCost(UPDATED_CALCULATED_TARIFFS_COST)
            .totalCumberlandPrice(UPDATED_TOTAL_CUMBERLAND_PRICE)
            .landedPrice(UPDATED_LANDED_PRICE)
            .approvalToGain(UPDATED_APPROVAL_TO_GAIN)
            .moldSizeMoldWeight(UPDATED_MOLD_SIZE_MOLD_WEIGHT)
            .moldLifeExpectancy(UPDATED_MOLD_LIFE_EXPECTANCY)
            .totalCostComparison(UPDATED_TOTAL_COST_COMPARISON)
            .length(UPDATED_LENGTH)
            .width(UPDATED_WIDTH)
            .guage(UPDATED_GUAGE)
            .tolerance(UPDATED_TOLERANCE);

        restBuyerRfqPricesDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBuyerRfqPricesDetail.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBuyerRfqPricesDetail))
            )
            .andExpect(status().isOk());

        // Validate the BuyerRfqPricesDetail in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBuyerRfqPricesDetailUpdatableFieldsEquals(
            partialUpdatedBuyerRfqPricesDetail,
            getPersistedBuyerRfqPricesDetail(partialUpdatedBuyerRfqPricesDetail)
        );
    }

    @Test
    @Transactional
    void patchNonExistingBuyerRfqPricesDetail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(buyerRfqPricesDetailSearchRepository.findAll());
        buyerRfqPricesDetail.setId(longCount.incrementAndGet());

        // Create the BuyerRfqPricesDetail
        BuyerRfqPricesDetailDTO buyerRfqPricesDetailDTO = buyerRfqPricesDetailMapper.toDto(buyerRfqPricesDetail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBuyerRfqPricesDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, buyerRfqPricesDetailDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(buyerRfqPricesDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuyerRfqPricesDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(buyerRfqPricesDetailSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBuyerRfqPricesDetail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(buyerRfqPricesDetailSearchRepository.findAll());
        buyerRfqPricesDetail.setId(longCount.incrementAndGet());

        // Create the BuyerRfqPricesDetail
        BuyerRfqPricesDetailDTO buyerRfqPricesDetailDTO = buyerRfqPricesDetailMapper.toDto(buyerRfqPricesDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuyerRfqPricesDetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(buyerRfqPricesDetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuyerRfqPricesDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(buyerRfqPricesDetailSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBuyerRfqPricesDetail() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(buyerRfqPricesDetailSearchRepository.findAll());
        buyerRfqPricesDetail.setId(longCount.incrementAndGet());

        // Create the BuyerRfqPricesDetail
        BuyerRfqPricesDetailDTO buyerRfqPricesDetailDTO = buyerRfqPricesDetailMapper.toDto(buyerRfqPricesDetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuyerRfqPricesDetailMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(buyerRfqPricesDetailDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BuyerRfqPricesDetail in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(buyerRfqPricesDetailSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteBuyerRfqPricesDetail() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);
        buyerRfqPricesDetailRepository.save(buyerRfqPricesDetail);
        buyerRfqPricesDetailSearchRepository.save(buyerRfqPricesDetail);

        long databaseSizeBeforeDelete = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(buyerRfqPricesDetailSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the buyerRfqPricesDetail
        restBuyerRfqPricesDetailMockMvc
            .perform(delete(ENTITY_API_URL_ID, buyerRfqPricesDetail.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(buyerRfqPricesDetailSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchBuyerRfqPricesDetail() throws Exception {
        // Initialize the database
        insertedBuyerRfqPricesDetail = buyerRfqPricesDetailRepository.saveAndFlush(buyerRfqPricesDetail);
        buyerRfqPricesDetailSearchRepository.save(buyerRfqPricesDetail);

        // Search the buyerRfqPricesDetail
        restBuyerRfqPricesDetailMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + buyerRfqPricesDetail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(buyerRfqPricesDetail.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].line").value(hasItem(DEFAULT_LINE)))
            .andExpect(jsonPath("$.[*].materialId").value(hasItem(DEFAULT_MATERIAL_ID)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].estUnitPrice").value(hasItem(sameNumber(DEFAULT_EST_UNIT_PRICE))))
            .andExpect(jsonPath("$.[*].actUnitPrice").value(hasItem(sameNumber(DEFAULT_ACT_UNIT_PRICE))))
            .andExpect(jsonPath("$.[*].awardFlag").value(hasItem(DEFAULT_AWARD_FLAG)))
            .andExpect(jsonPath("$.[*].quoteId").value(hasItem(DEFAULT_QUOTE_ID)))
            .andExpect(jsonPath("$.[*].receivedDate").value(hasItem(sameInstant(DEFAULT_RECEIVED_DATE))))
            .andExpect(jsonPath("$.[*].leadDays").value(hasItem(sameNumber(DEFAULT_LEAD_DAYS))))
            .andExpect(jsonPath("$.[*].rank").value(hasItem(DEFAULT_RANK)))
            .andExpect(jsonPath("$.[*].splitQuantityFlag").value(hasItem(DEFAULT_SPLIT_QUANTITY_FLAG)))
            .andExpect(jsonPath("$.[*].materialDescription").value(hasItem(DEFAULT_MATERIAL_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED)))
            .andExpect(jsonPath("$.[*].inviteRaFlag").value(hasItem(DEFAULT_INVITE_RA_FLAG)))
            .andExpect(jsonPath("$.[*].awardAcceptancesDate").value(hasItem(sameInstant(DEFAULT_AWARD_ACCEPTANCES_DATE))))
            .andExpect(jsonPath("$.[*].orderAcceptancesDate").value(hasItem(sameInstant(DEFAULT_ORDER_ACCEPTANCES_DATE))))
            .andExpect(jsonPath("$.[*].orderAcceptancesFlag").value(hasItem(DEFAULT_ORDER_ACCEPTANCES_FLAG)))
            .andExpect(jsonPath("$.[*].materialName").value(hasItem(DEFAULT_MATERIAL_NAME)))
            .andExpect(jsonPath("$.[*].materialImage").value(hasItem(DEFAULT_MATERIAL_IMAGE)))
            .andExpect(jsonPath("$.[*].technicalScrutinyFlag").value(hasItem(DEFAULT_TECHNICAL_SCRUTINY_FLAG)))
            .andExpect(jsonPath("$.[*].vendorAttributes").value(hasItem(DEFAULT_VENDOR_ATTRIBUTES)))
            .andExpect(jsonPath("$.[*].marginFactor").value(hasItem(sameNumber(DEFAULT_MARGIN_FACTOR))))
            .andExpect(jsonPath("$.[*].fob").value(hasItem(sameNumber(DEFAULT_FOB))))
            .andExpect(jsonPath("$.[*].shippingFactor").value(hasItem(sameNumber(DEFAULT_SHIPPING_FACTOR))))
            .andExpect(jsonPath("$.[*].freight").value(hasItem(sameNumber(DEFAULT_FREIGHT))))
            .andExpect(jsonPath("$.[*].finalShipmentCost").value(hasItem(sameNumber(DEFAULT_FINAL_SHIPMENT_COST))))
            .andExpect(jsonPath("$.[*].tariff").value(hasItem(sameNumber(DEFAULT_TARIFF))))
            .andExpect(jsonPath("$.[*].calculatedTariffsCost").value(hasItem(sameNumber(DEFAULT_CALCULATED_TARIFFS_COST))))
            .andExpect(jsonPath("$.[*].totalCumberlandPrice").value(hasItem(sameNumber(DEFAULT_TOTAL_CUMBERLAND_PRICE))))
            .andExpect(jsonPath("$.[*].landedPrice").value(hasItem(sameNumber(DEFAULT_LANDED_PRICE))))
            .andExpect(jsonPath("$.[*].approvalToGain").value(hasItem(sameNumber(DEFAULT_APPROVAL_TO_GAIN))))
            .andExpect(jsonPath("$.[*].moldSizeMoldWeight").value(hasItem(DEFAULT_MOLD_SIZE_MOLD_WEIGHT)))
            .andExpect(jsonPath("$.[*].moldLifeExpectancy").value(hasItem(sameNumber(DEFAULT_MOLD_LIFE_EXPECTANCY))))
            .andExpect(jsonPath("$.[*].totalCostComparison").value(hasItem(sameNumber(DEFAULT_TOTAL_COST_COMPARISON))))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH)))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH)))
            .andExpect(jsonPath("$.[*].guage").value(hasItem(DEFAULT_GUAGE)))
            .andExpect(jsonPath("$.[*].tolerance").value(hasItem(DEFAULT_TOLERANCE)));
    }

    protected long getRepositoryCount() {
        return buyerRfqPricesDetailRepository.count();
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

    protected BuyerRfqPricesDetail getPersistedBuyerRfqPricesDetail(BuyerRfqPricesDetail buyerRfqPricesDetail) {
        return buyerRfqPricesDetailRepository.findById(buyerRfqPricesDetail.getId()).orElseThrow();
    }

    protected void assertPersistedBuyerRfqPricesDetailToMatchAllProperties(BuyerRfqPricesDetail expectedBuyerRfqPricesDetail) {
        assertBuyerRfqPricesDetailAllPropertiesEquals(
            expectedBuyerRfqPricesDetail,
            getPersistedBuyerRfqPricesDetail(expectedBuyerRfqPricesDetail)
        );
    }

    protected void assertPersistedBuyerRfqPricesDetailToMatchUpdatableProperties(BuyerRfqPricesDetail expectedBuyerRfqPricesDetail) {
        assertBuyerRfqPricesDetailAllUpdatablePropertiesEquals(
            expectedBuyerRfqPricesDetail,
            getPersistedBuyerRfqPricesDetail(expectedBuyerRfqPricesDetail)
        );
    }
}
