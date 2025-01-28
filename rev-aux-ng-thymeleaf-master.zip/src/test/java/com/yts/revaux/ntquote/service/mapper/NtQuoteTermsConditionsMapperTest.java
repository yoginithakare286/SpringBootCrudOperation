package com.yts.revaux.ntquote.service.mapper;

import static com.yts.revaux.ntquote.domain.NtQuoteTermsConditionsAsserts.*;
import static com.yts.revaux.ntquote.domain.NtQuoteTermsConditionsTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NtQuoteTermsConditionsMapperTest {

    private NtQuoteTermsConditionsMapper ntQuoteTermsConditionsMapper;

    @BeforeEach
    void setUp() {
        ntQuoteTermsConditionsMapper = new NtQuoteTermsConditionsMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getNtQuoteTermsConditionsSample1();
        var actual = ntQuoteTermsConditionsMapper.toEntity(ntQuoteTermsConditionsMapper.toDto(expected));
        assertNtQuoteTermsConditionsAllPropertiesEquals(expected, actual);
    }
}
