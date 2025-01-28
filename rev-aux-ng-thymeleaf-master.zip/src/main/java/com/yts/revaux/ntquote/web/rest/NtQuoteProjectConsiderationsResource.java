package com.yts.revaux.ntquote.web.rest;

import com.yts.revaux.ntquote.repository.NtQuoteProjectConsiderationsRepository;
import com.yts.revaux.ntquote.service.NtQuoteProjectConsiderationsQueryService;
import com.yts.revaux.ntquote.service.NtQuoteProjectConsiderationsService;
import com.yts.revaux.ntquote.service.criteria.NtQuoteProjectConsiderationsCriteria;
import com.yts.revaux.ntquote.service.dto.NtQuoteProjectConsiderationsDTO;
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
 * REST controller for managing {@link com.yts.revaux.ntquote.domain.NtQuoteProjectConsiderations}.
 */
@RestController
@RequestMapping("/api/nt-quote-project-considerations")
public class NtQuoteProjectConsiderationsResource {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuoteProjectConsiderationsResource.class);

    private static final String ENTITY_NAME = "ntQuoteProjectConsiderations";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NtQuoteProjectConsiderationsService ntQuoteProjectConsiderationsService;

    private final NtQuoteProjectConsiderationsRepository ntQuoteProjectConsiderationsRepository;

    private final NtQuoteProjectConsiderationsQueryService ntQuoteProjectConsiderationsQueryService;

    public NtQuoteProjectConsiderationsResource(
        NtQuoteProjectConsiderationsService ntQuoteProjectConsiderationsService,
        NtQuoteProjectConsiderationsRepository ntQuoteProjectConsiderationsRepository,
        NtQuoteProjectConsiderationsQueryService ntQuoteProjectConsiderationsQueryService
    ) {
        this.ntQuoteProjectConsiderationsService = ntQuoteProjectConsiderationsService;
        this.ntQuoteProjectConsiderationsRepository = ntQuoteProjectConsiderationsRepository;
        this.ntQuoteProjectConsiderationsQueryService = ntQuoteProjectConsiderationsQueryService;
    }

    /**
     * {@code POST  /nt-quote-project-considerations} : Create a new ntQuoteProjectConsiderations.
     *
     * @param ntQuoteProjectConsiderationsDTO the ntQuoteProjectConsiderationsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ntQuoteProjectConsiderationsDTO, or with status {@code 400 (Bad Request)} if the ntQuoteProjectConsiderations has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<NtQuoteProjectConsiderationsDTO> createNtQuoteProjectConsiderations(
        @Valid @RequestBody NtQuoteProjectConsiderationsDTO ntQuoteProjectConsiderationsDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save NtQuoteProjectConsiderations : {}", ntQuoteProjectConsiderationsDTO);
        if (ntQuoteProjectConsiderationsDTO.getId() != null) {
            throw new BadRequestAlertException("A new ntQuoteProjectConsiderations cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ntQuoteProjectConsiderationsDTO = ntQuoteProjectConsiderationsService.save(ntQuoteProjectConsiderationsDTO);
        return ResponseEntity.created(new URI("/api/nt-quote-project-considerations/" + ntQuoteProjectConsiderationsDTO.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, ntQuoteProjectConsiderationsDTO.getId().toString())
            )
            .body(ntQuoteProjectConsiderationsDTO);
    }

    /**
     * {@code PUT  /nt-quote-project-considerations/:id} : Updates an existing ntQuoteProjectConsiderations.
     *
     * @param id the id of the ntQuoteProjectConsiderationsDTO to save.
     * @param ntQuoteProjectConsiderationsDTO the ntQuoteProjectConsiderationsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ntQuoteProjectConsiderationsDTO,
     * or with status {@code 400 (Bad Request)} if the ntQuoteProjectConsiderationsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ntQuoteProjectConsiderationsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<NtQuoteProjectConsiderationsDTO> updateNtQuoteProjectConsiderations(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NtQuoteProjectConsiderationsDTO ntQuoteProjectConsiderationsDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update NtQuoteProjectConsiderations : {}, {}", id, ntQuoteProjectConsiderationsDTO);
        if (ntQuoteProjectConsiderationsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ntQuoteProjectConsiderationsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ntQuoteProjectConsiderationsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ntQuoteProjectConsiderationsDTO = ntQuoteProjectConsiderationsService.update(ntQuoteProjectConsiderationsDTO);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ntQuoteProjectConsiderationsDTO.getId().toString())
            )
            .body(ntQuoteProjectConsiderationsDTO);
    }

    /**
     * {@code PATCH  /nt-quote-project-considerations/:id} : Partial updates given fields of an existing ntQuoteProjectConsiderations, field will ignore if it is null
     *
     * @param id the id of the ntQuoteProjectConsiderationsDTO to save.
     * @param ntQuoteProjectConsiderationsDTO the ntQuoteProjectConsiderationsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ntQuoteProjectConsiderationsDTO,
     * or with status {@code 400 (Bad Request)} if the ntQuoteProjectConsiderationsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ntQuoteProjectConsiderationsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ntQuoteProjectConsiderationsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NtQuoteProjectConsiderationsDTO> partialUpdateNtQuoteProjectConsiderations(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NtQuoteProjectConsiderationsDTO ntQuoteProjectConsiderationsDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update NtQuoteProjectConsiderations partially : {}, {}", id, ntQuoteProjectConsiderationsDTO);
        if (ntQuoteProjectConsiderationsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ntQuoteProjectConsiderationsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ntQuoteProjectConsiderationsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NtQuoteProjectConsiderationsDTO> result = ntQuoteProjectConsiderationsService.partialUpdate(
            ntQuoteProjectConsiderationsDTO
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ntQuoteProjectConsiderationsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /nt-quote-project-considerations} : get all the ntQuoteProjectConsiderations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ntQuoteProjectConsiderations in body.
     */
    @GetMapping("")
    public ResponseEntity<List<NtQuoteProjectConsiderationsDTO>> getAllNtQuoteProjectConsiderations(
        NtQuoteProjectConsiderationsCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get NtQuoteProjectConsiderations by criteria: {}", criteria);

        Page<NtQuoteProjectConsiderationsDTO> page = ntQuoteProjectConsiderationsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nt-quote-project-considerations/count} : count all the ntQuoteProjectConsiderations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countNtQuoteProjectConsiderations(NtQuoteProjectConsiderationsCriteria criteria) {
        LOG.debug("REST request to count NtQuoteProjectConsiderations by criteria: {}", criteria);
        return ResponseEntity.ok().body(ntQuoteProjectConsiderationsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /nt-quote-project-considerations/:id} : get the "id" ntQuoteProjectConsiderations.
     *
     * @param id the id of the ntQuoteProjectConsiderationsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ntQuoteProjectConsiderationsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<NtQuoteProjectConsiderationsDTO> getNtQuoteProjectConsiderations(@PathVariable("id") Long id) {
        LOG.debug("REST request to get NtQuoteProjectConsiderations : {}", id);
        Optional<NtQuoteProjectConsiderationsDTO> ntQuoteProjectConsiderationsDTO = ntQuoteProjectConsiderationsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ntQuoteProjectConsiderationsDTO);
    }

    /**
     * {@code DELETE  /nt-quote-project-considerations/:id} : delete the "id" ntQuoteProjectConsiderations.
     *
     * @param id the id of the ntQuoteProjectConsiderationsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNtQuoteProjectConsiderations(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete NtQuoteProjectConsiderations : {}", id);
        ntQuoteProjectConsiderationsService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /nt-quote-project-considerations/_search?query=:query} : search for the ntQuoteProjectConsiderations corresponding
     * to the query.
     *
     * @param query the query of the ntQuoteProjectConsiderations search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search")
    public ResponseEntity<List<NtQuoteProjectConsiderationsDTO>> searchNtQuoteProjectConsiderations(
        @RequestParam("query") String query,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to search for a page of NtQuoteProjectConsiderations for query {}", query);
        try {
            Page<NtQuoteProjectConsiderationsDTO> page = ntQuoteProjectConsiderationsService.search(query, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (RuntimeException e) {
            throw ElasticsearchExceptionMapper.mapException(e);
        }
    }
}
