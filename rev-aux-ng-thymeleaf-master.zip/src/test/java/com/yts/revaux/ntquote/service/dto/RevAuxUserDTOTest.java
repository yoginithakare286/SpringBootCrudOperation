package com.yts.revaux.ntquote.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RevAuxUserDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RevAuxUserDTO.class);
        RevAuxUserDTO revAuxUserDTO1 = new RevAuxUserDTO();
        revAuxUserDTO1.setId(1L);
        RevAuxUserDTO revAuxUserDTO2 = new RevAuxUserDTO();
        assertThat(revAuxUserDTO1).isNotEqualTo(revAuxUserDTO2);
        revAuxUserDTO2.setId(revAuxUserDTO1.getId());
        assertThat(revAuxUserDTO1).isEqualTo(revAuxUserDTO2);
        revAuxUserDTO2.setId(2L);
        assertThat(revAuxUserDTO1).isNotEqualTo(revAuxUserDTO2);
        revAuxUserDTO1.setId(null);
        assertThat(revAuxUserDTO1).isNotEqualTo(revAuxUserDTO2);
    }
}
