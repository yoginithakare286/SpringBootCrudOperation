package com.yts.revaux.ntquote.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class NtQuoteVendorPoCriteriaTest {

    @Test
    void newNtQuoteVendorPoCriteriaHasAllFiltersNullTest() {
        var ntQuoteVendorPoCriteria = new NtQuoteVendorPoCriteria();
        assertThat(ntQuoteVendorPoCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void ntQuoteVendorPoCriteriaFluentMethodsCreatesFiltersTest() {
        var ntQuoteVendorPoCriteria = new NtQuoteVendorPoCriteria();

        setAllFilters(ntQuoteVendorPoCriteria);

        assertThat(ntQuoteVendorPoCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void ntQuoteVendorPoCriteriaCopyCreatesNullFilterTest() {
        var ntQuoteVendorPoCriteria = new NtQuoteVendorPoCriteria();
        var copy = ntQuoteVendorPoCriteria.copy();

        assertThat(ntQuoteVendorPoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(ntQuoteVendorPoCriteria)
        );
    }

    @Test
    void ntQuoteVendorPoCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var ntQuoteVendorPoCriteria = new NtQuoteVendorPoCriteria();
        setAllFilters(ntQuoteVendorPoCriteria);

        var copy = ntQuoteVendorPoCriteria.copy();

        assertThat(ntQuoteVendorPoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(ntQuoteVendorPoCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var ntQuoteVendorPoCriteria = new NtQuoteVendorPoCriteria();

        assertThat(ntQuoteVendorPoCriteria).hasToString("NtQuoteVendorPoCriteria{}");
    }

    private static void setAllFilters(NtQuoteVendorPoCriteria ntQuoteVendorPoCriteria) {
        ntQuoteVendorPoCriteria.id();
        ntQuoteVendorPoCriteria.srNo();
        ntQuoteVendorPoCriteria.uid();
        ntQuoteVendorPoCriteria.vendorName();
        ntQuoteVendorPoCriteria.quoteDate();
        ntQuoteVendorPoCriteria.fileName();
        ntQuoteVendorPoCriteria.country();
        ntQuoteVendorPoCriteria.browse();
        ntQuoteVendorPoCriteria.createdBy();
        ntQuoteVendorPoCriteria.createdDate();
        ntQuoteVendorPoCriteria.updatedBy();
        ntQuoteVendorPoCriteria.updatedDate();
        ntQuoteVendorPoCriteria.ntQuoteId();
        ntQuoteVendorPoCriteria.distinct();
    }

    private static Condition<NtQuoteVendorPoCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getSrNo()) &&
                condition.apply(criteria.getUid()) &&
                condition.apply(criteria.getVendorName()) &&
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

    private static Condition<NtQuoteVendorPoCriteria> copyFiltersAre(
        NtQuoteVendorPoCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getSrNo(), copy.getSrNo()) &&
                condition.apply(criteria.getUid(), copy.getUid()) &&
                condition.apply(criteria.getVendorName(), copy.getVendorName()) &&
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
