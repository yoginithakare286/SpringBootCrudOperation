package com.yts.revaux.ntquote.domain;

import static com.yts.revaux.ntquote.domain.NtProjectApprovalTestSamples.*;
import static com.yts.revaux.ntquote.domain.NtQuoteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NtProjectApprovalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NtProjectApproval.class);
        NtProjectApproval ntProjectApproval1 = getNtProjectApprovalSample1();
        NtProjectApproval ntProjectApproval2 = new NtProjectApproval();
        assertThat(ntProjectApproval1).isNotEqualTo(ntProjectApproval2);

        ntProjectApproval2.setId(ntProjectApproval1.getId());
        assertThat(ntProjectApproval1).isEqualTo(ntProjectApproval2);

        ntProjectApproval2 = getNtProjectApprovalSample2();
        assertThat(ntProjectApproval1).isNotEqualTo(ntProjectApproval2);
    }

    @Test
    void ntQuoteTest() {
        NtProjectApproval ntProjectApproval = getNtProjectApprovalRandomSampleGenerator();
        NtQuote ntQuoteBack = getNtQuoteRandomSampleGenerator();

        ntProjectApproval.setNtQuote(ntQuoteBack);
        assertThat(ntProjectApproval.getNtQuote()).isEqualTo(ntQuoteBack);

        ntProjectApproval.ntQuote(null);
        assertThat(ntProjectApproval.getNtQuote()).isNull();
    }
}
