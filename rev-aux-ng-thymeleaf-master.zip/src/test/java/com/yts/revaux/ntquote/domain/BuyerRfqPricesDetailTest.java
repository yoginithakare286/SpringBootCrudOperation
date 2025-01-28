package com.yts.revaux.ntquote.domain;

import static com.yts.revaux.ntquote.domain.BuyerRfqPricesDetailTestSamples.*;
import static com.yts.revaux.ntquote.domain.RfqDetailTestSamples.*;
import static com.yts.revaux.ntquote.domain.VendorProfileTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BuyerRfqPricesDetailTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BuyerRfqPricesDetail.class);
        BuyerRfqPricesDetail buyerRfqPricesDetail1 = getBuyerRfqPricesDetailSample1();
        BuyerRfqPricesDetail buyerRfqPricesDetail2 = new BuyerRfqPricesDetail();
        assertThat(buyerRfqPricesDetail1).isNotEqualTo(buyerRfqPricesDetail2);

        buyerRfqPricesDetail2.setId(buyerRfqPricesDetail1.getId());
        assertThat(buyerRfqPricesDetail1).isEqualTo(buyerRfqPricesDetail2);

        buyerRfqPricesDetail2 = getBuyerRfqPricesDetailSample2();
        assertThat(buyerRfqPricesDetail1).isNotEqualTo(buyerRfqPricesDetail2);
    }

    @Test
    void rfqDetailTest() {
        BuyerRfqPricesDetail buyerRfqPricesDetail = getBuyerRfqPricesDetailRandomSampleGenerator();
        RfqDetail rfqDetailBack = getRfqDetailRandomSampleGenerator();

        buyerRfqPricesDetail.setRfqDetail(rfqDetailBack);
        assertThat(buyerRfqPricesDetail.getRfqDetail()).isEqualTo(rfqDetailBack);

        buyerRfqPricesDetail.rfqDetail(null);
        assertThat(buyerRfqPricesDetail.getRfqDetail()).isNull();
    }

    @Test
    void vendorTest() {
        BuyerRfqPricesDetail buyerRfqPricesDetail = getBuyerRfqPricesDetailRandomSampleGenerator();
        VendorProfile vendorProfileBack = getVendorProfileRandomSampleGenerator();

        buyerRfqPricesDetail.setVendor(vendorProfileBack);
        assertThat(buyerRfqPricesDetail.getVendor()).isEqualTo(vendorProfileBack);

        buyerRfqPricesDetail.vendor(null);
        assertThat(buyerRfqPricesDetail.getVendor()).isNull();
    }
}
