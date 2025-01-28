package com.yts.revaux.ntquote.service.mapper;

import static com.yts.revaux.ntquote.domain.NtQuoteProjectConsiderationsAsserts.*;
import static com.yts.revaux.ntquote.domain.NtQuoteProjectConsiderationsTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NtQuoteProjectConsiderationsMapperTest {

    private NtQuoteProjectConsiderationsMapper ntQuoteProjectConsiderationsMapper;

    @BeforeEach
    void setUp() {
        ntQuoteProjectConsiderationsMapper = new NtQuoteProjectConsiderationsMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getNtQuoteProjectConsiderationsSample1();
        var actual = ntQuoteProjectConsiderationsMapper.toEntity(ntQuoteProjectConsiderationsMapper.toDto(expected));
        assertNtQuoteProjectConsiderationsAllPropertiesEquals(expected, actual);
    }
}
