package com.yts.revaux.ntquote.domain;

import static com.yts.revaux.ntquote.domain.NtQuotePartInformationMasterTestSamples.*;
import static com.yts.revaux.ntquote.domain.NtQuoteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NtQuotePartInformationMasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NtQuotePartInformationMaster.class);
        NtQuotePartInformationMaster ntQuotePartInformationMaster1 = getNtQuotePartInformationMasterSample1();
        NtQuotePartInformationMaster ntQuotePartInformationMaster2 = new NtQuotePartInformationMaster();
        assertThat(ntQuotePartInformationMaster1).isNotEqualTo(ntQuotePartInformationMaster2);

        ntQuotePartInformationMaster2.setId(ntQuotePartInformationMaster1.getId());
        assertThat(ntQuotePartInformationMaster1).isEqualTo(ntQuotePartInformationMaster2);

        ntQuotePartInformationMaster2 = getNtQuotePartInformationMasterSample2();
        assertThat(ntQuotePartInformationMaster1).isNotEqualTo(ntQuotePartInformationMaster2);
    }

    @Test
    void ntQuoteTest() {
        NtQuotePartInformationMaster ntQuotePartInformationMaster = getNtQuotePartInformationMasterRandomSampleGenerator();
        NtQuote ntQuoteBack = getNtQuoteRandomSampleGenerator();

        ntQuotePartInformationMaster.setNtQuote(ntQuoteBack);
        assertThat(ntQuotePartInformationMaster.getNtQuote()).isEqualTo(ntQuoteBack);

        ntQuotePartInformationMaster.ntQuote(null);
        assertThat(ntQuotePartInformationMaster.getNtQuote()).isNull();
    }
}
