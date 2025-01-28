package com.yts.revaux.ntquote.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NtQuoteCustomerProjectDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NtQuoteCustomerProjectDTO.class);
        NtQuoteCustomerProjectDTO ntQuoteCustomerProjectDTO1 = new NtQuoteCustomerProjectDTO();
        ntQuoteCustomerProjectDTO1.setId(1L);
        NtQuoteCustomerProjectDTO ntQuoteCustomerProjectDTO2 = new NtQuoteCustomerProjectDTO();
        assertThat(ntQuoteCustomerProjectDTO1).isNotEqualTo(ntQuoteCustomerProjectDTO2);
        ntQuoteCustomerProjectDTO2.setId(ntQuoteCustomerProjectDTO1.getId());
        assertThat(ntQuoteCustomerProjectDTO1).isEqualTo(ntQuoteCustomerProjectDTO2);
        ntQuoteCustomerProjectDTO2.setId(2L);
        assertThat(ntQuoteCustomerProjectDTO1).isNotEqualTo(ntQuoteCustomerProjectDTO2);
        ntQuoteCustomerProjectDTO1.setId(null);
        assertThat(ntQuoteCustomerProjectDTO1).isNotEqualTo(ntQuoteCustomerProjectDTO2);
    }
}
