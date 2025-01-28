package com.yts.revaux.ntquote.web.rest;

import com.yts.revaux.ntquote.repository.NtQuoteComponentDetailRepository;
import com.yts.revaux.ntquote.service.NtQuoteComponentDetailQueryService;
import com.yts.revaux.ntquote.service.NtQuoteComponentDetailService;
import com.yts.revaux.ntquote.service.criteria.NtQuoteComponentDetailCriteria;
import com.yts.revaux.ntquote.service.dto.NtQuoteComponentDetailDTO;
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
 * REST controller for managing {@link com.yts.revaux.ntquote.domain.NtQuoteComponentDetail}.
 */
@RestController
@RequestMapping("/api/nt-quote-component-details")
public class NtQuoteComponentDetailResource {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuoteComponentDetailResource.class);

    private static final String ENTITY_NAME = "ntQuoteComponentDetail";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NtQuoteComponentDetailService ntQuoteComponentDetailService;

    private final NtQuoteComponentDetailRepository ntQuoteComponentDetailRepository;

    private final NtQuoteComponentDetailQueryService ntQuoteComponentDetailQueryService;

    public NtQuoteComponentDetailResource(
        NtQuoteComponentDetailService ntQuoteComponentDetailService,
        NtQuoteComponentDetailRepository ntQuoteComponentDetailRepository,
        NtQuoteComponentDetailQueryService ntQuoteComponentDetailQueryService
    ) {
        this.ntQuoteComponentDetailService = ntQuoteComponentDetailService;
        this.ntQuoteComponentDetailRepository = ntQuoteComponentDetailRepository;
        this.ntQuoteComponentDetailQueryService = ntQuoteComponentDetailQueryService;
    }

    /**
     * {@code POST  /nt-quote-component-details} : Create a new ntQuoteComponentDetail.
     *
     * @param ntQuoteComponentDetailDTO the ntQuoteComponentDetailDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ntQuoteComponentDetailDTO, or with status {@code 400 (Bad Request)} if the ntQuoteComponentDetail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<NtQuoteComponentDetailDTO> createNtQuoteComponentDetail(
        @Valid @RequestBody NtQuoteComponentDetailDTO ntQuoteComponentDetailDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save NtQuoteComponentDetail : {}", ntQuoteComponentDetailDTO);
        if (ntQuoteComponentDetailDTO.getId() != null) {
            throw new BadRequestAlertException("A new ntQuoteComponentDetail cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ntQuoteComponentDetailDTO = ntQuoteComponentDetailService.save(ntQuoteComponentDetailDTO);
        return ResponseEntity.created(new URI("/api/nt-quote-component-details/" + ntQuoteComponentDetailDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, ntQuoteComponentDetailDTO.getId().toString()))
            .body(ntQuoteComponentDetailDTO);
    }

    /**
     * {@code PUT  /nt-quote-component-details/:id} : Updates an existing ntQuoteComponentDetail.
     *
     * @param id the id of the ntQuoteComponentDetailDTO to save.
     * @param ntQuoteComponentDetailDTO the ntQuoteComponentDetailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ntQuoteComponentDetailDTO,
     * or with status {@code 400 (Bad Request)} if the ntQuoteComponentDetailDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ntQuoteComponentDetailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<NtQuoteComponentDetailDTO> updateNtQuoteComponentDetail(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NtQuoteComponentDetailDTO ntQuoteComponentDetailDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update NtQuoteComponentDetail : {}, {}", id, ntQuoteComponentDetailDTO);
        if (ntQuoteComponentDetailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ntQuoteComponentDetailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ntQuoteComponentDetailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ntQuoteComponentDetailDTO = ntQuoteComponentDetailService.update(ntQuoteComponentDetailDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ntQuoteComponentDetailDTO.getId().toString()))
            .body(ntQuoteComponentDetailDTO);
    }

    /**
     * {@code PATCH  /nt-quote-component-details/:id} : Partial updates given fields of an existing ntQuoteComponentDetail, field will ignore if it is null
     *
     * @param id the id of the ntQuoteComponentDetailDTO to save.
     * @param ntQuoteComponentDetailDTO the ntQuoteComponentDetailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ntQuoteComponentDetailDTO,
     * or with status {@code 400 (Bad Request)} if the ntQuoteComponentDetailDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ntQuoteComponentDetailDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ntQuoteComponentDetailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NtQuoteComponentDetailDTO> partialUpdateNtQuoteComponentDetail(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NtQuoteComponentDetailDTO ntQuoteComponentDetailDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update NtQuoteComponentDetail partially : {}, {}", id, ntQuoteComponentDetailDTO);
        if (ntQuoteComponentDetailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ntQuoteComponentDetailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ntQuoteComponentDetailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NtQuoteComponentDetailDTO> result = ntQuoteComponentDetailService.partialUpdate(ntQuoteComponentDetailDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ntQuoteComponentDetailDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /nt-quote-component-details} : get all the ntQuoteComponentDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ntQuoteComponentDetails in body.
     */
    @GetMapping("")
    public ResponseEntity<List<NtQuoteComponentDetailDTO>> getAllNtQuoteComponentDetails(
        NtQuoteComponentDetailCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get NtQuoteComponentDetails by criteria: {}", criteria);

        Page<NtQuoteComponentDetailDTO> page = ntQuoteComponentDetailQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nt-quote-component-details/count} : count all the ntQuoteComponentDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countNtQuoteComponentDetails(NtQuoteComponentDetailCriteria criteria) {
        LOG.debug("REST request to count NtQuoteComponentDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(ntQuoteComponentDetailQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /nt-quote-component-details/:id} : get the "id" ntQuoteComponentDetail.
     *
     * @param id the id of the ntQuoteComponentDetailDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ntQuoteComponentDetailDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<NtQuoteComponentDetailDTO> getNtQuoteComponentDetail(@PathVariable("id") Long id) {
        LOG.debug("REST request to get NtQuoteComponentDetail : {}", id);
        Optional<NtQuoteComponentDetailDTO> ntQuoteComponentDetailDTO = ntQuoteComponentDetailService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ntQuoteComponentDetailDTO);
    }

    /**
     * {@code DELETE  /nt-quote-component-details/:id} : delete the "id" ntQuoteComponentDetail.
     *
     * @param id the id of the ntQuoteComponentDetailDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNtQuoteComponentDetail(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete NtQuoteComponentDetail : {}", id);
        ntQuoteComponentDetailService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /nt-quote-component-details/_search?query=:query} : search for the ntQuoteComponentDetail corresponding
     * to the query.
     *
     * @param query the query of the ntQuoteComponentDetail search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search")
    public ResponseEntity<List<NtQuoteComponentDetailDTO>> searchNtQuoteComponentDetails(
        @RequestParam("query") String query,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to search for a page of NtQuoteComponentDetails for query {}", query);
        try {
            Page<NtQuoteComponentDetailDTO> page = ntQuoteComponentDetailService.search(query, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (RuntimeException e) {
            throw ElasticsearchExceptionMapper.mapException(e);
        }
    }
}
