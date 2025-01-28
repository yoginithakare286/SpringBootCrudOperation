package com.yts.revaux.ntquote.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class NtQuoteComponentDetailCriteriaTest {

    @Test
    void newNtQuoteComponentDetailCriteriaHasAllFiltersNullTest() {
        var ntQuoteComponentDetailCriteria = new NtQuoteComponentDetailCriteria();
        assertThat(ntQuoteComponentDetailCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void ntQuoteComponentDetailCriteriaFluentMethodsCreatesFiltersTest() {
        var ntQuoteComponentDetailCriteria = new NtQuoteComponentDetailCriteria();

        setAllFilters(ntQuoteComponentDetailCriteria);

        assertThat(ntQuoteComponentDetailCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void ntQuoteComponentDetailCriteriaCopyCreatesNullFilterTest() {
        var ntQuoteComponentDetailCriteria = new NtQuoteComponentDetailCriteria();
        var copy = ntQuoteComponentDetailCriteria.copy();

        assertThat(ntQuoteComponentDetailCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(ntQuoteComponentDetailCriteria)
        );
    }

    @Test
    void ntQuoteComponentDetailCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var ntQuoteComponentDetailCriteria = new NtQuoteComponentDetailCriteria();
        setAllFilters(ntQuoteComponentDetailCriteria);

        var copy = ntQuoteComponentDetailCriteria.copy();

        assertThat(ntQuoteComponentDetailCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(ntQuoteComponentDetailCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var ntQuoteComponentDetailCriteria = new NtQuoteComponentDetailCriteria();

        assertThat(ntQuoteComponentDetailCriteria).hasToString("NtQuoteComponentDetailCriteria{}");
    }

    private static void setAllFilters(NtQuoteComponentDetailCriteria ntQuoteComponentDetailCriteria) {
        ntQuoteComponentDetailCriteria.id();
        ntQuoteComponentDetailCriteria.srNo();
        ntQuoteComponentDetailCriteria.uid();
        ntQuoteComponentDetailCriteria.materialDescription();
        ntQuoteComponentDetailCriteria.partNumber();
        ntQuoteComponentDetailCriteria.eau();
        ntQuoteComponentDetailCriteria.manufacturingLocation();
        ntQuoteComponentDetailCriteria.fobLocation();
        ntQuoteComponentDetailCriteria.packingRequirements();
        ntQuoteComponentDetailCriteria.machineSize();
        ntQuoteComponentDetailCriteria.cycleTime();
        ntQuoteComponentDetailCriteria.partWeight();
        ntQuoteComponentDetailCriteria.runnerWeight();
        ntQuoteComponentDetailCriteria.cavities();
        ntQuoteComponentDetailCriteria.comments();
        ntQuoteComponentDetailCriteria.riskLevel();
        ntQuoteComponentDetailCriteria.createdBy();
        ntQuoteComponentDetailCriteria.createdDate();
        ntQuoteComponentDetailCriteria.updatedBy();
        ntQuoteComponentDetailCriteria.updatedDate();
        ntQuoteComponentDetailCriteria.ntQuoteId();
        ntQuoteComponentDetailCriteria.materialPriceId();
        ntQuoteComponentDetailCriteria.distinct();
    }

    private static Condition<NtQuoteComponentDetailCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getSrNo()) &&
                condition.apply(criteria.getUid()) &&
                condition.apply(criteria.getMaterialDescription()) &&
                condition.apply(criteria.getPartNumber()) &&
                condition.apply(criteria.getEau()) &&
                condition.apply(criteria.getManufacturingLocation()) &&
                condition.apply(criteria.getFobLocation()) &&
                condition.apply(criteria.getPackingRequirements()) &&
                condition.apply(criteria.getMachineSize()) &&
                condition.apply(criteria.getCycleTime()) &&
                condition.apply(criteria.getPartWeight()) &&
                condition.apply(criteria.getRunnerWeight()) &&
                condition.apply(criteria.getCavities()) &&
                condition.apply(criteria.getComments()) &&
                condition.apply(criteria.getRiskLevel()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedDate()) &&
                condition.apply(criteria.getNtQuoteId()) &&
                condition.apply(criteria.getMaterialPriceId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<NtQuoteComponentDetailCriteria> copyFiltersAre(
        NtQuoteComponentDetailCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getSrNo(), copy.getSrNo()) &&
                condition.apply(criteria.getUid(), copy.getUid()) &&
                condition.apply(criteria.getMaterialDescription(), copy.getMaterialDescription()) &&
                condition.apply(criteria.getPartNumber(), copy.getPartNumber()) &&
                condition.apply(criteria.getEau(), copy.getEau()) &&
                condition.apply(criteria.getManufacturingLocation(), copy.getManufacturingLocation()) &&
                condition.apply(criteria.getFobLocation(), copy.getFobLocation()) &&
                condition.apply(criteria.getPackingRequirements(), copy.getPackingRequirements()) &&
                condition.apply(criteria.getMachineSize(), copy.getMachineSize()) &&
                condition.apply(criteria.getCycleTime(), copy.getCycleTime()) &&
                condition.apply(criteria.getPartWeight(), copy.getPartWeight()) &&
                condition.apply(criteria.getRunnerWeight(), copy.getRunnerWeight()) &&
                condition.apply(criteria.getCavities(), copy.getCavities()) &&
                condition.apply(criteria.getComments(), copy.getComments()) &&
                condition.apply(criteria.getRiskLevel(), copy.getRiskLevel()) &&
                condition.apply(criteria.getCreatedBy(), copy.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate(), copy.getCreatedDate()) &&
                condition.apply(criteria.getUpdatedBy(), copy.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedDate(), copy.getUpdatedDate()) &&
                condition.apply(criteria.getNtQuoteId(), copy.getNtQuoteId()) &&
                condition.apply(criteria.getMaterialPriceId(), copy.getMaterialPriceId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
