package com.yts.revaux.ntquote.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class NtQuoteProjectApprovalCriteriaTest {

    @Test
    void newNtQuoteProjectApprovalCriteriaHasAllFiltersNullTest() {
        var ntQuoteProjectApprovalCriteria = new NtQuoteProjectApprovalCriteria();
        assertThat(ntQuoteProjectApprovalCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void ntQuoteProjectApprovalCriteriaFluentMethodsCreatesFiltersTest() {
        var ntQuoteProjectApprovalCriteria = new NtQuoteProjectApprovalCriteria();

        setAllFilters(ntQuoteProjectApprovalCriteria);

        assertThat(ntQuoteProjectApprovalCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void ntQuoteProjectApprovalCriteriaCopyCreatesNullFilterTest() {
        var ntQuoteProjectApprovalCriteria = new NtQuoteProjectApprovalCriteria();
        var copy = ntQuoteProjectApprovalCriteria.copy();

        assertThat(ntQuoteProjectApprovalCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(ntQuoteProjectApprovalCriteria)
        );
    }

    @Test
    void ntQuoteProjectApprovalCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var ntQuoteProjectApprovalCriteria = new NtQuoteProjectApprovalCriteria();
        setAllFilters(ntQuoteProjectApprovalCriteria);

        var copy = ntQuoteProjectApprovalCriteria.copy();

        assertThat(ntQuoteProjectApprovalCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(ntQuoteProjectApprovalCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var ntQuoteProjectApprovalCriteria = new NtQuoteProjectApprovalCriteria();

        assertThat(ntQuoteProjectApprovalCriteria).hasToString("NtQuoteProjectApprovalCriteria{}");
    }

    private static void setAllFilters(NtQuoteProjectApprovalCriteria ntQuoteProjectApprovalCriteria) {
        ntQuoteProjectApprovalCriteria.id();
        ntQuoteProjectApprovalCriteria.srNo();
        ntQuoteProjectApprovalCriteria.uid();
        ntQuoteProjectApprovalCriteria.approvedBy();
        ntQuoteProjectApprovalCriteria.approvalDate();
        ntQuoteProjectApprovalCriteria.programManager();
        ntQuoteProjectApprovalCriteria.engineering();
        ntQuoteProjectApprovalCriteria.quality();
        ntQuoteProjectApprovalCriteria.materials();
        ntQuoteProjectApprovalCriteria.plantManager();
        ntQuoteProjectApprovalCriteria.createdBy();
        ntQuoteProjectApprovalCriteria.createdDate();
        ntQuoteProjectApprovalCriteria.updatedBy();
        ntQuoteProjectApprovalCriteria.updatedDate();
        ntQuoteProjectApprovalCriteria.ntQuoteId();
        ntQuoteProjectApprovalCriteria.distinct();
    }

    private static Condition<NtQuoteProjectApprovalCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getSrNo()) &&
                condition.apply(criteria.getUid()) &&
                condition.apply(criteria.getApprovedBy()) &&
                condition.apply(criteria.getApprovalDate()) &&
                condition.apply(criteria.getProgramManager()) &&
                condition.apply(criteria.getEngineering()) &&
                condition.apply(criteria.getQuality()) &&
                condition.apply(criteria.getMaterials()) &&
                condition.apply(criteria.getPlantManager()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedDate()) &&
                condition.apply(criteria.getNtQuoteId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<NtQuoteProjectApprovalCriteria> copyFiltersAre(
        NtQuoteProjectApprovalCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getSrNo(), copy.getSrNo()) &&
                condition.apply(criteria.getUid(), copy.getUid()) &&
                condition.apply(criteria.getApprovedBy(), copy.getApprovedBy()) &&
                condition.apply(criteria.getApprovalDate(), copy.getApprovalDate()) &&
                condition.apply(criteria.getProgramManager(), copy.getProgramManager()) &&
                condition.apply(criteria.getEngineering(), copy.getEngineering()) &&
                condition.apply(criteria.getQuality(), copy.getQuality()) &&
                condition.apply(criteria.getMaterials(), copy.getMaterials()) &&
                condition.apply(criteria.getPlantManager(), copy.getPlantManager()) &&
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
