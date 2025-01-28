package com.yts.revaux.ntquote.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class NtQuoteCustomerPoCriteriaTest {

    @Test
    void newNtQuoteCustomerPoCriteriaHasAllFiltersNullTest() {
        var ntQuoteCustomerPoCriteria = new NtQuoteCustomerPoCriteria();
        assertThat(ntQuoteCustomerPoCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void ntQuoteCustomerPoCriteriaFluentMethodsCreatesFiltersTest() {
        var ntQuoteCustomerPoCriteria = new NtQuoteCustomerPoCriteria();

        setAllFilters(ntQuoteCustomerPoCriteria);

        assertThat(ntQuoteCustomerPoCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void ntQuoteCustomerPoCriteriaCopyCreatesNullFilterTest() {
        var ntQuoteCustomerPoCriteria = new NtQuoteCustomerPoCriteria();
        var copy = ntQuoteCustomerPoCriteria.copy();

        assertThat(ntQuoteCustomerPoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(ntQuoteCustomerPoCriteria)
        );
    }

    @Test
    void ntQuoteCustomerPoCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var ntQuoteCustomerPoCriteria = new NtQuoteCustomerPoCriteria();
        setAllFilters(ntQuoteCustomerPoCriteria);

        var copy = ntQuoteCustomerPoCriteria.copy();

        assertThat(ntQuoteCustomerPoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(ntQuoteCustomerPoCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var ntQuoteCustomerPoCriteria = new NtQuoteCustomerPoCriteria();

        assertThat(ntQuoteCustomerPoCriteria).hasToString("NtQuoteCustomerPoCriteria{}");
    }

    private static void setAllFilters(NtQuoteCustomerPoCriteria ntQuoteCustomerPoCriteria) {
        ntQuoteCustomerPoCriteria.id();
        ntQuoteCustomerPoCriteria.srNo();
        ntQuoteCustomerPoCriteria.uid();
        ntQuoteCustomerPoCriteria.customerName();
        ntQuoteCustomerPoCriteria.quoteDate();
        ntQuoteCustomerPoCriteria.fileName();
        ntQuoteCustomerPoCriteria.country();
        ntQuoteCustomerPoCriteria.browse();
        ntQuoteCustomerPoCriteria.createdBy();
        ntQuoteCustomerPoCriteria.createdDate();
        ntQuoteCustomerPoCriteria.updatedBy();
        ntQuoteCustomerPoCriteria.updatedDate();
        ntQuoteCustomerPoCriteria.ntQuoteId();
        ntQuoteCustomerPoCriteria.distinct();
    }

    private static Condition<NtQuoteCustomerPoCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getSrNo()) &&
                condition.apply(criteria.getUid()) &&
                condition.apply(criteria.getCustomerName()) &&
                condition.apply(criteria.getQuoteDate()) &&
                condition.apply(criteria.getFileName()) &&
                condition.apply(criteria.getCountry()) &&
                condition.apply(criteria.getBrowse()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedDate()) &&
                condition.apply(criteria.getNtQuoteId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<NtQuoteCustomerPoCriteria> copyFiltersAre(
        NtQuoteCustomerPoCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getSrNo(), copy.getSrNo()) &&
                condition.apply(criteria.getUid(), copy.getUid()) &&
                condition.apply(criteria.getCustomerName(), copy.getCustomerName()) &&
                condition.apply(criteria.getQuoteDate(), copy.getQuoteDate()) &&
                condition.apply(criteria.getFileName(), copy.getFileName()) &&
                condition.apply(criteria.getCountry(), copy.getCountry()) &&
                condition.apply(criteria.getBrowse(), copy.getBrowse()) &&
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
