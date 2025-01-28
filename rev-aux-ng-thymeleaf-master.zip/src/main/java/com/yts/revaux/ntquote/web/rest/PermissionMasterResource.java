package com.yts.revaux.ntquote.web.rest;

import com.yts.revaux.ntquote.repository.PermissionMasterRepository;
import com.yts.revaux.ntquote.service.PermissionMasterQueryService;
import com.yts.revaux.ntquote.service.PermissionMasterService;
import com.yts.revaux.ntquote.service.criteria.PermissionMasterCriteria;
import com.yts.revaux.ntquote.service.dto.PermissionMasterDTO;
import com.yts.revaux.ntquote.web.rest.errors.BadRequestAlertException;
import com.yts.revaux.ntquote.web.rest.errors.ElasticsearchExceptionMapper;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.yts.revaux.ntquote.domain.PermissionMaster}.
 */
@RestController
@RequestMapping("/api/permission-masters")
public class PermissionMasterResource {

    private static final Logger LOG = LoggerFactory.getLogger(PermissionMasterResource.class);

    private static final String ENTITY_NAME = "permissionMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PermissionMasterService permissionMasterService;

    private final PermissionMasterRepository permissionMasterRepository;

    private final PermissionMasterQueryService permissionMasterQueryService;

    public PermissionMasterResource(
        PermissionMasterService permissionMasterService,
        PermissionMasterRepository permissionMasterRepository,
        PermissionMasterQueryService permissionMasterQueryService
    ) {
        this.permissionMasterService = permissionMasterService;
        this.permissionMasterRepository = permissionMasterRepository;
        this.permissionMasterQueryService = permissionMasterQueryService;
    }

    /**
     * {@code POST  /permission-masters} : Create a new permissionMaster.
     *
     * @param permissionMasterDTO the permissionMasterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new permissionMasterDTO, or with status {@code 400 (Bad Request)} if the permissionMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PermissionMasterDTO> createPermissionMaster(@RequestBody PermissionMasterDTO permissionMasterDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save PermissionMaster : {}", permissionMasterDTO);
        if (permissionMasterDTO.getId() != null) {
            throw new BadRequestAlertException("A new permissionMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        permissionMasterDTO = permissionMasterService.save(permissionMasterDTO);
        return ResponseEntity.created(new URI("/api/permission-masters/" + permissionMasterDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, permissionMasterDTO.getId().toString()))
            .body(permissionMasterDTO);
    }

    /**
     * {@code PUT  /permission-masters/:id} : Updates an existing permissionMaster.
     *
     * @param id the id of the permissionMasterDTO to save.
     * @param permissionMasterDTO the permissionMasterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated permissionMasterDTO,
     * or with status {@code 400 (Bad Request)} if the permissionMasterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the permissionMasterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PermissionMasterDTO> updatePermissionMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PermissionMasterDTO permissionMasterDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update PermissionMaster : {}, {}", id, permissionMasterDTO);
        if (permissionMasterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, permissionMasterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!permissionMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        permissionMasterDTO = permissionMasterService.update(permissionMasterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, permissionMasterDTO.getId().toString()))
            .body(permissionMasterDTO);
    }

    /**
     * {@code PATCH  /permission-masters/:id} : Partial updates given fields of an existing permissionMaster, field will ignore if it is null
     *
     * @param id the id of the permissionMasterDTO to save.
     * @param permissionMasterDTO the permissionMasterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated permissionMasterDTO,
     * or with status {@code 400 (Bad Request)} if the permissionMasterDTO is not valid,
     * or with status {@code 404 (Not Found)} if the permissionMasterDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the permissionMasterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PermissionMasterDTO> partialUpdatePermissionMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PermissionMasterDTO permissionMasterDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update PermissionMaster partially : {}, {}", id, permissionMasterDTO);
        if (permissionMasterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, permissionMasterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!permissionMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PermissionMasterDTO> result = permissionMasterService.partialUpdate(permissionMasterDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, permissionMasterDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /permission-masters} : get all the permissionMasters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of permissionMasters in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PermissionMasterDTO>> getAllPermissionMasters(
        PermissionMasterCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get PermissionMasters by criteria: {}", criteria);

        Page<PermissionMasterDTO> page = permissionMasterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /permission-masters/count} : count all the permissionMasters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countPermissionMasters(PermissionMasterCriteria criteria) {
        LOG.debug("REST request to count PermissionMasters by criteria: {}", criteria);
        return ResponseEntity.ok().body(permissionMasterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /permission-masters/:id} : get the "id" permissionMaster.
     *
     * @param id the id of the permissionMasterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the permissionMasterDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PermissionMasterDTO> getPermissionMaster(@PathVariable("id") Long id) {
        LOG.debug("REST request to get PermissionMaster : {}", id);
        Optional<PermissionMasterDTO> permissionMasterDTO = permissionMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(permissionMasterDTO);
    }

    /**
     * {@code DELETE  /permission-masters/:id} : delete the "id" permissionMaster.
     *
     * @param id the id of the permissionMasterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePermissionMaster(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete PermissionMaster : {}", id);
        permissionMasterService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /permission-masters/_search?query=:query} : search for the permissionMaster corresponding
     * to the query.
     *
     * @param query the query of the permissionMaster search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search")
    public ResponseEntity<List<PermissionMasterDTO>> searchPermissionMasters(
        @RequestParam("query") String query,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to search for a page of PermissionMasters for query {}", query);
        try {
            Page<PermissionMasterDTO> page = permissionMasterService.search(query, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (RuntimeException e) {
            throw ElasticsearchExceptionMapper.mapException(e);
        }
    }
}
