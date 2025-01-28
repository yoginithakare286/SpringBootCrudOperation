package com.yts.revaux.ntquote.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VendorProfileDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VendorProfileDTO.class);
        VendorProfileDTO vendorProfileDTO1 = new VendorProfileDTO();
        vendorProfileDTO1.setId(1L);
        VendorProfileDTO vendorProfileDTO2 = new VendorProfileDTO();
        assertThat(vendorProfileDTO1).isNotEqualTo(vendorProfileDTO2);
        vendorProfileDTO2.setId(vendorProfileDTO1.getId());
        assertThat(vendorProfileDTO1).isEqualTo(vendorProfileDTO2);
        vendorProfileDTO2.setId(2L);
        assertThat(vendorProfileDTO1).isNotEqualTo(vendorProfileDTO2);
        vendorProfileDTO1.setId(null);
        assertThat(vendorProfileDTO1).isNotEqualTo(vendorProfileDTO2);
    }
}
