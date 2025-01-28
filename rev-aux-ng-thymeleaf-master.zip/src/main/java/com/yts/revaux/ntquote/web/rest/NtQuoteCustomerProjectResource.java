package com.yts.revaux.ntquote.web.rest;

import com.yts.revaux.ntquote.repository.NtQuoteCustomerProjectRepository;
import com.yts.revaux.ntquote.service.NtQuoteCustomerProjectQueryService;
import com.yts.revaux.ntquote.service.NtQuoteCustomerProjectService;
import com.yts.revaux.ntquote.service.criteria.NtQuoteCustomerProjectCriteria;
import com.yts.revaux.ntquote.service.dto.NtQuoteCustomerProjectDTO;
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
 * REST controller for managing {@link com.yts.revaux.ntquote.domain.NtQuoteCustomerProject}.
 */
@RestController
@RequestMapping("/api/nt-quote-customer-projects")
public class NtQuoteCustomerProjectResource {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuoteCustomerProjectResource.class);

    private static final String ENTITY_NAME = "ntQuoteCustomerProject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NtQuoteCustomerProjectService ntQuoteCustomerProjectService;

    private final NtQuoteCustomerProjectRepository ntQuoteCustomerProjectRepository;

    private final NtQuoteCustomerProjectQueryService ntQuoteCustomerProjectQueryService;

    public NtQuoteCustomerProjectResource(
        NtQuoteCustomerProjectService ntQuoteCustomerProjectService,
        NtQuoteCustomerProjectRepository ntQuoteCustomerProjectRepository,
        NtQuoteCustomerProjectQueryService ntQuoteCustomerProjectQueryService
    ) {
        this.ntQuoteCustomerProjectService = ntQuoteCustomerProjectService;
        this.ntQuoteCustomerProjectRepository = ntQuoteCustomerProjectRepository;
        this.ntQuoteCustomerProjectQueryService = ntQuoteCustomerProjectQueryService;
    }

    /**
     * {@code POST  /nt-quote-customer-projects} : Create a new ntQuoteCustomerProject.
     *
     * @param ntQuoteCustomerProjectDTO the ntQuoteCustomerProjectDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ntQuoteCustomerProjectDTO, or with status {@code 400 (Bad Request)} if the ntQuoteCustomerProject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<NtQuoteCustomerProjectDTO> createNtQuoteCustomerProject(
        @Valid @RequestBody NtQuoteCustomerProjectDTO ntQuoteCustomerProjectDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save NtQuoteCustomerProject : {}", ntQuoteCustomerProjectDTO);
        if (ntQuoteCustomerProjectDTO.getId() != null) {
            throw new BadRequestAlertException("A new ntQuoteCustomerProject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ntQuoteCustomerProjectDTO = ntQuoteCustomerProjectService.save(ntQuoteCustomerProjectDTO);
        return ResponseEntity.created(new URI("/api/nt-quote-customer-projects/" + ntQuoteCustomerProjectDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, ntQuoteCustomerProjectDTO.getId().toString()))
            .body(ntQuoteCustomerProjectDTO);
    }

    /**
     * {@code PUT  /nt-quote-customer-projects/:id} : Updates an existing ntQuoteCustomerProject.
     *
     * @param id the id of the ntQuoteCustomerProjectDTO to save.
     * @param ntQuoteCustomerProjectDTO the ntQuoteCustomerProjectDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ntQuoteCustomerProjectDTO,
     * or with status {@code 400 (Bad Request)} if the ntQuoteCustomerProjectDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ntQuoteCustomerProjectDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<NtQuoteCustomerProjectDTO> updateNtQuoteCustomerProject(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NtQuoteCustomerProjectDTO ntQuoteCustomerProjectDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update NtQuoteCustomerProject : {}, {}", id, ntQuoteCustomerProjectDTO);
        if (ntQuoteCustomerProjectDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ntQuoteCustomerProjectDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ntQuoteCustomerProjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ntQuoteCustomerProjectDTO = ntQuoteCustomerProjectService.update(ntQuoteCustomerProjectDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ntQuoteCustomerProjectDTO.getId().toString()))
            .body(ntQuoteCustomerProjectDTO);
    }

    /**
     * {@code PATCH  /nt-quote-customer-projects/:id} : Partial updates given fields of an existing ntQuoteCustomerProject, field will ignore if it is null
     *
     * @param id the id of the ntQuoteCustomerProjectDTO to save.
     * @param ntQuoteCustomerProjectDTO the ntQuoteCustomerProjectDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ntQuoteCustomerProjectDTO,
     * or with status {@code 400 (Bad Request)} if the ntQuoteCustomerProjectDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ntQuoteCustomerProjectDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ntQuoteCustomerProjectDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NtQuoteCustomerProjectDTO> partialUpdateNtQuoteCustomerProject(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NtQuoteCustomerProjectDTO ntQuoteCustomerProjectDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update NtQuoteCustomerProject partially : {}, {}", id, ntQuoteCustomerProjectDTO);
        if (ntQuoteCustomerProjectDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ntQuoteCustomerProjectDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ntQuoteCustomerProjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NtQuoteCustomerProjectDTO> result = ntQuoteCustomerProjectService.partialUpdate(ntQuoteCustomerProjectDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ntQuoteCustomerProjectDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /nt-quote-customer-projects} : get all the ntQuoteCustomerProjects.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ntQuoteCustomerProjects in body.
     */
    @GetMapping("")
    public ResponseEntity<List<NtQuoteCustomerProjectDTO>> getAllNtQuoteCustomerProjects(
        NtQuoteCustomerProjectCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get NtQuoteCustomerProjects by criteria: {}", criteria);

        Page<NtQuoteCustomerProjectDTO> page = ntQuoteCustomerProjectQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nt-quote-customer-projects/count} : count all the ntQuoteCustomerProjects.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countNtQuoteCustomerProjects(NtQuoteCustomerProjectCriteria criteria) {
        LOG.debug("REST request to count NtQuoteCustomerProjects by criteria: {}", criteria);
        return ResponseEntity.ok().body(ntQuoteCustomerProjectQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /nt-quote-customer-projects/:id} : get the "id" ntQuoteCustomerProject.
     *
     * @param id the id of the ntQuoteCustomerProjectDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ntQuoteCustomerProjectDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<NtQuoteCustomerProjectDTO> getNtQuoteCustomerProject(@PathVariable("id") Long id) {
        LOG.debug("REST request to get NtQuoteCustomerProject : {}", id);
        Optional<NtQuoteCustomerProjectDTO> ntQuoteCustomerProjectDTO = ntQuoteCustomerProjectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ntQuoteCustomerProjectDTO);
    }

    /**
     * {@code DELETE  /nt-quote-customer-projects/:id} : delete the "id" ntQuoteCustomerProject.
     *
     * @param id the id of the ntQuoteCustomerProjectDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNtQuoteCustomerProject(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete NtQuoteCustomerProject : {}", id);
        ntQuoteCustomerProjectService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /nt-quote-customer-projects/_search?query=:query} : search for the ntQuoteCustomerProject corresponding
     * to the query.
     *
     * @param query the query of the ntQuoteCustomerProject search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search")
    public ResponseEntity<List<NtQuoteCustomerProjectDTO>> searchNtQuoteCustomerProjects(
        @RequestParam("query") String query,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to search for a page of NtQuoteCustomerProjects for query {}", query);
        try {
            Page<NtQuoteCustomerProjectDTO> page = ntQuoteCustomerProjectService.search(query, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (RuntimeException e) {
            throw ElasticsearchExceptionMapper.mapException(e);
        }
    }
}
