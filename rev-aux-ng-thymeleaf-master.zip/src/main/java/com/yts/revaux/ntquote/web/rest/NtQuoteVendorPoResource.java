package com.yts.revaux.ntquote.web.rest;

import com.yts.revaux.ntquote.repository.NtQuoteVendorPoRepository;
import com.yts.revaux.ntquote.service.NtQuoteVendorPoQueryService;
import com.yts.revaux.ntquote.service.NtQuoteVendorPoService;
import com.yts.revaux.ntquote.service.criteria.NtQuoteVendorPoCriteria;
import com.yts.revaux.ntquote.service.dto.NtQuoteVendorPoDTO;
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
 * REST controller for managing {@link com.yts.revaux.ntquote.domain.NtQuoteVendorPo}.
 */
@RestController
@RequestMapping("/api/nt-quote-vendor-pos")
public class NtQuoteVendorPoResource {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuoteVendorPoResource.class);

    private static final String ENTITY_NAME = "ntQuoteVendorPo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NtQuoteVendorPoService ntQuoteVendorPoService;

    private final NtQuoteVendorPoRepository ntQuoteVendorPoRepository;

    private final NtQuoteVendorPoQueryService ntQuoteVendorPoQueryService;

    public NtQuoteVendorPoResource(
        NtQuoteVendorPoService ntQuoteVendorPoService,
        NtQuoteVendorPoRepository ntQuoteVendorPoRepository,
        NtQuoteVendorPoQueryService ntQuoteVendorPoQueryService
    ) {
        this.ntQuoteVendorPoService = ntQuoteVendorPoService;
        this.ntQuoteVendorPoRepository = ntQuoteVendorPoRepository;
        this.ntQuoteVendorPoQueryService = ntQuoteVendorPoQueryService;
    }

    /**
     * {@code POST  /nt-quote-vendor-pos} : Create a new ntQuoteVendorPo.
     *
     * @param ntQuoteVendorPoDTO the ntQuoteVendorPoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ntQuoteVendorPoDTO, or with status {@code 400 (Bad Request)} if the ntQuoteVendorPo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<NtQuoteVendorPoDTO> createNtQuoteVendorPo(@Valid @RequestBody NtQuoteVendorPoDTO ntQuoteVendorPoDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save NtQuoteVendorPo : {}", ntQuoteVendorPoDTO);
        if (ntQuoteVendorPoDTO.getId() != null) {
            throw new BadRequestAlertException("A new ntQuoteVendorPo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ntQuoteVendorPoDTO = ntQuoteVendorPoService.save(ntQuoteVendorPoDTO);
        return ResponseEntity.created(new URI("/api/nt-quote-vendor-pos/" + ntQuoteVendorPoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, ntQuoteVendorPoDTO.getId().toString()))
            .body(ntQuoteVendorPoDTO);
    }

    /**
     * {@code PUT  /nt-quote-vendor-pos/:id} : Updates an existing ntQuoteVendorPo.
     *
     * @param id the id of the ntQuoteVendorPoDTO to save.
     * @param ntQuoteVendorPoDTO the ntQuoteVendorPoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ntQuoteVendorPoDTO,
     * or with status {@code 400 (Bad Request)} if the ntQuoteVendorPoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ntQuoteVendorPoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<NtQuoteVendorPoDTO> updateNtQuoteVendorPo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NtQuoteVendorPoDTO ntQuoteVendorPoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update NtQuoteVendorPo : {}, {}", id, ntQuoteVendorPoDTO);
        if (ntQuoteVendorPoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ntQuoteVendorPoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ntQuoteVendorPoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ntQuoteVendorPoDTO = ntQuoteVendorPoService.update(ntQuoteVendorPoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ntQuoteVendorPoDTO.getId().toString()))
            .body(ntQuoteVendorPoDTO);
    }

    /**
     * {@code PATCH  /nt-quote-vendor-pos/:id} : Partial updates given fields of an existing ntQuoteVendorPo, field will ignore if it is null
     *
     * @param id the id of the ntQuoteVendorPoDTO to save.
     * @param ntQuoteVendorPoDTO the ntQuoteVendorPoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ntQuoteVendorPoDTO,
     * or with status {@code 400 (Bad Request)} if the ntQuoteVendorPoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ntQuoteVendorPoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ntQuoteVendorPoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NtQuoteVendorPoDTO> partialUpdateNtQuoteVendorPo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NtQuoteVendorPoDTO ntQuoteVendorPoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update NtQuoteVendorPo partially : {}, {}", id, ntQuoteVendorPoDTO);
        if (ntQuoteVendorPoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ntQuoteVendorPoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ntQuoteVendorPoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NtQuoteVendorPoDTO> result = ntQuoteVendorPoService.partialUpdate(ntQuoteVendorPoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ntQuoteVendorPoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /nt-quote-vendor-pos} : get all the ntQuoteVendorPos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ntQuoteVendorPos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<NtQuoteVendorPoDTO>> getAllNtQuoteVendorPos(
        NtQuoteVendorPoCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get NtQuoteVendorPos by criteria: {}", criteria);

        Page<NtQuoteVendorPoDTO> page = ntQuoteVendorPoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nt-quote-vendor-pos/count} : count all the ntQuoteVendorPos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countNtQuoteVendorPos(NtQuoteVendorPoCriteria criteria) {
        LOG.debug("REST request to count NtQuoteVendorPos by criteria: {}", criteria);
        return ResponseEntity.ok().body(ntQuoteVendorPoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /nt-quote-vendor-pos/:id} : get the "id" ntQuoteVendorPo.
     *
     * @param id the id of the ntQuoteVendorPoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ntQuoteVendorPoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<NtQuoteVendorPoDTO> getNtQuoteVendorPo(@PathVariable("id") Long id) {
        LOG.debug("REST request to get NtQuoteVendorPo : {}", id);
        Optional<NtQuoteVendorPoDTO> ntQuoteVendorPoDTO = ntQuoteVendorPoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ntQuoteVendorPoDTO);
    }

    /**
     * {@code DELETE  /nt-quote-vendor-pos/:id} : delete the "id" ntQuoteVendorPo.
     *
     * @param id the id of the ntQuoteVendorPoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNtQuoteVendorPo(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete NtQuoteVendorPo : {}", id);
        ntQuoteVendorPoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /nt-quote-vendor-pos/_search?query=:query} : search for the ntQuoteVendorPo corresponding
     * to the query.
     *
     * @param query the query of the ntQuoteVendorPo search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search")
    public ResponseEntity<List<NtQuoteVendorPoDTO>> searchNtQuoteVendorPos(
        @RequestParam("query") String query,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to search for a page of NtQuoteVendorPos for query {}", query);
        try {
            Page<NtQuoteVendorPoDTO> page = ntQuoteVendorPoService.search(query, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (RuntimeException e) {
            throw ElasticsearchExceptionMapper.mapException(e);
        }
    }
}
