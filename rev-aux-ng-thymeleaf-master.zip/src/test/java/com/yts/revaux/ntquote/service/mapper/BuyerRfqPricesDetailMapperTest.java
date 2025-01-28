package com.yts.revaux.ntquote.service.mapper;

import static com.yts.revaux.ntquote.domain.BuyerRfqPricesDetailAsserts.*;
import static com.yts.revaux.ntquote.domain.BuyerRfqPricesDetailTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BuyerRfqPricesDetailMapperTest {

    private BuyerRfqPricesDetailMapper buyerRfqPricesDetailMapper;

    @BeforeEach
    void setUp() {
        buyerRfqPricesDetailMapper = new BuyerRfqPricesDetailMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getBuyerRfqPricesDetailSample1();
        var actual = buyerRfqPricesDetailMapper.toEntity(buyerRfqPricesDetailMapper.toDto(expected));
        assertBuyerRfqPricesDetailAllPropertiesEquals(expected, actual);
    }
}
