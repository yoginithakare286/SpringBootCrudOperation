package com.yts.revaux.ntquote.web.rest;

import com.yts.revaux.ntquote.repository.NtQuoteCustomerPoRepository;
import com.yts.revaux.ntquote.service.NtQuoteCustomerPoQueryService;
import com.yts.revaux.ntquote.service.NtQuoteCustomerPoService;
import com.yts.revaux.ntquote.service.criteria.NtQuoteCustomerPoCriteria;
import com.yts.revaux.ntquote.service.dto.NtQuoteCustomerPoDTO;
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
 * REST controller for managing {@link com.yts.revaux.ntquote.domain.NtQuoteCustomerPo}.
 */
@RestController
@RequestMapping("/api/nt-quote-customer-pos")
public class NtQuoteCustomerPoResource {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuoteCustomerPoResource.class);

    private static final String ENTITY_NAME = "ntQuoteCustomerPo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NtQuoteCustomerPoService ntQuoteCustomerPoService;

    private final NtQuoteCustomerPoRepository ntQuoteCustomerPoRepository;

    private final NtQuoteCustomerPoQueryService ntQuoteCustomerPoQueryService;

    public NtQuoteCustomerPoResource(
        NtQuoteCustomerPoService ntQuoteCustomerPoService,
        NtQuoteCustomerPoRepository ntQuoteCustomerPoRepository,
        NtQuoteCustomerPoQueryService ntQuoteCustomerPoQueryService
    ) {
        this.ntQuoteCustomerPoService = ntQuoteCustomerPoService;
        this.ntQuoteCustomerPoRepository = ntQuoteCustomerPoRepository;
        this.ntQuoteCustomerPoQueryService = ntQuoteCustomerPoQueryService;
    }

    /**
     * {@code POST  /nt-quote-customer-pos} : Create a new ntQuoteCustomerPo.
     *
     * @param ntQuoteCustomerPoDTO the ntQuoteCustomerPoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ntQuoteCustomerPoDTO, or with status {@code 400 (Bad Request)} if the ntQuoteCustomerPo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<NtQuoteCustomerPoDTO> createNtQuoteCustomerPo(@Valid @RequestBody NtQuoteCustomerPoDTO ntQuoteCustomerPoDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save NtQuoteCustomerPo : {}", ntQuoteCustomerPoDTO);
        if (ntQuoteCustomerPoDTO.getId() != null) {
            throw new BadRequestAlertException("A new ntQuoteCustomerPo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ntQuoteCustomerPoDTO = ntQuoteCustomerPoService.save(ntQuoteCustomerPoDTO);
        return ResponseEntity.created(new URI("/api/nt-quote-customer-pos/" + ntQuoteCustomerPoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, ntQuoteCustomerPoDTO.getId().toString()))
            .body(ntQuoteCustomerPoDTO);
    }

    /**
     * {@code PUT  /nt-quote-customer-pos/:id} : Updates an existing ntQuoteCustomerPo.
     *
     * @param id the id of the ntQuoteCustomerPoDTO to save.
     * @param ntQuoteCustomerPoDTO the ntQuoteCustomerPoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ntQuoteCustomerPoDTO,
     * or with status {@code 400 (Bad Request)} if the ntQuoteCustomerPoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ntQuoteCustomerPoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<NtQuoteCustomerPoDTO> updateNtQuoteCustomerPo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NtQuoteCustomerPoDTO ntQuoteCustomerPoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update NtQuoteCustomerPo : {}, {}", id, ntQuoteCustomerPoDTO);
        if (ntQuoteCustomerPoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ntQuoteCustomerPoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ntQuoteCustomerPoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ntQuoteCustomerPoDTO = ntQuoteCustomerPoService.update(ntQuoteCustomerPoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ntQuoteCustomerPoDTO.getId().toString()))
            .body(ntQuoteCustomerPoDTO);
    }

    /**
     * {@code PATCH  /nt-quote-customer-pos/:id} : Partial updates given fields of an existing ntQuoteCustomerPo, field will ignore if it is null
     *
     * @param id the id of the ntQuoteCustomerPoDTO to save.
     * @param ntQuoteCustomerPoDTO the ntQuoteCustomerPoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ntQuoteCustomerPoDTO,
     * or with status {@code 400 (Bad Request)} if the ntQuoteCustomerPoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ntQuoteCustomerPoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ntQuoteCustomerPoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NtQuoteCustomerPoDTO> partialUpdateNtQuoteCustomerPo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NtQuoteCustomerPoDTO ntQuoteCustomerPoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update NtQuoteCustomerPo partially : {}, {}", id, ntQuoteCustomerPoDTO);
        if (ntQuoteCustomerPoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ntQuoteCustomerPoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ntQuoteCustomerPoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NtQuoteCustomerPoDTO> result = ntQuoteCustomerPoService.partialUpdate(ntQuoteCustomerPoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ntQuoteCustomerPoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /nt-quote-customer-pos} : get all the ntQuoteCustomerPos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ntQuoteCustomerPos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<NtQuoteCustomerPoDTO>> getAllNtQuoteCustomerPos(
        NtQuoteCustomerPoCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get NtQuoteCustomerPos by criteria: {}", criteria);

        Page<NtQuoteCustomerPoDTO> page = ntQuoteCustomerPoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nt-quote-customer-pos/count} : count all the ntQuoteCustomerPos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countNtQuoteCustomerPos(NtQuoteCustomerPoCriteria criteria) {
        LOG.debug("REST request to count NtQuoteCustomerPos by criteria: {}", criteria);
        return ResponseEntity.ok().body(ntQuoteCustomerPoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /nt-quote-customer-pos/:id} : get the "id" ntQuoteCustomerPo.
     *
     * @param id the id of the ntQuoteCustomerPoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ntQuoteCustomerPoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<NtQuoteCustomerPoDTO> getNtQuoteCustomerPo(@PathVariable("id") Long id) {
        LOG.debug("REST request to get NtQuoteCustomerPo : {}", id);
        Optional<NtQuoteCustomerPoDTO> ntQuoteCustomerPoDTO = ntQuoteCustomerPoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ntQuoteCustomerPoDTO);
    }

    /**
     * {@code DELETE  /nt-quote-customer-pos/:id} : delete the "id" ntQuoteCustomerPo.
     *
     * @param id the id of the ntQuoteCustomerPoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNtQuoteCustomerPo(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete NtQuoteCustomerPo : {}", id);
        ntQuoteCustomerPoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /nt-quote-customer-pos/_search?query=:query} : search for the ntQuoteCustomerPo corresponding
     * to the query.
     *
     * @param query the query of the ntQuoteCustomerPo search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search")
    public ResponseEntity<List<NtQuoteCustomerPoDTO>> searchNtQuoteCustomerPos(
        @RequestParam("query") String query,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to search for a page of NtQuoteCustomerPos for query {}", query);
        try {
            Page<NtQuoteCustomerPoDTO> page = ntQuoteCustomerPoService.search(query, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (RuntimeException e) {
            throw ElasticsearchExceptionMapper.mapException(e);
        }
    }
}
