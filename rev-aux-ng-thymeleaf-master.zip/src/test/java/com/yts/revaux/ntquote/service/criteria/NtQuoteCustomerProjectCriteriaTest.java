package com.yts.revaux.ntquote.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class NtQuoteCustomerProjectCriteriaTest {

    @Test
    void newNtQuoteCustomerProjectCriteriaHasAllFiltersNullTest() {
        var ntQuoteCustomerProjectCriteria = new NtQuoteCustomerProjectCriteria();
        assertThat(ntQuoteCustomerProjectCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void ntQuoteCustomerProjectCriteriaFluentMethodsCreatesFiltersTest() {
        var ntQuoteCustomerProjectCriteria = new NtQuoteCustomerProjectCriteria();

        setAllFilters(ntQuoteCustomerProjectCriteria);

        assertThat(ntQuoteCustomerProjectCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void ntQuoteCustomerProjectCriteriaCopyCreatesNullFilterTest() {
        var ntQuoteCustomerProjectCriteria = new NtQuoteCustomerProjectCriteria();
        var copy = ntQuoteCustomerProjectCriteria.copy();

        assertThat(ntQuoteCustomerProjectCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(ntQuoteCustomerProjectCriteria)
        );
    }

    @Test
    void ntQuoteCustomerProjectCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var ntQuoteCustomerProjectCriteria = new NtQuoteCustomerProjectCriteria();
        setAllFilters(ntQuoteCustomerProjectCriteria);

        var copy = ntQuoteCustomerProjectCriteria.copy();

        assertThat(ntQuoteCustomerProjectCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(ntQuoteCustomerProjectCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var ntQuoteCustomerProjectCriteria = new NtQuoteCustomerProjectCriteria();

        assertThat(ntQuoteCustomerProjectCriteria).hasToString("NtQuoteCustomerProjectCriteria{}");
    }

    private static void setAllFilters(NtQuoteCustomerProjectCriteria ntQuoteCustomerProjectCriteria) {
        ntQuoteCustomerProjectCriteria.id();
        ntQuoteCustomerProjectCriteria.srNo();
        ntQuoteCustomerProjectCriteria.uid();
        ntQuoteCustomerProjectCriteria.qsf();
        ntQuoteCustomerProjectCriteria.rev();
        ntQuoteCustomerProjectCriteria.date();
        ntQuoteCustomerProjectCriteria.customerName();
        ntQuoteCustomerProjectCriteria.contactName();
        ntQuoteCustomerProjectCriteria.phone();
        ntQuoteCustomerProjectCriteria.email();
        ntQuoteCustomerProjectCriteria.overallProjectRiskEvaluation();
        ntQuoteCustomerProjectCriteria.assessmentDate();
        ntQuoteCustomerProjectCriteria.reAssessmentDate();
        ntQuoteCustomerProjectCriteria.projectName();
        ntQuoteCustomerProjectCriteria.projectInformation();
        ntQuoteCustomerProjectCriteria.projectManager();
        ntQuoteCustomerProjectCriteria.projectRequirement();
        ntQuoteCustomerProjectCriteria.lengthOfProject();
        ntQuoteCustomerProjectCriteria.newMold();
        ntQuoteCustomerProjectCriteria.transferMold();
        ntQuoteCustomerProjectCriteria.contactReviewDate();
        ntQuoteCustomerProjectCriteria.createdBy();
        ntQuoteCustomerProjectCriteria.createdDate();
        ntQuoteCustomerProjectCriteria.updatedBy();
        ntQuoteCustomerProjectCriteria.updatedDate();
        ntQuoteCustomerProjectCriteria.ntQuoteId();
        ntQuoteCustomerProjectCriteria.distinct();
    }

    private static Condition<NtQuoteCustomerProjectCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getSrNo()) &&
                condition.apply(criteria.getUid()) &&
                condition.apply(criteria.getQsf()) &&
                condition.apply(criteria.getRev()) &&
                condition.apply(criteria.getDate()) &&
                condition.apply(criteria.getCustomerName()) &&
                condition.apply(criteria.getContactName()) &&
                condition.apply(criteria.getPhone()) &&
                condition.apply(criteria.getEmail()) &&
                condition.apply(criteria.getOverallProjectRiskEvaluation()) &&
                condition.apply(criteria.getAssessmentDate()) &&
                condition.apply(criteria.getReAssessmentDate()) &&
                condition.apply(criteria.getProjectName()) &&
                condition.apply(criteria.getProjectInformation()) &&
                condition.apply(criteria.getProjectManager()) &&
                condition.apply(criteria.getProjectRequirement()) &&
                condition.apply(criteria.getLengthOfProject()) &&
                condition.apply(criteria.getNewMold()) &&
                condition.apply(criteria.getTransferMold()) &&
                condition.apply(criteria.getContactReviewDate()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedDate()) &&
                condition.apply(criteria.getNtQuoteId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<NtQuoteCustomerProjectCriteria> copyFiltersAre(
        NtQuoteCustomerProjectCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getSrNo(), copy.getSrNo()) &&
                condition.apply(criteria.getUid(), copy.getUid()) &&
                condition.apply(criteria.getQsf(), copy.getQsf()) &&
                condition.apply(criteria.getRev(), copy.getRev()) &&
                condition.apply(criteria.getDate(), copy.getDate()) &&
                condition.apply(criteria.getCustomerName(), copy.getCustomerName()) &&
                condition.apply(criteria.getContactName(), copy.getContactName()) &&
                condition.apply(criteria.getPhone(), copy.getPhone()) &&
                condition.apply(criteria.getEmail(), copy.getEmail()) &&
                condition.apply(criteria.getOverallProjectRiskEvaluation(), copy.getOverallProjectRiskEvaluation()) &&
                condition.apply(criteria.getAssessmentDate(), copy.getAssessmentDate()) &&
                condition.apply(criteria.getReAssessmentDate(), copy.getReAssessmentDate()) &&
                condition.apply(criteria.getProjectName(), copy.getProjectName()) &&
                condition.apply(criteria.getProjectInformation(), copy.getProjectInformation()) &&
                condition.apply(criteria.getProjectManager(), copy.getProjectManager()) &&
                condition.apply(criteria.getProjectRequirement(), copy.getProjectRequirement()) &&
                condition.apply(criteria.getLengthOfProject(), copy.getLengthOfProject()) &&
                condition.apply(criteria.getNewMold(), copy.getNewMold()) &&
                condition.apply(criteria.getTransferMold(), copy.getTransferMold()) &&
                condition.apply(criteria.getContactReviewDate(), copy.getContactReviewDate()) &&
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
