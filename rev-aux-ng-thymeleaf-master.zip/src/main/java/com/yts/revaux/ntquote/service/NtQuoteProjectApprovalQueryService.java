package com.yts.revaux.ntquote.service;

import com.yts.revaux.ntquote.domain.*; // for static metamodels
import com.yts.revaux.ntquote.domain.NtQuoteProjectApproval;
import com.yts.revaux.ntquote.repository.NtQuoteProjectApprovalRepository;
import com.yts.revaux.ntquote.repository.search.NtQuoteProjectApprovalSearchRepository;
import com.yts.revaux.ntquote.service.criteria.NtQuoteProjectApprovalCriteria;
import com.yts.revaux.ntquote.service.dto.NtQuoteProjectApprovalDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuoteProjectApprovalMapper;
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
 * Service for executing complex queries for {@link NtQuoteProjectApproval} entities in the database.
 * The main input is a {@link NtQuoteProjectApprovalCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link NtQuoteProjectApprovalDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NtQuoteProjectApprovalQueryService extends QueryService<NtQuoteProjectApproval> {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuoteProjectApprovalQueryService.class);

    private final NtQuoteProjectApprovalRepository ntQuoteProjectApprovalRepository;

    private final NtQuoteProjectApprovalMapper ntQuoteProjectApprovalMapper;

    private final NtQuoteProjectApprovalSearchRepository ntQuoteProjectApprovalSearchRepository;

    public NtQuoteProjectApprovalQueryService(
        NtQuoteProjectApprovalRepository ntQuoteProjectApprovalRepository,
        NtQuoteProjectApprovalMapper ntQuoteProjectApprovalMapper,
        NtQuoteProjectApprovalSearchRepository ntQuoteProjectApprovalSearchRepository
    ) {
        this.ntQuoteProjectApprovalRepository = ntQuoteProjectApprovalRepository;
        this.ntQuoteProjectApprovalMapper = ntQuoteProjectApprovalMapper;
        this.ntQuoteProjectApprovalSearchRepository = ntQuoteProjectApprovalSearchRepository;
    }

    /**
     * Return a {@link Page} of {@link NtQuoteProjectApprovalDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NtQuoteProjectApprovalDTO> findByCriteria(NtQuoteProjectApprovalCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NtQuoteProjectApproval> specification = createSpecification(criteria);
        return ntQuoteProjectApprovalRepository.findAll(specification, page).map(ntQuoteProjectApprovalMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NtQuoteProjectApprovalCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<NtQuoteProjectApproval> specification = createSpecification(criteria);
        return ntQuoteProjectApprovalRepository.count(specification);
    }

    /**
     * Function to convert {@link NtQuoteProjectApprovalCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NtQuoteProjectApproval> createSpecification(NtQuoteProjectApprovalCriteria criteria) {
        Specification<NtQuoteProjectApproval> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NtQuoteProjectApproval_.id));
            }
            if (criteria.getSrNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSrNo(), NtQuoteProjectApproval_.srNo));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), NtQuoteProjectApproval_.uid));
            }
            if (criteria.getApprovedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApprovedBy(), NtQuoteProjectApproval_.approvedBy));
            }
            if (criteria.getApprovalDate() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getApprovalDate(), NtQuoteProjectApproval_.approvalDate)
                );
            }
            if (criteria.getProgramManager() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getProgramManager(), NtQuoteProjectApproval_.programManager)
                );
            }
            if (criteria.getEngineering() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEngineering(), NtQuoteProjectApproval_.engineering));
            }
            if (criteria.getQuality() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuality(), NtQuoteProjectApproval_.quality));
            }
            if (criteria.getMaterials() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMaterials(), NtQuoteProjectApproval_.materials));
            }
            if (criteria.getPlantManager() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getPlantManager(), NtQuoteProjectApproval_.plantManager)
                );
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), NtQuoteProjectApproval_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), NtQuoteProjectApproval_.createdDate));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), NtQuoteProjectApproval_.updatedBy));
            }
            if (criteria.getUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedDate(), NtQuoteProjectApproval_.updatedDate));
            }
            if (criteria.getNtQuoteId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getNtQuoteId(), root ->
                        root.join(NtQuoteProjectApproval_.ntQuotes, JoinType.LEFT).get(NtQuote_.id)
                    )
                );
            }
        }
        return specification;
    }
}
