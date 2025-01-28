package com.yts.revaux.ntquote.domain;

import static com.yts.revaux.ntquote.domain.PermissionMasterTestSamples.*;
import static com.yts.revaux.ntquote.domain.RevAuxUserTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.yts.revaux.ntquote.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class RevAuxUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RevAuxUser.class);
        RevAuxUser revAuxUser1 = getRevAuxUserSample1();
        RevAuxUser revAuxUser2 = new RevAuxUser();
        assertThat(revAuxUser1).isNotEqualTo(revAuxUser2);

        revAuxUser2.setId(revAuxUser1.getId());
        assertThat(revAuxUser1).isEqualTo(revAuxUser2);

        revAuxUser2 = getRevAuxUserSample2();
        assertThat(revAuxUser1).isNotEqualTo(revAuxUser2);
    }

    @Test
    void permissionsTest() {
        RevAuxUser revAuxUser = getRevAuxUserRandomSampleGenerator();
        PermissionMaster permissionMasterBack = getPermissionMasterRandomSampleGenerator();

        revAuxUser.addPermissions(permissionMasterBack);
        assertThat(revAuxUser.getPermissions()).containsOnly(permissionMasterBack);
        assertThat(permissionMasterBack.getRevAuxUser()).isEqualTo(revAuxUser);

        revAuxUser.removePermissions(permissionMasterBack);
        assertThat(revAuxUser.getPermissions()).doesNotContain(permissionMasterBack);
        assertThat(permissionMasterBack.getRevAuxUser()).isNull();

        revAuxUser.permissions(new HashSet<>(Set.of(permissionMasterBack)));
        assertThat(revAuxUser.getPermissions()).containsOnly(permissionMasterBack);
        assertThat(permissionMasterBack.getRevAuxUser()).isEqualTo(revAuxUser);

        revAuxUser.setPermissions(new HashSet<>());
        assertThat(revAuxUser.getPermissions()).doesNotContain(permissionMasterBack);
        assertThat(permissionMasterBack.getRevAuxUser()).isNull();
    }
}
