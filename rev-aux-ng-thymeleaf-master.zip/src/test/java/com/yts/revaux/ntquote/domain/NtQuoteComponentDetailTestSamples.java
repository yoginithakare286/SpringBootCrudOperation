package com.yts.revaux.ntquote.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class NtQuoteComponentDetailTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static NtQuoteComponentDetail getNtQuoteComponentDetailSample1() {
        return new NtQuoteComponentDetail()
            .id(1L)
            .srNo(1)
            .uid(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .materialDescription("materialDescription1")
            .partNumber("partNumber1")
            .eau(1)
            .manufacturingLocation("manufacturingLocation1")
            .fobLocation("fobLocation1")
            .packingRequirements("packingRequirements1")
            .machineSize("machineSize1")
            .cycleTime(1)
            .partWeight(1)
            .runnerWeight(1)
            .cavities(1)
            .comments("comments1")
            .riskLevel("riskLevel1")
            .createdBy("createdBy1")
            .updatedBy("updatedBy1");
    }

    public static NtQuoteComponentDetail getNtQuoteComponentDetailSample2() {
        return new NtQuoteComponentDetail()
            .id(2L)
            .srNo(2)
            .uid(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .materialDescription("materialDescription2")
            .partNumber("partNumber2")
            .eau(2)
            .manufacturingLocation("manufacturingLocation2")
            .fobLocation("fobLocation2")
            .packingRequirements("packingRequirements2")
            .machineSize("machineSize2")
            .cycleTime(2)
            .partWeight(2)
            .runnerWeight(2)
            .cavities(2)
            .comments("comments2")
            .riskLevel("riskLevel2")
            .createdBy("createdBy2")
            .updatedBy("updatedBy2");
    }

    public static NtQuoteComponentDetail getNtQuoteComponentDetailRandomSampleGenerator() {
        return new NtQuoteComponentDetail()
            .id(longCount.incrementAndGet())
            .srNo(intCount.incrementAndGet())
            .uid(UUID.randomUUID())
            .materialDescription(UUID.randomUUID().toString())
            .partNumber(UUID.randomUUID().toString())
            .eau(intCount.incrementAndGet())
            .manufacturingLocation(UUID.randomUUID().toString())
            .fobLocation(UUID.randomUUID().toString())
            .packingRequirements(UUID.randomUUID().toString())
            .machineSize(UUID.randomUUID().toString())
            .cycleTime(intCount.incrementAndGet())
            .partWeight(intCount.incrementAndGet())
            .runnerWeight(intCount.incrementAndGet())
            .cavities(intCount.incrementAndGet())
            .comments(UUID.randomUUID().toString())
            .riskLevel(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
