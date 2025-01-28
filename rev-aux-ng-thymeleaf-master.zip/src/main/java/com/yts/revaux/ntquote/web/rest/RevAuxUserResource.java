package com.yts.revaux.ntquote.web.rest;

import com.yts.revaux.ntquote.repository.RevAuxUserRepository;
import com.yts.revaux.ntquote.service.RevAuxUserQueryService;
import com.yts.revaux.ntquote.service.RevAuxUserService;
import com.yts.revaux.ntquote.service.criteria.RevAuxUserCriteria;
import com.yts.revaux.ntquote.service.dto.RevAuxUserDTO;
import com.yts.revaux.ntquote.web.rest.errors.BadRequestAlertException;
import com.yts.revaux.ntquote.web.rest.errors.ElasticsearchExceptionMapper;
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
 * REST controller for managing {@link com.yts.revaux.ntquote.domain.RevAuxUser}.
 */
@RestController
@RequestMapping("/api/rev-aux-users")
public class RevAuxUserResource {

    private static final Logger LOG = LoggerFactory.getLogger(RevAuxUserResource.class);

    private static final String ENTITY_NAME = "revAuxUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RevAuxUserService revAuxUserService;

    private final RevAuxUserRepository revAuxUserRepository;

    private final RevAuxUserQueryService revAuxUserQueryService;

    public RevAuxUserResource(
        RevAuxUserService revAuxUserService,
        RevAuxUserRepository revAuxUserRepository,
        RevAuxUserQueryService revAuxUserQueryService
    ) {
        this.revAuxUserService = revAuxUserService;
        this.revAuxUserRepository = revAuxUserRepository;
        this.revAuxUserQueryService = revAuxUserQueryService;
    }

    /**
     * {@code POST  /rev-aux-users} : Create a new revAuxUser.
     *
     * @param revAuxUserDTO the revAuxUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new revAuxUserDTO, or with status {@code 400 (Bad Request)} if the revAuxUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RevAuxUserDTO> createRevAuxUser(@RequestBody RevAuxUserDTO revAuxUserDTO) throws URISyntaxException {
        LOG.debug("REST request to save RevAuxUser : {}", revAuxUserDTO);
        if (revAuxUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new revAuxUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (Objects.isNull(revAuxUserDTO.getInternalUser())) {
            throw new BadRequestAlertException("Invalid association value provided", ENTITY_NAME, "null");
        }
        revAuxUserDTO = revAuxUserService.save(revAuxUserDTO);
        return ResponseEntity.created(new URI("/api/rev-aux-users/" + revAuxUserDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, revAuxUserDTO.getId().toString()))
            .body(revAuxUserDTO);
    }

    /**
     * {@code PUT  /rev-aux-users/:id} : Updates an existing revAuxUser.
     *
     * @param id the id of the revAuxUserDTO to save.
     * @param revAuxUserDTO the revAuxUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated revAuxUserDTO,
     * or with status {@code 400 (Bad Request)} if the revAuxUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the revAuxUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RevAuxUserDTO> updateRevAuxUser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RevAuxUserDTO revAuxUserDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update RevAuxUser : {}, {}", id, revAuxUserDTO);
        if (revAuxUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, revAuxUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!revAuxUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        revAuxUserDTO = revAuxUserService.update(revAuxUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, revAuxUserDTO.getId().toString()))
            .body(revAuxUserDTO);
    }

    /**
     * {@code PATCH  /rev-aux-users/:id} : Partial updates given fields of an existing revAuxUser, field will ignore if it is null
     *
     * @param id the id of the revAuxUserDTO to save.
     * @param revAuxUserDTO the revAuxUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated revAuxUserDTO,
     * or with status {@code 400 (Bad Request)} if the revAuxUserDTO is not valid,
     * or with status {@code 404 (Not Found)} if the revAuxUserDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the revAuxUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RevAuxUserDTO> partialUpdateRevAuxUser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RevAuxUserDTO revAuxUserDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update RevAuxUser partially : {}, {}", id, revAuxUserDTO);
        if (revAuxUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, revAuxUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!revAuxUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RevAuxUserDTO> result = revAuxUserService.partialUpdate(revAuxUserDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, revAuxUserDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /rev-aux-users} : get all the revAuxUsers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of revAuxUsers in body.
     */
    @GetMapping("")
    public ResponseEntity<List<RevAuxUserDTO>> getAllRevAuxUsers(
        RevAuxUserCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get RevAuxUsers by criteria: {}", criteria);

        Page<RevAuxUserDTO> page = revAuxUserQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rev-aux-users/count} : count all the revAuxUsers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countRevAuxUsers(RevAuxUserCriteria criteria) {
        LOG.debug("REST request to count RevAuxUsers by criteria: {}", criteria);
        return ResponseEntity.ok().body(revAuxUserQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /rev-aux-users/:id} : get the "id" revAuxUser.
     *
     * @param id the id of the revAuxUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the revAuxUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RevAuxUserDTO> getRevAuxUser(@PathVariable("id") Long id) {
        LOG.debug("REST request to get RevAuxUser : {}", id);
        Optional<RevAuxUserDTO> revAuxUserDTO = revAuxUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(revAuxUserDTO);
    }

    /**
     * {@code DELETE  /rev-aux-users/:id} : delete the "id" revAuxUser.
     *
     * @param id the id of the revAuxUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRevAuxUser(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete RevAuxUser : {}", id);
        revAuxUserService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /rev-aux-users/_search?query=:query} : search for the revAuxUser corresponding
     * to the query.
     *
     * @param query the query of the revAuxUser search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search")
    public ResponseEntity<List<RevAuxUserDTO>> searchRevAuxUsers(
        @RequestParam("query") String query,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to search for a page of RevAuxUsers for query {}", query);
        try {
            Page<RevAuxUserDTO> page = revAuxUserService.search(query, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (RuntimeException e) {
            throw ElasticsearchExceptionMapper.mapException(e);
        }
    }
}
