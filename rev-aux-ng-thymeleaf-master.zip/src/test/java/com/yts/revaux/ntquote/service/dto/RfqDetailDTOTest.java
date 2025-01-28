package com.yts.revaux.ntquote.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RfqDetailDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RfqDetailDTO.class);
        RfqDetailDTO rfqDetailDTO1 = new RfqDetailDTO();
        rfqDetailDTO1.setId(1L);
        RfqDetailDTO rfqDetailDTO2 = new RfqDetailDTO();
        assertThat(rfqDetailDTO1).isNotEqualTo(rfqDetailDTO2);
        rfqDetailDTO2.setId(rfqDetailDTO1.getId());
        assertThat(rfqDetailDTO1).isEqualTo(rfqDetailDTO2);
        rfqDetailDTO2.setId(2L);
        assertThat(rfqDetailDTO1).isNotEqualTo(rfqDetailDTO2);
        rfqDetailDTO1.setId(null);
        assertThat(rfqDetailDTO1).isNotEqualTo(rfqDetailDTO2);
    }
}
