package com.yts.revaux.ntquote.service.mapper;

import static com.yts.revaux.ntquote.domain.VendorProfileAsserts.*;
import static com.yts.revaux.ntquote.domain.VendorProfileTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VendorProfileMapperTest {

    private VendorProfileMapper vendorProfileMapper;

    @BeforeEach
    void setUp() {
        vendorProfileMapper = new VendorProfileMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getVendorProfileSample1();
        var actual = vendorProfileMapper.toEntity(vendorProfileMapper.toDto(expected));
        assertVendorProfileAllPropertiesEquals(expected, actual);
    }
}
