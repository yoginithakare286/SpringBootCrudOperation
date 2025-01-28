package com.yts.revaux.ntquote.service;

import com.yts.revaux.ntquote.domain.*; // for static metamodels
import com.yts.revaux.ntquote.domain.NtQuoteProjectConsiderations;
import com.yts.revaux.ntquote.repository.NtQuoteProjectConsiderationsRepository;
import com.yts.revaux.ntquote.repository.search.NtQuoteProjectConsiderationsSearchRepository;
import com.yts.revaux.ntquote.service.criteria.NtQuoteProjectConsiderationsCriteria;
import com.yts.revaux.ntquote.service.dto.NtQuoteProjectConsiderationsDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuoteProjectConsiderationsMapper;
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
 * Service for executing complex queries for {@link NtQuoteProjectConsiderations} entities in the database.
 * The main input is a {@link NtQuoteProjectConsiderationsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link NtQuoteProjectConsiderationsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NtQuoteProjectConsiderationsQueryService extends QueryService<NtQuoteProjectConsiderations> {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuoteProjectConsiderationsQueryService.class);

    private final NtQuoteProjectConsiderationsRepository ntQuoteProjectConsiderationsRepository;

    private final NtQuoteProjectConsiderationsMapper ntQuoteProjectConsiderationsMapper;

    private final NtQuoteProjectConsiderationsSearchRepository ntQuoteProjectConsiderationsSearchRepository;

    public NtQuoteProjectConsiderationsQueryService(
        NtQuoteProjectConsiderationsRepository ntQuoteProjectConsiderationsRepository,
        NtQuoteProjectConsiderationsMapper ntQuoteProjectConsiderationsMapper,
        NtQuoteProjectConsiderationsSearchRepository ntQuoteProjectConsiderationsSearchRepository
    ) {
        this.ntQuoteProjectConsiderationsRepository = ntQuoteProjectConsiderationsRepository;
        this.ntQuoteProjectConsiderationsMapper = ntQuoteProjectConsiderationsMapper;
        this.ntQuoteProjectConsiderationsSearchRepository = ntQuoteProjectConsiderationsSearchRepository;
    }

    /**
     * Return a {@link Page} of {@link NtQuoteProjectConsiderationsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NtQuoteProjectConsiderationsDTO> findByCriteria(NtQuoteProjectConsiderationsCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NtQuoteProjectConsiderations> specification = createSpecification(criteria);
        return ntQuoteProjectConsiderationsRepository.findAll(specification, page).map(ntQuoteProjectConsiderationsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NtQuoteProjectConsiderationsCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<NtQuoteProjectConsiderations> specification = createSpecification(criteria);
        return ntQuoteProjectConsiderationsRepository.count(specification);
    }

    /**
     * Function to convert {@link NtQuoteProjectConsiderationsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NtQuoteProjectConsiderations> createSpecification(NtQuoteProjectConsiderationsCriteria criteria) {
        Specification<NtQuoteProjectConsiderations> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NtQuoteProjectConsiderations_.id));
            }
            if (criteria.getSrNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSrNo(), NtQuoteProjectConsiderations_.srNo));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), NtQuoteProjectConsiderations_.uid));
            }
            if (criteria.getProjectConsideration() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getProjectConsideration(), NtQuoteProjectConsiderations_.projectConsideration)
                );
            }
            if (criteria.getChoice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getChoice(), NtQuoteProjectConsiderations_.choice));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), NtQuoteProjectConsiderations_.comments));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getCreatedBy(), NtQuoteProjectConsiderations_.createdBy)
                );
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getCreatedDate(), NtQuoteProjectConsiderations_.createdDate)
                );
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getUpdatedBy(), NtQuoteProjectConsiderations_.updatedBy)
                );
            }
            if (criteria.getUpdatedDate() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getUpdatedDate(), NtQuoteProjectConsiderations_.updatedDate)
                );
            }
            if (criteria.getNtQuoteId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getNtQuoteId(), root ->
                        root.join(NtQuoteProjectConsiderations_.ntQuote, JoinType.LEFT).get(NtQuote_.id)
                    )
                );
            }
        }
        return specification;
    }
}
