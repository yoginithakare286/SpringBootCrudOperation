package com.yts.revaux.ntquote.service;

import com.yts.revaux.ntquote.domain.*; // for static metamodels
import com.yts.revaux.ntquote.domain.NtQuoteContractReviewInformation;
import com.yts.revaux.ntquote.repository.NtQuoteContractReviewInformationRepository;
import com.yts.revaux.ntquote.repository.search.NtQuoteContractReviewInformationSearchRepository;
import com.yts.revaux.ntquote.service.criteria.NtQuoteContractReviewInformationCriteria;
import com.yts.revaux.ntquote.service.dto.NtQuoteContractReviewInformationDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuoteContractReviewInformationMapper;
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
 * Service for executing complex queries for {@link NtQuoteContractReviewInformation} entities in the database.
 * The main input is a {@link NtQuoteContractReviewInformationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link NtQuoteContractReviewInformationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NtQuoteContractReviewInformationQueryService extends QueryService<NtQuoteContractReviewInformation> {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuoteContractReviewInformationQueryService.class);

    private final NtQuoteContractReviewInformationRepository ntQuoteContractReviewInformationRepository;

    private final NtQuoteContractReviewInformationMapper ntQuoteContractReviewInformationMapper;

    private final NtQuoteContractReviewInformationSearchRepository ntQuoteContractReviewInformationSearchRepository;

    public NtQuoteContractReviewInformationQueryService(
        NtQuoteContractReviewInformationRepository ntQuoteContractReviewInformationRepository,
        NtQuoteContractReviewInformationMapper ntQuoteContractReviewInformationMapper,
        NtQuoteContractReviewInformationSearchRepository ntQuoteContractReviewInformationSearchRepository
    ) {
        this.ntQuoteContractReviewInformationRepository = ntQuoteContractReviewInformationRepository;
        this.ntQuoteContractReviewInformationMapper = ntQuoteContractReviewInformationMapper;
        this.ntQuoteContractReviewInformationSearchRepository = ntQuoteContractReviewInformationSearchRepository;
    }

    /**
     * Return a {@link Page} of {@link NtQuoteContractReviewInformationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NtQuoteContractReviewInformationDTO> findByCriteria(NtQuoteContractReviewInformationCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NtQuoteContractReviewInformation> specification = createSpecification(criteria);
        return ntQuoteContractReviewInformationRepository.findAll(specification, page).map(ntQuoteContractReviewInformationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NtQuoteContractReviewInformationCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<NtQuoteContractReviewInformation> specification = createSpecification(criteria);
        return ntQuoteContractReviewInformationRepository.count(specification);
    }

    /**
     * Function to convert {@link NtQuoteContractReviewInformationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NtQuoteContractReviewInformation> createSpecification(NtQuoteContractReviewInformationCriteria criteria) {
        Specification<NtQuoteContractReviewInformation> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NtQuoteContractReviewInformation_.id));
            }
            if (criteria.getSrNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSrNo(), NtQuoteContractReviewInformation_.srNo));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), NtQuoteContractReviewInformation_.uid));
            }
            if (criteria.getContractNumber() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getContractNumber(), NtQuoteContractReviewInformation_.contractNumber)
                );
            }
            if (criteria.getRevision() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getRevision(), NtQuoteContractReviewInformation_.revision)
                );
            }
            if (criteria.getReviewDate() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getReviewDate(), NtQuoteContractReviewInformation_.reviewDate)
                );
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getCreatedBy(), NtQuoteContractReviewInformation_.createdBy)
                );
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getCreatedDate(), NtQuoteContractReviewInformation_.createdDate)
                );
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getUpdatedBy(), NtQuoteContractReviewInformation_.updatedBy)
                );
            }
            if (criteria.getUpdatedDate() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getUpdatedDate(), NtQuoteContractReviewInformation_.updatedDate)
                );
            }
            if (criteria.getNtQuoteId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getNtQuoteId(), root ->
                        root.join(NtQuoteContractReviewInformation_.ntQuote, JoinType.LEFT).get(NtQuote_.id)
                    )
                );
            }
        }
        return specification;
    }
}
