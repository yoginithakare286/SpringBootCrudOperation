package com.yts.revaux.ntquote.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NtQuoteCustomerInputOutputVersionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NtQuoteCustomerInputOutputVersionDTO.class);
        NtQuoteCustomerInputOutputVersionDTO ntQuoteCustomerInputOutputVersionDTO1 = new NtQuoteCustomerInputOutputVersionDTO();
        ntQuoteCustomerInputOutputVersionDTO1.setId(1L);
        NtQuoteCustomerInputOutputVersionDTO ntQuoteCustomerInputOutputVersionDTO2 = new NtQuoteCustomerInputOutputVersionDTO();
        assertThat(ntQuoteCustomerInputOutputVersionDTO1).isNotEqualTo(ntQuoteCustomerInputOutputVersionDTO2);
        ntQuoteCustomerInputOutputVersionDTO2.setId(ntQuoteCustomerInputOutputVersionDTO1.getId());
        assertThat(ntQuoteCustomerInputOutputVersionDTO1).isEqualTo(ntQuoteCustomerInputOutputVersionDTO2);
        ntQuoteCustomerInputOutputVersionDTO2.setId(2L);
        assertThat(ntQuoteCustomerInputOutputVersionDTO1).isNotEqualTo(ntQuoteCustomerInputOutputVersionDTO2);
        ntQuoteCustomerInputOutputVersionDTO1.setId(null);
        assertThat(ntQuoteCustomerInputOutputVersionDTO1).isNotEqualTo(ntQuoteCustomerInputOutputVersionDTO2);
    }
}
