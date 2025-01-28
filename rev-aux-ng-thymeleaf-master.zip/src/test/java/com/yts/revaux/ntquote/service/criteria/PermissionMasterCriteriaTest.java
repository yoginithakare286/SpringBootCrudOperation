package com.yts.revaux.ntquote.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class PermissionMasterCriteriaTest {

    @Test
    void newPermissionMasterCriteriaHasAllFiltersNullTest() {
        var permissionMasterCriteria = new PermissionMasterCriteria();
        assertThat(permissionMasterCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void permissionMasterCriteriaFluentMethodsCreatesFiltersTest() {
        var permissionMasterCriteria = new PermissionMasterCriteria();

        setAllFilters(permissionMasterCriteria);

        assertThat(permissionMasterCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void permissionMasterCriteriaCopyCreatesNullFilterTest() {
        var permissionMasterCriteria = new PermissionMasterCriteria();
        var copy = permissionMasterCriteria.copy();

        assertThat(permissionMasterCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(permissionMasterCriteria)
        );
    }

    @Test
    void permissionMasterCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var permissionMasterCriteria = new PermissionMasterCriteria();
        setAllFilters(permissionMasterCriteria);

        var copy = permissionMasterCriteria.copy();

        assertThat(permissionMasterCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(permissionMasterCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var permissionMasterCriteria = new PermissionMasterCriteria();

        assertThat(permissionMasterCriteria).hasToString("PermissionMasterCriteria{}");
    }

    private static void setAllFilters(PermissionMasterCriteria permissionMasterCriteria) {
        permissionMasterCriteria.id();
        permissionMasterCriteria.permissionGroup();
        permissionMasterCriteria.permission();
        permissionMasterCriteria.revAuxUserId();
        permissionMasterCriteria.distinct();
    }

    private static Condition<PermissionMasterCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getPermissionGroup()) &&
                condition.apply(criteria.getPermission()) &&
                condition.apply(criteria.getRevAuxUserId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<PermissionMasterCriteria> copyFiltersAre(
        PermissionMasterCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getPermissionGroup(), copy.getPermissionGroup()) &&
                condition.apply(criteria.getPermission(), copy.getPermission()) &&
                condition.apply(criteria.getRevAuxUserId(), copy.getRevAuxUserId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
