package com.yts.revaux.ntquote.web.rest;

import com.yts.revaux.ntquote.repository.NtQuoteRepository;
import com.yts.revaux.ntquote.service.NtQuoteQueryService;
import com.yts.revaux.ntquote.service.NtQuoteService;
import com.yts.revaux.ntquote.service.criteria.NtQuoteCriteria;
import com.yts.revaux.ntquote.service.dto.NtQuoteDTO;
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
 * REST controller for managing {@link com.yts.revaux.ntquote.domain.NtQuote}.
 */
@RestController
@RequestMapping("/api/nt-quotes")
public class NtQuoteResource {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuoteResource.class);

    private static final String ENTITY_NAME = "ntQuote";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NtQuoteService ntQuoteService;

    private final NtQuoteRepository ntQuoteRepository;

    private final NtQuoteQueryService ntQuoteQueryService;

    public NtQuoteResource(NtQuoteService ntQuoteService, NtQuoteRepository ntQuoteRepository, NtQuoteQueryService ntQuoteQueryService) {
        this.ntQuoteService = ntQuoteService;
        this.ntQuoteRepository = ntQuoteRepository;
        this.ntQuoteQueryService = ntQuoteQueryService;
    }

    /**
     * {@code POST  /nt-quotes} : Create a new ntQuote.
     *
     * @param ntQuoteDTO the ntQuoteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ntQuoteDTO, or with status {@code 400 (Bad Request)} if the ntQuote has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<NtQuoteDTO> createNtQuote(@Valid @RequestBody NtQuoteDTO ntQuoteDTO) throws URISyntaxException {
        LOG.debug("REST request to save NtQuote : {}", ntQuoteDTO);
        if (ntQuoteDTO.getId() != null) {
            throw new BadRequestAlertException("A new ntQuote cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ntQuoteDTO = ntQuoteService.save(ntQuoteDTO);
        return ResponseEntity.created(new URI("/api/nt-quotes/" + ntQuoteDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, ntQuoteDTO.getId().toString()))
            .body(ntQuoteDTO);
    }

    /**
     * {@code PUT  /nt-quotes/:id} : Updates an existing ntQuote.
     *
     * @param id the id of the ntQuoteDTO to save.
     * @param ntQuoteDTO the ntQuoteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ntQuoteDTO,
     * or with status {@code 400 (Bad Request)} if the ntQuoteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ntQuoteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<NtQuoteDTO> updateNtQuote(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NtQuoteDTO ntQuoteDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update NtQuote : {}, {}", id, ntQuoteDTO);
        if (ntQuoteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ntQuoteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ntQuoteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ntQuoteDTO = ntQuoteService.update(ntQuoteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ntQuoteDTO.getId().toString()))
            .body(ntQuoteDTO);
    }

    /**
     * {@code PATCH  /nt-quotes/:id} : Partial updates given fields of an existing ntQuote, field will ignore if it is null
     *
     * @param id the id of the ntQuoteDTO to save.
     * @param ntQuoteDTO the ntQuoteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ntQuoteDTO,
     * or with status {@code 400 (Bad Request)} if the ntQuoteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ntQuoteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ntQuoteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NtQuoteDTO> partialUpdateNtQuote(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NtQuoteDTO ntQuoteDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update NtQuote partially : {}, {}", id, ntQuoteDTO);
        if (ntQuoteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ntQuoteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ntQuoteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NtQuoteDTO> result = ntQuoteService.partialUpdate(ntQuoteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ntQuoteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /nt-quotes} : get all the ntQuotes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ntQuotes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<NtQuoteDTO>> getAllNtQuotes(
        NtQuoteCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get NtQuotes by criteria: {}", criteria);

        Page<NtQuoteDTO> page = ntQuoteQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nt-quotes/count} : count all the ntQuotes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countNtQuotes(NtQuoteCriteria criteria) {
        LOG.debug("REST request to count NtQuotes by criteria: {}", criteria);
        return ResponseEntity.ok().body(ntQuoteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /nt-quotes/:id} : get the "id" ntQuote.
     *
     * @param id the id of the ntQuoteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ntQuoteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<NtQuoteDTO> getNtQuote(@PathVariable("id") Long id) {
        LOG.debug("REST request to get NtQuote : {}", id);
        Optional<NtQuoteDTO> ntQuoteDTO = ntQuoteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ntQuoteDTO);
    }

    /**
     * {@code DELETE  /nt-quotes/:id} : delete the "id" ntQuote.
     *
     * @param id the id of the ntQuoteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNtQuote(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete NtQuote : {}", id);
        ntQuoteService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /nt-quotes/_search?query=:query} : search for the ntQuote corresponding
     * to the query.
     *
     * @param query the query of the ntQuote search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search")
    public ResponseEntity<List<NtQuoteDTO>> searchNtQuotes(
        @RequestParam("query") String query,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to search for a page of NtQuotes for query {}", query);
        try {
            Page<NtQuoteDTO> page = ntQuoteService.search(query, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (RuntimeException e) {
            throw ElasticsearchExceptionMapper.mapException(e);
        }
    }
}
