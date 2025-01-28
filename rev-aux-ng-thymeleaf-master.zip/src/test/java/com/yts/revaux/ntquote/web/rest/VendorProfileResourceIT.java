package com.yts.revaux.ntquote.web.rest;

import static com.yts.revaux.ntquote.domain.VendorProfileAsserts.*;
import static com.yts.revaux.ntquote.web.rest.TestUtil.createUpdateProxyForBean;
import static com.yts.revaux.ntquote.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yts.revaux.ntquote.IntegrationTest;
import com.yts.revaux.ntquote.domain.VendorProfile;
import com.yts.revaux.ntquote.repository.VendorProfileRepository;
import com.yts.revaux.ntquote.repository.search.VendorProfileSearchRepository;
import com.yts.revaux.ntquote.service.dto.VendorProfileDTO;
import com.yts.revaux.ntquote.service.mapper.VendorProfileMapper;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link VendorProfileResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VendorProfileResourceIT {

    private static final Integer DEFAULT_SR_NO = 1;
    private static final Integer UPDATED_SR_NO = 2;
    private static final Integer SMALLER_SR_NO = 1 - 1;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final String DEFAULT_VENDOR_ID = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_ID = "BBBBBBBBBB";

    private static final String DEFAULT_VENDOR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_ENTRY_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ENTRY_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_ENTRY_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_TRADE_CURRENCY_ID = "AAAAAAAAAA";
    private static final String UPDATED_TRADE_CURRENCY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_2 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_3 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_3 = "BBBBBBBBBB";

    private static final String DEFAULT_MAIL_ID = "AAAAAAAAAA";
    private static final String UPDATED_MAIL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_RATING = "AAAAAAAAAA";
    private static final String UPDATED_RATING = "BBBBBBBBBB";

    private static final Integer DEFAULT_IS_DELETE_FLAG = 1;
    private static final Integer UPDATED_IS_DELETE_FLAG = 2;
    private static final Integer SMALLER_IS_DELETE_FLAG = 1 - 1;

    private static final String DEFAULT_RELATED_BUYER_UID = "AAAAAAAAAA";
    private static final String UPDATED_RELATED_BUYER_UID = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_FLAG = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_FLAG = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vendor-profiles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/vendor-profiles/_search";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VendorProfileRepository vendorProfileRepository;

    @Autowired
    private VendorProfileMapper vendorProfileMapper;

    @Autowired
    private VendorProfileSearchRepository vendorProfileSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVendorProfileMockMvc;

    private VendorProfile vendorProfile;

    private VendorProfile insertedVendorProfile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VendorProfile createEntity() {
        return new VendorProfile()
            .srNo(DEFAULT_SR_NO)
            .uid(DEFAULT_UID)
            .vendorId(DEFAULT_VENDOR_ID)
            .vendorName(DEFAULT_VENDOR_NAME)
            .contact(DEFAULT_CONTACT)
            .entryDate(DEFAULT_ENTRY_DATE)
            .tradeCurrencyId(DEFAULT_TRADE_CURRENCY_ID)
            .address1(DEFAULT_ADDRESS_1)
            .address2(DEFAULT_ADDRESS_2)
            .address3(DEFAULT_ADDRESS_3)
            .mailId(DEFAULT_MAIL_ID)
            .status(DEFAULT_STATUS)
            .rating(DEFAULT_RATING)
            .isDeleteFlag(DEFAULT_IS_DELETE_FLAG)
            .relatedBuyerUid(DEFAULT_RELATED_BUYER_UID)
            .country(DEFAULT_COUNTRY)
            .countryFlag(DEFAULT_COUNTRY_FLAG);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VendorProfile createUpdatedEntity() {
        return new VendorProfile()
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .vendorId(UPDATED_VENDOR_ID)
            .vendorName(UPDATED_VENDOR_NAME)
            .contact(UPDATED_CONTACT)
            .entryDate(UPDATED_ENTRY_DATE)
            .tradeCurrencyId(UPDATED_TRADE_CURRENCY_ID)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .address3(UPDATED_ADDRESS_3)
            .mailId(UPDATED_MAIL_ID)
            .status(UPDATED_STATUS)
            .rating(UPDATED_RATING)
            .isDeleteFlag(UPDATED_IS_DELETE_FLAG)
            .relatedBuyerUid(UPDATED_RELATED_BUYER_UID)
            .country(UPDATED_COUNTRY)
            .countryFlag(UPDATED_COUNTRY_FLAG);
    }

    @BeforeEach
    public void initTest() {
        vendorProfile = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedVendorProfile != null) {
            vendorProfileRepository.delete(insertedVendorProfile);
            vendorProfileSearchRepository.delete(insertedVendorProfile);
            insertedVendorProfile = null;
        }
    }

    @Test
    @Transactional
    void createVendorProfile() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(vendorProfileSearchRepository.findAll());
        // Create the VendorProfile
        VendorProfileDTO vendorProfileDTO = vendorProfileMapper.toDto(vendorProfile);
        var returnedVendorProfileDTO = om.readValue(
            restVendorProfileMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vendorProfileDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            VendorProfileDTO.class
        );

        // Validate the VendorProfile in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedVendorProfile = vendorProfileMapper.toEntity(returnedVendorProfileDTO);
        assertVendorProfileUpdatableFieldsEquals(returnedVendorProfile, getPersistedVendorProfile(returnedVendorProfile));

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(vendorProfileSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });

        insertedVendorProfile = returnedVendorProfile;
    }

    @Test
    @Transactional
    void createVendorProfileWithExistingId() throws Exception {
        // Create the VendorProfile with an existing ID
        vendorProfile.setId(1L);
        VendorProfileDTO vendorProfileDTO = vendorProfileMapper.toDto(vendorProfile);

        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(vendorProfileSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restVendorProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vendorProfileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VendorProfile in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(vendorProfileSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkUidIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(vendorProfileSearchRepository.findAll());
        // set the field null
        vendorProfile.setUid(null);

        // Create the VendorProfile, which fails.
        VendorProfileDTO vendorProfileDTO = vendorProfileMapper.toDto(vendorProfile);

        restVendorProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vendorProfileDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(vendorProfileSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkVendorIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(vendorProfileSearchRepository.findAll());
        // set the field null
        vendorProfile.setVendorId(null);

        // Create the VendorProfile, which fails.
        VendorProfileDTO vendorProfileDTO = vendorProfileMapper.toDto(vendorProfile);

        restVendorProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vendorProfileDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(vendorProfileSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllVendorProfiles() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList
        restVendorProfileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vendorProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].vendorId").value(hasItem(DEFAULT_VENDOR_ID)))
            .andExpect(jsonPath("$.[*].vendorName").value(hasItem(DEFAULT_VENDOR_NAME)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT)))
            .andExpect(jsonPath("$.[*].entryDate").value(hasItem(sameInstant(DEFAULT_ENTRY_DATE))))
            .andExpect(jsonPath("$.[*].tradeCurrencyId").value(hasItem(DEFAULT_TRADE_CURRENCY_ID)))
            .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS_1)))
            .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS_2)))
            .andExpect(jsonPath("$.[*].address3").value(hasItem(DEFAULT_ADDRESS_3)))
            .andExpect(jsonPath("$.[*].mailId").value(hasItem(DEFAULT_MAIL_ID)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)))
            .andExpect(jsonPath("$.[*].isDeleteFlag").value(hasItem(DEFAULT_IS_DELETE_FLAG)))
            .andExpect(jsonPath("$.[*].relatedBuyerUid").value(hasItem(DEFAULT_RELATED_BUYER_UID)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].countryFlag").value(hasItem(DEFAULT_COUNTRY_FLAG)));
    }

    @Test
    @Transactional
    void getVendorProfile() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get the vendorProfile
        restVendorProfileMockMvc
            .perform(get(ENTITY_API_URL_ID, vendorProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vendorProfile.getId().intValue()))
            .andExpect(jsonPath("$.srNo").value(DEFAULT_SR_NO))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.vendorId").value(DEFAULT_VENDOR_ID))
            .andExpect(jsonPath("$.vendorName").value(DEFAULT_VENDOR_NAME))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT))
            .andExpect(jsonPath("$.entryDate").value(sameInstant(DEFAULT_ENTRY_DATE)))
            .andExpect(jsonPath("$.tradeCurrencyId").value(DEFAULT_TRADE_CURRENCY_ID))
            .andExpect(jsonPath("$.address1").value(DEFAULT_ADDRESS_1))
            .andExpect(jsonPath("$.address2").value(DEFAULT_ADDRESS_2))
            .andExpect(jsonPath("$.address3").value(DEFAULT_ADDRESS_3))
            .andExpect(jsonPath("$.mailId").value(DEFAULT_MAIL_ID))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING))
            .andExpect(jsonPath("$.isDeleteFlag").value(DEFAULT_IS_DELETE_FLAG))
            .andExpect(jsonPath("$.relatedBuyerUid").value(DEFAULT_RELATED_BUYER_UID))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.countryFlag").value(DEFAULT_COUNTRY_FLAG));
    }

    @Test
    @Transactional
    void getVendorProfilesByIdFiltering() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        Long id = vendorProfile.getId();

        defaultVendorProfileFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultVendorProfileFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultVendorProfileFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllVendorProfilesBySrNoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where srNo equals to
        defaultVendorProfileFiltering("srNo.equals=" + DEFAULT_SR_NO, "srNo.equals=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllVendorProfilesBySrNoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where srNo in
        defaultVendorProfileFiltering("srNo.in=" + DEFAULT_SR_NO + "," + UPDATED_SR_NO, "srNo.in=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllVendorProfilesBySrNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where srNo is not null
        defaultVendorProfileFiltering("srNo.specified=true", "srNo.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorProfilesBySrNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where srNo is greater than or equal to
        defaultVendorProfileFiltering("srNo.greaterThanOrEqual=" + DEFAULT_SR_NO, "srNo.greaterThanOrEqual=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllVendorProfilesBySrNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where srNo is less than or equal to
        defaultVendorProfileFiltering("srNo.lessThanOrEqual=" + DEFAULT_SR_NO, "srNo.lessThanOrEqual=" + SMALLER_SR_NO);
    }

    @Test
    @Transactional
    void getAllVendorProfilesBySrNoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where srNo is less than
        defaultVendorProfileFiltering("srNo.lessThan=" + UPDATED_SR_NO, "srNo.lessThan=" + DEFAULT_SR_NO);
    }

    @Test
    @Transactional
    void getAllVendorProfilesBySrNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where srNo is greater than
        defaultVendorProfileFiltering("srNo.greaterThan=" + SMALLER_SR_NO, "srNo.greaterThan=" + DEFAULT_SR_NO);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where uid equals to
        defaultVendorProfileFiltering("uid.equals=" + DEFAULT_UID, "uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where uid in
        defaultVendorProfileFiltering("uid.in=" + DEFAULT_UID + "," + UPDATED_UID, "uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where uid is not null
        defaultVendorProfileFiltering("uid.specified=true", "uid.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorProfilesByVendorIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where vendorId equals to
        defaultVendorProfileFiltering("vendorId.equals=" + DEFAULT_VENDOR_ID, "vendorId.equals=" + UPDATED_VENDOR_ID);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByVendorIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where vendorId in
        defaultVendorProfileFiltering("vendorId.in=" + DEFAULT_VENDOR_ID + "," + UPDATED_VENDOR_ID, "vendorId.in=" + UPDATED_VENDOR_ID);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByVendorIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where vendorId is not null
        defaultVendorProfileFiltering("vendorId.specified=true", "vendorId.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorProfilesByVendorIdContainsSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where vendorId contains
        defaultVendorProfileFiltering("vendorId.contains=" + DEFAULT_VENDOR_ID, "vendorId.contains=" + UPDATED_VENDOR_ID);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByVendorIdNotContainsSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where vendorId does not contain
        defaultVendorProfileFiltering("vendorId.doesNotContain=" + UPDATED_VENDOR_ID, "vendorId.doesNotContain=" + DEFAULT_VENDOR_ID);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByVendorNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where vendorName equals to
        defaultVendorProfileFiltering("vendorName.equals=" + DEFAULT_VENDOR_NAME, "vendorName.equals=" + UPDATED_VENDOR_NAME);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByVendorNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where vendorName in
        defaultVendorProfileFiltering(
            "vendorName.in=" + DEFAULT_VENDOR_NAME + "," + UPDATED_VENDOR_NAME,
            "vendorName.in=" + UPDATED_VENDOR_NAME
        );
    }

    @Test
    @Transactional
    void getAllVendorProfilesByVendorNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where vendorName is not null
        defaultVendorProfileFiltering("vendorName.specified=true", "vendorName.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorProfilesByVendorNameContainsSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where vendorName contains
        defaultVendorProfileFiltering("vendorName.contains=" + DEFAULT_VENDOR_NAME, "vendorName.contains=" + UPDATED_VENDOR_NAME);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByVendorNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where vendorName does not contain
        defaultVendorProfileFiltering(
            "vendorName.doesNotContain=" + UPDATED_VENDOR_NAME,
            "vendorName.doesNotContain=" + DEFAULT_VENDOR_NAME
        );
    }

    @Test
    @Transactional
    void getAllVendorProfilesByContactIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where contact equals to
        defaultVendorProfileFiltering("contact.equals=" + DEFAULT_CONTACT, "contact.equals=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByContactIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where contact in
        defaultVendorProfileFiltering("contact.in=" + DEFAULT_CONTACT + "," + UPDATED_CONTACT, "contact.in=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByContactIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where contact is not null
        defaultVendorProfileFiltering("contact.specified=true", "contact.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorProfilesByContactContainsSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where contact contains
        defaultVendorProfileFiltering("contact.contains=" + DEFAULT_CONTACT, "contact.contains=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByContactNotContainsSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where contact does not contain
        defaultVendorProfileFiltering("contact.doesNotContain=" + UPDATED_CONTACT, "contact.doesNotContain=" + DEFAULT_CONTACT);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByEntryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where entryDate equals to
        defaultVendorProfileFiltering("entryDate.equals=" + DEFAULT_ENTRY_DATE, "entryDate.equals=" + UPDATED_ENTRY_DATE);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByEntryDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where entryDate in
        defaultVendorProfileFiltering(
            "entryDate.in=" + DEFAULT_ENTRY_DATE + "," + UPDATED_ENTRY_DATE,
            "entryDate.in=" + UPDATED_ENTRY_DATE
        );
    }

    @Test
    @Transactional
    void getAllVendorProfilesByEntryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where entryDate is not null
        defaultVendorProfileFiltering("entryDate.specified=true", "entryDate.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorProfilesByEntryDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where entryDate is greater than or equal to
        defaultVendorProfileFiltering(
            "entryDate.greaterThanOrEqual=" + DEFAULT_ENTRY_DATE,
            "entryDate.greaterThanOrEqual=" + UPDATED_ENTRY_DATE
        );
    }

    @Test
    @Transactional
    void getAllVendorProfilesByEntryDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where entryDate is less than or equal to
        defaultVendorProfileFiltering("entryDate.lessThanOrEqual=" + DEFAULT_ENTRY_DATE, "entryDate.lessThanOrEqual=" + SMALLER_ENTRY_DATE);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByEntryDateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where entryDate is less than
        defaultVendorProfileFiltering("entryDate.lessThan=" + UPDATED_ENTRY_DATE, "entryDate.lessThan=" + DEFAULT_ENTRY_DATE);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByEntryDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where entryDate is greater than
        defaultVendorProfileFiltering("entryDate.greaterThan=" + SMALLER_ENTRY_DATE, "entryDate.greaterThan=" + DEFAULT_ENTRY_DATE);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByTradeCurrencyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where tradeCurrencyId equals to
        defaultVendorProfileFiltering(
            "tradeCurrencyId.equals=" + DEFAULT_TRADE_CURRENCY_ID,
            "tradeCurrencyId.equals=" + UPDATED_TRADE_CURRENCY_ID
        );
    }

    @Test
    @Transactional
    void getAllVendorProfilesByTradeCurrencyIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where tradeCurrencyId in
        defaultVendorProfileFiltering(
            "tradeCurrencyId.in=" + DEFAULT_TRADE_CURRENCY_ID + "," + UPDATED_TRADE_CURRENCY_ID,
            "tradeCurrencyId.in=" + UPDATED_TRADE_CURRENCY_ID
        );
    }

    @Test
    @Transactional
    void getAllVendorProfilesByTradeCurrencyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where tradeCurrencyId is not null
        defaultVendorProfileFiltering("tradeCurrencyId.specified=true", "tradeCurrencyId.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorProfilesByTradeCurrencyIdContainsSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where tradeCurrencyId contains
        defaultVendorProfileFiltering(
            "tradeCurrencyId.contains=" + DEFAULT_TRADE_CURRENCY_ID,
            "tradeCurrencyId.contains=" + UPDATED_TRADE_CURRENCY_ID
        );
    }

    @Test
    @Transactional
    void getAllVendorProfilesByTradeCurrencyIdNotContainsSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where tradeCurrencyId does not contain
        defaultVendorProfileFiltering(
            "tradeCurrencyId.doesNotContain=" + UPDATED_TRADE_CURRENCY_ID,
            "tradeCurrencyId.doesNotContain=" + DEFAULT_TRADE_CURRENCY_ID
        );
    }

    @Test
    @Transactional
    void getAllVendorProfilesByAddress1IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where address1 equals to
        defaultVendorProfileFiltering("address1.equals=" + DEFAULT_ADDRESS_1, "address1.equals=" + UPDATED_ADDRESS_1);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByAddress1IsInShouldWork() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where address1 in
        defaultVendorProfileFiltering("address1.in=" + DEFAULT_ADDRESS_1 + "," + UPDATED_ADDRESS_1, "address1.in=" + UPDATED_ADDRESS_1);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByAddress1IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where address1 is not null
        defaultVendorProfileFiltering("address1.specified=true", "address1.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorProfilesByAddress1ContainsSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where address1 contains
        defaultVendorProfileFiltering("address1.contains=" + DEFAULT_ADDRESS_1, "address1.contains=" + UPDATED_ADDRESS_1);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByAddress1NotContainsSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where address1 does not contain
        defaultVendorProfileFiltering("address1.doesNotContain=" + UPDATED_ADDRESS_1, "address1.doesNotContain=" + DEFAULT_ADDRESS_1);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByAddress2IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where address2 equals to
        defaultVendorProfileFiltering("address2.equals=" + DEFAULT_ADDRESS_2, "address2.equals=" + UPDATED_ADDRESS_2);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByAddress2IsInShouldWork() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where address2 in
        defaultVendorProfileFiltering("address2.in=" + DEFAULT_ADDRESS_2 + "," + UPDATED_ADDRESS_2, "address2.in=" + UPDATED_ADDRESS_2);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByAddress2IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where address2 is not null
        defaultVendorProfileFiltering("address2.specified=true", "address2.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorProfilesByAddress2ContainsSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where address2 contains
        defaultVendorProfileFiltering("address2.contains=" + DEFAULT_ADDRESS_2, "address2.contains=" + UPDATED_ADDRESS_2);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByAddress2NotContainsSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where address2 does not contain
        defaultVendorProfileFiltering("address2.doesNotContain=" + UPDATED_ADDRESS_2, "address2.doesNotContain=" + DEFAULT_ADDRESS_2);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByAddress3IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where address3 equals to
        defaultVendorProfileFiltering("address3.equals=" + DEFAULT_ADDRESS_3, "address3.equals=" + UPDATED_ADDRESS_3);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByAddress3IsInShouldWork() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where address3 in
        defaultVendorProfileFiltering("address3.in=" + DEFAULT_ADDRESS_3 + "," + UPDATED_ADDRESS_3, "address3.in=" + UPDATED_ADDRESS_3);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByAddress3IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where address3 is not null
        defaultVendorProfileFiltering("address3.specified=true", "address3.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorProfilesByAddress3ContainsSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where address3 contains
        defaultVendorProfileFiltering("address3.contains=" + DEFAULT_ADDRESS_3, "address3.contains=" + UPDATED_ADDRESS_3);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByAddress3NotContainsSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where address3 does not contain
        defaultVendorProfileFiltering("address3.doesNotContain=" + UPDATED_ADDRESS_3, "address3.doesNotContain=" + DEFAULT_ADDRESS_3);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByMailIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where mailId equals to
        defaultVendorProfileFiltering("mailId.equals=" + DEFAULT_MAIL_ID, "mailId.equals=" + UPDATED_MAIL_ID);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByMailIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where mailId in
        defaultVendorProfileFiltering("mailId.in=" + DEFAULT_MAIL_ID + "," + UPDATED_MAIL_ID, "mailId.in=" + UPDATED_MAIL_ID);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByMailIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where mailId is not null
        defaultVendorProfileFiltering("mailId.specified=true", "mailId.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorProfilesByMailIdContainsSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where mailId contains
        defaultVendorProfileFiltering("mailId.contains=" + DEFAULT_MAIL_ID, "mailId.contains=" + UPDATED_MAIL_ID);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByMailIdNotContainsSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where mailId does not contain
        defaultVendorProfileFiltering("mailId.doesNotContain=" + UPDATED_MAIL_ID, "mailId.doesNotContain=" + DEFAULT_MAIL_ID);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where status equals to
        defaultVendorProfileFiltering("status.equals=" + DEFAULT_STATUS, "status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where status in
        defaultVendorProfileFiltering("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS, "status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where status is not null
        defaultVendorProfileFiltering("status.specified=true", "status.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorProfilesByStatusContainsSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where status contains
        defaultVendorProfileFiltering("status.contains=" + DEFAULT_STATUS, "status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where status does not contain
        defaultVendorProfileFiltering("status.doesNotContain=" + UPDATED_STATUS, "status.doesNotContain=" + DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByRatingIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where rating equals to
        defaultVendorProfileFiltering("rating.equals=" + DEFAULT_RATING, "rating.equals=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByRatingIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where rating in
        defaultVendorProfileFiltering("rating.in=" + DEFAULT_RATING + "," + UPDATED_RATING, "rating.in=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByRatingIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where rating is not null
        defaultVendorProfileFiltering("rating.specified=true", "rating.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorProfilesByRatingContainsSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where rating contains
        defaultVendorProfileFiltering("rating.contains=" + DEFAULT_RATING, "rating.contains=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByRatingNotContainsSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where rating does not contain
        defaultVendorProfileFiltering("rating.doesNotContain=" + UPDATED_RATING, "rating.doesNotContain=" + DEFAULT_RATING);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByIsDeleteFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where isDeleteFlag equals to
        defaultVendorProfileFiltering("isDeleteFlag.equals=" + DEFAULT_IS_DELETE_FLAG, "isDeleteFlag.equals=" + UPDATED_IS_DELETE_FLAG);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByIsDeleteFlagIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where isDeleteFlag in
        defaultVendorProfileFiltering(
            "isDeleteFlag.in=" + DEFAULT_IS_DELETE_FLAG + "," + UPDATED_IS_DELETE_FLAG,
            "isDeleteFlag.in=" + UPDATED_IS_DELETE_FLAG
        );
    }

    @Test
    @Transactional
    void getAllVendorProfilesByIsDeleteFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where isDeleteFlag is not null
        defaultVendorProfileFiltering("isDeleteFlag.specified=true", "isDeleteFlag.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorProfilesByIsDeleteFlagIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where isDeleteFlag is greater than or equal to
        defaultVendorProfileFiltering(
            "isDeleteFlag.greaterThanOrEqual=" + DEFAULT_IS_DELETE_FLAG,
            "isDeleteFlag.greaterThanOrEqual=" + UPDATED_IS_DELETE_FLAG
        );
    }

    @Test
    @Transactional
    void getAllVendorProfilesByIsDeleteFlagIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where isDeleteFlag is less than or equal to
        defaultVendorProfileFiltering(
            "isDeleteFlag.lessThanOrEqual=" + DEFAULT_IS_DELETE_FLAG,
            "isDeleteFlag.lessThanOrEqual=" + SMALLER_IS_DELETE_FLAG
        );
    }

    @Test
    @Transactional
    void getAllVendorProfilesByIsDeleteFlagIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where isDeleteFlag is less than
        defaultVendorProfileFiltering("isDeleteFlag.lessThan=" + UPDATED_IS_DELETE_FLAG, "isDeleteFlag.lessThan=" + DEFAULT_IS_DELETE_FLAG);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByIsDeleteFlagIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where isDeleteFlag is greater than
        defaultVendorProfileFiltering(
            "isDeleteFlag.greaterThan=" + SMALLER_IS_DELETE_FLAG,
            "isDeleteFlag.greaterThan=" + DEFAULT_IS_DELETE_FLAG
        );
    }

    @Test
    @Transactional
    void getAllVendorProfilesByRelatedBuyerUidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where relatedBuyerUid equals to
        defaultVendorProfileFiltering(
            "relatedBuyerUid.equals=" + DEFAULT_RELATED_BUYER_UID,
            "relatedBuyerUid.equals=" + UPDATED_RELATED_BUYER_UID
        );
    }

    @Test
    @Transactional
    void getAllVendorProfilesByRelatedBuyerUidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where relatedBuyerUid in
        defaultVendorProfileFiltering(
            "relatedBuyerUid.in=" + DEFAULT_RELATED_BUYER_UID + "," + UPDATED_RELATED_BUYER_UID,
            "relatedBuyerUid.in=" + UPDATED_RELATED_BUYER_UID
        );
    }

    @Test
    @Transactional
    void getAllVendorProfilesByRelatedBuyerUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where relatedBuyerUid is not null
        defaultVendorProfileFiltering("relatedBuyerUid.specified=true", "relatedBuyerUid.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorProfilesByRelatedBuyerUidContainsSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where relatedBuyerUid contains
        defaultVendorProfileFiltering(
            "relatedBuyerUid.contains=" + DEFAULT_RELATED_BUYER_UID,
            "relatedBuyerUid.contains=" + UPDATED_RELATED_BUYER_UID
        );
    }

    @Test
    @Transactional
    void getAllVendorProfilesByRelatedBuyerUidNotContainsSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where relatedBuyerUid does not contain
        defaultVendorProfileFiltering(
            "relatedBuyerUid.doesNotContain=" + UPDATED_RELATED_BUYER_UID,
            "relatedBuyerUid.doesNotContain=" + DEFAULT_RELATED_BUYER_UID
        );
    }

    @Test
    @Transactional
    void getAllVendorProfilesByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where country equals to
        defaultVendorProfileFiltering("country.equals=" + DEFAULT_COUNTRY, "country.equals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByCountryIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where country in
        defaultVendorProfileFiltering("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY, "country.in=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where country is not null
        defaultVendorProfileFiltering("country.specified=true", "country.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorProfilesByCountryContainsSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where country contains
        defaultVendorProfileFiltering("country.contains=" + DEFAULT_COUNTRY, "country.contains=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByCountryNotContainsSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where country does not contain
        defaultVendorProfileFiltering("country.doesNotContain=" + UPDATED_COUNTRY, "country.doesNotContain=" + DEFAULT_COUNTRY);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByCountryFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where countryFlag equals to
        defaultVendorProfileFiltering("countryFlag.equals=" + DEFAULT_COUNTRY_FLAG, "countryFlag.equals=" + UPDATED_COUNTRY_FLAG);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByCountryFlagIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where countryFlag in
        defaultVendorProfileFiltering(
            "countryFlag.in=" + DEFAULT_COUNTRY_FLAG + "," + UPDATED_COUNTRY_FLAG,
            "countryFlag.in=" + UPDATED_COUNTRY_FLAG
        );
    }

    @Test
    @Transactional
    void getAllVendorProfilesByCountryFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where countryFlag is not null
        defaultVendorProfileFiltering("countryFlag.specified=true", "countryFlag.specified=false");
    }

    @Test
    @Transactional
    void getAllVendorProfilesByCountryFlagContainsSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where countryFlag contains
        defaultVendorProfileFiltering("countryFlag.contains=" + DEFAULT_COUNTRY_FLAG, "countryFlag.contains=" + UPDATED_COUNTRY_FLAG);
    }

    @Test
    @Transactional
    void getAllVendorProfilesByCountryFlagNotContainsSomething() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        // Get all the vendorProfileList where countryFlag does not contain
        defaultVendorProfileFiltering(
            "countryFlag.doesNotContain=" + UPDATED_COUNTRY_FLAG,
            "countryFlag.doesNotContain=" + DEFAULT_COUNTRY_FLAG
        );
    }

    private void defaultVendorProfileFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultVendorProfileShouldBeFound(shouldBeFound);
        defaultVendorProfileShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVendorProfileShouldBeFound(String filter) throws Exception {
        restVendorProfileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vendorProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].vendorId").value(hasItem(DEFAULT_VENDOR_ID)))
            .andExpect(jsonPath("$.[*].vendorName").value(hasItem(DEFAULT_VENDOR_NAME)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT)))
            .andExpect(jsonPath("$.[*].entryDate").value(hasItem(sameInstant(DEFAULT_ENTRY_DATE))))
            .andExpect(jsonPath("$.[*].tradeCurrencyId").value(hasItem(DEFAULT_TRADE_CURRENCY_ID)))
            .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS_1)))
            .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS_2)))
            .andExpect(jsonPath("$.[*].address3").value(hasItem(DEFAULT_ADDRESS_3)))
            .andExpect(jsonPath("$.[*].mailId").value(hasItem(DEFAULT_MAIL_ID)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)))
            .andExpect(jsonPath("$.[*].isDeleteFlag").value(hasItem(DEFAULT_IS_DELETE_FLAG)))
            .andExpect(jsonPath("$.[*].relatedBuyerUid").value(hasItem(DEFAULT_RELATED_BUYER_UID)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].countryFlag").value(hasItem(DEFAULT_COUNTRY_FLAG)));

        // Check, that the count call also returns 1
        restVendorProfileMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVendorProfileShouldNotBeFound(String filter) throws Exception {
        restVendorProfileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVendorProfileMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingVendorProfile() throws Exception {
        // Get the vendorProfile
        restVendorProfileMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVendorProfile() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        long databaseSizeBeforeUpdate = getRepositoryCount();
        vendorProfileSearchRepository.save(vendorProfile);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(vendorProfileSearchRepository.findAll());

        // Update the vendorProfile
        VendorProfile updatedVendorProfile = vendorProfileRepository.findById(vendorProfile.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVendorProfile are not directly saved in db
        em.detach(updatedVendorProfile);
        updatedVendorProfile
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .vendorId(UPDATED_VENDOR_ID)
            .vendorName(UPDATED_VENDOR_NAME)
            .contact(UPDATED_CONTACT)
            .entryDate(UPDATED_ENTRY_DATE)
            .tradeCurrencyId(UPDATED_TRADE_CURRENCY_ID)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .address3(UPDATED_ADDRESS_3)
            .mailId(UPDATED_MAIL_ID)
            .status(UPDATED_STATUS)
            .rating(UPDATED_RATING)
            .isDeleteFlag(UPDATED_IS_DELETE_FLAG)
            .relatedBuyerUid(UPDATED_RELATED_BUYER_UID)
            .country(UPDATED_COUNTRY)
            .countryFlag(UPDATED_COUNTRY_FLAG);
        VendorProfileDTO vendorProfileDTO = vendorProfileMapper.toDto(updatedVendorProfile);

        restVendorProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vendorProfileDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vendorProfileDTO))
            )
            .andExpect(status().isOk());

        // Validate the VendorProfile in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVendorProfileToMatchAllProperties(updatedVendorProfile);

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(vendorProfileSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<VendorProfile> vendorProfileSearchList = Streamable.of(vendorProfileSearchRepository.findAll()).toList();
                VendorProfile testVendorProfileSearch = vendorProfileSearchList.get(searchDatabaseSizeAfter - 1);

                assertVendorProfileAllPropertiesEquals(testVendorProfileSearch, updatedVendorProfile);
            });
    }

    @Test
    @Transactional
    void putNonExistingVendorProfile() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(vendorProfileSearchRepository.findAll());
        vendorProfile.setId(longCount.incrementAndGet());

        // Create the VendorProfile
        VendorProfileDTO vendorProfileDTO = vendorProfileMapper.toDto(vendorProfile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVendorProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vendorProfileDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vendorProfileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VendorProfile in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(vendorProfileSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchVendorProfile() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(vendorProfileSearchRepository.findAll());
        vendorProfile.setId(longCount.incrementAndGet());

        // Create the VendorProfile
        VendorProfileDTO vendorProfileDTO = vendorProfileMapper.toDto(vendorProfile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVendorProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vendorProfileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VendorProfile in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(vendorProfileSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVendorProfile() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(vendorProfileSearchRepository.findAll());
        vendorProfile.setId(longCount.incrementAndGet());

        // Create the VendorProfile
        VendorProfileDTO vendorProfileDTO = vendorProfileMapper.toDto(vendorProfile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVendorProfileMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vendorProfileDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the VendorProfile in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(vendorProfileSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateVendorProfileWithPatch() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vendorProfile using partial update
        VendorProfile partialUpdatedVendorProfile = new VendorProfile();
        partialUpdatedVendorProfile.setId(vendorProfile.getId());

        partialUpdatedVendorProfile
            .uid(UPDATED_UID)
            .contact(UPDATED_CONTACT)
            .tradeCurrencyId(UPDATED_TRADE_CURRENCY_ID)
            .address3(UPDATED_ADDRESS_3)
            .mailId(UPDATED_MAIL_ID)
            .status(UPDATED_STATUS)
            .isDeleteFlag(UPDATED_IS_DELETE_FLAG)
            .country(UPDATED_COUNTRY)
            .countryFlag(UPDATED_COUNTRY_FLAG);

        restVendorProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVendorProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVendorProfile))
            )
            .andExpect(status().isOk());

        // Validate the VendorProfile in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVendorProfileUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVendorProfile, vendorProfile),
            getPersistedVendorProfile(vendorProfile)
        );
    }

    @Test
    @Transactional
    void fullUpdateVendorProfileWithPatch() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vendorProfile using partial update
        VendorProfile partialUpdatedVendorProfile = new VendorProfile();
        partialUpdatedVendorProfile.setId(vendorProfile.getId());

        partialUpdatedVendorProfile
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .vendorId(UPDATED_VENDOR_ID)
            .vendorName(UPDATED_VENDOR_NAME)
            .contact(UPDATED_CONTACT)
            .entryDate(UPDATED_ENTRY_DATE)
            .tradeCurrencyId(UPDATED_TRADE_CURRENCY_ID)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .address3(UPDATED_ADDRESS_3)
            .mailId(UPDATED_MAIL_ID)
            .status(UPDATED_STATUS)
            .rating(UPDATED_RATING)
            .isDeleteFlag(UPDATED_IS_DELETE_FLAG)
            .relatedBuyerUid(UPDATED_RELATED_BUYER_UID)
            .country(UPDATED_COUNTRY)
            .countryFlag(UPDATED_COUNTRY_FLAG);

        restVendorProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVendorProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVendorProfile))
            )
            .andExpect(status().isOk());

        // Validate the VendorProfile in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVendorProfileUpdatableFieldsEquals(partialUpdatedVendorProfile, getPersistedVendorProfile(partialUpdatedVendorProfile));
    }

    @Test
    @Transactional
    void patchNonExistingVendorProfile() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(vendorProfileSearchRepository.findAll());
        vendorProfile.setId(longCount.incrementAndGet());

        // Create the VendorProfile
        VendorProfileDTO vendorProfileDTO = vendorProfileMapper.toDto(vendorProfile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVendorProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vendorProfileDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vendorProfileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VendorProfile in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(vendorProfileSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVendorProfile() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(vendorProfileSearchRepository.findAll());
        vendorProfile.setId(longCount.incrementAndGet());

        // Create the VendorProfile
        VendorProfileDTO vendorProfileDTO = vendorProfileMapper.toDto(vendorProfile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVendorProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vendorProfileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VendorProfile in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(vendorProfileSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVendorProfile() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(vendorProfileSearchRepository.findAll());
        vendorProfile.setId(longCount.incrementAndGet());

        // Create the VendorProfile
        VendorProfileDTO vendorProfileDTO = vendorProfileMapper.toDto(vendorProfile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVendorProfileMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(vendorProfileDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the VendorProfile in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(vendorProfileSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteVendorProfile() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);
        vendorProfileRepository.save(vendorProfile);
        vendorProfileSearchRepository.save(vendorProfile);

        long databaseSizeBeforeDelete = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(vendorProfileSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the vendorProfile
        restVendorProfileMockMvc
            .perform(delete(ENTITY_API_URL_ID, vendorProfile.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(vendorProfileSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchVendorProfile() throws Exception {
        // Initialize the database
        insertedVendorProfile = vendorProfileRepository.saveAndFlush(vendorProfile);
        vendorProfileSearchRepository.save(vendorProfile);

        // Search the vendorProfile
        restVendorProfileMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + vendorProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vendorProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].vendorId").value(hasItem(DEFAULT_VENDOR_ID)))
            .andExpect(jsonPath("$.[*].vendorName").value(hasItem(DEFAULT_VENDOR_NAME)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT)))
            .andExpect(jsonPath("$.[*].entryDate").value(hasItem(sameInstant(DEFAULT_ENTRY_DATE))))
            .andExpect(jsonPath("$.[*].tradeCurrencyId").value(hasItem(DEFAULT_TRADE_CURRENCY_ID)))
            .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS_1)))
            .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS_2)))
            .andExpect(jsonPath("$.[*].address3").value(hasItem(DEFAULT_ADDRESS_3)))
            .andExpect(jsonPath("$.[*].mailId").value(hasItem(DEFAULT_MAIL_ID)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)))
            .andExpect(jsonPath("$.[*].isDeleteFlag").value(hasItem(DEFAULT_IS_DELETE_FLAG)))
            .andExpect(jsonPath("$.[*].relatedBuyerUid").value(hasItem(DEFAULT_RELATED_BUYER_UID)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].countryFlag").value(hasItem(DEFAULT_COUNTRY_FLAG)));
    }

    protected long getRepositoryCount() {
        return vendorProfileRepository.count();
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

    protected VendorProfile getPersistedVendorProfile(VendorProfile vendorProfile) {
        return vendorProfileRepository.findById(vendorProfile.getId()).orElseThrow();
    }

    protected void assertPersistedVendorProfileToMatchAllProperties(VendorProfile expectedVendorProfile) {
        assertVendorProfileAllPropertiesEquals(expectedVendorProfile, getPersistedVendorProfile(expectedVendorProfile));
    }

    protected void assertPersistedVendorProfileToMatchUpdatableProperties(VendorProfile expectedVendorProfile) {
        assertVendorProfileAllUpdatablePropertiesEquals(expectedVendorProfile, getPersistedVendorProfile(expectedVendorProfile));
    }
}
