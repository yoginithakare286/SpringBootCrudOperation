package com.yts.revaux.ntquote.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class NtQuoteProjectApprovalTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static NtQuoteProjectApproval getNtQuoteProjectApprovalSample1() {
        return new NtQuoteProjectApproval()
            .id(1L)
            .srNo(1)
            .uid(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .approvedBy("approvedBy1")
            .programManager("programManager1")
            .engineering("engineering1")
            .quality("quality1")
            .materials("materials1")
            .plantManager("plantManager1")
            .createdBy("createdBy1")
            .updatedBy("updatedBy1");
    }

    public static NtQuoteProjectApproval getNtQuoteProjectApprovalSample2() {
        return new NtQuoteProjectApproval()
            .id(2L)
            .srNo(2)
            .uid(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .approvedBy("approvedBy2")
            .programManager("programManager2")
            .engineering("engineering2")
            .quality("quality2")
            .materials("materials2")
            .plantManager("plantManager2")
            .createdBy("createdBy2")
            .updatedBy("updatedBy2");
    }

    public static NtQuoteProjectApproval getNtQuoteProjectApprovalRandomSampleGenerator() {
        return new NtQuoteProjectApproval()
            .id(longCount.incrementAndGet())
            .srNo(intCount.incrementAndGet())
            .uid(UUID.randomUUID())
            .approvedBy(UUID.randomUUID().toString())
            .programManager(UUID.randomUUID().toString())
            .engineering(UUID.randomUUID().toString())
            .quality(UUID.randomUUID().toString())
            .materials(UUID.randomUUID().toString())
            .plantManager(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
