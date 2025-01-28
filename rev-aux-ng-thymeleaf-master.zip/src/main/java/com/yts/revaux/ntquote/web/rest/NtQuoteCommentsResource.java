package com.yts.revaux.ntquote.web.rest;

import com.yts.revaux.ntquote.repository.NtQuoteCommentsRepository;
import com.yts.revaux.ntquote.service.NtQuoteCommentsService;
import com.yts.revaux.ntquote.service.dto.NtQuoteCommentsDTO;
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
 * REST controller for managing {@link com.yts.revaux.ntquote.domain.NtQuoteComments}.
 */
@RestController
@RequestMapping("/api/nt-quote-comments")
public class NtQuoteCommentsResource {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuoteCommentsResource.class);

    private static final String ENTITY_NAME = "ntQuoteComments";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NtQuoteCommentsService ntQuoteCommentsService;

    private final NtQuoteCommentsRepository ntQuoteCommentsRepository;

    public NtQuoteCommentsResource(NtQuoteCommentsService ntQuoteCommentsService, NtQuoteCommentsRepository ntQuoteCommentsRepository) {
        this.ntQuoteCommentsService = ntQuoteCommentsService;
        this.ntQuoteCommentsRepository = ntQuoteCommentsRepository;
    }

    /**
     * {@code POST  /nt-quote-comments} : Create a new ntQuoteComments.
     *
     * @param ntQuoteCommentsDTO the ntQuoteCommentsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ntQuoteCommentsDTO, or with status {@code 400 (Bad Request)} if the ntQuoteComments has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<NtQuoteCommentsDTO> createNtQuoteComments(@Valid @RequestBody NtQuoteCommentsDTO ntQuoteCommentsDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save NtQuoteComments : {}", ntQuoteCommentsDTO);
        if (ntQuoteCommentsDTO.getId() != null) {
            throw new BadRequestAlertException("A new ntQuoteComments cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ntQuoteCommentsDTO = ntQuoteCommentsService.save(ntQuoteCommentsDTO);
        return ResponseEntity.created(new URI("/api/nt-quote-comments/" + ntQuoteCommentsDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, ntQuoteCommentsDTO.getId().toString()))
            .body(ntQuoteCommentsDTO);
    }

    /**
     * {@code PUT  /nt-quote-comments/:id} : Updates an existing ntQuoteComments.
     *
     * @param id the id of the ntQuoteCommentsDTO to save.
     * @param ntQuoteCommentsDTO the ntQuoteCommentsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ntQuoteCommentsDTO,
     * or with status {@code 400 (Bad Request)} if the ntQuoteCommentsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ntQuoteCommentsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<NtQuoteCommentsDTO> updateNtQuoteComments(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NtQuoteCommentsDTO ntQuoteCommentsDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update NtQuoteComments : {}, {}", id, ntQuoteCommentsDTO);
        if (ntQuoteCommentsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ntQuoteCommentsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ntQuoteCommentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ntQuoteCommentsDTO = ntQuoteCommentsService.update(ntQuoteCommentsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ntQuoteCommentsDTO.getId().toString()))
            .body(ntQuoteCommentsDTO);
    }

    /**
     * {@code PATCH  /nt-quote-comments/:id} : Partial updates given fields of an existing ntQuoteComments, field will ignore if it is null
     *
     * @param id the id of the ntQuoteCommentsDTO to save.
     * @param ntQuoteCommentsDTO the ntQuoteCommentsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ntQuoteCommentsDTO,
     * or with status {@code 400 (Bad Request)} if the ntQuoteCommentsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ntQuoteCommentsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ntQuoteCommentsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NtQuoteCommentsDTO> partialUpdateNtQuoteComments(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NtQuoteCommentsDTO ntQuoteCommentsDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update NtQuoteComments partially : {}, {}", id, ntQuoteCommentsDTO);
        if (ntQuoteCommentsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ntQuoteCommentsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ntQuoteCommentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NtQuoteCommentsDTO> result = ntQuoteCommentsService.partialUpdate(ntQuoteCommentsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ntQuoteCommentsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /nt-quote-comments} : get all the ntQuoteComments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ntQuoteComments in body.
     */
    @GetMapping("")
    public ResponseEntity<List<NtQuoteCommentsDTO>> getAllNtQuoteComments(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of NtQuoteComments");
        Page<NtQuoteCommentsDTO> page = ntQuoteCommentsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nt-quote-comments/:id} : get the "id" ntQuoteComments.
     *
     * @param id the id of the ntQuoteCommentsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ntQuoteCommentsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<NtQuoteCommentsDTO> getNtQuoteComments(@PathVariable("id") Long id) {
        LOG.debug("REST request to get NtQuoteComments : {}", id);
        Optional<NtQuoteCommentsDTO> ntQuoteCommentsDTO = ntQuoteCommentsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ntQuoteCommentsDTO);
    }

    /**
     * {@code DELETE  /nt-quote-comments/:id} : delete the "id" ntQuoteComments.
     *
     * @param id the id of the ntQuoteCommentsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNtQuoteComments(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete NtQuoteComments : {}", id);
        ntQuoteCommentsService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /nt-quote-comments/_search?query=:query} : search for the ntQuoteComments corresponding
     * to the query.
     *
     * @param query the query of the ntQuoteComments search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search")
    public ResponseEntity<List<NtQuoteCommentsDTO>> searchNtQuoteComments(
        @RequestParam("query") String query,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to search for a page of NtQuoteComments for query {}", query);
        try {
            Page<NtQuoteCommentsDTO> page = ntQuoteCommentsService.search(query, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (RuntimeException e) {
            throw ElasticsearchExceptionMapper.mapException(e);
        }
    }
}
