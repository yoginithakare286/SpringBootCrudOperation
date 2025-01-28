package com.yts.revaux.ntquote.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NtQuoteCommentsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NtQuoteCommentsDTO.class);
        NtQuoteCommentsDTO ntQuoteCommentsDTO1 = new NtQuoteCommentsDTO();
        ntQuoteCommentsDTO1.setId(1L);
        NtQuoteCommentsDTO ntQuoteCommentsDTO2 = new NtQuoteCommentsDTO();
        assertThat(ntQuoteCommentsDTO1).isNotEqualTo(ntQuoteCommentsDTO2);
        ntQuoteCommentsDTO2.setId(ntQuoteCommentsDTO1.getId());
        assertThat(ntQuoteCommentsDTO1).isEqualTo(ntQuoteCommentsDTO2);
        ntQuoteCommentsDTO2.setId(2L);
        assertThat(ntQuoteCommentsDTO1).isNotEqualTo(ntQuoteCommentsDTO2);
        ntQuoteCommentsDTO1.setId(null);
        assertThat(ntQuoteCommentsDTO1).isNotEqualTo(ntQuoteCommentsDTO2);
    }
}
