package com.yts.revaux.ntquote.service.mapper;

import static com.yts.revaux.ntquote.domain.NtQuoteCustomerInputOutputMasterAsserts.*;
import static com.yts.revaux.ntquote.domain.NtQuoteCustomerInputOutputMasterTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NtQuoteCustomerInputOutputMasterMapperTest {

    private NtQuoteCustomerInputOutputMasterMapper ntQuoteCustomerInputOutputMasterMapper;

    @BeforeEach
    void setUp() {
        ntQuoteCustomerInputOutputMasterMapper = new NtQuoteCustomerInputOutputMasterMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getNtQuoteCustomerInputOutputMasterSample1();
        var actual = ntQuoteCustomerInputOutputMasterMapper.toEntity(ntQuoteCustomerInputOutputMasterMapper.toDto(expected));
        assertNtQuoteCustomerInputOutputMasterAllPropertiesEquals(expected, actual);
    }
}
