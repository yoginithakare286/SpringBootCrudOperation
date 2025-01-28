package com.yts.revaux.ntquote.domain;

import static com.yts.revaux.ntquote.domain.NtQuoteTestSamples.*;
import static com.yts.revaux.ntquote.domain.NtQuoteVendorPoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NtQuoteVendorPoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NtQuoteVendorPo.class);
        NtQuoteVendorPo ntQuoteVendorPo1 = getNtQuoteVendorPoSample1();
        NtQuoteVendorPo ntQuoteVendorPo2 = new NtQuoteVendorPo();
        assertThat(ntQuoteVendorPo1).isNotEqualTo(ntQuoteVendorPo2);

        ntQuoteVendorPo2.setId(ntQuoteVendorPo1.getId());
        assertThat(ntQuoteVendorPo1).isEqualTo(ntQuoteVendorPo2);

        ntQuoteVendorPo2 = getNtQuoteVendorPoSample2();
        assertThat(ntQuoteVendorPo1).isNotEqualTo(ntQuoteVendorPo2);
    }

    @Test
    void ntQuoteTest() {
        NtQuoteVendorPo ntQuoteVendorPo = getNtQuoteVendorPoRandomSampleGenerator();
        NtQuote ntQuoteBack = getNtQuoteRandomSampleGenerator();

        ntQuoteVendorPo.setNtQuote(ntQuoteBack);
        assertThat(ntQuoteVendorPo.getNtQuote()).isEqualTo(ntQuoteBack);

        ntQuoteVendorPo.ntQuote(null);
        assertThat(ntQuoteVendorPo.getNtQuote()).isNull();
    }
}
