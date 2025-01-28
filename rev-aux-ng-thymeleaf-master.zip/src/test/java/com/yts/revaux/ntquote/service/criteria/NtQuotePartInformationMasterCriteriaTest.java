package com.yts.revaux.ntquote.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class NtQuotePartInformationMasterCriteriaTest {

    @Test
    void newNtQuotePartInformationMasterCriteriaHasAllFiltersNullTest() {
        var ntQuotePartInformationMasterCriteria = new NtQuotePartInformationMasterCriteria();
        assertThat(ntQuotePartInformationMasterCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void ntQuotePartInformationMasterCriteriaFluentMethodsCreatesFiltersTest() {
        var ntQuotePartInformationMasterCriteria = new NtQuotePartInformationMasterCriteria();

        setAllFilters(ntQuotePartInformationMasterCriteria);

        assertThat(ntQuotePartInformationMasterCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void ntQuotePartInformationMasterCriteriaCopyCreatesNullFilterTest() {
        var ntQuotePartInformationMasterCriteria = new NtQuotePartInformationMasterCriteria();
        var copy = ntQuotePartInformationMasterCriteria.copy();

        assertThat(ntQuotePartInformationMasterCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(ntQuotePartInformationMasterCriteria)
        );
    }

    @Test
    void ntQuotePartInformationMasterCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var ntQuotePartInformationMasterCriteria = new NtQuotePartInformationMasterCriteria();
        setAllFilters(ntQuotePartInformationMasterCriteria);

        var copy = ntQuotePartInformationMasterCriteria.copy();

        assertThat(ntQuotePartInformationMasterCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(ntQuotePartInformationMasterCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var ntQuotePartInformationMasterCriteria = new NtQuotePartInformationMasterCriteria();

        assertThat(ntQuotePartInformationMasterCriteria).hasToString("NtQuotePartInformationMasterCriteria{}");
    }

    private static void setAllFilters(NtQuotePartInformationMasterCriteria ntQuotePartInformationMasterCriteria) {
        ntQuotePartInformationMasterCriteria.id();
        ntQuotePartInformationMasterCriteria.srNo();
        ntQuotePartInformationMasterCriteria.uid();
        ntQuotePartInformationMasterCriteria.materialDescription();
        ntQuotePartInformationMasterCriteria.partNumber();
        ntQuotePartInformationMasterCriteria.cadFile();
        ntQuotePartInformationMasterCriteria.eau();
        ntQuotePartInformationMasterCriteria.partWeight();
        ntQuotePartInformationMasterCriteria.materialType();
        ntQuotePartInformationMasterCriteria.materialCost();
        ntQuotePartInformationMasterCriteria.extendedMaterialCostPer();
        ntQuotePartInformationMasterCriteria.externalMachineCostPer();
        ntQuotePartInformationMasterCriteria.purchaseComponentCost();
        ntQuotePartInformationMasterCriteria.secondaryExternalOperationCost();
        ntQuotePartInformationMasterCriteria.overhead();
        ntQuotePartInformationMasterCriteria.packLogisticCostPer();
        ntQuotePartInformationMasterCriteria.machineSizeTons();
        ntQuotePartInformationMasterCriteria.numberOfCavities();
        ntQuotePartInformationMasterCriteria.cycleTime();
        ntQuotePartInformationMasterCriteria.perUnit();
        ntQuotePartInformationMasterCriteria.totalPricePerChina();
        ntQuotePartInformationMasterCriteria.totalPriceBudget();
        ntQuotePartInformationMasterCriteria.grainBudget();
        ntQuotePartInformationMasterCriteria.dogatingFixtureBudget();
        ntQuotePartInformationMasterCriteria.gaugeBudget();
        ntQuotePartInformationMasterCriteria.eoat();
        ntQuotePartInformationMasterCriteria.chinaTariffBudget();
        ntQuotePartInformationMasterCriteria.totalToolingBudget();
        ntQuotePartInformationMasterCriteria.leadTime();
        ntQuotePartInformationMasterCriteria.toolingNotes();
        ntQuotePartInformationMasterCriteria.partDescription();
        ntQuotePartInformationMasterCriteria.jobId();
        ntQuotePartInformationMasterCriteria.moldId();
        ntQuotePartInformationMasterCriteria.createdBy();
        ntQuotePartInformationMasterCriteria.createdDate();
        ntQuotePartInformationMasterCriteria.updatedBy();
        ntQuotePartInformationMasterCriteria.updatedDate();
        ntQuotePartInformationMasterCriteria.ntQuoteId();
        ntQuotePartInformationMasterCriteria.distinct();
    }

    private static Condition<NtQuotePartInformationMasterCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getSrNo()) &&
                condition.apply(criteria.getUid()) &&
                condition.apply(criteria.getMaterialDescription()) &&
                condition.apply(criteria.getPartNumber()) &&
                condition.apply(criteria.getCadFile()) &&
                condition.apply(criteria.getEau()) &&
                condition.apply(criteria.getPartWeight()) &&
                condition.apply(criteria.getMaterialType()) &&
                condition.apply(criteria.getMaterialCost()) &&
                condition.apply(criteria.getExtendedMaterialCostPer()) &&
                condition.apply(criteria.getExternalMachineCostPer()) &&
                condition.apply(criteria.getPurchaseComponentCost()) &&
                condition.apply(criteria.getSecondaryExternalOperationCost()) &&
                condition.apply(criteria.getOverhead()) &&
                condition.apply(criteria.getPackLogisticCostPer()) &&
                condition.apply(criteria.getMachineSizeTons()) &&
                condition.apply(criteria.getNumberOfCavities()) &&
                condition.apply(criteria.getCycleTime()) &&
                condition.apply(criteria.getPerUnit()) &&
                condition.apply(criteria.getTotalPricePerChina()) &&
                condition.apply(criteria.getTotalPriceBudget()) &&
                condition.apply(criteria.getGrainBudget()) &&
                condition.apply(criteria.getDogatingFixtureBudget()) &&
                condition.apply(criteria.getGaugeBudget()) &&
                condition.apply(criteria.getEoat()) &&
                condition.apply(criteria.getChinaTariffBudget()) &&
                condition.apply(criteria.getTotalToolingBudget()) &&
                condition.apply(criteria.getLeadTime()) &&
                condition.apply(criteria.getToolingNotes()) &&
                condition.apply(criteria.getPartDescription()) &&
                condition.apply(criteria.getJobId()) &&
                condition.apply(criteria.getMoldId()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedDate()) &&
                condition.apply(criteria.getNtQuoteId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<NtQuotePartInformationMasterCriteria> copyFiltersAre(
        NtQuotePartInformationMasterCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getSrNo(), copy.getSrNo()) &&
                condition.apply(criteria.getUid(), copy.getUid()) &&
                condition.apply(criteria.getMaterialDescription(), copy.getMaterialDescription()) &&
                condition.apply(criteria.getPartNumber(), copy.getPartNumber()) &&
                condition.apply(criteria.getCadFile(), copy.getCadFile()) &&
                condition.apply(criteria.getEau(), copy.getEau()) &&
                condition.apply(criteria.getPartWeight(), copy.getPartWeight()) &&
                condition.apply(criteria.getMaterialType(), copy.getMaterialType()) &&
                condition.apply(criteria.getMaterialCost(), copy.getMaterialCost()) &&
                condition.apply(criteria.getExtendedMaterialCostPer(), copy.getExtendedMaterialCostPer()) &&
                condition.apply(criteria.getExternalMachineCostPer(), copy.getExternalMachineCostPer()) &&
                condition.apply(criteria.getPurchaseComponentCost(), copy.getPurchaseComponentCost()) &&
                condition.apply(criteria.getSecondaryExternalOperationCost(), copy.getSecondaryExternalOperationCost()) &&
                condition.apply(criteria.getOverhead(), copy.getOverhead()) &&
                condition.apply(criteria.getPackLogisticCostPer(), copy.getPackLogisticCostPer()) &&
                condition.apply(criteria.getMachineSizeTons(), copy.getMachineSizeTons()) &&
                condition.apply(criteria.getNumberOfCavities(), copy.getNumberOfCavities()) &&
                condition.apply(criteria.getCycleTime(), copy.getCycleTime()) &&
                condition.apply(criteria.getPerUnit(), copy.getPerUnit()) &&
                condition.apply(criteria.getTotalPricePerChina(), copy.getTotalPricePerChina()) &&
                condition.apply(criteria.getTotalPriceBudget(), copy.getTotalPriceBudget()) &&
                condition.apply(criteria.getGrainBudget(), copy.getGrainBudget()) &&
                condition.apply(criteria.getDogatingFixtureBudget(), copy.getDogatingFixtureBudget()) &&
                condition.apply(criteria.getGaugeBudget(), copy.getGaugeBudget()) &&
                condition.apply(criteria.getEoat(), copy.getEoat()) &&
                condition.apply(criteria.getChinaTariffBudget(), copy.getChinaTariffBudget()) &&
                condition.apply(criteria.getTotalToolingBudget(), copy.getTotalToolingBudget()) &&
                condition.apply(criteria.getLeadTime(), copy.getLeadTime()) &&
                condition.apply(criteria.getToolingNotes(), copy.getToolingNotes()) &&
                condition.apply(criteria.getPartDescription(), copy.getPartDescription()) &&
                condition.apply(criteria.getJobId(), copy.getJobId()) &&
                condition.apply(criteria.getMoldId(), copy.getMoldId()) &&
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
