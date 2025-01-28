package com.yts.revaux.ntquote.service;

import com.yts.revaux.ntquote.domain.*; // for static metamodels
import com.yts.revaux.ntquote.domain.NtQuoteCustomerInputOutputVersion;
import com.yts.revaux.ntquote.repository.NtQuoteCustomerInputOutputVersionRepository;
import com.yts.revaux.ntquote.repository.search.NtQuoteCustomerInputOutputVersionSearchRepository;
import com.yts.revaux.ntquote.service.criteria.NtQuoteCustomerInputOutputVersionCriteria;
import com.yts.revaux.ntquote.service.dto.NtQuoteCustomerInputOutputVersionDTO;
import com.yts.revaux.ntquote.service.mapper.NtQuoteCustomerInputOutputVersionMapper;
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
 * Service for executing complex queries for {@link NtQuoteCustomerInputOutputVersion} entities in the database.
 * The main input is a {@link NtQuoteCustomerInputOutputVersionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link NtQuoteCustomerInputOutputVersionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NtQuoteCustomerInputOutputVersionQueryService extends QueryService<NtQuoteCustomerInputOutputVersion> {

    private static final Logger LOG = LoggerFactory.getLogger(NtQuoteCustomerInputOutputVersionQueryService.class);

    private final NtQuoteCustomerInputOutputVersionRepository ntQuoteCustomerInputOutputVersionRepository;

    private final NtQuoteCustomerInputOutputVersionMapper ntQuoteCustomerInputOutputVersionMapper;

    private final NtQuoteCustomerInputOutputVersionSearchRepository ntQuoteCustomerInputOutputVersionSearchRepository;

    public NtQuoteCustomerInputOutputVersionQueryService(
        NtQuoteCustomerInputOutputVersionRepository ntQuoteCustomerInputOutputVersionRepository,
        NtQuoteCustomerInputOutputVersionMapper ntQuoteCustomerInputOutputVersionMapper,
        NtQuoteCustomerInputOutputVersionSearchRepository ntQuoteCustomerInputOutputVersionSearchRepository
    ) {
        this.ntQuoteCustomerInputOutputVersionRepository = ntQuoteCustomerInputOutputVersionRepository;
        this.ntQuoteCustomerInputOutputVersionMapper = ntQuoteCustomerInputOutputVersionMapper;
        this.ntQuoteCustomerInputOutputVersionSearchRepository = ntQuoteCustomerInputOutputVersionSearchRepository;
    }

    /**
     * Return a {@link Page} of {@link NtQuoteCustomerInputOutputVersionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NtQuoteCustomerInputOutputVersionDTO> findByCriteria(NtQuoteCustomerInputOutputVersionCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NtQuoteCustomerInputOutputVersion> specification = createSpecification(criteria);
        return ntQuoteCustomerInputOutputVersionRepository.findAll(specification, page).map(ntQuoteCustomerInputOutputVersionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NtQuoteCustomerInputOutputVersionCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<NtQuoteCustomerInputOutputVersion> specification = createSpecification(criteria);
        return ntQuoteCustomerInputOutputVersionRepository.count(specification);
    }

    /**
     * Function to convert {@link NtQuoteCustomerInputOutputVersionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NtQuoteCustomerInputOutputVersion> createSpecification(NtQuoteCustomerInputOutputVersionCriteria criteria) {
        Specification<NtQuoteCustomerInputOutputVersion> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NtQuoteCustomerInputOutputVersion_.id));
            }
            if (criteria.getSrNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSrNo(), NtQuoteCustomerInputOutputVersion_.srNo));
            }
            if (criteria.getUid() != null) {
                specification = specification.and(buildSpecification(criteria.getUid(), NtQuoteCustomerInputOutputVersion_.uid));
            }
            if (criteria.getMaterialDescription() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getMaterialDescription(), NtQuoteCustomerInputOutputVersion_.materialDescription)
                );
            }
            if (criteria.getPartNumber() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getPartNumber(), NtQuoteCustomerInputOutputVersion_.partNumber)
                );
            }
            if (criteria.getMaterialId() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getMaterialId(), NtQuoteCustomerInputOutputVersion_.materialId)
                );
            }
            if (criteria.getSupplier() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getSupplier(), NtQuoteCustomerInputOutputVersion_.supplier)
                );
            }
            if (criteria.getEstAnnualVolume() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getEstAnnualVolume(), NtQuoteCustomerInputOutputVersion_.estAnnualVolume)
                );
            }
            if (criteria.getEstProductionRunYrs() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getEstProductionRunYrs(), NtQuoteCustomerInputOutputVersion_.estProductionRunYrs)
                );
            }
            if (criteria.getMaterialCostLb() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getMaterialCostLb(), NtQuoteCustomerInputOutputVersion_.materialCostLb)
                );
            }
            if (criteria.getPartWeightLb() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getPartWeightLb(), NtQuoteCustomerInputOutputVersion_.partWeightLb)
                );
            }
            if (criteria.getRunnerWeightLb() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getRunnerWeightLb(), NtQuoteCustomerInputOutputVersion_.runnerWeightLb)
                );
            }
            if (criteria.getMachineSize() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getMachineSize(), NtQuoteCustomerInputOutputVersion_.machineSize)
                );
            }
            if (criteria.getMachineRate() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getMachineRate(), NtQuoteCustomerInputOutputVersion_.machineRate)
                );
            }
            if (criteria.getScrapRate() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getScrapRate(), NtQuoteCustomerInputOutputVersion_.scrapRate)
                );
            }
            if (criteria.getMachineEfficiency() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getMachineEfficiency(), NtQuoteCustomerInputOutputVersion_.machineEfficiency)
                );
            }
            if (criteria.getFte() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFte(), NtQuoteCustomerInputOutputVersion_.fte));
            }
            if (criteria.getLaborRate() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getLaborRate(), NtQuoteCustomerInputOutputVersion_.laborRate)
                );
            }
            if (criteria.getNumberOfCavities() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getNumberOfCavities(), NtQuoteCustomerInputOutputVersion_.numberOfCavities)
                );
            }
            if (criteria.getCycleTime() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getCycleTime(), NtQuoteCustomerInputOutputVersion_.cycleTime)
                );
            }
            if (criteria.getPurchaseComponentCostPart() != null) {
                specification = specification.and(
                    buildRangeSpecification(
                        criteria.getPurchaseComponentCostPart(),
                        NtQuoteCustomerInputOutputVersion_.purchaseComponentCostPart
                    )
                );
            }
            if (criteria.getSecondaryOperationExternalProcess() != null) {
                specification = specification.and(
                    buildStringSpecification(
                        criteria.getSecondaryOperationExternalProcess(),
                        NtQuoteCustomerInputOutputVersion_.secondaryOperationExternalProcess
                    )
                );
            }
            if (criteria.getSecondaryOperationLaborRate() != null) {
                specification = specification.and(
                    buildRangeSpecification(
                        criteria.getSecondaryOperationLaborRate(),
                        NtQuoteCustomerInputOutputVersion_.secondaryOperationLaborRate
                    )
                );
            }
            if (criteria.getSecondaryOperationMachineRate() != null) {
                specification = specification.and(
                    buildRangeSpecification(
                        criteria.getSecondaryOperationMachineRate(),
                        NtQuoteCustomerInputOutputVersion_.secondaryOperationMachineRate
                    )
                );
            }
            if (criteria.getSecondaryOperationCycleTime() != null) {
                specification = specification.and(
                    buildRangeSpecification(
                        criteria.getSecondaryOperationCycleTime(),
                        NtQuoteCustomerInputOutputVersion_.secondaryOperationCycleTime
                    )
                );
            }
            if (criteria.getExternalOperationRate() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getExternalOperationRate(), NtQuoteCustomerInputOutputVersion_.externalOperationRate)
                );
            }
            if (criteria.getPreventativeMaintenanceFrequency() != null) {
                specification = specification.and(
                    buildRangeSpecification(
                        criteria.getPreventativeMaintenanceFrequency(),
                        NtQuoteCustomerInputOutputVersion_.preventativeMaintenanceFrequency
                    )
                );
            }
            if (criteria.getPreventativeMaintenanceCost() != null) {
                specification = specification.and(
                    buildRangeSpecification(
                        criteria.getPreventativeMaintenanceCost(),
                        NtQuoteCustomerInputOutputVersion_.preventativeMaintenanceCost
                    )
                );
            }
            if (criteria.getTargetProfit() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getTargetProfit(), NtQuoteCustomerInputOutputVersion_.targetProfit)
                );
            }
            if (criteria.getTargetMaterialMarkup() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getTargetMaterialMarkup(), NtQuoteCustomerInputOutputVersion_.targetMaterialMarkup)
                );
            }
            if (criteria.getActualMaterialCost() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getActualMaterialCost(), NtQuoteCustomerInputOutputVersion_.actualMaterialCost)
                );
            }
            if (criteria.getPartPerHours() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getPartPerHours(), NtQuoteCustomerInputOutputVersion_.partPerHours)
                );
            }
            if (criteria.getEstLotSize() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getEstLotSize(), NtQuoteCustomerInputOutputVersion_.estLotSize)
                );
            }
            if (criteria.getSetupHours() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getSetupHours(), NtQuoteCustomerInputOutputVersion_.setupHours)
                );
            }
            if (criteria.getExternalOperationCostPer() != null) {
                specification = specification.and(
                    buildRangeSpecification(
                        criteria.getExternalOperationCostPer(),
                        NtQuoteCustomerInputOutputVersion_.externalOperationCostPer
                    )
                );
            }
            if (criteria.getExternalMachineCostPer() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getExternalMachineCostPer(), NtQuoteCustomerInputOutputVersion_.externalMachineCostPer)
                );
            }
            if (criteria.getExtendedLaborCostPer() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getExtendedLaborCostPer(), NtQuoteCustomerInputOutputVersion_.extendedLaborCostPer)
                );
            }
            if (criteria.getExtendedMaterialCostPer() != null) {
                specification = specification.and(
                    buildRangeSpecification(
                        criteria.getExtendedMaterialCostPer(),
                        NtQuoteCustomerInputOutputVersion_.extendedMaterialCostPer
                    )
                );
            }
            if (criteria.getPackLogisticCostPer() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getPackLogisticCostPer(), NtQuoteCustomerInputOutputVersion_.packLogisticCostPer)
                );
            }
            if (criteria.getTotalProductionCost() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getTotalProductionCost(), NtQuoteCustomerInputOutputVersion_.totalProductionCost)
                );
            }
            if (criteria.getTotalMaterialCost() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getTotalMaterialCost(), NtQuoteCustomerInputOutputVersion_.totalMaterialCost)
                );
            }
            if (criteria.getTotalCostSgaProfit() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getTotalCostSgaProfit(), NtQuoteCustomerInputOutputVersion_.totalCostSgaProfit)
                );
            }
            if (criteria.getSgaRate() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getSgaRate(), NtQuoteCustomerInputOutputVersion_.sgaRate)
                );
            }
            if (criteria.getProfit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProfit(), NtQuoteCustomerInputOutputVersion_.profit));
            }
            if (criteria.getPartPrice() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getPartPrice(), NtQuoteCustomerInputOutputVersion_.partPrice)
                );
            }
            if (criteria.getTotalCost() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getTotalCost(), NtQuoteCustomerInputOutputVersion_.totalCost)
                );
            }
            if (criteria.getTotalSales() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getTotalSales(), NtQuoteCustomerInputOutputVersion_.totalSales)
                );
            }
            if (criteria.getTotalProfit() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getTotalProfit(), NtQuoteCustomerInputOutputVersion_.totalProfit)
                );
            }
            if (criteria.getCostMaterial() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getCostMaterial(), NtQuoteCustomerInputOutputVersion_.costMaterial)
                );
            }
            if (criteria.getTotalContributionMargin() != null) {
                specification = specification.and(
                    buildRangeSpecification(
                        criteria.getTotalContributionMargin(),
                        NtQuoteCustomerInputOutputVersion_.totalContributionMargin
                    )
                );
            }
            if (criteria.getContributionMargin() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getContributionMargin(), NtQuoteCustomerInputOutputVersion_.contributionMargin)
                );
            }
            if (criteria.getMaterialContributionMargin() != null) {
                specification = specification.and(
                    buildRangeSpecification(
                        criteria.getMaterialContributionMargin(),
                        NtQuoteCustomerInputOutputVersion_.materialContributionMargin
                    )
                );
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getVersion(), NtQuoteCustomerInputOutputVersion_.version)
                );
            }
            if (criteria.getComments() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getComments(), NtQuoteCustomerInputOutputVersion_.comments)
                );
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getCreatedBy(), NtQuoteCustomerInputOutputVersion_.createdBy)
                );
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getCreatedDate(), NtQuoteCustomerInputOutputVersion_.createdDate)
                );
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getUpdatedBy(), NtQuoteCustomerInputOutputVersion_.updatedBy)
                );
            }
            if (criteria.getUpdatedDate() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getUpdatedDate(), NtQuoteCustomerInputOutputVersion_.updatedDate)
                );
            }
            if (criteria.getNtQuoteId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getNtQuoteId(), root ->
                        root.join(NtQuoteCustomerInputOutputVersion_.ntQuote, JoinType.LEFT).get(NtQuote_.id)
                    )
                );
            }
        }
        return specification;
    }
}
