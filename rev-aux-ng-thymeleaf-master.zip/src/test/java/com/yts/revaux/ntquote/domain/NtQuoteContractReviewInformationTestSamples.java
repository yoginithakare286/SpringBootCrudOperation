package com.yts.revaux.ntquote.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class NtQuoteContractReviewInformationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static NtQuoteContractReviewInformation getNtQuoteContractReviewInformationSample1() {
        return new NtQuoteContractReviewInformation()
            .id(1L)
            .srNo(1)
            .uid(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .contractNumber("contractNumber1")
            .revision("revision1")
            .createdBy("createdBy1")
            .updatedBy("updatedBy1");
    }

    public static NtQuoteContractReviewInformation getNtQuoteContractReviewInformationSample2() {
        return new NtQuoteContractReviewInformation()
            .id(2L)
            .srNo(2)
            .uid(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .contractNumber("contractNumber2")
            .revision("revision2")
            .createdBy("createdBy2")
            .updatedBy("updatedBy2");
    }

    public static NtQuoteContractReviewInformation getNtQuoteContractReviewInformationRandomSampleGenerator() {
        return new NtQuoteContractReviewInformation()
            .id(longCount.incrementAndGet())
            .srNo(intCount.incrementAndGet())
            .uid(UUID.randomUUID())
            .contractNumber(UUID.randomUUID().toString())
            .revision(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
