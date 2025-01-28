package com.yts.revaux.ntquote.domain;

import static com.yts.revaux.ntquote.domain.NtQuoteCustomerInputOutputVersionTestSamples.*;
import static com.yts.revaux.ntquote.domain.NtQuoteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NtQuoteCustomerInputOutputVersionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NtQuoteCustomerInputOutputVersion.class);
        NtQuoteCustomerInputOutputVersion ntQuoteCustomerInputOutputVersion1 = getNtQuoteCustomerInputOutputVersionSample1();
        NtQuoteCustomerInputOutputVersion ntQuoteCustomerInputOutputVersion2 = new NtQuoteCustomerInputOutputVersion();
        assertThat(ntQuoteCustomerInputOutputVersion1).isNotEqualTo(ntQuoteCustomerInputOutputVersion2);

        ntQuoteCustomerInputOutputVersion2.setId(ntQuoteCustomerInputOutputVersion1.getId());
        assertThat(ntQuoteCustomerInputOutputVersion1).isEqualTo(ntQuoteCustomerInputOutputVersion2);

        ntQuoteCustomerInputOutputVersion2 = getNtQuoteCustomerInputOutputVersionSample2();
        assertThat(ntQuoteCustomerInputOutputVersion1).isNotEqualTo(ntQuoteCustomerInputOutputVersion2);
    }

    @Test
    void ntQuoteTest() {
        NtQuoteCustomerInputOutputVersion ntQuoteCustomerInputOutputVersion = getNtQuoteCustomerInputOutputVersionRandomSampleGenerator();
        NtQuote ntQuoteBack = getNtQuoteRandomSampleGenerator();

        ntQuoteCustomerInputOutputVersion.setNtQuote(ntQuoteBack);
        assertThat(ntQuoteCustomerInputOutputVersion.getNtQuote()).isEqualTo(ntQuoteBack);

        ntQuoteCustomerInputOutputVersion.ntQuote(null);
        assertThat(ntQuoteCustomerInputOutputVersion.getNtQuote()).isNull();
    }
}
