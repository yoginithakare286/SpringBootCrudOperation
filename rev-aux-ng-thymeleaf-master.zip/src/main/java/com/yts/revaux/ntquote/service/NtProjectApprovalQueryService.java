package com.yts.revaux.ntquote.service;

import com.yts.revaux.ntquote.domain.*; // for static metamodels
import com.yts.revaux.ntquote.domain.NtProjectApproval;
import com.yts.revaux.ntquote.repository.NtProjectApprovalRepository;
import com.yts.revaux.ntquote.repository.search.NtProjectApprovalSearchRepository;
import com.yts.revaux.ntquote.service.criteria.NtProjectApprovalCriteria;
import com.yts.revaux.ntquote.service.dto.NtProjectApprovalDTO;
import com.yts.revaux.ntquote.service.mapper.NtProjectApprovalMapper;
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
 * Service for executing complex queries for {@link NtProjectApproval} entities in the database.
 * The main input is a {@link NtProjectApprovalCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link NtProjectApprovalDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NtProjectApprovalQueryService extends QueryService<NtProjectApproval> {

    private static final Logger LOG = LoggerFactory.getLogger(NtProjectApprovalQueryService.class);

    private final NtProjectApprovalRepository ntProjectApprovalRepository;

    private final NtProjectApprovalMapper ntProjectApprovalMapper;

    private final NtProjectApprovalSearchRepository ntProjectApprovalSearchRepository;

    public NtProjectApprovalQueryService(
        NtProjectApprovalRepository ntProjectApprovalRepository,
        NtProjectApprovalMapper ntProjectApprovalMapper,
        NtProjectApprovalSearchRepository ntProjectApprovalSearchRepository
    ) {
        this.ntProjectApprovalRepository = ntProjectApprovalRepository;
        this.ntProjectApprovalMapper = ntProjectApprovalMapper;
        this.ntProjectApprovalSearchRepository = ntProjectApprovalSearchRepository;
    }

    /**
     * Return a {@link Page} of {@link NtProjectApprovalDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NtProjectApprovalDTO> findByCriteria(NtProjectApprovalCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NtProjectApproval> specification = createSpecification(criteria);
        return ntProjectApprovalRepository.findAll(specification, page).map(ntProjectApprovalMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NtProjectApprovalCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<NtProjectApproval> specification = createSpecification(criteria);
        return ntProjectApprovalRepository.count(specification);
    }

    /**
     * Function to convert {@link NtProjectApprovalCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NtProjectApproval> createSpecification(NtProjectApprovalCriteria criteria) {
        Specification<NtProjectApproval> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NtProjectApproval_.id));
            }
            if (criteria.getSrNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSrNo(), NtProjectApproval_.srNo));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), NtProjectApproval_.uid));
            }
            if (criteria.getApprovedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApprovedBy(), NtProjectApproval_.approvedBy));
            }
            if (criteria.getApprovalDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getApprovalDate(), NtProjectApproval_.approvalDate));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), NtProjectApproval_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), NtProjectApproval_.createdDate));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), NtProjectApproval_.updatedBy));
            }
            if (criteria.getUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedDate(), NtProjectApproval_.updatedDate));
            }
            if (criteria.getNtQuoteId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getNtQuoteId(), root ->
                        root.join(NtProjectApproval_.ntQuote, JoinType.LEFT).get(NtQuote_.id)
                    )
                );
            }
        }
        return specification;
    }
}
