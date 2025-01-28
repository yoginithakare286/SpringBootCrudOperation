package com.yts.revaux.ntquote.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class RfqDetailCriteriaTest {

    @Test
    void newRfqDetailCriteriaHasAllFiltersNullTest() {
        var rfqDetailCriteria = new RfqDetailCriteria();
        assertThat(rfqDetailCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void rfqDetailCriteriaFluentMethodsCreatesFiltersTest() {
        var rfqDetailCriteria = new RfqDetailCriteria();

        setAllFilters(rfqDetailCriteria);

        assertThat(rfqDetailCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void rfqDetailCriteriaCopyCreatesNullFilterTest() {
        var rfqDetailCriteria = new RfqDetailCriteria();
        var copy = rfqDetailCriteria.copy();

        assertThat(rfqDetailCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(rfqDetailCriteria)
        );
    }

    @Test
    void rfqDetailCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var rfqDetailCriteria = new RfqDetailCriteria();
        setAllFilters(rfqDetailCriteria);

        var copy = rfqDetailCriteria.copy();

        assertThat(rfqDetailCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(rfqDetailCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var rfqDetailCriteria = new RfqDetailCriteria();

        assertThat(rfqDetailCriteria).hasToString("RfqDetailCriteria{}");
    }

    private static void setAllFilters(RfqDetailCriteria rfqDetailCriteria) {
        rfqDetailCriteria.id();
        rfqDetailCriteria.srNo();
        rfqDetailCriteria.uid();
        rfqDetailCriteria.rfqId();
        rfqDetailCriteria.orderDate();
        rfqDetailCriteria.startDate();
        rfqDetailCriteria.endDate();
        rfqDetailCriteria.itemDescription();
        rfqDetailCriteria.rfqStatus();
        rfqDetailCriteria.rfqType();
        rfqDetailCriteria.customer();
        rfqDetailCriteria.rfqReceivedDate();
        rfqDetailCriteria.quoteDueDate();
        rfqDetailCriteria.part();
        rfqDetailCriteria.buyer();
        rfqDetailCriteria.expectedLaunch();
        rfqDetailCriteria.requestor();
        rfqDetailCriteria.raStatus();
        rfqDetailCriteria.isDelete();
        rfqDetailCriteria.customerFeedback();
        rfqDetailCriteria.buyerRfqPricesDetailId();
        rfqDetailCriteria.distinct();
    }

    private static Condition<RfqDetailCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getSrNo()) &&
                condition.apply(criteria.getUid()) &&
                condition.apply(criteria.getRfqId()) &&
                condition.apply(criteria.getOrderDate()) &&
                condition.apply(criteria.getStartDate()) &&
                condition.apply(criteria.getEndDate()) &&
                condition.apply(criteria.getItemDescription()) &&
                condition.apply(criteria.getRfqStatus()) &&
                condition.apply(criteria.getRfqType()) &&
                condition.apply(criteria.getCustomer()) &&
                condition.apply(criteria.getRfqReceivedDate()) &&
                condition.apply(criteria.getQuoteDueDate()) &&
                condition.apply(criteria.getPart()) &&
                condition.apply(criteria.getBuyer()) &&
                condition.apply(criteria.getExpectedLaunch()) &&
                condition.apply(criteria.getRequestor()) &&
                condition.apply(criteria.getRaStatus()) &&
                condition.apply(criteria.getIsDelete()) &&
                condition.apply(criteria.getCustomerFeedback()) &&
                condition.apply(criteria.getBuyerRfqPricesDetailId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<RfqDetailCriteria> copyFiltersAre(RfqDetailCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getSrNo(), copy.getSrNo()) &&
                condition.apply(criteria.getUid(), copy.getUid()) &&
                condition.apply(criteria.getRfqId(), copy.getRfqId()) &&
                condition.apply(criteria.getOrderDate(), copy.getOrderDate()) &&
                condition.apply(criteria.getStartDate(), copy.getStartDate()) &&
                condition.apply(criteria.getEndDate(), copy.getEndDate()) &&
                condition.apply(criteria.getItemDescription(), copy.getItemDescription()) &&
                condition.apply(criteria.getRfqStatus(), copy.getRfqStatus()) &&
                condition.apply(criteria.getRfqType(), copy.getRfqType()) &&
                condition.apply(criteria.getCustomer(), copy.getCustomer()) &&
                condition.apply(criteria.getRfqReceivedDate(), copy.getRfqReceivedDate()) &&
                condition.apply(criteria.getQuoteDueDate(), copy.getQuoteDueDate()) &&
                condition.apply(criteria.getPart(), copy.getPart()) &&
                condition.apply(criteria.getBuyer(), copy.getBuyer()) &&
                condition.apply(criteria.getExpectedLaunch(), copy.getExpectedLaunch()) &&
                condition.apply(criteria.getRequestor(), copy.getRequestor()) &&
                condition.apply(criteria.getRaStatus(), copy.getRaStatus()) &&
                condition.apply(criteria.getIsDelete(), copy.getIsDelete()) &&
                condition.apply(criteria.getCustomerFeedback(), copy.getCustomerFeedback()) &&
                condition.apply(criteria.getBuyerRfqPricesDetailId(), copy.getBuyerRfqPricesDetailId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
