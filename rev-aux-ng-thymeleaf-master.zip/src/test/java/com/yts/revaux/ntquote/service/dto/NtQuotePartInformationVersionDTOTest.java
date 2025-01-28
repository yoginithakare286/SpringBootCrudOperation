package com.yts.revaux.ntquote.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NtQuotePartInformationVersionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NtQuotePartInformationVersionDTO.class);
        NtQuotePartInformationVersionDTO ntQuotePartInformationVersionDTO1 = new NtQuotePartInformationVersionDTO();
        ntQuotePartInformationVersionDTO1.setId(1L);
        NtQuotePartInformationVersionDTO ntQuotePartInformationVersionDTO2 = new NtQuotePartInformationVersionDTO();
        assertThat(ntQuotePartInformationVersionDTO1).isNotEqualTo(ntQuotePartInformationVersionDTO2);
        ntQuotePartInformationVersionDTO2.setId(ntQuotePartInformationVersionDTO1.getId());
        assertThat(ntQuotePartInformationVersionDTO1).isEqualTo(ntQuotePartInformationVersionDTO2);
        ntQuotePartInformationVersionDTO2.setId(2L);
        assertThat(ntQuotePartInformationVersionDTO1).isNotEqualTo(ntQuotePartInformationVersionDTO2);
        ntQuotePartInformationVersionDTO1.setId(null);
        assertThat(ntQuotePartInformationVersionDTO1).isNotEqualTo(ntQuotePartInformationVersionDTO2);
    }
}
