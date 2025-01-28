package com.yts.revaux.ntquote.domain;

import static com.yts.revaux.ntquote.domain.NtProjectApprovalTestSamples.*;
import static com.yts.revaux.ntquote.domain.NtQuoteCommentsTestSamples.*;
import static com.yts.revaux.ntquote.domain.NtQuoteContractReviewInformationTestSamples.*;
import static com.yts.revaux.ntquote.domain.NtQuoteCustomerInputOutputVersionTestSamples.*;
import static com.yts.revaux.ntquote.domain.NtQuoteCustomerPoTestSamples.*;
import static com.yts.revaux.ntquote.domain.NtQuotePartInformationMasterTestSamples.*;
import static com.yts.revaux.ntquote.domain.NtQuotePartInformationVersionTestSamples.*;
import static com.yts.revaux.ntquote.domain.NtQuoteProjectApprovalTestSamples.*;
import static com.yts.revaux.ntquote.domain.NtQuoteProjectConsiderationsTestSamples.*;
import static com.yts.revaux.ntquote.domain.NtQuoteTermsConditionsTestSamples.*;
import static com.yts.revaux.ntquote.domain.NtQuoteTestSamples.*;
import static com.yts.revaux.ntquote.domain.NtQuoteVendorPoTestSamples.*;
import static com.yts.revaux.ntquote.domain.NtQuoteVendorQuoteTestSamples.*;
import static com.yts.revaux.ntquote.domain.RfqDetailTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class NtQuoteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NtQuote.class);
        NtQuote ntQuote1 = getNtQuoteSample1();
        NtQuote ntQuote2 = new NtQuote();
        assertThat(ntQuote1).isNotEqualTo(ntQuote2);

        ntQuote2.setId(ntQuote1.getId());
        assertThat(ntQuote1).isEqualTo(ntQuote2);

        ntQuote2 = getNtQuoteSample2();
        assertThat(ntQuote1).isNotEqualTo(ntQuote2);
    }

    @Test
    void projectConsiderationsTest() {
        NtQuote ntQuote = getNtQuoteRandomSampleGenerator();
        NtQuoteProjectConsiderations ntQuoteProjectConsiderationsBack = getNtQuoteProjectConsiderationsRandomSampleGenerator();

        ntQuote.addProjectConsiderations(ntQuoteProjectConsiderationsBack);
        assertThat(ntQuote.getProjectConsiderations()).containsOnly(ntQuoteProjectConsiderationsBack);
        assertThat(ntQuoteProjectConsiderationsBack.getNtQuote()).isEqualTo(ntQuote);

        ntQuote.removeProjectConsiderations(ntQuoteProjectConsiderationsBack);
        assertThat(ntQuote.getProjectConsiderations()).doesNotContain(ntQuoteProjectConsiderationsBack);
        assertThat(ntQuoteProjectConsiderationsBack.getNtQuote()).isNull();

        ntQuote.projectConsiderations(new HashSet<>(Set.of(ntQuoteProjectConsiderationsBack)));
        assertThat(ntQuote.getProjectConsiderations()).containsOnly(ntQuoteProjectConsiderationsBack);
        assertThat(ntQuoteProjectConsiderationsBack.getNtQuote()).isEqualTo(ntQuote);

        ntQuote.setProjectConsiderations(new HashSet<>());
        assertThat(ntQuote.getProjectConsiderations()).doesNotContain(ntQuoteProjectConsiderationsBack);
        assertThat(ntQuoteProjectConsiderationsBack.getNtQuote()).isNull();
    }

    @Test
    void contractReviewInformationTest() {
        NtQuote ntQuote = getNtQuoteRandomSampleGenerator();
        NtQuoteContractReviewInformation ntQuoteContractReviewInformationBack = getNtQuoteContractReviewInformationRandomSampleGenerator();

        ntQuote.addContractReviewInformation(ntQuoteContractReviewInformationBack);
        assertThat(ntQuote.getContractReviewInformations()).containsOnly(ntQuoteContractReviewInformationBack);
        assertThat(ntQuoteContractReviewInformationBack.getNtQuote()).isEqualTo(ntQuote);

        ntQuote.removeContractReviewInformation(ntQuoteContractReviewInformationBack);
        assertThat(ntQuote.getContractReviewInformations()).doesNotContain(ntQuoteContractReviewInformationBack);
        assertThat(ntQuoteContractReviewInformationBack.getNtQuote()).isNull();

        ntQuote.contractReviewInformations(new HashSet<>(Set.of(ntQuoteContractReviewInformationBack)));
        assertThat(ntQuote.getContractReviewInformations()).containsOnly(ntQuoteContractReviewInformationBack);
        assertThat(ntQuoteContractReviewInformationBack.getNtQuote()).isEqualTo(ntQuote);

        ntQuote.setContractReviewInformations(new HashSet<>());
        assertThat(ntQuote.getContractReviewInformations()).doesNotContain(ntQuoteContractReviewInformationBack);
        assertThat(ntQuoteContractReviewInformationBack.getNtQuote()).isNull();
    }

    @Test
    void customerInputOutputVersionTest() {
        NtQuote ntQuote = getNtQuoteRandomSampleGenerator();
        NtQuoteCustomerInputOutputVersion ntQuoteCustomerInputOutputVersionBack =
            getNtQuoteCustomerInputOutputVersionRandomSampleGenerator();

        ntQuote.addCustomerInputOutputVersion(ntQuoteCustomerInputOutputVersionBack);
        assertThat(ntQuote.getCustomerInputOutputVersions()).containsOnly(ntQuoteCustomerInputOutputVersionBack);
        assertThat(ntQuoteCustomerInputOutputVersionBack.getNtQuote()).isEqualTo(ntQuote);

        ntQuote.removeCustomerInputOutputVersion(ntQuoteCustomerInputOutputVersionBack);
        assertThat(ntQuote.getCustomerInputOutputVersions()).doesNotContain(ntQuoteCustomerInputOutputVersionBack);
        assertThat(ntQuoteCustomerInputOutputVersionBack.getNtQuote()).isNull();

        ntQuote.customerInputOutputVersions(new HashSet<>(Set.of(ntQuoteCustomerInputOutputVersionBack)));
        assertThat(ntQuote.getCustomerInputOutputVersions()).containsOnly(ntQuoteCustomerInputOutputVersionBack);
        assertThat(ntQuoteCustomerInputOutputVersionBack.getNtQuote()).isEqualTo(ntQuote);

        ntQuote.setCustomerInputOutputVersions(new HashSet<>());
        assertThat(ntQuote.getCustomerInputOutputVersions()).doesNotContain(ntQuoteCustomerInputOutputVersionBack);
        assertThat(ntQuoteCustomerInputOutputVersionBack.getNtQuote()).isNull();
    }

    @Test
    void partInformationMasterTest() {
        NtQuote ntQuote = getNtQuoteRandomSampleGenerator();
        NtQuotePartInformationMaster ntQuotePartInformationMasterBack = getNtQuotePartInformationMasterRandomSampleGenerator();

        ntQuote.addPartInformationMaster(ntQuotePartInformationMasterBack);
        assertThat(ntQuote.getPartInformationMasters()).containsOnly(ntQuotePartInformationMasterBack);
        assertThat(ntQuotePartInformationMasterBack.getNtQuote()).isEqualTo(ntQuote);

        ntQuote.removePartInformationMaster(ntQuotePartInformationMasterBack);
        assertThat(ntQuote.getPartInformationMasters()).doesNotContain(ntQuotePartInformationMasterBack);
        assertThat(ntQuotePartInformationMasterBack.getNtQuote()).isNull();

        ntQuote.partInformationMasters(new HashSet<>(Set.of(ntQuotePartInformationMasterBack)));
        assertThat(ntQuote.getPartInformationMasters()).containsOnly(ntQuotePartInformationMasterBack);
        assertThat(ntQuotePartInformationMasterBack.getNtQuote()).isEqualTo(ntQuote);

        ntQuote.setPartInformationMasters(new HashSet<>());
        assertThat(ntQuote.getPartInformationMasters()).doesNotContain(ntQuotePartInformationMasterBack);
        assertThat(ntQuotePartInformationMasterBack.getNtQuote()).isNull();
    }

    @Test
    void commentsTest() {
        NtQuote ntQuote = getNtQuoteRandomSampleGenerator();
        NtQuoteComments ntQuoteCommentsBack = getNtQuoteCommentsRandomSampleGenerator();

        ntQuote.addComments(ntQuoteCommentsBack);
        assertThat(ntQuote.getComments()).containsOnly(ntQuoteCommentsBack);
        assertThat(ntQuoteCommentsBack.getNtQuote()).isEqualTo(ntQuote);

        ntQuote.removeComments(ntQuoteCommentsBack);
        assertThat(ntQuote.getComments()).doesNotContain(ntQuoteCommentsBack);
        assertThat(ntQuoteCommentsBack.getNtQuote()).isNull();

        ntQuote.comments(new HashSet<>(Set.of(ntQuoteCommentsBack)));
        assertThat(ntQuote.getComments()).containsOnly(ntQuoteCommentsBack);
        assertThat(ntQuoteCommentsBack.getNtQuote()).isEqualTo(ntQuote);

        ntQuote.setComments(new HashSet<>());
        assertThat(ntQuote.getComments()).doesNotContain(ntQuoteCommentsBack);
        assertThat(ntQuoteCommentsBack.getNtQuote()).isNull();
    }

    @Test
    void termsConditionsTest() {
        NtQuote ntQuote = getNtQuoteRandomSampleGenerator();
        NtQuoteTermsConditions ntQuoteTermsConditionsBack = getNtQuoteTermsConditionsRandomSampleGenerator();

        ntQuote.addTermsConditions(ntQuoteTermsConditionsBack);
        assertThat(ntQuote.getTermsConditions()).containsOnly(ntQuoteTermsConditionsBack);
        assertThat(ntQuoteTermsConditionsBack.getNtQuote()).isEqualTo(ntQuote);

        ntQuote.removeTermsConditions(ntQuoteTermsConditionsBack);
        assertThat(ntQuote.getTermsConditions()).doesNotContain(ntQuoteTermsConditionsBack);
        assertThat(ntQuoteTermsConditionsBack.getNtQuote()).isNull();

        ntQuote.termsConditions(new HashSet<>(Set.of(ntQuoteTermsConditionsBack)));
        assertThat(ntQuote.getTermsConditions()).containsOnly(ntQuoteTermsConditionsBack);
        assertThat(ntQuoteTermsConditionsBack.getNtQuote()).isEqualTo(ntQuote);

        ntQuote.setTermsConditions(new HashSet<>());
        assertThat(ntQuote.getTermsConditions()).doesNotContain(ntQuoteTermsConditionsBack);
        assertThat(ntQuoteTermsConditionsBack.getNtQuote()).isNull();
    }

    @Test
    void projectApprovalTest() {
        NtQuote ntQuote = getNtQuoteRandomSampleGenerator();
        NtProjectApproval ntProjectApprovalBack = getNtProjectApprovalRandomSampleGenerator();

        ntQuote.addProjectApproval(ntProjectApprovalBack);
        assertThat(ntQuote.getProjectApprovals()).containsOnly(ntProjectApprovalBack);
        assertThat(ntProjectApprovalBack.getNtQuote()).isEqualTo(ntQuote);

        ntQuote.removeProjectApproval(ntProjectApprovalBack);
        assertThat(ntQuote.getProjectApprovals()).doesNotContain(ntProjectApprovalBack);
        assertThat(ntProjectApprovalBack.getNtQuote()).isNull();

        ntQuote.projectApprovals(new HashSet<>(Set.of(ntProjectApprovalBack)));
        assertThat(ntQuote.getProjectApprovals()).containsOnly(ntProjectApprovalBack);
        assertThat(ntProjectApprovalBack.getNtQuote()).isEqualTo(ntQuote);

        ntQuote.setProjectApprovals(new HashSet<>());
        assertThat(ntQuote.getProjectApprovals()).doesNotContain(ntProjectApprovalBack);
        assertThat(ntProjectApprovalBack.getNtQuote()).isNull();
    }

    @Test
    void partInformationVersionTest() {
        NtQuote ntQuote = getNtQuoteRandomSampleGenerator();
        NtQuotePartInformationVersion ntQuotePartInformationVersionBack = getNtQuotePartInformationVersionRandomSampleGenerator();

        ntQuote.addPartInformationVersion(ntQuotePartInformationVersionBack);
        assertThat(ntQuote.getPartInformationVersions()).containsOnly(ntQuotePartInformationVersionBack);
        assertThat(ntQuotePartInformationVersionBack.getNtQuote()).isEqualTo(ntQuote);

        ntQuote.removePartInformationVersion(ntQuotePartInformationVersionBack);
        assertThat(ntQuote.getPartInformationVersions()).doesNotContain(ntQuotePartInformationVersionBack);
        assertThat(ntQuotePartInformationVersionBack.getNtQuote()).isNull();

        ntQuote.partInformationVersions(new HashSet<>(Set.of(ntQuotePartInformationVersionBack)));
        assertThat(ntQuote.getPartInformationVersions()).containsOnly(ntQuotePartInformationVersionBack);
        assertThat(ntQuotePartInformationVersionBack.getNtQuote()).isEqualTo(ntQuote);

        ntQuote.setPartInformationVersions(new HashSet<>());
        assertThat(ntQuote.getPartInformationVersions()).doesNotContain(ntQuotePartInformationVersionBack);
        assertThat(ntQuotePartInformationVersionBack.getNtQuote()).isNull();
    }

    @Test
    void customerPoTest() {
        NtQuote ntQuote = getNtQuoteRandomSampleGenerator();
        NtQuoteCustomerPo ntQuoteCustomerPoBack = getNtQuoteCustomerPoRandomSampleGenerator();

        ntQuote.addCustomerPo(ntQuoteCustomerPoBack);
        assertThat(ntQuote.getCustomerPos()).containsOnly(ntQuoteCustomerPoBack);
        assertThat(ntQuoteCustomerPoBack.getNtQuote()).isEqualTo(ntQuote);

        ntQuote.removeCustomerPo(ntQuoteCustomerPoBack);
        assertThat(ntQuote.getCustomerPos()).doesNotContain(ntQuoteCustomerPoBack);
        assertThat(ntQuoteCustomerPoBack.getNtQuote()).isNull();

        ntQuote.customerPos(new HashSet<>(Set.of(ntQuoteCustomerPoBack)));
        assertThat(ntQuote.getCustomerPos()).containsOnly(ntQuoteCustomerPoBack);
        assertThat(ntQuoteCustomerPoBack.getNtQuote()).isEqualTo(ntQuote);

        ntQuote.setCustomerPos(new HashSet<>());
        assertThat(ntQuote.getCustomerPos()).doesNotContain(ntQuoteCustomerPoBack);
        assertThat(ntQuoteCustomerPoBack.getNtQuote()).isNull();
    }

    @Test
    void vendorQuoteTest() {
        NtQuote ntQuote = getNtQuoteRandomSampleGenerator();
        NtQuoteVendorQuote ntQuoteVendorQuoteBack = getNtQuoteVendorQuoteRandomSampleGenerator();

        ntQuote.addVendorQuote(ntQuoteVendorQuoteBack);
        assertThat(ntQuote.getVendorQuotes()).containsOnly(ntQuoteVendorQuoteBack);
        assertThat(ntQuoteVendorQuoteBack.getNtQuote()).isEqualTo(ntQuote);

        ntQuote.removeVendorQuote(ntQuoteVendorQuoteBack);
        assertThat(ntQuote.getVendorQuotes()).doesNotContain(ntQuoteVendorQuoteBack);
        assertThat(ntQuoteVendorQuoteBack.getNtQuote()).isNull();

        ntQuote.vendorQuotes(new HashSet<>(Set.of(ntQuoteVendorQuoteBack)));
        assertThat(ntQuote.getVendorQuotes()).containsOnly(ntQuoteVendorQuoteBack);
        assertThat(ntQuoteVendorQuoteBack.getNtQuote()).isEqualTo(ntQuote);

        ntQuote.setVendorQuotes(new HashSet<>());
        assertThat(ntQuote.getVendorQuotes()).doesNotContain(ntQuoteVendorQuoteBack);
        assertThat(ntQuoteVendorQuoteBack.getNtQuote()).isNull();
    }

    @Test
    void vendorPoTest() {
        NtQuote ntQuote = getNtQuoteRandomSampleGenerator();
        NtQuoteVendorPo ntQuoteVendorPoBack = getNtQuoteVendorPoRandomSampleGenerator();

        ntQuote.addVendorPo(ntQuoteVendorPoBack);
        assertThat(ntQuote.getVendorPos()).containsOnly(ntQuoteVendorPoBack);
        assertThat(ntQuoteVendorPoBack.getNtQuote()).isEqualTo(ntQuote);

        ntQuote.removeVendorPo(ntQuoteVendorPoBack);
        assertThat(ntQuote.getVendorPos()).doesNotContain(ntQuoteVendorPoBack);
        assertThat(ntQuoteVendorPoBack.getNtQuote()).isNull();

        ntQuote.vendorPos(new HashSet<>(Set.of(ntQuoteVendorPoBack)));
        assertThat(ntQuote.getVendorPos()).containsOnly(ntQuoteVendorPoBack);
        assertThat(ntQuoteVendorPoBack.getNtQuote()).isEqualTo(ntQuote);

        ntQuote.setVendorPos(new HashSet<>());
        assertThat(ntQuote.getVendorPos()).doesNotContain(ntQuoteVendorPoBack);
        assertThat(ntQuoteVendorPoBack.getNtQuote()).isNull();
    }

    @Test
    void rfqDetailTest() {
        NtQuote ntQuote = getNtQuoteRandomSampleGenerator();
        RfqDetail rfqDetailBack = getRfqDetailRandomSampleGenerator();

        ntQuote.setRfqDetail(rfqDetailBack);
        assertThat(ntQuote.getRfqDetail()).isEqualTo(rfqDetailBack);

        ntQuote.rfqDetail(null);
        assertThat(ntQuote.getRfqDetail()).isNull();
    }

    @Test
    void ntQuoteProjectApprovalTest() {
        NtQuote ntQuote = getNtQuoteRandomSampleGenerator();
        NtQuoteProjectApproval ntQuoteProjectApprovalBack = getNtQuoteProjectApprovalRandomSampleGenerator();

        ntQuote.setNtQuoteProjectApproval(ntQuoteProjectApprovalBack);
        assertThat(ntQuote.getNtQuoteProjectApproval()).isEqualTo(ntQuoteProjectApprovalBack);

        ntQuote.ntQuoteProjectApproval(null);
        assertThat(ntQuote.getNtQuoteProjectApproval()).isNull();
    }
}
