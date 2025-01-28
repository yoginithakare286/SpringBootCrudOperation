package com.yts.revaux.ntquote.domain;

import static com.yts.revaux.ntquote.domain.NtQuoteCustomerPoTestSamples.*;
import static com.yts.revaux.ntquote.domain.NtQuoteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NtQuoteCustomerPoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NtQuoteCustomerPo.class);
        NtQuoteCustomerPo ntQuoteCustomerPo1 = getNtQuoteCustomerPoSample1();
        NtQuoteCustomerPo ntQuoteCustomerPo2 = new NtQuoteCustomerPo();
        assertThat(ntQuoteCustomerPo1).isNotEqualTo(ntQuoteCustomerPo2);

        ntQuoteCustomerPo2.setId(ntQuoteCustomerPo1.getId());
        assertThat(ntQuoteCustomerPo1).isEqualTo(ntQuoteCustomerPo2);

        ntQuoteCustomerPo2 = getNtQuoteCustomerPoSample2();
        assertThat(ntQuoteCustomerPo1).isNotEqualTo(ntQuoteCustomerPo2);
    }

    @Test
    void ntQuoteTest() {
        NtQuoteCustomerPo ntQuoteCustomerPo = getNtQuoteCustomerPoRandomSampleGenerator();
        NtQuote ntQuoteBack = getNtQuoteRandomSampleGenerator();

        ntQuoteCustomerPo.setNtQuote(ntQuoteBack);
        assertThat(ntQuoteCustomerPo.getNtQuote()).isEqualTo(ntQuoteBack);

        ntQuoteCustomerPo.ntQuote(null);
        assertThat(ntQuoteCustomerPo.getNtQuote()).isNull();
    }
}
