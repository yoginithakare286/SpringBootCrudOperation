package com.yts.revaux.ntquote.web.rest;

import com.yts.revaux.ntquote.repository.NtQuoteContractReviewInformationRepository;
import com.yts.revaux.ntquote.service.NtQuoteContractReviewInformationQueryService;
import com.yts.revaux.ntquote.service.NtQuoteContractReviewInformationService;
import com.yts.revaux.ntquote.service.criteria.NtQuoteContractReviewInformationCriteria;
import com.yts.revaux.ntquote.service.dto.NtQuoteContractReviewInformationDTO;
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
 * REST controller for managing {@link com.yts.revaux.ntquote.domain.NtQuoteContractReviewInformation}.
 */
@RestController
@RequestMapping("/api/nt-quote-contract-review-informations")
public class NtQuoteContractReviewInformationResource {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuoteContractReviewInformationResource.class);

    private static final String ENTITY_NAME = "ntQuoteContractReviewInformation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NtQuoteContractReviewInformationService ntQuoteContractReviewInformationService;

    private final NtQuoteContractReviewInformationRepository ntQuoteContractReviewInformationRepository;

    private final NtQuoteContractReviewInformationQueryService ntQuoteContractReviewInformationQueryService;

    public NtQuoteContractReviewInformationResource(
        NtQuoteContractReviewInformationService ntQuoteContractReviewInformationService,
        NtQuoteContractReviewInformationRepository ntQuoteContractReviewInformationRepository,
        NtQuoteContractReviewInformationQueryService ntQuoteContractReviewInformationQueryService
    ) {
        this.ntQuoteContractReviewInformationService = ntQuoteContractReviewInformationService;
        this.ntQuoteContractReviewInformationRepository = ntQuoteContractReviewInformationRepository;
        this.ntQuoteContractReviewInformationQueryService = ntQuoteContractReviewInformationQueryService;
    }

    /**
     * {@code POST  /nt-quote-contract-review-informations} : Create a new ntQuoteContractReviewInformation.
     *
     * @param ntQuoteContractReviewInformationDTO the ntQuoteContractReviewInformationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ntQuoteContractReviewInformationDTO, or with status {@code 400 (Bad Request)} if the ntQuoteContractReviewInformation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<NtQuoteContractReviewInformationDTO> createNtQuoteContractReviewInformation(
        @Valid @RequestBody NtQuoteContractReviewInformationDTO ntQuoteContractReviewInformationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save NtQuoteContractReviewInformation : {}", ntQuoteContractReviewInformationDTO);
        if (ntQuoteContractReviewInformationDTO.getId() != null) {
            throw new BadRequestAlertException("A new ntQuoteContractReviewInformation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ntQuoteContractReviewInformationDTO = ntQuoteContractReviewInformationService.save(ntQuoteContractReviewInformationDTO);
        return ResponseEntity.created(new URI("/api/nt-quote-contract-review-informations/" + ntQuoteContractReviewInformationDTO.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    ntQuoteContractReviewInformationDTO.getId().toString()
                )
            )
            .body(ntQuoteContractReviewInformationDTO);
    }

    /**
     * {@code PUT  /nt-quote-contract-review-informations/:id} : Updates an existing ntQuoteContractReviewInformation.
     *
     * @param id the id of the ntQuoteContractReviewInformationDTO to save.
     * @param ntQuoteContractReviewInformationDTO the ntQuoteContractReviewInformationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ntQuoteContractReviewInformationDTO,
     * or with status {@code 400 (Bad Request)} if the ntQuoteContractReviewInformationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ntQuoteContractReviewInformationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<NtQuoteContractReviewInformationDTO> updateNtQuoteContractReviewInformation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NtQuoteContractReviewInformationDTO ntQuoteContractReviewInformationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update NtQuoteContractReviewInformation : {}, {}", id, ntQuoteContractReviewInformationDTO);
        if (ntQuoteContractReviewInformationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ntQuoteContractReviewInformationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ntQuoteContractReviewInformationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ntQuoteContractReviewInformationDTO = ntQuoteContractReviewInformationService.update(ntQuoteContractReviewInformationDTO);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    ntQuoteContractReviewInformationDTO.getId().toString()
                )
            )
            .body(ntQuoteContractReviewInformationDTO);
    }

    /**
     * {@code PATCH  /nt-quote-contract-review-informations/:id} : Partial updates given fields of an existing ntQuoteContractReviewInformation, field will ignore if it is null
     *
     * @param id the id of the ntQuoteContractReviewInformationDTO to save.
     * @param ntQuoteContractReviewInformationDTO the ntQuoteContractReviewInformationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ntQuoteContractReviewInformationDTO,
     * or with status {@code 400 (Bad Request)} if the ntQuoteContractReviewInformationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ntQuoteContractReviewInformationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ntQuoteContractReviewInformationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NtQuoteContractReviewInformationDTO> partialUpdateNtQuoteContractReviewInformation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NtQuoteContractReviewInformationDTO ntQuoteContractReviewInformationDTO
    ) throws URISyntaxException {
        LOG.debug(
            "REST request to partial update NtQuoteContractReviewInformation partially : {}, {}",
            id,
            ntQuoteContractReviewInformationDTO
        );
        if (ntQuoteContractReviewInformationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ntQuoteContractReviewInformationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ntQuoteContractReviewInformationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NtQuoteContractReviewInformationDTO> result = ntQuoteContractReviewInformationService.partialUpdate(
            ntQuoteContractReviewInformationDTO
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ntQuoteContractReviewInformationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /nt-quote-contract-review-informations} : get all the ntQuoteContractReviewInformations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ntQuoteContractReviewInformations in body.
     */
    @GetMapping("")
    public ResponseEntity<List<NtQuoteContractReviewInformationDTO>> getAllNtQuoteContractReviewInformations(
        NtQuoteContractReviewInformationCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get NtQuoteContractReviewInformations by criteria: {}", criteria);

        Page<NtQuoteContractReviewInformationDTO> page = ntQuoteContractReviewInformationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nt-quote-contract-review-informations/count} : count all the ntQuoteContractReviewInformations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countNtQuoteContractReviewInformations(NtQuoteContractReviewInformationCriteria criteria) {
        LOG.debug("REST request to count NtQuoteContractReviewInformations by criteria: {}", criteria);
        return ResponseEntity.ok().body(ntQuoteContractReviewInformationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /nt-quote-contract-review-informations/:id} : get the "id" ntQuoteContractReviewInformation.
     *
     * @param id the id of the ntQuoteContractReviewInformationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ntQuoteContractReviewInformationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<NtQuoteContractReviewInformationDTO> getNtQuoteContractReviewInformation(@PathVariable("id") Long id) {
        LOG.debug("REST request to get NtQuoteContractReviewInformation : {}", id);
        Optional<NtQuoteContractReviewInformationDTO> ntQuoteContractReviewInformationDTO = ntQuoteContractReviewInformationService.findOne(
            id
        );
        return ResponseUtil.wrapOrNotFound(ntQuoteContractReviewInformationDTO);
    }

    /**
     * {@code DELETE  /nt-quote-contract-review-informations/:id} : delete the "id" ntQuoteContractReviewInformation.
     *
     * @param id the id of the ntQuoteContractReviewInformationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNtQuoteContractReviewInformation(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete NtQuoteContractReviewInformation : {}", id);
        ntQuoteContractReviewInformationService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /nt-quote-contract-review-informations/_search?query=:query} : search for the ntQuoteContractReviewInformation corresponding
     * to the query.
     *
     * @param query the query of the ntQuoteContractReviewInformation search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search")
    public ResponseEntity<List<NtQuoteContractReviewInformationDTO>> searchNtQuoteContractReviewInformations(
        @RequestParam("query") String query,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to search for a page of NtQuoteContractReviewInformations for query {}", query);
        try {
            Page<NtQuoteContractReviewInformationDTO> page = ntQuoteContractReviewInformationService.search(query, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (RuntimeException e) {
            throw ElasticsearchExceptionMapper.mapException(e);
        }
    }
}
