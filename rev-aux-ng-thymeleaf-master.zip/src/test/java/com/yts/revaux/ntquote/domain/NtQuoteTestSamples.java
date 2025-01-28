package com.yts.revaux.ntquote.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class NtQuoteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static NtQuote getNtQuoteSample1() {
        return new NtQuote()
            .id(1L)
            .srNo(1)
            .uid(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .quoteKey("quoteKey1")
            .salesPerson("salesPerson1")
            .customerName("customerName1")
            .quoteNumber("quoteNumber1")
            .status("status1")
            .moldNumber("moldNumber1")
            .partNumber("partNumber1")
            .moldManual("moldManual1")
            .customerPo("customerPo1")
            .vendorQuote("vendorQuote1")
            .vendorPo("vendorPo1")
            .cadFile("cadFile1")
            .quotedPrice(1)
            .deliveryTime("deliveryTime1")
            .createdBy("createdBy1")
            .updatedBy("updatedBy1");
    }

    public static NtQuote getNtQuoteSample2() {
        return new NtQuote()
            .id(2L)
            .srNo(2)
            .uid(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .quoteKey("quoteKey2")
            .salesPerson("salesPerson2")
            .customerName("customerName2")
            .quoteNumber("quoteNumber2")
            .status("status2")
            .moldNumber("moldNumber2")
            .partNumber("partNumber2")
            .moldManual("moldManual2")
            .customerPo("customerPo2")
            .vendorQuote("vendorQuote2")
            .vendorPo("vendorPo2")
            .cadFile("cadFile2")
            .quotedPrice(2)
            .deliveryTime("deliveryTime2")
            .createdBy("createdBy2")
            .updatedBy("updatedBy2");
    }

    public static NtQuote getNtQuoteRandomSampleGenerator() {
        return new NtQuote()
            .id(longCount.incrementAndGet())
            .srNo(intCount.incrementAndGet())
            .uid(UUID.randomUUID())
            .quoteKey(UUID.randomUUID().toString())
            .salesPerson(UUID.randomUUID().toString())
            .customerName(UUID.randomUUID().toString())
            .quoteNumber(UUID.randomUUID().toString())
            .status(UUID.randomUUID().toString())
            .moldNumber(UUID.randomUUID().toString())
            .partNumber(UUID.randomUUID().toString())
            .moldManual(UUID.randomUUID().toString())
            .customerPo(UUID.randomUUID().toString())
            .vendorQuote(UUID.randomUUID().toString())
            .vendorPo(UUID.randomUUID().toString())
            .cadFile(UUID.randomUUID().toString())
            .quotedPrice(intCount.incrementAndGet())
            .deliveryTime(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
