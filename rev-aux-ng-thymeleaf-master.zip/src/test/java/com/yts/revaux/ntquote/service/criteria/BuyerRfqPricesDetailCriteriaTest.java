package com.yts.revaux.ntquote.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class BuyerRfqPricesDetailCriteriaTest {

    @Test
    void newBuyerRfqPricesDetailCriteriaHasAllFiltersNullTest() {
        var buyerRfqPricesDetailCriteria = new BuyerRfqPricesDetailCriteria();
        assertThat(buyerRfqPricesDetailCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void buyerRfqPricesDetailCriteriaFluentMethodsCreatesFiltersTest() {
        var buyerRfqPricesDetailCriteria = new BuyerRfqPricesDetailCriteria();

        setAllFilters(buyerRfqPricesDetailCriteria);

        assertThat(buyerRfqPricesDetailCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void buyerRfqPricesDetailCriteriaCopyCreatesNullFilterTest() {
        var buyerRfqPricesDetailCriteria = new BuyerRfqPricesDetailCriteria();
        var copy = buyerRfqPricesDetailCriteria.copy();

        assertThat(buyerRfqPricesDetailCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(buyerRfqPricesDetailCriteria)
        );
    }

    @Test
    void buyerRfqPricesDetailCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var buyerRfqPricesDetailCriteria = new BuyerRfqPricesDetailCriteria();
        setAllFilters(buyerRfqPricesDetailCriteria);

        var copy = buyerRfqPricesDetailCriteria.copy();

        assertThat(buyerRfqPricesDetailCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(buyerRfqPricesDetailCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var buyerRfqPricesDetailCriteria = new BuyerRfqPricesDetailCriteria();

        assertThat(buyerRfqPricesDetailCriteria).hasToString("BuyerRfqPricesDetailCriteria{}");
    }

    private static void setAllFilters(BuyerRfqPricesDetailCriteria buyerRfqPricesDetailCriteria) {
        buyerRfqPricesDetailCriteria.id();
        buyerRfqPricesDetailCriteria.srNo();
        buyerRfqPricesDetailCriteria.uid();
        buyerRfqPricesDetailCriteria.line();
        buyerRfqPricesDetailCriteria.materialId();
        buyerRfqPricesDetailCriteria.quantity();
        buyerRfqPricesDetailCriteria.estUnitPrice();
        buyerRfqPricesDetailCriteria.actUnitPrice();
        buyerRfqPricesDetailCriteria.awardFlag();
        buyerRfqPricesDetailCriteria.quoteId();
        buyerRfqPricesDetailCriteria.receivedDate();
        buyerRfqPricesDetailCriteria.leadDays();
        buyerRfqPricesDetailCriteria.rank();
        buyerRfqPricesDetailCriteria.splitQuantityFlag();
        buyerRfqPricesDetailCriteria.materialDescription();
        buyerRfqPricesDetailCriteria.lastUpdated();
        buyerRfqPricesDetailCriteria.inviteRaFlag();
        buyerRfqPricesDetailCriteria.awardAcceptancesDate();
        buyerRfqPricesDetailCriteria.orderAcceptancesDate();
        buyerRfqPricesDetailCriteria.orderAcceptancesFlag();
        buyerRfqPricesDetailCriteria.materialName();
        buyerRfqPricesDetailCriteria.materialImage();
        buyerRfqPricesDetailCriteria.technicalScrutinyFlag();
        buyerRfqPricesDetailCriteria.vendorAttributes();
        buyerRfqPricesDetailCriteria.marginFactor();
        buyerRfqPricesDetailCriteria.fob();
        buyerRfqPricesDetailCriteria.shippingFactor();
        buyerRfqPricesDetailCriteria.freight();
        buyerRfqPricesDetailCriteria.finalShipmentCost();
        buyerRfqPricesDetailCriteria.tariff();
        buyerRfqPricesDetailCriteria.calculatedTariffsCost();
        buyerRfqPricesDetailCriteria.totalCumberlandPrice();
        buyerRfqPricesDetailCriteria.landedPrice();
        buyerRfqPricesDetailCriteria.approvalToGain();
        buyerRfqPricesDetailCriteria.moldSizeMoldWeight();
        buyerRfqPricesDetailCriteria.moldLifeExpectancy();
        buyerRfqPricesDetailCriteria.totalCostComparison();
        buyerRfqPricesDetailCriteria.length();
        buyerRfqPricesDetailCriteria.width();
        buyerRfqPricesDetailCriteria.guage();
        buyerRfqPricesDetailCriteria.tolerance();
        buyerRfqPricesDetailCriteria.rfqDetailId();
        buyerRfqPricesDetailCriteria.vendorId();
        buyerRfqPricesDetailCriteria.distinct();
    }

    private static Condition<BuyerRfqPricesDetailCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getSrNo()) &&
                condition.apply(criteria.getUid()) &&
                condition.apply(criteria.getLine()) &&
                condition.apply(criteria.getMaterialId()) &&
                condition.apply(criteria.getQuantity()) &&
                condition.apply(criteria.getEstUnitPrice()) &&
                condition.apply(criteria.getActUnitPrice()) &&
                condition.apply(criteria.getAwardFlag()) &&
                condition.apply(criteria.getQuoteId()) &&
                condition.apply(criteria.getReceivedDate()) &&
                condition.apply(criteria.getLeadDays()) &&
                condition.apply(criteria.getRank()) &&
                condition.apply(criteria.getSplitQuantityFlag()) &&
                condition.apply(criteria.getMaterialDescription()) &&
                condition.apply(criteria.getLastUpdated()) &&
                condition.apply(criteria.getInviteRaFlag()) &&
                condition.apply(criteria.getAwardAcceptancesDate()) &&
                condition.apply(criteria.getOrderAcceptancesDate()) &&
                condition.apply(criteria.getOrderAcceptancesFlag()) &&
                condition.apply(criteria.getMaterialName()) &&
                condition.apply(criteria.getMaterialImage()) &&
                condition.apply(criteria.getTechnicalScrutinyFlag()) &&
                condition.apply(criteria.getVendorAttributes()) &&
                condition.apply(criteria.getMarginFactor()) &&
                condition.apply(criteria.getFob()) &&
                condition.apply(criteria.getShippingFactor()) &&
                condition.apply(criteria.getFreight()) &&
                condition.apply(criteria.getFinalShipmentCost()) &&
                condition.apply(criteria.getTariff()) &&
                condition.apply(criteria.getCalculatedTariffsCost()) &&
                condition.apply(criteria.getTotalCumberlandPrice()) &&
                condition.apply(criteria.getLandedPrice()) &&
                condition.apply(criteria.getApprovalToGain()) &&
                condition.apply(criteria.getMoldSizeMoldWeight()) &&
                condition.apply(criteria.getMoldLifeExpectancy()) &&
                condition.apply(criteria.getTotalCostComparison()) &&
                condition.apply(criteria.getLength()) &&
                condition.apply(criteria.getWidth()) &&
                condition.apply(criteria.getGuage()) &&
                condition.apply(criteria.getTolerance()) &&
                condition.apply(criteria.getRfqDetailId()) &&
                condition.apply(criteria.getVendorId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<BuyerRfqPricesDetailCriteria> copyFiltersAre(
        BuyerRfqPricesDetailCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getSrNo(), copy.getSrNo()) &&
                condition.apply(criteria.getUid(), copy.getUid()) &&
                condition.apply(criteria.getLine(), copy.getLine()) &&
                condition.apply(criteria.getMaterialId(), copy.getMaterialId()) &&
                condition.apply(criteria.getQuantity(), copy.getQuantity()) &&
                condition.apply(criteria.getEstUnitPrice(), copy.getEstUnitPrice()) &&
                condition.apply(criteria.getActUnitPrice(), copy.getActUnitPrice()) &&
                condition.apply(criteria.getAwardFlag(), copy.getAwardFlag()) &&
                condition.apply(criteria.getQuoteId(), copy.getQuoteId()) &&
                condition.apply(criteria.getReceivedDate(), copy.getReceivedDate()) &&
                condition.apply(criteria.getLeadDays(), copy.getLeadDays()) &&
                condition.apply(criteria.getRank(), copy.getRank()) &&
                condition.apply(criteria.getSplitQuantityFlag(), copy.getSplitQuantityFlag()) &&
                condition.apply(criteria.getMaterialDescription(), copy.getMaterialDescription()) &&
                condition.apply(criteria.getLastUpdated(), copy.getLastUpdated()) &&
                condition.apply(criteria.getInviteRaFlag(), copy.getInviteRaFlag()) &&
                condition.apply(criteria.getAwardAcceptancesDate(), copy.getAwardAcceptancesDate()) &&
                condition.apply(criteria.getOrderAcceptancesDate(), copy.getOrderAcceptancesDate()) &&
                condition.apply(criteria.getOrderAcceptancesFlag(), copy.getOrderAcceptancesFlag()) &&
                condition.apply(criteria.getMaterialName(), copy.getMaterialName()) &&
                condition.apply(criteria.getMaterialImage(), copy.getMaterialImage()) &&
                condition.apply(criteria.getTechnicalScrutinyFlag(), copy.getTechnicalScrutinyFlag()) &&
                condition.apply(criteria.getVendorAttributes(), copy.getVendorAttributes()) &&
                condition.apply(criteria.getMarginFactor(), copy.getMarginFactor()) &&
                condition.apply(criteria.getFob(), copy.getFob()) &&
                condition.apply(criteria.getShippingFactor(), copy.getShippingFactor()) &&
                condition.apply(criteria.getFreight(), copy.getFreight()) &&
                condition.apply(criteria.getFinalShipmentCost(), copy.getFinalShipmentCost()) &&
                condition.apply(criteria.getTariff(), copy.getTariff()) &&
                condition.apply(criteria.getCalculatedTariffsCost(), copy.getCalculatedTariffsCost()) &&
                condition.apply(criteria.getTotalCumberlandPrice(), copy.getTotalCumberlandPrice()) &&
                condition.apply(criteria.getLandedPrice(), copy.getLandedPrice()) &&
                condition.apply(criteria.getApprovalToGain(), copy.getApprovalToGain()) &&
                condition.apply(criteria.getMoldSizeMoldWeight(), copy.getMoldSizeMoldWeight()) &&
                condition.apply(criteria.getMoldLifeExpectancy(), copy.getMoldLifeExpectancy()) &&
                condition.apply(criteria.getTotalCostComparison(), copy.getTotalCostComparison()) &&
                condition.apply(criteria.getLength(), copy.getLength()) &&
                condition.apply(criteria.getWidth(), copy.getWidth()) &&
                condition.apply(criteria.getGuage(), copy.getGuage()) &&
                condition.apply(criteria.getTolerance(), copy.getTolerance()) &&
                condition.apply(criteria.getRfqDetailId(), copy.getRfqDetailId()) &&
                condition.apply(criteria.getVendorId(), copy.getVendorId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
