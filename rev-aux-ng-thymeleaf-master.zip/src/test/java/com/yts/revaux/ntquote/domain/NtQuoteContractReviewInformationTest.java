package com.yts.revaux.ntquote.domain;

import static com.yts.revaux.ntquote.domain.NtQuoteContractReviewInformationTestSamples.*;
import static com.yts.revaux.ntquote.domain.NtQuoteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NtQuoteContractReviewInformationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NtQuoteContractReviewInformation.class);
        NtQuoteContractReviewInformation ntQuoteContractReviewInformation1 = getNtQuoteContractReviewInformationSample1();
        NtQuoteContractReviewInformation ntQuoteContractReviewInformation2 = new NtQuoteContractReviewInformation();
        assertThat(ntQuoteContractReviewInformation1).isNotEqualTo(ntQuoteContractReviewInformation2);

        ntQuoteContractReviewInformation2.setId(ntQuoteContractReviewInformation1.getId());
        assertThat(ntQuoteContractReviewInformation1).isEqualTo(ntQuoteContractReviewInformation2);

        ntQuoteContractReviewInformation2 = getNtQuoteContractReviewInformationSample2();
        assertThat(ntQuoteContractReviewInformation1).isNotEqualTo(ntQuoteContractReviewInformation2);
    }

    @Test
    void ntQuoteTest() {
        NtQuoteContractReviewInformation ntQuoteContractReviewInformation = getNtQuoteContractReviewInformationRandomSampleGenerator();
        NtQuote ntQuoteBack = getNtQuoteRandomSampleGenerator();

        ntQuoteContractReviewInformation.setNtQuote(ntQuoteBack);
        assertThat(ntQuoteContractReviewInformation.getNtQuote()).isEqualTo(ntQuoteBack);

        ntQuoteContractReviewInformation.ntQuote(null);
        assertThat(ntQuoteContractReviewInformation.getNtQuote()).isNull();
    }
}
