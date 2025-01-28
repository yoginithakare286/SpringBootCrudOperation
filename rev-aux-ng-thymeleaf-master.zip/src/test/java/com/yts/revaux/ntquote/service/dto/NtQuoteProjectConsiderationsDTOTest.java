package com.yts.revaux.ntquote.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NtQuoteProjectConsiderationsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NtQuoteProjectConsiderationsDTO.class);
        NtQuoteProjectConsiderationsDTO ntQuoteProjectConsiderationsDTO1 = new NtQuoteProjectConsiderationsDTO();
        ntQuoteProjectConsiderationsDTO1.setId(1L);
        NtQuoteProjectConsiderationsDTO ntQuoteProjectConsiderationsDTO2 = new NtQuoteProjectConsiderationsDTO();
        assertThat(ntQuoteProjectConsiderationsDTO1).isNotEqualTo(ntQuoteProjectConsiderationsDTO2);
        ntQuoteProjectConsiderationsDTO2.setId(ntQuoteProjectConsiderationsDTO1.getId());
        assertThat(ntQuoteProjectConsiderationsDTO1).isEqualTo(ntQuoteProjectConsiderationsDTO2);
        ntQuoteProjectConsiderationsDTO2.setId(2L);
        assertThat(ntQuoteProjectConsiderationsDTO1).isNotEqualTo(ntQuoteProjectConsiderationsDTO2);
        ntQuoteProjectConsiderationsDTO1.setId(null);
        assertThat(ntQuoteProjectConsiderationsDTO1).isNotEqualTo(ntQuoteProjectConsiderationsDTO2);
    }
}
