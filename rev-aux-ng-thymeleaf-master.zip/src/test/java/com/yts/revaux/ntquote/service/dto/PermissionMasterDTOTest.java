package com.yts.revaux.ntquote.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PermissionMasterDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PermissionMasterDTO.class);
        PermissionMasterDTO permissionMasterDTO1 = new PermissionMasterDTO();
        permissionMasterDTO1.setId(1L);
        PermissionMasterDTO permissionMasterDTO2 = new PermissionMasterDTO();
        assertThat(permissionMasterDTO1).isNotEqualTo(permissionMasterDTO2);
        permissionMasterDTO2.setId(permissionMasterDTO1.getId());
        assertThat(permissionMasterDTO1).isEqualTo(permissionMasterDTO2);
        permissionMasterDTO2.setId(2L);
        assertThat(permissionMasterDTO1).isNotEqualTo(permissionMasterDTO2);
        permissionMasterDTO1.setId(null);
        assertThat(permissionMasterDTO1).isNotEqualTo(permissionMasterDTO2);
    }
}
