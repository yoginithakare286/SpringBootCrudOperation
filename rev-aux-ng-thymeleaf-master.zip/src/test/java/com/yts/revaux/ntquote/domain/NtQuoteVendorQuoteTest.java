package com.yts.revaux.ntquote.domain;

import static com.yts.revaux.ntquote.domain.NtQuoteTestSamples.*;
import static com.yts.revaux.ntquote.domain.NtQuoteVendorQuoteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NtQuoteVendorQuoteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NtQuoteVendorQuote.class);
        NtQuoteVendorQuote ntQuoteVendorQuote1 = getNtQuoteVendorQuoteSample1();
        NtQuoteVendorQuote ntQuoteVendorQuote2 = new NtQuoteVendorQuote();
        assertThat(ntQuoteVendorQuote1).isNotEqualTo(ntQuoteVendorQuote2);

        ntQuoteVendorQuote2.setId(ntQuoteVendorQuote1.getId());
        assertThat(ntQuoteVendorQuote1).isEqualTo(ntQuoteVendorQuote2);

        ntQuoteVendorQuote2 = getNtQuoteVendorQuoteSample2();
        assertThat(ntQuoteVendorQuote1).isNotEqualTo(ntQuoteVendorQuote2);
    }

    @Test
    void ntQuoteTest() {
        NtQuoteVendorQuote ntQuoteVendorQuote = getNtQuoteVendorQuoteRandomSampleGenerator();
        NtQuote ntQuoteBack = getNtQuoteRandomSampleGenerator();

        ntQuoteVendorQuote.setNtQuote(ntQuoteBack);
        assertThat(ntQuoteVendorQuote.getNtQuote()).isEqualTo(ntQuoteBack);

        ntQuoteVendorQuote.ntQuote(null);
        assertThat(ntQuoteVendorQuote.getNtQuote()).isNull();
    }
}
