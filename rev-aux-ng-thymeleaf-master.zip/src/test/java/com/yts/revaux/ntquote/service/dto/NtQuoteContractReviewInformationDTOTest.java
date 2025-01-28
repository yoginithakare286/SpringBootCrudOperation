package com.yts.revaux.ntquote.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NtQuoteContractReviewInformationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NtQuoteContractReviewInformationDTO.class);
        NtQuoteContractReviewInformationDTO ntQuoteContractReviewInformationDTO1 = new NtQuoteContractReviewInformationDTO();
        ntQuoteContractReviewInformationDTO1.setId(1L);
        NtQuoteContractReviewInformationDTO ntQuoteContractReviewInformationDTO2 = new NtQuoteContractReviewInformationDTO();
        assertThat(ntQuoteContractReviewInformationDTO1).isNotEqualTo(ntQuoteContractReviewInformationDTO2);
        ntQuoteContractReviewInformationDTO2.setId(ntQuoteContractReviewInformationDTO1.getId());
        assertThat(ntQuoteContractReviewInformationDTO1).isEqualTo(ntQuoteContractReviewInformationDTO2);
        ntQuoteContractReviewInformationDTO2.setId(2L);
        assertThat(ntQuoteContractReviewInformationDTO1).isNotEqualTo(ntQuoteContractReviewInformationDTO2);
        ntQuoteContractReviewInformationDTO1.setId(null);
        assertThat(ntQuoteContractReviewInformationDTO1).isNotEqualTo(ntQuoteContractReviewInformationDTO2);
    }
}
