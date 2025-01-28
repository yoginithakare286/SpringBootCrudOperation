package com.yts.revaux.ntquote.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BuyerRfqPricesDetailDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BuyerRfqPricesDetailDTO.class);
        BuyerRfqPricesDetailDTO buyerRfqPricesDetailDTO1 = new BuyerRfqPricesDetailDTO();
        buyerRfqPricesDetailDTO1.setId(1L);
        BuyerRfqPricesDetailDTO buyerRfqPricesDetailDTO2 = new BuyerRfqPricesDetailDTO();
        assertThat(buyerRfqPricesDetailDTO1).isNotEqualTo(buyerRfqPricesDetailDTO2);
        buyerRfqPricesDetailDTO2.setId(buyerRfqPricesDetailDTO1.getId());
        assertThat(buyerRfqPricesDetailDTO1).isEqualTo(buyerRfqPricesDetailDTO2);
        buyerRfqPricesDetailDTO2.setId(2L);
        assertThat(buyerRfqPricesDetailDTO1).isNotEqualTo(buyerRfqPricesDetailDTO2);
        buyerRfqPricesDetailDTO1.setId(null);
        assertThat(buyerRfqPricesDetailDTO1).isNotEqualTo(buyerRfqPricesDetailDTO2);
    }
}
