package com.yts.revaux.ntquote.service;

import com.yts.revaux.ntquote.domain.*; // for static metamodels
import com.yts.revaux.ntquote.domain.NtQuoteVendorQuote;
import com.yts.revaux.ntquote.repository.NtQuoteVendorQuoteRepository;
import com.yts.revaux.ntquote.repository.search.NtQuoteVendorQuoteSearchRepository;
import com.yts.revaux.ntquote.service.criteria.NtQuoteVendorQuoteCriteria;
import com.yts.revaux.ntquote.service.dto.NtQuoteVendorQuoteDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuoteVendorQuoteMapper;
import jakarta.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link NtQuoteVendorQuote} entities in the database.
 * The main input is a {@link NtQuoteVendorQuoteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link NtQuoteVendorQuoteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NtQuoteVendorQuoteQueryService extends QueryService<NtQuoteVendorQuote> {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuoteVendorQuoteQueryService.class);

    private final NtQuoteVendorQuoteRepository ntQuoteVendorQuoteRepository;

    private final NtQuoteVendorQuoteMapper ntQuoteVendorQuoteMapper;

    private final NtQuoteVendorQuoteSearchRepository ntQuoteVendorQuoteSearchRepository;

    public NtQuoteVendorQuoteQueryService(
        NtQuoteVendorQuoteRepository ntQuoteVendorQuoteRepository,
        NtQuoteVendorQuoteMapper ntQuoteVendorQuoteMapper,
        NtQuoteVendorQuoteSearchRepository ntQuoteVendorQuoteSearchRepository
    ) {
        this.ntQuoteVendorQuoteRepository = ntQuoteVendorQuoteRepository;
        this.ntQuoteVendorQuoteMapper = ntQuoteVendorQuoteMapper;
        this.ntQuoteVendorQuoteSearchRepository = ntQuoteVendorQuoteSearchRepository;
    }

    /**
     * Return a {@link Page} of {@link NtQuoteVendorQuoteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NtQuoteVendorQuoteDTO> findByCriteria(NtQuoteVendorQuoteCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NtQuoteVendorQuote> specification = createSpecification(criteria);
        return ntQuoteVendorQuoteRepository.findAll(specification, page).map(ntQuoteVendorQuoteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NtQuoteVendorQuoteCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<NtQuoteVendorQuote> specification = createSpecification(criteria);
        return ntQuoteVendorQuoteRepository.count(specification);
    }

    /**
     * Function to convert {@link NtQuoteVendorQuoteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NtQuoteVendorQuote> createSpecification(NtQuoteVendorQuoteCriteria criteria) {
        Specification<NtQuoteVendorQuote> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NtQuoteVendorQuote_.id));
            }
            if (criteria.getSrNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSrNo(), NtQuoteVendorQuote_.srNo));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), NtQuoteVendorQuote_.uid));
            }
            if (criteria.getVendorName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVendorName(), NtQuoteVendorQuote_.vendorName));
            }
            if (criteria.getQuoteDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuoteDate(), NtQuoteVendorQuote_.quoteDate));
            }
            if (criteria.getFileName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFileName(), NtQuoteVendorQuote_.fileName));
            }
            if (criteria.getCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountry(), NtQuoteVendorQuote_.country));
            }
            if (criteria.getBrowse() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBrowse(), NtQuoteVendorQuote_.browse));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), NtQuoteVendorQuote_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), NtQuoteVendorQuote_.createdDate));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), NtQuoteVendorQuote_.updatedBy));
            }
            if (criteria.getUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedDate(), NtQuoteVendorQuote_.updatedDate));
            }
            if (criteria.getNtQuoteId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getNtQuoteId(), root ->
                        root.join(NtQuoteVendorQuote_.ntQuote, JoinType.LEFT).get(NtQuote_.id)
                    )
                );
            }
        }
        return specification;
    }
}
