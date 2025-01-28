package com.yts.revaux.ntquote.service;

import com.yts.revaux.ntquote.domain.*; // for static metamodels
import com.yts.revaux.ntquote.domain.NtQuotePartInformationVersion;
import com.yts.revaux.ntquote.repository.NtQuotePartInformationVersionRepository;
import com.yts.revaux.ntquote.repository.search.NtQuotePartInformationVersionSearchRepository;
import com.yts.revaux.ntquote.service.criteria.NtQuotePartInformationVersionCriteria;
import com.yts.revaux.ntquote.service.dto.NtQuotePartInformationVersionDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuotePartInformationVersionMapper;
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
 * Service for executing complex queries for {@link NtQuotePartInformationVersion} entities in the database.
 * The main input is a {@link NtQuotePartInformationVersionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link NtQuotePartInformationVersionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NtQuotePartInformationVersionQueryService extends QueryService<NtQuotePartInformationVersion> {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuotePartInformationVersionQueryService.class);

    private final NtQuotePartInformationVersionRepository ntQuotePartInformationVersionRepository;

    private final NtQuotePartInformationVersionMapper ntQuotePartInformationVersionMapper;

    private final NtQuotePartInformationVersionSearchRepository ntQuotePartInformationVersionSearchRepository;

    public NtQuotePartInformationVersionQueryService(
        NtQuotePartInformationVersionRepository ntQuotePartInformationVersionRepository,
        NtQuotePartInformationVersionMapper ntQuotePartInformationVersionMapper,
        NtQuotePartInformationVersionSearchRepository ntQuotePartInformationVersionSearchRepository
    ) {
        this.ntQuotePartInformationVersionRepository = ntQuotePartInformationVersionRepository;
        this.ntQuotePartInformationVersionMapper = ntQuotePartInformationVersionMapper;
        this.ntQuotePartInformationVersionSearchRepository = ntQuotePartInformationVersionSearchRepository;
    }

    /**
     * Return a {@link Page} of {@link NtQuotePartInformationVersionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NtQuotePartInformationVersionDTO> findByCriteria(NtQuotePartInformationVersionCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NtQuotePartInformationVersion> specification = createSpecification(criteria);
        return ntQuotePartInformationVersionRepository.findAll(specification, page).map(ntQuotePartInformationVersionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NtQuotePartInformationVersionCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<NtQuotePartInformationVersion> specification = createSpecification(criteria);
        return ntQuotePartInformationVersionRepository.count(specification);
    }

    /**
     * Function to convert {@link NtQuotePartInformationVersionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NtQuotePartInformationVersion> createSpecification(NtQuotePartInformationVersionCriteria criteria) {
        Specification<NtQuotePartInformationVersion> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NtQuotePartInformationVersion_.id));
            }
            if (criteria.getSrNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSrNo(), NtQuotePartInformationVersion_.srNo));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), NtQuotePartInformationVersion_.uid));
            }
            if (criteria.getMaterialDescription() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getMaterialDescription(), NtQuotePartInformationVersion_.materialDescription)
                );
            }
            if (criteria.getPartNumber() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getPartNumber(), NtQuotePartInformationVersion_.partNumber)
                );
            }
            if (criteria.getCadFile() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCadFile(), NtQuotePartInformationVersion_.cadFile));
            }
            if (criteria.getEau() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEau(), NtQuotePartInformationVersion_.eau));
            }
            if (criteria.getPartWeight() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getPartWeight(), NtQuotePartInformationVersion_.partWeight)
                );
            }
            if (criteria.getMaterialType() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getMaterialType(), NtQuotePartInformationVersion_.materialType)
                );
            }
            if (criteria.getMaterialCost() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getMaterialCost(), NtQuotePartInformationVersion_.materialCost)
                );
            }
            if (criteria.getExtendedMaterialCostPer() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getExtendedMaterialCostPer(), NtQuotePartInformationVersion_.extendedMaterialCostPer)
                );
            }
            if (criteria.getExternalMachineCostPer() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getExternalMachineCostPer(), NtQuotePartInformationVersion_.externalMachineCostPer)
                );
            }
            if (criteria.getPurchaseComponentCost() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getPurchaseComponentCost(), NtQuotePartInformationVersion_.purchaseComponentCost)
                );
            }
            if (criteria.getSecondaryExternalOperationCost() != null) {
                specification = specification.and(
                    buildRangeSpecification(
                        criteria.getSecondaryExternalOperationCost(),
                        NtQuotePartInformationVersion_.secondaryExternalOperationCost
                    )
                );
            }
            if (criteria.getOverhead() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOverhead(), NtQuotePartInformationVersion_.overhead));
            }
            if (criteria.getPackLogisticCostPer() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getPackLogisticCostPer(), NtQuotePartInformationVersion_.packLogisticCostPer)
                );
            }
            if (criteria.getMachineSizeTons() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getMachineSizeTons(), NtQuotePartInformationVersion_.machineSizeTons)
                );
            }
            if (criteria.getNumberOfCavities() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getNumberOfCavities(), NtQuotePartInformationVersion_.numberOfCavities)
                );
            }
            if (criteria.getCycleTime() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getCycleTime(), NtQuotePartInformationVersion_.cycleTime)
                );
            }
            if (criteria.getPerUnit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPerUnit(), NtQuotePartInformationVersion_.perUnit));
            }
            if (criteria.getTotalPricePerChina() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getTotalPricePerChina(), NtQuotePartInformationVersion_.totalPricePerChina)
                );
            }
            if (criteria.getTotalPriceBudget() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getTotalPriceBudget(), NtQuotePartInformationVersion_.totalPriceBudget)
                );
            }
            if (criteria.getGrainBudget() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getGrainBudget(), NtQuotePartInformationVersion_.grainBudget)
                );
            }
            if (criteria.getDogatingFixtureBudget() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getDogatingFixtureBudget(), NtQuotePartInformationVersion_.dogatingFixtureBudget)
                );
            }
            if (criteria.getGaugeBudget() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getGaugeBudget(), NtQuotePartInformationVersion_.gaugeBudget)
                );
            }
            if (criteria.getEoat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEoat(), NtQuotePartInformationVersion_.eoat));
            }
            if (criteria.getChinaTariffBudget() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getChinaTariffBudget(), NtQuotePartInformationVersion_.chinaTariffBudget)
                );
            }
            if (criteria.getTotalToolingBudget() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getTotalToolingBudget(), NtQuotePartInformationVersion_.totalToolingBudget)
                );
            }
            if (criteria.getLeadTime() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getLeadTime(), NtQuotePartInformationVersion_.leadTime)
                );
            }
            if (criteria.getToolingNotes() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getToolingNotes(), NtQuotePartInformationVersion_.toolingNotes)
                );
            }
            if (criteria.getPartDescription() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getPartDescription(), NtQuotePartInformationVersion_.partDescription)
                );
            }
            if (criteria.getJobId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJobId(), NtQuotePartInformationVersion_.jobId));
            }
            if (criteria.getMoldId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMoldId(), NtQuotePartInformationVersion_.moldId));
            }
            if (criteria.getQuoteType() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getQuoteType(), NtQuotePartInformationVersion_.quoteType)
                );
            }
            if (criteria.getComments() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getComments(), NtQuotePartInformationVersion_.comments)
                );
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getCreatedBy(), NtQuotePartInformationVersion_.createdBy)
                );
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getCreatedDate(), NtQuotePartInformationVersion_.createdDate)
                );
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getUpdatedBy(), NtQuotePartInformationVersion_.updatedBy)
                );
            }
            if (criteria.getUpdatedDate() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getUpdatedDate(), NtQuotePartInformationVersion_.updatedDate)
                );
            }
            if (criteria.getNtQuoteId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getNtQuoteId(), root ->
                        root.join(NtQuotePartInformationVersion_.ntQuote, JoinType.LEFT).get(NtQuote_.id)
                    )
                );
            }
        }
        return specification;
    }
}
