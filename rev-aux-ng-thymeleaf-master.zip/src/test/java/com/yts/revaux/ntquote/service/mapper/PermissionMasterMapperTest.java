package com.yts.revaux.ntquote.service.mapper;

import static com.yts.revaux.ntquote.domain.PermissionMasterAsserts.*;
import static com.yts.revaux.ntquote.domain.PermissionMasterTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PermissionMasterMapperTest {

    private PermissionMasterMapper permissionMasterMapper;

    @BeforeEach
    void setUp() {
        permissionMasterMapper = new PermissionMasterMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPermissionMasterSample1();
        var actual = permissionMasterMapper.toEntity(permissionMasterMapper.toDto(expected));
        assertPermissionMasterAllPropertiesEquals(expected, actual);
    }
}
