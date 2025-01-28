package com.yts.revaux.ntquote.service;

import com.yts.revaux.ntquote.domain.*; // for static metamodels
import com.yts.revaux.ntquote.domain.NtQuoteCustomerPo;
import com.yts.revaux.ntquote.repository.NtQuoteCustomerPoRepository;
import com.yts.revaux.ntquote.repository.search.NtQuoteCustomerPoSearchRepository;
import com.yts.revaux.ntquote.service.criteria.NtQuoteCustomerPoCriteria;
import com.yts.revaux.ntquote.service.dto.NtQuoteCustomerPoDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuoteCustomerPoMapper;
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
 * Service for executing complex queries for {@link NtQuoteCustomerPo} entities in the database.
 * The main input is a {@link NtQuoteCustomerPoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link NtQuoteCustomerPoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NtQuoteCustomerPoQueryService extends QueryService<NtQuoteCustomerPo> {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuoteCustomerPoQueryService.class);

    private final NtQuoteCustomerPoRepository ntQuoteCustomerPoRepository;

    private final NtQuoteCustomerPoMapper ntQuoteCustomerPoMapper;

    private final NtQuoteCustomerPoSearchRepository ntQuoteCustomerPoSearchRepository;

    public NtQuoteCustomerPoQueryService(
        NtQuoteCustomerPoRepository ntQuoteCustomerPoRepository,
        NtQuoteCustomerPoMapper ntQuoteCustomerPoMapper,
        NtQuoteCustomerPoSearchRepository ntQuoteCustomerPoSearchRepository
    ) {
        this.ntQuoteCustomerPoRepository = ntQuoteCustomerPoRepository;
        this.ntQuoteCustomerPoMapper = ntQuoteCustomerPoMapper;
        this.ntQuoteCustomerPoSearchRepository = ntQuoteCustomerPoSearchRepository;
    }

    /**
     * Return a {@link Page} of {@link NtQuoteCustomerPoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NtQuoteCustomerPoDTO> findByCriteria(NtQuoteCustomerPoCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NtQuoteCustomerPo> specification = createSpecification(criteria);
        return ntQuoteCustomerPoRepository.findAll(specification, page).map(ntQuoteCustomerPoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NtQuoteCustomerPoCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<NtQuoteCustomerPo> specification = createSpecification(criteria);
        return ntQuoteCustomerPoRepository.count(specification);
    }

    /**
     * Function to convert {@link NtQuoteCustomerPoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NtQuoteCustomerPo> createSpecification(NtQuoteCustomerPoCriteria criteria) {
        Specification<NtQuoteCustomerPo> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NtQuoteCustomerPo_.id));
            }
            if (criteria.getSrNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSrNo(), NtQuoteCustomerPo_.srNo));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), NtQuoteCustomerPo_.uid));
            }
            if (criteria.getCustomerName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCustomerName(), NtQuoteCustomerPo_.customerName));
            }
            if (criteria.getQuoteDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuoteDate(), NtQuoteCustomerPo_.quoteDate));
            }
            if (criteria.getFileName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFileName(), NtQuoteCustomerPo_.fileName));
            }
            if (criteria.getCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountry(), NtQuoteCustomerPo_.country));
            }
            if (criteria.getBrowse() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBrowse(), NtQuoteCustomerPo_.browse));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), NtQuoteCustomerPo_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), NtQuoteCustomerPo_.createdDate));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), NtQuoteCustomerPo_.updatedBy));
            }
            if (criteria.getUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedDate(), NtQuoteCustomerPo_.updatedDate));
            }
            if (criteria.getNtQuoteId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getNtQuoteId(), root ->
                        root.join(NtQuoteCustomerPo_.ntQuote, JoinType.LEFT).get(NtQuote_.id)
                    )
                );
            }
        }
        return specification;
    }
}
