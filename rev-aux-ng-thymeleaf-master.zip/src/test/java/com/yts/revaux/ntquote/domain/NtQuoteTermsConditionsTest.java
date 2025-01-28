package com.yts.revaux.ntquote.domain;

import static com.yts.revaux.ntquote.domain.NtQuoteTermsConditionsTestSamples.*;
import static com.yts.revaux.ntquote.domain.NtQuoteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NtQuoteTermsConditionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NtQuoteTermsConditions.class);
        NtQuoteTermsConditions ntQuoteTermsConditions1 = getNtQuoteTermsConditionsSample1();
        NtQuoteTermsConditions ntQuoteTermsConditions2 = new NtQuoteTermsConditions();
        assertThat(ntQuoteTermsConditions1).isNotEqualTo(ntQuoteTermsConditions2);

        ntQuoteTermsConditions2.setId(ntQuoteTermsConditions1.getId());
        assertThat(ntQuoteTermsConditions1).isEqualTo(ntQuoteTermsConditions2);

        ntQuoteTermsConditions2 = getNtQuoteTermsConditionsSample2();
        assertThat(ntQuoteTermsConditions1).isNotEqualTo(ntQuoteTermsConditions2);
    }

    @Test
    void ntQuoteTest() {
        NtQuoteTermsConditions ntQuoteTermsConditions = getNtQuoteTermsConditionsRandomSampleGenerator();
        NtQuote ntQuoteBack = getNtQuoteRandomSampleGenerator();

        ntQuoteTermsConditions.setNtQuote(ntQuoteBack);
        assertThat(ntQuoteTermsConditions.getNtQuote()).isEqualTo(ntQuoteBack);

        ntQuoteTermsConditions.ntQuote(null);
        assertThat(ntQuoteTermsConditions.getNtQuote()).isNull();
    }
}
