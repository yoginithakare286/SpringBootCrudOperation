package com.yts.revaux.ntquote.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class RevAuxUserCriteriaTest {

    @Test
    void newRevAuxUserCriteriaHasAllFiltersNullTest() {
        var revAuxUserCriteria = new RevAuxUserCriteria();
        assertThat(revAuxUserCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void revAuxUserCriteriaFluentMethodsCreatesFiltersTest() {
        var revAuxUserCriteria = new RevAuxUserCriteria();

        setAllFilters(revAuxUserCriteria);

        assertThat(revAuxUserCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void revAuxUserCriteriaCopyCreatesNullFilterTest() {
        var revAuxUserCriteria = new RevAuxUserCriteria();
        var copy = revAuxUserCriteria.copy();

        assertThat(revAuxUserCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(revAuxUserCriteria)
        );
    }

    @Test
    void revAuxUserCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var revAuxUserCriteria = new RevAuxUserCriteria();
        setAllFilters(revAuxUserCriteria);

        var copy = revAuxUserCriteria.copy();

        assertThat(revAuxUserCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(revAuxUserCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var revAuxUserCriteria = new RevAuxUserCriteria();

        assertThat(revAuxUserCriteria).hasToString("RevAuxUserCriteria{}");
    }

    private static void setAllFilters(RevAuxUserCriteria revAuxUserCriteria) {
        revAuxUserCriteria.id();
        revAuxUserCriteria.phoneNumber();
        revAuxUserCriteria.pincode();
        revAuxUserCriteria.city();
        revAuxUserCriteria.state();
        revAuxUserCriteria.country();
        revAuxUserCriteria.preferredLanguage();
        revAuxUserCriteria.internalUserId();
        revAuxUserCriteria.permissionsId();
        revAuxUserCriteria.distinct();
    }

    private static Condition<RevAuxUserCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getPhoneNumber()) &&
                condition.apply(criteria.getPincode()) &&
                condition.apply(criteria.getCity()) &&
                condition.apply(criteria.getState()) &&
                condition.apply(criteria.getCountry()) &&
                condition.apply(criteria.getPreferredLanguage()) &&
                condition.apply(criteria.getInternalUserId()) &&
                condition.apply(criteria.getPermissionsId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<RevAuxUserCriteria> copyFiltersAre(RevAuxUserCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getPhoneNumber(), copy.getPhoneNumber()) &&
                condition.apply(criteria.getPincode(), copy.getPincode()) &&
                condition.apply(criteria.getCity(), copy.getCity()) &&
                condition.apply(criteria.getState(), copy.getState()) &&
                condition.apply(criteria.getCountry(), copy.getCountry()) &&
                condition.apply(criteria.getPreferredLanguage(), copy.getPreferredLanguage()) &&
                condition.apply(criteria.getInternalUserId(), copy.getInternalUserId()) &&
                condition.apply(criteria.getPermissionsId(), copy.getPermissionsId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
