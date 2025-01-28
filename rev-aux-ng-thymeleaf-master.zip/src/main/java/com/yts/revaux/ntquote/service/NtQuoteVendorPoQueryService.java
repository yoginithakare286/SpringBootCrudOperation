package com.yts.revaux.ntquote.service;

import com.yts.revaux.ntquote.domain.*; // for static metamodels
import com.yts.revaux.ntquote.domain.NtQuoteVendorPo;
import com.yts.revaux.ntquote.repository.NtQuoteVendorPoRepository;
import com.yts.revaux.ntquote.repository.search.NtQuoteVendorPoSearchRepository;
import com.yts.revaux.ntquote.service.criteria.NtQuoteVendorPoCriteria;
import com.yts.revaux.ntquote.service.dto.NtQuoteVendorPoDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuoteVendorPoMapper;
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
 * Service for executing complex queries for {@link NtQuoteVendorPo} entities in the database.
 * The main input is a {@link NtQuoteVendorPoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link NtQuoteVendorPoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NtQuoteVendorPoQueryService extends QueryService<NtQuoteVendorPo> {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuoteVendorPoQueryService.class);

    private final NtQuoteVendorPoRepository ntQuoteVendorPoRepository;

    private final NtQuoteVendorPoMapper ntQuoteVendorPoMapper;

    private final NtQuoteVendorPoSearchRepository ntQuoteVendorPoSearchRepository;

    public NtQuoteVendorPoQueryService(
        NtQuoteVendorPoRepository ntQuoteVendorPoRepository,
        NtQuoteVendorPoMapper ntQuoteVendorPoMapper,
        NtQuoteVendorPoSearchRepository ntQuoteVendorPoSearchRepository
    ) {
        this.ntQuoteVendorPoRepository = ntQuoteVendorPoRepository;
        this.ntQuoteVendorPoMapper = ntQuoteVendorPoMapper;
        this.ntQuoteVendorPoSearchRepository = ntQuoteVendorPoSearchRepository;
    }

    /**
     * Return a {@link Page} of {@link NtQuoteVendorPoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NtQuoteVendorPoDTO> findByCriteria(NtQuoteVendorPoCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NtQuoteVendorPo> specification = createSpecification(criteria);
        return ntQuoteVendorPoRepository.findAll(specification, page).map(ntQuoteVendorPoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NtQuoteVendorPoCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<NtQuoteVendorPo> specification = createSpecification(criteria);
        return ntQuoteVendorPoRepository.count(specification);
    }

    /**
     * Function to convert {@link NtQuoteVendorPoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NtQuoteVendorPo> createSpecification(NtQuoteVendorPoCriteria criteria) {
        Specification<NtQuoteVendorPo> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NtQuoteVendorPo_.id));
            }
            if (criteria.getSrNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSrNo(), NtQuoteVendorPo_.srNo));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), NtQuoteVendorPo_.uid));
            }
            if (criteria.getVendorName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVendorName(), NtQuoteVendorPo_.vendorName));
            }
            if (criteria.getQuoteDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuoteDate(), NtQuoteVendorPo_.quoteDate));
            }
            if (criteria.getFileName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFileName(), NtQuoteVendorPo_.fileName));
            }
            if (criteria.getCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountry(), NtQuoteVendorPo_.country));
            }
            if (criteria.getBrowse() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBrowse(), NtQuoteVendorPo_.browse));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), NtQuoteVendorPo_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), NtQuoteVendorPo_.createdDate));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), NtQuoteVendorPo_.updatedBy));
            }
            if (criteria.getUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedDate(), NtQuoteVendorPo_.updatedDate));
            }
            if (criteria.getNtQuoteId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getNtQuoteId(), root -> root.join(NtQuoteVendorPo_.ntQuote, JoinType.LEFT).get(NtQuote_.id))
                );
            }
        }
        return specification;
    }
}
