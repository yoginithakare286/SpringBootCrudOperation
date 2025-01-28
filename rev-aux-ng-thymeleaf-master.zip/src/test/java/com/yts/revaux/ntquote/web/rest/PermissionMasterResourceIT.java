package com.yts.revaux.ntquote.web.rest;

import static com.yts.revaux.ntquote.domain.PermissionMasterAsserts.*;
import static com.yts.revaux.ntquote.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yts.revaux.ntquote.IntegrationTest;
import com.yts.revaux.ntquote.domain.PermissionMaster;
import com.yts.revaux.ntquote.domain.RevAuxUser;
import com.yts.revaux.ntquote.repository.PermissionMasterRepository;
import com.yts.revaux.ntquote.repository.search.PermissionMasterSearchRepository;
import com.yts.revaux.ntquote.service.dto.PermissionMasterDTO;
import com.yts.revaux.ntquote.service.mapper.PermissionMasterMapper;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
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
 * Integration tests for the {@link PermissionMasterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PermissionMasterResourceIT {

    private static final String DEFAULT_PERMISSION_GROUP = "AAAAAAAAAA";
    private static final String UPDATED_PERMISSION_GROUP = "BBBBBBBBBB";

    private static final String DEFAULT_PERMISSION = "AAAAAAAAAA";
    private static final String UPDATED_PERMISSION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/permission-masters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/permission-masters/_search";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PermissionMasterRepository permissionMasterRepository;

    @Autowired
    private PermissionMasterMapper permissionMasterMapper;

    @Autowired
    private PermissionMasterSearchRepository permissionMasterSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPermissionMasterMockMvc;

    private PermissionMaster permissionMaster;

    private PermissionMaster insertedPermissionMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PermissionMaster createEntity() {
        return new PermissionMaster().permissionGroup(DEFAULT_PERMISSION_GROUP).permission(DEFAULT_PERMISSION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PermissionMaster createUpdatedEntity() {
        return new PermissionMaster().permissionGroup(UPDATED_PERMISSION_GROUP).permission(UPDATED_PERMISSION);
    }

    @BeforeEach
    public void initTest() {
        permissionMaster = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedPermissionMaster != null) {
            permissionMasterRepository.delete(insertedPermissionMaster);
            permissionMasterSearchRepository.delete(insertedPermissionMaster);
            insertedPermissionMaster = null;
        }
    }

    @Test
    @Transactional
    void createPermissionMaster() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(permissionMasterSearchRepository.findAll());
        // Create the PermissionMaster
        PermissionMasterDTO permissionMasterDTO = permissionMasterMapper.toDto(permissionMaster);
        var returnedPermissionMasterDTO = om.readValue(
            restPermissionMasterMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(permissionMasterDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PermissionMasterDTO.class
        );

        // Validate the PermissionMaster in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPermissionMaster = permissionMasterMapper.toEntity(returnedPermissionMasterDTO);
        assertPermissionMasterUpdatableFieldsEquals(returnedPermissionMaster, getPersistedPermissionMaster(returnedPermissionMaster));

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(permissionMasterSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });

        insertedPermissionMaster = returnedPermissionMaster;
    }

    @Test
    @Transactional
    void createPermissionMasterWithExistingId() throws Exception {
        // Create the PermissionMaster with an existing ID
        permissionMaster.setId(1L);
        PermissionMasterDTO permissionMasterDTO = permissionMasterMapper.toDto(permissionMaster);

        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(permissionMasterSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restPermissionMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(permissionMasterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PermissionMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(permissionMasterSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllPermissionMasters() throws Exception {
        // Initialize the database
        insertedPermissionMaster = permissionMasterRepository.saveAndFlush(permissionMaster);

        // Get all the permissionMasterList
        restPermissionMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(permissionMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].permissionGroup").value(hasItem(DEFAULT_PERMISSION_GROUP)))
            .andExpect(jsonPath("$.[*].permission").value(hasItem(DEFAULT_PERMISSION)));
    }

    @Test
    @Transactional
    void getPermissionMaster() throws Exception {
        // Initialize the database
        insertedPermissionMaster = permissionMasterRepository.saveAndFlush(permissionMaster);

        // Get the permissionMaster
        restPermissionMasterMockMvc
            .perform(get(ENTITY_API_URL_ID, permissionMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(permissionMaster.getId().intValue()))
            .andExpect(jsonPath("$.permissionGroup").value(DEFAULT_PERMISSION_GROUP))
            .andExpect(jsonPath("$.permission").value(DEFAULT_PERMISSION));
    }

    @Test
    @Transactional
    void getPermissionMastersByIdFiltering() throws Exception {
        // Initialize the database
        insertedPermissionMaster = permissionMasterRepository.saveAndFlush(permissionMaster);

        Long id = permissionMaster.getId();

        defaultPermissionMasterFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultPermissionMasterFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultPermissionMasterFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPermissionMastersByPermissionGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPermissionMaster = permissionMasterRepository.saveAndFlush(permissionMaster);

        // Get all the permissionMasterList where permissionGroup equals to
        defaultPermissionMasterFiltering(
            "permissionGroup.equals=" + DEFAULT_PERMISSION_GROUP,
            "permissionGroup.equals=" + UPDATED_PERMISSION_GROUP
        );
    }

    @Test
    @Transactional
    void getAllPermissionMastersByPermissionGroupIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPermissionMaster = permissionMasterRepository.saveAndFlush(permissionMaster);

        // Get all the permissionMasterList where permissionGroup in
        defaultPermissionMasterFiltering(
            "permissionGroup.in=" + DEFAULT_PERMISSION_GROUP + "," + UPDATED_PERMISSION_GROUP,
            "permissionGroup.in=" + UPDATED_PERMISSION_GROUP
        );
    }

    @Test
    @Transactional
    void getAllPermissionMastersByPermissionGroupIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPermissionMaster = permissionMasterRepository.saveAndFlush(permissionMaster);

        // Get all the permissionMasterList where permissionGroup is not null
        defaultPermissionMasterFiltering("permissionGroup.specified=true", "permissionGroup.specified=false");
    }

    @Test
    @Transactional
    void getAllPermissionMastersByPermissionGroupContainsSomething() throws Exception {
        // Initialize the database
        insertedPermissionMaster = permissionMasterRepository.saveAndFlush(permissionMaster);

        // Get all the permissionMasterList where permissionGroup contains
        defaultPermissionMasterFiltering(
            "permissionGroup.contains=" + DEFAULT_PERMISSION_GROUP,
            "permissionGroup.contains=" + UPDATED_PERMISSION_GROUP
        );
    }

    @Test
    @Transactional
    void getAllPermissionMastersByPermissionGroupNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPermissionMaster = permissionMasterRepository.saveAndFlush(permissionMaster);

        // Get all the permissionMasterList where permissionGroup does not contain
        defaultPermissionMasterFiltering(
            "permissionGroup.doesNotContain=" + UPDATED_PERMISSION_GROUP,
            "permissionGroup.doesNotContain=" + DEFAULT_PERMISSION_GROUP
        );
    }

    @Test
    @Transactional
    void getAllPermissionMastersByPermissionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPermissionMaster = permissionMasterRepository.saveAndFlush(permissionMaster);

        // Get all the permissionMasterList where permission equals to
        defaultPermissionMasterFiltering("permission.equals=" + DEFAULT_PERMISSION, "permission.equals=" + UPDATED_PERMISSION);
    }

    @Test
    @Transactional
    void getAllPermissionMastersByPermissionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPermissionMaster = permissionMasterRepository.saveAndFlush(permissionMaster);

        // Get all the permissionMasterList where permission in
        defaultPermissionMasterFiltering(
            "permission.in=" + DEFAULT_PERMISSION + "," + UPDATED_PERMISSION,
            "permission.in=" + UPDATED_PERMISSION
        );
    }

    @Test
    @Transactional
    void getAllPermissionMastersByPermissionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPermissionMaster = permissionMasterRepository.saveAndFlush(permissionMaster);

        // Get all the permissionMasterList where permission is not null
        defaultPermissionMasterFiltering("permission.specified=true", "permission.specified=false");
    }

    @Test
    @Transactional
    void getAllPermissionMastersByPermissionContainsSomething() throws Exception {
        // Initialize the database
        insertedPermissionMaster = permissionMasterRepository.saveAndFlush(permissionMaster);

        // Get all the permissionMasterList where permission contains
        defaultPermissionMasterFiltering("permission.contains=" + DEFAULT_PERMISSION, "permission.contains=" + UPDATED_PERMISSION);
    }

    @Test
    @Transactional
    void getAllPermissionMastersByPermissionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPermissionMaster = permissionMasterRepository.saveAndFlush(permissionMaster);

        // Get all the permissionMasterList where permission does not contain
        defaultPermissionMasterFiltering(
            "permission.doesNotContain=" + UPDATED_PERMISSION,
            "permission.doesNotContain=" + DEFAULT_PERMISSION
        );
    }

    @Test
    @Transactional
    void getAllPermissionMastersByRevAuxUserIsEqualToSomething() throws Exception {
        RevAuxUser revAuxUser;
        if (TestUtil.findAll(em, RevAuxUser.class).isEmpty()) {
            permissionMasterRepository.saveAndFlush(permissionMaster);
            revAuxUser = RevAuxUserResourceIT.createEntity(em);
        } else {
            revAuxUser = TestUtil.findAll(em, RevAuxUser.class).get(0);
        }
        em.persist(revAuxUser);
        em.flush();
        permissionMaster.setRevAuxUser(revAuxUser);
        permissionMasterRepository.saveAndFlush(permissionMaster);
        Long revAuxUserId = revAuxUser.getId();
        // Get all the permissionMasterList where revAuxUser equals to revAuxUserId
        defaultPermissionMasterShouldBeFound("revAuxUserId.equals=" + revAuxUserId);

        // Get all the permissionMasterList where revAuxUser equals to (revAuxUserId + 1)
        defaultPermissionMasterShouldNotBeFound("revAuxUserId.equals=" + (revAuxUserId + 1));
    }

    private void defaultPermissionMasterFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultPermissionMasterShouldBeFound(shouldBeFound);
        defaultPermissionMasterShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPermissionMasterShouldBeFound(String filter) throws Exception {
        restPermissionMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(permissionMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].permissionGroup").value(hasItem(DEFAULT_PERMISSION_GROUP)))
            .andExpect(jsonPath("$.[*].permission").value(hasItem(DEFAULT_PERMISSION)));

        // Check, that the count call also returns 1
        restPermissionMasterMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPermissionMasterShouldNotBeFound(String filter) throws Exception {
        restPermissionMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPermissionMasterMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPermissionMaster() throws Exception {
        // Get the permissionMaster
        restPermissionMasterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPermissionMaster() throws Exception {
        // Initialize the database
        insertedPermissionMaster = permissionMasterRepository.saveAndFlush(permissionMaster);

        long databaseSizeBeforeUpdate = getRepositoryCount();
        permissionMasterSearchRepository.save(permissionMaster);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(permissionMasterSearchRepository.findAll());

        // Update the permissionMaster
        PermissionMaster updatedPermissionMaster = permissionMasterRepository.findById(permissionMaster.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPermissionMaster are not directly saved in db
        em.detach(updatedPermissionMaster);
        updatedPermissionMaster.permissionGroup(UPDATED_PERMISSION_GROUP).permission(UPDATED_PERMISSION);
        PermissionMasterDTO permissionMasterDTO = permissionMasterMapper.toDto(updatedPermissionMaster);

        restPermissionMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, permissionMasterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(permissionMasterDTO))
            )
            .andExpect(status().isOk());

        // Validate the PermissionMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPermissionMasterToMatchAllProperties(updatedPermissionMaster);

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(permissionMasterSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<PermissionMaster> permissionMasterSearchList = Streamable.of(permissionMasterSearchRepository.findAll()).toList();
                PermissionMaster testPermissionMasterSearch = permissionMasterSearchList.get(searchDatabaseSizeAfter - 1);

                assertPermissionMasterAllPropertiesEquals(testPermissionMasterSearch, updatedPermissionMaster);
            });
    }

    @Test
    @Transactional
    void putNonExistingPermissionMaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(permissionMasterSearchRepository.findAll());
        permissionMaster.setId(longCount.incrementAndGet());

        // Create the PermissionMaster
        PermissionMasterDTO permissionMasterDTO = permissionMasterMapper.toDto(permissionMaster);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPermissionMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, permissionMasterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(permissionMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PermissionMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(permissionMasterSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchPermissionMaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(permissionMasterSearchRepository.findAll());
        permissionMaster.setId(longCount.incrementAndGet());

        // Create the PermissionMaster
        PermissionMasterDTO permissionMasterDTO = permissionMasterMapper.toDto(permissionMaster);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPermissionMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(permissionMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PermissionMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(permissionMasterSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPermissionMaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(permissionMasterSearchRepository.findAll());
        permissionMaster.setId(longCount.incrementAndGet());

        // Create the PermissionMaster
        PermissionMasterDTO permissionMasterDTO = permissionMasterMapper.toDto(permissionMaster);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPermissionMasterMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(permissionMasterDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PermissionMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(permissionMasterSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdatePermissionMasterWithPatch() throws Exception {
        // Initialize the database
        insertedPermissionMaster = permissionMasterRepository.saveAndFlush(permissionMaster);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the permissionMaster using partial update
        PermissionMaster partialUpdatedPermissionMaster = new PermissionMaster();
        partialUpdatedPermissionMaster.setId(permissionMaster.getId());

        partialUpdatedPermissionMaster.permissionGroup(UPDATED_PERMISSION_GROUP).permission(UPDATED_PERMISSION);

        restPermissionMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPermissionMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPermissionMaster))
            )
            .andExpect(status().isOk());

        // Validate the PermissionMaster in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPermissionMasterUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPermissionMaster, permissionMaster),
            getPersistedPermissionMaster(permissionMaster)
        );
    }

    @Test
    @Transactional
    void fullUpdatePermissionMasterWithPatch() throws Exception {
        // Initialize the database
        insertedPermissionMaster = permissionMasterRepository.saveAndFlush(permissionMaster);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the permissionMaster using partial update
        PermissionMaster partialUpdatedPermissionMaster = new PermissionMaster();
        partialUpdatedPermissionMaster.setId(permissionMaster.getId());

        partialUpdatedPermissionMaster.permissionGroup(UPDATED_PERMISSION_GROUP).permission(UPDATED_PERMISSION);

        restPermissionMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPermissionMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPermissionMaster))
            )
            .andExpect(status().isOk());

        // Validate the PermissionMaster in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPermissionMasterUpdatableFieldsEquals(
            partialUpdatedPermissionMaster,
            getPersistedPermissionMaster(partialUpdatedPermissionMaster)
        );
    }

    @Test
    @Transactional
    void patchNonExistingPermissionMaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(permissionMasterSearchRepository.findAll());
        permissionMaster.setId(longCount.incrementAndGet());

        // Create the PermissionMaster
        PermissionMasterDTO permissionMasterDTO = permissionMasterMapper.toDto(permissionMaster);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPermissionMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, permissionMasterDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(permissionMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PermissionMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(permissionMasterSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPermissionMaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(permissionMasterSearchRepository.findAll());
        permissionMaster.setId(longCount.incrementAndGet());

        // Create the PermissionMaster
        PermissionMasterDTO permissionMasterDTO = permissionMasterMapper.toDto(permissionMaster);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPermissionMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(permissionMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PermissionMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(permissionMasterSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPermissionMaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(permissionMasterSearchRepository.findAll());
        permissionMaster.setId(longCount.incrementAndGet());

        // Create the PermissionMaster
        PermissionMasterDTO permissionMasterDTO = permissionMasterMapper.toDto(permissionMaster);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPermissionMasterMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(permissionMasterDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PermissionMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(permissionMasterSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deletePermissionMaster() throws Exception {
        // Initialize the database
        insertedPermissionMaster = permissionMasterRepository.saveAndFlush(permissionMaster);
        permissionMasterRepository.save(permissionMaster);
        permissionMasterSearchRepository.save(permissionMaster);

        long databaseSizeBeforeDelete = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(permissionMasterSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the permissionMaster
        restPermissionMasterMockMvc
            .perform(delete(ENTITY_API_URL_ID, permissionMaster.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(permissionMasterSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchPermissionMaster() throws Exception {
        // Initialize the database
        insertedPermissionMaster = permissionMasterRepository.saveAndFlush(permissionMaster);
        permissionMasterSearchRepository.save(permissionMaster);

        // Search the permissionMaster
        restPermissionMasterMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + permissionMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(permissionMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].permissionGroup").value(hasItem(DEFAULT_PERMISSION_GROUP)))
            .andExpect(jsonPath("$.[*].permission").value(hasItem(DEFAULT_PERMISSION)));
    }

    protected long getRepositoryCount() {
        return permissionMasterRepository.count();
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

    protected PermissionMaster getPersistedPermissionMaster(PermissionMaster permissionMaster) {
        return permissionMasterRepository.findById(permissionMaster.getId()).orElseThrow();
    }

    protected void assertPersistedPermissionMasterToMatchAllProperties(PermissionMaster expectedPermissionMaster) {
        assertPermissionMasterAllPropertiesEquals(expectedPermissionMaster, getPersistedPermissionMaster(expectedPermissionMaster));
    }

    protected void assertPersistedPermissionMasterToMatchUpdatableProperties(PermissionMaster expectedPermissionMaster) {
        assertPermissionMasterAllUpdatablePropertiesEquals(
            expectedPermissionMaster,
            getPersistedPermissionMaster(expectedPermissionMaster)
        );
    }
}
