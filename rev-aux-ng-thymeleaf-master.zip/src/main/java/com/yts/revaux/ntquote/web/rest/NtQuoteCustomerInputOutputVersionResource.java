package com.yts.revaux.ntquote.web.rest;

import com.yts.revaux.ntquote.repository.NtQuoteCustomerInputOutputVersionRepository;
import com.yts.revaux.ntquote.service.NtQuoteCustomerInputOutputVersionQueryService;
import com.yts.revaux.ntquote.service.NtQuoteCustomerInputOutputVersionService;
import com.yts.revaux.ntquote.service.criteria.NtQuoteCustomerInputOutputVersionCriteria;
import com.yts.revaux.ntquote.service.dto.NtQuoteCustomerInputOutputVersionDTO;
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
 * REST controller for managing {@link com.yts.revaux.ntquote.domain.NtQuoteCustomerInputOutputVersion}.
 */
@RestController
@RequestMapping("/api/nt-quote-customer-input-output-versions")
public class NtQuoteCustomerInputOutputVersionResource {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuoteCustomerInputOutputVersionResource.class);

    private static final String ENTITY_NAME = "ntQuoteCustomerInputOutputVersion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NtQuoteCustomerInputOutputVersionService ntQuoteCustomerInputOutputVersionService;

    private final NtQuoteCustomerInputOutputVersionRepository ntQuoteCustomerInputOutputVersionRepository;

    private final NtQuoteCustomerInputOutputVersionQueryService ntQuoteCustomerInputOutputVersionQueryService;

    public NtQuoteCustomerInputOutputVersionResource(
        NtQuoteCustomerInputOutputVersionService ntQuoteCustomerInputOutputVersionService,
        NtQuoteCustomerInputOutputVersionRepository ntQuoteCustomerInputOutputVersionRepository,
        NtQuoteCustomerInputOutputVersionQueryService ntQuoteCustomerInputOutputVersionQueryService
    ) {
        this.ntQuoteCustomerInputOutputVersionService = ntQuoteCustomerInputOutputVersionService;
        this.ntQuoteCustomerInputOutputVersionRepository = ntQuoteCustomerInputOutputVersionRepository;
        this.ntQuoteCustomerInputOutputVersionQueryService = ntQuoteCustomerInputOutputVersionQueryService;
    }

    /**
     * {@code POST  /nt-quote-customer-input-output-versions} : Create a new ntQuoteCustomerInputOutputVersion.
     *
     * @param ntQuoteCustomerInputOutputVersionDTO the ntQuoteCustomerInputOutputVersionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ntQuoteCustomerInputOutputVersionDTO, or with status {@code 400 (Bad Request)} if the ntQuoteCustomerInputOutputVersion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<NtQuoteCustomerInputOutputVersionDTO> createNtQuoteCustomerInputOutputVersion(
        @Valid @RequestBody NtQuoteCustomerInputOutputVersionDTO ntQuoteCustomerInputOutputVersionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save NtQuoteCustomerInputOutputVersion : {}", ntQuoteCustomerInputOutputVersionDTO);
        if (ntQuoteCustomerInputOutputVersionDTO.getId() != null) {
            throw new BadRequestAlertException(
                "A new ntQuoteCustomerInputOutputVersion cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        ntQuoteCustomerInputOutputVersionDTO = ntQuoteCustomerInputOutputVersionService.save(ntQuoteCustomerInputOutputVersionDTO);
        return ResponseEntity.created(
            new URI("/api/nt-quote-customer-input-output-versions/" + ntQuoteCustomerInputOutputVersionDTO.getId())
        )
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    ntQuoteCustomerInputOutputVersionDTO.getId().toString()
                )
            )
            .body(ntQuoteCustomerInputOutputVersionDTO);
    }

    /**
     * {@code PUT  /nt-quote-customer-input-output-versions/:id} : Updates an existing ntQuoteCustomerInputOutputVersion.
     *
     * @param id the id of the ntQuoteCustomerInputOutputVersionDTO to save.
     * @param ntQuoteCustomerInputOutputVersionDTO the ntQuoteCustomerInputOutputVersionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ntQuoteCustomerInputOutputVersionDTO,
     * or with status {@code 400 (Bad Request)} if the ntQuoteCustomerInputOutputVersionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ntQuoteCustomerInputOutputVersionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<NtQuoteCustomerInputOutputVersionDTO> updateNtQuoteCustomerInputOutputVersion(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NtQuoteCustomerInputOutputVersionDTO ntQuoteCustomerInputOutputVersionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update NtQuoteCustomerInputOutputVersion : {}, {}", id, ntQuoteCustomerInputOutputVersionDTO);
        if (ntQuoteCustomerInputOutputVersionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ntQuoteCustomerInputOutputVersionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ntQuoteCustomerInputOutputVersionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ntQuoteCustomerInputOutputVersionDTO = ntQuoteCustomerInputOutputVersionService.update(ntQuoteCustomerInputOutputVersionDTO);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    ntQuoteCustomerInputOutputVersionDTO.getId().toString()
                )
            )
            .body(ntQuoteCustomerInputOutputVersionDTO);
    }

    /**
     * {@code PATCH  /nt-quote-customer-input-output-versions/:id} : Partial updates given fields of an existing ntQuoteCustomerInputOutputVersion, field will ignore if it is null
     *
     * @param id the id of the ntQuoteCustomerInputOutputVersionDTO to save.
     * @param ntQuoteCustomerInputOutputVersionDTO the ntQuoteCustomerInputOutputVersionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ntQuoteCustomerInputOutputVersionDTO,
     * or with status {@code 400 (Bad Request)} if the ntQuoteCustomerInputOutputVersionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ntQuoteCustomerInputOutputVersionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ntQuoteCustomerInputOutputVersionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NtQuoteCustomerInputOutputVersionDTO> partialUpdateNtQuoteCustomerInputOutputVersion(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NtQuoteCustomerInputOutputVersionDTO ntQuoteCustomerInputOutputVersionDTO
    ) throws URISyntaxException {
        LOG.debug(
            "REST request to partial update NtQuoteCustomerInputOutputVersion partially : {}, {}",
            id,
            ntQuoteCustomerInputOutputVersionDTO
        );
        if (ntQuoteCustomerInputOutputVersionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ntQuoteCustomerInputOutputVersionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ntQuoteCustomerInputOutputVersionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NtQuoteCustomerInputOutputVersionDTO> result = ntQuoteCustomerInputOutputVersionService.partialUpdate(
            ntQuoteCustomerInputOutputVersionDTO
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ntQuoteCustomerInputOutputVersionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /nt-quote-customer-input-output-versions} : get all the ntQuoteCustomerInputOutputVersions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ntQuoteCustomerInputOutputVersions in body.
     */
    @GetMapping("")
    public ResponseEntity<List<NtQuoteCustomerInputOutputVersionDTO>> getAllNtQuoteCustomerInputOutputVersions(
        NtQuoteCustomerInputOutputVersionCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get NtQuoteCustomerInputOutputVersions by criteria: {}", criteria);

        Page<NtQuoteCustomerInputOutputVersionDTO> page = ntQuoteCustomerInputOutputVersionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nt-quote-customer-input-output-versions/count} : count all the ntQuoteCustomerInputOutputVersions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countNtQuoteCustomerInputOutputVersions(NtQuoteCustomerInputOutputVersionCriteria criteria) {
        LOG.debug("REST request to count NtQuoteCustomerInputOutputVersions by criteria: {}", criteria);
        return ResponseEntity.ok().body(ntQuoteCustomerInputOutputVersionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /nt-quote-customer-input-output-versions/:id} : get the "id" ntQuoteCustomerInputOutputVersion.
     *
     * @param id the id of the ntQuoteCustomerInputOutputVersionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ntQuoteCustomerInputOutputVersionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<NtQuoteCustomerInputOutputVersionDTO> getNtQuoteCustomerInputOutputVersion(@PathVariable("id") Long id) {
        LOG.debug("REST request to get NtQuoteCustomerInputOutputVersion : {}", id);
        Optional<NtQuoteCustomerInputOutputVersionDTO> ntQuoteCustomerInputOutputVersionDTO =
            ntQuoteCustomerInputOutputVersionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ntQuoteCustomerInputOutputVersionDTO);
    }

    /**
     * {@code DELETE  /nt-quote-customer-input-output-versions/:id} : delete the "id" ntQuoteCustomerInputOutputVersion.
     *
     * @param id the id of the ntQuoteCustomerInputOutputVersionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNtQuoteCustomerInputOutputVersion(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete NtQuoteCustomerInputOutputVersion : {}", id);
        ntQuoteCustomerInputOutputVersionService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /nt-quote-customer-input-output-versions/_search?query=:query} : search for the ntQuoteCustomerInputOutputVersion corresponding
     * to the query.
     *
     * @param query the query of the ntQuoteCustomerInputOutputVersion search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search")
    public ResponseEntity<List<NtQuoteCustomerInputOutputVersionDTO>> searchNtQuoteCustomerInputOutputVersions(
        @RequestParam("query") String query,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to search for a page of NtQuoteCustomerInputOutputVersions for query {}", query);
        try {
            Page<NtQuoteCustomerInputOutputVersionDTO> page = ntQuoteCustomerInputOutputVersionService.search(query, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (RuntimeException e) {
            throw ElasticsearchExceptionMapper.mapException(e);
        }
    }
}
