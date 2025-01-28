package com.yts.revaux.ntquote.web.rest;

import com.yts.revaux.ntquote.repository.NtProjectApprovalRepository;
import com.yts.revaux.ntquote.service.NtProjectApprovalQueryService;
import com.yts.revaux.ntquote.service.NtProjectApprovalService;
import com.yts.revaux.ntquote.service.criteria.NtProjectApprovalCriteria;
import com.yts.revaux.ntquote.service.dto.NtProjectApprovalDTO;
import com.yts.revaux.ntquote.web.rest.errors.BadRequestAlertException;
import com.yts.revaux.ntquote.web.rest.errors.ElasticsearchExceptionMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.yts.revaux.ntquote.domain.NtProjectApproval}.
 */
@RestController
@RequestMapping("/api/nt-project-approvals")
public class NtProjectApprovalResource {

    private static final Logger LOG = LoggerFactory.getLogger(NtProjectApprovalResource.class);

    private static final String ENTITY_NAME = "ntProjectApproval";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NtProjectApprovalService ntProjectApprovalService;

    private final NtProjectApprovalRepository ntProjectApprovalRepository;

    private final NtProjectApprovalQueryService ntProjectApprovalQueryService;

    public NtProjectApprovalResource(
        NtProjectApprovalService ntProjectApprovalService,
        NtProjectApprovalRepository ntProjectApprovalRepository,
        NtProjectApprovalQueryService ntProjectApprovalQueryService
    ) {
        this.ntProjectApprovalService = ntProjectApprovalService;
        this.ntProjectApprovalRepository = ntProjectApprovalRepository;
        this.ntProjectApprovalQueryService = ntProjectApprovalQueryService;
    }

    /**
     * {@code POST  /nt-project-approvals} : Create a new ntProjectApproval.
     *
     * @param ntProjectApprovalDTO the ntProjectApprovalDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ntProjectApprovalDTO, or with status {@code 400 (Bad Request)} if the ntProjectApproval has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<NtProjectApprovalDTO> createNtProjectApproval(@Valid @RequestBody NtProjectApprovalDTO ntProjectApprovalDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save NtProjectApproval : {}", ntProjectApprovalDTO);
        if (ntProjectApprovalDTO.getId() != null) {
            throw new BadRequestAlertException("A new ntProjectApproval cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ntProjectApprovalDTO = ntProjectApprovalService.save(ntProjectApprovalDTO);
        return ResponseEntity.created(new URI("/api/nt-project-approvals/" + ntProjectApprovalDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, ntProjectApprovalDTO.getId().toString()))
            .body(ntProjectApprovalDTO);
    }

    /**
     * {@code PUT  /nt-project-approvals/:id} : Updates an existing ntProjectApproval.
     *
     * @param id the id of the ntProjectApprovalDTO to save.
     * @param ntProjectApprovalDTO the ntProjectApprovalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ntProjectApprovalDTO,
     * or with status {@code 400 (Bad Request)} if the ntProjectApprovalDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ntProjectApprovalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<NtProjectApprovalDTO> updateNtProjectApproval(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NtProjectApprovalDTO ntProjectApprovalDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update NtProjectApproval : {}, {}", id, ntProjectApprovalDTO);
        if (ntProjectApprovalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ntProjectApprovalDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ntProjectApprovalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ntProjectApprovalDTO = ntProjectApprovalService.update(ntProjectApprovalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ntProjectApprovalDTO.getId().toString()))
            .body(ntProjectApprovalDTO);
    }

    /**
     * {@code PATCH  /nt-project-approvals/:id} : Partial updates given fields of an existing ntProjectApproval, field will ignore if it is null
     *
     * @param id the id of the ntProjectApprovalDTO to save.
     * @param ntProjectApprovalDTO the ntProjectApprovalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ntProjectApprovalDTO,
     * or with status {@code 400 (Bad Request)} if the ntProjectApprovalDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ntProjectApprovalDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ntProjectApprovalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NtProjectApprovalDTO> partialUpdateNtProjectApproval(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NtProjectApprovalDTO ntProjectApprovalDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update NtProjectApproval partially : {}, {}", id, ntProjectApprovalDTO);
        if (ntProjectApprovalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ntProjectApprovalDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ntProjectApprovalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NtProjectApprovalDTO> result = ntProjectApprovalService.partialUpdate(ntProjectApprovalDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ntProjectApprovalDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /nt-project-approvals} : get all the ntProjectApprovals.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ntProjectApprovals in body.
     */
    @GetMapping("")
    public ResponseEntity<List<NtProjectApprovalDTO>> getAllNtProjectApprovals(
        NtProjectApprovalCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get NtProjectApprovals by criteria: {}", criteria);

        Page<NtProjectApprovalDTO> page = ntProjectApprovalQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nt-project-approvals/count} : count all the ntProjectApprovals.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countNtProjectApprovals(NtProjectApprovalCriteria criteria) {
        LOG.debug("REST request to count NtProjectApprovals by criteria: {}", criteria);
        return ResponseEntity.ok().body(ntProjectApprovalQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /nt-project-approvals/:id} : get the "id" ntProjectApproval.
     *
     * @param id the id of the ntProjectApprovalDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ntProjectApprovalDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<NtProjectApprovalDTO> getNtProjectApproval(@PathVariable("id") Long id) {
        LOG.debug("REST request to get NtProjectApproval : {}", id);
        Optional<NtProjectApprovalDTO> ntProjectApprovalDTO = ntProjectApprovalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ntProjectApprovalDTO);
    }

    /**
     * {@code DELETE  /nt-project-approvals/:id} : delete the "id" ntProjectApproval.
     *
     * @param id the id of the ntProjectApprovalDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNtProjectApproval(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete NtProjectApproval : {}", id);
        ntProjectApprovalService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /nt-project-approvals/_search?query=:query} : search for the ntProjectApproval corresponding
     * to the query.
     *
     * @param query the query of the ntProjectApproval search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search")
    public ResponseEntity<List<NtProjectApprovalDTO>> searchNtProjectApprovals(
        @RequestParam("query") String query,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to search for a page of NtProjectApprovals for query {}", query);
        try {
            Page<NtProjectApprovalDTO> page = ntProjectApprovalService.search(query, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (RuntimeException e) {
            throw ElasticsearchExceptionMapper.mapException(e);
        }
    }
}
