package com.yts.revaux.ntquote.web.rest;

import com.yts.revaux.ntquote.repository.VendorProfileRepository;
import com.yts.revaux.ntquote.service.VendorProfileQueryService;
import com.yts.revaux.ntquote.service.VendorProfileService;
import com.yts.revaux.ntquote.service.criteria.VendorProfileCriteria;
import com.yts.revaux.ntquote.service.dto.VendorProfileDTO;
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
 * REST controller for managing {@link com.yts.revaux.ntquote.domain.VendorProfile}.
 */
@RestController
@RequestMapping("/api/vendor-profiles")
public class VendorProfileResource {

    private static final Logger LOG = LoggerFactory.getLogger(VendorProfileResource.class);

    private static final String ENTITY_NAME = "vendorProfile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VendorProfileService vendorProfileService;

    private final VendorProfileRepository vendorProfileRepository;

    private final VendorProfileQueryService vendorProfileQueryService;

    public VendorProfileResource(
        VendorProfileService vendorProfileService,
        VendorProfileRepository vendorProfileRepository,
        VendorProfileQueryService vendorProfileQueryService
    ) {
        this.vendorProfileService = vendorProfileService;
        this.vendorProfileRepository = vendorProfileRepository;
        this.vendorProfileQueryService = vendorProfileQueryService;
    }

    /**
     * {@code POST  /vendor-profiles} : Create a new vendorProfile.
     *
     * @param vendorProfileDTO the vendorProfileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vendorProfileDTO, or with status {@code 400 (Bad Request)} if the vendorProfile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<VendorProfileDTO> createVendorProfile(@Valid @RequestBody VendorProfileDTO vendorProfileDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save VendorProfile : {}", vendorProfileDTO);
        if (vendorProfileDTO.getId() != null) {
            throw new BadRequestAlertException("A new vendorProfile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        vendorProfileDTO = vendorProfileService.save(vendorProfileDTO);
        return ResponseEntity.created(new URI("/api/vendor-profiles/" + vendorProfileDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, vendorProfileDTO.getId().toString()))
            .body(vendorProfileDTO);
    }

    /**
     * {@code PUT  /vendor-profiles/:id} : Updates an existing vendorProfile.
     *
     * @param id the id of the vendorProfileDTO to save.
     * @param vendorProfileDTO the vendorProfileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vendorProfileDTO,
     * or with status {@code 400 (Bad Request)} if the vendorProfileDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vendorProfileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<VendorProfileDTO> updateVendorProfile(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VendorProfileDTO vendorProfileDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update VendorProfile : {}, {}", id, vendorProfileDTO);
        if (vendorProfileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vendorProfileDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vendorProfileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        vendorProfileDTO = vendorProfileService.update(vendorProfileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vendorProfileDTO.getId().toString()))
            .body(vendorProfileDTO);
    }

    /**
     * {@code PATCH  /vendor-profiles/:id} : Partial updates given fields of an existing vendorProfile, field will ignore if it is null
     *
     * @param id the id of the vendorProfileDTO to save.
     * @param vendorProfileDTO the vendorProfileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vendorProfileDTO,
     * or with status {@code 400 (Bad Request)} if the vendorProfileDTO is not valid,
     * or with status {@code 404 (Not Found)} if the vendorProfileDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the vendorProfileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VendorProfileDTO> partialUpdateVendorProfile(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VendorProfileDTO vendorProfileDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update VendorProfile partially : {}, {}", id, vendorProfileDTO);
        if (vendorProfileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vendorProfileDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vendorProfileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VendorProfileDTO> result = vendorProfileService.partialUpdate(vendorProfileDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vendorProfileDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /vendor-profiles} : get all the vendorProfiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vendorProfiles in body.
     */
    @GetMapping("")
    public ResponseEntity<List<VendorProfileDTO>> getAllVendorProfiles(
        VendorProfileCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get VendorProfiles by criteria: {}", criteria);

        Page<VendorProfileDTO> page = vendorProfileQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vendor-profiles/count} : count all the vendorProfiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countVendorProfiles(VendorProfileCriteria criteria) {
        LOG.debug("REST request to count VendorProfiles by criteria: {}", criteria);
        return ResponseEntity.ok().body(vendorProfileQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /vendor-profiles/:id} : get the "id" vendorProfile.
     *
     * @param id the id of the vendorProfileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vendorProfileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<VendorProfileDTO> getVendorProfile(@PathVariable("id") Long id) {
        LOG.debug("REST request to get VendorProfile : {}", id);
        Optional<VendorProfileDTO> vendorProfileDTO = vendorProfileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vendorProfileDTO);
    }

    /**
     * {@code DELETE  /vendor-profiles/:id} : delete the "id" vendorProfile.
     *
     * @param id the id of the vendorProfileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendorProfile(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete VendorProfile : {}", id);
        vendorProfileService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /vendor-profiles/_search?query=:query} : search for the vendorProfile corresponding
     * to the query.
     *
     * @param query the query of the vendorProfile search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search")
    public ResponseEntity<List<VendorProfileDTO>> searchVendorProfiles(
        @RequestParam("query") String query,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to search for a page of VendorProfiles for query {}", query);
        try {
            Page<VendorProfileDTO> page = vendorProfileService.search(query, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (RuntimeException e) {
            throw ElasticsearchExceptionMapper.mapException(e);
        }
    }
}
