package com.yts.revaux.ntquote.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class NtQuoteContractReviewInformationCriteriaTest {

    @Test
    void newNtQuoteContractReviewInformationCriteriaHasAllFiltersNullTest() {
        var ntQuoteContractReviewInformationCriteria = new NtQuoteContractReviewInformationCriteria();
        assertThat(ntQuoteContractReviewInformationCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void ntQuoteContractReviewInformationCriteriaFluentMethodsCreatesFiltersTest() {
        var ntQuoteContractReviewInformationCriteria = new NtQuoteContractReviewInformationCriteria();

        setAllFilters(ntQuoteContractReviewInformationCriteria);

        assertThat(ntQuoteContractReviewInformationCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void ntQuoteContractReviewInformationCriteriaCopyCreatesNullFilterTest() {
        var ntQuoteContractReviewInformationCriteria = new NtQuoteContractReviewInformationCriteria();
        var copy = ntQuoteContractReviewInformationCriteria.copy();

        assertThat(ntQuoteContractReviewInformationCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(ntQuoteContractReviewInformationCriteria)
        );
    }

    @Test
    void ntQuoteContractReviewInformationCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var ntQuoteContractReviewInformationCriteria = new NtQuoteContractReviewInformationCriteria();
        setAllFilters(ntQuoteContractReviewInformationCriteria);

        var copy = ntQuoteContractReviewInformationCriteria.copy();

        assertThat(ntQuoteContractReviewInformationCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(ntQuoteContractReviewInformationCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var ntQuoteContractReviewInformationCriteria = new NtQuoteContractReviewInformationCriteria();

        assertThat(ntQuoteContractReviewInformationCriteria).hasToString("NtQuoteContractReviewInformationCriteria{}");
    }

    private static void setAllFilters(NtQuoteContractReviewInformationCriteria ntQuoteContractReviewInformationCriteria) {
        ntQuoteContractReviewInformationCriteria.id();
        ntQuoteContractReviewInformationCriteria.srNo();
        ntQuoteContractReviewInformationCriteria.uid();
        ntQuoteContractReviewInformationCriteria.contractNumber();
        ntQuoteContractReviewInformationCriteria.revision();
        ntQuoteContractReviewInformationCriteria.reviewDate();
        ntQuoteContractReviewInformationCriteria.createdBy();
        ntQuoteContractReviewInformationCriteria.createdDate();
        ntQuoteContractReviewInformationCriteria.updatedBy();
        ntQuoteContractReviewInformationCriteria.updatedDate();
        ntQuoteContractReviewInformationCriteria.ntQuoteId();
        ntQuoteContractReviewInformationCriteria.distinct();
    }

    private static Condition<NtQuoteContractReviewInformationCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getSrNo()) &&
                condition.apply(criteria.getUid()) &&
                condition.apply(criteria.getContractNumber()) &&
                condition.apply(criteria.getRevision()) &&
                condition.apply(criteria.getReviewDate()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedDate()) &&
                condition.apply(criteria.getNtQuoteId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<NtQuoteContractReviewInformationCriteria> copyFiltersAre(
        NtQuoteContractReviewInformationCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getSrNo(), copy.getSrNo()) &&
                condition.apply(criteria.getUid(), copy.getUid()) &&
                condition.apply(criteria.getContractNumber(), copy.getContractNumber()) &&
                condition.apply(criteria.getRevision(), copy.getRevision()) &&
                condition.apply(criteria.getReviewDate(), copy.getReviewDate()) &&
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
