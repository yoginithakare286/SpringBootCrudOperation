package com.yts.revaux.ntquote.service.mapper;

import static com.yts.revaux.ntquote.domain.NtQuoteContractReviewInformationAsserts.*;
import static com.yts.revaux.ntquote.domain.NtQuoteContractReviewInformationTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NtQuoteContractReviewInformationMapperTest {

    private NtQuoteContractReviewInformationMapper ntQuoteContractReviewInformationMapper;

    @BeforeEach
    void setUp() {
        ntQuoteContractReviewInformationMapper = new NtQuoteContractReviewInformationMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getNtQuoteContractReviewInformationSample1();
        var actual = ntQuoteContractReviewInformationMapper.toEntity(ntQuoteContractReviewInformationMapper.toDto(expected));
        assertNtQuoteContractReviewInformationAllPropertiesEquals(expected, actual);
    }
}
