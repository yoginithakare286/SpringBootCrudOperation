package com.yts.revaux.ntquote.service.mapper;

import com.yts.revaux.ntquote.domain.BuyerRfqPricesDetail;
import com.yts.revaux.ntquote.domain.NtQuote;
import com.yts.revaux.ntquote.domain.NtQuoteComponentDetail;
import com.yts.revaux.ntquote.service.dto.BuyerRfqPricesDetailDTO;
import com.yts.revaux.ntquote.service.dto.NtQuoteComponentDetailDTO;
import com.yts.revaux.ntquote.service.dto.NtQuoteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NtQuoteComponentDetail} and its DTO {@link NtQuoteComponentDetailDTO}.
 */
@Mapper(componentModel = "spring")
public interface NtQuoteComponentDetailMapper extends EntityMapper<NtQuoteComponentDetailDTO, NtQuoteComponentDetail> {
    @Mapping(target = "ntQuote", source = "ntQuote", qualifiedByName = "ntQuoteId")
    @Mapping(target = "materialPrice", source = "materialPrice", qualifiedByName = "buyerRfqPricesDetailId")
    NtQuoteComponentDetailDTO toDto(NtQuoteComponentDetail s);

    @Named("ntQuoteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    NtQuoteDTO toDtoNtQuoteId(NtQuote ntQuote);

    @Named("buyerRfqPricesDetailId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BuyerRfqPricesDetailDTO toDtoBuyerRfqPricesDetailId(BuyerRfqPricesDetail buyerRfqPricesDetail);
}
