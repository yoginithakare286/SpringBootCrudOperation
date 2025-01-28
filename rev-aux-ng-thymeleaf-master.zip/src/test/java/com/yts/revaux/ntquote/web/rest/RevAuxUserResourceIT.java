package com.yts.revaux.ntquote.web.rest;

import static com.yts.revaux.ntquote.domain.RevAuxUserAsserts.*;
import static com.yts.revaux.ntquote.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yts.revaux.ntquote.IntegrationTest;
import com.yts.revaux.ntquote.domain.RevAuxUser;
import com.yts.revaux.ntquote.domain.User;
import com.yts.revaux.ntquote.repository.RevAuxUserRepository;
import com.yts.revaux.ntquote.repository.UserRepository;
import com.yts.revaux.ntquote.repository.search.RevAuxUserSearchRepository;
import com.yts.revaux.ntquote.service.RevAuxUserService;
import com.yts.revaux.ntquote.service.dto.RevAuxUserDTO;
import com.yts.revaux.ntquote.service.mapper.RevAuxUserMapper;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.assertj.core.util.IterableUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Streamable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RevAuxUserResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class RevAuxUserResourceIT {

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_PINCODE = "AAAAAAAAAA";
    private static final String UPDATED_PINCODE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_PREFERRED_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_PREFERRED_LANGUAGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/rev-aux-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/rev-aux-users/_search";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RevAuxUserRepository revAuxUserRepository;

    @Autowired
    private UserRepository userRepository;

    @Mock
    private RevAuxUserRepository revAuxUserRepositoryMock;

    @Autowired
    private RevAuxUserMapper revAuxUserMapper;

    @Mock
    private RevAuxUserService revAuxUserServiceMock;

    @Autowired
    private RevAuxUserSearchRepository revAuxUserSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRevAuxUserMockMvc;

    private RevAuxUser revAuxUser;

    private RevAuxUser insertedRevAuxUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RevAuxUser createEntity(EntityManager em) {
        RevAuxUser revAuxUser = new RevAuxUser()
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .pincode(DEFAULT_PINCODE)
            .city(DEFAULT_CITY)
            .state(DEFAULT_STATE)
            .country(DEFAULT_COUNTRY)
            .preferredLanguage(DEFAULT_PREFERRED_LANGUAGE);
        // Add required entity
        User user = UserResourceIT.createEntity();
        em.persist(user);
        em.flush();
        revAuxUser.setInternalUser(user);
        return revAuxUser;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RevAuxUser createUpdatedEntity(EntityManager em) {
        RevAuxUser updatedRevAuxUser = new RevAuxUser()
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .pincode(UPDATED_PINCODE)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .country(UPDATED_COUNTRY)
            .preferredLanguage(UPDATED_PREFERRED_LANGUAGE);
        // Add required entity
        User user = UserResourceIT.createEntity();
        em.persist(user);
        em.flush();
        updatedRevAuxUser.setInternalUser(user);
        return updatedRevAuxUser;
    }

    @BeforeEach
    public void initTest() {
        revAuxUser = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedRevAuxUser != null) {
            revAuxUserRepository.delete(insertedRevAuxUser);
            revAuxUserSearchRepository.delete(insertedRevAuxUser);
            insertedRevAuxUser = null;
        }
    }

    @Test
    @Transactional
    void createRevAuxUser() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(revAuxUserSearchRepository.findAll());
        // Create the RevAuxUser
        RevAuxUserDTO revAuxUserDTO = revAuxUserMapper.toDto(revAuxUser);
        var returnedRevAuxUserDTO = om.readValue(
            restRevAuxUserMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(revAuxUserDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RevAuxUserDTO.class
        );

        // Validate the RevAuxUser in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedRevAuxUser = revAuxUserMapper.toEntity(returnedRevAuxUserDTO);
        assertRevAuxUserUpdatableFieldsEquals(returnedRevAuxUser, getPersistedRevAuxUser(returnedRevAuxUser));

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(revAuxUserSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });

        assertRevAuxUserMapsIdRelationshipPersistedValue(revAuxUser, returnedRevAuxUser);

        insertedRevAuxUser = returnedRevAuxUser;
    }

    @Test
    @Transactional
    void createRevAuxUserWithExistingId() throws Exception {
        // Create the RevAuxUser with an existing ID
        revAuxUser.setId(1L);
        RevAuxUserDTO revAuxUserDTO = revAuxUserMapper.toDto(revAuxUser);

        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(revAuxUserSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restRevAuxUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(revAuxUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RevAuxUser in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(revAuxUserSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void updateRevAuxUserMapsIdAssociationWithNewId() throws Exception {
        // Initialize the database
        insertedRevAuxUser = revAuxUserRepository.saveAndFlush(revAuxUser);
        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(revAuxUserSearchRepository.findAll());
        // Add a new parent entity
        User user = UserResourceIT.createEntity();
        em.persist(user);
        em.flush();

        // Load the revAuxUser
        RevAuxUser updatedRevAuxUser = revAuxUserRepository.findById(revAuxUser.getId()).orElseThrow();
        assertThat(updatedRevAuxUser).isNotNull();
        // Disconnect from session so that the updates on updatedRevAuxUser are not directly saved in db
        em.detach(updatedRevAuxUser);

        // Update the User with new association value
        updatedRevAuxUser.setInternalUser(user);
        RevAuxUserDTO updatedRevAuxUserDTO = revAuxUserMapper.toDto(updatedRevAuxUser);
        assertThat(updatedRevAuxUserDTO).isNotNull();

        // Update the entity
        restRevAuxUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRevAuxUserDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRevAuxUserDTO))
            )
            .andExpect(status().isOk());

        // Validate the RevAuxUser in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);

        /**
         * Validate the id for MapsId, the ids must be same
         * Uncomment the following line for assertion. However, please note that there is a known issue and uncommenting will fail the test.
         * Please look at https://github.com/jhipster/generator-jhipster/issues/9100. You can modify this test as necessary.
         * assertThat(testRevAuxUser.getId()).isEqualTo(testRevAuxUser.getUser().getId());
         */
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(revAuxUserSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllRevAuxUsers() throws Exception {
        // Initialize the database
        insertedRevAuxUser = revAuxUserRepository.saveAndFlush(revAuxUser);

        // Get all the revAuxUserList
        restRevAuxUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(revAuxUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].preferredLanguage").value(hasItem(DEFAULT_PREFERRED_LANGUAGE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRevAuxUsersWithEagerRelationshipsIsEnabled() throws Exception {
        when(revAuxUserServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRevAuxUserMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(revAuxUserServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRevAuxUsersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(revAuxUserServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRevAuxUserMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(revAuxUserRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getRevAuxUser() throws Exception {
        // Initialize the database
        insertedRevAuxUser = revAuxUserRepository.saveAndFlush(revAuxUser);

        // Get the revAuxUser
        restRevAuxUserMockMvc
            .perform(get(ENTITY_API_URL_ID, revAuxUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(revAuxUser.getId().intValue()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.pincode").value(DEFAULT_PINCODE))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.preferredLanguage").value(DEFAULT_PREFERRED_LANGUAGE));
    }

    @Test
    @Transactional
    void getRevAuxUsersByIdFiltering() throws Exception {
        // Initialize the database
        insertedRevAuxUser = revAuxUserRepository.saveAndFlush(revAuxUser);

        Long id = revAuxUser.getId();

        defaultRevAuxUserFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultRevAuxUserFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultRevAuxUserFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRevAuxUsersByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedRevAuxUser = revAuxUserRepository.saveAndFlush(revAuxUser);

        // Get all the revAuxUserList where phoneNumber equals to
        defaultRevAuxUserFiltering("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER, "phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllRevAuxUsersByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        insertedRevAuxUser = revAuxUserRepository.saveAndFlush(revAuxUser);

        // Get all the revAuxUserList where phoneNumber in
        defaultRevAuxUserFiltering(
            "phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER,
            "phoneNumber.in=" + UPDATED_PHONE_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllRevAuxUsersByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedRevAuxUser = revAuxUserRepository.saveAndFlush(revAuxUser);

        // Get all the revAuxUserList where phoneNumber is not null
        defaultRevAuxUserFiltering("phoneNumber.specified=true", "phoneNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllRevAuxUsersByPhoneNumberContainsSomething() throws Exception {
        // Initialize the database
        insertedRevAuxUser = revAuxUserRepository.saveAndFlush(revAuxUser);

        // Get all the revAuxUserList where phoneNumber contains
        defaultRevAuxUserFiltering("phoneNumber.contains=" + DEFAULT_PHONE_NUMBER, "phoneNumber.contains=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllRevAuxUsersByPhoneNumberNotContainsSomething() throws Exception {
        // Initialize the database
        insertedRevAuxUser = revAuxUserRepository.saveAndFlush(revAuxUser);

        // Get all the revAuxUserList where phoneNumber does not contain
        defaultRevAuxUserFiltering(
            "phoneNumber.doesNotContain=" + UPDATED_PHONE_NUMBER,
            "phoneNumber.doesNotContain=" + DEFAULT_PHONE_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllRevAuxUsersByPincodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedRevAuxUser = revAuxUserRepository.saveAndFlush(revAuxUser);

        // Get all the revAuxUserList where pincode equals to
        defaultRevAuxUserFiltering("pincode.equals=" + DEFAULT_PINCODE, "pincode.equals=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllRevAuxUsersByPincodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedRevAuxUser = revAuxUserRepository.saveAndFlush(revAuxUser);

        // Get all the revAuxUserList where pincode in
        defaultRevAuxUserFiltering("pincode.in=" + DEFAULT_PINCODE + "," + UPDATED_PINCODE, "pincode.in=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllRevAuxUsersByPincodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedRevAuxUser = revAuxUserRepository.saveAndFlush(revAuxUser);

        // Get all the revAuxUserList where pincode is not null
        defaultRevAuxUserFiltering("pincode.specified=true", "pincode.specified=false");
    }

    @Test
    @Transactional
    void getAllRevAuxUsersByPincodeContainsSomething() throws Exception {
        // Initialize the database
        insertedRevAuxUser = revAuxUserRepository.saveAndFlush(revAuxUser);

        // Get all the revAuxUserList where pincode contains
        defaultRevAuxUserFiltering("pincode.contains=" + DEFAULT_PINCODE, "pincode.contains=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllRevAuxUsersByPincodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedRevAuxUser = revAuxUserRepository.saveAndFlush(revAuxUser);

        // Get all the revAuxUserList where pincode does not contain
        defaultRevAuxUserFiltering("pincode.doesNotContain=" + UPDATED_PINCODE, "pincode.doesNotContain=" + DEFAULT_PINCODE);
    }

    @Test
    @Transactional
    void getAllRevAuxUsersByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedRevAuxUser = revAuxUserRepository.saveAndFlush(revAuxUser);

        // Get all the revAuxUserList where city equals to
        defaultRevAuxUserFiltering("city.equals=" + DEFAULT_CITY, "city.equals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllRevAuxUsersByCityIsInShouldWork() throws Exception {
        // Initialize the database
        insertedRevAuxUser = revAuxUserRepository.saveAndFlush(revAuxUser);

        // Get all the revAuxUserList where city in
        defaultRevAuxUserFiltering("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY, "city.in=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllRevAuxUsersByCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedRevAuxUser = revAuxUserRepository.saveAndFlush(revAuxUser);

        // Get all the revAuxUserList where city is not null
        defaultRevAuxUserFiltering("city.specified=true", "city.specified=false");
    }

    @Test
    @Transactional
    void getAllRevAuxUsersByCityContainsSomething() throws Exception {
        // Initialize the database
        insertedRevAuxUser = revAuxUserRepository.saveAndFlush(revAuxUser);

        // Get all the revAuxUserList where city contains
        defaultRevAuxUserFiltering("city.contains=" + DEFAULT_CITY, "city.contains=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllRevAuxUsersByCityNotContainsSomething() throws Exception {
        // Initialize the database
        insertedRevAuxUser = revAuxUserRepository.saveAndFlush(revAuxUser);

        // Get all the revAuxUserList where city does not contain
        defaultRevAuxUserFiltering("city.doesNotContain=" + UPDATED_CITY, "city.doesNotContain=" + DEFAULT_CITY);
    }

    @Test
    @Transactional
    void getAllRevAuxUsersByStateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedRevAuxUser = revAuxUserRepository.saveAndFlush(revAuxUser);

        // Get all the revAuxUserList where state equals to
        defaultRevAuxUserFiltering("state.equals=" + DEFAULT_STATE, "state.equals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllRevAuxUsersByStateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedRevAuxUser = revAuxUserRepository.saveAndFlush(revAuxUser);

        // Get all the revAuxUserList where state in
        defaultRevAuxUserFiltering("state.in=" + DEFAULT_STATE + "," + UPDATED_STATE, "state.in=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllRevAuxUsersByStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedRevAuxUser = revAuxUserRepository.saveAndFlush(revAuxUser);

        // Get all the revAuxUserList where state is not null
        defaultRevAuxUserFiltering("state.specified=true", "state.specified=false");
    }

    @Test
    @Transactional
    void getAllRevAuxUsersByStateContainsSomething() throws Exception {
        // Initialize the database
        insertedRevAuxUser = revAuxUserRepository.saveAndFlush(revAuxUser);

        // Get all the revAuxUserList where state contains
        defaultRevAuxUserFiltering("state.contains=" + DEFAULT_STATE, "state.contains=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllRevAuxUsersByStateNotContainsSomething() throws Exception {
        // Initialize the database
        insertedRevAuxUser = revAuxUserRepository.saveAndFlush(revAuxUser);

        // Get all the revAuxUserList where state does not contain
        defaultRevAuxUserFiltering("state.doesNotContain=" + UPDATED_STATE, "state.doesNotContain=" + DEFAULT_STATE);
    }

    @Test
    @Transactional
    void getAllRevAuxUsersByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedRevAuxUser = revAuxUserRepository.saveAndFlush(revAuxUser);

        // Get all the revAuxUserList where country equals to
        defaultRevAuxUserFiltering("country.equals=" + DEFAULT_COUNTRY, "country.equals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllRevAuxUsersByCountryIsInShouldWork() throws Exception {
        // Initialize the database
        insertedRevAuxUser = revAuxUserRepository.saveAndFlush(revAuxUser);

        // Get all the revAuxUserList where country in
        defaultRevAuxUserFiltering("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY, "country.in=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllRevAuxUsersByCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedRevAuxUser = revAuxUserRepository.saveAndFlush(revAuxUser);

        // Get all the revAuxUserList where country is not null
        defaultRevAuxUserFiltering("country.specified=true", "country.specified=false");
    }

    @Test
    @Transactional
    void getAllRevAuxUsersByCountryContainsSomething() throws Exception {
        // Initialize the database
        insertedRevAuxUser = revAuxUserRepository.saveAndFlush(revAuxUser);

        // Get all the revAuxUserList where country contains
        defaultRevAuxUserFiltering("country.contains=" + DEFAULT_COUNTRY, "country.contains=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllRevAuxUsersByCountryNotContainsSomething() throws Exception {
        // Initialize the database
        insertedRevAuxUser = revAuxUserRepository.saveAndFlush(revAuxUser);

        // Get all the revAuxUserList where country does not contain
        defaultRevAuxUserFiltering("country.doesNotContain=" + UPDATED_COUNTRY, "country.doesNotContain=" + DEFAULT_COUNTRY);
    }

    @Test
    @Transactional
    void getAllRevAuxUsersByPreferredLanguageIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedRevAuxUser = revAuxUserRepository.saveAndFlush(revAuxUser);

        // Get all the revAuxUserList where preferredLanguage equals to
        defaultRevAuxUserFiltering(
            "preferredLanguage.equals=" + DEFAULT_PREFERRED_LANGUAGE,
            "preferredLanguage.equals=" + UPDATED_PREFERRED_LANGUAGE
        );
    }

    @Test
    @Transactional
    void getAllRevAuxUsersByPreferredLanguageIsInShouldWork() throws Exception {
        // Initialize the database
        insertedRevAuxUser = revAuxUserRepository.saveAndFlush(revAuxUser);

        // Get all the revAuxUserList where preferredLanguage in
        defaultRevAuxUserFiltering(
            "preferredLanguage.in=" + DEFAULT_PREFERRED_LANGUAGE + "," + UPDATED_PREFERRED_LANGUAGE,
            "preferredLanguage.in=" + UPDATED_PREFERRED_LANGUAGE
        );
    }

    @Test
    @Transactional
    void getAllRevAuxUsersByPreferredLanguageIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedRevAuxUser = revAuxUserRepository.saveAndFlush(revAuxUser);

        // Get all the revAuxUserList where preferredLanguage is not null
        defaultRevAuxUserFiltering("preferredLanguage.specified=true", "preferredLanguage.specified=false");
    }

    @Test
    @Transactional
    void getAllRevAuxUsersByPreferredLanguageContainsSomething() throws Exception {
        // Initialize the database
        insertedRevAuxUser = revAuxUserRepository.saveAndFlush(revAuxUser);

        // Get all the revAuxUserList where preferredLanguage contains
        defaultRevAuxUserFiltering(
            "preferredLanguage.contains=" + DEFAULT_PREFERRED_LANGUAGE,
            "preferredLanguage.contains=" + UPDATED_PREFERRED_LANGUAGE
        );
    }

    @Test
    @Transactional
    void getAllRevAuxUsersByPreferredLanguageNotContainsSomething() throws Exception {
        // Initialize the database
        insertedRevAuxUser = revAuxUserRepository.saveAndFlush(revAuxUser);

        // Get all the revAuxUserList where preferredLanguage does not contain
        defaultRevAuxUserFiltering(
            "preferredLanguage.doesNotContain=" + UPDATED_PREFERRED_LANGUAGE,
            "preferredLanguage.doesNotContain=" + DEFAULT_PREFERRED_LANGUAGE
        );
    }

    @Test
    @Transactional
    void getAllRevAuxUsersByInternalUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User internalUser = revAuxUser.getInternalUser();
        revAuxUserRepository.saveAndFlush(revAuxUser);
        Long internalUserId = internalUser.getId();
        // Get all the revAuxUserList where internalUser equals to internalUserId
        defaultRevAuxUserShouldBeFound("internalUserId.equals=" + internalUserId);

        // Get all the revAuxUserList where internalUser equals to (internalUserId + 1)
        defaultRevAuxUserShouldNotBeFound("internalUserId.equals=" + (internalUserId + 1));
    }

    private void defaultRevAuxUserFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultRevAuxUserShouldBeFound(shouldBeFound);
        defaultRevAuxUserShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRevAuxUserShouldBeFound(String filter) throws Exception {
        restRevAuxUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(revAuxUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].preferredLanguage").value(hasItem(DEFAULT_PREFERRED_LANGUAGE)));

        // Check, that the count call also returns 1
        restRevAuxUserMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRevAuxUserShouldNotBeFound(String filter) throws Exception {
        restRevAuxUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRevAuxUserMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRevAuxUser() throws Exception {
        // Get the revAuxUser
        restRevAuxUserMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRevAuxUser() throws Exception {
        // Initialize the database
        insertedRevAuxUser = revAuxUserRepository.saveAndFlush(revAuxUser);

        long databaseSizeBeforeUpdate = getRepositoryCount();
        revAuxUserSearchRepository.save(revAuxUser);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(revAuxUserSearchRepository.findAll());

        // Update the revAuxUser
        RevAuxUser updatedRevAuxUser = revAuxUserRepository.findById(revAuxUser.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRevAuxUser are not directly saved in db
        em.detach(updatedRevAuxUser);
        updatedRevAuxUser
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .pincode(UPDATED_PINCODE)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .country(UPDATED_COUNTRY)
            .preferredLanguage(UPDATED_PREFERRED_LANGUAGE);
        RevAuxUserDTO revAuxUserDTO = revAuxUserMapper.toDto(updatedRevAuxUser);

        restRevAuxUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, revAuxUserDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(revAuxUserDTO))
            )
            .andExpect(status().isOk());

        // Validate the RevAuxUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRevAuxUserToMatchAllProperties(updatedRevAuxUser);

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(revAuxUserSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<RevAuxUser> revAuxUserSearchList = Streamable.of(revAuxUserSearchRepository.findAll()).toList();
                RevAuxUser testRevAuxUserSearch = revAuxUserSearchList.get(searchDatabaseSizeAfter - 1);

                assertRevAuxUserAllPropertiesEquals(testRevAuxUserSearch, updatedRevAuxUser);
            });
    }

    @Test
    @Transactional
    void putNonExistingRevAuxUser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(revAuxUserSearchRepository.findAll());
        revAuxUser.setId(longCount.incrementAndGet());

        // Create the RevAuxUser
        RevAuxUserDTO revAuxUserDTO = revAuxUserMapper.toDto(revAuxUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRevAuxUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, revAuxUserDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(revAuxUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RevAuxUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(revAuxUserSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchRevAuxUser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(revAuxUserSearchRepository.findAll());
        revAuxUser.setId(longCount.incrementAndGet());

        // Create the RevAuxUser
        RevAuxUserDTO revAuxUserDTO = revAuxUserMapper.toDto(revAuxUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRevAuxUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(revAuxUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RevAuxUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(revAuxUserSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRevAuxUser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(revAuxUserSearchRepository.findAll());
        revAuxUser.setId(longCount.incrementAndGet());

        // Create the RevAuxUser
        RevAuxUserDTO revAuxUserDTO = revAuxUserMapper.toDto(revAuxUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRevAuxUserMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(revAuxUserDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RevAuxUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(revAuxUserSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateRevAuxUserWithPatch() throws Exception {
        // Initialize the database
        insertedRevAuxUser = revAuxUserRepository.saveAndFlush(revAuxUser);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the revAuxUser using partial update
        RevAuxUser partialUpdatedRevAuxUser = new RevAuxUser();
        partialUpdatedRevAuxUser.setId(revAuxUser.getId());

        partialUpdatedRevAuxUser
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .country(UPDATED_COUNTRY)
            .preferredLanguage(UPDATED_PREFERRED_LANGUAGE);

        restRevAuxUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRevAuxUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRevAuxUser))
            )
            .andExpect(status().isOk());

        // Validate the RevAuxUser in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRevAuxUserUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRevAuxUser, revAuxUser),
            getPersistedRevAuxUser(revAuxUser)
        );
    }

    @Test
    @Transactional
    void fullUpdateRevAuxUserWithPatch() throws Exception {
        // Initialize the database
        insertedRevAuxUser = revAuxUserRepository.saveAndFlush(revAuxUser);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the revAuxUser using partial update
        RevAuxUser partialUpdatedRevAuxUser = new RevAuxUser();
        partialUpdatedRevAuxUser.setId(revAuxUser.getId());

        partialUpdatedRevAuxUser
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .pincode(UPDATED_PINCODE)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .country(UPDATED_COUNTRY)
            .preferredLanguage(UPDATED_PREFERRED_LANGUAGE);

        restRevAuxUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRevAuxUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRevAuxUser))
            )
            .andExpect(status().isOk());

        // Validate the RevAuxUser in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRevAuxUserUpdatableFieldsEquals(partialUpdatedRevAuxUser, getPersistedRevAuxUser(partialUpdatedRevAuxUser));
    }

    @Test
    @Transactional
    void patchNonExistingRevAuxUser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(revAuxUserSearchRepository.findAll());
        revAuxUser.setId(longCount.incrementAndGet());

        // Create the RevAuxUser
        RevAuxUserDTO revAuxUserDTO = revAuxUserMapper.toDto(revAuxUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRevAuxUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, revAuxUserDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(revAuxUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RevAuxUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(revAuxUserSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRevAuxUser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(revAuxUserSearchRepository.findAll());
        revAuxUser.setId(longCount.incrementAndGet());

        // Create the RevAuxUser
        RevAuxUserDTO revAuxUserDTO = revAuxUserMapper.toDto(revAuxUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRevAuxUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(revAuxUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RevAuxUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(revAuxUserSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRevAuxUser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(revAuxUserSearchRepository.findAll());
        revAuxUser.setId(longCount.incrementAndGet());

        // Create the RevAuxUser
        RevAuxUserDTO revAuxUserDTO = revAuxUserMapper.toDto(revAuxUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRevAuxUserMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(revAuxUserDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RevAuxUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(revAuxUserSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteRevAuxUser() throws Exception {
        // Initialize the database
        insertedRevAuxUser = revAuxUserRepository.saveAndFlush(revAuxUser);
        revAuxUserRepository.save(revAuxUser);
        revAuxUserSearchRepository.save(revAuxUser);

        long databaseSizeBeforeDelete = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(revAuxUserSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the revAuxUser
        restRevAuxUserMockMvc
            .perform(delete(ENTITY_API_URL_ID, revAuxUser.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(revAuxUserSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchRevAuxUser() throws Exception {
        // Initialize the database
        insertedRevAuxUser = revAuxUserRepository.saveAndFlush(revAuxUser);
        revAuxUserSearchRepository.save(revAuxUser);

        // Search the revAuxUser
        restRevAuxUserMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + revAuxUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(revAuxUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].preferredLanguage").value(hasItem(DEFAULT_PREFERRED_LANGUAGE)));
    }

    protected long getRepositoryCount() {
        return revAuxUserRepository.count();
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

    protected RevAuxUser getPersistedRevAuxUser(RevAuxUser revAuxUser) {
        return revAuxUserRepository.findById(revAuxUser.getId()).orElseThrow();
    }

    protected void assertPersistedRevAuxUserToMatchAllProperties(RevAuxUser expectedRevAuxUser) {
        assertRevAuxUserAllPropertiesEquals(expectedRevAuxUser, getPersistedRevAuxUser(expectedRevAuxUser));
    }

    protected void assertPersistedRevAuxUserToMatchUpdatableProperties(RevAuxUser expectedRevAuxUser) {
        assertRevAuxUserAllUpdatablePropertiesEquals(expectedRevAuxUser, getPersistedRevAuxUser(expectedRevAuxUser));
    }
}
