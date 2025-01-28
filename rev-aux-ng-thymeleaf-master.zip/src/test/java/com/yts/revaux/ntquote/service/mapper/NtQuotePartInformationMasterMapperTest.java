package com.yts.revaux.ntquote.service.mapper;

import static com.yts.revaux.ntquote.domain.NtQuotePartInformationMasterAsserts.*;
import static com.yts.revaux.ntquote.domain.NtQuotePartInformationMasterTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NtQuotePartInformationMasterMapperTest {

    private NtQuotePartInformationMasterMapper ntQuotePartInformationMasterMapper;

    @BeforeEach
    void setUp() {
        ntQuotePartInformationMasterMapper = new NtQuotePartInformationMasterMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getNtQuotePartInformationMasterSample1();
        var actual = ntQuotePartInformationMasterMapper.toEntity(ntQuotePartInformationMasterMapper.toDto(expected));
        assertNtQuotePartInformationMasterAllPropertiesEquals(expected, actual);
    }
}
