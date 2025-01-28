package com.yts.revaux.ntquote.web.rest;

import com.yts.revaux.ntquote.repository.RfqDetailRepository;
import com.yts.revaux.ntquote.service.RfqDetailQueryService;
import com.yts.revaux.ntquote.service.RfqDetailService;
import com.yts.revaux.ntquote.service.criteria.RfqDetailCriteria;
import com.yts.revaux.ntquote.service.dto.RfqDetailDTO;
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
 * REST controller for managing {@link com.yts.revaux.ntquote.domain.RfqDetail}.
 */
@RestController
@RequestMapping("/api/rfq-details")
public class RfqDetailResource {

    private static final Logger LOG = LoggerFactory.getLogger(RfqDetailResource.class);

    private static final String ENTITY_NAME = "rfqDetail";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RfqDetailService rfqDetailService;

    private final RfqDetailRepository rfqDetailRepository;

    private final RfqDetailQueryService rfqDetailQueryService;

    public RfqDetailResource(
        RfqDetailService rfqDetailService,
        RfqDetailRepository rfqDetailRepository,
        RfqDetailQueryService rfqDetailQueryService
    ) {
        this.rfqDetailService = rfqDetailService;
        this.rfqDetailRepository = rfqDetailRepository;
        this.rfqDetailQueryService = rfqDetailQueryService;
    }

    /**
     * {@code POST  /rfq-details} : Create a new rfqDetail.
     *
     * @param rfqDetailDTO the rfqDetailDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rfqDetailDTO, or with status {@code 400 (Bad Request)} if the rfqDetail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RfqDetailDTO> createRfqDetail(@Valid @RequestBody RfqDetailDTO rfqDetailDTO) throws URISyntaxException {
        LOG.debug("REST request to save RfqDetail : {}", rfqDetailDTO);
        if (rfqDetailDTO.getId() != null) {
            throw new BadRequestAlertException("A new rfqDetail cannot already have an ID", ENTITY_NAME, "idexists");
        }
        rfqDetailDTO = rfqDetailService.save(rfqDetailDTO);
        return ResponseEntity.created(new URI("/api/rfq-details/" + rfqDetailDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, rfqDetailDTO.getId().toString()))
            .body(rfqDetailDTO);
    }

    /**
     * {@code PUT  /rfq-details/:id} : Updates an existing rfqDetail.
     *
     * @param id the id of the rfqDetailDTO to save.
     * @param rfqDetailDTO the rfqDetailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rfqDetailDTO,
     * or with status {@code 400 (Bad Request)} if the rfqDetailDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rfqDetailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RfqDetailDTO> updateRfqDetail(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RfqDetailDTO rfqDetailDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update RfqDetail : {}, {}", id, rfqDetailDTO);
        if (rfqDetailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rfqDetailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rfqDetailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        rfqDetailDTO = rfqDetailService.update(rfqDetailDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rfqDetailDTO.getId().toString()))
            .body(rfqDetailDTO);
    }

    /**
     * {@code PATCH  /rfq-details/:id} : Partial updates given fields of an existing rfqDetail, field will ignore if it is null
     *
     * @param id the id of the rfqDetailDTO to save.
     * @param rfqDetailDTO the rfqDetailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rfqDetailDTO,
     * or with status {@code 400 (Bad Request)} if the rfqDetailDTO is not valid,
     * or with status {@code 404 (Not Found)} if the rfqDetailDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the rfqDetailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RfqDetailDTO> partialUpdateRfqDetail(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RfqDetailDTO rfqDetailDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update RfqDetail partially : {}, {}", id, rfqDetailDTO);
        if (rfqDetailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rfqDetailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rfqDetailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RfqDetailDTO> result = rfqDetailService.partialUpdate(rfqDetailDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rfqDetailDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /rfq-details} : get all the rfqDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rfqDetails in body.
     */
    @GetMapping("")
    public ResponseEntity<List<RfqDetailDTO>> getAllRfqDetails(
        RfqDetailCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get RfqDetails by criteria: {}", criteria);

        Page<RfqDetailDTO> page = rfqDetailQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rfq-details/count} : count all the rfqDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countRfqDetails(RfqDetailCriteria criteria) {
        LOG.debug("REST request to count RfqDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(rfqDetailQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /rfq-details/:id} : get the "id" rfqDetail.
     *
     * @param id the id of the rfqDetailDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rfqDetailDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RfqDetailDTO> getRfqDetail(@PathVariable("id") Long id) {
        LOG.debug("REST request to get RfqDetail : {}", id);
        Optional<RfqDetailDTO> rfqDetailDTO = rfqDetailService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rfqDetailDTO);
    }

    /**
     * {@code DELETE  /rfq-details/:id} : delete the "id" rfqDetail.
     *
     * @param id the id of the rfqDetailDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRfqDetail(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete RfqDetail : {}", id);
        rfqDetailService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /rfq-details/_search?query=:query} : search for the rfqDetail corresponding
     * to the query.
     *
     * @param query the query of the rfqDetail search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search")
    public ResponseEntity<List<RfqDetailDTO>> searchRfqDetails(
        @RequestParam("query") String query,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to search for a page of RfqDetails for query {}", query);
        try {
            Page<RfqDetailDTO> page = rfqDetailService.search(query, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (RuntimeException e) {
            throw ElasticsearchExceptionMapper.mapException(e);
        }
    }
}
