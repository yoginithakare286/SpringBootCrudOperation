package com.yts.revaux.ntquote.domain;

import static com.yts.revaux.ntquote.domain.NtQuoteProjectConsiderationsTestSamples.*;
import static com.yts.revaux.ntquote.domain.NtQuoteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NtQuoteProjectConsiderationsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NtQuoteProjectConsiderations.class);
        NtQuoteProjectConsiderations ntQuoteProjectConsiderations1 = getNtQuoteProjectConsiderationsSample1();
        NtQuoteProjectConsiderations ntQuoteProjectConsiderations2 = new NtQuoteProjectConsiderations();
        assertThat(ntQuoteProjectConsiderations1).isNotEqualTo(ntQuoteProjectConsiderations2);

        ntQuoteProjectConsiderations2.setId(ntQuoteProjectConsiderations1.getId());
        assertThat(ntQuoteProjectConsiderations1).isEqualTo(ntQuoteProjectConsiderations2);

        ntQuoteProjectConsiderations2 = getNtQuoteProjectConsiderationsSample2();
        assertThat(ntQuoteProjectConsiderations1).isNotEqualTo(ntQuoteProjectConsiderations2);
    }

    @Test
    void ntQuoteTest() {
        NtQuoteProjectConsiderations ntQuoteProjectConsiderations = getNtQuoteProjectConsiderationsRandomSampleGenerator();
        NtQuote ntQuoteBack = getNtQuoteRandomSampleGenerator();

        ntQuoteProjectConsiderations.setNtQuote(ntQuoteBack);
        assertThat(ntQuoteProjectConsiderations.getNtQuote()).isEqualTo(ntQuoteBack);

        ntQuoteProjectConsiderations.ntQuote(null);
        assertThat(ntQuoteProjectConsiderations.getNtQuote()).isNull();
    }
}
