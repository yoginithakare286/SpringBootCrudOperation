package com.yts.revaux.ntquote.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NtQuoteTermsConditionsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NtQuoteTermsConditionsDTO.class);
        NtQuoteTermsConditionsDTO ntQuoteTermsConditionsDTO1 = new NtQuoteTermsConditionsDTO();
        ntQuoteTermsConditionsDTO1.setId(1L);
        NtQuoteTermsConditionsDTO ntQuoteTermsConditionsDTO2 = new NtQuoteTermsConditionsDTO();
        assertThat(ntQuoteTermsConditionsDTO1).isNotEqualTo(ntQuoteTermsConditionsDTO2);
        ntQuoteTermsConditionsDTO2.setId(ntQuoteTermsConditionsDTO1.getId());
        assertThat(ntQuoteTermsConditionsDTO1).isEqualTo(ntQuoteTermsConditionsDTO2);
        ntQuoteTermsConditionsDTO2.setId(2L);
        assertThat(ntQuoteTermsConditionsDTO1).isNotEqualTo(ntQuoteTermsConditionsDTO2);
        ntQuoteTermsConditionsDTO1.setId(null);
        assertThat(ntQuoteTermsConditionsDTO1).isNotEqualTo(ntQuoteTermsConditionsDTO2);
    }
}
