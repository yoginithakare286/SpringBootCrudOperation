package com.yts.revaux.ntquote.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class BuyerRfqPricesDetailTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static BuyerRfqPricesDetail getBuyerRfqPricesDetailSample1() {
        return new BuyerRfqPricesDetail()
            .id(1L)
            .srNo(1)
            .uid(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .line("line1")
            .materialId("materialId1")
            .quantity(1)
            .awardFlag("awardFlag1")
            .quoteId("quoteId1")
            .rank("rank1")
            .splitQuantityFlag(1)
            .materialDescription("materialDescription1")
            .lastUpdated("lastUpdated1")
            .inviteRaFlag(1)
            .orderAcceptancesFlag(1)
            .materialName("materialName1")
            .materialImage("materialImage1")
            .technicalScrutinyFlag(1)
            .vendorAttributes("vendorAttributes1")
            .moldSizeMoldWeight("moldSizeMoldWeight1")
            .length("length1")
            .width("width1")
            .guage("guage1")
            .tolerance("tolerance1");
    }

    public static BuyerRfqPricesDetail getBuyerRfqPricesDetailSample2() {
        return new BuyerRfqPricesDetail()
            .id(2L)
            .srNo(2)
            .uid(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .line("line2")
            .materialId("materialId2")
            .quantity(2)
            .awardFlag("awardFlag2")
            .quoteId("quoteId2")
            .rank("rank2")
            .splitQuantityFlag(2)
            .materialDescription("materialDescription2")
            .lastUpdated("lastUpdated2")
            .inviteRaFlag(2)
            .orderAcceptancesFlag(2)
            .materialName("materialName2")
            .materialImage("materialImage2")
            .technicalScrutinyFlag(2)
            .vendorAttributes("vendorAttributes2")
            .moldSizeMoldWeight("moldSizeMoldWeight2")
            .length("length2")
            .width("width2")
            .guage("guage2")
            .tolerance("tolerance2");
    }

    public static BuyerRfqPricesDetail getBuyerRfqPricesDetailRandomSampleGenerator() {
        return new BuyerRfqPricesDetail()
            .id(longCount.incrementAndGet())
            .srNo(intCount.incrementAndGet())
            .uid(UUID.randomUUID())
            .line(UUID.randomUUID().toString())
            .materialId(UUID.randomUUID().toString())
            .quantity(intCount.incrementAndGet())
            .awardFlag(UUID.randomUUID().toString())
            .quoteId(UUID.randomUUID().toString())
            .rank(UUID.randomUUID().toString())
            .splitQuantityFlag(intCount.incrementAndGet())
            .materialDescription(UUID.randomUUID().toString())
            .lastUpdated(UUID.randomUUID().toString())
            .inviteRaFlag(intCount.incrementAndGet())
            .orderAcceptancesFlag(intCount.incrementAndGet())
            .materialName(UUID.randomUUID().toString())
            .materialImage(UUID.randomUUID().toString())
            .technicalScrutinyFlag(intCount.incrementAndGet())
            .vendorAttributes(UUID.randomUUID().toString())
            .moldSizeMoldWeight(UUID.randomUUID().toString())
            .length(UUID.randomUUID().toString())
            .width(UUID.randomUUID().toString())
            .guage(UUID.randomUUID().toString())
            .tolerance(UUID.randomUUID().toString());
    }
}
