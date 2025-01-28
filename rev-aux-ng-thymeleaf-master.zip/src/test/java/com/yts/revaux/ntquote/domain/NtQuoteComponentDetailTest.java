package com.yts.revaux.ntquote.domain;

import static com.yts.revaux.ntquote.domain.BuyerRfqPricesDetailTestSamples.*;
import static com.yts.revaux.ntquote.domain.NtQuoteComponentDetailTestSamples.*;
import static com.yts.revaux.ntquote.domain.NtQuoteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NtQuoteComponentDetailTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NtQuoteComponentDetail.class);
        NtQuoteComponentDetail ntQuoteComponentDetail1 = getNtQuoteComponentDetailSample1();
        NtQuoteComponentDetail ntQuoteComponentDetail2 = new NtQuoteComponentDetail();
        assertThat(ntQuoteComponentDetail1).isNotEqualTo(ntQuoteComponentDetail2);

        ntQuoteComponentDetail2.setId(ntQuoteComponentDetail1.getId());
        assertThat(ntQuoteComponentDetail1).isEqualTo(ntQuoteComponentDetail2);

        ntQuoteComponentDetail2 = getNtQuoteComponentDetailSample2();
        assertThat(ntQuoteComponentDetail1).isNotEqualTo(ntQuoteComponentDetail2);
    }

    @Test
    void ntQuoteTest() {
        NtQuoteComponentDetail ntQuoteComponentDetail = getNtQuoteComponentDetailRandomSampleGenerator();
        NtQuote ntQuoteBack = getNtQuoteRandomSampleGenerator();

        ntQuoteComponentDetail.setNtQuote(ntQuoteBack);
        assertThat(ntQuoteComponentDetail.getNtQuote()).isEqualTo(ntQuoteBack);

        ntQuoteComponentDetail.ntQuote(null);
        assertThat(ntQuoteComponentDetail.getNtQuote()).isNull();
    }

    @Test
    void materialPriceTest() {
        NtQuoteComponentDetail ntQuoteComponentDetail = getNtQuoteComponentDetailRandomSampleGenerator();
        BuyerRfqPricesDetail buyerRfqPricesDetailBack = getBuyerRfqPricesDetailRandomSampleGenerator();

        ntQuoteComponentDetail.setMaterialPrice(buyerRfqPricesDetailBack);
        assertThat(ntQuoteComponentDetail.getMaterialPrice()).isEqualTo(buyerRfqPricesDetailBack);

        ntQuoteComponentDetail.materialPrice(null);
        assertThat(ntQuoteComponentDetail.getMaterialPrice()).isNull();
    }
}
