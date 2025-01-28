package com.yts.revaux.ntquote.domain;

import static com.yts.revaux.ntquote.domain.BuyerRfqPricesDetailTestSamples.*;
import static com.yts.revaux.ntquote.domain.RfqDetailTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RfqDetailTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RfqDetail.class);
        RfqDetail rfqDetail1 = getRfqDetailSample1();
        RfqDetail rfqDetail2 = new RfqDetail();
        assertThat(rfqDetail1).isNotEqualTo(rfqDetail2);

        rfqDetail2.setId(rfqDetail1.getId());
        assertThat(rfqDetail1).isEqualTo(rfqDetail2);

        rfqDetail2 = getRfqDetailSample2();
        assertThat(rfqDetail1).isNotEqualTo(rfqDetail2);
    }

    @Test
    void buyerRfqPricesDetailTest() {
        RfqDetail rfqDetail = getRfqDetailRandomSampleGenerator();
        BuyerRfqPricesDetail buyerRfqPricesDetailBack = getBuyerRfqPricesDetailRandomSampleGenerator();

        rfqDetail.setBuyerRfqPricesDetail(buyerRfqPricesDetailBack);
        assertThat(rfqDetail.getBuyerRfqPricesDetail()).isEqualTo(buyerRfqPricesDetailBack);
        assertThat(buyerRfqPricesDetailBack.getRfqDetail()).isEqualTo(rfqDetail);

        rfqDetail.buyerRfqPricesDetail(null);
        assertThat(rfqDetail.getBuyerRfqPricesDetail()).isNull();
        assertThat(buyerRfqPricesDetailBack.getRfqDetail()).isNull();
    }
}
