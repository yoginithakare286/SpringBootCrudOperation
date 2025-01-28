package com.yts.revaux.ntquote.service.mapper;

import static com.yts.revaux.ntquote.domain.NtProjectApprovalAsserts.*;
import static com.yts.revaux.ntquote.domain.NtProjectApprovalTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NtProjectApprovalMapperTest {

    private NtProjectApprovalMapper ntProjectApprovalMapper;

    @BeforeEach
    void setUp() {
        ntProjectApprovalMapper = new NtProjectApprovalMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getNtProjectApprovalSample1();
        var actual = ntProjectApprovalMapper.toEntity(ntProjectApprovalMapper.toDto(expected));
        assertNtProjectApprovalAllPropertiesEquals(expected, actual);
    }
}
