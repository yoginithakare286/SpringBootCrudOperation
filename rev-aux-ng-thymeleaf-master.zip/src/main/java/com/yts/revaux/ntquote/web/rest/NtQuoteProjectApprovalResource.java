package com.yts.revaux.ntquote.web.rest;

import com.yts.revaux.ntquote.repository.NtQuoteProjectApprovalRepository;
import com.yts.revaux.ntquote.service.NtQuoteProjectApprovalQueryService;
import com.yts.revaux.ntquote.service.NtQuoteProjectApprovalService;
import com.yts.revaux.ntquote.service.criteria.NtQuoteProjectApprovalCriteria;
import com.yts.revaux.ntquote.service.dto.NtQuoteProjectApprovalDTO;
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
 * REST controller for managing {@link com.yts.revaux.ntquote.domain.NtQuoteProjectApproval}.
 */
@RestController
@RequestMapping("/api/nt-quote-project-approvals")
public class NtQuoteProjectApprovalResource {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuoteProjectApprovalResource.class);

    private static final String ENTITY_NAME = "ntQuoteProjectApproval";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NtQuoteProjectApprovalService ntQuoteProjectApprovalService;

    private final NtQuoteProjectApprovalRepository ntQuoteProjectApprovalRepository;

    private final NtQuoteProjectApprovalQueryService ntQuoteProjectApprovalQueryService;

    public NtQuoteProjectApprovalResource(
        NtQuoteProjectApprovalService ntQuoteProjectApprovalService,
        NtQuoteProjectApprovalRepository ntQuoteProjectApprovalRepository,
        NtQuoteProjectApprovalQueryService ntQuoteProjectApprovalQueryService
    ) {
        this.ntQuoteProjectApprovalService = ntQuoteProjectApprovalService;
        this.ntQuoteProjectApprovalRepository = ntQuoteProjectApprovalRepository;
        this.ntQuoteProjectApprovalQueryService = ntQuoteProjectApprovalQueryService;
    }

    /**
     * {@code POST  /nt-quote-project-approvals} : Create a new ntQuoteProjectApproval.
     *
     * @param ntQuoteProjectApprovalDTO the ntQuoteProjectApprovalDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ntQuoteProjectApprovalDTO, or with status {@code 400 (Bad Request)} if the ntQuoteProjectApproval has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<NtQuoteProjectApprovalDTO> createNtQuoteProjectApproval(
        @Valid @RequestBody NtQuoteProjectApprovalDTO ntQuoteProjectApprovalDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save NtQuoteProjectApproval : {}", ntQuoteProjectApprovalDTO);
        if (ntQuoteProjectApprovalDTO.getId() != null) {
            throw new BadRequestAlertException("A new ntQuoteProjectApproval cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ntQuoteProjectApprovalDTO = ntQuoteProjectApprovalService.save(ntQuoteProjectApprovalDTO);
        return ResponseEntity.created(new URI("/api/nt-quote-project-approvals/" + ntQuoteProjectApprovalDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, ntQuoteProjectApprovalDTO.getId().toString()))
            .body(ntQuoteProjectApprovalDTO);
    }

    /**
     * {@code PUT  /nt-quote-project-approvals/:id} : Updates an existing ntQuoteProjectApproval.
     *
     * @param id the id of the ntQuoteProjectApprovalDTO to save.
     * @param ntQuoteProjectApprovalDTO the ntQuoteProjectApprovalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ntQuoteProjectApprovalDTO,
     * or with status {@code 400 (Bad Request)} if the ntQuoteProjectApprovalDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ntQuoteProjectApprovalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<NtQuoteProjectApprovalDTO> updateNtQuoteProjectApproval(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NtQuoteProjectApprovalDTO ntQuoteProjectApprovalDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update NtQuoteProjectApproval : {}, {}", id, ntQuoteProjectApprovalDTO);
        if (ntQuoteProjectApprovalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ntQuoteProjectApprovalDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ntQuoteProjectApprovalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ntQuoteProjectApprovalDTO = ntQuoteProjectApprovalService.update(ntQuoteProjectApprovalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ntQuoteProjectApprovalDTO.getId().toString()))
            .body(ntQuoteProjectApprovalDTO);
    }

    /**
     * {@code PATCH  /nt-quote-project-approvals/:id} : Partial updates given fields of an existing ntQuoteProjectApproval, field will ignore if it is null
     *
     * @param id the id of the ntQuoteProjectApprovalDTO to save.
     * @param ntQuoteProjectApprovalDTO the ntQuoteProjectApprovalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ntQuoteProjectApprovalDTO,
     * or with status {@code 400 (Bad Request)} if the ntQuoteProjectApprovalDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ntQuoteProjectApprovalDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ntQuoteProjectApprovalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NtQuoteProjectApprovalDTO> partialUpdateNtQuoteProjectApproval(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NtQuoteProjectApprovalDTO ntQuoteProjectApprovalDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update NtQuoteProjectApproval partially : {}, {}", id, ntQuoteProjectApprovalDTO);
        if (ntQuoteProjectApprovalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ntQuoteProjectApprovalDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ntQuoteProjectApprovalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NtQuoteProjectApprovalDTO> result = ntQuoteProjectApprovalService.partialUpdate(ntQuoteProjectApprovalDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ntQuoteProjectApprovalDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /nt-quote-project-approvals} : get all the ntQuoteProjectApprovals.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ntQuoteProjectApprovals in body.
     */
    @GetMapping("")
    public ResponseEntity<List<NtQuoteProjectApprovalDTO>> getAllNtQuoteProjectApprovals(
        NtQuoteProjectApprovalCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get NtQuoteProjectApprovals by criteria: {}", criteria);

        Page<NtQuoteProjectApprovalDTO> page = ntQuoteProjectApprovalQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nt-quote-project-approvals/count} : count all the ntQuoteProjectApprovals.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countNtQuoteProjectApprovals(NtQuoteProjectApprovalCriteria criteria) {
        LOG.debug("REST request to count NtQuoteProjectApprovals by criteria: {}", criteria);
        return ResponseEntity.ok().body(ntQuoteProjectApprovalQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /nt-quote-project-approvals/:id} : get the "id" ntQuoteProjectApproval.
     *
     * @param id the id of the ntQuoteProjectApprovalDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ntQuoteProjectApprovalDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<NtQuoteProjectApprovalDTO> getNtQuoteProjectApproval(@PathVariable("id") Long id) {
        LOG.debug("REST request to get NtQuoteProjectApproval : {}", id);
        Optional<NtQuoteProjectApprovalDTO> ntQuoteProjectApprovalDTO = ntQuoteProjectApprovalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ntQuoteProjectApprovalDTO);
    }

    /**
     * {@code DELETE  /nt-quote-project-approvals/:id} : delete the "id" ntQuoteProjectApproval.
     *
     * @param id the id of the ntQuoteProjectApprovalDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNtQuoteProjectApproval(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete NtQuoteProjectApproval : {}", id);
        ntQuoteProjectApprovalService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /nt-quote-project-approvals/_search?query=:query} : search for the ntQuoteProjectApproval corresponding
     * to the query.
     *
     * @param query the query of the ntQuoteProjectApproval search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search")
    public ResponseEntity<List<NtQuoteProjectApprovalDTO>> searchNtQuoteProjectApprovals(
        @RequestParam("query") String query,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to search for a page of NtQuoteProjectApprovals for query {}", query);
        try {
            Page<NtQuoteProjectApprovalDTO> page = ntQuoteProjectApprovalService.search(query, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (RuntimeException e) {
            throw ElasticsearchExceptionMapper.mapException(e);
        }
    }
}
