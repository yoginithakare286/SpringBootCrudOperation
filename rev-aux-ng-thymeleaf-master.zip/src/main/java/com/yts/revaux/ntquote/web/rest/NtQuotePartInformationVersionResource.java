package com.yts.revaux.ntquote.web.rest;

import com.yts.revaux.ntquote.repository.NtQuotePartInformationVersionRepository;
import com.yts.revaux.ntquote.service.NtQuotePartInformationVersionQueryService;
import com.yts.revaux.ntquote.service.NtQuotePartInformationVersionService;
import com.yts.revaux.ntquote.service.criteria.NtQuotePartInformationVersionCriteria;
import com.yts.revaux.ntquote.service.dto.NtQuotePartInformationVersionDTO;
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
 * REST controller for managing {@link com.yts.revaux.ntquote.domain.NtQuotePartInformationVersion}.
 */
@RestController
@RequestMapping("/api/nt-quote-part-information-versions")
public class NtQuotePartInformationVersionResource {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuotePartInformationVersionResource.class);

    private static final String ENTITY_NAME = "ntQuotePartInformationVersion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NtQuotePartInformationVersionService ntQuotePartInformationVersionService;

    private final NtQuotePartInformationVersionRepository ntQuotePartInformationVersionRepository;

    private final NtQuotePartInformationVersionQueryService ntQuotePartInformationVersionQueryService;

    public NtQuotePartInformationVersionResource(
        NtQuotePartInformationVersionService ntQuotePartInformationVersionService,
        NtQuotePartInformationVersionRepository ntQuotePartInformationVersionRepository,
        NtQuotePartInformationVersionQueryService ntQuotePartInformationVersionQueryService
    ) {
        this.ntQuotePartInformationVersionService = ntQuotePartInformationVersionService;
        this.ntQuotePartInformationVersionRepository = ntQuotePartInformationVersionRepository;
        this.ntQuotePartInformationVersionQueryService = ntQuotePartInformationVersionQueryService;
    }

    /**
     * {@code POST  /nt-quote-part-information-versions} : Create a new ntQuotePartInformationVersion.
     *
     * @param ntQuotePartInformationVersionDTO the ntQuotePartInformationVersionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ntQuotePartInformationVersionDTO, or with status {@code 400 (Bad Request)} if the ntQuotePartInformationVersion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<NtQuotePartInformationVersionDTO> createNtQuotePartInformationVersion(
        @Valid @RequestBody NtQuotePartInformationVersionDTO ntQuotePartInformationVersionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save NtQuotePartInformationVersion : {}", ntQuotePartInformationVersionDTO);
        if (ntQuotePartInformationVersionDTO.getId() != null) {
            throw new BadRequestAlertException("A new ntQuotePartInformationVersion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ntQuotePartInformationVersionDTO = ntQuotePartInformationVersionService.save(ntQuotePartInformationVersionDTO);
        return ResponseEntity.created(new URI("/api/nt-quote-part-information-versions/" + ntQuotePartInformationVersionDTO.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    ntQuotePartInformationVersionDTO.getId().toString()
                )
            )
            .body(ntQuotePartInformationVersionDTO);
    }

    /**
     * {@code PUT  /nt-quote-part-information-versions/:id} : Updates an existing ntQuotePartInformationVersion.
     *
     * @param id the id of the ntQuotePartInformationVersionDTO to save.
     * @param ntQuotePartInformationVersionDTO the ntQuotePartInformationVersionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ntQuotePartInformationVersionDTO,
     * or with status {@code 400 (Bad Request)} if the ntQuotePartInformationVersionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ntQuotePartInformationVersionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<NtQuotePartInformationVersionDTO> updateNtQuotePartInformationVersion(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NtQuotePartInformationVersionDTO ntQuotePartInformationVersionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update NtQuotePartInformationVersion : {}, {}", id, ntQuotePartInformationVersionDTO);
        if (ntQuotePartInformationVersionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ntQuotePartInformationVersionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ntQuotePartInformationVersionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ntQuotePartInformationVersionDTO = ntQuotePartInformationVersionService.update(ntQuotePartInformationVersionDTO);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ntQuotePartInformationVersionDTO.getId().toString())
            )
            .body(ntQuotePartInformationVersionDTO);
    }

    /**
     * {@code PATCH  /nt-quote-part-information-versions/:id} : Partial updates given fields of an existing ntQuotePartInformationVersion, field will ignore if it is null
     *
     * @param id the id of the ntQuotePartInformationVersionDTO to save.
     * @param ntQuotePartInformationVersionDTO the ntQuotePartInformationVersionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ntQuotePartInformationVersionDTO,
     * or with status {@code 400 (Bad Request)} if the ntQuotePartInformationVersionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ntQuotePartInformationVersionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ntQuotePartInformationVersionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NtQuotePartInformationVersionDTO> partialUpdateNtQuotePartInformationVersion(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NtQuotePartInformationVersionDTO ntQuotePartInformationVersionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update NtQuotePartInformationVersion partially : {}, {}", id, ntQuotePartInformationVersionDTO);
        if (ntQuotePartInformationVersionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ntQuotePartInformationVersionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ntQuotePartInformationVersionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NtQuotePartInformationVersionDTO> result = ntQuotePartInformationVersionService.partialUpdate(
            ntQuotePartInformationVersionDTO
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ntQuotePartInformationVersionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /nt-quote-part-information-versions} : get all the ntQuotePartInformationVersions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ntQuotePartInformationVersions in body.
     */
    @GetMapping("")
    public ResponseEntity<List<NtQuotePartInformationVersionDTO>> getAllNtQuotePartInformationVersions(
        NtQuotePartInformationVersionCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get NtQuotePartInformationVersions by criteria: {}", criteria);

        Page<NtQuotePartInformationVersionDTO> page = ntQuotePartInformationVersionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nt-quote-part-information-versions/count} : count all the ntQuotePartInformationVersions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countNtQuotePartInformationVersions(NtQuotePartInformationVersionCriteria criteria) {
        LOG.debug("REST request to count NtQuotePartInformationVersions by criteria: {}", criteria);
        return ResponseEntity.ok().body(ntQuotePartInformationVersionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /nt-quote-part-information-versions/:id} : get the "id" ntQuotePartInformationVersion.
     *
     * @param id the id of the ntQuotePartInformationVersionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ntQuotePartInformationVersionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<NtQuotePartInformationVersionDTO> getNtQuotePartInformationVersion(@PathVariable("id") Long id) {
        LOG.debug("REST request to get NtQuotePartInformationVersion : {}", id);
        Optional<NtQuotePartInformationVersionDTO> ntQuotePartInformationVersionDTO = ntQuotePartInformationVersionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ntQuotePartInformationVersionDTO);
    }

    /**
     * {@code DELETE  /nt-quote-part-information-versions/:id} : delete the "id" ntQuotePartInformationVersion.
     *
     * @param id the id of the ntQuotePartInformationVersionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNtQuotePartInformationVersion(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete NtQuotePartInformationVersion : {}", id);
        ntQuotePartInformationVersionService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /nt-quote-part-information-versions/_search?query=:query} : search for the ntQuotePartInformationVersion corresponding
     * to the query.
     *
     * @param query the query of the ntQuotePartInformationVersion search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search")
    public ResponseEntity<List<NtQuotePartInformationVersionDTO>> searchNtQuotePartInformationVersions(
        @RequestParam("query") String query,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to search for a page of NtQuotePartInformationVersions for query {}", query);
        try {
            Page<NtQuotePartInformationVersionDTO> page = ntQuotePartInformationVersionService.search(query, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (RuntimeException e) {
            throw ElasticsearchExceptionMapper.mapException(e);
        }
    }
}
