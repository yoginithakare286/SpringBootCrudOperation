package com.yts.revaux.ntquote.service.mapper;

import static com.yts.revaux.ntquote.domain.NtQuoteComponentDetailAsserts.*;
import static com.yts.revaux.ntquote.domain.NtQuoteComponentDetailTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NtQuoteComponentDetailMapperTest {

    private NtQuoteComponentDetailMapper ntQuoteComponentDetailMapper;

    @BeforeEach
    void setUp() {
        ntQuoteComponentDetailMapper = new NtQuoteComponentDetailMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getNtQuoteComponentDetailSample1();
        var actual = ntQuoteComponentDetailMapper.toEntity(ntQuoteComponentDetailMapper.toDto(expected));
        assertNtQuoteComponentDetailAllPropertiesEquals(expected, actual);
    }
}
