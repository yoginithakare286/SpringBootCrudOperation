package com.yts.revaux.ntquote.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class NtQuoteCommentsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static NtQuoteComments getNtQuoteCommentsSample1() {
        return new NtQuoteComments()
            .id(1L)
            .srNo(1)
            .uid(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .comments("comments1")
            .createdBy("createdBy1")
            .updatedBy("updatedBy1");
    }

    public static NtQuoteComments getNtQuoteCommentsSample2() {
        return new NtQuoteComments()
            .id(2L)
            .srNo(2)
            .uid(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .comments("comments2")
            .createdBy("createdBy2")
            .updatedBy("updatedBy2");
    }

    public static NtQuoteComments getNtQuoteCommentsRandomSampleGenerator() {
        return new NtQuoteComments()
            .id(longCount.incrementAndGet())
            .srNo(intCount.incrementAndGet())
            .uid(UUID.randomUUID())
            .comments(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
