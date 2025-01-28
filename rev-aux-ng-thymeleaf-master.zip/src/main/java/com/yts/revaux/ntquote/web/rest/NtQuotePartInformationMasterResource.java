package com.yts.revaux.ntquote.web.rest;

import com.yts.revaux.ntquote.repository.NtQuotePartInformationMasterRepository;
import com.yts.revaux.ntquote.service.NtQuotePartInformationMasterQueryService;
import com.yts.revaux.ntquote.service.NtQuotePartInformationMasterService;
import com.yts.revaux.ntquote.service.criteria.NtQuotePartInformationMasterCriteria;
import com.yts.revaux.ntquote.service.dto.NtQuotePartInformationMasterDTO;
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
 * REST controller for managing {@link com.yts.revaux.ntquote.domain.NtQuotePartInformationMaster}.
 */
@RestController
@RequestMapping("/api/nt-quote-part-information-masters")
public class NtQuotePartInformationMasterResource {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuotePartInformationMasterResource.class);

    private static final String ENTITY_NAME = "ntQuotePartInformationMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NtQuotePartInformationMasterService ntQuotePartInformationMasterService;

    private final NtQuotePartInformationMasterRepository ntQuotePartInformationMasterRepository;

    private final NtQuotePartInformationMasterQueryService ntQuotePartInformationMasterQueryService;

    public NtQuotePartInformationMasterResource(
        NtQuotePartInformationMasterService ntQuotePartInformationMasterService,
        NtQuotePartInformationMasterRepository ntQuotePartInformationMasterRepository,
        NtQuotePartInformationMasterQueryService ntQuotePartInformationMasterQueryService
    ) {
        this.ntQuotePartInformationMasterService = ntQuotePartInformationMasterService;
        this.ntQuotePartInformationMasterRepository = ntQuotePartInformationMasterRepository;
        this.ntQuotePartInformationMasterQueryService = ntQuotePartInformationMasterQueryService;
    }

    /**
     * {@code POST  /nt-quote-part-information-masters} : Create a new ntQuotePartInformationMaster.
     *
     * @param ntQuotePartInformationMasterDTO the ntQuotePartInformationMasterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ntQuotePartInformationMasterDTO, or with status {@code 400 (Bad Request)} if the ntQuotePartInformationMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<NtQuotePartInformationMasterDTO> createNtQuotePartInformationMaster(
        @Valid @RequestBody NtQuotePartInformationMasterDTO ntQuotePartInformationMasterDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save NtQuotePartInformationMaster : {}", ntQuotePartInformationMasterDTO);
        if (ntQuotePartInformationMasterDTO.getId() != null) {
            throw new BadRequestAlertException("A new ntQuotePartInformationMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ntQuotePartInformationMasterDTO = ntQuotePartInformationMasterService.save(ntQuotePartInformationMasterDTO);
        return ResponseEntity.created(new URI("/api/nt-quote-part-information-masters/" + ntQuotePartInformationMasterDTO.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, ntQuotePartInformationMasterDTO.getId().toString())
            )
            .body(ntQuotePartInformationMasterDTO);
    }

    /**
     * {@code PUT  /nt-quote-part-information-masters/:id} : Updates an existing ntQuotePartInformationMaster.
     *
     * @param id the id of the ntQuotePartInformationMasterDTO to save.
     * @param ntQuotePartInformationMasterDTO the ntQuotePartInformationMasterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ntQuotePartInformationMasterDTO,
     * or with status {@code 400 (Bad Request)} if the ntQuotePartInformationMasterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ntQuotePartInformationMasterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<NtQuotePartInformationMasterDTO> updateNtQuotePartInformationMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NtQuotePartInformationMasterDTO ntQuotePartInformationMasterDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update NtQuotePartInformationMaster : {}, {}", id, ntQuotePartInformationMasterDTO);
        if (ntQuotePartInformationMasterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ntQuotePartInformationMasterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ntQuotePartInformationMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ntQuotePartInformationMasterDTO = ntQuotePartInformationMasterService.update(ntQuotePartInformationMasterDTO);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ntQuotePartInformationMasterDTO.getId().toString())
            )
            .body(ntQuotePartInformationMasterDTO);
    }

    /**
     * {@code PATCH  /nt-quote-part-information-masters/:id} : Partial updates given fields of an existing ntQuotePartInformationMaster, field will ignore if it is null
     *
     * @param id the id of the ntQuotePartInformationMasterDTO to save.
     * @param ntQuotePartInformationMasterDTO the ntQuotePartInformationMasterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ntQuotePartInformationMasterDTO,
     * or with status {@code 400 (Bad Request)} if the ntQuotePartInformationMasterDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ntQuotePartInformationMasterDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ntQuotePartInformationMasterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NtQuotePartInformationMasterDTO> partialUpdateNtQuotePartInformationMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NtQuotePartInformationMasterDTO ntQuotePartInformationMasterDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update NtQuotePartInformationMaster partially : {}, {}", id, ntQuotePartInformationMasterDTO);
        if (ntQuotePartInformationMasterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ntQuotePartInformationMasterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ntQuotePartInformationMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NtQuotePartInformationMasterDTO> result = ntQuotePartInformationMasterService.partialUpdate(
            ntQuotePartInformationMasterDTO
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ntQuotePartInformationMasterDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /nt-quote-part-information-masters} : get all the ntQuotePartInformationMasters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ntQuotePartInformationMasters in body.
     */
    @GetMapping("")
    public ResponseEntity<List<NtQuotePartInformationMasterDTO>> getAllNtQuotePartInformationMasters(
        NtQuotePartInformationMasterCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get NtQuotePartInformationMasters by criteria: {}", criteria);

        Page<NtQuotePartInformationMasterDTO> page = ntQuotePartInformationMasterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nt-quote-part-information-masters/count} : count all the ntQuotePartInformationMasters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countNtQuotePartInformationMasters(NtQuotePartInformationMasterCriteria criteria) {
        LOG.debug("REST request to count NtQuotePartInformationMasters by criteria: {}", criteria);
        return ResponseEntity.ok().body(ntQuotePartInformationMasterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /nt-quote-part-information-masters/:id} : get the "id" ntQuotePartInformationMaster.
     *
     * @param id the id of the ntQuotePartInformationMasterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ntQuotePartInformationMasterDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<NtQuotePartInformationMasterDTO> getNtQuotePartInformationMaster(@PathVariable("id") Long id) {
        LOG.debug("REST request to get NtQuotePartInformationMaster : {}", id);
        Optional<NtQuotePartInformationMasterDTO> ntQuotePartInformationMasterDTO = ntQuotePartInformationMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ntQuotePartInformationMasterDTO);
    }

    /**
     * {@code DELETE  /nt-quote-part-information-masters/:id} : delete the "id" ntQuotePartInformationMaster.
     *
     * @param id the id of the ntQuotePartInformationMasterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNtQuotePartInformationMaster(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete NtQuotePartInformationMaster : {}", id);
        ntQuotePartInformationMasterService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /nt-quote-part-information-masters/_search?query=:query} : search for the ntQuotePartInformationMaster corresponding
     * to the query.
     *
     * @param query the query of the ntQuotePartInformationMaster search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search")
    public ResponseEntity<List<NtQuotePartInformationMasterDTO>> searchNtQuotePartInformationMasters(
        @RequestParam("query") String query,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to search for a page of NtQuotePartInformationMasters for query {}", query);
        try {
            Page<NtQuotePartInformationMasterDTO> page = ntQuotePartInformationMasterService.search(query, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (RuntimeException e) {
            throw ElasticsearchExceptionMapper.mapException(e);
        }
    }
}
