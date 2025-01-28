package com.yts.revaux.ntquote.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NtQuoteCustomerInputOutputMasterDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NtQuoteCustomerInputOutputMasterDTO.class);
        NtQuoteCustomerInputOutputMasterDTO ntQuoteCustomerInputOutputMasterDTO1 = new NtQuoteCustomerInputOutputMasterDTO();
        ntQuoteCustomerInputOutputMasterDTO1.setId(1L);
        NtQuoteCustomerInputOutputMasterDTO ntQuoteCustomerInputOutputMasterDTO2 = new NtQuoteCustomerInputOutputMasterDTO();
        assertThat(ntQuoteCustomerInputOutputMasterDTO1).isNotEqualTo(ntQuoteCustomerInputOutputMasterDTO2);
        ntQuoteCustomerInputOutputMasterDTO2.setId(ntQuoteCustomerInputOutputMasterDTO1.getId());
        assertThat(ntQuoteCustomerInputOutputMasterDTO1).isEqualTo(ntQuoteCustomerInputOutputMasterDTO2);
        ntQuoteCustomerInputOutputMasterDTO2.setId(2L);
        assertThat(ntQuoteCustomerInputOutputMasterDTO1).isNotEqualTo(ntQuoteCustomerInputOutputMasterDTO2);
        ntQuoteCustomerInputOutputMasterDTO1.setId(null);
        assertThat(ntQuoteCustomerInputOutputMasterDTO1).isNotEqualTo(ntQuoteCustomerInputOutputMasterDTO2);
    }
}
