package com.yts.revaux.ntquote.web.rest;

import com.yts.revaux.ntquote.repository.NtQuoteTermsConditionsRepository;
import com.yts.revaux.ntquote.service.NtQuoteTermsConditionsService;
import com.yts.revaux.ntquote.service.dto.NtQuoteTermsConditionsDTO;
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
 * REST controller for managing {@link com.yts.revaux.ntquote.domain.NtQuoteTermsConditions}.
 */
@RestController
@RequestMapping("/api/nt-quote-terms-conditions")
public class NtQuoteTermsConditionsResource {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuoteTermsConditionsResource.class);

    private static final String ENTITY_NAME = "ntQuoteTermsConditions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NtQuoteTermsConditionsService ntQuoteTermsConditionsService;

    private final NtQuoteTermsConditionsRepository ntQuoteTermsConditionsRepository;

    public NtQuoteTermsConditionsResource(
        NtQuoteTermsConditionsService ntQuoteTermsConditionsService,
        NtQuoteTermsConditionsRepository ntQuoteTermsConditionsRepository
    ) {
        this.ntQuoteTermsConditionsService = ntQuoteTermsConditionsService;
        this.ntQuoteTermsConditionsRepository = ntQuoteTermsConditionsRepository;
    }

    /**
     * {@code POST  /nt-quote-terms-conditions} : Create a new ntQuoteTermsConditions.
     *
     * @param ntQuoteTermsConditionsDTO the ntQuoteTermsConditionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ntQuoteTermsConditionsDTO, or with status {@code 400 (Bad Request)} if the ntQuoteTermsConditions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<NtQuoteTermsConditionsDTO> createNtQuoteTermsConditions(
        @Valid @RequestBody NtQuoteTermsConditionsDTO ntQuoteTermsConditionsDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save NtQuoteTermsConditions : {}", ntQuoteTermsConditionsDTO);
        if (ntQuoteTermsConditionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new ntQuoteTermsConditions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ntQuoteTermsConditionsDTO = ntQuoteTermsConditionsService.save(ntQuoteTermsConditionsDTO);
        return ResponseEntity.created(new URI("/api/nt-quote-terms-conditions/" + ntQuoteTermsConditionsDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, ntQuoteTermsConditionsDTO.getId().toString()))
            .body(ntQuoteTermsConditionsDTO);
    }

    /**
     * {@code PUT  /nt-quote-terms-conditions/:id} : Updates an existing ntQuoteTermsConditions.
     *
     * @param id the id of the ntQuoteTermsConditionsDTO to save.
     * @param ntQuoteTermsConditionsDTO the ntQuoteTermsConditionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ntQuoteTermsConditionsDTO,
     * or with status {@code 400 (Bad Request)} if the ntQuoteTermsConditionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ntQuoteTermsConditionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<NtQuoteTermsConditionsDTO> updateNtQuoteTermsConditions(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NtQuoteTermsConditionsDTO ntQuoteTermsConditionsDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update NtQuoteTermsConditions : {}, {}", id, ntQuoteTermsConditionsDTO);
        if (ntQuoteTermsConditionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ntQuoteTermsConditionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ntQuoteTermsConditionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ntQuoteTermsConditionsDTO = ntQuoteTermsConditionsService.update(ntQuoteTermsConditionsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ntQuoteTermsConditionsDTO.getId().toString()))
            .body(ntQuoteTermsConditionsDTO);
    }

    /**
     * {@code PATCH  /nt-quote-terms-conditions/:id} : Partial updates given fields of an existing ntQuoteTermsConditions, field will ignore if it is null
     *
     * @param id the id of the ntQuoteTermsConditionsDTO to save.
     * @param ntQuoteTermsConditionsDTO the ntQuoteTermsConditionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ntQuoteTermsConditionsDTO,
     * or with status {@code 400 (Bad Request)} if the ntQuoteTermsConditionsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ntQuoteTermsConditionsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ntQuoteTermsConditionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NtQuoteTermsConditionsDTO> partialUpdateNtQuoteTermsConditions(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NtQuoteTermsConditionsDTO ntQuoteTermsConditionsDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update NtQuoteTermsConditions partially : {}, {}", id, ntQuoteTermsConditionsDTO);
        if (ntQuoteTermsConditionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ntQuoteTermsConditionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ntQuoteTermsConditionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NtQuoteTermsConditionsDTO> result = ntQuoteTermsConditionsService.partialUpdate(ntQuoteTermsConditionsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ntQuoteTermsConditionsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /nt-quote-terms-conditions} : get all the ntQuoteTermsConditions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ntQuoteTermsConditions in body.
     */
    @GetMapping("")
    public ResponseEntity<List<NtQuoteTermsConditionsDTO>> getAllNtQuoteTermsConditions(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of NtQuoteTermsConditions");
        Page<NtQuoteTermsConditionsDTO> page = ntQuoteTermsConditionsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nt-quote-terms-conditions/:id} : get the "id" ntQuoteTermsConditions.
     *
     * @param id the id of the ntQuoteTermsConditionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ntQuoteTermsConditionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<NtQuoteTermsConditionsDTO> getNtQuoteTermsConditions(@PathVariable("id") Long id) {
        LOG.debug("REST request to get NtQuoteTermsConditions : {}", id);
        Optional<NtQuoteTermsConditionsDTO> ntQuoteTermsConditionsDTO = ntQuoteTermsConditionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ntQuoteTermsConditionsDTO);
    }

    /**
     * {@code DELETE  /nt-quote-terms-conditions/:id} : delete the "id" ntQuoteTermsConditions.
     *
     * @param id the id of the ntQuoteTermsConditionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNtQuoteTermsConditions(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete NtQuoteTermsConditions : {}", id);
        ntQuoteTermsConditionsService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /nt-quote-terms-conditions/_search?query=:query} : search for the ntQuoteTermsConditions corresponding
     * to the query.
     *
     * @param query the query of the ntQuoteTermsConditions search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search")
    public ResponseEntity<List<NtQuoteTermsConditionsDTO>> searchNtQuoteTermsConditions(
        @RequestParam("query") String query,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to search for a page of NtQuoteTermsConditions for query {}", query);
        try {
            Page<NtQuoteTermsConditionsDTO> page = ntQuoteTermsConditionsService.search(query, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (RuntimeException e) {
            throw ElasticsearchExceptionMapper.mapException(e);
        }
    }
}
