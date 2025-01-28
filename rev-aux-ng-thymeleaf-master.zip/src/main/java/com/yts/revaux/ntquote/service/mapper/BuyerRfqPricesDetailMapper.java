package com.yts.revaux.ntquote.service.mapper;

import com.yts.revaux.ntquote.domain.BuyerRfqPricesDetail;
import com.yts.revaux.ntquote.domain.RfqDetail;
import com.yts.revaux.ntquote.domain.VendorProfile;
import com.yts.revaux.ntquote.service.dto.BuyerRfqPricesDetailDTO;
import com.yts.revaux.ntquote.service.dto.RfqDetailDTO;
import com.yts.revaux.ntquote.service.dto.VendorProfileDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BuyerRfqPricesDetail} and its DTO {@link BuyerRfqPricesDetailDTO}.
 */
@Mapper(componentModel = "spring")
public interface BuyerRfqPricesDetailMapper extends EntityMapper<BuyerRfqPricesDetailDTO, BuyerRfqPricesDetail> {
    @Mapping(target = "rfqDetail", source = "rfqDetail", qualifiedByName = "rfqDetailId")
    @Mapping(target = "vendor", source = "vendor", qualifiedByName = "vendorProfileId")
    BuyerRfqPricesDetailDTO toDto(BuyerRfqPricesDetail s);

    @Named("rfqDetailId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RfqDetailDTO toDtoRfqDetailId(RfqDetail rfqDetail);

    @Named("vendorProfileId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VendorProfileDTO toDtoVendorProfileId(VendorProfile vendorProfile);
}
