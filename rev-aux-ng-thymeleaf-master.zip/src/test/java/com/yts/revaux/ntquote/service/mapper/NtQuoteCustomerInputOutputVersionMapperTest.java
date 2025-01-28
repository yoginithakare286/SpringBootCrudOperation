package com.yts.revaux.ntquote.service.mapper;

import static com.yts.revaux.ntquote.domain.NtQuoteCustomerInputOutputVersionAsserts.*;
import static com.yts.revaux.ntquote.domain.NtQuoteCustomerInputOutputVersionTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NtQuoteCustomerInputOutputVersionMapperTest {

    private NtQuoteCustomerInputOutputVersionMapper ntQuoteCustomerInputOutputVersionMapper;

    @BeforeEach
    void setUp() {
        ntQuoteCustomerInputOutputVersionMapper = new NtQuoteCustomerInputOutputVersionMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getNtQuoteCustomerInputOutputVersionSample1();
        var actual = ntQuoteCustomerInputOutputVersionMapper.toEntity(ntQuoteCustomerInputOutputVersionMapper.toDto(expected));
        assertNtQuoteCustomerInputOutputVersionAllPropertiesEquals(expected, actual);
    }
}
