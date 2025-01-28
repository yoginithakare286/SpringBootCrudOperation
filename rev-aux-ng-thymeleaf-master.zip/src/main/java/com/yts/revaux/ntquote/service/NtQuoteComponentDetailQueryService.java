package com.yts.revaux.ntquote.service;

import com.yts.revaux.ntquote.domain.*; // for static metamodels
import com.yts.revaux.ntquote.domain.NtQuoteComponentDetail;
import com.yts.revaux.ntquote.repository.NtQuoteComponentDetailRepository;
import com.yts.revaux.ntquote.repository.search.NtQuoteComponentDetailSearchRepository;
import com.yts.revaux.ntquote.service.criteria.NtQuoteComponentDetailCriteria;
import com.yts.revaux.ntquote.service.dto.NtQuoteComponentDetailDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuoteComponentDetailMapper;
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
 * Service for executing complex queries for {@link NtQuoteComponentDetail} entities in the database.
 * The main input is a {@link NtQuoteComponentDetailCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link NtQuoteComponentDetailDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NtQuoteComponentDetailQueryService extends QueryService<NtQuoteComponentDetail> {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuoteComponentDetailQueryService.class);

    private final NtQuoteComponentDetailRepository ntQuoteComponentDetailRepository;

    private final NtQuoteComponentDetailMapper ntQuoteComponentDetailMapper;

    private final NtQuoteComponentDetailSearchRepository ntQuoteComponentDetailSearchRepository;

    public NtQuoteComponentDetailQueryService(
        NtQuoteComponentDetailRepository ntQuoteComponentDetailRepository,
        NtQuoteComponentDetailMapper ntQuoteComponentDetailMapper,
        NtQuoteComponentDetailSearchRepository ntQuoteComponentDetailSearchRepository
    ) {
        this.ntQuoteComponentDetailRepository = ntQuoteComponentDetailRepository;
        this.ntQuoteComponentDetailMapper = ntQuoteComponentDetailMapper;
        this.ntQuoteComponentDetailSearchRepository = ntQuoteComponentDetailSearchRepository;
    }

    /**
     * Return a {@link Page} of {@link NtQuoteComponentDetailDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NtQuoteComponentDetailDTO> findByCriteria(NtQuoteComponentDetailCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NtQuoteComponentDetail> specification = createSpecification(criteria);
        return ntQuoteComponentDetailRepository.findAll(specification, page).map(ntQuoteComponentDetailMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NtQuoteComponentDetailCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<NtQuoteComponentDetail> specification = createSpecification(criteria);
        return ntQuoteComponentDetailRepository.count(specification);
    }

    /**
     * Function to convert {@link NtQuoteComponentDetailCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NtQuoteComponentDetail> createSpecification(NtQuoteComponentDetailCriteria criteria) {
        Specification<NtQuoteComponentDetail> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NtQuoteComponentDetail_.id));
            }
            if (criteria.getSrNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSrNo(), NtQuoteComponentDetail_.srNo));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), NtQuoteComponentDetail_.uid));
            }
            if (criteria.getMaterialDescription() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getMaterialDescription(), NtQuoteComponentDetail_.materialDescription)
                );
            }
            if (criteria.getPartNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPartNumber(), NtQuoteComponentDetail_.partNumber));
            }
            if (criteria.getEau() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEau(), NtQuoteComponentDetail_.eau));
            }
            if (criteria.getManufacturingLocation() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getManufacturingLocation(), NtQuoteComponentDetail_.manufacturingLocation)
                );
            }
            if (criteria.getFobLocation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFobLocation(), NtQuoteComponentDetail_.fobLocation));
            }
            if (criteria.getPackingRequirements() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getPackingRequirements(), NtQuoteComponentDetail_.packingRequirements)
                );
            }
            if (criteria.getMachineSize() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMachineSize(), NtQuoteComponentDetail_.machineSize));
            }
            if (criteria.getCycleTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCycleTime(), NtQuoteComponentDetail_.cycleTime));
            }
            if (criteria.getPartWeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPartWeight(), NtQuoteComponentDetail_.partWeight));
            }
            if (criteria.getRunnerWeight() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getRunnerWeight(), NtQuoteComponentDetail_.runnerWeight)
                );
            }
            if (criteria.getCavities() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCavities(), NtQuoteComponentDetail_.cavities));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), NtQuoteComponentDetail_.comments));
            }
            if (criteria.getRiskLevel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRiskLevel(), NtQuoteComponentDetail_.riskLevel));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), NtQuoteComponentDetail_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), NtQuoteComponentDetail_.createdDate));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), NtQuoteComponentDetail_.updatedBy));
            }
            if (criteria.getUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedDate(), NtQuoteComponentDetail_.updatedDate));
            }
            if (criteria.getNtQuoteId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getNtQuoteId(), root ->
                        root.join(NtQuoteComponentDetail_.ntQuote, JoinType.LEFT).get(NtQuote_.id)
                    )
                );
            }
            if (criteria.getMaterialPriceId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getMaterialPriceId(), root ->
                        root.join(NtQuoteComponentDetail_.materialPrice, JoinType.LEFT).get(BuyerRfqPricesDetail_.id)
                    )
                );
            }
        }
        return specification;
    }
}
