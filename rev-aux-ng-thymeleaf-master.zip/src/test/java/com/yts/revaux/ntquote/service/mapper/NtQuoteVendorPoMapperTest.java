package com.yts.revaux.ntquote.service.mapper;

import static com.yts.revaux.ntquote.domain.NtQuoteVendorPoAsserts.*;
import static com.yts.revaux.ntquote.domain.NtQuoteVendorPoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NtQuoteVendorPoMapperTest {

    private NtQuoteVendorPoMapper ntQuoteVendorPoMapper;

    @BeforeEach
    void setUp() {
        ntQuoteVendorPoMapper = new NtQuoteVendorPoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getNtQuoteVendorPoSample1();
        var actual = ntQuoteVendorPoMapper.toEntity(ntQuoteVendorPoMapper.toDto(expected));
        assertNtQuoteVendorPoAllPropertiesEquals(expected, actual);
    }
}
