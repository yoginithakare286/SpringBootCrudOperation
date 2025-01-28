package com.yts.revaux.ntquote.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class VendorProfileTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static VendorProfile getVendorProfileSample1() {
        return new VendorProfile()
            .id(1L)
            .srNo(1)
            .uid(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .vendorId("vendorId1")
            .vendorName("vendorName1")
            .contact("contact1")
            .tradeCurrencyId("tradeCurrencyId1")
            .address1("address11")
            .address2("address21")
            .address3("address31")
            .mailId("mailId1")
            .status("status1")
            .rating("rating1")
            .isDeleteFlag(1)
            .relatedBuyerUid("relatedBuyerUid1")
            .country("country1")
            .countryFlag("countryFlag1");
    }

    public static VendorProfile getVendorProfileSample2() {
        return new VendorProfile()
            .id(2L)
            .srNo(2)
            .uid(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .vendorId("vendorId2")
            .vendorName("vendorName2")
            .contact("contact2")
            .tradeCurrencyId("tradeCurrencyId2")
            .address1("address12")
            .address2("address22")
            .address3("address32")
            .mailId("mailId2")
            .status("status2")
            .rating("rating2")
            .isDeleteFlag(2)
            .relatedBuyerUid("relatedBuyerUid2")
            .country("country2")
            .countryFlag("countryFlag2");
    }

    public static VendorProfile getVendorProfileRandomSampleGenerator() {
        return new VendorProfile()
            .id(longCount.incrementAndGet())
            .srNo(intCount.incrementAndGet())
            .uid(UUID.randomUUID())
            .vendorId(UUID.randomUUID().toString())
            .vendorName(UUID.randomUUID().toString())
            .contact(UUID.randomUUID().toString())
            .tradeCurrencyId(UUID.randomUUID().toString())
            .address1(UUID.randomUUID().toString())
            .address2(UUID.randomUUID().toString())
            .address3(UUID.randomUUID().toString())
            .mailId(UUID.randomUUID().toString())
            .status(UUID.randomUUID().toString())
            .rating(UUID.randomUUID().toString())
            .isDeleteFlag(intCount.incrementAndGet())
            .relatedBuyerUid(UUID.randomUUID().toString())
            .country(UUID.randomUUID().toString())
            .countryFlag(UUID.randomUUID().toString());
    }
}
