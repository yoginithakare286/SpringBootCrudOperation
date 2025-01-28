package com.yts.revaux.ntquote.web.rest;

import static com.yts.revaux.ntquote.domain.NtQuoteCustomerProjectAsserts.*;
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
import com.yts.revaux.ntquote.domain.NtQuote;
import com.yts.revaux.ntquote.domain.NtQuoteCustomerProject;
import com.yts.revaux.ntquote.repository.NtQuoteCustomerProjectRepository;
import com.yts.revaux.ntquote.repository.search.NtQuoteCustomerProjectSearchRepository;
import com.yts.revaux.ntquote.service.dto.NtQuoteCustomerProjectDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuoteCustomerProjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
 * Integration tests for the {@link NtQuoteCustomerProjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NtQuoteCustomerProjectResourceIT {

    private static final Integer DEFAULT_SR_NO = 1;
    private static final Integer UPDATED_SR_NO = 2;
    private static final Integer SMALLER_SR_NO = 1 - 1;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final String DEFAULT_QSF = "AAAAAAAAAA";
    private static final String UPDATED_QSF = "BBBBBBBBBB";

    private static final String DEFAULT_REV = "AAAAAAAAAA";
    private static final String UPDATED_REV = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_CUSTOMER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_OVERALL_PROJECT_RISK_EVALUATION = "AAAAAAAAAA";
    private static final String UPDATED_OVERALL_PROJECT_RISK_EVALUATION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_ASSESSMENT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ASSESSMENT_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_ASSESSMENT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_RE_ASSESSMENT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_RE_ASSESSMENT_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_RE_ASSESSMENT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_PROJECT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT_INFORMATION = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_INFORMATION = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT_MANAGER = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_MANAGER = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT_REQUIREMENT = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_REQUIREMENT = "BBBBBBBBBB";

    private static final String DEFAULT_LENGTH_OF_PROJECT = "AAAAAAAAAA";
    private static final String UPDATED_LENGTH_OF_PROJECT = "BBBBBBBBBB";

    private static final String DEFAULT_NEW_MOLD = "AAAAAAAAAA";
    private static final String UPDATED_NEW_MOLD = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSFER_MOLD = "AAAAAAAAAA";
    private static final String UPDATED_TRANSFER_MOLD = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CONTACT_REVIEW_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CONTACT_REVIEW_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CONTACT_REVIEW_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/nt-quote-customer-projects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/nt-quote-customer-projects/_search";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NtQuoteCustomerProjectRepository ntQuoteCustomerProjectRepository;

    @Autowired
    private NtQuoteCustomerProjectMapper ntQuoteCustomerProjectMapper;

    @Autowired
    private NtQuoteCustomerProjectSearchRepository ntQuoteCustomerProjectSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNtQuoteCustomerProjectMockMvc;

    private NtQuoteCustomerProject ntQuoteCustomerProject;

    private NtQuoteCustomerProject insertedNtQuoteCustomerProject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NtQuoteCustomerProject createEntity() {
        return new NtQuoteCustomerProject()
            .srNo(DEFAULT_SR_NO)
            .uid(DEFAULT_UID)
            .qsf(DEFAULT_QSF)
            .rev(DEFAULT_REV)
            .date(DEFAULT_DATE)
            .customerName(DEFAULT_CUSTOMER_NAME)
            .contactName(DEFAULT_CONTACT_NAME)
            .phone(DEFAULT_PHONE)
            .email(DEFAULT_EMAIL)
            .overallProjectRiskEvaluation(DEFAULT_OVERALL_PROJECT_RISK_EVALUATION)
            .assessmentDate(DEFAULT_ASSESSMENT_DATE)
            .reAssessmentDate(DEFAULT_RE_ASSESSMENT_DATE)
            .projectName(DEFAULT_PROJECT_NAME)
            .projectInformation(DEFAULT_PROJECT_INFORMATION)
            .projectManager(DEFAULT_PROJECT_MANAGER)
            .projectRequirement(DEFAULT_PROJECT_REQUIREMENT)
            .lengthOfProject(DEFAULT_LENGTH_OF_PROJECT)
            .newMold(DEFAULT_NEW_MOLD)
            .transferMold(DEFAULT_TRANSFER_MOLD)
            .contactReviewDate(DEFAULT_CONTACT_REVIEW_DATE)
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
    public static NtQuoteCustomerProject createUpdatedEntity() {
        return new NtQuoteCustomerProject()
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .qsf(UPDATED_QSF)
            .rev(UPDATED_REV)
            .date(UPDATED_DATE)
            .customerName(UPDATED_CUSTOMER_NAME)
            .contactName(UPDATED_CONTACT_NAME)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .overallProjectRiskEvaluation(UPDATED_OVERALL_PROJECT_RISK_EVALUATION)
            .assessmentDate(UPDATED_ASSESSMENT_DATE)
            .reAssessmentDate(UPDATED_RE_ASSESSMENT_DATE)
            .projectName(UPDATED_PROJECT_NAME)
            .projectInformation(UPDATED_PROJECT_INFORMATION)
            .projectManager(UPDATED_PROJECT_MANAGER)
            .projectRequirement(UPDATED_PROJECT_REQUIREMENT)
            .lengthOfProject(UPDATED_LENGTH_OF_PROJECT)
            .newMold(UPDATED_NEW_MOLD)
            .transferMold(UPDATED_TRANSFER_MOLD)
            .contactReviewDate(UPDATED_CONTACT_REVIEW_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);
    }

    @BeforeEach
    public void initTest() {
        ntQuoteCustomerProject = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedNtQuoteCustomerProject != null) {
            ntQuoteCustomerProjectRepository.delete(insertedNtQuoteCustomerProject);
            ntQuoteCustomerProjectSearchRepository.delete(insertedNtQuoteCustomerProject);
            insertedNtQuoteCustomerProject = null;
        }
    }

    @Test
    @Transactional
    void createNtQuoteCustomerProject() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerProjectSearchRepository.findAll());
        // Create the NtQuoteCustomerProject
        NtQuoteCustomerProjectDTO ntQuoteCustomerProjectDTO = ntQuoteCustomerProjectMapper.toDto(ntQuoteCustomerProject);
        var returnedNtQuoteCustomerProjectDTO = om.readValue(
            restNtQuoteCustomerProjectMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteCustomerProjectDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            NtQuoteCustomerProjectDTO.class
        );

        // Validate the NtQuoteCustomerProject in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedNtQuoteCustomerProject = ntQuoteCustomerProjectMapper.toEntity(returnedNtQuoteCustomerProjectDTO);
        assertNtQuoteCustomerProjectUpdatableFieldsEquals(
            returnedNtQuoteCustomerProject,
            getPersistedNtQuoteCustomerProject(returnedNtQuoteCustomerProject)
        );

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerProjectSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });

        insertedNtQuoteCustomerProject = returnedNtQuoteCustomerProject;
    }

    @Test
    @Transactional
    void createNtQuoteCustomerProjectWithExistingId() throws Exception {
        // Create the NtQuoteCustomerProject with an existing ID
        ntQuoteCustomerProject.setId(1L);
        NtQuoteCustomerProjectDTO ntQuoteCustomerProjectDTO = ntQuoteCustomerProjectMapper.toDto(ntQuoteCustomerProject);

        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerProjectSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restNtQuoteCustomerProjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteCustomerProjectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteCustomerProject in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerProjectSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkUidIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerProjectSearchRepository.findAll());
        // set the field null
        ntQuoteCustomerProject.setUid(null);

        // Create the NtQuoteCustomerProject, which fails.
        NtQuoteCustomerProjectDTO ntQuoteCustomerProjectDTO = ntQuoteCustomerProjectMapper.toDto(ntQuoteCustomerProject);

        restNtQuoteCustomerProjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteCustomerProjectDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerProjectSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjects() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList
        restNtQuoteCustomerProjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuoteCustomerProject.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].qsf").value(hasItem(DEFAULT_QSF)))
            .andExpect(jsonPath("$.[*].rev").value(hasItem(DEFAULT_REV)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME)))
            .andExpect(jsonPath("$.[*].contactName").value(hasItem(DEFAULT_CONTACT_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].overallProjectRiskEvaluation").value(hasItem(DEFAULT_OVERALL_PROJECT_RISK_EVALUATION)))
            .andExpect(jsonPath("$.[*].assessmentDate").value(hasItem(sameInstant(DEFAULT_ASSESSMENT_DATE))))
            .andExpect(jsonPath("$.[*].reAssessmentDate").value(hasItem(sameInstant(DEFAULT_RE_ASSESSMENT_DATE))))
            .andExpect(jsonPath("$.[*].projectName").value(hasItem(DEFAULT_PROJECT_NAME)))
            .andExpect(jsonPath("$.[*].projectInformation").value(hasItem(DEFAULT_PROJECT_INFORMATION)))
            .andExpect(jsonPath("$.[*].projectManager").value(hasItem(DEFAULT_PROJECT_MANAGER)))
            .andExpect(jsonPath("$.[*].projectRequirement").value(hasItem(DEFAULT_PROJECT_REQUIREMENT)))
            .andExpect(jsonPath("$.[*].lengthOfProject").value(hasItem(DEFAULT_LENGTH_OF_PROJECT)))
            .andExpect(jsonPath("$.[*].newMold").value(hasItem(DEFAULT_NEW_MOLD)))
            .andExpect(jsonPath("$.[*].transferMold").value(hasItem(DEFAULT_TRANSFER_MOLD)))
            .andExpect(jsonPath("$.[*].contactReviewDate").value(hasItem(DEFAULT_CONTACT_REVIEW_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getNtQuoteCustomerProject() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get the ntQuoteCustomerProject
        restNtQuoteCustomerProjectMockMvc
            .perform(get(ENTITY_API_URL_ID, ntQuoteCustomerProject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ntQuoteCustomerProject.getId().intValue()))
            .andExpect(jsonPath("$.srNo").value(DEFAULT_SR_NO))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.qsf").value(DEFAULT_QSF))
            .andExpect(jsonPath("$.rev").value(DEFAULT_REV))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.customerName").value(DEFAULT_CUSTOMER_NAME))
            .andExpect(jsonPath("$.contactName").value(DEFAULT_CONTACT_NAME))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.overallProjectRiskEvaluation").value(DEFAULT_OVERALL_PROJECT_RISK_EVALUATION))
            .andExpect(jsonPath("$.assessmentDate").value(sameInstant(DEFAULT_ASSESSMENT_DATE)))
            .andExpect(jsonPath("$.reAssessmentDate").value(sameInstant(DEFAULT_RE_ASSESSMENT_DATE)))
            .andExpect(jsonPath("$.projectName").value(DEFAULT_PROJECT_NAME))
            .andExpect(jsonPath("$.projectInformation").value(DEFAULT_PROJECT_INFORMATION))
            .andExpect(jsonPath("$.projectManager").value(DEFAULT_PROJECT_MANAGER))
            .andExpect(jsonPath("$.projectRequirement").value(DEFAULT_PROJECT_REQUIREMENT))
            .andExpect(jsonPath("$.lengthOfProject").value(DEFAULT_LENGTH_OF_PROJECT))
            .andExpect(jsonPath("$.newMold").value(DEFAULT_NEW_MOLD))
            .andExpect(jsonPath("$.transferMold").value(DEFAULT_TRANSFER_MOLD))
            .andExpect(jsonPath("$.contactReviewDate").value(DEFAULT_CONTACT_REVIEW_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNtQuoteCustomerProjectsByIdFiltering() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        Long id = ntQuoteCustomerProject.getId();

        defaultNtQuoteCustomerProjectFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultNtQuoteCustomerProjectFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultNtQuoteCustomerProjectFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsBySrNoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where srNo equals to
        defaultNtQuoteCustomerProjectFiltering("srNo.equals=" + DEFAULT_SR_NO, "srNo.equals=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsBySrNoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where srNo in
        defaultNtQuoteCustomerProjectFiltering("srNo.in=" + DEFAULT_SR_NO + "," + UPDATED_SR_NO, "srNo.in=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsBySrNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where srNo is not null
        defaultNtQuoteCustomerProjectFiltering("srNo.specified=true", "srNo.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsBySrNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where srNo is greater than or equal to
        defaultNtQuoteCustomerProjectFiltering("srNo.greaterThanOrEqual=" + DEFAULT_SR_NO, "srNo.greaterThanOrEqual=" + UPDATED_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsBySrNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where srNo is less than or equal to
        defaultNtQuoteCustomerProjectFiltering("srNo.lessThanOrEqual=" + DEFAULT_SR_NO, "srNo.lessThanOrEqual=" + SMALLER_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsBySrNoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where srNo is less than
        defaultNtQuoteCustomerProjectFiltering("srNo.lessThan=" + UPDATED_SR_NO, "srNo.lessThan=" + DEFAULT_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsBySrNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where srNo is greater than
        defaultNtQuoteCustomerProjectFiltering("srNo.greaterThan=" + SMALLER_SR_NO, "srNo.greaterThan=" + DEFAULT_SR_NO);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where uid equals to
        defaultNtQuoteCustomerProjectFiltering("uid.equals=" + DEFAULT_UID, "uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where uid in
        defaultNtQuoteCustomerProjectFiltering("uid.in=" + DEFAULT_UID + "," + UPDATED_UID, "uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where uid is not null
        defaultNtQuoteCustomerProjectFiltering("uid.specified=true", "uid.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByQsfIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where qsf equals to
        defaultNtQuoteCustomerProjectFiltering("qsf.equals=" + DEFAULT_QSF, "qsf.equals=" + UPDATED_QSF);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByQsfIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where qsf in
        defaultNtQuoteCustomerProjectFiltering("qsf.in=" + DEFAULT_QSF + "," + UPDATED_QSF, "qsf.in=" + UPDATED_QSF);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByQsfIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where qsf is not null
        defaultNtQuoteCustomerProjectFiltering("qsf.specified=true", "qsf.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByQsfContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where qsf contains
        defaultNtQuoteCustomerProjectFiltering("qsf.contains=" + DEFAULT_QSF, "qsf.contains=" + UPDATED_QSF);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByQsfNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where qsf does not contain
        defaultNtQuoteCustomerProjectFiltering("qsf.doesNotContain=" + UPDATED_QSF, "qsf.doesNotContain=" + DEFAULT_QSF);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByRevIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where rev equals to
        defaultNtQuoteCustomerProjectFiltering("rev.equals=" + DEFAULT_REV, "rev.equals=" + UPDATED_REV);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByRevIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where rev in
        defaultNtQuoteCustomerProjectFiltering("rev.in=" + DEFAULT_REV + "," + UPDATED_REV, "rev.in=" + UPDATED_REV);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByRevIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where rev is not null
        defaultNtQuoteCustomerProjectFiltering("rev.specified=true", "rev.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByRevContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where rev contains
        defaultNtQuoteCustomerProjectFiltering("rev.contains=" + DEFAULT_REV, "rev.contains=" + UPDATED_REV);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByRevNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where rev does not contain
        defaultNtQuoteCustomerProjectFiltering("rev.doesNotContain=" + UPDATED_REV, "rev.doesNotContain=" + DEFAULT_REV);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where date equals to
        defaultNtQuoteCustomerProjectFiltering("date.equals=" + DEFAULT_DATE, "date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where date in
        defaultNtQuoteCustomerProjectFiltering("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE, "date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where date is not null
        defaultNtQuoteCustomerProjectFiltering("date.specified=true", "date.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where date is greater than or equal to
        defaultNtQuoteCustomerProjectFiltering("date.greaterThanOrEqual=" + DEFAULT_DATE, "date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where date is less than or equal to
        defaultNtQuoteCustomerProjectFiltering("date.lessThanOrEqual=" + DEFAULT_DATE, "date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where date is less than
        defaultNtQuoteCustomerProjectFiltering("date.lessThan=" + UPDATED_DATE, "date.lessThan=" + DEFAULT_DATE);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where date is greater than
        defaultNtQuoteCustomerProjectFiltering("date.greaterThan=" + SMALLER_DATE, "date.greaterThan=" + DEFAULT_DATE);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByCustomerNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where customerName equals to
        defaultNtQuoteCustomerProjectFiltering(
            "customerName.equals=" + DEFAULT_CUSTOMER_NAME,
            "customerName.equals=" + UPDATED_CUSTOMER_NAME
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByCustomerNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where customerName in
        defaultNtQuoteCustomerProjectFiltering(
            "customerName.in=" + DEFAULT_CUSTOMER_NAME + "," + UPDATED_CUSTOMER_NAME,
            "customerName.in=" + UPDATED_CUSTOMER_NAME
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByCustomerNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where customerName is not null
        defaultNtQuoteCustomerProjectFiltering("customerName.specified=true", "customerName.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByCustomerNameContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where customerName contains
        defaultNtQuoteCustomerProjectFiltering(
            "customerName.contains=" + DEFAULT_CUSTOMER_NAME,
            "customerName.contains=" + UPDATED_CUSTOMER_NAME
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByCustomerNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where customerName does not contain
        defaultNtQuoteCustomerProjectFiltering(
            "customerName.doesNotContain=" + UPDATED_CUSTOMER_NAME,
            "customerName.doesNotContain=" + DEFAULT_CUSTOMER_NAME
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByContactNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where contactName equals to
        defaultNtQuoteCustomerProjectFiltering("contactName.equals=" + DEFAULT_CONTACT_NAME, "contactName.equals=" + UPDATED_CONTACT_NAME);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByContactNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where contactName in
        defaultNtQuoteCustomerProjectFiltering(
            "contactName.in=" + DEFAULT_CONTACT_NAME + "," + UPDATED_CONTACT_NAME,
            "contactName.in=" + UPDATED_CONTACT_NAME
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByContactNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where contactName is not null
        defaultNtQuoteCustomerProjectFiltering("contactName.specified=true", "contactName.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByContactNameContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where contactName contains
        defaultNtQuoteCustomerProjectFiltering(
            "contactName.contains=" + DEFAULT_CONTACT_NAME,
            "contactName.contains=" + UPDATED_CONTACT_NAME
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByContactNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where contactName does not contain
        defaultNtQuoteCustomerProjectFiltering(
            "contactName.doesNotContain=" + UPDATED_CONTACT_NAME,
            "contactName.doesNotContain=" + DEFAULT_CONTACT_NAME
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where phone equals to
        defaultNtQuoteCustomerProjectFiltering("phone.equals=" + DEFAULT_PHONE, "phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where phone in
        defaultNtQuoteCustomerProjectFiltering("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE, "phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where phone is not null
        defaultNtQuoteCustomerProjectFiltering("phone.specified=true", "phone.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByPhoneContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where phone contains
        defaultNtQuoteCustomerProjectFiltering("phone.contains=" + DEFAULT_PHONE, "phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where phone does not contain
        defaultNtQuoteCustomerProjectFiltering("phone.doesNotContain=" + UPDATED_PHONE, "phone.doesNotContain=" + DEFAULT_PHONE);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where email equals to
        defaultNtQuoteCustomerProjectFiltering("email.equals=" + DEFAULT_EMAIL, "email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where email in
        defaultNtQuoteCustomerProjectFiltering("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL, "email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where email is not null
        defaultNtQuoteCustomerProjectFiltering("email.specified=true", "email.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByEmailContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where email contains
        defaultNtQuoteCustomerProjectFiltering("email.contains=" + DEFAULT_EMAIL, "email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where email does not contain
        defaultNtQuoteCustomerProjectFiltering("email.doesNotContain=" + UPDATED_EMAIL, "email.doesNotContain=" + DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByOverallProjectRiskEvaluationIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where overallProjectRiskEvaluation equals to
        defaultNtQuoteCustomerProjectFiltering(
            "overallProjectRiskEvaluation.equals=" + DEFAULT_OVERALL_PROJECT_RISK_EVALUATION,
            "overallProjectRiskEvaluation.equals=" + UPDATED_OVERALL_PROJECT_RISK_EVALUATION
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByOverallProjectRiskEvaluationIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where overallProjectRiskEvaluation in
        defaultNtQuoteCustomerProjectFiltering(
            "overallProjectRiskEvaluation.in=" + DEFAULT_OVERALL_PROJECT_RISK_EVALUATION + "," + UPDATED_OVERALL_PROJECT_RISK_EVALUATION,
            "overallProjectRiskEvaluation.in=" + UPDATED_OVERALL_PROJECT_RISK_EVALUATION
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByOverallProjectRiskEvaluationIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where overallProjectRiskEvaluation is not null
        defaultNtQuoteCustomerProjectFiltering(
            "overallProjectRiskEvaluation.specified=true",
            "overallProjectRiskEvaluation.specified=false"
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByOverallProjectRiskEvaluationContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where overallProjectRiskEvaluation contains
        defaultNtQuoteCustomerProjectFiltering(
            "overallProjectRiskEvaluation.contains=" + DEFAULT_OVERALL_PROJECT_RISK_EVALUATION,
            "overallProjectRiskEvaluation.contains=" + UPDATED_OVERALL_PROJECT_RISK_EVALUATION
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByOverallProjectRiskEvaluationNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where overallProjectRiskEvaluation does not contain
        defaultNtQuoteCustomerProjectFiltering(
            "overallProjectRiskEvaluation.doesNotContain=" + UPDATED_OVERALL_PROJECT_RISK_EVALUATION,
            "overallProjectRiskEvaluation.doesNotContain=" + DEFAULT_OVERALL_PROJECT_RISK_EVALUATION
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByAssessmentDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where assessmentDate equals to
        defaultNtQuoteCustomerProjectFiltering(
            "assessmentDate.equals=" + DEFAULT_ASSESSMENT_DATE,
            "assessmentDate.equals=" + UPDATED_ASSESSMENT_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByAssessmentDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where assessmentDate in
        defaultNtQuoteCustomerProjectFiltering(
            "assessmentDate.in=" + DEFAULT_ASSESSMENT_DATE + "," + UPDATED_ASSESSMENT_DATE,
            "assessmentDate.in=" + UPDATED_ASSESSMENT_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByAssessmentDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where assessmentDate is not null
        defaultNtQuoteCustomerProjectFiltering("assessmentDate.specified=true", "assessmentDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByAssessmentDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where assessmentDate is greater than or equal to
        defaultNtQuoteCustomerProjectFiltering(
            "assessmentDate.greaterThanOrEqual=" + DEFAULT_ASSESSMENT_DATE,
            "assessmentDate.greaterThanOrEqual=" + UPDATED_ASSESSMENT_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByAssessmentDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where assessmentDate is less than or equal to
        defaultNtQuoteCustomerProjectFiltering(
            "assessmentDate.lessThanOrEqual=" + DEFAULT_ASSESSMENT_DATE,
            "assessmentDate.lessThanOrEqual=" + SMALLER_ASSESSMENT_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByAssessmentDateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where assessmentDate is less than
        defaultNtQuoteCustomerProjectFiltering(
            "assessmentDate.lessThan=" + UPDATED_ASSESSMENT_DATE,
            "assessmentDate.lessThan=" + DEFAULT_ASSESSMENT_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByAssessmentDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where assessmentDate is greater than
        defaultNtQuoteCustomerProjectFiltering(
            "assessmentDate.greaterThan=" + SMALLER_ASSESSMENT_DATE,
            "assessmentDate.greaterThan=" + DEFAULT_ASSESSMENT_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByReAssessmentDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where reAssessmentDate equals to
        defaultNtQuoteCustomerProjectFiltering(
            "reAssessmentDate.equals=" + DEFAULT_RE_ASSESSMENT_DATE,
            "reAssessmentDate.equals=" + UPDATED_RE_ASSESSMENT_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByReAssessmentDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where reAssessmentDate in
        defaultNtQuoteCustomerProjectFiltering(
            "reAssessmentDate.in=" + DEFAULT_RE_ASSESSMENT_DATE + "," + UPDATED_RE_ASSESSMENT_DATE,
            "reAssessmentDate.in=" + UPDATED_RE_ASSESSMENT_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByReAssessmentDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where reAssessmentDate is not null
        defaultNtQuoteCustomerProjectFiltering("reAssessmentDate.specified=true", "reAssessmentDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByReAssessmentDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where reAssessmentDate is greater than or equal to
        defaultNtQuoteCustomerProjectFiltering(
            "reAssessmentDate.greaterThanOrEqual=" + DEFAULT_RE_ASSESSMENT_DATE,
            "reAssessmentDate.greaterThanOrEqual=" + UPDATED_RE_ASSESSMENT_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByReAssessmentDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where reAssessmentDate is less than or equal to
        defaultNtQuoteCustomerProjectFiltering(
            "reAssessmentDate.lessThanOrEqual=" + DEFAULT_RE_ASSESSMENT_DATE,
            "reAssessmentDate.lessThanOrEqual=" + SMALLER_RE_ASSESSMENT_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByReAssessmentDateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where reAssessmentDate is less than
        defaultNtQuoteCustomerProjectFiltering(
            "reAssessmentDate.lessThan=" + UPDATED_RE_ASSESSMENT_DATE,
            "reAssessmentDate.lessThan=" + DEFAULT_RE_ASSESSMENT_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByReAssessmentDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where reAssessmentDate is greater than
        defaultNtQuoteCustomerProjectFiltering(
            "reAssessmentDate.greaterThan=" + SMALLER_RE_ASSESSMENT_DATE,
            "reAssessmentDate.greaterThan=" + DEFAULT_RE_ASSESSMENT_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByProjectNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where projectName equals to
        defaultNtQuoteCustomerProjectFiltering("projectName.equals=" + DEFAULT_PROJECT_NAME, "projectName.equals=" + UPDATED_PROJECT_NAME);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByProjectNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where projectName in
        defaultNtQuoteCustomerProjectFiltering(
            "projectName.in=" + DEFAULT_PROJECT_NAME + "," + UPDATED_PROJECT_NAME,
            "projectName.in=" + UPDATED_PROJECT_NAME
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByProjectNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where projectName is not null
        defaultNtQuoteCustomerProjectFiltering("projectName.specified=true", "projectName.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByProjectNameContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where projectName contains
        defaultNtQuoteCustomerProjectFiltering(
            "projectName.contains=" + DEFAULT_PROJECT_NAME,
            "projectName.contains=" + UPDATED_PROJECT_NAME
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByProjectNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where projectName does not contain
        defaultNtQuoteCustomerProjectFiltering(
            "projectName.doesNotContain=" + UPDATED_PROJECT_NAME,
            "projectName.doesNotContain=" + DEFAULT_PROJECT_NAME
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByProjectInformationIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where projectInformation equals to
        defaultNtQuoteCustomerProjectFiltering(
            "projectInformation.equals=" + DEFAULT_PROJECT_INFORMATION,
            "projectInformation.equals=" + UPDATED_PROJECT_INFORMATION
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByProjectInformationIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where projectInformation in
        defaultNtQuoteCustomerProjectFiltering(
            "projectInformation.in=" + DEFAULT_PROJECT_INFORMATION + "," + UPDATED_PROJECT_INFORMATION,
            "projectInformation.in=" + UPDATED_PROJECT_INFORMATION
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByProjectInformationIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where projectInformation is not null
        defaultNtQuoteCustomerProjectFiltering("projectInformation.specified=true", "projectInformation.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByProjectInformationContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where projectInformation contains
        defaultNtQuoteCustomerProjectFiltering(
            "projectInformation.contains=" + DEFAULT_PROJECT_INFORMATION,
            "projectInformation.contains=" + UPDATED_PROJECT_INFORMATION
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByProjectInformationNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where projectInformation does not contain
        defaultNtQuoteCustomerProjectFiltering(
            "projectInformation.doesNotContain=" + UPDATED_PROJECT_INFORMATION,
            "projectInformation.doesNotContain=" + DEFAULT_PROJECT_INFORMATION
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByProjectManagerIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where projectManager equals to
        defaultNtQuoteCustomerProjectFiltering(
            "projectManager.equals=" + DEFAULT_PROJECT_MANAGER,
            "projectManager.equals=" + UPDATED_PROJECT_MANAGER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByProjectManagerIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where projectManager in
        defaultNtQuoteCustomerProjectFiltering(
            "projectManager.in=" + DEFAULT_PROJECT_MANAGER + "," + UPDATED_PROJECT_MANAGER,
            "projectManager.in=" + UPDATED_PROJECT_MANAGER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByProjectManagerIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where projectManager is not null
        defaultNtQuoteCustomerProjectFiltering("projectManager.specified=true", "projectManager.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByProjectManagerContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where projectManager contains
        defaultNtQuoteCustomerProjectFiltering(
            "projectManager.contains=" + DEFAULT_PROJECT_MANAGER,
            "projectManager.contains=" + UPDATED_PROJECT_MANAGER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByProjectManagerNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where projectManager does not contain
        defaultNtQuoteCustomerProjectFiltering(
            "projectManager.doesNotContain=" + UPDATED_PROJECT_MANAGER,
            "projectManager.doesNotContain=" + DEFAULT_PROJECT_MANAGER
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByProjectRequirementIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where projectRequirement equals to
        defaultNtQuoteCustomerProjectFiltering(
            "projectRequirement.equals=" + DEFAULT_PROJECT_REQUIREMENT,
            "projectRequirement.equals=" + UPDATED_PROJECT_REQUIREMENT
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByProjectRequirementIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where projectRequirement in
        defaultNtQuoteCustomerProjectFiltering(
            "projectRequirement.in=" + DEFAULT_PROJECT_REQUIREMENT + "," + UPDATED_PROJECT_REQUIREMENT,
            "projectRequirement.in=" + UPDATED_PROJECT_REQUIREMENT
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByProjectRequirementIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where projectRequirement is not null
        defaultNtQuoteCustomerProjectFiltering("projectRequirement.specified=true", "projectRequirement.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByProjectRequirementContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where projectRequirement contains
        defaultNtQuoteCustomerProjectFiltering(
            "projectRequirement.contains=" + DEFAULT_PROJECT_REQUIREMENT,
            "projectRequirement.contains=" + UPDATED_PROJECT_REQUIREMENT
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByProjectRequirementNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where projectRequirement does not contain
        defaultNtQuoteCustomerProjectFiltering(
            "projectRequirement.doesNotContain=" + UPDATED_PROJECT_REQUIREMENT,
            "projectRequirement.doesNotContain=" + DEFAULT_PROJECT_REQUIREMENT
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByLengthOfProjectIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where lengthOfProject equals to
        defaultNtQuoteCustomerProjectFiltering(
            "lengthOfProject.equals=" + DEFAULT_LENGTH_OF_PROJECT,
            "lengthOfProject.equals=" + UPDATED_LENGTH_OF_PROJECT
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByLengthOfProjectIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where lengthOfProject in
        defaultNtQuoteCustomerProjectFiltering(
            "lengthOfProject.in=" + DEFAULT_LENGTH_OF_PROJECT + "," + UPDATED_LENGTH_OF_PROJECT,
            "lengthOfProject.in=" + UPDATED_LENGTH_OF_PROJECT
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByLengthOfProjectIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where lengthOfProject is not null
        defaultNtQuoteCustomerProjectFiltering("lengthOfProject.specified=true", "lengthOfProject.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByLengthOfProjectContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where lengthOfProject contains
        defaultNtQuoteCustomerProjectFiltering(
            "lengthOfProject.contains=" + DEFAULT_LENGTH_OF_PROJECT,
            "lengthOfProject.contains=" + UPDATED_LENGTH_OF_PROJECT
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByLengthOfProjectNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where lengthOfProject does not contain
        defaultNtQuoteCustomerProjectFiltering(
            "lengthOfProject.doesNotContain=" + UPDATED_LENGTH_OF_PROJECT,
            "lengthOfProject.doesNotContain=" + DEFAULT_LENGTH_OF_PROJECT
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByNewMoldIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where newMold equals to
        defaultNtQuoteCustomerProjectFiltering("newMold.equals=" + DEFAULT_NEW_MOLD, "newMold.equals=" + UPDATED_NEW_MOLD);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByNewMoldIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where newMold in
        defaultNtQuoteCustomerProjectFiltering("newMold.in=" + DEFAULT_NEW_MOLD + "," + UPDATED_NEW_MOLD, "newMold.in=" + UPDATED_NEW_MOLD);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByNewMoldIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where newMold is not null
        defaultNtQuoteCustomerProjectFiltering("newMold.specified=true", "newMold.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByNewMoldContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where newMold contains
        defaultNtQuoteCustomerProjectFiltering("newMold.contains=" + DEFAULT_NEW_MOLD, "newMold.contains=" + UPDATED_NEW_MOLD);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByNewMoldNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where newMold does not contain
        defaultNtQuoteCustomerProjectFiltering("newMold.doesNotContain=" + UPDATED_NEW_MOLD, "newMold.doesNotContain=" + DEFAULT_NEW_MOLD);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByTransferMoldIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where transferMold equals to
        defaultNtQuoteCustomerProjectFiltering(
            "transferMold.equals=" + DEFAULT_TRANSFER_MOLD,
            "transferMold.equals=" + UPDATED_TRANSFER_MOLD
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByTransferMoldIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where transferMold in
        defaultNtQuoteCustomerProjectFiltering(
            "transferMold.in=" + DEFAULT_TRANSFER_MOLD + "," + UPDATED_TRANSFER_MOLD,
            "transferMold.in=" + UPDATED_TRANSFER_MOLD
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByTransferMoldIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where transferMold is not null
        defaultNtQuoteCustomerProjectFiltering("transferMold.specified=true", "transferMold.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByTransferMoldContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where transferMold contains
        defaultNtQuoteCustomerProjectFiltering(
            "transferMold.contains=" + DEFAULT_TRANSFER_MOLD,
            "transferMold.contains=" + UPDATED_TRANSFER_MOLD
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByTransferMoldNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where transferMold does not contain
        defaultNtQuoteCustomerProjectFiltering(
            "transferMold.doesNotContain=" + UPDATED_TRANSFER_MOLD,
            "transferMold.doesNotContain=" + DEFAULT_TRANSFER_MOLD
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByContactReviewDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where contactReviewDate equals to
        defaultNtQuoteCustomerProjectFiltering(
            "contactReviewDate.equals=" + DEFAULT_CONTACT_REVIEW_DATE,
            "contactReviewDate.equals=" + UPDATED_CONTACT_REVIEW_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByContactReviewDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where contactReviewDate in
        defaultNtQuoteCustomerProjectFiltering(
            "contactReviewDate.in=" + DEFAULT_CONTACT_REVIEW_DATE + "," + UPDATED_CONTACT_REVIEW_DATE,
            "contactReviewDate.in=" + UPDATED_CONTACT_REVIEW_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByContactReviewDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where contactReviewDate is not null
        defaultNtQuoteCustomerProjectFiltering("contactReviewDate.specified=true", "contactReviewDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByContactReviewDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where contactReviewDate is greater than or equal to
        defaultNtQuoteCustomerProjectFiltering(
            "contactReviewDate.greaterThanOrEqual=" + DEFAULT_CONTACT_REVIEW_DATE,
            "contactReviewDate.greaterThanOrEqual=" + UPDATED_CONTACT_REVIEW_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByContactReviewDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where contactReviewDate is less than or equal to
        defaultNtQuoteCustomerProjectFiltering(
            "contactReviewDate.lessThanOrEqual=" + DEFAULT_CONTACT_REVIEW_DATE,
            "contactReviewDate.lessThanOrEqual=" + SMALLER_CONTACT_REVIEW_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByContactReviewDateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where contactReviewDate is less than
        defaultNtQuoteCustomerProjectFiltering(
            "contactReviewDate.lessThan=" + UPDATED_CONTACT_REVIEW_DATE,
            "contactReviewDate.lessThan=" + DEFAULT_CONTACT_REVIEW_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByContactReviewDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where contactReviewDate is greater than
        defaultNtQuoteCustomerProjectFiltering(
            "contactReviewDate.greaterThan=" + SMALLER_CONTACT_REVIEW_DATE,
            "contactReviewDate.greaterThan=" + DEFAULT_CONTACT_REVIEW_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where createdBy equals to
        defaultNtQuoteCustomerProjectFiltering("createdBy.equals=" + DEFAULT_CREATED_BY, "createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where createdBy in
        defaultNtQuoteCustomerProjectFiltering(
            "createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY,
            "createdBy.in=" + UPDATED_CREATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where createdBy is not null
        defaultNtQuoteCustomerProjectFiltering("createdBy.specified=true", "createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where createdBy contains
        defaultNtQuoteCustomerProjectFiltering("createdBy.contains=" + DEFAULT_CREATED_BY, "createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where createdBy does not contain
        defaultNtQuoteCustomerProjectFiltering(
            "createdBy.doesNotContain=" + UPDATED_CREATED_BY,
            "createdBy.doesNotContain=" + DEFAULT_CREATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where createdDate equals to
        defaultNtQuoteCustomerProjectFiltering("createdDate.equals=" + DEFAULT_CREATED_DATE, "createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where createdDate in
        defaultNtQuoteCustomerProjectFiltering(
            "createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE,
            "createdDate.in=" + UPDATED_CREATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where createdDate is not null
        defaultNtQuoteCustomerProjectFiltering("createdDate.specified=true", "createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where updatedBy equals to
        defaultNtQuoteCustomerProjectFiltering("updatedBy.equals=" + DEFAULT_UPDATED_BY, "updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where updatedBy in
        defaultNtQuoteCustomerProjectFiltering(
            "updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY,
            "updatedBy.in=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where updatedBy is not null
        defaultNtQuoteCustomerProjectFiltering("updatedBy.specified=true", "updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where updatedBy contains
        defaultNtQuoteCustomerProjectFiltering("updatedBy.contains=" + DEFAULT_UPDATED_BY, "updatedBy.contains=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where updatedBy does not contain
        defaultNtQuoteCustomerProjectFiltering(
            "updatedBy.doesNotContain=" + UPDATED_UPDATED_BY,
            "updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where updatedDate equals to
        defaultNtQuoteCustomerProjectFiltering("updatedDate.equals=" + DEFAULT_UPDATED_DATE, "updatedDate.equals=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where updatedDate in
        defaultNtQuoteCustomerProjectFiltering(
            "updatedDate.in=" + DEFAULT_UPDATED_DATE + "," + UPDATED_UPDATED_DATE,
            "updatedDate.in=" + UPDATED_UPDATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        // Get all the ntQuoteCustomerProjectList where updatedDate is not null
        defaultNtQuoteCustomerProjectFiltering("updatedDate.specified=true", "updatedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNtQuoteCustomerProjectsByNtQuoteIsEqualToSomething() throws Exception {
        NtQuote ntQuote;
        if (TestUtil.findAll(em, NtQuote.class).isEmpty()) {
            ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);
            ntQuote = NtQuoteResourceIT.createEntity();
        } else {
            ntQuote = TestUtil.findAll(em, NtQuote.class).get(0);
        }
        em.persist(ntQuote);
        em.flush();
        ntQuoteCustomerProject.setNtQuote(ntQuote);
        ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);
        Long ntQuoteId = ntQuote.getId();
        // Get all the ntQuoteCustomerProjectList where ntQuote equals to ntQuoteId
        defaultNtQuoteCustomerProjectShouldBeFound("ntQuoteId.equals=" + ntQuoteId);

        // Get all the ntQuoteCustomerProjectList where ntQuote equals to (ntQuoteId + 1)
        defaultNtQuoteCustomerProjectShouldNotBeFound("ntQuoteId.equals=" + (ntQuoteId + 1));
    }

    private void defaultNtQuoteCustomerProjectFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultNtQuoteCustomerProjectShouldBeFound(shouldBeFound);
        defaultNtQuoteCustomerProjectShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNtQuoteCustomerProjectShouldBeFound(String filter) throws Exception {
        restNtQuoteCustomerProjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuoteCustomerProject.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].qsf").value(hasItem(DEFAULT_QSF)))
            .andExpect(jsonPath("$.[*].rev").value(hasItem(DEFAULT_REV)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME)))
            .andExpect(jsonPath("$.[*].contactName").value(hasItem(DEFAULT_CONTACT_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].overallProjectRiskEvaluation").value(hasItem(DEFAULT_OVERALL_PROJECT_RISK_EVALUATION)))
            .andExpect(jsonPath("$.[*].assessmentDate").value(hasItem(sameInstant(DEFAULT_ASSESSMENT_DATE))))
            .andExpect(jsonPath("$.[*].reAssessmentDate").value(hasItem(sameInstant(DEFAULT_RE_ASSESSMENT_DATE))))
            .andExpect(jsonPath("$.[*].projectName").value(hasItem(DEFAULT_PROJECT_NAME)))
            .andExpect(jsonPath("$.[*].projectInformation").value(hasItem(DEFAULT_PROJECT_INFORMATION)))
            .andExpect(jsonPath("$.[*].projectManager").value(hasItem(DEFAULT_PROJECT_MANAGER)))
            .andExpect(jsonPath("$.[*].projectRequirement").value(hasItem(DEFAULT_PROJECT_REQUIREMENT)))
            .andExpect(jsonPath("$.[*].lengthOfProject").value(hasItem(DEFAULT_LENGTH_OF_PROJECT)))
            .andExpect(jsonPath("$.[*].newMold").value(hasItem(DEFAULT_NEW_MOLD)))
            .andExpect(jsonPath("$.[*].transferMold").value(hasItem(DEFAULT_TRANSFER_MOLD)))
            .andExpect(jsonPath("$.[*].contactReviewDate").value(hasItem(DEFAULT_CONTACT_REVIEW_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));

        // Check, that the count call also returns 1
        restNtQuoteCustomerProjectMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNtQuoteCustomerProjectShouldNotBeFound(String filter) throws Exception {
        restNtQuoteCustomerProjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNtQuoteCustomerProjectMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNtQuoteCustomerProject() throws Exception {
        // Get the ntQuoteCustomerProject
        restNtQuoteCustomerProjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNtQuoteCustomerProject() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        long databaseSizeBeforeUpdate = getRepositoryCount();
        ntQuoteCustomerProjectSearchRepository.save(ntQuoteCustomerProject);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerProjectSearchRepository.findAll());

        // Update the ntQuoteCustomerProject
        NtQuoteCustomerProject updatedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository
            .findById(ntQuoteCustomerProject.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedNtQuoteCustomerProject are not directly saved in db
        em.detach(updatedNtQuoteCustomerProject);
        updatedNtQuoteCustomerProject
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .qsf(UPDATED_QSF)
            .rev(UPDATED_REV)
            .date(UPDATED_DATE)
            .customerName(UPDATED_CUSTOMER_NAME)
            .contactName(UPDATED_CONTACT_NAME)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .overallProjectRiskEvaluation(UPDATED_OVERALL_PROJECT_RISK_EVALUATION)
            .assessmentDate(UPDATED_ASSESSMENT_DATE)
            .reAssessmentDate(UPDATED_RE_ASSESSMENT_DATE)
            .projectName(UPDATED_PROJECT_NAME)
            .projectInformation(UPDATED_PROJECT_INFORMATION)
            .projectManager(UPDATED_PROJECT_MANAGER)
            .projectRequirement(UPDATED_PROJECT_REQUIREMENT)
            .lengthOfProject(UPDATED_LENGTH_OF_PROJECT)
            .newMold(UPDATED_NEW_MOLD)
            .transferMold(UPDATED_TRANSFER_MOLD)
            .contactReviewDate(UPDATED_CONTACT_REVIEW_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);
        NtQuoteCustomerProjectDTO ntQuoteCustomerProjectDTO = ntQuoteCustomerProjectMapper.toDto(updatedNtQuoteCustomerProject);

        restNtQuoteCustomerProjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ntQuoteCustomerProjectDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteCustomerProjectDTO))
            )
            .andExpect(status().isOk());

        // Validate the NtQuoteCustomerProject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedNtQuoteCustomerProjectToMatchAllProperties(updatedNtQuoteCustomerProject);

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerProjectSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<NtQuoteCustomerProject> ntQuoteCustomerProjectSearchList = Streamable.of(
                    ntQuoteCustomerProjectSearchRepository.findAll()
                ).toList();
                NtQuoteCustomerProject testNtQuoteCustomerProjectSearch = ntQuoteCustomerProjectSearchList.get(searchDatabaseSizeAfter - 1);

                assertNtQuoteCustomerProjectAllPropertiesEquals(testNtQuoteCustomerProjectSearch, updatedNtQuoteCustomerProject);
            });
    }

    @Test
    @Transactional
    void putNonExistingNtQuoteCustomerProject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerProjectSearchRepository.findAll());
        ntQuoteCustomerProject.setId(longCount.incrementAndGet());

        // Create the NtQuoteCustomerProject
        NtQuoteCustomerProjectDTO ntQuoteCustomerProjectDTO = ntQuoteCustomerProjectMapper.toDto(ntQuoteCustomerProject);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNtQuoteCustomerProjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ntQuoteCustomerProjectDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteCustomerProjectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteCustomerProject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerProjectSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchNtQuoteCustomerProject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerProjectSearchRepository.findAll());
        ntQuoteCustomerProject.setId(longCount.incrementAndGet());

        // Create the NtQuoteCustomerProject
        NtQuoteCustomerProjectDTO ntQuoteCustomerProjectDTO = ntQuoteCustomerProjectMapper.toDto(ntQuoteCustomerProject);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteCustomerProjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ntQuoteCustomerProjectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteCustomerProject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerProjectSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNtQuoteCustomerProject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerProjectSearchRepository.findAll());
        ntQuoteCustomerProject.setId(longCount.incrementAndGet());

        // Create the NtQuoteCustomerProject
        NtQuoteCustomerProjectDTO ntQuoteCustomerProjectDTO = ntQuoteCustomerProjectMapper.toDto(ntQuoteCustomerProject);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteCustomerProjectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ntQuoteCustomerProjectDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NtQuoteCustomerProject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerProjectSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateNtQuoteCustomerProjectWithPatch() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ntQuoteCustomerProject using partial update
        NtQuoteCustomerProject partialUpdatedNtQuoteCustomerProject = new NtQuoteCustomerProject();
        partialUpdatedNtQuoteCustomerProject.setId(ntQuoteCustomerProject.getId());

        partialUpdatedNtQuoteCustomerProject
            .srNo(UPDATED_SR_NO)
            .date(UPDATED_DATE)
            .contactName(UPDATED_CONTACT_NAME)
            .phone(UPDATED_PHONE)
            .assessmentDate(UPDATED_ASSESSMENT_DATE)
            .projectName(UPDATED_PROJECT_NAME)
            .projectInformation(UPDATED_PROJECT_INFORMATION)
            .projectManager(UPDATED_PROJECT_MANAGER)
            .newMold(UPDATED_NEW_MOLD)
            .contactReviewDate(UPDATED_CONTACT_REVIEW_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restNtQuoteCustomerProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNtQuoteCustomerProject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNtQuoteCustomerProject))
            )
            .andExpect(status().isOk());

        // Validate the NtQuoteCustomerProject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNtQuoteCustomerProjectUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedNtQuoteCustomerProject, ntQuoteCustomerProject),
            getPersistedNtQuoteCustomerProject(ntQuoteCustomerProject)
        );
    }

    @Test
    @Transactional
    void fullUpdateNtQuoteCustomerProjectWithPatch() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ntQuoteCustomerProject using partial update
        NtQuoteCustomerProject partialUpdatedNtQuoteCustomerProject = new NtQuoteCustomerProject();
        partialUpdatedNtQuoteCustomerProject.setId(ntQuoteCustomerProject.getId());

        partialUpdatedNtQuoteCustomerProject
            .srNo(UPDATED_SR_NO)
            .uid(UPDATED_UID)
            .qsf(UPDATED_QSF)
            .rev(UPDATED_REV)
            .date(UPDATED_DATE)
            .customerName(UPDATED_CUSTOMER_NAME)
            .contactName(UPDATED_CONTACT_NAME)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .overallProjectRiskEvaluation(UPDATED_OVERALL_PROJECT_RISK_EVALUATION)
            .assessmentDate(UPDATED_ASSESSMENT_DATE)
            .reAssessmentDate(UPDATED_RE_ASSESSMENT_DATE)
            .projectName(UPDATED_PROJECT_NAME)
            .projectInformation(UPDATED_PROJECT_INFORMATION)
            .projectManager(UPDATED_PROJECT_MANAGER)
            .projectRequirement(UPDATED_PROJECT_REQUIREMENT)
            .lengthOfProject(UPDATED_LENGTH_OF_PROJECT)
            .newMold(UPDATED_NEW_MOLD)
            .transferMold(UPDATED_TRANSFER_MOLD)
            .contactReviewDate(UPDATED_CONTACT_REVIEW_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE);

        restNtQuoteCustomerProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNtQuoteCustomerProject.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNtQuoteCustomerProject))
            )
            .andExpect(status().isOk());

        // Validate the NtQuoteCustomerProject in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNtQuoteCustomerProjectUpdatableFieldsEquals(
            partialUpdatedNtQuoteCustomerProject,
            getPersistedNtQuoteCustomerProject(partialUpdatedNtQuoteCustomerProject)
        );
    }

    @Test
    @Transactional
    void patchNonExistingNtQuoteCustomerProject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerProjectSearchRepository.findAll());
        ntQuoteCustomerProject.setId(longCount.incrementAndGet());

        // Create the NtQuoteCustomerProject
        NtQuoteCustomerProjectDTO ntQuoteCustomerProjectDTO = ntQuoteCustomerProjectMapper.toDto(ntQuoteCustomerProject);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNtQuoteCustomerProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ntQuoteCustomerProjectDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ntQuoteCustomerProjectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteCustomerProject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerProjectSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNtQuoteCustomerProject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerProjectSearchRepository.findAll());
        ntQuoteCustomerProject.setId(longCount.incrementAndGet());

        // Create the NtQuoteCustomerProject
        NtQuoteCustomerProjectDTO ntQuoteCustomerProjectDTO = ntQuoteCustomerProjectMapper.toDto(ntQuoteCustomerProject);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteCustomerProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ntQuoteCustomerProjectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NtQuoteCustomerProject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerProjectSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNtQuoteCustomerProject() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerProjectSearchRepository.findAll());
        ntQuoteCustomerProject.setId(longCount.incrementAndGet());

        // Create the NtQuoteCustomerProject
        NtQuoteCustomerProjectDTO ntQuoteCustomerProjectDTO = ntQuoteCustomerProjectMapper.toDto(ntQuoteCustomerProject);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNtQuoteCustomerProjectMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ntQuoteCustomerProjectDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NtQuoteCustomerProject in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerProjectSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteNtQuoteCustomerProject() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);
        ntQuoteCustomerProjectRepository.save(ntQuoteCustomerProject);
        ntQuoteCustomerProjectSearchRepository.save(ntQuoteCustomerProject);

        long databaseSizeBeforeDelete = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ntQuoteCustomerProjectSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the ntQuoteCustomerProject
        restNtQuoteCustomerProjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, ntQuoteCustomerProject.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ntQuoteCustomerProjectSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchNtQuoteCustomerProject() throws Exception {
        // Initialize the database
        insertedNtQuoteCustomerProject = ntQuoteCustomerProjectRepository.saveAndFlush(ntQuoteCustomerProject);
        ntQuoteCustomerProjectSearchRepository.save(ntQuoteCustomerProject);

        // Search the ntQuoteCustomerProject
        restNtQuoteCustomerProjectMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + ntQuoteCustomerProject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ntQuoteCustomerProject.getId().intValue())))
            .andExpect(jsonPath("$.[*].srNo").value(hasItem(DEFAULT_SR_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].qsf").value(hasItem(DEFAULT_QSF)))
            .andExpect(jsonPath("$.[*].rev").value(hasItem(DEFAULT_REV)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME)))
            .andExpect(jsonPath("$.[*].contactName").value(hasItem(DEFAULT_CONTACT_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].overallProjectRiskEvaluation").value(hasItem(DEFAULT_OVERALL_PROJECT_RISK_EVALUATION)))
            .andExpect(jsonPath("$.[*].assessmentDate").value(hasItem(sameInstant(DEFAULT_ASSESSMENT_DATE))))
            .andExpect(jsonPath("$.[*].reAssessmentDate").value(hasItem(sameInstant(DEFAULT_RE_ASSESSMENT_DATE))))
            .andExpect(jsonPath("$.[*].projectName").value(hasItem(DEFAULT_PROJECT_NAME)))
            .andExpect(jsonPath("$.[*].projectInformation").value(hasItem(DEFAULT_PROJECT_INFORMATION)))
            .andExpect(jsonPath("$.[*].projectManager").value(hasItem(DEFAULT_PROJECT_MANAGER)))
            .andExpect(jsonPath("$.[*].projectRequirement").value(hasItem(DEFAULT_PROJECT_REQUIREMENT)))
            .andExpect(jsonPath("$.[*].lengthOfProject").value(hasItem(DEFAULT_LENGTH_OF_PROJECT)))
            .andExpect(jsonPath("$.[*].newMold").value(hasItem(DEFAULT_NEW_MOLD)))
            .andExpect(jsonPath("$.[*].transferMold").value(hasItem(DEFAULT_TRANSFER_MOLD)))
            .andExpect(jsonPath("$.[*].contactReviewDate").value(hasItem(DEFAULT_CONTACT_REVIEW_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    protected long getRepositoryCount() {
        return ntQuoteCustomerProjectRepository.count();
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

    protected NtQuoteCustomerProject getPersistedNtQuoteCustomerProject(NtQuoteCustomerProject ntQuoteCustomerProject) {
        return ntQuoteCustomerProjectRepository.findById(ntQuoteCustomerProject.getId()).orElseThrow();
    }

    protected void assertPersistedNtQuoteCustomerProjectToMatchAllProperties(NtQuoteCustomerProject expectedNtQuoteCustomerProject) {
        assertNtQuoteCustomerProjectAllPropertiesEquals(
            expectedNtQuoteCustomerProject,
            getPersistedNtQuoteCustomerProject(expectedNtQuoteCustomerProject)
        );
    }

    protected void assertPersistedNtQuoteCustomerProjectToMatchUpdatableProperties(NtQuoteCustomerProject expectedNtQuoteCustomerProject) {
        assertNtQuoteCustomerProjectAllUpdatablePropertiesEquals(
            expectedNtQuoteCustomerProject,
            getPersistedNtQuoteCustomerProject(expectedNtQuoteCustomerProject)
        );
    }
}
