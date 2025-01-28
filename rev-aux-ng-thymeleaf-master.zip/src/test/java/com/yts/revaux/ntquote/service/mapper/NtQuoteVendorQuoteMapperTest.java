package com.yts.revaux.ntquote.service.mapper;

import static com.yts.revaux.ntquote.domain.NtQuoteVendorQuoteAsserts.*;
import static com.yts.revaux.ntquote.domain.NtQuoteVendorQuoteTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NtQuoteVendorQuoteMapperTest {

    private NtQuoteVendorQuoteMapper ntQuoteVendorQuoteMapper;

    @BeforeEach
    void setUp() {
        ntQuoteVendorQuoteMapper = new NtQuoteVendorQuoteMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getNtQuoteVendorQuoteSample1();
        var actual = ntQuoteVendorQuoteMapper.toEntity(ntQuoteVendorQuoteMapper.toDto(expected));
        assertNtQuoteVendorQuoteAllPropertiesEquals(expected, actual);
    }
}
