package com.yts.revaux.ntquote.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class VendorProfileCriteriaTest {

    @Test
    void newVendorProfileCriteriaHasAllFiltersNullTest() {
        var vendorProfileCriteria = new VendorProfileCriteria();
        assertThat(vendorProfileCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void vendorProfileCriteriaFluentMethodsCreatesFiltersTest() {
        var vendorProfileCriteria = new VendorProfileCriteria();

        setAllFilters(vendorProfileCriteria);

        assertThat(vendorProfileCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void vendorProfileCriteriaCopyCreatesNullFilterTest() {
        var vendorProfileCriteria = new VendorProfileCriteria();
        var copy = vendorProfileCriteria.copy();

        assertThat(vendorProfileCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(vendorProfileCriteria)
        );
    }

    @Test
    void vendorProfileCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var vendorProfileCriteria = new VendorProfileCriteria();
        setAllFilters(vendorProfileCriteria);

        var copy = vendorProfileCriteria.copy();

        assertThat(vendorProfileCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(vendorProfileCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var vendorProfileCriteria = new VendorProfileCriteria();

        assertThat(vendorProfileCriteria).hasToString("VendorProfileCriteria{}");
    }

    private static void setAllFilters(VendorProfileCriteria vendorProfileCriteria) {
        vendorProfileCriteria.id();
        vendorProfileCriteria.srNo();
        vendorProfileCriteria.uid();
        vendorProfileCriteria.vendorId();
        vendorProfileCriteria.vendorName();
        vendorProfileCriteria.contact();
        vendorProfileCriteria.entryDate();
        vendorProfileCriteria.tradeCurrencyId();
        vendorProfileCriteria.address1();
        vendorProfileCriteria.address2();
        vendorProfileCriteria.address3();
        vendorProfileCriteria.mailId();
        vendorProfileCriteria.status();
        vendorProfileCriteria.rating();
        vendorProfileCriteria.isDeleteFlag();
        vendorProfileCriteria.relatedBuyerUid();
        vendorProfileCriteria.country();
        vendorProfileCriteria.countryFlag();
        vendorProfileCriteria.buyerRfqPricesDetailId();
        vendorProfileCriteria.distinct();
    }

    private static Condition<VendorProfileCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getSrNo()) &&
                condition.apply(criteria.getUid()) &&
                condition.apply(criteria.getVendorId()) &&
                condition.apply(criteria.getVendorName()) &&
                condition.apply(criteria.getContact()) &&
                condition.apply(criteria.getEntryDate()) &&
                condition.apply(criteria.getTradeCurrencyId()) &&
                condition.apply(criteria.getAddress1()) &&
                condition.apply(criteria.getAddress2()) &&
                condition.apply(criteria.getAddress3()) &&
                condition.apply(criteria.getMailId()) &&
                condition.apply(criteria.getStatus()) &&
                condition.apply(criteria.getRating()) &&
                condition.apply(criteria.getIsDeleteFlag()) &&
                condition.apply(criteria.getRelatedBuyerUid()) &&
                condition.apply(criteria.getCountry()) &&
                condition.apply(criteria.getCountryFlag()) &&
                condition.apply(criteria.getBuyerRfqPricesDetailId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<VendorProfileCriteria> copyFiltersAre(
        VendorProfileCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getSrNo(), copy.getSrNo()) &&
                condition.apply(criteria.getUid(), copy.getUid()) &&
                condition.apply(criteria.getVendorId(), copy.getVendorId()) &&
                condition.apply(criteria.getVendorName(), copy.getVendorName()) &&
                condition.apply(criteria.getContact(), copy.getContact()) &&
                condition.apply(criteria.getEntryDate(), copy.getEntryDate()) &&
                condition.apply(criteria.getTradeCurrencyId(), copy.getTradeCurrencyId()) &&
                condition.apply(criteria.getAddress1(), copy.getAddress1()) &&
                condition.apply(criteria.getAddress2(), copy.getAddress2()) &&
                condition.apply(criteria.getAddress3(), copy.getAddress3()) &&
                condition.apply(criteria.getMailId(), copy.getMailId()) &&
                condition.apply(criteria.getStatus(), copy.getStatus()) &&
                condition.apply(criteria.getRating(), copy.getRating()) &&
                condition.apply(criteria.getIsDeleteFlag(), copy.getIsDeleteFlag()) &&
                condition.apply(criteria.getRelatedBuyerUid(), copy.getRelatedBuyerUid()) &&
                condition.apply(criteria.getCountry(), copy.getCountry()) &&
                condition.apply(criteria.getCountryFlag(), copy.getCountryFlag()) &&
                condition.apply(criteria.getBuyerRfqPricesDetailId(), copy.getBuyerRfqPricesDetailId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
