package com.yts.revaux.ntquote.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NtQuoteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NtQuoteDTO.class);
        NtQuoteDTO ntQuoteDTO1 = new NtQuoteDTO();
        ntQuoteDTO1.setId(1L);
        NtQuoteDTO ntQuoteDTO2 = new NtQuoteDTO();
        assertThat(ntQuoteDTO1).isNotEqualTo(ntQuoteDTO2);
        ntQuoteDTO2.setId(ntQuoteDTO1.getId());
        assertThat(ntQuoteDTO1).isEqualTo(ntQuoteDTO2);
        ntQuoteDTO2.setId(2L);
        assertThat(ntQuoteDTO1).isNotEqualTo(ntQuoteDTO2);
        ntQuoteDTO1.setId(null);
        assertThat(ntQuoteDTO1).isNotEqualTo(ntQuoteDTO2);
    }
}
