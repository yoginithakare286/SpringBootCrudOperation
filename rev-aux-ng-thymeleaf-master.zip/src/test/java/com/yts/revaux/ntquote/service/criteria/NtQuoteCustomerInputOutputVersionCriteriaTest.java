package com.yts.revaux.ntquote.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class NtQuoteCustomerInputOutputVersionCriteriaTest {

    @Test
    void newNtQuoteCustomerInputOutputVersionCriteriaHasAllFiltersNullTest() {
        var ntQuoteCustomerInputOutputVersionCriteria = new NtQuoteCustomerInputOutputVersionCriteria();
        assertThat(ntQuoteCustomerInputOutputVersionCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void ntQuoteCustomerInputOutputVersionCriteriaFluentMethodsCreatesFiltersTest() {
        var ntQuoteCustomerInputOutputVersionCriteria = new NtQuoteCustomerInputOutputVersionCriteria();

        setAllFilters(ntQuoteCustomerInputOutputVersionCriteria);

        assertThat(ntQuoteCustomerInputOutputVersionCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void ntQuoteCustomerInputOutputVersionCriteriaCopyCreatesNullFilterTest() {
        var ntQuoteCustomerInputOutputVersionCriteria = new NtQuoteCustomerInputOutputVersionCriteria();
        var copy = ntQuoteCustomerInputOutputVersionCriteria.copy();

        assertThat(ntQuoteCustomerInputOutputVersionCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(ntQuoteCustomerInputOutputVersionCriteria)
        );
    }

    @Test
    void ntQuoteCustomerInputOutputVersionCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var ntQuoteCustomerInputOutputVersionCriteria = new NtQuoteCustomerInputOutputVersionCriteria();
        setAllFilters(ntQuoteCustomerInputOutputVersionCriteria);

        var copy = ntQuoteCustomerInputOutputVersionCriteria.copy();

        assertThat(ntQuoteCustomerInputOutputVersionCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(ntQuoteCustomerInputOutputVersionCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var ntQuoteCustomerInputOutputVersionCriteria = new NtQuoteCustomerInputOutputVersionCriteria();

        assertThat(ntQuoteCustomerInputOutputVersionCriteria).hasToString("NtQuoteCustomerInputOutputVersionCriteria{}");
    }

    private static void setAllFilters(NtQuoteCustomerInputOutputVersionCriteria ntQuoteCustomerInputOutputVersionCriteria) {
        ntQuoteCustomerInputOutputVersionCriteria.id();
        ntQuoteCustomerInputOutputVersionCriteria.srNo();
        ntQuoteCustomerInputOutputVersionCriteria.uid();
        ntQuoteCustomerInputOutputVersionCriteria.materialDescription();
        ntQuoteCustomerInputOutputVersionCriteria.partNumber();
        ntQuoteCustomerInputOutputVersionCriteria.materialId();
        ntQuoteCustomerInputOutputVersionCriteria.supplier();
        ntQuoteCustomerInputOutputVersionCriteria.estAnnualVolume();
        ntQuoteCustomerInputOutputVersionCriteria.estProductionRunYrs();
        ntQuoteCustomerInputOutputVersionCriteria.materialCostLb();
        ntQuoteCustomerInputOutputVersionCriteria.partWeightLb();
        ntQuoteCustomerInputOutputVersionCriteria.runnerWeightLb();
        ntQuoteCustomerInputOutputVersionCriteria.machineSize();
        ntQuoteCustomerInputOutputVersionCriteria.machineRate();
        ntQuoteCustomerInputOutputVersionCriteria.scrapRate();
        ntQuoteCustomerInputOutputVersionCriteria.machineEfficiency();
        ntQuoteCustomerInputOutputVersionCriteria.fte();
        ntQuoteCustomerInputOutputVersionCriteria.laborRate();
        ntQuoteCustomerInputOutputVersionCriteria.numberOfCavities();
        ntQuoteCustomerInputOutputVersionCriteria.cycleTime();
        ntQuoteCustomerInputOutputVersionCriteria.purchaseComponentCostPart();
        ntQuoteCustomerInputOutputVersionCriteria.secondaryOperationExternalProcess();
        ntQuoteCustomerInputOutputVersionCriteria.secondaryOperationLaborRate();
        ntQuoteCustomerInputOutputVersionCriteria.secondaryOperationMachineRate();
        ntQuoteCustomerInputOutputVersionCriteria.secondaryOperationCycleTime();
        ntQuoteCustomerInputOutputVersionCriteria.externalOperationRate();
        ntQuoteCustomerInputOutputVersionCriteria.preventativeMaintenanceFrequency();
        ntQuoteCustomerInputOutputVersionCriteria.preventativeMaintenanceCost();
        ntQuoteCustomerInputOutputVersionCriteria.targetProfit();
        ntQuoteCustomerInputOutputVersionCriteria.targetMaterialMarkup();
        ntQuoteCustomerInputOutputVersionCriteria.actualMaterialCost();
        ntQuoteCustomerInputOutputVersionCriteria.partPerHours();
        ntQuoteCustomerInputOutputVersionCriteria.estLotSize();
        ntQuoteCustomerInputOutputVersionCriteria.setupHours();
        ntQuoteCustomerInputOutputVersionCriteria.externalOperationCostPer();
        ntQuoteCustomerInputOutputVersionCriteria.externalMachineCostPer();
        ntQuoteCustomerInputOutputVersionCriteria.extendedLaborCostPer();
        ntQuoteCustomerInputOutputVersionCriteria.extendedMaterialCostPer();
        ntQuoteCustomerInputOutputVersionCriteria.packLogisticCostPer();
        ntQuoteCustomerInputOutputVersionCriteria.totalProductionCost();
        ntQuoteCustomerInputOutputVersionCriteria.totalMaterialCost();
        ntQuoteCustomerInputOutputVersionCriteria.totalCostSgaProfit();
        ntQuoteCustomerInputOutputVersionCriteria.sgaRate();
        ntQuoteCustomerInputOutputVersionCriteria.profit();
        ntQuoteCustomerInputOutputVersionCriteria.partPrice();
        ntQuoteCustomerInputOutputVersionCriteria.totalCost();
        ntQuoteCustomerInputOutputVersionCriteria.totalSales();
        ntQuoteCustomerInputOutputVersionCriteria.totalProfit();
        ntQuoteCustomerInputOutputVersionCriteria.costMaterial();
        ntQuoteCustomerInputOutputVersionCriteria.totalContributionMargin();
        ntQuoteCustomerInputOutputVersionCriteria.contributionMargin();
        ntQuoteCustomerInputOutputVersionCriteria.materialContributionMargin();
        ntQuoteCustomerInputOutputVersionCriteria.version();
        ntQuoteCustomerInputOutputVersionCriteria.comments();
        ntQuoteCustomerInputOutputVersionCriteria.createdBy();
        ntQuoteCustomerInputOutputVersionCriteria.createdDate();
        ntQuoteCustomerInputOutputVersionCriteria.updatedBy();
        ntQuoteCustomerInputOutputVersionCriteria.updatedDate();
        ntQuoteCustomerInputOutputVersionCriteria.ntQuoteId();
        ntQuoteCustomerInputOutputVersionCriteria.distinct();
    }

    private static Condition<NtQuoteCustomerInputOutputVersionCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getSrNo()) &&
                condition.apply(criteria.getUid()) &&
                condition.apply(criteria.getMaterialDescription()) &&
                condition.apply(criteria.getPartNumber()) &&
                condition.apply(criteria.getMaterialId()) &&
                condition.apply(criteria.getSupplier()) &&
                condition.apply(criteria.getEstAnnualVolume()) &&
                condition.apply(criteria.getEstProductionRunYrs()) &&
                condition.apply(criteria.getMaterialCostLb()) &&
                condition.apply(criteria.getPartWeightLb()) &&
                condition.apply(criteria.getRunnerWeightLb()) &&
                condition.apply(criteria.getMachineSize()) &&
                condition.apply(criteria.getMachineRate()) &&
                condition.apply(criteria.getScrapRate()) &&
                condition.apply(criteria.getMachineEfficiency()) &&
                condition.apply(criteria.getFte()) &&
                condition.apply(criteria.getLaborRate()) &&
                condition.apply(criteria.getNumberOfCavities()) &&
                condition.apply(criteria.getCycleTime()) &&
                condition.apply(criteria.getPurchaseComponentCostPart()) &&
                condition.apply(criteria.getSecondaryOperationExternalProcess()) &&
                condition.apply(criteria.getSecondaryOperationLaborRate()) &&
                condition.apply(criteria.getSecondaryOperationMachineRate()) &&
                condition.apply(criteria.getSecondaryOperationCycleTime()) &&
                condition.apply(criteria.getExternalOperationRate()) &&
                condition.apply(criteria.getPreventativeMaintenanceFrequency()) &&
                condition.apply(criteria.getPreventativeMaintenanceCost()) &&
                condition.apply(criteria.getTargetProfit()) &&
                condition.apply(criteria.getTargetMaterialMarkup()) &&
                condition.apply(criteria.getActualMaterialCost()) &&
                condition.apply(criteria.getPartPerHours()) &&
                condition.apply(criteria.getEstLotSize()) &&
                condition.apply(criteria.getSetupHours()) &&
                condition.apply(criteria.getExternalOperationCostPer()) &&
                condition.apply(criteria.getExternalMachineCostPer()) &&
                condition.apply(criteria.getExtendedLaborCostPer()) &&
                condition.apply(criteria.getExtendedMaterialCostPer()) &&
                condition.apply(criteria.getPackLogisticCostPer()) &&
                condition.apply(criteria.getTotalProductionCost()) &&
                condition.apply(criteria.getTotalMaterialCost()) &&
                condition.apply(criteria.getTotalCostSgaProfit()) &&
                condition.apply(criteria.getSgaRate()) &&
                condition.apply(criteria.getProfit()) &&
                condition.apply(criteria.getPartPrice()) &&
                condition.apply(criteria.getTotalCost()) &&
                condition.apply(criteria.getTotalSales()) &&
                condition.apply(criteria.getTotalProfit()) &&
                condition.apply(criteria.getCostMaterial()) &&
                condition.apply(criteria.getTotalContributionMargin()) &&
                condition.apply(criteria.getContributionMargin()) &&
                condition.apply(criteria.getMaterialContributionMargin()) &&
                condition.apply(criteria.getVersion()) &&
                condition.apply(criteria.getComments()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedDate()) &&
                condition.apply(criteria.getNtQuoteId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<NtQuoteCustomerInputOutputVersionCriteria> copyFiltersAre(
        NtQuoteCustomerInputOutputVersionCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getSrNo(), copy.getSrNo()) &&
                condition.apply(criteria.getUid(), copy.getUid()) &&
                condition.apply(criteria.getMaterialDescription(), copy.getMaterialDescription()) &&
                condition.apply(criteria.getPartNumber(), copy.getPartNumber()) &&
                condition.apply(criteria.getMaterialId(), copy.getMaterialId()) &&
                condition.apply(criteria.getSupplier(), copy.getSupplier()) &&
                condition.apply(criteria.getEstAnnualVolume(), copy.getEstAnnualVolume()) &&
                condition.apply(criteria.getEstProductionRunYrs(), copy.getEstProductionRunYrs()) &&
                condition.apply(criteria.getMaterialCostLb(), copy.getMaterialCostLb()) &&
                condition.apply(criteria.getPartWeightLb(), copy.getPartWeightLb()) &&
                condition.apply(criteria.getRunnerWeightLb(), copy.getRunnerWeightLb()) &&
                condition.apply(criteria.getMachineSize(), copy.getMachineSize()) &&
                condition.apply(criteria.getMachineRate(), copy.getMachineRate()) &&
                condition.apply(criteria.getScrapRate(), copy.getScrapRate()) &&
                condition.apply(criteria.getMachineEfficiency(), copy.getMachineEfficiency()) &&
                condition.apply(criteria.getFte(), copy.getFte()) &&
                condition.apply(criteria.getLaborRate(), copy.getLaborRate()) &&
                condition.apply(criteria.getNumberOfCavities(), copy.getNumberOfCavities()) &&
                condition.apply(criteria.getCycleTime(), copy.getCycleTime()) &&
                condition.apply(criteria.getPurchaseComponentCostPart(), copy.getPurchaseComponentCostPart()) &&
                condition.apply(criteria.getSecondaryOperationExternalProcess(), copy.getSecondaryOperationExternalProcess()) &&
                condition.apply(criteria.getSecondaryOperationLaborRate(), copy.getSecondaryOperationLaborRate()) &&
                condition.apply(criteria.getSecondaryOperationMachineRate(), copy.getSecondaryOperationMachineRate()) &&
                condition.apply(criteria.getSecondaryOperationCycleTime(), copy.getSecondaryOperationCycleTime()) &&
                condition.apply(criteria.getExternalOperationRate(), copy.getExternalOperationRate()) &&
                condition.apply(criteria.getPreventativeMaintenanceFrequency(), copy.getPreventativeMaintenanceFrequency()) &&
                condition.apply(criteria.getPreventativeMaintenanceCost(), copy.getPreventativeMaintenanceCost()) &&
                condition.apply(criteria.getTargetProfit(), copy.getTargetProfit()) &&
                condition.apply(criteria.getTargetMaterialMarkup(), copy.getTargetMaterialMarkup()) &&
                condition.apply(criteria.getActualMaterialCost(), copy.getActualMaterialCost()) &&
                condition.apply(criteria.getPartPerHours(), copy.getPartPerHours()) &&
                condition.apply(criteria.getEstLotSize(), copy.getEstLotSize()) &&
                condition.apply(criteria.getSetupHours(), copy.getSetupHours()) &&
                condition.apply(criteria.getExternalOperationCostPer(), copy.getExternalOperationCostPer()) &&
                condition.apply(criteria.getExternalMachineCostPer(), copy.getExternalMachineCostPer()) &&
                condition.apply(criteria.getExtendedLaborCostPer(), copy.getExtendedLaborCostPer()) &&
                condition.apply(criteria.getExtendedMaterialCostPer(), copy.getExtendedMaterialCostPer()) &&
                condition.apply(criteria.getPackLogisticCostPer(), copy.getPackLogisticCostPer()) &&
                condition.apply(criteria.getTotalProductionCost(), copy.getTotalProductionCost()) &&
                condition.apply(criteria.getTotalMaterialCost(), copy.getTotalMaterialCost()) &&
                condition.apply(criteria.getTotalCostSgaProfit(), copy.getTotalCostSgaProfit()) &&
                condition.apply(criteria.getSgaRate(), copy.getSgaRate()) &&
                condition.apply(criteria.getProfit(), copy.getProfit()) &&
                condition.apply(criteria.getPartPrice(), copy.getPartPrice()) &&
                condition.apply(criteria.getTotalCost(), copy.getTotalCost()) &&
                condition.apply(criteria.getTotalSales(), copy.getTotalSales()) &&
                condition.apply(criteria.getTotalProfit(), copy.getTotalProfit()) &&
                condition.apply(criteria.getCostMaterial(), copy.getCostMaterial()) &&
                condition.apply(criteria.getTotalContributionMargin(), copy.getTotalContributionMargin()) &&
                condition.apply(criteria.getContributionMargin(), copy.getContributionMargin()) &&
                condition.apply(criteria.getMaterialContributionMargin(), copy.getMaterialContributionMargin()) &&
                condition.apply(criteria.getVersion(), copy.getVersion()) &&
                condition.apply(criteria.getComments(), copy.getComments()) &&
                condition.apply(criteria.getCreatedBy(), copy.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate(), copy.getCreatedDate()) &&
                condition.apply(criteria.getUpdatedBy(), copy.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedDate(), copy.getUpdatedDate()) &&
                condition.apply(criteria.getNtQuoteId(), copy.getNtQuoteId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
