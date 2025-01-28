package com.yts.revaux.ntquote.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class NtQuotePartInformationVersionCriteriaTest {

    @Test
    void newNtQuotePartInformationVersionCriteriaHasAllFiltersNullTest() {
        var ntQuotePartInformationVersionCriteria = new NtQuotePartInformationVersionCriteria();
        assertThat(ntQuotePartInformationVersionCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void ntQuotePartInformationVersionCriteriaFluentMethodsCreatesFiltersTest() {
        var ntQuotePartInformationVersionCriteria = new NtQuotePartInformationVersionCriteria();

        setAllFilters(ntQuotePartInformationVersionCriteria);

        assertThat(ntQuotePartInformationVersionCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void ntQuotePartInformationVersionCriteriaCopyCreatesNullFilterTest() {
        var ntQuotePartInformationVersionCriteria = new NtQuotePartInformationVersionCriteria();
        var copy = ntQuotePartInformationVersionCriteria.copy();

        assertThat(ntQuotePartInformationVersionCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(ntQuotePartInformationVersionCriteria)
        );
    }

    @Test
    void ntQuotePartInformationVersionCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var ntQuotePartInformationVersionCriteria = new NtQuotePartInformationVersionCriteria();
        setAllFilters(ntQuotePartInformationVersionCriteria);

        var copy = ntQuotePartInformationVersionCriteria.copy();

        assertThat(ntQuotePartInformationVersionCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(ntQuotePartInformationVersionCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var ntQuotePartInformationVersionCriteria = new NtQuotePartInformationVersionCriteria();

        assertThat(ntQuotePartInformationVersionCriteria).hasToString("NtQuotePartInformationVersionCriteria{}");
    }

    private static void setAllFilters(NtQuotePartInformationVersionCriteria ntQuotePartInformationVersionCriteria) {
        ntQuotePartInformationVersionCriteria.id();
        ntQuotePartInformationVersionCriteria.srNo();
        ntQuotePartInformationVersionCriteria.uid();
        ntQuotePartInformationVersionCriteria.materialDescription();
        ntQuotePartInformationVersionCriteria.partNumber();
        ntQuotePartInformationVersionCriteria.cadFile();
        ntQuotePartInformationVersionCriteria.eau();
        ntQuotePartInformationVersionCriteria.partWeight();
        ntQuotePartInformationVersionCriteria.materialType();
        ntQuotePartInformationVersionCriteria.materialCost();
        ntQuotePartInformationVersionCriteria.extendedMaterialCostPer();
        ntQuotePartInformationVersionCriteria.externalMachineCostPer();
        ntQuotePartInformationVersionCriteria.purchaseComponentCost();
        ntQuotePartInformationVersionCriteria.secondaryExternalOperationCost();
        ntQuotePartInformationVersionCriteria.overhead();
        ntQuotePartInformationVersionCriteria.packLogisticCostPer();
        ntQuotePartInformationVersionCriteria.machineSizeTons();
        ntQuotePartInformationVersionCriteria.numberOfCavities();
        ntQuotePartInformationVersionCriteria.cycleTime();
        ntQuotePartInformationVersionCriteria.perUnit();
        ntQuotePartInformationVersionCriteria.totalPricePerChina();
        ntQuotePartInformationVersionCriteria.totalPriceBudget();
        ntQuotePartInformationVersionCriteria.grainBudget();
        ntQuotePartInformationVersionCriteria.dogatingFixtureBudget();
        ntQuotePartInformationVersionCriteria.gaugeBudget();
        ntQuotePartInformationVersionCriteria.eoat();
        ntQuotePartInformationVersionCriteria.chinaTariffBudget();
        ntQuotePartInformationVersionCriteria.totalToolingBudget();
        ntQuotePartInformationVersionCriteria.leadTime();
        ntQuotePartInformationVersionCriteria.toolingNotes();
        ntQuotePartInformationVersionCriteria.partDescription();
        ntQuotePartInformationVersionCriteria.jobId();
        ntQuotePartInformationVersionCriteria.moldId();
        ntQuotePartInformationVersionCriteria.quoteType();
        ntQuotePartInformationVersionCriteria.comments();
        ntQuotePartInformationVersionCriteria.createdBy();
        ntQuotePartInformationVersionCriteria.createdDate();
        ntQuotePartInformationVersionCriteria.updatedBy();
        ntQuotePartInformationVersionCriteria.updatedDate();
        ntQuotePartInformationVersionCriteria.ntQuoteId();
        ntQuotePartInformationVersionCriteria.distinct();
    }

    private static Condition<NtQuotePartInformationVersionCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
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
                condition.apply(criteria.getQuoteType()) &&
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

    private static Condition<NtQuotePartInformationVersionCriteria> copyFiltersAre(
        NtQuotePartInformationVersionCriteria copy,
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
                condition.apply(criteria.getQuoteType(), copy.getQuoteType()) &&
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
