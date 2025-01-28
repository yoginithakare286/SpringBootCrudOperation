package com.yts.revaux.ntquote.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NtQuoteVendorQuoteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NtQuoteVendorQuoteDTO.class);
        NtQuoteVendorQuoteDTO ntQuoteVendorQuoteDTO1 = new NtQuoteVendorQuoteDTO();
        ntQuoteVendorQuoteDTO1.setId(1L);
        NtQuoteVendorQuoteDTO ntQuoteVendorQuoteDTO2 = new NtQuoteVendorQuoteDTO();
        assertThat(ntQuoteVendorQuoteDTO1).isNotEqualTo(ntQuoteVendorQuoteDTO2);
        ntQuoteVendorQuoteDTO2.setId(ntQuoteVendorQuoteDTO1.getId());
        assertThat(ntQuoteVendorQuoteDTO1).isEqualTo(ntQuoteVendorQuoteDTO2);
        ntQuoteVendorQuoteDTO2.setId(2L);
        assertThat(ntQuoteVendorQuoteDTO1).isNotEqualTo(ntQuoteVendorQuoteDTO2);
        ntQuoteVendorQuoteDTO1.setId(null);
        assertThat(ntQuoteVendorQuoteDTO1).isNotEqualTo(ntQuoteVendorQuoteDTO2);
    }
}
