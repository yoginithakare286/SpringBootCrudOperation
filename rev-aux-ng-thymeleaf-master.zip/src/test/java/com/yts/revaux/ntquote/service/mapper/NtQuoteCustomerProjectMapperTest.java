package com.yts.revaux.ntquote.service.mapper;

import static com.yts.revaux.ntquote.domain.NtQuoteCustomerProjectAsserts.*;
import static com.yts.revaux.ntquote.domain.NtQuoteCustomerProjectTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NtQuoteCustomerProjectMapperTest {

    private NtQuoteCustomerProjectMapper ntQuoteCustomerProjectMapper;

    @BeforeEach
    void setUp() {
        ntQuoteCustomerProjectMapper = new NtQuoteCustomerProjectMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getNtQuoteCustomerProjectSample1();
        var actual = ntQuoteCustomerProjectMapper.toEntity(ntQuoteCustomerProjectMapper.toDto(expected));
        assertNtQuoteCustomerProjectAllPropertiesEquals(expected, actual);
    }
}
