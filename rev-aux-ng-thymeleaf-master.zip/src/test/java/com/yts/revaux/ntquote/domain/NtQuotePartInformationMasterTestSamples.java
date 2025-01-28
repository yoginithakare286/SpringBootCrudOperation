package com.yts.revaux.ntquote.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class NtQuotePartInformationMasterTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static NtQuotePartInformationMaster getNtQuotePartInformationMasterSample1() {
        return new NtQuotePartInformationMaster()
            .id(1L)
            .srNo(1)
            .uid(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .materialDescription("materialDescription1")
            .partNumber("partNumber1")
            .cadFile("cadFile1")
            .eau(1)
            .partWeight(1)
            .materialType("materialType1")
            .machineSizeTons("machineSizeTons1")
            .numberOfCavities(1)
            .cycleTime(1)
            .leadTime("leadTime1")
            .toolingNotes("toolingNotes1")
            .partDescription("partDescription1")
            .jobId("jobId1")
            .moldId("moldId1")
            .createdBy("createdBy1")
            .updatedBy("updatedBy1");
    }

    public static NtQuotePartInformationMaster getNtQuotePartInformationMasterSample2() {
        return new NtQuotePartInformationMaster()
            .id(2L)
            .srNo(2)
            .uid(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .materialDescription("materialDescription2")
            .partNumber("partNumber2")
            .cadFile("cadFile2")
            .eau(2)
            .partWeight(2)
            .materialType("materialType2")
            .machineSizeTons("machineSizeTons2")
            .numberOfCavities(2)
            .cycleTime(2)
            .leadTime("leadTime2")
            .toolingNotes("toolingNotes2")
            .partDescription("partDescription2")
            .jobId("jobId2")
            .moldId("moldId2")
            .createdBy("createdBy2")
            .updatedBy("updatedBy2");
    }

    public static NtQuotePartInformationMaster getNtQuotePartInformationMasterRandomSampleGenerator() {
        return new NtQuotePartInformationMaster()
            .id(longCount.incrementAndGet())
            .srNo(intCount.incrementAndGet())
            .uid(UUID.randomUUID())
            .materialDescription(UUID.randomUUID().toString())
            .partNumber(UUID.randomUUID().toString())
            .cadFile(UUID.randomUUID().toString())
            .eau(intCount.incrementAndGet())
            .partWeight(intCount.incrementAndGet())
            .materialType(UUID.randomUUID().toString())
            .machineSizeTons(UUID.randomUUID().toString())
            .numberOfCavities(intCount.incrementAndGet())
            .cycleTime(intCount.incrementAndGet())
            .leadTime(UUID.randomUUID().toString())
            .toolingNotes(UUID.randomUUID().toString())
            .partDescription(UUID.randomUUID().toString())
            .jobId(UUID.randomUUID().toString())
            .moldId(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
