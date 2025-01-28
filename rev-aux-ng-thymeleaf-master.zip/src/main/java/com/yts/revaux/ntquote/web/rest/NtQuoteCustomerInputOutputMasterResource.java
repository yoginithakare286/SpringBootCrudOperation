package com.yts.revaux.ntquote.web.rest;

import com.yts.revaux.ntquote.repository.NtQuoteCustomerInputOutputMasterRepository;
import com.yts.revaux.ntquote.service.NtQuoteCustomerInputOutputMasterService;
import com.yts.revaux.ntquote.service.dto.NtQuoteCustomerInputOutputMasterDTO;
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
 * REST controller for managing {@link com.yts.revaux.ntquote.domain.NtQuoteCustomerInputOutputMaster}.
 */
@RestController
@RequestMapping("/api/nt-quote-customer-input-output-masters")
public class NtQuoteCustomerInputOutputMasterResource {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuoteCustomerInputOutputMasterResource.class);

    private static final String ENTITY_NAME = "ntQuoteCustomerInputOutputMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NtQuoteCustomerInputOutputMasterService ntQuoteCustomerInputOutputMasterService;

    private final NtQuoteCustomerInputOutputMasterRepository ntQuoteCustomerInputOutputMasterRepository;

    public NtQuoteCustomerInputOutputMasterResource(
        NtQuoteCustomerInputOutputMasterService ntQuoteCustomerInputOutputMasterService,
        NtQuoteCustomerInputOutputMasterRepository ntQuoteCustomerInputOutputMasterRepository
    ) {
        this.ntQuoteCustomerInputOutputMasterService = ntQuoteCustomerInputOutputMasterService;
        this.ntQuoteCustomerInputOutputMasterRepository = ntQuoteCustomerInputOutputMasterRepository;
    }

    /**
     * {@code POST  /nt-quote-customer-input-output-masters} : Create a new ntQuoteCustomerInputOutputMaster.
     *
     * @param ntQuoteCustomerInputOutputMasterDTO the ntQuoteCustomerInputOutputMasterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ntQuoteCustomerInputOutputMasterDTO, or with status {@code 400 (Bad Request)} if the ntQuoteCustomerInputOutputMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<NtQuoteCustomerInputOutputMasterDTO> createNtQuoteCustomerInputOutputMaster(
        @Valid @RequestBody NtQuoteCustomerInputOutputMasterDTO ntQuoteCustomerInputOutputMasterDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save NtQuoteCustomerInputOutputMaster : {}", ntQuoteCustomerInputOutputMasterDTO);
        if (ntQuoteCustomerInputOutputMasterDTO.getId() != null) {
            throw new BadRequestAlertException("A new ntQuoteCustomerInputOutputMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ntQuoteCustomerInputOutputMasterDTO = ntQuoteCustomerInputOutputMasterService.save(ntQuoteCustomerInputOutputMasterDTO);
        return ResponseEntity.created(new URI("/api/nt-quote-customer-input-output-masters/" + ntQuoteCustomerInputOutputMasterDTO.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    ntQuoteCustomerInputOutputMasterDTO.getId().toString()
                )
            )
            .body(ntQuoteCustomerInputOutputMasterDTO);
    }

    /**
     * {@code PUT  /nt-quote-customer-input-output-masters/:id} : Updates an existing ntQuoteCustomerInputOutputMaster.
     *
     * @param id the id of the ntQuoteCustomerInputOutputMasterDTO to save.
     * @param ntQuoteCustomerInputOutputMasterDTO the ntQuoteCustomerInputOutputMasterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ntQuoteCustomerInputOutputMasterDTO,
     * or with status {@code 400 (Bad Request)} if the ntQuoteCustomerInputOutputMasterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ntQuoteCustomerInputOutputMasterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<NtQuoteCustomerInputOutputMasterDTO> updateNtQuoteCustomerInputOutputMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NtQuoteCustomerInputOutputMasterDTO ntQuoteCustomerInputOutputMasterDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update NtQuoteCustomerInputOutputMaster : {}, {}", id, ntQuoteCustomerInputOutputMasterDTO);
        if (ntQuoteCustomerInputOutputMasterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ntQuoteCustomerInputOutputMasterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ntQuoteCustomerInputOutputMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ntQuoteCustomerInputOutputMasterDTO = ntQuoteCustomerInputOutputMasterService.update(ntQuoteCustomerInputOutputMasterDTO);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    ntQuoteCustomerInputOutputMasterDTO.getId().toString()
                )
            )
            .body(ntQuoteCustomerInputOutputMasterDTO);
    }

    /**
     * {@code PATCH  /nt-quote-customer-input-output-masters/:id} : Partial updates given fields of an existing ntQuoteCustomerInputOutputMaster, field will ignore if it is null
     *
     * @param id the id of the ntQuoteCustomerInputOutputMasterDTO to save.
     * @param ntQuoteCustomerInputOutputMasterDTO the ntQuoteCustomerInputOutputMasterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ntQuoteCustomerInputOutputMasterDTO,
     * or with status {@code 400 (Bad Request)} if the ntQuoteCustomerInputOutputMasterDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ntQuoteCustomerInputOutputMasterDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ntQuoteCustomerInputOutputMasterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NtQuoteCustomerInputOutputMasterDTO> partialUpdateNtQuoteCustomerInputOutputMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NtQuoteCustomerInputOutputMasterDTO ntQuoteCustomerInputOutputMasterDTO
    ) throws URISyntaxException {
        LOG.debug(
            "REST request to partial update NtQuoteCustomerInputOutputMaster partially : {}, {}",
            id,
            ntQuoteCustomerInputOutputMasterDTO
        );
        if (ntQuoteCustomerInputOutputMasterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ntQuoteCustomerInputOutputMasterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ntQuoteCustomerInputOutputMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NtQuoteCustomerInputOutputMasterDTO> result = ntQuoteCustomerInputOutputMasterService.partialUpdate(
            ntQuoteCustomerInputOutputMasterDTO
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ntQuoteCustomerInputOutputMasterDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /nt-quote-customer-input-output-masters} : get all the ntQuoteCustomerInputOutputMasters.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ntQuoteCustomerInputOutputMasters in body.
     */
    @GetMapping("")
    public ResponseEntity<List<NtQuoteCustomerInputOutputMasterDTO>> getAllNtQuoteCustomerInputOutputMasters(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of NtQuoteCustomerInputOutputMasters");
        Page<NtQuoteCustomerInputOutputMasterDTO> page = ntQuoteCustomerInputOutputMasterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nt-quote-customer-input-output-masters/:id} : get the "id" ntQuoteCustomerInputOutputMaster.
     *
     * @param id the id of the ntQuoteCustomerInputOutputMasterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ntQuoteCustomerInputOutputMasterDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<NtQuoteCustomerInputOutputMasterDTO> getNtQuoteCustomerInputOutputMaster(@PathVariable("id") Long id) {
        LOG.debug("REST request to get NtQuoteCustomerInputOutputMaster : {}", id);
        Optional<NtQuoteCustomerInputOutputMasterDTO> ntQuoteCustomerInputOutputMasterDTO = ntQuoteCustomerInputOutputMasterService.findOne(
            id
        );
        return ResponseUtil.wrapOrNotFound(ntQuoteCustomerInputOutputMasterDTO);
    }

    /**
     * {@code DELETE  /nt-quote-customer-input-output-masters/:id} : delete the "id" ntQuoteCustomerInputOutputMaster.
     *
     * @param id the id of the ntQuoteCustomerInputOutputMasterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNtQuoteCustomerInputOutputMaster(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete NtQuoteCustomerInputOutputMaster : {}", id);
        ntQuoteCustomerInputOutputMasterService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /nt-quote-customer-input-output-masters/_search?query=:query} : search for the ntQuoteCustomerInputOutputMaster corresponding
     * to the query.
     *
     * @param query the query of the ntQuoteCustomerInputOutputMaster search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search")
    public ResponseEntity<List<NtQuoteCustomerInputOutputMasterDTO>> searchNtQuoteCustomerInputOutputMasters(
        @RequestParam("query") String query,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to search for a page of NtQuoteCustomerInputOutputMasters for query {}", query);
        try {
            Page<NtQuoteCustomerInputOutputMasterDTO> page = ntQuoteCustomerInputOutputMasterService.search(query, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (RuntimeException e) {
            throw ElasticsearchExceptionMapper.mapException(e);
        }
    }
}
