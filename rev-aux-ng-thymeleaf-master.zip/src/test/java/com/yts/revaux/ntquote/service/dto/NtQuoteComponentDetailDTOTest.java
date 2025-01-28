package com.yts.revaux.ntquote.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NtQuoteComponentDetailDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NtQuoteComponentDetailDTO.class);
        NtQuoteComponentDetailDTO ntQuoteComponentDetailDTO1 = new NtQuoteComponentDetailDTO();
        ntQuoteComponentDetailDTO1.setId(1L);
        NtQuoteComponentDetailDTO ntQuoteComponentDetailDTO2 = new NtQuoteComponentDetailDTO();
        assertThat(ntQuoteComponentDetailDTO1).isNotEqualTo(ntQuoteComponentDetailDTO2);
        ntQuoteComponentDetailDTO2.setId(ntQuoteComponentDetailDTO1.getId());
        assertThat(ntQuoteComponentDetailDTO1).isEqualTo(ntQuoteComponentDetailDTO2);
        ntQuoteComponentDetailDTO2.setId(2L);
        assertThat(ntQuoteComponentDetailDTO1).isNotEqualTo(ntQuoteComponentDetailDTO2);
        ntQuoteComponentDetailDTO1.setId(null);
        assertThat(ntQuoteComponentDetailDTO1).isNotEqualTo(ntQuoteComponentDetailDTO2);
    }
}
