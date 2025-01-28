package com.yts.revaux.ntquote.service.mapper;

import static com.yts.revaux.ntquote.domain.NtQuoteProjectApprovalAsserts.*;
import static com.yts.revaux.ntquote.domain.NtQuoteProjectApprovalTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NtQuoteProjectApprovalMapperTest {

    private NtQuoteProjectApprovalMapper ntQuoteProjectApprovalMapper;

    @BeforeEach
    void setUp() {
        ntQuoteProjectApprovalMapper = new NtQuoteProjectApprovalMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getNtQuoteProjectApprovalSample1();
        var actual = ntQuoteProjectApprovalMapper.toEntity(ntQuoteProjectApprovalMapper.toDto(expected));
        assertNtQuoteProjectApprovalAllPropertiesEquals(expected, actual);
    }
}
