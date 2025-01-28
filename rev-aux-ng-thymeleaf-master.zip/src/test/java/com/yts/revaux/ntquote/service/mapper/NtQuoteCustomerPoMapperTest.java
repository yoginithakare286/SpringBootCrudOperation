package com.yts.revaux.ntquote.service.mapper;

import static com.yts.revaux.ntquote.domain.NtQuoteCustomerPoAsserts.*;
import static com.yts.revaux.ntquote.domain.NtQuoteCustomerPoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NtQuoteCustomerPoMapperTest {

    private NtQuoteCustomerPoMapper ntQuoteCustomerPoMapper;

    @BeforeEach
    void setUp() {
        ntQuoteCustomerPoMapper = new NtQuoteCustomerPoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getNtQuoteCustomerPoSample1();
        var actual = ntQuoteCustomerPoMapper.toEntity(ntQuoteCustomerPoMapper.toDto(expected));
        assertNtQuoteCustomerPoAllPropertiesEquals(expected, actual);
    }
}
