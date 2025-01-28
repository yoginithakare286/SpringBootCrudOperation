package com.yts.revaux.ntquote.domain;

import static com.yts.revaux.ntquote.domain.BuyerRfqPricesDetailTestSamples.*;
import static com.yts.revaux.ntquote.domain.VendorProfileTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VendorProfileTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VendorProfile.class);
        VendorProfile vendorProfile1 = getVendorProfileSample1();
        VendorProfile vendorProfile2 = new VendorProfile();
        assertThat(vendorProfile1).isNotEqualTo(vendorProfile2);

        vendorProfile2.setId(vendorProfile1.getId());
        assertThat(vendorProfile1).isEqualTo(vendorProfile2);

        vendorProfile2 = getVendorProfileSample2();
        assertThat(vendorProfile1).isNotEqualTo(vendorProfile2);
    }

    @Test
    void buyerRfqPricesDetailTest() {
        VendorProfile vendorProfile = getVendorProfileRandomSampleGenerator();
        BuyerRfqPricesDetail buyerRfqPricesDetailBack = getBuyerRfqPricesDetailRandomSampleGenerator();

        vendorProfile.setBuyerRfqPricesDetail(buyerRfqPricesDetailBack);
        assertThat(vendorProfile.getBuyerRfqPricesDetail()).isEqualTo(buyerRfqPricesDetailBack);
        assertThat(buyerRfqPricesDetailBack.getVendor()).isEqualTo(vendorProfile);

        vendorProfile.buyerRfqPricesDetail(null);
        assertThat(vendorProfile.getBuyerRfqPricesDetail()).isNull();
        assertThat(buyerRfqPricesDetailBack.getVendor()).isNull();
    }
}
