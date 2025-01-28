package com.yts.revaux.ntquote.domain;

import static com.yts.revaux.ntquote.domain.PermissionMasterTestSamples.*;
import static com.yts.revaux.ntquote.domain.RevAuxUserTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PermissionMasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PermissionMaster.class);
        PermissionMaster permissionMaster1 = getPermissionMasterSample1();
        PermissionMaster permissionMaster2 = new PermissionMaster();
        assertThat(permissionMaster1).isNotEqualTo(permissionMaster2);

        permissionMaster2.setId(permissionMaster1.getId());
        assertThat(permissionMaster1).isEqualTo(permissionMaster2);

        permissionMaster2 = getPermissionMasterSample2();
        assertThat(permissionMaster1).isNotEqualTo(permissionMaster2);
    }

    @Test
    void revAuxUserTest() {
        PermissionMaster permissionMaster = getPermissionMasterRandomSampleGenerator();
        RevAuxUser revAuxUserBack = getRevAuxUserRandomSampleGenerator();

        permissionMaster.setRevAuxUser(revAuxUserBack);
        assertThat(permissionMaster.getRevAuxUser()).isEqualTo(revAuxUserBack);

        permissionMaster.revAuxUser(null);
        assertThat(permissionMaster.getRevAuxUser()).isNull();
    }
}
