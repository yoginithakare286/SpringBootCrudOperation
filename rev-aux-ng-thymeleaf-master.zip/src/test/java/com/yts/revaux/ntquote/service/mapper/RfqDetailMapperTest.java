package com.yts.revaux.ntquote.service.mapper;

import static com.yts.revaux.ntquote.domain.RfqDetailAsserts.*;
import static com.yts.revaux.ntquote.domain.RfqDetailTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RfqDetailMapperTest {

    private RfqDetailMapper rfqDetailMapper;

    @BeforeEach
    void setUp() {
        rfqDetailMapper = new RfqDetailMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getRfqDetailSample1();
        var actual = rfqDetailMapper.toEntity(rfqDetailMapper.toDto(expected));
        assertRfqDetailAllPropertiesEquals(expected, actual);
    }
}
