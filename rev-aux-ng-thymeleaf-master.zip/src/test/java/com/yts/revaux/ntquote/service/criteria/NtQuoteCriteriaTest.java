package com.yts.revaux.ntquote.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class NtQuoteCriteriaTest {

    @Test
    void newNtQuoteCriteriaHasAllFiltersNullTest() {
        var ntQuoteCriteria = new NtQuoteCriteria();
        assertThat(ntQuoteCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void ntQuoteCriteriaFluentMethodsCreatesFiltersTest() {
        var ntQuoteCriteria = new NtQuoteCriteria();

        setAllFilters(ntQuoteCriteria);

        assertThat(ntQuoteCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void ntQuoteCriteriaCopyCreatesNullFilterTest() {
        var ntQuoteCriteria = new NtQuoteCriteria();
        var copy = ntQuoteCriteria.copy();

        assertThat(ntQuoteCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(ntQuoteCriteria)
        );
    }

    @Test
    void ntQuoteCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var ntQuoteCriteria = new NtQuoteCriteria();
        setAllFilters(ntQuoteCriteria);

        var copy = ntQuoteCriteria.copy();

        assertThat(ntQuoteCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(ntQuoteCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var ntQuoteCriteria = new NtQuoteCriteria();

        assertThat(ntQuoteCriteria).hasToString("NtQuoteCriteria{}");
    }

    private static void setAllFilters(NtQuoteCriteria ntQuoteCriteria) {
        ntQuoteCriteria.id();
        ntQuoteCriteria.srNo();
        ntQuoteCriteria.uid();
        ntQuoteCriteria.quoteKey();
        ntQuoteCriteria.salesPerson();
        ntQuoteCriteria.customerName();
        ntQuoteCriteria.quoteNumber();
        ntQuoteCriteria.status();
        ntQuoteCriteria.moldNumber();
        ntQuoteCriteria.partNumber();
        ntQuoteCriteria.dueDate();
        ntQuoteCriteria.moldManual();
        ntQuoteCriteria.customerPo();
        ntQuoteCriteria.vendorQuote();
        ntQuoteCriteria.vendorPo();
        ntQuoteCriteria.cadFile();
        ntQuoteCriteria.quotedPrice();
        ntQuoteCriteria.deliveryTime();
        ntQuoteCriteria.quoteDate();
        ntQuoteCriteria.createdBy();
        ntQuoteCriteria.createdDate();
        ntQuoteCriteria.updatedBy();
        ntQuoteCriteria.updatedDate();
        ntQuoteCriteria.projectConsiderationsId();
        ntQuoteCriteria.contractReviewInformationId();
        ntQuoteCriteria.customerInputOutputVersionId();
        ntQuoteCriteria.partInformationMasterId();
        ntQuoteCriteria.commentsId();
        ntQuoteCriteria.termsConditionsId();
        ntQuoteCriteria.projectApprovalId();
        ntQuoteCriteria.partInformationVersionId();
        ntQuoteCriteria.customerPoId();
        ntQuoteCriteria.vendorQuoteId();
        ntQuoteCriteria.vendorPoId();
        ntQuoteCriteria.rfqDetailId();
        ntQuoteCriteria.ntQuoteProjectApprovalId();
        ntQuoteCriteria.distinct();
    }

    private static Condition<NtQuoteCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getSrNo()) &&
                condition.apply(criteria.getUid()) &&
                condition.apply(criteria.getQuoteKey()) &&
                condition.apply(criteria.getSalesPerson()) &&
                condition.apply(criteria.getCustomerName()) &&
                condition.apply(criteria.getQuoteNumber()) &&
                condition.apply(criteria.getStatus()) &&
                condition.apply(criteria.getMoldNumber()) &&
                condition.apply(criteria.getPartNumber()) &&
                condition.apply(criteria.getDueDate()) &&
                condition.apply(criteria.getMoldManual()) &&
                condition.apply(criteria.getCustomerPo()) &&
                condition.apply(criteria.getVendorQuote()) &&
                condition.apply(criteria.getVendorPo()) &&
                condition.apply(criteria.getCadFile()) &&
                condition.apply(criteria.getQuotedPrice()) &&
                condition.apply(criteria.getDeliveryTime()) &&
                condition.apply(criteria.getQuoteDate()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedDate()) &&
                condition.apply(criteria.getProjectConsiderationsId()) &&
                condition.apply(criteria.getContractReviewInformationId()) &&
                condition.apply(criteria.getCustomerInputOutputVersionId()) &&
                condition.apply(criteria.getPartInformationMasterId()) &&
                condition.apply(criteria.getCommentsId()) &&
                condition.apply(criteria.getTermsConditionsId()) &&
                condition.apply(criteria.getProjectApprovalId()) &&
                condition.apply(criteria.getPartInformationVersionId()) &&
                condition.apply(criteria.getCustomerPoId()) &&
                condition.apply(criteria.getVendorQuoteId()) &&
                condition.apply(criteria.getVendorPoId()) &&
                condition.apply(criteria.getRfqDetailId()) &&
                condition.apply(criteria.getNtQuoteProjectApprovalId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<NtQuoteCriteria> copyFiltersAre(NtQuoteCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getSrNo(), copy.getSrNo()) &&
                condition.apply(criteria.getUid(), copy.getUid()) &&
                condition.apply(criteria.getQuoteKey(), copy.getQuoteKey()) &&
                condition.apply(criteria.getSalesPerson(), copy.getSalesPerson()) &&
                condition.apply(criteria.getCustomerName(), copy.getCustomerName()) &&
                condition.apply(criteria.getQuoteNumber(), copy.getQuoteNumber()) &&
                condition.apply(criteria.getStatus(), copy.getStatus()) &&
                condition.apply(criteria.getMoldNumber(), copy.getMoldNumber()) &&
                condition.apply(criteria.getPartNumber(), copy.getPartNumber()) &&
                condition.apply(criteria.getDueDate(), copy.getDueDate()) &&
                condition.apply(criteria.getMoldManual(), copy.getMoldManual()) &&
                condition.apply(criteria.getCustomerPo(), copy.getCustomerPo()) &&
                condition.apply(criteria.getVendorQuote(), copy.getVendorQuote()) &&
                condition.apply(criteria.getVendorPo(), copy.getVendorPo()) &&
                condition.apply(criteria.getCadFile(), copy.getCadFile()) &&
                condition.apply(criteria.getQuotedPrice(), copy.getQuotedPrice()) &&
                condition.apply(criteria.getDeliveryTime(), copy.getDeliveryTime()) &&
                condition.apply(criteria.getQuoteDate(), copy.getQuoteDate()) &&
                condition.apply(criteria.getCreatedBy(), copy.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate(), copy.getCreatedDate()) &&
                condition.apply(criteria.getUpdatedBy(), copy.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedDate(), copy.getUpdatedDate()) &&
                condition.apply(criteria.getProjectConsiderationsId(), copy.getProjectConsiderationsId()) &&
                condition.apply(criteria.getContractReviewInformationId(), copy.getContractReviewInformationId()) &&
                condition.apply(criteria.getCustomerInputOutputVersionId(), copy.getCustomerInputOutputVersionId()) &&
                condition.apply(criteria.getPartInformationMasterId(), copy.getPartInformationMasterId()) &&
                condition.apply(criteria.getCommentsId(), copy.getCommentsId()) &&
                condition.apply(criteria.getTermsConditionsId(), copy.getTermsConditionsId()) &&
                condition.apply(criteria.getProjectApprovalId(), copy.getProjectApprovalId()) &&
                condition.apply(criteria.getPartInformationVersionId(), copy.getPartInformationVersionId()) &&
                condition.apply(criteria.getCustomerPoId(), copy.getCustomerPoId()) &&
                condition.apply(criteria.getVendorQuoteId(), copy.getVendorQuoteId()) &&
                condition.apply(criteria.getVendorPoId(), copy.getVendorPoId()) &&
                condition.apply(criteria.getRfqDetailId(), copy.getRfqDetailId()) &&
                condition.apply(criteria.getNtQuoteProjectApprovalId(), copy.getNtQuoteProjectApprovalId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
