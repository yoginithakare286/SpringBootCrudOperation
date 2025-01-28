package com.yts.revaux.ntquote.service.mapper;

import static com.yts.revaux.ntquote.domain.NtQuotePartInformationVersionAsserts.*;
import static com.yts.revaux.ntquote.domain.NtQuotePartInformationVersionTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NtQuotePartInformationVersionMapperTest {

    private NtQuotePartInformationVersionMapper ntQuotePartInformationVersionMapper;

    @BeforeEach
    void setUp() {
        ntQuotePartInformationVersionMapper = new NtQuotePartInformationVersionMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getNtQuotePartInformationVersionSample1();
        var actual = ntQuotePartInformationVersionMapper.toEntity(ntQuotePartInformationVersionMapper.toDto(expected));
        assertNtQuotePartInformationVersionAllPropertiesEquals(expected, actual);
    }
}
