package com.yts.revaux.ntquote.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NtQuoteProjectApprovalDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NtQuoteProjectApprovalDTO.class);
        NtQuoteProjectApprovalDTO ntQuoteProjectApprovalDTO1 = new NtQuoteProjectApprovalDTO();
        ntQuoteProjectApprovalDTO1.setId(1L);
        NtQuoteProjectApprovalDTO ntQuoteProjectApprovalDTO2 = new NtQuoteProjectApprovalDTO();
        assertThat(ntQuoteProjectApprovalDTO1).isNotEqualTo(ntQuoteProjectApprovalDTO2);
        ntQuoteProjectApprovalDTO2.setId(ntQuoteProjectApprovalDTO1.getId());
        assertThat(ntQuoteProjectApprovalDTO1).isEqualTo(ntQuoteProjectApprovalDTO2);
        ntQuoteProjectApprovalDTO2.setId(2L);
        assertThat(ntQuoteProjectApprovalDTO1).isNotEqualTo(ntQuoteProjectApprovalDTO2);
        ntQuoteProjectApprovalDTO1.setId(null);
        assertThat(ntQuoteProjectApprovalDTO1).isNotEqualTo(ntQuoteProjectApprovalDTO2);
    }
}
