package com.yts.revaux.ntquote.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NtQuoteCustomerPoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NtQuoteCustomerPoDTO.class);
        NtQuoteCustomerPoDTO ntQuoteCustomerPoDTO1 = new NtQuoteCustomerPoDTO();
        ntQuoteCustomerPoDTO1.setId(1L);
        NtQuoteCustomerPoDTO ntQuoteCustomerPoDTO2 = new NtQuoteCustomerPoDTO();
        assertThat(ntQuoteCustomerPoDTO1).isNotEqualTo(ntQuoteCustomerPoDTO2);
        ntQuoteCustomerPoDTO2.setId(ntQuoteCustomerPoDTO1.getId());
        assertThat(ntQuoteCustomerPoDTO1).isEqualTo(ntQuoteCustomerPoDTO2);
        ntQuoteCustomerPoDTO2.setId(2L);
        assertThat(ntQuoteCustomerPoDTO1).isNotEqualTo(ntQuoteCustomerPoDTO2);
        ntQuoteCustomerPoDTO1.setId(null);
        assertThat(ntQuoteCustomerPoDTO1).isNotEqualTo(ntQuoteCustomerPoDTO2);
    }
}
