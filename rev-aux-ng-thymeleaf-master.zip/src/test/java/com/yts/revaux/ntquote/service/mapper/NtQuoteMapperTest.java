package com.yts.revaux.ntquote.service.mapper;

import static com.yts.revaux.ntquote.domain.NtQuoteAsserts.*;
import static com.yts.revaux.ntquote.domain.NtQuoteTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NtQuoteMapperTest {

    private NtQuoteMapper ntQuoteMapper;

    @BeforeEach
    void setUp() {
        ntQuoteMapper = new NtQuoteMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getNtQuoteSample1();
        var actual = ntQuoteMapper.toEntity(ntQuoteMapper.toDto(expected));
        assertNtQuoteAllPropertiesEquals(expected, actual);
    }
}
