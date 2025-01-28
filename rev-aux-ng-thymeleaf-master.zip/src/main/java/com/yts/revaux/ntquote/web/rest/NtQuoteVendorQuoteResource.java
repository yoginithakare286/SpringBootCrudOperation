package com.yts.revaux.ntquote.web.rest;

import com.yts.revaux.ntquote.repository.NtQuoteVendorQuoteRepository;
import com.yts.revaux.ntquote.service.NtQuoteVendorQuoteQueryService;
import com.yts.revaux.ntquote.service.NtQuoteVendorQuoteService;
import com.yts.revaux.ntquote.service.criteria.NtQuoteVendorQuoteCriteria;
import com.yts.revaux.ntquote.service.dto.NtQuoteVendorQuoteDTO;
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
 * REST controller for managing {@link com.yts.revaux.ntquote.domain.NtQuoteVendorQuote}.
 */
@RestController
@RequestMapping("/api/nt-quote-vendor-quotes")
public class NtQuoteVendorQuoteResource {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuoteVendorQuoteResource.class);

    private static final String ENTITY_NAME = "ntQuoteVendorQuote";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NtQuoteVendorQuoteService ntQuoteVendorQuoteService;

    private final NtQuoteVendorQuoteRepository ntQuoteVendorQuoteRepository;

    private final NtQuoteVendorQuoteQueryService ntQuoteVendorQuoteQueryService;

    public NtQuoteVendorQuoteResource(
        NtQuoteVendorQuoteService ntQuoteVendorQuoteService,
        NtQuoteVendorQuoteRepository ntQuoteVendorQuoteRepository,
        NtQuoteVendorQuoteQueryService ntQuoteVendorQuoteQueryService
    ) {
        this.ntQuoteVendorQuoteService = ntQuoteVendorQuoteService;
        this.ntQuoteVendorQuoteRepository = ntQuoteVendorQuoteRepository;
        this.ntQuoteVendorQuoteQueryService = ntQuoteVendorQuoteQueryService;
    }

    /**
     * {@code POST  /nt-quote-vendor-quotes} : Create a new ntQuoteVendorQuote.
     *
     * @param ntQuoteVendorQuoteDTO the ntQuoteVendorQuoteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ntQuoteVendorQuoteDTO, or with status {@code 400 (Bad Request)} if the ntQuoteVendorQuote has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<NtQuoteVendorQuoteDTO> createNtQuoteVendorQuote(@Valid @RequestBody NtQuoteVendorQuoteDTO ntQuoteVendorQuoteDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save NtQuoteVendorQuote : {}", ntQuoteVendorQuoteDTO);
        if (ntQuoteVendorQuoteDTO.getId() != null) {
            throw new BadRequestAlertException("A new ntQuoteVendorQuote cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ntQuoteVendorQuoteDTO = ntQuoteVendorQuoteService.save(ntQuoteVendorQuoteDTO);
        return ResponseEntity.created(new URI("/api/nt-quote-vendor-quotes/" + ntQuoteVendorQuoteDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, ntQuoteVendorQuoteDTO.getId().toString()))
            .body(ntQuoteVendorQuoteDTO);
    }

    /**
     * {@code PUT  /nt-quote-vendor-quotes/:id} : Updates an existing ntQuoteVendorQuote.
     *
     * @param id the id of the ntQuoteVendorQuoteDTO to save.
     * @param ntQuoteVendorQuoteDTO the ntQuoteVendorQuoteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ntQuoteVendorQuoteDTO,
     * or with status {@code 400 (Bad Request)} if the ntQuoteVendorQuoteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ntQuoteVendorQuoteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<NtQuoteVendorQuoteDTO> updateNtQuoteVendorQuote(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NtQuoteVendorQuoteDTO ntQuoteVendorQuoteDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update NtQuoteVendorQuote : {}, {}", id, ntQuoteVendorQuoteDTO);
        if (ntQuoteVendorQuoteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ntQuoteVendorQuoteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ntQuoteVendorQuoteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ntQuoteVendorQuoteDTO = ntQuoteVendorQuoteService.update(ntQuoteVendorQuoteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ntQuoteVendorQuoteDTO.getId().toString()))
            .body(ntQuoteVendorQuoteDTO);
    }

    /**
     * {@code PATCH  /nt-quote-vendor-quotes/:id} : Partial updates given fields of an existing ntQuoteVendorQuote, field will ignore if it is null
     *
     * @param id the id of the ntQuoteVendorQuoteDTO to save.
     * @param ntQuoteVendorQuoteDTO the ntQuoteVendorQuoteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ntQuoteVendorQuoteDTO,
     * or with status {@code 400 (Bad Request)} if the ntQuoteVendorQuoteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ntQuoteVendorQuoteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ntQuoteVendorQuoteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NtQuoteVendorQuoteDTO> partialUpdateNtQuoteVendorQuote(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NtQuoteVendorQuoteDTO ntQuoteVendorQuoteDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update NtQuoteVendorQuote partially : {}, {}", id, ntQuoteVendorQuoteDTO);
        if (ntQuoteVendorQuoteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ntQuoteVendorQuoteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ntQuoteVendorQuoteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NtQuoteVendorQuoteDTO> result = ntQuoteVendorQuoteService.partialUpdate(ntQuoteVendorQuoteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ntQuoteVendorQuoteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /nt-quote-vendor-quotes} : get all the ntQuoteVendorQuotes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ntQuoteVendorQuotes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<NtQuoteVendorQuoteDTO>> getAllNtQuoteVendorQuotes(
        NtQuoteVendorQuoteCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get NtQuoteVendorQuotes by criteria: {}", criteria);

        Page<NtQuoteVendorQuoteDTO> page = ntQuoteVendorQuoteQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nt-quote-vendor-quotes/count} : count all the ntQuoteVendorQuotes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countNtQuoteVendorQuotes(NtQuoteVendorQuoteCriteria criteria) {
        LOG.debug("REST request to count NtQuoteVendorQuotes by criteria: {}", criteria);
        return ResponseEntity.ok().body(ntQuoteVendorQuoteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /nt-quote-vendor-quotes/:id} : get the "id" ntQuoteVendorQuote.
     *
     * @param id the id of the ntQuoteVendorQuoteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ntQuoteVendorQuoteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<NtQuoteVendorQuoteDTO> getNtQuoteVendorQuote(@PathVariable("id") Long id) {
        LOG.debug("REST request to get NtQuoteVendorQuote : {}", id);
        Optional<NtQuoteVendorQuoteDTO> ntQuoteVendorQuoteDTO = ntQuoteVendorQuoteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ntQuoteVendorQuoteDTO);
    }

    /**
     * {@code DELETE  /nt-quote-vendor-quotes/:id} : delete the "id" ntQuoteVendorQuote.
     *
     * @param id the id of the ntQuoteVendorQuoteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNtQuoteVendorQuote(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete NtQuoteVendorQuote : {}", id);
        ntQuoteVendorQuoteService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /nt-quote-vendor-quotes/_search?query=:query} : search for the ntQuoteVendorQuote corresponding
     * to the query.
     *
     * @param query the query of the ntQuoteVendorQuote search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search")
    public ResponseEntity<List<NtQuoteVendorQuoteDTO>> searchNtQuoteVendorQuotes(
        @RequestParam("query") String query,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to search for a page of NtQuoteVendorQuotes for query {}", query);
        try {
            Page<NtQuoteVendorQuoteDTO> page = ntQuoteVendorQuoteService.search(query, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (RuntimeException e) {
            throw ElasticsearchExceptionMapper.mapException(e);
        }
    }
}
