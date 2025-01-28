package com.yts.revaux.ntquote.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NtQuotePartInformationMasterDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NtQuotePartInformationMasterDTO.class);
        NtQuotePartInformationMasterDTO ntQuotePartInformationMasterDTO1 = new NtQuotePartInformationMasterDTO();
        ntQuotePartInformationMasterDTO1.setId(1L);
        NtQuotePartInformationMasterDTO ntQuotePartInformationMasterDTO2 = new NtQuotePartInformationMasterDTO();
        assertThat(ntQuotePartInformationMasterDTO1).isNotEqualTo(ntQuotePartInformationMasterDTO2);
        ntQuotePartInformationMasterDTO2.setId(ntQuotePartInformationMasterDTO1.getId());
        assertThat(ntQuotePartInformationMasterDTO1).isEqualTo(ntQuotePartInformationMasterDTO2);
        ntQuotePartInformationMasterDTO2.setId(2L);
        assertThat(ntQuotePartInformationMasterDTO1).isNotEqualTo(ntQuotePartInformationMasterDTO2);
        ntQuotePartInformationMasterDTO1.setId(null);
        assertThat(ntQuotePartInformationMasterDTO1).isNotEqualTo(ntQuotePartInformationMasterDTO2);
    }
}
