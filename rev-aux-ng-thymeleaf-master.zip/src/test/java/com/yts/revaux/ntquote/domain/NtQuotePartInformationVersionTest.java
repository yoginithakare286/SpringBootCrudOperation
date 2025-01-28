package com.yts.revaux.ntquote.domain;

import static com.yts.revaux.ntquote.domain.NtQuotePartInformationVersionTestSamples.*;
import static com.yts.revaux.ntquote.domain.NtQuoteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NtQuotePartInformationVersionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NtQuotePartInformationVersion.class);
        NtQuotePartInformationVersion ntQuotePartInformationVersion1 = getNtQuotePartInformationVersionSample1();
        NtQuotePartInformationVersion ntQuotePartInformationVersion2 = new NtQuotePartInformationVersion();
        assertThat(ntQuotePartInformationVersion1).isNotEqualTo(ntQuotePartInformationVersion2);

        ntQuotePartInformationVersion2.setId(ntQuotePartInformationVersion1.getId());
        assertThat(ntQuotePartInformationVersion1).isEqualTo(ntQuotePartInformationVersion2);

        ntQuotePartInformationVersion2 = getNtQuotePartInformationVersionSample2();
        assertThat(ntQuotePartInformationVersion1).isNotEqualTo(ntQuotePartInformationVersion2);
    }

    @Test
    void ntQuoteTest() {
        NtQuotePartInformationVersion ntQuotePartInformationVersion = getNtQuotePartInformationVersionRandomSampleGenerator();
        NtQuote ntQuoteBack = getNtQuoteRandomSampleGenerator();

        ntQuotePartInformationVersion.setNtQuote(ntQuoteBack);
        assertThat(ntQuotePartInformationVersion.getNtQuote()).isEqualTo(ntQuoteBack);

        ntQuotePartInformationVersion.ntQuote(null);
        assertThat(ntQuotePartInformationVersion.getNtQuote()).isNull();
    }
}
