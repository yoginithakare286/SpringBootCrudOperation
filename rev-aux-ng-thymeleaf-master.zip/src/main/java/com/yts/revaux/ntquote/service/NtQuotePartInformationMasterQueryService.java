package com.yts.revaux.ntquote.service;

import com.yts.revaux.ntquote.domain.*; // for static metamodels
import com.yts.revaux.ntquote.domain.NtQuotePartInformationMaster;
import com.yts.revaux.ntquote.repository.NtQuotePartInformationMasterRepository;
import com.yts.revaux.ntquote.repository.search.NtQuotePartInformationMasterSearchRepository;
import com.yts.revaux.ntquote.service.criteria.NtQuotePartInformationMasterCriteria;
import com.yts.revaux.ntquote.service.dto.NtQuotePartInformationMasterDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuotePartInformationMasterMapper;
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
 * Service for executing complex queries for {@link NtQuotePartInformationMaster} entities in the database.
 * The main input is a {@link NtQuotePartInformationMasterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link NtQuotePartInformationMasterDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NtQuotePartInformationMasterQueryService extends QueryService<NtQuotePartInformationMaster> {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuotePartInformationMasterQueryService.class);

    private final NtQuotePartInformationMasterRepository ntQuotePartInformationMasterRepository;

    private final NtQuotePartInformationMasterMapper ntQuotePartInformationMasterMapper;

    private final NtQuotePartInformationMasterSearchRepository ntQuotePartInformationMasterSearchRepository;

    public NtQuotePartInformationMasterQueryService(
        NtQuotePartInformationMasterRepository ntQuotePartInformationMasterRepository,
        NtQuotePartInformationMasterMapper ntQuotePartInformationMasterMapper,
        NtQuotePartInformationMasterSearchRepository ntQuotePartInformationMasterSearchRepository
    ) {
        this.ntQuotePartInformationMasterRepository = ntQuotePartInformationMasterRepository;
        this.ntQuotePartInformationMasterMapper = ntQuotePartInformationMasterMapper;
        this.ntQuotePartInformationMasterSearchRepository = ntQuotePartInformationMasterSearchRepository;
    }

    /**
     * Return a {@link Page} of {@link NtQuotePartInformationMasterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NtQuotePartInformationMasterDTO> findByCriteria(NtQuotePartInformationMasterCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NtQuotePartInformationMaster> specification = createSpecification(criteria);
        return ntQuotePartInformationMasterRepository.findAll(specification, page).map(ntQuotePartInformationMasterMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NtQuotePartInformationMasterCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<NtQuotePartInformationMaster> specification = createSpecification(criteria);
        return ntQuotePartInformationMasterRepository.count(specification);
    }

    /**
     * Function to convert {@link NtQuotePartInformationMasterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NtQuotePartInformationMaster> createSpecification(NtQuotePartInformationMasterCriteria criteria) {
        Specification<NtQuotePartInformationMaster> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NtQuotePartInformationMaster_.id));
            }
            if (criteria.getSrNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSrNo(), NtQuotePartInformationMaster_.srNo));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), NtQuotePartInformationMaster_.uid));
            }
            if (criteria.getMaterialDescription() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getMaterialDescription(), NtQuotePartInformationMaster_.materialDescription)
                );
            }
            if (criteria.getPartNumber() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getPartNumber(), NtQuotePartInformationMaster_.partNumber)
                );
            }
            if (criteria.getCadFile() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCadFile(), NtQuotePartInformationMaster_.cadFile));
            }
            if (criteria.getEau() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEau(), NtQuotePartInformationMaster_.eau));
            }
            if (criteria.getPartWeight() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getPartWeight(), NtQuotePartInformationMaster_.partWeight)
                );
            }
            if (criteria.getMaterialType() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getMaterialType(), NtQuotePartInformationMaster_.materialType)
                );
            }
            if (criteria.getMaterialCost() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getMaterialCost(), NtQuotePartInformationMaster_.materialCost)
                );
            }
            if (criteria.getExtendedMaterialCostPer() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getExtendedMaterialCostPer(), NtQuotePartInformationMaster_.extendedMaterialCostPer)
                );
            }
            if (criteria.getExternalMachineCostPer() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getExternalMachineCostPer(), NtQuotePartInformationMaster_.externalMachineCostPer)
                );
            }
            if (criteria.getPurchaseComponentCost() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getPurchaseComponentCost(), NtQuotePartInformationMaster_.purchaseComponentCost)
                );
            }
            if (criteria.getSecondaryExternalOperationCost() != null) {
                specification = specification.and(
                    buildRangeSpecification(
                        criteria.getSecondaryExternalOperationCost(),
                        NtQuotePartInformationMaster_.secondaryExternalOperationCost
                    )
                );
            }
            if (criteria.getOverhead() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOverhead(), NtQuotePartInformationMaster_.overhead));
            }
            if (criteria.getPackLogisticCostPer() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getPackLogisticCostPer(), NtQuotePartInformationMaster_.packLogisticCostPer)
                );
            }
            if (criteria.getMachineSizeTons() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getMachineSizeTons(), NtQuotePartInformationMaster_.machineSizeTons)
                );
            }
            if (criteria.getNumberOfCavities() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getNumberOfCavities(), NtQuotePartInformationMaster_.numberOfCavities)
                );
            }
            if (criteria.getCycleTime() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getCycleTime(), NtQuotePartInformationMaster_.cycleTime)
                );
            }
            if (criteria.getPerUnit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPerUnit(), NtQuotePartInformationMaster_.perUnit));
            }
            if (criteria.getTotalPricePerChina() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getTotalPricePerChina(), NtQuotePartInformationMaster_.totalPricePerChina)
                );
            }
            if (criteria.getTotalPriceBudget() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getTotalPriceBudget(), NtQuotePartInformationMaster_.totalPriceBudget)
                );
            }
            if (criteria.getGrainBudget() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getGrainBudget(), NtQuotePartInformationMaster_.grainBudget)
                );
            }
            if (criteria.getDogatingFixtureBudget() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getDogatingFixtureBudget(), NtQuotePartInformationMaster_.dogatingFixtureBudget)
                );
            }
            if (criteria.getGaugeBudget() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getGaugeBudget(), NtQuotePartInformationMaster_.gaugeBudget)
                );
            }
            if (criteria.getEoat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEoat(), NtQuotePartInformationMaster_.eoat));
            }
            if (criteria.getChinaTariffBudget() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getChinaTariffBudget(), NtQuotePartInformationMaster_.chinaTariffBudget)
                );
            }
            if (criteria.getTotalToolingBudget() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getTotalToolingBudget(), NtQuotePartInformationMaster_.totalToolingBudget)
                );
            }
            if (criteria.getLeadTime() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLeadTime(), NtQuotePartInformationMaster_.leadTime));
            }
            if (criteria.getToolingNotes() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getToolingNotes(), NtQuotePartInformationMaster_.toolingNotes)
                );
            }
            if (criteria.getPartDescription() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getPartDescription(), NtQuotePartInformationMaster_.partDescription)
                );
            }
            if (criteria.getJobId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJobId(), NtQuotePartInformationMaster_.jobId));
            }
            if (criteria.getMoldId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMoldId(), NtQuotePartInformationMaster_.moldId));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getCreatedBy(), NtQuotePartInformationMaster_.createdBy)
                );
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getCreatedDate(), NtQuotePartInformationMaster_.createdDate)
                );
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getUpdatedBy(), NtQuotePartInformationMaster_.updatedBy)
                );
            }
            if (criteria.getUpdatedDate() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getUpdatedDate(), NtQuotePartInformationMaster_.updatedDate)
                );
            }
            if (criteria.getNtQuoteId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getNtQuoteId(), root ->
                        root.join(NtQuotePartInformationMaster_.ntQuote, JoinType.LEFT).get(NtQuote_.id)
                    )
                );
            }
        }
        return specification;
    }
}
