package com.yts.revaux.ntquote.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NtQuoteVendorPoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NtQuoteVendorPoDTO.class);
        NtQuoteVendorPoDTO ntQuoteVendorPoDTO1 = new NtQuoteVendorPoDTO();
        ntQuoteVendorPoDTO1.setId(1L);
        NtQuoteVendorPoDTO ntQuoteVendorPoDTO2 = new NtQuoteVendorPoDTO();
        assertThat(ntQuoteVendorPoDTO1).isNotEqualTo(ntQuoteVendorPoDTO2);
        ntQuoteVendorPoDTO2.setId(ntQuoteVendorPoDTO1.getId());
        assertThat(ntQuoteVendorPoDTO1).isEqualTo(ntQuoteVendorPoDTO2);
        ntQuoteVendorPoDTO2.setId(2L);
        assertThat(ntQuoteVendorPoDTO1).isNotEqualTo(ntQuoteVendorPoDTO2);
        ntQuoteVendorPoDTO1.setId(null);
        assertThat(ntQuoteVendorPoDTO1).isNotEqualTo(ntQuoteVendorPoDTO2);
    }
}
