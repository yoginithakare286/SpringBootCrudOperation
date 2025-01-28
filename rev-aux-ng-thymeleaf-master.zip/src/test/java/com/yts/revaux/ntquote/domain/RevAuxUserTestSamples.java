package com.yts.revaux.ntquote.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RevAuxUserTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static RevAuxUser getRevAuxUserSample1() {
        return new RevAuxUser()
            .id(1L)
            .phoneNumber("phoneNumber1")
            .pincode("pincode1")
            .city("city1")
            .state("state1")
            .country("country1")
            .preferredLanguage("preferredLanguage1");
    }

    public static RevAuxUser getRevAuxUserSample2() {
        return new RevAuxUser()
            .id(2L)
            .phoneNumber("phoneNumber2")
            .pincode("pincode2")
            .city("city2")
            .state("state2")
            .country("country2")
            .preferredLanguage("preferredLanguage2");
    }

    public static RevAuxUser getRevAuxUserRandomSampleGenerator() {
        return new RevAuxUser()
            .id(longCount.incrementAndGet())
            .phoneNumber(UUID.randomUUID().toString())
            .pincode(UUID.randomUUID().toString())
            .city(UUID.randomUUID().toString())
            .state(UUID.randomUUID().toString())
            .country(UUID.randomUUID().toString())
            .preferredLanguage(UUID.randomUUID().toString());
    }
}
