package com.yts.revaux.ntquote.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class NtQuoteVendorPoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static NtQuoteVendorPo getNtQuoteVendorPoSample1() {
        return new NtQuoteVendorPo()
            .id(1L)
            .srNo(1)
            .uid(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .vendorName("vendorName1")
            .fileName("fileName1")
            .country("country1")
            .browse("browse1")
            .createdBy("createdBy1")
            .updatedBy("updatedBy1");
    }

    public static NtQuoteVendorPo getNtQuoteVendorPoSample2() {
        return new NtQuoteVendorPo()
            .id(2L)
            .srNo(2)
            .uid(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .vendorName("vendorName2")
            .fileName("fileName2")
            .country("country2")
            .browse("browse2")
            .createdBy("createdBy2")
            .updatedBy("updatedBy2");
    }

    public static NtQuoteVendorPo getNtQuoteVendorPoRandomSampleGenerator() {
        return new NtQuoteVendorPo()
            .id(longCount.incrementAndGet())
            .srNo(intCount.incrementAndGet())
            .uid(UUID.randomUUID())
            .vendorName(UUID.randomUUID().toString())
            .fileName(UUID.randomUUID().toString())
            .country(UUID.randomUUID().toString())
            .browse(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
