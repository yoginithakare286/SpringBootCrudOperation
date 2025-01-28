package com.yts.revaux.ntquote.domain;

import static com.yts.revaux.ntquote.domain.NtQuoteCustomerProjectTestSamples.*;
import static com.yts.revaux.ntquote.domain.NtQuoteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NtQuoteCustomerProjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NtQuoteCustomerProject.class);
        NtQuoteCustomerProject ntQuoteCustomerProject1 = getNtQuoteCustomerProjectSample1();
        NtQuoteCustomerProject ntQuoteCustomerProject2 = new NtQuoteCustomerProject();
        assertThat(ntQuoteCustomerProject1).isNotEqualTo(ntQuoteCustomerProject2);

        ntQuoteCustomerProject2.setId(ntQuoteCustomerProject1.getId());
        assertThat(ntQuoteCustomerProject1).isEqualTo(ntQuoteCustomerProject2);

        ntQuoteCustomerProject2 = getNtQuoteCustomerProjectSample2();
        assertThat(ntQuoteCustomerProject1).isNotEqualTo(ntQuoteCustomerProject2);
    }

    @Test
    void ntQuoteTest() {
        NtQuoteCustomerProject ntQuoteCustomerProject = getNtQuoteCustomerProjectRandomSampleGenerator();
        NtQuote ntQuoteBack = getNtQuoteRandomSampleGenerator();

        ntQuoteCustomerProject.setNtQuote(ntQuoteBack);
        assertThat(ntQuoteCustomerProject.getNtQuote()).isEqualTo(ntQuoteBack);

        ntQuoteCustomerProject.ntQuote(null);
        assertThat(ntQuoteCustomerProject.getNtQuote()).isNull();
    }
}
