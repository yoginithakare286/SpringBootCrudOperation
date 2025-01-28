package com.yts.revaux.ntquote.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NtProjectApprovalDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NtProjectApprovalDTO.class);
        NtProjectApprovalDTO ntProjectApprovalDTO1 = new NtProjectApprovalDTO();
        ntProjectApprovalDTO1.setId(1L);
        NtProjectApprovalDTO ntProjectApprovalDTO2 = new NtProjectApprovalDTO();
        assertThat(ntProjectApprovalDTO1).isNotEqualTo(ntProjectApprovalDTO2);
        ntProjectApprovalDTO2.setId(ntProjectApprovalDTO1.getId());
        assertThat(ntProjectApprovalDTO1).isEqualTo(ntProjectApprovalDTO2);
        ntProjectApprovalDTO2.setId(2L);
        assertThat(ntProjectApprovalDTO1).isNotEqualTo(ntProjectApprovalDTO2);
        ntProjectApprovalDTO1.setId(null);
        assertThat(ntProjectApprovalDTO1).isNotEqualTo(ntProjectApprovalDTO2);
    }
}
