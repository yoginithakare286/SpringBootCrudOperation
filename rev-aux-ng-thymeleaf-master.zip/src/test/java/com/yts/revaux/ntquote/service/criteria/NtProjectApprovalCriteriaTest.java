package com.yts.revaux.ntquote.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class NtProjectApprovalCriteriaTest {

    @Test
    void newNtProjectApprovalCriteriaHasAllFiltersNullTest() {
        var ntProjectApprovalCriteria = new NtProjectApprovalCriteria();
        assertThat(ntProjectApprovalCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void ntProjectApprovalCriteriaFluentMethodsCreatesFiltersTest() {
        var ntProjectApprovalCriteria = new NtProjectApprovalCriteria();

        setAllFilters(ntProjectApprovalCriteria);

        assertThat(ntProjectApprovalCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void ntProjectApprovalCriteriaCopyCreatesNullFilterTest() {
        var ntProjectApprovalCriteria = new NtProjectApprovalCriteria();
        var copy = ntProjectApprovalCriteria.copy();

        assertThat(ntProjectApprovalCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(ntProjectApprovalCriteria)
        );
    }

    @Test
    void ntProjectApprovalCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var ntProjectApprovalCriteria = new NtProjectApprovalCriteria();
        setAllFilters(ntProjectApprovalCriteria);

        var copy = ntProjectApprovalCriteria.copy();

        assertThat(ntProjectApprovalCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(ntProjectApprovalCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var ntProjectApprovalCriteria = new NtProjectApprovalCriteria();

        assertThat(ntProjectApprovalCriteria).hasToString("NtProjectApprovalCriteria{}");
    }

    private static void setAllFilters(NtProjectApprovalCriteria ntProjectApprovalCriteria) {
        ntProjectApprovalCriteria.id();
        ntProjectApprovalCriteria.srNo();
        ntProjectApprovalCriteria.uid();
        ntProjectApprovalCriteria.approvedBy();
        ntProjectApprovalCriteria.approvalDate();
        ntProjectApprovalCriteria.createdBy();
        ntProjectApprovalCriteria.createdDate();
        ntProjectApprovalCriteria.updatedBy();
        ntProjectApprovalCriteria.updatedDate();
        ntProjectApprovalCriteria.ntQuoteId();
        ntProjectApprovalCriteria.distinct();
    }

    private static Condition<NtProjectApprovalCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getSrNo()) &&
                condition.apply(criteria.getUid()) &&
                condition.apply(criteria.getApprovedBy()) &&
                condition.apply(criteria.getApprovalDate()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedDate()) &&
                condition.apply(criteria.getNtQuoteId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<NtProjectApprovalCriteria> copyFiltersAre(
        NtProjectApprovalCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getSrNo(), copy.getSrNo()) &&
                condition.apply(criteria.getUid(), copy.getUid()) &&
                condition.apply(criteria.getApprovedBy(), copy.getApprovedBy()) &&
                condition.apply(criteria.getApprovalDate(), copy.getApprovalDate()) &&
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
