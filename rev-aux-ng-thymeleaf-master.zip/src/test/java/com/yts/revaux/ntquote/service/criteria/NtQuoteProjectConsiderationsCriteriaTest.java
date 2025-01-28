package com.yts.revaux.ntquote.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class NtQuoteProjectConsiderationsCriteriaTest {

    @Test
    void newNtQuoteProjectConsiderationsCriteriaHasAllFiltersNullTest() {
        var ntQuoteProjectConsiderationsCriteria = new NtQuoteProjectConsiderationsCriteria();
        assertThat(ntQuoteProjectConsiderationsCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void ntQuoteProjectConsiderationsCriteriaFluentMethodsCreatesFiltersTest() {
        var ntQuoteProjectConsiderationsCriteria = new NtQuoteProjectConsiderationsCriteria();

        setAllFilters(ntQuoteProjectConsiderationsCriteria);

        assertThat(ntQuoteProjectConsiderationsCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void ntQuoteProjectConsiderationsCriteriaCopyCreatesNullFilterTest() {
        var ntQuoteProjectConsiderationsCriteria = new NtQuoteProjectConsiderationsCriteria();
        var copy = ntQuoteProjectConsiderationsCriteria.copy();

        assertThat(ntQuoteProjectConsiderationsCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(ntQuoteProjectConsiderationsCriteria)
        );
    }

    @Test
    void ntQuoteProjectConsiderationsCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var ntQuoteProjectConsiderationsCriteria = new NtQuoteProjectConsiderationsCriteria();
        setAllFilters(ntQuoteProjectConsiderationsCriteria);

        var copy = ntQuoteProjectConsiderationsCriteria.copy();

        assertThat(ntQuoteProjectConsiderationsCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(ntQuoteProjectConsiderationsCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var ntQuoteProjectConsiderationsCriteria = new NtQuoteProjectConsiderationsCriteria();

        assertThat(ntQuoteProjectConsiderationsCriteria).hasToString("NtQuoteProjectConsiderationsCriteria{}");
    }

    private static void setAllFilters(NtQuoteProjectConsiderationsCriteria ntQuoteProjectConsiderationsCriteria) {
        ntQuoteProjectConsiderationsCriteria.id();
        ntQuoteProjectConsiderationsCriteria.srNo();
        ntQuoteProjectConsiderationsCriteria.uid();
        ntQuoteProjectConsiderationsCriteria.projectConsideration();
        ntQuoteProjectConsiderationsCriteria.choice();
        ntQuoteProjectConsiderationsCriteria.comments();
        ntQuoteProjectConsiderationsCriteria.createdBy();
        ntQuoteProjectConsiderationsCriteria.createdDate();
        ntQuoteProjectConsiderationsCriteria.updatedBy();
        ntQuoteProjectConsiderationsCriteria.updatedDate();
        ntQuoteProjectConsiderationsCriteria.ntQuoteId();
        ntQuoteProjectConsiderationsCriteria.distinct();
    }

    private static Condition<NtQuoteProjectConsiderationsCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getSrNo()) &&
                condition.apply(criteria.getUid()) &&
                condition.apply(criteria.getProjectConsideration()) &&
                condition.apply(criteria.getChoice()) &&
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

    private static Condition<NtQuoteProjectConsiderationsCriteria> copyFiltersAre(
        NtQuoteProjectConsiderationsCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getSrNo(), copy.getSrNo()) &&
                condition.apply(criteria.getUid(), copy.getUid()) &&
                condition.apply(criteria.getProjectConsideration(), copy.getProjectConsideration()) &&
                condition.apply(criteria.getChoice(), copy.getChoice()) &&
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
