package com.yts.revaux.ntquote.web.rest;

import com.yts.revaux.ntquote.repository.BuyerRfqPricesDetailRepository;
import com.yts.revaux.ntquote.service.BuyerRfqPricesDetailQueryService;
import com.yts.revaux.ntquote.service.BuyerRfqPricesDetailService;
import com.yts.revaux.ntquote.service.criteria.BuyerRfqPricesDetailCriteria;
import com.yts.revaux.ntquote.service.dto.BuyerRfqPricesDetailDTO;
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
 * REST controller for managing {@link com.yts.revaux.ntquote.domain.BuyerRfqPricesDetail}.
 */
@RestController
@RequestMapping("/api/buyer-rfq-prices-details")
public class BuyerRfqPricesDetailResource {

    private static final Logger LOG = LoggerFactory.getLogger(BuyerRfqPricesDetailResource.class);

    private static final String ENTITY_NAME = "buyerRfqPricesDetail";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BuyerRfqPricesDetailService buyerRfqPricesDetailService;

    private final BuyerRfqPricesDetailRepository buyerRfqPricesDetailRepository;

    private final BuyerRfqPricesDetailQueryService buyerRfqPricesDetailQueryService;

    public BuyerRfqPricesDetailResource(
        BuyerRfqPricesDetailService buyerRfqPricesDetailService,
        BuyerRfqPricesDetailRepository buyerRfqPricesDetailRepository,
        BuyerRfqPricesDetailQueryService buyerRfqPricesDetailQueryService
    ) {
        this.buyerRfqPricesDetailService = buyerRfqPricesDetailService;
        this.buyerRfqPricesDetailRepository = buyerRfqPricesDetailRepository;
        this.buyerRfqPricesDetailQueryService = buyerRfqPricesDetailQueryService;
    }

    /**
     * {@code POST  /buyer-rfq-prices-details} : Create a new buyerRfqPricesDetail.
     *
     * @param buyerRfqPricesDetailDTO the buyerRfqPricesDetailDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new buyerRfqPricesDetailDTO, or with status {@code 400 (Bad Request)} if the buyerRfqPricesDetail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BuyerRfqPricesDetailDTO> createBuyerRfqPricesDetail(
        @Valid @RequestBody BuyerRfqPricesDetailDTO buyerRfqPricesDetailDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save BuyerRfqPricesDetail : {}", buyerRfqPricesDetailDTO);
        if (buyerRfqPricesDetailDTO.getId() != null) {
            throw new BadRequestAlertException("A new buyerRfqPricesDetail cannot already have an ID", ENTITY_NAME, "idexists");
        }
        buyerRfqPricesDetailDTO = buyerRfqPricesDetailService.save(buyerRfqPricesDetailDTO);
        return ResponseEntity.created(new URI("/api/buyer-rfq-prices-details/" + buyerRfqPricesDetailDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, buyerRfqPricesDetailDTO.getId().toString()))
            .body(buyerRfqPricesDetailDTO);
    }

    /**
     * {@code PUT  /buyer-rfq-prices-details/:id} : Updates an existing buyerRfqPricesDetail.
     *
     * @param id the id of the buyerRfqPricesDetailDTO to save.
     * @param buyerRfqPricesDetailDTO the buyerRfqPricesDetailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated buyerRfqPricesDetailDTO,
     * or with status {@code 400 (Bad Request)} if the buyerRfqPricesDetailDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the buyerRfqPricesDetailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BuyerRfqPricesDetailDTO> updateBuyerRfqPricesDetail(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BuyerRfqPricesDetailDTO buyerRfqPricesDetailDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update BuyerRfqPricesDetail : {}, {}", id, buyerRfqPricesDetailDTO);
        if (buyerRfqPricesDetailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, buyerRfqPricesDetailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!buyerRfqPricesDetailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        buyerRfqPricesDetailDTO = buyerRfqPricesDetailService.update(buyerRfqPricesDetailDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, buyerRfqPricesDetailDTO.getId().toString()))
            .body(buyerRfqPricesDetailDTO);
    }

    /**
     * {@code PATCH  /buyer-rfq-prices-details/:id} : Partial updates given fields of an existing buyerRfqPricesDetail, field will ignore if it is null
     *
     * @param id the id of the buyerRfqPricesDetailDTO to save.
     * @param buyerRfqPricesDetailDTO the buyerRfqPricesDetailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated buyerRfqPricesDetailDTO,
     * or with status {@code 400 (Bad Request)} if the buyerRfqPricesDetailDTO is not valid,
     * or with status {@code 404 (Not Found)} if the buyerRfqPricesDetailDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the buyerRfqPricesDetailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BuyerRfqPricesDetailDTO> partialUpdateBuyerRfqPricesDetail(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BuyerRfqPricesDetailDTO buyerRfqPricesDetailDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update BuyerRfqPricesDetail partially : {}, {}", id, buyerRfqPricesDetailDTO);
        if (buyerRfqPricesDetailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, buyerRfqPricesDetailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!buyerRfqPricesDetailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BuyerRfqPricesDetailDTO> result = buyerRfqPricesDetailService.partialUpdate(buyerRfqPricesDetailDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, buyerRfqPricesDetailDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /buyer-rfq-prices-details} : get all the buyerRfqPricesDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of buyerRfqPricesDetails in body.
     */
    @GetMapping("")
    public ResponseEntity<List<BuyerRfqPricesDetailDTO>> getAllBuyerRfqPricesDetails(
        BuyerRfqPricesDetailCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get BuyerRfqPricesDetails by criteria: {}", criteria);

        Page<BuyerRfqPricesDetailDTO> page = buyerRfqPricesDetailQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /buyer-rfq-prices-details/count} : count all the buyerRfqPricesDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countBuyerRfqPricesDetails(BuyerRfqPricesDetailCriteria criteria) {
        LOG.debug("REST request to count BuyerRfqPricesDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(buyerRfqPricesDetailQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /buyer-rfq-prices-details/:id} : get the "id" buyerRfqPricesDetail.
     *
     * @param id the id of the buyerRfqPricesDetailDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the buyerRfqPricesDetailDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BuyerRfqPricesDetailDTO> getBuyerRfqPricesDetail(@PathVariable("id") Long id) {
        LOG.debug("REST request to get BuyerRfqPricesDetail : {}", id);
        Optional<BuyerRfqPricesDetailDTO> buyerRfqPricesDetailDTO = buyerRfqPricesDetailService.findOne(id);
        return ResponseUtil.wrapOrNotFound(buyerRfqPricesDetailDTO);
    }

    /**
     * {@code DELETE  /buyer-rfq-prices-details/:id} : delete the "id" buyerRfqPricesDetail.
     *
     * @param id the id of the buyerRfqPricesDetailDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBuyerRfqPricesDetail(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete BuyerRfqPricesDetail : {}", id);
        buyerRfqPricesDetailService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /buyer-rfq-prices-details/_search?query=:query} : search for the buyerRfqPricesDetail corresponding
     * to the query.
     *
     * @param query the query of the buyerRfqPricesDetail search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search")
    public ResponseEntity<List<BuyerRfqPricesDetailDTO>> searchBuyerRfqPricesDetails(
        @RequestParam("query") String query,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to search for a page of BuyerRfqPricesDetails for query {}", query);
        try {
            Page<BuyerRfqPricesDetailDTO> page = buyerRfqPricesDetailService.search(query, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (RuntimeException e) {
            throw ElasticsearchExceptionMapper.mapException(e);
        }
    }
}
