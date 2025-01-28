package com.yts.revaux.ntquote.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class NtQuoteCustomerProjectTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static NtQuoteCustomerProject getNtQuoteCustomerProjectSample1() {
        return new NtQuoteCustomerProject()
            .id(1L)
            .srNo(1)
            .uid(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .qsf("qsf1")
            .rev("rev1")
            .customerName("customerName1")
            .contactName("contactName1")
            .phone("phone1")
            .email("email1")
            .overallProjectRiskEvaluation("overallProjectRiskEvaluation1")
            .projectName("projectName1")
            .projectInformation("projectInformation1")
            .projectManager("projectManager1")
            .projectRequirement("projectRequirement1")
            .lengthOfProject("lengthOfProject1")
            .newMold("newMold1")
            .transferMold("transferMold1")
            .createdBy("createdBy1")
            .updatedBy("updatedBy1");
    }

    public static NtQuoteCustomerProject getNtQuoteCustomerProjectSample2() {
        return new NtQuoteCustomerProject()
            .id(2L)
            .srNo(2)
            .uid(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .qsf("qsf2")
            .rev("rev2")
            .customerName("customerName2")
            .contactName("contactName2")
            .phone("phone2")
            .email("email2")
            .overallProjectRiskEvaluation("overallProjectRiskEvaluation2")
            .projectName("projectName2")
            .projectInformation("projectInformation2")
            .projectManager("projectManager2")
            .projectRequirement("projectRequirement2")
            .lengthOfProject("lengthOfProject2")
            .newMold("newMold2")
            .transferMold("transferMold2")
            .createdBy("createdBy2")
            .updatedBy("updatedBy2");
    }

    public static NtQuoteCustomerProject getNtQuoteCustomerProjectRandomSampleGenerator() {
        return new NtQuoteCustomerProject()
            .id(longCount.incrementAndGet())
            .srNo(intCount.incrementAndGet())
            .uid(UUID.randomUUID())
            .qsf(UUID.randomUUID().toString())
            .rev(UUID.randomUUID().toString())
            .customerName(UUID.randomUUID().toString())
            .contactName(UUID.randomUUID().toString())
            .phone(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .overallProjectRiskEvaluation(UUID.randomUUID().toString())
            .projectName(UUID.randomUUID().toString())
            .projectInformation(UUID.randomUUID().toString())
            .projectManager(UUID.randomUUID().toString())
            .projectRequirement(UUID.randomUUID().toString())
            .lengthOfProject(UUID.randomUUID().toString())
            .newMold(UUID.randomUUID().toString())
            .transferMold(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
