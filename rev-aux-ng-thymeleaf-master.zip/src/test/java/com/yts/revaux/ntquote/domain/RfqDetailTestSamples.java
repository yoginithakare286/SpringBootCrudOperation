package com.yts.revaux.ntquote.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class RfqDetailTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static RfqDetail getRfqDetailSample1() {
        return new RfqDetail()
            .id(1L)
            .srNo(1)
            .uid(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .rfqId("rfqId1")
            .itemDescription("itemDescription1")
            .rfqStatus("rfqStatus1")
            .rfqType("rfqType1")
            .customer("customer1")
            .part("part1")
            .buyer("buyer1")
            .expectedLaunch("expectedLaunch1")
            .requestor("requestor1")
            .raStatus("raStatus1")
            .isDelete(1)
            .customerFeedback("customerFeedback1");
    }

    public static RfqDetail getRfqDetailSample2() {
        return new RfqDetail()
            .id(2L)
            .srNo(2)
            .uid(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .rfqId("rfqId2")
            .itemDescription("itemDescription2")
            .rfqStatus("rfqStatus2")
            .rfqType("rfqType2")
            .customer("customer2")
            .part("part2")
            .buyer("buyer2")
            .expectedLaunch("expectedLaunch2")
            .requestor("requestor2")
            .raStatus("raStatus2")
            .isDelete(2)
            .customerFeedback("customerFeedback2");
    }

    public static RfqDetail getRfqDetailRandomSampleGenerator() {
        return new RfqDetail()
            .id(longCount.incrementAndGet())
            .srNo(intCount.incrementAndGet())
            .uid(UUID.randomUUID())
            .rfqId(UUID.randomUUID().toString())
            .itemDescription(UUID.randomUUID().toString())
            .rfqStatus(UUID.randomUUID().toString())
            .rfqType(UUID.randomUUID().toString())
            .customer(UUID.randomUUID().toString())
            .part(UUID.randomUUID().toString())
            .buyer(UUID.randomUUID().toString())
            .expectedLaunch(UUID.randomUUID().toString())
            .requestor(UUID.randomUUID().toString())
            .raStatus(UUID.randomUUID().toString())
            .isDelete(intCount.incrementAndGet())
            .customerFeedback(UUID.randomUUID().toString());
    }
}
