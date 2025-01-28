package com.yts.revaux.ntquote.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class NtQuoteCustomerInputOutputVersionTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static NtQuoteCustomerInputOutputVersion getNtQuoteCustomerInputOutputVersionSample1() {
        return new NtQuoteCustomerInputOutputVersion()
            .id(1L)
            .srNo(1)
            .uid(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .materialDescription("materialDescription1")
            .partNumber("partNumber1")
            .materialId("materialId1")
            .supplier("supplier1")
            .estAnnualVolume(1)
            .estProductionRunYrs(1)
            .machineSize("machineSize1")
            .fte("fte1")
            .numberOfCavities(1)
            .cycleTime(1)
            .secondaryOperationExternalProcess("secondaryOperationExternalProcess1")
            .secondaryOperationCycleTime(1)
            .preventativeMaintenanceFrequency(1)
            .preventativeMaintenanceCost(1)
            .version(1)
            .comments("comments1")
            .createdBy("createdBy1")
            .updatedBy("updatedBy1");
    }

    public static NtQuoteCustomerInputOutputVersion getNtQuoteCustomerInputOutputVersionSample2() {
        return new NtQuoteCustomerInputOutputVersion()
            .id(2L)
            .srNo(2)
            .uid(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .materialDescription("materialDescription2")
            .partNumber("partNumber2")
            .materialId("materialId2")
            .supplier("supplier2")
            .estAnnualVolume(2)
            .estProductionRunYrs(2)
            .machineSize("machineSize2")
            .fte("fte2")
            .numberOfCavities(2)
            .cycleTime(2)
            .secondaryOperationExternalProcess("secondaryOperationExternalProcess2")
            .secondaryOperationCycleTime(2)
            .preventativeMaintenanceFrequency(2)
            .preventativeMaintenanceCost(2)
            .version(2)
            .comments("comments2")
            .createdBy("createdBy2")
            .updatedBy("updatedBy2");
    }

    public static NtQuoteCustomerInputOutputVersion getNtQuoteCustomerInputOutputVersionRandomSampleGenerator() {
        return new NtQuoteCustomerInputOutputVersion()
            .id(longCount.incrementAndGet())
            .srNo(intCount.incrementAndGet())
            .uid(UUID.randomUUID())
            .materialDescription(UUID.randomUUID().toString())
            .partNumber(UUID.randomUUID().toString())
            .materialId(UUID.randomUUID().toString())
            .supplier(UUID.randomUUID().toString())
            .estAnnualVolume(intCount.incrementAndGet())
            .estProductionRunYrs(intCount.incrementAndGet())
            .machineSize(UUID.randomUUID().toString())
            .fte(UUID.randomUUID().toString())
            .numberOfCavities(intCount.incrementAndGet())
            .cycleTime(intCount.incrementAndGet())
            .secondaryOperationExternalProcess(UUID.randomUUID().toString())
            .secondaryOperationCycleTime(intCount.incrementAndGet())
            .preventativeMaintenanceFrequency(intCount.incrementAndGet())
            .preventativeMaintenanceCost(intCount.incrementAndGet())
            .version(intCount.incrementAndGet())
            .comments(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
