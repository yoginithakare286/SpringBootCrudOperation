package com.yts.revaux.ntquote.service.mapper;

import static com.yts.revaux.ntquote.domain.RevAuxUserAsserts.*;
import static com.yts.revaux.ntquote.domain.RevAuxUserTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RevAuxUserMapperTest {

    private RevAuxUserMapper revAuxUserMapper;

    @BeforeEach
    void setUp() {
        revAuxUserMapper = new RevAuxUserMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getRevAuxUserSample1();
        var actual = revAuxUserMapper.toEntity(revAuxUserMapper.toDto(expected));
        assertRevAuxUserAllPropertiesEquals(expected, actual);
    }
}
