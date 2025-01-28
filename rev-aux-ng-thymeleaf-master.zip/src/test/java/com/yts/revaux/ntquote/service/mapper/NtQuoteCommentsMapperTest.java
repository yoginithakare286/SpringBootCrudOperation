package com.yts.revaux.ntquote.service.mapper;

import static com.yts.revaux.ntquote.domain.NtQuoteCommentsAsserts.*;
import static com.yts.revaux.ntquote.domain.NtQuoteCommentsTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NtQuoteCommentsMapperTest {

    private NtQuoteCommentsMapper ntQuoteCommentsMapper;

    @BeforeEach
    void setUp() {
        ntQuoteCommentsMapper = new NtQuoteCommentsMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getNtQuoteCommentsSample1();
        var actual = ntQuoteCommentsMapper.toEntity(ntQuoteCommentsMapper.toDto(expected));
        assertNtQuoteCommentsAllPropertiesEquals(expected, actual);
    }
}
